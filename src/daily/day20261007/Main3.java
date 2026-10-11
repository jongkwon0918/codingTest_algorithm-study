package daily.day20261007;

import java.util.*;

/*
3번. 해킹

컴퓨터는 총 n대이며, 번호는 1번부터 n번까지이다.
start는 처음 감염된 컴퓨터의 번호이다.
컴퓨터 하나가 처음 감염된 뒤 의존 관계를 따라 감염이 퍼진다.
dependencies[i]={a,b,seconds}는 a가 b에 의존한다는 뜻이다.
b가 감염되면 seconds초 후 a가 감염된다. 반대 방향으로는 퍼지지 않는다.
여러 경로로 감염될 수 있으면 가장 이른 감염 시각을 사용한다.
{총 감염 컴퓨터 수, 마지막 컴퓨터가 감염된 시각}을 반환하라.
처음 감염된 start 컴퓨터도 개수에 포함하며 감염 시각은 0이다.

제한사항
- 1 <= n <= 10,000, 1 <= start <= n
- 1 <= dependencies.length <= 100,000
- 1 <= a,b <= n, a != b, 0 <= seconds <= 1,000
- 같은 의존 관계 (a,b)는 중복되지 않는다.

입출력 예시
n=3, start=2, dependencies={{2,1,5},{3,2,5}} -> {2,5}
n=3, start=1, dependencies={{2,1,2},{3,1,8},{3,2,4}} -> {3,6}
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        try {
        System.out.println(Arrays.toString(solution.solution(3, 2, new int[][]{
                {2, 1, 5}, {3, 2, 5}
        })) + " / 기대값: [2, 5]");
        System.out.println(Arrays.toString(solution.solution(3, 1, new int[][]{
                {2, 1, 2}, {3, 1, 8}, {3, 2, 4}
        })) + " / 기대값: [3, 6]");
        } catch (UnsupportedOperationException e) {
            System.out.println("미구현: Solution3을 작성한 뒤 다시 실행하세요.");
        }
    }
}

class Solution3 {
    public int[] solution(int n, int start, int[][] dependencies) {
        List<List<int[]>> graph = new ArrayList<>();
        for(int i=0; i<=n; i++){
            graph.add(new ArrayList<>());
        }

        for(int[] dependency : dependencies){
            int a = dependency[0];
            int b = dependency[1];
            int seconds = dependency[2];

            graph.get(b).add(new int[]{a, seconds});
        }

        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start]=1;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->Integer.compare(a[1], b[1]));
        pq.offer(new int[]{start, 0});

        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            int currStart = curr[0];
            int currTime = curr[1];

            if(dist[currStart]<currTime){
                continue;
            }

            for(int[] next : graph.get(currStart)){
                int nextStart = next[0];
                int nextTime = next[1] + currTime;

                if(dist[nextStart]>nextTime){
                    dist[nextStart]=nextTime;
                    pq.offer(new int[]{nextStart, nextTime});
                }
            }
        }
        int count=0;
        int maxtime=0;
        for(int i=0; i<=n; i++){
            if(dist[i]!=Integer.MAX_VALUE){
                count++;
                maxtime = Math.max(maxtime, dist[i]);
            }
        }
        return new int[]{count, maxtime};
    }
}
