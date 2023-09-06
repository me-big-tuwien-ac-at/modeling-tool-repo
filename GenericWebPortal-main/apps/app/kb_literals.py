import os
import json


config_file = json.load(open(os.path.join(os.path.dirname(__file__), 'cmsw_config.json')))
DATA_FOLDER = config_file['data_folder']
DB_KEY = config_file['db_key']


COMPLETE_KNOWLEDGE_BASE_FILE = DATA_FOLDER + os.sep + config_file['excel_file']['name']
COLUMN_VALUES_DELIMITER = config_file['column_values_delimiter']

search_cases = config_file['search-cases']

DATA_ANALYSIS = config_file['data_analysis']
MODELLING_LANGUAGES_FILE = DATA_FOLDER + os.sep + DATA_ANALYSIS['modeling-language-count']['filename']
MODELLING_LANGUAGES_COUNT_DF = "modeling_language_count_df"


COUNTRIES_BY_PUBLICATIONS_DF = "country_by_publications_df"
COUNTRIES_BY_AUTHOR_DF = "country_by_author_df"
UNIVERSITY_BY_PUBLICATION_DF = "univeristy_by_publication_df"
UNIVERSITY_BY_AUTHOR_DF = "university_by_author_df"
AUTHOR_BY_PUBLICATION_DF = "author_by_publication_df"

BIBLIOMETRIC_DATA = "data/bibliometric_data.pkl"
NON_BIBLIOMETRIC_DATA = "data/non_bibliometric_data.pkl"


LOG_FILE = DATA_FOLDER + os.sep + config_file['logging']['name']

TAXONOMIES_LABEL = 'taxonomies'
NON_TAXONOMIES_LABEL = 'non-taxonomies'
TAXONOMIES = config_file[TAXONOMIES_LABEL]

NON_TAXONOMY_COLUMN_LABEL = "column_label"
TAXONOMY_COLUMN_LABEL = "column_label"
TAXONOMY_LABEL = 'label'
TAXONOMY_NAME_LABEL = 'name'
TAXONOMY_COLUMNS = 'columns'
TAXONOMY_VALUES = 'values'
TAXONOMY_BADGE = 'badge'
TAXONOMY_BADGE_COLOR = 'badge_color'


BADGE_CATEGORIES = {
    column: taxonomy[TAXONOMY_BADGE] 
    for taxonomy in TAXONOMIES for column in taxonomy[TAXONOMY_COLUMNS]
}


TOP_SEARCH_CASES = search_cases['top_search_cases']
PUBLICATION_SEARCH = TOP_SEARCH_CASES['Publications']
AUTHOR_SEARCH = TOP_SEARCH_CASES['Authors']
VENUE_SEARCH = TOP_SEARCH_CASES['Venues']


YEAR = config_file['year']

VENUES = "Journals & Conferences"
PUBLICATIONS = "Publications"
AUTHORS = "Authors"

UI_LITERALS = config_file['UI-Labels']

AFFILIATION_NAME = 'name'
AFFILIATION_COUNTRY = 'country'
AFFILIATION_UNIVERSITY = 'University'
AFFILIATIONS_DETAILS = 'Affiliation(s)'
AFFILIATION_SCOPUS_ID = 'scopus_affiliation_id'
AFFILIATION_SCOPUS_IDS = 'scopus_affiliation_ids'
AUTHOR_NAME = 'name'
AUTHORS_DETAILS = 'Authors'
AUTHORS_NAMES = AUTHORS_DETAILS + " " + AUTHOR_NAME
AUTHOR = 'Author'
AUTHOR_SCOPUS_ID = 'author_scopus_id'
AUTHOR_PUBLICATION_COUNT = "Author Publication Count"
COUNTRY_PUBLICATION_COUNT = "Country Publication Count"
COUNTRY_AUTHOR_COUNT = "Country Author Count"
UNIVERSITY_PUBLICATION_COUNT = "University Publication Count"
UNIVERSITY_AUTHOR_COUNT = "University Author Count"
PUBLICATION_ID = "EID"

AUTHOR_NAME_INDEX = 'Author(s)'
COUNTRY_NAME_INDEX = 'Country(s)'
UNIVERSITY_NAME_INDEX = 'University(s)'


kb_column_labels = config_file['kb_column_labels_map']
TITLE_HASH = 'TitleHash'
TITLE_LABEL = 'Title'
YEAR_LABEL = 'Year'
DOC_TYPE_LABEL = 'DocumentType'
AUTHORS_LABEL = 'Authors'
DOI_LABEL = 'DOI'
ABSTRACT_LABEL = 'Abstract'
VENUE_LABEL = 'Venue'


DOCUMENT_TYPE_VALUES = config_file['DocumentTypeValues']
JOURNAL_DOC_TYPE = DOCUMENT_TYPE_VALUES['Article']
CONFERENCE_PAPER_DOC_TYPE = DOCUMENT_TYPE_VALUES['Conference Paper']
BOOK_CHAPTER_DOC_TYPE = DOCUMENT_TYPE_VALUES['Book Chapter']


MANDATORY_HEADER = [TITLE_LABEL, YEAR_LABEL, AUTHORS_LABEL, DOI_LABEL, ABSTRACT_LABEL, VENUE_LABEL, DOC_TYPE_LABEL]
