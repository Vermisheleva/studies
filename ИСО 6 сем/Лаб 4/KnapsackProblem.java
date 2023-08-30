public class KnapsackProblem {
    private int n;
    private int capacity;
    private int[] x;
    private int[] weight;
    private int[] price;
    private int[][] f;
    private int[][] p;

    KnapsackProblem(int n, int[] w, int[] p, int W){
        this.n = n;
        this.capacity = W;
        this.weight = w;
        this.price = p;
        this.f = new int[n + 1][W + 1];
        this.p = new int[n][W + 1];
        this.x = new int[n];
    }

    public int[][] getF() {
        return f;
    }

    public int[][] getP() {
        return p;
    }

    public void fill_table(){
        for (int i = 0; i <= capacity; i++){
            f[0][i] = 0;
        }

        for (int i = 1; i <= n; i++) {
            f[i][0] = 0;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= capacity; j++) {
                if (j >= weight[i - 1]) {
                    f[i][j] = Math.max(f[i - 1][j], f[i][j - weight[i - 1]] + price[i - 1]);
                }
               else{
                   f[i][j] = f[i - 1][j];
                }
                p[i - 1][j] = f[i][j] == f[i - 1][j] ? 0 : 1;
            }
        }
    }

    public void find_answer(){
        int W = capacity;
        int i = n - 1;

        while (W > 0 && i >= 0){
            while (p[i][W] != 0 && W != 0){
                x[i] += 1;
                W -= weight[i];
            }

            if (W == 0){
                return;
            }

            i--;
        }
    }

    public void print_data(int[][] arr){
        for (int[] row : arr){
            for (int elem : row) {
                System.out.print(elem + " ");
            }
            System.out.println();
        }
    }

    public void print_answer(){
        System.out.println("Answer: ");
        System.out.print("x: ");
        for (int elem : x){
            System.out.print(elem + " ");
        }
        System.out.println();
        System.out.println("f(x): " + f[n][capacity]);
    }

    public static void main(String[] args){
        int[] p = new int[] {3, 8, 13};
        int[] w =  new int[] {2, 3, 5};
        KnapsackProblem obj = new KnapsackProblem(3, w, p, 9);
        obj.fill_table();
        System.out.println("f: ");
        obj.print_data(obj.getF());
        System.out.println("p: ");
        obj.print_data(obj.getP());
        obj.find_answer();
        obj.print_answer();

    }
}


