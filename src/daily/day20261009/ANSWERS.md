# 정답 및 해설 — 2026-10-09

문제 풀이 파일과 분리했습니다. 정답 코드를 먼저, 설명을 뒤에 제공합니다.

## 1. 감염되는 컴퓨터

[완성된 실행 파일](answers/Main1.java)

```java
import java.util.*;

class Solution1 {
    List<Integer>[] graph;
    boolean[] visited;
    int count;

    public int solution(int n, int[][] edges) {
        graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }
        visited = new boolean[n + 1];
        count = 0;
        dfs(1);
        return count;
    }

    private void dfs(int current) {
        visited[current] = true;
        for (int next : graph[current]) {
            if (!visited[next]) {
                count++;
                dfs(next);
            }
        }
    }
}
```

1. **알고리즘:** 재귀 DFS.
2. **선택 이유:** 최단거리는 필요 없고, 1번에서 연결을 따라 도달할 수 있는 정점만 세면 된다.
3. **실행 순서:** 양방향 그래프 작성 → 방문 배열과 count 초기화 → dfs(1) → 추가 방문 수 반환.
4. **핵심 코드:** 방문 전에 count를 증가시킨다. 시작점 1은 직접 dfs로 들어가므로 세지 않는다. 방문한 정점은 다시 호출하지 않아 순환에도 안전하다.
5. **예제 추적:** dfs(1) → visited[1]=true → next=2, count=1 → dfs(2) → next=3, count=2 → dfs(3) 종료 → 2에서 next=5, count=3 → dfs(5) → next=6, count=4 → 종료. 1로 돌아왔을 때 5는 이미 방문했다. 4·7은 방문하지 않는다.
6. **복잡도:** 정점 V, 간선 E에 대해 시간 O(V+E), 공간 O(V+E). 재귀 깊이는 최대 V이며 여기서는 100 이하이다.

원본: **백준 2606 — 바이러스**

[원본 문제](https://www.acmicpc.net/problem/2606)

## 2. 정확한 거리의 도시

[완성된 실행 파일](answers/Main2.java)

```java
import java.util.*;

class Solution2 {
    public int[] solution(int n, int[][] roads, int k, int x) {
        List<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            graph[road[0]].add(road[1]);
        }

        boolean[] visited = new boolean[n + 1];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x, 0});
        visited[x] = true;
        List<Integer> answer = new ArrayList<>();

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int city = current[0];
            int distance = current[1];

            if (distance == k) {
                answer.add(city);
            }
            for (int next : graph[city]) {
                if (!visited[next]) {
                    visited[next] = true;
                    queue.offer(new int[]{next, distance + 1});
                }
            }
        }

        Collections.sort(answer);
        if (answer.isEmpty()) {
            return new int[]{-1};
        }
        int[] result = new int[answer.size()];
        for (int i = 0; i < answer.size(); i++) {
            result[i] = answer.get(i);
        }
        return result;
    }
}
```

1. **알고리즘:** BFS.
2. **선택 이유:** 모든 도로의 거리가 1이므로 처음 방문할 때의 거리가 최단거리이다.
3. **실행 순서:** 단방향 인접 리스트 → 시작 도시 방문 처리와 큐 삽입 → 큐 순서대로 탐색 → 거리 k인 도시 수집 → 정렬과 배열 변환.
4. **핵심 코드:** 큐에 {도시, 거리}를 저장하고 visited로 재방문을 막는다. 거리를 별도 dist에도 중복 저장할 필요가 없다. 도로는 반대 방향으로 추가하지 않는다.
5. **예제 추적:** k=2일 때 queue=[{1,0}] → 1을 꺼내 [{2,1},{3,1}] → 2를 꺼내면 3은 이미 방문, 4를 넣어 [{3,1},{4,2}] → 3을 꺼낸 뒤 [{4,2}] → 4에서 answer=[4]. 도시 3은 2를 거쳐 다시 방문하지 않으므로 제외된다.
6. **복잡도:** V개 도시, E개 도로, 정답 A개에 대해 시간 O(V+E+A log A), 공간 O(V+E).

원본: **백준 18352 — 특정 거리의 도시 찾기**

[원본 문제](https://www.acmicpc.net/problem/18352)

## 3. 중복 없이 이동하기

[완성된 실행 파일](answers/Main3.java)

```java
import java.util.*;

class Solution3 {
    boolean[] visited;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};
    int n;
    int m;
    char[][] tempBoard;
    int answer;

    public int solution(String[] board) {
        // 같은 Solution 객체로 다시 호출해도 이전 상태가 남지 않도록 초기화한다.
        visited = new boolean[26];
        answer = 0;

        n = board.length;
        m = board[0].length();
        tempBoard = new char[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                tempBoard[i][j] = board[i].charAt(j);
            }
        }

        visited[tempBoard[0][0] - 'A'] = true;
        dfs(0, 0, 1);
        return answer;
    }

    private void dfs(int r, int c, int count) {
        answer = Math.max(answer, count);
        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];

            if (nr >= 0 && nr < n && nc >= 0 && nc < m
                    && !visited[tempBoard[nr][nc] - 'A']) {
                int alphabet = tempBoard[nr][nc] - 'A';
                visited[alphabet] = true;
                dfs(nr, nc, count + 1);
                visited[alphabet] = false;
            }
        }
    }
}
```

1. **알고리즘:** 재귀 DFS와 백트래킹.
2. **선택 이유:** 가능한 경로를 모두 시도해야 한다. 한 경로에서 사용한 알파벳을 다른 경로에서도 금지하면 정답 경로를 놓친다.
3. **실행 순서:** 필드 초기화 → 보드 변환 → 시작 알파벳 방문 → 이동 가능하면 표시·재귀·해제 → 최대 길이 반환.
4. **핵심 코드:** visited는 칸의 방문 여부가 아니라 현재 경로의 알파벳 사용 여부이다. 재귀 후 false로 되돌려 다음 경로를 시도한다. 범위 검사를 먼저 하므로 배열 접근이 안전하다.
5. **예제 추적:** dfs(0,0,1), C 표시 → 아래 A 표시, dfs(1,0,2) → 오른쪽 D 표시, dfs(1,1,3) → 위 A와 오른쪽 C는 이미 사용해 이동 불가 → D 해제 → A 해제 → 시작점에서 오른쪽 A로 다른 경로 탐색. 최대 길이는 3이다. 같은 객체로 다음 AAAA 보드를 호출하면 visited를 새로 만들고 answer=0으로 되돌린다. 시작 A만 가능하므로 1, 그다음 ABC는 3이다.
6. **복잡도:** R행 C열, 최대 경로 길이 L=min(26,R*C)에 대해 시간의 느슨한 상한 O(R*C+4^L), 공간 O(R*C+L). 실제 탐색량은 알파벳과 경계로 줄어든다.

**수정한 오류:** 이전 코드는 같은 객체로 위 예제를 순서대로 호출하면 두 번째 결과가 1 대신 3이었다. answer가 이전 최댓값을 유지했고 시작 알파벳도 visited에 남았다. solution 시작의 두 초기화로 해결했다.

원본: **백준 1987 — 알파벳**

[원본 문제](https://www.acmicpc.net/problem/1987)

## 4. 친구를 데리고 갈 수 있을까

[완성된 실행 파일](answers/Main4.java)

```java
import java.util.*;

class Solution4 {
    public String solution(int n, int[][] roads, int p) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] road : roads) {
            int u = road[0];
            int v = road[1];
            int weight = road[2];
            graph.get(u).add(new int[]{v, weight});
            graph.get(v).add(new int[]{u, weight});
        }

        int[] distFrom1 = dijkstra(1, n, graph);
        int[] distFromP = dijkstra(p, n, graph);
        int shortest = distFrom1[n];
        int throughP = distFrom1[p] + distFromP[n];
        if (shortest == throughP) {
            return "SAVE HIM";
        }
        return "GOOD BYE";
    }

    private int[] dijkstra(int start, int n, List<List<int[]>> graph) {
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
            for (int[] next : graph.get(currentNode)) {
                int nextNode = next[0];
                int nextDistance = currentDistance + next[1];
                if (nextDistance < dist[nextNode]) {
                    dist[nextNode] = nextDistance;
                    pq.offer(new int[]{nextNode, nextDistance});
                }
            }
        }
        return dist;
    }
}
```

1. **알고리즘:** 다익스트라를 두 번 실행한다.
2. **선택 이유:** 양수이지만 서로 다른 도로 길이이므로 일반 BFS로 최단거리를 구할 수 없다. 최단거리와 친구를 경유한 최단거리를 비교한다.
3. **실행 순서:** 양방향 그래프 → 1번 시작 최단거리 → p번 시작 최단거리 → distFrom1[n]과 distFrom1[p]+distFromP[n] 비교.
4. **핵심 코드:** PriorityQueue에는 {정점, 거리}가 들어간다. 작은 거리부터 꺼내고, dist보다 큰 오래된 정보는 continue한다. 더 짧을 때만 갱신한다. 별도 visited는 필요 없다. 가장 짧은 경로가 여러 개라도 경유 거리와 전체 거리가 같으면 충분하다.
5. **예제 추적:** 1을 꺼내 dist[2]=1, dist[3]=1 → 3을 처리하면 dist[4]=2, dist[5]=3 → 4에서 5로 가도 3으로 같아 추가 갱신 없음 → 5에서 dist[6]=4. p=4에서 시작하면 distFromP[6]=2이므로 2+2=4, SAVE HIM이다. p=2라면 distFrom1[2]=1, distFromP[6]=5이므로 1+5=6>4, GOOD BYE이다.
6. **복잡도:** V개 정점, E개 간선에 대해 시간 O((V+E) log V), 공간 O(V+E). 두 번 실행해도 점근 복잡도는 같다. 양수 간선이므로 최단 경로는 단순 경로이고 최대 4,999*10,000이다. 두 거리의 합도 int 범위이며, 필요한 경로가 존재하는 조건을 사용한다.

원본: **백준 18223 — 민준이와 마산 그리고 건우**

[원본 문제](https://www.acmicpc.net/problem/18223)

## 5. 국경이 열리는 날

[완성된 실행 파일](answers/Main5.java)

```java
import java.util.*;

class Solution5 {
    int[][] population;
    boolean[][] visited;
    int n;
    int l;
    int r;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(int[][] population, int l, int r) {
        this.population = population;
        this.n = population.length;
        this.l = l;
        this.r = r;
        int days = 0;

        while (true) {
            visited = new boolean[n][n];
            boolean moved = false;
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    if (!visited[row][col]) {
                        if (bfs(row, col)) {
                            moved = true;
                        }
                    }
                }
            }
            if (!moved) {
                return days;
            }
            days++;
        }
    }

    private boolean bfs(int startRow, int startCol) {
        Queue<int[]> queue = new LinkedList<>();
        List<int[]> union = new ArrayList<>();
        queue.offer(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;
        int sum = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            union.add(current);
            sum += population[row][col];

            for (int d = 0; d < 4; d++) {
                int nextRow = row + dr[d];
                int nextCol = col + dc[d];

                if (nextRow >= 0 && nextRow < n && nextCol >= 0 && nextCol < n
                        && !visited[nextRow][nextCol]
                        && Math.abs(population[row][col] - population[nextRow][nextCol]) >= l
                        && Math.abs(population[row][col] - population[nextRow][nextCol]) <= r) {
                    visited[nextRow][nextCol] = true;
                    queue.offer(new int[]{nextRow, nextCol});
                }
            }
        }

        if (union.size() == 1) {
            return false;
        }
        int average = sum / union.size();
        for (int[] country : union) {
            population[country[0]][country[1]] = average;
        }
        return true;
    }
}
```

1. **알고리즘:** BFS와 일별 시뮬레이션.
2. **선택 이유:** 열린 국경으로 연결된 나라들의 연합을 찾고, 다음 날에는 바뀐 인구수로 다시 탐색해야 한다.
3. **실행 순서:** 하루 방문 배열 초기화 → 미방문 나라에서 BFS로 연합과 합계 계산 → 평균 배분 → 모든 연합 처리 → 이동했으면 days 증가, 아니면 종료.
4. **핵심 코드:** visited는 오늘 이미 연합을 정한 나라인지 나타낸다. union은 배분할 좌표 목록이라 queue와 역할이 다르다. 평균 배분은 반드시 해당 연합 BFS를 끝낸 뒤 한다. 하나의 연합을 완전히 찾으면 다른 미방문 나라와 열린 국경이 없다는 것이 확정된다. 이후 BFS는 visited인 나라를 검사하지 않고 미방문 나라의 인구는 아직 원래 값이므로, 연합별로 바로 배분해도 그날의 연합 결정이 변하지 않는다. 입력 population을 직접 변경하므로 같은 원래 입력을 다시 시험하려면 새 배열을 넘긴다.
5. **예제 추적:** {{50,30},{20,40}}, l=20, r=50에서 queue=[{0,0}], visited[0][0]=true → 아래 20과 차이30, 오른쪽30과 차이20으로 두 칸 삽입 → 20에서 오른쪽40과 차이20으로 마지막 칸 삽입 → union 4개, sum=140 → 평균35로 모두 변경 → days=1. 다음 날 visited를 초기화하면 인구 차이0<20으로 모든 연합이 한 칸이다. moved=false이므로 1 반환.
6. **복잡도:** 이동 일수를 D라 하면 종료 확인을 포함해 시간 O((D+1)*N²), 공간 O(N²).

원본: **백준 16234 — 인구 이동**

[원본 문제](https://www.acmicpc.net/problem/16234)

## 원본 확인

백준 직접 조회에서는 정상 문제 본문을 받지 못했습니다. 다섯 문제의 번호·제목과 실재 여부를 [BaekjoonHub 문제별 기록](https://github.com/chech2/Algorithm) 및 [별도 백준 풀이 목록](https://github.com/jiy12345/Beakjoon-Online-Judge)에서 교차 확인했습니다. 원본을 직접 열어 검증했다고 기록하지 않습니다.

인구 이동의 조건·제한은 [문제 조건 기록](https://junuuu.tistory.com/m/37), 친구 경유 문제의 정점·간선 제한과 예제는 [문제 기록](https://jaewoo2233.tistory.com/83), 감염 문제의 컴퓨터 제한은 [문제 기록](https://daily-stack.tistory.com/70)과 대조했습니다. 입출력만 메서드 매개변수와 반환값으로 변경했습니다.

도시 문제의 입력 제한은 [문제 조건 기록](https://developer-project.tistory.com/430), 알파벳 보드의 크기·문자 조건은 [문제 조건 기록](https://kdh0518.tistory.com/165?category=607016)과 대조했습니다.

