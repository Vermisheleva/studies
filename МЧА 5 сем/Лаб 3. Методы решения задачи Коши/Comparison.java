public class Comparison {
    private double[] yEuler;
    private double[] yTrapezoid;
    private double[] yLmm;
    private double[] yMpppt;

    public Comparison(double[] yEuler, double[] yTrapezoid, double[] yLmm, double[] yMpppt) {
        this.yEuler = yEuler;
        this.yTrapezoid = yTrapezoid;
        this.yLmm = yLmm;
        this.yMpppt = yMpppt;
    }

    public void compare(){
        System.out.println("Решение задачи Коши в узлах: ");
        System.out.print("Неявный метод Эйлера  ");
        System.out.print("Экстраполяционный метод Адамса  ");
        System.out.print("Разность");
        System.out.println();
        String format = "%-22s%-32s%-32s%n";

        int n = yEuler.length - 1;
        for(int i = 0; i < n; i++){
            System.out.printf(format, yEuler[i], yLmm[i], (yEuler[i] - yLmm[i]));
        }
        System.out.println();

        System.out.println("Решение задачи Коши в узлах: ");
        System.out.print("Метод последовательного повышения порядка точности  ");
        System.out.print("Экстраполяционный метод Адамса  ");
        System.out.print("Разность");
        System.out.println();
        format = "%-52s%-32s%-32s%n";

        for(int i = 0; i < n; i++){
            System.out.printf(format, yMpppt[i], yLmm[i], (yMpppt[i] - yLmm[i]));
        }
        System.out.println();

        System.out.println("Решение задачи Коши в узлах: ");
        System.out.print("Метод Рунге-Кутта       ");
        System.out.print("Экстраполяционный метод Адамса  ");
        System.out.print("Разность");
        System.out.println();
        format = "%-24s%-32s%-32s%n";

        for(int i = 0; i < n; i++){
            System.out.printf(format, yTrapezoid[i], yLmm[i], (yTrapezoid[i] - yLmm[i]));
        }

    }

    public static void main(String[] args){
        EulerMethod euler = new EulerMethod(10, 1., 1.5, 0.);
        euler.solve();
        MPPPT mpppt = new MPPPT(10, 1., 1.5, 0.);
        mpppt.solve();
        LMM lmm = new LMM(10, 1., 1.5, 0.);
        lmm.solve();
        TrapezoidalRule trapezoid = new TrapezoidalRule(10, 1., 1.5, 0.);
        trapezoid.solve();

        Comparison comparison = new Comparison(euler.getY(), trapezoid.getY(), lmm.getY(), mpppt.getY());
        comparison.compare();


    }

}
