from django import forms

from apps.app import kb_literals as kb


class Taxonomy(forms.Form):
    def __init__(self, taxonomy):
        super(Taxonomy, self).__init__()
        
        self.name = taxonomy['name']
        self.values = [(forms.BooleanField(label=key), value_check['count'],
                        value_check['checked'], value_check['to_show'])
                       for key, value_check in taxonomy['values'].items()]
        self.id = "".join(self.name.split())
        self.count = taxonomy['count']


class NonTaxonomy(forms.Form):
    def __init__(self, non_taxonomy):
        super(NonTaxonomy, self).__init__()
        self.name = non_taxonomy['label']
        self.values = [(forms.BooleanField(label=key), value_check['count'],
                        value_check['checked'], value_check['to_show'])
                       for key, value_check in non_taxonomy['values'].items()]
        self.id = "".join(self.name.split())
        self.count = non_taxonomy['count']


class CompleteForm(forms.Form):
    def __init__(self, taxonomies, non_taxonomies, search_query, search_types):
        super(CompleteForm, self).__init__()
        
        self.taxonomies = {
            taxonomy_name: {
                kb.TAXONOMY_LABEL: top_level_taxonomy_data[kb.TAXONOMY_LABEL],
                kb.TAXONOMY_COLUMNS: [Taxonomy(taxonomy_column) for taxonomy_column in top_level_taxonomy_data[kb.TAXONOMY_COLUMNS]]
            }
            for taxonomy_name, top_level_taxonomy_data in taxonomies.items()
        }
        self.search_types = [forms.BooleanField(label=search_type) for search_type in search_types]
        self.search_query = search_query
        self.years = NonTaxonomy(non_taxonomies['years'])
        self.countries = NonTaxonomy(non_taxonomies['countries'])
