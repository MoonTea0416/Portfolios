# Generated by Django 3.2.6 on 2021-11-20 21:29

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('crayonApp', '0009_alter_room_room_id'),
    ]

    operations = [
        migrations.AddField(
            model_name='room',
            name='name',
            field=models.CharField(default=' ', max_length=256),
        ),
        migrations.AlterField(
            model_name='quizsubtype',
            name='name',
            field=models.CharField(max_length=256),
        ),
        migrations.AlterField(
            model_name='room',
            name='room_id',
            field=models.CharField(default='90561e73', editable=False, max_length=256, primary_key=True, serialize=False, unique=True),
        ),
    ]
