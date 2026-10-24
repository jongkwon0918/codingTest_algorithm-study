package daily.day20261024;

import java.util.*;

/*
1. 조건을 만족하는 숫자 선택

정수 배열 numbers에서 원소를 하나 이상 골라 합이 target이 되는 경우의 수를 반환하라.
각 원소는 한 번만 고를 수 있고, 고른 원소의 원래 순서는 유지한다.
연속된 원소만 고를 필요는 없다. 아무것도 고르지 않는 경우는 제외한다.
값이 같아도 배열의 위치가 다르면 서로 다른 선택으로 센다.

제한사항
- 1 <= numbers.length <= 20
- -100,000 <= numbers[i] <= 100,000
- -1,000,000 <= target <= 1,000,000

입출력 예시
numbers={-7,-3,-2,5,8}, target=0 -> 1
인덱스 1,2,3의 {-3,-2,5}를 고르면 합이 0이다.
numbers={0,0}, target=0 -> 3
첫째만, 둘째만, 둘 다 고르는 세 가지이다.
numbers={1,1,1}, target=2 -> 3
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        System.out.println(solution.solution(new int[]{-7,-3,-2,5,8}, 0)); // 1
        System.out.println(solution.solution(new int[]{0,0}, 0)); // 3
        System.out.println(solution.solution(new int[]{1,1,1}, 2)); // 3
    }
}

class Solution1 {
    public int solution(int[] numbers, int target) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

