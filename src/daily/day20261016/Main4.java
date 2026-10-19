package daily.day20261016;

import java.util.*;

/*
4. 계란을 부딪쳐 최대한 깨기

계란이 왼쪽부터 놓여 있고 eggs[i]={내구도,무게}이다.
왼쪽 계란부터 차례로 한 번씩 손에 들 차례가 온다.
손에 든 계란이 깨져 있으면 다음 계란 차례로 넘어간다.
깨지지 않았다면 다른 깨지지 않은 계란 하나를 골라 반드시 한 번 부딪친다.
상대 계란이 하나도 없다면 부딪치지 않고 다음 차례로 넘어간다.
부딪치면 두 계란의 내구도가 동시에 상대 계란의 무게만큼 감소한다.
내구도가 0 이하가 된 계란은 깨진 것이다.
부딪친 뒤에는 손에 들었던 계란의 다음 위치 계란 차례로 넘어간다.
모든 차례가 끝났을 때 깨진 계란 수의 최댓값을 반환하라.
무게는 변하지 않으며, 앞서 차례가 지난 계란도 살아 있다면 상대가 될 수 있다.

제한사항
- 1 <= eggs.length <= 8
- 1 <= 내구도,무게 <= 300

입출력 예시
eggs={{1,1},{1,1}} -> 2
eggs={{10,1},{10,1}} -> 0
eggs={{1,1},{1,1},{1,1}} -> 2
eggs={{5,3}} -> 0
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        System.out.println(solution.solution(new int[][]{{1, 1}, {1, 1}})); // 2
        System.out.println(solution.solution(new int[][]{{10, 1}, {10, 1}})); // 0
        System.out.println(solution.solution(new int[][]{{1, 1}, {1, 1}, {1, 1}})); // 2
        System.out.println(solution.solution(new int[][]{{5, 3}})); // 0
    }
}

class Solution4 {
    int n;
    int answer;
    public int solution(int[][] eggs) {
        n = eggs.length;
        answer = 0;

        dfs(eggs, 0);

        return answer;
    }

    private void dfs(int[][] eggs, int currEgg) {
        if (currEgg == n) {
            int count = 0;
            for (int i = 0; i < n; i++) {
                if (eggs[i][0] <= 0) {
                    count++;
                }
            }
            answer = Math.max(answer, count);
            return;
        }

        // 기존에는 손의 계란이 깨졌을 때 다음 차례를 진행하지 못했다.
        if (eggs[currEgg][0] <= 0) {
            dfs(eggs, currEgg + 1);
            return;
        }

        boolean hit = false;
        for (int i = 0; i < n; i++) {
            if (currEgg != i && eggs[i][0] > 0) {
                hit = true;
                int beforeTarget = eggs[i][0];
                int beforeCurrent = eggs[currEgg][0];

                eggs[currEgg][0] -= eggs[i][1];
                eggs[i][0] -= eggs[currEgg][1];

                dfs(eggs, currEgg + 1);

                eggs[currEgg][0] = beforeCurrent;
                eggs[i][0] = beforeTarget;
            }
        }

        // 기존에는 칠 상대가 없을 때도 다음 차례를 진행하지 못했다.
        if (!hit) {
            dfs(eggs, currEgg + 1);
        }
    }
}

