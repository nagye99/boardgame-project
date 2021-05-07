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

    public ObjectProperty<Position> positionProperty(int pieceNumber) {
        return pieces.get(pieceNumber).positionProperty();
    }

    public boolean isValidMove(Position newPosition){
        if (! isOnBoard(newPosition)) {
            return false;
        }
        for (var block : blocks){
            if (block.getPosition().equals(newPosition)){
                return false;
            }
        }
        return true;
    }

    public boolean isValidRedMove(Position position, RedDirection direction) {
        if (!isOnBoard(position)){
            throw new IllegalArgumentException();
        }
        if (! redPiecesPosition.contains(position)){
            return false;
        }
        Position newPosition = position.moveTo(direction);
        if(!isValidMove(newPosition)){
            return false;
        }
        for (var pos : redPiecesPosition){
            if (pos.equals(newPosition)){
                return false;
            }
        }
        if (direction == RedDirection.DOWN){
            for (var pos : bluePiecesPosition) {
                if (pos.equals(newPosition)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isValidBlueMove(Position position, BlueDirection direction) {
        if (! bluePiecesPosition.contains(position)){
            throw new IllegalArgumentException();
        }
        Position newPosition = position.moveTo(direction);
        if(!isValidMove(newPosition)){
            return false;
        }
        for (var pos : bluePiecesPosition){
            if (pos.equals(newPosition)){
                return false;
            }
        }
        if (direction == BlueDirection.UP){
            for (var pos : redPiecesPosition) {
                if (pos.equals(newPosition)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Set<RedDirection> getValidRedMoves(Position position) {
        EnumSet<RedDirection> validMoves = EnumSet.noneOf(RedDirection.class);
        for (var direction : RedDirection.values()) {
            if (isValidRedMove(position, direction)) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }

    public Set<BlueDirection> getValidBlueMoves(Position position) {
        EnumSet<BlueDirection> validMoves = EnumSet.noneOf(BlueDirection.class);
        for (var direction : BlueDirection.values()) {
            if (isValidBlueMove(position, direction)) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }

    public boolean canBlueMove(){
        for (var position : bluePiecesPosition){
            if(!getValidBlueMoves(position).isEmpty()){
                return true;
            }
        }
        return false;
    }

    public boolean canRedMove(){
        for (var position : redPiecesPosition){
            if (!getValidRedMoves(position).isEmpty()){
                return true;
            }
        }
        return false;
    }

    public void move(int pieceNumber, Direction direction) {
        if (pieces.get(pieceNumber).getType() == PieceColor.RED){
            redPiecesPosition.remove(pieces.get(pieceNumber).getPosition());
            redPiecesPosition.add(pieces.get(pieceNumber).getPosition().moveTo(direction));
        }else{
                bluePiecesPosition.remove(pieces.get(pieceNumber).getPosition());
                bluePiecesPosition.add(pieces.get(pieceNumber).getPosition().moveTo(direction));
        }
        pieces.get(pieceNumber).moveTo(direction);
    }


    public List<Position> getRedPositions() {
        List<Position> redPositions = new ArrayList<>();
        for (var position : redPiecesPosition){
            if (!getValidRedMoves(position).isEmpty()){
                redPositions.add(position);
            }
        }
        return redPositions;
    }

    public List<Position> getBluePositions() {
        List<Position> bluePositions = new ArrayList<>();
        for (var position : bluePiecesPosition){
            if (!getValidBlueMoves(position).isEmpty()){
                bluePositions.add(position);
            }
        }
        return bluePositions;
    }

    public OptionalInt getPieceNumber(Position position) {
        for (int i = 0; i < pieces.size(); i++) {
            if (pieces.get(i).getPosition().equals(position)) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
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
        System.out.println(model);
        System.out.println(model.canBlueMove());
        System.out.println(model.canRedMove());
    }
}