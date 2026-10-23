package daily.day20261022;

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
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{a, 0});

        Set<Integer> visited = new HashSet<>();
        visited.add(a);

        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int currNum = curr[0];
            int count = curr[1];

            if(currNum==b){
                return count+1;
            }

            long[] nexts = {currNum * 2L, currNum * 10L +1};
            for(long next : nexts){
                if(!visited.contains((int)next) && next<=b){
                    queue.offer(new int[]{(int)next, count+1});
                    visited.add((int)next);
                }
            }
        }

        return -1;
    }
}

