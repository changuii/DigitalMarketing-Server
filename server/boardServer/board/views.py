import json
import redis
from .serializers import CommentSerializer, PostReadSerializer, PostWriteSerializer
from .models import Category, Post, Tag
import logging
from django.db import DatabaseError

logger = logging.getLogger(__name__)
redis_conn = redis.StrictRedis(host='rhljh201.codns.com', port=6379, db=0, decode_responses=True)

def convert_to_java_format(data):
    """Django 모델 또는 직렬화된 데이터를 Jackson 라이브러리와 호환되는 형식으로 변환합니다."""
    # 기본 구조
    java_format = {
        "@class": "org.json.simple.JSONObject"
    }

    # 데이터가 딕셔너리(직렬화된 데이터와 같은)인 경우, 우리의 구조에 그것을 통합합니다.
    if isinstance(data, dict):
        for key, value in data.items():
            if key == "pmTag":  # pmTag에 대한 특별한 처리
                if not value or value == [{}]:  # pmTag의 값이 비어있거나 빈 딕셔너리의 리스트인 경우
                    java_format[key] = ["java.util.ArrayList", value]
                else:  # 그 외의 경우, 일반적인 리스트 변환을 합니다.
                    java_format[key] = ["java.util.ArrayList", [convert_to_java_format(item) if isinstance(item, dict) else item for item in value]]
                continue

            if key == "pmPostPictures":  # pmTag에 대한 특별한 처리
                if not value or value == [{}]:  # pmTag의 값이 비어있거나 빈 딕셔너리의 리스트인 경우
                    java_format[key] = ["java.util.ArrayList", value]
                else:  # 그 외의 경우, 일반적인 리스트 변환을 합니다.
                    java_format[key] = ["java.util.ArrayList", [convert_to_java_format(item) if isinstance(item, dict) else item for item in value]]
                continue

            if isinstance(value, list):  # 값이 리스트인 경우
                java_format[key] = ["java.util.ArrayList", [convert_to_java_format(item) if isinstance(item, dict) else item for item in value]]
            elif isinstance(value, dict):  # 값이 또 다른 딕셔너리인 경우, 재귀적으로 변환
                java_format[key] = convert_to_java_format(value)
            else:
                java_format[key] = value

    return java_format


# save_to_redis 함수는 변경되지 않아 그대로 사용됩니다.


def save_to_redis(request_id, result, data=None):
    try:
        response_id = "PromotionalPostResponse"
        
        if data:
            # 데이터가 리스트인 경우, 각 아이템을 convert_to_java_format으로 변환합니다.
            if isinstance(data, list):
                data = ["java.util.ArrayList", [convert_to_java_format(item) for item in data]]
            else:
                data = convert_to_java_format(data)          
            response_data = {
                "@class": "org.json.simple.JSONObject",
                "result": result,
                "data": data
            }
        else:
            response_data = {
                "@class": "org.json.simple.JSONObject",
                "result": result,
            }

        value = json.dumps(response_data, ensure_ascii=False)  # 한글을 유니코드로 변환하지 않도록 함
        redis_conn.hset(response_id, request_id, value)
        logger.info(f"Saved data to Redis with key {request_id}: {value}")
    except Exception as e:
        logger.error(f"Failed to save to Redis: {e}")


def create_post(data):
    tags_data = data.get("pmTag", [])
    category_data = data.get("pmCategory", None)

    if isinstance(tags_data, str):
        tags_data = [tags_data]

    # Create or fetch the tags and replace the tag names with their respective IDs.
    tag_ids = []
    for tag_name in tags_data:
        tag, _ = Tag.objects.get_or_create(name=tag_name)
        tag_ids.append(tag.id)
    data["pmTag"] = tag_ids

    # For the category, replace the category name with its ID.
    if category_data:
        category, _ = Category.objects.get_or_create(name=category_data)
        data["pmCategory"] = category.id

    # Convert hit count and like count to integers.
    data["pmPostHitCount"] = int(data["pmPostHitCount"])
    data["pmPostLike"] = int(data["pmPostLike"])

    serializer = PostWriteSerializer(data=data)
    if serializer.is_valid():
        post_instance = serializer.save()
        logger.info(f"Post created: {vars(post_instance)}")
        return "success"
    else:
        logger.info(f"Post Failed: {serializer.errors}")
        return "Failed to create post"



def update_post(data):
    pmPostNumber = data.pop("pmPostNumber", None)
    if not pmPostNumber:
        return "pmPostNumber not provided"

    post_id = int(pmPostNumber)  # pmPostNumber를 정수로 변환

    try:
        post = Post.objects.get(id=post_id)
    except Post.DoesNotExist:
        return "Post does not exist"

    tags_data = data.get("pmTag", [])
    category_data = data.get("pmCategory", None)

    # If category_data is present, update it
    if category_data:
        category, _ = Category.objects.get_or_create(name=category_data)
        data["pmCategory"] = category.id

    # Use `partial=True` to allow partial updates
    serializer = PostWriteSerializer(post, data=data, partial=True)
    if serializer.is_valid():
        post_instance = serializer.save()

        # If tags_data is present, update it
        if tags_data:
            post_instance.pmTag.clear()
            for tag_name in tags_data:
                tag, _ = Tag.objects.get_or_create(name=tag_name)
                post_instance.pmTag.add(tag.id)
        return "success"
    else:
        return "Failed to update post"



def delete_post(data):
    pmPostNumber = data.pop("pmPostNumber")
    try:
        post = Post.objects.get(id=pmPostNumber)
        post.delete()
        return "success"
    except Post.DoesNotExist:
        logger.error(f"Post with id {pmPostNumber} does not exist.")
        return "Failed to delete post"


def create_comment(data):
    post_id = data.get('pmPostNumber')
    try:
        post_instance = Post.objects.get(id=post_id)
    except Post.DoesNotExist:
        return "Post not found"

    # `post` 정보를 data에 추가합니다.
    data['post'] = post_instance.id
    data["commentLike"] = int(data["commentLike"])
    serializer = CommentSerializer(data=data, partial=True)
    if serializer.is_valid():
        comment_instance = serializer.save()
        logger.info(f"Comment created: {vars(comment_instance)}")
        return "success"
    else:
        logger.error(f"Comment creation failed: {serializer.errors}")
        return "Failed to create comment"

def read_post_with_comments(data):
    pmPostNumber = data.pop("pmPostNumber")
    post_id = int(pmPostNumber)  # pmPostNumber를 정수로 변환
    try:
        post = Post.objects.get(id=post_id)
    except Post.DoesNotExist:
        return "Post not found"
    # 포스트와 해당 포스트의 댓글들을 직렬화
    serializer = PostReadSerializer(post)
    return serializer.data


def read_all_posts():
    posts = Post.objects.all()
    print(posts)
    serializer = PostReadSerializer(posts, many=True)
    serialized_data = serializer.data
    logger.info(f"Read all posts: {serialized_data}")
    return serializer.data


def read_posts_by_tags(tag_names):
    posts = Post.objects.filter(pmTag__name__in=tag_names)
    serializer = PostReadSerializer(posts, many=True)
    return serializer.data



def read_posts_by_category(category_name):
    category = Category.objects.filter(name=category_name).first()
    if not category:
        return []
    posts = category.categorys.all()
    serializer = PostReadSerializer(posts, many=True)
    return serializer.data


def handle_message(data):
    action = data.pop("action", "")
    request_id = data.pop("requestId")

    try:
        if action == 'pmPostCreate':
            result = create_post(data)
            save_to_redis(request_id, result)

        elif action == 'pmPostUpdate':
            result= update_post(data)
            save_to_redis(request_id, result)

        elif action == 'pmPostDelete':
            result = delete_post(data)
            save_to_redis(request_id, result)

        elif action == 'pmCommentCreate':
            result = create_comment(data)
            save_to_redis(request_id, result)

        elif action == 'pmPostReadAll':
            posts_data = read_all_posts()
            if posts_data:
                save_to_redis(request_id, "success", posts_data)
            else:
                save_to_redis(request_id, "No posts found")

        elif action == 'pmPostReadTag':
            tag_names = data.get('pmTag', [])
            if not tag_names:
                save_to_redis(request_id, "Tag names not provided")
                return
            if isinstance(tag_names, str):
                tag_names = [tag_names]
            posts_data = read_posts_by_tags(tag_names)
            if not posts_data:
                save_to_redis(request_id, "No posts with provided tags found")
                return
            print(posts_data)
            save_to_redis(request_id, "success", posts_data)


        elif action == 'pmPostReadCategory':
            category_name = data.get('pmCategory')
            if not category_name:
                save_to_redis(request_id, "Category name not provided")
                return
            posts_data = read_posts_by_category(category_name)
            if not posts_data:
                save_to_redis(request_id, "Category not found")
                return
            save_to_redis(request_id, "success", posts_data)
        
        elif action == 'pmPostRead':
            posts_data= read_post_with_comments(data)
            if posts_data:
                save_to_redis(request_id, "success", posts_data)
            else:
                save_to_redis(request_id, "Post not found")

    except DatabaseError as db_err:
        logger.error(f"Database Error: {db_err}")
        save_to_redis(request_id, "Database Error")

    except Exception as e:
        logger.error(f"Unhandled Exception: {e}")
        save_to_redis(request_id, "Unhandled Exception")