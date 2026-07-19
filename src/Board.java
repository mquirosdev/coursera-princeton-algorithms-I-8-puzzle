public class Board {
    private final int dimension;
    private final int[][] grid;

    public Board(int[][] tiles) {
        this.grid = tiles;
        this.dimension = tiles.length;
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

    private int lastLinearIndex() {
        return (dimension * dimension) - 1;
    }

    public static void main(String[] args) {
        // previousBoard: null, heuristic
        int[][] tiles = {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        };

        Board board = new Board(tiles);
        System.out.println(board);
        System.out.println(board.hamming());
        System.out.println(board.manhattan());
    }
}
