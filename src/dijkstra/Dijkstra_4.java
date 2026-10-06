// 문제: 배달 (프로그래머스)
package dijkstra;/*N개의 마을로 이루어진 나라가 있습니다.
이 나라의 각 마을에는 1부터 N까지의 번호가 각각 하나씩 부여되어 있습니다. 
각 마을은 양방향으로 통행할 수 있는 도로로 연결되어 있는데, 
서로 다른 마을 간에 이동할 때는 이 도로를 지나야 합니다. 도로를 지날 때 걸리는 시간은 도로별로 다릅니다.
현재 1번 마을에 있는 음식점에서 각 마을로 음식 배달을 하려고 합니다. 각 마을로부터 음식 주문을 받으려고 하는데, 
N개의 마을 중에서 K 이하의 시간에 배달이 가능한 마을의 개수를 구하려 합니다.
마을의 개수 N, 각 마을을 연결하는 도로의 정보 road, 음식 배달이 가능한 시간 K가 매개변수로 주어질 때, 
음식 주문을 받을 수 있는 마을의 개수를 return 하도록 solution 함수를 작성해주세요.
제한사항
마을의 개수 N은 1 이상 50 이하의 자연수입니다.
road의 길이는 1 이상 2,000 이하입니다.
road의 각 원소는 [a, b, c]이며, a, b는 도로가 연결하는 두 마을의 번호, c는 도로를 지나는데 걸리는 시간입니다. (1 이상 10,000 이하)
두 마을 a, b를 연결하는 도로는 여러 개가 있을 수 있습니다.
K는 1 이상 500,000 이하이며, 음식 배달이 가능한 시간을 나타냅니다.
임의의 두 마을 간에 항상 이동 가능한 경로가 존재합니다.*/

import javax.lang.model.type.ArrayType;
import java.util.*;
public class Dijkstra_4 {

	public static void main(String[] args) {
		Solution12 sol = new Solution12();
    	
        int[][] road = {
        				{1,2,1},
        				{2,3,3},
        				{5,2,2},
        				{1,4,2},
        				{5,3,1},
        				{5,4,2}
        			 };
        int n = 5;
        int k = 3;
        int result = sol.solution(n, road, k);
        System.out.println("음식 배달이 가능한 마을: " + result);


	}

}

class Solution12 {
    public int solution(int n, int[][] road, int k) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i=0; i<=n; i++){
            graph.add(new ArrayList<>());
        }
        for(int[] roads : road){
            int u = roads[0];
            int v = roads[1];
            int w = roads[2];

            graph.get(u).add(new int[]{v,w});
            graph.get(v).add(new int[]{u,w});
        }

        int[] dist = new int[n+1];
        Arrays.fill(dist,Integer.MAX_VALUE);
        dist[1] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[1]-b[1]);
        pq.offer(new int[]{1,0});

        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            int currStart = curr[0];
            int currCost = curr[1];

            if(currCost>dist[currStart]){
                continue;
            }

            for(int[] next : graph.get(currStart)){
                int nextStart = next[0];
                int nextCost = next[1]+currCost;
                if(nextCost<dist[nextStart]){
                    dist[nextStart] = nextCost;
                    pq.offer(new int[]{nextStart, nextCost});
                }
            }
        }
        int answer = 0;
        for(int i=1; i<=n; i++){
            if(dist[i]<=k){
                answer++;
            }
        }
        return answer;
    }
}