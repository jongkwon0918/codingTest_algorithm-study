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

        System.out.println(answer);
    }
}
class Solution3 {

    static class Job {
        int request;
        int time;
        int priority;
        int index;

        Job(int request, int time, int priority, int index) {
            this.request = request;
            this.time = time;
            this.priority = priority;
            this.index = index;
        }
    }

    public int[] solution(int[][] jobs) {
        int n = jobs.length;

        Job[] arr = new Job[n];

        for (int i = 0; i < n; i++) {
            arr[i] = new Job(
                    jobs[i][0],
                    jobs[i][1],
                    jobs[i][2],
                    i
            );
        }

        Arrays.sort(arr, Comparator.comparingInt(a -> a.request));

        PriorityQueue<Job> pq = new PriorityQueue<>((a, b) -> {
            if (a.priority != b.priority) {
                return Integer.compare(b.priority, a.priority);
            }

            if (a.request != b.request) {
                return Integer.compare(a.request, b.request);
            }

            return Integer.compare(a.index, b.index);
        });

        int[] answer = new int[n];

        int time = 0;
        int index = 0;
        int count = 0;

        while (count < n) {

            while (index < n && arr[index].request <= time) {
                pq.offer(arr[index]);
                index++;
            }

            if (pq.isEmpty()) {
                time = arr[index].request;
                continue;
            }

            Job current = pq.poll();

            answer[count++] = current.index;
            time += current.time;
        }

        return answer;
    }
}