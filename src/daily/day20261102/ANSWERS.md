# 2026-11-02 정답 및 해설

각 문제는 완성 Solution 코드 다음에 알고리즘·선택 이유·실행 순서·핵심 역할·실제 예제 추적·복잡도를 설명합니다. 전체 Main은 answers 패키지에 있습니다.

## 확인 범위

오늘1~5번 모두 백준 페이지 직접 조회에서 정상 문제 본문을 얻지 못했습니다. 오류 또는 서비스 안내가 표시되어 원문 직접 대조를 완료한 것은 아닙니다. 공개 문제별 기록의 번호·제목·제한·규칙을 대조하고 기존 문제의 입출력을 메서드 매개변수와 반환으로 옮겼습니다. 아래에 원본 링크와 대조 출처를 각각 기록합니다. 작은 예제는 학습용 입력입니다.

## 1. 노드를 지운 뒤 남은 끝 노드

[완성 Main1.java](answers/Main1.java)

```java
import java.util.*;

class Solution1 {
    List<Integer>[] children;
    int removed;

    public int solution(int[] parents, int removed) {
        int n = parents.length;
        this.removed = removed;
        children = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            children[i] = new ArrayList<>();
        }

        int root = 0;
        for (int i = 0; i < n; i++) {
            if (parents[i] == -1) {
                root = i;
            } else {
                children[parents[i]].add(i);
            }
        }

        if (root == removed) {
            return 0;
        }
        return dfs(root);
    }

    private int dfs(int current) {
        int count = 0;
        boolean hasChild = false;

        for (int next : children[current]) {
            if (next != removed) {
                hasChild = true;
                count += dfs(next);
            }
        }

        if (!hasChild) {
            return 1;
        }
        return count;
    }
}
```

1. 알고리즘: 자식 인접 리스트를 이용한 재귀 DFS.
2. 이유: 삭제한 노드로 내려가지 않으면 그 아래 자손 전체도 자동으로 제외된다. 남은 자식이 없는지를 탐색 결과로 판단한다.
3. 실행 순서: 부모 배열로 자식 리스트 생성 -> 실제 루트 확인 -> 루트 삭제면0 -> 루트부터 삭제 노드를 제외하며 끝 노드 수 합산.
4. 핵심: children[current].isEmpty()만 검사하면 유일한 자식이 삭제된 경우를 놓친다. 실제 탐색 가능한 자식이 있었는지를 hasChild로 판단한다. 부모에서 자식으로만 내려가는 트리라 visited가 필요 없다.
5. 예제 추적: {-1,0,1}에서2삭제. dfs(0)은 자식1로 진입 -> dfs(1)은 자식2를 제외하여 hasChild=false ->1반환 -> dfs(0)의 count=1. 루트가 항상0인 것은 아니므로 parents의-1을 찾는다.
6. 복잡도: 시간 O(n), 자식 리스트와 재귀를 포함한 공간 O(n). 최대 깊이가50이라 재귀 DFS에 적절하다.

원본: [백준 1068 — 트리](https://www.acmicpc.net/problem/1068)

대조 출처: [문제별 공개 기록 1](https://blob-thinking.tistory.com/310), [문제별 공개 기록 2](https://sore-demo.tistory.com/40).

## 2. 벽을 피해 만날 수 있는 사람

[완성 Main2.java](answers/Main2.java)

```java
import java.util.*;

class Solution2 {
    public String solution(String[] campus) {
        int n = campus.length;
        int m = campus[0].length();
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        boolean[][] visited = new boolean[n][m];
        Queue<int[]> queue = new LinkedList<>();

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (campus[r].charAt(c) == 'I') {
                    visited[r][c] = true;
                    queue.offer(new int[]{r, c});
                }
            }
        }

        int count = 0;
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];
            if (campus[r].charAt(c) == 'P') {
                count++;
            }

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && campus[nr].charAt(nc) != 'X' && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return count == 0 ? "TT" : String.valueOf(count);
    }
}
```

1. 알고리즘: 시작 위치에서 도달 가능한 모든 칸을 BFS로 탐색한다.
2. 이유: 목적은 최단거리가 아니라 연결된 영역의 사람 수다. DFS도 가능하지만 최대36만 칸의 재귀 깊이 위험을 피하고 Queue 기본형을 사용한다.
3. 실행 순서: I를 찾아 방문 표시 후 큐 삽입 -> 하나씩 꺼내 P이면 카운트 -> 벽이 아니고 미방문인 이웃 삽입 -> 전체 탐색 후 수 또는TT 반환.
4. 핵심: 사람을 만나도 탐색을 멈추지 않는다. 큐에 넣을 때 방문 표시하므로 한 사람은 한 번만 센다. 거리 정보가 필요 없어서 dist와 큐의 시간 값은 없다.
5. 예제 추적: {"IOP","XXO","POP"}에서 queue는(0,0) ->(0,1) ->(0,2)로 진행하며 count=1. 이어(1,2) ->(2,2)에서 count=2, (2,1)을 지나(2,0)에서 count=3이 된다.
6. 복잡도: 시간과 공간 모두 O(n*m).

원본: [백준 21736 — 헌내기는 친구가 필요해](https://www.acmicpc.net/problem/21736)

대조 출처: [문제별 공개 기록 1](https://ondj.tistory.com/147), [문제별 공개 기록 2](https://kne-coding.tistory.com/260).

## 3. 한 도시를 거치거나 피해서 도착하기

[완성 Main3.java](answers/Main3.java)

```java
import java.util.*;

class Solution3 {
    public int[] solution(int n, int[][] roads, int x, int y, int z) {
        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            graph[road[0]].add(new int[]{road[1], road[2]});
        }

        int[] distFromX = dijkstra(x, 0, n, graph);
        int[] distFromY = dijkstra(y, 0, n, graph);
        int[] distWithoutY = dijkstra(x, y, n, graph);

        int through = -1;
        if (distFromX[y] != Integer.MAX_VALUE
                && distFromY[z] != Integer.MAX_VALUE) {
            through = distFromX[y] + distFromY[z];
        }

        int without = -1;
        if (distWithoutY[z] != Integer.MAX_VALUE) {
            without = distWithoutY[z];
        }
        return new int[]{through, without};
    }

    private int[] dijkstra(int start, int blocked, int n, List<int[]>[] graph) {
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

            for (int[] edge : graph[currentNode]) {
                int next = edge[0];
                int nextCost = currentCost + edge[1];
                if (next != blocked && nextCost < dist[next]) {
                    dist[next] = nextCost;
                    pq.offer(new int[]{next, nextCost});
                }
            }
        }
        return dist;
    }
}
```

1. 알고리즘: 다익스트라3회와 금지 도시 조건.
2. 이유: 도로 거리가 양수이고 서로 다르다. 경유 조건은 x->y와y->z의 최단거리를 합하면 되고, 회피 조건은 y로 들어가는 이동을 제외하면 된다.
3. 실행 순서: 단방향 인접 리스트 생성 -> x와y에서 각각 일반 최단거리 -> x에서y진입을 막고 최단거리 -> 각 도달 가능 여부를 확인한 뒤 반환.
4. 핵심: blocked=0은 실제 도시 번호가 아니므로 금지 도시 없음이다. 그래프 자체를 삭제하거나 변경하지 않아 세 탐색이 서로 영향을 주지 않는다. 한 구간이라도 도달 불가이면 경유 결과는-1이다. 유효한 각 구간은 최대(n-1)*10000이고 두 구간의 합도1999980000 이하라 int로 표현할 수 있다.
5. 예제 추적: 첫 예제에서 distFromX[2]=2,distFromY[4]=3이라 through=5. 금지 도시2를 둔 탐색은1->2를 넣지 않고1->3(1)->4(8)로 진행해 without=8. 두 번째 예제는2에 도달할 수 없어서 through=-1이지만1->3의 거리4는 구할 수 있다.
6. 복잡도: 정점 n, 간선 e일 때 세 번을 상수로 생략해 시간 O((n+e)*log(n+e)), 공간 O(n+e).

원본: [백준 23793 — 두 단계 최단 경로 1](https://www.acmicpc.net/problem/23793)

대조 출처: [문제별 공개 기록 1](https://littlesam95.tistory.com/entry/BOJGold-4-백준-23793-두-단계-최단-경로-1C), [문제별 공개 기록 2](https://ghcode.tistory.com/307).

## 4. 한 칸을 채워 가장 큰 모양 만들기

[완성 Main4.java](answers/Main4.java)

```java
import java.util.*;

class Solution4 {
    int n;
    int m;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(int[][] board) {
        n = board.length;
        m = board[0].length;
        int[][] group = new int[n][m];
        int[] sizes = new int[n * m + 1];
        int groupCount = 0;

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (board[r][c] == 1 && group[r][c] == 0) {
                    groupCount++;
                    sizes[groupCount] = bfs(r, c, groupCount, board, group);
                }
            }
        }

        int answer = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (board[r][c] == 0) {
                    int count = 1;
                    int[] seen = new int[4];
                    int seenCount = 0;

                    for (int d = 0; d < 4; d++) {
                        int nr = r + dr[d];
                        int nc = c + dc[d];
                        if (nr >= 0 && nr < n && nc >= 0 && nc < m
                                && group[nr][nc] > 0) {
                            int id = group[nr][nc];
                            boolean duplicate = false;
                            for (int i = 0; i < seenCount; i++) {
                                if (seen[i] == id) {
                                    duplicate = true;
                                }
                            }
                            if (!duplicate) {
                                seen[seenCount] = id;
                                seenCount++;
                                count += sizes[id];
                            }
                        }
                    }
                    answer = Math.max(answer, count);
                }
            }
        }
        return answer;
    }

    private int bfs(int startRow, int startCol, int id,
                    int[][] board, int[][] group) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});
        group[startRow][startCol] = id;
        int count = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];
            count++;

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && board[nr][nc] == 1 && group[nr][nc] == 0) {
                    group[nr][nc] = id;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return count;
    }
}
```

1. 알고리즘: BFS로 연결 요소에 번호와 크기를 부여한 뒤0칸마다 주변 그룹을 합산한다.
2. 이유: 최대100만 칸이다.0을 하나씩 바꿔 전체 BFS를 반복하면 너무 느리다. 미리 구한 영역 크기를 재사용하면 후보당 네 방향만 보면 된다.
3. 실행 순서: 모든1영역을 BFS로 번호 지정 -> sizes에 각 크기 저장 ->0칸을 보며 인접 그룹의 크기를 중복 없이 합산하고 자기 칸1추가 -> 최대 반환.
4. 핵심: group의0은 미방문/빈칸이고 양수는 그룹 번호라 별도 visited가 없다. 인접 그룹은 최대4개이므로 int[4]로 이미 더한 번호만 확인한다. HashSet도 가능하지만 작은 고정 배열이 충분하다. 지도는 실제 변경하지 않으므로 후보 사이의 복원도 필요 없다.
5. 예제 추적: {{1,1,1},{1,0,1},{1,1,1}}의 바깥8칸은 모두group=1이고 sizes[1]=8이다. 가운데0에서 첫 이웃 번호1을 발견해 count=1+8=9,seen=[1]. 나머지 세 방향도 번호1이라 추가하지 않는다.
6. 복잡도: 영역 BFS 전체 O(n*m), 후보마다 최대4개 번호에 대해 최대4개 중복 비교라 상수 시간이다. 전체 시간·공간 O(n*m).

원본: [백준 16932 — 모양 만들기](https://www.acmicpc.net/problem/16932)

대조 출처: [문제별 공개 기록 1](https://ongveloper.tistory.com/176?category=854013), [문제별 공개 기록 2](https://yabmoons.tistory.com/270), [문제별 공개 기록 3](https://drasgon.tistory.com/293).

## 5. 쌓인 말을 색깔 규칙대로 옮기기

[완성 Main5.java](answers/Main5.java)

```java
import java.util.*;

class Solution5 {
    public int solution(int[][] colors, int[][] pieces) {
        int n = colors.length;
        int k = pieces.length;
        int[] dr = {0, 0, 0, -1, 1};
        int[] dc = {0, 1, -1, 0, 0};
        int[][] current = new int[k][3];
        List<Integer>[][] stacks = new ArrayList[n][n];

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                stacks[r][c] = new ArrayList<>();
            }
        }
        for (int i = 0; i < k; i++) {
            current[i][0] = pieces[i][0] - 1;
            current[i][1] = pieces[i][1] - 1;
            current[i][2] = pieces[i][2];
            stacks[current[i][0]][current[i][1]].add(i);
        }

        for (int turn = 1; turn <= 1000; turn++) {
            for (int i = 0; i < k; i++) {
                int r = current[i][0];
                int c = current[i][1];
                int direction = current[i][2];
                int nr = r + dr[direction];
                int nc = c + dc[direction];

                if (isBlocked(nr, nc, colors)) {
                    if (direction % 2 == 1) {
                        direction++;
                    } else {
                        direction--;
                    }
                    current[i][2] = direction;
                    nr = r + dr[direction];
                    nc = c + dc[direction];
                }

                if (isBlocked(nr, nc, colors)) {
                    continue;
                }

                List<Integer> from = stacks[r][c];
                int index = from.indexOf(i);
                List<Integer> moving = new ArrayList<>();

                // 현재 말부터 맨 위까지를 아래->위 순서로 꺼낸다.
                while (from.size() > index) {
                    moving.add(from.remove(index));
                }

                if (colors[nr][nc] == 1) {
                    Collections.reverse(moving);
                }

                for (int number : moving) {
                    stacks[nr][nc].add(number);
                    current[number][0] = nr;
                    current[number][1] = nc;
                }

                if (stacks[nr][nc].size() >= 4) {
                    return turn;
                }
            }
        }
        return -1;
    }

    private boolean isBlocked(int r, int c, int[][] colors) {
        int n = colors.length;
        return r < 0 || r >= n || c < 0 || c >= n || colors[r][c] == 2;
    }
}
```

1. 알고리즘: 위치별 말 목록과 말별 현재 위치·방향을 관리하는 시뮬레이션. 골드2 수준의 구현 문제다.
2. 이유: 최단 경로나 이동 방법을 선택하는 것이 아니라, 말 번호와 칸 색에 따라 정해진 동작을 반복해야 한다. 맨 아래 말만 처리하거나 모두 동시에 옮기면 다른 문제가 된다.
3. 실행 순서: 칸마다 아래->위 말 번호 목록 생성 -> 턴마다1번부터 이동 -> 파란색/범위 밖이면 현재 말만 방향 전환 -> 이동 묶음 분리 -> 빨간 칸이면 묶음 반전 -> 목적지에 추가하고 모든 이동 말 좌표 갱신 ->4개 이상이면 즉시 반환.
4. 핵심: from.indexOf(i)로 중간에 있는 현재 말도 찾는다. remove(index)를 반복하면 아래 말은 남고 해당 말부터 위까지 같은 순서로 moving에 담긴다. 방향을 바꾸는 대상은 current[i] 하나뿐이다. 다른 말에 실려 간 말도 자기 차례가 오면 current의 갱신된 좌표에서 움직인다. isBlocked는 배열 접근 전에 범위를 검사하는 ||의 단락 평가를 사용한다. 여기서는 재시도 후 이동 불가를 continue로 분리하는 편이 명확하다.
5. 예제 추적: 첫 예제의 목록은 말 번호를1부터 표현하면 (2,1)=[1],(2,2)=[2],(2,3)=[3],(2,4)=[4]. 1차례 후(2,2)=[2,1]. 2차례에는[2,1]이 이동해(2,3)=[3,2,1]. 3차례에는[3,2,1]이 이동해(2,4)=[4,3,2,1]이 되어 turn1을 반환한다. 빨간 칸의 기존 목록이[4,5]이고 이동 묶음이[1,2,3]이면 최종[4,5,3,2,1]이다.
6. 복잡도: 턴 상한 T=1000, 말 수 k일 때 ArrayList 중간 삭제 반복은 한 이동당 O(k^2)이므로 보수적 시간 O(T*k^3+n^2), 공간 O(n^2+k). k<=10이라 기본 ArrayList만으로 충분하다. 불필요한 별도 Node나 Deque는 없다.

원본: [백준 17837 — 새로운 게임 2](https://www.acmicpc.net/problem/17837)

대조 출처: [문제별 공개 기록 1](https://zombiecodingfactory.tistory.com/23), [문제별 공개 기록 2](https://ddb8036631.github.io/boj/17837_새로운-게임-2/), [문제별 공개 기록 3](https://unluckyjung.github.io/ps/2020/04/19/BOJ17837/).


