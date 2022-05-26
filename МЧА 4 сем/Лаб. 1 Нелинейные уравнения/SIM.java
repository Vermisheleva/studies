public class SIM {
    private final double EPS = Math.pow(10, -7);
    private double prev;
    private double next;
    private int count;
    private double ans;
    private double res;

    SIM(double x0){
        this.prev = x0;
    }

    public void method(){
        next = (Math.exp(-2 * prev) + 1)/2;
        count = 1;

        while(Math.abs(next - prev) > EPS){
            prev = next;
            next = (Math.exp(-2 * prev) + 1)/2;
            count++;
        }
        ans = next;
        res = Math.exp(-2 * ans) - 2 * ans + 1;

        System.out.println("Корень уравнения: " + ans);
        System.out.println("Количество итераций: " + count);
        System.out.println("Невязка: " + res);
    }

    public static void main(String[] args){
        SIM obj = new SIM(0.6);
        obj.method();
    }
}
