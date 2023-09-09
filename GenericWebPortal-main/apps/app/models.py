# -*- encoding: utf-8 -*-
"""
Copyright (c) 2019 - present AppSeed.us
"""
from enum import Enum

from django.db import models
from django.contrib.auth.models import User
from django.utils.translation import gettext_lazy as _


class Technology(models.TextChoices):
    APP = 'APP', _('App')
    FRAMEWORK = 'FRW', _('Framework')


class License(models.TextChoices):
    FREE = 'FR', _('Free')
    COMMERCIAL = 'CO', _('Commercial')
    FREEMIUM = 'FM', _('Freemium')


class Property(models.Model):
    name = models.CharField(max_length=200)
    deletable = models.BooleanField()


class ModelingLanguage(Property):
    pass


class Platform(Property):
    pass


class ProgrammingLanguage(Property):
    pass


class Creator(models.Model):
    name = models.CharField(max_length=200)


# Create your models here.
class ModelingTool(models.Model):
    name = models.CharField(max_length=200)
    link = models.TextField(null=True, blank=True)
    open_source = models.BooleanField(null=True, blank=True)
    technology = models.ManyToManyField(null=True, blank=True, to=Technology)
    web_app = models.BooleanField(null=True, blank=True)
    desktop_app = models.BooleanField(null=True, blank=True)
    category = models.CharField(max_length=3, choices=Technology.choices, default=Technology.APP)
    modeling_languages = models.ManyToManyField(null=True, blank=True, to=ModelingLanguage)
    source_code_generation = models.BooleanField(null=True, blank=True)
    cloud_service = models.BooleanField(null=True, blank=True)
    license = models.CharField(max_length=2, choices=License.choices, default=License.FREE)
    login_required = models.BooleanField(null=True, blank=True)
    creators = models.ManyToManyField(null=True, blank=True, to=Creator)
    platforms = models.ManyToManyField(null=True, blank=True, to=Platform)
    real_time_collab = models.BooleanField(null=True, blank=True)
    programming_languages = models.ManyToManyField(to=ProgrammingLanguage)


class ModelingToolSuggestion(models.Model):
    pass
