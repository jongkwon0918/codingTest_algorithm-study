/*1번. 완벽한 문자열

문자열이 주어진다.

문자열에는 영어 대문자와 소문자가 섞여 있을 수 있다.

대문자와 소문자를 구분하지 않고 알파벳 26개가 모두 한 번 이상 등장하는지 확인한다.

26개가 모두 등장하면 "perfect"를 반환한다.
하나라도 등장하지 않았다면, 등장하지 않은 알파벳을 소문자 기준 오름차순으로 이어 붙여 반환한다.
        예시
입력: "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYz"
출력: "perfect"
입력: "abcDef"
출력: "ghijklmnopqrstuvwxyz"*/
package bankware;
public class Test1 {

    public static void main(String[] args) {

        String s = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYz";

        Solution solution = new Solution();

        String answer = solution.solution(s);

        System.out.println(answer);
    }
}

class Solution {

    public String solution(String s) {

        boolean[] visited = new boolean[26];

        for (int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);

            if (c >= 'A' && c <= 'Z') {
                c = (char) (c + 32);
            }

            visited[c - 'a'] = true;
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 26; i++) {

            if (!visited[i]) {
                sb.append((char) ('a' + i));
            }
        }

        if (sb.length() == 0) {
            return "perfect";
        }

        return sb.toString();
    }
}