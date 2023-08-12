from __future__ import absolute_import, unicode_literals
import os
from celery import Celery

# Django 프로젝트의 설정을 'settings' 모듈로 지정합니다.
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'config.settings')

app = Celery('config')

# Celery가 Django의 설정 파일에서 구성을 로드하도록 지정합니다.
app.config_from_object('django.conf:settings', namespace='CELERY')

# 등록된 Django 앱 구성에서 task를 불러옵니다.
app.autodiscover_tasks()

@app.task(bind=True)
def debug_task(self):
    print('Request: {0!r}'.format(self.request))
