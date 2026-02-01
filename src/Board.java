import java.util.Random;

public class Board {
    private final int size;
    private final char[][] hidden;   // barcos + estado real
    private final char[][] visible;  // lo que ve el jugador
    private int shipsRemaining;

    // símbolos
    private static final char AGUA = '~';
    private static final char BARCO = 'B';
    private static final char TOCADO = 'X';
    private static final char FALLADO = 'O';

    public Board(int size) {
        if (size <= 0) throw new IllegalArgumentException("Size debe ser > 0");
        this.size = size;
        this.hidden = new char[size][size];
        this.visible = new char[size][size];
        fill(hidden, AGUA);
        fill(visible, AGUA);
        this.shipsRemaining = 0;
    }

    private void fill(char[][] m, char ch) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) m[i][j] = ch;
        }
    }

    public int getSize() {
        return size;
    }

    public int getShipsRemaining() {
        return shipsRemaining;
    }

    /** Coloca N barcos de 1 casilla aleatoriamente, sin solaparse. */
    public void placeRandomShips(int count, long seed) {
        if (count < 0) throw new IllegalArgumentException("count no puede ser negativo");
        if (count > size * size) throw new IllegalArgumentException("Demasiados barcos para el tablero");

        Random rnd = new Random(seed);
        int placed = 0;
        while (placed < count) {
            int r = rnd.nextInt(size);
            int c = rnd.nextInt(size);
            if (hidden[r][c] != BARCO) {
                hidden[r][c] = BARCO;
                placed++;
            }
        }
        shipsRemaining = count;
    }

    /** Devuelve tablero visible (para imprimir). Copia defensiva. */
    public char[][] getVisibleSnapshot() {
        char[][] copy = new char[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(visible[i], 0, copy[i], 0, size);
        }
        return copy;
    }

    /** Disparo: actualiza visible + hidden y devuelve el resultado. */
    public ShotResult shoot(int row, int col) {
        if (!inBounds(row, col)) return ShotResult.FUERA_TABLERO;

        // si ya se disparó aquí, visible no será AGUA
        if (visible[row][col] == TOCADO || visible[row][col] == FALLADO) {
            return ShotResult.REPETIDO;
        }

        if (hidden[row][col] == BARCO) {
            visible[row][col] = TOCADO;
            hidden[row][col] = TOCADO; // marca también en oculto
            shipsRemaining--;
            // barcos de 1 casilla: TOCADO == HUNDIDO
            return ShotResult.HUNDIDO;
        } else {
            visible[row][col] = FALLADO;
            return ShotResult.AGUA;
        }
    }

    private boolean inBounds(int r, int c) {
        return r >= 0 && r < size && c >= 0 && c < size;
    }
}