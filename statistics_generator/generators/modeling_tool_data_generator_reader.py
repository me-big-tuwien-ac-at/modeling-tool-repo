import re

path: str = r'../backend/src/main/java/com/example/modeling_tools/datagenerator/ModelingToolDataGenerator.java'


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
        self.__platforms = platforms
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
            return ([x for x in list_of_entries.group(1).split(",")]
                    if "," in list_of_entries.group(1)
                    else [list_of_entries.group(1)]
                    )

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

    def get_license(self) -> str:
        return self.__license

    def get_login_required(self) -> bool:
        return self.__login_required

    def get_real_time_collab(self) -> bool:
        return self.__real_time_collab

    def __str__(self):
        return f"{self.__name}: {self.__category} | {self.__creators} | {self.__programming_languages}"


def read_modeling_tools() -> [ModelingTool]:
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


def print_modeling_tools(modeling_tools: [ModelingTool]):
    for i, tool in modeling_tools:
        print(f"{i}. {tool.get_name()} {tool}")
