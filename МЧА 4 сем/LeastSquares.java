//интерпояционный многочлен 10 степени методом наименьших квадратов
public class LeastSquares {
    private double x0;
    private double h;
    private double[] f_x;
    private int n;
    private int m;
    private double[] coeff;
    private double[][] matr;
    private double[] matr_f;
    private double[] res;
    private double div;
    private double[] points;


    LeastSquares(double x0){
        this.x0 = x0;
        this.h = 0.1;
        this.n = 10;
        this.m = n / 2;
        this.f_x = new double[n + 1];
        this.coeff = new double[m + 1];
        this.matr = new double[m + 1][m + 1];
        this.matr_f = new double[m + 1];
        this.points = new double[3];
        this.res = new double[3];

    }

    public void calculateF_x(){
        for(int i = 0; i < 11; i++){
            double xi = x0 + i * h;
            f_x[i] = x0 * Math.exp(xi) + (1 - x0) * Math.cos(xi);
        }
    }

    public void calculatePoints(){
        points[0] = x0 + 2 * h / 3;
        points[1] = x0 + (n / 2) * h + h / 2;
        points[2] = x0 + n * h - h / 3;
    }

    public double matrElem(int i, int j) {
        double sum = 0;
        for (int k = 0; k < n + 1; k++) {
            sum += Math.pow(x0 + k * h, i + j);
        }
        return sum;
    }

    public double fElem(int i) {
        double sum = 0;
        for (int k = 0; k < n + 1; k++) {
            sum += f_x[k] * Math.pow(x0 + k * h, i);
        }
        return sum;
    }

    public void createMatrix(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                matr[i][j] = matrElem(i, j);
            }
            matr_f[i] = fElem(i);
        }
    }

    public void gaussMethod(){
        double temp1;
        double temp2;
        //прямой ход
        for (int k = 0; k < m + 1; k++){
            temp1 = matr[k][k];

            for(int j = 0; j < m + 1; j++) {
                matr[k][j] = matr[k][j] / temp1;
            }
            matr_f[k] /= temp1;

            for(int i = k + 1; i < m + 1; i++) {
                temp2 = matr[i][k];
                matr_f[i] = matr_f[i] - (matr_f[k] * temp2);

                for(int j = 0; j < m + 1; j++) {
                    matr[i][j] = matr[i][j] - (matr[k][j] * temp2);
                }

            }
        }//обратный ход
        coeff[m] = matr_f[m];

        for (int k = m - 1; k >= 0; k--){
            coeff[k] = matr_f[k];
            for (int j = k+1; j < m + 1; j++){
                coeff[k] -= matr[k][j] * coeff[j];
            }
        }

        System.out.println("Коэффициенты многочлена: ");
        for(double c : coeff){
            System.out.print(c + " ");
        }
        System.out.println();
    }

    public void pointValue(){
        for (int j = 0; j < 3; j++){
            System.out.println("Точка восстановления: " + points[j]);
            double val = 0;
            for(int i = 0; i < m + 1; i ++){
                val += coeff[i] * Math.pow(points[j], i);
            }
            System.out.println("Значение в точке: " + val);
            res[j] = Math.abs(x0 * Math.exp(points[j]) + (1 - x0) * Math.cos(points[j]) - val);
            System.out.println("Погрешность в точке: " + res[j]);
        }
    }
    public void calculateDivergence(){
        int sum = 0;
        for(int i = 0; i < n + 1; i++){
            double x = x0 + i * h;
            double val = 0;
            for(int j = 0; j < m + 1; j ++){
                val += coeff[j] * Math.pow(x, j);
            }
            sum += Math.pow((val - f_x[i]), 2);
        }
        div = Math.sqrt(sum);
        System.out.println("Погрешность: " + div);
    }

    public static void main(String[] args){
        LeastSquares obj = new LeastSquares(0.6);
        obj.calculateF_x();
        obj.calculatePoints();
        obj.createMatrix();
        obj.gaussMethod();
        obj.calculateDivergence();
        obj.pointValue();
    }
}
