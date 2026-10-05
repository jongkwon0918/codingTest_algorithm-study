/*2번. 버튼 클릭 횟수

이것도 기억이 많이 빠져 있어서 좌표 + 도형 + 경계 포함 판정 문제라고 보고 복원해볼게.

문제

화면에 여러 개의 버튼이 있다.

각 버튼은 직사각형 영역으로 표현된다.

버튼의 영역은 다음과 같이 주어진다.

[x1, y1, x2, y2]

어떤 점 (x, y)를 클릭했을 때 해당 점이 버튼 영역 안에 포함되면 버튼이 클릭된 것으로 판단한다.

버튼의 테두리나 꼭짓점에 정확히 닿는 경우도 클릭된 것으로 처리한다.

여러 번 클릭한 위치가 주어질 때, 각 클릭에서 눌린 버튼의 번호를 반환한다.

예시

버튼:

1번 버튼 = [0, 0, 5, 5]
2번 버튼 = [6, 0, 10, 5]

클릭:

(0, 0)
(5, 5)
(7, 3)
(20, 20)

결과:

[1, 1, 2, -1]*/

package bnkSystem;
import java.util.*;
public class Test2 {
    public static void main(String[] args) {

        int[][] buttons = {
                {0, 0, 5, 5},
                {6, 0, 10, 5}
        };

        int[][] clicks = {
                {0, 0},
                {5, 5},
                {7, 3},
                {20, 20}
        };

        Solution2 solution = new Solution2();

        int[] answer = solution.solution(buttons, clicks);

        System.out.println(Arrays.toString(answer));
    }
}
class Solution2 {

    public int[] solution(int[][] buttons, int[][] clicks) {

        int[] answer = new int[clicks.length];

        for (int i = 0; i < clicks.length; i++) {

            int x = clicks[i][0];
            int y = clicks[i][1];

            answer[i] = -1;

            for (int j = 0; j < buttons.length; j++) {

                int x1 = buttons[j][0];
                int y1 = buttons[j][1];
                int x2 = buttons[j][2];
                int y2 = buttons[j][3];

                if (x >= x1 && x <= x2 &&
                        y >= y1 && y <= y2) {

                    answer[i] = j + 1;
                    break;
                }
            }
        }

        return answer;
    }
}