package daily.day20261114;

import java.util.*;

/*
2. 여덟 가지 이동으로 만나기

0번부터 100000번까지 돌이 일렬로 있다. start에서 goal로 이동하려고 한다.
현재 위치가 x일 때 한 번의 동작으로 다음 중 하나를 선택할 수 있다.
x-1, x+1, x-a, x+a, x-b, x+b, x*a, x*b.
한 동작에 여러 방법을 이어서 사용하지 않는다. 같은 방법은 여러 번 사용할 수 있다.
0보다 작거나 100000보다 큰 위치로 이동할 수 없다.
goal에 도달하는 최소 동작 수를 반환하라. 처음부터 같은 위치이면 0이다.

제한사항
- 2 <= a,b <= 30
- 0 <= start,goal <= 100000
- 모든 입력에서 목표에 도달할 수 있다.

입출력 예시
a=2,b=3,start=1,goal=6 -> 2
1 -> 3 -> 6처럼 두 번 이동한다.
a=2,b=3,start=10,goal=7 -> 1
a=2,b=3,start=0,goal=0 -> 0
*/
public class Main2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        System.out.println(solution.solution(2, 3, 1, 6)); // 2
        System.out.println(solution.solution(2, 3, 10, 7)); // 1
        System.out.println(solution.solution(2, 3, 0, 0)); // 0
    }
}

class Solution2 {
    int max = 100000;
    public int solution(int a, int b, int start, int goal) {
        boolean[] visited = new boolean[max+1];

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{start, 0});
        visited[start]=true;

        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int currStart = curr[0];
            int currCost = curr[1];

            if(currStart==goal){
                return currCost;
            }

            int[] nexts = {currStart-1, currStart+1, currStart-a, currStart+a, currStart-b, currStart+b, currStart*a, currStart*b};
            for(int next : nexts){
                if(next>=0 && next<=max && !visited[next]){
                    queue.offer(new int[]{next, currCost+1});
                    visited[next]=true;
                }
            }
        }

        return -1;
    }
}

