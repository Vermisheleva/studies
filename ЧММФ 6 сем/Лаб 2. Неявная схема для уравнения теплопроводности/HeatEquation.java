public class HeatEquation {
    private double h;
    private double tau;
    private double[][] y;
    private int n1;
    private int n2;

    private double a;
    private double b;
    private double c;
    private int print_param_i;
    private int print_param_j;
    private double kappa;
    private double[] f;
    private double[] alpha;
    private double[] beta;

    public HeatEquation(double h, double tau) {
        this.h = h;
        this.tau = tau;
        this.n1 = (int) (1/h);
        this.n2 = (int) (1/tau);
        this.y = new double[n2 + 1][n1 + 1];

        this.print_param_i = n1 / 20;
        this.print_param_j = n2 / 20;
        this.a = tau / Math.pow(h, 2);
        this.b = a;
        this.c = 1 + (2 * tau) / Math.pow(h, 2);
        this.kappa = 1;
        this.alpha = new double[n1];
        this.beta = new double[n1];
    }

    public int getPrint_param_i() {
        return print_param_i;
    }

    public int getPrint_param_j() {
        return print_param_j;
    }

    public double[][] getY() {
        return y;
    }

    public double getH() {
        return h;
    }

    public double getTau() {
        return tau;
    }

    public double phi(double i, double j){
        return -(i * h + Math.pow(j * tau, 2)) * Math.exp(- i * h * j * tau);
    }

    public double mu(double j){
        return (j * tau - 1) * Math.exp(-j * tau);
    }

    public double f(int i, int j){
        return tau * phi(i, j) + y[j][i];
    }

    public void calculate_alpha(){
        alpha[0] = 0;
        for (int i = 1; i < n1; i++) {
            alpha[i] = b / (c - a * alpha[i - 1]);
        }
    }

    public void calculate_beta(int j){
        beta[0] = 1;
        for (int i = 1; i < n1; i++) {
            beta[i] = (f(i - 1, j - 1) + beta[i - 1] * a) / (c - a * alpha[i - 1]);
        }
    }

    public void tridiagonal_matrix_algorithm(int j){
        calculate_beta(j);

        double v = ((h * mu(j)) / 2 + (1 + h / 2) * y[j - 1][n1]) * h;

        y[j][n1] = (v + kappa * beta[n1 - 1]) / (1 - alpha[n1 - 1] * kappa);

        for (int i = n1 - 1; i >= 0; i--){
            y[j][i] = alpha[i] * y[j][i + 1] + beta[i];
        }
    }

    public void solve(){
        System.out.println("h = " + h + " tau = " + tau);
        for (int i = 0; i <= n1; i++) {
            y[0][i] = 1;
        }
        calculate_alpha();

        for (int j = 1; j <= n2; j++) {
            tridiagonal_matrix_algorithm(j);
        }

        for (int i = n2; i >= 0; i-=print_param_j) {
            for(int j = 0; j <= n1; j+=print_param_i){
                System.out.print(String.format("%7.5f", y[i][j]) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    public void compare(HeatEquation obj){
        double h2 = obj.getH();
        double tau2 = obj.getTau();
        System.out.println("Comparing h1 = " + h +" tau1 = " + tau + " with h2 = " + h2 + " tau2 = " + tau2);
        double[][] y2 = obj.getY();
        int i2 = obj.getPrint_param_i();
        int j2 = obj.getPrint_param_j();

        for (int i = 20; i >= 0; i--) {
            for(int j = 0; j <= 20; j++){
                double elem = Math.abs(y[i * print_param_j][j * print_param_i] - y2[i * j2][j * i2]);
                System.out.print(String.format("%7.5f", elem) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    public static void main(String[] args){
        HeatEquation obj = new HeatEquation(0.05, 0.05);
        HeatEquation obj3 = new HeatEquation(0.005, 0.005);
        obj.solve();
        obj3.solve();

        obj.compare(obj3);
    }
}
