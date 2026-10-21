package daily.day20261017;

import java.util.*;

/*
4. 위아래 숫자 집합 맞추기

두 행의 표가 있다. 위 행은 1,2,...,n이고 아래 행은 numbers이다.
위 행에서 몇 개의 숫자를 선택하면, 같은 열의 아래 숫자들도 함께 선택된다.
선택한 위 숫자의 집합과 아래 숫자의 집합이 같도록, 최대한 많은 위 숫자를 선택하라.
선택한 위 숫자들을 오름차순 배열로 반환한다. 배열 길이가 선택한 개수이다.
아래 행은 순열이 아니며 같은 숫자가 여러 번 나올 수 있다.

제한사항
- 1 <= numbers.length <= 100
- 1 <= numbers[i] <= n
- numbers[i-1]은 위 숫자 i의 바로 아래 숫자이다.

입출력 예시
numbers={3,1,1,5,5,4,6} -> [1,3,5]
선택한 위 집합 {1,3,5}, 아래 숫자는 {3,1,5}로 같은 집합이다.
numbers={2,3,2} -> [2,3]
numbers={1,2,3} -> [1,2,3]
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        int[] answer = solution.solution(new int[]{3, 1, 1, 5, 5, 4, 6});
        System.out.println(answer.length); // 3
        System.out.println(Arrays.toString(answer)); // [1, 3, 5]
        System.out.println(Arrays.toString(solution.solution(new int[]{2, 3, 2}))); // [2, 3]
        System.out.println(Arrays.toString(solution.solution(new int[]{1, 2, 3}))); // [1, 2, 3]
    }
}

class Solution4 {
    List<Integer> answer;
    int n;
    public int[] solution(int[] numbers) {
        n = numbers.length;
        answer = new ArrayList<>();
        List<Integer>[] graph = new ArrayList[n+1];
        for(int i=0; i<=n; i++){
            graph[i] = new ArrayList<>();
        }
        for(int i=1; i<=n; i++){
            graph[i].add(numbers[i-1]);
        }

        for(int i=1; i<=n; i++){
            boolean[] visited = new boolean[n+1];
            dfs(graph, visited, i, i);
        }

        int[] result = new int[answer.size()];
        for(int i=0; i<answer.size(); i++){
            result[i] = answer.get(i);
        }
        return result;
    }

    private void dfs(List<Integer>[] graph, boolean[] visited, int start, int current) {
        // 이번 출발점에서 탐색하며 현재 숫자를 방문했다고 표시한다.
        visited[current] = true;

        for (int next : graph[current]) {
            // 출발점으로 돌아오면 고리에 포함되는 숫자이다.
            if(next==start){
                answer.add(start);
                return;
            }

            // 출발점은 유지하고, 아직 방문하지 않은 다음 숫자로 이동한다.
            if(!visited[next]){
                dfs(graph, visited, start, next);
            }
        }
    }
}

