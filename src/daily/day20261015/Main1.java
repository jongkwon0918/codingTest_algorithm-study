package daily.day20261015;

import java.util.*;

/*
1. 대각선으로 이어진 섬

직사각형 지도에서 1은 땅, 0은 바다이다.
땅끼리 상하좌우뿐 아니라 대각선으로 붙어 있어도 연결되어 있다.
연결된 땅을 따라 서로 이동할 수 있는 칸들은 하나의 섬이다.
지도의 섬 개수를 반환하라. 지도 밖으로는 이동할 수 없다.

제한사항
- 행과 열은 각각 1 이상 50 이하이다.
- 모든 행의 길이는 같으며 값은 0 또는 1이다.
- 원본의 테스트 케이스 하나를 solution 호출 한 번으로 전달한다.

입출력 예시
map={{1,0},{0,1}} -> 1
map={{1,0,1},{0,0,0},{1,0,1}} -> 4
map={{0}} -> 0
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        System.out.println(solution.solution(new int[][]{{1, 0}, {0, 1}})); // 1
        System.out.println(solution.solution(
                new int[][]{{1, 0, 1}, {0, 0, 0}, {1, 0, 1}})); // 4
        System.out.println(solution.solution(new int[][]{{0}})); // 0
    }
}

class Solution1 {
    public int solution(int[][] map) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

