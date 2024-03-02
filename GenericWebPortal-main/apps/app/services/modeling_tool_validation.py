from apps.app.models import ModelingTool, ModelingLanguage, Platform, ProgrammingLanguage, Technology, Category, \
    License, Creator


def modeling_tool_validation(
        name: str,
        link: str,
        technologies: [str],
        category: str,
        modeling_languages: [ModelingLanguage],
        tool_license: License,
        developers: [Creator],
        platforms: [Platform],
        programming_languages: [ProgrammingLanguage],
        remarks: str
) -> [str]:
    validation_exceptions: [str] = []

    if name is None or len(name) == 0:
        validation_exceptions.append('Name cannot be empty')
    if len(name) > 255:
        validation_exceptions.append('Name cannot contain more than 255 characters')

    if link is None or len(link) == 0:
        validation_exceptions.append('Link cannot be empty')
    if len(link) > 255:
        validation_exceptions.append('Link cannot contain more than 255 characters')

    db_technologies: [Technology] = Technology.objects.all()
    for tech_name in technologies:
        if len(db_technologies.filter(name=tech_name)) == 0:
            validation_exceptions.append(f'Technology named {tech_name} is an unacceptable input')
            break

    db_categories = Category.choices
    found_matching_category: bool = False
    for category_name in db_categories:
        if category_name[1].lower() == category:
            found_matching_category = True
            break
    if not found_matching_category:
        validation_exceptions.append(f'Category named {category} is an unacceptable input')

    db_modeling_languages: [ModelingLanguage] = ModelingLanguage.objects.all()
    for modeling_language_name in modeling_languages:
        if len(db_modeling_languages.filter(name=modeling_language_name)) == 0:
            validation_exceptions.append(f'Technology named {modeling_language_name} is an unacceptable input')
            break

    db_licenses = License.choices
    found_matching_license: bool = False
    for license_name in db_licenses:
        if license_name[1].lower() == tool_license:
            found_matching_license = True
            break
    if not found_matching_license:
        validation_exceptions.append(f'License named {tool_license} is an unacceptable input')

    db_developers: [str] = Creator.objects.all()
    for dev_name in developers:
        if len(db_developers.filter(name=dev_name)) == 0:
            pass
            # TODO: Uncomment
            # dev: Creator = Creator(name=dev_name, deletable=True)

    db_platforms: [Platform] = Platform.objects.all()
    for platform_name in platforms:
        if len(db_platforms.filter(name=platform_name)) == 0:
            pass
            # TODO: Uncomment
            # platform_new: Platform = Platform(name=platform_name, deletable=True)
    db_programming_languages: [ProgrammingLanguage] = ProgrammingLanguage.objects.all()
    for language_name in programming_languages:
        if len(db_programming_languages.filter(name=language_name)) == 0:
            pass
            # TODO: Uncomment
            # platform_new: Platform = Platform(name=platform_name, deletable=True)
    if len(remarks) > 500:
        validation_exceptions.append('User remarks cannot contain more than 500 characters')
