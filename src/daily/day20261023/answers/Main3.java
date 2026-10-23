package daily.day20261023.answers;

import java.util.*;

/*
3. 파동으로 목표에 닿기

직사각형 교실에서 '*'는 주난이, '#'는 목표 학생, '0'은 빈칸, '1'은 다른 학생이다.
주난이는 자기 자리에서 반복해서 점프한다. 점프 한 번의 파동은 상하좌우 인접 칸을 따라
빈칸을 계속 통과하며 모든 방향으로 퍼진다. 중간에서 방향을 바꿔 퍼지는 것도 가능하다.
파동이 학생을 만나면 그 학생을 쓰러뜨리고 그 칸 너머로는 같은 점프에서 진행하지 않는다.
쓰러진 '1'은 다음 점프부터 빈칸처럼 통과할 수 있다.
한 점프에서 만난 여러 학생은 함께 쓰러진다. '#'에 파동이 닿으면 목표를 잡는다.
목표를 잡기 위한 최소 점프 횟수를 반환하라. 주난이가 직접 걸어가는 문제가 아니다.
start와 target은 각각 {행,열}이며 원본처럼 1부터 시작한다.

제한사항
- 행과 열의 길이는 각각 1 이상 300 이하이다.
- 모든 행의 길이는 같고 문자는 0,1,*,# 중 하나이다.
- '*'와 '#'는 각각 하나이며 서로 다른 위치이다.
- start와 target은 각각 '*'와 '#'의 위치이다.

입출력 예시
classroom={"*00#"}, start={1,1},target={1,4} -> 1
빈칸을 모두 통과해 첫 점프에 목표를 잡는다.
classroom={"*11#"}, start={1,1},target={1,4} -> 3
첫 점프에 첫 학생, 다음 점프에 둘째 학생, 세 번째에 목표를 잡는다.
classroom={"#0000","11111","0000*"}, start={3,5},target={1,1} -> 2
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        System.out.println(solution.solution(
                new String[]{"*00#"}, new int[]{1,1}, new int[]{1,4})); // 1
        System.out.println(solution.solution(
                new String[]{"*11#"}, new int[]{1,1}, new int[]{1,4})); // 3
        System.out.println(solution.solution(
                new String[]{"#0000","11111","0000*"},
                new int[]{3,5}, new int[]{1,1})); // 2
    }
}

class Solution3 {
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(String[] classroom, int[] start, int[] target) {
        int n = classroom.length;
        int m = classroom[0].length();
        int[][] dist = new int[n][m];
        for (int row = 0; row < n; row++) {
            Arrays.fill(dist[row], Integer.MAX_VALUE);
        }

        int startRow = start[0] - 1;
        int startCol = start[1] - 1;
        int targetRow = target[0] - 1;
        int targetCol = target[1] - 1;
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[2], b[2]));
        dist[startRow][startCol] = 0;
        pq.offer(new int[]{startRow, startCol, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int row = current[0];
            int col = current[1];
            int cost = current[2];
            if (cost > dist[row][col]) {
                continue;
            }
            if (row == targetRow && col == targetCol) {
                return cost;
            }

            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
                    char next = classroom[nr].charAt(nc);
                    int nextCost = cost;
                    if (next == '1' || next == '#') {
                        nextCost++;
                    }
                    if (nextCost < dist[nr][nc]) {
                        dist[nr][nc] = nextCost;
                        pq.offer(new int[]{nr, nc, nextCost});
                    }
                }
            }
        }
        return -1;
    }
}

