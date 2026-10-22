# 2026-10-22 정답 및 해설

문제를 먼저 풀고 확인하세요. 각 절은 완성 코드 다음에 풀이를 설명합니다.

## 1. 한 번 이상 이동해서 도달하기

[완성된 실행 파일](answers/Main1.java)

```java
import java.util.*;

class Solution1 {
    List<Integer>[] graph;
    boolean[] visited;

    public int[][] solution(int[][] connections) {
        int n = connections.length;
        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (connections[i][j] == 1) {
                    graph[i].add(j);
                }
            }
        }

        int[][] answer = new int[n][n];
        for (int start = 0; start < n; start++) {
            visited = new boolean[n];
            // 최소 한 개의 간선을 이용해야 하므로 바로 다음 정점부터 방문한다.
            for (int next : graph[start]) {
                if (!visited[next]) {
                    dfs(next);
                }
            }
            for (int i = 0; i < n; i++) {
                if (visited[i]) {
                    answer[start][i] = 1;
                }
            }
        }
        return answer;
    }

    private void dfs(int current) {
        visited[current] = true;
        for (int next : graph[current]) {
            if (!visited[next]) {
                dfs(next);
            }
        }
    }
}
```

1. **알고리즘:** 출발점마다 재귀 DFS를 실행한다.
2. **이유:** 거리보다 도달 가능 여부가 필요하다. 각 정점에서 연결 방향을 따라 방문하면 된다.
3. **실행 순서:** 인접 리스트 구성 → 출발점별 visited 초기화 → 출발점의 이웃부터 DFS → 방문 결과를 정답 행으로 복사.
4. **핵심 코드:** 시작부터 visited[start]=true로 만들지 않는다. 최소 한 번 이동한 뒤부터 방문 처리하므로, 자기 자신은 실제 돌아오는 경로가 있을 때만 1이다. 방문 배열은 출발점마다 별개이며 백트래킹하지 않는다.
5. **예제 추적:** 첫 예제에서 start=0 → 이웃 1에서 dfs(1) → visited=[false,true,false] → dfs(2) → [false,true,true]. 첫 행은 [0,1,1]. start=1에서는 2만 방문해 [0,0,1]. 두 번째 예제에서는 start=0 → dfs(1) → dfs(0)으로 둘 다 true가 되어 대각선도 1이다.
6. **복잡도:** 정점 N, 간선 E에 대해 시간 O(N²+N(N+E)), 공간 O(N²+E). 결과 행렬을 포함하며 재귀 깊이는 최대 N이다.

원본: **백준 11403 — 경로 찾기**

[원본 문제](https://www.acmicpc.net/problem/11403) · [문제 조건 대조 기록](https://mbc-dev-blog.vercel.app/posts/2020-01-01-algorithm-BOJ-11403-post)

## 2. 두 정점이 연결되는 순간의 비용

[완성된 실행 파일](answers/Main2.java)

```java
import java.util.*;

class Solution2 {
    public int solution(int n, int[][] edges, int s, int t) {
        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int cost = edge[2];
            graph[u].add(new int[]{v, cost});
            graph[v].add(new int[]{u, cost});
        }

        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[s] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[1], b[1]));
        pq.offer(new int[]{s, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currentNode = current[0];
            int currentCost = current[1];
            if (currentCost > dist[currentNode]) {
                continue;
            }
            for (int[] next : graph[currentNode]) {
                int nextNode = next[0];
                int nextCost = currentCost + next[1];
                if (nextCost < dist[nextNode]) {
                    dist[nextNode] = nextCost;
                    pq.offer(new int[]{nextNode, nextCost});
                }
            }
        }
        return dist[t];
    }
}
```

1. **알고리즘:** PriorityQueue를 사용하는 다익스트라.
2. **이유:** s와 t가 연결되면 추가된 간선 안에 s→t 경로가 있다. 양의 가중치이므로 추가한 전체 비용은 그 경로 비용 이상이다. 반대로 가장 짧은 경로의 간선만 순서대로 추가하면 그 비용으로 연결된다. 따라서 목표 값은 최단 경로 비용이다.
3. **실행 순서:** 양방향 연결 → dist 초기화 → 최소 비용 정점부터 꺼내기 → 인접 정점 거리 갱신 → dist[t] 반환.
4. **핵심 코드:** nextCost와 dist[nextNode]를 비교한다. 현재 정점까지의 비용과 비교하지 않는다. 큐에서 꺼낸 오래된 비용은 continue로 넘긴다. 모든 정점을 연결하는 최소 신장 트리를 만들 필요가 없다.
5. **예제 추적:** dist[1]=0 → 1 처리 후 dist[2]=2,dist[3]=1 → 3 처리 후 dist[4]=9 → 2 처리 후 dist[4]=4로 갱신 → 4 처리. 이후 큐의 {4,9}는 9>4이므로 건너뛴다. 결과 4.
6. **복잡도:** 시간 O(N+E log(E+1)), 공간 O(N+E). 중복 간선이 있어도 같은 코드로 처리한다. 최단 단순 경로 비용은 최대 4,999×100이므로 int로 충분하다.

원본: **백준 14284 — 간선 이어가기 2**

[원본 문제](https://www.acmicpc.net/problem/14284) · [문제 조건 대조 기록](https://intrepidgeeks.com/tutorial/baizhun-algorithm-14284-trunk-connection-2)

## 3. 두 연산으로 목표 숫자 만들기

[완성된 실행 파일](answers/Main3.java)

```java
import java.util.*;

class Solution3 {
    public int solution(int a, int b) {
        // {현재 수, 지금까지의 연산 횟수 + 1}
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{a, 1});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int number = current[0];
            int count = current[1];
            if (number == b) {
                return count;
            }

            // int 곱셈이 먼저 넘치지 않도록 long으로 계산한다.
            long next = number * 2L;
            if (next <= b) {
                queue.offer(new int[]{(int) next, count + 1});
            }
            next = number * 10L + 1;
            if (next <= b) {
                queue.offer(new int[]{(int) next, count + 1});
            }
        }
        return -1;
    }
}
```

1. **알고리즘:** Queue+LinkedList를 사용하는 BFS.
2. **이유:** 두 연산 모두 한 번의 비용이 같으므로 연산 횟수 순서로 탐색한다. 역방향 풀이도 가능하지만 여기서는 숫자 상태를 탐색하는 기본 BFS를 연습한다.
3. **실행 순서:** {a,1} 삽입 → 목표인지 확인 → 두 연산 결과 중 b 이하만 삽입 → 최초 목표의 count 반환.
4. **핵심 코드:** number*10L+1은 오른쪽에 1을 붙이는 연산이다. 곱셈부터 long으로 계산하고 범위 검사 후 int로 넣는다. 두 연산은 항상 수를 증가시킨다. 또한 다음 수가 짝수이면 이전 수는 절반, 끝자리가 1이면 이전 수는 마지막 1을 지운 수로 유일하고 두 경우는 겹치지 않는다. 따라서 같은 수를 여러 경로로 만들지 않아 visited나 거대한 배열이 필요 없다.
5. **예제 추적:** {2,1} → {4,2},{21,2} → {8,3},{41,3},{42,3} → 이후 {81,4} → {162,5}. 162가 처음 나올 때 반환값은 5다. 4→42는 가능한 상태가 모두 b를 넘거나 탐색이 끝나므로 -1이다.
6. **복잡도:** 실제 생성한 상태 수를 S라 하면 시간 O(S), 공간 O(S). 상태 값이 모두 다르고 b 이하이므로 S<=b-a+1이다. 각 연산이 두 배 이상 증가해 깊이는 O(log(b/a))이고 두 갈래 탐색의 느슨한 상한은 O(b/a)이다.

원본: **백준 16953 — A → B**

[원본 문제](https://www.acmicpc.net/problem/16953) · [문제 조건 대조 기록](https://intrepidgeeks.com/tutorial/baekjoon-16953-ab)

## 4. 모두의 이동 거리가 가장 작은 방

[완성된 실행 파일](answers/Main4.java)

```java
import java.util.*;

class Solution4 {
    public int solution(int n, int[][] passages, int[] friends) {
        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] passage : passages) {
            int u = passage[0];
            int v = passage[1];
            int cost = passage[2];
            graph[u].add(new int[]{v, cost});
            graph[v].add(new int[]{u, cost});
        }

        int[] total = new int[n + 1];
        for (int start : friends) {
            int[] dist = dijkstra(n, graph, start);
            for (int room = 1; room <= n; room++) {
                total[room] += dist[room];
            }
        }

        int answer = 1;
        for (int room = 2; room <= n; room++) {
            if (total[room] < total[answer]) {
                answer = room;
            }
        }
        return answer;
    }

    private int[] dijkstra(int n, List<int[]>[] graph, int start) {
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
            for (int[] next : graph[currentNode]) {
                int nextNode = next[0];
                int nextCost = currentCost + next[1];
                if (nextCost < dist[nextNode]) {
                    dist[nextNode] = nextCost;
                    pq.offer(new int[]{nextNode, nextCost});
                }
            }
        }
        return dist;
    }
}
```

1. **알고리즘:** 친구마다 다익스트라를 실행하고 방별로 거리를 합산한다.
2. **이유:** 통로 비용이 양수이고 서로 다르다. 친구 한 명의 최단거리 배열을 구하면 그 친구가 모든 후보 방으로 가는 비용을 한 번에 알 수 있다.
3. **실행 순서:** 양방향 그래프 → 친구별 최단거리 → total[방] 누적 → 합이 가장 작은 방 선택.
4. **핵심 코드:** total은 각 방의 거리 합이며 dist와 역할이 다르다. 번호가 작은 방부터 검사하면서 더 작을 때만 answer를 바꾸면 동률일 때 작은 방이 유지된다. 정렬은 필요 없다. 모든 방에 도달할 수 있어 INF를 합산하지 않는다.
5. **예제 추적:** friends={1,4}에서 1번의 dist=[0,1,2,3], 4번의 dist=[3,2,1,0] (0번 인덱스 생략). total=[3,3,3,3]이므로 answer=1 유지. friends={1,3,4}이면 total=[5,4,3,4]라서 3번 방을 선택한다. 한 친구만 4번에 있으면 4번의 합이 0으로 최소다.
6. **복잡도:** 친구 K명, 통로 E개에 대해 시간 O(K(N+E log(E+1))), 공간 O(N+E). total은 최대 100×99×1,000으로 int 범위 안이다.

원본: **백준 13424 — 비밀 모임**

[원본 문제](https://www.acmicpc.net/problem/13424) · [문제 조건 대조 기록](https://wgrgwg.tistory.com/entry/백준-13424-비밀-모임-Java)

## 5. 내려오는 벽을 피해 탈출하기

[완성된 실행 파일](answers/Main5.java)

```java
import java.util.*;

class Solution5 {
    int[] dr = {-1, 1, 0, 0, -1, -1, 1, 1, 0};
    int[] dc = {0, 0, -1, 1, -1, 1, -1, 1, 0};

    public int solution(String[] maze) {
        char[][] board = new char[8][8];
        for (int row = 0; row < 8; row++) {
            board[row] = maze[row].toCharArray();
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{7, 0});

        while (!queue.isEmpty()) {
            int size = queue.size();
            // 같은 시간의 같은 칸만 중복 제거한다. 다음 초에는 다시 방문 가능하다.
            boolean[][] visited = new boolean[8][8];

            for (int i = 0; i < size; i++) {
                int[] current = queue.poll();
                int row = current[0];
                int col = current[1];
                // 지난 초에 벽이 내려와 덮은 상태는 더 탐색하지 않는다.
                if (board[row][col] == '#') {
                    continue;
                }
                if (row == 0 && col == 7) {
                    return 1;
                }

                for (int d = 0; d < 9; d++) {
                    int nr = row + dr[d];
                    int nc = col + dc[d];
                    if (nr >= 0 && nr < 8 && nc >= 0 && nc < 8
                            && board[nr][nc] != '#' && !visited[nr][nc]) {
                        visited[nr][nc] = true;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }

            // 같은 초의 모든 캐릭터 상태를 처리한 뒤 벽을 한 번 내린다.
            // 아래쪽부터 복사해야 원래 행이 덮어쓰이지 않는다.
            for (int row = 7; row >= 1; row--) {
                for (int col = 0; col < 8; col++) {
                    board[row][col] = board[row - 1][col];
                }
            }
            Arrays.fill(board[0], '.');
        }
        return 0;
    }
}
```

1. **알고리즘:** 시간 단위 BFS + 벽 이동 시뮬레이션.
2. **이유:** 같은 칸도 벽이 내려간 다음에는 다시 유효한 상태가 된다. 제자리 대기까지 포함해 가능한 상태들을 매초 함께 탐색해야 한다.
3. **실행 순서:** 현재 큐 크기를 저장 → 그 개수만큼 현재 초의 상태 처리 → 9가지 행동 삽입 → 벽을 한 번 아래로 이동 → 다음 초 반복.
4. **핵심 코드:** size를 고정해 방금 넣은 다음 시간 상태를 즉시 꺼내지 않는다. visited는 시간마다 새로 만들어 같은 시간의 중복만 막는다. 벽 이동 뒤 덮인 상태는 다음 while 회차에서 꺼냈을 때 버린다. 마지막 방향 (0,0)은 대기다. 이동 거리나 별도의 시간 배열은 필요 없다.
5. **예제 추적:** falling 예제에서 초기 벽은 (6,0), 캐릭터는 (7,0). 첫 행동으로 (7,1) 또는 (6,1)로 피할 수 있다. 대기한 (7,0)도 일단 큐에 들어가지만 벽이 (7,0)으로 내려온 다음 회차에서 버린다. 옆으로 피한 상태는 살아남고, 다음 벽 이동으로 벽이 사라져 목표로 갈 수 있다. trapped 예제는 첫 행동에서 맨 아래 칸들만 가능하고 벽 이동 후 그 모든 칸이 덮여 큐가 소진되어 0이다.
6. **복잡도:** 초마다 최대 64개 위치, 위치마다 9가지 행동을 검사하고 벽 64칸을 갱신한다. 8초 뒤 모든 벽이 사라지며 살아남은 상태는 7번 이내의 추가 이동으로 목표에 도달한다. 따라서 8*8 고정 입력에서 시간·공간은 O(1)이다.

원본: **백준 16954 — 움직이는 미로 탈출**

[원본 문제](https://www.acmicpc.net/problem/16954) · [문제 조건 대조 기록](https://100100e.tistory.com/246)

## 출처 확인 및 변환 범위

백준 직접 조회에서 정상 본문을 얻지 못한 문제들은 공개된 문제별 기록으로 실재 번호·제목·제한사항·핵심 조건을 확인했습니다. 원문 직접 대조 완료라고 주장하지 않습니다. 출처는 각 절에 남겼습니다.

입출력만 메서드 매개변수와 반환값으로 바꿨습니다. 1번의 양수 길이 경로, 2번의 간선 추가 중단 조건, 3번의 연산 횟수+1, 4번의 동률 시 작은 방 번호, 5번의 캐릭터 선행·벽 후행 및 대기를 유지했습니다. 4번의 원본 여러 테스트 케이스는 각각 solution을 호출합니다. 예제 중 일부는 같은 원본 조건을 만족하는 연습용 데이터이며 공식 예제라고 주장하지 않습니다.

최근 혼동한 출발점별 방문 초기화, 간선 비용 합산, 시간별 방문 상태를 복습하도록 선정했습니다. 재귀 DFS·Queue+LinkedList·PriorityQueue 기본 형태를 사용하며 Deque·ArrayDeque·Stream·별도 Node 클래스는 사용하지 않습니다.

