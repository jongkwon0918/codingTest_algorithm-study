package daily.day20261117;

import java.util.*;

/*
1. 나무 모양 길의 거리

1번부터 n번까지 지점이 있고, 양방향 길 n-1개가 모든 지점을 연결한다.
길들은 고리가 없는 나무 모양이다. 따라서 두 지점 사이의 단순 경로는 정확히 하나다.
roads[i]={a,b,length}는 a,b를 연결하는 길의 길이다.
queries[i]={start,target}마다 두 지점 사이 경로의 길이 합을 구해 질의 순서대로 배열로 반환하라.
길 개수가 아닌 각 길의 length를 합한다.

제한사항
- 2 <= n <= 1000, roads.length == n-1
- 1 <= queries.length <= 1000
- 지점 번호는1~n, 길의 길이는1~10000이다.
- 각 질의의 두 지점은 서로 다르다.
- 모든 지점은 연결되어 있고 고리와 중복 길은 없다.

입출력 예시
n=5, roads={{1,2,3},{1,3,2},{2,4,4},{2,5,6}},
queries={{4,3},{4,5},{1,5}} -> {9,10,9}
4->2->1->3의 길이는4+3+2=9다.
n=2, roads={{1,2,7}}, queries={{2,1}} -> {7}
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        System.out.println(Arrays.toString(solution.solution(5, new int[][]{
            {1,2,3},{1,3,2},{2,4,4},{2,5,6}
        }, new int[][]{{4,3},{4,5},{1,5}}))); // [9, 10, 9]
        System.out.println(Arrays.toString(solution.solution(2,
                new int[][]{{1,2,7}}, new int[][]{{2,1}}))); // [7]
    }
}

class Solution1 {
    public int[] solution(int n, int[][] roads, int[][] queries) {

        return new int[]{};
    }
}

