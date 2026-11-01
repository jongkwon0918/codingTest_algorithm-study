# 2026-11-01 정답 및 해설

문제 풀이 후 확인하세요. 완성 Solution 코드가 먼저 나오고, 알고리즘·선택 이유·실행 순서·핵심 코드·실제 예제 추적·복잡도를 이어서 설명합니다. answers/Main1~Main5는 그대로 실행할 수 있습니다.

## 출처 확인 범위

오늘의 1~5번 모두 백준 직접 조회에서는 정상 문제 본문을 얻지 못했습니다. 오류 또는 서비스 안내가 표시되어 직접 원문 대조를 완료한 것은 아닙니다. 대신 각 항목의 공개 문제별 기록에 남은 번호·제목·제한·규칙·예제를 대조했습니다. 확인 출처를 항목별로 남기며, 문제를 새로 창작한 것이 아니라 기존 문제의 입출력을 메서드로 옮긴 것입니다. 작은 예제는 규칙을 설명하기 위한 학습용 입력입니다.

## 1. 서로 다른 다섯 사람의 연결

[완성 Main1.java](answers/Main1.java)

```java
import java.util.*;

class Solution1 {
    List<Integer>[] graph;
    boolean[] visited;

    public int solution(int n, int[][] friendships) {
        graph = new ArrayList[n];
        visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : friendships) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        for (int start = 0; start < n; start++) {
            if (dfs(start, 1)) {
                return 1;
            }
        }
        return 0;
    }

    private boolean dfs(int current, int count) {
        visited[current] = true;
        if (count == 5) {
            visited[current] = false;
            return true;
        }

        for (int next : graph[current]) {
            if (!visited[next]) {
                if (dfs(next, count + 1)) {
                    visited[current] = false;
                    return true;
                }
            }
        }

        visited[current] = false;
        return false;
    }
}
```

1. 알고리즘: 재귀 DFS와 백트래킹. 다섯 명으로 이루어진 단순 경로를 찾는다.
2. 이유: 같은 연결 요소의 크기가5 이상인지만 보면 별 모양 그래프를 잘못 판단한다. 현재 경로에서 서로 다른 다섯 사람을 실제로 이어야 한다.
3. 실행 순서: 모든 사람을 출발점으로 시도 -> 현재 사람 방문 표시 -> 다섯 명이면 성공 -> 미방문 친구로 재귀 -> 돌아올 때 방문 해제.
4. 핵심: visited는 전체 탐색 완료 여부가 아니라 현재 경로에 들어 있는지를 뜻한다. 재귀에서 돌아오면 해제하므로 다른 경로에서도 그 사람을 쓸 수 있다. count는 간선 수가 아닌 사람 수여서1로 시작한다.
5. 예제 추적: dfs(0,1)에서 visited[0]=true -> dfs(1,2) -> dfs(2,3) -> dfs(3,4) -> dfs(4,5)로 성공한다. 별 모양에서는0->1에서 막혀1을 해제하고0->2 등을 시도한다. 잎에서 시작해도 잎->0->다른 잎까지 세 명에서 막힌다.
6. 복잡도: 최대 차수를 D라고 할 때 깊이를5명으로 제한한 보수적 시간 상한은 O(n*(1+D+D^2+D^3+D^4))이다. 일반 연결 요소 탐색의 O(n+e)라고 하면 안 된다. 실제로는 단순 경로만 시도하고 하나를 찾는 즉시 종료한다. 그래프·방문 배열 공간 O(n+e), 재귀 깊이는 최대5다.

원본: [백준 13023 — ABCDE](https://www.acmicpc.net/problem/13023)

대조 출처: [공개 문제 기록 1](https://www.programmingstory.com/2024/02/13023-abcde.html), [공개 문제 기록 2](https://davincicoding.co.kr/156).

## 2. 가장 안전한 빈칸의 거리

[완성 Main2.java](answers/Main2.java)

```java
import java.util.*;

class Solution2 {
    public int solution(int[][] sea) {
        int n = sea.length;
        int m = sea[0].length;
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[][] dist = new int[n][m];
        Queue<int[]> queue = new LinkedList<>();

        for (int r = 0; r < n; r++) {
            Arrays.fill(dist[r], -1);
            for (int c = 0; c < m; c++) {
                if (sea[r][c] == 1) {
                    dist[r][c] = 0;
                    queue.offer(new int[]{r, c});
                }
            }
        }

        int answer = 0;
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];

            for (int d = 0; d < 8; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[r][c] + 1;
                    answer = Math.max(answer, dist[nr][nc]);
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return answer;
    }
}
```

1. 알고리즘: 모든 상어를 시작점으로 넣는 다중 시작점 BFS.
2. 이유: 이동마다 거리가1이며, 여러 상어 중 가장 가까운 상어까지의 거리가 필요하다. 상어에서 빈칸으로 거꾸로 확산해도 거리는 같다.
3. 실행 순서: dist를-1로 초기화 -> 모든 상어는 거리0으로 큐에 넣음 ->8방향으로 미방문 칸 확장 -> 설정한 거리의 최댓값 반환.
4. 핵심: 처음 방문한 거리가 가장 가까운 시작 상어까지의 거리다. dist가 방문 여부도 구분하므로 visited는 없고 큐에 거리도 중복 저장하지 않는다.
5. 예제 추적: 첫 예제 queue=[(0,0)], dist[0][0]=0. 꺼내면(0,1),(1,0),(1,1)이 거리1이 된다. 이 세 칸에서 나머지 칸에 도달하면 거리2다. 최종 dist={{0,1,2},{1,1,2},{2,2,2}}라 답은2다.
6. 복잡도: 각 칸을 한 번 큐에 넣고8방향만 확인하므로 시간·공간 모두 O(n*m).

원본: [백준 17086 — 아기 상어 2](https://www.acmicpc.net/problem/17086)

대조 출처: [공개 문제 기록 1](https://jjunsu.tistory.com/369), [공개 문제 기록 2](https://astrid-dm.tistory.com/422).

## 3. 가장 가까운 친구로부터 멀리 살기

[완성 Main3.java](answers/Main3.java)

```java
import java.util.*;

class Solution3 {
    public int solution(int n, int[] friends, int[][] roads) {
        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            graph[road[0]].add(new int[]{road[1], road[2]});
            graph[road[1]].add(new int[]{road[0], road[2]});
        }

        int[] distA = dijkstra(friends[0], n, graph);
        int[] distB = dijkstra(friends[1], n, graph);
        int[] distC = dijkstra(friends[2], n, graph);

        int answer = 1;
        int maxDistance = -1;
        for (int current = 1; current <= n; current++) {
            int distance = Math.min(distA[current],
                    Math.min(distB[current], distC[current]));
            if (distance > maxDistance) {
                maxDistance = distance;
                answer = current;
            }
        }
        return answer;
    }

    private int[] dijkstra(int start, int n, List<int[]>[] graph) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[1], b[1]));
        pq.offer(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currentNode = current[0];
            int currentCost = current[1];
            if (currentCost > dist[currentNode]) {
                continue;
            }

            for (int[] edge : graph[currentNode]) {
                int next = edge[0];
                int nextCost = currentCost + edge[1];
                if (nextCost < dist[next]) {
                    dist[next] = nextCost;
                    pq.offer(new int[]{next, nextCost});
                }
            }
        }
        return dist;
    }
}
```

1. 알고리즘: 친구 세 명의 집에서 각각 다익스트라를 수행한다.
2. 이유: 도로 길이가 다르고 양수이며, 양방향이므로 후보->친구와 친구->후보의 최단거리는 같다. 모든 후보에서 탐색하는 대신 세 번만 탐색한다.
3. 실행 순서: 양방향 그래프 -> 세 거리 배열 계산 -> 후보 번호 오름차순 확인 -> 세 거리의 최솟값이 현재 최댓값보다 큰 경우 갱신.
4. 핵심: Math.min은 한 후보에서 가장 가까운 친구를 고른다. 바깥의 최댓값 갱신은 그런 후보 중 가장 먼 땅을 고른다. 엄격한 >로 갱신하여 동률에서는 먼저 본 작은 번호를 유지한다. 최대 유효 최단거리는(100000-1)*10000으로 int 범위 안이다. 도달하지 못한 거리는 INF로 남아 유한 거리보다 크게 취급되며 더하기는 큐에서 꺼낸 유한 거리로만 한다.
5. 예제 추적: 후보4에서 세 거리는(2,2,2)라 평가2. 후보5는(5,5,5)라 평가5로 answer=5. 후보6도 평가5라 >조건이 거짓이고 answer=5를 유지한다. friends에 같은 번호가 있어도 세 번의 독립 탐색은 정확하다.
6. 복잡도: 정점 n, 도로 e에 대해 시간 O((n+e)*log(n+e)), 세 번이라는 상수는 생략한다. 인접 리스트·거리 배열·PQ 공간 O(n+e). 같은 친구가 있어도 별도 최적화 없이 정석을 유지한다.

원본: [백준 22865 — 가장 먼 곳](https://www.acmicpc.net/problem/22865)

대조 출처: [공개 문제 기록 1](https://ongveloper.tistory.com/379), [공개 문제 기록 2](https://littlesam95.tistory.com/entry/BOJGold-4-백준-22865-가장-먼-곳C).

## 4. 열쇠를 모아 문을 지나 탈출하기

[완성 Main4.java](answers/Main4.java)

```java
import java.util.*;

class Solution4 {
    public int solution(String[] maze) {
        int n = maze.length;
        int m = maze[0].length();
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        boolean[][][] visited = new boolean[n][m][64];
        Queue<int[]> queue = new LinkedList<>();

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (maze[r].charAt(c) == '0') {
                    // {행, 열, 보유 열쇠 상태, 이동 횟수}
                    queue.offer(new int[]{r, c, 0, 0});
                    visited[r][c][0] = true;
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];
            int keys = current[2];
            int count = current[3];

            if (maze[r].charAt(c) == '1') {
                return count;
            }

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && maze[nr].charAt(nc) != '#') {
                    char cell = maze[nr].charAt(nc);
                    int nextKeys = keys;
                    boolean canMove = true;

                    if (cell >= 'A' && cell <= 'F') {
                        int door = 1 << (cell - 'A');
                        canMove = (keys & door) != 0;
                    }

                    if (cell >= 'a' && cell <= 'f') {
                        nextKeys = keys | (1 << (cell - 'a'));
                    }

                    if (canMove && !visited[nr][nc][nextKeys]) {
                        visited[nr][nc][nextKeys] = true;
                        queue.offer(new int[]{nr, nc, nextKeys, count + 1});
                    }
                }
            }
        }
        return -1;
    }
}
```

1. 알고리즘: (행,열,보유 열쇠 집합) 상태를 구분하는 BFS.
2. 이유: 모든 이동은1회이며 열쇠를 얻고 이전 칸으로 돌아와야 할 수 있다. 위치만 방문 처리하면 필요한 재방문이 막힌다.
3. 실행 순서: 시작점에서 열쇠 없는 상태로 출발 -> 다음 칸 범위·벽 확인 -> 문 통과 가능 여부 확인 -> 열쇠 획득 후 상태 결정 -> 미방문 상태 큐 삽입 -> 첫 출구를 꺼내면 반환.
4. 핵심: 열쇠6종 각각을 a=1,b=2,c=4,d=8,e=16,f=32로 표시한다. 종류별 값이2배씩이라 집합을0~63으로 표현할 수 있다. 1 << 번호는 해당 종류의 값, keys | 값은 보유 표시 추가, keys & 값이0이 아니면 보유 중이라는 뜻이다. a와b를 가지면 keys=3이고 다시 a를 얻어도3이다. nextKeys를 따로 만들어 다른 방향이나 경로의 열쇠 상태를 변경하지 않는다. 큐에 횟수를 넣으므로 dist는 별도로 없다.
5. 예제 추적: {"a0A1"}에서 (열,keys,횟수)는 (1,0,0) -> 왼쪽a(0,1,1) -> 시작칸(1,1,2) -> A문(2,1,3) -> 출구(3,1,4). visited[0][1][0]은 이미 참이어도 visited[0][1][1]은 다른 상태라 다시 갈 수 있다. 문A 검사는 keys & 1로 한다.
6. 복잡도: 열쇠 집합은64개라 시간·공간 모두 O(n*m*64). 비트 연산은 열쇠 집합을 나타내는 수단이며 DP가 아니다.

원본: [백준 1194 — 달이 차오른다, 가자.](https://www.acmicpc.net/problem/1194)

대조 출처: [공개 문제 기록 1](https://loosie.tistory.com/343), [공개 문제 기록 2](https://ghkvud2.tistory.com/13).

## 5. 원형 숫자판을 돌리고 같은 수 지우기

[완성 Main5.java](answers/Main5.java)

```java
import java.util.*;

class Solution5 {
    int n;
    int m;
    int[][] map;

    public int solution(int[][] disks, int[][] commands) {
        n = disks.length;
        m = disks[0].length;
        map = new int[n][m];
        for (int r = 0; r < n; r++) {
            map[r] = disks[r].clone();
        }

        for (int[] command : commands) {
            rotate(command[0], command[1], command[2]);
            if (!removeSame()) {
                adjustAverage();
            }
        }

        int answer = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                answer += map[r][c];
            }
        }
        return answer;
    }

    private void rotate(int x, int direction, int count) {
        for (int number = x; number <= n; number += x) {
            int r = number - 1;
            int[] next = new int[m];
            for (int c = 0; c < m; c++) {
                int nextCol;
                if (direction == 0) {
                    nextCol = (c + count) % m;
                } else {
                    nextCol = (c - count + m) % m;
                }
                next[nextCol] = map[r][c];
            }
            map[r] = next;
        }
    }

    private boolean removeSame() {
        boolean[][] remove = new boolean[n][m];
        boolean found = false;

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (map[r][c] > 0) {
                    int right = (c + 1) % m;
                    if (map[r][c] == map[r][right]) {
                        remove[r][c] = true;
                        remove[r][right] = true;
                        found = true;
                    }
                    if (r + 1 < n && map[r][c] == map[r + 1][c]) {
                        remove[r][c] = true;
                        remove[r + 1][c] = true;
                        found = true;
                    }
                }
            }
        }

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (remove[r][c]) {
                    map[r][c] = 0;
                }
            }
        }
        return found;
    }

    private void adjustAverage() {
        int sum = 0;
        int count = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (map[r][c] > 0) {
                    sum += map[r][c];
                    count++;
                }
            }
        }

        if (count == 0) {
            return;
        }
        double average = (double) sum / count;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (map[r][c] > 0) {
                    if (map[r][c] > average) {
                        map[r][c]--;
                    } else if (map[r][c] < average) {
                        map[r][c]++;
                    }
                }
            }
        }
    }
}
```

1. 알고리즘: 회전 -> 인접한 같은 수 동시 삭제 -> 삭제가 없을 때만 평균 조정을 수행하는 구현·시뮬레이션. 골드2 수준의 구현 한 문제를 계속 포함해 달라는 요청을 반영한 항목이다.
2. 이유: 주어진 명령을 그대로 실행해야 한다. 같은 수 그룹을 BFS로 찾을 수도 있지만, 이 문제는 같은 값을 가진 이웃 쌍의 양 끝을 모두 표시해도 지워야 할 모든 칸을 찾는다. 따라서 더 단순한 배열 순회를 사용한다. 나머지 네 문제에서 DFS·BFS·다익스트라를 연습한다.
3. 실행 순서: 원본 복사 -> 명령별 대상 원판 회전 -> 삭제 표시를 모두 모아 적용 -> 지운 것이 전혀 없으면 고정된 평균으로 조정 -> 다음 명령 -> 합 반환.
4. 핵심: 행 인덱스가 아닌 원판 번호가 x의 배수인지 본다. 시계 회전은 기존 c가(c+k)%m으로, 반시계는(c-k+m)%m으로 간다. 각 이웃 쌍은 오른쪽과 바깥 원판 쪽만 보아도 모두 확인된다. 가로는 나머지 연산으로 이어지지만 세로는 끝과 끝이 이어지지 않는다. 먼저 remove에 표시하므로 앞에서 지운 값이 뒤 비교에 영향을 주지 않는다. 평균은 남은 칸만 세고, double로 계산해 정수 나눗셈을 피한다.
5. 예제 추적: {{1,2,3},{10,20,30}}에서 명령{2,0,1}이면2번 원판만{30,10,20}으로 바뀐다. 같은 이웃 쌍이 없어서 remove는 모두false다. sum=66,count=6,average=11. 1,2,3,10에는1을 더하고20,30에는1을 빼서{{2,3,4},{29,11,19}}, 합68이다. 모두1인 예제는 remove 전체가true가 되어0으로 지워지며 평균 조정을 하지 않는다.
6. 복잡도: 원판 수 n, 각 원판 길이 m, 명령 수 T에 대해 시간 O(T*n*m), 공간 O(n*m). 별도 Node나 Deque 없이 배열과 기본 반복문만 사용한다.

원본: [백준 17822 — 원판 돌리기](https://www.acmicpc.net/problem/17822)

대조 출처: [공개 문제 기록 1](https://dheldh77.tistory.com/entry/백준-17822번-원판-돌리기), [공개 문제 기록 2](https://suhyeokeee.tistory.com/187), [공개 문제 기록 3](https://abbiddo.tistory.com/174).


