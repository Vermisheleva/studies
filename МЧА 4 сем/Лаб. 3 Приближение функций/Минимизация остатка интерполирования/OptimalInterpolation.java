//интерполяционный многочлен Ньютона 10 степени, используя в качестве
//узлов интерполирования корни многочлена Чебышева
public class OptimalInterpolation {
    final double FACT_11 = 39916800;
    private int n;
    private double a;
    private double b;
    private double coeff;
    private double[] points;
    private double[] x;
    private double[] res;
    private double[][] div_differences;

    OptimalInterpolation(double a, double b){
        this.n = 10;
        this.x = new double[n + 1];
        this.points = new double[3];
        this.res = new double[3];
        this.div_differences = new double[n + 1][n + 1];
        this.a = a;
        this.b = b;
    }

    public void calculatePoints(){
        points[0] = a + 2 * 0.1 / 3;
        points[1] = a + (n / 2) * 0.1 + 0.1 / 2;
        points[2] = a + n * 0.1 - 0.1 / 3;

        coeff =  (a * Math.exp(b) + (1 - a) * Math.sin(b)) / FACT_11;
    }

    public void pointValues(){
        for (int j = 0; j < 3; j++){
            System.out.println("Точка восстановления: " + points[j]);
            double val = 0;
            double w = 1;
            for(int i = 0; i < n + 1; i ++){
                val += div_differences[0][i] * w;
                w *= (points[j] - x[i]);
            }
            System.out.println("Значение в точке: " + val);

            double est_res_at_point = Math.abs(w * coeff);
            System.out.println("Оценка погрешности в точке: " + est_res_at_point);

            res[j] = Math.abs(a * Math.exp(points[j]) + (1 - a) * Math.cos(points[j]) - val);
            System.out.println("Истинная погрешность в точке: " + res[j]);

            System.out.println();

        }
        double est_res = coeff * Math.pow((b - a), (n - 1)) / Math.pow(2, (2 * n + 1));
        System.out.println("Оценка остатка интерполирования на всем отрезке: " + est_res);
    }

    public void createDivDifferencesMatrix(){
        double coeff1 = (a + b) / 2;
        double coeff2 = (a - b) / 2;

        for(int i = 0; i < n + 1; i++){
            x[i] = coeff1 + coeff2 * Math.cos((2 * i + 1) * Math.PI / (2 * (n + 1)));
            div_differences[i][0] = a * Math.exp(x[i]) + (1 - a) * Math.cos(x[i]);
        }

        int p = 1;
        int k = n;
        for(int j = 1; j < n + 1; j++) {
            for (int i = 0; i < k; i++) {
                div_differences[i][j] = (div_differences[i][j - 1] - div_differences[i + 1][j - 1])
                        / (x[i] - x[i + p]);

            }
            p++;
            k--;
        }


        System.out.println("Таблица разделенных разностей: ");
        for(int i = 0; i < n + 1; i++){
            for(int j = 0; j < n + 1; j++){
                System.out.print(div_differences[i][j] + " ");
            }
            System.out.println();
        }


    }

    public static void main(String[] args){
        OptimalInterpolation obj = new OptimalInterpolation(0.6, 1.6);
        obj.calculatePoints();
        obj.createDivDifferencesMatrix();
        obj.pointValues();
    }
}

