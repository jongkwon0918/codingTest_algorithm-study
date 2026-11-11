package daily.day20261111.answers;

import java.util.*;

/*
5. 구슬을 지우고 묶음을 바꾸는 마법

홀수 n*n 격자의 중심 (n/2,n/2)에 상어가 있고 나머지 칸에는 1,2,3번 구슬 또는 빈칸 0이 있다.
좌표는 0부터 시작한다. 칸의 순서는 중심 바로 왼쪽에서 시작해 왼쪽1, 아래1, 오른쪽2,
위2, 왼쪽3, 아래3, 오른쪽4, 위4, ... 순으로 나선형으로 이동하며 정한다.
중심은 제외하며 마지막 칸은 (0,0)이다. 여기서 연속은 상하좌우 영역이 아닌 이 칸 순서를 뜻한다.
spells[i]={direction,distance}를 차례로 실행한다. 방향은 1=위,2=아래,3=왼쪽,4=오른쪽이다.

마법 하나의 처리 순서
1. 중심에서 지정 방향으로 1~distance칸 떨어진 위치의 구슬을 모두 파괴한다.
2. 나선형 칸 순서대로 빈칸을 없애며 구슬을 중심 쪽으로 당긴다. 상대적인 순서는 유지한다.
3. 같은 번호가 4개 이상 연속한 모든 묶음을 동시에 폭발시키고 다시 당긴다.
   새롭게 4개 이상 이어질 수 있으므로 폭발이 없어질 때까지 반복한다.
4. 남은 구슬을 연속한 같은 번호끼리 묶고, 각 묶음을 {묶음 크기, 구슬 번호} 두 구슬로 바꾼다.
   예를 들어 {2,2,2,1,1,3}은 {3,2,2,1,1,3}이 된다.
   앞에서부터 나선형 순서대로 채우고 칸 수 n*n-1을 넘는 뒷부분은 버린다.
   변화 직후 폭발시키지 않고 다음 마법으로 넘어간다.
폭발한 구슬 번호의 합을 반환하라. 1단계 마법으로 직접 파괴한 구슬은 점수에 포함하지 않는다.

제한사항
- 3 <= n <= 49, n은 홀수, board는 정사각형이다.
- 1 <= spells.length <= 100
- 1 <= direction <= 4, 1 <= distance <= (n-1)/2
- board 값은 0~3이며 중심은 0이다.
- 초기 구슬은 나선형 순서의 앞쪽부터 빈칸 없이 배치되어 있다.
- 초기 상태에는 같은 번호가 4개 이상 연속하지 않는다.

입출력 예시
board={{2,2,2},{1,0,1},{1,2,1}}, spells={{2,1}} -> 4
나선 순서 {1,1,2,1,1,2,2,2}에서 아래 구슬 2를 파괴하면
{1,1,1,1,2,2,2}가 되어 1 네 개가 폭발한다. 점수4.
남은 {2,2,2}는 {3,2}로 바뀐다.
3*3 빈 격자, spells={{1,1}} -> 0
*/
public class Main5 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        System.out.println(solution.solution(new int[][]{
            {2,2,2},{1,0,1},{1,2,1}
        }, new int[][]{{2,1}})); // 4
        System.out.println(solution.solution(new int[3][3], new int[][]{{1,1}})); // 0
    }
}

class Solution5 {
    int n;
    int[][] index;
    int[] beads;
    int score;

    public int solution(int[][] board, int[][] spells) {
        n = board.length;
        index = new int[n][n];
        beads = new int[n * n - 1];
        score = 0;
        makeOrder(board);
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        for (int[] spell : spells) {
            int direction = spell[0] - 1;
            for (int step = 1; step <= spell[1]; step++) {
                int r = n / 2 + dr[direction] * step;
                int c = n / 2 + dc[direction] * step;
                beads[index[r][c]] = 0;
            }
            compact();
            while (explode()) {
                compact();
            }
            transform();
        }
        return score;
    }

    private void makeOrder(int[][] board) {
        int[] dr = {0, 1, 0, -1};
        int[] dc = {-1, 0, 1, 0};
        int r = n / 2;
        int c = n / 2;
        int direction = 0;
        int length = 1;
        int count = 0;
        while (count < beads.length) {
            for (int repeat = 0; repeat < 2; repeat++) {
                for (int step = 0; step < length; step++) {
                    r += dr[direction];
                    c += dc[direction];
                    index[r][c] = count;
                    beads[count] = board[r][c];
                    count++;
                    if (count == beads.length) {
                        return;
                    }
                }
                direction = (direction + 1) % 4;
            }
            length++;
        }
    }

    private void compact() {
        int[] next = new int[beads.length];
        int count = 0;
        for (int bead : beads) {
            if (bead != 0) {
                next[count++] = bead;
            }
        }
        beads = next;
    }

    private boolean explode() {
        boolean removed = false;
        int start = 0;
        while (start < beads.length && beads[start] != 0) {
            int end = start + 1;
            while (end < beads.length && beads[end] == beads[start]) {
                end++;
            }
            int count = end - start;
            if (count >= 4) {
                score += beads[start] * count;
                Arrays.fill(beads, start, end, 0);
                removed = true;
            }
            start = end;
        }
        return removed;
    }

    private void transform() {
        int[] next = new int[beads.length];
        int count = 0;
        int start = 0;
        while (start < beads.length && beads[start] != 0 && count < next.length) {
            int end = start + 1;
            while (end < beads.length && beads[end] == beads[start]) {
                end++;
            }
            next[count++] = end - start;
            if (count < next.length) {
                next[count++] = beads[start];
            }
            start = end;
        }
        beads = next;
    }
}

