/**
 * @author:txf
 * @description:
 * @date: 2023/4/24.
 */
public class Code04_BagProblem {
    public static void main(String[] args) {
        int[] weight = {3, 2, 4, 7};
        int[] value = {5, 6, 3, 19};
        int ret = maxValue1(weight, value, 11);
        System.out.println(ret);

        int ret2 = maxValue2(weight, value, 0, 11);
        System.out.println(ret2);
    }

    public static int maxValue1(int[] weight, int[] value,int bag) {
        if (weight == null || value == null || weight.length != value.length || bag < 0) {
            return 0;
        }
        return process1(weight, value, 0, bag);
    }
    //给定weight和value数组，返回从cur位置开始，能拿到的最大价值，重量不能超过rest
    public static int process1(int[] weight, int[] value, int cur, int rest) {
        if (rest < 0) {
            return -1;
        }
        if (cur == weight.length) {
            return 0;
        }
        //选择不拿当前物品，去后面剩余的物品里面拿
        int p1 = process1(weight, value, cur + 1, rest);

        //选择拿当前的物品，然后再去后面的物品里面挑物品，但要判断当前能不能拿货
        int p2 = 0;
        int postVal = process1(weight, value, cur + 1, rest - weight[cur]);
        if (postVal != -1) {
            p2 = value[cur] + postVal;
        }
        return Math.max(p1, p2);
    }

    public static int maxValue2(int[] weight, int[] value, int cur, int rest) {
        int[][] dp = new int[weight.length + 1][rest + 1];

        //dp[weight.length][...] = 0;
        //java里面默认初始化为0，那么不用赋值了

        for (int i = weight.length - 1; i >= 0; i--) {
            for (int j = 0; j <= rest; j++) {
                int p1 = dp[i + 1][j];
                int p2 = 0;
                if (j - weight[i] >= 0) {
                    p2 = value[i] + dp[i + 1][j - weight[i]];
                }
                dp[i][j] = Math.max(p1, p2);
            }
        }

        return dp[0][rest];

    }
}
