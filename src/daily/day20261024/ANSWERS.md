# 2026-10-24 정답 및 해설

문제를 먼저 풀고 확인하세요. 완성 코드 다음에 알고리즘·선택 이유·실행 순서·핵심 코드·예제 추적·복잡도를 설명합니다.

## 1. 조건을 만족하는 숫자 선택

[완성된 실행 파일](answers/Main1.java)

```java
import java.util.*;

class Solution1 {
    int[] numbers;
    int target;
    int answer;

    public int solution(int[] numbers, int target) {
        this.numbers = numbers;
        this.target = target;
        answer = 0;
        dfs(0, 0, 0);
        return answer;
    }

    private void dfs(int index, int sum, int selected) {
        if (index == numbers.length) {
            if (selected > 0 && sum == target) {
                answer++;
            }
            return;
        }

        // 현재 원소를 고르는 선택
        dfs(index + 1, sum + numbers[index], selected + 1);
        // 현재 원소를 고르지 않는 선택
        dfs(index + 1, sum, selected);
    }
}
```

1. **알고리즘:** 재귀 DFS로 선택/미선택을 완전탐색한다.
2. **이유:** 최대20개라 모든 부분 선택 2^20개를 검사할 수 있다. 음수가 있어 현재 합이 목표를 넘었다고 중단하면 안 된다.
3. **실행 순서:** 현재 원소 포함 → 다음 인덱스 → 현재 원소 미포함 → 다음 인덱스 → 끝에서 합과 공집합 여부 검사.
4. **핵심 코드:** index는 매번1씩 증가하므로 visited가 필요 없다. selected는 합이0인 비어 있지 않은 선택과 공집합을 구분한다. 값이 같아도 인덱스별로 분기하므로 중복 제거 Set을 쓰면 안 된다. 인자를 값으로 전달해 별도 복원이 없다.
5. **예제 추적:** {0,0}에서 dfs(0,0,0) → 첫째 선택 dfs(1,0,1) → 둘째 선택 dfs(2,0,2): answer1 → 둘째 미선택 dfs(2,0,1): answer2. 첫째 미선택 분기에서 둘째 선택이면 answer3, 둘 다 미선택이면 selected0이라 제외한다.
6. **복잡도:** 시간 O(2^N), 재귀 공간 O(N). 최대 정답은 2^20-1이고 합의 절댓값은 최대2,000,000이라 int로 충분하다.

원본: **백준 1182 — 부분수열의 합** (실버 2, 조회한 공개 기록 기준)

[원본 문제](https://www.acmicpc.net/problem/1182) · [문제 조건 대조 기록](https://ivelopalways.tistory.com/entry/BaekJoon-백트래킹-백준-1182번-문제-부분수열의-합)

## 2. 가장 멀리 떨어진 두 육지

[완성된 실행 파일](answers/Main2.java)

```java
import java.util.*;

class Solution2 {
    int n;
    int m;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(String[] island) {
        n = island.length;
        m = island[0].length();
        int answer = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if (island[row].charAt(col) == 'L') {
                    answer = Math.max(answer, bfs(row, col, island));
                }
            }
        }
        return answer;
    }

    private int bfs(int startRow, int startCol, String[] island) {
        int[][] dist = new int[n][m];
        for (int row = 0; row < n; row++) {
            Arrays.fill(dist[row], -1);
        }
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});
        dist[startRow][startCol] = 0;
        int farthest = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            farthest = Math.max(farthest, dist[row][col]);

            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && island[nr].charAt(nc) == 'L' && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[row][col] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return farthest;
    }
}
```

1. **알고리즘:** 모든 육지를 출발점으로 하는 BFS.
2. **이유:** 한 출발점에서 BFS를 하면 그 점과 다른 육지 사이의 최단거리가 나온다. 그 최단거리의 최댓값을 모든 출발점에서 비교한다.
3. **실행 순서:** 육지 시작점 선택 → dist=-1 초기화 → BFS로 모든 도달 가능한 육지 거리 계산 → 최대 거리 반환 → 전체 최댓값 갱신.
4. **핵심 코드:** 출발점마다 dist를 새로 만든다. dist로 방문 여부를 판단하고 큐에는 좌표만 넣는다. 이동할 수 없는 다른 섬은 거리 후보에 넣지 않는다. 임의 출발점에서 두 번만 BFS하는 방식은 일반적인 격자 그래프에서 항상 성립하지 않으므로 모든 시작점을 검사한다.
5. **예제 추적:** 고리 지도에서 (0,0)의 거리0 → (1,0),(0,1)은1 → (2,0),(0,2)는2 → (2,1),(1,2)는3 → (2,2)는4. 이 출발점의 최대 최단거리는4다. 이미 방문한 시작점으로 돌아가 거리를8로 늘리지 않는다. 모든 출발점에서 비교해도 답은4다.
6. **복잡도:** 시간 O((NM)^2), 공간 O(NM). 최대50*50에서 전체 시작점 BFS로 충분하다.

원본: **백준 2589 — 보물섬** (골드 5, 조회한 공개 기록 기준)

[원본 문제](https://www.acmicpc.net/problem/2589) · [문제 조건 대조 기록](https://loosie.tistory.com/216)

## 3. 최소 비용과 실제 이동 경로

[완성된 실행 파일](answers/Main3.java)

```java
import java.util.*;

class Solution3 {
    public int[][] solution(int n, int[][] buses, int start, int target) {
        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] bus : buses) {
            graph[bus[0]].add(new int[]{bus[1], bus[2]});
        }

        int[] dist = new int[n + 1];
        int[] parent = new int[n + 1];
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
            if (currentNode == target) {
                break;
            }

            for (int[] next : graph[currentNode]) {
                int nextNode = next[0];
                int nextCost = currentCost + next[1];
                if (nextCost < dist[nextNode]) {
                    dist[nextNode] = nextCost;
                    parent[nextNode] = currentNode;
                    pq.offer(new int[]{nextNode, nextCost});
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        int current = target;
        while (current != 0) {
            path.add(current);
            current = parent[current];
        }
        Collections.reverse(path);
        int[] route = new int[path.size()];
        for (int i = 0; i < path.size(); i++) {
            route[i] = path.get(i);
        }
        return new int[][]{{dist[target], route.length}, route};
    }
}
```

1. **알고리즘:** 다익스트라 + 이전 정점 배열로 경로 복원.
2. **이유:** 비용이0 이상이라 다익스트라가 가능하고, 최소 비용뿐 아니라 실제 경로를 요구한다.
3. **실행 순서:** 단방향 그래프 → dist 초기화 → 비용 갱신 시 parent 기록 → 목표 최소 비용 확정 → 목표에서 parent를 역추적 → 뒤집어서 반환.
4. **핵심 코드:** parent[nextNode]=currentNode는 더 싼 경로를 찾을 때만 갱신한다. 비용이 같을 때 부모를 바꾸지 않아 비용0 순환에서도 불필요한 부모 순환을 만들지 않는다. parent는 방문 배열이 아니라 경로 복원 정보이며 시작점의 부모0이 종료 표시다. 출발=목표라면 비용0, 도시1개다.
5. **예제 추적:** 첫 예제에서 1 처리 후 dist[2]=2,parent[2]=1 및 dist[3]=1,parent[3]=1. 3에서 4를9로 갱신했다가 2에서 4를5로 갱신하며 parent[4]=2로 바꾼다. 역추적4→2→1→0, 뒤집으면[1,2,4]. 비용5, 도시 수3이다.
6. **복잡도:** 정점 N, 버스 E개에 대해 시간 O(N+E log(E+1)), 공간 O(N+E). 최단 단순 경로 비용은 최대999×99,999로 int 범위 안이다.

원본: **백준 11779 — 최소비용 구하기 2** (골드 3, 조회한 공개 기록 기준)

[원본 문제](https://www.acmicpc.net/problem/11779) · [문제 조건 대조 기록](https://jjunsu.tistory.com/267)

## 4. 카메라 방향으로 사각지대 줄이기

[완성된 실행 파일](answers/Main4.java)

```java
import java.util.*;

class Solution4 {
    int n;
    int m;
    int[][] office;
    int[][] covered;
    List<int[]> cameras;
    int answer;
    // 위, 오른쪽, 아래, 왼쪽 순서
    int[] dr = {-1, 0, 1, 0};
    int[] dc = {0, 1, 0, -1};
    int[][] directions = {
        {}, {0}, {0,2}, {0,1}, {0,1,2}, {0,1,2,3}
    };
    int[] rotations = {0, 4, 2, 4, 4, 1};

    public int solution(int[][] office) {
        this.office = office;
        n = office.length;
        m = office[0].length;
        covered = new int[n][m];
        cameras = new ArrayList<>();
        answer = n * m;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if (office[row][col] >= 1 && office[row][col] <= 5) {
                    cameras.add(new int[]{row, col, office[row][col]});
                }
            }
        }
        dfs(0);
        return answer;
    }

    private void dfs(int index) {
        if (index == cameras.size()) {
            int count = 0;
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < m; col++) {
                    if (office[row][col] == 0 && covered[row][col] == 0) {
                        count++;
                    }
                }
            }
            answer = Math.min(answer, count);
            return;
        }

        int[] camera = cameras.get(index);
        int row = camera[0];
        int col = camera[1];
        int type = camera[2];
        for (int rotation = 0; rotation < rotations[type]; rotation++) {
            for (int direction : directions[type]) {
                watch(row, col, (direction + rotation) % 4, 1);
            }
            dfs(index + 1);
            for (int direction : directions[type]) {
                watch(row, col, (direction + rotation) % 4, -1);
            }
        }
    }

    private void watch(int row, int col, int direction, int change) {
        int nr = row + dr[direction];
        int nc = col + dc[direction];
        while (nr >= 0 && nr < n && nc >= 0 && nc < m) {
            if (office[nr][nc] == 6) {
                break;
            }
            if (office[nr][nc] == 0) {
                covered[nr][nc] += change;
            }
            nr += dr[direction];
            nc += dc[direction];
        }
    }
}
```

1. **알고리즘:** 카메라별 방향 조합 DFS + 직선 감시 시뮬레이션 + 백트래킹.
2. **이유:** 어떤 방향이 유리한지는 다른 카메라와의 중복에 따라 달라진다. 최대8대라 모든 방향 조합을 검사할 수 있다.
3. **실행 순서:** 카메라 목록 생성 → 한 카메라 회전 선택 → 감시 수+1 → 다음 카메라 재귀 → 같은 시선에서 감시 수-1 → 끝에서 사각지대 계산.
4. **핵심 코드:** directions[type]은 회전 전 감시 방향이고 (direction+rotation)%4로 함께 회전한다. covered는 현재 몇 카메라가 보는지 나타낸다. boolean을 단순 false로 복원하면 다른 카메라의 감시까지 지워져 횟수를 더하고 빼는 방식으로 복원한다. 벽만 시선을 막고 카메라는 통과한다. 2번은 두 회전,5번은 한 회전만으로 모든 다른 모양을 검사한다.
5. **예제 추적:** {{1,0,1}}에서 왼쪽 카메라를 오른쪽으로 두면 가운데 covered=1. 오른쪽을 왼쪽으로 두면2가 된다. 모든 방향 결정 후 사각지대0. 오른쪽 선택을 복원하면2→1이므로 왼쪽 카메라 감시는 유지된다. 두 카메라의 방향 조합을 모두 검사해 최솟값0을 얻는다. 가운데5번 예제는 상하좌우만 보므로 모서리4칸이 사각지대다.
6. **복잡도:** 카메라 K대, 최대 변 길이 D에 대해 시간 O(4^K×(NM+KD))의 느슨한 상한, 공간 O(NM+K). 실제로2번과5번은 분기가 더 적다.

원본: **백준 15683 — 감시** (골드 3, 조회한 공개 기록 기준)

[원본 문제](https://www.acmicpc.net/problem/15683) · [문제 조건 대조 기록](https://duxpetal.tistory.com/127)

## 5. 같은 색을 지우고 아래로 떨어뜨리기

[완성된 실행 파일](answers/Main5.java)

```java
import java.util.*;

class Solution5 {
    char[][] board;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(String[] field) {
        board = new char[12][6];
        for (int row = 0; row < 12; row++) {
            board[row] = field[row].toCharArray();
        }
        int count = 0;
        while (removeGroups()) {
            count++;
            fall();
        }
        return count;
    }

    private boolean removeGroups() {
        boolean[][] visited = new boolean[12][6];
        List<int[]> remove = new ArrayList<>();
        for (int row = 0; row < 12; row++) {
            for (int col = 0; col < 6; col++) {
                if (board[row][col] != '.' && !visited[row][col]) {
                    List<int[]> group = bfs(row, col, visited);
                    if (group.size() >= 4) {
                        remove.addAll(group);
                    }
                }
            }
        }
        for (int[] position : remove) {
            board[position[0]][position[1]] = '.';
        }
        return !remove.isEmpty();
    }

    private List<int[]> bfs(int startRow, int startCol, boolean[][] visited) {
        char color = board[startRow][startCol];
        List<int[]> group = new ArrayList<>();
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            group.add(current);
            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < 12 && nc >= 0 && nc < 6
                        && board[nr][nc] == color && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return group;
    }

    private void fall() {
        for (int col = 0; col < 6; col++) {
            int nextRow = 11;
            for (int row = 11; row >= 0; row--) {
                if (board[row][col] != '.') {
                    board[nextRow][col] = board[row][col];
                    nextRow--;
                }
            }
            while (nextRow >= 0) {
                board[nextRow][col] = '.';
                nextRow--;
            }
        }
    }
}
```

1. **알고리즘:** BFS 영역 탐색 + 동시 제거 + 중력 시뮬레이션.
2. **이유:** 연결된 같은 색의 그룹 크기를 구해야 하고, 제거와 낙하가 다음 탐색 결과를 바꾼다. 각 단계의 순서를 지켜 반복해야 한다.
3. **실행 순서:** 현재 판의 모든 그룹 탐색 → 크기4 이상 그룹을 제거 목록에 모으기 → 한꺼번에 제거 → 연쇄1 증가 → 모든 열 낙하 → 다음 판 검사.
4. **핵심 코드:** visited는 한 번의 전체 판 검사 동안 공유하고 다음 회차에는 새로 만든다. 그룹 좌표 목록과 제거 목록은 각각 그룹 크기 판단과 동시 제거에 필요하다. 그룹을 찾자마자 낙하시키면 같은 회차의 판이 바뀌어 틀린다. fall은 각 열을 아래부터 읽어 아래 빈자리부터 채우므로 남은 블록 순서가 유지된다.
5. **예제 추적:** simultaneous 예제는 빨강 그룹4개와 초록 그룹4개를 모두 remove에 모아8칸을 지운다. count는 그룹 수2가 아니라 회차 수1이 된다. 마지막 예제는 빨강4개 제거 후 노랑이 아래로 모임 → 노랑4개 제거 후 초록이 모임 → 초록4개 제거로 총3연쇄다. 매회 visited를 새로 만들어 낙하한 블록의 새 연결을 확인한다.
6. **복잡도:** 일반화한 R*C 판과 연쇄 T회에 대해 시간 O((T+1)RC), 공간 O(RC). 한 번 터질 때 최소4개가 사라져 T<=RC/4이며 실제 입력은12*6으로 고정되어 있다.

원본: **백준 11559 — Puyo Puyo** (골드 4, 조회한 공개 기록 기준)

[원본 문제](https://www.acmicpc.net/problem/11559) · [문제 조건 대조 기록](https://chanhuiseok.github.io/posts/baek-12/)

## 확인 범위와 형식 변환

백준 직접 조회에서는 정상 문제 본문을 얻지 못했습니다. 공개된 문제별 기록으로 실재 번호·제목·제한·핵심 조건을 대조했으며 각 절에 출처를 남겼습니다. 원문 직접 대조 완료라고 주장하지 않습니다. 난이도 등급은 공개 기록 기준이며 기업 시험과 정확히 일치한다는 뜻이 아닙니다.

입출력만 solution 매개변수와 반환값으로 바꿨습니다. 3번은 원본의 최소 비용·도시 수·경로를 모두 유지해 {{비용,도시수},{경로}}로 반환합니다. 최소 경로가 여러 개이면 임의의 유효한 최소 경로를 인정합니다. 4번은 벽과 카메라 통과 조건,5번은 동시 제거와 제거 후 낙하를 유지했습니다. 일부 예제는 원본 조건에 맞춘 연습용 데이터이며 모두 공식 예제라고 주장하지 않습니다.

재귀 DFS·Queue+LinkedList·PriorityQueue를 사용하며 Deque·ArrayDeque·Stream·별도 Node 클래스는 사용하지 않습니다. 1번의 선택/미선택,3번의 비용과 경로,4번의 감시 중복과 복원,5번의 같은 회차 동시 처리를 중점 연습합니다.

