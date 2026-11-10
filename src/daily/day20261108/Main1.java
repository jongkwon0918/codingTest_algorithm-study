package daily.day20261108;

import java.util.*;

/*
1. 같은 숫자도 다시 골라 수열 만들기

서로 다른 자연수들이 numbers에 주어진다. 이 숫자들로 길이 m인 모든 수열을 만들어 반환하라.
같은 숫자를 여러 번 선택할 수 있으며, 한 수열 안의 숫자는 앞에서 뒤로 작아지면 안 된다.
즉 a[0] <= a[1] <= ... <= a[m-1]을 만족해야 한다.
결과의 각 행이 수열 하나이며, 전체 행은 사전순으로 반환한다.
입력 숫자가 정렬되어 있다는 보장은 없다. 중복된 결과 행을 반환하면 안 된다.

제한사항
- 1 <= m <= numbers.length <= 8
- numbers의 값은1 이상10000 이하이며 서로 다르다.
- 반환형은 int[][]이다. 두 자리 이상 숫자도 한 원소로 보관한다.

입출력 예시
numbers={12,3,7}, m=2 ->
{{3,3},{3,7},{3,12},{7,7},{7,12},{12,12}}
numbers={42}, m=1 -> {{42}}
numbers={9,1}, m=2 -> {{1,1},{1,9},{9,9}}
*/
public class Main1 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        for (int[] row : solution.solution(new int[]{12,3,7}, 2)) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println(Arrays.deepToString(solution.solution(new int[]{42}, 1))); // [[42]]
        System.out.println(Arrays.deepToString(solution.solution(new int[]{9,1}, 2)));
        // [[1, 1], [1, 9], [9, 9]]
    }
}

class Solution1 {
    List<int[]> list;
    int n;
    int[] numbers;
    public int[][] solution(int[] numbers, int m) {
        this.numbers = numbers.clone();
        list = new ArrayList<>();
        n = numbers.length;
        Arrays.sort(this.numbers);
        for(int i=0; i<n; i++){
            int[] tempList = new int[m];
            dfs(i, 0, m, tempList);
        }


        int[][] answer = new int[list.size()][m];
        for(int i=0; i<list.size(); i++){
            for(int j=0; j<m; j++){
                answer[i][j] = list.get(i)[j];
            }
        }

        return answer;
    }

    private void dfs(int start, int index, int m, int[] tempList) {
        tempList[index]=numbers[start];
        if (index==m-1){
            list.add(tempList.clone());
            return;
        }

        for(int i=start; i<n; i++){
            dfs(i, index+1, m, tempList);
        }
    }
}

