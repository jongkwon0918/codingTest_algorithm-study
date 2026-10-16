package daily.day20261012;

import java.util.*;

/*
4. 조건을 만족하는 암호 후보

중복 없는 영어 소문자 letters가 주어진다. 이 문자 중 length개를 골라 암호를 만든다.
암호의 문자는 알파벳 오름차순이어야 하며, 각 문자는 최대 한 번 사용한다.
암호에는 모음(a,e,i,o,u)이 최소 1개, 자음이 최소 2개 있어야 한다.
조건을 만족하는 모든 암호를 사전순 문자열 배열로 반환하라.
가능한 암호가 없으면 빈 배열을 반환한다.

제한사항
- 3 <= length <= letters.length() <= 15
- letters는 중복 없는 영어 소문자로 구성되며 입력 순서는 정렬되어 있지 않을 수 있다.

입출력 예시
length=3, letters="dcba" -> ["abc","abd","acd"]
length=3, letters="aei" -> []
length=4, letters="ibca" -> ["abci"]
*/
public class Main4 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
        System.out.println(Arrays.toString(solution.solution(3, "dcba"))); // [abc, abd, acd]
        System.out.println(Arrays.toString(solution.solution(3, "aei"))); // []
        System.out.println(Arrays.toString(solution.solution(4, "ibca"))); // [abci]
    }
}

class Solution4 {
    char[] letter;
    char[] selected;
    List<String> list;
    int n;
    public String[] solution(int length, String letters) {
        n = letters.length();
        list = new ArrayList<>();
        letter = letters.toCharArray();
        Arrays.sort(letter);
        selected = new char[length];

        dfs(0,0, length);

        String[] answer = new String[list.size()];
        for(int i=0; i<list.size(); i++){
            answer[i] = list.get(i);
        }

        return answer;
    }

    private void dfs(int curr, int count, int length) {

        if(count==length){
            int mo = 0;

            for(char c : selected){
                if(c=='a' || c=='e' || c=='i' || c=='o' || c=='u'){
                    mo++;
                }
            }
            if(mo>=1 && length-mo>=2){
                list.add(new String(selected));
            }
            return;
        }

        for(int i=curr; i<n; i++){
            selected[count] = letter[i];
            dfs(i+1, count+1, length);
        }
    }

}

