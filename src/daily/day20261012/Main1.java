package daily.day20261012;

import java.util.*;

/*
1. 두 가지 방문 순서

정점 번호는 1부터 n까지이고 edges[i]={u,v}는 양방향 간선이다.
start에서 다음 두 규칙으로 탐색한 방문 순서를 각각 반환한다.
첫 번째: 현재 정점의 미방문 이웃 중 번호가 작은 정점으로 들어가 같은 규칙을 반복한다.
더 갈 곳이 없으면 이전 정점으로 돌아와 남은 이웃을 탐색한다.
두 번째: 시작 정점을 발견하고, 발견한 정점들을 발견 순서대로 처리한다.
각 정점을 처리할 때 미방문 이웃을 번호가 작은 순서로 발견 목록에 넣는다.
각 탐색에서 정점은 한 번만 방문하며, start에서 도달할 수 있는 정점만 포함한다.
answer[0]은 첫 번째 순서, answer[1]은 두 번째 순서이다.

제한사항
- 1 <= n <= 1,000
- 1 <= edges.length <= 10,000
- 정점 번호 및 start는 1 이상 n 이하이다.
- 같은 두 정점 사이에 여러 간선이 있을 수 있다.

입출력 예시
n=4, edges={{1,2},{1,3},{1,4},{2,4},{3,4}}, start=1
-> [[1,2,4,3],[1,2,3,4]]
n=4, edges={{1,2},{3,4}}, start=3 -> [[3,4],[3,4]]
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        int[][] edges = {{1, 2}, {1, 3}, {1, 4}, {2, 4}, {3, 4}};
        System.out.println(Arrays.deepToString(solution.solution(4, edges, 1)));
        // [[1, 2, 4, 3], [1, 2, 3, 4]]
        System.out.println(Arrays.deepToString(solution.solution(
                4, new int[][]{{1, 2}, {3, 4}}, 3))); // [[3, 4], [3, 4]]
    }
}

class Solution1 {
    public int[][] solution(int n, int[][] edges, int start) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

