package daily.day20261117;

import java.util.*;

/*
3. 세 번 이동할 때마다 쉬어 가기

n*n 목초지에서 소가 (0,0)을 출발해 (n-1,n-1)로 이동한다.
상하좌우 인접 칸으로 한 번 이동할 때마다 moveTime초가 걸리고 격자 밖으로 나갈 수 없다.
누적 이동 횟수가3의 배수일 때마다, 방금 도착한 칸의 풀을 반드시 먹는다.
풀을 먹는 데 grass[r][c]초가 추가로 걸린다. 출발 직전에는 풀을 먹지 않는다.
목표에 도착한 이동이3번째,6번째,...라면 목표 칸에서도 풀을 먹은 시간까지 포함한다.
이전에 지난 칸을 다시 방문할 수 있으며, 풀은 먹은 뒤에도 다음 방문 시 조건에 따라 다시 먹는다.
도착까지 걸리는 최소 총 시간을 반환하라. 이동 횟수만 최소화하는 문제가 아니다.

제한사항
- 3 <= grass.length <= 100, grass는 정사각형이다.
- 0 <= moveTime <= 1000000
- 1 <= grass[r][c] <= 100000

입출력 예시
grass가3*3이고 모든 값이1, moveTime=1 -> 5
최소 네 번 이동하고 세 번째 이동에서 풀을 한 번 먹어4+1=5다.
같은 지도에서 moveTime=0 -> 1
Main의 마지막 예제 -> 31
*/
public class Main3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        int[][] grass = {{1,1,1},{1,1,1},{1,1,1}};
        System.out.println(solution.solution(grass, 1)); // 5
        System.out.println(solution.solution(grass, 0)); // 1
        System.out.println(solution.solution(new int[][]{
            {30,92,36,10},{38,85,60,16},{41,13,5,68},{20,97,13,80}
        }, 2)); // 31
    }
}

class Solution3 {
    public int solution(int[][] grass, int moveTime) {
        // 여기에 구현하세요.
        throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
    }
}

