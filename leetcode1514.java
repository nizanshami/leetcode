/*
 * You are given an undirected weighted graph of n nodes (0-indexed), represented by an edge list where edges[i] = [a, b] is an undirected edge connecting the nodes a and b with a probability of success of traversing that edge succProb[i].

Given two nodes start and end, find the path with the maximum probability of success to go from start to end and return its success probability.

If there is no path from start to end, return 0. Your answer will be accepted if it differs from the correct answer by at most 1e-5.

 

Example 1:



Input: n = 3, edges = [[0,1],[1,2],[0,2]], succProb = [0.5,0.5,0.2], start = 0, end = 2
Output: 0.25000
Explanation: There are two paths from start to end, one having a probability of success = 0.2 and the other has 0.5 * 0.5 = 0.25.
Example 2:



Input: n = 3, edges = [[0,1],[1,2],[0,2]], succProb = [0.5,0.5,0.3], start = 0, end = 2
Output: 0.30000
Example 3:



Input: n = 3, edges = [[0,1]], succProb = [0.5], start = 0, end = 2
Output: 0.00000
Explanation: There is no path between 0 and 2.
 

Constraints:

2 <= n <= 10^4
0 <= start, end < n
start != end
0 <= a, b < n
a != b
0 <= succProb.length == edges.length <= 2*10^4
0 <= succProb[i] <= 1
There is at most one edge between every two nodes.
 */


import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

class Solution {
    public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
        double[] probFromS = new double[n];
        Map<Integer, List<int[]>> adjList = new HashMap<>();
        PriorityQueue<double[]> pq = new PriorityQueue<>((a, b) -> a[1] > b[1] ? -1 : (a[1] < b[1] ? 1 : 0 ) );
        // create adj list
        for (int i = 0; i < edges.length; i++) {
            if (adjList.containsKey(edges[i][0])) {
                adjList.get(edges[i][0]).add(new int[]{edges[i][1], i});
            } else {
                List<int[]> l = new ArrayList<>();
                l.add(new int[]{edges[i][1], i});
                adjList.put(edges[i][0], l);
            }
            if (adjList.containsKey(edges[i][1])) {
                adjList.get(edges[i][1]).add(new int[]{edges[i][0], i});
            } else {
                List<int[]> l = new ArrayList<>();
                l.add(new int[]{edges[i][0], i});
                adjList.put(edges[i][1], l);
            }
        }
        if (adjList.get(start) == null){
            return 0;
        }
        // init dijkstra
        for(int i = 0; i < n; i++){
            probFromS[i] = i == start ? 1 : -1;
        }

        pq.add(new double[] {start, 1});
        while (!pq.isEmpty()) {
            int u = (int) pq.poll()[0];
            if (u == end) {
                break;
            }

            for (int[] v : adjList.get(u)) {
                double newDis = probFromS[u] * succProb[v[1]];
                if (probFromS[v[0]] < newDis){
                    probFromS[v[0]] = newDis;
                    pq.add(new double[] {v[0], newDis});
                } 
            }
        }

        return probFromS[end] > 0 ? probFromS[end] : 0;
    }
}