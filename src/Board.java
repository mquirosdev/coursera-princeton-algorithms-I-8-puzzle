import java.util.ArrayList;

public class Board {
    private final int dimension;
    private final int[][] grid;

    public Board(int[][] tiles) {
        dimension = tiles.length;

        int[][] grid = new int[dimension][dimension];
        for(int i = 0; i < dimension; i++) grid[i] = tiles[i].clone();
        this.grid = grid;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(dimension);
        str.append("\n");

        for (int i = 0; i < grid.length; i++) {
            int[] row = grid[i];
            for (int j = 0; j < row.length; j++) str.append(" " + row[j]);
            if (i < grid.length - 1) str.append("\n");
        }

        return str.toString();
    }

    public int dimension() {
        return this.dimension;
    }

    public int hamming() {
        int h = 0;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int linearIndex = (i * dimension) + j;
                if (linearIndex == lastLinearIndex()) continue;
                if (grid[i][j] != linearIndex + 1) h++;
            }
        }

        return h;
    }

    public int manhattan() {
        int m = 0;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int goalLinearIndex = grid[i][j] - 1;
                if (goalLinearIndex < 0) continue;

                int gi = (goalLinearIndex / dimension);
                int gj = (goalLinearIndex % dimension);
                int diff = Math.abs(i - gi) + Math.abs(j - gj);
                m = m + diff;
            }
        }

        return m;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public boolean equals(Object y) {
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;

        if (that.dimension != dimension) return false;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (grid[i][j] != that.grid[i][j]) return false;
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> boards = new ArrayList<Board>();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (grid[i][j] == 0) {
                    computeMovesFromCell(boards, i, j);
                }
            }
        }

        return boards;
    }

    public Board twin() {
        return this;
    }

    private void computeMovesFromCell(ArrayList<Board> boards, int y, int x) {
        int[][] deltas = { { -1, 0 },  { 1, 0 }, { 0, -1 }, { 0 , 1 } };

        for (int[] delta : deltas) {
            int dy = delta[0];
            int dx = delta[1];

            int i = y + dy;
            int j = x + dx;

            if (i >= 0 && i < dimension && j >= 0 && j < dimension) {
               Board board = new Board(this.grid);
               int tmp = board.grid[y][x];
               board.grid[y][x] = board.grid[i][j];
               board.grid[i][j] = tmp;
               boards.add(board);
            }
        }
    }

    private int lastLinearIndex() {
        return (dimension * dimension) - 1;
    }

    public static void main(String[] args) {
        // previousBoard: null, heuristic
        int[][] tiles1 = {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        };

        int[][] tiles2 = {
                { 8, 0, 3 },
                { 4, 1, 2 },
                { 7, 6, 5 }
        };

        Board board1 = new Board(tiles1);
        Board board2 = new Board(tiles2);
        System.out.println(board1);
        for(Board b : board1.neighbors()) {
            System.out.println(b);
        }
//        System.out.println(board1);
//        System.out.println(board1.hamming());
//        System.out.println(board1.manhattan());
    }
}
