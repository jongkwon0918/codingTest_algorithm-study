package daily.day20261101;

import java.util.*;

/*
1. 서로 다른 다섯 사람의 연결

사람 n명은 0번부터 n-1번까지 번호가 붙어 있다.
friendships[i]={a,b}이면 두 사람이 서로 친구이다.
서로 다른 다섯 사람 A,B,C,D,E를 골라 다음 네 관계를 모두 만족할 수 있는지 판단하라.
A와 B는 친구, B와 C는 친구, C와 D는 친구, D와 E는 친구이다.
다섯 명이 모두 서로 친구일 필요는 없고, 다른 친구 관계가 추가로 있어도 된다.
가능하면1, 불가능하면0을 반환하라. 같은 사람을 두 번 사용하는 것은 안 된다.

제한사항
- 5 <= n <= 2000
- 1 <= friendships.length <= 2000
- 0 <= a,b < n이며 a와 b는 서로 다르다.
- 같은 친구 관계는 중복해서 주어지지 않는다. 관계는 양방향이다.

입출력 예시
n=5, friendships={{0,1},{1,2},{2,3},{3,4}} -> 1
0->1->2->3->4로 다섯 명을 연결할 수 있다.
n=5, friendships={{0,1},{0,2},{0,3},{0,4}} -> 0
다섯 명이 한 묶음이어도 같은 사람을 재사용하지 않고 다섯 명을 이어 갈 수는 없다.
n=6, friendships={{0,1},{1,2},{2,0},{3,4}} -> 0
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        System.out.println(solution.solution(5,
                new int[][]{{0,1},{1,2},{2,3},{3,4}})); // 1
        System.out.println(solution.solution(5,
                new int[][]{{0,1},{0,2},{0,3},{0,4}})); // 0
        System.out.println(solution.solution(6,
                new int[][]{{0,1},{1,2},{2,0},{3,4}})); // 0
    }
}

class Solution1 {
    public int solution(int n, int[][] friendships) {
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=-0; i<n; i++){
            graph.add(new ArrayList<>());
        }
        for(int[] friendship : friendships){
            int u = friendship[0];
            int v = friendship[1];

            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        for(int i=0; i<n; i++){
            boolean[] visited = new boolean[n];
            boolean cycle = dfs(i, graph, n, visited, 1);
            if(cycle) return 1;
        }

        return 0;
    }

    private boolean dfs(int i, List<List<Integer>> graph, int n, boolean[] visited, int num) {
        visited[i]=true;

        if(num==5){
            return true;
        }

        for(int next : graph.get(i)){
            if(!visited[next]){
                if(dfs(next, graph, n, visited, num+1)){
                    return true;
                }
            }
        }

        visited[i] = false;
        return false;
    }
}

