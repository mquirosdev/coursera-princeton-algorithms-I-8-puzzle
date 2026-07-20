import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class PuzzleChecker {
    public static void main(String[] args) {

        // for each command-line argument
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            Board initial = new Board(tiles);
            System.out.println(initial);
            System.out.println(initial.hamming());
//            Solver solver = new Solver(initial);
//            StdOut.println(filename + ": " + solver.moves());
        }
    }
}
