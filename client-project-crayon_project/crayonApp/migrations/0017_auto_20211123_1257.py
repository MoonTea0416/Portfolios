# Generated by Django 3.2.6 on 2021-11-23 19:57

import crayonApp.models
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('crayonApp', '0016_auto_20211121_0503'),
    ]

    operations = [
        migrations.CreateModel(
            name='Choice',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('socre', models.IntegerField(default=0)),
                ('quiz', models.ForeignKey(default=None, null=True, on_delete=django.db.models.deletion.CASCADE, to='crayonApp.quiz')),
            ],
        ),
        migrations.DeleteModel(
            name='Response',
        ),
        migrations.AlterField(
            model_name='room',
            name='room_id',
            field=models.CharField(default=crayonApp.models._default_room_id, editable=False, max_length=256, primary_key=True, serialize=False, unique=True),
        ),
    ]