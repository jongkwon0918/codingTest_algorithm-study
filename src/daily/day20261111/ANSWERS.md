# 2026-11-11 정답 및 해설

이 파일은 정답 영역입니다. 문제를 먼저 풀고 읽으세요.

검증 범위: 오늘 다섯 문제의 백준 직접 조회에서는 정상 본문을 얻지 못했습니다(안내 페이지 또는 조회 오류). 각 문제 아래 공개된 문제별 기록으로 실재 번호·제목·조건·제한을 대조했습니다. 원문을 직접 대조 완료했다고 주장하지 않습니다. 변환 예제에는 직접 만든 테스트 입력도 포함하며 문제 규칙은 원본을 유지했습니다.

## 1. 가장 큰 덩어리

[완성된 실행 파일](answers/Main1.java)

### 완성된 Solution

```java
import java.util.*;

class Solution1 {
    public int solution(int n, int m, int[][] waste) {
        boolean[][] map = new boolean[n][m];
        boolean[][] visited = new boolean[n][m];
        for (int[] point : waste) {
            map[point[0] - 1][point[1] - 1] = true;
        }
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        int answer = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (map[r][c] && !visited[r][c]) {
                    Queue<int[]> queue = new LinkedList<>();
                    queue.offer(new int[]{r, c});
                    visited[r][c] = true;
                    int count = 0;
                    while (!queue.isEmpty()) {
                        int[] current = queue.poll();
                        count++;
                        for (int d = 0; d < 4; d++) {
                            int nr = current[0] + dr[d];
                            int nc = current[1] + dc[d];
                            if (nr >= 0 && nr < n && nc >= 0 && nc < m
                                    && map[nr][nc] && !visited[nr][nc]) {
                                visited[nr][nc] = true;
                                queue.offer(new int[]{nr, nc});
                            }
                        }
                    }
                    answer = Math.max(answer, count);
                }
            }
        }
        return answer;
    }
}
```

### 핵심 풀이와 예제 추적

1. 알고리즘: 영역별 BFS.

2. 이유: 한 칸에서 연결된 음식물을 모두 모으면 덩어리 하나의 크기를 구할 수 있다. 최대 10000칸이 길게 이어질 수 있어 재귀 깊이를 피하는 큐를 사용했다.

3. 실행 순서: 음식물 표시 → 미방문 음식물에서 BFS → 크기 최댓값 갱신.

4. 핵심: 큐에 넣을 때 visited를 표시해 한 칸을 정확히 한 번 센다. 거리 정보는 필요 없다.

5. 예제 추적(0기반): (0,0)을 꺼내 count=1로 종료한다. 다음 (1,1)에서 시작하면 (2,1),(1,2)가 큐에 들어간다. (2,1)에서 (2,0)을 추가하여 총 count=4다. answer는 1에서 4가 된다.

6. 시간복잡도 O(n*m), 추가 공간 O(n*m).

원본: **BOJ 1743 음식물 피하기** — [백준 문제](https://www.acmicpc.net/problem/1743)

대조 출처: [문제별 공개 기록](https://chaesilver.tistory.com/65).

---

## 2. 불길보다 먼저 건물 밖으로

[완성된 실행 파일](answers/Main2.java)

### 완성된 Solution

```java
import java.util.*;

class Solution2 {
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public String solution(String[] building) {
        int n = building.length;
        int m = building[0].length();
        int[][] fireTime = new int[n][m];
        int[][] dist = new int[n][m];
        Queue<int[]> fire = new LinkedList<>();
        Queue<int[]> queue = new LinkedList<>();
        for (int r = 0; r < n; r++) {
            Arrays.fill(fireTime[r], -1);
            Arrays.fill(dist[r], -1);
        }
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (building[r].charAt(c) == '*') {
                    fireTime[r][c] = 0;
                    fire.offer(new int[]{r, c});
                } else if (building[r].charAt(c) == '@') {
                    dist[r][c] = 0;
                    queue.offer(new int[]{r, c});
                }
            }
        }
        while (!fire.isEmpty()) {
            int[] current = fire.poll();
            int r = current[0];
            int c = current[1];
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && building[nr].charAt(nc) != '#' && fireTime[nr][nc] == -1) {
                    fireTime[nr][nc] = fireTime[r][c] + 1;
                    fire.offer(new int[]{nr, nc});
                }
            }
        }
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                int nextTime = dist[r][c] + 1;
                if (nr < 0 || nr >= n || nc < 0 || nc >= m) {
                    return String.valueOf(nextTime);
                }
                if (building[nr].charAt(nc) != '#' && dist[nr][nc] == -1
                        && (fireTime[nr][nc] == -1 || nextTime < fireTime[nr][nc])) {
                    dist[nr][nc] = nextTime;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return "IMPOSSIBLE";
    }
}
```

### 핵심 풀이와 예제 추적

1. 알고리즘: 불의 다중 시작점 BFS와 사람의 BFS.

2. 이유: 이동 시간이 모두 1초이고, 안전한 이동 여부는 목적지에 불이 도착하는 시간과 비교할 수 있다.

3. 실행 순서: 모든 불을 거리 0으로 큐에 넣기 → fireTime 계산 → 사람의 dist 계산 → 바깥 이동에서 반환.

4. 핵심: nextTime < fireTime이어야 한다. 두 배열은 각각 불과 사람의 도착 시간이므로 중복 방문 배열이 아니다. 현재 칸에 다음 초 불이 온다고 사람을 중단시키면 안 된다.

5. 예제 추적: 첫 지도에서 dist[1][1]=0. 큐는 (1,2), (1,3) 순으로 진행하고 거리는 1,2다. (1,3)에서 오른쪽 밖으로 이동하여 3 반환. 마지막 지도에서는 (1,2)에 사람과 불 모두 1초에 도착하므로 큐에 넣지 못한다.

6. 시간복잡도 O(높이*너비), 공간 O(높이*너비). 기존 불 탈출 문제와 유사한 규칙을 확인하는 중급 복습이며 새 상태 설계를 요구하는 문제는 아니다.

원본: **BOJ 5427 불** — [백준 문제](https://www.acmicpc.net/problem/5427)

대조 출처: [문제별 공개 기록](https://196code-gray.tistory.com/40).

---

## 3. 두 가지 불편함을 피하는 산책

[완성된 실행 파일](answers/Main3.java)

### 완성된 Solution

```java
import java.util.*;

class Solution3 {
    public int[] solution(String[] forest) {
        int n = forest.length;
        int m = forest[0].length();
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        boolean[][] near = new boolean[n][m];
        int[][] garbageDist = new int[n][m];
        int[][] nearDist = new int[n][m];
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            if (a[2] != b[2]) {
                return Integer.compare(a[2], b[2]);
            }
            return Integer.compare(a[3], b[3]);
        });
        for (int r = 0; r < n; r++) {
            Arrays.fill(garbageDist[r], Integer.MAX_VALUE);
            Arrays.fill(nearDist[r], Integer.MAX_VALUE);
            for (int c = 0; c < m; c++) {
                if (forest[r].charAt(c) == '.') {
                    for (int d = 0; d < 4; d++) {
                        int nr = r + dr[d];
                        int nc = c + dc[d];
                        if (nr >= 0 && nr < n && nc >= 0 && nc < m
                                && forest[nr].charAt(nc) == 'g') {
                            near[r][c] = true;
                        }
                    }
                }
            }
        }
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (forest[r].charAt(c) == 'S') {
                    garbageDist[r][c] = 0;
                    nearDist[r][c] = 0;
                    pq.offer(new int[]{r, c, 0, 0});
                }
            }
        }
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int r = current[0];
            int c = current[1];
            int garbage = current[2];
            int nearby = current[3];
            if (garbage > garbageDist[r][c]
                    || (garbage == garbageDist[r][c] && nearby > nearDist[r][c])) {
                continue;
            }
            if (forest[r].charAt(c) == 'F') {
                return new int[]{garbage, nearby};
            }
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
                    int nextGarbage = garbage + (forest[nr].charAt(nc) == 'g' ? 1 : 0);
                    int nextNear = nearby + (near[nr][nc] ? 1 : 0);
                    if (nextGarbage < garbageDist[nr][nc]
                            || (nextGarbage == garbageDist[nr][nc] && nextNear < nearDist[nr][nc])) {
                        garbageDist[nr][nc] = nextGarbage;
                        nearDist[nr][nc] = nextNear;
                        pq.offer(new int[]{nr, nc, nextGarbage, nextNear});
                    }
                }
            }
        }
        throw new IllegalStateException("도착점에 도달할 수 없습니다.");
    }
}
```

### 핵심 풀이와 예제 추적

1. 알고리즘: 두 비용을 순서대로 비교하는 다익스트라.

2. 이유: 쓰레기 수를 우선 최소화하고, 그 값이 같을 때만 인접 빈칸 수를 비교한다. 이동 횟수만 비교하는 BFS로는 우선순위를 표현하지 못한다.

3. 실행 순서: '.'의 쓰레기 인접 여부 표시 → 두 거리 배열 초기화 → S에서 우선순위 큐 탐색 → F가 나오면 반환.

4. 핵심: PQ와 거리 갱신에서 같은 사전식 비교를 사용한다. 두 거리 배열은 하나의 비용 쌍이며 두 값을 따로 최소화하면 안 된다. S,F,g에는 near를 표시하지 않는다.

5. 예제 추적: 세 번째 지도에서 S의 비용=(0,0). 오른쪽 '.'에 가면 (0,1), 아래 g로 가면 (1,0)이다. PQ는 (0,1)을 먼저 꺼낸다. 이어 F는 추가 비용이 없어 (0,1)로 종료한다.

6. 시간복잡도 O(n*m*log(n*m)), 공간 O(n*m).

원본: **BOJ 1445 일요일 아침의 데이트** — [백준 문제](https://www.acmicpc.net/problem/1445)

대조 출처: [문제별 공개 기록](https://junseok.tistory.com/520), [추가 규칙 확인](https://thinking-face.tistory.com/300).

---

## 4. 세 명이 동시에 지키는 성

[완성된 실행 파일](answers/Main4.java)

### 완성된 Solution

```java
import java.util.*;

class Solution4 {
    int n;
    int m;
    int distance;
    int answer;
    int[] choice;
    int[][] board;

    public int solution(int[][] board, int distance) {
        this.board = board;
        this.distance = distance;
        n = board.length;
        m = board[0].length;
        answer = 0;
        choice = new int[3];
        dfs(0, 0);
        return answer;
    }

    private void dfs(int start, int count) {
        if (count == 3) {
            answer = Math.max(answer, simulate());
            return;
        }
        for (int col = start; col < m; col++) {
            choice[count] = col;
            dfs(col + 1, count + 1);
        }
    }

    private int simulate() {
        int[][] map = new int[n][m];
        for (int r = 0; r < n; r++) {
            map[r] = board[r].clone();
        }
        int count = 0;
        for (int turn = 0; turn < n; turn++) {
            boolean[][] targets = new boolean[n][m];
            for (int archerCol : choice) {
                // {행, 열, 거리}: 먼저 거리, 같으면 왼쪽을 선택한다.
                List<int[]> candidates = new ArrayList<>();
                for (int r = 0; r < n; r++) {
                    for (int c = 0; c < m; c++) {
                        int currentDistance = n - r + Math.abs(archerCol - c);
                        if (map[r][c] == 1 && currentDistance <= distance) {
                            candidates.add(new int[]{r, c, currentDistance});
                        }
                    }
                }
                candidates.sort((a, b) -> {
                    if (a[2] != b[2]) {
                        return Integer.compare(a[2], b[2]);
                    }
                    return Integer.compare(a[1], b[1]);
                });
                if (!candidates.isEmpty()) {
                    int[] target = candidates.get(0);
                    targets[target[0]][target[1]] = true;
                }
            }
            // 모든 궁수가 고른 뒤 한꺼번에 삭제한다.
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < m; c++) {
                    if (targets[r][c]) {
                        map[r][c] = 0;
                        count++;
                    }
                }
            }
            for (int r = n - 1; r >= 1; r--) {
                map[r] = map[r - 1].clone();
            }
            Arrays.fill(map[0], 0);
        }
        return count;
    }
}
```

### 핵심 풀이와 예제 추적

1. 알고리즘: 재귀 DFS 조합 + 시뮬레이션 + 대상 정렬.

2. 이유: 서로 다른 열 3개를 고르면 이후 게임 진행은 정해진다. m<=15이므로 모든 배치를 시도할 수 있다.

3. 실행 순서: DFS로 열 3개 선택 → 원본 지도 복사 → 궁수별 대상 선택 → 동시 제거 → 적 이동 → 최대 제거 수 갱신.

4. 핵심: targets에 표시만 하고 궁수 3명이 모두 선택한 뒤 지운다. 같은 적은 true 한 번으로 처리한다. start=col+1은 순서만 다른 같은 배치를 막는다.

5. 예제 추적: distance=2, choice=[0,2,4]이면 첫 턴에 (3,0),(3,2),(3,4)를 표시한다. 제거 수=3. 남은 적은 (4,1),(4,3)으로 내려온다. 다음 턴에 이 두 적을 중복 없이 제거해 최종5다. distance=1이면 첫 턴 공격할 수 없고 두 번째 턴에3마리만 제거한다.

6. 시간복잡도 O(C(m,3)*n*(n*m)*log(n*m)), 공간 O(n*m).

원본: **BOJ 17135 캐슬 디펜스** — [백준 문제](https://www.acmicpc.net/problem/17135)

대조 출처: [문제별 공개 기록](https://sujin7837.tistory.com/344), [추가 규칙 확인](https://dailyheumsi.tistory.com/66?category=811453).

---

## 5. 구슬을 지우고 묶음을 바꾸는 마법

[완성된 실행 파일](answers/Main5.java)

### 완성된 Solution

```java
import java.util.*;

class Solution5 {
    int n;
    int[][] index;
    int[] beads;
    int score;

    public int solution(int[][] board, int[][] spells) {
        n = board.length;
        index = new int[n][n];
        beads = new int[n * n - 1];
        score = 0;
        makeOrder(board);
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        for (int[] spell : spells) {
            int direction = spell[0] - 1;
            for (int step = 1; step <= spell[1]; step++) {
                int r = n / 2 + dr[direction] * step;
                int c = n / 2 + dc[direction] * step;
                beads[index[r][c]] = 0;
            }
            compact();
            while (explode()) {
                compact();
            }
            transform();
        }
        return score;
    }

    private void makeOrder(int[][] board) {
        int[] dr = {0, 1, 0, -1};
        int[] dc = {-1, 0, 1, 0};
        int r = n / 2;
        int c = n / 2;
        int direction = 0;
        int length = 1;
        int count = 0;
        while (count < beads.length) {
            for (int repeat = 0; repeat < 2; repeat++) {
                for (int step = 0; step < length; step++) {
                    r += dr[direction];
                    c += dc[direction];
                    index[r][c] = count;
                    beads[count] = board[r][c];
                    count++;
                    if (count == beads.length) {
                        return;
                    }
                }
                direction = (direction + 1) % 4;
            }
            length++;
        }
    }

    private void compact() {
        int[] next = new int[beads.length];
        int count = 0;
        for (int bead : beads) {
            if (bead != 0) {
                next[count++] = bead;
            }
        }
        beads = next;
    }

    private boolean explode() {
        boolean removed = false;
        int start = 0;
        while (start < beads.length && beads[start] != 0) {
            int end = start + 1;
            while (end < beads.length && beads[end] == beads[start]) {
                end++;
            }
            int count = end - start;
            if (count >= 4) {
                score += beads[start] * count;
                Arrays.fill(beads, start, end, 0);
                removed = true;
            }
            start = end;
        }
        return removed;
    }

    private void transform() {
        int[] next = new int[beads.length];
        int count = 0;
        int start = 0;
        while (start < beads.length && beads[start] != 0 && count < next.length) {
            int end = start + 1;
            while (end < beads.length && beads[end] == beads[start]) {
                end++;
            }
            next[count++] = end - start;
            if (count < next.length) {
                next[count++] = beads[start];
            }
            start = end;
        }
        beads = next;
    }
}
```

### 핵심 풀이와 예제 추적

1. 알고리즘: 배열을 사용하는 단계별 시뮬레이션. DP는 필요 없다.

2. 이유: 마법 순서가 고정되어 있고, 구슬의 연속 여부는 나선형 순서에서 판단한다.

3. 실행 순서: 칸 좌표→순번 대응 만들기 → 파괴 → 압축 → 폭발과 압축 반복 → 묶음 변환.

4. 핵심: 각 단계의 다음 배열을 따로 만든다. 폭발에서는 같은 단계의 모든 묶음을 표시한 다음 compact한다. 직접 파괴에는 점수를 더하지 않는다. index는 칸 순번이며 beads의 값은 구슬 번호다.

5. 예제 추적: 3*3 칸 순번은 {{8,7,6},{1,상어,5},{2,3,4}}(설명용1기반). 내부 index는 이보다1 작다. 아래는 index2이므로 값2를0으로 만든다. 압축 후 [1,1,1,1,2,2,2,0], 폭발하면 score=4, 압축 후 [2,2,2,0,...], 변환 후 [3,2,0,...]다.

6. K=n*n-1, 마법 수 M일 때 한 번의 압축·폭발·변환은 O(K). 폭발 반복은 최악 O(K)번이므로 전체 O(M*K*K), 추가 공간 O(K). 실제로 폭발마다 최소4개가 사라진다.

원본: **BOJ 21611 마법사 상어와 블리자드** — [백준 문제](https://www.acmicpc.net/problem/21611)

대조 출처: [문제별 공개 기록](https://blog.yjyoon.dev/boj/2022/01/04/boj-21611/), [추가 규칙 확인](https://kimjingo.tistory.com/171).

