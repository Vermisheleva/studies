//Система:
// y * (x + 1) = 5
// y - 4 * x = 1
//Спасибо Ангелине!!!

public class Newton {
    final double EPS = Math.pow(10, -7);
    private double[] prev;
    private double[] next;
    private double res_norm;
    private double count;

    public Newton(double x0, double y0) {
        this.prev = new double[2];
        this.prev[0] = x0;
        this.prev[1] = y0;
        this.next = new double[2];
        this.count = 0;
    }

    public double det(double x, double y){
        return y + 4 * x + 4;
    }
    public double f1(double x, double y){
        return y * (x + 1) - 5;
    }
    public double f2(double x, double y){
        return y - 4 * x - 1;
    }

    public boolean continueIteration(){
        return Math.sqrt(Math.pow((next[0] - prev[0]), 2) + Math.pow((next[1] - prev[1]), 2)) > EPS;
    }

    public void solve(){
        while(continueIteration()){
            if(count != 0){
                prev[0] = next[0];
                prev[1] = next[1];
            }

            next[0] = prev[0] - (f1(prev[0], prev[1]) - (prev[0] + 1) * f2(prev[0], prev[1])) / det(prev[0], prev[1]);
            next[1] = prev[1] - (4 * f1(prev[0], prev[1]) + prev[1] * f2(prev[0], prev[1])) / det(prev[0], prev[1]);
            count++;
        }

        System.out.println("Решение: ");
        System.out.println("x = " + next[0]);
        System.out.println("y = " + next[1]);
        System.out.println("Количество итераций: ");
        System.out.println(count);
        System.out.println("Невязка: ");
        System.out.println(f1(next[0], next[1]) + " ");
        System.out.println(f2(next[0], next[1]));

        res_norm = Math.sqrt(Math.pow(f1(next[0], next[1]), 2) + Math.pow(f2(next[0], next[1]), 2));
        System.out.println("Норма невязки: ");
        System.out.println(res_norm);
    }

    public static void main(String[] args){
        Newton obj = new Newton(0.5, 3.2);
        obj.solve();
    }
}


