package daily.day20261007;

import java.util.*;

/*
1번. 촌수계산

1번부터 n번까지의 사람들과 부모·자식 관계가 주어진다.
relations[i] = {부모 번호, 자식 번호}이다.
부모와 자식 사이는 1촌이며, 관계를 따라 한 번 이동할 때마다 촌수가 1 증가한다.
두 사람 사이의 촌수를 반환하라. 친척 관계가 없으면 -1을 반환한다.

제한사항
- 1 <= n <= 100
- start와 target은 1 이상 n 이하의 서로 다른 번호
- 한 사람의 부모는 최대 한 명이며, 올바른 가족 관계가 주어진다.
- relations.length는 주어진 부모·자식 관계의 수이다.

입출력 예시
n=9, start=7, target=3
relations={{1,2},{1,3},{2,7},{2,8},{2,9},{4,5},{4,6}} -> 3
같은 관계에서 start=7, target=6 -> -1
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        try {
        int[][] relations = {
                {1, 2}, {1, 3}, {2, 7}, {2, 8}, {2, 9}, {4, 5}, {4, 6}
        };
        System.out.println(solution.solution(9, 7, 3, relations) + " / 기대값: 3");
        System.out.println(solution.solution(9, 7, 6, relations) + " / 기대값: -1");
        } catch (UnsupportedOperationException e) {
            System.out.println("미구현: Solution1을 작성한 뒤 다시 실행하세요.");
        }
    }
}

class Solution1 {
    public int solution(int n, int start, int target, int[][] relations) {
        List<Integer>[] graph = new ArrayList[n+1];
        for(int i=0; i<=n; i++){
            graph[i] = new ArrayList<>();
        }

        for(int[] relation : relations){
            int parentNum = relation[0];
            int childrenNum = relation[1];

            graph[parentNum].add(childrenNum);
            graph[childrenNum].add(parentNum);
        }

        boolean[] visited = new boolean[n+1];

        return dfs(start, target, 0, visited, graph);
    }

    private int dfs(int start, int target, int count, boolean[] visited, List<Integer>[] graph) {
        visited[start] = true;
        if(start==target){
            return count;
        }
        for(int next : graph[start]){
            if(!visited[next]){
                int answer =  dfs(next, target, count+1, visited, graph);
                if(answer != -1){
                    return answer;
                }
            }
        }

        return -1;
    }
}

