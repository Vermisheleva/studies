public class NewtonPolynomial {
    private int n;
    private double h;
    private double[] points;
    private double[] x;
    private double[] res;
    private double[][] div_differences;

    NewtonPolynomial(double x0){
        this.n = 10;
        this.h = 0.1;
        this.x = new double[n + 2];
        this.x[0] = x0;
        this.points = new double[3];
        this.res = new double[3];
        this.div_differences = new double[n + 2][n + 2];
    }

    public void calculatePoints(){
        points[0] = x[0] + 2 * h / 3;
        points[1] = x[0] + (n / 2) * h + h / 2;
        points[2] = x[0] + n * h - h / 3;
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
            res[j] = Math.abs(x[0] * Math.exp(points[j]) + (1 - x[0]) * Math.cos(points[j]) - val);
            System.out.println("Истинная погрешность в точке: " + res[j]);
            double est_res = Math.abs(w * div_differences[0][n + 1]);
            System.out.println("Остаток интерполирования в точке: " + est_res);
            if(j != 2){
                recalculateDivDifferences(points[j + 1]);
            }
        }
    }

    public void recalculateDivDifferences(double val) {
        div_differences[n + 1][0] = x[0] * Math.exp(val) + (1 - x[0]) * Math.cos(val);
        x[n + 1] = val;

        int p = 1;
        int k = n;

        for (int j = 1; j < n + 2; j++) {
         div_differences[k][j] = (div_differences[k][j - 1] - div_differences[k + 1][j - 1])
                        / (x[k] - x[k + p]);

            p++;
            k--;
        }
    }
    public void createDivDifferencesMatrix(){
        for(int i = 0; i < n + 1; i++){
            x[i] = x[0] + i * h;
            div_differences[i][0] = x[0] * Math.exp(x[i]) + (1 - x[0]) * Math.cos(x[i]);
        }
        div_differences[n + 1][0] = x[0] * Math.exp(points[0]) + (1 - x[0]) * Math.cos(points[0]);
        x[n + 1] = points[0];

        int p = 1;
        int k = n + 1;
        for(int j = 1; j < n + 2; j++) {
            for (int i = 0; i < k; i++) {
                div_differences[i][j] = (div_differences[i][j - 1] - div_differences[i + 1][j - 1])
                        / (x[i] - x[i + p]);

            }
            p++;
            k--;
        }


        /*System.out.println("Таблица разделенных разностей: ");
        for(int i = 0; i < n + 1; i++){
            for(int j = 0; j < n + 1; j++){
                System.out.print(div_differences[i][j] + " ");
            }
            System.out.println();
        }

        */
    }

    public static void main(String[] args){
        NewtonPolynomial obj = new NewtonPolynomial(0.6);
        obj.calculatePoints();
        obj.createDivDifferencesMatrix();
        obj.pointValues();
    }
}
