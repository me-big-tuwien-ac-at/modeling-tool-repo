import os
from typing import Tuple
from generators.modeling_tool_data_generator_reader import ModelingTool

import numpy as np
import matplotlib.pyplot as plt


def generate_technologies_graph(modeling_tools: [ModelingTool]):
    tech_counter: [int] = count_technologies(modeling_tools)

    # Free, mix, commercial
    technologies: Tuple[int, int, int] = tuple(tech_counter)
    technology_colors: Tuple[str, str, str] = ('#8098c7', '#00308F', '#001848')

    # the x locations for the groups
    N: int = len(technologies)
    ind = np.arange(N)
    width = 0.35
    fig = plt.figure()
    ax = fig.add_axes([0, 0, 1, 1])
    ax.bar(ind, technologies, width, color=technology_colors)
    ax.set_ylabel('Modeling Tools')
    ax.set_title('Modeling Tool Technologies')
    ax.set_xticks(ind, ("App", "Framework", "Library"))
    ax.set_yticks(np.arange(0, 60, 10))

    # Add values
    for i in range(len(technologies)):
        # idk, idk, value, text position
        plt.text(i, technologies[i] - 2, technologies[i], ha='center', color="white")

    # Save and display the figure
    # Save and display the figure
    if not os.path.isdir('./graphics'):
        os.makedirs('./graphics')
    plt.savefig('./graphics/modeling_tool_technology_bar_graph.png', bbox_inches='tight')
    plt.show()


def count_technologies(modeling_tools: [ModelingTool]) -> [int, int, int]:
    tech_counter: [int] = [0, 0, 0]
    for tool in modeling_tools:
        for tech in tool.get_technologies():
            if tech == "APP":
                tech_counter[0] = tech_counter[0] + 1
            elif tech == "FRAMEWORK":
                tech_counter[1] = tech_counter[1] + 1
            elif tech == "LIBRARY":
                tech_counter[2] = tech_counter[2] + 1
    return tech_counter
