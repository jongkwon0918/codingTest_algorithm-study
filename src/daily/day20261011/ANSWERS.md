# 정답 및 해설 — 2026-10-11

각 문제의 정답 코드를 먼저 제공하고, 알고리즘 선택부터 예제 추적·복잡도 순으로 설명합니다.

## 1. 밭을 돌보는 지렁이

[완성된 실행 파일](answers/Main1.java)

```java
import java.util.*;

class Solution1 {
    int[][] field;
    boolean[][] visited;
    int n;
    int m;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(int[][] field) {
        this.field = field;
        n = field.length;
        m = field[0].length;
        visited = new boolean[n][m];
        int count = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if (field[row][col] == 1 && !visited[row][col]) {
                    dfs(row, col);
                    count++;
                }
            }
        }
        return count;
    }

    private void dfs(int row, int col) {
        visited[row][col] = true;

        for (int d = 0; d < 4; d++) {
            int nr = row + dr[d];
            int nc = col + dc[d];
            if (nr >= 0 && nr < n && nc >= 0 && nc < m
                    && field[nr][nc] == 1 && !visited[nr][nc]) {
                dfs(nr, nc);
            }
        }
    }
}
```

1. **어떤 알고리즘인가:** 재귀 DFS.
2. **왜 사용하는가:** 연결된 배추를 모두 방문해 하나의 묶음으로 세면 된다. 거리 정보는 필요 없다.
3. **실행 순서:** 방문 배열 생성 → 전체 칸 확인 → 미방문 배추에서 DFS → 한 묶음 탐색을 마칠 때 count 증가 → 반환.
4. **핵심 코드:** DFS 시작에서 방문 처리한다. 범위, 배추 존재, 미방문 조건을 모두 만족하는 칸만 호출한다. count는 재귀 호출마다 증가시키지 않고 새로운 묶음을 시작할 때 한 번 증가시킨다.
5. **예제 대입:** dfs(0,0) → visited[0][0]=true → 오른쪽 dfs(0,1) → 아래 dfs(1,1) → 탐색 종료, count=1. 다음 미방문 배추 (1,3)에서 (2,3)까지 방문해 count=2. (2,0)은 단독 묶음이므로 count=3. 대각선인 (1,1)과 (2,0)은 연결되지 않는다. 다음 solution 호출에서는 방문 배열을 새로 만든다.
6. **시간복잡도:** 시간 O(N*M), 공간 O(N*M). 재귀 깊이는 최악에 배추 수만큼이다. 여기서는 최대 2,500칸이며, 더 큰 격자의 연결 영역을 다룰 때에는 재귀 깊이를 별도로 고려한다.

원본: **백준 1012 — 유기농 배추**

[원본 문제](https://www.acmicpc.net/problem/1012) · [문제 조건 대조 기록](https://terry1213.com/backjoon/1012/)

## 2. 가장 멀리 숨을 곳

[완성된 실행 파일](answers/Main2.java)

```java
import java.util.*;

class Solution2 {
    public int[] solution(int n, int[][] roads) {
        List<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            graph[road[0]].add(road[1]);
            graph[road[1]].add(road[0]);
        }

        int[] dist = new int[n + 1];
        Arrays.fill(dist, -1);
        Queue<Integer> queue = new LinkedList<>();
        dist[1] = 0;
        queue.offer(1);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int next : graph[current]) {
                if (dist[next] == -1) {
                    dist[next] = dist[current] + 1;
                    queue.offer(next);
                }
            }
        }

        int answer = 0;
        int maxDistance = -1;
        int count = 0;
        for (int i = 2; i <= n; i++) {
            if (dist[i] > maxDistance) {
                answer = i;
                maxDistance = dist[i];
                count = 1;
            } else if (dist[i] == maxDistance) {
                count++;
            }
        }
        return new int[]{answer, maxDistance, count};
    }
}
```

1. **어떤 알고리즘인가:** BFS.
2. **왜 사용하는가:** 모든 길의 거리가 1이므로 첫 방문 거리가 최단거리다. 그 최단거리 중 최댓값을 찾아야 한다.
3. **실행 순서:** 양방향 그래프 → dist=-1 초기화 → 1번부터 BFS → 번호 순으로 거리를 비교 → 번호·거리·개수 반환.
4. **핵심 코드:** dist=-1이면 미방문이므로 별도 visited가 필요 없다. 큐에는 정점만 넣는다. 번호를 작은 순서로 확인하고 같은 거리에서는 answer를 바꾸지 않아 가장 작은 번호를 유지한다. 더 먼 거리를 발견하면 count를 1로 다시 설정한다.
5. **예제 대입:** queue=[1], dist[1]=0 → 1을 꺼내 3과 2 방문, queue=[3,2], 두 거리=1 → 3에서 6과 4 방문, 거리=2 → 2에서 5 방문, 거리=2. 최종 dist[1..6]=[0,1,1,2,2,2]. 번호 4에서 answer=4,maxDistance=2,count=1 → 5에서 count=2 → 6에서 count=3. 결과는 [4,2,3].
6. **시간복잡도:** 시간 O(V+E), 공간 O(V+E).

원본: **백준 6118 — 숨바꼭질**

[원본 문제](https://www.acmicpc.net/problem/6118) · [문제 조건 대조 기록](https://hoho325.tistory.com/155)

## 3. 감시를 피해 목적지까지

[완성된 실행 파일](answers/Main3.java)

```java
import java.util.*;

class Solution3 {
    public long solution(int[] visible, int[][] roads) {
        int n = visible.length;
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            graph[road[0]].add(new int[]{road[1], road[2]});
            graph[road[1]].add(new int[]{road[0], road[2]});
        }

        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>(
                (a, b) -> Long.compare(a[1], b[1]));
        pq.offer(new long[]{0, 0});

        while (!pq.isEmpty()) {
            long[] current = pq.poll();
            int currentNode = (int) current[0];
            long currentDistance = current[1];
            if (currentDistance > dist[currentNode]) {
                continue;
            }

            for (int[] next : graph[currentNode]) {
                int nextNode = next[0];
                if (visible[nextNode] == 0 || nextNode == n - 1) {
                    long nextDistance = currentDistance + next[1];
                    if (nextDistance < dist[nextNode]) {
                        dist[nextNode] = nextDistance;
                        pq.offer(new long[]{nextNode, nextDistance});
                    }
                }
            }
        }
        return dist[n - 1] == Long.MAX_VALUE ? -1 : dist[n - 1];
    }
}
```

1. **어떤 알고리즘인가:** PriorityQueue를 이용한 다익스트라.
2. **왜 사용하는가:** 도로별 양수 비용이 다르다. 감시 조건으로 다음 정점의 허용 여부를 판단하면서 최소 비용을 갱신한다.
3. **실행 순서:** 그래프 생성 → dist 초기화와 시작점 0 설정 → 작은 거리부터 꺼내기 → 허용된 이웃의 거리 갱신 → 도착지 거리 또는 -1 반환.
4. **핵심 코드:** 다음 분기점이 안전하거나 목적지일 때만 처리한다. 별도 visited 없이 dist를 사용하고, 꺼낸 거리가 현재 dist보다 크면 오래된 항목이므로 continue한다. 최대 단순 경로 비용은 99,999*100,000=9,999,900,000이므로 long이 필요하다. 큐에서도 long[]과 Long.compare를 사용한다. Long.MAX_VALUE인 정점은 큐에 넣지 않으므로 그 값에 비용을 더하지 않는다.
5. **예제 대입:** dist=[0,INF,INF,INF,INF], pq=[{0,0}] → 0의 이웃 1은 금지, dist[2]=4,dist[4]=20 → {2,4}를 꺼내 dist[3]=7 → {3,7}을 꺼내 목적지 4는 visible=1이어도 허용, dist[4]=9 → {4,9} 처리 → 나중에 {4,20}은 20>dist[4]라 무시. 결과 9.
6. **시간복잡도:** 시간 O((V+E) log V), 공간 O(V+E).

원본: **백준 17396 — 백도어**

[원본 문제](https://www.acmicpc.net/problem/17396) · [문제 조건 대조 기록](https://geumba.tistory.com/67)

## 4. 제한된 점프로 건너가기

[완성된 실행 파일](answers/Main4.java)

```java
import java.util.*;

class Solution4 {
    public int solution(int[][] map, int k) {
        int n = map.length;
        int m = map[0].length;
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        int[] jumpRow = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] jumpCol = {-1, 1, -2, 2, -2, 2, -1, 1};

        boolean[][][] visited = new boolean[n][m][k + 1];
        Queue<int[]> queue = new LinkedList<>();
        // {행, 열, 사용한 점프 수, 동작 수}
        queue.offer(new int[]{0, 0, 0, 0});
        visited[0][0][0] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            int used = current[2];
            int distance = current[3];

            if (row == n - 1 && col == m - 1) {
                return distance;
            }

            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && map[nr][nc] == 0 && !visited[nr][nc][used]) {
                    visited[nr][nc][used] = true;
                    queue.offer(new int[]{nr, nc, used, distance + 1});
                }
            }

            if (used < k) {
                for (int d = 0; d < 8; d++) {
                    int nr = row + jumpRow[d];
                    int nc = col + jumpCol[d];
                    if (nr >= 0 && nr < n && nc >= 0 && nc < m
                            && map[nr][nc] == 0 && !visited[nr][nc][used + 1]) {
                        visited[nr][nc][used + 1] = true;
                        queue.offer(new int[]{nr, nc, used + 1, distance + 1});
                    }
                }
            }
        }
        return -1;
    }
}
```

1. **어떤 알고리즘인가:** 상태를 포함한 BFS.
2. **왜 사용하는가:** 모든 동작의 비용이 1이다. 같은 칸이라도 점프를 몇 번 썼는지에 따라 이후 갈 수 있는 곳이 달라진다.
3. **실행 순서:** (0,0,사용0) 방문 → 큐에서 상태 꺼내기 → 도착 확인 → 일반 이동 4개 → 남은 횟수가 있으면 점프 8개 → 큐가 비면 -1.
4. **핵심 코드:** visited[row][col][used]로 상태별 방문을 관리한다. 거리는 큐에만 저장하며 dist 배열에 중복 저장하지 않는다. 일반 이동은 used 유지, 점프는 used+1이다. 이동 전에 배열 범위, 평지, 상태 미방문을 하나의 if에서 검사한다. 첫 도착 상태의 거리가 모든 사용 횟수를 통틀어 최단거리이다.
5. **예제 대입:** 장애물 예제, k=1에서 queue=[{0,0,0,0}], visited[0][0][0]=true → 일반 이동은 막힘 → 점프로 (1,2)와 (2,1)에 착지해 queue=[{1,2,1,1},{2,1,1,1}] → (1,2)에서 아래 (2,2)에 일반 이동하여 {2,2,1,2} 삽입 → 도착 상태를 꺼내면 2 반환. k=0이면 첫 칸에서 다음 상태를 넣지 못해 -1이다. 빈 격자에서는 같은 칸을 사용0과 사용1로 각각 방문할 수 있다.
6. **시간복잡도:** 상태 수가 N*M*(K+1), 상태당 이동 후보는 최대 12개이므로 시간·공간 O(N*M*(K+1)).

원본: **백준 1600 — 말이 되고픈 원숭이**

[원본 문제](https://www.acmicpc.net/problem/1600) · [문제 조건 대조 기록](https://imucoding.tistory.com/699)

## 5. 모두 녹기 직전

[완성된 실행 파일](answers/Main5.java)

```java
import java.util.*;

class Solution5 {
    int[][] map;
    int n;
    int m;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int[] solution(int[][] map) {
        this.map = map;
        n = map.length;
        m = map[0].length;
        int count = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if (map[row][col] == 1) {
                    count++;
                }
            }
        }

        int hours = 0;
        int lastCount = 0;
        while (count > 0) {
            lastCount = count;
            List<int[]> melting = bfs();
            for (int[] cheese : melting) {
                map[cheese[0]][cheese[1]] = 0;
            }
            count -= melting.size();
            hours++;
        }
        return new int[]{hours, lastCount};
    }

    private List<int[]> bfs() {
        boolean[][] visited = new boolean[n][m];
        Queue<int[]> queue = new LinkedList<>();
        List<int[]> melting = new ArrayList<>();
        queue.offer(new int[]{0, 0});
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];

            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    if (map[nr][nc] == 0) {
                        queue.offer(new int[]{nr, nc});
                    } else {
                        melting.add(new int[]{nr, nc});
                    }
                }
            }
        }
        return melting;
    }
}
```

1. **어떤 알고리즘인가:** 외부 공기를 찾는 BFS와 한 시간씩 진행하는 시뮬레이션.
2. **왜 사용하는가:** 단순히 0인 칸과 닿았다고 녹으면 내부 구멍까지 잘못 공기로 취급한다. 바깥에서 도달할 수 있는 빈칸을 탐색해야 한다.
3. **실행 순서:** 전체 치즈 수 계산 → 이번 시간 시작의 count 저장 → 외부 공기 BFS와 녹을 좌표 수집 → 수집된 좌표를 모두 0으로 변경 → 수와 시간 갱신 → 치즈가 없으면 결과 반환.
4. **핵심 코드:** 가장자리가 모두 0이므로 (0,0)에서 시작하면 전체 외부 공기를 찾을 수 있다. 빈칸은 큐에 넣고, 공기와 접촉한 치즈는 melting에만 넣는다. 둘 다 방문 처리하여 여러 면이 공기와 닿아도 한 번만 추가한다. 탐색 중 치즈를 녹여 큐에 넣으면 같은 시간에 더 깊은 치즈까지 녹일 수 있으므로, 변경은 BFS 종료 후에 한다. visited는 매시간 새로 만든다. count>0인 반복을 시작할 때 lastCount를 저장하면 마지막 시간의 시작 개수가 남는다. 입력 map을 직접 변경하므로 같은 원래 지도를 다시 시험하려면 새 배열을 전달한다.
5. **예제 대입:** 3*3 치즈 덩어리 예제에서 count=9,hours=0 → lastCount=9 → queue=[{0,0}]에서 바깥 0들을 탐색 → melting에 외곽 치즈 8개 저장, 가운데 (2,2)는 도달하지 못함 → 8칸을 동시에 0으로 변경, count=1,hours=1 → visited 새로 생성, lastCount=1 → 바깥 공기가 새로 녹은 칸까지 들어가 가운데 1칸을 melting에 저장 → count=0,hours=2 → [2,1] 반환.
6. **시간복잡도:** 다 녹는 시간을 T라 하면 시간 O((T+1)*N*M), 공간 O(N*M).

원본: **백준 2636 — 치즈**

[원본 문제](https://www.acmicpc.net/problem/2636) · [문제 조건 대조 기록](https://steady-coding.tistory.com/173)

## 원본 확인과 형식 변환

백준 직접 조회에서는 정상 문제 본문을 받지 못했습니다. 실제 문제 존재 여부는 공개 백준 문제 목록과 문제별 기록을 교차 확인했습니다. [BaekjoonHub 목록](https://github.com/chech2/Algorithm), [별도 백준 풀이 목록](https://github.com/jiy12345/Beakjoon-Online-Judge) 및 각 문제의 조건 대조 링크를 사용했습니다. 원문 직접 대조를 완료했다고 기록하지 않습니다.

1번은 원본에서 배추가 있는 (x,y) 좌표로 주는 밭을 field[y][x]=1인 배열로 전달합니다. 여러 테스트 케이스는 여러 solution 호출로 바꿨습니다. 나머지 문제도 매개변수·반환값만 바꾸었으며 이동·감시·동률·동시 변경 조건은 유지했습니다.

## 학습 포인트

최근 시뮬레이션 풀이에서 이동 여부를 반환받고도 moved에 반영하지 않는 실수가 있었습니다. 5번에서는 시간별 변경 대상 수집, 반영, 남은 개수와 시간 갱신을 각각 확인하세요. 한 번의 실수를 자주 틀리는 유형으로 단정하지 않습니다.

