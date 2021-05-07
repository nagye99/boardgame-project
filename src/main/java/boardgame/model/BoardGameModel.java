package boardgame.model;

import javafx.beans.property.ObjectProperty;

import java.util.*;

public class BoardGameModel {

    public static int BOARD_WIDTH = 7;
    public static int BOARD_HEIGHT = 6;

    private static final ArrayList<Piece> piecesArray = generatePieces();

    private final ArrayList<Piece> pieces;
    private final ArrayList<Block> blocks;
    private final HashSet<Position> redPiecesPosition = new HashSet<>();
    private final HashSet<Position> bluePiecesPosition = new HashSet<>();

    private static ArrayList<Piece> generatePieces(){
        ArrayList<Piece> tmpPieces = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            tmpPieces.add(new Piece(PieceColor.RED, new Position(0, i)));
        }
        for (int i = 0; i <= 6; i++) {
            tmpPieces.add(new Piece(PieceColor.BLUE, new Position(5, i)));
        }
        return tmpPieces;
    }

    public BoardGameModel() {
        this(piecesArray, new Block(new Position(2, 4)), new Block(new Position(3,2)));
    }

    public BoardGameModel(ArrayList<Piece> piecesList, Block...blocks ) {
        checkPieces(piecesList);
        this.pieces = piecesList;
        this.blocks = new ArrayList<>(Arrays.asList(blocks));
        for(var piece : piecesList){
            if(piece.getType() == PieceColor.RED){
                redPiecesPosition.add(piece.getPosition());
            }else{
                bluePiecesPosition.add(piece.getPosition());
            }
        }
    }

    private void checkPieces(ArrayList<Piece> pieces) {
        var seen = new HashSet<Position>();
        for (var piece : pieces) {
            if (! isOnBoard(piece.getPosition()) || seen.contains(piece.getPosition())) {
                throw new IllegalArgumentException();
            }
            seen.add(piece.getPosition());
        }
    }

    public static boolean isOnBoard(Position position) {
        return 0 <= position.row() && position.row() < BOARD_HEIGHT
                && 0 <= position.col() && position.col() < BOARD_WIDTH;
    }

    public int getPieceCount() {
        return pieces.size();
    }

    public int getBlockCount() {
        return blocks.size();
    }

    public PieceColor getPieceType(int pieceNumber) {
        return pieces.get(pieceNumber).getType();
    }

    public Position getPiecePosition(int pieceNumber) {
        return pieces.get(pieceNumber).getPosition();
    }

    public Position getBlockPosition(int blockNumber) {
        return blocks.get(blockNumber).getPosition();
    }

    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        for (var piece : pieces) {
            joiner.add(piece.toString());
        }
        for (var pos:bluePiecesPosition){
            joiner.add("\nkék: " + pos.toString());
        }
        for (var pos:redPiecesPosition){
            joiner.add("\npiros: " + pos.toString());
        }
        return joiner.toString();
    }

    public static void main(String[] args) {
        var model = new BoardGameModel();
    }
}