//Приближенное вычисление интеграла от sqrt(exp(x) - 1) на [0.1, 2] с точностью 10^(-5)
//с помощью составной формулы средних прямоугольников

public class MiddleRiemannSum {
    private double a;
    private double b;
    private int N;
    private double h;
    private double Q;
    private double f_2;
    private double r;
    private final double I = 2.64577597;
    private final double EPS = Math.pow(10, -5);

    public MiddleRiemannSum(double a, double b) {
        this.a = a;
        this.b = b;
        this.f_2 = (Math.exp(2) * (Math.exp(2) - 2)) / (4 * Math.pow(Math.exp(2) - 1, 3./2));
    }

    public double f(double x){
        return Math.sqrt(Math.exp(x) - 1);
    }

    public void findH(){
        N = (int)Math.sqrt((Math.pow((b - a), 3) * f_2) / (24 * EPS));
        h = (b - a) / N;
        System.out.println("Количество разбиений: " + N);
        System.out.println("Шаг: " + h);
    }

    public void findSum(){
        for (int i = 0; i < N; i++){
            Q += f(a + i * h + h / 2);
        }
        Q *= h;
        r = Math.abs(I - Q);
        System.out.println("Примерное значение интеграла: " + Q);
        System.out.println("Точное значение интеграла: " + I);
        System.out.println("Невязка: " + r);
    }

    public static void main(String[] args){
        MiddleRiemannSum obj = new MiddleRiemannSum(0.1, 2);
        obj.findH();
        obj.findSum();
    }
}
