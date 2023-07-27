import os
from typing import Tuple
from generators.modeling_tool_data_generator_reader import ModelingTool

import numpy as np
import matplotlib.pyplot as plt


def generate_categories_graph(modeling_tools: [ModelingTool]):
    category_counter = count_categories(modeling_tools)

    # GRAPHICAL_MODELING_TOOL, BUSINESS_TOOL, DRAWING_TOOL, TEXT_BASED_MODELING_TOOL, METAMODELING_TOOL,
    # MIXED_TEXTUAL_AND_GRAPHICAL_MODELING_TOOL
    licenses: Tuple[int, int, int, int, int, int] = tuple(category_counter)
    license_colors: Tuple[str, str, str, str, str, str] = ('green', 'teal', 'blue', 'purple', 'orange', 'red')

    # the x locations for the groups
    N: int = len(licenses)
    ind = np.arange(N)
    width = 0.35
    fig = plt.figure()
    ax = fig.add_axes([0, 0, 1, 1])
    ax.bar(ind, licenses, width, color=license_colors)
    ax.set_ylabel('Modeling Tools')
    ax.set_title('Modeling Tool Categories')
    # ax.set_xticks(ind, ("Graphical\nTool", "Business\nTool", "Drawing\nTool", "Metamodeling\nTool",
    #                    "Mixed Textual\nandGraphical\nModeling Tool", "Text-based\nModeling Tool",))

    ax.set_xticks(ind, ("Graphical\nTool", "Business\nTool", "Drawing\nTool", "Text-based\nModeling Tool",
                        "Metamodeling\nTool", "Mixed Textual\nand Graphical\nModeling Tool",))
    ax.set_yticks(np.arange(0, 40, 10))

    # Add values
    for i in range(len(licenses)):
        # idk, idk, value, text position
        plt.text(i, licenses[i] - 1.5, licenses[i], ha='center', color="white")

    # Save and display the figure
    if not os.path.isdir('./graphics'):
        os.makedirs('./graphics')
    plt.savefig('./graphics/modeling_tool_categories_bar_graph.png', bbox_inches='tight')
    plt.show()


def count_categories(modeling_tools: [ModelingTool]) -> [int, int, int, int, int, int]:
    category_counter: [int] = [0, 0, 0, 0, 0, 0]
    for tool in modeling_tools:
        category: str = tool.get_category()
        if category == "GRAPHICAL_MODELING_TOOL":
            category_counter[0] = category_counter[0] + 1
        elif category == "BUSINESS_TOOL":
            category_counter[1] = category_counter[1] + 1
        elif category == "DRAWING_TOOL":
            category_counter[2] = category_counter[2] + 1
        elif category == "TEXT_BASED_MODELING_TOOL":
            category_counter[3] = category_counter[3] + 1
        elif category == "METAMODELING_TOOL":
            category_counter[4] = category_counter[4] + 1
        elif category == "MIXED_TEXTUAL_AND_GRAPHICAL_MODELING_TOOL":
            category_counter[5] = category_counter[5] + 1
    return category_counter
