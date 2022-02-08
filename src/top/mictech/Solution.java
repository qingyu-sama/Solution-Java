package top.mictech;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.ToIntFunction;

class test {
    public static void main(String[] args) {
        Solution solution = new Solution();
        long ns = System.nanoTime(), ms = System.currentTimeMillis();
        System.out.println(solution.dominantIndex(new int[]{3, 6, 1, 0}));
        ns = System.nanoTime() - ns;
        ms = System.currentTimeMillis() - ms;
        System.out.println(ms);
        System.out.println(ns);
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
    int val;
    Node left;
    Node right;
    Node next;

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
    private static int[][] VECTOR = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    // region 一般题

    // 941. 有效的山脉数组
    public boolean validMountainArray(int[] arr) {
        int len = arr.length, i;
        if (len < 3) return false;
        for (i = 1; i < len; i++)
            if (arr[i] <= arr[i - 1]) break;
        if (i == 1 || i == len) return false;
        while (i < len) if (arr[i - 1] <= arr[i++]) return false;
        return true;
    }

    // 1021. 删除最外层的括号
    public String removeOuterParentheses(String s) {
        int count = 0, len = s.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            if (s.charAt(i) == '(') {
                if (count++ > 0) sb.append('(');
            } else if (--count > 0) sb.append(')');
        }
        return sb.toString();
    }

    // 1405. 最长快乐字符串
    public String longestDiverseString(int a, int b, int c) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((x, y) -> y[1] - x[1]);
        if (a > 0) pq.add(new int[]{'a', (char) a});
        if (b > 0) pq.add(new int[]{'b', (char) b});
        if (c > 0) pq.add(new int[]{'c', (char) c});
        StringBuilder sb = new StringBuilder();
        int l1 = 'd', l2 = l1;
        while (!pq.isEmpty()) {
            int[] cs = pq.poll();
            if (cs[0] != l1 || l1 != l2) {
                sb.append((char) cs[0]);
                l2 = l1;
                l1 = cs[0];
                cs[1]--;
            } else {
                if (pq.isEmpty()) break;
                int[] ncs = pq.poll();
                sb.append((char) ncs[0]);
                l2 = l1;
                l1 = ncs[0];
                if (--ncs[1] > 0) pq.add(ncs);
            }
            if (cs[1] > 0) pq.add(cs);
        }
        return sb.toString();
    }

    // 面试题 05.06. 整数转换
    public int convertInteger(int A, int B) {
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            if ((A & 1) != (B & 1)) ans++;
            A = A >>> 1;
            B = B >>> 1;
        }
        return ans;
    }

    // 1748. 唯一元素的和
    public int sumOfUnique(int[] nums) {
        int[] ns = new int[101];
        for (int i : nums) ns[i]++;
        int ans = 0;
        for (int i = 0; i < 101; i++) if (ns[i] == 1) ans += i;
        return ans;
    }

    // 1219. 黄金矿工
    public int getMaximumGold(int[][] grid) {
        int ans = 0;
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++)
                if (grid[i][j] != 0) ans = Math.max(ans, getMaximumGoldDFS(grid, i, j));
        return ans;
    }

    private int getMaximumGoldDFS(int[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i == grid.length || j == grid[i].length || grid[i][j] == 0)
            return 0;
        int t = grid[i][j];
        grid[i][j] = 0;
        int res = Math.max(Math.max(getMaximumGoldDFS(grid, i + 1, j), getMaximumGoldDFS(grid, i - 1, j)), Math.max(getMaximumGoldDFS(grid, i, j + 1), getMaximumGoldDFS(grid, i, j - 1)));
        grid[i][j] = t;
        return t + res;
    }

    // 791. 自定义字符串排序
    public String customSortString(String S, String T) {
        int[] chars = new int[26];
        for (int i = 0; i < T.length(); i++) chars[T.charAt(i) - 'a']++;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            int n = chars[c - 'a'];
            while (n-- > 0) sb.append(c);
            chars[c - 'a'] = 0;
        }
        for (char i = 'a'; i <= 'z'; i++) {
            int n = chars[i - 'a'];
            while (n-- > 0) sb.append(i);
        }
        return sb.toString();
    }

    // 744. 寻找比目标字母大的最小字母
    public char nextGreatestLetter(char[] letters, char target) {
        if (letters[letters.length - 1] <= target) return letters[0];
        int l = 0, r = letters.length - 1, m = 0;
        while (l <= r) {
            m = l + r >> 1;
            if (letters[m] > target && (m == 0 || letters[m - 1] <= target)) break;
            if (letters[m] <= target) l = m + 1;
            else r = m - 1;
        }
        return letters[m];
    }

    // 1217. 玩筹码
    public int minCostToMoveChips(int[] position) {
        Map<Integer, Integer> map = new HashMap<>();
        int max = 0, all = 0;
        for (int i : position) {
            int c = map.getOrDefault(i, 0) + 1;
            map.put(i, c);
            if (c > max) max = c;
            all++;
        }
        return all - max;
    }

    // 1725. 可以形成最大正方形的矩形数目
    public int countGoodRectangles(int[][] rectangles) {
        int max = 0, ans = 0;
        for (int[] is : rectangles) {
            int l = Math.min(is[0], is[1]);
            if (l > max) {
                max = l;
                ans = 1;
            } else if (l == max) ans++;
        }
        return ans;
    }

    // 1414. 和为 K 的最少斐波那契数字数目
    private static final int[] findMinFibonacciNumbersFibs = {1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418, 317811, 514229, 832040, 1346269, 2178309, 3524578, 5702887, 9227465, 14930352, 24157817, 39088169, 63245986, 102334155, 165580141, 267914296, 433494437, 701408733};

    public int findMinFibonacciNumbers(int k) {
        int ans = 0;
        while (k != 0) {
            int l = 0, r = findMinFibonacciNumbersFibs.length - 1, m = 0;
            while (l <= r) {
                m = l + r >> 1;
                if (findMinFibonacciNumbersFibs[m] <= k && (m == findMinFibonacciNumbersFibs.length - 1 || findMinFibonacciNumbersFibs[m + 1] > k))
                    break;
                else if (findMinFibonacciNumbersFibs[m] > k) r = m - 1;
                else l = m + 1;
            }
            k -= findMinFibonacciNumbersFibs[m];
            ans++;
        }
        return ans;
    }

    // 2000. 反转单词前缀
    public String reversePrefix(String word, char ch) {
        char[] cs = new char[word.length()];
        for (int i = 0; i < word.length(); i++) {
            cs[i] = word.charAt(i);
            if (cs[i] == ch) {
                int l = 0, r = i;
                while (l < r) {
                    final char t = cs[l];
                    cs[l++] = cs[r];
                    cs[r--] = t;
                }
                for (i++; i < word.length(); i++)
                    cs[i] = word.charAt(i);
                return new String(cs);
            }
        }
        return new String(cs);
    }

    // 1763. 最长的美好子字符串
    public String longestNiceSubstring(String s) {
        String str;
        for (int len = s.length() - 1; len >= 0; len--)
            for (int i = 0; i < s.length() - len; i++) {
                str = s.substring(i, i + len + 1);
                if (longestNiceSubstringCheck(str)) return str;
            }
        return "";
    }

    private boolean longestNiceSubstringCheck(String s) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) set.add(s.charAt(i));
        for (char c : set) {
            if (c > 'Z') c -= 32;
            else c += 32;
            if (!set.contains(c)) return false;
        }
        return true;
    }

    // 1342. 将数字变成 0 的操作次数
    public int numberOfSteps(int num) {
        int ans = 0;
        while (num != 0 && ++ans > 0) num = num % 2 == 0 ? num >> 1 : num - 1;
        return ans;
    }

    // 884. 两句话中的不常见单词
    public String[] uncommonFromSentences(String s1, String s2) {
        Set<String> set = new HashSet<>();
        Set<String> doNot = new HashSet<>();
        for (String s : s1.split(" ")) {
            if (!doNot.contains(s) && !set.add(s)) {
                set.remove(s);
                doNot.add(s);
            }
        }
        for (String s : s2.split(" ")) {
            if (!doNot.contains(s) && !set.add(s)) {
                set.remove(s);
                doNot.add(s);
            }
        }
        return set.toArray(new String[set.size()]);
    }

    // 1765. 地图中的最高点
    public int[][] highestPeak(int[][] isWater) {
        int w = isWater.length, h = isWater[0].length;
        Deque<int[]> deque = new ArrayDeque<>();
        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++)
                if (isWater[i][j] == 1) {
                    deque.add(new int[]{i, j});
                    isWater[i][j] = 0;
                } else isWater[i][j] = -1;
        while (!deque.isEmpty()) {
            int[] pos = deque.poll();
            int range = isWater[pos[0]][pos[1]] + 1;
            for (int[] is : VECTOR) {
                int i = pos[0] + is[0];
                int j = pos[1] + is[1];
                if (i < 0 || j < 0 || i == w || j == h || isWater[i][j] != -1) continue;
                isWater[i][j] = range;
                deque.add(new int[]{i, j});
            }
        }
        return isWater;
    }

    // 1996. 游戏中弱角色的数量
    public int numberOfWeakCharacters(int[][] properties) {
        int maxA = 0;
        for (int[] is : properties) maxA = Math.max(maxA, is[0]);
        int[] ds = new int[maxA + 1];
        for (int[] is : properties) ds[is[0]] = Math.max(ds[is[0]], is[1]);
        int maxD = 0;
        for (int i = maxA; i >= 0; i--) {
            int t = maxD;
            if (ds[i] > maxD) maxD = ds[i];
            ds[i] = t;
        }
        int ans = 0;
        for (int[] is : properties) if (is[0] < maxA && is[1] < ds[is[0]]) ans++;
        return ans;
    }

    // 2047. 句子中的有效单词数
    public int countValidWords(String sentence) {
        int ans = 0, len = sentence.length();
        boolean igone = false, line = false;
        if (sentence.charAt(0) == ' ') igone = true;
        for (int i = 0; i < len; i++) {
            char c = sentence.charAt(i);
            if (c == ' ') {
                if (i == len - 1 || sentence.charAt(i + 1) != ' ') {
                    if (!igone) ans++;
                    igone = false;
                    line = false;
                }
                continue;
            }
            if (igone) continue;
            if (c == '-') {
                if (line || i == 0 || i == len - 1 || sentence.charAt(i - 1) < 'a' || sentence.charAt(i - 1) > 'z' || sentence.charAt(i + 1) < 'a' || sentence.charAt(i + 1) > 'z') {
                    igone = true;
                    continue;
                }
                line = true;
            }
            if ((c >= '0' && c <= '9') ||
                    ((c == '!' || c == '.' || c == ',') &&
                            ((i > 0 && (sentence.charAt(i - 1) != ' ' && (sentence.charAt(i - 1) < 'a' || sentence.charAt(i - 1) > 'z'))) || (i < len - 1 && sentence.charAt(i + 1) != ' ')))
            ) {
                igone = true;
            }
        }
        if (!igone && sentence.charAt(len - 1) != ' ') ans++;
        return ans;
    }

    // 1688. 比赛中的配对次数
    public int numberOfMatches(int n) {
        return --n;
    }

    // 1905. 统计子岛屿
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int ans = 0;
        for (int i = 0; i < grid2.length; i++)
            for (int j = 0; j < grid2[i].length; j++)
                if (grid2[i][j] == 1 && countSubIslandsDFS(grid1, grid2, i, j) == 1)
                    ans++;
        return ans;
    }

    private int countSubIslandsDFS(int[][] parent, int[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i == grid.length || j == grid[i].length || grid[i][j] == 0) return 1;
        grid[i][j] = 0;
        return parent[i][j] & countSubIslandsDFS(parent, grid, i + 1, j) &
                countSubIslandsDFS(parent, grid, i - 1, j) &
                countSubIslandsDFS(parent, grid, i, j + 1) &
                countSubIslandsDFS(parent, grid, i, j - 1);
    }

    // 1332. 删除回文子序列
    public int removePalindromeSub(String s) {
        int l = 0, r = s.length() - 1;
        while (l < r) if (s.charAt(l++) != s.charAt(r--)) return 2;
        return 1;
    }

    // 1945. 字符串转化后的各位数字之和
    public int getLucky(String s, int k) {
        int ans = 0, n, temp;
        for (int i = 0; i < s.length(); i++) {
            n = s.charAt(i) - 96;
            while (n != 0) {
                ans += n % 10;
                n /= 10;
            }
        }
        while (ans >= 10 && --k > 0) {
            temp = 0;
            while (ans != 0) {
                temp += ans % 10;
                ans /= 10;
            }
            ans = temp;
        }
        return ans;
    }

    // 2029. 石子游戏 IX
    public boolean stoneGameIX(int[] stones) {
        int[] nums = new int[3];
        for (int i : stones) nums[i % 3]++;
        return nums[0] % 2 == 0 ? !(nums[1] == 0 || nums[2] == 0) : !(Math.abs(nums[1] - nums[2]) <= 2);
    }

    // 231. 2 的幂
    public boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    // 292. Nim 游戏
    public boolean canWinNim(int n) {
        return n % 4 != 0;
    }

    // 219. 存在重复元素 II
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            Integer idx = map.put(nums[i], i);
            if (idx != null && i - idx <= k) return true;
        }
        return false;
    }

    // 215. 数组中的第K个最大元素
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i : nums)
            if (pq.size() < k) pq.add(i);
            else if (i > pq.peek()) {
                pq.poll();
                pq.add(i);
            }
        return pq.peek();
    }

    // 539. 最小时间差
    public int findMinDifference(List<String> timePoints) {
        if (timePoints.size() > 1440) return 0;
        int[] ms = new int[timePoints.size()];
        int i = 0;
        for (String s : timePoints)
            ms[i++] = ((s.charAt(0) - '0') * 10 + (s.charAt(1) - '0')) * 60 + (s.charAt(3) - '0') * 10 + (s.charAt(4) - '0');
        Arrays.sort(ms);
        i = ms[0] + 1440 - ms[ms.length - 1];
        for (int j = 1; j < ms.length; j++) i = Math.min(ms[j] - ms[j - 1], i);
        return i;
    }

    // 3. 无重复字符的最长子串
    public int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) return 0;
        int[] lastIndex = new int[128];
        int ans = 0;
        for (int i = 0, start = 0; i < s.length(); i++) {
            int index = s.charAt(i);
            start = Math.max(start, lastIndex[index]);
            ans = Math.max(ans, i - start + 1);
            lastIndex[index] = i + 1;
        }
        return ans;
    }

    // 31. 下一个排列
    public void nextPermutation(int[] nums) {
        int p1 = nums.length - 1, p2 = p1 + 1;
        while (p1 > 0) if (nums[p1] > nums[--p1]) break;
        while (--p2 > 0) if (nums[p2] > nums[p1]) break;
        int l = 0, r = nums.length - 1;
        if (p1 != p2) {
            l = p1 + 1;
            int temp = nums[p1];
            nums[p1] = nums[p2];
            nums[p2] = temp;
        }
        while (l < r) {
            int temp = nums[l];
            nums[l++] = nums[r];
            nums[r--] = temp;
        }
    }

    // 1716. 计算力扣银行的钱
    public int totalMoney(int n) {
        int a = n / 7, b = n % 7;
        return ((49 + a * 7) * a + (a * 2 + b + 1) * b) >> 1;
    }

    // 1091. 二进制矩阵中的最短路径
    public int shortestPathBinaryMatrix(int[][] grid) {
        LinkedList<int[]> list = new LinkedList<>();
        if (grid[0][0] == 0) {
            grid[0][0] = 1;
            list.add(new int[]{0, 0});
        }
        while (!list.isEmpty()) {
            int c = list.size();
            while (--c >= 0) {
                int[] pos = list.removeFirst();
                if (pos[0] == grid.length - 1 && pos[1] == grid[0].length - 1) return grid[pos[0]][pos[1]];
                final int range = grid[pos[0]][pos[1]] + 1;
                if (pos[0] > 0 && grid[pos[0] - 1][pos[1]] == 0) {
                    grid[pos[0] - 1][pos[1]] = range;
                    list.add(new int[]{pos[0] - 1, pos[1]});
                }
                if (pos[1] > 0 && grid[pos[0]][pos[1] - 1] == 0) {
                    grid[pos[0]][pos[1] - 1] = range;
                    list.add(new int[]{pos[0], pos[1] - 1});
                }
                if (pos[0] < grid.length - 1 && grid[pos[0] + 1][pos[1]] == 0) {
                    grid[pos[0] + 1][pos[1]] = range;
                    list.add(new int[]{pos[0] + 1, pos[1]});
                }
                if (pos[1] < grid[0].length - 1 && grid[pos[0]][pos[1] + 1] == 0) {
                    grid[pos[0]][pos[1] + 1] = range;
                    list.add(new int[]{pos[0], pos[1] + 1});
                }
                if (pos[0] > 0 && pos[1] > 0 && grid[pos[0] - 1][pos[1] - 1] == 0) {
                    grid[pos[0] - 1][pos[1] - 1] = range;
                    list.add(new int[]{pos[0] - 1, pos[1] - 1});
                }
                if (pos[0] < grid.length - 1 && pos[1] > 0 && grid[pos[0] + 1][pos[1] - 1] == 0) {
                    grid[pos[0] + 1][pos[1] - 1] = range;
                    list.add(new int[]{pos[0] + 1, pos[1] - 1});
                }
                if (pos[0] > 0 && pos[1] < grid[0].length - 1 && grid[pos[0] - 1][pos[1] + 1] == 0) {
                    grid[pos[0] - 1][pos[1] + 1] = range;
                    list.add(new int[]{pos[0] - 1, pos[1] + 1});
                }
                if (pos[0] < grid.length - 1 && pos[1] < grid[0].length - 1 && grid[pos[0] + 1][pos[1] + 1] == 0) {
                    grid[pos[0] + 1][pos[1] + 1] = range;
                    list.add(new int[]{pos[0] + 1, pos[1] + 1});
                }
            }
        }
        return -1;
    }

    // 747. 至少是其他数字两倍的最大数
    public int dominantIndex(int[] nums) {
        int max1 = 0, max2 = 0, idx = 0;
        for (int i = 0; i < nums.length; i++)
            if (nums[i] >= max1) {
                max2 = max1;
                max1 = nums[i];
                idx = i;
            } else if (nums[i] > max2) max2 = nums[i];
        if (max1 >= max2 * 2) return idx;
        else return -1;
    }

    // 334. 递增的三元子序列
    public boolean increasingTriplet(int[] nums) {
        if (nums.length < 3) return false;
        int min1 = Integer.MAX_VALUE, min2 = min1;
        for (int i : nums)
            if (i <= min1) min1 = i;
            else if (i <= min2) min2 = i;
            else return true;
        return false;
    }

    // 162. 寻找峰值
    public int findPeakElement(int[] nums) {
        int l = 0, m = 0, r = nums.length - 1;
        while (l <= r) {
            m = l + r >> 1;
            if ((m == 0 || nums[m - 1] < nums[m]) && (m == nums.length - 1 || nums[m + 1] < nums[m]))
                return m;
            else if (m > 0 && nums[m - 1] > nums[m]) r = m - 1;
            else l = m + 1;
        }
        return m;
    }

    // 547. 省份数量
    public int findCircleNum(int[][] isConnected) {
        boolean[] bs = new boolean[isConnected.length];
        int ans = 0;
        for (int i = 0; i < bs.length; i++)
            if (findCircleNumDFS(isConnected, bs, i)) ans++;
        return ans;
    }

    private boolean findCircleNumDFS(int[][] isConnected, boolean[] bs, int p) {
        if (bs[p]) return false;
        bs[p] = true;
        for (int i = 0; i < isConnected.length; i++)
            if (isConnected[i][p] == 1 && isConnected[p][i] == 1) findCircleNumDFS(isConnected, bs, i);
        return true;
    }

    // 438. 找到字符串中所有字母异位词
    public List<Integer> findAnagrams(String s, String p) {
        if (s.length() < p.length()) return new ArrayList<>();
        int[] ints = new int[26];
        for (int i = 0; i < p.length(); i++) {
            ints[p.charAt(i) - 97]++;
            ints[s.charAt(i) - 97]--;
        }
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < 26; i++)
            if (ints[i] != 0) break;
            else if (i == 25) list.add(0);
        B:
        for (int l = 0, r = p.length(); r < s.length(); ) {
            int p1 = s.charAt(l++) - 97, p2 = s.charAt(r++) - 97;
            ints[p1]++;
            ints[p2]--;
            if (ints[p1] == 0 && ints[p2] == 0) {
                for (int i : ints)
                    if (i != 0) continue B;
                list.add(l);
            }
        }
        return list;
    }

    // 1629. 按键持续时间最长的键
    public char slowestKey(int[] releaseTimes, String keysPressed) {
        int time = 0, ans = 0;
        char c = 'a';
        for (int i = 0; i < releaseTimes.length; i++) {
            releaseTimes[i] -= time;
            if (releaseTimes[i] > ans || (releaseTimes[i] == ans && keysPressed.charAt(i) > c)) {
                ans = releaseTimes[i];
                c = keysPressed.charAt(i);
            }
            time += releaseTimes[i];
        }
        return c;
    }

    // 986. 区间列表的交集
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        LinkedList<int[]> list = new LinkedList<>();
        int p1 = 0, p2 = 0;
        while (p1 < firstList.length && p2 < secondList.length) {
            int l = Math.max(firstList[p1][0], secondList[p2][0]);
            int r = Math.min(firstList[p1][1], secondList[p2][1]);
            if (l <= r) list.addLast(new int[]{l, r});
            if (firstList[p1][1] < secondList[p2][1]) p1++;
            else p2++;
        }
        return list.toArray(new int[list.size()][]);
    }

    // 1614. 括号的最大嵌套深度
    public int maxDepth(String s) {
        int n = 0, ans = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') ans = Math.max(++n, ans);
            else if (s.charAt(i) == ')') n--;
        }
        return ans;
    }

    // 71. 简化路径
    public String simplifyPath(String path) {
        LinkedList<String> list = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);
            if (c == '/') {
                if (sb.length() > 0)
                    list.addLast(sb.toString());
                sb.setLength(0);
            } else if (c == '.') {
                while (i < path.length() && path.charAt(i) != '/')
                    sb.append(path.charAt(i++));
                if (sb.length() > 2) list.addLast(sb.toString());
                else if (sb.length() == 2 && sb.charAt(1) == '.' && !list.isEmpty())
                    list.removeLast();
                sb.setLength(0);
                i--;
            } else sb.append(c);
        }
        if (sb.length() > 0) list.addLast(sb.toString());
        if (list.isEmpty()) return "/";
        sb.setLength(0);
        for (String s : list)
            sb.append('/').append(s);
        return sb.toString();
    }

    // 2011. 执行操作后的变量值
    public int finalValueAfterOperations(String[] operations) {
        Map<String, ToIntFunction<Integer>> map = new HashMap<>();
        map.put("X++", a -> a + 1);
        map.put("++X", a -> a + 1);
        map.put("X--", a -> a - 1);
        map.put("--X", a -> a - 1);
        int x = 0;
        for (String s : operations) x = map.get(s).applyAsInt(x);
        return x;
    }

    // 1576. 替换所有的问号
    public String modifyString(String s) {
        char c = 'a';
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == '?') {
                char l = i > 0 ? s.charAt(i - 1) : '?';
                char r = i < s.length() - 1 ? s.charAt(i + 1) : '?';
                while (c == l || c == r) if (++c > 'z') c = 'a';
                sb.append(c);
                if (++c > 'z') c = 'a';
            } else sb.append(s.charAt(i));
        return sb.toString();
    }

    // 784. 字母大小写全排列
    public List<String> letterCasePermutation(String s) {
        List<String> list = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        letterCasePermutationDFS(list, sb, s, 0);
        return list;
    }

    private void letterCasePermutationDFS(List<String> list, StringBuilder sb, String s, int idx) {
        if (idx == s.length()) {
            list.add(sb.toString());
            return;
        }
        char c = s.charAt(idx);
        sb.append(c);
        letterCasePermutationDFS(list, sb, s, idx + 1);
        sb.setLength(idx);
        if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
            if (c > 'Z') c -= 32;
            else c += 32;
            sb.append(c);
            letterCasePermutationDFS(list, sb, s, idx + 1);
            sb.setLength(idx);
        }
    }

    // 77. 组合
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> lists = new LinkedList<>();
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 1; i <= n; i++)
            combineDFS(lists, list, i, n, k);
        return lists;
    }

    private void combineDFS(List<List<Integer>> lists, LinkedList<Integer> list, int p, int m, int c) {
        list.addLast(p);
        if (list.size() == c) {
            lists.add(new ArrayList<>(list));
            list.removeLast();
            return;
        }
        while (++p <= m) combineDFS(lists, list, p, m, c);
        list.removeLast();
    }

    // 1185. 一周中的第几天
    public String dayOfTheWeek(int day, int month, int year) {
        final String[] week = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        if (month == 1 || month == 2) {
            month += 12;
            year--;
        }
        return week[(day + 2 * month + 3 * (month + 1) / 5 + year + year / 4 - year / 100 + year / 400 + 1) % 7];
    }

    // 191. 位1的个数
    public int hammingWeight(int n) {
        int ans = 0;
        while (n != 0) {
            n &= n - 1;
            ans++;
        }
        return ans;
    }

    // 2022. 将一维数组转变成二维数组
    public int[][] construct2DArray(int[] original, int m, int n) {
        if (original.length != m * n) return new int[0][0];
        int p = 0;
        int[][] ans = new int[m][n];
        for (int[] is : ans)
            for (int i = 0; i < is.length; i++)
                is[i] = original[p++];
        return ans;
    }

    // 507. 完美数
    public boolean checkPerfectNumber(int num) {
        return num == 6 || num == 28 || num == 496 || num == 8128 || num == 33550336;
    }

    // 1995. 统计特殊四元组
    public int countQuadruplets(int[] nums) {
        int ans = 0, len = nums.length;
        for (int i = 0; i < len; i++)
            for (int j = i + 1; j < len; j++)
                for (int k = j + 1; k < len; k++)
                    for (int l = k + 1; l < len; l++)
                        if (nums[i] + nums[j] + nums[k] == nums[l]) ans++;
        return ans;
    }

    // 1557. 可以到达所有点的最少点数目
    public List<Integer> findSmallestSetOfVertices(int n, List<List<Integer>> edges) {
        boolean[] bs = new boolean[n];
        for (List<Integer> list : edges)
            bs[list.get(1)] = true;
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < n; i++)
            if (!bs[i]) list.add(i);
        return list;
    }

    // 841. 钥匙和房间
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        boolean[] bs = new boolean[rooms.size()];
        canVisitAllRoomsDFS(rooms, bs, 0);
        for (boolean b : bs)
            if (!b) return false;
        return true;
    }

    private void canVisitAllRoomsDFS(List<List<Integer>> rooms, boolean[] bs, int p) {
        if (bs[p]) return;
        bs[p] = true;
        for (Integer i : rooms.get(p))
            canVisitAllRoomsDFS(rooms, bs, i);
    }

    // 567. 字符串的排列
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) return false;
        int[] ints1 = new int[26];
        for (int i = 0; i < s1.length(); i++)
            ints1[s1.charAt(i) - 97]++;
        int p1 = 0, p2 = s1.length();
        int[] ints2 = new int[26];
        for (int i = 0; i < p2; i++)
            ints2[s2.charAt(i) - 97]++;
        for (int i = 0; i < 26; i++)
            if (ints1[i] != ints2[i]) break;
            else if (i == 25) return true;
        while (p2 < s2.length()) {
            ints2[s2.charAt(p1++) - 97]--;
            ints2[s2.charAt(p2++) - 97]++;
            for (int i = 0; i < 26; i++)
                if (ints1[i] != ints2[i]) break;
                else if (i == 25) return true;
        }
        return false;
    }

    // 1078. Bigram 分词
    public String[] findOcurrences(String text, String first, String second) {
        StringBuilder sb = new StringBuilder();
        List<String> list = new ArrayList<>();
        int len = text.length() - first.length() - second.length() - 2;
        CN:
        for (int i = 0; i < len; i++) {
            if (text.charAt(i) == first.charAt(0) && (i == 0 || text.charAt(i - 1) == ' ')) {
                int t = i + 1;
                for (int j = 1; j < first.length(); j++)
                    if (text.charAt(t++) != first.charAt(j)) continue CN;
                if (text.charAt(t++) != ' ') continue;
                for (int j = 0; j < second.length(); j++)
                    if (text.charAt(t++) != second.charAt(j)) continue CN;
                if (text.charAt(t++) != ' ') continue;
                while (t < text.length() && text.charAt(t) != ' ') sb.append(text.charAt(t++));
                list.add(sb.toString());
                sb.setLength(0);
            }
        }
        return list.toArray(new String[list.size()]);
    }

    // 1705. 吃苹果的最大数目
    public int eatenApples(int[] apples, int[] days) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        int t = 0, count = 0, len = apples.length;
        while (t < len || !pq.isEmpty()) {
            if (t < len && apples[t] != 0) pq.add(new int[]{t + days[t], apples[t]});
            while (!pq.isEmpty() && pq.peek()[0] == t) pq.poll();
            if (!pq.isEmpty()) {
                int[] temp = pq.poll();
                if (--temp[1] > 0 && temp[0] > t) pq.add(temp);
                count++;
            }
            t++;
        }
        return count;
    }

    // 1823. 找出游戏的获胜者
    public int findTheWinner(int n, int k) {
        class Node {
            private int val;
            private Node next;

            private Node(int val, Node next) {
                this.val = val;
                this.next = next;
            }
        }
        Node head = new Node(1, null);
        head.next = head;
        for (int i = n; i > 1; i--)
            head.next = new Node(i, head.next);
        while (head.val != head.next.val) {
            for (int i = 1; i < k; i++)
                head = head.next;
            head.val = head.next.val;
            head.next = head.next.next;
        }
        return head.val;
    }

    // 1249. 移除无效的括号
    public String minRemoveToMakeValid(String s) {
        Stack<Character> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(c);
                sb.append(c);
            } else if (c == ')') {
                if (!stack.empty()) {
                    sb.append(c);
                    stack.pop();
                }
            } else sb.append(c);
        }
        s = sb.toString();
        stack.clear();
        sb.setLength(0);
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == ')') {
                sb.append(c);
                stack.push(c);
            } else if (c == '(') {
                if (!stack.empty()) {
                    sb.append(c);
                    stack.pop();
                }
            } else sb.append(c);
        }
        sb.reverse();
        return sb.toString();
    }

    // 686. 重复叠加字符串匹配
    public int repeatedStringMatch(String a, String b) {
        if (true) {
            Set<Character> set = new HashSet<>();
            for (int i = 0; i < a.length(); i++)
                set.add(a.charAt(i));
            for (int i = 0; i < b.length(); i++)
                if (!set.contains(b.charAt(i))) return -1;
            set.clear();
        }
        int c = 0;
        StringBuilder sb = new StringBuilder();
        for (; sb.length() < b.length(); c++) sb.append(a);
        if (sb.toString().contains(b)) return c;
        sb.append(a);
        if (sb.toString().contains(b)) return c + 1;
        return -1;
    }

    // 278. 第一个错误的版本
    public int firstBadVersion(int n) {
        int l = 1, r = n, m;
        while (l < r) {
            m = l + ((r - l) >> 1);
            if (isBadVersion(m)) r = m;
            else l = m + 1;
        }
        return l;
    }

    private boolean isBadVersion(int m) {
        return true;
    }

    // 202. 快乐数
    public boolean isHappy(int n) {
        Set<Integer> set = new HashSet<>();
        int c = n, s;
        while (c != 1) {
            if (!set.add(c)) return false;
            n = 0;
            while (c != 0) {
                s = c % 10;
                c /= 10;
                n += s * s;
            }
            c = n;
        }
        return true;
    }

    // 1154. 一年中的第几天
    public int dayOfYear(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).getDayOfYear();
    }

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
        int p1 = s.length() - 1, p2 = t.length() - 1;
        int d1 = 0, d2 = 0;
        while (p1 >= 0 || p2 >= 0) {
            char c1 = p1 >= 0 ? s.charAt(p1) : '?';
            char c2 = p2 >= 0 ? t.charAt(p2) : '?';
            if (d1 == 0 && d2 == 0 && c1 != '#' && c2 != '#') {
                if (c1 != c2) return false;
                p1--;
                p2--;
            } else {
                if (c1 == '#' || d1 > 0) p1--;
                if (c1 == '#') d1++;
                else if (d1 > 0) d1--;
                if (c2 == '#' || d2 > 0) p2--;
                if (c2 == '#') d2++;
                else if (d2 > 0) d2--;
            }
        }
        return true;
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
        for (int i = 0, p = 0; i < nums.length; i++)
            if (nums[p] != 0) p++;
            else if (nums[i] != 0 && (nums[p++] = nums[i]) != 0) nums[i] = 0;
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

    // 167. 两数之和 II - 输入有序数组
    public int[] twoSum2(int[] numbers, int target) {
        int l = 0, r = numbers.length - 1, t;
        while (l < r) {
            t = numbers[l] + numbers[r];
            if (t == target) return new int[]{l + 1, r + 1};
            if (t > target) r--;
            else l++;
        }
        return new int[]{-1, -1};
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
            for (int j = 1; j < i; j++) l.add(list.get(i - 1).get(j - 1) + list.get(i - 1).get(j));
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
            for (int j = 0; j < i; j++)
                l2.add(l1.get(j) + l1.get(j + 1));
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
        for (int i = 0; i < grid[0].length; i++)
            for (int j = 0; j < grid.length; j++)
                w[i] = Math.max(grid[j][i], w[i]);
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++)
                c += (Math.min(h[i], w[j]) - grid[i][j]);
        return c;
    }

    // endregion

    // region 链表题

    // 817. 链表组件
    public int numComponents(ListNode head, int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int i : nums) set.add(i);
        int ans = 0;
        boolean ignore = false;
        while (head != null) {
            if (set.contains(head.val)) {
                if (!ignore) ans++;
                ignore = true;
            } else ignore = false;
            head = head.next;
        }
        return ans;
    }

    // 148. 排序链表
    public ListNode sortList(ListNode head) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.val));
        while (head != null) {
            pq.add(head);
            head = head.next;
        }
        head = new ListNode(0);
        ListNode ans = head;
        while (!pq.isEmpty()) {
            head.next = pq.poll();
            head = head.next;
        }
        head.next = null;
        return ans.next;
    }

    // 382. 链表随机节点
    private ListNode getRandomHead;
    private Random getRandomRandom;

    public Solution(ListNode head) {
        getRandomHead = head;
        getRandomRandom = new Random();
    }

    public int getRandom() {
        int ans = 0, i = 1;
        ListNode node = getRandomHead;
        while (node != null) {
            if (getRandomRandom.nextInt(i++) == 0) ans = node.val;
            node = node.next;
        }
        return ans;
    }

    // 206. 反转链表
    public ListNode reverseList(ListNode head) {
        ListNode l1, l2 = head;
        while (l2 != null && l2.next != null) {
            l1 = l2.next;
            l2.next = l2.next.next;
            l1.next = head;
            head = l1;
        }
        return head;
    }

    // 21. 合并两个有序链表
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null && list2 == null) return null;
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        ListNode nd = new ListNode(0), ans = nd;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                nd.next = list1;
                list1 = list1.next;
            } else {
                nd.next = list2;
                list2 = list2.next;
            }
            nd = nd.next;
        }
        nd.next = list1 == null ? list2 : list1;
        return ans.next;
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

    private int getListNodeLength(ListNode head) {
        int i = 0;
        while (head != null && i++ >= 0) head = head.next;
        return i;
    }

    // 61. 旋转链表
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null) return head;
        k %= getListNodeLength(head);
        ListNode l1 = head, l2 = l1;
        while (l2.next != null) {
            l2 = l2.next;
            if (k-- <= 0) l1 = l1.next;
        }
        l2.next = head;
        head = l1.next;
        l1.next = null;
        return head;
    }

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

    // region 二叉树题

    // 257. 二叉树的所有路径
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> list = new LinkedList<>();
        LinkedList<Integer> path = new LinkedList<>();
        binaryTreePathsDFS(root, list, path);
        return list;
    }

    private void binaryTreePathsDFS(TreeNode root, List<String> list, LinkedList<Integer> path) {
        if (root == null) return;
        path.addLast(root.val);
        if (root.left == null && root.right == null) {
            StringBuilder sb = new StringBuilder();
            for (int i : path) sb.append(i).append('-').append('>');
            sb.setLength(sb.length() - 2);
            list.add(sb.toString());
        } else {
            binaryTreePathsDFS(root.left, list, path);
            binaryTreePathsDFS(root.right, list, path);
        }
        path.removeLast();
    }

    // 235. 二叉搜索树的最近公共祖先
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        while (true)
            if ((p.val >= root.val && q.val <= root.val) || (q.val >= root.val && p.val <= root.val)) return root;
            else if (p.val > root.val) root = root.right;
            else root = root.left;
    }

    // 103. 二叉树的锯齿形层序遍历
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> lists = new ArrayList<>();
        zigzagLevelOrderDFS(root, lists, 0);
        return lists;
    }

    private void zigzagLevelOrderDFS(TreeNode root, List<List<Integer>> lists, int deep) {
        if (root == null) return;
        if (lists.size() == deep) lists.add(new LinkedList<>());
        if (deep % 2 == 0) lists.get(deep).add(root.val);
        else lists.get(deep).add(0, root.val);
        zigzagLevelOrderDFS(root.left, lists, ++deep);
        zigzagLevelOrderDFS(root.right, lists, deep);
    }

    // 104. 二叉树的最大深度
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return Math.max(maxDepthDFS(root.left, 1), maxDepthDFS(root.right, 1));
    }

    private int maxDepthDFS(TreeNode root, int deep) {
        if (root == null) return deep;
        return Math.max(maxDepthDFS(root.left, ++deep), maxDepthDFS(root.right, deep));
    }

    // 111. 二叉树的最小深度
    public int minDepth(TreeNode root) {
        LinkedList<TreeNode> list = new LinkedList<>();
        if (root != null) {
            root.val = 1;
            list.addLast(root);
        }
        while (!list.isEmpty()) {
            final TreeNode node = list.removeFirst();
            if (node.left == null && node.right == null) return node.val;
            if (node.left != null) {
                node.left.val = node.val + 1;
                list.addLast(node.left);
            }
            if (node.right != null) {
                node.right.val = node.val + 1;
                list.addLast(node.right);
            }
        }
        return 0;
    }

    // 107. 二叉树的层序遍历 II
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> lists = new ArrayList<>();
        // DFS代码
        levelOrderBottomDFS(lists, 0, root);
        // BFS代码
//        Deque<TreeNode> deque = new LinkedList<>();
//        int count = 0;
//        if (root != null) {
//            deque.add(root);
//            count++;
//        }
//        while (!deque.isEmpty()) {
//            final List<Integer> list = new LinkedList<>();
//            int temp = 0;
//            while (count > 0) {
//                final TreeNode node = deque.pop();
//                list.add(node.val);
//                if (node.left != null) {
//                    deque.add(node.left);
//                    temp++;
//                }
//                if (node.right != null) {
//                    deque.add(node.right);
//                    temp++;
//                }
//                count--;
//            }
//            lists.add(list);
//            count = temp;
//        }
        for (int l = 0, r = lists.size() - 1; l < r; ) {
            final List<Integer> list = lists.get(l);
            lists.set(l++, lists.get(r));
            lists.set(r--, list);
        }
        return lists;
    }

    private void levelOrderBottomDFS(List<List<Integer>> lists, int deep, TreeNode root) {
        if (root == null) return;
        if (deep == lists.size()) lists.add(new LinkedList<>());
        lists.get(deep).add(root.val);
        levelOrderBottomDFS(lists, deep + 1, root.left);
        levelOrderBottomDFS(lists, deep + 1, root.right);
    }

    // 102. 二叉树的层序遍历
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> lists = new ArrayList<>();
        // DFS代码
        levelOrderDFS(lists, 0, root);
        // BFS代码
//        Deque<TreeNode> deque = new LinkedList<>();
//        int count = 0;
//        if (root != null) {
//            deque.add(root);
//            count++;
//        }
//        while (!deque.isEmpty()) {
//            final List<Integer> list = new LinkedList<>();
//            int temp = 0;
//            while (count > 0) {
//                final TreeNode node = deque.pop();
//                list.add(node.val);
//                if (node.left != null) {
//                    deque.add(node.left);
//                    temp++;
//                }
//                if (node.right != null) {
//                    deque.add(node.right);
//                    temp++;
//                }
//                count--;
//            }
//            lists.add(list);
//            count = temp;
//        }
        return lists;
    }

    private void levelOrderDFS(List<List<Integer>> lists, int deep, TreeNode root) {
        if (root == null) return;
        if (deep == lists.size()) lists.add(new LinkedList<>());
        lists.get(deep).add(root.val);
        levelOrderDFS(lists, deep + 1, root.left);
        levelOrderDFS(lists, deep + 1, root.right);
    }

    // 116. 填充每个节点的下一个右侧节点指针 117. 填充每个节点的下一个右侧节点指针 II
    public Node connect(Node root) {
        List<Node> list = new ArrayList<>();
        connectDFS(root, list, 0);
        return root;
    }

    private void connectDFS(Node root, List<Node> list, int deep) {
        if (root == null) return;
        if (deep == list.size()) list.add(root);
        else {
            list.get(deep).next = root;
            list.set(deep, root);
        }
        deep++;
        connectDFS(root.left, list, deep);
        connectDFS(root.right, list, deep);
    }

    // 572. 另一棵树的子树
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null) return false;
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot) || isSubtreeDFS(root, subRoot);
    }

    private boolean isSubtreeDFS(TreeNode root, TreeNode subRoot) {
        if (root == null && subRoot == null) return true;
        if (root == null || subRoot == null || root.val != subRoot.val) return false;
        return isSubtreeDFS(root.left, subRoot.left) && isSubtreeDFS(root.right, subRoot.right);
    }

    // 236. 二叉树的最近公共祖先
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode l = lowestCommonAncestor(root.left, p, q);
        TreeNode r = lowestCommonAncestor(root.right, p, q);
        if (l == null) return r;
        if (r == null) return l;
        return root;
    }

    // 783. 二叉搜索树节点最小距离
    private TreeNode minDiffInBSTRoot = null;
    private int minDiffInBSTAns = Integer.MAX_VALUE;

    // 783. 二叉搜索树节点最小距离
    public int minDiffInBST(TreeNode root) {
        minDiffInDFS(root);
        return minDiffInBSTAns;
    }

    private void minDiffInDFS(TreeNode root) {
        if (root == null || minDiffInBSTAns == 1) return;
        minDiffInDFS(root.left);
        if (minDiffInBSTRoot != null)
            minDiffInBSTAns = Math.min(minDiffInBSTAns, Math.abs(minDiffInBSTRoot.val - root.val));
        minDiffInBSTRoot = root;
        minDiffInDFS(root.right);
    }

    // 230. 二叉搜索树中第K小的元素
    private int kthSmallest, kthSmallestAns;

    // 230. 二叉搜索树中第K小的元素
    public int kthSmallest(TreeNode root, int k) {
        kthSmallest = k;
        kthSmallestDFS(root);
        return kthSmallestAns;
    }

    private void kthSmallestDFS(TreeNode root) {
        if (root == null || kthSmallest <= 0) return;
        kthSmallestDFS(root.left);
        if (--kthSmallest == 0) kthSmallestAns = root.val;
        kthSmallestDFS(root.right);
    }

    // 1609. 奇偶树
    public boolean isEvenOddTree(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        return isEvenOddTreeDFS(root, list, 0);
    }

    private boolean isEvenOddTreeDFS(TreeNode root, List<Integer> list, int deep) {
        if (root == null) return true;
        if (deep % 2 == root.val % 2) return false;
        if (list.size() > deep)
            if (deep % 2 == 0 && list.get(deep) >= root.val) return false;
            else if (deep % 2 == 1 && list.get(deep) <= root.val) return false;
            else list.set(deep, root.val);
        else list.add(root.val);
        return isEvenOddTreeDFS(root.left, list, deep + 1) && isEvenOddTreeDFS(root.right, list, deep + 1);
    }

    // 113. 路径总和 II
    public List<List<Integer>> pathSumDFS(TreeNode root, int targetSum) {
        List<List<Integer>> lists = new LinkedList<>();
        LinkedList<Integer> list = new LinkedList<>();
        pathSumDFS(root, list, lists, targetSum);
        return lists;
    }

    private void pathSumDFS(TreeNode root, LinkedList<Integer> list, List<List<Integer>> lists, int t) {
        if (root == null) return;
        list.addLast(root.val);
        if ((t -= root.val) == 0 && root.left == null && root.right == null) {
            lists.add(new LinkedList<>(list));
            list.removeLast();
            return;
        }
        pathSumDFS(root.left, list, lists, t);
        pathSumDFS(root.right, list, lists, t);
        list.removeLast();
    }

    // 199. 二叉树的右视图
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> list = new LinkedList<>();
        rightSideView(root, list, 0);
        return list;
    }

    private void rightSideView(TreeNode root, List<Integer> list, int d) {
        if (root == null) return;
        if (d++ == list.size()) list.add(root.val);
        rightSideView(root.right, list, d);
        rightSideView(root.left, list, d);
    }

    // 108. 将有序数组转换为二叉搜索树
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBST(nums, 0, nums.length - 1);
    }

    private TreeNode sortedArrayToBST(int[] nums, int l, int r) {
        if (l > r) return null;
        int m = l + r >> 1;
        TreeNode root = new TreeNode(nums[m]);
        root.left = sortedArrayToBST(nums, l, m - 1);
        root.right = sortedArrayToBST(nums, m + 1, r);
        return root;
    }

    // endregion

    public Solution() {

    }
}

// region 数据结构设计题

// 2034. 股票价格波动
class StockPrice {
    private int cur;
    private HashMap<Integer, Integer> hashMap;
    private TreeMap<Integer, Integer> treeMap;

    public StockPrice() {
        hashMap = new HashMap<>();
        treeMap = new TreeMap<>();
        cur = 0;
    }

    public void update(int timestamp, int price) {
        if (hashMap.containsKey(timestamp)) {
            int prev = hashMap.get(timestamp);
            int c = treeMap.get(prev);
            if (c == 1) treeMap.remove(prev);
            else treeMap.put(prev, c - 1);
        } else cur = Math.max(cur, timestamp);
        hashMap.put(timestamp, price);
        treeMap.put(price, treeMap.getOrDefault(price, 0) + 1);
    }

    public int current() {
        return hashMap.get(cur);
    }

    public int maximum() {
        return treeMap.lastKey();
    }

    public int minimum() {
        return treeMap.firstKey();
    }
}

// 146. LRU 缓存
class LRUCache {
    private int maxSize;
    private int listSize;
    private HashMap<Integer, Integer> map;
    private ListNode head;
    private ListNode tail;

    public LRUCache(int capacity) {
        map = new HashMap<>();
        maxSize = capacity;
        listSize = 0;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        remove(key);
        add(key);
        return map.get(key);
    }

    public void put(int key, int value) {
        if (map.put(key, value) != null) remove(key);
        add(key);
        if (listSize > maxSize) map.remove(removeLast());
    }

    private void add(int val) {
        ListNode node = new ListNode(val, null);
        if (tail == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = tail.next;
        }
        listSize++;
    }

    private void remove(int val) {
        ListNode node = head;
        if (node.val == val) {
            head = head.next;
            node.next = null;
            if (head == null) tail = null;
        } else {
            while (node.next.val != val) node = node.next;
            node.next = node.next.next;
            if (node.next == null) tail = node;
        }
        listSize--;
    }

    private int removeLast() {
        int result = head.val;
        head = head.next;
        listSize--;
        return result;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}

// 380. O(1) 时间插入、删除和获取随机元素
class RandomizedSet {
    private Map<Integer, Integer> map;
    private List<Integer> list;
    private Random random;

    public RandomizedSet() {
        map = new HashMap<>();
        list = new ArrayList<>();
        random = new Random();
    }

    public boolean insert(int val) {
        if (map.containsKey(val)) return false;
        map.put(val, list.size());
        list.add(val);
        return true;
    }

    public boolean remove(int val) {
        if (!map.containsKey(val)) return false;
        final int idx = list.size() - 1, num = list.get(idx);
        list.set(map.get(val), num);
        list.remove(idx);
        map.put(num, map.get(val));
        map.remove(val);
        return true;
    }

    public int getRandom() {
        return list.get(random.nextInt(list.size()));
    }
}

// 155. 最小栈
class MinStack {
    private Node head;

    public MinStack() {
    }

    public void push(int val) {
        head = new Node(val, head == null ? val : Math.min(head.min, val), head);
    }

    public void pop() {
        head = head.next;
    }

    public int top() {
        return head.val;
    }

    public int getMin() {
        return head.min;
    }

    class Node {
        int val;
        int min;
        Node next;

        Node(int val, int min, Node next) {
            this.val = val;
            this.min = min;
            this.next = next;
        }
    }
}

// 707. 设计链表
class MyLinkedList {
    private Node head;
    private Node tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public int get(int index) {
        if (index < 0 || index > size - 1) return -1;
        Node n = head;
        while (index-- > 0)
            n = n.next;
        return n.val;
    }

    public void addAtHead(int val) {
        head = new Node(val, head);
        if (tail == null) tail = head;
        size++;
    }

    public void addAtTail(int val) {
        Node n = new Node(val);
        if (tail == null) {
            tail = n;
            head = n;
        } else {
            tail.next = n;
            tail = tail.next;
        }
        size++;
    }

    public void addAtIndex(int index, int val) {
        if (index <= 0) addAtHead(val);
        else if (index == size) addAtTail(val);
        else if (index < size) {
            Node n = head;
            while (--index > 0)
                n = n.next;
            n.next = new Node(val, n.next);
            size++;
        }
    }

    public void deleteAtIndex(int index) {
        if (index < 0 || index >= size) return;
        if (index == 0) {
            head = head.next;
            if (head == null) tail = null;
        } else {
            Node n = head;
            while (--index > 0) n = n.next;
            n.next = n.next.next;
            if (n.next == null) tail = n;
        }
        size--;
    }

    private class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
            next = null;
        }

        Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }
}

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