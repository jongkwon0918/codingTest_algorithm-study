package daily.day20261016.answers;

import java.util.*;

/*
1. 화살표를 따라 만나는 고리

1부터 n까지의 수가 한 번씩 들어 있는 순열 permutation이 있다.
정점 i에서 permutation[i-1]번 정점으로 향하는 화살표를 그린다.
화살표를 계속 따라가면 출발한 정점으로 돌아오는 고리가 만들어진다.
서로 다른 고리의 개수를 반환하라. 자기 자신으로 돌아오는 정점도 고리 하나이다.
배열 인덱스는 0부터지만 정점 번호는 1부터이다.

제한사항
- 2 <= permutation.length <= 1,000
- permutation은 1부터 n까지의 수를 중복 없이 포함한다.
- 원본의 테스트 케이스 하나를 solution 호출 한 번으로 전달한다.

입출력 예시
permutation={2,1,3} -> 2
고리는 1->2->1과 3->3이다.
permutation={2,3,4,1} -> 1
permutation={1,2,3,4} -> 4
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        System.out.println(solution.solution(new int[]{2, 1, 3})); // 2
        System.out.println(solution.solution(new int[]{2, 3, 4, 1})); // 1
        System.out.println(solution.solution(new int[]{1, 2, 3, 4})); // 4
    }
}

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

