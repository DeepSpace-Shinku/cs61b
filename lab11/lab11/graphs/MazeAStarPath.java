package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;
/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private MinPQ<SearchNode> pq;

    private class SearchNode implements Comparable<SearchNode>
    {
        private int index;
        private int priority;
        private int moves;

        public SearchNode(int index, int moves)
        {
            this.index = index;
            this.moves = moves;
            this.priority = calculatePriority();
        }

        public int getIndex()
        {
            return index;
        }

        private int calculatePriority()
        {
            return moves + h(index);
        }

        @Override
        public int compareTo(SearchNode o) {
            return this.priority - o.priority;
        }
    }


    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        pq = new MinPQ<>();
        SearchNode firstNode = new SearchNode(s, 0);
        pq.insert(firstNode);
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t)) + Math.abs(maze.toY(v) - maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return pq.delMin().index;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
        while (!pq.isEmpty()) {
            SearchNode topNode = pq.delMin();
            int top = topNode.getIndex();
            marked[top] = true;
            announce();
            if (top == t) break;
            for (int neighbour : maze.adj(top)) {
                if (!marked[neighbour]) {
                    pq.insert(new SearchNode(neighbour, topNode.moves + 1));
                    distTo[neighbour] = distTo[top] + 1;
                    edgeTo[neighbour] = top;
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

