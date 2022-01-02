package ru.itmo.wp.model.TicTacToe;

public class Board {

    public int cnt = 0;
    public Cell[][] cells;
    public int size;
    public Phase phase;
    public boolean crossesMove;

    public Board(int size) {
        this.cells = new Cell[size][size];
        this.phase = Phase.RUNNING;
        this.crossesMove = true;
        this.size = size;
    }

    public Cell lastCell() {
        if (crossesMove) {
            return Cell.X;
        } else {
            return Cell.O;
        }
    }

    public void makeMove(int x, int y) {
        cnt++;
        if (cells[x][y] == null) {
            cells[x][y] = lastCell();
            gameStatus();
            crossesMove = !crossesMove;
        }
    }

    public void gameStatus() {
        if (winningCases()) {
            if (!crossesMove) {
                phase = Phase.WON_O;
            } else {
                phase = Phase.WON_X;
            }
        } else if (cnt == size * size) {
            phase = Phase.DRAW;
        }
    }

    public boolean winningCases() {
        for (int i = 0; i < 3; i++) {
            if (cells[i][0] != null && cells[i][0] == cells[i][1] && cells[i][1] == cells[i][2] ||
                    cells[0][i] != null && cells[0][i] == cells[1][i] && cells[1][i] == cells[2][i]) {
                return true;
            }
        }
        return cells[0][0] != null && cells[0][0] == cells[1][1] && cells[1][1] == cells[2][2] ||
                cells[0][2] != null && cells[0][2] == cells[1][1] && cells[1][1] == cells[2][0];
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Phase getPhase() {
        return phase;
    }

    public boolean getCrossesMove() {
        return crossesMove;
    }
}