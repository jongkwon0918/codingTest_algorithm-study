package daily.day20261030.answers;

import java.util.*;

/*
1. 순서를 구분해서 숫자 고르기

1부터 n까지의 서로 다른 자연수 중 m개를 골라 나열하는 모든 방법을 반환하라.
한 수열 안에서 같은 숫자를 두 번 사용할 수 없다.
선택한 숫자가 같아도 순서가 다르면 다른 수열이다. 예: {1,2}와 {2,1}.
각 행은 길이 m인 수열이며, 전체 행은 사전순으로 반환한다.
사전순은 앞에서부터 비교하여 처음 다른 숫자가 작은 수열이 먼저 오는 순서다.

제한사항
- 1 <= m <= n <= 8
- 반환형은 int[][]이며, 숫자를 이어 붙인 정수가 아니라 각 숫자를 별도 칸에 저장한다.

입출력 예시
n=3, m=2 -> {{1,2},{1,3},{2,1},{2,3},{3,1},{3,2}}
n=1, m=1 -> {{1}}
n=8, m=8 -> 총 40320행
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        for (int[] row : solution.solution(3, 2)) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println(Arrays.deepToString(solution.solution(1, 1))); // [[1]]
        System.out.println(solution.solution(8, 8).length); // 40320
    }
}

class Solution1 {
    int n;
    int m;
    int[] choice;
    boolean[] visited;
    List<int[]> answer;

    public int[][] solution(int n, int m) {
        this.n = n;
        this.m = m;
        choice = new int[m];
        visited = new boolean[n + 1];
        answer = new ArrayList<>();
        dfs(0);

        int[][] result = new int[answer.size()][m];
        for (int i = 0; i < answer.size(); i++) {
            result[i] = answer.get(i);
        }
        return result;
    }

    private void dfs(int count) {
        if (count == m) {
            answer.add(choice.clone());
            return;
        }

        for (int next = 1; next <= n; next++) {
            if (!visited[next]) {
                visited[next] = true;
                choice[count] = next;
                dfs(count + 1);
                visited[next] = false;
            }
        }
    }
}

