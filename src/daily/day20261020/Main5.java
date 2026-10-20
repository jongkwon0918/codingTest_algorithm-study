package daily.day20261020;

import java.util.*;

/*
5. 길을 건너지 않고 만날 수 있을까

n*n 목초지에서 소들은 상하좌우 인접 칸으로 자유롭게 이동할 수 있다.
다만 roads[i]={r1,c1,r2,c2}로 주어진 인접한 두 칸 사이에는 길이 놓여 있다.
그 사이를 직접 이동하려면 길을 건너야 한다. 길은 양방향으로 적용된다.
다른 경로로 돌아서 길을 한 번도 건너지 않고 이동할 수 있다면 만나도 된다.
cows[i]={행,열}은 소 한 마리의 위치이며 모든 소의 위치는 서로 다르다.
길을 하나 이상 건너지 않고서는 만날 수 없는 소의 쌍 개수를 반환하라.
(A,B)와 (B,A)는 같은 쌍이며 자기 자신은 세지 않는다.

제한사항
- 2 <= n <= 100
- 1 <= cows.length <= 100, cows.length <= n*n
- roads의 각 두 칸은 상하좌우로 인접한다.
- roads와 cows의 좌표는 모두 1 이상 n 이하이다.
- 길이 없는 두 인접 칸 사이에는 자유롭게 이동할 수 있다.

입출력 예시
n=3, roads={{2,2,2,3},{3,3,3,2},{3,3,2,3}},
cows={{3,3},{2,2},{2,3}} -> 2
(3,3)의 소가 다른 두 소와 길 없이 만나지 못한다.
n=2, roads={{1,1,1,2}}, cows={{1,1},{1,2}} -> 0
아래쪽 두 칸을 거쳐 돌아갈 수 있다.
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        int[][] roads = {{2, 2, 2, 3}, {3, 3, 3, 2}, {3, 3, 2, 3}};
        int[][] cows = {{3, 3}, {2, 2}, {2, 3}};
        System.out.println(solution.solution(3, roads, cows)); // 2
        System.out.println(solution.solution(
                2, new int[][]{{1, 1, 1, 2}}, new int[][]{{1, 1}, {1, 2}})); // 0
    }
}

class Solution5 {
    public int solution(int n, int[][] roads, int[][] cows) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

