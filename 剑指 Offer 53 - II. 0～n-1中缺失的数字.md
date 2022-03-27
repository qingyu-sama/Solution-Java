```java
class Solution {
    public int missingNumber(int[] nums) {
        int l = 0, r = nums.length - 1, m = 0;
        while (l <= r) {
            m = l + r >> 1;
            if (nums[m] == m && (m == nums.length - 1 || nums[m + 1] != m + 1)) return m + 1;
            if (nums[m] != m && (m == 0 || nums[m - 1] == m - 1)) return m;
            if (nums[m] == m) l = m + 1;
            else r = m - 1;
        }
        return m;
    }
}
```

