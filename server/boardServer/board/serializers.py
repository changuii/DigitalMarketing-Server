from rest_framework import serializers
from .models import Post, Comment, Tag, Category

class CommentSerializer(serializers.ModelSerializer):
    post = serializers.PrimaryKeyRelatedField(queryset=Post.objects.all(), write_only=True)

    class Meta:
        model = Comment
        fields = '__all__'

class TagSerializer(serializers.ModelSerializer):
    posts = serializers.SerializerMethodField()

    class Meta:
        model = Tag
        fields = '__all__'

    def get_posts(self, obj):
        posts = obj.tags.all()
        return PostReadSerializer(posts, many=True).data

class CategorySerializer(serializers.ModelSerializer):
    posts = serializers.SerializerMethodField()

    class Meta:
        model = Category
        fields = '__all__'

    def get_posts(self, obj):
        posts = obj.categorys.all()
        return PostReadSerializer(posts, many=True).data

class PostReadSerializer(serializers.ModelSerializer):
    pmPostNumber = serializers.IntegerField(source='id', read_only=True)
    comments = CommentSerializer(many=True, read_only=True)
    pmTag = serializers.StringRelatedField(many=True, read_only=True)
    pmCategory = serializers.StringRelatedField(read_only=True)

    class Meta:
        model = Post
        fields = '__all__'

class PostWriteSerializer(serializers.ModelSerializer):
    pmPostNumber = serializers.IntegerField(source='id', read_only=True)  # 추가
    comments = CommentSerializer(many=True, read_only=True)
    pmTag = serializers.PrimaryKeyRelatedField(many=True, queryset=Tag.objects.all()) # 변경
    pmCategory = serializers.PrimaryKeyRelatedField(queryset=Category.objects.all()) # 변경

    class Meta:
        model = Post
        fields = '__all__'
