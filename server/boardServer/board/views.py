import json
import redis
from .serializers import PostSerializer
from .models import Category, Post, Tag
import logging
from django.db import DatabaseError

logger = logging.getLogger(__name__)
redis_conn = redis.StrictRedis(host='172.20.10.14', port=6379, db=0, decode_responses=True)

def save_to_redis(request_id, value, data=None):
    try:
        response_id = "PromotionalPostResponse"
        # 필요한 경우 JSON 문자열로 변환
        if not isinstance(value, str) or data:
            value = json.dumps({
                "@class": "org.json.simple.JSONObject",
                "result": value,
                "data": data if data else None
            })
        redis_conn.hset(response_id, request_id, value)
        logger.info(f"Saved data to Redis with key {request_id} {value}")
    except Exception as e:
        logger.error(f"Failed to save to Redis: {e}")

def handle_message(data):
    action = data.get("action", "").upper()
    request_id = data.get("requestId")

    try:
        if action == 'PMPOSTCREATE':
            tags_data = data.pop("pmTag", [])
            category_data = data.pop("pmCategory", None)

            if isinstance(tags_data, str):
                tags_data = [tags_data]
            if category_data:
                category, created = Category.objects.get_or_create(name=category_data)
                data["pmCategory"] = category_data

            serializer = PostSerializer(data=data)
            if serializer.is_valid():
                post_instance = serializer.save()

                for tag_name in tags_data:
                    tag, created = Tag.objects.get_or_create(name=tag_name)
                    post_instance.pmTag.add(tag)
                logger.info(f"Post created: {vars(post_instance)}")
                success = "success"
                failure = "No posts found"
                save_to_redis(request_id,  success)
            else:
                save_to_redis(request_id, failure)

        elif action == 'PMPOSTUPDATE':
            post = Post.objects.get(id=data['pmPostNumber'])

            tags_data = data.pop("pmTag", [])
            category_data = data.pop("pmCategory", None)

            if category_data:
                category, created = Category.objects.get_or_create(name=category_data)
                data["pmCategory"] = category.id

            serializer = PostSerializer(post, data=data)
            if serializer.is_valid():
                post_instance = serializer.save()

                post_instance.pmTag.clear()
                for tag_name in tags_data:
                    tag, created = Tag.objects.get_or_create(name=tag_name)
                    post_instance.pmTag.add(tag)
                success = "success"           
                failure = "No posts found"
                save_to_redis(request_id, success)
            else:
                save_to_redis(request_id, failure)

        elif action == 'PMPOSTDELETE':
            post = Post.objects.get(id=data['pmPostNumber'])
            post.delete()
            success = "success"
            save_to_redis(request_id, success)

        elif action == 'PMPOSTREADALL':
            posts = Post.objects.all()
            serializer = PostSerializer(posts, many=True)
            posts_data = serializer.data
            if posts_data:
                success = "success"
                save_to_redis(request_id, success, posts_data)
            else:
                failure = "No posts found"
                save_to_redis(request_id, failure)

        elif action == 'PMPOSTREADTAG':
            tag_name = data.get('tagName')
            if not tag_name:
                save_to_redis(request_id, {
                    "result": "Tag name not provided"
                })
                return
            tag = Tag.objects.filter(name=tag_name).first()
            if not tag:
                save_to_redis(request_id, {
                    "result": "Tag not found"
                })
                return
            posts = tag.posts.all()
            serializer = PostSerializer(posts, many=True)
            posts_data = serializer.data
            save_to_redis(request_id, {
                "result": "success",
                "data": posts_data
            })

        elif action == 'PMPOSTREADCATEGORY':
            category_name = data.get('categoryName')
            if not category_name:
                save_to_redis(request_id, {
                    "result": "Category name not provided"
                })
                return
            category = Category.objects.filter(name=category_name).first()
            if not category:
                save_to_redis(request_id, {
                    "result": "Category not found"
                })
                return
            posts = category.posts.all()
            serializer = PostSerializer(posts, many=True)
            posts_data = serializer.data
            save_to_redis(request_id, {
                "result": "success",
                "data": posts_data
            })

    except Post.DoesNotExist:
        failure = "No posts found"
        save_to_redis(request_id, failure)

    except DatabaseError as db_err:
        logger.error(f"Database Error: {db_err}")
        save_to_redis(request_id, "Database Error")

    except Exception as e:
        logger.error(f"Unhandled Exception: {e}")
        save_to_redis(request_id, "Unhandled Exception")

