import ast

import nltk
import pandas as pd
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize
from apps.app import kb_literals as kb
from apps.app.data import kb_data
from apps.app import utils


nltk.download('stopwords')
nltk.download('punkt')


def get_records_by_column_values(column_name, values, dataset, allow_empty=True):
    empty = dataset[dataset[column_name].isna()]
    records = dataset[~dataset[column_name].isna()]
    indices = [i for i, row in records.iterrows() if any(str(value).lower() in str(row[column_name]).lower() for value in values)]
    matched = dataset.loc[indices]

    if allow_empty:
        results = pd.concat([matched, empty], ignore_index=True).drop_duplicates(subset=[kb.TITLE_HASH])
        return results
    return matched

def get_records_by_column_values_excluded(column_name, values, dataset, allow_empty=True):
    records = get_records_by_column_values(column_name, values, dataset, allow_empty)
    return dataset[~dataset[kb.TITLE_HASH].isin(records[kb.TITLE_HASH])]


def get_records_by_keywords(search_query, search_type):
    text_tokens = word_tokenize(search_query)
    if search_type == kb.PUBLICATIONS:
        text_tokens = [word for word in text_tokens if word not in stopwords.words() and word.isalpha()]

    results = pd.DataFrame()
    if not text_tokens:
        return kb_data

    for column in kb.TOP_SEARCH_CASES[search_type]:
        records = get_records_by_column_values(column, text_tokens, kb_data)
        # results = df_intersection_by_hash(results, records) if len(results) != 0 else records
        results = pd.concat([results, records], ignore_index=True).drop_duplicates(subset=[kb.TITLE_HASH])
    return results


def get_final_set_count(record_hash_count):
    final_set = set()
    for _, hashes_set in record_hash_count.items():
        final_set = final_set.union(hashes_set)
    return len(final_set)


def update_taxonomies_to_show(results, request_taxonomies):
    for _, top_level_taxonomy_data in request_taxonomies.items():
        for taxonomy in top_level_taxonomy_data[kb.TAXONOMY_COLUMNS]:
            record_hash_count = dict()
            for value, value_data in taxonomy[kb.TAXONOMY_VALUES].items():
                records = results.loc[[i for i, row in results.iterrows() if str(value).lower() in str(row[taxonomy['name']]).lower()]]
                value_data['count'] = len(records)
                value_data['to_show'] = value_data['count'] != 0
                record_hash_count[value] = set(records[kb.TITLE_HASH])
            taxonomy['count'] = get_final_set_count(record_hash_count)


def update_non_taxonomies_to_show(results, non_taxonomies):
    record_hash_count = dict()
    for year, year_data in non_taxonomies['years'][kb.TAXONOMY_VALUES].items():
        records = results[results[kb.YEAR_LABEL] == int(year)]
        count = len(records)
        year_data['count'] = count
        year_data['to_show'] = count != 0
        record_hash_count[year] = set(records[kb.TITLE_HASH])
    non_taxonomies['years']['count'] = get_final_set_count(record_hash_count)

    for country, country_data in non_taxonomies['countries'][kb.TAXONOMY_VALUES].items():
        records = results.loc[[i for i, row in results.iterrows() if str(country).lower() in str(row[kb.AFFILIATIONS_DETAILS]).lower()]]
        count = len(records)
        country_data['count'] = count
        country_data['to_show'] = count != 0
        record_hash_count[country] = set(records[kb.TITLE_HASH])

    non_taxonomies['countries']['count'] = get_final_set_count(record_hash_count)


def update_taxonomies_to_check(all_taxonomies):
    for _, top_level_taxonomy_data in all_taxonomies.items():
        for taxonomy in top_level_taxonomy_data[kb.TAXONOMY_COLUMNS]:
            for _, value_data in taxonomy[kb.TAXONOMY_VALUES].items():
                if value_data['checked']:
                    value_data['checked'] = value_data['count'] != 0


def update_non_taxonomies_to_check(non_taxonomies):
    
    for _, year_data in non_taxonomies['years'][kb.TAXONOMY_VALUES].items():
        year_data['checked'] = year_data['count'] != 0

    for _, country_data in non_taxonomies['countries'][kb.TAXONOMY_VALUES].items():
        country_data['checked'] = country_data['count'] != 0


def df_intersection_by_hash(df1, df2):
    record_hash_map = {record[kb.TITLE_HASH]: record for records in [df1, df2] for _, record in records.iterrows()}
    s = set(df1[kb.TITLE_HASH].values)
    s = s.intersection(set(df2[kb.TITLE_HASH].values))
    return pd.DataFrame([record_hash_map[record_hash] for record_hash in s])


def get_taxonomy_filtered_records(query, records):
    if not query:
        return records

    filtered_records = records
    for query_column, query_values in query.items():
        if len(query_values) != 0:
            filtered_records = get_records_by_column_values_excluded(query_column, query_values, filtered_records)

    return filtered_records


def get_filter_query(taxonomies_data, non_taxonomies_data):
    query_dict = dict()
    for _, top_level_taxonomy in taxonomies_data.items():
        for taxonomy_column in top_level_taxonomy[kb.TAXONOMY_COLUMNS]:
            values = [value for value, value_data in taxonomy_column[kb.TAXONOMY_VALUES].items() if value_data['to_show'] and not value_data['checked']]
            # show_values = [value for value, value_data in taxonomy_column[kb.TAXONOMY_VALUES].items() if value_data['to_show']]
            # checked_values = [value for value, value_data in taxonomy_column[kb.TAXONOMY_VALUES].items() if value_data['checked']]
            # if len(show_values) != len(checked_values):
            #     query_dict[taxonomy_column[kb.TAXONOMY_COLUMN_LABEL]] = checked_values
            query_dict[taxonomy_column[kb.TAXONOMY_COLUMN_LABEL]] = values
    
    for _, non_taxonomy_data in non_taxonomies_data.items():
        values = [value for value, value_data in non_taxonomy_data[kb.TAXONOMY_VALUES].items() if value_data['to_show'] and not value_data['checked']]
        # show_values = [value for value, value_data in non_taxonomy_data[kb.TAXONOMY_VALUES].items() if value_data['to_show'] and not value_data['checked']]
        # checked_values = [value for value, value_data in non_taxonomy_data[kb.TAXONOMY_VALUES].items() if value_data['checked']]
        # if len(show_values) != len(checked_values):
        #     query_dict[non_taxonomy_column[kb.NON_TAXONOMY_COLUMN_LABEL]] = checked_values 
        query_dict[non_taxonomy_data[kb.NON_TAXONOMY_COLUMN_LABEL]] = values

    return query_dict


def process_dois(records):
    records[kb.DOI_LABEL] = records[kb.DOI_LABEL].apply(lambda x: "https://doi.org/" + x if not isinstance(x, float) and x.startswith('10.') else x)
    records[kb.DOI_LABEL] = records[kb.DOI_LABEL].apply(lambda x: None if isinstance(x, float) else x)
    return records


def get_records(taxonomies,  non_taxonomies, search_query, search_type):
    results_by_keywords = get_records_by_keywords(search_query, search_type)
    filter_query = get_filter_query(taxonomies, non_taxonomies)
    records = get_taxonomy_filtered_records(filter_query, results_by_keywords)

    update_taxonomies_to_show(records, taxonomies)
    update_non_taxonomies_to_show(records, non_taxonomies)

    update_taxonomies_to_check(taxonomies)
    update_non_taxonomies_to_check(non_taxonomies)

    summary = create_results_summary(results_by_keywords)

    process_dois(records)
    return records, summary, taxonomies, non_taxonomies


def get_badges(result, badges_columns):
    badges = {taxonomy[kb.TAXONOMY_BADGE]: {kb.TAXONOMY_BADGE_COLOR: taxonomy[kb.TAXONOMY_BADGE_COLOR], "values": list()} for taxonomy in kb.TAXONOMIES}
    for column in badges_columns:
        if not isinstance(result[column], float):
            badges[kb.BADGE_CATEGORIES[column]]['values'] += list(set([badge.strip() for badge in result[column].split(',')]))
    return badges


def get_author_from_authors_list(authors, query):
    authors_list = list()
    authors = [author['name'] for author in authors]
    for author in authors:
        if not query or query.lower() in author.lower():
            authors_list.append(author)

    return authors_list


def get_authors_by_publications(publications, search_query):
    author_set = set()
    for _, row in publications.iterrows():
        for author in get_author_from_authors_list(row[kb.AUTHORS_LABEL], search_query):
            author_set.add(author)
    authors_data = dict()
    for _, publication in publications.iterrows():
        record = publication
        for author in author_set:
            if author in authors_data:
                authors_data[author].append(record)
            else:
                authors_data[author] = [record]
    return authors_data


def get_venues_by_publications(publications, search_query):
    venues_set = set()
    for _, row in publications.iterrows():
        if not search_query or search_query.lower() in row[kb.VENUE_LABEL].lower():
            venues_set.add(row[kb.VENUE_LABEL])
    venues_data = dict()
    for _, publication in publications.iterrows():
        record = publication
        for venue in venues_set:
            if venue == record[kb.VENUE_LABEL]:
                if venue in venues_data:
                    venues_data[venue].append(record)
                else:
                    venues_data[venue] = [record]
    return venues_data


def format_publications_to_show(df, mandatory_header, badges_columns):
    results = list()
    for i, row in df.iterrows():
        result = dict()
        for column in mandatory_header:
            result[column] = row[column]
        result['badges'] = get_badges(row, badges_columns)
        result['index'] = i + 1
        results.append(result)
    return results


def format_authors_to_show(df, search_query, mandatory_header, badges_columns):
    authors_data = get_authors_by_publications(df, search_query)
    authors_results = list()
    for author, publications in authors_data.items():
        author_result = {'name': author}
        publications_list = list()
        for publication in publications:
            publication_data = dict()
            for column in mandatory_header:
                publication_data[column] = publication[column]
            publication_data[kb.AFFILIATIONS_DETAILS] = get_affiliations_list(publication, search_query)
            publication_data['badges'] = get_badges(publication, badges_columns)
            publication_data[kb.TITLE_HASH] = abs(hash(publication['Title']))
            publications_list.append(publication_data)
        publications_list = sorted(publications_list, key=lambda x: x[kb.YEAR_LABEL], reverse=True)
        author_result[kb.PUBLICATIONS] = publications_list
        authors_results.append(author_result)
    authors_results = sorted(authors_results, key=lambda x: (len(x[kb.PUBLICATIONS]), x['name']), reverse=True)
    return authors_results


def format_venues_to_show(df, search_query, mandatory_header, badges_columns):
    venues_data = get_venues_by_publications(df, search_query)
    venues_results = list()
    for venue, publications in venues_data.items():
        venue_result = {'name': venue}
        publications_list = list()
        for publication in publications:
            publication_data = dict()
            for column in mandatory_header:
                publication_data[column] = publication[column]
            publication_data[kb.AFFILIATIONS_DETAILS] = get_affiliations_list(publication)
            publication_data['badges'] = get_badges(publication, badges_columns)
            publications_list.append(publication_data)
        publications_list = sorted(publications_list, key=lambda x: x['Year'], reverse=True)
        venue_result['publications'] = publications_list
        venues_results.append(venue_result)
    venues_results = sorted(venues_results, key=lambda x: (len(x['publications']), x['name']), reverse=True)
    return venues_results


def create_results_summary(results):
    authors_affiliations_summary = get_authors_affiliations_summary(results)
    doc_type_summary = get_document_type_summary(results)
    years = results[kb.YEAR_LABEL].unique().tolist()
    years.sort()
    return {
        "affiliations_summary": authors_affiliations_summary,
        "doc_type_summary": doc_type_summary,
        'years': {'yearly_count': years}
    }


def add_if_present(keys, dictionary):
    for key in keys:
        if key in dictionary:
            dictionary[key] += 1
        else:
            dictionary[key] = 1
    return dictionary


def getTopN(d, n=5):
    return d.sort_values(by=[d.columns[-1]], ascending=False).values.tolist()[:n]


def get_authors_affiliations_summary(results):
    bibliometric_data = utils.bibliometric_analysis(results)

    country_by_publication = bibliometric_data[kb.COUNTRIES_BY_PUBLICATIONS_DF]
    univeristy_by_publication = bibliometric_data[kb.UNIVERSITY_BY_PUBLICATION_DF]
    author_by_publication = bibliometric_data[kb.AUTHOR_BY_PUBLICATION_DF]
    

    affiliation_summary = {
        "author_count": len(author_by_publication),
        "uni_count": len(univeristy_by_publication),
        "country_count": len(country_by_publication),
        "topNAuthors": getTopN(author_by_publication, 10),
        "topNUniversities": getTopN(univeristy_by_publication, 10),
        "topNCountries": getTopN(country_by_publication, 10),
        "authors": {
            'name': "Authors",
            'values': author_by_publication[kb.AUTHOR_NAME].tolist(),
        },
        "Institutions": {
            'name': "Institutions",
            'values': univeristy_by_publication[kb.AFFILIATION_UNIVERSITY].tolist(),
        },
        "countries": {
            "name": "Countries",
            "values": country_by_publication[kb.AFFILIATION_COUNTRY].tolist(),
        }
    }

    return affiliation_summary


def get_document_type_summary(results):
    journals = results.loc[results[kb.DOC_TYPE_LABEL] == kb.JOURNAL_DOC_TYPE][kb.VENUE_LABEL]
    papers = results.loc[results[kb.DOC_TYPE_LABEL] == kb.CONFERENCE_PAPER_DOC_TYPE][kb.VENUE_LABEL]
    chapters = results[results[kb.DOC_TYPE_LABEL] == kb.BOOK_CHAPTER_DOC_TYPE][kb.VENUE_LABEL]
    chapter_count, paper_count, journal_count = dict(), dict(), dict()
    for paper in papers:
        try:
            paper_count[paper] += 1
        except KeyError:
            paper_count[paper] = 1

    for chapter in chapters:
        try:
            chapter_count[chapter] += 1
        except KeyError:
            chapter_count[chapter] = 1

    for journal in journals:
        try:
            journal_count[journal] += 1
        except KeyError:
            journal_count[journal] = 1
    doc_type_summary = {
        'journal_count': len(journals),
        'chapters_count': len(chapters),
        'conferences_count': len(papers),
        "topNJournals": list(sorted(journal_count.items(), key=lambda x: x[1], reverse=True)[:5]),
        "topNConferences": list(sorted(paper_count.items(), key=lambda x: x[1], reverse=True)[:5]),
        "conferences": {
            'name': "Conference Papers",
            'values': list(paper_count.keys()),
        },
        "journals": {
            'name': "Journals",
            'values': list(journal_count.keys())
        }
    }

    return doc_type_summary


def get_affiliations_list(publication, search_query=None):
    affiliations_list = list()
    affiliations = publication[kb.AFFILIATIONS_DETAILS]
    authors = publication[kb.AUTHORS_LABEL]
    
    for author, affiliation in zip(authors, affiliations):
        affiliation_data = {
            "author": author['name'],
            'searched_author': True if search_query and search_query.lower() in author[kb.AUTHOR_NAME].lower() else False
        }
        affiliation_data['institute_countries'] = [
            {'university': affiliation_item[kb.AFFILIATION_NAME], kb.AFFILIATION_COUNTRY: affiliation_item[kb.AFFILIATION_COUNTRY]} 
            for affiliation_item in affiliation
            ]
        affiliations_list.append(affiliation_data)

    return affiliations_list
