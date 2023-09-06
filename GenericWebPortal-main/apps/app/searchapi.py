from apps.app.searchutils import *
from apps.app import kb_literals as kb

def search_records(context):
    mandatory_header, badges_categories = kb.MANDATORY_HEADER, kb.BADGE_CATEGORIES
    taxonomies, non_taxonomies = context[kb.TAXONOMIES_LABEL], context[kb.NON_TAXONOMIES_LABEL]
    search_query = context['search_query']

    records, summary, taxonomy_count, non_taxonomy_count = get_records(taxonomies, non_taxonomies, search_query, context['search_type'])
    if context['search_type'] == kb.PUBLICATIONS:
        records = format_publications_to_show(records, mandatory_header, badges_categories)
    elif context['search_type'] == kb.AUTHORS_LABEL:
        records = format_authors_to_show(records, search_query, mandatory_header, badges_categories)
    else:
        records = format_venues_to_show(records, search_query, mandatory_header, badges_categories)
    return records, summary, taxonomy_count, non_taxonomy_count

