package game;

public class Match {
    private int x;
    private int m;
    private int n;
    private int k;

    public Match(int x, int n, int m, int k, Player player1, Player player2) {
        this.x = x;
        int score1 = 0;
        int score2 = 0;
        while (score1 != x && score2 != x) {
            final Game game = new Game(player1, player2, true);
            System.out.println("Current board");
            int res = game.play(new Board(n, m, k));
            if (res == 1) {
                System.out.println("X's won in current round");
                System.out.println('\n');
                score1++;
            } else if (res == 2) {
                System.out.println("O's won in current round");
                System.out.println('\n');
                score2++;
            } else {
                System.out.println("Draw");
                System.out.println('\n');
            }
        }
        if (score1 == x) {
            System.out.println("X's won in this match");
        } else {
            System.out.println("O's won in this match");
        }
    }
}