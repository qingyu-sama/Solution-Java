#枚举版

```java
class Solution {
    public int[][] ballGame(int num, String[] plate) {
        int n = plate.length, m = plate[0].length();
        char[][] grid = new char[n][];
        for (int i = 0; i < n; i++) {
            grid[i] = plate[i].toCharArray();
        }
        List<int[]> res = new ArrayList<>();
        for (int i = 1; i < n - 1; i++) {
            if (grid[i][0] == '.' && dfs(i, 0, 0, 1, 0, num, grid)) res.add(new int[]{i, 0});
            if (grid[i][m - 1] == '.' && dfs(i, m - 1, 0, -1, 0, num, grid)) res.add(new int[]{i, m - 1});
        }
        for (int i = 1; i < m - 1; i++) {
            if (grid[0][i] == '.' && dfs(0, i, 1, 0, 0, num, grid)) res.add(new int[]{0, i});
            if (grid[n - 1][i] == '.' && dfs(n - 1, i, -1, 0, 0, num, grid)) res.add(new int[]{n - 1, i});
        }
        return res.toArray(new int[res.size()][]);
    }

    private boolean dfs(int i, int j, int x, int y, int step, int num, char[][] grid) {
        if (step++ > num) return false;
        if (i < 0 || j < 0 || i == grid.length || j == grid[0].length) return false;
        char c = grid[i][j];
        if (c == 'O') return true;
        if (c != '.') {
            if (c == 'W') {
                int t = x;
                x = -y;
                y = t;
            } else if (c == 'E') {
                int t = x;
                x = y;
                y = -t;
            }
        }
        return dfs(i + x, j + y, x, y, step, num, grid);
    }
}
```
