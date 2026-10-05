/*1번. 소수점 판별

이건 네 기억이 비교적 확실해서 문자열 문제로 복원할 수 있어.

문제

숫자가 문자열로 주어진다.

숫자의 소수점 뒤에 숫자가 존재하더라도, 그 숫자들이 모두 0이라면 정수로 판단한다.

반대로 소수점 뒤에 0이 아닌 숫자가 하나라도 존재하면 소수로 판단한다.

소수인 경우 true, 정수인 경우 false를 반환한다.

예시
"12.3400" → true
        "12.0000" → false
        "100"     → false
        "3.14"    → true*/
package bnkSystem;
public class Test1 {
    public static void main(String[] args) {

        String number = "12.3400";

        Solution solution = new Solution();

        boolean answer = solution.solution(number);

        System.out.println(answer);
    }
}
class Solution {

    public boolean solution(String number) {

        int index = number.indexOf('.');

        if (index == -1) {
            return false;
        }

        for (int i = index + 1; i < number.length(); i++) {

            if (number.charAt(i) != '0') {
                return true;
            }
        }

        return false;
    }
}