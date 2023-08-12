from django.contrib import admin
from .models import Category, Tag, Post, Comment

# Category Admin
class CategoryAdmin(admin.ModelAdmin):
    list_display = ['name', 'description']
    search_fields = ['name']

admin.site.register(Category, CategoryAdmin)

# Tag Admin
class TagAdmin(admin.ModelAdmin):
    list_display = ['name']
    search_fields = ['name']

admin.site.register(Tag, TagAdmin)

# Post Admin
class PostAdmin(admin.ModelAdmin):
    list_display = ['pmPostTitle', 'pmPostWriter', 'pmPostDate',  'pmPostHitCount', 'pmPostLike', 'get_tags', 'get_category']
    list_filter = ['pmPostDate', 'pmPostWriter', 'pmTags', 'pmCategory']
    search_fields = ['pmPostTitle', 'pmPostWriter']
    date_hierarchy = 'pmPostDate'
    raw_id_fields = ['pmTags', 'pmCategory']

    def get_tags(self, obj):
        return ", ".join([tag.name for tag in obj.pmTags.all()])

    get_tags.short_description = 'Tags'

    def get_category(self, obj):
        return obj.pmCategory.name if obj.pmCategory else "None"

    get_category.short_description = 'Category'

admin.site.register(Post, PostAdmin)


# Comment Admin
class CommentAdmin(admin.ModelAdmin):
    list_display = ['post', 'username', 'content', 'created_at', 'updated_at', 'commentLike']
    list_filter = ['created_at', 'updated_at']
    search_fields = ['username', 'content']
    date_hierarchy = 'created_at'

admin.site.register(Comment, CommentAdmin)
