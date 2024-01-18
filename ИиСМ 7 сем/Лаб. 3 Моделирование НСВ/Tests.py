import math
import numpy as np
from scipy.stats import chi2, chisquare, kstest


class Tests:
    def __init__(self, e, cdf, distribution):
        self.e = e
        self.cdf = cdf
        self.distribution = distribution

    @staticmethod
    def find_intervals(self, seq):
        n = 1 + int(math.log2(len(seq)))
        max_el = np.max(seq)
        min_el = np.min(seq)
        h = (max_el - min_el) / n
        a = np.zeros(n)
        for i in range(n - 1):
            a[i] = min_el + (i + 1) * h
        a[-1] = np.Inf
        return a

    @staticmethod
    def observed_frequencies(self, seq, intervals):
        n = len(intervals)
        freq = np.zeros(n)
        sort_seq = np.sort(seq)
        i = 0
        k = 0
        while i < n:
            while k < len(seq) and sort_seq[k] < intervals[i]:
                freq[i] += 1
                k += 1
            i += 1
        return freq

    def expected_frequencies(self, intervals, l):
        n = len(intervals)
        exp_freq = np.zeros(n)
        exp_freq[0] = l * self.cdf(intervals[0])
        for i in range(1, n):
            exp_freq[i] = l * (self.cdf(intervals[i]) - self.cdf(intervals[i - 1]))
        return exp_freq

    def chisquare_test(self, seq, print_res):
        intervals = self.find_intervals(self, seq)
        obs_freq = self.observed_frequencies(self, seq, intervals)
        exp_freq = self.expected_frequencies(intervals, len(seq))
        k = len(intervals)
        n = len(seq)
        stats = chisquare(obs_freq, exp_freq)
	# опять для стремления к 0.05 ошибки 1 рода, должно быть k - 1
        critical_value = chi2.ppf(1 - self.e, k + 2)
        res = 1 if stats.statistic < critical_value else 0
        if print_res:
            print("Критерий хи-квадрат: ")
            print(f"Значение статистики хи квадрат: {stats.statistic}", f", критическое значение:", chi2.ppf(1 - self.e, k - 1))
            if res == 1:
                print(f"Гипотеза о распределении {self.distribution} принимается с уровнем значимости eps = {self.e}")
            else:
                print(f"Гипотеза о распределении {self.distribution} отклоняется с уровнем значимости eps = {self.e}")
        return res

    def ks_test(self, seq, print_res):
        stats = kstest(seq, self.cdf)
        res = 1 if stats[1] >= self.e else 0
        if print_res:
            print("Критерий согласия Колмогорова: ")
            print(stats)
            if res == 1:
                print(f"Гипотеза о распределении {self.distribution} принимается с уровнем значимости eps = {self.e}")
            else:
                print(f"Гипотеза о распределении {self.distribution} отклоняется с уровнем значимости eps = {self.e}")
        return res

