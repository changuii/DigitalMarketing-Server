from rest_framework import serializers
from .models import Post

class PostSerializer(serializers.ModelSerializer):
    class Meta:
        model = Post
        fields = [
            'pmCategory', 'pmPostTitle',
            'pmPostWriter', 'pmPostDate', 'pmPostContents', 'pmPostPicture',
            'pmPostHitCount', 'pmPostLike', 'pmComment', 'storeLocation'
        ]
