package daily.day20261022.answers;

import java.util.*;

/*
3. 두 연산으로 목표 숫자 만들기

양의 정수 a를 b로 바꾸려고 한다. 한 번에 다음 중 하나를 수행할 수 있다.
- 현재 수에 2를 곱한다.
- 현재 수의 오른쪽 끝에 숫자 1을 붙인다. 예: 12 → 121
가능하다면 필요한 최소 연산 횟수에 1을 더한 값을 반환하라.
불가능하다면 -1을 반환하라. 반환값이 연산 횟수 자체가 아니라는 점에 주의한다.

제한사항
- 1 <= a < b <= 1,000,000,000

입출력 예시
a=2,b=162 -> 5
2→4→8→81→162로 연산 4번이므로 5를 반환한다.
a=4,b=42 -> -1
a=100,b=40021 -> 5
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        System.out.println(solution.solution(2, 162)); // 5
        System.out.println(solution.solution(4, 42)); // -1
        System.out.println(solution.solution(100, 40021)); // 5
    }
}

class Solution3 {
    public int solution(int a, int b) {
        // {현재 수, 지금까지의 연산 횟수 + 1}
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{a, 1});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int number = current[0];
            int count = current[1];
            if (number == b) {
                return count;
            }

            // int 곱셈이 먼저 넘치지 않도록 long으로 계산한다.
            long next = number * 2L;
            if (next <= b) {
                queue.offer(new int[]{(int) next, count + 1});
            }
            next = number * 10L + 1;
            if (next <= b) {
                queue.offer(new int[]{(int) next, count + 1});
            }
        }
        return -1;
    }
}

