package daily.day20261024.answers;

import java.util.*;

/*
5. 같은 색을 지우고 아래로 떨어뜨리기

12행6열의 판에 색깔 블록이 놓여 있다. '.'는 빈칸이고 R,G,B,P,Y는 색깔이다.
같은 색 블록이 상하좌우로 4개 이상 연결된 그룹은 그 그룹 전체가 사라진다.
현재 사라질 수 있는 그룹은 색이나 위치와 관계없이 모두 동시에 사라진다.
그다음 남은 블록들은 같은 열에서 아래로 떨어져 빈 공간을 채운다.
낙하 후 다시 4개 이상 연결된 그룹이 생기면 같은 처리를 반복한다.
그룹 여러 개가 동시에 사라져도 연쇄는 한 번만 증가한다.
더 이상 사라질 그룹이 없을 때까지 일어나는 연쇄 횟수를 반환하라.
대각선 연결은 인정하지 않으며 새 블록을 추가하지 않는다.

제한사항
- field는 길이6인 문자열12개이다.
- 문자는 .,R,G,B,P,Y 중 하나이다.
- 초기 판은 이미 아래로 떨어진 상태라 블록 바로 아래에 빈칸이 있는 경우는 없다.

입출력 예시
모든 칸이 '.' -> 0
맨 아래 행만 "RRRR.."이고 나머지는 모두 "......" -> 1
맨 아래 두 행이 "....GG","RRRRGG"이고 나머지는 모두 빈칸 -> 1
빨강4개와 초록4개가 동시에 사라지므로2가 아니라1이다.
아래 Main의 마지막 예제 -> 3
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        String[] empty = new String[12];
        Arrays.fill(empty, "......");
        System.out.println(solution.solution(empty)); // 0

        String[] one = empty.clone();
        one[11] = "RRRR..";
        System.out.println(solution.solution(one)); // 1

        String[] simultaneous = empty.clone();
        simultaneous[10] = "....GG";
        simultaneous[11] = "RRRRGG";
        System.out.println(solution.solution(simultaneous)); // 1

        String[] chain = {
            "......", "......", "......", "......",
            "......", "......", "......", "......",
            ".Y....", ".YG...", "RRYG..", "RRYGG."
        };
        System.out.println(solution.solution(chain)); // 3
    }
}

class Solution5 {
    char[][] board;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(String[] field) {
        board = new char[12][6];
        for (int row = 0; row < 12; row++) {
            board[row] = field[row].toCharArray();
        }
        int count = 0;
        while (removeGroups()) {
            count++;
            fall();
        }
        return count;
    }

    private boolean removeGroups() {
        boolean[][] visited = new boolean[12][6];
        List<int[]> remove = new ArrayList<>();
        for (int row = 0; row < 12; row++) {
            for (int col = 0; col < 6; col++) {
                if (board[row][col] != '.' && !visited[row][col]) {
                    List<int[]> group = bfs(row, col, visited);
                    if (group.size() >= 4) {
                        remove.addAll(group);
                    }
                }
            }
        }
        for (int[] position : remove) {
            board[position[0]][position[1]] = '.';
        }
        return !remove.isEmpty();
    }

    private List<int[]> bfs(int startRow, int startCol, boolean[][] visited) {
        char color = board[startRow][startCol];
        List<int[]> group = new ArrayList<>();
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            group.add(current);
            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < 12 && nc >= 0 && nc < 6
                        && board[nr][nc] == color && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return group;
    }

    private void fall() {
        for (int col = 0; col < 6; col++) {
            int nextRow = 11;
            for (int row = 11; row >= 0; row--) {
                if (board[row][col] != '.') {
                    board[nextRow][col] = board[row][col];
                    nextRow--;
                }
            }
            while (nextRow >= 0) {
                board[nextRow][col] = '.';
                nextRow--;
            }
        }
    }
}

