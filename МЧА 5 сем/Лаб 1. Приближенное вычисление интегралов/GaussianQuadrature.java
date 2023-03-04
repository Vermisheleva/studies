public class GaussianQuadrature {
    private double a;
    private double b;
    private int n;
    private double Q;
    private double[] x;
    private double[] A;
    private final double EPS = Math.pow(10, -5);
    private double r;
    private final double I = 2.64577597;
    private double f_10;
    private double R;

    public GaussianQuadrature(double a, double b, int n) {
        this.a = a;
        this.b = b;
        this.n = n;
        this.Q = 0.;
        this.x = new double[]{0.906179845938664, 0.538469310105683, 0., -0.538469310105683, -0.906179845938664};
        this.A = new double[]{0.236926885056189, 0.478628704993665, 0.568888888888889, 0.478628704993665, 0.236926885056189};
        this.f_10 = Math.abs((Math.exp(0.1) * (-126208 * Math.exp(0.1) - 2554624 * Math.exp(0.2) - 11281600 * Math.exp(0.3)
                - 14415136 * Math.exp(0.4) - 5533648 * Math.exp(0.5) - 540352 * Math.exp(0.6) - 7336 * Math.exp(0.7)
                - 10 * Math.exp(0.8) + Math.exp(0.9) - 512))/(1024 * Math.pow(-1 + Math.exp(0.1),19./2)));
    }

    public double f(double x){
        return Math.sqrt(Math.exp(x) - 1);
    }

    public static int factorial(int num) {
        if (num == 0){
            return 1;
        }
        int result = 1;
        for (int i = 2; i <= num; i++) {
            result *= i;
        }
        return result;
    }

    public void findSum(){
        for(int i = 0; i <= n; i++){
            Q += A[i] * f(x[i] * (b - a) / 2 + (b + a) / 2);
        }
        Q *= (b - a) / 2;

        R = (Math.pow(b - a, 2 * n + 3) * Math.pow(factorial(n + 1), 4) * f_10) /
                ((2 * n + 3) * Math.pow(factorial(2 * n + 2), 3));

        r = Math.abs(I - Q);
        System.out.println("Примерное значение интеграла: " + Q);
        System.out.println("Оценка погрешности интегрирования через формулу остаточного члена: " + R);
        System.out.println("Точное значение интеграла: " + I);
        System.out.println("Невязка: " + r);
    }

    public static void main(String[] args){
        GaussianQuadrature obj = new GaussianQuadrature(0.1, 2, 4);
        obj.findSum();
    }
}
