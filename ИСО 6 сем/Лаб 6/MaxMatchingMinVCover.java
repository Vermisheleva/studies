import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


public class MaxMatchingMinVCover {
    private int n;
    private int k;
    List[] list;
    private int[] is_visited;
    private int[] marks;
    private int[] matching;


    MaxMatchingMinVCover(String filename) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(filename));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        //!! Количество вершин 1-ой доли
        this.n = Integer.parseInt(st.nextToken());
        //!! Количество вершин 2-ой доли
        this.k = Integer.parseInt(st.nextToken());

        this.is_visited = new int[n + k];
        this.marks = new int[n + k];
        this.matching = new int[k + n];
        this.list = new List [n + k];
        for(int i = 0; i < n + k; i++){
            list[i] = new ArrayList<Integer>();
        }

        // Первая доля - которая с 0, во входном файле номер вершины 1-ой доли и номера смежных вершин 2-ой доли
        String line = br.readLine();
        while(line != null){
            st = new StringTokenizer(line, " ");
            int i = Integer.parseInt(st.nextToken());

            while(st.hasMoreTokens()){
                int v = Integer.parseInt(st.nextToken());
                list[i].add(v);
            }
            line = br.readLine();
        }
    }


    public void print_lists(){
        for(int i = 0; i < n + k; i++){
            for(int j = 0; j < list[i].size(); j++)
                System.out.print(list[i].get(j) + " ");
            System.out.println();
        }
    }


    public boolean kuhn_algorithm(int v){
        if (is_visited[v] == 1)
            return false;

        is_visited[v] = 1;

        for (int i = 0; i < list[v].size(); i++) {
            int t = Integer.parseInt(list[v].get(i).toString());

            if (matching[t] == -1 || kuhn_algorithm(matching[t])) {
                matching[t] = v;
                return true;
            }
        }
        return false;
    }


    public void DepthFirstSearch (int start) {
        is_visited[start] = 1;

        for (int i = 0; i < list[start].size(); i++) {
            int v = Integer.parseInt(list[start].get(i).toString());
            if (is_visited[v] == 0) {
                DepthFirstSearch(v);
            }
        }
    }


    public void min_vertex_cover(){
        Arrays.fill(marks, 1);
        for (int i = 0; i < n; i++){
            for(int j = 0; j < list[i].size(); j++){
                int v = Integer.parseInt(list[i].get(j).toString());

                if(matching[v] != i) {
                    list[v].add(i);
                }
                else{
                    marks[v] = 0;
                }
            }
        }

        Arrays.fill(is_visited, 0);
        for(int i = n; i < n + k; i++){
            if(marks[i] == 1 && is_visited[i] == 0)
                DepthFirstSearch(i);

        }

        System.out.println("Minimum Vertex Cover:");
        for(int i = 0; i < n; i++){
            if (is_visited[i] == 1){
                System.out.print(i + " ");
            }
        }

        for(int i = n - 1; i < n + k; i++){
            if(is_visited[i] == 0){
                System.out.print(i + " ");
            }
        }
    }


    public void solve(){
        Arrays.fill(matching, -1);

        for (int v = 0; v < n; v++) {
           Arrays.fill(is_visited, 0);
           kuhn_algorithm(v);
        }

        System.out.println("Maximum matching:");
        for (int i = 0; i < k + n; i++)
            if (matching[i] != -1){
                System.out.println(matching[i] + " " + i);
            }

        min_vertex_cover();
    }


    public static void main(String[] args) throws IOException{
        MaxMatchingMinVCover obj = new MaxMatchingMinVCover("input.txt");
        //obj.print_lists();
        obj.solve();
    }
}
