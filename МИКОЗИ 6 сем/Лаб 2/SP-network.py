# Зуйкевич Лидия, 3 курс, 7 группа
# Лабораторная работа 2, вариант 7. SP-подстановка, круглый плюс, S-блоки: 2, 5,
# P-блок: циклический сдвиг на 3 бита влево


def get_message_and_key():
    #n, q, r = input("Введите номер студента в списке, количество символов имени, количество символов фамилии: ").split(' ')
    #n = int(n)
    #q = int(q)
    #r = int(r)
    n = 7
    q = 5
    r = 8
    print("Параметры: ", n, q, r)
    x = 7 * n
    k = 4096 - 11 * q * r
    message = x
    #message = 0b00110001
    cipher_key = to_binary(k, 12)
    print("Сообщение: ", x, " = ", bin(message))
    print("Ключ: ", k, " = ", *cipher_key)
    return message, cipher_key,


def key_expansion(cipher_key):
    k1 = [1, 2, 3, 4, 5, 6, 7, 8]
    k2 = [6, 7, 8, 9, 10, 11, 12, 1]
    k3 = [11, 12, 1, 2, 3, 4, 5, 6]

    round_keys = [0] * 3
    fact = 1
    i = 7
    while i > -1:
        round_keys[0] += cipher_key[k1[i] - 1] * fact
        round_keys[1] += cipher_key[k2[i] - 1] * fact
        round_keys[2] += cipher_key[k3[i] - 1] * fact
        i -= 1
        fact *= 2
    print("Раундовые ключи: ", *round_keys)
    return round_keys


def encrypt(x, round_keys):
    s_box_1 = [3, 7, 14, 9, 8, 10, 15, 0, 5, 2, 6, 12, 11, 4, 13, 1]
    s_box_2 = [11, 5, 1, 9, 8, 13, 15, 0, 14, 4, 2, 3, 12, 7, 10, 6]

    result = x
    for i in range(3):
        t = result ^ round_keys[i]
        t1 = t // 16
        t2 = t % 16

        n1 = s_box_1[t1]
        n2 = s_box_2[t2]
        result = p_box(n1, n2)
        print("Результат на итерации", i + 1, ": ", result, " = ", bin(result))


def p_box(n1, n2):
    result = (n1 % 2) * 128 + n2 * 8 + (n1 // 2)
    return result


def to_binary(num, length):
    answer = [0] * length
    i = length - 1
    while num > 0:
        answer[i] = num % 2
        num //= 2
        i -= 1
    return answer


message, cipher_key = get_message_and_key()
round_keys = key_expansion(cipher_key)
encrypt(message, round_keys)