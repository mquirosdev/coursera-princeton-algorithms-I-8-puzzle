import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;

public class Solver {
    private class SearchNode implements Comparable<SearchNode> {
        private final int priority;
        private final int moves;
        private final int manhattan;
        private final Board board;
        private final SearchNode previous;

        public SearchNode(int moves, SearchNode previous, Board board) {
            this.previous = previous;
            this.board = board;
            this.moves = moves;
            this.manhattan = this.board.manhattan();
            this.priority = this.moves + this.manhattan;
        }

        public int compareTo(SearchNode that) {
            return Integer.compare(this.priority, that.priority);
        }

        public String toString() {
            return this.moves + "\n" + this.board.toString();
        }

        public boolean isGoal() {
            return this.manhattan == 0;
        }
    }

    private SearchNode goal;
    private MinPQ<SearchNode> pq;
    private ArrayList<Board> solution;

    public Solver(Board initial) {
        goal = null;
        pq = new MinPQ<SearchNode>();
        solution = new ArrayList<Board>();
        solve(initial);
    }

    public boolean isSolvable() {
        return goal != null;
    }

    public int moves() {
        return isSolvable() ? goal.moves : -1;
    }

    public Iterable<Board> solution() {
        return isSolvable() ?  this.solution : null;
    }

    private void solve(Board initial) {
        pq.insert(new SearchNode(0, null, initial));
        SearchNode node = null;

        while (!pq.isEmpty()) {
            node = pq.delMin();

            if (node.isGoal()) {
                goal = node;
                break;
            };

            Board prevBoard = node.previous != null ? node.previous.board : null;
            for (Board b : node.board.neighbors()) {
                if (prevBoard != null && prevBoard.equals(b)) continue;
                pq.insert(new SearchNode(node.moves + 1, node, b));
            }
        }

        if (goal != null) {
            SearchNode current = node;
            while (current != null) {
                solution.add(current.board);
                current = current.previous;
            }

            Collections.reverse(this.solution);
        }
    }

    public static void main(String[] args) {
        int[][] tiles1 = {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        };

        Board board1 = new Board(tiles1);

        Solver s = new Solver(board1);
        System.out.println("is solvable:" + s.isSolvable());
        System.out.println("moves:" + s.moves());
        for(Board b : s.solution()) {
            System.out.println(b);
        }
    }
}
