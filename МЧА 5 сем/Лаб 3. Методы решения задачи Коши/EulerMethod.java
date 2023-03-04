public class EulerMethod {
    private final int N;
    private final double a;
    private final double b;
    private final double h;
    private final double[] t;
    private double[] y;
    private final double u0;
    private final double EPS;

    public EulerMethod(int n, double a, double b, double u0) {
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
        this.EPS = Math.pow(h, 3);
    }

    public double[] getY() {
        return y;
    }

    public double f(double y, double t){
        return (y + Math.sqrt(Math.pow(t, 2) + Math.pow(y, 2))) / t;
    }

    public double f_(double y, double t){
        return 1 / t + (2 * y) / (2 * t * Math.sqrt(Math.pow(t, 2) + Math.pow(y, 2)));
    }

    public double function (double yk, int i){
        return yk - (yk - y[i] - h * f(yk, t[i + 1])) / (1 - h * f_(yk, t[i + 1]));
    }

    public double newtonMethod(int i){
        double y1 = y[i];
        double y2 = function(y1, i);

        while (Math.abs(y2 - y1) > EPS){
            y1 = y2;
            y2 = function(y1, i);
        }

        return y2;
    }

    public void solve(){
       y[0] = u0;
       double yk;

       for (int i = 0; i < N; i++){
           yk = newtonMethod(i);
           y[i + 1] = y[i] + h * f(yk, t[i + 1]);

       }

       System.out.println("Решение задачи Коши в узлах: ");
       for(double elem: y){
           System.out.println(elem);
       }
    }

    public static void main(String[] args){
        EulerMethod obj = new EulerMethod(10, 1., 1.5, 0.);
        obj.solve();
    }
}
