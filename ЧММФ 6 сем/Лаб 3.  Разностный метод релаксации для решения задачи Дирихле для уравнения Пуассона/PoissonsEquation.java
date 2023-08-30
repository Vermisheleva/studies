import java.util.Arrays;

public class PoissonsEquation {
    private final double h_x;
    private final double h_y;
    private final int n_x;
    private final int n_y;
    private final double EPS;

    private final double a;
    private final double c;
    private final double q;

    private double[][] prev;
    private double[][] next;
    private final int print_param_x;
    private final int print_param_y;

    public PoissonsEquation(double h_x, double h_y, double a, double b, double c, double d, double q) {
        this.h_x = h_x;
        this.h_y = h_y;
        this.EPS = q * Math.max(Math.pow(h_x, 3), Math.pow(h_y, 3));
        this.n_x = (int) ((b - a)/h_x);
        this.n_y = (int) ((d - c)/h_y);
        this.print_param_x = n_x / 20;
        this.print_param_y = n_y / 10;

        this.a = a;
        this.c = c;
        this.q = q;
        this.prev = new double[n_y + 1][n_x + 1];
        this.next = new double[n_y + 1][n_x + 1];
    }

    public double getH_x() {
        return h_x;
    }

    public double getH_y() {
        return h_y;
    }

    public double[][] getNext() {
        return next;
    }

    public int getPrint_param_x() {
        return print_param_x;
    }

    public int getPrint_param_y() {
        return print_param_y;
    }

    public double psi1(double y){
        return Math.sin(Math.PI * y);
    }

    public double psi2(double y){
        return Math.abs(Math.sin(2 * Math.PI * y));
    }

    public double psi3(double x){
        return - x * (x + 1);
    }

    public double psi4(double x){
        return - x * (x + 1);
    }

    public double f(int i, int j){
        return Math.cosh(Math.pow(a + i * h_x, 2) * (c + j * h_y));
    }

    public void solve(){
        System.out.println("h_x = " + h_x + " h_y = " + h_y + " q = " + q);

        double val_y = c;
        for(int i = 1; i < n_y; i++){
            val_y += h_y;
            next[i][0] = psi1(val_y);
            next[i][n_x] = psi2(val_y);
        }

        double val_x = a;
        for(int i = 0; i <= n_x; i++){
            next[0][i] = psi3(val_x);
            next[n_y][i] = psi4(val_x);
            val_x += h_x;
        }

        double hx_2 = Math.pow(h_x, 2);
        double hy_2 = Math.pow(h_y, 2);
        double coeff = q / (2. / hx_2 + 2. / hy_2);

        sor(hx_2, hy_2, coeff);
        print_matrix(next);
        System.out.println();
    }

    public void copyMatrix(double[][] destination, double[][] source){
        for (int i = 0; i <= n_y; i++) {
            destination[i] = Arrays.copyOf(source[i], n_x + 1);
        }
    }

    public void sor(double hx_2, double hy_2, double coeff){
        double max;
        int num = 0;
        do{
            copyMatrix(prev, next);
            max = - Double.MAX_VALUE;

            for (int j = 1; j < n_y; j++) {
                for (int i = 1; i < n_x; i++) {
                    next[j][i] = coeff * ((prev[j][i+1] + next[j][i - 1]) / hx_2 + (prev[j + 1][i] + next[j - 1][i]) / hy_2 + f(i, j)) + (1 - q) * prev[j][i];
                    double res = Math.abs(next[j][i] - prev[j][i]);

                    max = Math.max(res, max);
                }
            }
            num++;
        } while(max > EPS);
        System.out.println("Number of iterations: " + num);
    }

    public void print_matrix(double[][] matr){
        for(int j = 0; j <= n_y; j += print_param_y){
            for(int i = 0; i <= n_x; i += print_param_x){
                System.out.print(String.format("%7.5f", matr[j][i]) + " ");
            }
            System.out.println();
        }
    }

    public void compare(PoissonsEquation obj){
        double h2x = obj.getH_x();
        double h2y = obj.getH_y();
        System.out.println("Comparing h1_x = " + h_x +" h1_y = " + h_y + " with h2_x = " + h2x + " h2_y = " + h2y);
        double[][] y2 = obj.getNext();
        int i2 = obj.getPrint_param_x();
        int j2 = obj.getPrint_param_y();

        for (int i = 0; i <= 10; i++) {
            for(int j = 0; j <= 20; j++){
                double elem = Math.abs(next[i * print_param_y][j * print_param_x] - y2[i * j2][j * i2]);
                System.out.print(String.format("%7.5f", elem) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    public static void main(String[] args){
        double q = 1.8;
        PoissonsEquation obj = new PoissonsEquation(0.05, 0.1, -1, 0, 0, 1, q);
        PoissonsEquation obj2 = new PoissonsEquation(0.005, 0.01, -1, 0, 0, 1, q);
        obj.solve();
        obj2.solve();
        obj.compare(obj2);
    }
}
