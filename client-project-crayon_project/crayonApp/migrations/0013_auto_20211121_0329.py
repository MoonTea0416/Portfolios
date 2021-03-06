# Generated by Django 3.2.6 on 2021-11-21 10:29

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('crayonApp', '0012_auto_20211121_0328'),
    ]

    operations = [
        migrations.AddField(
            model_name='file_attr',
            name='file_id',
            field=models.ForeignKey(default=None, null=True, on_delete=django.db.models.deletion.CASCADE, to='crayonApp.file'),
        ),
        migrations.AlterField(
            model_name='file',
            name='f_id',
            field=models.CharField(default='4221c9d5', editable=False, max_length=256, primary_key=True, serialize=False, unique=True),
        ),
        migrations.AlterField(
            model_name='room',
            name='room_id',
            field=models.CharField(default='87a07707', editable=False, max_length=256, primary_key=True, serialize=False, unique=True),
        ),
    ]
