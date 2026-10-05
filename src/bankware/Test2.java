/*2번. 문자열로 만들어진 이진 트리

문자열의 각 문자를 순서대로 이진 트리의 노드에 넣는다.

노드는 다음과 같은 방식으로 구성된다.

첫 번째 문자가 루트
이후 문자는 레벨 순서대로 왼쪽 자식, 오른쪽 자식 순서로 배치
배열의 인덱스를 기준으로 i번 노드의 왼쪽 자식은 2 * i + 1
오른쪽 자식은 2 * i + 2

완성된 이진 트리를 후위 순회한 결과를 반환한다.
예시
입력: "ABCDEFG"

트리:

        A
      /   \
     B     C
    / \   / \
   D   E F   G

후위 순회:

D E B F G C A

따라서 결과는

"DEBFGCA"*/
package bankware;

public class Test2 {
    public static void main(String[] args) {

        String s = "ABCDEFG";

        Solution1 solution = new Solution1();

        String answer = solution.solution(s);

        System.out.println(answer);
    }
}
class Solution1 {

    public String solution(String s) {

        char[] tree = s.toCharArray();

        StringBuilder sb = new StringBuilder();

        dfs(0, tree, sb);

        return sb.toString();
    }

    private void dfs(int index, char[] tree, StringBuilder sb) {

        if (index >= tree.length) {
            return;
        }

        dfs(index * 2 + 1, tree, sb);

        dfs(index * 2 + 2, tree, sb);

        sb.append(tree[index]);
    }
}