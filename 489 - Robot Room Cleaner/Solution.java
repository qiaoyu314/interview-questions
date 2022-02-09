// https://leetcode.com/problems/robot-room-cleaner/

/**
 * // This is the robot's control interface.
 * // You should not implement it, or speculate about its implementation
 * interface Robot {
 *     // Returns true if the cell in front is open and robot moves into the cell.
 *     // Returns false if the cell in front is blocked and robot stays in the current cell.
 *     public boolean move();
 *
 *     // Robot will stay in the same cell after calling turnLeft/turnRight.
 *     // Each turn will be 90 degrees.
 *     public void turnLeft();
 *     public void turnRight();
 *
 *     // Clean the current cell.
 *     public void clean();
 * }
 */

class Solution {
    public void cleanRoom(Robot robot) {
        // Init the map.
        boolean[][] cleaned = new boolean[2 * 100  - 1][2 * 200 - 1];
        dfs(robot, Direction.UP, 99, 199, cleaned);
    }
    
    enum Direction {
        UP(0),
        RIGHT(1), 
        DOWN(2),
        LEFT(3);
        
        final int value;
        Direction(int i) {
            value = i;
        }
    }
    
    // The row and column indeices offset for the four directions. 
    private static int[][] OFFSET = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    
    // Defines the cleaning order.
    private static Direction[] DIRECTIONS = {Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT};
    
    // Guides the robot to move back.
    private static Direction[] OPPO_DIRECTIONS = {Direction.DOWN, Direction.LEFT, Direction.UP, Direction.RIGHT};
    
    private Direction dfs(Robot robot, Direction curDir, int row, int column, boolean[][] cleaned) {        
        robot.clean();
        
        cleaned[row][column] = true;
        
        // Try to move the neighbours. Skip it if:
        // 1) It's already cleaned.
        // 2) It's wall. 
            
        for (int i = 0; i < OFFSET.length; i++) {
            int[] offset = OFFSET[i];
            Direction movingDirection = DIRECTIONS[i];
            if (!cleaned[row + offset[0]][column + offset[1]]) {
                rotate(robot, curDir, movingDirection);
                curDir = movingDirection;
                if(robot.move()) {
                    // Recursion.
                    Direction newDir = dfs(robot, movingDirection, row + offset[0], column + offset[1], cleaned);
                    // Need to move back to the current location in order to clean another neighbour.
                    rotate(robot, newDir, OPPO_DIRECTIONS[i]);
                    curDir = OPPO_DIRECTIONS[i];
                    robot.move();
                }
            } 
        }
        return curDir;
    }
    
    private void rotate(Robot robot, Direction curDir, Direction desiredDir) {
        int diff = desiredDir.value - curDir.value;
        for (int i = 0; i < Math.abs(diff); i++ ) {
            if (diff > 0) {
                robot.turnRight();  
            } else {
                robot.turnLeft();
            }
        }
    }
}
