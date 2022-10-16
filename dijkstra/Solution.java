
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        HashMap<Integer, HashMap<Integer, Integer>> mp = new HashMap<>();
        int[] shortestDistance = new int[n];
        
        for (int i = 0; i < flights.length; ++i) {
            // mp.put(flights[i][0], new HashMap<Integer, Integer>(flights[i][1], flights[i][2]));
            HashMap<Integer, Integer> map = mp.getOrDefault(flights[i][0], new HashMap<>());
            map.put(flights[i][1], flights[i][2]);
            mp.put(flights[i][0], map);
        }
        int[] visited = new int[n];
        // (distance, destination node, step)
        // // (a, b) -> {
        //     int firstElementOfA = a.get(0);
        //     int firstEleOfB = b.get(0);
        //     return firstElementOfA - firstEleOfB;
        // }
        PriorityQueue<List<Integer>> q = new PriorityQueue<>((a, b) -> Integer.compare(a.get(0), b.get(0)));
        // List<Integer>
        q.offer(List.of(0, src, -1));
        int shortest = Integer.MAX_VALUE;
        int shortestTarget = -1;
        while (!q.isEmpty()) {
            List<Integer> target = q.poll();
            HashMap<Integer, Integer> neighbors = mp.get(target.get(1));
            // for (Integer neighbor : neighbors.keySet()); 
            if (neighbors == null) {
                break;
            }
            visited[target.get(1)] = 1;
            final int baseDistance = target.get(0);
            final int basestep = target.get(2);
            for (Integer neighbor : neighbors.keySet()) {
                if (visited[neighbor] == 1) {
                    continue;
                }
                int delta = neighbors.get(neighbor);
                int existingShortest = shortestDistance[neighbor];
                //basedistance = math.min(basedistance, d + bd);
                if (existingShortest == 0) {
                    shortestDistance[neighbor] = delta + baseDistance;
                } else {
                    existingShortest = Math.min(existingShortest, delta + baseDistance);
                    shortestDistance[neighbor] = existingShortest;
                }
                int step = basestep + 1;
                if (step < k && neighbor != dst) {
                    q.offer(List.of(delta + baseDistance, neighbor, step));
                }
            }
        }
        if (shortestDistance[dst] == 0) {
            return -1;
        }
        return shortestDistance[dst];
    }
}