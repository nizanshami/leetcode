/*
 * You are given two integer arrays nums1 and nums2 sorted in ascending order and an integer k.

Define a pair (u, v) which consists of one element from the first array and one element from the second array.

Return the k pairs (u1, v1), (u2, v2), ..., (uk, vk) with the smallest sums.

 

Example 1:

Input: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
Output: [[1,2],[1,4],[1,6]]
Explanation: The first 3 pairs are returned from the sequence: [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
Example 2:

Input: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
Output: [[1,1],[1,1]]
Explanation: The first 2 pairs are returned from the sequence: [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
Example 3:

Input: nums1 = [1,2], nums2 = [3], k = 3
Output: [[1,3],[2,3]]
Explanation: All possible pairs are returned from the sequence: [1,3],[2,3]
 

Constraints:

1 <= nums1.length, nums2.length <= 105
-109 <= nums1[i], nums2[i] <= 109
nums1 and nums2 both are sorted in ascending order.
1 <= k <= 104
 */

import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue; 
import java.util.List;

class Solution {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> result = new ArrayList<>();
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a,b) -> a[0] - b[0]);
        int n = nums1.length;
        int m = nums2.length;
        int i = 0;
        int j = 0;
        int t = 0;

        //for inserting to the heap each (i, j) only once
        Set<String> set = new HashSet<>();
        
        //init start state
        set.add("(0,0)");
        minHeap.offer(new int[] {nums1[0]+nums2[0], i, j});
        
        while (t < k && !minHeap.isEmpty()){
            int[] tmp = minHeap.poll();
            i = tmp[1];
            j = tmp[2];
            //adding the pair to the finel result
            result.add(List.of(nums1[i], nums2[j]));
            if(i + 1 < n){
                String key = String.format("(%d, %d)", i+1, j);
                if(!set.contains(key)){
                    set.add(key);
                    minHeap.offer(new int[] {nums1[i+1] + nums2[j], i+1, j});       
                }
            }
            if(j + 1 < m){
                String key = String.format("(%d, %d)", i, j+1);
                if(!set.contains(key)){
                    set.add(key);
                    minHeap.add(new int[] {nums1[i] + nums2[j+1], i, j+1});   
                }
            }
        t++;
        }
        return result;
    }
}