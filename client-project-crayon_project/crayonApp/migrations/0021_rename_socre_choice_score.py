# Generated by Django 3.2.6 on 2021-12-02 09:40

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('crayonApp', '0020_rename_choice_text_quiz_quiz_context'),
    ]

    operations = [
        migrations.RenameField(
            model_name='choice',
            old_name='socre',
            new_name='score',
        ),
    ]