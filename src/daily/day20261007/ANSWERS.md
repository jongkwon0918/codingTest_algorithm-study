# 정답 및 해설 영역

먼저 문제 풀이 파일에서 구현하세요. 아래에는 정답 코드가 먼저 나오고, 알고리즘·이유·실행 순서·핵심 코드·예제 추적·복잡도가 이어집니다.

## 1. 촌수계산

### 완성된 Solution 코드

```java
import java.util.*;

class Solution1 {
    List<Integer>[] graph;
    boolean[] visited;

    public int solution(int n, int start, int target, int[][] relations) {
        graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] relation : relations) {
            int u = relation[0];
            int v = relation[1];
            graph[u].add(v);
            graph[v].add(u);
        }
        visited = new boolean[n + 1];
        return dfs(start, target, 0);
    }

    private int dfs(int current, int target, int count) {
        visited[current] = true;
        if (current == target) {
            return count;
        }
        for (int next : graph[current]) {
            if (!visited[next]) {
                int answer = dfs(next, target, count + 1);
                if (answer != -1) {
                    return answer;
                }
            }
        }
        return -1;
    }
}
```

### 1. 사용할 알고리즘

재귀 DFS

### 2. 선택 이유

부모·자식 관계는 가족 트리이므로 연결된 두 사람의 경로가 하나다. DFS로 그 경로의 간선 수를 세면 촌수가 된다. 일반적인 순환 그래프의 최단거리에는 이 방식 대신 BFS가 필요하다.

### 3. 실행 순서

양방향 인접 리스트를 만든다 → 시작 사람에서 dfs를 호출한다 → 방문 처리 후 목표인지 확인한다 → 미방문 친척으로 재귀 호출한다 → 목표에서 촌수를 반환한다.

### 4. 핵심 코드 역할

visited는 사람의 중복 방문을 막고, count는 현재 재귀 경로의 촌수를 전달한다. 정점별 거리를 보관할 필요가 없어 dist 배열을 만들지 않는다.

### 5. 예제에 직접 대입하기

```text
dfs(7, 3, 0): visited[7]=true
→ next=2, dfs(2, 3, 1): visited[2]=true
→ next=1, dfs(1, 3, 2): visited[1]=true
→ next=2는 이미 방문했으므로 건너뜀
→ next=3, dfs(3, 3, 3): visited[3]=true
→ current==target이므로 3 반환
→ dfs(1), dfs(2), dfs(7) 순으로 3 반환
최종 visited=true인 사람: {7, 2, 1, 3}
```

### 6. 시간복잡도

시간 O(n + e), 공간 O(n + e). e는 관계 수이며 재귀 깊이는 최대 n.

### 원본 문제와 실행 파일

백준 2644번 **촌수계산** — [원본 링크](https://www.acmicpc.net/problem/2644)

[실행 가능한 정답 파일](answers/Main1.java) · [존재 여부 확인 기록](https://github.com/kitae0522/Online-Problem-Solving)

## 2. 스타트링크

### 완성된 Solution 코드

```java
import java.util.*;

class Solution2 {
    public int solution(int f, int start, int target, int up, int down) {
        int[] dist = new int[f + 1];
        Arrays.fill(dist, -1);
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        dist[start] = 0;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == target) {
                return dist[current];
            }
            int[] nextFloors = {current + up, current - down};
            for (int next : nextFloors) {
                if (next >= 1 && next <= f && dist[next] == -1) {
                    dist[next] = dist[current] + 1;
                    queue.offer(next);
                }
            }
        }
        return -1;
    }
}
```

### 1. 사용할 알고리즘

BFS

### 2. 선택 이유

버튼을 한 번 누르는 비용이 항상 1이므로 BFS의 최초 도착 거리가 최소 횟수다.

### 3. 실행 순서

dist를 -1로 초기화한다 → 시작층을 거리 0으로 큐에 넣는다 → 층을 꺼낸다 → 목표면 거리를 반환한다 → 범위 안의 미방문 다음 층을 큐에 넣는다.

### 4. 핵심 코드 역할

dist[next]==-1이 방문 여부를 판단하므로 visited가 필요 없다. up이나 down이 0인 경우도 이미 방문한 층을 다시 넣지 않는다.

### 5. 예제에 직접 대입하기

```text
예제 1: f=10, start=1, target=10, up=2, down=1
초기: queue=[1], dist[1]=0
1 poll → 3 offer: queue=[3], dist[3]=1
3 poll → 5,2 offer: queue=[5,2], dist[5]=2, dist[2]=2
5 poll → 7,4 offer: queue=[2,7,4]
2 poll → 4,1은 방문됨: queue=[7,4]
7 poll → 9,6 offer: queue=[4,9,6], dist[9]=4, dist[6]=4
4 poll → 새 층 없음: queue=[9,6]
9 poll → 8 offer: queue=[6,8], dist[8]=5
6 poll → 새 층 없음: queue=[8]
8 poll → 10 offer: queue=[10], dist[10]=6
10 poll → 6 반환
```

### 6. 시간복잡도

시간 O(f), 공간 O(f).

### 원본 문제와 실행 파일

백준 5014번 **스타트링크** — [원본 링크](https://www.acmicpc.net/problem/5014)

[실행 가능한 정답 파일](answers/Main2.java) · [존재 여부 확인 기록](https://github.com/Dezeli/BOJ)

## 3. 해킹

### 완성된 Solution 코드

```java
import java.util.*;

class Solution3 {
    public int[] solution(int n, int start, int[][] dependencies) {
        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] dependency : dependencies) {
            int a = dependency[0];
            int b = dependency[1];
            int seconds = dependency[2];
            graph[b].add(new int[]{a, seconds});
        }

        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        // {컴퓨터 번호, 감염 시각}
        PriorityQueue<int[]> queue = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[1], b[1])
        );
        queue.offer(new int[]{start, 0});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int currentNode = current[0];
            int currentDist = current[1];
            if (currentDist > dist[currentNode]) {
                continue;
            }
            for (int[] next : graph[currentNode]) {
                int nextNode = next[0];
                int nextDist = currentDist + next[1];
                if (nextDist < dist[nextNode]) {
                    dist[nextNode] = nextDist;
                    queue.offer(new int[]{nextNode, nextDist});
                }
            }
        }

        int count = 0;
        int time = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] != Integer.MAX_VALUE) {
                count++;
                time = Math.max(time, dist[i]);
            }
        }
        return new int[]{count, time};
    }
}
```

### 1. 사용할 알고리즘

다익스트라

### 2. 선택 이유

감염 지연 시간이 서로 다르고 음수가 없다. 시작 컴퓨터에서 각 컴퓨터까지의 최소 비용이 최초 감염 시각이다.

### 3. 실행 순서

의존 입력을 b→a로 연결한다 → dist[start]=0으로 시작한다 → 가장 이른 시각을 꺼낸다 → 더 빠른 감염 경로면 갱신한다 → 유한한 거리의 개수와 최댓값을 계산한다.

### 4. 핵심 코드 역할

graph[b].add({a,seconds})가 전파 방향을 표현한다. PriorityQueue에서 꺼낸 시각이 dist보다 크면 이미 더 빠른 경로가 있어 건너뛴다. 별도 visited는 필요 없다.

### 5. 예제에 직접 대입하기

```text
예제 2: 그래프 1→2(2), 1→3(8), 2→3(4)
초기 dist[1..3]=[0, INF, INF], queue={(1,0)}
(1,0) poll → dist=[0,2,8], queue={(2,2),(3,8)}
(2,2) poll → nextDist=2+4=6 < 8
→ dist=[0,2,6], queue={(3,6),(3,8)}
(3,6) poll → 다음 간선 없음
(3,8) poll → 8 > dist[3]=6이므로 continue
유한한 dist 3개, 최댓값 6 → [3,6]
queue 표기는 우선순위 순서이며 내부 배열 모양을 뜻하지 않는다.
```

### 6. 시간복잡도

시간 O(n + d log(d + 1)), 공간 O(n + d). d는 의존 관계 수.

### 원본 문제와 실행 파일

백준 10282번 **해킹** — [원본 링크](https://www.acmicpc.net/problem/10282)

[실행 가능한 정답 파일](answers/Main3.java) · [존재 여부 확인 기록](https://github.com/chech2/Algorithm)

## 4. 치킨 배달

### 완성된 Solution 코드

```java
import java.util.*;

class Solution4 {
    List<int[]> houses;
    List<int[]> stores;
    int[] selected;
    int answer;
    int m;

    public int solution(int[][] city, int m) {
        houses = new ArrayList<>();
        stores = new ArrayList<>();
        this.m = m;
        answer = Integer.MAX_VALUE;
        selected = new int[m];

        for (int r = 0; r < city.length; r++) {
            for (int c = 0; c < city.length; c++) {
                if (city[r][c] == 1) {
                    houses.add(new int[]{r, c});
                } else if (city[r][c] == 2) {
                    stores.add(new int[]{r, c});
                }
            }
        }
        dfs(0, 0);
        return answer;
    }

    private void dfs(int start, int count) {
        if (count == m) {
            int sum = 0;
            for (int[] house : houses) {
                int minDist = Integer.MAX_VALUE;
                for (int index : selected) {
                    int[] store = stores.get(index);
                    int dist = Math.abs(house[0] - store[0])
                            + Math.abs(house[1] - store[1]);
                    minDist = Math.min(minDist, dist);
                }
                sum += minDist;
            }
            answer = Math.min(answer, sum);
            return;
        }

        for (int i = start; i < stores.size(); i++) {
            selected[count] = i;
            dfs(i + 1, count + 1);
        }
    }
}
```

### 1. 사용할 알고리즘

재귀 DFS + 조합 완전탐색

### 2. 선택 이유

치킨집은 최대 13개라 m개를 고르는 모든 조합을 확인할 수 있다. 치킨집을 추가해도 집의 최소 거리는 늘지 않으므로 '최대 m개' 중 정확히 m개를 남기는 조합을 검사하면 된다.

### 3. 실행 순서

집·치킨집 좌표를 저장한다 → DFS로 m개 조합을 만든다 → 각 집과 선택한 치킨집 사이 최소 거리를 구한다 → 합계의 최솟값을 갱신한다.

### 4. 핵심 코드 역할

dfs(i+1,count+1)로 같은 치킨집 재선택과 순서만 다른 중복 조합을 막는다. selected[count]는 다음 분기에서 덮어쓰므로 방문 배열이나 별도 원상복구가 필요 없다.

### 5. 예제에 직접 대입하기

```text
예제 2: houses=[(0,0),(3,3)], stores=[(0,1),(3,2)], m=2
dfs(0,0)
→ i=0: selected[0]=0, dfs(1,1)
  → i=1: selected[1]=1, dfs(2,2)
    → count==m, selected=[0,1]
    → 집(0,0)의 거리: 1,5 → minDist=1
    → 집(3,3)의 거리: 5,1 → minDist=1
    → sum=2, answer=2
→ i=1: selected[0]=1, dfs(2,1)
  → 선택할 다음 치킨집이 없어 복귀
최종 answer=2
```

### 6. 시간복잡도

시간 O(N² + Σ(k=0..m) C(C,k) + C(C,m) × H × m), 공간 O(H + C + m). C는 치킨집 수, H는 집 수. 재귀 조합 트리 방문과 조합별 거리 계산을 포함한 상한이다.

### 원본 문제와 실행 파일

백준 15686번 **치킨 배달** — [원본 링크](https://www.acmicpc.net/problem/15686)

[실행 가능한 정답 파일](answers/Main4.java) · [존재 여부 확인 기록](https://github.com/chech2/Algorithm)

## 5. 스타트 택시

### 완성된 Solution 코드

```java
import java.util.*;

class Solution5 {
    public int solution(int[][] map, int[] taxi, int[][] passengers, int fuel) {
        // 출발 행, 출발 열 순서로 먼저 정렬한다.
        Arrays.sort(passengers, (a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        });

        int r = taxi[0];
        int c = taxi[1];
        boolean[] completed = new boolean[passengers.length];

        for (int count = 0; count < passengers.length; count++) {
            int[][] dist = bfs(map, r, c);
            int selected = -1;
            int minDist = Integer.MAX_VALUE;

            for (int i = 0; i < passengers.length; i++) {
                if (completed[i]) {
                    continue;
                }
                int[] next = passengers[i];
                int nextDist = dist[next[0]][next[1]];
                if (nextDist == -1) {
                    continue;
                }

                if (nextDist < minDist) {
                    selected = i;
                    minDist = nextDist;
                }
            }

            if (selected == -1 || minDist > fuel) {
                return -1;
            }
            fuel -= minDist;

            int[] passenger = passengers[selected];
            dist = bfs(map, passenger[0], passenger[1]);
            int rideDist = dist[passenger[2]][passenger[3]];
            if (rideDist == -1 || rideDist > fuel) {
                return -1;
            }
            fuel -= rideDist;
            fuel += rideDist * 2;
            completed[selected] = true;
            r = passenger[2];
            c = passenger[3];
        }
        return fuel;
    }

    private int[][] bfs(int[][] map, int startR, int startC) {
        int n = map.length;
        int[][] dist = new int[n][n];
        for (int[] row : dist) {
            Arrays.fill(row, -1);
        }
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startR, startC});
        dist[startR][startC] = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            for (int d = 0; d < 4; d++) {
                int nextR = current[0] + dx[d];
                int nextC = current[1] + dy[d];
                if (nextR >= 0 && nextR < n && nextC >= 0 && nextC < n
                        && map[nextR][nextC] == 0 && dist[nextR][nextC] == -1) {
                    dist[nextR][nextC] = dist[current[0]][current[1]] + 1;
                    queue.offer(new int[]{nextR, nextC});
                }
            }
        }
        return dist;
    }
}
```

### 1. 사용할 알고리즘

BFS + 정렬 + 시뮬레이션

### 2. 선택 이유

지도에서 한 칸 이동 비용이 같아 최단거리는 BFS로 계산한다. 운송마다 위치·연료·미운송 승객이 변하므로 주어진 순서대로 시뮬레이션한다.

### 3. 실행 순서

처음에 승객을 출발 행·열 오름차순으로 정렬한다 → 현재 위치에서 BFS → 정렬된 순서로 승객을 검사하며 거리가 더 짧을 때만 선택을 바꾼다 → 탑승 전 이동 연료 차감 → 승객 출발지에서 BFS → 운송 가능 여부 검사 → 이동 연료 차감과 충전 → 운송 완료 표시와 택시 위치 갱신.

### 4. 핵심 코드 역할

dist의 -1이 격자 방문을 판단하므로 격자 visited는 없다. completed는 격자 방문과 다른 '승객 운송 여부'이므로 별도로 필요하다. rideDist>fuel일 때만 실패해서 정확한 연료로 도착하는 경우도 성공한다.

Arrays.sort로 위쪽·왼쪽 승객이 먼저 나오게 정렬한다. 이후 nextDist < minDist일 때만 교체하므로 거리가 같으면 먼저 선택한 승객을 유지한다. <=를 쓰면 동률인 뒤쪽 승객으로 바뀌므로 반드시 <를 쓴다. 거리는 택시 위치에 따라 바뀌므로 매 운송마다 다시 계산하고, 고정된 행·열 순서만 처음 한 번 정렬한다.

completed는 정렬 후의 인덱스를 기준으로 관리한다. Arrays.sort는 전달받은 passengers 배열의 순서를 직접 바꾼다.

### 5. 예제에 직접 대입하기

```text
예제 1: taxi=(1,1), fuel=2
출발 행·열 정렬 결과: [(0,1)→(0,0), (1,0)→(2,0)]
첫 BFS:
dist=[[2,1,2],[1,0,1],[2,1,2]]
승객0 출발(0,1), 승객1 출발(1,0): 거리 모두 1
→ i=0: 거리 1 < INF, selected=0, minDist=1
→ i=1: 거리 1 == minDist, 교체하지 않음
→ 정렬에서 먼저 나온 위쪽 승객0 선택
→ 탑승 이동: fuel=2-1=1
(0,1)에서 BFS: dist=[[1,0,1],[2,1,2],[3,2,3]]
→ 목적지(0,0)까지 rideDist=1
→ fuel=1-1+2=2, completed=[true,false], taxi=(0,0)
다음 BFS: dist=[[0,1,2],[1,2,3],[2,3,4]]
→ 승객1까지 1, fuel=2-1=1
(1,0)에서 BFS: dist=[[1,2,3],[0,1,2],[1,2,3]]
→ 목적지(2,0)까지 1
→ fuel=1-1+2=2, completed=[true,true], taxi=(2,0)
최종 2 반환
```

### 6. 시간복잡도

시간 O(M log M + M × N² + M²), 공간 O(N² + M). M은 승객 수이다. 초기 정렬은 O(M log M), 매 운송마다 BFS 2회와 승객 M명 검사를 한다.

### 원본 문제와 실행 파일

백준 19238번 **스타트 택시** — [원본 링크](https://www.acmicpc.net/problem/19238)

[실행 가능한 정답 파일](answers/Main5.java) · [존재 여부 확인 기록](https://github.com/DaAlG/code_studying)

## 출처 확인과 변환 기준

백준 원본 페이지 직접 조회는 실패하거나 정상 문제 내용을 반환하지 않았습니다. 공개 문제 저장소와 문제 기록으로 실제 번호·제목을 교차 확인했습니다. 원본 직접 열람 완료로 기록하지 않습니다.

문제의 규칙과 핵심 조건을 유지하고 입력을 solution 매개변수로, 출력을 반환값으로 변환했습니다. 원본의 여러 테스트 케이스는 메서드를 여러 번 호출하면 됩니다. 스타트링크의 원본 'use the stairs' 출력은 -1로, 스타트 택시의 원본 1부터 세는 좌표는 0부터 세는 좌표로 변환했습니다. 치킨 배달의 '최대 m개' 조건은 유지하고 정답에서 정확히 m개 조합을 검사하는 이유를 설명했습니다.

코드는 직접 작성했으며, 위 예시들은 원본 조건을 만족하는 함수 호출 데이터입니다.
