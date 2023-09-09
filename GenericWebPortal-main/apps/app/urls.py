# -*- encoding: utf-8 -*-
"""
Copyright (c) 2019 - present AppSeed.us
"""

from django.urls import path, re_path
from apps.app import views
from apps.app import kb_literals as kb


urlpatterns = [

    # The home page
    path('', views.index, name='home'),
    path(kb.DB_KEY, views.index, name='home'),
    path(kb.DB_KEY + '/search', views.search_results, name='search_results'),
    path(kb.DB_KEY + '/analysis', views.comprehensive_analysis, name='comprehensive_analysis'),
    path('modelingtools', views.modeling_tools, name='modeling_tools')
    # Matches any html file
    # re_path(r'^.*\.*', views.pages, name='pages'),
]
