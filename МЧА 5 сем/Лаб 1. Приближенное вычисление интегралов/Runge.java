//Приближенное вычисление интеграла от sqrt(exp(x) - 1) на [0.1, 2] с точностью 10^(-5)
//с помощью правила Рунге и формулы правых прямоугольников

public class Runge {
    private double a;
    private double b;
    private int N;
    private double h;
    private double Q1;
    private double Q2;
    private double R;
    private double r;
    private final double I = 2.64577597;
    private final double EPS = Math.pow(10, -5);

    public Runge(double a, double b) {
        this.a = a;
        this.b = b;
        this.N = 2;
        this.h = (b - a) / N;
        this.R = Double.MAX_VALUE;
    }

    public double f(double x){
        return Math.sqrt(Math.exp(x) - 1);
    }

    public void solve(){
        for (int i = 0; i < N; i++){
            Q1 += f(a + i * h);
        }

        Q1 *= h;
        h /= 2;
        N *= 2;

        while (Math.abs(R) > EPS){
            for (int i = 0; i < N; i++){
                Q2 += f(a + i * h);
            }
            Q2 *= h;

            R = Q2 - Q1;
            Q1 = Q2;
            h /= 2;
            N *= 2;
        }

        r = Math.abs(I - Q2);
        System.out.println("Приближенное значение интеграла: " + Q2);
        System.out.println("Точное значение интеграла: " + I);
        System.out.println("Количество разбиений: " + N/2);
        System.out.println("Шаг: " + h);
        System.out.println("Невязка: " + r);
    }
    public static void main(String[] args){
        Runge obj = new Runge (0.1, 2);
        obj.solve();
    }
}
