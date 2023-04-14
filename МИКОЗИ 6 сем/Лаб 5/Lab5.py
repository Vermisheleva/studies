from random import randrange
from hashlib import sha256

def fast_exponentation(base, exp, mod):
    res = 1
    while(exp > 0):
        if (exp & 1 == 1):
            res = (res * base) % mod
        base = (base * base) % mod
        exp >>= 1
    return res


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


def generate_keys():
    q = 185497337331032352746789861834054230815003783507015530555482386637095968522879
    #q = int(input("enter q:"))
    r_lim = 4 * (q + 1) + 2
    r = randrange(2, r_lim, 2)
    p = q * r + 1
    while(pow(2, q * r, p) != 1 or pow(2, r, p) == 1):
        r = randrange(2, r_lim, 2)
        p = q * r + 1

    x = randrange(0, p)
    g = fast_exponentation(x, r, p)
    while(g == 1):
        x = randrange(0, p)
        g = fast_exponentation(x, r, p)

    d = randrange(0, q)
    e = fast_exponentation(g, d, p)
    print("p =", p, "\n q = ", q, "\n g =", g, "\n e =", e, "\n d =", d)
    return p, q, g, e, d


def sign(p, q, g, d):
    #message = input("Please enter a message you would like to sign:")
    message = "I, Lidia Zuikevich, love MIKOZI"
    m = int(sha256(bytes(message, encoding="utf-8")).hexdigest(), 16)
    k = randrange(1, q)
    r = fast_exponentation(g, k, p)
    s = (find_inverse(k, q) * (m - d * r)) % q
    print("m =", m, "\n k =", k, "\n r =", r, "\n s =", s)
    return r, s, m, message


def verify(p, q, g, e, message, r, s):
    if (r < 1 or r > p - 1):
        print("r is out of range.")
        return False
    if (s < 0 or s > q - 1):
        print("s is out of range.")
        return False
    m = int(sha256(bytes(message, encoding="utf-8")).hexdigest(), 16)
    left = (fast_exponentation(e, r, p) * fast_exponentation(r, s, p)) % p
    right = fast_exponentation(g, m, p)
    if (left == right):
        print("The signature is correct.")
        return True
    else:
        print("The signature isn't correct.")
        return False


def run():
    print("Please choose what would you like to do:")
    p = 0
    q = 0
    g = 0
    e = 0
    d = 0
    r = 0
    s = 0
    message = ""

    while True:
        action = input("Enter 'gen' for key generation \n 'sign' for signing message \n 'verify' for signature verifying \n 'exit' for exit\n")

        if (action == "gen"):
            print("Key generation:")
            p, q, g, e, d = generate_keys()

            with open('Report.txt', 'w') as file:
                file.write("Key generation:\n")
                file.write(f"p = {p} \nq = {q} \ng = {g} \ne = {e} \nd = {d}\n")
            continue


        if (action == "sign"):
            if (d != 0):
                print("Signing message:")
                r, s, m, message = sign(p, q, g, d,)
                with open('Report.txt', 'a') as file:
                    file.write("Signing message:\n")
                    file.write(f"m = {m} \nr = {r} \ns = {s}\n")
                continue
            else:
                 print("Please do key generation first.")

        if(action == "verify"):
            if (e != 0 and r != 0):
                print("Signature verifying:")
                result = verify(p, q, g, e, message, r, s)
                print(result)
                with open('Report.txt', 'a') as file:
                    file.write("Signature verifying:\n")
                    file.write(f"result = {result}\n")
                continue
            else:
                print("Please do key generation and signing first.")

        if(action == "exit"):
            return
        else:
            print("The command is wrong.")
            continue


run()