from random import uniform
import matplotlib.pyplot as plt
import numpy as np
import math


class MarkovChainSimulation:
    def __init__(self, n, pi, p):
        self.n = n
        self.pi = pi
        self.p = p
        self.c = np.arange(n)
        # вспомогательный вектор q
        self.q_v = [sum(pi[:j]) for j in range(n + 1)]
        # вспомогательная матрица Q
        self.q_m = [[sum(p[i][:j]) for j in range(n + 1)] for i in range(n)]

    def simulation(self, mc_len):
        mc = np.zeros(mc_len)
        v0 = 0
        alpha = uniform(0, 1)
        for i in range(self.n):
            if self.q_v[i] <= alpha < self.q_v[i + 1]:
                mc[0] = int(self.c[i])
                v0 = i
                break
        for k in range(1, mc_len):
            alpha = uniform(0, 1)
            for i in range(self.n):
                if self.q_m[v0][i] <= alpha < self.q_m[v0][i + 1]:
                    mc[k] = int(self.c[i])
                    v0 = i
                    break
        return mc


class MonteCarloSolving:
    def __init__(self, dim, matrix, f_vector, solution, mc_gen):
        self.n = dim
        self.a = matrix
        self.f = f_vector
        self.exp = solution
        self.mc_gen = mc_gen

    def calculate_weights(self, mc, h, mc_len):
        q = np.zeros(mc_len)
        if self.mc_gen.pi[mc[0]] > 0:
            q[0] = h[mc[0]] / self.mc_gen.pi[mc[0]]
        else:
            q[0] = 0

        for i in range(1, mc_len):
            if self.mc_gen.p[mc[i - 1]][mc[i]] > 0:
                q[i] = q[i - 1] * self.a[mc[i - 1]][mc[i]] / self.mc_gen.p[mc[i - 1]][mc[i]]
            else:
                q[i] = 0
        return q

    def generate_ksi(self, h, mc_len):
        mc = [int(elem) for elem in self.mc_gen.simulation(mc_len)]
        q = self.calculate_weights(mc, h, mc_len)
        ksi = sum(q[i] * self.f[mc[i]] for i in range(mc_len))
        return ksi

    def solve(self, l, mc_len):
        h = [1, 0, 0]
        x = 0
        for _ in range(l):
            x += self.generate_ksi(h, mc_len)
        x /= l
        h = [0, 1, 0]
        y = 0
        for _ in range(l):
            y += self.generate_ksi(h, mc_len)
        y /= l
        h = [0, 0, 1]
        z = 0
        for _ in range(l):
            z += self.generate_ksi(h, mc_len)
        z /= l
        f_sol = [x, y, z]
        rez = [self.exp[i] - f_sol[i] for i in range(self.n)]
        if l == 10000:
            print(f'Приближенное решение: {f_sol}')
            print(f'Точное решение: {self.exp}')
            print(f'Вектор невязки: {rez}')
        return rez


dim = 3
pi = [1/3, 1/3, 1/3]
p = [[1/3, 1/3, 1/3], [1/3, 1/3, 1/3], [1/3, 1/3, 1/3]]
mc_gen = MarkovChainSimulation(dim, pi, p)
mc_len = 1000
matrix = [[-0.1, 0.1, -0.2], [-0.1, 0.5, -0.3], [0.3, 0.1, -0.3]]
f = [-3, 1, 4]
acc_sol = [-3.07018, 1.14035, 2.45614]
sol = MonteCarloSolving(dim, matrix, f, acc_sol, mc_gen)
rez_x = []
rez_y = []
rez_z = []
l_lims = np.arange(1000, 10001, step=1000)
for l in l_lims:
    f_rez = sol.solve(l, mc_len)
    rez_x.append(f_rez[0])
    rez_y.append(f_rez[1])
    rez_z.append(f_rez[2])

plt.plot(l_lims, rez_x)
plt.title('График зависимости невязки от L для x')
plt.show()
plt.plot(l_lims, rez_y)
plt.title('График зависимости невязки от L для y')
plt.show()
plt.plot(l_lims, rez_z)
plt.title('График зависимости невязки от L для z')
plt.show()

rez_len_x = []
rez_len_y = []
rez_len_z = []
len_lims = np.arange(1000, 10001, step=1000)
for m_len in len_lims:
    f_rez = sol.solve(1000, m_len)
    rez_len_x.append(f_rez[0])
    rez_len_y.append(f_rez[1])
    rez_len_z.append(f_rez[2])

plt.plot(len_lims, rez_len_x)
plt.title('График зависимости невязки от длины цепи для x')
plt.show()
plt.plot(len_lims, rez_len_y)
plt.title('График зависимости невязки от длины цепи для y')
plt.show()
plt.plot(len_lims, rez_len_z)
plt.title('График зависимости невязки от длины цепи для z')
plt.show()
