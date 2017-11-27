import matplotlib.pyplot as plt
from math import exp, sqrt, pi
import sys


def gauss(value, expected, variance):
    return exp((-(value - expected)**2)/(2 * variance))/sqrt(2 * pi * variance)


def uniform(start, end):
    return 1/(end - start + 1)


def discrete(value, point):
    return value == point


def plot_model(expected):
    plt.plot([0.6400683399434086 * gauss(i, expected, 9.02790308154548) for i in range(256)])
    plt.plot([0.3227144026058709 * uniform(0, 255) for i in range(256)])
    plt.plot([0.037217257450720485 * discrete(i, 255) for i in range(256)])
    plt.xlim(0, 260)

    plt.show()


def plot_histogram():
    measurement_list = []
    with open(sys.argv[1]) as measurements_file:
        for line in measurements_file:
            split_line = line.split(',')
            measurement_list.append(float(split_line[4]) - float(split_line[5]))

    plt.xlim(-100, 260)
    plt.grid(.5)
    plt.hist(measurement_list)
    plt.show()


if len(sys.argv) > 1:
    plot_histogram()
else:
    plot_model(60)
