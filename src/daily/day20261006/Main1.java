package daily.day20261006;

import java.util.*;

/*
1번. 연결 요소의 개수

방향이 없는 그래프의 정점은 1번부터 n번까지이다.
서로 경로로 연결된 정점들은 하나의 연결 요소를 이룬다.
간선이 없는 정점도 연결 요소 하나로 센다.
solution(int n, int[][] edges)는 연결 요소의 개수를 반환한다.

제한사항
- 1 <= n <= 1,000
- 0 <= edges.length <= n * (n - 1) / 2
- edges[i] = {u, v}, 1 <= u, v <= n, u != v
- 같은 간선은 한 번만 주어진다.

입출력 예시
n=6, edges={{1,2},{2,5},{5,1},{3,4},{4,6}} -> 2
n=4, edges={} -> 4

아래 Solution1의 메서드를 구현하세요.
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        try {
        System.out.println(solution.solution(6, new int[][]{
                {1, 2}, {2, 5}, {5, 1}, {3, 4}, {4, 6}
        }) + " / 기대값: 2");
        System.out.println(solution.solution(4, new int[][]{}) + " / 기대값: 4");
        } catch (UnsupportedOperationException e) {
            System.out.println("아직 미구현입니다. Solution1을 작성한 뒤 다시 실행하세요.");
        }
    }
}

class Solution1 {
    public int solution(int n, int[][] edges) {
        // 여기에 풀이를 작성하세요.
        throw new UnsupportedOperationException("미구현");
    }
}

