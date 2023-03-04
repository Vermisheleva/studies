import java.util.Arrays;

public class MPP {
    private final double a;
    private final double b;
    private final int n;
    private final double lambda;
    private double h;
    private double [][] yFIE;
    private double [][] yVIE;
    private double[] x;
    final int N = 10;

    public MPP(double a, double b, int n, double lambda) {
        this.a = a;
        this.b = b;
        this.n = n;
        this.h = (this.b - a) / N;
        this.yFIE = new double [2][N + 2];
        this.yVIE = new double [2][N + 2];
        this.x = new double [N + 2];
        this.lambda = lambda;
    }

    public double[][] getyFIE() {
        return yFIE;
    }

    public double[][] getyVIE() {
        return yVIE;
    }

    public double K(double k, double s){
        return Math.sin(k + s);
    }

    public double function(double p){
        return (Math.sin(p) + 1);
    }

    public void solveFIE(double p){
        for(int i = 0; i <= N; i++){
            x[i] = a + h * i;
            yFIE[0][i] = function(x[i]);
        }
        x[N + 1] = p;
        yFIE[0][N + 1] = function(x[N + 1]);

        int count = n;
        int t = 0;
        int k = 1;
        int temp;
        while (count > 0){
            for(int i = 0; i < N + 2; i++){
                for(int j = 1; j < N; j++){
                    yFIE[k][i] += lambda * h * K(x[i], x[j]) * yFIE[t][j];
                }
                yFIE[k][i] += lambda * (h / 2) * (K(x[i], x[0]) * yFIE[t][0] + K(x[i], x[N]) * yFIE[t][N]);
                yFIE[k][i] += function(x[i]);
            }
            temp = k;
            k = t;
            t = temp;
            count--;

            Arrays.fill(yFIE[k], 0);
        }
        for (int i = 0; i < N + 1; i++){
            System.out.println(yFIE[t][i]);
        }
        System.out.println("Решение уравнения Фредгольма в точке х*: ");
        System.out.println(yFIE[t][N + 1]);
    }

    public void solveVIE(double p){
        //Arrays.fill(y[0], 0);
        //Arrays.fill(y[1], 0);

        for(int i = 0; i <= N; i++){
            yVIE[0][i] = function(x[i]);
        }

        int count = n;
        int t = 0;
        int k = 1;
        int temp;
        while (count > 0){
            for(int i = 0; i < N + 1; i++){
                for(int j = 0; j < i; j++){
                    yVIE[k][i] += lambda * h * K(x[i], x[j]) * yVIE[t][j];
                }
                yVIE[k][i] += function(x[i]);
            }
            temp = k;
            k = t;
            t = temp;
            count--;

            Arrays.fill(yVIE[k], 0);
        }
        for (int i = 0; i < N + 1; i++){
            System.out.println(yVIE[t][i]);
        }
        System.out.println("Решение уравнения Вольтерра в точке х*: ");
        findValueInPointForVIE(p);
    }

    public void findValueInPointForVIE(double p){
        double w = 1;

        for (int i = 0; i <= N; i++){
            w *= (p - x[i]);

            double temp = yVIE[1][i];
            for(int j = 0; j <= N; j++){
                if (i != j){
                    temp /= (x[i] - x[j]);
                }
            }
            temp /= (p - x[i]);
            yVIE[1][N + 1] += temp;
        }
        yVIE[1][N + 1] *= w;
        System.out.println(yVIE[1][N + 1]);
    }

    public static void main(String[] args){
        MPP obj = new MPP(0, Math.PI / 2, 5, 0.1);
        System.out.println("Решение уравнения Фредгольма в узлах: ");
        obj.solveFIE(Math.PI / 4.4);
        System.out.println("Решение уравнения Вольтерра в узлах: ");
        obj.solveVIE(Math.PI / 4.4);
    }
}
