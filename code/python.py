# Snippets from a fix Lazy Unbinding bug (Mass Assignment)
from django.db import models

class Comment(models.Model):
    content = models.CharField(max_length=200)
    approved = models.BooleanField(default=False)

    def __str__(self):
        return self.content

    def is_approved(self):
        return self.approved

    def save(self, *args, **kwargs):
        if self.approved and self._state.adding:
            self.approved = False
        super(Comment, self).save(*args, **kwargs)

# Vulnerable part
#def index(request):
#    if request.method == 'POST':
#        form = CommentForm(request.POST)
#        if form.is_valid():
#            form.save()
#            return HttpResponse("Your comment is saved for review")
#    approved_comments = Comment.objects.filter(approved=True)
#    template = loader.get_template('program/index.html')
#    context = { 'approved_comments': approved_comments }
#    return HttpResponse(template.render(context,request))
