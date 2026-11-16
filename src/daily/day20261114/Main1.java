package daily.day20261114;

import java.util.*;

/*
1. 친구의 친구까지 세기

사람은 0번부터 n-1번까지이며 friends[i].charAt(j)가 'Y'이면 i와 j는 직접 친구다.
두 사람이 직접 친구이거나, 두 사람 모두와 직접 친구인 사람이 한 명 이상 있으면
서로 '2-친구'라고 한다. 즉 친구 관계를 한 번 또는 두 번 이어 도달하는 다른 사람이다.
같은 사람을 여러 경로로 알게 되어도 한 번만 세며 자기 자신은 제외한다.
각 사람의 2-친구 수 중 최댓값을 반환하라. 사람 번호를 반환하는 문제가 아니다.
세 번 이상 관계를 이어야만 만나는 사람은 세지 않는다.

제한사항
- 1 <= friends.length <= 50
- 각 문자열 길이는 n이고 문자는 'Y' 또는 'N'이다.
- 자기 자신과는 친구가 아니므로 friends[i].charAt(i)는 'N'이다.
- 친구 관계는 양방향이다.

입출력 예시
{"NYNNN","YNYNN","NYNYN","NNYNY","NNNYN"} -> 4
관계는 0-1-2-3-4다. 2번의 2-친구는 0,1,3,4로 4명이다.
0번은 1,2만 포함하고 3,4는 포함하지 않는다.
{"NNN","NNN","NNN"} -> 0
{"NYY","YNY","YYN"} -> 2
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        System.out.println(solution.solution(new String[]{
            "NYNNN","YNYNN","NYNYN","NNYNY","NNNYN"
        })); // 4
        System.out.println(solution.solution(new String[]{"NNN","NNN","NNN"})); // 0
        System.out.println(solution.solution(new String[]{"NYY","YNY","YYN"})); // 2
    }
}

class Solution1 {
    int n;
    int m;
    int maxHaveFriend;
    public int solution(String[] friends) {
        n = friends.length;
        m = friends[0].length();
        maxHaveFriend=0;
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0; i<n; i++){
            graph.add(new ArrayList<>());
        }
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(friends[i].charAt(j)=='Y'){
                    graph.get(i).add(j);
                    graph.get(j).add(i);
                }
            }
        }

        for(int i=0; i<n; i++){
            bfs(graph, i);
        }

        return maxHaveFriend;
    }

    private void bfs(List<List<Integer>> graph, int i) {
        boolean[] visited = new boolean[n];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, 0});
        visited[i]=true;

        int haveFriend = 0;

        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int currStart = curr[0];
            int currCost = curr[1];

            for(int next : graph.get(currStart)){
                int nextCost = currCost+1;
                if(!visited[next] && nextCost<=2){
                    queue.offer(new int[]{next, nextCost});
                    visited[next]=true;
                    haveFriend++;
                }
            }
        }

        maxHaveFriend = Math.max(maxHaveFriend, haveFriend);
    }
}

