package game;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Scanner in1;
        int m, n, k;
        do {
            System.out.println("Give the values of {n, m, k}");
            in1 = new Scanner(in.nextLine());
            try {
                n = in1.nextInt();
                m = in1.nextInt();
                k = in1.nextInt();
            } catch (NoSuchElementException e) {
                n = -1;
                m = -1;
                k = -1;
            }
        } while (n < 1 || m < 1 || k < 1);
        int c, b, x;
        do {
            System.out.println("Give a value of count of tour, members of players and winner score of match: ");
            in1 = new Scanner(in.nextLine());
            try {
                c = in1.nextInt();
                b = in1.nextInt();
                x = in1.nextInt();
            } catch (NoSuchElementException e) {
                c = 0;
                b = 0;
                x = -1;
            }
        } while (c < 1 || b < 2 || x < 1);
        Player[] queue = new Player[b];
        for (int i = 0; i < b; ++i) {
            int num;
            do {
                System.out.println("Give a Type of Players: HumanPlayer - 1, RandomPlayer - 2, SequentialPlayer - 3 ");
                in1 = new Scanner(in.nextLine());
                try {
                    num = in1.nextInt();
                } catch (NoSuchElementException e) {
                    num = 0;
                }
            } while (num < 1 || num > 3);
            if (num == 1) {
                queue[i] = new HumanPlayer();
            } else if (num == 2) {
                queue[i] = new RandomPlayer();
            } else {
                queue[i] = new SequentialPlayer();
            }
        }
        new TournamentWithMatch(c, b, x, n, m, k, queue);
    }
}