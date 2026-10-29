package daily.day20261030.answers;

import java.util.*;

/*
4. 복사와 붙여넣기로 목표 개수 만들기

화면에는 처음에 이모티콘이 1개 있고, 클립보드는 비어 있다.
다음 세 동작 중 하나를 선택할 때마다 1초가 걸린다.
1. 화면의 이모티콘 전체를 클립보드에 복사한다. 기존 클립보드 내용은 덮어쓴다.
2. 클립보드의 이모티콘 전체를 화면에 붙여넣는다. 화면 개수에 클립보드 개수가 더해진다.
3. 화면에서 이모티콘 한 개를 삭제한다.
일부만 복사할 수 없으며, 클립보드가 비어 있으면 붙여넣을 수 없다.
화면이 비어 있으면 삭제할 수 없다. 붙여넣거나 삭제해도 클립보드 내용은 유지된다.
화면에 정확히 target개를 만드는 데 필요한 최소 시간을 반환하라.
목표 개수를 잠시 넘겼다가 삭제하는 동작도 허용된다.

제한사항
- 2 <= target <= 1000
- 처음 화면 개수는1, 클립보드 개수는0이다.
- 복사·붙여넣기·삭제는 각각 별도의1초짜리 동작이다.

입출력 예시
target=2 -> 2
복사(화면1,저장1) -> 붙여넣기(화면2,저장1).
target=4 -> 4
target=6 -> 5
복사 -> 붙여넣기 -> 복사 -> 붙여넣기 -> 붙여넣기.
target=18 -> 8
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        System.out.println(solution.solution(2)); // 2
        System.out.println(solution.solution(4)); // 4
        System.out.println(solution.solution(6)); // 5
        System.out.println(solution.solution(18)); // 8
    }
}

class Solution4 {
    public int solution(int target) {
        int maxCount = target * 2;
        int[][] dist = new int[maxCount + 1][maxCount + 1];
        for (int i = 0; i <= maxCount; i++) {
            Arrays.fill(dist[i], -1);
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{1, 0});
        dist[1][0] = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int screen = current[0];
            int clipboard = current[1];
            int nextTime = dist[screen][clipboard] + 1;

            if (screen == target) {
                return dist[screen][clipboard];
            }

            // 복사: 화면은 그대로, 클립보드를 화면 개수로 덮어쓴다.
            if (dist[screen][screen] == -1) {
                dist[screen][screen] = nextTime;
                queue.offer(new int[]{screen, screen});
            }

            // 붙여넣기: 클립보드 내용은 유지한다.
            int nextScreen = screen + clipboard;
            if (clipboard > 0 && nextScreen <= maxCount
                    && dist[nextScreen][clipboard] == -1) {
                dist[nextScreen][clipboard] = nextTime;
                queue.offer(new int[]{nextScreen, clipboard});
            }

            // 삭제: 클립보드 내용은 유지한다.
            if (screen > 0 && dist[screen - 1][clipboard] == -1) {
                dist[screen - 1][clipboard] = nextTime;
                queue.offer(new int[]{screen - 1, clipboard});
            }
        }
        return -1;
    }
}

