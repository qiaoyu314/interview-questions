class OneListSolution {
    public int minimumTime(String s) {
        char[] c = s.toCharArray();
        
        // dpL[i] is the min cost of removing [1st, ith] 1s from the left size. 
        // We have two choise:
        // 1) Removing all cars on the left. 
        // 2) Removing it from the middle. 
        
        Deque<Integer> dpL = new ArrayDeque<>();
        dpL.addLast(0);
        for (int i = 0; i< c.length; i++) {
            if (c[i] == '1') {
                dpL.addLast(Math.min(i+1, dpL.getLast() + 2));
            }
        }
        
        int result = Integer.MAX_VALUE;
        int costOfRemovingOnesFromRight = 0;
        for (int r = c.length - 1; r >=-1; r--) {
            if (r == -1) {
                // Special case: remove all 1s from the right side.
                result = Math.min(result, costOfRemovingOnesFromRight);
            } else if (c[r] == '1') {
                result = Math.min(result, costOfRemovingOnesFromRight + dpL.removeLast());
                costOfRemovingOnesFromRight = c.length - r;
            }
        }
        
        return result;
    }
}
