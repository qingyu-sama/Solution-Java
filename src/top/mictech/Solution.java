package top.mictech;

import java.util.*;

//region 题目提供的数据结构

class ListNode {
    ListNode next;
    private int val;

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

    // 240. 搜索二维矩阵 II
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
}