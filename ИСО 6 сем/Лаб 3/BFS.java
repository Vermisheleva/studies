import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BFS {
    private int n;
    private int[][] matr;
    private int[] is_visited;
    private Queue<Integer> queue;
    private int len;

    BFS(String filename) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(filename));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        this.n = Integer.parseInt(st.nextToken());
        this.matr = new int[n][n];
        this.is_visited = new int[n];
        this.queue = new LinkedList<>();

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine(), " ");
            for(int j = 0; j < n; j++){
                matr[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        this.len = 1;


    }

    public void BreadthFirstSearch(int start){
        queue.add(start);
        is_visited[start] = 1;
        int p;

        while(!queue.isEmpty()){
            p = queue.poll();

            for(int j = p; j < n; j++){
               if(matr[p][j] == 1 && is_visited[j] == 0){
                   is_visited[j] = 1;
                   queue.add(j);
               }
            }
        }


    }
    public void solve() throws IOException{
        FileWriter fw = new FileWriter("output.txt");

        this.BreadthFirstSearch(0);
        for(int i = 0; i < n; i++){
            if(is_visited[i] == 0){
                fw.write("NO");
                fw.close();
                return;
            }
        }

        fw.write("YES");
        fw.close();
    }
    public static void main(String[] args) throws IOException{
        BFS obj = new BFS("input.txt");
        obj.solve();
    }
}
