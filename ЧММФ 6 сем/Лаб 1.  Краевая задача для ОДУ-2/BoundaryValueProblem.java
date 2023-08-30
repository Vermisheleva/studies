public class BoundaryValueProblem {
    private double h;
    private int n;
    private double kappa0;
    private double kappa1;
    private double g0;
    private double g1;
    private double[] y1;
    private double[] y2;
    private double[] y3;

    public BoundaryValueProblem(double h) {
        this.h = h;
        this.n = (int) (1 / h);
        this.kappa0 = 0;
        this.kappa1 = 1;
        this.g0 = 0;
        this.g1 = 0;
        this.y1 = new double[n + 1];
        this.y2 = new double[n + 1];
        this.y3 = new double[n + 1];
    }

    public double f(double x){
        return Math.sin(x);
    }

    public double k(double x){
        return Math.exp(x);
    }

    public double q(double x){
        return Math.exp(x);
    }

    public double k_(double x){
        return Math.exp(x);
    }

    public double[] calculate_alpha(double[] a, double[] b, double[] c, double k1, int N){
        double[] alpha = new double[N];

        alpha[0] = k1;
        for (int i = 0; i < N - 1; i++) {
            alpha[i + 1] = b[i] / (c[i] - a[i] * alpha[i]);
        }

        return alpha;
    }

    public double[] calculate_beta(double[] a, double[] c, double[] F, double[] alpha, double v1, int N){
        double[] beta = new double[N];

        beta[0] = v1;
        for (int i = 0; i < N - 1; i++) {
            beta[i + 1] = (F[i] + beta[i] * a[i]) / (c[i] - a[i] * alpha[i]);
        }

        return beta;
    }

    public double[] tridiagonal_matrix_algorithm(double[] a, double[] b, double[] c, double[] F, double k1, double k2, double v1, double v2, int N){
        double[] y = new double[N + 1];
        double[] alpha = calculate_alpha(a, b, c, k1, N);
        double[] beta = calculate_beta(a, c, F, alpha, v1, N);

        y[N] = (v2 + k2 * beta[N - 1]) / (1 - alpha[N - 1] * k2);

        for (int i = N - 1; i >= 0; i--){
            y[i] = alpha[i] * y[i + 1] + beta[i];
        }

        return y;
    }

    public double[] differenceOperatorsMethod(double step){
        int N = (int) (1 / step);

        double[] a = new double[N - 1];
        double[] b = new double[N - 1];
        double[] c = new double[N - 1];
        double[] F = new double[N - 1];

        double val = step;
        double h2 = Math.pow(step, 2);

        for (int i = 0; i < N - 1; i++) {
            a[i] = k(val) / h2 - k_(val) / (2 * step);
            b[i] = k_(val) / (2 * step) + k(val) / h2;
            c[i] = 2 * k(val) / h2 + q(val);
            F[i] = f(val);
            val += step;
        }

        double k0_ = kappa0 * (1  - (step * k_(0)) / (k(0) * 2)) + (step * q(0)) / 2;
        double g0_ = g0 * (1  - (k_(0) * step) / (k(0) * 2)) + (step * f(0)) / 2;
        double k1_ = kappa1 * (1 + (k_(1) * step) / (2 * k(1))) + (step * q(1)) / 2;
        double g1_ = g1 * (1 + (step * k_(1)) / (2 * k(1))) + (step * f(1)) / 2;
        double k1 = k(0) / (step * k0_ + k(0));
        double v1 = (step * g0_) / (step * k0_ + k(0));
        double k2 = k(1) / (step * k1_ + k(1));
        double v2 = (step * g1_) / (step * k1_ + k(1));
        double[] y = tridiagonal_matrix_algorithm(a, b, c, F, k1, k2, v1, v2, N);

        return y;
    }

    public double[] balanceMethod(double step){
        int N = (int) (1 / step);

        double[] a = new double[N];
        double[] d = new double[N + 1];
        double[] phi = new double[N + 1];
        double[] A = new double[N - 1];
        double[] b = new double[N - 1];
        double[] c = new double[N - 1];
        double[] F = new double[N - 1];

        double h2 = Math.pow(step, 2);

        for (int i = 0; i < N - 1; i++) {
            a[i] = - step / (Math.exp(-(i + 1) * step) - Math.exp(-i * step));
            d[i + 1] = (Math.exp((i + 1.5) * step) - Math.exp((i + 0.5) * step)) / step;
            phi[i + 1] = - (Math.cos((i + 1.5) * step) - Math.cos((i + 0.5) * step)) / step;
        }

        d[0] = 2 * (Math.exp(step / 2) - 1) / step;
        d[N] = 2 * (Math.exp(1) - Math.exp(1 - step / 2)) / step;
        phi[0] = - 2 * (Math.cos(step / 2) - 1) / step;
        phi[N] = - 2 * (Math.cos(1) - Math.cos(1 - step / 2)) / step;
        a[N - 1] = - step / (Math.exp(- N * step) - Math.exp(-(N - 1) * step));

        for (int i = 0; i < N - 1; i++) {
            A[i] = a[i] / h2;
            b[i] = a[i + 1] / h2;
            c[i] = a[i + 1] / h2 + a[i] / h2 + d[i + 1];
            F[i] = phi[i + 1];
        }

        double k0_ = kappa0 + step * d[0] / 2;
        double g0_ = g0 + step * phi[0] / 2;
        double k1_ = kappa1 + step * d[N] / 2;
        double g1_ = g1 + step * phi[N] / 2;
        double k1 = a[0] / (step * k0_ + a[0]);
        double v1 = step * g0_ / (step * k0_ + a[0]);
        double k2 = a[N - 1] / (step * k1_ + a[N - 1]);
        double v2 = step * g1_ / (step * k1_ + a[N - 1]);
        double[] y = tridiagonal_matrix_algorithm(A, b, c, F, k1, k2, v1, v2, N);

        return y;
    }

    public double sp_a(double a, double b, double step){
        double mid = (b + a) / 2;
        return  (b - a) * (k(mid) - q(mid) * (mid - a) * (b - mid)) / step;
    }

    public double sp_di(double a, double b, double c, double step){
        double mid1 = (b + a) / 2;
        double mid2 = (b + c) / 2;
        return  ((b - a) * (q(mid1) * (mid1 - a)) + (c - b) * (q(mid2) * (c - mid2))) / Math.pow(step, 2);
    }

    public double sp_phi_i(double a, double b,double c, double step){
        double mid1 = (b + a) / 2;
        double mid2 = (b + c) / 2;
        return  ((b - a) * (f(mid1) * (mid1 - a)) + (c - b) * (f(mid2) * (c - mid2))) / Math.pow(step, 2);
    }

    public double[] ritzMethod(double step){
        int N = (int) (1 / step);

        double[] a = new double[N];
        double[] d = new double[N + 1];
        double[] phi = new double[N + 1];
        double[] A = new double[N - 1];
        double[] b = new double[N - 1];
        double[] c = new double[N - 1];
        double[] F = new double[N - 1];

        double h2 = Math.pow(step, 2);

        for (int i = 1; i < N; i++) {
            a[i - 1] = sp_a((i - 1) * step, i * step, step);
            d[i] = sp_di((i - 1) * step, i * step, (i + 1) * step, step);
            phi[i] = sp_phi_i((i - 1) * step, i * step, (i + 1) * step, step);
        }

        d[0] = q(step / 2);
        d[N] = q(1 - step / 2);
        phi[0] = f(step / 2);
        phi[N] = f(1 - step / 2);
        a[N - 1] = sp_a((N - 1) * step, N * step, step);

        for (int i = 0; i < N - 1; i++) {
            A[i] = a[i] / h2;
            b[i] = a[i + 1] / h2;
            c[i] = a[i + 1] / h2 + a[i] / h2 + d[i + 1];
            F[i] = phi[i + 1];
        }

        double k0_ = kappa0 + step * d[0] / 2;
        double g0_ = g0 + step * phi[0] / 2;
        double k1_ = kappa1 + step * d[N] / 2;
        double g1_ = g1 + step * phi[N] / 2;
        double k1 = a[0] / (step * k0_ + a[0]);
        double v1 = step * g0_ / (step * k0_ + a[0]);
        double k2 = a[N - 1] / (step * k1_ + a[N - 1]);
        double v2 = step * g1_ / (step * k1_ + a[N - 1]);
        double[] y = tridiagonal_matrix_algorithm(A, b, c, F, k1, k2, v1, v2, N);

        return y;
    }

    public void printArrays(double[] y, double[] u, int h_diff){
        double res;
        System.out.print("Решение при h = 0.1:       ");
        System.out.print("Решение при h = 0.001:        ");
        System.out.print("Невязка: \n");
        for(int i = 0; i < y.length; i++){
            res = Math.abs(u[i * h_diff] - y[i]);
            System.out.print(String.format("%5.8f", y[i]));
            System.out.print(String.format("%27.8f", u[i * h_diff]));
            System.out.print(String.format("%30.8f",  res));
            System.out.println();
        }
    }

    public void solve(){
        System.out.println("Метод замены дифференциальных операторов разностными: \n");
        y1 = differenceOperatorsMethod(h);
        double[] u1 = differenceOperatorsMethod(h / 100);
        int h_diff = 100;

        printArrays(y1, u1, h_diff);

        System.out.println("\nМетод баланса: \n");
        y2 = balanceMethod(h);
        double[] u2 = balanceMethod(h / 100);

        printArrays(y2, u2, h_diff);

        System.out.println("\nМетод Ритца: \n");
        y3 = ritzMethod(h);
        double[] u3 = ritzMethod(h / 100);

        printArrays(y3, u3, h_diff);
    }

    public static void main(String[] args){
        BoundaryValueProblem obj = new BoundaryValueProblem(0.1);
        obj.solve();
    }
}
