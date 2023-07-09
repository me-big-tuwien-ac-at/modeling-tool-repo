import os.path
from typing import Tuple

import numpy as np
import matplotlib.pyplot as plt

from generators.modeling_tool_data_generator_reader import ModelingTool


def generate_bool_graph(modeling_tools: [ModelingTool]):
    yes_no_counter: [[int]] = count_yes_no_properties(modeling_tools)

    N = 5
    # OpenSource, WebApp, DesktopApp, LoginRequired, RealTimeCollaboration
    true_values: Tuple[int, int, int, int, int] = tuple(yes_no_counter[0])
    false_values: Tuple[int, int, int, int, int] = tuple(yes_no_counter[1])
    ind = np.arange(N)  # the x locations for the groups
    width = 0.35
    fig = plt.figure()
    ax = fig.add_axes([0, 0, 1, 1])
    ax.bar(ind, true_values, width, color='#32de84')
    ax.bar(ind, false_values, width, bottom=true_values, color='r')
    ax.set_ylabel('Modeling Tools')
    ax.set_title('Modeling Tools based on yes/no properties')
    ax.set_xticks(ind, ('Open\nSource', 'Web\nApp', 'Desktop\nApp', 'Login\nRequired', 'Real Time\nCollaboration'))
    ax.set_yticks(np.arange(0, 92, 10))
    ax.legend(labels=['True', 'False'])

    # Add values
    for i in range(len(max(true_values, false_values))):
        # idk, idk, value, text position
        plt.text(i, true_values[i] - 3, true_values[i], ha='center')
        plt.text(i, 69 - 3, false_values[i], ha='center')

    # Save and display the figure
    if not os.path.isdir('./graphics'):
        os.makedirs('./graphics')
    plt.savefig('./graphics/modeling_tool_yes_no_bar_graph.png', bbox_inches='tight')
    plt.show()


def count_yes_no_properties(modeling_tools: [ModelingTool]) -> [[int, int, int, int, int], [int, int, int, int, int]]:
    # Open Source, Web App, Desktop App, Login Required, Real Time Collaboration
    bool_counter: [[int]] = [[0, 0, 0, 0, 0], [0, 0, 0, 0, 0]]

    def increment_counter(value: bool, counter: [[int]], section: int):
        if value is not None:
            if value:
                counter[0][section] = counter[0][section] + 1
            else:
                counter[1][section] = counter[1][section] + 1

    for tool in modeling_tools:
        increment_counter(tool.get_open_source(), bool_counter, 0)
        increment_counter(tool.get_web_app(), bool_counter, 1)
        increment_counter(tool.get_desktop_app(), bool_counter, 2)
        increment_counter(tool.get_login_required(), bool_counter, 3)
        increment_counter(tool.get_real_time_collab(), bool_counter, 4)

    return bool_counter
