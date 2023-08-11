from kafka import KafkaConsumer    
import json
import logging
from board.views import handle_message  # views.py 내의 handle_message 함수를 import 합니다.

logging.basicConfig(level=logging.INFO, format="%(asctime)s %(levelname)s %(message)s")

class PostKafkaConsumer:
    def __init__(self):
        kafka_logger = logging.getLogger('kafka')
        kafka_logger.setLevel(logging.WARNING)

        self.consumer = KafkaConsumer(
            'test2',
            bootstrap_servers=['192.168.0.3:9092'],
            group_id='foo',
            auto_offset_reset='latest',
            value_deserializer=lambda v: json.loads(v.decode('utf-8')),
            api_version=(3, 5, 1),
        )

    def run(self):
        try:
            self.consumer.poll(timeout_ms=1000)  
            logging.info("카프카 서버에 성공적으로 연결되었습니다.")
        except Exception as ex:
            logging.error(f"카프카에 연결하는 중 예외 발생: {ex}")

        while True:
            try:
                for message in self.consumer:
                    data = message.value
                    print(f"수신한 메시지: {data}")
                    handle_message(data)  # 메시지를 views.py의 handle_message 함수에 넘겨줍니다.
            except KeyboardInterrupt:  
                logging.warning("중단됨")  
                break  
            except Exception as ex:  
                logging.error(f"메시지를 소비하는 도중 예외 발생: {ex}")
