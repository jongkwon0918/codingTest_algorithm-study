package daily.day20261023.answers;

import java.util.*;

/*
4. 세 칸짜리 무기를 겹치지 않게 만들기

직사각형 나무판의 각 칸에 강도가 적혀 있다.
세 칸을 사용하는 ㄱ 모양 부메랑을 여러 개 만들려고 한다.
부메랑 하나는 중심 한 칸, 중심의 위 또는 아래 한 칸, 중심의 왼쪽 또는 오른쪽 한 칸으로 구성된다.
따라서 중심을 기준으로 위+왼쪽, 위+오른쪽, 아래+왼쪽, 아래+오른쪽의 네 모양이 가능하다.
강도는 중심 칸의 강도*2 + 나머지 두 칸의 강도이다.
서로 다른 부메랑끼리 칸을 공유할 수 없다. 나무판의 일부 칸을 사용하지 않아도 된다.
만들 수 있는 부메랑들의 강도 합의 최댓값을 반환하라. 하나도 못 만들면 0이다.

제한사항
- 행과 열의 길이는 각각 1 이상 5 이하이다.
- 모든 행 길이는 같고 각 칸의 강도는 1 이상 100 이하이다.

입출력 예시
wood={{1,2},{3,4}} -> 13
(1,1)의 4를 중심으로 위쪽 2, 왼쪽 3을 사용하면 4*2+2+3=13이다.
wood={{7}} -> 0
wood={{32,83,75},{24,96,56},{71,88,12}} -> 632
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        System.out.println(solution.solution(new int[][]{{1,2},{3,4}})); // 13
        System.out.println(solution.solution(new int[][]{{7}})); // 0
        System.out.println(solution.solution(
                new int[][]{{32,83,75},{24,96,56},{71,88,12}})); // 632
    }
}

class Solution4 {
    int n;
    int m;
    int[][] wood;
    boolean[][] visited;
    int answer;
    int[] dr = {-1, -1, 1, 1};
    int[] dc = {-1, 1, -1, 1};

    public int solution(int[][] wood) {
        this.wood = wood;
        n = wood.length;
        m = wood[0].length;
        visited = new boolean[n][m];
        answer = 0;
        dfs(0, 0, 0);
        return answer;
    }

    private void dfs(int row, int col, int sum) {
        if (col == m) {
            row++;
            col = 0;
        }
        if (row == n) {
            answer = Math.max(answer, sum);
            return;
        }

        if (!visited[row][col]) {
            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d]; // 세로로 붙는 칸: (nr, col)
                int nc = col + dc[d]; // 가로로 붙는 칸: (row, nc)
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && !visited[nr][col] && !visited[row][nc]) {
                    visited[row][col] = true;
                    visited[nr][col] = true;
                    visited[row][nc] = true;

                    int strength = wood[row][col] * 2
                            + wood[nr][col] + wood[row][nc];
                    dfs(row, col + 1, sum + strength);

                    visited[row][col] = false;
                    visited[nr][col] = false;
                    visited[row][nc] = false;
                }
            }
        }

        // 이 칸을 중심으로 만들지 않는 선택도 확인한다.
        dfs(row, col + 1, sum);
    }
}

