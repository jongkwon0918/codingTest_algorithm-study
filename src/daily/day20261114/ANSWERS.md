# 2026-11-14 정답 및 해설

정답 영역입니다. 문제를 먼저 풀고 읽으세요.

확인 범위: 다섯 문제 모두 백준 직접 조회에서 정상 본문을 얻지 못했습니다. 공개된 문제별 기록으로 번호·제목·조건·제한을 대조했고, 출처는 각 해설 아래에 남겼습니다. 원문 직접 대조 완료라고 주장하지 않습니다. Main에는 원본 규칙을 유지한 자체 테스트 입력도 있습니다.

## 1. 친구의 친구까지 세기

[완성된 실행 Main](answers/Main1.java)

### 완성된 Solution

```java
import java.util.*;

class Solution1 {
    public int solution(String[] friends) {
        int n = friends.length;
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (friends[i].charAt(j) == 'Y') {
                    graph[i].add(j);
                }
            }
        }
        int answer = 0;
        for (int start = 0; start < n; start++) {
            int[] dist = new int[n];
            Arrays.fill(dist, -1);
            Queue<Integer> queue = new LinkedList<>();
            dist[start] = 0;
            queue.offer(start);
            int count = 0;
            while (!queue.isEmpty()) {
                int current = queue.poll();
                if (dist[current] == 2) {
                    continue;
                }
                for (int next : graph[current]) {
                    if (dist[next] == -1) {
                        dist[next] = dist[current] + 1;
                        count++;
                        queue.offer(next);
                    }
                }
            }
            answer = Math.max(answer, count);
        }
        return answer;
    }
}
```

### 핵심 풀이와 예제 추적

1. 알고리즘: 사람마다 깊이를 2로 제한한 BFS.

2. 이유: 친구 관계를 몇 번 이어야 하는지가 기준이다. 처음 발견한 사람의 거리가 최소이며 두 번까지만 포함한다.

3. 실행 순서: 인접 리스트 만들기 → 시작 사람별 dist 초기화 → 거리1,2인 새 사람 세기 → 최댓값 갱신.

4. 핵심: dist=-1이 방문 여부까지 나타낸다. 시작점은 거리0이라 다시 세지 않는다. 입력에 양방향 관계가 이미 두 행에 주어지므로 각 행에서 적힌 방향을 한 번씩만 추가한다.

5. 예제 추적: 2번 시작 시 dist=[-1,-1,0,-1,-1]. 1,3을 넣으면 [-1,1,0,1,-1], 다음으로0,4를 넣으면 [2,1,0,1,2]가 되고 count=4다. 거리2인0,4에서는 더 확장하지 않는다.

6. 시간복잡도 O(n*(n+E)), 최악 O(n^3). 추가 공간 O(n+E), E는 인접 리스트에 저장한 항목 수다.

원본: **BOJ 1058 친구** — [백준 링크](https://www.acmicpc.net/problem/1058)

대조 출처: [공개 기록1](https://bono039.tistory.com/1082), [공개 기록2](https://yjs2673.tistory.com/17).

---

## 2. 여덟 가지 이동으로 만나기

[완성된 실행 Main](answers/Main2.java)

### 완성된 Solution

```java
import java.util.*;

class Solution2 {
    public int solution(int a, int b, int start, int goal) {
        int[] dist = new int[100001];
        Arrays.fill(dist, -1);
        Queue<Integer> queue = new LinkedList<>();
        dist[start] = 0;
        queue.offer(start);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == goal) {
                return dist[current];
            }
            int[] nextPositions = {
                current - 1, current + 1,
                current - a, current + a,
                current - b, current + b,
                current * a, current * b
            };
            for (int next : nextPositions) {
                if (next >= 0 && next <= 100000 && dist[next] == -1) {
                    dist[next] = dist[current] + 1;
                    queue.offer(next);
                }
            }
        }
        return -1;
    }
}
```

### 핵심 풀이와 예제 추적

1. 알고리즘: 정수 위치를 정점으로 보는 BFS.

2. 이유: 여덟 이동 모두 비용이 동작1회로 같다. 곱셈이라고 비용이 커지는 것이 아니다.

3. 실행 순서: 시작 거리0 → 여덟 다음 위치 계산 → 범위 안 미방문 위치 추가 → 목표를 꺼내면 반환.

4. 핵심: 다음 위치가 중복되어도 dist=-1 검사로 한 번만 넣는다. current*a는 최대3000000이라 int 범위 안이다. 별도 visited나 큐의 횟수 필드가 필요 없다.

5. 예제 추적: 1에서 시작하면 거리1인 새 위치는 0,2,3,4다. 이후3을 처리할 때6을 거리2로 발견한다. 한 번 이동으로6에 갈 수 없으므로 정답2다.

6. U=100001일 때 시간 O(8U)=O(U), 공간 O(U).

원본: **BOJ 12761 돌다리** — [백준 링크](https://www.acmicpc.net/problem/12761)

대조 출처: [공개 기록1](https://wgrgwg.tistory.com/m/entry/백준-12761-돌다리-Java), [공개 기록2](https://murra.tistory.com/40).

---

## 3. 조건을 차례대로 적용하는 약속 장소

[완성된 실행 Main](answers/Main3.java)

### 완성된 Solution

```java
import java.util.*;

class Solution3 {
    public int solution(int n, int[][] roads, int j, int s) {
        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            graph[road[0]].add(new int[]{road[1], road[2]});
            graph[road[1]].add(new int[]{road[0], road[2]});
        }
        int[] distJ = dijkstra(j, graph);
        int[] distS = dijkstra(s, graph);
        int minSum = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            if (i != j && i != s && distJ[i] != Integer.MAX_VALUE
                    && distS[i] != Integer.MAX_VALUE) {
                minSum = Math.min(minSum, distJ[i] + distS[i]);
            }
        }
        int answer = -1;
        int minJ = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            if (i != j && i != s && distJ[i] != Integer.MAX_VALUE
                    && distS[i] != Integer.MAX_VALUE
                    && distJ[i] + distS[i] == minSum && distJ[i] <= distS[i]) {
                // 번호 오름차순으로 보므로 같은 시간에서는 먼저 고른 작은 번호를 유지한다.
                if (distJ[i] < minJ) {
                    minJ = distJ[i];
                    answer = i;
                }
            }
        }
        return answer;
    }

    private int[] dijkstra(int start, List<int[]>[] graph) {
        int[] dist = new int[graph.length];
        Arrays.fill(dist, Integer.MAX_VALUE);
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[1], b[1]));
        dist[start] = 0;
        pq.offer(new int[]{start, 0});
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];
            int cost = current[1];
            if (cost > dist[node]) {
                continue;
            }
            for (int[] next : graph[node]) {
                int nextCost = cost + next[1];
                if (nextCost < dist[next[0]]) {
                    dist[next[0]] = nextCost;
                    pq.offer(new int[]{next[0], nextCost});
                }
            }
        }
        return dist;
    }
}
```

### 핵심 풀이와 예제 추적

1. 알고리즘: 두 번의 다익스트라 + 순서가 있는 후보 필터링.

2. 이유: 도로별 시간이 다르므로 가중치 최단거리가 필요하다. 이동 결과를 얻은 뒤 선택 조건을 순서대로 적용한다.

3. 실행 순서: 양방향 그래프 → j,s에서 각각 최단거리 → 두 출발지 제외 후 최소 합 → 그 합을 만족하며 지헌이가 늦지 않는 후보 → 지헌이 시간과 번호 비교.

4. 핵심: minSum을 구할 때 distJ<=distS를 적용하지 않는다. 먼저 최소 합을 확정해야 한다. 도달 불가 값은 더하기 전에 제외한다.

5. 예제 추적: 두 번째 예제는 2번 비용(4,1),3번 비용(3,3)이다. minSum=5가 먼저 정해진다. 2번은4>1이라 탈락하고,3번은합6이라 탈락하여 -1이다.

6. 정점 V, 도로 E일 때 시간 O((V+E)log(V+E)), 공간 O(V+E). 도로 중복을 포함하는 PQ 기본 구현 기준이다.

원본: **BOJ 17270 연예인은 힘들어** — [백준 링크](https://www.acmicpc.net/problem/17270)

대조 출처: [공개 기록1](https://littlesam95.tistory.com/entry/백준BOJ-17270-연예인은-힘들어Gold-2), [공개 기록2](https://ljm0850.tistory.com/163).

---

## 4. 순환 노선까지 몇 정거장

[완성된 실행 Main](answers/Main4.java)

### 완성된 Solution

```java
import java.util.*;

class Solution4 {
    List<Integer>[] graph;
    boolean[] visited;
    boolean[] cycle;
    int[] parent;
    boolean found;

    public int[] solution(int n, int[][] connections) {
        graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : connections) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }
        visited = new boolean[n + 1];
        cycle = new boolean[n + 1];
        parent = new int[n + 1];
        found = false;
        dfs(1, 0);

        int[] dist = new int[n + 1];
        Arrays.fill(dist, -1);
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (cycle[i]) {
                dist[i] = 0;
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int next : graph[current]) {
                if (dist[next] == -1) {
                    dist[next] = dist[current] + 1;
                    queue.offer(next);
                }
            }
        }
        return Arrays.copyOfRange(dist, 1, n + 1);
    }

    private void dfs(int current, int previous) {
        visited[current] = true;
        for (int next : graph[current]) {
            if (next == previous) {
                continue;
            }
            if (!visited[next]) {
                parent[next] = current;
                dfs(next, current);
                if (found) {
                    return;
                }
            } else {
                // 부모가 아닌 이미 방문한 조상을 만나면 그 사이의 경로가 고리다.
                cycle[next] = true;
                int node = current;
                while (node != next) {
                    cycle[node] = true;
                    node = parent[node];
                }
                found = true;
                return;
            }
        }
    }
}
```

### 핵심 풀이와 예제 추적

1. 알고리즘: 재귀 DFS로 고리 찾기 + 고리 전체를 시작점으로 하는 BFS.

2. 이유: 거리0인 역이 여러 개다. 고리를 먼저 식별하면 그 모든 역에서 동시에 최단거리를 퍼뜨릴 수 있다.

3. 실행 순서: 그래프 연결 → DFS 부모 기록과 고리 표시 → 모든 고리 역을 거리0으로 큐에 넣기 → 가지의 거리 계산.

4. 핵심: 양방향 그래프에서 직전 부모로 돌아가는 간선은 고리가 아니다. 부모가 아닌 방문한 조상을 만나면 parent를 거슬러 고리만 표시한다. found로 고리를 찾은 뒤 DFS 전체를 종료한다.

5. 예제 추적: dfs(1)→dfs(2)→dfs(3)에서 이미 방문한1을 만난다. parent[3]=2,parent[2]=1이므로1,2,3을 고리로 표시한다. BFS 초기 큐=[1,2,3], 거리0. 2에서6을거리1,3에서4를거리1,4에서5를거리2로 넣는다.

6. 구간 수가 n이므로 시간 O(n), 공간 O(n). 재귀 깊이는 최대 n이다.

원본: **BOJ 16947 서울 지하철 2호선** — [백준 링크](https://www.acmicpc.net/problem/16947)

대조 출처: [공개 기록1](https://chaewsscode.tistory.com/209), [공개 기록2](https://kyu9341.github.io/algorithm/2020/03/06/algorithm16947/).

---

## 5. 냄새를 따라 움직이는 상어들

[완성된 실행 Main](answers/Main5.java)

### 완성된 Solution

```java
import java.util.*;

class Solution5 {
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(int[][] board, int[] directions, int[][][] priorities, int k) {
        int n = board.length;
        int m = directions.length;
        // {행, 열, 방향(0~3)}. 행이 -1이면 사라진 상어다.
        int[][] sharks = new int[m + 1][3];
        int[][] smellOwner = new int[n][n];
        int[][] smellTime = new int[n][n];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                int id = board[r][c];
                if (id > 0) {
                    sharks[id] = new int[]{r, c, directions[id - 1] - 1};
                    smellOwner[r][c] = id;
                    smellTime[r][c] = k;
                }
            }
        }
        int alive = m;
        for (int time = 1; time <= 1000; time++) {
            int[][] nextBoard = new int[n][n];
            // 번호가 작은 상어부터 넣으면 충돌 시 먼저 도착 정보가 기록된 상어가 이긴다.
            // 냄새 배열은 이 반복문 동안 바꾸지 않으므로 이동 결정은 동시에 이루어진다.
            for (int id = 1; id <= m; id++) {
                if (sharks[id][0] == -1) {
                    continue;
                }
                int r = sharks[id][0];
                int c = sharks[id][1];
                int direction = sharks[id][2];
                int nextR = -1;
                int nextC = -1;
                int nextDirection = direction;
                // mode0: 냄새 없는 칸, mode1: 자기 냄새 칸.
                for (int mode = 0; mode < 2 && nextR == -1; mode++) {
                    for (int option : priorities[id - 1][direction]) {
                        int d = option - 1;
                        int nr = r + dr[d];
                        int nc = c + dc[d];
                        if (nr >= 0 && nr < n && nc >= 0 && nc < n
                                && ((mode == 0 && smellTime[nr][nc] == 0)
                                || (mode == 1 && smellTime[nr][nc] > 0
                                    && smellOwner[nr][nc] == id))) {
                            nextR = nr;
                            nextC = nc;
                            nextDirection = d;
                            break;
                        }
                    }
                }
                if (nextBoard[nextR][nextC] == 0) {
                    nextBoard[nextR][nextC] = id;
                    sharks[id] = new int[]{nextR, nextC, nextDirection};
                } else {
                    sharks[id][0] = -1;
                    alive--;
                }
            }
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    if (smellTime[r][c] > 0) {
                        smellTime[r][c]--;
                        if (smellTime[r][c] == 0) {
                            smellOwner[r][c] = 0;
                        }
                    }
                    if (nextBoard[r][c] > 0) {
                        smellOwner[r][c] = nextBoard[r][c];
                        smellTime[r][c] = k;
                    }
                }
            }
            if (alive == 1) {
                return time;
            }
        }
        return -1;
    }
}
```

### 핵심 풀이와 예제 추적

1. 알고리즘: 시간 순서대로 진행하는 시뮬레이션. 최단경로 탐색이나 DP는 필요 없다.

2. 이유: 방향 우선순위가 다음 행동을 하나로 정한다. 이동 후보를 선택할 때 참조하는 냄새는 모든 상어에게 동일한 시점이어야 한다.

3. 실행 순서: 상어별 좌표·방향과 초기 냄새 저장 → 이전 냄새로 목적지 결정 → 다음 판에서 충돌 처리 → 냄새 감소 → 생존 상어 냄새 기록 → 종료 검사.

4. 핵심: 현재 정보와 nextBoard를 분리한다. id 오름차순 처리 시 nextBoard에 이미 상어가 있으면 현재 상어가 번호가 더 크다. 냄새 시간과 주인은 다른 의미이고, 상어의 생존은 행=-1로 표현해 별도 alive 배열은 만들지 않는다. 처음에는 인접한 빈칸이 있고 이후에는 직전 칸의 자기 냄새가 있으므로 이동 후보가 존재한다.

5. 예제 추적: 첫 예제에서 두 상어는 냄새 없는(1,1)을 고른다. 1번이 nextBoard[1][1]=1을 기록하고,2번도 같은 목적지라 사라져 alive=1이다. 예전 냄새2는1로 줄고 중앙에는1번 냄새2가 기록된다. 반환1.

6. 최대 시간 T=1000, 상어 수 M, 판 크기 N일 때 O(T*(N*N+M)), 공간 O(N*N+M). 우선순위 입력은 O(M) 크기다.

원본: **BOJ 19237 어른 상어** — [백준 링크](https://www.acmicpc.net/problem/19237)

대조 출처: [공개 기록1](https://hjpark00.tistory.com/70), [공개 기록2](https://chanhuiseok.github.io/posts/baek-26/).

---


