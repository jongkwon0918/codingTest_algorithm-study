package daily.day20261108.answers;

import java.util.*;

/*
4. 두 버튼으로 목표 숫자 맞추기

다섯 자리까지 표시할 수 있는 장치에 현재 숫자 start가 적혀 있다.
버튼을 최대 limit번 눌러 정확히 goal로 만들려고 한다.
A버튼: 현재 숫자를1 증가시킨다.
B버튼: 현재 숫자를2배로 만든 뒤, 가장 높은 자리의 숫자를1 줄인다.
예를 들어123 -> 246 -> 146, 5 -> 10 -> 0, 3 -> 6 -> 5이다.
현재 숫자가0이면 B를 눌러도0이다.
어느 과정에서든99999를 넘으면 실패한다.
특히 B에서2배로 만든 직후99999를 넘으면, 이후 자릿수를 줄여도 그 이동은 사용할 수 없다.
각 버튼 조작은1회이며, limit번 이내에 목표를 만들 수 있으면 최소 횟수를 문자열로 반환하라.
불가능하면 "ANG"를 반환한다. 처음부터 목표와 같으면0회다.
각 숫자는 정수로 다루며 표시의 앞쪽0은 값에 영향을 주지 않는다.

제한사항
- 0 <= start,goal <= 99999
- 1 <= limit <= 99999

입출력 예시
start=123, limit=1, goal=146 -> "1"
start=0, limit=1, goal=1 -> "1"
start=5, limit=1, goal=5 -> "0"
start=50000, limit=1, goal=90000 -> "ANG"
B는2배 직후100000이 되어 사용할 수 없다.
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        System.out.println(solution.solution(123, 1, 146)); // 1
        System.out.println(solution.solution(0, 1, 1)); // 1
        System.out.println(solution.solution(5, 1, 5)); // 0
        System.out.println(solution.solution(50000, 1, 90000)); // ANG
    }
}

class Solution4 {
    public String solution(int start, int limit, int goal) {
        int[] dist = new int[100000];
        Arrays.fill(dist, -1);
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        dist[start] = 0;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == goal) {
                return String.valueOf(dist[current]);
            }
            if (dist[current] == limit) {
                continue;
            }

            int nextA = current + 1;
            if (nextA <= 99999 && dist[nextA] == -1) {
                dist[nextA] = dist[current] + 1;
                queue.offer(nextA);
            }

            int doubled = current * 2;
            if (current > 0 && doubled <= 99999) {
                int place = 1;
                while (place * 10 <= doubled) {
                    place *= 10;
                }
                int nextB = doubled - place;
                if (dist[nextB] == -1) {
                    dist[nextB] = dist[current] + 1;
                    queue.offer(nextB);
                }
            }
        }
        return "ANG";
    }
}

