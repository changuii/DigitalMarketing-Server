from kafka import KafkaProducer
import json

class SingletonMeta(type):
    _instances = {}
    
    def __call__(cls, *args, **kwargs):
        if cls not in cls._instances:
            instance = super().__call__(*args, **kwargs)
            cls._instances[cls] = instance
        return cls._instances[cls]

class PostKafkaProducer(metaclass=SingletonMeta):
    def __init__(self):
        self.producer = KafkaProducer(
            bootstrap_servers=['192.168.0.3:9092'],  # Kafka 서버의 위치를 입력합니다.
            value_serializer=lambda v: json.dumps(v).encode('utf-8'),  # JSON 형식의 메시지를 직렬화합니다.
            api_version=(3, 5, 1),
        )

    def send_post_save(self, sender, instance, created, **kwargs):
        if created:  # 생성된 경우
            action = 'create'
        else:  # 수정된 경우
            action = 'update'
        message = {'action': action, 'id': instance.id, 'title': instance.title, 'content': instance.content}
        self.producer.send('test4', message)

    def send_post_delete(self, sender, instance, **kwargs):
        message = {'action': 'delete', 'id': instance.id}
        self.producer.send('test4', message)
