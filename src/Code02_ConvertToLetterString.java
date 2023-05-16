/**
 * @author:txf
 * @description:
 * @date: 2023/5/14.
 */
public class Code02_ConvertToLetterString {
    public static void main(String[] args) {
        String str = "";
        process(str, 0);
    }

    public static int process(String str, int i) {
        if (i >= str.length()) {
            return 0;
        }
        int count1 = 0;
        int count2 = 0;

        count1 = process(str, i + 1);

        String tmp = str.charAt(i) + "";
        if (i + 1 < str.length()) {
            int val = Integer.valueOf(tmp + str.charAt(i + 1)).intValue();
            if (val >= 1 && val <= 26) {
                count2 = process(str, i + 2);
            }
        }

        int sum = count1 + count2;
        return sum;
    }
}
