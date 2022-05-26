public class Newton {
    private final double EPS = Math.pow(10, -10);
    private double prev;
    private double next;
    private int count;
    private double ans;
    double res;

    Newton(double x0){
        this.prev = x0;
    }

    public void method(){
        next = prev + (Math.exp(-2 * prev) - 2 * prev + 1)/(2 * Math.exp(-2 * prev) + 2);
        System.out.println(next);
        count = 1;

        while(Math.abs(next - prev) > EPS){
            prev = next;
            next = prev + (Math.exp(-2 * prev) - 2 * prev + 1)/(2 * Math.exp(-2 * prev) + 2);
            count++;
        }

        ans = next;
        res = Math.exp(-2 * ans) - 2 * ans + 1;

        System.out.println("Корень уравнения: " + ans);
        System.out.println("Количество итераций: " + count);
        System.out.println("Невязка: " + res);
    }

    public static void main(String[] args){
        Newton obj = new Newton(0.6);
        obj.method();
    }
}
