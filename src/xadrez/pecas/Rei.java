package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {
    public Rei(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString() {
        return "R";
    }

    private boolean podeMover(Posicao pos){
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(pos);
        return p == null || p.getCor() != getCor();
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        Posicao pos = new Posicao(0,0);

        pos.setValores(posicao.getLinha() - 1, posicao.getColuna());
        if (getTabuleiro().existePosicao(pos) && podeMover(pos)){
            mat[pos.getLinha()][pos.getColuna()] = true;
        }
        pos.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
        if (getTabuleiro().existePosicao(pos) && podeMover(pos)){
            mat[pos.getLinha()][pos.getColuna()] = true;
        }
        pos.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
        if (getTabuleiro().existePosicao(pos) && podeMover(pos)){
            mat[pos.getLinha()][pos.getColuna()] = true;
        }
        pos.setValores(posicao.getLinha(), posicao.getColuna() - 1);
        if (getTabuleiro().existePosicao(pos) && podeMover(pos)){
            mat[pos.getLinha()][pos.getColuna()] = true;
        }
        pos.setValores(posicao.getLinha(), posicao.getColuna() + 1);
        if (getTabuleiro().existePosicao(pos) && podeMover(pos)){
            mat[pos.getLinha()][pos.getColuna()] = true;
        }
        pos.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
        if (getTabuleiro().existePosicao(pos) && podeMover(pos)){
            mat[pos.getLinha()][pos.getColuna()] = true;
        }
        pos.setValores(posicao.getLinha() + 1, posicao.getColuna());
        if (getTabuleiro().existePosicao(pos) && podeMover(pos)){
            mat[pos.getLinha()][pos.getColuna()] = true;
        }
        pos.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
        if (getTabuleiro().existePosicao(pos) && podeMover(pos)){
            mat[pos.getLinha()][pos.getColuna()] = true;
        }

        return mat;
    }
}
