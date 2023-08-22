from django.core.management.base import BaseCommand
from board.consumers import PostKafkaConsumer
import logging

class Command(BaseCommand):
    help = "Runs the Kafka Consumer for Posts."

    def handle(self, *args, **options):
        try:
            post_consumer = PostKafkaConsumer()
            post_consumer.run()
        except Exception as e:
            logging.error(f'An error occurred while consuming messages: {e}')
