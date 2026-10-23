package daily.day20261023;

import java.util.*;

/*
2. 걸어서 또는 정거장으로 이동하기

1부터 n까지 일렬로 놓인 지점들이 있고 각 지점에는 정거장이 있다.
현재 위치가 x이면 x-1 또는 x+1로 걸어서 이동할 수 있다. 범위 밖으로는 나갈 수 없다.
teleports[i]={a,b}이면 a와 b 사이를 양방향으로 바로 이동할 수도 있다.
걷기 한 번과 정거장 이동 한 번은 모두 1초가 걸린다.
start에서 target까지 가는 최소 시간을 반환하라.
한 지점에 여러 정거장 연결이 있을 수 있다.

제한사항
- 2 <= n <= 300,000
- 0 <= teleports.length <= min(n*(n-1)/2, 300,000)
- 모든 지점 번호는 1 이상 n 이하이다.
- start != target, 각 연결의 두 지점은 서로 다르다.
- 같은 두 지점을 잇는 연결은 중복되지 않는다.

입출력 예시
n=10, teleports={{1,8},{8,10}}, start=1,target=10 -> 2
n=5, teleports={}, start=1,target=5 -> 4
n=10, teleports={{1,8},{8,10}}, start=10,target=1 -> 2
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        int[][] teleports = {{1,8},{8,10}};
        System.out.println(solution.solution(10, teleports, 1, 10)); // 2
        System.out.println(solution.solution(5, new int[0][2], 1, 5)); // 4
        System.out.println(solution.solution(10, teleports, 10, 1)); // 2
    }
}

class Solution2 {
    public int solution(int n, int[][] teleports, int start, int target) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

