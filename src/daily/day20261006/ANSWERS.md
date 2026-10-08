# 정답 및 해설 영역

문제 풀이를 마친 뒤 확인하세요. 코드는 독립적으로 작성한 풀이입니다.

## 1. 연결 요소의 개수

### 완성된 Solution 코드

```java
import java.util.*;

class Solution1 {
    public int solution(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        boolean[] visited = new boolean[n + 1];
        int count = 0;
        for (int vertex = 1; vertex <= n; vertex++) {
            if (!visited[vertex]) {
                dfs(vertex, graph, visited);
                count++;
            }
        }
        return count;
    }

    private void dfs(int vertex, List<List<Integer>> graph, boolean[] visited) {
        visited[vertex] = true;
        for (int next : graph.get(vertex)) {
            if (!visited[next]) {
                dfs(next, graph, visited);
            }
        }
    }
}
```

### 핵심 풀이 과정

미방문 정점에서 DFS를 시작할 때마다 연결 요소를 하나 늘린다. 인접 리스트는 양방향으로 만든다. 모든 정점을 검사하므로 고립된 정점도 빠뜨리지 않는다.

### 시간복잡도

O(n + m), m은 간선 수. 공간복잡도는 O(n + m)입니다.

### 원본 문제

백준 11724번 **연결 요소의 개수** — [원본 링크](https://www.acmicpc.net/problem/11724)

[실행 가능한 정답 Main](answers/Main1.java)

## 2. 그림

### 완성된 Solution 코드

```java
import java.util.*;

class Solution2 {
    public int[] solution(int[][] paper) {
        int n = paper.length;
        int m = paper[0].length;
        boolean[][] visited = new boolean[n][m];
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        int count = 0;
        int maxArea = 0;

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (paper[r][c] == 0 || visited[r][c]) {
                    continue;
                }
                count++;
                int area = 0;
                Deque<int[]> stack = new ArrayDeque<>();
                stack.push(new int[]{r, c});
                visited[r][c] = true;

                while (!stack.isEmpty()) {
                    int[] current = stack.pop();
                    area++;
                    for (int d = 0; d < 4; d++) {
                        int nr = current[0] + dr[d];
                        int nc = current[1] + dc[d];
                        if (nr < 0 || nr >= n || nc < 0 || nc >= m) {
                            continue;
                        }
                        if (paper[nr][nc] == 1 && !visited[nr][nc]) {
                            visited[nr][nc] = true;
                            stack.push(new int[]{nr, nc});
                        }
                    }
                }
                maxArea = Math.max(maxArea, area);
            }
        }
        return new int[]{count, maxArea};
    }
}
```

### 핵심 풀이 과정

미방문 1에서 스택을 사용하는 DFS로 그림 하나의 넓이를 센다. 스택에 넣을 때 방문 처리하고, 그림 수와 최대 넓이를 따로 갱신한다. 최대 25만 칸을 다루므로 재귀 깊이 문제를 피하는 반복형 탐색을 사용했다.

### 시간복잡도

O(n * m). 공간복잡도는 O(n * m)입니다.

### 원본 문제

백준 1926번 **그림** — [원본 링크](https://www.acmicpc.net/problem/1926)

[실행 가능한 정답 Main](answers/Main2.java)

## 3. 지름길

### 완성된 Solution 코드

```java
import java.util.*;

class Solution3 {
    public int solution(int d, int[][] shortcuts) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i <= d; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < d; i++) {
            graph.get(i).add(new int[]{i + 1, 1});
        }
        for (int[] shortcut : shortcuts) {
            if (shortcut[1] <= d) {
                graph.get(shortcut[0]).add(new int[]{shortcut[1], shortcut[2]});
            }
        }

        int[] dist = new int[d + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;
        // {누적 거리, 현재 위치}
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[0], b[0])
        );
        pq.offer(new int[]{0, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int cost = current[0];
            int position = current[1];
            if (cost != dist[position]) {
                continue;
            }
            if (position == d) {
                return cost;
            }
            for (int[] next : graph.get(position)) {
                int nextCost = cost + next[1];
                if (nextCost < dist[next[0]]) {
                    dist[next[0]] = nextCost;
                    pq.offer(new int[]{nextCost, next[0]});
                }
            }
        }
        return dist[d];
    }
}
```

### 핵심 풀이 과정

0부터 D까지를 정점으로 보고 일반 도로 i→i+1과 지름길을 방향 간선으로 만든다. D를 넘어가는 지름길은 제외한다. 다익스트라로 최소 비용을 구하면 길지만 불리한 지름길도 자연스럽게 선택하지 않는다. 이 문제는 앞쪽으로만 이동하므로 순서대로 거리 배열을 갱신하는 DP로도 풀 수 있다.

### 시간복잡도

O((D + S) log(D + S)), S는 지름길 수. 공간복잡도는 O(D + S)입니다.

### 원본 문제

백준 1446번 **지름길** — [원본 링크](https://www.acmicpc.net/problem/1446)

[실행 가능한 정답 Main](answers/Main3.java)

## 4. 숨바꼭질 2

### 완성된 Solution 코드

```java
import java.util.*;

class Solution4 {
    public int[] solution(int n, int k) {
        int limit = 100000;
        int[] dist = new int[limit + 1];
        int[] ways = new int[limit + 1];
        Arrays.fill(dist, -1);
        dist[n] = 0;
        ways[n] = 1;

        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(n);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (dist[k] != -1 && dist[current] >= dist[k]) {
                continue;
            }
            int[] nextPositions = {current - 1, current + 1, current * 2};
            for (int next : nextPositions) {
                if (next < 0 || next > limit) {
                    continue;
                }
                int nextTime = dist[current] + 1;
                if (dist[next] == -1) {
                    dist[next] = nextTime;
                    ways[next] = ways[current];
                    queue.offer(next);
                } else if (dist[next] == nextTime) {
                    ways[next] += ways[current];
                }
            }
        }
        return new int[]{dist[k], ways[k]};
    }
}
```

### 핵심 풀이 과정

BFS 거리 배열과 방법 수 배열을 함께 관리한다. 처음 도착하면 거리와 방법 수를 기록하고, 같은 최단거리로 다시 도착하면 방법 수만 더한다. 같은 결과를 내는 서로 다른 연산도 각각 계산한다. 목표를 처음 발견한 즉시 종료하면 같은 거리의 다른 방법을 누락할 수 있다.

### 시간복잡도

O(L), L=100,001인 탐색 위치 수. 공간복잡도는 O(L)입니다.

### 원본 문제

백준 12851번 **숨바꼭질 2** — [원본 링크](https://www.acmicpc.net/problem/12851)

[실행 가능한 정답 Main](answers/Main4.java)

## 5. 아기 상어

### 완성된 Solution 코드

```java
import java.util.*;

class Solution5 {
    public int solution(int[][] sea) {
        int n = sea.length;
        int[][] map = new int[n][n];
        int r = 0;
        int c = 0;
        for (int i = 0; i < n; i++) {
            map[i] = sea[i].clone();
            for (int j = 0; j < n; j++) {
                if (map[i][j] == 9) {
                    r = i;
                    c = j;
                    map[i][j] = 0;
                }
            }
        }

        int size = 2;
        int eaten = 0;
        int time = 0;
        while (true) {
            int[] fish = findFish(map, r, c, size);
            if (fish == null) {
                return time;
            }
            r = fish[0];
            c = fish[1];
            time += fish[2];
            map[r][c] = 0;
            eaten++;
            if (eaten == size) {
                size++;
                eaten = 0;
            }
        }
    }

    private int[] findFish(int[][] map, int sr, int sc, int size) {
        int n = map.length;
        int[][] dist = new int[n][n];
        for (int[] row : dist) {
            Arrays.fill(row, -1);
        }
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{sr, sc});
        dist[sr][sc] = 0;
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        // 현재 크기로 통과 가능한 칸들의 거리를 구한다.
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            for (int d = 0; d < 4; d++) {
                int nr = current[0] + dr[d];
                int nc = current[1] + dc[d];
                if (nr < 0 || nr >= n || nc < 0 || nc >= n) {
                    continue;
                }
                if (dist[nr][nc] == -1 && map[nr][nc] <= size) {
                    dist[nr][nc] = dist[current[0]][current[1]] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }

        List<int[]> fishes = new ArrayList<>();
        // 도달할 수 있고 먹을 수 있는 물고기만 모은다.
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (map[r][c] > 0 && map[r][c] < size && dist[r][c] != -1) {
                    fishes.add(new int[]{r, c, dist[r][c]});
                }
            }
        }
        if (fishes.isEmpty()) {
            return null;
        }

        // {행, 열, 거리}: 거리 → 위쪽 → 왼쪽 순으로 정렬한다.
        fishes.sort((a, b) -> {
            if (a[2] != b[2]) {
                return Integer.compare(a[2], b[2]);
            }
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        });
        return fishes.get(0);
    }
}
```

### 핵심 풀이 과정

먹이를 하나 먹을 때마다 현재 크기로 이동 가능한 칸의 BFS 거리를 다시 구한다. 도달해서 먹을 수 있는 물고기를 {행, 열, 거리}로 모아 fishes.sort로 거리→행→열 순으로 정렬한다. fishes.get(0)이 이번에 먹을 물고기다. 이후 위치, 누적 시간, 먹은 마릿수, 크기를 갱신한다. 시작 위치의 9는 0으로 지워야 성장한 상어가 9를 물고기로 오인하지 않는다.

예를 들어 후보가 {(1,0,1), (0,1,1), (2,2,3)}이면 정렬 결과는 {(0,1,1), (1,0,1), (2,2,3)}이다. 거리 1인 두 후보 중 행이 작은 (0,1)을 선택한다. 행도 같으면 열이 작은 후보가 먼저 온다. 거리와 먹을 수 있는 후보는 매번 달라지므로 먹이 선택 때마다 다시 정렬한다. BFS에는 LinkedList를 사용하고, dist로 방문 여부를 판단하므로 별도 visited는 만들지 않는다.

### 시간복잡도

O(N^4 log N): 물고기는 최대 N²-1마리이고, 매 선택에서 BFS는 O(N²), 후보 정렬은 최대 O(N² log N)이다. 공간복잡도는 O(N²)입니다.

### 원본 문제

백준 16236번 **아기 상어** — [원본 링크](https://www.acmicpc.net/problem/16236)

[실행 가능한 정답 Main](answers/Main5.java)

## 출처 확인 기록

백준 원본 페이지는 조사 당시 직접 조회가 실패하거나 정상 문제 내용을 반환하지 않았습니다.
아래 공개된 문제 기록으로 실제 백준 번호·제목을 교차 확인했습니다. 풀이 알고리즘과 코드는 직접 구성했고, 위 예시는 원본 조건에 맞춘 함수 호출 예시입니다.

- 연결 요소의 개수: [공개 풀이 저장소](https://github.com/hogumachu/Coding-Test)
- 그림: [공개 풀이 저장소](https://github.com/Dezeli/BOJ)
- 지름길: [공개 풀이 저장소](https://github.com/devgeon/Problem-Solving)
- 숨바꼭질 2: [공개 풀이 저장소](https://github.com/dev-loggi/algorithm-kotlin), [공개 풀이 저장소](https://github.com/hogumachu/Coding-Test)
- 아기 상어: [공개 풀이 저장소](https://github.com/subinium/BOJ-Samsung-Implementation), [공개 풀이 저장소](https://github.com/devgeon/Problem-Solving)

문제 설명은 원문의 조건을 함수 매개변수·반환값 형식으로 정리했습니다. 원본 페이지를 직접 대조한 것으로 기록하지 않습니다.
