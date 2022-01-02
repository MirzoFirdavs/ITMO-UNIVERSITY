package ru.itmo.wp.web.page;

import ru.itmo.wp.model.TicTacToe.*;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class TicTacToePage {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("state") == null) {
            newGame(request, view);
        } else {
            view.put("state", request.getSession().getAttribute("state"));
        }
    }

    public void newGame(HttpServletRequest request, Map<String, Object> view) {
        State curState = new State();
        view.put("state", curState);
        request.getSession().setAttribute("state", curState);
        throw new RedirectException(request.getRequestURI());
    }

    private void onMove(HttpServletRequest request, Map<String, Object> view) {
        State curState = (State) request.getSession().getAttribute("state");
        if (curState.board.getPhase() == Phase.RUNNING) {
            int x = -1, y = -1;
            for (String name : request.getParameterMap().keySet()) {
                if (name.matches("cell_[0-2]{2}")) {
                    y = name.charAt(name.length() - 1) - '0';
                    x = name.charAt(name.length() - 2) - '0';
                }
            }
            if (x >= 0 && y >= 0 && x <= 2 && y <= 2) {
                curState.makeMove(x, y);
                request.getSession().setAttribute("state", curState);
            }
        }
        view.put("state", curState);
        throw new RedirectException(request.getRequestURI());
    }

    public static class State {
        private final Board board;
        private final int size = 3;

        public State() {
            this.board = new Board(size);
        }

        public void makeMove(int x, int y) {
            board.makeMove(x, y);
        }

        public int getSize() {
            return size;
        }

        public Cell[][] getCells() {
            return board.getCells();
        }

        public boolean getCrossesMove() {
            return board.getCrossesMove();
        }

        public Phase getPhase() {
            return board.getPhase();
        }
    }
}