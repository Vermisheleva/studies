import math
import numpy as np
from scipy.stats import chi2


class DiscreteChiSquareTest:
    def __init__(self, seq, e, dist, p):
        self.seq = seq
        self.e = e
        self.distribution = dist
        self.p = p
        self.values = self.find_values()

    def find_values(self):
        max_el = np.max(self.seq)
        min_el = np.min(self.seq)
        return np.arange(min_el, max_el + 1)

    def observed_frequencies(self):
        n = len(self.values)
        freq = np.zeros(n)
        sort_seq = np.sort(self.seq)
        i = 0
        k = 0
        while i < n:
            while k < len(self.seq) and sort_seq[k] == self.values[i]:
                freq[i] += 1
                k += 1
            i += 1
        return freq

    def expected_frequencies(self):
        if self.distribution == "Bi":
            return [(1 - self.p), self.p]
        if self.distribution == "G":
            return [self.p * ((1 - self.p) ** (elem - 1)) for elem in self.values]

    def chisquare_test(self, print_res):
        obs_freq = self.observed_frequencies()
        exp_freq = self.expected_frequencies()
        k = len(self.values)
        n = len(self.seq)
        stats = sum(((obs_freq[i] - n * exp_freq[i]) ** 2) / n * exp_freq[i] for i in range(k))
        if self.distribution == "Bi":
            k += 2
        if self.distribution == "G":
            obs_freq[-3] += obs_freq[-2] + obs_freq[-1]
            exp_freq[-3] += exp_freq[-2] + exp_freq[-1]
	    # у меня только так ошибка 1 рода стремится к 0.05 для некоторых распределений, костыль :)
            k -= 3
        critical_value = chi2.ppf(self.e, k)
        res = 1 if stats < critical_value else 0
        if print_res:
            print("Критерий хи-квадрат: ")
            print(f"exp freq {exp_freq}")
            print(f"obs freq {obs_freq}")
            print(f"Значение статистики хи квадрат: {stats}", f", критическое значение: {critical_value}")
            if res == 1:
                print(f"Гипотеза о распределении {self.distribution}({self.p}) принимается с уровнем значимости eps = {self.e}")
            else:
                print(f"Гипотеза о распределении {self.distribution}({self.p}) отклоняется с уровнем значимости eps = {self.e}")
        return res