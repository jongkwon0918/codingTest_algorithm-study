//네트워크란 컴퓨터 상호 간에 정보를 교환할 수 있도록 연결된 형태를 의미합니다. 
//예를 들어, 컴퓨터 A와 B가 직접 연결되어 있고, B와 C가 직접 연결되어 있다면 컴퓨터 A와 C도 간접적으로 연결되어 정보 교환이 가능합니다. 
//따라서 컴퓨터 A, B, C는 모두 같은 네트워크 상에 있다고 할 수 있습니다.
//컴퓨터의 개수 n, 연결에 대한 정보가 담긴 2차원 배열 computers가 매개변수로 주어질 때, 
//네트워크의 총 개수(연결된 덩어리의 개수)를 return 하도록 solution 함수를 작성해 주세요.
//제한사항
//컴퓨터의 개수 n은 1 이상 200 이하인 자연수입니다.
//각 컴퓨터는 0부터 n-1인 정수로 표현합니다.
//i번 컴퓨터와 j번 컴퓨터가 연결되어 있으면 computers[i][j]를 1로, 아니면 0으로 표현합니다.
//자기 자신과의 연결인 computers[i][i]는 항상 1입니다.

package dfs;

public class DFS_1 {
	public static void main(String[] args) {
    	Solution7 sol = new Solution7();
    	
    	int n = 3;
        int[][] computers = {
            {1, 1, 0},
            {1, 1, 0},
            {0, 0, 1}
        };
        
        int result = sol.solution(n, computers);
        System.out.println("컴퓨터 개수: " + result);
    }
}
class Solution7 {
    public int solution(int n, int[][] computers) {
        int answer = 0;
        boolean[] visited = new boolean[n]; // 방문 여부 체크 배열
        
        for (int i = 0; i < n; i++) {
            // 아직 방문하지 않은 컴퓨터라면 새로운 네트워크(덩어리) 발견!
            if (!visited[i]) {
                dfs(i, n, computers, visited);
                answer++;
            }
        }
        
        return answer;
    }
    
    // 깊이 우선 탐색(DFS)을 통해 연결된 모든 컴퓨터를 방문 처리하는 함수
    private void dfs(int current, int n, int[][] computers, boolean[] visited) {
        visited[current] = true; // 현재 컴퓨터 방문 처리
        
        // 다른 컴퓨터들과의 연결 상태 확인
        for (int next = 0; next < n; next++) {
            // 자기 자신이 아니고, 연결되어 있으며(1), 아직 방문하지 않았다면 탐색 계속
            if (computers[current][next] == 1 && !visited[next]) {
                dfs(next, n, computers, visited);
            }
        }
    }
}