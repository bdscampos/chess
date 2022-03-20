package xadrez.pecas;

import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rainha extends PecaXadrez {

    public Rainha(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        return new boolean[0][];
    }

    @Override
    public String toString() {
        return "Q";
    }

}
