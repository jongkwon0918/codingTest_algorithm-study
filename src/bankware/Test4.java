/*4번. 작업 처리 순서

여러 작업이 주어진다.

각 작업은 다음 정보를 가진다.

[요청 시간, 작업 시간, 카테고리, 우선순위]

작업은 요청 시간이 되면 대기열에 들어온다.

현재 작업이 끝난 시점까지 요청된 작업들을 대상으로 다음 순서로 작업을 선택한다.

현재 처리 중인 작업이 없다면 대기 중인 작업을 확인한다.
같은 카테고리의 작업은 우선순위를 합산해서 비교한다.
카테고리의 우선순위 합이 높은 카테고리부터 처리한다.
같은 카테고리 안에서는 요청 시간이 빠른 작업을 처리한다.
모든 조건이 같으면 입력 순서가 빠른 작업을 처리한다.

처리된 작업의 번호를 순서대로 반환한다.*/

package bankware;
import java.util.*;

public class Test4 {
    public static void main(String[] args) {

        int[][] jobs = {
                {0, 3, 1, 5},
                {1, 2, 2, 3},
                {2, 1, 1, 4},
                {4, 2, 2, 5}
        };

        Solution3 solution = new Solution3();

        int[] answer = solution.solution(jobs);

        System.out.println(Arrays.toString(answer));
    }
}

class Solution3 {

    public int[] solution(int[][] jobs) {

        int n = jobs.length;

        Integer[] order = new Integer[n];

        for (int i = 0; i < n; i++) {
            order[i] = i;
        }

        Arrays.sort(order, (a, b) -> {
            return Integer.compare(jobs[a][0], jobs[b][0]);
        });

        boolean[] completed = new boolean[n];

        int[] answer = new int[n];

        int answerIndex = 0;
        int currentTime = 0;

        while (answerIndex < n) {

            int selected = -1;

            Map<Integer, Integer> categoryPriority = new HashMap<>();

            // 현재 시간까지 들어온 작업들의 카테고리별 우선순위 합
            for (int i = 0; i < n; i++) {

                if (completed[i]) {
                    continue;
                }

                if (jobs[i][0] <= currentTime) {

                    int category = jobs[i][2];
                    int priority = jobs[i][3];

                    categoryPriority.put(
                            category,
                            categoryPriority.getOrDefault(category, 0) + priority
                    );
                }
            }

            // 가장 높은 카테고리 우선순위 찾기
            int bestCategory = -1;
            int bestPriority = -1;

            for (Map.Entry<Integer, Integer> entry : categoryPriority.entrySet()) {

                int category = entry.getKey();
                int priority = entry.getValue();

                if (priority > bestPriority ||
                        (priority == bestPriority && category < bestCategory)) {

                    bestPriority = priority;
                    bestCategory = category;
                }
            }

            // 선택 가능한 작업 중 조건에 맞는 작업 찾기
            for (int i = 0; i < n; i++) {

                if (completed[i]) {
                    continue;
                }

                if (jobs[i][0] > currentTime) {
                    continue;
                }

                if (jobs[i][2] != bestCategory) {
                    continue;
                }

                if (selected == -1) {
                    selected = i;
                    continue;
                }

                if (jobs[i][0] < jobs[selected][0]) {
                    selected = i;
                } else if (jobs[i][0] == jobs[selected][0] && i < selected) {
                    selected = i;
                }
            }

            // 처리할 작업이 없다면 시간 이동
            if (selected == -1) {

                int nextTime = Integer.MAX_VALUE;

                for (int i = 0; i < n; i++) {

                    if (!completed[i]) {
                        nextTime = Math.min(nextTime, jobs[i][0]);
                    }
                }

                currentTime = nextTime;

                continue;
            }

            completed[selected] = true;

            answer[answerIndex++] = selected + 1;

            currentTime = Math.max(currentTime, jobs[selected][0]);
            currentTime += jobs[selected][1];
        }

        return answer;
    }
}