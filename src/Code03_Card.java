/**
 * @author:txf
 * @description:
 * @date: 2023/4/20.
 */
public class Code03_Card {
    public static void main(String[] args) {
        int[] arr = {5, 7, 4, 5, 8, 1, 6, 0, 3, 6, 1, 7};
        System.out.println(win1(arr, 0, arr.length - 1));
        System.out.println(win2(arr, 0, arr.length - 1));
        System.out.println(win3(arr, 0, arr.length - 1));
    }

    public static int win1(int[] arr, int left, int right) {
        int v1 = f1(arr, left, right);
        int v2 = g1(arr, left, right);
        return Math.max(v1, v2);
    }

    //先手，在left-right中拿牌的最大值
    public static int f1(int[] arr, int left, int right) {
        //只剩一张牌，直接返回
        if (left == right) {
            return arr[left];
        }
        int v1 = arr[left] + g1(arr, left + 1, right);
        int v2 = arr[right] + g1(arr, left, right - 1);
        return Math.max(v1, v2);
    }

    //后手，在left到right中拿牌的最大值
    public static int g1(int[] arr, int left, int right) {
        //只剩一张牌，作为后手没得选择
        if (left == right) {
            return 0;
        }
        int v1 = f1(arr, left + 1, right);//如果先手拿走左边的那张牌
        int v2 = f1(arr, left, right - 1);//如果先手拿走右边的那张牌
        //取小的那个，因为假定先手都会做出最优决策
        return Math.min(v1, v2);
    }

    public static int win2(int[] arr, int left, int right) {
        //直接上缓存
        int len = arr.length;
        int[][] fmap = new int[len][len];
        int[][] gmap = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                fmap[i][j] = -1;
            }
        }
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                gmap[i][j] = -1;
            }
        }

        int v1 = f2(arr, left, right, fmap, gmap);
        int v2 = g2(arr, left, right, fmap, gmap);
        return Math.max(v1, v2);
    }


    //先手，在left-right中拿牌的最大值
    public static int f2(int[] arr, int left, int right, int[][] fmap, int[][] gmap) {
        //如果缓存里都有记录了，就不算了，直接返回
        if (fmap[left][right] != -1) {
            return fmap[left][right];
        }
        int ans = 0;
        if (left == right) {
            ans = arr[left];
        } else {
            int v1 = arr[left] + g2(arr, left + 1, right, fmap, gmap);
            int v2 = arr[right] + g2(arr, left, right - 1, fmap, gmap);
            ans = Math.max(v1, v2);
        }
        fmap[left][right] = ans;
        return ans;
    }

    //后手，在left到right中拿牌的最大值
    public static int g2(int[] arr, int left, int right, int[][] fmap, int[][] gmap) {
        if (gmap[left][right] != -1) {
            return gmap[left][right];
        }
        int ans = 0;
        //只剩一张牌，作为后手没得选择
        if (left == right) {
            ans = 0;
        } else {
            int v1 = f2(arr, left + 1, right, fmap, gmap);//如果先手拿走左边的那张牌
            int v2 = f2(arr, left, right - 1, fmap, gmap);//如果先手拿走右边的那张牌
            //取小的那个，因为假定先手都会做出最优决策
            ans = Math.min(v1, v2);
        }
        gmap[left][right] = ans;
        return ans;
    }

    public static int win3(int[] arr, int left, int right) {
        int len = arr.length;
        int[][] fmap = new int[len][len];
        int[][] gmap = new int[len][len];
        //对角线赋值
        int v1 = arr[left] + g1(arr, left + 1, right);
        int v2 = arr[right] + g1(arr, left, right - 1);
        for (int startCol = 1; startCol < len; startCol++) {
            int row = 0;
            int col = startCol;
            while (col < len) {
                fmap[row][col] = Math.max(arr[row] + gmap[row + 1][col], arr[col] + gmap[row][col - 1]);
                gmap[row][col] = Math.min(fmap[row][col - 1], fmap[row + 1][col]);
                row++;
                col++;
            }
        }

        return Math.max(fmap[left][right], gmap[left][right]);
    }
}
