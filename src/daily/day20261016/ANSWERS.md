# 정답 및 해설 — 2026-10-16

각 문제의 코드를 먼저 제공하고 알고리즘·이유·실행 순서·핵심 코드·예제 추적·복잡도를 설명합니다.

## 1. 화살표를 따라 만나는 고리

[완성된 실행 파일](answers/Main1.java)

```java
import java.util.*;

class Solution1 {
    int[] permutation;
    boolean[] visited;

    public int solution(int[] permutation) {
        this.permutation = permutation;
        int n = permutation.length;
        visited = new boolean[n + 1];
        int count = 0;
        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                dfs(i);
                count++;
            }
        }
        return count;
    }

    private void dfs(int current) {
        visited[current] = true;
        int next = permutation[current - 1];
        if (!visited[next]) {
            dfs(next);
        }
    }
}
```

1. **알고리즘:** 재귀 DFS.
2. **이유:** 순열에서는 각 정점의 나가는 화살표와 들어오는 화살표가 각각 하나라 전체가 겹치지 않는 고리들로 나뉜다. 미방문 정점에서 탐색할 때마다 고리 하나를 센다.
3. **실행 순서:** 방문 배열 초기화 → 정점1부터 확인 → 미방문이면 화살표를 따라 DFS → count 증가.
4. **핵심 코드:** 다음 정점은 permutation[current-1]이다. 정점은1부터, 배열은0부터라 인덱스를 구분한다. 다음 위치가 하나라 인접 리스트는 필요 없다. 일반 방향 그래프 전체에 이 고리 세기 방법을 그대로 적용할 수 있는 것은 아니며, 순열이라는 조건을 사용한다.
5. **예제 추적:** [2,1,3]에서 dfs(1) → visited[1]=true → next=2 → dfs(2) → visited[2]=true → next=1은 이미 방문, 종료 → count=1. 정점2는 건너뛰고 dfs(3) → next=3은 이미 방문 → count=2.
6. **복잡도:** 시간 O(N), 방문 배열과 재귀 공간 O(N).

원본: **백준 10451 — 순열 사이클**

[원본 문제](https://www.acmicpc.net/problem/10451) · [문제 조건 대조 기록](https://loosie.tistory.com/207)

## 2. 배송에 필요한 최소 비용

[완성된 실행 파일](answers/Main2.java)

```java
import java.util.*;

class Solution2 {
    public int solution(int n, int[][] roads) {
        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            graph[road[0]].add(new int[]{road[1], road[2]});
            graph[road[1]].add(new int[]{road[0], road[2]});
        }

        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[1] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[1], b[1]));
        pq.offer(new int[]{1, 0});

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
        return dist[n];
    }
}
```

1. **알고리즘:** PriorityQueue를 사용하는 다익스트라.
2. **이유:** 이동 비용이 서로 다르고 음수가 없다. 비용0도 다익스트라에서 처리할 수 있다.
3. **실행 순서:** 양방향 그래프 → dist를 큰 값으로 초기화 → 시작점1의 거리0 → 작은 거리부터 처리 → n번 거리 반환.
4. **핵심 코드:** 큐는 {정점,거리}이며 거리 인덱스1로 비교한다. 현재 dist보다 큰 항목은 무시하고 더 짧을 때만 갱신한다. 같은 비용에서는 다시 넣지 않아 비용0인 순환도 반복하지 않는다. 최대 단순 경로 비용은 (N-1)*1,000으로 int 범위이다.
5. **예제 추적:** dist[1]=0 → 1에서 dist[2]=0,dist[3]=1 → 2에서 dist[4]=3 → 3에서 dist[4]=2로 갱신 → 나중에 {4,3}은 오래된 거리라 무시 → 결과2.
6. **복잡도:** 시간 O((V+E) log(V+E+1)), 공간 O(V+E).

원본: **백준 5972 — 택배 배송**

[원본 문제](https://www.acmicpc.net/problem/5972) · [문제 조건 대조 기록](https://wonsjung.tistory.com/475)

## 3. 사다리와 뱀을 거쳐 도착하기

[완성된 실행 파일](answers/Main3.java)

```java
import java.util.*;

class Solution3 {
    public int solution(int[][] ladders, int[][] snakes) {
        int[] move = new int[101];
        for (int[] ladder : ladders) {
            move[ladder[0]] = ladder[1];
        }
        for (int[] snake : snakes) {
            move[snake[0]] = snake[1];
        }

        int[] dist = new int[101];
        Arrays.fill(dist, -1);
        dist[1] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == 100) {
                return dist[current];
            }

            for (int dice = 1; dice <= 6; dice++) {
                int next = current + dice;
                if (next <= 100) {
                    if (move[next] != 0) {
                        next = move[next];
                    }
                    if (dist[next] == -1) {
                        dist[next] = dist[current] + 1;
                        queue.offer(next);
                    }
                }
            }
        }
        return -1;
    }
}
```

1. **알고리즘:** 주사위 한 번을 간선 하나로 보는 BFS.
2. **이유:** 주사위의 눈은 달라도 굴린 횟수의 비용은 모두1이다. 사다리·뱀 이동을 적용한 최종 위치를 다음 상태로 처리한다.
3. **실행 순서:** 강제 이동 배열 → 1번 거리0 → 주사위1~6 시도 → 사다리·뱀 반영 → 최종 칸이 미방문이면 큐 삽입 → 100번 도착 시 횟수 반환.
4. **핵심 코드:** 방문 여부는 강제 이동 후의 칸에서 판단한다. 사다리를 타도 거리 증가량은1이다. dist=-1이 미방문이라 visited는 필요 없다. next를 변환해야 하므로 범위 확인과 최종 칸 방문 확인을 단계별로 작성했다.
5. **예제 추적:** queue=[1],dist[1]=0 → 주사위1로 next=2 → move[2]=98이라 next=98 → dist[98]=1,큐 삽입 → 98에서 주사위1은99에서 뱀을 타고3으로 이동, 주사위2는100 → dist[100]=2 → 100을 꺼내2 반환.
6. **복잡도:** 칸 수 B=100, 주사위 눈 D=6에 대해 시간 O(B*D), 공간 O(B).

원본: **백준 16928 — 뱀과 사다리 게임**

[원본 문제](https://www.acmicpc.net/problem/16928) · [문제 조건 대조 기록](https://luz315.tistory.com/196?category=1393979)

## 4. 계란을 부딪쳐 최대한 깨기

[완성된 실행 파일](answers/Main4.java)

```java
import java.util.*;

class Solution4 {
    int[] durability;
    int[] weight;
    int n;
    int answer;

    public int solution(int[][] eggs) {
        n = eggs.length;
        durability = new int[n];
        weight = new int[n];
        answer = 0;
        for (int i = 0; i < n; i++) {
            durability[i] = eggs[i][0];
            weight[i] = eggs[i][1];
        }
        dfs(0);
        return answer;
    }

    private void dfs(int current) {
        if (current == n) {
            int count = 0;
            for (int value : durability) {
                if (value <= 0) {
                    count++;
                }
            }
            answer = Math.max(answer, count);
            return;
        }

        if (durability[current] <= 0) {
            dfs(current + 1);
            return;
        }

        boolean hit = false;
        for (int next = 0; next < n; next++) {
            if (next != current && durability[next] > 0) {
                hit = true;
                durability[current] -= weight[next];
                durability[next] -= weight[current];

                dfs(current + 1);

                durability[current] += weight[next];
                durability[next] += weight[current];
            }
        }
        if (!hit) {
            dfs(current + 1);
        }
    }
}
```

1. **알고리즘:** 재귀 DFS와 시뮬레이션, 내구도 복원.
2. **이유:** 매 차례 어떤 계란을 칠지에 따라 이후 깨진 상태가 달라진다. 가능한 선택마다 상태를 바꾸어 끝까지 진행하고 결과를 비교한다.
3. **실행 순서:** 내구도·무게 준비 → 첫 계란 차례 → 손의 계란이 깨졌으면 건너뜀 → 상대 선택·양쪽 내구도 감소 → 다음 차례 → 양쪽 내구도 복원 → 끝에서 깨진 수 비교.
4. **핵심 코드:** 상대는 앞의 계란도 될 수 있어 next를0부터 검사한다. 부딪칠 수 있는 상대가 있는데 임의로 건너뛰면 안 된다. hit=false일 때만 그냥 다음 차례로 간다. 다른 선택을 시험할 때 두 내구도를 모두 복원해야 한다. answer는 호출마다0으로 초기화한다.
5. **예제 추적:** [[1,1],[1,1],[1,1]]에서 dfs(0) → 상대1 선택, 내구도=[0,0,1] → dfs(1)은 깨져 있어 dfs(2)로 이동 → 계란2는 살아 있지만 다른 계란이 모두 깨져 hit=false → dfs(3)에서 깨진 수2,answer=2 → 돌아와 내구도=[1,1,1]로 복원 → 계란0의 다른 상대2도 시험한다.
6. **복잡도:** 계란 N개에 대해 느슨한 시간 상한 O(N^(N+1)), 추가 공간 O(N). 각 차례 최대 N-1개 선택과 마지막 개수 계산을 포함한 상한이며 N<=8이다.

원본: **백준 16987 — 계란으로 계란치기**

[원본 문제](https://www.acmicpc.net/problem/16987) · [문제 조건 대조 기록](https://prefer2.tistory.com/entry/백준-16987번-계란으로-계란치기-c)

## 5. 여러 벽을 부수며 도착하기

[완성된 실행 파일](answers/Main5.java)

```java
import java.util.*;

class Solution5 {
    public int solution(int[][] map, int k) {
        int n = map.length;
        int m = map[0].length;
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        // visited[부순 벽 수][행][열]
        boolean[][][] visited = new boolean[k + 1][n][m];
        Queue<int[]> queue = new LinkedList<>();
        // {행, 열, 부순 벽 수, 시작 칸을 포함한 길이}
        queue.offer(new int[]{0, 0, 0, 1});
        visited[0][0][0] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            int broken = current[2];
            int distance = current[3];
            if (row == n - 1 && col == m - 1) {
                return distance;
            }

            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
                    int nextBroken = broken + map[nr][nc];
                    if (nextBroken <= k && !visited[nextBroken][nr][nc]) {
                        visited[nextBroken][nr][nc] = true;
                        queue.offer(new int[]{nr, nc, nextBroken, distance + 1});
                    }
                }
            }
        }
        return -1;
    }
}
```

1. **알고리즘:** 부순 벽 수를 상태에 포함하는 BFS.
2. **이유:** 같은 위치라도 남은 파괴 기회에 따라 이후 갈 수 있는 곳이 달라진다. 모든 한 칸 이동의 길이 증가량은1이다.
3. **실행 순서:** 시작 상태(0,0,부순0,길이1) 큐 삽입 → 네 방향 확인 → 다음 칸이 벽이면 부순 수 증가 → 제한 이내의 미방문 상태 삽입 → 도착 상태를 꺼내 길이 반환.
4. **핵심 코드:** visited[broken][row][col]로 상태를 구분한다. 거리는 큐에만 저장해 dist와 중복하지 않는다. 지도 값을 읽어 다음 상태를 계산해야 하므로 먼저 범위를 확인하고, 계산 후 이동 가능 조건을 검사한다. 이곳에서는 억지로 긴 단일 if로 합치지 않았다. 원본은 이동 횟수가 아니라 시작 칸을 포함한 칸 수라 초기 길이가1이다.
5. **예제 추적:** [0,1,1,0],k=2 → queue=[{0,0,0,1}] → 첫 벽(0,1)로 이동해 {0,1,1,2},visited[1][0][1]=true → 두 번째 벽(0,2)로 이동해 {0,2,2,3} → 빈칸(0,3)로 이동해 {0,3,2,4} → 도착해4 반환. k=1이면 두 번째 벽으로 들어갈 때 nextBroken=2>1이라 넣지 못해 -1이다.
6. **복잡도:** 시간·공간 O((K+1)*N*M).

원본: **백준 14442 — 벽 부수고 이동하기 2**

[원본 문제](https://www.acmicpc.net/problem/14442) · [문제 조건 대조 기록](https://programmingiraffe.tistory.com/146)

## 원본 확인과 형식 변환

백준 직접 조회에서는 정상 문제 본문을 얻지 못했습니다. 각 문제의 공개 기록을 대조해 실재 여부와 번호·제목·조건을 확인했습니다. 원문 직접 대조 완료라고 주장하지 않습니다. 확인 출처는 문제별 링크에 남겼습니다.

원본의 입력을 매개변수로, 출력을 반환값으로 바꿨습니다. 1번은 테스트 케이스별로 호출합니다. 2번의 비용0과 양방향·중복 도로, 3번의 강제 이동과 주사위 횟수, 4번의 반드시 충돌할 조건과 양쪽 상태 변경, 5번의 시작 칸을 포함한 길이와 최대K개 파괴 조건을 유지했습니다.

5번은 기존 벽 부수고 이동하기(2206)의 반복 출제가 아니라, 최대K개를 처리하는 별도 실제 문제14442입니다. 최근 연습한 좌표+추가 자원 상태를 확장합니다. 4번은 일반 구현만 하는 문제가 아니라 재귀로 모든 상대 선택을 시도하고 시뮬레이션 상태를 복원하는 연습입니다.

