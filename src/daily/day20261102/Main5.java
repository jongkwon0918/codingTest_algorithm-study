package daily.day20261102;

import java.util.*;

/*
5. 쌓인 말을 색깔 규칙대로 옮기기

n*n 게임판에서0은 흰색, 1은 빨간색, 2는 파란색 칸이다.
pieces[i]={행,열,방향}은 i+1번 말의 초기 정보다.
행·열은1부터 시작하고 방향은1=오른쪽, 2=왼쪽, 3=위, 4=아래이다.
초기에는 한 칸에 말이 하나씩만 있으며, 이동 중에는 여러 말을 아래에서 위로 쌓을 수 있다.

한 턴에는1번부터 마지막 말까지 번호 순서대로 각 말의 이동을 처리한다.
어떤 말의 차례가 오면 그 말과 같은 칸에서 그 말 위에 있는 모든 말을 함께 한 칸 옮긴다.
그 말 아래의 말은 남는다. 맨 아래에 있는 말만 움직이는 것이 아니다.
이동하려는 칸에 따라 다음 규칙을 적용한다.
- 흰색: 이동 묶음의 아래->위 순서를 유지하여 목적지의 기존 말 위에 얹는다.
- 빨간색: 이동 묶음의 순서만 뒤집어서 기존 말 위에 얹는다. 목적지에 원래 있던 말은 뒤집지 않는다.
- 파란색 또는 범위 밖: 현재 차례인 말의 방향만 반대로 바꾸고 반대쪽 칸으로 이동을 다시 시도한다.
  반대쪽도 파란색 또는 범위 밖이면 이동하지 않는다. 바뀐 방향은 유지한다.
  반대쪽이 흰색이나 빨간색이면 해당 규칙으로 이동한다.
함께 실려 가는 다른 말들의 방향은 바뀌지 않는다.
다른 말에 실려 이미 움직였더라도, 같은 턴에 자기 번호 차례가 오면 다시 이동한다.

말 하나의 이동 처리가 끝난 순간 어느 칸에4개 이상 쌓이면 즉시 종료한다.
그때의 턴 번호를 반환하라. 한 턴의 나머지 말을 모두 움직일 때까지 기다리지 않는다.
턴은1부터 시작하며1000번째 턴까지 종료되지 않으면-1을 반환한다.

제한사항
- 4 <= colors.length = n <= 12, 모든 행의 길이는 n이다.
- 각 색은0,1,2 중 하나이다.
- 4 <= pieces.length <= 10
- 초기 행·열은1 이상 n 이하이며 위치는 서로 다르다.
- 방향은1 이상4 이하이다. 말이 놓인 칸의 색도 일반 규칙으로 처리한다.

입출력 예시
4*4모든 칸이 흰색이고 pieces={{2,1,1},{2,2,1},{2,3,1},{2,4,1}} -> 1
1번이2번 위로, 다음2번이1번과 함께3번 위로,
다음3번이2번·1번과 함께4번 위로 이동하여 첫 턴 중에 네 개가 쌓인다.
4*4모든 칸이 파란색이고 위와 같은 pieces -> -1
양쪽이 모두 파란색이라 방향만 바뀌고 이동하지 않는다.
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        int[][] pieces = {{2, 1, 1}, {2, 2, 1}, {2, 3, 1}, {2, 4, 1}};
        System.out.println(solution.solution(new int[4][4], pieces)); // 1

        int[][] blue = new int[4][4];
        for (int r = 0; r < 4; r++) {
            Arrays.fill(blue[r], 2);
        }
        System.out.println(solution.solution(blue, pieces)); // -1

        int[][] red = new int[4][4];
        red[1][1] = 1;
        System.out.println(solution.solution(red, pieces)); // 1
    }
}

class Solution5 {
    int n;
    int m;
    int[] dr = {0, 0, -1, 1};
    int[] dc = {1, -1, 0, 0};

    List<Integer>[][] map;

    public int solution(int[][] colors, int[][] pieces) {
        n = colors.length;
        m = pieces.length;
        map = new List[n][n];

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                map[r][c] = new ArrayList<>();
            }
        }

        for (int i = 0; i < m; i++) {
            int r = pieces[i][0] - 1;
            int c = pieces[i][1] - 1;
            map[r][c].add(i);
        }

        return movePiece(pieces, colors);
    }

    private int movePiece(int[][] pieces, int[][] colors) {
        int count = 1;
        while (true) {
            if (count == 1001) {
                return -1;
            }
            for (int i = 0; i < m; i++) {
                int r = pieces[i][0] - 1;
                int c = pieces[i][1] - 1;
                int d = pieces[i][2] - 1;

                List<Integer> temp = new ArrayList<>();
                boolean getList = false;
                for (int t = 0; t < map[r][c].size(); t++) {
                    if (map[r][c].get(t).equals(i)) {
                        getList = true;
                    }
                    if (getList) {
                        temp.add(map[r][c].get(t));
                    }
                }

                int nr = r + dr[d];
                int nc = c + dc[d];

                if ((nr < 0 || nr >= n || nc < 0 || nc >= n) || colors[nr][nc] == 2) {
                    if (d == 0 || d == 2) {
                        nr = r + dr[d + 1];
                        nc = c + dc[d + 1];
                    } else {
                        nr = r + dr[d - 1];
                        nc = c + dc[d - 1];
                    }

                    if ((nr < 0 || nr >= n || nc < 0 || nc >= n) || colors[nr][nc] == 2) {
                        continue;
                    }
                }


                for (int number : temp) {
                    map[r][c].remove(Integer.valueOf(number));
                }

                if (colors[nr][nc] == 0) {
                    map[nr][nc].addAll(temp);
                } else if (colors[nr][nc] == 1) {
                    if (map[nr][nc].isEmpty()) {
                        map[nr][nc].addAll(temp);
                    } else {
                        if (map[nr][nc].isEmpty()) {
                            map[nr][nc].addAll(temp);
                        } else {
                            for (int j = temp.size() - 1; j >= 0; j--) {
                                map[nr][nc].add(temp.get(j));
                            }
                        }
                    }
                }

                for (int number : temp) {
                    pieces[number][0] = nr + 1;
                    pieces[number][1] = nc + 1;
                }

                if (map[nr][nc].size() >= 4) {
                    return count;
                }

            }

            count++;
        }

    }
}

