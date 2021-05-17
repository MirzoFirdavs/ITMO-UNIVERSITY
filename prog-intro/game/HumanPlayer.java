package game;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final Scanner in;
    Scanner in1;
    private int n, m;

    public HumanPlayer(final Scanner in) {
        this.in = in;
    }

    public HumanPlayer() {
        this(new Scanner(System.in));
    }

    @Override
    public Move makeMove(final Position position, final Cell cell) {
        this.n = position.getN();
        this.m = position.getM();
        while (true) {
            //System.out.println("Current position");
            //System.out.println(position.toString());
            int row;
            do {
                System.out.println("Enter " + cell + "'s move");
                System.out.println("Row: ");
                in1 = new Scanner(in.nextLine());
                try {
                    row = in1.nextInt();
                } catch (NoSuchElementException e) {
                    row = -1;
                }
            } while (row >= n || row < 0);
            int col;
            do {
                System.out.println("Col: ");
                in1 = new Scanner(in.nextLine());
                try {
                    col = in1.nextInt();
                } catch (NoSuchElementException e) {
                    col = -1;
                }
            } while (col >= m || col < 0);
            final Move move = new Move(row, col, cell);
            if (position.isValid(move)) {
                return move;
            } else {
                if ((move.getRow() < 0 || move.getRow() >= n) || (move.getCol() < 0 || move.getCol() >= m)) {
                    System.out.println("Error coordinate, try again please");
                } else if (position.get(move.getRow(), move.getCol()) != Cell.E) {
                    System.out.println("Position " + row + " " + col + " is not empty, try again please");
                }
            }
        }
    }
}