package daily.day20261024;

import java.util.*;

/*
3. 최소 비용과 실제 이동 경로

1부터 n까지의 도시와 단방향 버스 노선이 있다.
buses[i]={from,to,cost}는 from에서 to로 가는 버스와 비용을 의미한다.
start에서 target까지의 최소 비용과, 그 비용으로 이동하는 실제 도시 경로를 반환하라.
반환 형식은 answer[0]={최소 비용, 경로에 포함된 도시 수},
answer[1]={출발 도시,...,도착 도시}이다.
도시 수에는 출발과 도착도 포함한다.
최소 비용 경로가 여러 개면 그중 아무 경로나 반환해도 된다.
같은 두 도시 사이에 여러 버스가 있을 수 있고 반대 방향 이동은 보장되지 않는다.

제한사항
- 1 <= n <= 1,000, 1 <= buses.length <= 100,000
- 도시 번호는 1 이상 n 이하이다.
- 0 <= cost < 100,000
- start에서 target까지 이동할 수 있다.

입출력 예시
n=4, buses={{1,2,2},{2,4,3},{1,3,1},{3,4,8}}, start=1,target=4
-> {{5,3},{1,2,4}}
n=3, buses={{1,2,0},{2,3,4},{1,3,9}}, start=1,target=3
-> {{4,3},{1,2,3}}
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        System.out.println(Arrays.deepToString(solution.solution(
                4, new int[][]{{1,2,2},{2,4,3},{1,3,1},{3,4,8}}, 1, 4)));
        // [[5, 3], [1, 2, 4]]
        System.out.println(Arrays.deepToString(solution.solution(
                3, new int[][]{{1,2,0},{2,3,4},{1,3,9}}, 1, 3)));
        // [[4, 3], [1, 2, 3]]
    }
}

class Solution3 {
    public int[][] solution(int n, int[][] buses, int start, int target) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

