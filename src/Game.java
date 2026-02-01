public class Game {
    private final Board board;
    private int attemptsLeft;

    public Game(int size, int ships, int attempts, long seed) {
        if (attempts <= 0) throw new IllegalArgumentException("attempts debe ser > 0");
        this.board = new Board(size);
        this.board.placeRandomShips(ships, seed);
        this.attemptsLeft = attempts;
    }

    public Board getBoard() {
        return board;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public boolean isWin() {
        return board.getShipsRemaining() == 0;
    }

    public boolean isOver() {
        return isWin() || attemptsLeft <= 0;
    }

    /** Aplica un turno: resta intentos solo si el tiro fue válido y no repetido. */
    public ShotResult playTurn(int row, int col) {
        if (isOver()) throw new IllegalStateException("La partida ya terminó");

        ShotResult result = board.shoot(row, col);

        // regla: no penalizar por fuera de tablero o repetido (puedes cambiarlo)
        if (result != ShotResult.FUERA_TABLERO && result != ShotResult.REPETIDO) {
            attemptsLeft--;
        }
        return result;
    }
}