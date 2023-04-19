/**
 * @author:txf
 * @description:
 * @date: 2023/4/19.
 */
public class Code01_RobotWalk {
    public static void main(String[] args) {
        System.out.println(process01(2, 4, 4, 4));
        System.out.println(process02(2, 4, 4, 4));
        System.out.println(process03(2, 4, 4, 4));
    }

    /**
     *
     * @param cur   当前位置
     * @param rest  剩余的步数
     * @param aim   目标位置
     * @param N     1~N为可走的位置
     * @return      返回初始位置走到目标位置的方法数目
     */
    public static int process01 (int cur, int rest, int aim, int N) {
        //步数为0，而且没到终点
        if (rest == 0 && cur != aim) {
            return 0;
        }
        //剩余步数为0，而且到终点
        if (rest == 0 && cur == aim) {
            return 1;
        }

        //其他情况
        if (cur == 1) {
            return process01(cur + 1, rest - 1, aim, N);
        } else if (cur == N) {
            return process01(cur - 1, rest - 1, aim, N);
        } else {
            return process01(cur + 1, rest - 1, aim, N) + process01(cur - 1, rest - 1, aim, N);
        }
    }

    public static int process02(int cur, int rest, int aim, int N) {
        //cur的范围:1-N
        //rest的范围:0-rest
        int[][] dp = new int[N + 1][rest + 1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= rest; j++) {
                dp[i][j] = -1;
            }
        }
        return process02(cur, rest, aim, N, dp);
    }

    public static int process02(int cur, int rest, int aim, int N,int[][] dp) {
        //已经计算过的
        if (dp[cur][rest] != -1) {
            return dp[cur][rest];
        }

        //没有计算过就计算
        int ans = 0;
        //其他情况
        if (rest == 0) {
            ans = (cur == aim ? 1 : 0);
        } else if (cur == 1) {
            ans = process02(cur + 1, rest - 1, aim, N, dp);
        } else if (cur == N) {
            ans = process02(cur - 1, rest - 1, aim, N, dp);
        } else {
            ans = process02(cur + 1, rest - 1, aim, N, dp) + process02(cur - 1, rest - 1, aim, N, dp);
        }
        dp[cur][rest] = ans;
        return ans;
    }

    public static int process03(int cur, int rest, int aim, int N) {
        //直接构造dp数组
        int[][] dp = new int[N + 1][rest + 1];
        //数组默认初始化为0，所以只需dp[aim][0]=1即可
        dp[aim][0] = 1;
        //列
        for (int i = 1; i <= rest; i++) {
            //列中最上面的元素依赖于左下角的元素
            dp[1][i] = dp[2][i - 1];
            //中间元素依赖于左下角和左上角的元素
            for (int j = 2; j <= N - 1; j++) {
                dp[j][i] = dp[j - 1][i - 1] + dp[j + 1][i - 1];
            }
            //列中，最下面的元素依赖于左上角的元素
            dp[N][i] = dp[N - 1][i - 1];
        }

        return dp[cur][rest];
    }
}
