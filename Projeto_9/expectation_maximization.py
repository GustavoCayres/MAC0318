import sys
from math import log, fabs, pi, sqrt, exp


def gauss(value, expected, variance):
    return exp((-(value - expected)**2)/(2 * variance))/sqrt(2 * pi * variance)


def uniform(start, end):
    return 1/(end - start + 1)


def discrete(value, point):
    return value == point


def expectation_maximization(w_object, w_others, w_lim, var_object, measurements, threshold):
    old_theta = threshold  # guarantees it will iterate at least once
    while True:
        sum_e_object = 0
        sum_e_object_times_error = 0
        sum_e_others = 0
        sum_e_lim = 0
        theta = 0
        for z, z_esp in measurements:
            p_object = gauss(z, z_esp, var_object)
            p_others = uniform(0, 255)
            p_lim = discrete(z, 255)

            n = 1/(p_object + p_others + p_lim)
            sum_e_object += n * p_object
            sum_e_object_times_error += n * p_object * ((z - z_esp)**2)
            sum_e_others += n * p_others
            sum_e_lim += n * p_lim

            theta += log(w_object * p_object + w_others * p_others + w_lim * p_lim)

        w_object = sum_e_object/len(measurements)
        w_others = sum_e_others/len(measurements)
        w_lim = sum_e_lim/len(measurements)
        var_object = sum_e_object_times_error/sum_e_object

        if fabs(theta - old_theta) < threshold:
            break

        old_theta = theta

    return w_object, w_others, w_lim, var_object


measurement_list = []
with open(sys.argv[1]) as measurements_file:
    for line in measurements_file:
        split_line = line.split(',')
        measurement_list.append((float(split_line[4]), float(split_line[5])))

print(expectation_maximization(w_object=.8,
                               w_others=.15,
                               w_lim=.05,
                               var_object=3,
                               threshold=.0000001,
                               measurements=measurement_list))
