package boardgame.model;

import javafx.beans.property.ObjectProperty;
import org.tinylog.Logger;

import java.util.*;

public class BoardGameModel {

    public static int BOARD_WIDTH = 7;
    public static int BOARD_HEIGHT = 6;

    private final ArrayList<Piece> pieces;
    private final ArrayList<Block> blocks;
    private final HashSet<Position> redPiecesPosition = new HashSet<>();
    private final HashSet<Position> bluePiecesPosition = new HashSet<>();

    public BoardGameModel() {
        this(new ArrayList<Block>(Arrays.asList(new Block(new Position(2,4)), new Block(new Position(3,2)))),
                new Piece(PieceColor.RED, new Position(0,0)), new Piece(PieceColor.RED, new Position(0,1)), new Piece(PieceColor.RED, new Position(0,2)), new Piece(PieceColor.RED, new Position(0,3)), new Piece(PieceColor.RED, new Position(0,4)), new Piece(PieceColor.RED, new Position(0,5)), new Piece(PieceColor.RED, new Position(0,6)),
                new Piece(PieceColor.BLUE, new Position(5,0)), new Piece(PieceColor.BLUE, new Position(5,1)), new Piece(PieceColor.BLUE, new Position(5,2)), new Piece(PieceColor.BLUE, new Position(5,3)), new Piece(PieceColor.BLUE, new Position(5,4)), new Piece(PieceColor.BLUE, new Position(5,5)), new Piece(PieceColor.BLUE, new Position(5,6)));
    }

    public BoardGameModel(ArrayList<Block> blocks, Piece...pieces) {
        checkPieces(pieces);
        Logger.error("Elötte: ",pieces.toString());
        this.pieces = new ArrayList<Piece>(Arrays.asList(pieces));
        Logger.error("Utána: "+pieces);
        this.blocks = blocks;
        for(var piece : pieces){
            if(piece.getType() == PieceColor.RED){
                redPiecesPosition.add(piece.getPosition());
            }else{
                bluePiecesPosition.add(piece.getPosition());
            }
        }
    }

    private void checkPieces(Piece[] pieces) {
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
        var newPosition = pieces.get(pieceNumber).getPosition().moveTo(direction);
        var piece = pieces.get(pieceNumber);
        if (piece.getType() == PieceColor.RED){
            moveRedPiece(piece, newPosition);
        }else{
              moveBluePiece(piece, newPosition);
        }
        piece.moveTo(direction);
    }

    private void moveRedPiece(Piece piece, Position newPosition){
        redPiecesPosition.remove(piece.getPosition());
        redPiecesPosition.add(newPosition);
        if(bluePiecesPosition.contains(newPosition)){
            bluePiecesPosition.remove(newPosition);
            var a = pieces.remove(new Piece(PieceColor.BLUE, newPosition));
            Logger.debug(a+"\n"+pieces);
        }
    }

    private void moveBluePiece(Piece piece, Position newPosition){
        bluePiecesPosition.remove(piece.getPosition());
        bluePiecesPosition.add(newPosition);
        if(redPiecesPosition.contains(newPosition)){
            redPiecesPosition.remove(newPosition);
            var a =pieces.remove(new Piece(PieceColor.RED, newPosition));
            Logger.debug(a+"\n"+pieces);
        }
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
        //System.out.println(model.getPieceType(13));
        //System.out.println(model);
        //System.out.println(model.getBlockPosition(0));
        //System.out.println(model.canBlueMove());
        //System.out.println(model.canRedMove());
/*        System.out.println(model.pieces);
        System.out.println(piecesArray.remove(new Piece(PieceColor.RED, new Position(0,0))));
        System.out.println(model.pieces);*/
        System.out.println(model.bluePiecesPosition.contains(new Position(4,6)));
    }
}