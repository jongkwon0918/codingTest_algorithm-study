# 2026-10-23 정답 및 해설

문제를 먼저 풀고 확인하세요. 완성 코드를 먼저 제공하고 그 뒤 풀이와 예제 추적을 설명합니다.

## 1. 여섯 자리 숫자 만들기

[완성된 실행 파일](answers/Main1.java)

```java
import java.util.*;

class Solution1 {
    int[][] board;
    Set<String> answer;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(int[][] board) {
        this.board = board;
        answer = new HashSet<>();
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                dfs(row, col, String.valueOf(board[row][col]));
            }
        }
        return answer.size();
    }

    private void dfs(int row, int col, String number) {
        if (number.length() == 6) {
            answer.add(number);
            return;
        }
        for (int d = 0; d < 4; d++) {
            int nr = row + dr[d];
            int nc = col + dc[d];
            if (nr >= 0 && nr < 5 && nc >= 0 && nc < 5) {
                dfs(nr, nc, number + board[nr][nc]);
            }
        }
    }
}
```

1. **알고리즘:** 재귀 DFS로 모든 시작점과 다섯 번의 이동을 완전탐색한다.
2. **이유:** 최대 25개 시작점에서 깊이 5의 네 방향 이동만 확인하면 된다. 경로 재방문이 허용되므로 visited가 필요 없다.
3. **실행 순서:** 시작 칸의 숫자를 문자열로 전달 → 인접 칸 숫자 연결 → 길이 6에서 Set에 추가 → 모든 시작점 반복.
4. **핵심 코드:** 문자열을 사용해 앞자리 0을 그대로 유지한다. answer의 HashSet은 좌표 방문이 아니라 완성된 결과의 중복을 제거한다. String은 새 값이 전달되므로 숫자를 지우는 복원 코드가 필요 없다.
5. **예제 추적:** 모든 칸이 0일 때 (0,0)에서 시작하면 "0" → 오른쪽 (0,1)로 "00" → 왼쪽 (0,0)으로 "000" → 반복해 "000000"을 추가한다. 다른 경로에서도 같은 문자열이므로 Set 크기는 1이다. 같은 칸으로 돌아오는 이동을 막으면 안 된다.
6. **복잡도:** 자리 수를 6으로 고정하면 시간 O(25×4^5), 공간 O(U+6). U는 서로 다른 완성 문자열 수이며 각 문자열 길이는 6이다. 재귀 깊이는 최대 6이다.

원본: **백준 2210 — 숫자판 점프**

[원본 문제](https://www.acmicpc.net/problem/2210) · [문제 조건 대조 기록](https://hyunsix.tistory.com/entry/백준-2210-숫자판-펌프-python)

## 2. 걸어서 또는 정거장으로 이동하기

[완성된 실행 파일](answers/Main2.java)

```java
import java.util.*;

class Solution2 {
    public int solution(int n, int[][] teleports, int start, int target) {
        List<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] teleport : teleports) {
            graph[teleport[0]].add(teleport[1]);
            graph[teleport[1]].add(teleport[0]);
        }

        int[] dist = new int[n + 1];
        Arrays.fill(dist, -1);
        Queue<Integer> queue = new LinkedList<>();
        dist[start] = 0;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == target) {
                return dist[current];
            }

            int[] nexts = {current - 1, current + 1};
            for (int next : nexts) {
                if (next >= 1 && next <= n && dist[next] == -1) {
                    dist[next] = dist[current] + 1;
                    queue.offer(next);
                }
            }
            for (int next : graph[current]) {
                if (dist[next] == -1) {
                    dist[next] = dist[current] + 1;
                    queue.offer(next);
                }
            }
        }
        return -1;
    }
}
```

1. **알고리즘:** Queue+LinkedList를 사용하는 BFS.
2. **이유:** 걷기와 정거장 이동이 모두 1초라 같은 비용의 간선으로 볼 수 있다.
3. **실행 순서:** 양방향 정거장 인접 리스트 → dist=-1 → 출발점 0 → 좌우 걷기와 연결된 모든 정거장 탐색 → 목표를 꺼내면 반환.
4. **핵심 코드:** dist=-1이 미방문을 뜻하므로 visited를 따로 만들지 않는다. 큐에는 지점 번호만 저장한다. 한 지점에서 여러 정거장으로 갈 수 있어 연결을 단일 배열 값 하나로 저장하면 안 된다.
5. **예제 추적:** 첫 예제에서 1 처리 후 dist[2]=1,dist[8]=1. 2 처리로 3의 거리는 2. 8 처리로 7,9,10의 거리가 2가 된다. 10을 꺼내면 2 반환. 정거장 연결은 양방향이므로 10→8→1도 2초다.
6. **복잡도:** 지점 N, 정거장 연결 M개에 대해 시간 O(N+M), 공간 O(N+M). 걷기 간선을 그래프에 따로 저장하지 않고 처리한다.

원본: **백준 18232 — 텔레포트 정거장**

[원본 문제](https://www.acmicpc.net/problem/18232) · [문제 조건 대조 기록](https://ongveloper.tistory.com/441)

## 3. 파동으로 목표에 닿기

[완성된 실행 파일](answers/Main3.java)

```java
import java.util.*;

class Solution3 {
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(String[] classroom, int[] start, int[] target) {
        int n = classroom.length;
        int m = classroom[0].length();
        int[][] dist = new int[n][m];
        for (int row = 0; row < n; row++) {
            Arrays.fill(dist[row], Integer.MAX_VALUE);
        }

        int startRow = start[0] - 1;
        int startCol = start[1] - 1;
        int targetRow = target[0] - 1;
        int targetCol = target[1] - 1;
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[2], b[2]));
        dist[startRow][startCol] = 0;
        pq.offer(new int[]{startRow, startCol, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int row = current[0];
            int col = current[1];
            int cost = current[2];
            if (cost > dist[row][col]) {
                continue;
            }
            if (row == targetRow && col == targetCol) {
                return cost;
            }

            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
                    char next = classroom[nr].charAt(nc);
                    int nextCost = cost;
                    if (next == '1' || next == '#') {
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

1. **알고리즘:** 격자 기반 다익스트라. 빈칸 진입 비용 0, 학생과 목표 진입 비용 1로 모델링한다.
2. **이유:** 한 점프에서 빈칸은 얼마든지 통과하지만 학생 한 겹을 통과하려면 다음 파동 단계가 필요하다. 어떤 칸에 파동을 도달시키는 최소 단계를 누적 비용으로 계산할 수 있다. 목표 학생까지 비용 1을 포함하면 실제 필요한 점프 횟수가 된다.
3. **실행 순서:** 시작 비용 0 → 비용이 작은 칸부터 처리 → 빈칸은 같은 비용으로 확장, 학생은 +1 → 목표를 꺼내면 반환.
4. **핵심 코드:** 이동 칸 수가 아니라 학생 겹 수를 최소화한다. 비용 0과 1이 섞이므로 일반 BFS의 최초 방문만으로 거리를 확정하지 않는다. 별도의 visited 없이 dist 갱신과 오래된 항목 제외를 사용한다. 실제 지도를 매번 바꾸지 않아도 최소 전파 단계를 계산할 수 있다.
5. **예제 추적:** "*11#"의 각 칸 최소 비용은 [0,1,2,3]. 첫 학생 비용 1 → 둘째 학생 비용 2 → 목표 비용 3. "*00#"이면 [0,0,0,1]이므로 목표를 잡는 점프는 1번이다. 세 번째 예제는 첫 점프에 가로로 놓인 학생 한 겹이 쓰러지고, 두 번째 점프에 위쪽 빈칸을 통과해 목표를 잡는다.
6. **복잡도:** 시간 O(NM log(NM+1)), 공간 O(NM). 격자의 간선 수는 칸 수의 상수 배다.

원본: **백준 14497 — 주난의 난(難)**

[원본 문제](https://www.acmicpc.net/problem/14497) · [문제 조건 대조 기록](https://littlesam95.tistory.com/entry/BOJGold-4-백준-14497-주난의-난難C)

## 4. 세 칸짜리 무기를 겹치지 않게 만들기

[완성된 실행 파일](answers/Main4.java)

```java
import java.util.*;

class Solution4 {
    int n;
    int m;
    int[][] wood;
    boolean[][] visited;
    int answer;
    int[] dr = {-1, -1, 1, 1};
    int[] dc = {-1, 1, -1, 1};

    public int solution(int[][] wood) {
        this.wood = wood;
        n = wood.length;
        m = wood[0].length;
        visited = new boolean[n][m];
        answer = 0;
        dfs(0, 0, 0);
        return answer;
    }

    private void dfs(int row, int col, int sum) {
        if (col == m) {
            row++;
            col = 0;
        }
        if (row == n) {
            answer = Math.max(answer, sum);
            return;
        }

        if (!visited[row][col]) {
            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d]; // 세로로 붙는 칸: (nr, col)
                int nc = col + dc[d]; // 가로로 붙는 칸: (row, nc)
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && !visited[nr][col] && !visited[row][nc]) {
                    visited[row][col] = true;
                    visited[nr][col] = true;
                    visited[row][nc] = true;

                    int strength = wood[row][col] * 2
                            + wood[nr][col] + wood[row][nc];
                    dfs(row, col + 1, sum + strength);

                    visited[row][col] = false;
                    visited[nr][col] = false;
                    visited[row][nc] = false;
                }
            }
        }

        // 이 칸을 중심으로 만들지 않는 선택도 확인한다.
        dfs(row, col + 1, sum);
    }
}
```

1. **알고리즘:** 재귀 DFS + 백트래킹으로 무기 배치를 완전탐색한다.
2. **이유:** 최대 25칸이고 각 칸에서 중심으로 만들지 않거나 네 방향 중 하나를 선택한다. 현재 배치가 이후 다른 배치의 가능 여부를 바꾸므로 실제 사용 칸을 표시하고 복원해야 한다.
3. **실행 순서:** (행,열)을 순서대로 확인 → 가능한 모양의 세 칸 표시 → 다음 칸 탐색 → 세 칸 복원 → 중심으로 사용하지 않는 경우 탐색 → 마지막에서 최대 합 갱신.
4. **핵심 코드:** visited는 현재 배치에서 사용 중인 칸이다. 다음 선택을 위해 반드시 세 칸 모두 false로 복원한다. row,col은 다음 중심 후보를 결정하고 visited는 실제 겹침을 막는다. 현재 칸을 중심으로 사용하지 않아도 뒤에서 선택하는 부메랑의 날개로는 사용할 수 있다. 중심 강도만 두 배다. 일차원 인덱스 나눗셈 대신 열 끝에서 다음 행으로 넘어간다.
5. **예제 추적:** 2*2 판에서 중심 (0,0)에 아래3+오른쪽2를 쓰면 합7. 재귀 후 세 칸을 복원해 다른 중심도 검사한다. 중심 (1,1)에 위2+왼쪽3을 쓰면 4*2+2+3=13. 총 네 칸뿐이라 무기 두 개는 불가능하므로 최댓값13이다. {{7}}은 두 날개를 만들 수 없어 건너뛰기만 실행해0이다.
6. **복잡도:** K=NM이라 할 때 느슨한 상한은 시간 O(5^K), 공간 O(K). 실제로는 범위와 칸 겹침 조건이 많은 분기를 제거한다. 재귀 깊이는 K+1이고 최대5*5 입력에서 실행을 검증한다.

원본: **백준 18430 — 무기공학**

[원본 문제](https://www.acmicpc.net/problem/18430) · [문제 조건 대조 기록](https://jun-bae.tistory.com/126?category=1135822)

## 5. 위치와 방향을 함께 맞추기

[완성된 실행 파일](answers/Main5.java)

```java
import java.util.*;

class Solution5 {
    int[] dr = {0, 0, 0, 1, -1};
    int[] dc = {0, 1, -1, 0, 0};

    public int solution(int[][] factory, int[] start, int[] target) {
        int n = factory.length;
        int m = factory[0].length;
        int[][][] dist = new int[n][m][5];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                Arrays.fill(dist[row][col], -1);
            }
        }

        int startRow = start[0] - 1;
        int startCol = start[1] - 1;
        int targetRow = target[0] - 1;
        int targetCol = target[1] - 1;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol, start[2]});
        dist[startRow][startCol][start[2]] = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            int direction = current[2];
            int count = dist[row][col][direction];
            if (row == targetRow && col == targetCol && direction == target[2]) {
                return count;
            }

            // 동/서에서 회전하면 남/북, 남/북에서 회전하면 동/서이다.
            int firstDirection = direction <= 2 ? 3 : 1;
            for (int nextDirection = firstDirection;
                    nextDirection < firstDirection + 2; nextDirection++) {
                if (dist[row][col][nextDirection] == -1) {
                    dist[row][col][nextDirection] = count + 1;
                    queue.offer(new int[]{row, col, nextDirection});
                }
            }

            for (int step = 1; step <= 3; step++) {
                int nr = row + dr[direction] * step;
                int nc = col + dc[direction] * step;
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && factory[nr][nc] == 0) {
                    if (dist[nr][nc][direction] == -1) {
                        dist[nr][nc][direction] = count + 1;
                        queue.offer(new int[]{nr, nc, direction});
                    }
                } else {
                    // 장애물이나 경계는 뛰어넘을 수 없다.
                    break;
                }
            }
        }
        return -1; // 원본의 도달 가능 조건에서는 실행되지 않는다.
    }
}
```

1. **알고리즘:** 위치+방향 상태 BFS.
2. **이유:** 전진 거리와 관계없이 명령 한 번의 비용은1이고 회전도1이다. 같은 위치라도 방향에 따라 다음 행동이 달라지므로 (행,열,방향)을 하나의 상태로 본다.
3. **실행 순서:** 시작 상태 거리0 → 상태 꺼내기 → 위치와 방향 모두 일치하면 반환 → 좌우90도 회전 상태 추가 → 전진1~3칸 상태 추가.
4. **핵심 코드:** dist=-1로 방문 여부도 판단한다. 180도 회전을 비용2의 상태로 바로 큐에 넣으면 BFS의 동일 비용 규칙이 깨지므로90도 회전만 추가한다. 전진 중 장애물이나 범위 밖을 만나면 break, 이미 방문한 상태는 삽입만 생략하고 더 먼 전진 칸은 계속 검사한다. 원본 방향 번호를 유지해 별도의 방향 변환은 없다.
5. **예제 추적:** 첫 예제 시작 (1,1,동)의 거리0에서 (1,2,동),(1,3,동),(1,4,동)을 모두 거리1로 추가한다. 목표를 꺼내면1. 두 번째 예제는 동0 → 남1 또는 북1 → 서2이므로 위치가 같아도2. 세 번째 예제는 동쪽2칸 전진 → 북쪽으로90도 회전 → 북쪽2칸 전진으로3이다. 좌표는 설명에서1부터 적었다.
6. **복잡도:** 상태 수는4NM, 각 상태에서 최대5가지 명령 결과를 검사하므로 시간 O(NM), 공간 O(NM).

원본: **백준 1726 — 로봇**

[원본 문제](https://www.acmicpc.net/problem/1726) · [문제 조건 대조 기록](https://black-hair.tistory.com/118)

## 출처 확인과 변환 범위

백준 직접 조회에서 정상 본문을 얻지 못한 문제는 공개된 문제별 기록으로 번호·제목·제한사항·핵심 조건을 대조했습니다. 각 절에 확인 출처가 있으며 원문 직접 대조 완료라고 주장하지 않습니다.

입력은 메서드 매개변수로, 출력은 반환값으로 변환했습니다. 1번은 앞자리0과 재방문, 2번은 양방향 정거장과 걷기의 같은 이동 비용, 3번은 학생 한 겹씩 전파되는 점프, 4번은 중심 강도2배와 세 칸의 겹침 금지, 5번은1~3칸 전진 한 명령과 최종 방향 조건을 유지했습니다. 3번과5번의 입력 좌표는 원본처럼1부터 시작합니다. 일부 예제는 원본 조건에 맞춰 만든 연습용 데이터이며 모두 공식 예제라고 주장하지 않습니다.

1번은 경로 재방문 허용, 2번은 dist를 이용한 방문 판단, 4번은 배치 복원을 위한 visited, 5번은 방향까지 포함한 상태를 연습합니다. 재귀 DFS·Queue+LinkedList·PriorityQueue의 기본 구조를 사용하며 Deque·ArrayDeque·Stream·별도 Node 클래스는 사용하지 않습니다.

