package daily.day20261007.answers;

import java.util.*;

/*
정답: 4번 치킨 배달
해설과 예제 추적: ../ANSWERS.md
원본 백준 15686번 치킨 배달
https://www.acmicpc.net/problem/15686
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        try {
        int[][] city = {
                {1, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 1}
        };
        System.out.println(solution.solution(city, 1) + " / 기대값: 6");
        System.out.println(solution.solution(city, 2) + " / 기대값: 2");
        } catch (UnsupportedOperationException e) {
            System.out.println("미구현: Solution4을 작성한 뒤 다시 실행하세요.");
        }
    }
}

class Solution4 {
    List<int[]> houses;
    List<int[]> stores;
    int[] selected;
    int answer;
    int m;

    public int solution(int[][] city, int m) {
        houses = new ArrayList<>();
        stores = new ArrayList<>();
        this.m = m;
        answer = Integer.MAX_VALUE;
        selected = new int[m];

        for (int r = 0; r < city.length; r++) {
            for (int c = 0; c < city.length; c++) {
                if (city[r][c] == 1) {
                    houses.add(new int[]{r, c});
                } else if (city[r][c] == 2) {
                    stores.add(new int[]{r, c});
                }
            }
        }
        dfs(0, 0);
        return answer;
    }

    private void dfs(int start, int count) {
        if (count == m) {
            int sum = 0;
            for (int[] house : houses) {
                int minDist = Integer.MAX_VALUE;
                for (int index : selected) {
                    int[] store = stores.get(index);
                    int dist = Math.abs(house[0] - store[0])
                            + Math.abs(house[1] - store[1]);
                    minDist = Math.min(minDist, dist);
                }
                sum += minDist;
            }
            answer = Math.min(answer, sum);
            return;
        }

        for (int i = start; i < stores.size(); i++) {
            selected[count] = i;
            dfs(i + 1, count + 1);
        }
    }
}

