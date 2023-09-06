# -*- encoding: utf-8 -*-
"""
Copyright (c) 2019 - present AppSeed.us
"""

from django.urls import path
from .views import login_view, register_user
from django.contrib.auth.views import LogoutView

from apps.app.kb_literals import DB_KEY

login_url = DB_KEY + '/login/'
register_url = DB_KEY + '/register/'
logout_url = DB_KEY + '/logout/'

urlpatterns = [
    path(login_url, login_view, name="login"),
    path(register_url, register_user, name="register"),
    path(logout_url, LogoutView.as_view(), name="logout")
]
