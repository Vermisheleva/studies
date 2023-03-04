import java.util.Arrays;

public class MMK {
    private final double a;
    private final double b;
    private final int n;
    private final double lambda;
    private double h;
    private double [][] B;
    private double[] f;
    private double[] yFIE;
    private double[] yVIE;
    private final double[] x;

    public MMK(double a, double b, int n, double lambda) {
        this.a = a;
        this.b = b;
        this.n = n;
        this.lambda = lambda;
        this.h = (b - a) / n;
        this.B = new double [n + 1][n + 1];
        this.yFIE = new double [n + 2];
        this.yVIE = new double [n + 2];
        this.f = new double [n + 1];
        this.x = new double[n + 1];

        for(int i = 0; i <= n; i++){
            x[i] = a + h * i;
        }
    }

    public double[] getyFIE() {
        return yFIE;
    }

    public double[] getyVIE() {
        return yVIE;
    }

    public double K(double x, double s){
        return Math.sin(x + s);
    }

    public double function(double x){
        return (Math.sin(x) + 1);
    }

    public void fillDataForFIE(){
        for(int i = 0; i <= n; i++){
            f[i] = function(x[i]);

            for(int j = 1; j < n; j++){
                B[i][j] = - lambda * h * K(x[i], x[j]);

                if (j == i){
                    B[i][j]++;
                }
            }
            B[i][0] = - lambda * (h / 2) * K(x[i], a);
            B[i][n] = - lambda * (h / 2) * K(x[i], x[n]);
        }
        B[0][0]++;
        B[n][n]++;
    }

    public void fillDataForVIE(){
        for (int i = 0; i <= n; i++){
            Arrays.fill(B[i], i, n + 1, 0);
        }
        //Arrays.fill(y, 0);

        for(int i = 0; i <= n; i++){
            f[i] = function(x[i]);
            B[i][i] = 1;

            for(int j = 0; j < i; j++){
                B[i][j] = - lambda * h * K(x[i], x[j]);
            }
        }
    }

    public void gaussMethod(double[] y){
        double temp1;
        double temp2;

        //прямой ход
        for (int k = 0; k <= n; k++){
            temp1 = B[k][k];
            for(int j = 0; j <= n; j++) {
                B[k][j] = B[k][j] / temp1;
            }
            f[k] /= temp1;
            for(int i = k + 1; i <= n; i++) {
                temp2 = B[i][k];
                f[i] = f[i] - (f[k] * temp2);
                for(int j = 0; j <= n; j++) {
                    B[i][j] = B[i][j] - (B[k][j] * temp2);
                }
            }
        }
//обратный ход
        y[n] = f[n];
        for (int k = n - 1; k >= 0; k--) {
            y[k] = f[k];
            for (int j = k + 1; j <= n; j++) {
                y[k] -= B[k][j] * y[j];
            }
        }

        for (int i = 0; i <= n; i++){
            System.out.println(y[i] + " ");
        }
    }

    public void findValueInPointForFIE(double p){
        for(int i = 1; i < n; i++){
            yFIE[n + 1] += lambda * h * K(p, x[i]) * yFIE[i];
        }
        yFIE[n + 1] += lambda * (h / 2) * (K(p, a) * yFIE[0] + K(p, x[n]) * yFIE[n]) + function(p);

        System.out.println(yFIE[n + 1]);
    }

    public void findValueInPointForVIE(double p){
        double w = 1;

        for (int i = 0; i <= n; i++){
            w *= (p - x[i]);

            double temp = yVIE[i];
            for(int j = 0; j <= n; j++){
                if (i != j){
                    temp /= (x[i] - x[j]);
                }
            }
            temp /= (p - x[i]);
            yVIE[n + 1] += temp;
        }
        yVIE[n + 1] *= w;
        System.out.println(yVIE[n + 1]);
    }

    public static void main(String[] args){
        MMK obj = new MMK(0, Math.PI / 2, 10, 0.1);
        obj.fillDataForFIE();

        System.out.println("Решение уравнения Фредгольма в узлах: ");
        obj.gaussMethod(obj.getyFIE());
        System.out.println("Решение уравнения Фредгольма в точке х*: ");
        obj.findValueInPointForFIE(Math.PI / 4.4);
        obj.fillDataForVIE();
        System.out.println("Решение уравнения Вольтерра в узлах: ");
        obj.gaussMethod(obj.getyVIE());
        System.out.println("Решение уравнения Вольтерра в точке х*: ");
        obj.findValueInPointForVIE(Math.PI / 4.4);
    }
}
