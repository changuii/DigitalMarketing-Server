import json  # json 모듈을 임포트합니다.
import redis  # redis 모듈을 임포트합니다.
from .serializers import PostSerializer  # 현재 패키지의 serializers에서 PostSerializer를 임포트합니다.
from .models import Post  # 현재 패키지의 models에서 Post 모델을 임포트합니다.
import logging  # 로깅 모듈을 임포트합니다.
from celery import shared_task

# 현재 모듈의 로깅 객체를 가져옵니다.
logger = logging.getLogger(__name__)

# Redis 서버와 연결합니다.
redis_conn = redis.StrictRedis(host='localhost', port=6379, db=0)

def save_to_redis(key, value):  # save_to_redis 함수를 정의합니다.
    # 주어진 key와 value를 Redis에 저장합니다.
    redis_conn.set(key, json.dumps(value))
    # 로그에 정보를 출력합니다.
    logger.info(f"Saved data to Redis with key {key}")

# handle_message 함수를 정의합니다.
@shared_task
def handle_message(data):
    # data에서 "action" 키의 값을 대문자로 변환하여 가져옵니다. 기본값은 빈 문자열입니다.
    action = data.get("action", "").upper()
    # data에서 "requestId" 키의 값을 가져옵니다.
    request_id = data.get("requestId")

    # action이 'PMPOSTCREATE'일 경우의 로직
    if action == 'PMPOSTCREATE':
        # 데이터를 PostSerializer에 전달합니다.
        serializer = PostSerializer(data=data)
        # 데이터가 유효한지 확인합니다.
        if serializer.is_valid():
            # 유효하면 저장합니다.
            serializer.save()
            # Redis에 성공 메시지를 저장합니다.
            save_to_redis(request_id, {"status": "success"})
        else:
            # 유효하지 않으면 Redis에 실패 메시지와 에러 메시지를 저장합니다.
            save_to_redis(request_id, {"status": "failure", "message": serializer.errors})

    # action이 'PMPOSTUPDATE'일 경우의 로직
    elif action == 'PMPOSTUPDATE':
        try:
            # data에서 'pmPostNumber' 키의 값을 사용하여 Post 객체를 가져옵니다.
            post = Post.objects.get(id=data['pmPostNumber'])
            # Post 객체와 데이터를 PostSerializer에 전달합니다.
            serializer = PostSerializer(post, data=data)
            # 데이터가 유효한지 확인합니다.
            if serializer.is_valid():
                # 유효하면 저장합니다.
                serializer.save()
                # Redis에 성공 메시지를 저장합니다.
                save_to_redis(request_id, {"status": "success"})
        except Post.DoesNotExist:
            # Post 객체가 존재하지 않는 경우, Redis에 실패 메시지를 저장합니다.
            save_to_redis(request_id, {"status": "failure", "message": "Post Not Found"})

    # action이 'PMPOSTDELETE'일 경우의 로직
    elif action == 'PMPOSTDELETE':
        try:
            # data에서 'pmPostNumber' 키의 값을 사용하여 Post 객체를 가져옵니다.
            post = Post.objects.get(id=data['pmPostNumber'])
            # Post 객체를 삭제합니다.
            post.delete()
            # Redis에 성공 메시지를 저장합니다.
            save_to_redis(request_id, {"status": "success"})
        except Post.DoesNotExist:
            # Post 객체가 존재하지 않는 경우, Redis에 실패 메시지를 저장합니다.
            save_to_redis(request_id, {"status": "failure", "message": "Post Not Found"})

    # action이 'PMPOSTREADALL'일 경우의 로직
    elif action == 'PMPOSTREADALL':
        # 모든 Post 객체를 가져옵니다.
        posts = Post.objects.all()
        # 모든 Post 객체를 PostSerializer에 전달합니다.
        serializer = PostSerializer(posts, many=True)
        # 직렬화된 데이터를 가져옵니다.
        posts_data = serializer.data

        # 직렬화된 데이터가 있으면 Redis에 성공 메시지와 함께 저장합니다.
        if posts_data:
            save_to_redis(request_id, {
                "status": "success",
                "data": posts_data
            })
        else:
            # 데이터가 없으면 Redis에 실패 메시지를 저장합니다.
            save_to_redis(request_id, {
                "status": "failure",
                "message": "No posts found"
            })
