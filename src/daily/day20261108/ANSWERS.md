# 2026-11-08 정답 및 해설

각 항목은 완성 Solution 코드를 먼저 제공한 뒤 알고리즘·선택 이유·실행 순서·핵심 역할·실제 예제 추적·복잡도를 설명합니다. 전체 Main은 answers 패키지에서 실행하세요.

## 원본 확인 범위

오늘1~5번 모두 백준 페이지 직접 조회에서 정상 본문을 얻지 못했습니다. 오류 또는 안내 페이지가 표시되어 직접 원문 대조를 완료한 것은 아닙니다. 공개된 문제별 기록에서 번호·제목·입력 제한·핵심 규칙·예제를 대조했습니다. 아래에 원본 링크와 대조 출처를 함께 남깁니다. 기존 문제의 입출력을 메서드 호출/반환으로 옮겼으며 작은 예제는 학습용 입력입니다.

## 1. 같은 숫자도 다시 골라 수열 만들기

[완성 Main1.java](answers/Main1.java)

```java
import java.util.*;

class Solution1 {
    int[] numbers;
    int[] choice;
    List<int[]> answer;

    public int[][] solution(int[] numbers, int m) {
        this.numbers = numbers.clone();
        Arrays.sort(this.numbers);
        choice = new int[m];
        answer = new ArrayList<>();
        dfs(0, 0);

        int[][] result = new int[answer.size()][m];
        for (int i = 0; i < answer.size(); i++) {
            result[i] = answer.get(i);
        }
        return result;
    }

    private void dfs(int start, int count) {
        if (count == choice.length) {
            answer.add(choice.clone());
            return;
        }

        for (int i = start; i < numbers.length; i++) {
            choice[count] = numbers[i];
            // 같은 숫자를 다시 고를 수 있으므로 i + 1이 아니라 i부터 시작한다.
            dfs(i, count + 1);
        }
    }
}
```

1. 알고리즘: 정렬 후 재귀 DFS로 중복 선택이 가능한 비내림차순 수열을 만든다.
2. 이유: 같은 숫자를 재사용하면서 작은 숫자로 되돌아가면 안 된다. 후보의 시작 인덱스를 유지하면 두 조건을 함께 표현할 수 있다.
3. 실행 순서: 입력 복사·정렬 -> 현재 깊이의 choice에 후보 기록 -> 다음 깊이는 같은 인덱스부터 -> m개면 복사 저장 -> 다음 후보 탐색.
4. 핵심: dfs(i, count+1)은 반복 선택, dfs(i+1,...)은 같은 원소 재사용 금지라는 차이가 있다. 방문 배열은 필요 없고 choice는 다음 선택에서 덮어쓴다. clone으로 완성된 결과를 보존한다.
5. 예제 추적: 정렬하면[3,7,12]. choice[0]=3 -> choice[1]=3으로[3,3]저장 -> choice[1]=7로[3,7] ->12로[3,12]. 첫 칸이7이면 다음 후보도7부터라[7,7],[7,12]가 나오고 마지막[12,12]가 나온다.
6. 복잡도: n개에서 m개 중복 선택 결과 수를 P=C(n+m-1,m)라 하면 시간 O(n log n+P*m), 결과 공간 O(P*m), 작업 공간 O(n+m).

원본: [백준 15657 — N과 M (8)](https://www.acmicpc.net/problem/15657)

대조 출처: [문제별 공개 기록 1](https://lycos7560.com/cpp/baekjoon_online_judge/백준-15657번-n과-m-8-c-baekjoon/34430/).

## 2. 울타리 안에서 살아남은 동물

[완성 Main2.java](answers/Main2.java)

```java
import java.util.*;

class Solution2 {
    int n;
    int m;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};
    boolean[][] visited;

    public int[] solution(String[] yard) {
        n = yard.length;
        m = yard[0].length();
        visited = new boolean[n][m];
        int sheep = 0;
        int wolves = 0;

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (yard[r].charAt(c) != '#' && !visited[r][c]) {
                    int[] count = bfs(r, c, yard);
                    if (count[0] > count[1]) {
                        sheep += count[0];
                    } else {
                        wolves += count[1];
                    }
                }
            }
        }
        return new int[]{sheep, wolves};
    }

    private int[] bfs(int startRow, int startCol, String[] yard) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;
        int sheep = 0;
        int wolves = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];
            char cell = yard[r].charAt(c);
            if (cell == 'o') {
                sheep++;
            } else if (cell == 'v') {
                wolves++;
            }

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && yard[nr].charAt(nc) != '#' && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return new int[]{sheep, wolves};
    }
}
```

1. 알고리즘: BFS로 영역을 나누고 영역별 양·늑대 수를 비교한다.
2. 이유: 전체 동물 수를 한 번에 비교하면 울타리로 떨어진 영역끼리 영향을 주게 된다. 영역 하나를 끝까지 센 뒤 생존 규칙을 적용해야 한다.
3. 실행 순서: 미방문 열린 칸 발견 -> BFS에서 동물 수 집계 -> 양이 엄격하게 많으면 양만 합산, 그 외 늑대만 합산 -> 모든 영역 반복.
4. 핵심: 거리 계산은 필요 없어서 visited만 사용한다. BFS의 sheep,wolves는 호출마다0으로 시작한다. 바깥으로 열린 부분도 순회하지만 입력 보장상 동물이 없어0만 더해지므로 결과에 영향이 없다.
5. 예제 추적: 첫 예제에서(1,1)부터 방문하면 o,o,v 세 칸이 이어져 반환[2,1], 전체 sheep=2. 다음 미방문 동물 칸(1,5)은 단독v라[0,1], 전체 wolves=1이다. 동률 예제[1,1]은 >가 거짓이라 늑대1마리만 남는다.
6. 복잡도: 시간·공간 O(n*m). 최대62500칸이라 재귀 깊이 부담 없이 Queue+LinkedList를 사용한다.

원본: [백준 3184 — 양](https://www.acmicpc.net/problem/3184)

대조 출처: [문제별 공개 기록 1](https://sangminlog.tistory.com/entry/boj-3184), [문제별 공개 기록 2](https://blog.system32.kr/470?category=852535).

## 3. 두 종류 시설에 모두 가까운 집

[완성 Main3.java](answers/Main3.java)

```java
import java.util.*;

class Solution3 {
    public int solution(int n, int[][] roads, int[] burgers, int burgerLimit,
                        int[] coffees, int coffeeLimit) {
        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            graph[road[0]].add(new int[]{road[1], road[2]});
            graph[road[1]].add(new int[]{road[0], road[2]});
        }

        int[] burgerDist = dijkstra(burgers, n, graph);
        int[] coffeeDist = dijkstra(coffees, n, graph);
        int answer = Integer.MAX_VALUE;

        for (int current = 1; current <= n; current++) {
            // 도로 길이가 양수이므로 거리0인 정점은 해당 종류의 시설이다.
            if (burgerDist[current] > 0 && burgerDist[current] <= burgerLimit
                    && coffeeDist[current] > 0 && coffeeDist[current] <= coffeeLimit) {
                answer = Math.min(answer, burgerDist[current] + coffeeDist[current]);
            }
        }
        return answer == Integer.MAX_VALUE ? -1 : answer;
    }

    private int[] dijkstra(int[] starts, int n, List<int[]>[] graph) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[1], b[1]));

        for (int start : starts) {
            dist[start] = 0;
            pq.offer(new int[]{start, 0});
        }

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
                    pq.offer(new int[]{next, nextCost});
                }
            }
        }
        return dist;
    }
}
```

1. 알고리즘: 시설 종류별 다중 시작점 다익스트라 두 번.
2. 이유: 시설 수가 많아 각 시설마다 탐색하면 느리다. 한 종류의 모든 시설을 거리0으로 PQ에 넣으면 각 정점에서 가장 가까운 해당 시설까지의 거리를 한 번에 얻는다.
3. 실행 순서: 양방향 그래프 -> 맥도날드를 모두 시작점으로 탐색 -> 스타벅스도 같은 방식 -> 시설 정점 제외 및 두 제한 검사 -> 거리 합 최소 반환.
4. 핵심: 모든 간선 길이가 양수라 거리0은 시설 자체뿐이다. 따라서 거리>0조건으로 후보 시설을 제외하며 중복 시설용 visited를 추가하지 않는다. 도달 불가 INF는 제한보다 크므로 제외된다. 두 거리의 합은 유효 입력에서 int 범위 안이다. 가상 정점이나 별도 Node를 만들 필요 없다.
5. 예제 추적: burger PQ는[(1,0),(2,0)]으로 시작해4번까지1,3번까지2를 기록한다. coffee PQ는[(5,0),(6,0)]으로 시작해4번까지3,3번까지5를 기록한다. 3번 집은 커피 거리5가 제한4를 넘어서 제외하고4번은1+3=4를 선택한다. 같은 정점에 두 종류 시설이 있어도 두 탐색이 독립적이라 가능하다.
6. 복잡도: 정점 n, 간선 e, 전체 시설 수 s라 할 때 시간 O((n+e+s)*log(n+e+s)), 공간 O(n+e+s). 두 번 탐색의 상수는 생략한다.

원본: [백준 13911 — 집 구하기](https://www.acmicpc.net/problem/13911)

대조 출처: [문제별 공개 기록 1](https://wonsjung.tistory.com/260), [문제별 공개 기록 2](https://kalpaca.tistory.com/102).

## 4. 두 버튼으로 목표 숫자 맞추기

[완성 Main4.java](answers/Main4.java)

```java
import java.util.*;

class Solution4 {
    public String solution(int start, int limit, int goal) {
        int[] dist = new int[100000];
        Arrays.fill(dist, -1);
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        dist[start] = 0;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == goal) {
                return String.valueOf(dist[current]);
            }
            if (dist[current] == limit) {
                continue;
            }

            int nextA = current + 1;
            if (nextA <= 99999 && dist[nextA] == -1) {
                dist[nextA] = dist[current] + 1;
                queue.offer(nextA);
            }

            int doubled = current * 2;
            if (current > 0 && doubled <= 99999) {
                int place = 1;
                while (place * 10 <= doubled) {
                    place *= 10;
                }
                int nextB = doubled - place;
                if (dist[nextB] == -1) {
                    dist[nextB] = dist[current] + 1;
                    queue.offer(nextB);
                }
            }
        }
        return "ANG";
    }
}
```

1. 알고리즘: 숫자를 상태로 보는 BFS와 버튼 동작 구현.
2. 이유: 모든 버튼이1회이고 목표까지의 최소 횟수가 필요하다. 버튼B가 값을 줄일 수도 있으므로 순서대로 값을 증가시키는 방식으로는 해결할 수 없다.
3. 실행 순서: start거리0 -> 목표면 반환 -> 제한 횟수면 확장 중단 -> A와B의 유효 결과 계산 -> 미방문 숫자를 큐에 넣음 -> 도달 불가면ANG.
4. 핵심: B의 넘침 검사는 자릿수 감소 전 doubled에서 한다. place를1,10,100,...으로 키워 최고 자리의 단위를 찾고 한 번 빼면 그 자리 숫자가1 줄어든다. current=0인 B는 같은 상태라 큐에 다시 넣을 필요 없다. dist가 방문과 횟수를 겸한다.
5. 예제 추적: start123에서 dist[123]=0. A결과124를거리1로넣는다. B의 doubled=246,place는1->10->100에서 멈추고 nextB=146이라 거리1로넣는다. 목표146을 꺼내면1을 반환한다. start=goal이면 확장 전에0을 반환한다.
6. 복잡도: 숫자 상태는100000개이고 상태마다 두 동작과 최대5자리 검사를 하므로 이 제한에서 시간·공간 O(100000). 일반적으로 상태 수 U에 대한 시간 상한은 O(U log U), 공간 O(U)이다.

원본: [백준 16397 — 탈출](https://www.acmicpc.net/problem/16397)

대조 출처: [문제별 공개 기록 1](https://kibbomi.tistory.com/247), [문제별 공개 기록 2](https://dev-gorany.tistory.com/172).

## 5. 물고기를 움직이고 다음 먹이를 고르기

[완성 Main5.java](answers/Main5.java)

```java
import java.util.*;

class Solution5 {
    int answer;
    int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
    int[] dc = {0, -1, -1, -1, 0, 1, 1, 1};

    public int solution(int[][] numbers, int[][] directions) {
        answer = 0;
        dfs(0, 0, 0, numbers, directions);
        return answer;
    }

    private void dfs(int sharkRow, int sharkCol, int sum,
                     int[][] numbers, int[][] directions) {
        // 각 선택은 자기 판에서 진행하여 다른 선택의 상태와 섞이지 않게 한다.
        int[][] map = copy(numbers);
        int[][] dirs = copy(directions);

        sum += map[sharkRow][sharkCol];
        int sharkDirection = dirs[sharkRow][sharkCol] - 1;
        map[sharkRow][sharkCol] = 0;
        answer = Math.max(answer, sum);

        moveFish(map, dirs, sharkRow, sharkCol);

        for (int step = 1; step <= 3; step++) {
            int nr = sharkRow + dr[sharkDirection] * step;
            int nc = sharkCol + dc[sharkDirection] * step;
            if (nr >= 0 && nr < 4 && nc >= 0 && nc < 4
                    && map[nr][nc] > 0) {
                dfs(nr, nc, sum, map, dirs);
            }
        }
    }

    private void moveFish(int[][] map, int[][] dirs, int sharkRow, int sharkCol) {
        for (int number = 1; number <= 16; number++) {
            int r = -1;
            int c = -1;
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    if (map[row][col] == number) {
                        r = row;
                        c = col;
                    }
                }
            }

            // 이미 먹힌 물고기는 이동하지 않는다.
            if (r == -1) {
                continue;
            }

            int direction = dirs[r][c] - 1;
            for (int turn = 0; turn < 8; turn++) {
                int nextDirection = (direction + turn) % 8;
                int nr = r + dr[nextDirection];
                int nc = c + dc[nextDirection];
                if (nr >= 0 && nr < 4 && nc >= 0 && nc < 4
                        && !(nr == sharkRow && nc == sharkCol)) {
                    // 상대 번호와 방향을 원래 칸으로 옮긴 뒤, 현재 물고기를 목적지에 둔다.
                    map[r][c] = map[nr][nc];
                    dirs[r][c] = dirs[nr][nc];
                    map[nr][nc] = number;
                    dirs[nr][nc] = nextDirection + 1;
                    break;
                }
            }
        }
    }

    private int[][] copy(int[][] source) {
        int[][] result = new int[4][4];
        for (int r = 0; r < 4; r++) {
            result[r] = source[r].clone();
        }
        return result;
    }
}
```

1. 알고리즘: 물고기 순서 이동 시뮬레이션과 상어의 목적지를 고르는 재귀 DFS·완전탐색. 골드2 수준의 복합 문제다.
2. 이유: 물고기의 동작은 정해져 있지만 상어가 한 직선의 어느 물고기를 먹는지에 따라 이후 판 전체가 달라진다. 당장 번호가 큰 물고기만 먹는 선택으로는 최댓값을 보장할 수 없다.
3. 실행 순서: 이번 호출의 판 복사 -> 목적지 물고기 섭취와 방향 갱신 ->1번부터16번까지 살아 있는 물고기 이동 -> 상어 직선의1~3칸 중 물고기가 있는 칸마다 재귀 -> 최대 합 반환.
4. 핵심: map은 물고기 번호,dirs는 그 번호가 있는 칸의 방향이다. 교환할 때 둘 다 옮겨야 한다.0은 빈칸이며 상어 위치는 별도 좌표로 관리한다. 물고기 이동은 번호 기준으로 매번 현재 위치를 찾는다. 이 판은16칸뿐이라 별도의 위치 자료구조를 만들 필요가 없다. 자식 호출이 자기 배열을 복사하므로 부모 판을 바꾸지 않아 명시적 원상 복구가 필요 없다. 한 번 먹을 때마다 물고기가 줄어들어 visited도 필요 없다.
5. 예제 추적: 답33예제에서(0,0)의7을 먹어 sum=7,상어 방향6(오른쪽 아래)이 된다. 1번은(1,1),방향8이라(0,2)의15번과 교환하고 방향8을 유지한다. 2번은(0,1)에서 왼쪽의 상어를 만나므로 다음 반시계 방향4로 돌려(1,0)의3번과 교환한다. 이후 나머지도 순서대로 이동한다. 모든 물고기 이동이 끝나면 상어는 오른쪽 아래 직선상의 유효 먹이들을 각각 시도한다. 각 자식은 별도 판을 가져 한 선택의 교환·삭제가 다른 선택에 섞이지 않는다. 전체 탐색의 최대 합은33이다.
6. 복잡도: 물고기 수를 F=16으로 두면 각 상태의 물고기 위치 검색·이동은 O(F^2), 분기는 최대3개이고 먹는 깊이는 최대F다. 보수적 시간 상한 O(3^F*F^2), 재귀 경로별 판 복사를 포함한 공간 O(F^2)이다. 실제로는 빈칸·경계·먹이 감소로 분기가 줄어든다.

원본: [백준 19236 — 청소년 상어](https://www.acmicpc.net/problem/19236)

대조 출처: [문제별 공개 기록 1](https://hjpark00.tistory.com/69), [문제별 공개 기록 2](https://rational331.tistory.com/75?category=733319), [문제별 공개 기록 3](https://coder-in-war.tistory.com/entry/BOJ-JAVA19236-청소년-상어?category=936483).


