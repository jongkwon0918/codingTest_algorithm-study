package daily.day20261030;

import java.util.*;

/*
3. 목적지마다 처음 보낼 곳 정하기

집하장 n개 사이에 양방향 도로가 있고, roads[i]={a,b,time}은
a번과 b번 집하장 사이의 이동 시간이 time이라는 뜻이다.
각 출발 집하장에서 각 목적 집하장으로 최소 시간에 화물을 보내려고 한다.
이때 출발 직후 가장 먼저 보내야 하는 집하장 번호를 경로표에 기록하라.
전체 이동 시간이나 도착 직전의 집하장을 반환하는 문제가 아니다.
반환형은 String[][]이며 answer[a-1][b-1]에 처음 보낼 집하장 번호를 문자열로 넣는다.
출발과 목적이 같으면 "-"를 넣는다. 이동할 수 없는 쌍도 "-"로 표시한다.
최단 경로가 여러 개라면 그중 한 경로의 첫 집하장을 반환하면 된다.
원본의 경로표 출력 기호를 문자열 배열의 원소로 옮긴 형식이다.

제한사항
- 1 <= n <= 200
- 1 <= roads.length <= 10000
- 집하장 번호는 1부터 n까지이며 이동 시간은 1 이상 1000 이하이다.
- 연결된 집하장 쌍에는 양방향으로 이동할 수 있다.
- 동일 목적지의 동률에서 작은 번호를 고르는 등의 추가 우선순위는 요구하지 않는다.

입출력 예시
n=3, roads={{1,2,2},{2,3,3},{1,3,10}} ->
{{"-","2","2"},{"1","-","3"},{"2","2","-"}}
1에서3으로 직접 가면10, 2를 거치면5이므로 answer[0][2]="2"이다.
n=2, roads={{1,2,7}} -> {{"-","2"},{"1","-"}}
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        String[][] answer = solution.solution(3,
                new int[][]{{1,2,2},{2,3,3},{1,3,10}});
        for (String[] row : answer) {
            System.out.println(Arrays.toString(row));
        }
        // [-, 2, 2], [1, -, 3], [2, 2, -]
        System.out.println(Arrays.deepToString(solution.solution(2,
                new int[][]{{1,2,7}}))); // [[-, 2], [1, -]]
    }
}

class Solution3 {
    public String[][] solution(int n, int[][] roads) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

