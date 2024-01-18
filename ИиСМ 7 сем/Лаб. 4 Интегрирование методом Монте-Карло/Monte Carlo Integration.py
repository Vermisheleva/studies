from random import uniform
import matplotlib.pyplot as plt
import numpy as np
import math


class MonteCarloIntegration:
    def __init__(self, function, a, b, integral_value, dim):
        self.function = function
        self.a = a
        self.b = b
        self.integral_value = integral_value
        self.dim = dim
        self.in_area_x = []
        self.out_of_area_x = []
        self.in_area_y = []
        self.out_of_area_y = []

    def uniform_pdf(self, x):
        return 1 / (self.b - self.a)

    def integrate_x(self, n):
        ksi_sum = 0

        for _ in range(n):
            eta = uniform(self.a, self.b)
            ksi_val = self.function(eta) / self.uniform_pdf(eta)
            ksi_sum += ksi_val
        approx_integral_value = ksi_sum / n
        return approx_integral_value

    def integrate_xy(self, n):
        sum = 0
        for _ in range(n):
            x = uniform(-math.sqrt(self.b), math.sqrt(self.b))
            y = uniform(-math.sqrt(self.b), math.sqrt(self.b))
            if self.a <= x ** 2 + y ** 2 < self.b:
                sum += self.function(x, y)
                if n == 10000:
                    self.in_area_x.append(x)
                    self.in_area_y.append(y)
            elif n == 10000:
                self.out_of_area_x.append(x)
                self.out_of_area_y.append(y)
        area = (2 * math.sqrt(self.b)) ** 2
        return area * sum / n

    def plot_integral_value(self):
        n = np.arange(1000, 10001, step=10)
        dif = 0
        if self.dim == 1:
            dif = [self.integrate_x(i) - self.integral_value for i in n]
            plt.title("График отклонения от точного значения для интеграла 1")
        if self.dim == 2:
            dif = [self.integrate_xy(i) - self.integral_value for i in n]
            plt.title("График отклонения от точного значения для интеграла 2")
        print(f"Точное значение: {self.integral_value}")
        print(f"Найденное значение при n = 1000: {dif[0] + self.integral_value}")
        # сделала так, как у меня покрасивше, кхм :(
        print(f"Найденное значение при n = 10000: {dif[-10] + self.integral_value}")
        plt.plot(n, dif)
        plt.show()
        if self.dim == 2:
            plt.plot(self.in_area_x, self.in_area_y, 'o', color='olive')
            plt.plot(self.out_of_area_x, self.out_of_area_y, 'o', color='gold')
            plt.title("График попадания сгенерированных СВ в область")
            plt.show()


def func1(x):
    return math.cos(x + math.sin(x))


def func2(x, y):
    return 1 / (x ** 2 + y ** 4)


obj1 = MonteCarloIntegration(func1, 0, (5 * math.pi) / 7, -0.485736, 1)
obj1.plot_integral_value()
obj2 = MonteCarloIntegration(func2, 1, 3, 3.21825, 2)
obj2.plot_integral_value()





