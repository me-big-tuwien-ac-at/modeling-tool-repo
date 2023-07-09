import os

from generators.boolean_statistics import generate_bool_graph
from generators.categories import generate_categories_graph
from generators.license import generate_licenses_graph
from generators.modeling_tool_data_generator_reader import ModelingTool, read_modeling_tools
from generators.technology import generate_technologies_graph


if __name__ == "__main__":
    modeling_tools: [ModelingTool] = read_modeling_tools()

    generate_bool_graph(modeling_tools)
    generate_categories_graph(modeling_tools)
    generate_licenses_graph(modeling_tools)
    generate_technologies_graph(modeling_tools)

    os.getcwd()

    print('Generated statistics for:\n  * True/False Values\n  * Categories\n  * Licenses\n  * Technologies')
    print(f'Output can be found in {os.getcwd()}\graphics')
