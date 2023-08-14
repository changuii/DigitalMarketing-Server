from django.db import models

# 카테고리 모델
class Category(models.Model):
    name = models.CharField(max_length=200, unique=True)

    def __str__(self):
        return self.name

# 태그 모델
class Tag(models.Model):
    name = models.CharField(max_length=200, unique=True)

    def __str__(self):
        return self.name

class Post(models.Model):
    pmTag = models.ManyToManyField(Tag, blank=True, related_name='tags')  # 태그 필드 추가
    pmCategory = models.ForeignKey(Category, on_delete=models.SET_NULL, null=True, related_name='categorys')  # 카테고리 필드 추가
    pmPostTitle = models.CharField(max_length=200, blank=True)
    pmPostWriter = models.CharField(max_length=200, blank=True)
    pmPostDate = models.DateTimeField(auto_now_add=True,null=True)
    pmPostContents = models.TextField(blank=True,null=True)
    pmPostPicture =  models.CharField(max_length=200, blank=True)
    pmPostHitCount = models.IntegerField(default=0)
    pmPostLike = models.IntegerField(default=0)
    storeLocation = models.CharField(max_length=300, blank=True,null=True)

    def __str__(self):
        return self.pmPostTitle

# 댓글 모델
class Comment(models.Model):
    post = models.ForeignKey(Post, on_delete=models.CASCADE, related_name='comments')
    username = models.CharField(max_length=200)  # Kafka에서 전달받은 사용자 이름
    content = models.TextField(blank=True,null=True)
    commentLike=models.IntegerField(default=0)
    created_at = models.DateTimeField(auto_now_add=True,null=True)
    updated_at = models.DateTimeField(auto_now=True,null=True)

    def __str__(self):
        return self.content
