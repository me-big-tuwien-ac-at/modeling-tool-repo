from apps.app import data
from apps.app import kb_literals as kb


print("Get Taxonomies")

taxonomy_map = dict()
for taxonomy in kb.TAXONOMIES:
    for value in taxonomy[kb.TAXONOMY_COLUMNS]:
        taxonomy_map[value] = taxonomy[kb.TAXONOMY_NAME_LABEL]


def get_list(pandas_series, delimiter=kb.COLUMN_VALUES_DELIMITER):
    values_ = [[v.strip() for v in value_.split(delimiter) if v.strip() != '']
               if value_ and not isinstance(value_, float) else ""
               for _, value_ in pandas_series.iteritems()]
    unique_values = dict()
    for value_ in values_:
        for v in value_:
            if v in unique_values:
                unique_values[v] += 1
            else:
                unique_values[v] = 1
    return values_, unique_values


def get_taxonomies():
    taxonomies = dict()
    for taxonomy in kb.TAXONOMIES:
        taxonomy_columns = list()
        for column in taxonomy[kb.TAXONOMY_COLUMNS]:
            taxonomy_column_init = {
                kb.TAXONOMY_COLUMN_LABEL: column,
                kb.TAXONOMY_NAME_LABEL: column,
                kb.TAXONOMY_VALUES: {
                    value: {
                        'count': 0, 'checked': True, 'to_show': True
                    } 
                    for value in list(get_list(data.kb_data[column])[1].keys())
                },
                'count': 0
                }
            taxonomy_columns.append(taxonomy_column_init)
        
        taxonomies[taxonomy['name']] = {
            kb.TAXONOMY_COLUMN_LABEL: taxonomy['name'],
            kb.TAXONOMY_LABEL: taxonomy['label'],
            kb.TAXONOMY_COLUMNS: taxonomy_columns
        }

    return taxonomies


def get_non_taxonomies():
    non_taxonomies = dict()
    non_taxonomies['years'] = {
        kb.TAXONOMY_COLUMN_LABEL: kb.YEAR_LABEL,
        kb.TAXONOMY_LABEL: 'Years',
        kb.TAXONOMY_VALUES: {
            year: {
                'count': 0, 'checked': True, 'to_show': True
            } 
            for year in range(kb.YEAR['min'], kb.YEAR['max'])
        },
        'count': 0
    }
    non_taxonomies['countries'] = {
        kb.TAXONOMY_COLUMN_LABEL: kb.AFFILIATIONS_DETAILS,
        kb.TAXONOMY_LABEL: 'Countries',
        kb.TAXONOMY_VALUES: {
            country: {
                'count': 0, 'checked': True, 'to_show': True
            } 
            for country in data.countries
        },
        'count': 0
    }

    return non_taxonomies