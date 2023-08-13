from rest_framework import serializers
from .models import Post, Comment, Tag, Category

class CommentSerializer(serializers.ModelSerializer):
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
    comments = CommentSerializer(many=True, read_only=True)
    pmTag = TagSerializer(many=True, read_only=True)
    pmCategory = serializers.SlugRelatedField(slug_field='name', queryset=Category.objects.all())

    class Meta:
        model = Post
        fields = '__all__'
