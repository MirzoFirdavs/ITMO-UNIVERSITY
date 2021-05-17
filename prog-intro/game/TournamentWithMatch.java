package game;

import java.util.Random;

public class TournamentWithMatch {
    private Player[] queue;

    public TournamentWithMatch(int c, int b, int d, int n, int m, int k, Player[] queue) {
        this.queue = queue;
        Table table = new Table(b);
        for (int tur = 0; tur < c; ++tur) {
            for (int i = 1; i <= b; ++i) {
                for (int j = i + 1; j <= b; ++j) {
                    int score1 = 0;
                    int score2 = 0;
                    int[] x = new int[2];
                    x[0] = i - 1;
                    x[1] = j - 1;
                    int a = 1;
                    while (score1 != d && score2 != d) {
                        final Random random = new Random();
                        a = random.nextInt(2);
                        System.out.println("Player " + (x[a] + 1) + " plays with Player " + (x[1 - a] + 1));
                        final Game game = new Game(queue[x[a]], queue[x[1 - a]], true);
                        int res = game.play(new Board(n, m, k));
                        if (res == 1) {
                            System.out.println("X's wins");
                            System.out.println("Game result: Player " + (x[a] + 1));
                            score1++;
                        } else if (res == 2) {
                            System.out.println("O's wins");
                            System.out.println("Game result: Player" + (x[1 - a] + 1));
                            score2++;
                        }
                    }
                    if (score1 == d) {
                        System.out.println("X's won in this match");
                        table.add(x[a], 3);
                    } else {
                        System.out.println("O's won in this match");
                        table.add(x[1 - a], 3);
                    }
                }
            }
        }
        System.out.println(table.toString());
    }
}