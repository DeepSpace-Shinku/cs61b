package lab11.graphs;

import edu.princeton.cs.algs4.Queue;
/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private Queue<Integer> fringe;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        fringe = new Queue<Integer>();
        fringe.enqueue(s);
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        while(true){
            int top = fringe.dequeue();
            marked[top] = true;
            announce();
            if (top == t) break;
            for(int neighbour: maze.adj(top)){
                if (!marked[neighbour]) {
                    fringe.enqueue(neighbour);
                    distTo[neighbour] = distTo[top] + 1;
                    edgeTo[neighbour] = top;
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

