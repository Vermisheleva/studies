public class LMM {
    private final int N;
    private final double a;
    private final double b;
    private final double h;
    private final double[] t;
    private double[] y;
    private final double u0;

    public LMM(int n, double a, double b, double u0) {
        N = n;
        this.a = a;
        this.b = b;
        this.h = (b - a) / N;
        this.t = new double[N + 1];
        for (int i = 0; i <= N; i++){
            t[i] = a + h * i;
        }
        this.y = new double[N + 1];
        this.u0 = u0;
    }

    public double[] getY() {
        return y;
    }

    public double f(double y, double t){
        return (y + Math.sqrt(Math.pow(t, 2) + Math.pow(y, 2))) / t;
    }

    public void mpppt(int i){
        double y2 = y[i - 1] + (h / 3) * f(y[i - 1], t[i - 1]);
        double y3 = y[i - 1] + (2 * h / 3) * f(y2, t[i - 1] + h / 3);
        y[i] = y[i - 1] + (h / 4) * (f(y[i - 1], t[i - 1]) + 3 * f(y3, t[i - 1] + 2 * h /3));
    }

    public void solve(){
        y[0] = u0;
        mpppt(1);
        mpppt(2);

        for (int i = 2; i < N; i++){
            y[i + 1] = y[i] + (h / 12) * (23 * f(y[i], t[i]) - 16 * f(y[i - 1], t[i - 1]) + 5 * f(y[i - 2], t[i - 2]));
        }

        System.out.println("Решение задачи Коши в узлах: ");
        for(double elem: y){
            System.out.println(elem);
        }
    }

    public static void main(String[] args){
        LMM obj = new LMM(10, 1., 1.5, 0.);
        obj.solve();
    }
}
