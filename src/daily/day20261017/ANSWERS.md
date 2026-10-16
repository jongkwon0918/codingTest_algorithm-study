# 정답 및 해설 — 2026-10-17

완성 코드를 먼저 제공하고 알고리즘·선택 이유·실행 순서·핵심 코드·예제 추적·복잡도를 설명합니다.

## 1. 적힌 수만큼 점프하기

[완성된 실행 파일](answers/Main1.java)

```java
import java.util.*;

class Solution1 {
    int[][] board;
    boolean[][] visited;
    int n;
    int[] dr = {0, 1};
    int[] dc = {1, 0};

    public String solution(int[][] board) {
        this.board = board;
        n = board.length;
        visited = new boolean[n][n];
        return dfs(0, 0) ? "HaruHaru" : "Hing";
    }

    private boolean dfs(int row, int col) {
        visited[row][col] = true;
        if (board[row][col] == -1) {
            return true;
        }
        int jump = board[row][col];
        for (int d = 0; d < 2; d++) {
            int nr = row + dr[d] * jump;
            int nc = col + dc[d] * jump;
            if (nr >= 0 && nr < n && nc >= 0 && nc < n
                    && !visited[nr][nc]) {
                if (dfs(nr, nc)) {
                    return true;
                }
            }
        }
        return false;
    }
}
```

1. **알고리즘:** 재귀 DFS.
2. **이유:** 최소 이동 횟수가 아니라 도달 가능 여부만 필요하다. 가능한 오른쪽·아래 이동을 탐색하면 된다.
3. **실행 순서:** 방문 배열 초기화 → (0,0)부터 재귀 → 목표면 true → 적힌 수만큼 두 방향 이동 → 성공 경로가 없으면 false.
4. **핵심 코드:** 이동량에 현재 칸의 jump를 곱한다. 방문 표시를 먼저 하므로 0칸에서 같은 칸을 무한 재귀하지 않는다. 경로에 따라 칸의 이동 규칙이 달라지지 않으므로 방문 처리를 되돌릴 필요가 없다.
5. **예제 추적:** [[1,1],[1,-1]]에서 dfs(0,0) → 방문 → 오른쪽(0,1) → 오른쪽은 범위 밖 → 아래(1,1) → -1이라 true → 호출자에게 true 전달 → HaruHaru. 시작값0인 경우 두 이동 모두 이미 방문한 시작 칸이라 재귀하지 않고 Hing이다.
6. **복잡도:** 시간·공간 O(N²). 여기서는 N<=3이라 작지만 일반적인 격자 탐색 패턴을 연습한다.

원본: **백준 16173 — 점프왕 쩰리 (Small)**

[원본 문제](https://www.acmicpc.net/problem/16173) · [문제 조건 대조 기록](https://codesign.tistory.com/31)

## 2. 여섯 방향으로 움직이는 말

[완성된 실행 파일](answers/Main2.java)

```java
import java.util.*;

class Solution2 {
    public int solution(int n, int[] start, int[] target) {
        int[] dr = {-2, -2, 0, 0, 2, 2};
        int[] dc = {-1, 1, -2, 2, -1, 1};
        int[][] dist = new int[n][n];
        for (int row = 0; row < n; row++) {
            Arrays.fill(dist[row], -1);
        }
        Queue<int[]> queue = new LinkedList<>();
        dist[start[0]][start[1]] = 0;
        queue.offer(new int[]{start[0], start[1]});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            if (row == target[0] && col == target[1]) {
                return dist[row][col];
            }
            for (int d = 0; d < 6; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n
                        && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[row][col] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return -1;
    }
}
```

1. **알고리즘:** BFS.
2. **이유:** 여섯 종류의 이동이 모두 한 번의 동작이라 첫 방문 거리가 최단거리이다.
3. **실행 순서:** dist=-1 초기화 → 시작점 거리0 → 큐 순서로 여섯 이웃 탐색 → 목표를 꺼내면 거리 반환 → 큐가 비면 -1.
4. **핵심 코드:** dist로 미방문 여부를 판단해 별도 visited를 만들지 않는다. 큐에도 거리를 중복 저장하지 않는다. 장애물이 없어도 주어진 이동만으로는 도달하지 못하는 칸이 있다.
5. **예제 추적:** (0,0),dist=0에서 유효한 첫 이동은 (0,2),(2,1)이며 거리1 → (2,1)을 처리하면 (4,2)를 거리2로 발견 → 목표를 꺼내2 반환. 행 변화가 항상 짝수라 시작 행0에서 목표 행1에는 갈 수 없어 -1이다.
6. **복잡도:** 시간·공간 O(N²).

원본: **백준 16948 — 데스 나이트**

[원본 문제](https://www.acmicpc.net/problem/16948) · [문제 조건 대조 기록](https://seaotter.tistory.com/38)

## 3. 하루 이동 제한 안에서 떡 돌리기

[완성된 실행 파일](answers/Main3.java)

```java
import java.util.*;

class Solution3 {
    public int solution(int n, int[][] roads, int dailyLimit, int home) {
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            graph[road[0]].add(new int[]{road[1], road[2]});
            graph[road[1]].add(new int[]{road[0], road[2]});
        }

        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[home] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[1], b[1]));
        pq.offer(new int[]{home, 0});

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

        Arrays.sort(dist);
        int days = 1;
        int used = 0;
        // 양수 도로이므로 정렬 후 첫 값0은 내 집의 거리이다.
        for (int i = 1; i < n; i++) {
            if (dist[i] == Integer.MAX_VALUE || dist[i] * 2 > dailyLimit) {
                return -1;
            }
            int roundTrip = dist[i] * 2;
            if (used + roundTrip > dailyLimit) {
                days++;
                used = 0;
            }
            used += roundTrip;
        }
        return days;
    }
}
```

1. **알고리즘:** 다익스트라 후 거리 정렬과 일별 합산.
2. **이유:** 집마다 이동 비용이 달라 최단거리를 먼저 구해야 한다. 한 집씩 왕복하고 가까운 순으로 방문하는 조건이라 정렬한 왕복 거리를 순서대로 처리한다.
3. **실행 순서:** 양방향 그래프 → 내 집에서 최단거리 → 거리 정렬 → 왕복 가능 여부 확인 → 오늘 남은 거리에 들어가면 배달, 아니면 다음 날 배달.
4. **핵심 코드:** 편도가 아니라 dist*2를 하루 예산에서 사용한다. 도달 불가 INF는 곱하기 전에 검사한다. 정렬 뒤에는 도시 번호가 필요 없고 거리만 필요하다. 자기 집의0은 건너뛴다. 정해진 순서에서 오늘 가능한 배달을 미룰 이유가 없어 제한을 넘을 때만 다음 날로 간다. 최대 단순 경로 길이9,990,000, 왕복19,980,000으로 int 범위이다.
5. **예제 추적:** dist=[0,1,2,3],dailyLimit=8 → days=1,used=0 → 왕복2로 used=2 → 왕복4로 used=6 → 다음 왕복6은6+6>8 → days=2,used=0으로 변경한 뒤 used=6 →2 반환. dailyLimit=5이면 왕복6인 집은 어느 날에도 갈 수 없어 -1이다.
6. **복잡도:** 시간 O((V+E) log(V+E+1)+V log V), 공간 O(V+E).

원본: **백준 20007 — 떡 돌리기**

[원본 문제](https://www.acmicpc.net/problem/20007) · [문제 조건 대조 기록](https://ongveloper.tistory.com/612)

## 4. 위아래 숫자 집합 맞추기

[완성된 실행 파일](answers/Main4.java)

```java
import java.util.*;

class Solution4 {
    int[] numbers;
    boolean[] visited;

    public int[] solution(int[] numbers) {
        this.numbers = numbers;
        int n = numbers.length;
        List<Integer> answer = new ArrayList<>();
        for (int start = 1; start <= n; start++) {
            visited = new boolean[n + 1];
            if (dfs(start, start)) {
                answer.add(start);
            }
        }

        int[] result = new int[answer.size()];
        for (int i = 0; i < answer.size(); i++) {
            result[i] = answer.get(i);
        }
        return result;
    }

    private boolean dfs(int current, int start) {
        visited[current] = true;
        int next = numbers[current - 1];
        if (next == start) {
            return true;
        }
        if (!visited[next]) {
            return dfs(next, start);
        }
        return false;
    }
}
```

1. **알고리즘:** 각 시작점에서 자기 자신으로 돌아오는지 확인하는 재귀 DFS.
2. **이유:** 위 숫자를 정점, 아래 숫자를 다음 정점으로 보면 선택 집합 안에서 화살표가 순환해야 두 집합이 같다. 모든 고리에 속한 숫자를 모으면 최대 집합이다.
3. **실행 순서:** 시작점1부터 순서대로 → 매 시작점마다 방문 배열 초기화 → 다음 숫자를 따라가 시작점으로 돌아오면 포함 → 이미 방문한 다른 정점을 만나면 제외.
4. **핵심 코드:** visited를 시작점마다 새로 만들어 자기 자신으로 돌아오는지 각각 판단한다. 16일의 순열 문제와 달리 고리로 들어가기만 하는 정점은 제외해야 한다. 번호순으로 추가하므로 결과를 정렬할 필요 없다. N<=100이라 별도의 복잡한 상태 관리 없이 O(N²) 탐색으로 충분하다.
5. **예제 추적:** [2,3,2]에서 start=1: dfs(1,1) →2→3→2, 이미 방문한2이며 시작1이 아니므로 false. start=2: 방문 초기화 →2→3→2, next==start라 true로2 추가. start=3: 방문 초기화 →3→2→3, true로3 추가. 결과 [2,3].
6. **복잡도:** 시간 O(N²), 방문 배열·재귀 포함 추가 공간 O(N).

원본: **백준 2668 — 숫자고르기**

[원본 문제](https://www.acmicpc.net/problem/2668) · [문제 조건 대조 기록](https://wonsjung.tistory.com/97)

## 5. 가장 짧은 다리 하나 놓기

[완성된 실행 파일](answers/Main5.java)

```java
import java.util.*;

class Solution5 {
    int n;
    int[][] map;
    int[][] group;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(int[][] map) {
        this.map = map;
        n = map.length;
        group = new int[n][n];
        int count = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (map[row][col] == 1 && group[row][col] == 0) {
                    count++;
                    label(row, col, count);
                }
            }
        }

        int answer = Integer.MAX_VALUE;
        for (int island = 1; island <= count; island++) {
            answer = Math.min(answer, bfs(island));
        }
        return answer;
    }

    private void label(int startRow, int startCol, int number) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});
        group[startRow][startCol] = number;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n
                        && map[nr][nc] == 1 && group[nr][nc] == 0) {
                    group[nr][nc] = number;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
    }

    private int bfs(int island) {
        int[][] dist = new int[n][n];
        Queue<int[]> queue = new LinkedList<>();
        for (int row = 0; row < n; row++) {
            Arrays.fill(dist[row], -1);
            for (int col = 0; col < n; col++) {
                if (group[row][col] == island) {
                    dist[row][col] = 0;
                    queue.offer(new int[]{row, col});
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n
                        && dist[nr][nc] == -1) {
                    if (group[nr][nc] != 0) {
                        return dist[row][col];
                    }
                    dist[nr][nc] = dist[row][col] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return Integer.MAX_VALUE;
    }
}
```

1. **알고리즘:** BFS로 섬에 번호를 붙이고, 각 섬에서 다중 시작점 BFS를 실행한다.
2. **이유:** 같은1이라도 같은 섬인지 다른 섬인지 구분해야 한다. 출발 섬의 어느 육지에서 바다로 나갈지 모르므로 해당 섬의 모든 육지를 거리0으로 시작한다.
3. **실행 순서:** 연결된 육지에 섬 번호 부여 → 섬 하나의 모든 육지 큐 삽입 → 바다를 한 칸씩 확장 → 다른 섬을 처음 만나면 길이 반환 → 모든 출발 섬 중 최솟값 선택.
4. **핵심 코드:** group은 섬 번호, dist는 현재 출발 섬에서의 바다 거리라 역할이 다르다. group=0이 미분류를, dist=-1이 미방문을 나타내 별도 visited는 없다. 출발 섬의 모든 육지는 dist=0이라 재방문 조건에서 제외된다. 따라서 미방문인 육지는 다른 섬이며 그곳에 도착하는 이동은 바다를 추가하지 않아 현재 거리 그대로 반환한다. 전체 확장을 한 번에 처리하는 방식도 있지만, 여기서는 섬 하나씩 탐색하는 기본 구조를 사용한다.
5. **예제 추적:** 첫 지도에서 group=[[1,0,2],[1,0,0],[0,0,0]] → 섬1의 (0,0),(1,0)을 거리0으로 큐에 삽입 → 바다(0,1),(1,1),(2,0)을 거리1로 발견 → (0,1)에서 이웃(0,2)의 group=2를 만남 → 현재 바다 거리1 반환. 바다 한 칸만 메우면 연결되며 육지 도착을 더해2로 세면 안 된다.
6. **복잡도:** 섬 수를 C라 하면 시간 O(C*N²), 공간 O(N²). 섬 분류는 O(N²)이며 N<=100이다.

원본: **백준 2146 — 다리 만들기**

[원본 문제](https://www.acmicpc.net/problem/2146) · [문제 조건 대조 기록](https://www.programmingstory.com/2024/03/2146-bfs.html)

## 원본 확인과 형식 변환

직접 백준 조회에서는 정상 문제 본문을 받지 못했습니다. 문제별 공개 기록을 대조해 실재 여부와 번호·제목·제한·핵심 조건을 확인했습니다. 원문 직접 대조 완료라고 주장하지 않습니다. 각 문제의 대조 출처는 위에 있습니다.

입력은 매개변수, 출력은 반환값으로 바꿨습니다. 1번의 성공·실패 문자열, 2번의 여섯 이동, 3번의 가까운 집 순서·집별 왕복·하루 한도, 4번의 최대 집합과 아래 행 중복 허용, 5번의 서로 다른 두 섬·바다 칸 수를 유지했습니다. 4번은 반환 배열 길이가 원본의 선택 개수에 해당하며 Main에서 함께 출력합니다.

16일 순열 고리 문제와17일4번은 별도 실제 문제입니다. 순열에서는 모든 정점이 고리에 속하지만17일4번은 고리로 들어가기만 하는 정점을 제외해야 합니다. 5번은 기존 섬 개수 연습의 반복이 아니라 섬 분류 결과를 이용해 다른 섬까지의 거리를 구하는 문제입니다.

