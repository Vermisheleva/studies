//строим интерполяционный многочлен Ньютона на равноотстоящей сетке узлов
//3 степени для интерполирования в конце таблицы
public class EquidistantNodes {
        private int k;
        private double n;
        private double a;
        private double b;
        private double h;
        private double point;
        private double[] x;
        private double t;
        private double res;
        private double[][] finite_differences;

        EquidistantNodes(double a, double b){
            this.k = 3;
            this.n = 10;
            this.h = 0.1;
            this.x = new double[k + 1];
            this.finite_differences = new double[k + 1][k + 1];
            this.a = a;
            this.b = b;
        }

        public void calculatePoints(){
            point = a + n * 0.1 - 0.1 / 3;

            for(int i = k; i >= 0; i--){
                x[k - i] = b - h * i;
            }

            t = (point - x[k]) / h;

        }

        public void pointValue(){
                System.out.println("Точка восстановления: " + point);
                double val = 0;
                double coeff = 1;
                for(int i = 0; i < k + 1; i ++){
                    val += finite_differences[k - i][i] * coeff;
                    coeff *= (t + i) / (i + 1);
                }
                System.out.println("Значение в точке: " + val);

                double Df_max_value = finite_differences[k][0];

                double est_res_at_point = Math.abs(Math.pow(h, k + 1) * coeff * Df_max_value);
                System.out.println("Оценка погрешности в точке: " + est_res_at_point);

                res = Math.abs(a * Math.exp(point) + (1 - a) * Math.cos(point) - val);
                System.out.println("Истинная погрешность в точке: " + res);

            }

        public void createFiniteDifferencesMatrix(){
            for(int i = 0; i < k + 1; i++){
                finite_differences[i][0] = a * Math.exp(x[i]) + (1 - a) * Math.cos(x[i]);
            }

            int m = k;
            for(int j = 1; j < k + 1; j++) {
                for (int i = 0; i < m; i++) {
                    finite_differences[i][j] = finite_differences[i + 1][j - 1] - finite_differences[i][j - 1];

                }
                m--;
            }
            
            System.out.println("Таблица конечных разностей: ");
            for(int i = 0; i < k + 1; i++){
                for(int j = 0; j < k + 1; j++){
                    System.out.print(finite_differences[i][j] + " ");
                }
                System.out.println();
            }
        }

        public static void main(String[] args){
            EquidistantNodes obj = new EquidistantNodes(0.6, 1.6);
            obj.calculatePoints();
            obj.createFiniteDifferencesMatrix();
            obj.pointValue();
        }
    }


