package top.mictech;

import java.util.*;

class test {
    public static void main(String[] args) {
        System.out.println(new Solution().rangeBitwiseAnd(2147483646, 2147483647));
    }
}

//region 题目提供的数据结构

class ListNode {
    ListNode next;
    int val;

    ListNode(int x) {
        val = x;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {
    }

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
}

//endregion

public class Solution {
    // region 一般题

    // 201. 数字范围按位与
    public int rangeBitwiseAnd(int left, int right) {
        int s = left;
        for (int i = left; i <= right; i++)
            if ((s &= i) == 0 || i == right) break;
        return s;
    }

    // 187. 重复的DNA序列
    public List<String> findRepeatedDnaSequences(String s) {
        if (s.length() < 11) return new ArrayList<>();
        Map<String, Boolean> map = new HashMap<>();
        List<String> list = new LinkedList<>();
        for (int i = 0; i <= s.length() - 10; i++) {
            String str = s.substring(i, i + 10);
            if (map.containsKey(str)) {
                if (map.get(str) && map.put(str, false))
                    list.add(str);
            } else map.put(str, true);
        }
        return list;
    }

    // 997. 找到小镇的法官
    public int findJudge(int n, int[][] trust) {
        int[] inp = new int[n + 1], oup = new int[n + 1];
        for (int[] is : trust) {
            inp[is[1]]++;
            oup[is[0]]++;
        }
        for (int i = 1; i <= n; i++)
            if (inp[i] == n - 1 && oup[i] == 0) return i;
        return -1;
    }

    // 58. 最后一个单词的长度
    public int lengthOfLastWord(String s) {
        int l = 0;
        for (int i = s.length() - 1; i >= 0; i--)
            if (s.charAt(i) != ' ') l++;
            else if (l > 0) break;
        return l;
    }

    // 1034. 边界着色
    public int[][] colorBorder(int[][] grid, int row, int col, int color) {
        boolean[][] zt = new boolean[grid.length][grid[0].length];
        colorBorderDFS(grid, zt, row, col, grid[row][col], color);
        return grid;
    }

    private void colorBorderDFS(int[][] grid, boolean[][] zt, int p1, int p2, int t, int n) {
        if (zt[p1][p2]) return;
        zt[p1][p2] = true;
        if (!(p1 > 0) || !(p2 > 0) || !(p1 < grid.length - 1) || !(p2 < grid[0].length - 1) ||
                !(grid[p1 - 1][p2] == t || zt[p1 - 1][p2]) || !(grid[p1][p2 - 1] == t || zt[p1][p2 - 1]) ||
                !(grid[p1 + 1][p2] == t || zt[p1 + 1][p2]) || !(grid[p1][p2 + 1] == t || zt[p1][p2 + 1]))
            grid[p1][p2] = n;
        if (p1 > 0 && grid[p1 - 1][p2] == t) colorBorderDFS(grid, zt, p1 - 1, p2, t, n);
        if (p2 > 0 && grid[p1][p2 - 1] == t) colorBorderDFS(grid, zt, p1, p2 - 1, t, n);
        if (p1 < grid.length - 1 && grid[p1 + 1][p2] == t) colorBorderDFS(grid, zt, p1 + 1, p2, t, n);
        if (p2 < grid[0].length - 1 && grid[p1][p2 + 1] == t) colorBorderDFS(grid, zt, p1, p2 + 1, t, n);
    }

    // 383. 赎金信
    public boolean canConstruct(String ransomNote, String magazine) {
        if (magazine.length() < ransomNote.length()) return false;
        int[] magazines = new int[26];
        for (int i = 0; i < magazine.length(); i++)
            magazines[magazine.charAt(i) - 97]++;
        for (int i = 0; i < ransomNote.length(); i++)
            if (--magazines[ransomNote.charAt(i) - 97] < 0) return false;
        return true;
    }

    // 1005. K 次取反后最大化的数组和
    public int largestSumAfterKNegations(int[] nums, int k) {
        Arrays.sort(nums);
        int min = 0;
        for (int i = 0; i < k; i++)
            if ((nums[min] = -nums[min]) == nums[min] && min < nums.length - 1 && nums[min] >= nums[min + 1]) min++;
        int s = 0;
        for (int i : nums) s += i;
        return s;
    }

    // 506. 相对名次
    public String[] findRelativeRanks(int[] score) {
        int[] val = Arrays.copyOf(score, score.length);
        Arrays.sort(val);
        Map<Integer, String> map = new HashMap<>();
        if (val.length > 0) map.put(val[val.length - 1], "Gold Medal");
        if (val.length > 1) map.put(val[val.length - 2], "Silver Medal");
        if (val.length > 2) map.put(val[val.length - 3], "Bronze Medal");
        for (int i = val.length - 4; i >= 0; i--)
            map.put(val[i], String.valueOf(val.length - i));
        int p = 0;
        String[] strings = new String[score.length];
        for (int i : score)
            strings[p++] = map.get(i);
        return strings;
    }

    // 1446. 连续字符
    public int maxPower(String s) {
        char c = s.charAt(0);
        int v = 0, z = 0;
        for (int i = 0; i < s.length(); i++) {
            if (c == s.charAt(i)) v++;
            else if ((v = 1) == 1) c = s.charAt(i);
            z = Math.max(z, v);
        }
        return z;
    }

    // 56. 合并区间
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        LinkedList<int[]> ls = new LinkedList<>();
        ls.addLast(intervals[0]);
        for (int[] is : intervals)
            if (is[0] <= ls.getLast()[1]) ls.getLast()[1] = Math.max(ls.getLast()[1], is[1]);
            else ls.addLast(is);
        return ls.toArray(new int[ls.size()][]);
    }

    // 49. 字母异位词分组
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            final char[] cs = s.toCharArray();
            Arrays.sort(cs);
            String p = new String(cs);
            if (!map.containsKey(p))
                map.put(p, new LinkedList<>());
            map.get(p).add(s);
        }
        return new LinkedList<>(map.values());
    }

    // 2. 两数相加
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode listNode = new ListNode(0);
        ListNode l = listNode;
        int c = 0;
        c += l1.val + l2.val;
        l.val = c % 10;
        c /= 10;
        l1 = l1.next;
        l2 = l2.next;
        while (l1 != null || l2 != null || c != 0) {
            c += l1 == null ? 0 : l1.val;
            c += l2 == null ? 0 : l2.val;
            l.next = new ListNode(c % 10);
            l = l.next;
            c /= 10;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        return listNode;
    }

    // 419. 甲板上的战舰
    public int countBattleships(char[][] board) {
        /*DFS解法代码
        int c = 0;
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                if (board[i][j] == 'X' && c++ >= 0) countBattleshipsDFS(board, i, j);
        return c;
         */
        int c = 0;
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                if (!(i > 0 && board[i - 1][j] == 'X' || j > 0 && board[i][j - 1] == 'X') && board[i][j] == 'X') c++;
        return c;
    }

    private void countBattleshipsDFS(char[][] board, int p1, int p2) {
        board[p1][p2] = '.';
        if (p1 > 0 && board[p1 - 1][p2] == 'X') countBattleshipsDFS(board, p1 - 1, p2);
        if (p2 > 0 && board[p1][p2 - 1] == 'X') countBattleshipsDFS(board, p1, p2 - 1);
        if (p1 < board.length - 1 && board[p1 + 1][p2] == 'X') countBattleshipsDFS(board, p1 + 1, p2);
        if (p2 < board[0].length - 1 && board[p1][p2 + 1] == 'X') countBattleshipsDFS(board, p1, p2 + 1);
    }

    // 39. 组合总和(未完成)
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> lists = new LinkedList<>();
        LinkedList<Integer> list = new LinkedList<>();
        boolean[] bs = new boolean[candidates.length];
        for (int i : candidates)
            combinationSum(candidates, i, 0, target, list, lists);
        return lists;
    }

    private void combinationSum(int[] is, int p, int s, int t, LinkedList<Integer> ls, List<List<Integer>> lists) {
        ls.addLast(p);
        if (s == t)
            lists.add(new LinkedList<>(ls));
        else if (s < t)
            for (int i : is)
                combinationSum(is, i, s + i, t, ls, lists);
        ls.removeLast();
    }

    // 46. 全排列
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> lists = new LinkedList<>();
        LinkedList<Integer> list = new LinkedList<>();
        boolean[] bs = new boolean[nums.length];
        for (int i = 0; i < nums.length; i++)
            permute(nums, i, bs, list, lists);
        return lists;
    }

    private void permute(int[] nums, int p, boolean[] bs, LinkedList<Integer> ls, List<List<Integer>> lists) {
        ls.addLast(nums[p]);
        bs[p] = true;
        if (ls.size() == nums.length) {
            lists.add(new LinkedList<>(ls));
            ls.removeLast();
            bs[p] = false;
            return;
        }
        for (int i = 0; i < nums.length; i++)
            if (!bs[i]) permute(nums, i, bs, ls, lists);
        bs[p] = false;
        ls.removeLast();
    }

    // 剑指 Offer 58 - II. 左旋转字符串
    public String reverseLeftWords(String s, int n) {
        return s.substring(n) + s.substring(0, n);
    }

    // 剑指 Offer 05. 替换空格
    public String replaceSpace(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') sb.append("%20");
            else sb.append(c);
        }
        return sb.toString();
    }

    // 1816. 截断句子
    public String truncateSentence(String s, int k) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray())
            if (c == ' ' && k-- == 1) break;
            else sb.append(c);
        return sb.toString();
    }

    // 1518. 换酒问题
    public int numWaterBottles(int numBottles, int numExchange) {
        int bottles = numBottles;
        while (numBottles >= numExchange) {
            bottles += numBottles / numExchange;
            numBottles = numBottles / numExchange + numBottles % numExchange;
        }
        return bottles;
    }

    // 1137. 第 N 个泰波那契数
    public int tribonacci(int n) {
        if (n <= 1) return n;
        if (n == 2) return 1;
        int p1 = 0, p2 = 1, p3 = 1;
        for (int i = 2; i < n; i++) {
            p3 = p3 + p2 + p1;
            p2 = p3 - p2 - p1;
            p1 = p3 - p2 - p1;
        }
        return p3;
    }

    // 977. 有序数组的平方
    public int[] sortedSquares(int[] nums) {
        int p1 = 0, p2 = nums.length - 1, len = nums.length;
        int[] ns = new int[nums.length];
        while (p1 <= p2) {
            len--;
            if (Math.abs(nums[p1]) > Math.abs(nums[p2])) {
                ns[len] = nums[p1] * nums[p1];
                p1++;
            } else {
                ns[len] = nums[p2] * nums[p2];
                p2--;
            }
        }
        return ns;
    }

    // 844. 比较含退格的字符串
    public boolean backspaceCompare(String s, String t) {
        Stack<Character> s1 = new Stack<>();
        Stack<Character> t1 = new Stack<>();
        for (char c : s.toCharArray())
            if (c != '#') s1.push(c);
            else if (!s1.empty()) s1.pop();
        for (char c : t.toCharArray())
            if (c != '#') t1.push(c);
            else if (!t1.empty()) t1.pop();
        if (s1.size() != t1.size()) return false;
        while (!s1.empty() && !t1.empty())
            if (s1.pop() != t1.pop()) return false;
        return s1.empty() && t1.empty();
    }

    // 797. 所有可能的路径
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> a = new ArrayList<>();
        LinkedList<Integer> z = new LinkedList<>();
        allPathsSourceTargetDFS(graph, a, 0, z);
        return a;
    }

    private void allPathsSourceTargetDFS(int[][] graph, List<List<Integer>> a, int p, LinkedList<Integer> z) {
        z.addLast(p);
        for (int i : graph[p])
            allPathsSourceTargetDFS(graph, a, i, z);
        if (p == graph.length - 1) a.add(new ArrayList<>(z));
        z.removeLast();
    }

    // 748. 最短补全词
    public String shortestCompletingWord(String licensePlate, String[] words) {
        Map<Character, Integer> map = shortestCompletingWord(licensePlate);
        String str = null;
        for (String s : words)
            if (shortestCompletingWord(map, s))
                str = str == null ? s : s.length() < str.length() ? s : str;
        return str;
    }

    private Map<Character, Integer> shortestCompletingWord(String licensePlate) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : licensePlate.toCharArray()) {
            if (c >= 'A' && c <= 'Z') c += 32;
            if (c >= 'a' && c <= 'z')
                map.put(c, map.getOrDefault(c, 0) + 1);
        }
        return map;
    }

    private boolean shortestCompletingWord(Map<Character, Integer> map, String word) {
        Map<Character, Integer> m = new HashMap<>();
        for (char c : word.toCharArray()) {
            if (c >= 'A' && c <= 'Z') c += 32;
            m.put(c, m.getOrDefault(c, 0) + 1);
        }
        for (Map.Entry<Character, Integer> entry : map.entrySet())
            if (m.getOrDefault(entry.getKey(), 0) < entry.getValue()) return false;
        return true;
    }

    // 746. 使用最小花费爬楼梯
    public int minCostClimbingStairs(int[] cost) {
        int p1 = 0, p2 = 0, g;
        for (int i = 2; i <= cost.length; i++) {
            g = Math.min(p1 + cost[i - 2], p2 + cost[i - 1]);
            p1 = p2;
            p2 = g;
        }
        return p2;
    }

    // 739. 每日温度
    public int[] dailyTemperatures(int[] temperatures) {
        int[] ns = new int[temperatures.length];
        CN:
        for (int i = 0; i < ns.length; i++) {
            for (int j = i + 1; j < ns.length; j++) {
                if (temperatures[j] > temperatures[i]) {
                    ns[i] = j - i;
                    continue CN;
                }
                ns[i] = 0;
            }
        }
        return ns;
    }

    // 733. 图像渲染
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        floodFillDFS(image, sr, sc, image[sr][sc], newColor);
        return image;
    }

    private void floodFillDFS(int[][] image, int p1, int p2, int t, int n) {
        if (p1 < 0 || p2 < 0 || p1 > image.length - 1 || p2 > image[0].length - 1 || image[p1][p2] != t || image[p1][p2] == n)
            return;
        image[p1][p2] = n;
        floodFillDFS(image, p1 - 1, p2, t, n);
        floodFillDFS(image, p1, p2 - 1, t, n);
        floodFillDFS(image, p1 + 1, p2, t, n);
        floodFillDFS(image, p1, p2 + 1, t, n);
    }

    // 709. 转换成小写字母
    public String toLowerCase(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            sb.append(c >= 'A' && c <= 'Z' ? (char) (c + 32) : c);
        }
        return sb.toString();
    }

    // 704. 二分查找
    public int search(int[] nums, int target) {
        int l = 0, m, r = nums.length - 1;
        while (l <= r) {
            m = l + r >> 1;
            if (nums[m] == target) return m;
            else if (nums[m] > target) r = m - 1;
            else l = m + 1;
        }
        return -1;
    }

    // 695. 岛屿的最大面积
    public int maxAreaOfIsland(int[][] grid) {
        int size = 0;
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++)
                if (grid[i][j] == 1) size = Math.max(size, findGrid(grid, i, j));
        return size;
    }

    private int findGrid(int[][] grid, int p1, int p2) {
        if (p1 < 0 || p1 > grid.length - 1 || p2 < 0 || p2 > grid[0].length - 1) return 0;
        if (grid[p1][p2]-- == 1)
            return 1 + findGrid(grid, p1 - 1, p2) +
                    findGrid(grid, p1, p2 - 1) +
                    findGrid(grid, p1 + 1, p2) +
                    findGrid(grid, p1, p2 + 1);
        return 0;
    }

    // 566. 重塑矩阵
    public int[][] matrixReshape(int[][] mat, int r, int c) {
        if (mat.length * mat[0].length != r * c) return mat;
        int[][] nums = new int[r][c];
        int p1 = 0, p2 = 0;
        for (int[] is : mat) {
            for (int i : is) {
                if (p2 == c) {
                    p1++;
                    p2 = 0;
                }
                nums[p1][p2] = i;
                p2++;
            }
        }
        return nums;
    }

    // 557. 反转字符串中的单词 III
    public String reverseWords(String s) {
        int p1, p2, p = 0;
        char[] chars = s.toCharArray();
        while (p < chars.length) {
            p1 = p;
            p2 = p1 + 1;
            while (p2 < chars.length && chars[p2] != ' ')
                p2++;
            p = p2 + 1;
            p2--;
            while (p1 < p2) {
                char c = chars[p1];
                chars[p1] = chars[p2];
                chars[p2] = c;
                p1++;
                p2--;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(chars);
        return sb.toString();
    }

    // 509. 斐波那契数
    public int fib(int n) {
        if (n <= 1) return n;
        int p1 = 0, p2 = 1;
        for (int i = 1; i < n; i++) {
            p2 += p1;
            p1 = p2 - p1;
        }
        return p2;
    }

    // 496. 下一个更大元素 I
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        int length1 = nums1.length;
        int length2 = nums2.length;
        for (int i = 0; i < length2; i++)
            map.put(nums2[i], i);
        int l, n, s;
        for (int i = 0; i < length1; i++) {
            n = nums1[i];
            l = map.get(n) + 1;
            while (l < length2) {
                s = nums2[l];
                if (s > n) {
                    n = s;
                    break;
                }
                l++;
            }
            if (n == nums1[i]) nums1[i] = -1;
            else nums1[i] = n;
        }
        return nums1;
    }

    // 492. 构造矩形
    public int[] constructRectangle(int area) {
        int w = (int) Math.sqrt(area);
        while (area % w != 0) w--;
        int[] n = new int[2];
        n[0] = area / w;
        n[1] = w;
        return n;
    }

    // 350. 两个数组的交集 II
    public int[] intersect(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length)
            return intersect(nums2, nums1);
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : nums1) {
            int count = map.getOrDefault(i, 0) + 1;
            map.put(i, count);
        }
        int[] nums = new int[nums1.length];
        int p = 0;
        for (int i : nums2) {
            int c = map.getOrDefault(i, 0);
            if (c > 0) {
                nums[p++] = i;
                c--;
                if (c > 0)
                    map.put(i, c);
                else
                    map.remove(i);
            }
        }
        return Arrays.copyOfRange(nums, 0, p);
    }

    // 344. 反转字符串
    public void reverseString(char[] s) {
        int p1 = 0, p2 = s.length - 1;
        while (p1 < p2) {
            char c = s[p1];
            s[p1] = s[p2];
            s[p2] = c;
            p1++;
            p2--;
        }
    }

    // 283. 移动零
    public void moveZeroes(int[] nums) {
        int p1 = 0, p2;
        while (p1 < nums.length) {
            if (nums[p1] == 0) {
                p2 = p1 + 1;
                while (p2 < nums.length) {
                    if (nums[p2] != 0) {
                        nums[p1] = nums[p2];
                        nums[p2] = 0;
                        break;
                    }
                    p2++;
                }
                if (p2 == nums.length) return;
            }
            p1++;
        }
    }

    // 217. 存在重复元素
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int i : nums)
            if (!set.add(i)) return true;
        return false;
    }

    // 200. 岛屿数量
    public int numIslands(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++)
                if (grid[i][j] == '1' && count++ >= 0) numIslandsDFS(grid, i, j);
        return count;
    }

    private void numIslandsDFS(char[][] grid, int p1, int p2) {
        if (grid[p1][p2] == '0') return;
        grid[p1][p2] = '0';
        if (p1 > 0) numIslandsDFS(grid, p1 - 1, p2);
        if (p2 > 0) numIslandsDFS(grid, p1, p2 - 1);
        if (p1 < grid.length - 1) numIslandsDFS(grid, p1 + 1, p2);
        if (p2 < grid[0].length - 1) numIslandsDFS(grid, p1, p2 + 1);
    }

    // 189. 轮转数组
    public void rotate(int[] nums, int k) {
        k %= nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }

    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            nums[start] = nums[start] ^ nums[end];
            nums[end] = nums[end] ^ nums[start];
            nums[start] = nums[start] ^ nums[end];
            start += 1;
            end -= 1;
        }
    }

    // 169. 多数元素
    public int majorityElement(int[] nums) {
        int m = nums[0];
        int vc = 0;
        for (int num : nums) {
            if (num == m) vc++;
            else vc--;
            if (vc == 0) {
                m = num;
                vc = 1;
            }
        }
        return m;
    }

    // 153. 寻找旋转排序数组中的最小值
    public int findMin(int[] nums) {
        if (nums.length == 1) return nums[0];
        if (nums[0] < nums[nums.length - 1]) return nums[0];
        int l = 1, m = 0, r = nums.length - 1;
        while (l <= r) {
            m = l + r >> 1;
            if (nums[m] < nums[m - 1]) return nums[m];
            if (nums[m] > nums[0]) l = m + 1;
            else r = m - 1;
        }
        return nums[m];
    }

    // 136. 只出现一次的数字
    public int singleNumber(int[] nums) {
        int p = 0;
        for (int i : nums) p ^= i;
        return p;
    }

    // 130. 被围绕的区域
    public void solve(char[][] board) {
        boolean[][] zt = new boolean[board.length][board[0].length];
        int len1 = board.length - 1, len2 = board[0].length - 1;
        for (int i = 0; i <= len1; i++) {
            solveDFS(board, zt, i, 0);
            solveDFS(board, zt, i, len2);
        }
        for (int i = 0; i <= len2; i++) {
            solveDFS(board, zt, 0, i);
            solveDFS(board, zt, len1, i);
        }
        for (int i = 0; i < len1; i++)
            for (int j = 0; j < len2; j++)
                if (board[i][j] == 'O' && !zt[i][j]) board[i][j] = 'X';
    }

    private void solveDFS(char[][] board, boolean[][] zt, int i, int j) {
        if (zt[i][j] || board[i][j] == 'X') return;
        zt[i][j] = true;
        if (i > 0) solveDFS(board, zt, i - 1, j);
        if (j > 0) solveDFS(board, zt, i, j - 1);
        if (i < board.length - 1) solveDFS(board, zt, i + 1, j);
        if (j < board[0].length - 1) solveDFS(board, zt, i, j + 1);
    }

    // 75. 颜色分类
    public void sortColors(int[] nums) {
        int l = 0, r = nums.length - 1;
        for (int i = 0; i <= r; i++) {
            if (nums[r] == 2) {
                r--;
                i--;
                continue;
            }
            if (nums[l] == 0) {
                l++;
                continue;
            }
            if (nums[l] != 0 && nums[r] != 2) {
                if (nums[i] == 2) {
                    int t = nums[r];
                    nums[r] = nums[i];
                    nums[i] = t;
                    r--;
                }
                if (nums[i] == 0) {
                    int t = nums[i];
                    nums[i] = nums[l];
                    nums[l] = t;
                    l++;
                }
            }
        }
    }

    // 409. 最长回文串
    public int longestPalindrome(String s) {
        int[] ints = new int[128];
        int len = s.length();
        for (int i = 0; i < len; i++)
            ints[s.charAt(i)]++;
        int c = 0;
        for (int i : ints)
            if (i % 2 == 1) c++;
        return c == 0 ? len : len - c + 1;
    }

    // 290. 单词规律
    public boolean wordPattern(String pattern, String s) {
        Map<Character, String> map = new HashMap<>();
        String[] strings = s.split(" ");
        if (pattern.length() != strings.length) return false;
        for (int i = 0; i < strings.length; i++)
            if (!map.containsKey(pattern.charAt(i)))
                if (map.containsValue(strings[i])) return false;
                else map.put(pattern.charAt(i), strings[i]);
            else if (!map.get(pattern.charAt(i)).equalsIgnoreCase(strings[i])) return false;
        return true;
    }

    // 415. 字符串相加
    public String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int p1 = num1.length() - 1, p2 = num2.length() - 1;
        int c = 0;
        while (p1 >= 0 || p2 >= 0 || c != 0) {
            c += p1 >= 0 ? num1.charAt(p1) - '0' : 0;
            c += p2 >= 0 ? num2.charAt(p2) - '0' : 0;
            sb.append(c % 10);
            c /= 10;
            p1--;
            p2--;
        }
        sb.reverse();
        return sb.toString();
    }

    // 74. 搜索二维矩阵 240. 搜索二维矩阵 II
    public boolean searchMatrix(int[][] matrix, int target) {
        for (int[] ints : matrix) {
            for (int i = ints.length - 1; i >= 0; i--) {
                if (target < ints[i]) ;
                else if (ints[i] == target) return true;
                else break;
            }
        }
        return false;
    }

    // 70. 爬楼梯
    public int climbStairs(int n) {
        if (n <= 3) return n;
        int p1 = 2, p2 = 3;
        for (int i = 3; i < n; i++) {
            p2 += p1;
            p1 = p2 - p1;
        }
        return p2;
    }

    // 66. 加一
    public int[] plusOne(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] != 9) {
                digits[i]++;
                return digits;
            } else {
                digits[i] = 0;
            }
        }
        int[] nums = new int[digits.length + 1];
        nums[0] = 1;
        return nums;
    }

    // 53. 最大子数组和
    public int maxSubArray(int[] nums) {
        int pre = 0, maxAns = nums[0];
        for (int x : nums) {
            pre = Math.max(pre + x, x);
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;
    }

    // 35. 搜索插入位置
    public int searchInsert(int[] nums, int target) {
        if (nums.length == 1) return nums[0] < target ? 1 : 0;
        int left = 0, right = nums.length - 1, p, length = right;
        while (left <= right) {
            p = left + right >> 1;
            if (nums[p] == target)
                return p;
            else if (nums[p] > target) {
                if (p == 0)
                    return 0;
                else if (nums[p - 1] < target)
                    return p;
                right = p - 1;
            } else if (nums[0] < target) {
                if (p == length)
                    return p + 1;
                else if (nums[p + 1] > target)
                    return p + 1;
                left = p + 1;
            }
        }
        return -1;
    }

    // 34. 在排序数组中查找元素的第一个和最后一个位置
    public int[] searchRange(int[] nums, int target) {
        if (nums.length == 0) return new int[]{-1, -1};
        if (target > nums[nums.length - 1] || target < nums[0]) return new int[]{-1, -1};
        int p1 = 0, p2 = nums.length - 1, p = 0;
        while (p1 <= p2) {
            p = p1 + p2 >> 1;
            if (nums[p] == target) break;
            if (nums[p] > target) p2 = p - 1;
            else p1 = p + 1;
        }
        if (nums[p] != target) return new int[]{-1, -1};
        p1 = p;
        p2 = p;
        while (p1 > 0)
            if (nums[p1 - 1] == target) p1--;
            else break;
        while (p2 < nums.length - 1)
            if (nums[p2 + 1] == target) p2++;
            else break;
        return new int[]{p1, p2};
    }

    // 27. 移除元素
    public int removeElement(int[] nums, int val) {
        int l = nums.length - 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] == val) {
                nums[i] = nums[l];
                l--;
            }
        }
        return l + 1;
    }

    // 26. 删除有序数组中的重复项
    public int removeDuplicates(int[] nums) {
        int l = 0;
        for (int n : nums) {
            if (n != nums[l]) {
                l++;
                nums[l] = n;
            }
        }
        return l + 1;
    }

    // 33. 搜索旋转排序数组
    public int searchRotate(int[] nums, int target) {
        int l = 0, m, r = nums.length - 1;
        while (l <= r) {
            m = l + r >> 1;
            if (nums[m] == target) return m;
            if (nums[m] >= nums[0])
                if (nums[m] < target) l = m + 1;
                else if (target >= nums[0]) r = m - 1;
                else l = m + 1;
            else if (nums[m] > target) r = m - 1;
            else if (target >= nums[0]) r = m - 1;
            else l = m + 1;
        }
        return -1;
    }

    // 20. 有效的括号
    public boolean isValid(String s) {
        if (s.length() == 0) return true;
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{')
                stack.push(c);
            else if (map.containsKey(c))
                if (stack.empty()) return false;
                else if (!(stack.pop() == map.get(c)))
                    return false;
        }
        return stack.empty();
    }

    // 14. 最长公共前缀
    public String longestCommonPrefix(String[] strs) {
        if (strs[0].length() == 0) return "";
        if (strs.length == 1) return strs[0];
        String str = strs[0];
        for (int i = 0; i < strs.length; i++) {
            if (!strs[i].startsWith(str)) {
                str = str.substring(0, str.length() - 1);
                i--;
            }
        }
        return str;
    }

    // 11. 盛最多水的容器
    public int maxArea(int[] height) {
        int n1 = 0, n2 = height.length - 1;
        int size = 0;
        while (n1 < n2)
            size = height[n1] < height[n2] ?
                    Math.max(size, (n2 - n1) * height[n1++]) :
                    Math.max(size, (n2 - n1) * height[n2--]);
        return size;
    }

    // 8. 字符串转换整数 (atoi)
    public int myAtoi(String s) {
        int num = 0, n = 0, length = s.length();
        Boolean f = null;
        String v;
        for (int i = 0; i < length; i++) {
            v = s.substring(i, i + 1);
            if (v.equalsIgnoreCase(" ") && num == 0 && f == null) {
            } else if (v.equalsIgnoreCase("-") && f == null) {
                f = true;
            } else if (v.equalsIgnoreCase("+") && f == null) {
                f = false;
            } else if (v.equalsIgnoreCase("0") ||
                    v.equalsIgnoreCase("1") ||
                    v.equalsIgnoreCase("2") ||
                    v.equalsIgnoreCase("3") ||
                    v.equalsIgnoreCase("4") ||
                    v.equalsIgnoreCase("5") ||
                    v.equalsIgnoreCase("6") ||
                    v.equalsIgnoreCase("7") ||
                    v.equalsIgnoreCase("8") ||
                    v.equalsIgnoreCase("9")) {
                if (f == null) f = false;
                n = n * 10 + Integer.parseInt(v);
                if (n / 10 != num) {
                    return f ? Integer.MIN_VALUE : Integer.MAX_VALUE;
                }
                num = n;
            } else {
                if (f == null) return num;
                return f ? -num : num;
            }
        }
        if (f == null) return num;
        return f ? -num : num;
    }

    // 7. 整数反转
    public int reverse(int x) {
        if (x == 0)
            return 0;
        boolean isNegtive = false;
        if (x < 0)
            isNegtive = true;
        int res = 0;
        int num = x < 0 ? -x : x;
        while (num != 0) {
            if (res > (Integer.MAX_VALUE - (num % 10)) / 10)
                return 0;
            res = res * 10 + num % 10;
            num /= 10;
        }
        return isNegtive ? -res : res;
    }

    // 6. Z 字形变换
    public String convert(String s, int numRows) {
        // 排除 numRows == 1的情况,因为这样所有内容都在第一行
        if (numRows == 1) return s;
        // 获取长度 后面要多次用到所以先存起来避免多次调用消耗性能
        int length = s.length();
        // 排除length < 3的情况,因为如果length < 3则一定不会有第二列(numRows为1已经被排除)
        // 排除numRows > length的情况,因为如果numRows > length则不会有第二列
        if (length < 3 || length < numRows) return s;
        // 提前声明好变量
        int n, l;
        // 将字符串储存为字符数组,后面使用效率更高
        char[] c = s.toCharArray();
        // 最后结果的字符串用StringBuilder生成,避免运行中多次拼接字符串造成额外开销
        StringBuilder sb = new StringBuilder();
        // 开始遍历每一行
        for (int i = 0; i < numRows; i++) {
            // 记录当前行第一个字符的起始位置
            n = i;
            // while循环判断这个位置是否存在
            while (n < length) {
                // 存在,加入到结果集尾部
                sb.append(c[n]);
                // 记录当前行下一个位置的数字
                // 不用 numRows * 2 - 2 是因为乘法效率比加法要低
                n += numRows + numRows - 2;
                // 排除首行和尾行的情况,因为它们没有斜的一列
                if (i > 0 && i < numRows - 1) {
                    // 记录当前行在斜列的位置
                    l = n - i * 2;
                    // 判断该位置是否存在字符
                    if (l < length) {
                        // 存在,加入到结果集尾部
                        sb.append(c[l]);
                    }
                }
            }
        }
        // 生成结果集,返回
        return sb.toString();
    }

    // 1. 两数之和
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int i;
        for (i = 0; i < nums.length; i++)
            if (map.containsKey(target - nums[i])) break;
            else map.put(nums[i], i);
        return new int[]{map.get(target - nums[i]), i};
    }

    // 79. 单词搜索
    public boolean exist(char[][] board, String word) {
        boolean[][] zt = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                if (board[i][j] == word.charAt(0) && existDFS(board, zt, word, 0, i, j))
                    return true;
        return false;
    }

    private boolean existDFS(char[][] board, boolean[][] zt, String word, int index, int i, int j) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || zt[i][j] || board[i][j] != word.charAt(index))
            return false;
        if (index == word.length() - 1) return true;
        zt[i][j] = true;
        boolean b = existDFS(board, zt, word, index + 1, i + 1, j) ||
                existDFS(board, zt, word, index + 1, i - 1, j) ||
                existDFS(board, zt, word, index + 1, i, j + 1) ||
                existDFS(board, zt, word, index + 1, i, j - 1);
        zt[i][j] = false;
        return b;
    }

    // 630. 课程表 III
    public int scheduleCourse(int[][] courses) {
        Arrays.sort(courses, Comparator.comparingInt(a -> a[1]));
        PriorityQueue<Integer> q = new PriorityQueue<>((a, b) -> b - a);
        int sum = 0;
        for (int[] c : courses) {
            int d = c[0];
            sum += d;
            q.add(d);
            if (sum > c[1]) sum -= q.poll();
        }
        return q.size();
    }

    // 118. 杨辉三角
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> l = new ArrayList<>();
        l.add(1);
        list.add(new ArrayList<>(l));
        for (int i = 1; i < numRows; i++) {
            l.clear();
            l.add(1);
            for (int j = 1; j < i; j++) {
                l.add(list.get(i - 1).get(j - 1) + list.get(i - 1).get(j));
            }
            l.add(1);
            list.add(new ArrayList<>(l));
        }
        return list;
    }

    // 119. 杨辉三角 II
    public List<Integer> getRow(int rowIndex) {
        List<Integer> l1;
        List<Integer> l2 = new ArrayList<>();
        l2.add(1);
        l1 = new ArrayList<>(l2);
        for (int i = 0; i < rowIndex; i++) {
            l2.clear();
            l2.add(1);
            for (int j = 0; j < i; j++) {
                l2.add(l1.get(j) + l1.get(j + 1));
            }
            l2.add(1);
            l1 = new ArrayList<>(l2);
        }
        return l1;
    }

    // 78. 子集
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> f = new LinkedList<>();
        f.add(new LinkedList<>());
        for (int i : nums) {
            final LinkedList<LinkedList<Integer>> n = new LinkedList<>();
            for (List<Integer> l : f) {
                final LinkedList<Integer> b = new LinkedList<>(l);
                b.addLast(i);
                n.addLast(b);
            }
            f.addAll(n);
        }
        return f;
    }

    // 807. 保持城市天际线
    public int maxIncreaseKeepingSkyline(int[][] grid) {
        int[] h = new int[grid.length];
        int[] w = new int[grid[0].length];
        int c = 0;
        for (int i = 0; i < grid.length; i++)
            for (int j : grid[i])
                h[i] = Math.max(h[i], j);
        for (int i = 0; i < grid[0].length; i++) {
            for (int j = 0; j < grid.length; j++) {
                w[i] = Math.max(grid[j][i], w[i]);
            }
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                c += (Math.min(h[i], w[j]) - grid[i][j]);
            }
        }
        return c;
    }

    // endregion

    // region 链表题

    // 160. 相交链表
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        ListNode l1 = headA, l2 = headB;
        while (l1 != l2) {
            l1 = l1 == null ? headB : l1.next;
            l2 = l2 == null ? headA : l2.next;
        }
        return l1;
    }

    // endregion
}

// region 数据结构设计题

// 剑指 Offer 09. 用两个栈实现队列
class CQueue {
    private MyQueue head;
    private MyQueue boot;

    public CQueue() {
    }

    public void appendTail(int value) {
        if (head == null) {
            head = new MyQueue(value, null);
            boot = head;
            return;
        }
        boot.next = new MyQueue(value, null);
        boot = boot.next;
    }

    public int deleteHead() {
        if (head == null) return -1;
        int i = head.val;
        head = head.next;
        return i;
    }

    class MyQueue {
        int val;
        MyQueue next;

        public MyQueue(int val, MyQueue next) {
            this.val = val;
            this.next = next;
        }
    }
}

// endregion