package daily.day20261025;

import java.util.*;

/*
1. 여섯 개를 고르는 모든 방법

오름차순으로 주어진 서로 다른 숫자들 중 정확히 여섯 개를 고르는 모든 방법을 반환하라.
한 방법 안의 숫자는 오름차순이어야 하며 같은 숫자를 두 번 고를 수 없다.
결과의 각 행은 한 방법이고, 행들은 사전순으로 정렬되어야 한다.
사전순은 앞에서부터 비교해 처음 다른 숫자가 작은 행이 먼저 오는 순서이다.

제한사항
- 7 <= numbers.length <= 12
- 각 숫자는 1 이상 49 이하이며 중복 없이 오름차순이다.
- 원본의 테스트 케이스 하나를 solution 호출 한 번으로 전달한다.

입출력 예시
numbers={1,2,3,4,5,6,7} ->
{{1,2,3,4,5,6},
 {1,2,3,4,5,7},
 {1,2,3,4,6,7},
 {1,2,3,5,6,7},
 {1,2,4,5,6,7},
 {1,3,4,5,6,7},
 {2,3,4,5,6,7}}
numbers={1,2,3,5,8,13,21,34} -> 총28행
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        int[][] answer1 = solution.solution(new int[]{1,2,3,4,5,6,7});
        for (int[] row : answer1) {
            System.out.println(Arrays.toString(row));
        }
        int[][] answer2 = solution.solution(new int[]{1,2,3,5,8,13,21,34});
        for (int[] row : answer2) {
            System.out.println(Arrays.toString(row));
        }
    }
}

class Solution1 {
    int n;
    List<int[]> list;
    int[] choice;
    public int[][] solution(int[] numbers) {
        n = numbers.length;
        list = new ArrayList<>();
        choice = new int[6];

        for(int i=0; i<=n-6; i++){
            choice[0] = numbers[i];
            dfs(i, numbers, 1);
        }

        int[][] answer = new int[list.size()][6];

        for(int i=0; i<list.size(); i++){
            answer[i] = list.get(i);
        }

        return answer;
    }

    private void dfs(int start, int[] numbers, int repeat) {
        if(repeat==6){
            list.add(choice.clone());
            return;
        }

        for(int i=start+1; i<n; i++){
            choice[repeat] = numbers[i];
            dfs(i, numbers, repeat+1);
        }
    }
}

