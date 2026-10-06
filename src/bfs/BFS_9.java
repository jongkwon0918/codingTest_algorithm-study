// 문제: DSLR
package bfs;/*네 개의 명령어 D, S, L, R을 이용해 0 이상 9999 이하의 십진 정수들을 조작하는 계산기가 있습니다.
D: n을 두 배로 바꾼다. 결과 값이 9999 보다 크면 10000으로 나눈 나머지를 취한다. (2n mod 10000)
S: n에서 1을 뺀다. 만약 n이 0이라면 9999가 된다. (n = 0 이면 9999)
L: n의 각 자릿수를 왼쪽으로 회전시켜 각 자릿수를 밀어낸다. (예: 1234 -> 2341, 1000 -> 0001 = 1)
R: n의 각 자릿수를 오른쪽으로 회전시킨다. (예: 1234 -> 4123)
시작 숫자 A와 목표 숫자 B가 주어질 때,
A를 B로 변환하기 위해 가장 적은 연산 횟수로 수행하는 명령어 나열(문자열)을 반환하는 solution 함수를 작성해 주세요.
(답이 여러 가지일 경우 아무거나 반환해도 됩니다.)
제한사항
A와 B는 0 이상 9999 이하의 정수입니다.
A와 B가 처음부터 같은 경우는 주어지지 않습니다.
항상 A를 B로 바꿀 수 있는 입력만 주어집니다.*/
import java.util.*;
public class BFS_9 {
    public static void main(String[] args) {
        Solution19 sol = new Solution19();
        int A = 1234;
        int B = 3412;

        String result = sol.solution(A, B);

        System.out.println(result);

    }
}

class Solution19 {

    public String solution(int a, int b) {

        boolean[] visited = new boolean[10000];

        Queue<Object[]> queue = new LinkedList<>();

        queue.offer(new Object[]{a, ""});
        visited[a] = true;

        while (!queue.isEmpty()) {

            Object[] curr = queue.poll();

            int currNum = (int) curr[0];
            String currCost = (String) curr[1];

            if (currNum == b) {
                return currCost;
            }

            // D
            int nextD = (currNum * 2) % 10000;

            if (!visited[nextD]) {
                visited[nextD] = true;
                queue.offer(new Object[]{nextD, currCost + "D"});
            }

            // S
            int nextS = currNum == 0 ? 9999 : currNum - 1;

            if (!visited[nextS]) {
                visited[nextS] = true;
                queue.offer(new Object[]{nextS, currCost + "S"});
            }

            // L
            int nextL = (currNum % 1000) * 10 + currNum / 1000;

            if (!visited[nextL]) {
                visited[nextL] = true;
                queue.offer(new Object[]{nextL, currCost + "L"});
            }

            // R
            int nextR = (currNum % 10) * 1000 + currNum / 10;

            if (!visited[nextR]) {
                visited[nextR] = true;
                queue.offer(new Object[]{nextR, currCost + "R"});
            }
        }

        return "";
    }
}
