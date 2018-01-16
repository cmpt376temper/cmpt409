import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by yilunq on 12/01/18.
 */
public class ErdosNumber {
    public static void main(String[] args) throws IOException {
//        int nameSize = 2;
//        String[] names = new String[nameSize];
//
//        names[0] = "Smith, M.N.";
//        names[1] = "Hsueh, Z";
//        names[1] = "Chen, X.";

        Map<String, Set<String>> graph = new HashMap<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine().trim();
        int scenarios = Integer.parseInt(line);
        for(int scenario = 0; scenario < scenarios; ++scenario) {
            System.out.println("Scenario " + (scenario + 1));
            String[] parts = br.readLine().trim().split("\\s+");
            int P = Integer.parseInt(parts[0].trim());
            int N = Integer.parseInt(parts[1].trim());

            for(int p = 0; p < P; ++p) {
                // 解析paper的构成
                parts = br.readLine().trim().split("\\.[,\\:]");
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim() + ".";
                }

                // 构建图
                initialGraph(graph, parts);
            }

            String[] inputNames = new String[N];
            for(int n = 0; n < N; ++n) {
                inputNames[n] = br.readLine().trim();
            }

//            System.out.println(Arrays.toString(inputNames));

            findErdosNumber(graph, inputNames);
        }
    }


    private static int findErdosNumber(Map<String, Set<String>> graph, String[] names) {
        for (int i = 0; i < names.length; i++) {
            int result = bfs(names[i], graph);
            if (result == -1) {
                System.out.println(names[i] + " infinity");
            } else {
                System.out.println(names[i] + " "+ result);
            }

//            System.out.println("*****");
        }

        return -1;
    }

    private static int bfs(String name, Map<String, Set<String>> graph) {
        Queue<String> queue = new LinkedList<>();
        queue.offer(name);
        int edrosNumber = 0;

        Set<String> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            if (edrosNumber > graph.size()) {
                return -1;
            }
            edrosNumber++;

            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String head = queue.poll();
                visited.add(head);
                // 若不存在图中
                if (graph.get(head) == null) {
                    break;
                }

                for (String neighbor : graph.get(head)) {
                    if (neighbor.equals("Erdos, P.")) {
                        return edrosNumber;
                    }
                    if (visited.contains(neighbor)) {
                        continue;
                    }
                    queue.offer(neighbor);
//                    System.out.println(neighbor);
                }
            }
        }
        return -1;
    }

    private static void initialGraph(Map<String, Set<String>> graph,
                              String[] papers) {
        // add other's names to its Set
        for (String author : papers) {
            if (!graph.containsKey(author)) {
                graph.put(author, new HashSet<String>());
            }
        }

        for (int i = 0; i < papers.length; i++) {
            for (int j = 0; j < papers.length; j++) {
                if (i != j) {
                    graph.get(papers[i]).add(papers[j]);
                }
            }
        }
    }
}
