package daily.day20261007;

import java.util.*;

/*
2번. 스타트링크

1층부터 f층까지 있는 건물에서 start층에 서 있다.
엘리베이터의 위 버튼은 up층 올라가고, 아래 버튼은 down층 내려간다.
건물 범위를 벗어나는 이동은 불가능하다.
target층에 도착하기 위한 최소 버튼 횟수를 반환하라.
엘리베이터로 도착할 수 없으면 -1을 반환한다.

제한사항
- 1 <= start, target <= f <= 1,000,000
- 0 <= up, down <= 1,000,000

입출력 예시
f=10, start=1, target=10, up=2, down=1 -> 6
f=10, start=1, target=10, up=2, down=0 -> -1
f=10, start=5, target=5, up=0, down=0 -> 0
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        try {
        System.out.println(solution.solution(10, 1, 10, 2, 1) + " / 기대값: 6");
        System.out.println(solution.solution(10, 1, 10, 2, 0) + " / 기대값: -1");
        System.out.println(solution.solution(10, 5, 5, 0, 0) + " / 기대값: 0");
        } catch (UnsupportedOperationException e) {
            System.out.println("미구현: Solution2을 작성한 뒤 다시 실행하세요.");
        }
    }
}

class Solution2 {
    public int solution(int f, int start, int target, int up, int down) {
        boolean[] visited = new boolean[f+1];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{start,0});
        visited[start] = true;
        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int currStart = curr[0];
            int currCount = curr[1];

            if(currStart==target){
                return currCount;
            }

            int currUp = currStart+up;
            int currDown = currStart-down;

            if(currUp>=1 && currUp<=f && !visited[currUp]){
                queue.offer(new int[]{currUp, currCount+1});
                visited[currUp] = true;
            }
            if(currDown>=1 && currDown<=f && !visited[currDown]){
                queue.offer(new int[]{currDown, currCount+1});
                visited[currDown] = true;
            }
        }

        return -1;
    }
}

