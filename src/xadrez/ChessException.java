package xadrez;

import tabuleiro.BoardException;

public class ChessException extends BoardException {

    public ChessException(String msg){
        super(msg);
    }
}
