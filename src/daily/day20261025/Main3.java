package daily.day20261025;

import java.util.*;

/*
3. 방 색을 최소한으로 바꾸기

n*n 방 배열에서 '1'은 흰 방, '0'은 검은 방이다.
흰 방은 자유롭게 통과할 수 있고 검은 방은 원래 통과할 수 없다.
일부 검은 방을 흰 방으로 바꾸어 왼쪽 위에서 오른쪽 아래까지 이동하려 한다.
상하좌우로 이동할 수 있으며, 바꿔야 하는 검은 방 수의 최솟값을 반환하라.
이동 칸 수를 최소로 만드는 문제가 아니다.
출발 방과 도착 방은 흰 방이다.

제한사항
- 1 <= n <= 50
- rooms는 길이n인 문자열n개로 구성된다.
- 문자는0 또는1이다.

입출력 예시
rooms={"11","11"} -> 0
rooms={"10","01"} -> 1
rooms={"111","000","111"} -> 1
가운데 줄의 검은 방 하나만 바꾸면 된다.
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        System.out.println(solution.solution(new String[]{"11","11"})); // 0
        System.out.println(solution.solution(new String[]{"10","01"})); // 1
        System.out.println(solution.solution(new String[]{"111","000","111"})); // 1
    }
}

class Solution3 {
    public int solution(String[] rooms) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

