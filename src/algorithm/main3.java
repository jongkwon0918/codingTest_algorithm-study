/*3번. 작업 처리

작업마다 다음 정보가 주어진다.

{요청시간, 처리시간, 우선순위}

CPU는 한 번에 하나의 작업만 처리한다.

현재 처리할 수 있는 작업 중 우선순위가 가장 높은 작업을 선택한다.

우선순위가 같다면 요청시간이 빠른 작업을 선택하고, 그것도 같다면 입력 순서가 빠른 작업을 선택한다.

모든 작업을 처리한 순서를 반환하라.*/
package algorithm;
import java.util.*;
public class main3 {
    public static void main(String[] args) {

        Solution3 solution = new Solution3();

        int[][] jobs = {
                        {0, 3, 1},
                        {1, 2, 5},
                        {2, 1, 3},
                        {4, 2, 4}
                };


        int[] answer = solution.solution(jobs);

        System.out.println(Arrays.toString(answer));
    }
}
class Solution3 {

    public int[] solution(int[][] jobs) {
        int n = jobs.length;

        // 원래 입력 순서를 jobs[i][3]에 추가
        int[][] arr = new int[n][4];

        for (int i = 0; i < n; i++) {
            arr[i][0] = jobs[i][0]; // 요청시간
            arr[i][1] = jobs[i][1]; // 처리시간
            arr[i][2] = jobs[i][2]; // 우선순위
            arr[i][3] = i;          // 입력순서
        }

        // 요청시간 기준 정렬
        Arrays.sort(arr, (a, b) ->
                Integer.compare(a[0], b[0]));

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            // 우선순위 높은 순
            if (a[2] != b[2]) {
                return Integer.compare(b[2], a[2]);
            }

            // 요청시간 빠른 순
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }

            // 입력순서 빠른 순
            return Integer.compare(a[3], b[3]);
        });

        int[] answer = new int[n];

        int time = 0;
        int index = 0;
        int count = 0;

        while (count < n) {

            // 현재 시간까지 들어온 작업 전부 PQ에 추가
            while (index < n && arr[index][0] <= time) {
                pq.offer(arr[index]);
                index++;
            }

            // 처리할 작업이 없으면 다음 요청시간으로 이동
            if (pq.isEmpty()) {
                time = arr[index][0];
                continue;
            }

            int[] current = pq.poll();

            answer[count++] = current[3];
            time += current[1];
        }

        return answer;
    }
}
