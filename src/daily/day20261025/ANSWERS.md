# 2026-10-25 정답 및 해설

풀어본 뒤 확인하세요. 완성 코드 뒤에 알고리즘·선택 이유·실행 순서·핵심 코드·예제 추적·복잡도를 설명합니다.

## 1. 여섯 개를 고르는 모든 방법

[완성된 실행 파일](answers/Main1.java)

```java
import java.util.*;

class Solution1 {
    int[] numbers;
    int[] choice;
    List<int[]> answer;

    public int[][] solution(int[] numbers) {
        this.numbers = numbers;
        choice = new int[6];
        answer = new ArrayList<>();
        dfs(0, 0);

        int[][] result = new int[answer.size()][6];
        for (int i = 0; i < answer.size(); i++) {
            result[i] = answer.get(i);
        }
        return result;
    }

    private void dfs(int start, int count) {
        if (count == 6) {
            // 다음 선택이 choice를 덮어쓰므로 완성된 값은 복사해 저장한다.
            answer.add(choice.clone());
            return;
        }
        for (int i = start; i < numbers.length; i++) {
            choice[count] = numbers[i];
            dfs(i + 1, count + 1);
        }
    }
}
```

1. **알고리즘:** 재귀 DFS로 여섯 개를 고르는 조합을 생성한다.
2. **이유:** 최대12개 중6개를 고르는 모든 결과를 요구한다. 선택한 인덱스 다음부터만 탐색하면 중복 없는 조합이 된다.
3. **실행 순서:** 현재 자리 숫자 선택 → 다음 시작 인덱스를 i+1로 전달 → 여섯 자리 완성 시 복사 저장 → 다른 숫자로 교체.
4. **핵심 코드:** start는 다음에 선택할 수 있는 인덱스이고 count는 채운 자리 수다. i+1을 전달해 같은 숫자 재사용과 순서만 다른 중복을 막는다. choice[count]는 다음 선택에서 덮어쓰므로 따로0으로 복원하지 않아도 된다. 단, 결과 목록에는 배열 참조가 아닌 clone을 저장해야 한다. 입력이 정렬되어 있어 결과도 사전순으로 생성된다.
5. **예제 추적:** {1,...,7}에서 choice=[1,2,3,4,5,6] 저장 → 마지막 자리 후보를7로 바꿔 [1,2,3,4,5,7] 저장 → 다섯째 자리를6으로 바꾸고 마지막에7을 골라 [1,2,3,4,6,7] 저장 → 반복해7행이 된다.
6. **복잡도:** K=입력 길이일 때 탐색의 느슨한 상한 O(K^6), 출력 저장 O(6×C(K,6)), 재귀 깊이6이다.

원본: **백준 6603 — 로또** (실버 2, 공개 기록 기준)

[원본 문제](https://www.acmicpc.net/problem/6603) · [조건 대조 기록](https://hyonee.tistory.com/6)

## 2. 모두에게 가까운 사람

[완성된 실행 파일](answers/Main2.java)

```java
import java.util.*;

class Solution2 {
    public int solution(int n, int[][] friendships) {
        List<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] friendship : friendships) {
            graph[friendship[0]].add(friendship[1]);
            graph[friendship[1]].add(friendship[0]);
        }

        int answer = 1;
        int minSum = Integer.MAX_VALUE;
        for (int start = 1; start <= n; start++) {
            int sum = bfs(start, n, graph);
            if (sum < minSum) {
                minSum = sum;
                answer = start;
            }
        }
        return answer;
    }

    private int bfs(int start, int n, List<Integer>[] graph) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, -1);
        dist[start] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int next : graph[current]) {
                if (dist[next] == -1) {
                    dist[next] = dist[current] + 1;
                    queue.offer(next);
                }
            }
        }
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += dist[i];
        }
        return sum;
    }
}
```

1. **알고리즘:** 사람마다 BFS를 실행하고 최단 단계 합을 비교한다.
2. **이유:** 모든 친구 연결의 비용이1이라 BFS로 최소 단계를 구할 수 있다. DP나 플로이드–워셜은 사용하지 않는다.
3. **실행 순서:** 양방향 인접 리스트 → 출발점별 dist 초기화 → BFS → 거리 합산 → 최솟값의 사람 번호 갱신.
4. **핵심 코드:** dist=-1로 방문 여부까지 판단한다. 중복 친구 관계가 있어도 이미 방문한 정점은 다시 넣지 않는다. 사람 번호 오름차순에서 더 작은 합일 때만 바꾸므로 동률이면 작은 번호가 남는다.
5. **예제 추적:** 직선1-2-3-4에서 start1의 dist는 [0,1,2,3]으로 합6. start2는 [1,0,1,2]로 합4여서 answer2. start3도 합4라 유지. start4는 합6이라 최종2다. 배열 설명에서0번 인덱스는 생략했다.
6. **복잡도:** 정점 N, 친구 관계 E개에 대해 시간 O(N(N+E)), 공간 O(N+E).

원본: **백준 1389 — 케빈 베이컨의 6단계 법칙** (실버 1, 공개 기록 기준)

[원본 문제](https://www.acmicpc.net/problem/1389) · [조건 대조 기록](https://geumba.tistory.com/34)

## 3. 방 색을 최소한으로 바꾸기

[완성된 실행 파일](answers/Main3.java)

```java
import java.util.*;

class Solution3 {
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(String[] rooms) {
        int n = rooms.length;
        int[][] dist = new int[n][n];
        for (int row = 0; row < n; row++) {
            Arrays.fill(dist[row], Integer.MAX_VALUE);
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[2], b[2]));
        dist[0][0] = 0;
        pq.offer(new int[]{0, 0, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int row = current[0];
            int col = current[1];
            int cost = current[2];
            if (cost > dist[row][col]) {
                continue;
            }
            if (row == n - 1 && col == n - 1) {
                return cost;
            }
            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
                    int nextCost = cost;
                    if (rooms[nr].charAt(nc) == '0') {
                        nextCost++;
                    }
                    if (nextCost < dist[nr][nc]) {
                        dist[nr][nc] = nextCost;
                        pq.offer(new int[]{nr, nc, nextCost});
                    }
                }
            }
        }
        return -1;
    }
}
```

1. **알고리즘:** PriorityQueue 기반 격자 다익스트라.
2. **이유:** 흰 방 진입 비용은0, 검은 방은1이다. 이동 횟수가 아니라 색을 바꾼 수를 기준으로 처리해야 한다.
3. **실행 순서:** dist 큰 값 초기화 → 시작 비용0 → 가장 작은 비용부터 꺼내기 → 검은 방이면+1 → 더 작은 비용만 갱신 → 도착 비용 반환.
4. **핵심 코드:** '1'이 통과 비용0인 흰 방이라는 점에 주의한다. 단순 Queue의 최초 방문으로 확정하지 않고 dist로 개선 여부를 판단한다. 큐의 비용 비교는 {행,열,비용}의 인덱스2이다. 음수 간선이 없어 다익스트라를 적용할 수 있다.
5. **예제 추적:** {"10","01"}에서 (0,0) 비용0 → (0,1),(1,0)은 각각 비용1 → 어느 쪽에서든 도착 (1,1)은 흰 방이므로 비용1 유지 → 반환1. {"11","11"}이면 모든 진입 비용이0이라 이동 두 번을 해도 반환값0이다.
6. **복잡도:** 시간 O(N² log(N²+1)), 공간 O(N²).

원본: **백준 2665 — 미로만들기** (골드 4, 공개 기록 기준)

[원본 문제](https://www.acmicpc.net/problem/2665) · [조건 대조 기록](https://jjunsu.tistory.com/271)

## 4. 검을 얻고 제한 시간 안에 도착하기

[완성된 실행 파일](answers/Main4.java)

```java
import java.util.*;

class Solution4 {
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public String solution(int[][] castle, int limit) {
        int n = castle.length;
        int m = castle[0].length;
        int[][][] dist = new int[n][m][2];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                Arrays.fill(dist[row][col], -1);
            }
        }
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0, 0});
        dist[0][0][0] = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            int sword = current[2];
            int time = dist[row][col][sword];
            if (row == n - 1 && col == m - 1) {
                return String.valueOf(time);
            }
            if (time == limit) {
                continue;
            }

            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && (castle[nr][nc] != 1 || sword == 1)) {
                    int nextSword = sword;
                    if (castle[nr][nc] == 2) {
                        nextSword = 1;
                    }
                    if (dist[nr][nc][nextSword] == -1) {
                        dist[nr][nc][nextSword] = time + 1;
                        queue.offer(new int[]{nr, nc, nextSword});
                    }
                }
            }
        }
        return "Fail";
    }
}
```

1. **알고리즘:** 위치+검 보유 상태 BFS.
2. **이유:** 모든 이동은1시간이지만 검 보유 여부에 따라 통과 가능한 칸이 달라진다. 같은 좌표도 검이 있는 상태와 없는 상태를 구분한다.
3. **실행 순서:** 시작 상태0 → BFS → 목표이면 시간 반환 → 제한 시간에서 확장 중단 → 이동 가능 칸에서 검 상태 갱신 → 미방문 상태 삽입.
4. **핵심 코드:** dist[행][열][검]으로 방문과 시간을 함께 관리한다. 큐에는 시간이 중복 저장되지 않는다. 검 칸으로 이동한 뒤의 nextSword로 방문 여부를 검사한다. 목적지 검사를 시간 제한 검사보다 먼저 해 정확히 limit에 도착해도 성공한다. limit보다 큰 상태는 큐에 들어가지 않는다.
5. **예제 추적:** 첫 예제에서 (0,0,검0) 시간0 → (0,1,검0) 시간1 → (0,2,검1) 시간2 → 벽 (1,2,검1) 시간3 → 목표 (2,2,검1) 시간4. limit4이면 "4",limit3이면 시간3에서 더 확장하지 않아 "Fail". 다른 예제는 검에 접근할 수 없어 벽 줄을 넘지 못한다.
6. **복잡도:** 상태 수2NM에 대해 시간 O(NM), 공간 O(NM).

원본: **백준 17836 — 공주님을 구해라!** (골드 5, 공개 기록 기준)

[원본 문제](https://www.acmicpc.net/problem/17836) · [조건 대조 기록](https://potato9703.tistory.com/79?category=1318101)

## 5. 이동하는 상어를 순서대로 잡기

[완성된 실행 파일](answers/Main5.java)

```java
import java.util.*;

class Solution5 {
    int[] dr = {0, -1, 1, 0, 0};
    int[] dc = {0, 0, 0, 1, -1};
    int[] reverse = {0, 2, 1, 4, 3};

    public int solution(int rows, int cols, int[][] sharks) {
        // board[행][열]에 {속력, 방향, 크기}를 저장한다. 없으면 null이다.
        int[][][] board = new int[rows][cols][];
        for (int[] shark : sharks) {
            int direction = shark[3];
            int period = direction <= 2 ? 2 * (rows - 1) : 2 * (cols - 1);
            board[shark[0] - 1][shark[1] - 1] =
                    new int[]{shark[2] % period, direction, shark[4]};
        }

        int answer = 0;
        for (int fisherCol = 0; fisherCol < cols; fisherCol++) {
            // 현재 열에서 가장 위에 있는 상어 한 마리만 잡는다.
            for (int row = 0; row < rows; row++) {
                if (board[row][fisherCol] != null) {
                    answer += board[row][fisherCol][2];
                    board[row][fisherCol] = null;
                    break;
                }
            }

            // 이동 전 지도와 이동 후 지도를 분리한다.
            int[][][] nextBoard = new int[rows][cols][];
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (board[row][col] != null) {
                        int[] shark = board[row][col];
                        int nr = row;
                        int nc = col;
                        int direction = shark[1];

                        for (int step = 0; step < shark[0]; step++) {
                            int nextRow = nr + dr[direction];
                            int nextCol = nc + dc[direction];
                            if (nextRow < 0 || nextRow >= rows
                                    || nextCol < 0 || nextCol >= cols) {
                                direction = reverse[direction];
                            }
                            nr += dr[direction];
                            nc += dc[direction];
                        }
                        shark[1] = direction;

                        if (nextBoard[nr][nc] == null
                                || nextBoard[nr][nc][2] < shark[2]) {
                            nextBoard[nr][nc] = shark;
                        }
                    }
                }
            }
            board = nextBoard;
        }
        return answer;
    }
}
```

1. **알고리즘:** 순서가 있는 구현·시뮬레이션. 사용자의 새 요청에 따라 탐색과 독립적인 골드 상위 구현 문제를1개 포함했다. DP는 사용하지 않는다.
2. **이유:** 낚시 위치, 잡힌 상어, 이동 방향, 이동 후 충돌이 다음 회차 상태를 바꾼다. 전부 같은 순서로 반영해야 한다.
3. **실행 순서:** 현재 열의 위쪽 상어 제거 → 남은 각 상어 이동 → nextBoard에서 크기 비교 → 지도 교체 → 다음 열 반복.
4. **핵심 코드:** board는 이동 전 상어 목록이고 nextBoard는 이동 후 자리라 아직 이동하지 않은 상어와 미리 충돌시키지 않는다. 경계 반사는 이동 전에 다음 칸으로 검사한다. 속력은 세로2(rows-1),가로2(cols-1)의 왕복 주기로 줄여 불필요한 반복을 없앤다. 이는 같은 왕복을 생략하는 것으로 전체1000번 이동을 매번 수행하지 않기 위한 처리다. 방향과 속력은상어 배열, 위치는지도 인덱스에만 저장해 중복 좌표 상태를 두지 않는다. 입력 배열은 수정하지 않는다.
5. **예제 추적:** 충돌 예제에서 첫 열에는 상어가 없어 합0. (1,2)의 크기4는 아래로1칸, (3,2)의 크기9는 위로1칸 이동해 둘 다(2,2)에 도착한다. nextBoard에는 크기9만 남는다. 다음 열에서 그 상어를 잡아 합9. 이동 도중의 경로가 아니라 최종 칸에서만 합친다. 속력0 상어도 충돌 비교 대상이다.
6. **복잡도:** R행,C열,M마리,D=max(R,C)라 하면 줄인 속력은2D 미만이다. 시간의 느슨한 상한 O(C(RC+MD)), 공간 O(RC+M). 모듈러로 주기를 줄인 뒤의 단순 한 칸 이동을 사용한다.

원본: **백준 17143 — 낚시왕** (골드 1, 공개 기록 기준)

[원본 문제](https://www.acmicpc.net/problem/17143) · [조건 대조 기록](https://skdltm117.tistory.com/34)

## 확인 범위와 형식 변환

백준 직접 조회에서는 정상 문제 본문을 얻지 못했습니다. 공개 문제별 기록으로 번호·제목·제한·핵심 조건을 확인했고 각 절에 출처를 남겼습니다. 원문 직접 대조 완료라고 주장하지 않습니다. 5번의 골드1 표기는 [공개 난이도 기록](https://developer-traxer.tistory.com/124)도 대조했습니다.

입출력만 solution 인자와 반환값으로 바꿨습니다. 1번은 원본의 여러 테스트 케이스를 개별 호출로 전달하고 모든6개 조합을2차원 배열로 반환합니다. 2번의 동률 시 작은 번호,3번의 검은방0·흰방1,4번의 검 획득 직후 상태 변경과 제한 시간 포함,5번의 상어 좌표1부터·속력0·잡기 이후 동시 이동·최종 위치 충돌을 유지했습니다. 일부 예제는 원본 조건에 맞춘 연습용 데이터이며 모두 공식 예제라고 주장하지 않습니다.

DP 풀이를 사용하지 않습니다. DFS 재귀,Queue+LinkedList,PriorityQueue의 기본 패턴을 유지하고 Deque·ArrayDeque·Stream·별도 Node 클래스는 사용하지 않습니다. 5번은 사용자가 새로 요청한 골드 상위 구현 문제이며 기존의 탐색 중심 범위에 추가한 것입니다.

