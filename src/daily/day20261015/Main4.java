package daily.day20261015;

import java.util.*;

/*
4. 연산 순서에 따른 최댓값과 최솟값

numbers에 주어진 수들의 순서는 바꿀 수 없다.
수 사이에 연산자를 하나씩 넣되, operators={더하기 수, 빼기 수, 곱하기 수, 나누기 수}에
주어진 연산자를 모두 사용한다.
계산은 연산자 우선순위를 적용하지 않고 왼쪽에서 오른쪽으로 진행한다.
정수 나눗셈의 소수 부분은 0을 향해 버린다. 예를 들어 -7/3은 -2이다.
가능한 계산 결과의 최댓값과 최솟값을 {최댓값, 최솟값}으로 반환하라.

제한사항
- 2 <= numbers.length <= 11
- 1 <= numbers[i] <= 100
- operators.length=4, 각 값은 0 이상의 정수이다.
- operators의 합은 numbers.length-1이다.
- 어떤 연산자 순서에서도 중간 결과와 최종 결과는 -10억 이상 10억 이하이다.

입출력 예시
numbers={1,2,3}, operators={1,0,1,0} -> [9,5]
(1+2)*3=9, (1*2)+3=5이다.
numbers={8,3,2}, operators={0,1,0,1} -> [2,0]
(8-3)/2=2, (8/3)-2=0이다.
numbers={1,8,3}, operators={0,1,0,1} -> [-2,-3]
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        System.out.println(Arrays.toString(solution.solution(
                new int[]{1, 2, 3}, new int[]{1, 0, 1, 0}))); // [9, 5]
        System.out.println(Arrays.toString(solution.solution(
                new int[]{8, 3, 2}, new int[]{0, 1, 0, 1}))); // [2, 0]
        System.out.println(Arrays.toString(solution.solution(
                new int[]{1, 8, 3}, new int[]{0, 1, 0, 1}))); // [-2, -3]
    }
}

class Solution4 {
    public int[] solution(int[] numbers, int[] operators) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

