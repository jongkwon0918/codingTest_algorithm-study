# 2026-11-17 정답 및 해설

문제를 먼저 풀고 읽는 정답 영역입니다.

확인 범위: 다섯 문제의 백준 직접 조회에서는 정상 본문을 얻지 못했습니다(안내 페이지 또는 조회 오류). 공개 문제 기록에서 실재 번호·제목·조건·제한을 대조했습니다. 3번은 원 출제 기관 USACO의 해당 문제도 확인했습니다. 원본 링크와 대조 출처는 각 해설 아래에 있습니다. Main에는 규칙을 유지한 자체 테스트 입력도 포함합니다.

## 1. 나무 모양 길의 거리

[완성된 실행 Main](answers/Main1.java)

### 완성된 Solution

```java
import java.util.*;

class Solution1 {
    List<int[]>[] graph;
    boolean[] visited;

    public int[] solution(int n, int[][] roads, int[][] queries) {
        graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            graph[road[0]].add(new int[]{road[1], road[2]});
            graph[road[1]].add(new int[]{road[0], road[2]});
        }
        int[] answer = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            visited = new boolean[n + 1];
            answer[i] = dfs(queries[i][0], queries[i][1], 0);
        }
        return answer;
    }

    private int dfs(int current, int target, int distance) {
        visited[current] = true;
        if (current == target) {
            return distance;
        }
        for (int[] next : graph[current]) {
            if (!visited[next[0]]) {
                int result = dfs(next[0], target, distance + next[1]);
                if (result != -1) {
                    return result;
                }
            }
        }
        return -1;
    }
}
```

### 핵심 풀이와 예제 추적

1. 알고리즘: 질의마다 재귀 DFS.

2. 이유: 트리는 두 지점 사이 경로가 하나다. 길마다 가중치가 달라도 여러 경로 중 최솟값을 비교할 필요가 없다.

3. 실행 순서: 양방향 인접 리스트 생성 → 질의마다 visited 초기화 → 길이를 누적하며 목표 탐색 → 반환값 저장.

4. 핵심: 재귀 호출 결과를 result로 받고 성공한 값은 위로 전달한다. -1은 그 가지에서 목표를 못 찾았다는 뜻이다. 모든 경로를 비교하는 문제가 아니므로 방문 복원은 필요 없다.

5. 예제 추적: dfs(4,3,0)→dfs(2,3,4)→dfs(1,3,7)→dfs(3,3,9). 9가 각 호출을 거쳐 그대로 반환된다.

6. 질의 수 Q일 때 시간 O(Q*n), 공간 O(n). 최대 재귀 깊이는 n이다.

원본: **BOJ 1240 노드사이의 거리** — [백준 링크](https://www.acmicpc.net/problem/1240)

대조 출처: [확인 자료1](https://noname-yet.tistory.com/153).

---

## 2. 여러 음식 중 가장 가까운 곳

[완성된 실행 Main](answers/Main2.java)

### 완성된 Solution

```java
import java.util.*;

class Solution2 {
    public int solution(String[] map) {
        int n = map.length;
        int m = map[0].length();
        boolean[][] visited = new boolean[n][m];
        Queue<int[]> queue = new LinkedList<>();
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (map[r].charAt(c) == '2') {
                    visited[r][c] = true;
                    queue.offer(new int[]{r, c, 0});
                }
            }
        }
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];
            int distance = current[2];
            char value = map[r].charAt(c);
            if (value == '3' || value == '4' || value == '5') {
                return distance;
            }
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && map[nr].charAt(nc) != '1' && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc, distance + 1});
                }
            }
        }
        return -1;
    }
}
```

### 핵심 풀이와 예제 추적

1. 알고리즘: 한 시작점에서 여러 목적지를 찾는 BFS.

2. 이유: 모든 이동 비용이1이며 BFS에서 처음 꺼낸 음식 칸의 거리가 전체 음식 중 최단거리다.

3. 실행 순서: 시작점 찾기 → 방문 표시와 큐 초기화 → 장애물이 아닌 이웃 추가 → 처음 음식을 만나면 반환.

4. 핵심: 음식마다 탐색을 따로 하지 않는다. visited와 큐의 거리만 쓰며 별도 dist는 만들지 않는다.

5. 예제 추적: 첫 지도에서 (0,0,0)→(0,1,1)→(0,2,2)→(0,3,3)을 지나 (0,4,4)를 꺼내면3번 음식이므로4를 반환한다.

6. 칸 수 V=행*열일 때 시간 O(V), 공간 O(V).

원본: **BOJ 17129 윌리암슨수액빨이딱따구리가 정보섬에 올라온 이유** — [백준 링크](https://www.acmicpc.net/problem/17129)

대조 출처: [확인 자료1](https://doshisha.tistory.com/83), [확인 자료2](https://ymg5218.tistory.com/141?category=1101571).

---

## 3. 세 번 이동할 때마다 쉬어 가기

[완성된 실행 Main](answers/Main3.java)

### 완성된 Solution

```java
import java.util.*;

class Solution3 {
    public int solution(int[][] grass, int moveTime) {
        int n = grass.length;
        // 마지막으로 풀을 먹은 뒤 이동 횟수: 0,1,2.
        int[][][] dist = new int[n][n][3];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                Arrays.fill(dist[r][c], Integer.MAX_VALUE);
            }
        }
        // {행, 열, 이동 횟수의 나머지, 총 시간}
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[3], b[3]));
        dist[0][0][0] = 0;
        pq.offer(new int[]{0, 0, 0, 0});
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int r = current[0];
            int c = current[1];
            int count = current[2];
            int cost = current[3];
            if (cost > dist[r][c][count]) {
                continue;
            }
            if (r == n - 1 && c == n - 1) {
                return cost;
            }
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
                    int nextCount = (count + 1) % 3;
                    int nextCost = cost + moveTime;
                    if (nextCount == 0) {
                        nextCost += grass[nr][nc];
                    }
                    if (nextCost < dist[nr][nc][nextCount]) {
                        dist[nr][nc][nextCount] = nextCost;
                        pq.offer(new int[]{nr, nc, nextCount, nextCost});
                    }
                }
            }
        }
        return -1;
    }
}
```

### 핵심 풀이와 예제 추적

1. 알고리즘: 이동 횟수 나머지를 상태에 포함하는 다익스트라.

2. 이유: 같은 칸이어도 다음 이동에서 풀을 먹는지가 다르면 이후 비용이 다르다. 위치만의 dist로 합치면 안 된다.

3. 실행 순서: dist[r][c][0~2] 초기화 → 시작 상태0 → 한 칸 이동 후 나머지 갱신 → 나머지0이면 풀 비용 추가 → 거리 갱신.

4. 핵심: count=(이동 횟수%3).0→1→2→0으로 순환한다. 별도 visited는 필요 없다. 상태별 최단거리이므로 DP 문제가 아니다. 최단 시간을 기준으로 큐에서 처음 꺼낸 목표 상태를 반환한다.

5. 예제 추적: 모두1인3*3에서 (0,0,나머지0,시간0)→(0,1,1,1)→(0,2,2,2)→(1,2,0,4)→(2,2,1,5). 세 번째 이동만 이동1+풀1이 든다.

6. V=3*n*n일 때 시간 O(V log V), 공간 O(V). 최적 경로는 최소한 단조 경로보다 비싸지 않아 이 제한의 시간은 int로 표현 가능하다.

원본: **BOJ 14461 소가 길을 건너간 이유 7** — [백준 링크](https://www.acmicpc.net/problem/14461)

대조 출처: [확인 자료1](https://usaco.org/index.php?page=viewproblem2&cpid=717), [확인 자료2](https://justicehui.github.io/usaco/2019/06/22/BOJ14461/), [확인 자료3](https://drasgon.tistory.com/357).

---

## 4. 두 돌로 상대 무리 둘러싸기

[완성된 실행 Main](answers/Main4.java)

### 완성된 Solution

```java
import java.util.*;

class Solution4 {
    int n;
    int m;
    int answer;
    int[][] map;
    List<int[]> blanks;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(int[][] board) {
        n = board.length;
        m = board[0].length;
        answer = 0;
        map = new int[n][m];
        blanks = new ArrayList<>();
        for (int r = 0; r < n; r++) {
            map[r] = board[r].clone();
            for (int c = 0; c < m; c++) {
                if (map[r][c] == 0) {
                    blanks.add(new int[]{r, c});
                }
            }
        }
        dfs(0, 0);
        return answer;
    }

    private void dfs(int start, int count) {
        if (count == 2) {
            answer = Math.max(answer, countCaptured());
            return;
        }
        for (int i = start; i < blanks.size(); i++) {
            int r = blanks.get(i)[0];
            int c = blanks.get(i)[1];
            map[r][c] = 1;
            dfs(i + 1, count + 1);
            map[r][c] = 0;
        }
    }

    private int countCaptured() {
        boolean[][] visited = new boolean[n][m];
        int total = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (map[r][c] == 2 && !visited[r][c]) {
                    Queue<int[]> queue = new LinkedList<>();
                    queue.offer(new int[]{r, c});
                    visited[r][c] = true;
                    int size = 0;
                    boolean hasBlank = false;
                    while (!queue.isEmpty()) {
                        int[] current = queue.poll();
                        size++;
                        for (int d = 0; d < 4; d++) {
                            int nr = current[0] + dr[d];
                            int nc = current[1] + dc[d];
                            if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
                                if (map[nr][nc] == 0) {
                                    hasBlank = true;
                                } else if (map[nr][nc] == 2 && !visited[nr][nc]) {
                                    visited[nr][nc] = true;
                                    queue.offer(new int[]{nr, nc});
                                }
                            }
                        }
                    }
                    if (!hasBlank) {
                        total += size;
                    }
                }
            }
        }
        return total;
    }
}
```

### 핵심 풀이와 예제 추적

1. 알고리즘: 재귀 DFS 조합·백트래킹 + 상대 무리별 BFS.

2. 이유: 두 빈칸의 모든 선택을 확인할 수 있고, 포위 여부는 개별 돌이 아닌 연결 무리 전체의 인접 빈칸으로 판단한다.

3. 실행 순서: 빈칸 목록 수집 → 두 칸에1을 놓기 → BFS로 무리 크기와 빈칸 존재 확인 → 잡은 돌 합산 → 놓은 두 돌 복원.

4. 핵심: 빈칸을 하나 발견해도 무리 전체 탐색은 끝내야 visited가 완성된다. 판 밖은 빈칸이 아니다. 상대 돌은 실제로 지우지 않고 포위 여부만 집계하여 다른 무리 판정에 영향을 주지 않는다.

5. 예제 추적: 두 번째 판의 빈칸(2,0),(2,2)에1을 놓는다. (0,0)에서 시작한 BFS는2인 일곱 칸을 방문하고 hasBlank=false다. total=7을 얻고 DFS가 두 칸을 다시0으로 되돌린다.

6. 빈칸 수 B, 전체 칸 수 V일 때 시간 O(B^2*V), 공간 O(V). DFS 깊이는2다.

원본: **BOJ 16988 Baaaaaaaaaduk2 (Easy)** — [백준 링크](https://www.acmicpc.net/problem/16988)

대조 출처: [확인 자료1](https://riveroverflow.tistory.com/entry/BOJ-16988-Baaaaaaaaaduk2-Easy), [확인 자료2](https://jun-bae.tistory.com/18).

---

## 5. 복제되는 물고기와 세 걸음의 선택

[완성된 실행 Main](answers/Main5.java)

### 완성된 Solution

```java
import java.util.*;

class Solution5 {
    // 물고기: 왼쪽부터 시계방향. 반시계 회전은 인덱스를 하나 줄인다.
    int[] fishDr = {0, -1, -1, -1, 0, 1, 1, 1};
    int[] fishDc = {-1, -1, 0, 1, 1, 1, 0, -1};
    // 상어 경로의 사전순: 위, 왼쪽, 아래, 오른쪽.
    int[] sharkDr = {-1, 0, 1, 0};
    int[] sharkDc = {0, -1, 0, 1};
    int[][][] fishCount;
    int[][] smell;
    int sharkR;
    int sharkC;
    int maxEat;
    int[] choice;
    int[] bestPath;

    public int solution(int[][] fishes, int[] shark, int turns) {
        fishCount = new int[4][4][8];
        smell = new int[4][4];
        sharkR = shark[0] - 1;
        sharkC = shark[1] - 1;
        choice = new int[3];
        for (int[] fish : fishes) {
            fishCount[fish[0] - 1][fish[1] - 1][fish[2] - 1]++;
        }
        for (int turn = 0; turn < turns; turn++) {
            // 1. 복제할 위치와 방향별 마릿수 보관.
            int[][][] backup = new int[4][4][8];
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    backup[r][c] = fishCount[r][c].clone();
                }
            }
            // 2. 모든 물고기 이동.
            moveFish();
            // 3. 상어의 세 걸음 중 가장 많이 먹는 경로 선택.
            maxEat = -1;
            bestPath = new int[3];
            dfs(sharkR, sharkC, 0, 0, new boolean[4][4]);
            for (int d : bestPath) {
                sharkR += sharkDr[d];
                sharkC += sharkDc[d];
                int count = countAt(sharkR, sharkC);
                if (count > 0) {
                    Arrays.fill(fishCount[sharkR][sharkC], 0);
                    // 바로 다음 감소에서2가 되어 다음 두 번의 이동 동안 남는다.
                    smell[sharkR][sharkC] = 3;
                }
            }
            // 4. 냄새 감소. 물고기가 이동할 때는 감소 전 냄새를 사용했다.
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    if (smell[r][c] > 0) {
                        smell[r][c]--;
                    }
                }
            }
            // 5. 원래 위치와 방향으로 복제 물고기 추가.
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    for (int d = 0; d < 8; d++) {
                        fishCount[r][c][d] += backup[r][c][d];
                    }
                }
            }
        }
        int answer = 0;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                answer += countAt(r, c);
            }
        }
        return answer;
    }

    private void moveFish() {
        int[][][] next = new int[4][4][8];
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                for (int d = 0; d < 8; d++) {
                    int count = fishCount[r][c][d];
                    if (count == 0) {
                        continue;
                    }
                    boolean moved = false;
                    for (int rotation = 0; rotation < 8; rotation++) {
                        int nextDirection = (d - rotation + 8) % 8;
                        int nr = r + fishDr[nextDirection];
                        int nc = c + fishDc[nextDirection];
                        if (nr >= 0 && nr < 4 && nc >= 0 && nc < 4
                                && !(nr == sharkR && nc == sharkC) && smell[nr][nc] == 0) {
                            next[nr][nc][nextDirection] += count;
                            moved = true;
                            break;
                        }
                    }
                    if (!moved) {
                        next[r][c][d] += count;
                    }
                }
            }
        }
        fishCount = next;
    }

    private void dfs(int r, int c, int depth, int eaten, boolean[][] visited) {
        if (depth == 3) {
            // 동률에서는 먼저 찾은 사전순 경로를 유지한다.
            if (eaten > maxEat) {
                maxEat = eaten;
                bestPath = choice.clone();
            }
            return;
        }
        for (int d = 0; d < 4; d++) {
            int nr = r + sharkDr[d];
            int nc = c + sharkDc[d];
            if (nr >= 0 && nr < 4 && nc >= 0 && nc < 4) {
                boolean alreadyVisited = visited[nr][nc];
                int nextEaten = eaten;
                if (!alreadyVisited) {
                    nextEaten += countAt(nr, nc);
                    visited[nr][nc] = true;
                }
                choice[depth] = d;
                dfs(nr, nc, depth + 1, nextEaten, visited);
                // 재방문은 허용하되 같은 칸의 물고기는 한 번만 센다.
                if (!alreadyVisited) {
                    visited[nr][nc] = false;
                }
            }
        }
    }

    private int countAt(int r, int c) {
        int count = 0;
        for (int d = 0; d < 8; d++) {
            count += fishCount[r][c][d];
        }
        return count;
    }
}
```

### 핵심 풀이와 예제 추적

1. 알고리즘: 단계별 시뮬레이션 + 깊이3 재귀 DFS.

2. 이유: 물고기 이동은 규칙으로 정해지고, 상어의 이번 세 걸음은 최대64가지다. 이번에 먹는 수와 사전순으로 경로가 정해지며 미래 턴의 최종 물고기 수를 최적화하는 문제가 아니다.

3. 실행 순서: 복제 원본 저장 → 물고기 동시 이동 → DFS로 상어 경로 평가 → 실제로 먹기 → 냄새 감소 → 저장한 물고기 더하기.

4. 핵심: 같은 칸·방향의 물고기는 행동이 같아서 fishCount[r][c][d]에 개수로 저장한다. DFS의 visited는 이동 금지가 아니라 중복 섭취 계산을 막는다. 시작 칸도 경로로 다시 들어올 때 처음 먹으므로 DFS 시작점은 미리 방문 표시하지 않는다. 반시계 회전은 인덱스 감소이고, 새 냄새3은 같은 턴에2로 줄여 다음 두 번 이동에서 유효하다.

5. 예제 추적: 첫 예제 물고기(1,1,왼쪽)는 왼쪽이 범위 밖이므로 반시계 회전해 왼쪽아래도 막히고 아래(2,1)로 이동한다. 상어(4,4)와의 거리는4라 세 걸음으로 먹을 수 없다. 기존 물고기1+복제1=2다. 이동 방향을 끝까지 검사하면 실제 선택은 방향7=아래다.

6. 판 크기와 방향 수가 고정이므로 턴 수 S에 대해 시간 O(S*(4*4*8*8+4^3*8)), 추가 공간 O(4*4*8). 물고기 개수만큼 객체를 만들지 않는다.

원본: **BOJ 23290 마법사 상어와 복제** — [백준 링크](https://www.acmicpc.net/problem/23290)

대조 출처: [확인 자료1](https://blog.yjyoon.dev/boj/2022/01/08/boj-23290/), [확인 자료2](https://littlesam95.tistory.com/entry/백준BOJ-23290-마법사-상어와-복제Gold-1), [확인 자료3](https://weecandoit.tistory.com/13).

---


