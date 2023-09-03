import Model.*;
import javafx.util.Pair;

import java.awt.*;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TicTakToeGame {
    Deque<Player> players;
    Board gameBoard;

    TicTakToeGame(){
        initializeGame();
    }

    private void initializeGame() {

        players=new LinkedList<>();
        PieceX pieceX = new PieceX();
        Player player1 = new Player("Player1", pieceX);

        PieceO pieceO = new PieceO();
        Player player2 = new Player("Player2", pieceO);

        players.add(player1);
        players.add(player2);

        gameBoard = new Board(3);
    }

    String startGame(){
        boolean noWinner = true;

        while (noWinner){
            Player playerTurn = players.removeFirst();

            //Get free space in board
            gameBoard.printBoard();
            List<Pair<Integer,Integer>> freeSpaces = gameBoard.getFreeCells();
            if (freeSpaces.isEmpty()){
                noWinner=false;
                continue;
            }

            //read User input
            System.out.println("Player : "+ playerTurn.getName() +" Enter row, column: ");
            Scanner inputScanner = new Scanner(System.in);
            String s = inputScanner.nextLine();
            String[] values = s.split(",");
            int inputRow = Integer.parseInt(values[0]);
            int inputCol = Integer.parseInt(values[1]);

            boolean pieceAddedSuccessfully = gameBoard.addPiece(inputRow, inputCol, playerTurn.getPlayerPiece());
            if(!pieceAddedSuccessfully){
                System.out.println("Incorrect Position chosen, Try again");
                players.addFirst(playerTurn);
                continue;
            }
            players.addLast(playerTurn);

            boolean winner = isThereWinner(inputRow, inputCol, playerTurn.getPlayerPiece().pieceType);

            if (winner){
                return playerTurn.name;
            }
        }

        return "No";
    }

    private boolean isThereWinner(int inputRow, int inputCol, PieceType pieceType) {
        boolean colMatch=true;
        boolean rowMatch=true;
        boolean diagonalMatch=true;
        boolean antiDiagonalMatch=true;

        int size= gameBoard.size;

        for (int i = 0; i < size; i++) {
            if (gameBoard.board[inputRow][i]==null || gameBoard.board[inputRow][i].pieceType!=pieceType){
                rowMatch=false;
                break;
            }
        }

        for (int i = 0; i < size; i++) {
            if (gameBoard.board[i][inputCol]==null || gameBoard.board[i][inputCol].pieceType!=pieceType){
                colMatch=false;
                break;
            }
        }

        for (int i = 0; i < size; i++) {
            if (gameBoard.board[i][i]==null || gameBoard.board[i][i].pieceType!=pieceType){
                diagonalMatch=false;
                break;
            }
        }

        for (int i = 0; i < size; i++) {
            if (gameBoard.board[i][size-i-1]==null || gameBoard.board[i][size-i-1].pieceType!=pieceType){
                antiDiagonalMatch=false;
                break;
            }
        }

        return rowMatch || colMatch || diagonalMatch || antiDiagonalMatch;
    }
}
