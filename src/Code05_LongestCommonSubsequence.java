/**
 * @author:txf
 * @description:
 * @date: 2023/5/16.
 */
public class Code05_LongestCommonSubsequence {
    public static void main(String[] args) {

    }

    //最长公共子序列
    public static int getLongestCommonSubsequence(String str1, String str2) {
        if (str1.length() == 0 || str2.length() == 0 || str1 == null || str2 == null) {
            return 0;
        }
        char[] c1 = str1.toCharArray();
        char[] c2 = str2.toCharArray();
        return process1(c1, c2, c1.length - 1, c2.length - 2);
    }

    /**
     *
     * @param str1 字符串 1
     * @param str2 字符串 2
     * @param i    str1 0 - i范围
     * @param j    str2 0 - j范围
     * @return返回最长公共子序列的长度
     */
    public static int process1(char[] str1, char[] str2, int i, int j) {
        if (i == 0 && j == 0) {
            return str1[0] == str2[0] ? 1 : 0;
        } else if (i == 0) {
            if (str1[i] == str2[j]) {
                return 1;
            } else {
               return process1(str1, str2, i, j - 1);
            }
        } else if (j == 0) {
            if (str1[i] == str2[j]) {
                return 1;
            }
            return process1(str1, str2, i - 1, j);
        } else {
            //1.可能以i结尾情况
            int len1 = process1(str1, str2, i, j - 1);

            //2.可能以j结尾的情况
            int len2 = process1(str1, str2, i - 1, j);

            //3.以i和j结尾的子序列
            int len3 = str1[i] == str2[j] ? 1 + process1(str1, str2, i - 1, j - 1) : 0;

            int max = Math.max(len1, len2);
            max = Math.max(max, len3);
            return max;
        }
    }

    public static int process2(char[] str1, char[] str2, int i, int j) {
        int[][] dp = new int[str1.length][str2.length];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;

        for (int k = 1; k < str2.length; k++) {
            if (str1[0] == str2[k]) {
                dp[0][k] = 1;
            } else {
                dp[0][k] = dp[0][k - 1];
            }
        }

        for (int k = 1; k < str1.length; k++) {
            if (str1[k] == str2[0]) {
                dp[k][0] = 1;
            } else {
                dp[k][0] = dp[k - 1][0];
            }
        }

        for (int row = 1; row < str1.length; row++) {
            for (int col = 1; col < str1.length; col++) {
                int max =Math.max(dp[row][col - 1], dp[row - 1][col]);
                int tmp = str1[row] == str2[col] ? 1 + dp[row - 1][col - 1] : 0;
                max = Math.max(max, tmp);
                dp[row][col] = max;
            }
        }

        return dp[i][j];
    }
}
