public class TrapezoidalRule {
    private final int N;
    private final double a;
    private final double b;
    private final double h;
    private final double[] t;
    private double[] y;
    private final double u0;

    public TrapezoidalRule(int n, double a, double b, double u0) {
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


    public void solve(){
        y[0] = u0;

        for (int i = 0; i < N; i++){
            y[i + 1] = y[i] + (h / 2) * (f(y[i], t[i]) + f(y[i] + h * f(y[i], t[i]), t[i + 1]));
        }

        System.out.println("Решение задачи Коши в узлах: ");
        for(double elem: y){
            System.out.println(elem);
        }
    }

    public static void main(String[] args){
        TrapezoidalRule obj = new TrapezoidalRule(10, 1., 1.5, 0.);
        obj.solve();
    }
}
