import json
import redis
from .serializers import PostSerializer
from .models import Post
import logging

logger = logging.getLogger(__name__)

# Redis 연결 초기화
redis_conn = redis.StrictRedis(host='localhost', port=6379, db=0)

def save_to_redis(key, value):
    redis_conn.set(key, json.dumps(value))
    logger.info(f"Saved data to Redis with key {key}")

def handle_message(data):
    action = data.get("action", "").upper()  # 액션 값을 대문자로 가져옴
    request_id = data.get("requestId")

    if action == 'PMPOSTCREATE':
        serializer = PostSerializer(data=data)
        if serializer.is_valid():
            serializer.save()
            save_to_redis(request_id, {"status": "success"})
        else:
            save_to_redis(request_id, {"status": "failure", "message": serializer.errors})

    elif action == 'PMPOSTUPDATE':
        try:
            post = Post.objects.get(id=data['pmPostNumber'])
            serializer = PostSerializer(post, data=data)
            if serializer.is_valid():
                serializer.save()
                save_to_redis(request_id, {"status": "success"})
        except Post.DoesNotExist:
            save_to_redis(request_id, {"status": "failure", "message": "Post Not Found"})

    elif action == 'PMPOSTDELETE':
        try:
            post = Post.objects.get(id=data['pmPostNumber'])
            post.delete()
            save_to_redis(request_id, {"status": "success"})
        except Post.DoesNotExist:
            save_to_redis(request_id, {"status": "failure", "message": "Post Not Found"})

    elif action == 'PMPOSTREADALL':
        posts = Post.objects.all()
        serializer = PostSerializer(posts, many=True)
        posts_data = serializer.data
    
        if posts_data:  # 게시물 데이터가 있을 때
            save_to_redis(request_id, {
                "status": "success",
                "data": posts_data
            })
        else:  # 게시물 데이터가 없을 때 (예: DB에 게시물이 없는 경우)
            save_to_redis(request_id, {
                "status": "failure",
                "message": "No posts found"
            })


