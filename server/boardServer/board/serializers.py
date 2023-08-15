from rest_framework import serializers
from .models import Post, Comment, Tag, Category


class CommentSerializer(serializers.ModelSerializer):
    # `post`는 댓글 생성 시 전달되지 않지만, 내부적으로 관리되어야 합니다.
    post = serializers.PrimaryKeyRelatedField(queryset=Post.objects.all(), write_only=True)

    class Meta:
        model = Comment
        fields = '__all__'

class TagSerializer(serializers.ModelSerializer):
    posts = serializers.SerializerMethodField()  # 해당 태그에 연결된 게시물들

    class Meta:
        model = Tag
        fields = '__all__'

    def get_posts(self, obj):
        # Tag 객체에서 연결된 게시글들을 가져오기 위한 로직
        posts = obj.tags.all()  # Changed from post_set to tags because of the related_name
        return PostSerializer(posts, many=True).data

class CategorySerializer(serializers.ModelSerializer):
    posts = serializers.SerializerMethodField()  # 해당 카테고리에 연결된 게시물들

    class Meta:
        model = Category
        fields = '__all__'

    def get_posts(self, obj):
        # Category 객체에서 연결된 게시글들을 가져오기 위한 로직
        posts = obj.categorys.all()  # changed from post_set to categorys because of the related_name
        return PostSerializer(posts, many=True).data

class PostSerializer(serializers.ModelSerializer):
    pmPostNumber = serializers.IntegerField(source='id', read_only=True)  # 추가
    comments = CommentSerializer(many=True, read_only=True)
    pmTag = serializers.PrimaryKeyRelatedField(many=True, queryset=Tag.objects.all()) # 변경
    pmCategory = serializers.PrimaryKeyRelatedField(queryset=Category.objects.all()) # 변경

    class Meta:
        model = Post
        fields = '__all__'

