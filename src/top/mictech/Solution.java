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