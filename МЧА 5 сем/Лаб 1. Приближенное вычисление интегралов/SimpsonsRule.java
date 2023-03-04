public class SimpsonsRule {
    private double a;
    private double b;
    private int N;
    private double h;
    private double Q;
    private double f_4;
    private double r;
    private final double I = 2.64577597;
    private final double EPS = Math.pow(10, -5);

    public SimpsonsRule(double a, double b) {
        this.a = a;
        this.b = b;
        this.f_4 = Math.abs(Math.exp(0.1) * (-4 * Math.exp(0.1) - 4 * Math.exp(0.2) + Math.exp(0.3) - 8)
                / (16 * Math.pow(Math.exp(0.1) - 1, 7./2)));
    }

    public double f(double x){
        return Math.sqrt(Math.exp(x) - 1);
    }

    public void findH(){
        N = (int)(Math.pow((Math.pow(b - a, 5) * f_4) / (EPS * 2880),  1. / 4));
        h = (b - a) / N;

        System.out.println("Количество разбиений: " + N);
        System.out.println("Шаг: " + h);
    }

    public void findSum(){
        for (int i = 0; i < N; i++){
            Q += f(a + i * h) + 4 * f(a + i * h + h / 2) + f(a + (i + 1) * h);
        }
        Q *= h / 6;

        r = Math.abs(I - Q);
        System.out.println("Примерное значение интеграла:" + Q);
        System.out.println("Точное значение интеграла: " + I);
        System.out.println("Невязка: " + r);
    }

    public static void main(String[] args){
        SimpsonsRule obj = new SimpsonsRule(0.1, 2);
        obj.findH();
        obj.findSum();
    }
}
