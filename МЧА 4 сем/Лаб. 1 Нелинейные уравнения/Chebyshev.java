public class Chebyshev {
    private final double EPS = Math.pow(10, -10);
    private double prev;
    private double next;
    private int count;
    private double ans;
    double res;

    Chebyshev(double x0){
        this.prev = x0;
    }

    public void method(){
        double f = Math.exp(-2 * prev) - 2 * prev + 1;
        double f_ = - 2 * Math.exp(-2 * prev) - 2;
        double f__ = 4 * Math.exp(-2 * prev);

        next = prev - (f / f_) - (f__ * Math.pow(f, 2)) / (2 * Math.pow(f_, 3));
        count = 1;

        while(Math.abs(next - prev) > EPS){
            prev = next;

            f = Math.exp(-2 * prev) - 2 * prev + 1;
            f_ = - 2 * Math.exp(-2 * prev) - 2;
            f__ = 4 * Math.exp(-2 * prev);

            next = prev - (f / f_) - (f__ * Math.pow(f, 2)) / (2 * Math.pow(f_, 3));
            count++;
        }

        ans = next;
        res = Math.exp(-2 * ans) - 2 * ans + 1;

        System.out.println("Корень уравнения: " + ans);
        System.out.println("Количество итераций: " + count);
        System.out.println("Невязка: " + res);
    }

    public static void main(String[] args){
        Chebyshev obj = new Chebyshev(0.6);
        obj.method();
    }
}
