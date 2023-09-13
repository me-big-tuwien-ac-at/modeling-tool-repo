# -*- encoding: utf-8 -*-
"""
Copyright (c) 2019 - present AppSeed.us
"""
from enum import Enum

from django.db import models
from django.contrib.auth.models import User
from django.utils.translation import gettext_lazy as _


class Category(models.TextChoices):
    GRAPHICAL_MODELING_TOOL = 'GMT', _('Graphical Modeling Tool')
    BUSINESS_TOOL = 'BUT', _('Business Tool')
    DRAWING_TOOL = 'DRT', _('Drawing Tool')
    TEXT_BASED_MODELING_TOOL = 'TMT', _('Text-based Modeling Tool')
    METAMODELING_TOOL = 'MTT', _('Metamodeling Tool')
    MIXED_TEXTUAL_AND_GRAPHICAL_MODELING_TOOL = 'MTG', _('Mixed textual and graphical Modeling Tool')

    class Meta:
        ordering = ['-updated', '-created']  # - orders it descending, default is ascending


class License(models.TextChoices):
    FREE = 'FR', _('Free')
    COMMERCIAL = 'CO', _('Commercial')
    FREEMIUM = 'FM', _('Freemium')

    class Meta:
        ordering = ['-updated', '-created']  # - orders it descending, default is ascending


class Property(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=200)
    deletable = models.BooleanField(default=True)

    class Meta:
        abstract = True


class ModelingLanguage(Property):
    pass


class Platform(Property):
    pass


class ProgrammingLanguage(Property):
    pass


class Technology(Property):
    pass


class Creator(Property):
    pass


# Create your models here.
class ModelingTool(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=200)
    link = models.TextField(null=True, blank=True)
    open_source = models.BooleanField(null=True, blank=True)
    technology = models.ManyToManyField(Technology, blank=True)
    web_app = models.BooleanField(null=True, blank=True)
    desktop_app = models.BooleanField(null=True, blank=True)
    category = models.CharField(max_length=100, choices=Category.choices, default=Category.GRAPHICAL_MODELING_TOOL)  # TODO: Adjust
    modeling_languages = models.ManyToManyField(ModelingLanguage, blank=True)
    source_code_generation = models.BooleanField(null=True, blank=True)
    cloud_service = models.BooleanField(null=True, blank=True)
    license = models.CharField(max_length=100, choices=License.choices, default=License.FREE)  # TODO: Adjust
    login_required = models.BooleanField(null=True, blank=True)
    creators = models.ManyToManyField(Creator, blank=True)
    platforms = models.ManyToManyField(Platform, blank=True)
    real_time_collab = models.BooleanField(null=True, blank=True)
    programming_languages = models.ManyToManyField(ProgrammingLanguage)


class ModelingToolSuggestion(models.Model):
    id = models.AutoField(primary_key=True)
    pass
