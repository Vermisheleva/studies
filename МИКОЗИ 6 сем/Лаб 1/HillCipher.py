# Зуйкевич Лидия, 3 курс, 7 группа
# Вариант 7, задание 1. Шифр Хилла
# открытый текст: статистика
# полученный шифртекст: нхебчфаяах
import numpy as np


def create_alphabet():
    alphabet = []
    for i in range(ord('а'), ord('е') + 1):
       alphabet += chr(i)
    alphabet += 'ё'
    for i in range(ord('ж'), ord('я') + 1):
       alphabet += chr(i)
    return alphabet


def get_plain_text():
    str = "статистика"
    # str = input("Введите открытый текст, слово должно содержать четное количество букв: ")
    print("Открытый текст: ", str)
    plain_text = list(str)
    return plain_text


def get_plain_text_codes(plain_text, alphabet):
    text_len = len(plain_text)
    plain_text_codes = [0] * text_len
    for i in range(text_len):
        plain_text_codes[i] = alphabet.index(plain_text[i])
    return plain_text_codes


def encrypt(plain_text_codes, alphabet):
    m = 33
    text_len = len(plain_text_codes)
    key = [[6, 14], [2, 7]]
    # print("Введите ключ: обратимую по модулю 33 матрицу 2х2: ")
    # key = []
    # for i in range(2):
    #   row = input().split()
    #   for i in range(2):
    #      row[i] = int(row[i])
    #   key.append(row)

    ciphertext_codes = []
    if text_len % 2 == 0:
        i = 0
        while i < text_len:
            ciphertext_codes[i:i+2] = np.dot(plain_text_codes[i:i+2], key)
            i += 2
    else:
        print("Нечетное количество букв")
        return

    answer = ""
    for i in range(len(ciphertext_codes)):
        answer += alphabet[ciphertext_codes[i] % m]
    print("Шифртекст: ", answer)


alphabet = create_alphabet()
plain_text = get_plain_text()
plain_text_codes = get_plain_text_codes(plain_text, alphabet)
encrypt(plain_text_codes, alphabet)
