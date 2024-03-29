# -*- encoding: utf-8 -*-
"""
Copyright (c) 2019 - present AppSeed.us
"""
import re
from ast import literal_eval
from datetime import datetime

from django import template
from django.contrib.auth.decorators import login_required
from django.http import HttpResponse, HttpResponseRedirect, JsonResponse
from django.shortcuts import render
from django.template import loader
from django.urls import reverse
from django.utils.safestring import SafeString

from apps.app.models import ModelingTool, ModelingLanguage, Platform, ProgrammingLanguage, Technology, Category, License
from apps.app.searchapi import *
from apps.app.forms import CompleteForm
from apps.app.request_processing import process_request_parameters, searchFor, \
    get_taxonomies_based_on_request, NAVIGATION_KEY, TAXONOMIES_KEY

from apps.app.data import log_file
from apps.app.ip_info import get_visitor_ip_address
from apps.app import utils

from apps.authentication.urls import login_url

results_template_map = {
    "Publications": "publications_results.html",
    "Venues": "venue_results.html",
    "Authors": "authors_results.html"
}

step = 10
    

#@login_required(login_url="/login/")
def search_results(request):
    context = {}
    results_template = "publications_results.html"
    if request.method == "POST":
        request_input = process_request_parameters(request)
        search_type, search_query = request_input['search_type'], request_input['search_query']
        request_input[kb.TAXONOMIES_LABEL], request_input[kb.NON_TAXONOMIES_LABEL] = get_taxonomies_based_on_request(request, search_query, search_type)
        records, summary, taxonomy_count, non_taxonomy_count = search_records(request_input)
        form = CompleteForm(taxonomy_count, non_taxonomy_count, search_query, searchFor)

        start, end = 1, min(step, len(records))
        first, last = 1, min(step, (len(records)//step)+1)
        current, next_, previous = 1, min(2, len(records)//step), 1
        pages_ = range(1, min(last, first+step+1))

        context = {
            'user': request.user,
            "ui_labels": kb.UI_LITERALS,
            'results': records[:step],
            'start': start,
            'end': min(step, len(records)),
            'first': first,
            'last': last,
            'current': current,
            'previous': previous,
            'next': next_,
            'total_records': len(records),
            'summary': summary,
            'form': form,
            'search_type': search_type,
            'search_query': search_query,
            'pages': pages_
        }
        request.session[NAVIGATION_KEY + search_query.lower()+search_type] = {
            'results': records,
            'summary': summary,
        }
        results_template = results_template_map[search_type]

    if request.method == "GET":
        request_input = process_request_parameters(request)
        search_query, search_type = request_input['search_query'], request_input['search_type']
        page_id = int(request_input['page_id'])
        key = TAXONOMIES_KEY + search_query.lower() + search_type.lower()
        session_output = request.session.get(key)
        form = CompleteForm(session_output[kb.TAXONOMIES_LABEL], session_output[kb.NON_TAXONOMIES_LABEL], search_query, search_type)
        navigation_session_data = request.session[NAVIGATION_KEY + search_query.lower() + search_type]
        records, summary = navigation_session_data['results'], navigation_session_data['summary']

        start, end = ((page_id-1)*step)+1 if page_id else 1, min(page_id*step, len(records)) \
            if page_id else min(step, len(records))
        first, last = 1, min(page_id+step, (len(records)//step)+1)
        current = page_id if page_id else 1
        next_ = min(page_id+1, len(records) // step) if page_id else min(2, len(records)//step)
        previous = page_id-1 if page_id else 1
        pages_ = range(1, min(last, page_id + step if page_id else 11))

        context = {
            'user': request.user,
            "ui_labels": kb.UI_LITERALS,
            'results': records[(page_id-1)*step:page_id*step-1] if page_id else records[:step],
            'start': start,
            'end': end,
            'first': 1,
            'last': min(page_id+step, (len(records)//step)+1),
            'current': current,
            'previous': previous,
            'next': next_,
            'total_records': len(records),
            'summary': summary,
            'form': form,
            'search_type': search_type,
            'search_query': search_query,
            'pages': pages_
        }
        results_template = results_template_map[search_type]
    html_template = loader.get_template(results_template)
    return HttpResponse(html_template.render(context, request))


# @login_required(login_url=login_url)
def index(request):
    visitor_ip = get_visitor_ip_address(request)
    print("Visited by: " + visitor_ip + " at: " + str(datetime.now())+"\n")
    log_file.write("System Visited by: " + visitor_ip + " at: " + str(datetime.now())+"\n")
    # log_ip_location(visitor_ip)
    # if request.user.is_authenticated:
    context = {"search_types": searchFor, "ui_labels": kb.UI_LITERALS, "data_analysis": kb.DATA_ANALYSIS}
    html_template = loader.get_template('home.html')
    return HttpResponse(html_template.render(context, request))


# @login_required(login_url=login_url)
def comprehensive_analysis(request):
    # if request.user.is_authenticated:
    page = request.GET['analysis-type']
    table = utils.get_relevant_table(page)
    context = {"table": table, "ui_labels": kb.UI_LITERALS, "data_analysis": kb.DATA_ANALYSIS}
    html_template = loader.get_template('show_detailed_table.html')
    return HttpResponse(html_template.render(context, request))


# @login_required(login_url=login_url)
def landing(request):
    context = {"ui_labels": kb.UI_LITERALS}
    try:
        if request.user.is_authenticated and request.user.is_superuser:
            html_template = loader.get_template('landing.html')
            return HttpResponse(html_template.render(context, request))
        else:
            html_template = loader.get_template('page-404.html')
            return HttpResponse(html_template.render(context, request))
    except template.TemplateDoesNotExist:
        html_template = loader.get_template('page-404.html')
        return HttpResponse(html_template.render(context, request))
    except:
        html_template = loader.get_template('page-500.html')
        return HttpResponse(html_template.render(context, request))


# @login_required(login_url=login_url)
def pages(request):
    context = {}
    # All resource paths end in .html.
    # Pick out the html file name from the url. And load that template.
    try:
        load_template = request.path.split('/')[-1]

        if load_template == 'admin':
            return HttpResponseRedirect(reverse('admin:index'))
        context['segment'] = load_template

        html_template = loader.get_template(load_template)
        return HttpResponse(html_template.render(context, request))

    except template.TemplateDoesNotExist:

        html_template = loader.get_template('page-404.html')
        return HttpResponse(html_template.render(context, request))
    except:
        html_template = loader.get_template('page-500.html')
        return HttpResponse(html_template.render(context, request))


def modeling_tools(request):
    print(len(ModelingTool.objects.all()))
    context = {
        'modeling_tools': ModelingTool.objects.all(),
        'technology': Technology.objects.all(),
        'modeling_languages': ModelingLanguage.objects.all(),
        'platforms': Platform.objects.all(),
        'programming_languages': ProgrammingLanguage.objects.all()
    }
    return render(request, 'modeling_tool/modeling_tool.html', context)


def modeling_tools_home(request):
    modeling_tools = ModelingTool.objects.all()
    modeling_languages = ModelingLanguage.objects.all()
    platforms = Platform.objects.all()
    programming_languages = ProgrammingLanguage.objects.all()

    context = {
        'modeling_tools': modeling_tools,
        'modeling_languages': modeling_languages,
        'platforms': platforms,
        'programming_languages': programming_languages
    }
    return render(request, 'modeling_tool/modeling_tool.html', context)


def create_modeling_tool(request):
    context = {
        'technologies': SafeString(__get_modeling_tool_names(Technology.objects.all())),
        'technologies_properties': __get_property_names(Technology.objects.all()),
        'categories': [c[1] for c in Category.choices],
        'licenses': [lic[1] for lic in License.choices],
        'modeling_languages': SafeString(__get_property_names(ModelingLanguage.objects.all())),
        'modeling_languages_properties': __get_property_names(ModelingLanguage.objects.all()),
        'modeling_tools': SafeString(__get_modeling_tool_names(ModelingTool.objects.all())),
        'platforms': SafeString(__get_property_names(Platform.objects.all())),
        'platforms_properties': __get_property_names(Platform.objects.all()),
        'programming_languages': SafeString(__get_property_names(ProgrammingLanguage.objects.all())),
        'programming_languages_properties': __get_property_names(ProgrammingLanguage.objects.all())
    }

    tools_converted = __convert_modeling_tool_query_set_to_array(ModelingTool.objects.all())

    return render(request, 'modeling_tool/create_modeling_tool.html', context)


def post_modeling_tool(request):
    body = request.body.decode('utf-8')
    name: str = __get_json_body_key_value(body, "name")
    link: str = __get_json_body_key_value(body, "link")
    open_source: bool = __get_json_body_key_value_bool(body, "openSource")
    technology: [str] = __get_json_body_key_value_arr(body, "technologies")
    web_app: bool = __get_json_body_key_value_bool(body, "webApp")
    desktop_app: bool = __get_json_body_key_value_bool(body, "desktopApp")
    category: str = __get_json_body_key_value(body, "category")
    modeling_languages: [str] = __get_json_body_key_value_arr(body, "modelingLanguages")
    source_code_generation: bool = __get_json_body_key_value_bool(body, "sourceCodeGeneration")
    cloud_service: bool = __get_json_body_key_value_bool(body, "cloudService")
    tool_license: str = __get_json_body_key_value(body, "license")
    log_in: bool = __get_json_body_key_value_bool(body, "loginRequired")
    real_time_collab: bool = __get_json_body_key_value_bool(body, "realTimeCollaboration")
    developers: [str] = __get_json_body_key_value_arr(body, "creators")
    platforms: [str] = __get_json_body_key_value_arr(body, "platforms")
    programming_languages: [str] = __get_json_body_key_value_arr(body, "programmingLanguages")
    remarks: str = __get_json_body_key_value(body, "remarks")


    print(name)
    print(link)
    print(open_source)
    print(technology)
    print(web_app)
    print(desktop_app)
    print(category)
    print(modeling_languages)
    print(source_code_generation)
    print(cloud_service)
    print(tool_license)
    print(log_in)
    print(real_time_collab)
    print(developers)
    print(platforms)
    print(programming_languages)
    print(remarks)

    modeling_tools = ModelingTool.objects.all()
    modeling_languages = ModelingLanguage.objects.all()
    platforms = Platform.objects.all()
    programming_languages = ProgrammingLanguage.objects.all()

    """
    modeling_tool_suggestion = ModelingTool.objects.create(
        name=__get_json_body_key_value(body, "name"),
        link=__get_json_body_key_value(body, "link")
    )
    """

    context = {
        'modeling_tools': modeling_tools,
        'modeling_languages': modeling_languages,
        'platforms': platforms,
        'programming_languages': programming_languages
    }
    return render(request, 'modeling_tool/modeling_tool.html', context)


def edit_modeling_tool_no_pk(request):
    return render(request, 'modeling_tool/edit_modeling_tool.html', {})


def edit_modeling_tool(request, pk):
    return render(request, 'modeling_tool/edit_modeling_tool.html', {})


def __convert_modeling_tool_query_set_to_array(tools_raw: [ModelingTool]) -> [{}]:
    """
    Converts an object of type ModelingTool to a dictionary that can be forwarded to JavaScript files.
    :param tools_raw: object of type ModelingTool
    :return: modeling tool as a dictionary
    """
    tools_converted = []
    for tool in tools_raw:
        tool_converted = {
            'name': tool.name,
            'link': tool.link,
            'open_source': str(tool.open_source),
            'technology': __get_property_names(tool.technology),
            'web_app': str(tool.web_app),
            'desktop_app': str(tool.desktop_app),
            'category': tool.category,
            'modeling_languages': __get_property_names(tool.modeling_languages),
            'source_code_generation': str(tool.source_code_generation),
            'cloud_service': str(tool.cloud_service),
            'license': tool.license,
            'login_required': str(tool.cloud_service),
            'creators': __get_property_names(tool.creators),
            'platforms': __get_property_names(tool.platforms),
            'real_time_collab': str(tool.cloud_service),
            'programming_languages': __get_property_names(tool.programming_languages)
        }
        tools_converted.append(tool_converted)
    return tools_converted


def __get_modeling_tool_names(tools: [ModelingTool]) -> [str]:
    names: [str] = []
    for tool in tools:
        names.append(tool.name)
    return names


def __get_property_names(tool_property) -> [str]:
    """
    Collects names of all properties (e.g., technology, modeling languages, ...) that a modeling tool contains.
    :param tool_property: property contained with a modeling tool
    :return: list of property names
    """
    property_names: [str] = []
    for tech in tool_property.all():
        property_names.append(tech.name)
    return property_names


def __get_json_body_key_value(body: str, key: str) -> str | None:
    re_match = re.findall(rf'"{key}":"(.*?)"', body)
    return re_match[0] if len(re_match) > 0 else None


def __get_json_body_key_value_bool(body: str, key: str) -> bool | None:
    re_match = re.findall(rf'"{key}":(.*?)[,}}]', body)
    return (True if re_match[0].lower() == 'true' else False) if len(re_match) > 0 else None


def __get_json_body_key_value_arr(body: str, key: str) -> [str]:
    re_match = re.findall(rf'"{key}":(\[.*?\])', body)
    return literal_eval(re_match[0]) if len(re_match) > 0 else []
