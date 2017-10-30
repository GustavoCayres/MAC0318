import sys
import math


def polar2cartesian(x, y, t, r, delta_t):
    point_x = x + r * math.cos(math.radians(t + delta_t))
    point_y = y + r * math.sin(math.radians(t + delta_t))

    return point_x, point_y


def points2lines(points):
    return points2lines_rec(0, len(points) - 1, points)


def points2lines_rec(i_a, i_b, points):
    threshold = 100
    lines = []
    for i_p in range(i_a, i_b + 1):
        if distance2line(i_a, i_b, i_p, points) > threshold:
            lines += points2lines_rec(i_a, i_p, points)
            lines += points2lines_rec(i_p, i_b, points)
            break

    if len(lines) == 0:
        lines.append((points[i_a], points[i_b]))

    return lines


def distance2line(i_a, i_b, i_p, points):
    x_a, y_a = points[i_a]
    x_b, y_b = points[i_b]
    x_p, y_p = points[i_p]
    numerator = abs((y_b - y_a) * x_p - (x_b - x_a) * y_p + (x_b * y_a) - (y_b * x_a))
    denominator = math.sqrt((y_b - y_a) ** 2 + (x_b - x_a) ** 2)
    return numerator / denominator


def main():
    cartesian_points = []
    with open(sys.argv[1], 'r') as f:
        for line in f:
            values = line.split()

            x, y, theta = float(values[0]), float(values[1]), float(values[2])
            readings = values[3:]
            for i, reading in enumerate(readings):
                if float(reading) < 255:
                    cartesian_points.append(polar2cartesian(x, y, theta, float(reading), -90 + (2 * i)))
            lines = points2lines(cartesian_points)
            for l in lines:
                print("new Line(" + str(int(l[0][0]) + 250) + "," + str(int(l[0][1]) + 250) + "," + str(
                    int(l[1][0]) + 250) + "," + str(int(l[1][1]) + 250) + "),")


if __name__ == "__main__":
    main()
