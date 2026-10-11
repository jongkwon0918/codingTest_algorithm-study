package daily.day20261009;

import java.util.*;

/*
2. 정확한 거리의 도시

1부터 n까지의 도시와 단방향 도로가 있다. 모든 도로의 거리는 1이다.
x에서 출발했을 때 최단거리가 정확히 k인 도시 번호를 오름차순 배열로 반환하라.
해당 도시가 없으면 {-1}을 반환한다.
roads[i] = {a, b}는 a에서 b로 이동할 수 있다는 의미이다.
반대 방향 이동은 보장되지 않는다. 출발 도시까지의 최단거리는 0이다.

제한사항
- 2 <= n <= 300,000
- 1 <= roads.length <= 1,000,000
- 1 <= k <= 300,000, 1 <= x <= n
- 도로 양 끝의 도시 번호는 서로 다르다.

입출력 예시
n=4, roads={{1,2},{1,3},{2,3},{2,4}}, x=1
k=2 -> [4], k=1 -> [2,3], k=3 -> [-1]
도시 3에 길이 2인 경로가 있어도 최단거리는 1이므로 k=2의 답이 아니다.
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        int[][] roads = {{1, 2}, {1, 3}, {2, 3}, {2, 4}};
        System.out.println(Arrays.toString(solution.solution(4, roads, 2, 1))); // [4]
        System.out.println(Arrays.toString(solution.solution(4, roads, 1, 1))); // [2, 3]
        System.out.println(Arrays.toString(solution.solution(4, roads, 3, 1))); // [-1]
    }
}

class Solution2 {
    public int[] solution(int n, int[][] roads, int k, int x) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

