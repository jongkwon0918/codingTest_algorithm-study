package daily.day20261030;

import java.util.*;

/*
2. 편의점을 거쳐 공연장에 도착하기

집에서 출발해 공연장까지 걸어가려고 한다. 처음에는 맥주 20병을 가지고 있다.
50미터를 걷기 직전에 맥주 한 병을 마셔야 하며, 한 병으로 최대 50미터를 간다.
편의점에 도착하면 빈 병을 버리고 맥주를 다시 최대 20병까지 채울 수 있다.
한 번에 들고 있는 맥주는 20병을 넘을 수 없다.
두 좌표 사이의 이동 거리는 |x1-x2| + |y1-y2|이다.
편의점들을 필요한 순서대로 이용하여 공연장에 도착할 수 있으면 "happy",
도착할 수 없으면 "sad"를 반환하라. 최단 이동 거리를 반환하는 문제가 아니다.
home과 festival은 각각 {x,y}, stores[i]는 i번째 편의점의 {x,y}이다.

제한사항
- 0 <= stores.length <= 100
- 모든 x,y 좌표는 -32768 이상 32767 이하의 정수다.
- 원본의 테스트 케이스 하나를 solution 호출 한 번으로 전달한다.

입출력 예시
home={0,0}, stores={{1000,0},{2000,0}}, festival={3000,0} -> "happy"
집 -> 첫 편의점 -> 둘째 편의점 -> 공연장 순서로 간다.
home={0,0}, stores={}, festival={1001,0} -> "sad"
home={-500,-500}, stores={}, festival={0,0} -> "happy"
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        System.out.println(solution.solution(new int[]{0,0},
                new int[][]{{1000,0},{2000,0}}, new int[]{3000,0})); // happy
        System.out.println(solution.solution(new int[]{0,0},
                new int[0][2], new int[]{1001,0})); // sad
        System.out.println(solution.solution(new int[]{-500,-500},
                new int[0][2], new int[]{0,0})); // happy
    }
}

class Solution2 {
    public String solution(int[] home, int[][] stores, int[] festival) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

