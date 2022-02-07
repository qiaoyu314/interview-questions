class Solution {
    public int minimumTime(String s) {
        char[] c = s.toCharArray();
        
        // dpL[i] is the min cost of removing [1st, ith] "1" from the left side. 
        // We have two choise:
        // 1) Removing all cars on the left. 
        // 2) Removing it from the middle. 
        List<Integer> dpL = new ArrayList<>();
        dpL.add(0);
        for (int i = 0; i< c.length; i++) {
            if (c[i] == '1') {
                dpL.add(Math.min(i+1, dpL.get(dpL.size() - 1) + 2));
            }
        }
        
        // dpR[i] is the min cost of removing [1st, ith] "1" from the right side. 
        List<Integer> dpR = new ArrayList<>();
        dpR.add(0);
        for (int i = c.length - 1; i > -1; i--) {
            if (c[i] == '1') {
                dpR.add(Math.min(c.length - i, dpR.get(dpR.size() - 1) + 2));
            }
        }
        
        int result = Integer.MAX_VALUE;
        int l = 0;
        int r = dpR.size() - 1;
        while (l < dpL.size()) {
            result = Math.min(result, dpL.get(l++) + dpR.get(r--));
        }
        
        return result;
    }
}
