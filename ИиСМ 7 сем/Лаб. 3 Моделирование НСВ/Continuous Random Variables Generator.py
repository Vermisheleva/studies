import MultiplicativeCongruentalGenerator as mcg
import Tests
import math
import matplotlib.pyplot as plt
import numpy as np
from scipy.stats import norm, logistic, laplace


class NormalVariableGenerator:
    def __init__(self, mean, sigma, num, gen):
        self.mean = mean
        self.sigma = sigma
        self.num = num
        self.gen = gen

    def next_element(self):
        norm_var = sum(self.gen.next_elem() for _ in range(self.num))
        return (norm_var - 6) * self.sigma + self.mean

    def exp_mean(self):
        return self.mean

    def exp_variance(self):
        return self.sigma ** 2

    def cdf(self, x):
        return norm.cdf(x, loc=self.mean, scale=self.sigma)


class LogisticVariableGenerator:
    def __init__(self, mu, k, gen):
        self.mu = mu
        self.k = k
        self.gen = gen

    def next_element(self):
        y = self.gen.next_elem()
        return self.mu + self.k * math.log(y / (1 - y))

    def exp_mean(self):
        return self.mu

    def exp_variance(self):
        return ((self.k * math.pi) ** 2) / 3

    def cdf(self, x):
        return logistic.cdf(x, loc=self.mu, scale=self.k)


class LaplaceVariableGenerator:
    def __init__(self, p, gen):
        self.p = p
        self.gen = gen

    def next_element(self):
        y = self.gen.next_elem()
        return math.log(2 * y) / self.p if y < 0.5 else - math.log(2 - 2 * y) / self.p

    @staticmethod
    def exp_mean():
        return 0

    def exp_variance(self):
        return 2 / (self.p ** 2)

    def cdf(self, x):
        return laplace.cdf(x, loc=0, scale=1/self.p)


class CRVSequence:
    def __init__(self, generator, seq_len, test_times, distribution, e):
        self.generator = generator
        self.seq_len = seq_len
        self.test_times = test_times
        self.e = e
        self.cdf = generator.cdf
        self.distribution = distribution
        self.seq = self.generate_sequence()

    def generate_sequence(self):
        return [self.generator.next_element() for _ in range(self.seq_len)]

    def est_mean(self):
        return sum(self.seq[i] for i in range(self.seq_len)) / self.seq_len

    def est_variance(self):
        mean = self.est_mean()
        return sum((self.seq[i] - mean) ** 2 for i in range(self.seq_len)) / (self.seq_len + 1)

    def tests(self):
        tests = Tests.Tests(self.e, self.cdf, self.distribution)
        tests.chisquare_test(self.seq, True)
        tests.ks_test(self.seq, True)
        self.type1error(tests)

    def type1error(self, tests):
        xi_res = 0
        k_res = 0
        for _ in range(self.test_times):
            temp = self.generate_sequence()
            xi_res += tests.chisquare_test(temp, False)
            k_res += tests.ks_test(temp, False)

        xi_error = 1 - (xi_res / self.test_times)
        k_error = 1 - (k_res / self.test_times)
        print(f"Вероятность ошибки первого рода для СВ из {self.distribution}, критерий хи-квадрат: {xi_error}")
        print(f"Вероятность ошибки первого рода для СВ из {self.distribution}, критерий Колмогорова: {k_error}")

    def histogram(self):
        plt.hist(self.seq)
        max_el = np.max(self.seq)
        min_el = np.min(self.seq)
        values = np.arange(min_el, max_el + 1)
        plt.xticks(values, labels=np.round(values))
        plt.title(f"Гистограмма для СВ из {self.distribution}")
        plt.show()

    def scatter_plot(self):
        plt.plot(np.arange(len(self.seq)), self.seq, 'ro')
        plt.title(f"Диаграмма рассеяния для СВ из {self.distribution}")
        plt.show()

    def print_results(self):
        print(f"Сгенерированная последовательность из {self.distribution}:")
        print(*self.seq)
        print("Среднее: ")
        print(f"Несмещенная оценка: {self.est_mean()}, истинное значение: {self.generator.exp_mean()}, "
              f"разность: {abs(self.generator.exp_mean() - self.est_mean())}")
        print("Дисперсия: ")
        print(f"Несмещенная оценка: {self.est_variance()}, истинное значение: {self.generator.exp_variance()},"
              f" разность: {abs(self.generator.exp_variance() - self.est_variance())}")

        self.histogram()
        self.scatter_plot()
        self.tests()


n = 1000
times = 1000
beta = 78121
beta3 = 78117
m = 2 ** 31
eps = 0.05
gen = mcg.MultiplicativeCongruentialMethod(beta, beta, m)
gen3 = mcg.MultiplicativeCongruentialMethod(beta3, beta3, m)
mean = 0
sigma = 1
norm_gen = NormalVariableGenerator(mean, sigma, 12, gen)
norm_seq = CRVSequence(norm_gen, n, times, f"N({mean}, {sigma**2})", eps)
norm_seq.print_results()
print()
a = 2
laplace_gen = LaplaceVariableGenerator(a, gen)
laplace_seq = CRVSequence(laplace_gen, n, times, f"L({a})", eps)
laplace_seq.print_results()
print()
mu = 2
k = 3
lg_gen = LogisticVariableGenerator(mu, k, gen3)
lg_seq = CRVSequence(lg_gen, n, times, f"LG({mu}, {k})", eps)
lg_seq.print_results()



