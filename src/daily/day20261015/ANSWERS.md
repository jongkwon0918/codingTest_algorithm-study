# 정답 및 해설 — 2026-10-15

완성 코드를 먼저 제공하고 알고리즘·이유·실행 순서·핵심 코드·예제 추적·복잡도를 설명합니다.

## 1. 대각선으로 이어진 섬

[완성된 실행 파일](answers/Main1.java)

```java
import java.util.*;

class Solution1 {
    int[][] map;
    boolean[][] visited;
    int n;
    int m;
    int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
    int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

    public int solution(int[][] map) {
        this.map = map;
        n = map.length;
        m = map[0].length;
        visited = new boolean[n][m];
        int count = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if (map[row][col] == 1 && !visited[row][col]) {
                    dfs(row, col);
                    count++;
                }
            }
        }
        return count;
    }

    private void dfs(int row, int col) {
        visited[row][col] = true;
        for (int d = 0; d < 8; d++) {
            int nr = row + dr[d];
            int nc = col + dc[d];
            if (nr >= 0 && nr < n && nc >= 0 && nc < m
                    && map[nr][nc] == 1 && !visited[nr][nc]) {
                dfs(nr, nc);
            }
        }
    }
}
```

1. **알고리즘:** 재귀 DFS.
2. **이유:** 최단거리가 아니라 연결된 땅의 묶음 개수를 구한다. 원본은 대각선도 연결한다.
3. **실행 순서:** 방문 배열 초기화 → 전체 격자 확인 → 미방문 땅에서 DFS → 연결 영역 하나마다 count 증가.
4. **핵심 코드:** dr,dc에 8방향을 넣는다. 범위·땅·미방문 조건을 하나의 if로 검사한다. 행 반복은 n, 열 반복은 m, 방향 반복은 d++이다. 방문 배열은 매번 새로 만들어 연속 호출에 상태가 남지 않는다.
5. **예제 추적:** {{1,0},{0,1}}에서 dfs(0,0) → visited[0][0]=true → 대각선 (1,1)에 땅이 있고 미방문 → dfs(1,1) → 방문 후 종료 → count=1. 전체 반복에서 (1,1)은 이미 방문했으므로 추가로 세지 않는다.
6. **복잡도:** 시간·공간 O(N*M). 재귀 깊이는 최악에 땅 수만큼이며 최대 2,500칸이다.

원본: **백준 4963 — 섬의 개수**

[원본 문제](https://www.acmicpc.net/problem/4963) · [문제 조건 대조 기록](https://young-library.tistory.com/167)

## 2. 목표까지의 거리 지도

[완성된 실행 파일](answers/Main2.java)

```java
import java.util.*;

class Solution2 {
    public int[][] solution(int[][] map) {
        int n = map.length;
        int m = map[0].length;
        int[][] dist = new int[n][m];
        Queue<int[]> queue = new LinkedList<>();
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if (map[row][col] == 1) {
                    dist[row][col] = -1;
                } else if (map[row][col] == 2) {
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
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && map[nr][nc] != 0 && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[row][col] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return dist;
    }
}
```

1. **알고리즘:** 목표를 시작점으로 하는 BFS.
2. **이유:** 이동은 양방향이고 비용이 1이다. 각 칸에서 목표로 가는 거리와 목표에서 각 칸으로 가는 거리가 같아서 한 번의 탐색으로 모두 구할 수 있다.
3. **실행 순서:** 갈 수 있는 칸만 -1로 초기화 → 목표 좌표 큐 삽입, 거리0 → BFS로 거리 갱신 → 배열 반환.
4. **핵심 코드:** dist=-1이면 미방문인 땅이다. 원래 장애물은 0을 유지하고, 도달하지 못한 땅은 -1을 유지한다. 별도 visited와 큐의 거리 저장은 필요 없다. n,m을 각각 행·열 범위에 사용한다.
5. **예제 추적:** 목표(0,0)의 dist=0,queue=[{0,0}] → (1,0),(0,1)의 dist=1 → (1,0)에서 (1,1)을 발견해 dist=2 → (0,1)에서는 (1,1)이 이미 거리2라 다시 넣지 않음. (2,2)는 0인 칸들에 막혀 -1이다. 최종 [[0,1,0],[1,2,0],[0,0,-1]].
6. **복잡도:** 시간·공간 O(N*M).

원본: **백준 14940 — 쉬운 최단거리**

[원본 문제](https://www.acmicpc.net/problem/14940) · [문제 조건 대조 기록](https://kne-coding.tistory.com/270)

## 3. 가까운 면접장까지 가장 먼 도시

[완성된 실행 파일](answers/Main3.java)

```java
import java.util.*;

class Solution3 {
    public long[] solution(int n, int[][] roads, int[] centers) {
        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            // 원래 u -> v인 도로를 v -> u로 저장한다.
            graph[road[1]].add(new int[]{road[0], road[2]});
        }

        long[] dist = new long[n + 1];
        Arrays.fill(dist, Long.MAX_VALUE);
        PriorityQueue<long[]> pq = new PriorityQueue<>(
                (a, b) -> Long.compare(a[1], b[1]));
        for (int center : centers) {
            dist[center] = 0;
            pq.offer(new long[]{center, 0});
        }

        while (!pq.isEmpty()) {
            long[] current = pq.poll();
            int currentNode = (int) current[0];
            long currentDistance = current[1];
            if (currentDistance > dist[currentNode]) {
                continue;
            }
            for (int[] next : graph[currentNode]) {
                int nextNode = next[0];
                long nextDistance = currentDistance + next[1];
                if (nextDistance < dist[nextNode]) {
                    dist[nextNode] = nextDistance;
                    pq.offer(new long[]{nextNode, nextDistance});
                }
            }
        }

        int answer = 1;
        for (int i = 2; i <= n; i++) {
            if (dist[i] > dist[answer]) {
                answer = i;
            }
        }
        return new long[]{answer, dist[answer]};
    }
}
```

1. **알고리즘:** 도로를 뒤집고 모든 면접장을 동시에 시작점으로 넣는 다익스트라.
2. **이유:** 모든 도시를 출발점으로 실행하면 입력 규모가 크다. 도시→면접장 경로를 뒤집으면 면접장→도시 경로가 되고, 비용은 같다. 면접장을 모두 거리0으로 넣으면 가장 가까운 면접장까지의 거리만 남는다.
3. **실행 순서:** 역방향 그래프 생성 → 모든 면접장 dist=0,큐 삽입 → 최소거리부터 갱신 → 도시 번호 순으로 최대 거리 선택.
4. **핵심 코드:** 한 방향만 뒤집어 저장하며 양방향으로 만들지 않는다. 누적 거리·dist·큐는 long으로 통일한다. 오래된 거리 항목은 continue한다. 최대 거리가 같은 경우에는 answer를 바꾸지 않아 작은 번호가 유지된다. 별도 visited는 없다.
5. **예제 추적:** 4,5를 시작점으로 삽입 → 4에서 역방향 도로로 도시2의 거리3,도시3의 거리6 → 5에서 도시1의 거리10,도시3의 거리1 → 거리3인 도시2에서 도시1을 3+2=5로 갱신 → 최종 도시1..5의 dist=[5,3,1,0,0] → 최대는 도시1의5이다.
6. **복잡도:** 시간 O((V+E) log(V+1)), 공간 O(V+E).

원본: **백준 17835 — 면접보는 승범이네**

[원본 문제](https://www.acmicpc.net/problem/17835) · [문제 조건 대조 기록](https://coding-moments.tistory.com/39?category=1212494)

## 4. 연산 순서에 따른 최댓값과 최솟값

[완성된 실행 파일](answers/Main4.java)

```java
import java.util.*;

class Solution4 {
    int[] numbers;
    int[] operators;
    int max;
    int min;

    public int[] solution(int[] numbers, int[] operators) {
        this.numbers = numbers;
        this.operators = operators;
        max = Integer.MIN_VALUE;
        min = Integer.MAX_VALUE;
        dfs(1, numbers[0]);
        return new int[]{max, min};
    }

    private void dfs(int index, int current) {
        if (index == numbers.length) {
            max = Math.max(max, current);
            min = Math.min(min, current);
            return;
        }

        for (int i = 0; i < 4; i++) {
            if (operators[i] > 0) {
                operators[i]--;
                int next = numbers[index];
                if (i == 0) {
                    dfs(index + 1, current + next);
                } else if (i == 1) {
                    dfs(index + 1, current - next);
                } else if (i == 2) {
                    dfs(index + 1, current * next);
                } else {
                    dfs(index + 1, current / next);
                }
                operators[i]++;
            }
        }
    }
}
```

1. **알고리즘:** 재귀 DFS와 연산자 개수의 백트래킹.
2. **이유:** 숫자 순서는 고정이고 사용 가능한 연산자 종류를 선택해야 한다. 최대 연산자 수가10이라 가능한 순서를 탐색할 수 있다.
3. **실행 순서:** 최댓값·최솟값 초기화 → 첫 숫자를 현재 값으로 설정 → 남은 연산자 하나 선택·감소 → 다음 숫자와 계산하고 재귀 → 사용 개수 복원 → 모든 숫자 사용 시 결과 갱신.
4. **핵심 코드:** operators[i]를 감소시켰다가 재귀 후 증가시킨다. 같은 종류의 연산자를 개별 번호로 구분하지 않아 중복 순서를 만들지 않는다. Java 정수 나눗셈은 음수에서도 0 방향으로 버리므로 원본 조건과 같다. next는 양수라 0 나누기는 없다. max,min은 매 호출 초기화하고 operators도 탐색 종료 후 원래 값으로 복원된다.
5. **예제 추적:** numbers=[1,2,3],operators=[1,0,1,0] → dfs(1,1) → + 사용, [0,0,1,0] → dfs(2,3) → * 사용 → dfs(3,9),max=min=9 → 연산자 복원 → 첫 위치에서 * 사용 → dfs(2,2) → + 사용 → dfs(3,5),max=9,min=5 → [9,5] 반환.
6. **복잡도:** 숫자 수 N에 대해 시간의 느슨한 상한 O((N-1)!), 재귀 공간 O(N). 같은 연산자가 여러 개면 탐색량은 더 작다.

원본: **백준 14888 — 연산자 끼워넣기**

[원본 문제](https://www.acmicpc.net/problem/14888) · [문제 조건 대조 기록](https://hellominchan.tistory.com/175)

## 5. 일부 바이러스만 활성화하기

[완성된 실행 파일](answers/Main5.java)

```java
import java.util.*;

class Solution5 {
    int[][] lab;
    int n;
    int m;
    int emptyCount;
    int answer;
    List<int[]> viruses;
    int[] selected;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(int[][] lab, int m) {
        this.lab = lab;
        n = lab.length;
        this.m = m;
        emptyCount = 0;
        answer = Integer.MAX_VALUE;
        viruses = new ArrayList<>();
        selected = new int[m];

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (lab[row][col] == 0) {
                    emptyCount++;
                } else if (lab[row][col] == 2) {
                    viruses.add(new int[]{row, col});
                }
            }
        }
        if (emptyCount == 0) {
            return 0;
        }
        dfs(0, 0);
        return answer == Integer.MAX_VALUE ? -1 : answer;
    }

    private void dfs(int start, int count) {
        if (count == m) {
            answer = Math.min(answer, bfs());
            return;
        }
        for (int i = start; i < viruses.size(); i++) {
            selected[count] = i;
            dfs(i + 1, count + 1);
        }
    }

    private int bfs() {
        int[][] dist = new int[n][n];
        for (int row = 0; row < n; row++) {
            Arrays.fill(dist[row], -1);
        }
        Queue<int[]> queue = new LinkedList<>();
        for (int index : selected) {
            int[] virus = viruses.get(index);
            int row = virus[0];
            int col = virus[1];
            dist[row][col] = 0;
            queue.offer(new int[]{row, col});
        }

        int infected = 0;
        int time = 0;
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n
                        && lab[nr][nc] != 1 && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[row][col] + 1;
                    queue.offer(new int[]{nr, nc});
                    if (lab[nr][nc] == 0) {
                        infected++;
                        time = Math.max(time, dist[nr][nc]);
                    }
                }
            }
        }
        return infected == emptyCount ? time : Integer.MAX_VALUE;
    }
}
```

1. **알고리즘:** 재귀 DFS로 활성화 조합 선택, 다중 시작점 BFS로 전파 시간 계산.
2. **이유:** 어느 바이러스를 고르는지에 따라 결과가 달라지며, 여러 활성 바이러스는 동시에 퍼진다. 조합별 최단 전파 시간을 비교해야 한다.
3. **실행 순서:** 후보 좌표와 빈칸 수 수집 → m개 후보 조합 → 선택한 바이러스를 모두 거리0으로 큐에 삽입 → 벽을 제외한 칸 탐색 → 모든 빈칸을 감염시킨 조합의 최소 시간 유지.
4. **핵심 코드:** selected에는 좌표가 아니라 후보 리스트의 인덱스를 넣는다. i+1부터 선택해 중복을 막는다. dist=-1이 미방문을 구분해 visited는 필요 없다. 0과2 모두 탐색하지만 infected와 완료 시간은 원래0인 칸에서만 갱신한다. 비활성 칸에도 이동 시간이 걸리며, 마지막까지 남은 비활성 칸 때문에 정답 시간을 늘리지 않는다. 실패 조합은 INF을 반환하고 모든 조합이 실패하면 -1이다.
5. **예제 추적:** 첫 지도에서 후보0=(0,0),후보1=(0,2). 후보0만 선택하면 dist[0][0]=0 → 빈칸(0,1)=1,infected=1 → 비활성(0,2)=2,infected 유지 → 빈칸(0,3)=3,infected=2,time=3. 후보1을 선택하면 좌우 빈칸 모두 거리1 → infected=2,time=1. answer=min(3,1)=1. 두 번째 지도에서는 후보(0,1)에서 빈칸(0,0)에 시간1로 도달한 뒤 먼 비활성 칸까지 탐색해도 time은1로 유지된다.
6. **복잡도:** 후보 V개 중 M개를 고르는 조합 수를 C라 하면 시간 O(C*N²+2^V), 공간 O(N²+V+M). V<=10이라 최대 조합 수는252이고 재귀 깊이는 M 이하이다.

원본: **백준 17142 — 연구소 3**

[원본 문제](https://www.acmicpc.net/problem/17142) · [문제 조건 대조 기록](https://loosie.tistory.com/261)

## 원본 확인과 학습 기록

백준 직접 조회에서는 정상 문제 본문을 얻지 못했습니다. 문제별 공개 기록을 대조해 실제 문제의 번호·제목과 조건을 확인했습니다. 원문 직접 대조 완료라고 주장하지 않습니다. 각 문제의 확인 출처는 위에 있습니다. 면접장 문제의 입력 크기는 [입력 제한 기록](https://infinitecode.tistory.com/33)과도 대조했습니다.

1번은 여러 테스트 케이스를 여러 solution 호출로, 나머지는 원본 입력 데이터를 매개변수로 전달하고 원본 출력값을 반환값으로 바꿨습니다. 2번의 0과-1 의미, 3번의 단방향·동률, 4번의 왼쪽부터 계산·음수 나눗셈, 5번의 비활성 칸 이동 시간·빈칸만 완료 판정 조건을 유지했습니다.

기존 일반 연습 main4는 상하좌우 연결이라 대각선도 연결하는 4963과 같지 않습니다. 1번은 이번에 처음 출제합니다. 최근 코드 검토에서 행·열 범위, 누적 거리 자료형, 상태 구분을 놓친 사례를 관찰했습니다. 오늘 관련 조건을 연습에 반영했으며 아직 빈번한 오답 유형으로 단정하지 않습니다.

