import re

path: str = r'./../backend/src/main/java/com/example/modeling_tools/datagenerator/ModelingToolDataGenerator.java'


class Property(object):
    name: str
    deletable: bool
    pk: int

    def __init__(self, name: str, deletable: bool, pk: int):
        self.name = name
        self.deletable = deletable
        self.pk = pk

    def __get_name(self) -> str:
        return self.name

    def __get_deletable(self) -> bool:
        return self.deletable

    def __get_pk(self) -> int:
        return self.pk


class ModelingLanguage(Property):
    pass


class Platform(Property):
    pass


class ProgrammingLanguage(Property):
    pass


class Technology(Property):
    pass


class Creator(Property):
    pass


class ModelingTool:
    __name: str
    __link: str
    __open_source: bool
    __technologies: [str]
    __web_app: bool
    __desktop_app: bool
    __category: str
    __modeling_languages: [str]
    __source_code_generation: bool
    __cloud_service: bool
    __license: str
    __login_required: bool
    __creators: [str]
    __platforms: [str]
    __real_time_collab: bool
    __programming_languages: [str]

    def __init__(self, name: str, link: str, open_source: str, technologies: [str], web_app: str, desktop_app: str,
                 category: str, modeling_languages: [str], source_code_generation: str, cloud_service: str,
                 license: str, login_required: str, creators: [str], platforms: [str], real_time_collab: str,
                 programming_languages: [str]):
        self.__name = name.replace('"', '')
        self.__link = link
        self.__open_source = self.__get_bool_value(open_source)
        self.__technologies = self.__extract_list_of_values(technologies)
        self.__web_app = self.__get_bool_value(web_app)
        self.__desktop_app = self.__get_bool_value(desktop_app)
        self.__category = self.__extract_license_category(category)
        self.__modeling_languages = self.__extract_programming_languages_modeling_languages(modeling_languages)
        self.__source_code_generation = self.__get_bool_value(source_code_generation)
        self.__cloud_service = self.__get_bool_value(cloud_service)
        self.__license = self.__extract_license_category(license)
        self.__login_required = self.__get_bool_value(login_required)
        self.__creators = self.__extract_list_of_values(creators)
        self.__platforms = self.__extract_platforms(platforms)
        self.__real_time_collab = self.__get_bool_value(real_time_collab)
        self.__programming_languages = self.__extract_programming_languages_modeling_languages(programming_languages)

    def __get_bool_value(self, value: str) -> bool:
        value = value.strip().lower()
        return True if value == 'true' else (False if value == 'false' else None)

    def __extract_license_category(self, entry: str) -> str:
        """
        Extracts the values which are paired with a Java Enumerator class.
        Following modeling tool attributes are returned: licenses and category.

        :param entry: string value which contains our sought for value
        :return: extracted value
        """
        match = re.match("^\s*(Category|License)\.(.*)|^\s*(null)$", entry)
        return None if match.group(0).strip() == "null" else match.group(2)

    def __extract_list_of_values(self, entry: str) -> [str]:
        """
        Extracts values from strings contained within "List.of(x)", whereby x represents the value we wish to extract.
        Following modeling tool attributes are returned: creators and technologies.
        :param entry: string value which contains our sought for value
        :return: extracted value
        """

        list_of_entries = re.match("\s*List.of\((.*?)\)$|^\s*(null)$", entry)
        if list_of_entries.group(0).strip() == "null":
            return None
        elif "Technology." in list_of_entries.group(1):
            return ([x.split('.')[1] for x in list_of_entries.group(1).split(",")]
                    if "," in list_of_entries.group(1)
                    else [list_of_entries.group(1).split(".")[1]]
                    )
        else:
            return ([x for x in list_of_entries.group(1).split('"\s*,')]
                    if "," in list_of_entries.group(1)
                    else [list_of_entries.group(1)]
                    )

    def __extract_platforms(self, entry: str) -> [str]:
        # Form: 'getPlatforms(List.of("Windows"))'
        list_of_platforms = re.match('^\s*getPlatforms\((.*?)\)\s*$', entry)
        if list_of_platforms is None:
            return None
        entries_within_list = re.match("\s*List.of\((.*?)\)$|^\s*(null)$", list_of_platforms.group(1))
        if entries_within_list is None:
            return None
        return re.findall('"(.*?)"', entries_within_list.group(1))

    def __extract_programming_languages_modeling_languages(self, entry: str) -> [str]:
        """
        Extracts values from strings contained within "List.of(x)", whereby x represents the value we wish to extract.
        Following modeling tool attributes are returned: programming languages and modeling languages
        :param entry: string value which contains our sought for value
        :return: extracted value
        """
        list_of_entries = re.match("\s*(getProgrammingLanguages|getModelingLanguages)\(List.of\((.*?)\){2}$|^\s*(null)$",
                                   entry)

        return None if list_of_entries.group(0).strip() == "null" else (
            [x.replace('"', '') for x in list_of_entries.group(2).split(",")]
            if "," in list_of_entries.group(2)
            else [list_of_entries.group(2).replace('"', '')]
        )

    def get_name(self) -> str:
        return self.__name

    def get_link(self) -> str:
        return self.__link

    def get_open_source(self) -> bool:
        return self.__open_source

    def get_technologies(self) -> [str]:
        return self.__technologies

    def get_web_app(self) -> bool:
        return self.__web_app

    def get_desktop_app(self) -> bool:
        return self.__desktop_app

    def get_category(self) -> str:
        return self.__category

    def get_category_short(self) -> str:
        if self.__category == "GRAPHICAL_MODELING_TOOL":
            return "GMT"
        if self.__category == "BUSINESS_TOOL":
            return "BUT"
        if self.__category == "DRAWING_TOOL":
            return "DRT"
        if self.__category == "TEXT_BASED_MODELING_TOOL":
            return "TMT"
        if self.__category == "METAMODELING_TOOL":
            return 'MTT'
        if self.__category == "MIXED_TEXTUAL_AND_GRAPHICAL_MODELING_TOOL":
            return 'MTG'

    def get_modeling_languages(self) -> [str]:
        return self.__modeling_languages

    def get_source_code_generation(self) -> bool:
        return self.__source_code_generation

    def get_cloud_service(self) -> bool:
        return self.__cloud_service

    def get_license(self) -> str:
        return self.__license

    def get_license_short(self) -> str:
        if self.__license is None:
            return ""
        if self.__license == "FREE":
            return "FR"
        if self.__license == "COMMERCIAL":
            return "CO"
        if self.__license == "FREEMIUM" or self.__license == "RESTRICTED_FREE_AND_COMMERCIAL":
            return "FM"


    def get_login_required(self) -> bool:
        return self.__login_required

    def get_creators(self) -> [str]:
        return self.__creators

    def get_platforms(self) -> [str]:
        return self.__platforms

    def get_real_time_collab(self) -> bool:
        return self.__real_time_collab

    def get_programming_languages(self) -> [str]:
        return self.__programming_languages

    def __str__(self):
        return f"{self.__name}: {self.__category} | {self.__creators} | {self.__programming_languages}"


def read_modeling_tools() -> [ModelingTool]:
    """
    Reads all modeling tools from the previous project and extracts each attribute using regex-matching.
    :return: a list of Modeling Tools
    """
    modeling_tools: [str] = []
    with open(path, "r") as data_generator:
        file: str = data_generator.read()
        lines: [str] = file.splitlines()

        i: int = 0
        while i < len(lines):
            if "tools.add(" in lines[i]:
                mt_line = lines[i + 1].strip() + lines[i + 2].strip() + lines[i + 3].strip() + lines[i + 4].strip()
                mt_reg = re.match(
                    "new ModelingToolVerified\(\s*(.*?),\s*(.*?),\s*(.*?),\s*(List.of\(.*?\)|null),\s*(.*?),\s*(.*?),\s*(.*?),\s*(getModelingLanguages\(List.of\(.*?\)\)|null),\s*(.*?),\s*(.*?),\s*(.*?),\s*(.*?),\s*(List.of\(.*?\)|null),\s*(getPlatforms\(List.of\(.*?\)\)|null),\s*(.*?),\s*(getProgrammingLanguages\(List.of\(.*?\)\)|null)\)",
                    mt_line
                )
                mt = ModelingTool(mt_reg.group(1), mt_reg.group(2), mt_reg.group(3), mt_reg.group(4), mt_reg.group(5),
                                  mt_reg.group(6), mt_reg.group(7), mt_reg.group(8), mt_reg.group(9), mt_reg.group(10),
                                  mt_reg.group(11), mt_reg.group(12), mt_reg.group(13), mt_reg.group(14), mt_reg.group(15),
                                  mt_reg.group(16))
                modeling_tools.append(mt)
                i += 5
            i += 1

    return modeling_tools


def print_modeling_tools(modeling_tools: [ModelingTool]) -> None:
    """
    Prints all modeling tools, numbered.
    :param modeling_tools: list of modeling tools expected to be printed
    :return: modeling tools printed
    """
    for i, tool in enumerate(modeling_tools):
        print(f"{i + 1}. {tool.get_name()} {tool}")


def read_properties() -> (
        {str: ModelingLanguage},
        {str: Platform},
        {str: ProgrammingLanguage},
        {str: Technology},
        {str: Creator},
        {str: ModelingLanguage}
):
    """
    Reads all properties contained within the data-generator of the previous project.
    :return: properties structurally stored as Python objects
    """
    modeling_tools: [ModelingTool] = read_modeling_tools()
    modeling_languages: {str: ModelingLanguage} = {}
    platforms: {str: Platform} = {}
    programming_languages: {str: ProgrammingLanguage} = {}
    technologies: {str: Technology} = {"APP": Technology("APP", False, 1), "FRAMEWORK": Technology("FRAMEWORK", False, 2)}
    creators: {str: Creator} = {}

    reg_pattern = r'\s*%s [a-zA-Z0-9]+\s*=\s*new %s\("(.*?)",\s*(.*?)\)'
    with open(path, "r") as data_generator:
        file: str = data_generator.read()
        lines: [str] = file.splitlines()
        i: int = 0
        while i < len(lines):
            modeling_language_reg_groups = re.match(reg_pattern % ("ModelingLanguage", "ModelingLanguage"), lines[i])
            modeling_platform_reg_groups = re.match(reg_pattern % ("Platform", "Platform"), lines[i])
            modeling_programming_language_reg_groups = re.match(reg_pattern % ("ProgrammingLanguage", "ProgrammingLanguage"), lines[i])
            if modeling_language_reg_groups:
                modeling_language: ModelingLanguage = ModelingLanguage(
                    modeling_language_reg_groups.group(1),
                    True if modeling_language_reg_groups.group(2) == "true" else False,
                    len(modeling_languages) + 1
                )
                modeling_languages[modeling_language.name] = modeling_language
            elif modeling_platform_reg_groups:
                platform: Platform = Platform(
                    modeling_platform_reg_groups.group(1),
                    True if modeling_platform_reg_groups.group(2) == "true" else False,
                    len(platforms) + 1
                )
                platforms[platform.name] = platform
            elif modeling_programming_language_reg_groups:
                programming_language: ProgrammingLanguage = ProgrammingLanguage(
                    modeling_programming_language_reg_groups.group(1),
                    True if modeling_programming_language_reg_groups.group(2) == "true" else False,
                    len(programming_languages) + 1
                )
                programming_languages[programming_language.name] = programming_language
            i += 1

    for tool in modeling_tools:
        tool_creators: [str] = tool.get_creators()
        i = 0
        if tool_creators:
            while i < len(tool_creators):
                creator_added: bool = False
                for added_creator in creators:
                    if added_creator == tool_creators[i].replace("\"", "").strip():
                        creator_added = True
                        break
                if not creator_added:
                    creator: Creator = Creator(tool_creators[i].replace("\"", "").strip(), True, len(creators) + 1)
                    creators[creator.name] = creator
                i = i + 1

    return modeling_languages, platforms, programming_languages, technologies, creators, modeling_tools


def print_properties(
        modeling_languages: bool = True,
        platforms: bool = True,
        programming_languages: bool = True,
        technologies: bool = True,
        creators: bool = True
) -> None:
    """
    Prints all existing properties.
    :param modeling_languages: Specifies if modeling languages should be printed
    :param platforms: Specifies if platforms should be printed
    :param programming_languages: Specifies if programming languages should be printed
    :param technologies: Specifies if technologies should be printed
    :param creators: Specifies if creators should be printed
    :return: a printed list of properties
    """
    result = read_properties()
    if modeling_languages:
        print(" MODELING LANGUAGES (" + str(len(result[0])) + ")")
        for r in result[0]:
            print(f"{result[0][r].pk}. {r}")
    if platforms:
        print("\nPLATFORMS (" + str(len(result[1])) + ")")
        for r in result[1]:
            print(f"{result[1][r].pk}. {r}")
    if programming_languages:
        print("\nPROGRAMMING LANGUAGES (" + str(len(result[2])) + ")")
        for r in result[2]:
            print(f"{result[2][r].pk}. {r}")
    if technologies:
        print("\nTECHNOLOGIES (" + str(len(result[3])) + ")")
        for r in result[3]:
            print(f"{result[3][r].pk}. {r}")
    if creators:
        print("\nCREATORS (" + str(len(result[4])) + ")")
        for r in result[4]:
            print(f"{result[4][r].pk}. {r}")


def extract_property_pk(tool_properties: [str], properties: {str: Property}) -> [int]:
    """
    Extracts the corresponding primary key (pk) of a property based on the name of the property within tool_properties.
    :param tool_properties: Tool properties whose name are expected to be checked for the pk
    :param properties: Property objects containing the pk
    :return: a list of pk's
    """
    pks: [int] = []
    if tool_properties:
        for tool_prop in tool_properties:
            # TODO: Decide on whether LIBRARY should be permanently omitted
            if tool_prop == "LIBRARY":
                pks.append(2)
            else:
                prop = properties[tool_prop.strip().replace('"', '')]
                pks.append(prop.pk)
        return pks
    return []


def generate_all_data_json() -> None:
    """
    Key method in this file, generates the JSON output later used as a fixture by Django.
    :return: generates a JSON file
    """
    properties = read_properties()
    modeling_languages: {str: ModelingLanguage} = properties[0]
    platforms: {str: Platform} = properties[1]
    programming_languages: {str: Platform} = properties[2]
    technologies: {str: Platform} = properties[3]
    creators: {str: Platform} = properties[4]
    modeling_tools: {str: ModelingTool} = properties[5]

    # Output path
    output_path: str = 'apps/app/fixtures/all_data_generated.json'

    with open(output_path, "w+") as f:
        f.write("[\n")
        for ml in modeling_languages:
            f.write('  {"model": "app.modelinglanguage", "pk": ' + str(modeling_languages[ml].pk) + ', "fields": {"name": "' + ml + '", "deletable": ' + str(modeling_languages[ml].deletable).lower() + '}},\n')
        f.write("\n")
        for platform in platforms:
            f.write('  {"model": "app.platform", "pk": ' + str(platforms[platform].pk) + ', "fields": {"name": "' + platform + '", "deletable": ' + str(platforms[platform].deletable).lower() + '}},\n')
        f.write("\n")
        for pl in programming_languages:
            f.write('  {"model": "app.programminglanguage", "pk": ' + str(programming_languages[pl].pk) + ', "fields": {"name": "' + pl + '", "deletable": ' + str(programming_languages[pl].deletable).lower() + '}},\n')
        f.write("\n")
        for technology in technologies:
            f.write('  {"model": "app.technology", "pk": ' + str(technologies[technology].pk) + ', "fields": {"name": "' + technology + '", "deletable": ' + str(technologies[technology].deletable).lower() + '}},\n')
        f.write("\n")
        for creator in creators:
            f.write('  {"model": "app.creator", "pk": ' + str(creators[creator].pk) + ', "fields": {"name": "' + creator + '", "deletable": ' + str(creators[creator].deletable).lower() + '}},\n')
        f.write("\n")
        for counter, tool in enumerate(modeling_tools):
            f.write('  {"model": "app.modelingtool", "pk": ' + str(counter + 1) + ', "fields": {"name": "' + tool.get_name() + '", "link": ' + tool.get_link() + ', "open_source": ' + extract_boolean_value(tool.get_open_source()) + ', "technology": ' + str(extract_property_pk(tool.get_technologies(), technologies)) + ', "web_app": ' + extract_boolean_value(tool.get_web_app()) + ', "desktop_app": ' + extract_boolean_value(tool.get_desktop_app()) + ', "category": "' + tool.get_category_short() + '", "modeling_languages": ' + str(extract_property_pk(tool.get_modeling_languages(), modeling_languages)) + ', "source_code_generation": ' + extract_boolean_value(tool.get_source_code_generation()) + ', "cloud_service": ' + extract_boolean_value(tool.get_cloud_service()) + ', "license": ' + extract_json_null_value(tool.get_license_short()) + ', "login_required": ' + extract_boolean_value(tool.get_login_required()) + ', "creators": ' + str(extract_property_pk(tool.get_creators(), creators)) + ', "platforms": ' + str(extract_property_pk(tool.get_platforms(), platforms)) + ', "real_time_collab": ' + extract_boolean_value(tool.get_real_time_collab()) + ', "programming_languages": ' + str(extract_property_pk(tool.get_programming_languages(), programming_languages)) + '}}')
            if counter + 1 != len(modeling_tools):
                f.write(",\n")
        f.write("\n")
        f.write("]")
    print(f"Generated new fixture \"{output_path}\"")


def extract_json_null_value(value: str):
    """
    Extracts the null value as a string in a JSON-appropriate format, otherwise return the value back but surrounded
    with quotation marks.
    :param value: value that is checked if it is null or empty
    :return: null of type JSON, else the initial value surrounded by strings
    """
    return "null" if value is None or len(value) == 0 else f'"{value}"'


def extract_boolean_value(value: bool) -> str:
    """
    Extracts values into a JSON-appropriate value.
    :param value: boolean value
    :return: JSON-appropriate value
    """
    return "null" if value is None else str(value).lower()


# print_modeling_tools(read_modeling_tools())
# print_properties()
generate_all_data_json()
