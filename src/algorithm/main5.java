/*5번. 제한된 예산으로 최대 개수 구매

상품의 가격이 배열 prices로 주어진다.

주어진 예산 budget 안에서 최대한 많은 상품을 구매할 때 구매할 수 있는 상품의 최대 개수를 구하라.

각 상품은 한 번만 구매할 수 있다.*/
package algorithm;
import java.util.*;
public class main5 {
    public static void main(String[] args) {

        Solution5 solution = new Solution5();

        int[] prices = {3, 1, 4, 2, 5};
        int budget = 7;

        int answer = solution.solution(prices, budget);

        System.out.println(answer);
    }
}
class Solution5 {
    public int solution(int[] prices, int budget) {
        Arrays.sort(prices);

        int count = 0;

        for (int price : prices) {
            if (budget < price) {
                break;
            }

            budget -= price;
            count++;
        }

        return count;
    }
}