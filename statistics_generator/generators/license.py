import os
from typing import Tuple
from generators.modeling_tool_data_generator_reader import ModelingTool

import numpy as np
import matplotlib.pyplot as plt


def generate_licenses_graph(modeling_tools: [ModelingTool]):
    license_counter: [int] = count_licenses(modeling_tools)

    # Free, Freemium, Commercial
    licenses: Tuple[int, int, int, int] = tuple(license_counter)
    license_colors: Tuple[str, str, str, str] = ('yellow', 'orange', 'red', '#989898')

    # the x locations for the groups
    N: int = len(licenses)
    ind = np.arange(N)
    width = 0.35
    fig = plt.figure()
    ax = fig.add_axes([0, 0, 1, 1])
    ax.bar(ind, licenses, width, color=license_colors)
    ax.set_ylabel('Modeling Tools')
    ax.set_title('Modeling Tool Licenses')
    ax.set_xticks(ind, ("Free", "Restricted free content,\ncommercial", "Commercial", "Unknown"))
    ax.set_yticks(np.arange(0, 40, 10))

    # Add values
    for i in range(len(licenses)):
        # idk, idk, value, text position
        if licenses[i] > 10:
            plt.text(i, licenses[i] - 1.5, licenses[i], ha='center')
        else:
            plt.text(i, licenses[i] + 0.5, licenses[i], ha='center')

    # Save and display the figure
    if not os.path.isdir('./graphics'):
        os.makedirs('./graphics')
    plt.savefig('./graphics/modeling_tool_licenses_bar_graph.png', bbox_inches='tight')
    plt.show()


def count_licenses(modeling_tools: [ModelingTool]) -> [int, int, int, int]:
    license_counter: [int] = [0, 0, 0, 0]
    for tool in modeling_tools:
        license: str = tool.get_license()
        if license == "FREE":
            license_counter[0] = license_counter[0] + 1
        elif license == "RESTRICTED_FREE_AND_COMMERCIAL":
            license_counter[1] = license_counter[1] + 1
        elif license == "COMMERCIAL":
            license_counter[2] = license_counter[2] + 1
        else:
            license_counter[3] = license_counter[3] + 1
    return license_counter
