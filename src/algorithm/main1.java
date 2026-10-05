/*1번. 알파벳 종류 확인

문자열 s가 주어진다. 대소문자를 구분하지 않고 문자열에 등장한 알파벳 종류의 개수를 구하라.

예를 들어 "aAbBc"는 a, b, c 세 종류이므로 3을 반환한다.

제한사항
s의 길이는 1 이상 100,000 이하
영문 대소문자만 주어진다.*/
package algorithm;

public class main1 {
    public static void main(String[] args) {

        Solution solution = new Solution();

        // 테스트 입력 직접 작성
        String s = "aAbBc";

        int answer = solution.solution(s);

        System.out.println(answer);
    }
}
class Solution {
    public int solution(String s) {
        boolean[] visited = new boolean[26];

        for (char c : s.toCharArray()) {
            c = Character.toLowerCase(c);
            visited[c - 'a'] = true;
        }

        int answer = 0;

        for (boolean b : visited) {
            if (b) answer++;
        }

        return answer;
    }
}
