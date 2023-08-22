from django import forms
from django.contrib import admin
from .models import Category, Tag, Post, Comment

class PostAdminForm(forms.ModelForm):
    pmCategory = forms.ModelChoiceField(queryset=Category.objects.all(), required=False, widget=forms.Select)
    pmTag = forms.ModelMultipleChoiceField(
        queryset=Tag.objects.all(),
        widget=forms.CheckboxSelectMultiple,
        required=False
    )

    class Meta:
        model = Post
        fields = '__all__'


class CommentInline(admin.TabularInline):  # 또는 admin.StackedInline 사용도 가능
    model = Comment
    extra = 1


class CategoryAdmin(admin.ModelAdmin):
    list_display = ['name', ]
    search_fields = ['name']

admin.site.register(Category, CategoryAdmin)


class TagAdmin(admin.ModelAdmin):
    list_display = ['name']
    search_fields = ['name']

admin.site.register(Tag, TagAdmin)


class PostAdmin(admin.ModelAdmin):
    form = PostAdminForm
    list_display = [
        'pmPostTitle', 'get_category', 'get_tags', 'pmPostWriter', 'pmPostContents', 'pmMainImage',
        'pmPostPictures', 'pmPostLike', 
        'get_comment_count','pmPostDate'
    ]
    list_filter = ['pmPostDate', 'pmPostWriter', 'pmTag', 'pmCategory']
    search_fields = ['pmPostTitle', 'pmPostWriter']
    date_hierarchy = 'pmPostDate'
    raw_id_fields = []
    inlines = [CommentInline]

    def get_tags(self, obj):
        return ", ".join([tag.name for tag in obj.pmTag.all()])
    get_tags.short_description = 'Tag'

    def get_category(self, obj):
        return obj.pmCategory.name if obj.pmCategory else "None"
    get_category.short_description = 'Category'

    def get_comment_count(self, obj):
        return obj.comments.count()  # 'comments'는 Comment 모델에서 Post에 설정된 related_name 입니다.
    get_comment_count.short_description = 'Comment Count'



admin.site.register(Post, PostAdmin)


class CommentAdmin(admin.ModelAdmin):
    list_display = ['post', 'username', 'content', 'created_at', 'updated_at', 'commentLike']
    list_filter = ['created_at', 'updated_at']
    search_fields = ['username', 'content']
    date_hierarchy = 'created_at'

admin.site.register(Comment, CommentAdmin)
