from django.contrib import admin

# -*- encoding: utf-8 -*-
"""
Copyright (c) 2019 - present AppSeed.us
"""

from django.urls import path, re_path
from apps.app import views
from apps.app import kb_literals as kb


urlpatterns = [

    # The home page
    # path('', views.index, name='home'),
    path(kb.DB_KEY, views.index, name='home'),
    path(kb.DB_KEY + '/search', views.search_results, name='search_results'),
    path(kb.DB_KEY + '/analysis', views.comprehensive_analysis, name='comprehensive_analysis'),
    path('', views.modeling_tools, name='modeling_tools'),
    path('modelingtoolshome', views.modeling_tools_home, name='modeling_tools_home'),
    path('create-modeling-tool', views.create_modeling_tool, name='create_modeling_tools'),
    path('create-modeling-tool/add', views.post_modeling_tool, name='create_modeling_tools_add'),
    path('edit-modeling-tool', views.edit_modeling_tool_no_pk, name='edit_modeling_tools_no_pk'),
    path('edit-modeling-tool/<str:pk>', views.edit_modeling_tool, name='edit_modeling_tools'),
    # Matches any html file
    # re_path(r'^.*\.*', views.pages, name='pages'),
    path('admin/', admin.site.urls)

]
