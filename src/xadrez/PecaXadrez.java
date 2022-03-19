package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca {
    private Cor cor;
    private int moveCount;


    public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }

    public void aumentaMoveCount(){
        moveCount++;
    }

    public void diminuiMoveCount(){
        moveCount--;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public PosicaoXadrez getPosicaoXadrez(){
        return PosicaoXadrez.convertePosicaoXadrez(posicao);
    }

    protected boolean temUmaPecaAdversaria(Posicao pos){
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(pos);
        return p != null && p.getCor() != cor;
    }
}
