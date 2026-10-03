package dijkstra;/*수빈이는 동생과 숨바꼭질을 하고 있습니다.
수빈이는 현재 점 N에 있고, 동생은 점 K에 있습니다. 
수빈이는 걷거나 순간이동을 할 수 있습니다.
만약 수빈이의 위치가 X일 때 걷는다면 1초 후에 X-1 또는 X+1로 이동하게 됩니다. 
하지만 순간이동을 하는 경우에는 0초 후에 2*X의 위치로 이동하게 됩니다.
수빈이와 동생의 위치가 주어졌을 때, 수빈이가 동생을 찾을 수 있는 가장 빠른 시간이 몇 초 후인지 구하는 함수를 작성해주세요.
제한사항
N과 K는 0 이상 100,000 이하의 정수입니다.*/

import java.util.*;

public class Dijkstra_2 {

	public static void main(String[] args) {
		Solution3 sol = new Solution3();

        int n = 5;
        int k = 17;
        
        int result = sol.solution(n, k);
        System.out.println("숨바꼭질 결과: " + result);

	}
}

class Solution3 {
    public int solution(int n, int k) {
        int max = 100001;
        int[] dist = new int[max];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[n] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[1]-b[1]);
        pq.offer(new int[]{n,0});

        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            int currStart = curr[0];
            int currCost = curr[1];

            if(currStart == k){
                return currCost;
            }
            if(currCost > dist[currStart]){
                continue;
            }

            int[] nexts = {currStart-1, currStart+1};

            for(int next : nexts){
               if(next>=0 && next<max && dist[next]>currCost+1){
                   dist[next] = currCost+1;
                   pq.offer(new int[]{next, currCost+1});
               }
            }
            int next = currStart*2;
            if(next<max && dist[next]>currCost){
                dist[next] = currCost;
                pq.offer(new int[]{next,currCost});
            }
        }
        return 0;
    }
}
