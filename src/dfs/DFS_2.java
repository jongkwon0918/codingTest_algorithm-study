package dfs;//정사각형 모양의 지도가 있습니다. 1은 집이 있는 곳을, 0은 집이 없는 곳을 나타냅니다.
//철수는 이 지도를 가지고 연결된 집의 모임인 '단지'를 정의하고, 단지에 번호를 붙이려 합니다.
//여기서 연결되었다는 것은 어떤 집이 좌우, 혹은 위아래로 다른 집이 있는 경우를 의미합니다. 
//(대각선 방향은 연결된 것으로 보지 않습니다.) 지도를 입력하여 총 단지수를 구하고, 
//각 단지에 속하는 집의 수를 오름차순으로 정렬하여 배열에 담아 반환하는 solution 함수를 작성해 주세요.
//제한사항
//지도의 크기 N은 5 이상 25 이하의 자연수입니다.
//반환하는 정수 배열의 첫 번째 원소에는 총 단지수를 넣고, 
//그 뒤로는 각 단지에 속하는 집의 수를 오름차순으로 정렬하여 담아주세요.

import java.util.*;

public class DFS_2 {

	public static void main(String[] args) {
		Solution8 sol = new Solution8();
    	
        int[][] map = {
            {0, 1, 1, 0, 1, 0, 0},
            {0, 1, 1, 0, 1, 0, 0},
            {1, 1, 1, 0, 1, 0, 0},
            {0, 1, 1, 0, 1, 1, 1},
            {0, 1, 1, 0, 0, 0, 0},
            {0, 1, 1, 0, 1, 1, 0},
            {0, 1, 1, 0, 1, 1, 0},
        };
        
        int[] result = sol.solution(map);
        System.out.println("결과: " + Arrays.toString(result));
	}

}

class Solution8 {
    int n;
    boolean[][] visited;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int[] solution(int[][] map) {
        n = map.length;
        visited = new boolean[n][n];
        List<Integer> complexSizes = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // 집이 있고 아직 방문하지 않은 곳이라면 새로운 단지 시작!
                if (map[i][j] == 1 && !visited[i][j]) {
                    int size = dfs(i, j, map);
                    complexSizes.add(size);
                }
            }
        }

        // 단지 크기 오름차순 정렬
        Collections.sort(complexSizes);

        // 첫 번째 원소로 총 단지수를 넣기 위해 크기 + 1인 배열 생성
        int[] answer = new int[complexSizes.size() + 1];
        answer[0] = complexSizes.size(); // 총 단지수
        for (int i = 0; i < complexSizes.size(); i++) {
            answer[i + 1] = complexSizes.get(i);
        }

        return answer;
    }

    private int dfs(int r, int c, int[][] map) {
        visited[r][c] = true;
        int count = 1; // 현재 집 포함

        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];

            // 범위를 벗어나지 않고, 집이 있으며(1), 아직 방문하지 않았다면
            if (nr >= 0 && nr < n && nc >= 0 && nc < n && map[nr][nc] == 1 && !visited[nr][nc]) {
                count += dfs(nr, nc, map); // 연결된 집들의 개수를 누적 합산
            }
        }

        return count; // 이 단지에 속한 총 집의 수 반환
    }
}