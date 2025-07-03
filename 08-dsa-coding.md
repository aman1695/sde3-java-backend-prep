# DSA (Data Structures and Algorithms) Preparation Plan

## Overview
This plan is designed to help you master DSA concepts and problem-solving skills for SDE interviews. It covers core data structures, algorithms, and includes regular coding practice.

---

# DSA Interview Problems Grouped by Pattern/Algorithm

## Arrays & Two Pointers
- Two Sum (LeetCode 1)
- Best Time to Buy and Sell Stock (LeetCode 121)
- Move Zeroes (LeetCode 283)
- Container With Most Water (LeetCode 11)
- Remove Duplicates from Sorted Array (LeetCode 26)
- Product of Array Except Self (LeetCode 238)

### Two Sum (LeetCode 1)
**Pattern:** Arrays & Hashing

#### Optimal Solution (HashMap, O(n))
```java
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (map.containsKey(complement)) {
            return new int[] { map.get(complement), i };
        }
        map.put(nums[i], i);
    }
    return new int[0];
}
```
**Explanation:**
- We use a HashMap to store each number and its index as we iterate.
- For each number, check if its complement (target - current number) exists in the map.
- If found, return the indices.

**Time Complexity:** O(n)
**Space Complexity:** O(n)

#### Recursive Solution
- Not practical for this problem (would be less efficient and not idiomatic).

#### Improvement Areas
- For very large datasets, consider space-time tradeoffs.
- For sorted arrays, use two pointers.

---

## Sliding Window
- Longest Substring Without Repeating Characters (LeetCode 3)
- Minimum Size Subarray Sum (LeetCode 209)
- Longest Substring with At Most K Distinct Characters (LeetCode 340)
- Longest Repeating Character Replacement (LeetCode 424)
- Permutation in String (LeetCode 567)
- Maximum Sum Subarray of Size K (LeetCode 643)

### Longest Substring Without Repeating Characters (LeetCode 3)
**Pattern:** Sliding Window

#### Optimal Solution (Sliding Window + HashMap, O(n))
```java
public int lengthOfLongestSubstring(String s) {
    Map<Character, Integer> map = new HashMap<>();
    int maxLen = 0;
    for (int start = 0, end = 0; end < s.length(); end++) {
        char c = s.charAt(end);
        if (map.containsKey(c)) {
            start = Math.max(map.get(c) + 1, start);
        }
        map.put(c, end);
        maxLen = Math.max(maxLen, end - start + 1);
    }
    return maxLen;
}
```
**Explanation:**
- Use a sliding window with two pointers (`start`, `end`).
- Move `end` forward, and if a character repeats, move `start` to the right of the last occurrence.
- Track the max window size.

**Time Complexity:** O(n)
**Space Complexity:** O(min(n, m)), where m is the charset size.

#### Recursive Solution
- Not practical for this problem; recursion would be inefficient and complex.

#### Improvement Areas
- For ASCII strings, can use an int[128] array instead of HashMap for faster access.

---

## Hashing & Maps
- Group Anagrams (LeetCode 49)
- Valid Anagram (LeetCode 242)
- Subarray Sum Equals K (LeetCode 560)
- Top K Frequent Elements (LeetCode 347)
- Longest Consecutive Sequence (LeetCode 128)

### Group Anagrams (LeetCode 49)
**Pattern:** Hashing & Maps

#### Optimal Solution (HashMap, O(nk))
```java
public List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> map = new HashMap<>();
    for (String s : strs) {
        char[] ca = s.toCharArray();
        Arrays.sort(ca);
        String key = new String(ca);
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
    }
    return new ArrayList<>(map.values());
}
```
**Explanation:**
- For each string, sort its characters to get a canonical key.
- Group all strings with the same sorted key together in a map.
- Return the grouped values.

**Time Complexity:** O(nk log k), where n = number of strings, k = max string length (for sorting)
**Space Complexity:** O(nk)

#### Recursive Solution
- Not applicable for this problem.

#### Improvement Areas
- For large alphabets, can use a frequency array as key instead of sorting for O(nk) time.

### Valid Anagram (LeetCode 242)
**Pattern:** Hashing & Maps

#### Optimal Solution (Frequency Array, O(n))
```java
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) return false;
    int[] count = new int[26];
    for (int i = 0; i < s.length(); i++) {
        count[s.charAt(i) - 'a']++;
        count[t.charAt(i) - 'a']--;
    }
    for (int c : count) if (c != 0) return false;
    return true;
}
```
**Explanation:**
- Count the frequency of each character in both strings.
- If all counts are zero at the end, they are anagrams.

**Time Complexity:** O(n)
**Space Complexity:** O(1) (since alphabet size is constant)

#### Recursive Solution
- Not practical for this problem.

#### Improvement Areas
- For Unicode, use a HashMap instead of an array.

---

## Stack & Monotonic Stack
- Valid Parentheses (LeetCode 20)
- Daily Temperatures (LeetCode 739)
- Largest Rectangle in Histogram (LeetCode 84)
- Min Stack (LeetCode 155)
- Next Greater Element I/II (LeetCode 496/503)

### Valid Parentheses (LeetCode 20)
**Pattern:** Stack

#### Optimal Solution (Stack, O(n))
```java
public boolean isValid(String s) {
    Stack<Character> stack = new Stack<>();
    for (char c : s.toCharArray()) {
        if (c == '(' || c == '{' || c == '[') {
            stack.push(c);
        } else {
            if (stack.isEmpty()) return false;
            char open = stack.pop();
            if ((c == ')' && open != '(') ||
                (c == '}' && open != '{') ||
                (c == ']' && open != '[')) {
                return false;
            }
        }
    }
    return stack.isEmpty();
}
```
**Explanation:**
- Use a stack to track opening brackets.
- For each closing bracket, check if it matches the top of the stack.
- If not, or if the stack is empty when a closing bracket appears, return false.
- At the end, stack should be empty for a valid string.

**Time Complexity:** O(n)
**Space Complexity:** O(n)

#### Recursive Solution
- Possible but not efficient. Here’s a recursive approach for educational purposes:
```java
public boolean isValidRecursive(String s) {
    if (s.isEmpty()) return true;
    for (int i = 1; i < s.length(); i++) {
        if (isPair(s.charAt(0), s.charAt(i))) {
            if (isValidRecursive(s.substring(1, i)) && isValidRecursive(s.substring(i + 1))) {
                return true;
            }
        }
    }
    return false;
}
private boolean isPair(char open, char close) {
    return (open == '(' && close == ')') ||
           (open == '{' && close == '}') ||
           (open == '[' && close == ']');
}
```
**Explanation:**
- Recursively checks for valid pairs and splits the string.
- **Not recommended** for interviews due to poor performance.

**Time Complexity:** Exponential (O(2^n)), not efficient.

#### Improvement Areas
- Use stack-based approach for efficiency.
- Recursive solution is only for educational purposes.

### Daily Temperatures (LeetCode 739)
**Pattern:** Monotonic Stack

#### Optimal Solution (Monotonic Stack, O(n))
```java
public int[] dailyTemperatures(int[] temperatures) {
    int n = temperatures.length;
    int[] res = new int[n];
    Stack<Integer> stack = new Stack<>();
    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
            int idx = stack.pop();
            res[idx] = i - idx;
        }
        stack.push(i);
    }
    return res;
}
```
**Explanation:**
- Use a stack to keep indices of decreasing temperatures.
- For each day, pop from the stack until you find a warmer day.
- The difference in indices gives the answer for each day.

**Time Complexity:** O(n)
**Space Complexity:** O(n)

#### Recursive Solution
- Not practical for this problem.

#### Improvement Areas
- For very large arrays, can use an array as a stack for better performance.

---

## Linked List
- Reverse Linked List (LeetCode 206)
- Merge Two Sorted Lists (LeetCode 21)
- Linked List Cycle (LeetCode 141)
- Remove Nth Node From End of List (LeetCode 19)
- Add Two Numbers (LeetCode 2)
- LRU Cache (LeetCode 146)

### Reverse Linked List (LeetCode 206)
**Pattern:** Linked List

#### Optimal Solution (Iterative, O(n))
```java
public ListNode reverseList(ListNode head) {
    ListNode prev = null, curr = head;
    while (curr != null) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    return prev;
}
```
**Explanation:**
- Traverse the list, reversing the `next` pointer at each node.
- `prev` tracks the new head.

**Time Complexity:** O(n)
**Space Complexity:** O(1)

#### Recursive Solution
```java
public ListNode reverseList(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode p = reverseList(head.next);
    head.next.next = head;
    head.next = null;
    return p;
}
```
**Explanation:**
- Recursively reverse the rest of the list, then fix the current node’s pointer.

**Time Complexity:** O(n)
**Space Complexity:** O(n) (due to recursion stack)

#### Improvement Areas
- Iterative is preferred for large lists to avoid stack overflow.

### Merge Two Sorted Lists (LeetCode 21)
**Pattern:** Linked List

#### Optimal Solution (Iterative, O(n+m))
```java
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(-1), curr = dummy;
    while (l1 != null && l2 != null) {
        if (l1.val < l2.val) {
            curr.next = l1;
            l1 = l1.next;
        } else {
            curr.next = l2;
            l2 = l2.next;
        }
        curr = curr.next;
    }
    curr.next = (l1 != null) ? l1 : l2;
    return dummy.next;
}
```
**Explanation:**
- Use a dummy node to simplify edge cases.
- Compare nodes from both lists and build the merged list.

**Time Complexity:** O(n + m)
**Space Complexity:** O(1)

#### Recursive Solution
```java
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    if (l1 == null) return l2;
    if (l2 == null) return l1;
    if (l1.val < l2.val) {
        l1.next = mergeTwoLists(l1.next, l2);
        return l1;
    } else {
        l2.next = mergeTwoLists(l1, l2.next);
        return l2;
    }
}
```
**Explanation:**
- Recursively merge the smaller node with the rest.

**Time Complexity:** O(n + m)
**Space Complexity:** O(n + m) (due to recursion stack)

#### Improvement Areas
- Iterative is preferred for very long lists.

---

## Trees & Recursion
- Binary Tree Inorder Traversal (LeetCode 94)
- Maximum Depth of Binary Tree (LeetCode 104)
- Invert/Flip Binary Tree (LeetCode 226)
- Diameter of Binary Tree (LeetCode 543)
- Lowest Common Ancestor of a Binary Tree (LeetCode 236)
- Serialize and Deserialize Binary Tree (LeetCode 297)

### Binary Tree Inorder Traversal (LeetCode 94)
**Pattern:** Binary Tree Traversal

#### Optimal Solution (Iterative, O(n))
```java
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    Stack<TreeNode> stack = new Stack<>();
    TreeNode curr = root;
    while (curr != null || !stack.isEmpty()) {
        while (curr != null) {
            stack.push(curr);
            curr = curr.left;
        }
        curr = stack.pop();
        res.add(curr.val);
        curr = curr.right;
    }
    return res;
}
```
**Explanation:**
- Use a stack to simulate recursion for inorder traversal.

**Time Complexity:** O(n)
**Space Complexity:** O(n)

#### Recursive Solution
```java
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    inorder(root, res);
    return res;
}
private void inorder(TreeNode node, List<Integer> res) {
    if (node == null) return;
    inorder(node.left, res);
    res.add(node.val);
    inorder(node.right, res);
}
```
**Explanation:**
- Standard recursive inorder traversal.

**Time Complexity:** O(n)
**Space Complexity:** O(n) (due to recursion stack)

#### Improvement Areas
- Morris Traversal can achieve O(1) space but is less common in interviews.

---

## Binary Search
- Binary Search (LeetCode 704)
- Search in Rotated Sorted Array (LeetCode 33)
- Find Minimum in Rotated Sorted Array (LeetCode 153)
- Median of Two Sorted Arrays (LeetCode 4)
- Kth Smallest Element in a Sorted Matrix (LeetCode 378)

### Binary Search (LeetCode 704)
**Pattern:** Binary Search

#### Optimal Solution (Iterative, O(log n))
```java
public int search(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] == target) return mid;
        else if (nums[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}
```
**Explanation:**
- Standard binary search on a sorted array.

**Time Complexity:** O(log n)
**Space Complexity:** O(1)

#### Recursive Solution
```java
public int search(int[] nums, int target) {
    return binarySearch(nums, target, 0, nums.length - 1);
}
private int binarySearch(int[] nums, int target, int left, int right) {
    if (left > right) return -1;
    int mid = left + (right - left) / 2;
    if (nums[mid] == target) return mid;
    else if (nums[mid] < target) return binarySearch(nums, target, mid + 1, right);
    else return binarySearch(nums, target, left, mid - 1);
}
```
**Explanation:**
- Recursively search left or right half.

**Time Complexity:** O(log n)
**Space Complexity:** O(log n) (due to recursion stack)

#### Improvement Areas
- Always use `mid = left + (right - left) / 2` to avoid overflow.

--- 

---

## Backtracking
- Subsets (LeetCode 78)
- Permutations (LeetCode 46)
- Combination Sum (LeetCode 39)
- Word Search (LeetCode 79)
- N-Queens (LeetCode 51)

### Subsets (LeetCode 78)
**Pattern:** Backtracking

#### Optimal Solution (Backtracking, O(n*2^n))
```java
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    backtrack(res, new ArrayList<>(), nums, 0);
    return res;
}
private void backtrack(List<List<Integer>> res, List<Integer> temp, int[] nums, int start) {
    res.add(new ArrayList<>(temp));
    for (int i = start; i < nums.length; i++) {
        temp.add(nums[i]);
        backtrack(res, temp, nums, i + 1);
        temp.remove(temp.size() - 1);
    }
}
```
**Explanation:**
- For each index, choose to include or exclude the element, recursively building all subsets.

**Time Complexity:** O(n*2^n)
**Space Complexity:** O(n*2^n)

#### Recursive Solution
- The above is recursive (backtracking).

#### Improvement Areas
- Can also use bitmasking for iterative solution.

---

### Permutations (LeetCode 46)
**Pattern:** Backtracking

#### Optimal Solution (Backtracking, O(n*n!))
```java
public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    backtrack(res, new ArrayList<>(), nums, new boolean[nums.length]);
    return res;
}
private void backtrack(List<List<Integer>> res, List<Integer> temp, int[] nums, boolean[] used) {
    if (temp.size() == nums.length) {
        res.add(new ArrayList<>(temp));
        return;
    }
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        used[i] = true;
        temp.add(nums[i]);
        backtrack(res, temp, nums, used);
        temp.remove(temp.size() - 1);
        used[i] = false;
    }
}
```
**Explanation:**
- Build permutations by choosing unused elements at each step.

**Time Complexity:** O(n*n!)
**Space Complexity:** O(n*n!)

#### Recursive Solution
- The above is recursive (backtracking).

#### Improvement Areas
- For unique permutations with duplicates, use a HashSet or sort and skip duplicates.

---

### Combination Sum (LeetCode 39)
**Pattern:** Backtracking

#### Optimal Solution (Backtracking, O(2^n))
```java
public List<List<Integer>> combinationSum(int[] candidates, int target) {
    List<List<Integer>> res = new ArrayList<>();
    backtrack(res, new ArrayList<>(), candidates, target, 0);
    return res;
}
private void backtrack(List<List<Integer>> res, List<Integer> temp, int[] candidates, int remain, int start) {
    if (remain < 0) return;
    if (remain == 0) {
        res.add(new ArrayList<>(temp));
        return;
    }
    for (int i = start; i < candidates.length; i++) {
        temp.add(candidates[i]);
        backtrack(res, temp, candidates, remain - candidates[i], i);
        temp.remove(temp.size() - 1);
    }
}
```
**Explanation:**
- Try each candidate, allowing repeats, and backtrack when the sum exceeds the target.

**Time Complexity:** O(2^n)
**Space Complexity:** O(target)

#### Recursive Solution
- The above is recursive (backtracking).

#### Improvement Areas
- For unique combinations, sort and skip duplicates.

---

### Word Search (LeetCode 79)
**Pattern:** Backtracking

#### Optimal Solution (Backtracking, O(m*n*4^L))
```java
public boolean exist(char[][] board, String word) {
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            if (dfs(board, word, i, j, 0)) return true;
        }
    }
    return false;
}
private boolean dfs(char[][] board, String word, int i, int j, int k) {
    if (k == word.length()) return true;
    if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != word.charAt(k)) return false;
    char temp = board[i][j];
    board[i][j] = '#';
    boolean found = dfs(board, word, i+1, j, k+1) || dfs(board, word, i-1, j, k+1) ||
                   dfs(board, word, i, j+1, k+1) || dfs(board, word, i, j-1, k+1);
    board[i][j] = temp;
    return found;
}
```
**Explanation:**
- For each cell, try to match the word recursively in all four directions, marking visited cells.

**Time Complexity:** O(m*n*4^L), where L is the word length
**Space Complexity:** O(L)

#### Recursive Solution
- The above is recursive (DFS/backtracking).

#### Improvement Areas
- Prune early if remaining letters can’t be matched.

---

### N-Queens (LeetCode 51)
**Pattern:** Backtracking

#### Optimal Solution (Backtracking, O(n!))
```java
public List<List<String>> solveNQueens(int n) {
    List<List<String>> res = new ArrayList<>();
    char[][] board = new char[n][n];
    for (char[] row : board) Arrays.fill(row, '.');
    backtrack(res, board, 0);
    return res;
}
private void backtrack(List<List<String>> res, char[][] board, int row) {
    if (row == board.length) {
        List<String> temp = new ArrayList<>();
        for (char[] r : board) temp.add(new String(r));
        res.add(temp);
        return;
    }
    for (int col = 0; col < board.length; col++) {
        if (isValid(board, row, col)) {
            board[row][col] = 'Q';
            backtrack(res, board, row + 1);
            board[row][col] = '.';
        }
    }
}
private boolean isValid(char[][] board, int row, int col) {
    for (int i = 0; i < row; i++) {
        if (board[i][col] == 'Q') return false;
        if (col - (row - i) >= 0 && board[i][col - (row - i)] == 'Q') return false;
        if (col + (row - i) < board.length && board[i][col + (row - i)] == 'Q') return false;
    }
    return true;
}
```
**Explanation:**
- Place queens row by row, checking for conflicts in columns and diagonals.

**Time Complexity:** O(n!)
**Space Complexity:** O(n^2)

#### Recursive Solution
- The above is recursive (backtracking).

#### Improvement Areas
- Use bitmasking for more efficient state representation.

--- 

---

## Dynamic Programming (DP)
- Climbing Stairs (LeetCode 70)
- House Robber (LeetCode 198)
- Coin Change (LeetCode 322)
- Longest Increasing Subsequence (LeetCode 300)
- Longest Common Subsequence (LeetCode 1143)
- Edit Distance (LeetCode 72)
- Word Break (LeetCode 139)
- Maximum Subarray (LeetCode 53)

### Climbing Stairs (LeetCode 70)
**Pattern:** Dynamic Programming

#### Optimal Solution (DP, O(n))
```java
public int climbStairs(int n) {
    if (n <= 2) return n;
    int a = 1, b = 2;
    for (int i = 3; i <= n; i++) {
        int c = a + b;
        a = b;
        b = c;
    }
    return b;
}
```
**Explanation:**
- The number of ways to reach the nth step is the sum of the ways to reach the previous two steps (Fibonacci sequence).

**Time Complexity:** O(n)
**Space Complexity:** O(1)

#### Recursive Solution
```java
public int climbStairs(int n) {
    if (n <= 2) return n;
    return climbStairs(n - 1) + climbStairs(n - 2);
}
```
**Explanation:**
- Recursively compute the number of ways for each step.

**Time Complexity:** O(2^n) (exponential, not efficient)
**Space Complexity:** O(n) (recursion stack)

#### Improvement Areas
- Use memoization to optimize the recursive solution.

---

### House Robber (LeetCode 198)
**Pattern:** Dynamic Programming

#### Optimal Solution (DP, O(n))
```java
public int rob(int[] nums) {
    if (nums.length == 0) return 0;
    if (nums.length == 1) return nums[0];
    int prev1 = 0, prev2 = 0;
    for (int num : nums) {
        int temp = prev1;
        prev1 = Math.max(prev2 + num, prev1);
        prev2 = temp;
    }
    return prev1;
}
```
**Explanation:**
- For each house, decide to rob it (and add to prev2) or skip it (take prev1).

**Time Complexity:** O(n)
**Space Complexity:** O(1)

#### Recursive Solution
```java
public int rob(int[] nums) {
    return robHelper(nums, nums.length - 1);
}
private int robHelper(int[] nums, int i) {
    if (i < 0) return 0;
    return Math.max(robHelper(nums, i - 2) + nums[i], robHelper(nums, i - 1));
}
```
**Explanation:**
- Recursively decide to rob or skip each house.

**Time Complexity:** O(2^n) (exponential, not efficient)
**Space Complexity:** O(n) (recursion stack)

#### Improvement Areas
- Use memoization to optimize the recursive solution.

---

### Coin Change (LeetCode 322)
**Pattern:** Dynamic Programming

#### Optimal Solution (DP, O(amount * n))
```java
public int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, amount + 1);
    dp[0] = 0;
    for (int coin : coins) {
        for (int i = coin; i <= amount; i++) {
            dp[i] = Math.min(dp[i], dp[i - coin] + 1);
        }
    }
    return dp[amount] > amount ? -1 : dp[amount];
}
```
**Explanation:**
- For each coin, update the minimum coins needed for each amount.

**Time Complexity:** O(amount * n), n = number of coins
**Space Complexity:** O(amount)

#### Recursive Solution
```java
public int coinChange(int[] coins, int amount) {
    if (amount == 0) return 0;
    int min = Integer.MAX_VALUE;
    for (int coin : coins) {
        if (amount - coin >= 0) {
            int res = coinChange(coins, amount - coin);
            if (res >= 0 && res < min) min = 1 + res;
        }
    }
    return min == Integer.MAX_VALUE ? -1 : min;
}
```
**Explanation:**
- Try every coin and recursively solve for the remaining amount.

**Time Complexity:** Exponential (very slow for large amounts)
**Space Complexity:** O(amount) (recursion stack)

#### Improvement Areas
- Use memoization to optimize the recursive solution.

---

### Longest Increasing Subsequence (LeetCode 300)
**Pattern:** Dynamic Programming

#### Optimal Solution (DP, O(n^2))
```java
public int lengthOfLIS(int[] nums) {
    int n = nums.length, res = 1;
    int[] dp = new int[n];
    Arrays.fill(dp, 1);
    for (int i = 1; i < n; i++) {
        for (int j = 0; j < i; j++) {
            if (nums[i] > nums[j]) {
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
        res = Math.max(res, dp[i]);
    }
    return res;
}
```
**Explanation:**
- For each element, check all previous elements and update the LIS ending at that element.

**Time Complexity:** O(n^2)
**Space Complexity:** O(n)

#### Improvement Areas
- Can be optimized to O(n log n) using binary search and patience sorting.

---

### Maximum Subarray (LeetCode 53)
**Pattern:** Dynamic Programming (Kadane's Algorithm)

#### Optimal Solution (Kadane's Algorithm, O(n))
```java
public int maxSubArray(int[] nums) {
    int maxSoFar = nums[0], maxEndingHere = nums[0];
    for (int i = 1; i < nums.length; i++) {
        maxEndingHere = Math.max(nums[i], maxEndingHere + nums[i]);
        maxSoFar = Math.max(maxSoFar, maxEndingHere);
    }
    return maxSoFar;
}
```
**Explanation:**
- At each step, decide whether to extend the current subarray or start a new one.

**Time Complexity:** O(n)
**Space Complexity:** O(1)

#### Recursive Solution
- Not practical for this problem.

#### Improvement Areas
- For subarray indices, track start and end positions.

--- 

---

## Greedy
- Jump Game (LeetCode 55)
- Merge Intervals (LeetCode 56)
- Insert Interval (LeetCode 57)
- Non-overlapping Intervals (LeetCode 435)
- Gas Station (LeetCode 134)

### Jump Game (LeetCode 55)
**Pattern:** Greedy

#### Optimal Solution (Greedy, O(n))
```java
public boolean canJump(int[] nums) {
    int maxReach = 0;
    for (int i = 0; i < nums.length; i++) {
        if (i > maxReach) return false;
        maxReach = Math.max(maxReach, i + nums[i]);
    }
    return true;
}
```
**Explanation:**
- Track the farthest index reachable at each step. If you ever reach an index beyond maxReach, return false.

**Time Complexity:** O(n)
**Space Complexity:** O(1)

#### Recursive Solution
- Not practical for this problem.

#### Improvement Areas
- For minimum jumps, see Jump Game II (LeetCode 45).

---

### Merge Intervals (LeetCode 56)
**Pattern:** Greedy

#### Optimal Solution (Sort + Merge, O(n log n))
```java
public int[][] merge(int[][] intervals) {
    if (intervals.length == 0) return new int[0][0];
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    List<int[]> res = new ArrayList<>();
    int[] curr = intervals[0];
    for (int i = 1; i < intervals.length; i++) {
        if (curr[1] >= intervals[i][0]) {
            curr[1] = Math.max(curr[1], intervals[i][1]);
        } else {
            res.add(curr);
            curr = intervals[i];
        }
    }
    res.add(curr);
    return res.toArray(new int[res.size()][]);
}
```
**Explanation:**
- Sort intervals by start. Merge overlapping intervals as you iterate.

**Time Complexity:** O(n log n)
**Space Complexity:** O(n)

#### Recursive Solution
- Not practical for this problem.

#### Improvement Areas
- For interval insertions, see Insert Interval (LeetCode 57).

---

### Gas Station (LeetCode 134)
**Pattern:** Greedy

#### Optimal Solution (Greedy, O(n))
```java
public int canCompleteCircuit(int[] gas, int[] cost) {
    int total = 0, curr = 0, start = 0;
    for (int i = 0; i < gas.length; i++) {
        total += gas[i] - cost[i];
        curr += gas[i] - cost[i];
        if (curr < 0) {
            start = i + 1;
            curr = 0;
        }
    }
    return total < 0 ? -1 : start;
}
```
**Explanation:**
- If total gas is less than total cost, no solution. Otherwise, the start index after a deficit is the answer.

**Time Complexity:** O(n)
**Space Complexity:** O(1)

#### Recursive Solution
- Not practical for this problem.

#### Improvement Areas
- Understand why greedy works: only need to check after a deficit.

---

## Heaps & Priority Queue
- Kth Largest Element in an Array (LeetCode 215)
- Merge k Sorted Lists (LeetCode 23)
- Top K Frequent Words (LeetCode 692)
- Find Median from Data Stream (LeetCode 295)

### Kth Largest Element in an Array (LeetCode 215)
**Pattern:** Heap

#### Optimal Solution (Min Heap, O(n log k))
```java
public int findKthLargest(int[] nums, int k) {
    PriorityQueue<Integer> heap = new PriorityQueue<>();
    for (int num : nums) {
        heap.offer(num);
        if (heap.size() > k) heap.poll();
    }
    return heap.peek();
}
```
**Explanation:**
- Maintain a min-heap of size k. The root is the kth largest.

**Time Complexity:** O(n log k)
**Space Complexity:** O(k)

#### Improvement Areas
- Quickselect can achieve average O(n) time.

---

### Merge k Sorted Lists (LeetCode 23)
**Pattern:** Heap

#### Optimal Solution (Min Heap, O(N log k))
```java
public ListNode mergeKLists(ListNode[] lists) {
    PriorityQueue<ListNode> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a.val));
    for (ListNode node : lists) if (node != null) heap.offer(node);
    ListNode dummy = new ListNode(0), curr = dummy;
    while (!heap.isEmpty()) {
        ListNode node = heap.poll();
        curr.next = node;
        curr = curr.next;
        if (node.next != null) heap.offer(node.next);
    }
    return dummy.next;
}
```
**Explanation:**
- Use a min-heap to always pull the smallest node from the k lists.

**Time Complexity:** O(N log k), N = total nodes
**Space Complexity:** O(k)

#### Improvement Areas
- Divide and conquer can also achieve O(N log k).

--- 

---

## Graphs (BFS/DFS/Union-Find)
- Number of Islands (LeetCode 200)
- Clone Graph (LeetCode 133)
- Course Schedule (LeetCode 207)
- Word Ladder (LeetCode 127)
- Graph Valid Tree (LeetCode 261)
- Redundant Connection (LeetCode 684)

### Number of Islands (LeetCode 200)
**Pattern:** BFS/DFS

#### Optimal Solution (DFS, O(m*n))
```java
public int numIslands(char[][] grid) {
    int count = 0;
    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[i][j] == '1') {
                dfs(grid, i, j);
                count++;
            }
        }
    }
    return count;
}
private void dfs(char[][] grid, int i, int j) {
    if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == '0') return;
    grid[i][j] = '0';
    dfs(grid, i + 1, j);
    dfs(grid, i - 1, j);
    dfs(grid, i, j + 1);
    dfs(grid, i, j - 1);
}
```
**Explanation:**
- For each '1', start a DFS to mark all connected land as visited.

**Time Complexity:** O(m*n)
**Space Complexity:** O(m*n) (recursion stack)

#### Improvement Areas
- Can use BFS or Union-Find for alternative solutions.

---

### Clone Graph (LeetCode 133)
**Pattern:** BFS/DFS

#### Optimal Solution (DFS with HashMap, O(n))
```java
public Node cloneGraph(Node node) {
    if (node == null) return null;
    Map<Node, Node> map = new HashMap<>();
    return dfs(node, map);
}
private Node dfs(Node node, Map<Node, Node> map) {
    if (map.containsKey(node)) return map.get(node);
    Node copy = new Node(node.val);
    map.put(node, copy);
    for (Node neighbor : node.neighbors) {
        copy.neighbors.add(dfs(neighbor, map));
    }
    return copy;
}
```
**Explanation:**
- Use a map to avoid revisiting nodes. Recursively clone each node and its neighbors.

**Time Complexity:** O(n)
**Space Complexity:** O(n)

#### Improvement Areas
- BFS can also be used for iterative cloning.

---

### Course Schedule (LeetCode 207)
**Pattern:** Topological Sort (BFS)

#### Optimal Solution (BFS, O(V+E))
```java
public boolean canFinish(int numCourses, int[][] prerequisites) {
    List<List<Integer>> graph = new ArrayList<>();
    int[] indegree = new int[numCourses];
    for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());
    for (int[] pre : prerequisites) {
        graph.get(pre[1]).add(pre[0]);
        indegree[pre[0]]++;
    }
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) if (indegree[i] == 0) queue.offer(i);
    int count = 0;
    while (!queue.isEmpty()) {
        int curr = queue.poll();
        count++;
        for (int next : graph.get(curr)) {
            if (--indegree[next] == 0) queue.offer(next);
        }
    }
    return count == numCourses;
}
```
**Explanation:**
- Build a graph and track indegrees. Use BFS to process nodes with zero indegree.

**Time Complexity:** O(V+E)
**Space Complexity:** O(V+E)

#### Improvement Areas
- Can also use DFS for cycle detection.

---

## Tries
- Implement Trie (Prefix Tree) (LeetCode 208)
- Word Search II (LeetCode 212)
- Design Add and Search Words Data Structure (LeetCode 211)

### Implement Trie (LeetCode 208)
**Pattern:** Trie

#### Optimal Solution (Trie Node, O(m) per operation)
```java
class Trie {
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd = false;
    }
    private TrieNode root;
    public Trie() { root = new TrieNode(); }
    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int i = c - 'a';
            if (node.children[i] == null) node.children[i] = new TrieNode();
            node = node.children[i];
        }
        node.isEnd = true;
    }
    public boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int i = c - 'a';
            if (node.children[i] == null) return false;
            node = node.children[i];
        }
        return node.isEnd;
    }
    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            int i = c - 'a';
            if (node.children[i] == null) return false;
            node = node.children[i];
        }
        return true;
    }
}
```
**Explanation:**
- Each node has 26 children (for lowercase letters). Insert, search, and prefix check are all O(m), m = word length.

**Time Complexity:** O(m) per operation
**Space Complexity:** O(n*m), n = number of words

#### Improvement Areas
- For Unicode, use a HashMap instead of an array.

---

## Bit Manipulation
- Single Number (LeetCode 136)
- Number of 1 Bits (LeetCode 191)
- Counting Bits (LeetCode 338)
- Missing Number (LeetCode 268)

### Single Number (LeetCode 136)
**Pattern:** Bit Manipulation

#### Optimal Solution (XOR, O(n))
```java
public int singleNumber(int[] nums) {
    int res = 0;
    for (int num : nums) res ^= num;
    return res;
}
```
**Explanation:**
- XOR-ing all numbers cancels out pairs, leaving the unique number.

**Time Complexity:** O(n)
**Space Complexity:** O(1)

#### Improvement Areas
- For numbers appearing three times, use bit counting.

---

## Advanced Patterns
- LRU Cache (LeetCode 146)
- LFU Cache (LeetCode 460)
- Design Twitter (LeetCode 355)
- Design Snake Game (LeetCode 353)

### LRU Cache (LeetCode 146)
**Pattern:** Design, HashMap + Doubly Linked List

#### Optimal Solution (O(1) per operation)
```java
class LRUCache {
    private final int capacity;
    private final Map<Integer, Node> map;
    private final Node head, tail;
    class Node {
        int key, value;
        Node prev, next;
        Node(int k, int v) { key = k; value = v; }
    }
    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }
    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node node = map.get(key);
        remove(node);
        insert(node);
        return node.value;
    }
    public void put(int key, int value) {
        if (map.containsKey(key)) remove(map.get(key));
        if (map.size() == capacity) remove(tail.prev);
        insert(new Node(key, value));
    }
    private void remove(Node node) {
        map.remove(node.key);
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    private void insert(Node node) {
        map.put(node.key, node);
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
}
```
**Explanation:**
- Use a doubly linked list for order and a HashMap for O(1) access.

**Time Complexity:** O(1) per operation
**Space Complexity:** O(capacity)

#### Improvement Areas
- For LFU, use frequency lists.

--- 