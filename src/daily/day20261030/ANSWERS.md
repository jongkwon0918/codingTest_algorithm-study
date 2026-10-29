# 2026-10-30 정답 및 해설

각 항목은 완성된 Solution 코드 다음에 알고리즘·이유·실행 순서·핵심 코드·실제 예제 추적·복잡도를 설명합니다. 전체 Main을 실행하려면 answers/Main 파일을 이용하세요.

문제 실재 여부와 조건은 아래 공개 문제별 기록과 원본 링크를 대조했습니다. 백준 직접 조회에서는 정상 문제 본문을 얻지 못했으므로 직접 원문 대조를 완료했다고 주장하지 않습니다. 원래 규칙을 유지하고 입출력을 메서드 호출/반환으로 옮겼으며 작은 예제는 학습용 입력입니다.

## 1. 순서를 구분해서 숫자 고르기

[완성 Main1.java](answers/Main1.java)

```java
import java.util.*;

class Solution1 {
    int n;
    int m;
    int[] choice;
    boolean[] visited;
    List<int[]> answer;

    public int[][] solution(int n, int m) {
        this.n = n;
        this.m = m;
        choice = new int[m];
        visited = new boolean[n + 1];
        answer = new ArrayList<>();
        dfs(0);

        int[][] result = new int[answer.size()][m];
        for (int i = 0; i < answer.size(); i++) {
            result[i] = answer.get(i);
        }
        return result;
    }

    private void dfs(int count) {
        if (count == m) {
            answer.add(choice.clone());
            return;
        }

        for (int next = 1; next <= n; next++) {
            if (!visited[next]) {
                visited[next] = true;
                choice[count] = next;
                dfs(count + 1);
                visited[next] = false;
            }
        }
    }
}
```

1. 알고리즘: 재귀 DFS와 백트래킹으로 순열을 만든다.
2. 이유: 모든 순서를 반환해야 한다. 조합처럼 다음 인덱스부터만 고르면 {2,1}을 놓친다.
3. 실행 순서: 깊이마다 1부터 n까지 미사용 숫자를 고른다. m개가 되면 저장하고 돌아온다.
4. 핵심: visited는 현재 수열에서 사용한 숫자만 표시하며 재귀 후 해제한다. choice는 덮어쓰므로 지우지 않지만, 저장할 때 clone으로 복사한다. 오름차순 후보 순회가 사전순을 보장한다.
5. 예제 추적: n=3,m=2에서 1선택(visited[1]=true) -> 2선택 -> [1,2]복사 -> visited[2]=false -> 3선택 -> [1,3]복사 -> 1선택 해제. 다음 2선택 -> 1선택 -> [2,1]복사. 이후 [2,3],[3,1],[3,2]가 이어진다.
6. 복잡도: P=n!/(n-m)!일 때 출력 복사를 포함한 시간 O(P*m), 결과 공간 O(P*m), 재귀와 작업 배열 O(n+m).

원본: [백준 15649 — N과 M (1)](https://www.acmicpc.net/problem/15649)

대조 기록: [문제별 공개 기록](https://github.com/chech2/Algorithm/blob/main/%EB%B0%B1%EC%A4%80/Silver/15649.%E2%80%85N%EA%B3%BC%E2%80%85M%E2%80%85%EF%BC%881%EF%BC%89/README.md).

## 2. 편의점을 거쳐 공연장에 도착하기

[완성 Main2.java](answers/Main2.java)

```java
import java.util.*;

class Solution2 {
    public String solution(int[] home, int[][] stores, int[] festival) {
        int count = stores.length;
        int[][] places = new int[count + 2][2];
        places[0] = home;
        for (int i = 0; i < count; i++) {
            places[i + 1] = stores[i];
        }
        places[count + 1] = festival;

        boolean[] visited = new boolean[count + 2];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        visited[0] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == count + 1) {
                return "happy";
            }

            for (int next = 0; next < places.length; next++) {
                int distance = Math.abs(places[current][0] - places[next][0])
                        + Math.abs(places[current][1] - places[next][1]);
                if (!visited[next] && distance <= 1000) {
                    visited[next] = true;
                    queue.offer(next);
                }
            }
        }
        return "sad";
    }
}
```

1. 알고리즘: 장소를 정점으로 보는 BFS.
2. 이유: 한 박스의 최대 이동 거리는 20*50=1000이다. 편의점마다 20병을 채울 수 있으므로, 1000 이내인 장소끼리 연결되는지 확인하면 된다. 병 수를 별도 상태로 둘 필요가 없다.
3. 실행 순서: 집을 큐에 넣고, 꺼낸 장소에서 1000 이내의 미방문 장소를 넣는다. 공연장을 꺼내면 성공한다.
4. 핵심: 방문은 큐에 넣을 때 표시한다. 도달 가능 여부만 필요해서 dist는 없다. places는 입력을 읽기만 하고 변경하지 않는다.
5. 예제 추적: 장소 번호를 집0,편의점1·2,공연장3으로 둔다. queue=[0] -> 0에서1방문,queue=[1] -> 1에서2방문,queue=[2] -> 2에서3방문,queue=[3] -> happy. 경계 거리1000은 가능하지만1001은 불가능하다.
6. 복잡도: 편의점 수를 k라 할 때 시간 O((k+2)^2), 공간 O(k+2).

원본: [백준 9205 — 맥주 마시면서 걸어가기](https://www.acmicpc.net/problem/9205)

대조 기록: [문제별 공개 기록](https://youjourney.github.io/archivers/BOJ9205).

## 3. 목적지마다 처음 보낼 곳 정하기

[완성 Main3.java](answers/Main3.java)

```java
import java.util.*;

class Solution3 {
    public String[][] solution(int n, int[][] roads) {
        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            graph[road[0]].add(new int[]{road[1], road[2]});
            graph[road[1]].add(new int[]{road[0], road[2]});
        }

        String[][] answer = new String[n][n];
        for (int start = 1; start <= n; start++) {
            int[] dist = new int[n + 1];
            int[] first = new int[n + 1];
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

                for (int[] edge : graph[currentNode]) {
                    int next = edge[0];
                    int nextCost = currentCost + edge[1];
                    if (nextCost < dist[next]) {
                        dist[next] = nextCost;
                        if (currentNode == start) {
                            first[next] = next;
                        } else {
                            first[next] = first[currentNode];
                        }
                        pq.offer(new int[]{next, nextCost});
                    }
                }
            }

            for (int target = 1; target <= n; target++) {
                if (target == start || dist[target] == Integer.MAX_VALUE) {
                    answer[start - 1][target - 1] = "-";
                } else {
                    answer[start - 1][target - 1] = String.valueOf(first[target]);
                }
            }
        }
        return answer;
    }
}
```

1. 알고리즘: 모든 출발점에서 다익스트라를 반복하고 첫 이동 집하장을 함께 기록한다. DP나 플로이드 워셜을 사용하지 않는다.
2. 이유: 도로마다 시간이 다르고 모두 양수다. 거리만으로는 경로표를 만들 수 없어서 first에 첫 이동지를 보존한다.
3. 실행 순서: 양방향 그래프 생성 -> 출발점마다 dist 초기화 -> PQ에서 작은 비용부터 꺼내 완화 -> first를 경로표의 한 행에 기록.
4. 핵심: 출발점에서 직접 이동하면 first[next]=next, 그 뒤에는 first[next]=first[currentNode]를 물려준다. 더 짧은 거리로 갱신할 때만 first도 바꾼다.
5. 예제 추적: 출발1에서 dist[2]=2,first[2]=2 / dist[3]=10,first[3]=3. 다음2를 꺼내면3의 비용이5로 줄어서 dist[3]=5,first[3]=first[2]=2. PQ의 이전(3,10)은 꺼냈을 때 건너뛴다. 따라서1행은[-,2,2].
6. 복잡도: 정점 n, 도로 e일 때 시간 O(n*(n+(n+e)*log(n+e))), 결과를 포함한 공간 O(n^2+n+e). 희소·연결 그래프에서는 흔히 O(n*e*log n)으로 표현한다.

원본: [백준 1719 — 택배](https://www.acmicpc.net/problem/1719)

대조 기록: [문제별 공개 기록](https://excited-hyun.tistory.com/156?category=939530).

## 4. 복사와 붙여넣기로 목표 개수 만들기

[완성 Main4.java](answers/Main4.java)

```java
import java.util.*;

class Solution4 {
    public int solution(int target) {
        int maxCount = target * 2;
        int[][] dist = new int[maxCount + 1][maxCount + 1];
        for (int i = 0; i <= maxCount; i++) {
            Arrays.fill(dist[i], -1);
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{1, 0});
        dist[1][0] = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int screen = current[0];
            int clipboard = current[1];
            int nextTime = dist[screen][clipboard] + 1;

            if (screen == target) {
                return dist[screen][clipboard];
            }

            // 복사: 화면은 그대로, 클립보드를 화면 개수로 덮어쓴다.
            if (dist[screen][screen] == -1) {
                dist[screen][screen] = nextTime;
                queue.offer(new int[]{screen, screen});
            }

            // 붙여넣기: 클립보드 내용은 유지한다.
            int nextScreen = screen + clipboard;
            if (clipboard > 0 && nextScreen <= maxCount
                    && dist[nextScreen][clipboard] == -1) {
                dist[nextScreen][clipboard] = nextTime;
                queue.offer(new int[]{nextScreen, clipboard});
            }

            // 삭제: 클립보드 내용은 유지한다.
            if (screen > 0 && dist[screen - 1][clipboard] == -1) {
                dist[screen - 1][clipboard] = nextTime;
                queue.offer(new int[]{screen - 1, clipboard});
            }
        }
        return -1;
    }
}
```

1. 알고리즘: (화면 개수, 클립보드 개수)를 상태로 하는 BFS.
2. 이유: 세 동작이 모두1초다. 같은 화면 개수라도 클립보드가 다르면 다음 붙여넣기 결과가 다르므로 두 값을 함께 기록해야 한다.
3. 실행 순서: (1,0)에서 출발 -> 복사·붙여넣기·삭제의 다음 상태 생성 -> 미방문 상태 거리 설정 -> 목표 화면을 꺼내면 반환.
4. 핵심: dist[screen][clipboard]가 방문과 시간을 겸한다. 큐에는 시간을 중복 저장하지 않는다. 탐색 상한은2*target이다. 최초로 목표를 넘는 붙여넣기 직전 화면과 유효 클립보드는 모두 target 미만이므로 그 결과는2*target 미만이다. 이미 목표보다 커진 뒤에는 추가 복사·붙여넣기보다 삭제만 하는 편이 빠르다. 따라서 이 범위 안에 최적 경로가 있다.
5. 예제 추적: target6에서 (화면,저장,시간)은 (1,0,0) -> 복사(1,1,1) -> 붙여넣기(2,1,2) -> 복사(2,2,3) -> 붙여넣기(4,2,4) -> 붙여넣기(6,2,5). dist[2][1]과 dist[2][2]는 다른 상태다.
6. 복잡도: target을 S라 할 때 상태가 O(S^2), 상태마다 세 동작이므로 시간과 공간 모두 O(S^2).

원본: [백준 14226 — 이모티콘](https://www.acmicpc.net/problem/14226)

대조 기록: [문제별 공개 기록](https://guru.tistory.com/115).

## 5. 블록 그룹을 골라 지우고 회전하기

[완성 Main5.java](answers/Main5.java)

```java
import java.util.*;

class Solution5 {
    int n;
    int[][] map;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(int colors, int[][] board) {
        n = board.length;
        map = new int[n][n];
        for (int r = 0; r < n; r++) {
            map[r] = board[r].clone();
        }

        int answer = 0;
        while (true) {
            List<int[]> candidates = findGroups();
            if (candidates.isEmpty()) {
                return answer;
            }

            // {그룹 크기, 무지개 수, 기준 행, 기준 열}: 모두 큰 값 우선
            candidates.sort((a, b) -> {
                for (int i = 0; i < 4; i++) {
                    if (a[i] != b[i]) {
                        return Integer.compare(b[i], a[i]);
                    }
                }
                return 0;
            });

            int[] selected = candidates.get(0);
            List<int[]> blocks = bfs(selected[2], selected[3], new boolean[n][n]);
            for (int[] block : blocks) {
                map[block[0]][block[1]] = -2;
            }
            answer += blocks.size() * blocks.size();

            gravity();
            rotate();
            gravity();
        }
    }

    private List<int[]> findGroups() {
        List<int[]> candidates = new ArrayList<>();
        boolean[][] visited = new boolean[n][n];

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (map[r][c] > 0 && !visited[r][c]) {
                    List<int[]> blocks = bfs(r, c, visited);
                    int rainbowCount = 0;
                    int standardRow = r;
                    int standardCol = c;

                    for (int[] block : blocks) {
                        int row = block[0];
                        int col = block[1];
                        if (map[row][col] == 0) {
                            rainbowCount++;
                            // 다른 색의 그룹에서도 무지개를 사용할 수 있다.
                            visited[row][col] = false;
                        } else if (row < standardRow
                                || (row == standardRow && col < standardCol)) {
                            standardRow = row;
                            standardCol = col;
                        }
                    }

                    if (blocks.size() >= 2) {
                        candidates.add(new int[]{
                                blocks.size(), rainbowCount, standardRow, standardCol});
                    }
                }
            }
        }
        return candidates;
    }

    private List<int[]> bfs(int startRow, int startCol, boolean[][] visited) {
        int color = map[startRow][startCol];
        List<int[]> blocks = new ArrayList<>();
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];
            blocks.add(current);

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n
                        && !visited[nr][nc]
                        && (map[nr][nc] == color || map[nr][nc] == 0)) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return blocks;
    }

    private void gravity() {
        for (int c = 0; c < n; c++) {
            int bottom = n - 1;
            for (int r = n - 1; r >= 0; r--) {
                if (map[r][c] == -1) {
                    bottom = r - 1;
                } else if (map[r][c] >= 0) {
                    int color = map[r][c];
                    map[r][c] = -2;
                    map[bottom][c] = color;
                    bottom--;
                }
            }
        }
    }

    private void rotate() {
        int[][] nextMap = new int[n][n];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                nextMap[n - 1 - c][r] = map[r][c];
            }
        }
        map = nextMap;
    }
}
```

1. 알고리즘: BFS로 그룹을 찾고, 정렬로 우선순위를 정한 다음 삭제·중력·회전을 순서대로 반복하는 시뮬레이션. 골드2 수준이다.
2. 이유: 그룹 정보로 다음 행동을 결정하고 행동 후 판이 달라진다. 매 차례 그룹을 다시 구해야 한다. DFS로 가능한 모든 삭제 순서를 시험하는 문제가 아니라, 규칙으로 정해진 한 그룹만 삭제한다.
3. 실행 순서: findGroups -> 후보 정렬 -> 첫 후보를 BFS로 다시 찾아 삭제 -> 점수 추가 -> gravity -> rotate -> gravity -> 다음 차례. 일반 블록은 한 번만 후보 탐색하고 무지개 방문만 해제한다.
4. 핵심: 후보는 {크기,무지개 수,기준 행,기준 열}이다. 기준 칸은 그룹 내부에서 작은 행·열이지만 그룹 정렬은 큰 값 우선이다. 선택한 그룹을 한 번 더 BFS하여 좌표를 얻으면 모든 후보의 좌표를 별도로 보관할 필요가 없다. 중력은 검은 칸을 만나면 bottom=r-1로 바꾸고, 빈칸-2와 무지개0을 구분한다. 반시계 회전은 (r,c)->(n-1-c,r).
5. 예제 추적: {{1,0},{2,-1}}에서 색1그룹={(0,0),(0,1)}, 색2그룹={(1,0),(0,1)}이다. 첫 BFS 후 무지개(0,1)의 visited만 해제하므로 두 번째 후보도 그 칸을 쓸 수 있다. 두 후보의 크기2·무지개1은 같고 기준 행이1인 색2를 선택한다. 삭제 후 {{1,-2},{-2,-1}}, 중력 후 {{-2,-2},{1,-1}}, 반시계 회전 후 {{-2,-1},{-2,1}}이다. 마지막 중력 후에도 같고 남은 일반 블록은1개라 종료한다. 점수는2*2=4.
6. 복잡도: 보수적으로 한 차례에 최대 n^2번의 BFS가 각각 O(n^2)이므로 O(n^4), 삭제로 차례 수는 O(n^2)이어서 전체 시간 O(n^6), 공간 O(n^2). 실제 구현은 일반 블록의 중복 시작을 막는다. 후보 정렬 O(n^2 log n)은 이 상한에 포함된다. n<=20에서 사용할 수 있는 정석 방식이다.
colors는 입력 블록 색의 범위를 나타내며, 탐색은 시작 칸의 색을 기준으로 하므로 별도 순회에 사용하지 않는다.

원본: [백준 21609 — 상어 중학교](https://www.acmicpc.net/problem/21609)

대조 기록: [문제별 공개 기록](https://yabmoons.tistory.com/657), [예제·입력 조건 추가 대조](https://leeeehhjj.tistory.com/100).


