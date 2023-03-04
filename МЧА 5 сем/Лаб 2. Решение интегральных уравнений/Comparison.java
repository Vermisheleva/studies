public class Comparison {
    private double[] yFIE1;
    private double[] yFIE2;
    private double[] yVIE1;
    private double[] yVIE2;

    public Comparison(double[] yFIE1, double[] yFIE2, double[] yVIE1, double[] yVIE2) {
        this.yFIE1 = yFIE1;
        this.yFIE2 = yFIE2;
        this.yVIE1 = yVIE1;
        this.yVIE2 = yVIE2;
    }

    public void compare(){
        System.out.println("Решение уравнения Фредгольма в узлах: ");
        System.out.print("Метод механических квадратур  ");
        System.out.print("Метод последовательных приближений  ");
        System.out.print("Разность");
        System.out.println();
        String format = "%-30s%-36s%-36s%n";

        int n = yFIE1.length - 1;
        for(int i = 0; i < n; i++){
            System.out.printf(format, yFIE1[i], yFIE2[i], (yFIE1[i] - yFIE2[i]));
        }
        System.out.println("Решение уравнения Фредгольма в точке х*: ");
        System.out.printf(format, yFIE1[n], yFIE2[n], (yFIE1[n] - yFIE2[n]));
        System.out.println();

        System.out.println("Решение уравнения Вольтерра в узлах: ");
        System.out.print("Метод механических квадратур  ");
        System.out.print("Метод последовательных приближений  ");
        System.out.print("Разность");
        System.out.println();

        for(int i = 0; i < n; i++){
            System.out.printf(format, yVIE1[i], yVIE2[i], (yVIE1[i] - yVIE2[i]));
        }

        System.out.println("Решение уравнения Вольтерра в точке х*: ");
        System.out.printf(format, yVIE1[n], yVIE2[n], (yVIE1[n] - yVIE2[n]));
    }

    public static void main(String[] args){
        MMK obj = new MMK(0, Math.PI / 2, 10, 0.1);
        obj.fillDataForFIE();

        System.out.println("Решение уравнения Фредгольма в узлах: ");
        obj.gaussMethod(obj.getyFIE());
        System.out.println("Решение уравнения Фредгольма в точке х*: ");
        obj.findValueInPointForFIE(Math.PI / 4.4);
        obj.fillDataForVIE();
        System.out.println("Решение уравнения Вольтерра в узлах: ");
        obj.gaussMethod(obj.getyVIE());
        System.out.println("Решение уравнения Вольтерра в точке х*: ");
        obj.findValueInPointForVIE(Math.PI / 4.4);

        MPP obj1 = new MPP(0, Math.PI / 2, 5, 0.1);
        System.out.println("Решение уравнения Фредгольма в узлах: ");
        obj1.solveFIE(Math.PI / 4.4);
        System.out.println("Решение уравнения Вольтерра в узлах: ");
        obj1.solveVIE(Math.PI / 4.4);
        System.out.println();

        Comparison comp = new Comparison(obj.getyFIE(), obj1.getyFIE()[1], obj.getyVIE(), obj1.getyVIE()[1]);
        comp.compare();
    }
}
