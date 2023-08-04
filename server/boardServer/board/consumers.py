from kafka import KafkaConsumer
import json
import logging
from .serializers import PostSerializer
from .models import Post

# 로그 설정 추가
logging.basicConfig(level=logging.INFO, format="%(asctime)s %(levelname)s %(message)s")

class PostKafkaConsumer:
    def __init__(self):
        # 카프카 관련 로그 레벨 설정 추가
        kafka_logger = logging.getLogger('kafka')
        kafka_logger.setLevel(logging.WARNING)

        self.consumer = KafkaConsumer(
            'test2',  # 토픽 이름을 입력합니다.
            bootstrap_servers=['192.168.0.3:9092'],  # Kafka 서버의 위치를 입력합니다.
            group_id='foo', # 그룹 아이디를 입력합니다.
            auto_offset_reset='latest', # 가장 최근에 생성된 메시지부터 가져옵니다.
            value_deserializer=lambda v: json.loads(v.decode('utf-8')),  # JSON 형식의 메시지를 파싱합니다.
            api_version=(3, 5, 1),
        )

    def run(self):
        # Kafka 서버에 연결할 수 있는지 확인
        try:
            self.consumer.poll(timeout_ms=1000)
            logging.info("Connected to Kafka server successfully")  # 로그 추가
        except Exception as ex:
            logging.error(f"Exception while connecting Kafka: {ex}")

        # 메시지 처리 루프
        while True:
            try:
                for message in self.consumer:
                    data = message.value
                    
                    # 콘솔에 메시지 데이타 출력
                    print(f"Received message: {data}")

                    post_id = data.get('id', None)
                    if data['action'] == 'create':
                        serializer = PostSerializer(data=data)
                        if serializer.is_valid():
                            serializer.save()
                    elif data['action'] == 'update' and post_id:
                        try:
                            post = Post.objects.get(id=post_id)
                            serializer = PostSerializer(post, data=data)
                            if serializer.is_valid():
                                serializer.save()
                        except Post.DoesNotExist:
                            pass
                    elif data['action'] == 'delete' and post_id:
                        try:
                            post = Post.objects.get(id=post_id)
                            post.delete()
                        except Post.DoesNotExist:
                            pass
            except KeyboardInterrupt:
                logging.warning("Interrupted")
                break
            except Exception as ex:
                logging.error(f"Exception while consuming message: {ex}")
