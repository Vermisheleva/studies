# Зуйкевич Лидия, 3 курс, 7 группа
# Вариант 7, задание 2. Аффинный шифр
# шифртекст: лтмявфтду
# расшифрованный текст: грейпфрут


def create_alphabet():
    alphabet = []
    for i in range(ord('а'), ord('е') + 1):
       alphabet += chr(i)
    alphabet += 'ё'
    for i in range(ord('ж'), ord('я') + 1):
       alphabet += chr(i)
    return alphabet


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


def get_ciphertext(alphabet):
    str = "лтмявфтду"
    # str = input("Введите шифртекст: ")
    print("Шифртекст: ", str)
    ciphertext = list(str)
    ciphertext_codes = [0] * len(ciphertext)
    for i in range(len(ciphertext)):
        ciphertext_codes[i] = alphabet.index(ciphertext[i])
    return ciphertext_codes


def decrypt(ciphertext_codes, a, b, alphabet):
    m = 33
    inv = find_inverse(a, m)
    answer = ""
    for i in range(len(ciphertext_codes)):
        answer += alphabet[(inv * (ciphertext_codes[i] - b)) % m]
    print("Расшифрованный текст: ", answer)


alphabet = create_alphabet()
ciphertext_codes = get_ciphertext(alphabet)
decrypt(ciphertext_codes, 17, 27, alphabet)
