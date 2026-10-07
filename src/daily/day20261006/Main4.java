package daily.day20261006;

import java.util.*;

/*
4번. 숨바꼭질 2

수빈이는 n에, 동생은 k에 있다.
현재 위치가 x일 때 1초 후 x-1, x+1, 2*x 중 하나로 이동할 수 있다.
solution(int n, int k)는 {가장 빠른 시간, 그 시간에 도착하는 방법의 수}를 반환한다.
이동 결과가 같아도 걷기와 순간이동은 서로 다른 방법이다.
예를 들어 1에서 2로 걷기와 순간이동은 각각 1초가 걸리는 다른 방법이다.

제한사항
- 0 <= n, k <= 100,000
- 걷기와 순간이동 모두 1초가 걸린다.

입출력 예시
n=5, k=17 -> {4,2}
n=1, k=2 -> {1,2}
n=7, k=7 -> {0,1}

아래 Solution4의 메서드를 구현하세요.
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        try {
        System.out.println(Arrays.toString(solution.solution(5, 17)) + " / 기대값: [4, 2]");
        System.out.println(Arrays.toString(solution.solution(1, 2)) + " / 기대값: [1, 2]");
        System.out.println(Arrays.toString(solution.solution(7, 7)) + " / 기대값: [0, 1]");
        } catch (UnsupportedOperationException e) {
            System.out.println("아직 미구현입니다. Solution4을 작성한 뒤 다시 실행하세요.");
        }
    }
}

class Solution4 {
    public int[] solution(int n, int k) {
        // 여기에 풀이를 작성하세요.
        throw new UnsupportedOperationException("미구현");
    }
}

