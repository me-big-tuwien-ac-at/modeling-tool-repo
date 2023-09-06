import pandas as pd
from apps.app import kb_literals as kb, utils
import ast


def update_modeling_languages(df):
    def f(x):
        if x.strip() in reverse_keys:
            return reverse_keys[x.strip()]
        return "Others"
    
    d = dict()
    d['UML & UML Diagram Type'] = ['UML', 'Class Diagram', 'State Machine Diagram', 'Activity Diagram', 'Object Diagram',
                                'Use Case Diagram', 'State Diagram', 'Sequence Diagram', 'State Chart Diagram']
    d['Petri Nets'] = ['Petri Net', 'Fuzzy Petri Net', 'Petri Net extension']
    d['UML Profile'] = ['UML Profile']
    d['Entity Relationship'] = ['Entity Relationship Diagram', 'EER']
    d['Goal Model & iStar'] = ['Goal Model', 'iStar']
    d['SysML'] = ['SysML', 'SysML Profile']
    d['DSML'] = ['DSML', 'DSML (document metamodel)']

    reverse_keys = dict()
    for key, values in d.items():
        for value in values:
            reverse_keys[value] = key

    reverse_keys['Domain Ontology'] = 'Domain Ontology'
    reverse_keys["Ecore"] = "Ecore"
    reverse_keys['BPMN'] = "BPMN"

    df['Modelling Language'] = df['Modelling Language'].apply(
            lambda x: ", ".join(list(map(f, x.split(',')))) if not isinstance(x, float) else "Others")
    
    return df


def preprocess_data(df):
    df = df.rename(columns={v: k for k, v in kb.kb_column_labels.items()})
    df = deserialize(df)
    df = utils.add_index_columns(df)

    ## create functions for different data preprocessing
    # Example preprocessing function
    # df = update_modeling_languages(df)

    return df

def add_hash(df):
    df[kb.TITLE_HASH] = df[kb.TITLE_LABEL].apply(lambda x: abs(hash(x)))
    return df

def deserialize(df):
    assert kb.AFFILIATIONS_DETAILS in df.columns
    assert kb.AUTHORS_DETAILS in df.columns

    df[kb.AFFILIATIONS_DETAILS] = df[kb.AFFILIATIONS_DETAILS].apply(lambda x: ast.literal_eval(x))
    df[kb.AUTHORS_DETAILS] = df[kb.AUTHORS_DETAILS].apply(lambda x: ast.literal_eval(x))
    return df
