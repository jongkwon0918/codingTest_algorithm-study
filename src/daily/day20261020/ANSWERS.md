# 정답 및 해설 — 2026-10-20

완성 코드 이후 알고리즘·이유·실행 순서·핵심 코드·예제 추적·복잡도를 설명합니다.

## 1. 방향에 따라 이어지는 판자

[완성된 실행 파일](answers/Main1.java)

```java
import java.util.*;

class Solution1 {
    String[] floor;
    boolean[][] visited;
    int n;
    int m;

    public int solution(String[] floor) {
        this.floor = floor;
        n = floor.length;
        m = floor[0].length();
        visited = new boolean[n][m];
        int count = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if (!visited[row][col]) {
                    dfs(row, col);
                    count++;
                }
            }
        }
        return count;
    }

    private void dfs(int row, int col) {
        visited[row][col] = true;
        char current = floor[row].charAt(col);
        int nr = row;
        int nc = col;
        if (current == '-') {
            nc++;
        } else {
            nr++;
        }
        if (nr < n && nc < m
                && floor[nr].charAt(nc) == current && !visited[nr][nc]) {
            dfs(nr, nc);
        }
    }
}
```

1. **알고리즘:** 재귀 DFS로 같은 판자를 방문 처리한다.
2. **이유:** 연결 방향이 문자마다 다르다. 가로 판자는 오른쪽, 세로 판자는 아래쪽의 같은 문자로만 연결된다.
3. **실행 순서:** 방문 배열 초기화 → 위에서 아래·왼쪽에서 오른쪽으로 순회 → 미방문 칸에서 연결 판자 전체 탐색 → 판자 수 증가.
4. **핵심 코드:** 순회 순서상 새 판자는 가장 왼쪽 또는 가장 위의 칸에서 시작하므로 오른쪽·아래로만 재귀해도 된다. 한 판자를 방문할 때 방향은 유지된다. nr,nc는 현재 좌표에서 증가하기만 하므로 음수 범위 검사는 필요 없다.
5. **예제 추적:** -|- / -|-에서 (0,0)의 '-'는 오른쪽이 '|'라 종료,count=1 → (0,1)의 '|'는 (1,1)까지 방문,count=2 → (0,2)의 '-'는 끝,count=3 → (1,0)은 새 가로 판자,count=4 → (1,1)은 방문해서 건너뜀 → (1,2)에서 count=5.
6. **복잡도:** 시간·공간 O(N*M), 재귀 깊이는 한 판자의 길이로 최대50이다.

원본: **백준 1388 — 바닥 장식**

[원본 문제](https://www.acmicpc.net/problem/1388) · [조건 대조 기록](https://applewind.tistory.com/27)

## 2. 출발점에서 모든 정점까지

[완성된 실행 파일](answers/Main2.java)

```java
import java.util.*;

class Solution2 {
    public String[] solution(int n, int[][] edges, int start) {
        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(new int[]{edge[1], edge[2]});
        }

        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[1], b[1]));
        pq.offer(new int[]{start, 0});
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currentNode = current[0];
            int currentDistance = current[1];
            if (currentDistance > dist[currentNode]) {
                continue;
            }
            for (int[] next : graph[currentNode]) {
                int nextNode = next[0];
                int nextDistance = currentDistance + next[1];
                if (nextDistance < dist[nextNode]) {
                    dist[nextNode] = nextDistance;
                    pq.offer(new int[]{nextNode, nextDistance});
                }
            }
        }

        String[] answer = new String[n];
        for (int i = 1; i <= n; i++) {
            answer[i - 1] = dist[i] == Integer.MAX_VALUE
                    ? "INF" : String.valueOf(dist[i]);
        }
        return answer;
    }
}
```

1. **알고리즘:** PriorityQueue를 사용하는 다익스트라.
2. **이유:** 간선 비용이 양수이며 서로 다르다. 한 출발점에서 모든 정점까지의 거리가 필요하다.
3. **실행 순서:** 단방향 인접 리스트 → dist 초기화 → 거리순 큐 처리와 갱신 → 번호순 결과 배열 생성.
4. **핵심 코드:** 반대 방향 간선을 추가하지 않는다. 도달하지 못한 INF는 결과에서도 그대로 표시한다. dist가 갱신 상태를 관리하므로 별도 visited는 없다. 최단거리의 최대는 (N-1)*10으로 int 범위이다.
5. **예제 추적:** dist[1]=0 → 1에서 2는2,3은3 → 2에서 3은같은3이라 유지,4는7 → 3에서4를4로 갱신 → 나중의 {4,7}은 현재 거리4보다 커서 무시 → 5는 미도달 → [0,2,3,4,INF].
6. **복잡도:** 시간 O((V+E) log(V+E+1)), 공간 O(V+E).

원본: **백준 1753 — 최단경로**

[원본 문제](https://www.acmicpc.net/problem/1753) · [조건 대조 기록](https://howtolivelikehuman.tistory.com/206)

## 3. 층을 오가며 출구 찾기

[완성된 실행 파일](answers/Main3.java)

```java
import java.util.*;

class Solution3 {
    public String solution(String[][] building) {
        int floors = building.length;
        int n = building[0].length;
        int m = building[0][0].length();
        int[][][] dist = new int[floors][n][m];
        Queue<int[]> queue = new LinkedList<>();
        int[] dz = {-1, 1, 0, 0, 0, 0};
        int[] dr = {0, 0, -1, 1, 0, 0};
        int[] dc = {0, 0, 0, 0, -1, 1};

        for (int z = 0; z < floors; z++) {
            for (int row = 0; row < n; row++) {
                Arrays.fill(dist[z][row], -1);
                for (int col = 0; col < m; col++) {
                    if (building[z][row].charAt(col) == 'S') {
                        dist[z][row][col] = 0;
                        queue.offer(new int[]{z, row, col});
                    }
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int z = current[0];
            int row = current[1];
            int col = current[2];
            if (building[z][row].charAt(col) == 'E') {
                return "Escaped in " + dist[z][row][col] + " minute(s).";
            }
            for (int d = 0; d < 6; d++) {
                int nz = z + dz[d];
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nz >= 0 && nz < floors && nr >= 0 && nr < n && nc >= 0 && nc < m
                        && building[nz][nr].charAt(nc) != '#' && dist[nz][nr][nc] == -1) {
                    dist[nz][nr][nc] = dist[z][row][col] + 1;
                    queue.offer(new int[]{nz, nr, nc});
                }
            }
        }
        return "Trapped!";
    }
}
```

1. **알고리즘:** 3차원 BFS.
2. **이유:** 층 이동을 포함한 여섯 방향의 비용이 모두1분이므로 첫 방문 시간이 최소이다.
3. **실행 순서:** 거리 초기화와 S 탐색 → {층,행,열} 큐 삽입 → 여섯 방향 확장 → E를 꺼내면 거리 포함 문자열 반환.
4. **핵심 코드:** dist가 거리와 방문 여부를 담당하며 큐에는 좌표만 넣는다. 층·행·열의 범위를 먼저 검사하고 벽과 방문 조건을 확인한다. 기존2차원 BFS의 좌표에 층 하나가 추가된 형태다.
5. **예제 추적:** 0층 S.,1층 #E에서 queue=[{0,0,0}],거리0 → 같은 좌표1층은 벽,오른쪽{0,0,1}은빈칸이라 거리1 → 그 칸에서 위층{1,0,1}은 E라 거리2 → E를 꺼내 Escaped in 2 minute(s). 반환.
6. **복잡도:** 층 L·행 R·열 C에 대해 시간·공간 O(L*R*C).

원본: **백준 6593 — 상범 빌딩**

[원본 문제](https://www.acmicpc.net/problem/6593) · [조건 대조 기록](https://chakae.tistory.com/11)

## 4. 세 장애물로 시선 가리기

[완성된 실행 파일](answers/Main4.java)

```java
import java.util.*;

class Solution4 {
    char[][] board;
    List<int[]> teachers;
    int n;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public String solution(String[] hallway) {
        n = hallway.length;
        board = new char[n][n];
        teachers = new ArrayList<>();
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                board[row][col] = hallway[row].charAt(col);
                if (board[row][col] == 'T') {
                    teachers.add(new int[]{row, col});
                }
            }
        }
        return dfs(0, 0) ? "YES" : "NO";
    }

    private boolean dfs(int start, int count) {
        if (count == 3) {
            return safe();
        }
        for (int i = start; i < n * n; i++) {
            int row = i / n;
            int col = i % n;
            if (board[row][col] == 'X') {
                board[row][col] = 'O';
                boolean possible = dfs(i + 1, count + 1);
                board[row][col] = 'X';
                if (possible) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean safe() {
        for (int[] teacher : teachers) {
            for (int d = 0; d < 4; d++) {
                int row = teacher[0] + dr[d];
                int col = teacher[1] + dc[d];
                while (row >= 0 && row < n && col >= 0 && col < n) {
                    if (board[row][col] == 'O') {
                        break;
                    }
                    if (board[row][col] == 'S') {
                        return false;
                    }
                    row += dr[d];
                    col += dc[d];
                }
            }
        }
        return true;
    }
}
```

1. **알고리즘:** 재귀 DFS로 세 장애물의 조합을 고르고 직선 시야를 시뮬레이션한다.
2. **이유:** 한 장애물이 여러 시선을 막을 수 있어 모든 위치 조합을 검사해야 한다. n<=6이라 기본 완전탐색이 가능하다.
3. **실행 순서:** 선생님 좌표 수집 → 빈칸에 O 설치 → 다음 인덱스부터 선택 → 세 개면 시야 검사 → 돌아오며 X로 복원 → 성공 조합이 있으면 YES.
4. **핵심 코드:** i+1은 같은 세 칸의 선택 순서 중복을 막고, X로 복원하는 것은 다른 조합에 장애물 흔적이 남지 않게 한다. 이 두 역할은 다르다. 성공 결과도 복원 후 반환한다. 학생을 하나라도 보면 실패이고, O만 시선을 막는다.
5. **예제 추적:** TXS / XXX / XXX에서 첫 후보(0,1)를 O로 변경 → 다른 빈칸 두 개를 골라 O → safe에서 선생님의 오른쪽 시야가 (0,1)의 O에서 중단 → 다른 방향에도 학생이 없으므로 true → 세 장애물을 차례로 복원하며 true 반환 → YES. TSX는 바로 옆 S를 가릴 칸이 없어 모든 조합이 실패한다.
6. **복잡도:** 빈칸 수 E, 선생님 수 T라 하면 시간 O(조합(E,3)*T*N+N^6), 느슨한 상한이다. 공간 O(N²+T), 재귀 깊이는3이다.

원본: **백준 18428 — 감시 피하기**

[원본 문제](https://www.acmicpc.net/problem/18428) · [조건 대조 기록](https://leeyeongeol.github.io/baekjoon/Baekjoon-18428번-감시-피하기/)

## 5. 길을 건너지 않고 만날 수 있을까

[완성된 실행 파일](answers/Main5.java)

```java
import java.util.*;

class Solution5 {
    int n;
    boolean[][][] blocked;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(int n, int[][] roads, int[][] cows) {
        this.n = n;
        blocked = new boolean[n][n][4];
        for (int[] road : roads) {
            int r1 = road[0] - 1;
            int c1 = road[1] - 1;
            int r2 = road[2] - 1;
            int c2 = road[3] - 1;
            for (int d = 0; d < 4; d++) {
                if (r1 + dr[d] == r2 && c1 + dc[d] == c2) {
                    blocked[r1][c1][d] = true;
                }
                if (r2 + dr[d] == r1 && c2 + dc[d] == c1) {
                    blocked[r2][c2][d] = true;
                }
            }
        }

        int answer = 0;
        for (int i = 0; i < cows.length; i++) {
            boolean[][] visited = bfs(cows[i][0] - 1, cows[i][1] - 1);
            for (int j = i + 1; j < cows.length; j++) {
                if (!visited[cows[j][0] - 1][cows[j][1] - 1]) {
                    answer++;
                }
            }
        }
        return answer;
    }

    private boolean[][] bfs(int startRow, int startCol) {
        boolean[][] visited = new boolean[n][n];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n
                        && !blocked[row][col][d] && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return visited;
    }
}
```

1. **알고리즘:** 소마다 BFS로 길을 건너지 않는 도달 가능 칸을 찾는다.
2. **이유:** 길은 칸 자체를 막는 것이 아니라 특정 두 칸 사이의 이동만 제한한다. 돌아가는 경로가 있는지 탐색해야 한다.
3. **실행 순서:** 길의 양방향 이동 제한 기록 → 소 하나에서 BFS → 그 이후 번호의 소 중 미도달 소를 세기 → 반복.
4. **핵심 코드:** blocked[row][col][d]는 현재 칸에서 d방향으로 이동할 수 없는지를 뜻한다. 모든 길은 양방향으로 기록한다. 거리 출력이 없어 visited만 사용한다. j=i+1부터 세어 중복 쌍을 막는다. 입력 좌표는1부터라 배열 접근에서1을 뺀다.
5. **예제 추적:** 첫 예제의 (3,3)에서 왼쪽(3,2),위(2,3) 두 이동이 모두 길로 막혀 시작 칸만 방문한다. 다른 두 소가 모두 미도달이라 answer=2. 다음 (2,2)에서는 돌아가는 경로로 (2,3)에 도달해 추가하지 않는다. 두 번째 예제에서는 직접 오른쪽은 막혔지만 (1,1)→(2,1)→(2,2)→(1,2)로 만나므로0이다.
6. **복잡도:** 소 K마리, 길 R개에 대해 시간 O(R+K*N²+K²), 공간 O(N²). 입력 소가 최대100마리라 소별 BFS로 충분하다.

원본: **백준 14466 — 소가 길을 건너간 이유 6**

[원본 문제](https://www.acmicpc.net/problem/14466) · [조건 대조 기록](https://suhyeokeee.tistory.com/208)

## 원본 확인과 형식 변환

백준 직접 조회에서는 정상 문제 본문을 얻지 못했습니다. 공개된 문제별 기록을 대조하여 번호·제목과 제한·핵심 조건을 확인했습니다. 원문 직접 대조 완료라고 주장하지 않습니다. 각 문제의 대조 출처는 위에 남겼습니다.

원본 입력을 메서드 매개변수로, 출력을 반환값으로 바꿨습니다. 2번은 원본처럼 미도달을 INF로 표시하고, 3번은 원본의 성공·실패 문자열을 유지합니다. 5번의 좌표는 원본처럼1부터 시작하므로 코드에서0부터 시작하는 배열 인덱스로 바꿉니다.

최근 상태 복원과 이동 조건을 연습한 흐름을 반영했습니다. 4번은 조합의 인덱스 이동과 실제 지도 상태 복원이 별개의 역할임을, 5번은 칸의 방문 여부와 두 칸 사이의 이동 제한이 별개임을 연습합니다.

