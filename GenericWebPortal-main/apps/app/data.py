import pandas as pd
from apps.app import data_preprocessing
from apps.app.utils import bibliometric_analysis
from apps.app import kb_literals as kb
import pickle

data_file = kb.COMPLETE_KNOWLEDGE_BASE_FILE


kb_data = pd.read_excel(data_file)

assert all([value in kb_data.columns for _, value in kb.kb_column_labels.items()]), "Missing columns in the knowledge base"

if kb.TITLE_HASH not in kb_data.columns:
    kb_data = data_preprocessing.add_hash(kb_data)

kb_data = data_preprocessing.preprocess_data(kb_data)

kb_bibliometric_data = bibliometric_analysis(kb_data)
pickle.dump(kb_bibliometric_data, open(kb.BIBLIOMETRIC_DATA, "wb"))

modelling_language_count_file = kb.MODELLING_LANGUAGES_FILE
modeling_language_count = pd.read_excel(modelling_language_count_file)

kb_non_bibliometric_data = {
    kb.MODELLING_LANGUAGES_COUNT_DF: modeling_language_count
}

pickle.dump(kb_non_bibliometric_data, open(kb.NON_BIBLIOMETRIC_DATA, "wb"))

countries = list(set(country for _, row in kb_data.iterrows() for country in row[kb.COUNTRY_NAME_INDEX].split(';')))

log_file = open(kb.LOG_FILE, 'a')

print("Data Loaded!")