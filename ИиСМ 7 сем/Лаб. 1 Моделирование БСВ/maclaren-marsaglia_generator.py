import math
import numpy as np
from scipy.stats import chi2, kstest, uniform
import matplotlib.pyplot as plt


class MultiplicativeCongruentMethod:
    def __init__(self, a, b, m):
        self.prev_el = a
        self.beta = b
        self.M = m

    def next_element(self):
        z = self.beta * self.prev_el
        self.prev_el = z - self.M * int(z / self.M)
        return self.prev_el / self.M

    def generate_n(self, num_el):
        return [self.next_element() for _ in range(num_el)]


class MultiplicativeCongruentialGenerator:
    def __init__(self, size, beta, alpha0, m):
        self.size = size
        self.beta = beta
        self.alpha0 = alpha0
        self.m = m
        self.seq = np.zeros(size)
        self.prev = alpha0

    def generate_sequence(self):
        for i in range(self.size):
            z = self.beta * self.prev
            self.prev = z - self.m * int(z / self.m)
            self.seq[i] = self.prev / self.m

    def print_seq(self):
        print(f"Реализация БСВ мультипликативным конгруэнтным методом при beta = {self.beta}, alpha0 = {self.alpha0}, m = {self.m}: ")
        print(self.seq)


class MacLarenMarsagliaGenerator:
    def __init__(self, n, k):
        self.n = n
        self.k = k
        self.alpha = np.zeros(n)

    def maclaren_marsaglia_method(self, c, b):
        # len(c) = n, len(b) = n + k
        v = b[:self.k]
        for i in range(self.n):
            s = int(c[i] * self.k)
            self.alpha[i] = v[s]
            v[s] = b[i + self.k]

    def print_seq(self):
        print(f"Реализация БСВ методом МакЛарена Марсальи, k = {self.k}: ", '\n', self.alpha)


class StatTests:
    def __init__(self, seq, e, name):
        self.seq = seq
        self.e = e
        self.name = name
        self.intervals = self.find_intervals()

    def find_intervals(self):
        n = 1 + int(math.log2(len(self.seq)))
        max_el = np.max(self.seq)
        min_el = np.min(self.seq)
        h = (max_el - min_el) / n
        a = np.zeros(n)
        for i in range(n - 1):
            a[i] = min_el + (i + 1) * h
        a[-1] = 1
        return a

    def observed_frequencies(self, seq):
        n = len(self.intervals)
        freq = np.zeros(n)
        sort_seq = np.sort(seq)
        i = 0
        k = 0
        while i < n:
            while k < len(seq) and sort_seq[k] < self.intervals[i]:
                freq[i] += 1
                k += 1
            i += 1
        return freq

    def expected_frequencies(self):
        n = len(self.intervals)
        exp_freq = np.zeros(n)
        exp_freq[0] = uniform.cdf(self.intervals[0])
        for i in range(1, n - 1):
            exp_freq[i] = uniform.cdf(self.intervals[i]) - uniform.cdf(self.intervals[i - 1])
        exp_freq[-1] = 1 - uniform.cdf(self.intervals[-2])
        return exp_freq

    def chisquare_test(self):
        print("Критерий хи-квадрат: ")
        obs_freq = self.observed_frequencies(self.seq)
        exp_freq = self.expected_frequencies()
        k = len(self.intervals)
        n = len(self.seq)
        stats = sum(((obs_freq[i] - n * exp_freq[i]) ** 2) / n * exp_freq[i] for i in range(k))
        critical_value = chi2.ppf(self.e, k)
        print(f"Значение статистики хи квадрат: {stats}", f", критическое значение: {critical_value}")
        if stats < critical_value:
            print(f"Гипотеза о равномерном распределении принимается с уровнем значимости eps = {self.e}")
        else:
            print(f"Гипотеза о равномерном распределении отклоняется с уровнем значимости eps = {self.e}")

    def ks_test(self):
        print("Критерий согласия Колмогорова: ")
        stats = kstest(self.seq, 'uniform')
        print(stats)
        if stats[1] >= self.e:
            print(f"Гипотеза о равномерном распределении принимается с уровнем значимости eps = {self.e}")
        else:
            print(f"Гипотеза о равномерном распределении отклоняется с уровнем значимости eps = {self.e}")

    def histogram(self):
        plt.hist(self.seq, bins=len(self.intervals))
        plt.xticks(self.intervals, labels=np.round(self.intervals, 2))
        plt.title(f"Гистограмма: {self.name}")
        plt.show()

    def scatter_plot(self):
        plt.plot(np.arange(len(self.seq)), self.seq, 'ro')
        plt.yticks(self.intervals, labels=np.round(self.intervals, 2))
        plt.title(f"Диаграмма рассеяния: {self.name}")
        plt.show()

    def standart_tests(self):
        self.ks_test()
        self.chisquare_test()
        self.histogram()
        self.scatter_plot()

a1 = 78125
#a2 = 12167
a2 = 32771
#a2 = 29537
n = 1000
k = 256
m = 2 ** 31
mcg1 = MultiplicativeCongruentialGenerator(n, a1, a1, m)
mcg1.generate_sequence()
mcg1.print_seq()
mcg2 = MultiplicativeCongruentialGenerator(n + k, a2, a2, m)
mcg2.generate_sequence()
mmg = MacLarenMarsagliaGenerator(1000, 256)
mmg.maclaren_marsaglia_method(mcg1.seq, mcg2.seq)
mmg.print_seq()
print()
eps = 0.05
print("Тесты для реализации мультипликативным конгруэнтным методом:")
st_test1 = StatTests(mcg1.seq, eps, "Мультипикативный конгруэнтный метод")
st_test1.ks_test()
st_test1.chisquare_test()
st_test1.histogram()
st_test1.scatter_plot()
print()
print("Тесты для реализации методом МакЛарена Марсальи:")
st_test2 = StatTests(mmg.alpha, eps, "Метод МакЛарена Марсальи")
st_test2.ks_test()
st_test2.chisquare_test()
st_test2.histogram()
st_test2.scatter_plot()
