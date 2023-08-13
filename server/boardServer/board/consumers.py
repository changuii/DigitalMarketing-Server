from kafka import KafkaConsumer  # KafkaConsumer 모듈을 가져옵니다.
import json  # json 모듈을 가져옵니다.
import logging  # 로깅 모듈을 가져옵니다.
from board.views import handle_message  # views.py 내의 handle_message 함수를 import 합니다.

# 로깅 설정: 정보 레벨(logging.INFO)로 로그를 출력하고, 출력 형식도 설정합니다.
logging.basicConfig(level=logging.INFO, format="%(asctime)s %(levelname)s %(message)s")

class PostKafkaConsumer:  # PostKafkaConsumer 클래스 정의 시작
    def __init__(self):  # 생성자 함수
        # Kafka 관련 로그를 처리하기 위한 로거 설정
        kafka_logger = logging.getLogger('kafka')
        kafka_logger.setLevel(logging.WARNING)

        # 카프카 소비자 인스턴스를 생성합니다.
        self.consumer = KafkaConsumer(
            'PromotionalPostRequest',  # topic 이름
            bootstrap_servers=['172.20.10.14:9092'],  # Kafka broker 주소
            group_id='foo',  # 소비자 그룹 ID
            auto_offset_reset='latest',  # 가장 최신의 메시지부터 시작
            value_deserializer=lambda v: json.loads(v.decode('utf-8')),  # 메시지를 JSON 형식으로 디코딩
            api_version=(3, 5, 1),  # 카프카 API 버전
        )

    def run(self):  # run 함수 정의 시작
        try:
            # 카프카에서 메시지를 가져옵니다. 타임아웃은 1000ms(1초)입니다.
            self.consumer.poll(timeout_ms=1000)  
            logging.info("카프카 서버에 성공적으로 연결되었습니다.")
        except Exception as ex:  # 예외가 발생하면
            logging.error(f"카프카에 연결하는 중 예외 발생: {ex}")

        # 계속해서 카프카로부터 메시지를 수신
        while True:  # 무한 루프 시작
            try:
                for message in self.consumer:  # 카프카에서 메시지를 가져옵니다.
                    data = message.value  # 메시지의 값을 data 변수에 할당합니다.
                    print(f"수신한 메시지: {data}")
                    handle_message(data)  # 메시지를 views.py의 handle_message 함수에 넘겨줍니다.
            except KeyboardInterrupt:  # 키보드 인터럽트 (예: Ctrl+C)로 종료하려 할 때
                logging.warning("중단됨")  
                break  # 무한 루프를 빠져나옵니다.
            except Exception as ex:  # 그 외의 예외 발생 시
                logging.error(f"메시지를 소비하는 도중 예외 발생: {ex}")
