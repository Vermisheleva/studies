# Зуйкевич Лидия, 3 курс, 7 группа
# Лабораторная работа 3, вариант 7. Поточные криптосистемы. РЛОРС, генератор Геффе
def lfsr(n, a, c):
    print("Входные данные РСЛОС:")
    print("a:", bin(a))
    print("c:", bin(c))
    answer = a
    T = 0
    s = [0]*(pow(2, n) - 1)
    while (answer != a or T == 0):
        b = answer & c
        s[T] = answer % 2
        i = n
        r = b & 1
        for i in range (1, n):
            r = r ^ (b & (1 << i)) >> i
        answer = (answer >> 1) + (r << (n - 1))
        T += 1
    print("Период:", T)
    print("Выходная последовательность:", *s[:T])
    return s, T

def geffe_generator(num, n1, a1, c1, n2, a2, c2, n3, a3, c3):
    s1, T1 = lfsr(n1, a1, c1)
    s2, T2 = lfsr(n2, a2, c2)
    s3, T3 = lfsr(n3, a3, c3)

    gamma = [0] * num
    gamma[0] = (s1[0] & s2[0]) ^ ((s1[0] ^ 1) & s3[0])
    num_of_1 = gamma[0]
    r = [0] * 5
    for i in range(1, num):
        gamma[i] = (s1[i % T1] & s2[i % T2]) ^ ((s1[i % T1] ^ 1) & s3[i % T3])
        num_of_1 += gamma[i]
        r[0] += pow(-1, (gamma[i] ^ gamma[i - 1]))
    for i in range(2, 6):
        j = 0
        while (j < num - i):
            r[i - 1] += pow(-1, (gamma[j] ^ gamma[j + i]))
            j += 1
    num_of_0 = num - num_of_1
    print()
    print("Последовательность генератора Геффе:")
    print(*gamma)
    print("Количество единиц:", num_of_1)
    print("Количество нулей:", num_of_0)
    for i in range(0, 5):
        print("r", i + 1, "=", r[i])


geffe_generator(10000, 5, 0b00101, 0b10101, 7, 0b0111001, 0b1100111, 8, 0b10010100, 0b11010011)