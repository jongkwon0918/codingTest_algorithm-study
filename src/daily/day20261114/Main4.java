package daily.day20261114;

import java.util.*;

/*
4. 순환 노선까지 몇 정거장

1번부터 n번까지 역과 양방향 구간 n개가 있다. connections[i]={a,b}이다.
전체 노선은 연결되어 있고 고리는 정확히 하나다. 고리에 속하지 않는 역들은 가지처럼 이어져 있다.
고리는 같은 구간을 되돌아가지 않고 여러 역을 거쳐 출발 역으로 돌아올 수 있는 노선이다.
이 문제에서는 자기 연결과 중복 구간이 없으므로 고리는 최소 서로 다른 역3개와 구간3개로 이루어진다.
예: 1 -> 2 -> 3 -> 1은 고리다. 출발 역을 제외한 중간 역은 중복하지 않는다.
반면 1 -> 2 -> 1은 같은 구간을 왕복한 것이므로 고리가 아니다.
각 역에서 고리에 속한 역까지 이동해야 하는 최소 구간 수를 구하라.
고리에 속한 역의 답은 0이다. 역1번부터 n번 순서로 길이 n의 배열을 반환하라.
구간마다 이동 비용은1이며 자기 자신으로 연결된 구간과 중복 구간은 없다.

제한사항
- 3 <= n <= 3000
- connections.length == n
- 1 <= a,b <= n, a != b
- 모든 역은 서로 이동할 수 있고 고리는 정확히 하나다.

입출력 예시
n=6, connections={{1,2},{2,3},{3,1},{3,4},{4,5},{2,6}} -> {0,0,0,1,2,1}
1-2-3-1이 고리이고 5번은 4번을 거쳐3번까지 두 구간이다.
n=3, connections={{1,2},{2,3},{3,1}} -> {0,0,0}
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        System.out.println(Arrays.toString(solution.solution(6, new int[][]{
            {1,2},{2,3},{3,1},{3,4},{4,5},{2,6}
        }))); // [0, 0, 0, 1, 2, 1]
        System.out.println(Arrays.toString(solution.solution(3,
                new int[][]{{1,2},{2,3},{3,1}}))); // [0, 0, 0]
    }
}

class Solution4 {
    boolean[] visited;
    public int[] solution(int n, int[][] connections) {
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0; i<=n; i++){
            graph.add(new ArrayList<>());
        }
        for(int[] conn : connections){
            int u = conn[0];
            int v = conn[1];

            graph.get(u).add(v);
            graph.get(v).add(u);
        }


        List<Integer> list = new ArrayList<>();

        for(int i=1; i<=n; i++){
            list = new ArrayList<>();
            visited = new boolean[n+1];
            list.add(i);
            boolean findStation = dfs(graph, i, i, list, 1);
            if(findStation){
                break;
            }
        }

        int[] answer = new int[n];
        for(int i=0; i<n; i++){
            answer[i] = bfs(i+1, 0, n, list, graph);
        }

        return answer;
    }

    private int bfs(int i, int length, int n, List<Integer> list, List<List<Integer>> graph) {
        visited = new boolean[n+1];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, length});
        visited[i]=true;

        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int currStart = curr[0];
            int currLength = curr[1];

            if(list.contains(currStart)){
                return currLength;
            }

            for(int next : graph.get(currStart)){
                if(!visited[next]){
                    queue.offer(new int[]{next, currLength+1});
                    visited[next]=true;
                }
            }
        }
        return 0;
    }

    private boolean dfs(List<List<Integer>> graph, int i, int first, List<Integer> list, int curr) {
        visited[i]=true;

        for(int next : graph.get(i)){
            if(!visited[next]){
                list.add(next);
                if(dfs(graph, next, first, list, curr+1)){
                    return true;
                }
                list.remove(list.size()-1);
                visited[next]=false;
            }else if(curr>=3 && first==next){
                return true;
            }
        }

        return false;
    }
}

