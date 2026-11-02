package daily.day20261102.answers;

import java.util.*;

/*
1. 노드를 지운 뒤 남은 끝 노드

0번부터 n-1번까지의 노드로 이루어진 트리가 주어진다.
parents[i]는 i번 노드의 부모 번호이며, 루트의 부모는-1이다.
removed번 노드와 그 아래의 모든 자손 노드를 삭제한다.
삭제 후 남은 트리에서 자식이 없는 노드의 개수를 반환하라.
원래 자식이 있던 노드도 자식이 모두 삭제되면 끝 노드가 될 수 있다.
루트를 지우면 남은 노드는 없으므로0을 반환한다.
한 노드의 자식 수가 최대 두 개라고 가정하면 안 된다.

제한사항
- 1 <= parents.length = n <= 50
- parents는 유효한 트리를 나타내고 부모가-1인 노드는 정확히 하나이다.
- 그 외 부모 번호는0 이상 n-1 이하이다.
- 0 <= removed < n

입출력 예시
parents={-1,0,0,1,1}, removed=2 -> 2
3번과4번이 끝 노드다.
같은 트리에서 removed=1 -> 1
같은 트리에서 removed=0 -> 0
parents={-1,0,1}, removed=2 -> 1
1번의 유일한 자식2번이 사라져1번이 끝 노드가 된다.
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        int[] parents = {-1,0,0,1,1};
        System.out.println(solution.solution(parents, 2)); // 2
        System.out.println(solution.solution(parents, 1)); // 1
        System.out.println(solution.solution(parents, 0)); // 0
        System.out.println(solution.solution(new int[]{-1,0,1}, 2)); // 1
    }
}

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

