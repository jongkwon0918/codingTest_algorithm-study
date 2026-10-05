/*4번. 주차장

주차장은 N × M 크기의 격자로 주어진다.

각 칸은 다음과 같다.

* : 주차할 수 있는 자리
. : 주차할 수 없는 자리

차량의 종류는 다음과 같다.

승용차 1 : 주차 자리 1칸 사용
트럭 2 : 주차 자리 2칸 연속 사용

주차할 차량의 종류가 주어졌을 때, 주차장에 최대한 많은 차량을 주차한다.

차량은 가로 방향으로만 주차할 수 있으며, 이미 다른 차량이 차지한 자리는 사용할 수 없다.

최대로 주차할 수 있는 승용차와 트럭의 대수를 반환한다.

예시

주차장이 다음과 같다고 하자.

3 6

..****
**..**
.***..

승용차는 1칸, 트럭은 2칸을 차지한다.

각 연속된 주차 가능 구간에서 최대한 차량을 배치하면 된다.*/
package bnkSystem;
import java.util.*;
public class Test4 {
    public static void main(String[] args) {

        String[] parking = {
                "..****",
                "**..**",
                ".***.."
        };

        int[] cars = {1, 2};

        Solution4 solution = new Solution4();

        int[] answer = solution.solution(parking, cars);

        System.out.println(Arrays.toString(answer));
    }
}

class Solution4 {

    public int[] solution(String[] parking, int[] cars) {

        int[] answer = new int[2];

        for (int i = 0; i < parking.length; i++) {

            int count = 0;

            for (int j = 0; j < parking[i].length(); j++) {

                if (parking[i].charAt(j) == '*') {
                    count++;
                } else {

                    answer[0] += count;
                    answer[1] += count / 2;

                    count = 0;
                }
            }

            answer[0] += count;
            answer[1] += count / 2;
        }

        return answer;
    }
}
