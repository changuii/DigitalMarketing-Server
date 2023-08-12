from django.db import models

class Post(models.Model):
    pmCategory = models.CharField(max_length=200, null=True, blank=True)
    pmPostTitle = models.CharField(max_length=200, null=True, blank=True)
    pmPostWriter = models.CharField(max_length=200, null=True, blank=True)
    pmPostDate = models.DateTimeField(auto_now_add=True)  # 날짜는 자동으로 현재 날짜와 시간을 할당합니다.
    pmPostContents = models.TextField(null=True, blank=True)
    pmPostPicture = models.ImageField(upload_to='posts/images/', blank=True)  # 이미지는 posts/images/ 디렉토리에 저장됩니다.
    pmPostHitCount = models.IntegerField(default=0)  # 조회수는 기본적으로 0으로 설정합니다.
    pmPostLike = models.IntegerField(default=0)  # 좋아요 수는 기본적으로 0으로 설정합니다.
    pmComment = models.TextField(null=True, blank=True)  # 댓글 내용을 저장합니다.
    storeLocation = models.CharField(max_length=300, blank=True)
