# 정답 및 해설 — 2026-10-12

정답 코드를 먼저 보여주고, 알고리즘·이유·실행 순서·핵심 코드·예제 추적·복잡도 순으로 설명합니다.

## 1. 두 가지 방문 순서

[완성된 실행 파일](answers/Main1.java)

```java
import java.util.*;

class Solution1 {
    List<Integer>[] graph;
    boolean[] visited;
    List<Integer> firstOrder;

    public int[][] solution(int n, int[][] edges, int start) {
        graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }
        for (int i = 1; i <= n; i++) {
            Collections.sort(graph[i]);
        }

        visited = new boolean[n + 1];
        firstOrder = new ArrayList<>();
        dfs(start);

        visited = new boolean[n + 1];
        List<Integer> secondOrder = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        visited[start] = true;
        queue.offer(start);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            secondOrder.add(current);
            for (int next : graph[current]) {
                if (!visited[next]) {
                    visited[next] = true;
                    queue.offer(next);
                }
            }
        }
        return new int[][]{toArray(firstOrder), toArray(secondOrder)};
    }

    private void dfs(int current) {
        visited[current] = true;
        firstOrder.add(current);
        for (int next : graph[current]) {
            if (!visited[next]) {
                dfs(next);
            }
        }
    }

    private int[] toArray(List<Integer> order) {
        int[] result = new int[order.size()];
        for (int i = 0; i < order.size(); i++) {
            result[i] = order.get(i);
        }
        return result;
    }
}
```

1. **알고리즘:** 재귀 DFS와 Queue를 사용하는 BFS.
2. **이유:** 첫 규칙은 이웃을 끝까지 따라간 뒤 돌아오는 탐색이다. 두 번째는 발견한 정점을 순서대로 처리하는 탐색이다.
3. **실행 순서:** 양방향 그래프 → 이웃 번호 정렬 → DFS → 방문 배열 초기화 → BFS → 두 방문 순서를 배열로 변환.
4. **핵심 코드:** 정렬은 이웃의 선택 순서를 보장한다. 두 탐색은 독립적이므로 visited를 새로 만든다. DFS는 함수 시작에서, BFS는 큐 삽입 시 방문 처리한다. 중복 간선도 방문 검사로 안전하게 처리한다.
5. **예제 추적:** dfs(1) → visited[1]=true → dfs(2) → dfs(4) → dfs(3) → 종료, 첫 순서=[1,2,4,3]. 방문 배열 초기화 → queue=[1] → 1을 꺼내 [2,3,4] 삽입 → 2에서 4는 이미 방문했으므로 넣지 않음 → 3 → 4를 꺼내 두 번째 순서=[1,2,3,4].
6. **복잡도:** 정렬 포함 시간 O(V+E log E), 공간 O(V+E). DFS 재귀 깊이는 최대 V이며 여기서는 1,000 이하이다.

원본: **백준 1260 — DFS와 BFS**

[원본 문제](https://www.acmicpc.net/problem/1260) · [문제 조건 대조 기록](https://trying-ce-student.tistory.com/18)

## 2. 이동한 경로까지 찾기

[완성된 실행 파일](answers/Main2.java)

```java
import java.util.*;

class Solution2 {
    public int[][] solution(int start, int target) {
        int limit = 100000;
        int[] dist = new int[limit + 1];
        int[] parent = new int[limit + 1];
        Arrays.fill(dist, -1);
        dist[start] = 0;
        parent[start] = start;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == target) {
                break;
            }
            int[] nextPositions = {current - 1, current + 1, current * 2};
            for (int next : nextPositions) {
                if (next >= 0 && next <= limit && dist[next] == -1) {
                    dist[next] = dist[current] + 1;
                    parent[next] = current;
                    queue.offer(next);
                }
            }
        }

        int[] path = new int[dist[target] + 1];
        int current = target;
        for (int i = path.length - 1; i >= 0; i--) {
            path[i] = current;
            current = parent[current];
        }
        return new int[][]{new int[]{dist[target]}, path};
    }
}
```

1. **알고리즘:** BFS와 부모 위치를 이용한 경로 복원.
2. **이유:** 세 이동의 비용이 모두 1초라 첫 방문이 최단거리이다. 시간만으로는 경로를 알 수 없으므로 직전 위치도 기록한다.
3. **실행 순서:** dist=-1 → 시작점 큐 삽입 → 세 이웃의 거리와 부모 저장 → 목표를 꺼내면 종료 → 목표부터 부모를 따라 배열을 뒤에서 채움.
4. **핵심 코드:** dist가 방문 여부까지 담당한다. parent는 경로 복원용으로 역할이 다르다. 큐에는 위치만 저장한다. 0~100,000 범위에서 최단 경로를 찾을 수 있다. 범위를 넘어갔다가 되돌아오는 순간이동 경로보다, 경계 안에서 목표 근처로 이동하는 경로가 짧거나 같으므로 탐색 범위를 이 구간으로 제한해도 된다.
5. **예제 추적:** queue=[5] → 5에서 [4,6,10] 발견, 거리1 → 4에서 3과8 발견, dist[8]=2,parent[8]=4 → 8에서 16을 발견해 dist[16]=3,parent[16]=8 → 16에서 17 발견, dist[17]=4,parent[17]=16. 복원: path[4]=17 → path[3]=16 → path[2]=8 → path[1]=4 → path[0]=5. 다른 최단 경로도 허용된다.
6. **복잡도:** 탐색 범위 크기를 B=100,001이라 하면 시간 O(B), 공간 O(B).

원본: **백준 13913 — 숨바꼭질 4**

[원본 문제](https://www.acmicpc.net/problem/13913) · [문제 조건 대조 기록](https://suhanlim.tistory.com/280)

## 3. 아이템을 가장 많이 모을 시작점

[완성된 실행 파일](answers/Main3.java)

```java
import java.util.*;

class Solution3 {
    public int solution(int[] items, int range, int[][] roads) {
        int n = items.length;
        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            graph[road[0]].add(new int[]{road[1], road[2]});
            graph[road[1]].add(new int[]{road[0], road[2]});
        }

        int answer = 0;
        for (int start = 1; start <= n; start++) {
            int[] dist = dijkstra(start, n, graph);
            int count = 0;
            for (int i = 1; i <= n; i++) {
                if (dist[i] <= range) {
                    count += items[i - 1];
                }
            }
            answer = Math.max(answer, count);
        }
        return answer;
    }

    private int[] dijkstra(int start, int n, List<int[]>[] graph) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[1], b[1]));
        pq.offer(new int[]{start, 0});

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
        return dist;
    }
}
```

1. **알고리즘:** 모든 시작점에서 다익스트라를 실행한다.
2. **이유:** 도로 길이가 서로 다르므로 가중치 최단거리가 필요하다. 가장 좋은 시작점이 정해져 있지 않아 각 후보를 비교한다. 정점이 최대 100개여서 반복 실행이 충분하다.
3. **실행 순서:** 양방향 그래프 → start=1부터 n까지 최단거리 계산 → range 이내 지역의 아이템 합산 → 최댓값 갱신.
4. **핵심 코드:** 아이템 배열은 0부터, 지역 번호는 1부터라 items[i-1]로 접근한다. dist만으로 거리 갱신 여부를 판단하며 별도 visited는 없다. 도달하지 못한 지역의 거리는 INF이므로 합산에서 제외된다. 시작점은 거리0으로 포함된다.
5. **예제 추적:** start=1이면 dist[1..3]=[0,2,4], range=2에서 아이템5+7=12. start=2: pq=[{2,0}] → dist[1]=2,dist[3]=2 → 최종 [2,0,2], 합계5+7+8=20. start=3이면 [4,2,0], 합계7+8=15. answer는12→20→20으로 바뀐다.
6. **복잡도:** 시간 O(V*((V+E) log(V+1)+V)), 공간 O(V+E).

원본: **백준 14938 — 서강그라운드**

[원본 문제](https://www.acmicpc.net/problem/14938) · [문제 조건 대조 기록](https://geumba.tistory.com/65)

## 4. 조건을 만족하는 암호 후보

[완성된 실행 파일](answers/Main4.java)

```java
import java.util.*;

class Solution4 {
    char[] letters;
    char[] selected;
    int length;
    List<String> answer;

    public String[] solution(int length, String letters) {
        this.length = length;
        this.letters = letters.toCharArray();
        Arrays.sort(this.letters);
        selected = new char[length];
        answer = new ArrayList<>();
        dfs(0, 0);

        String[] result = new String[answer.size()];
        for (int i = 0; i < answer.size(); i++) {
            result[i] = answer.get(i);
        }
        return result;
    }

    private void dfs(int start, int count) {
        if (count == length) {
            int vowel = 0;
            for (char letter : selected) {
                if (letter == 'a' || letter == 'e' || letter == 'i'
                        || letter == 'o' || letter == 'u') {
                    vowel++;
                }
            }
            if (vowel >= 1 && length - vowel >= 2) {
                answer.add(new String(selected));
            }
            return;
        }

        for (int i = start; i < letters.length; i++) {
            selected[count] = letters[i];
            dfs(i + 1, count + 1);
        }
    }
}
```

1. **알고리즘:** 재귀 DFS로 조합을 만드는 백트래킹.
2. **이유:** 문자 순서를 바꾸는 순열이 아니라 정렬된 문자에서 일부를 선택하는 조합이다. 만든 조합마다 모음·자음 조건을 검사한다.
3. **실행 순서:** 문자 정렬 → 결과 목록과 선택 배열 초기화 → 다음 인덱스부터 재귀 선택 → length개면 조건 검사 → 결과 배열 반환.
4. **핵심 코드:** dfs(i+1,count+1)로 다음 문자는 현재보다 뒤에서만 골라 중복 사용과 순서 뒤집힘을 막는다. 별도 visited는 필요 없다. selected[count]는 다음 후보가 덮어쓰므로 해제 코드도 필요 없다. 정렬된 문자를 앞에서부터 고르는 DFS 순서가 사전순이라 결과를 다시 정렬하지 않는다. answer는 solution마다 초기화한다.
5. **예제 추적:** dcba를 정렬하면 abcd. dfs(0,0) → a 선택 → b 선택 → c 선택, abc의 모음1·자음2로 저장 → 마지막 칸을 d로 덮어써 abd 저장 → 두 번째 칸을 c로 바꾸고 d 선택해 acd 저장 → 첫 칸 b에서 bcd를 만들지만 모음0이라 제외. 결과=[abc,abd,acd]. 다음 aei 호출은 자음0이라 빈 배열이다.
6. **복잡도:** C개 문자, 길이 L에 대해 정렬 O(C log C), 탐색은 느슨한 상한 O(2^C), 완성 조합 검사 O(조합(C,L)*L)이다. 전체 시간 O(C log C+2^C+조합(C,L)*L). 결과 저장 제외 추가 공간 O(C+L), 결과 저장 공간은 암호 개수 A에 대해 O(A*L).

원본: **백준 1759 — 암호 만들기**

[원본 문제](https://www.acmicpc.net/problem/1759) · [문제 조건 대조 기록](https://www.programmingstory.com/2024/02/1759.html)

## 5. 물이 차오르기 전에 탈출

[완성된 실행 파일](answers/Main5.java)

```java
import java.util.*;

class Solution5 {
    public String solution(String[] board) {
        int n = board.length;
        int m = board[0].length();
        char[][] map = new char[n][m];
        int[][] waterTime = new int[n][m];
        int[][] dist = new int[n][m];
        Queue<int[]> waterQueue = new LinkedList<>();
        Queue<int[]> queue = new LinkedList<>();
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        for (int row = 0; row < n; row++) {
            Arrays.fill(waterTime[row], -1);
            Arrays.fill(dist[row], -1);
            for (int col = 0; col < m; col++) {
                map[row][col] = board[row].charAt(col);
                if (map[row][col] == '*') {
                    waterTime[row][col] = 0;
                    waterQueue.offer(new int[]{row, col});
                } else if (map[row][col] == 'S') {
                    dist[row][col] = 0;
                    queue.offer(new int[]{row, col});
                }
            }
        }

        while (!waterQueue.isEmpty()) {
            int[] current = waterQueue.poll();
            int row = current[0];
            int col = current[1];
            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && (map[nr][nc] == '.' || map[nr][nc] == 'S')
                        && waterTime[nr][nc] == -1) {
                    waterTime[nr][nc] = waterTime[row][col] + 1;
                    waterQueue.offer(new int[]{nr, nc});
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            if (map[row][col] == 'D') {
                return String.valueOf(dist[row][col]);
            }

            for (int d = 0; d < 4; d++) {
                int nr = row + dr[d];
                int nc = col + dc[d];
                int nextTime = dist[row][col] + 1;
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && (map[nr][nc] == '.' || map[nr][nc] == 'D')
                        && dist[nr][nc] == -1
                        && (waterTime[nr][nc] == -1 || nextTime < waterTime[nr][nc])) {
                    dist[nr][nc] = nextTime;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return "KAKTUS";
    }
}
```

1. **알고리즘:** 여러 물 시작점의 BFS 후, 시간 조건을 적용한 고슴도치 BFS.
2. **이유:** 지도는 시간에 따라 달라진다. 각 칸에 물이 처음 도착하는 시각을 먼저 구하면 이동할 때 안전 여부를 판단할 수 있다.
3. **실행 순서:** 지도와 두 시간 배열 초기화 → 모든 물을 동시에 시작점으로 큐 삽입 → 물 도착 시각 계산 → S에서 고슴도치 탐색 → 굴에 도착하면 시간, 큐가 비면 KAKTUS.
4. **핵심 코드:** nextTime < waterTime이어야 이동 가능하다. 같은 시각에 물이 차는 칸은 금지하므로 <=를 쓰면 안 된다. 물은 D로 퍼지지 않으므로 굴의 waterTime은 -1이다. waterTime은 물의 도착 시각, dist는 고슴도치 거리로 서로 다른 상태이며 중복 정보가 아니다. 각 배열의 -1로 미방문을 구분해 별도 visited는 없다. 큐에는 좌표만 저장한다.
5. **예제 추적:** D.* / ... / .S.에서 물 시작 (0,2)의 시간0 → (1,2),(0,1)은1 → (2,2),(1,1)은2 → S(2,1),(1,0)은3 → (2,0)은4. 굴은 -1이다. 고슴도치 queue=[{2,1}],dist=0 → 위 (1,1)은 도착1<물2, 왼쪽 (2,0)도1<4라 허용 → (1,1)에서 왼쪽 (1,0)에 도착2<물3이므로 허용 → 굴(0,0)에 도착3, 물은 굴에 들어오지 않으므로 허용 → "3" 반환. 두 번째 지도에서는 (1,0)에 도착하는 시각3과 물 도착3이 같아 이동 불가이다.
6. **복잡도:** 물과 고슴도치가 각각 각 칸을 최대 한 번 방문하므로 시간·공간 O(R*C).

원본: **백준 3055 — 탈출**

[원본 문제](https://www.acmicpc.net/problem/3055) · [문제 조건 대조 기록](https://wooono.tistory.com/414)

## 문제 실재 여부와 형식 변환

백준 직접 조회에서는 정상 문제 본문을 받지 못했습니다. [백준 풀이 목록](https://github.com/jiy12345/Beakjoon-Online-Judge), [별도 문제 목록](https://github.com/rokrokss/baekjoon), [BaekjoonHub 기록](https://github.com/chech2/Algorithm)과 각 문제의 조건 기록을 교차 확인했습니다. 원문 직접 대조 완료라고 주장하지 않습니다.

문제의 핵심 조건을 유지하고 입력은 매개변수, 출력은 반환값으로 변경했습니다. 1번의 두 방문 순서는 배열 두 개, 2번의 시간과 경로는 {시간 배열, 경로 배열}, 4번의 줄별 암호는 문자열 배열로 반환합니다. 2번은 원본처럼 어떤 최단 경로라도 허용하며, 5번의 실패 표기 KAKTUS를 유지했습니다.

최근 4번 풀이에서 좌표뿐 아니라 남은 점프 횟수별로 상태를 구분하는 연습을 했습니다. 오늘은 직전 위치를 기록해 경로를 복원하는 상태와, 물 도착 시각을 구분하는 상태를 추가로 연습합니다. 스스로 작성 중인 풀이의 방식을 우선 존중하고 다른 버전은 요청할 때 비교합니다.

