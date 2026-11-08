package daily.day20261102;

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

긴 예시: 노드12개
노드 번호:  0  1  2  3  4  5  6  7  8  9 10 11
parents:   -1  0  0  0  1  1  2  3  4  4  6  6
예: parents[4]=1은 4번의 부모가1번이라는 뜻이다.

0
├─ 1
│  ├─ 4
│  │  ├─ 8
│  │  └─ 9
│  └─ 5
├─ 2
│  └─ 6
│     ├─ 10
│     └─ 11
└─ 3
   └─ 7

삭제 전 끝 노드는 {5,7,8,9,10,11}이다.
각 경우는 위 원본 트리에서 독립적으로 시작한다. 삭제를 누적하지 않는다.

경우1) removed=1 -> 3
삭제되는 노드는 {1,4,5,8,9}이다. 1번뿐 아니라 그 아래 자손도 전부 없어진다.
남은 연결: 0의 자식{2,3}, 2의 자식{6}, 6의 자식{10,11}, 3의 자식{7}.
남은 끝 노드는 {7,10,11}이므로 답은3이다.

경우2) removed=6 -> 5
삭제되는 노드는 {6,10,11}이다.
2번은 남아 있지만 유일한 자식6번이 없어져 새 끝 노드가 된다.
남은 끝 노드는 {2,5,7,8,9}이므로 답은5이다.

경우3) removed=4 -> 4
삭제되는 노드는 {4,8,9}이다.
1번에는 자식5번이 남으므로 1번은 끝 노드가 아니다.
남은 끝 노드는 {5,7,10,11}이므로 답은4이다.

경우4) removed=0 -> 0
루트와 모든 자손이 없어져 남은 노드가 없다.
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        int[] parents = {-1,0,0,1,1};
        System.out.println(solution.solution(parents, 2)); // 2
        System.out.println(solution.solution(parents, 1)); // 1
        System.out.println(solution.solution(parents, 0)); // 0
        System.out.println(solution.solution(new int[]{-1,0,1}, 2)); // 1

        // 위 문제 설명의12개 노드 트리. 각 호출은 원본 트리에서 독립적으로 시작한다.
        int[] longParents = {-1,0,0,0,1,1,2,3,4,4,6,6};
        System.out.println(solution.solution(longParents, 1)); // 기대값3: 끝 노드 {7,10,11}
        System.out.println(solution.solution(longParents, 6)); // 기대값5: 끝 노드 {2,5,7,8,9}
        System.out.println(solution.solution(longParents, 4)); // 기대값4: 끝 노드 {5,7,10,11}
        System.out.println(solution.solution(longParents, 0)); // 기대값0: 전체 삭제
    }
}

class Solution1 {
    List<Integer> removedIdx;
    boolean[] visited;
    int n;
    public int solution(int[] parents, int removed) {
        n = parents.length;
        removedIdx = new ArrayList<>();
        removedIdx.add(removed);
        visited = new boolean[n];
        dfs(removed, parents);

        int count=0;

        for(int i=0; i<n; i++){
            if(!removedIdx.contains(i)){
                boolean hasChild = false;

                for(int next = 0; next<n; next++){
                    if(!removedIdx.contains(next) && parents[next] == i){
                        hasChild = true;
                        break;
                    }
                }

                if(!hasChild){
                    count++;
                }
            }
        }

        return count;
    }

    private void dfs(int removed, int[] parents) {
        visited[removed] = true;

        for(int i = removed+1; i<n; i++){
            if(parents[i]==removed && !visited[i]){
                removedIdx.add(i);
                dfs(i, parents);
            }
        }

    }
}

