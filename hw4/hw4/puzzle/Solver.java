package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class Solver
{
    private final int INF = 2147483647;
    private List<WorldState> solution;
    private MinPQ<SearchNode> pq;
    private int moves;

    private class SearchNode implements Comparable<SearchNode> {
        private SearchNode prev;
        private WorldState worldState;
        private int moves;
        private Integer priority;

        public SearchNode(WorldState worldState, int moves, SearchNode prev)
        {
            this.worldState = worldState;
            this.moves = moves;
            this.prev = prev;
        }

        public boolean isGoal()
        {
            return worldState.isGoal();
        }

        private int priority()
        {
            if (this.priority == null){
                this.priority =  moves + worldState.estimatedDistanceToGoal();
            }
            return this.priority;
        }


        @Override
        public int compareTo(SearchNode o) {
            return this.priority() - o.priority();
        }

        public Iterable<WorldState> neighbours()
        {
            return this.worldState.neighbors();
        }

        public WorldState getWorldState() {
            return worldState;
        }
    }

    public Solver(WorldState initial)
    {
        solution = new LinkedList<>();
        pq = new MinPQ<>();
        SearchNode firstNode = new SearchNode(initial, 0, null);
        pq.insert(firstNode);
        solve();
    }


    public int moves()
    {
        return this.moves;
    }

    public Iterable<WorldState> solution()
    {
        return this.solution;
    }

    private void solve()
    {
        while (true) {
            SearchNode top = pq.delMin();
            //System.out.println("Pop node: " + top.getWorldState().toString());
            if (!top.isGoal()) {
                enqueueAllNeighbours(top);
            }else{
                constructSolutionAndMoves(top);
                //System.out.println("Done!\n\n");
                return;
            }
        }
    }

    private void constructSolutionAndMoves(SearchNode lastNode)
    {
        moves = lastNode.moves;
        constructHelper(lastNode);
    }

    private void constructHelper(SearchNode node)
    {
        if (node != null){
            constructHelper(node.prev);
            solution.add(node.getWorldState());
        }
    }

    private void enqueueAllNeighbours(SearchNode node)
    {
        int moves = node.moves + 1;
        for (WorldState neighbour: node.neighbours()) {
            if ((node.prev == null || !neighbour.equals(node.prev.getWorldState())) && !node.getWorldState().equals(neighbour)){
                SearchNode searchNode = new SearchNode(neighbour, moves, node);
                //System.out.println("Insert node: " + neighbour.toString() + " with range " + moves + "\n");
                pq.insert(searchNode);
            }
        }
    }


}
