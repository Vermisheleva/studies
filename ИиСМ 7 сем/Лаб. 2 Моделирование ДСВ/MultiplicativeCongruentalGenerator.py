class MultiplicativeCongruentialMethod:
    def __init__(self, a, b, m):
        self.prev_el = a
        self.beta = b
        self.m = m

    def next_elem(self):
        z = self.beta * self.prev_el
        self.prev_el = z - self.m * int(z / self.m)
        return self.prev_el / self.m
