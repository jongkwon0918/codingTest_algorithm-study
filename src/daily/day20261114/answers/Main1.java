package daily.day20261114.answers;

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
    public int solution(String[] friends) {
        int n = friends.length;
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (friends[i].charAt(j) == 'Y') {
                    graph[i].add(j);
                }
            }
        }
        int answer = 0;
        for (int start = 0; start < n; start++) {
            int[] dist = new int[n];
            Arrays.fill(dist, -1);
            Queue<Integer> queue = new LinkedList<>();
            dist[start] = 0;
            queue.offer(start);
            int count = 0;
            while (!queue.isEmpty()) {
                int current = queue.poll();
                if (dist[current] == 2) {
                    continue;
                }
                for (int next : graph[current]) {
                    if (dist[next] == -1) {
                        dist[next] = dist[current] + 1;
                        count++;
                        queue.offer(next);
                    }
                }
            }
            answer = Math.max(answer, count);
        }
        return answer;
    }
}

