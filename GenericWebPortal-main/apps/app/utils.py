import pickle
import pandas as pd
from apps.app import kb_literals as kb


bib_analysis_table_map = {
    "country-by-publication": kb.COUNTRIES_BY_PUBLICATIONS_DF,
    "university-by-publication": kb.UNIVERSITY_BY_PUBLICATION_DF,
    "author-by-publication": kb.AUTHOR_BY_PUBLICATION_DF,
    "university-by-author": kb.UNIVERSITY_BY_AUTHOR_DF,
    "country-by-author": kb.COUNTRIES_BY_AUTHOR_DF
}

non_bib_analysis_table_map = {
    "modeling-language-count": kb.MODELLING_LANGUAGES_COUNT_DF
}

def add_if_present(key, dictionary):
    if key in dictionary:
        dictionary[key] += 1
    else:
        dictionary[key] = 1
    return dictionary


def bibliometric_analysis(dataset):
    assert kb.AFFILIATIONS_DETAILS in dataset.columns
    assert kb.AUTHORS_DETAILS in dataset.columns

    publication_author_affiliation_map, authors_map, affiliations_map = {}, {}, {}
    university_by_authors, country_by_authors = {}, {}
    for _, row in dataset.iterrows():
        authors = row[kb.AUTHORS]
        affiliations = row[kb.AFFILIATIONS_DETAILS]
        author_ids = [author[kb.AUTHOR_SCOPUS_ID] for author in authors]
        affiliation_ids = [
            [author_affiliation[kb.AFFILIATION_SCOPUS_ID] for author_affiliation in author_affliations]
            for author_affliations in affiliations
        ]
        for author in authors:
            if author[kb.AUTHOR_SCOPUS_ID] not in authors_map:
                authors_map[author[kb.AUTHOR_SCOPUS_ID]] = author

        for author_affiliations in affiliations:
            for author_affiliation in author_affiliations:
                university_by_authors = add_if_present(author_affiliation[kb.AFFILIATION_NAME], university_by_authors)
                country_by_authors = add_if_present(author_affiliation[kb.AFFILIATION_COUNTRY], country_by_authors)
                
                if author_affiliation[kb.AFFILIATION_SCOPUS_ID] not in affiliations_map:
                    affiliations_map[author_affiliation[kb.AFFILIATION_SCOPUS_ID]] = author_affiliation
        
        publication_author_affiliation_map[row[kb.PUBLICATION_ID]] = {"author_ids": author_ids, "affiliation_ids": affiliation_ids}
                    
    
    authors_by_publications, country_by_publications, university_by_publications = {}, {}, {}
    for _, author_affliation_ids in publication_author_affiliation_map.items():
        author_ids = author_affliation_ids['author_ids']
        affiliation_ids = author_affliation_ids['affiliation_ids']
        for author_id in author_ids:
            authors_by_publications = add_if_present(author_id, authors_by_publications)
        university, country = set(), set()
        for author_affiliation_ids in affiliation_ids:
            for author_affiliation_id in author_affiliation_ids:
                university.add(affiliations_map[author_affiliation_id][kb.AFFILIATION_NAME])
                country.add(affiliations_map[author_affiliation_id][kb.AFFILIATION_COUNTRY])

        for u in university:
            university_by_publications = add_if_present(u, university_by_publications)
        for c in country:
            country_by_publications = add_if_present(c, country_by_publications)
    
    country_by_publications = {k: v for k, v in sorted(country_by_publications.items(), key=lambda item: item[1], reverse=True)}
    authors_by_publications = {authors_map[k][kb.AUTHOR_NAME]: v for k, v in sorted(authors_by_publications.items(), key=lambda item: item[1], reverse=True)}
    university_by_publications = {k: v for k, v in sorted(university_by_publications.items(), key=lambda item: item[1], reverse=True)}
    university_by_authors = {k: v for k, v in sorted(university_by_authors.items(), key=lambda item: item[1], reverse=True)}
    country_by_authors = {k: v for k, v in sorted(country_by_authors.items(), key=lambda item: item[1], reverse=True)}

    statistics_dataframes = {
        kb.COUNTRIES_BY_PUBLICATIONS_DF: pd.DataFrame([(k, v) for k, v in country_by_publications.items()], columns=[kb.AFFILIATION_COUNTRY, kb.COUNTRY_PUBLICATION_COUNT]),
        kb.UNIVERSITY_BY_PUBLICATION_DF: pd.DataFrame([(k, v) for k, v in university_by_publications.items()], columns=[kb.AFFILIATION_UNIVERSITY, kb.UNIVERSITY_PUBLICATION_COUNT]),
        kb.AUTHOR_BY_PUBLICATION_DF: pd.DataFrame([(k, v) for k, v in authors_by_publications.items()], columns=[kb.AUTHOR_NAME, kb.AUTHOR_PUBLICATION_COUNT]),
        kb.UNIVERSITY_BY_AUTHOR_DF: pd.DataFrame([(k, v) for k, v in university_by_authors.items()], columns=[kb.AFFILIATION_UNIVERSITY, kb.UNIVERSITY_AUTHOR_COUNT]),
        kb.COUNTRIES_BY_AUTHOR_DF: pd.DataFrame([(k, v) for k, v in country_by_authors.items()], columns=[kb.AFFILIATION_COUNTRY, kb.COUNTRY_AUTHOR_COUNT])
    }

    return statistics_dataframes


def create_table_data(table, name):
    if 'Unnamed: 0' in table.columns:
        table = table.drop('Unnamed: 0', axis=1)

    if 'SR.NO' not in table.columns:
        table.insert(0, 'SR.NO', range(1, 1 + len(table)))
    table_data = {"tableName": name, "columns": list(table.columns), "values": table.values.tolist()}
    return table_data


def get_analysis_table(analysis_data, analysis_map, name):
    return create_table_data(analysis_data[analysis_map[name]], kb.DATA_ANALYSIS[name]['label'])


def get_relevant_table(name):
    if name in bib_analysis_table_map:
        kb_bibliometric_data = pickle.load(open(kb.BIBLIOMETRIC_DATA, 'rb'))
        return get_analysis_table(kb_bibliometric_data, bib_analysis_table_map, name)
    else:
        kb_non_bibliometric_data = pickle.load(open(kb.NON_BIBLIOMETRIC_DATA, 'rb'))
        return get_analysis_table(kb_non_bibliometric_data, non_bib_analysis_table_map, name)


def add_index_columns(df):
    df[[kb.AUTHOR_NAME_INDEX, kb.COUNTRY_NAME_INDEX, kb.UNIVERSITY_NAME_INDEX]] = ""
    for index, row in df.iterrows():
        df.at[index, kb.AUTHOR_NAME_INDEX] = ";".join([author[kb.AUTHOR_NAME] for author in row[kb.AUTHORS]])
        df.at[index, kb.COUNTRY_NAME_INDEX] = ";".join(
            set(author_affiliation[kb.AFFILIATION_COUNTRY] \
            for author_affiliations in row[kb.AFFILIATIONS_DETAILS] \
                for author_affiliation in author_affiliations if author_affiliation[kb.AFFILIATION_COUNTRY])
        )
        df.at[index, kb.UNIVERSITY_NAME_INDEX] = ";".join(
            set(author_affiliation[kb.AFFILIATION_NAME] \
            for author_affiliations in row[kb.AFFILIATIONS_DETAILS] \
            for author_affiliation in author_affiliations if author_affiliation[kb.AFFILIATION_NAME]))
    
    return df