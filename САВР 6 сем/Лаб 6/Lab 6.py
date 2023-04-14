import statsmodels.api as sma
from statsmodels.graphics.tsaplots import plot_acf
import matplotlib.pyplot as plt

y = [7.60, 7.50, 7.20, 7.40, 7.10, 7.00, 8.00, 8.10, 8.00, 7.70, 7.20, 7.70, 7.40, 7.00, 7.20, 6.80, 6.60, 7.00, 6.60, 5.70, 6.00, 5.10, 5.40, 7.10, 6.60, 7.50, 6.50, 7.10, 7.50, 5.80, 6.50, 7.50, 6.90, 6.00, 5.90, 6.40, 6.60]
rs = sma.tsa.acf(y, nlags= 18)
for i in range (1, 19):
    print("Коэффициент автокорреляции для s =", i, "равен: ", rs[i])
plot_acf(y, lags = 18, alpha = 0.05, zero = False)
plt.show()
