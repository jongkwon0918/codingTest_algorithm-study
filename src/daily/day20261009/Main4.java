package daily.day20261009;

import java.util.*;

/*
4. 친구를 데리고 갈 수 있을까

1부터 n까지의 정점 사이에 양방향 도로가 있다.
민준이는 1번에서 출발해 n번으로 최단거리로 이동하려고 한다.
친구 건우는 p번 정점에 있다.
최단 경로 중 p번을 지나는 경로가 하나라도 있으면 "SAVE HIM",
없다면 "GOOD BYE"를 반환하라.
roads[i] = {a, b, c}는 a와 b를 연결하는 길이 c의 양방향 도로이다.

제한사항
- 2 <= n <= 5,000, 1 <= roads.length <= 10,000
- 1 <= p <= n, 1 <= c <= 10,000
- 중복 간선과 자기 자신을 연결하는 간선은 없다.
- 1번에서 p번과 n번으로 이동할 수 있다.

입출력 예시
n=6, roads={{1,2,1},{1,3,1},{2,3,10},{3,4,1},
           {3,5,2},{4,5,1},{5,6,1}}
p=4 -> "SAVE HIM", p=2 -> "GOOD BYE"
p=4일 때 1->3->4->5->6의 거리 4는 전체 최단거리와 같다.
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        int[][] roads = {
            {1, 2, 1}, {1, 3, 1}, {2, 3, 10}, {3, 4, 1},
            {3, 5, 2}, {4, 5, 1}, {5, 6, 1}
        };
        System.out.println(solution.solution(6, roads, 4)); // SAVE HIM
        System.out.println(solution.solution(6, roads, 2)); // GOOD BYE
    }
}

class Solution4 {
    public String solution(int n, int[][] roads, int p) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

