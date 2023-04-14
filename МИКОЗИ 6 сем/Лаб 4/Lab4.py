# Зуйкевич Лидия, 3 курс, 7 группа
# Лабораторная работа 4, вариант 7. Криптосистема RSA

def gcd_extended(a, b):
   if a == 0:
       return b, 0, 1
   gcd, x1, y1 = gcd_extended(b % a, a)
   x = y1 - (b // a) * x1
   y = x1
   return gcd, x, y

def find_inverse(a, m):
    gcd, x, y = gcd_extended(a, m)
    if gcd == 1:
        return (x % m + m) % m


def fast_exponentation(base, exp, n):
    res = 1
    while(exp > 0):
        if (exp & 1 == 1):
            res = (res * base) % n
        base = (base * base) % n
        exp >>= 1
    return res

def generate_keys(p, q, e):
    n = p * q
    phi = (p - 1) * (q - 1)
    gcd, x, y = gcd_extended(e, phi)
    if gcd == 1:
        d = find_inverse(e, phi)
    else:
        print("gcd(e, phi) is not equal to 1")
    return n, e, d


def encrypt(x, e, n):
    y = fast_exponentation(x, e, n)
    return y

def decrypt(y, d, n):
    x = fast_exponentation(y, d, n)
    return x


def task():
    p = 578569278720973
    q = 976534805568533
    e = 235108486320061234453015373083
    x1 = 402700874043636335474593885222
    y2 = 416593343738152120120255791792
    n, e, d = generate_keys(p, q, e)
    print("Сгенерированные ключи:", "e =", e, "d =", d)
    y1 = encrypt(x1, e, n)
    print("Сообщение для зашифрования: x1 =", x1)
    print("Результат зашифрования x1: y1 =", y1)
    x = decrypt(y1, d, n)
    print("Результат расшифорования y1: x =", x)
    x2 = encrypt(y2, d, n)
    print("Сообщение для расшифрования: y2 =", y2)
    print("Результат расшифорования y2: x2 =", x2)

task()

