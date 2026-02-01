import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game(
                5,      // size
                3,      // ships
                10,     // attempts
                1234L   // seed (para que sea reproducible)
        );

        Scanner sc = new Scanner(System.in);

        while (!game.isOver()) {
            printBoard(game.getBoard().getVisibleSnapshot());
            System.out.println("Intentos: " + game.getAttemptsLeft());
            System.out.print("Fila (0-" + (game.getBoard().getSize() - 1) + "): ");
            int r = sc.nextInt();
            System.out.print("Col (0-" + (game.getBoard().getSize() - 1) + "): ");
            int c = sc.nextInt();

            ShotResult res = game.playTurn(r, c);
            System.out.println("Resultado: " + res);
            System.out.println();
        }

        printBoard(game.getBoard().getVisibleSnapshot());
        if (game.isWin()) System.out.println("¡Has ganado!");
        else System.out.println("Has perdido.");
    }

    private static void printBoard(char[][] b) {
        System.out.print("  ");
        for (int c = 0; c < b.length; c++) System.out.print(c + " ");
        System.out.println();
        for (int r = 0; r < b.length; r++) {
            System.out.print(r + " ");
            for (int c = 0; c < b[r].length; c++) {
                System.out.print(b[r][c] + " ");
            }
            System.out.println();
        }
    }
}