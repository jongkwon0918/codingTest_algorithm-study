package daily.day20261006;

import java.util.*;

/*
2번. 그림

도화지는 0(색칠하지 않음)과 1(색칠함)로 이루어진 직사각형 배열이다.
상하좌우로 연결된 1들은 하나의 그림이다. 대각선으로만 접하면 다른 그림이다.
그림의 넓이는 그 그림에 포함된 1의 개수이다.
solution(int[][] paper)는 {그림 개수, 가장 큰 그림의 넓이}를 반환한다.
그림이 없으면 {0, 0}을 반환한다.

제한사항
- 세로와 가로 길이는 각각 1 이상 500 이하
- paper의 값은 0 또는 1

입출력 예시
paper={{1,1,0,1},{1,0,0,1},{0,0,1,0}} -> {3,3}
paper={{0,0},{0,0}} -> {0,0}

아래 Solution2의 메서드를 구현하세요.
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        try {
        int[][] paper = {
                {1, 1, 0, 1},
                {1, 0, 0, 1},
                {0, 0, 1, 0}
        };
        System.out.println(Arrays.toString(solution.solution(paper)) + " / 기대값: [3, 3]");
        System.out.println(Arrays.toString(solution.solution(new int[][]{
                {0, 0}, {0, 0}
        })) + " / 기대값: [0, 0]");
        } catch (UnsupportedOperationException e) {
            System.out.println("아직 미구현입니다. Solution2을 작성한 뒤 다시 실행하세요.");
        }
    }
}

class Solution2 {
    public int[] solution(int[][] paper) {
        // 여기에 풀이를 작성하세요.
        throw new UnsupportedOperationException("미구현");
    }
}

