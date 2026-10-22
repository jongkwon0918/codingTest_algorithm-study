package daily.day20261022;

import java.util.*;

/*
4. 모두의 이동 거리가 가장 작은 방

1부터 n까지 번호가 붙은 방들이 양방향 통로로 연결되어 있다.
passages[i]={a,b,c}는 a와 b 사이의 길이 c인 통로를 의미한다.
friends에는 친구들이 현재 있는 방 번호가 주어진다. 친구들은 각자 최단 경로로 모임 방에 간다.
친구들의 이동 거리 합이 최소가 되는 모임 방 번호를 반환하라.
그러한 방이 여러 개라면 번호가 가장 작은 방을 선택한다.
친구가 처음부터 모임 방에 있으면 그 친구의 이동 거리는 0이다.

제한사항
- 2 <= n <= 100
- n-1 <= passages.length <= n*(n-1)/2
- 1 <= a,b <= n, a != b, 1 <= c <= 1,000
- 같은 두 방을 잇는 통로는 하나이며 모든 방 사이에 이동할 수 있다.
- 1 <= friends.length <= n, 친구의 방 번호는 서로 다르다.
- 원본의 테스트 케이스 하나를 solution 호출 한 번으로 전달한다.

입출력 예시
n=4, passages={{1,2,1},{2,3,1},{3,4,1}}, friends={1,4} -> 1
모든 방에서 거리 합이 3으로 같으므로 가장 작은 번호 1이다.
같은 통로에서 friends={1,3,4} -> 3
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        int[][] passages = {{1,2,1},{2,3,1},{3,4,1}};
        System.out.println(solution.solution(4, passages, new int[]{1,4})); // 1
        System.out.println(solution.solution(4, passages, new int[]{1,3,4})); // 3
        System.out.println(solution.solution(4, passages, new int[]{4})); // 4
    }
}

class Solution4 {
    public int solution(int n, int[][] passages, int[] friends) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

