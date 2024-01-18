import math
import numpy as np
import MultiplicativeCongruentalGenerator as mcg
import ChiSquareTest as Chi2Test
import matplotlib.pyplot as plt


class BernoulliDRV:
    def __init__(self, p, mcs):
        self.p = p
        self.mcs = mcs

    def generate_drv(self):
        a = self.mcs.next_elem()
        return 1 if a <= self.p else 0


class GeometricDRV:
    def __init__(self, p, mcs):
        self.q = 1 - p
        self.mcs = mcs

    def generate_drv(self):
        a = self.mcs.next_elem()
        return math.ceil(math.log(a) / math.log(self.q))


class DRVSequence:
    def __init__(self, n, dist, p, mcm):
        self.n = n
        self.seq = np.zeros(n)
        self.distribution = dist
        self.p = p
        self.mcs = mcm

    def generate_sequence(self):
        if self.distribution == "Bi":
            generator = BernoulliDRV(self.p, self.mcs)
            self.seq = [generator.generate_drv() for elem in self.seq]
        if self.distribution == "G":
            generator = GeometricDRV(self.p, self.mcs)
            self.seq = [generator.generate_drv() for elem in self.seq]

    def exp_mean(self):
        if self.distribution == "G":
            return 1 / self.p
        if self.distribution == "Bi":
            return self.p

    def exp_variance(self):
        if self.distribution == "G":
            return (1 - self.p) / (self.p ** 2)
        if self.distribution == "Bi":
            return self.p * (1 - self.p)

    def est_mean(self):
        return sum(self.seq[i] for i in range(self.n)) / self.n

    def est_variance(self):
        mean = self.est_mean()
        return sum((self.seq[i] - mean) ** 2 for i in range(self.n)) / (self.n + 1)

    def print_seq(self):
        print(f"Сгенерированная последовательность из {self.distribution}({self.p})")
        print(*self.seq)
        print("Среднее: ")
        print(f"Несмещенная оценка: {self.est_mean()}, истинное значение: {self.exp_mean()}, "
              f"разность: {abs(self.exp_mean() - self.est_mean())}")
        print("Дисперсия: ")
        print(f"Несмещенная оценка: {self.est_variance()}, истинное значение: {self.exp_variance()},"
              f" разность: {abs(self.exp_variance() - self.est_variance())}")

    def histogram(self):
        plt.hist(self.seq)
        max_el = np.max(self.seq)
        min_el = np.min(self.seq)
        values = np.arange(min_el, max_el + 1)
        plt.xticks(values, labels=values)
        plt.title(f"Гистограмма для ДСВ из {self.distribution}")
        plt.show()

    def scatter_plot(self):
        plt.plot(np.arange(len(self.seq)), self.seq, 'ro')
        plt.title(f"Диаграмма рассеяния для ДСВ из {self.distribution}")
        plt.show()


def type1error(bi_p, g_p, num, e, n2):
    bi_res = 0
    g_res = 0
    mgen = mcg.MultiplicativeCongruentialMethod(75465, 75465, 2 ** 31)

    for _ in range(n2):
        g = DRVSequence(num, "G", g_p, mgen)
        g.generate_sequence()
        g_test = Chi2Test.DiscreteChiSquareTest(g.seq, e, "G", g_p)
        g_res += g_test.chisquare_test(False)

        bi = DRVSequence(num, "Bi", bi_p, mgen)
        bi.generate_sequence()
        bi_test = Chi2Test.DiscreteChiSquareTest(bi.seq, e, "Bi", bi_p)
        bi_res += bi_test.chisquare_test(False)

    bi_error = 1 - (bi_res / n2)
    g_error = 1 - (g_res / n2)
    print(f"Вероятность ошибки первого рода для ДСВ из Bi({bi_p}): {bi_error}")
    print(f"Вероятность ошибки первого рода для ДСВ из G({g_p}): {g_error}")


n = 1000
beta = 78121
m = 2 ** 31
mcm = mcg.MultiplicativeCongruentialMethod(beta, beta, m)
eps = 0.05
bi_seq = DRVSequence(n, "Bi", 0.2, mcm)
bi_seq.generate_sequence()
bi_seq.print_seq()
bi_seq.histogram()
bi_seq.scatter_plot()
chi2bi = Chi2Test.DiscreteChiSquareTest(bi_seq.seq, eps, "Bi", 0.2)
chi2bi.chisquare_test(True)
print()
g_seq = DRVSequence(n, "G", 0.6, mcm)
g_seq.generate_sequence()
g_seq.print_seq()
g_seq.histogram()
g_seq.scatter_plot()
chi2g = Chi2Test.DiscreteChiSquareTest(g_seq.seq, eps, "G", 0.6)
chi2g.chisquare_test(True)
print()
type1error(0.2, 0.6, n, eps, 100)
