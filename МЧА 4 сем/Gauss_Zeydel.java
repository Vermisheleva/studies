//Система:
// y * (x + 1) = 5
// y - 4 * x = 1
//(вырожденный случай)
//Спасибо Ангелине!!!

public class Gauss_Zeydel {
    final double EPS = Math.pow(10, -7);
    private double[] prev;
    private double[] next;
    private double res;
    private int count;

    Gauss_Zeydel(double x0, double y0){
        this.prev = new double[2];
        this.next = new double[2];
        this.next[0] = x0;
        this.next[1] = y0;
        this.count = 0;
    }

    public void calculateResidualsNorm(){
        System.out.println("Невязка: ");
        System.out.println((next[1] * (next[0] + 1) - 5) + " ");
        System.out.println(next[1] - 4 * next[0] - 1);

        double a = Math.pow((next[1] * (next[0] + 1) - 5), 2);
        double b = Math.pow((next[1] - 4 * next[0] - 1), 2);

        res = Math.sqrt(a + b);
        System.out.println("Норма невязки: ");
        System.out.println(res);
    }

    public boolean continueIteration(){
        return Math.sqrt(Math.pow((next[0] - prev[0]), 2) + Math.pow((next[1] - prev[1]), 2)) > EPS;
    }

    public void solve() {
        while(continueIteration()) {
            prev[0] = next[0];
            prev[1] = next[1];

            next[0] = (prev[1] - 1) / 4;
            next[1] = 5 / (next[0] + 1);
            count++;
        }

        System.out.println("Решение: ");
        System.out.println("x = " + next[0]);
        System.out.println("y = " + next[1]);
        System.out.println("Количество итераций: ");
        System.out.println(count);

        this.calculateResidualsNorm();
    }

    public static void main(String[] args){
        Gauss_Zeydel obj = new Gauss_Zeydel(0.5, 3.2);
        obj.solve();
    }
}
