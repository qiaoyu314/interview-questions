
/*
A modified version of https://leetcode.com/problems/max-area-of-island/

but instead of 1s and 0s, there were different numbers.
*/

public class Main
{
    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] grid = {{0,1,2,2}, {0,2,2,3}, {0,0,0,3}};
        System.out.println(s.maxAreaOfIsland(grid));
    }
    
    static class Solution {
        int[][] grid;
        boolean[][] seen;
        int ans = 0;

    public int area(int r, int c, int preVal) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length ||
                seen[r][c] || grid[r][c] == 0)
            return 0;
        seen[r][c] = true;
        
        int area = (1 + area(r+1, c, grid[r][c]) + area(r-1, c, grid[r][c])
                  + area(r, c-1, grid[r][c]) + area(r, c+1, grid[r][c]));
        ans = Math.max(ans, area);
                  
        return grid[r][c] == preVal ? area : 0;
    }

    public int maxAreaOfIsland(int[][] grid) {
        this.grid = grid;
        seen = new boolean[grid.length][grid[0].length];
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                area(r, c, grid[r][c]);
            }
        }
        return ans;
    }
}
}
