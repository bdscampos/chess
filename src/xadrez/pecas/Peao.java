package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {


    public Peao(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        Posicao pos = new Posicao(0,0);
        Posicao aux = new Posicao(0,0);

        if (getCor() == Cor.BRANCO){
            pos.setValores(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().existePosicao(pos) && !getTabuleiro().temUmaPeca(pos)){
                mat[pos.getLinha()][pos.getColuna()] = true;
            }
            aux.setValores(pos.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().existePosicao(pos) && !getTabuleiro().temUmaPeca(pos) && !getTabuleiro().temUmaPeca(aux) && getTabuleiro().existePosicao(aux) && getMoveCount() == 0) {
                mat[aux.getLinha()][aux.getColuna()] = true;
            }
            pos.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
            if (getTabuleiro().existePosicao(pos) && temUmaPecaAdversaria(pos)){
                mat[pos.getLinha()][pos.getColuna()] = true;
            }
            pos.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
            if (getTabuleiro().existePosicao(pos) && temUmaPecaAdversaria(pos)){
                mat[pos.getLinha()][pos.getColuna()] = true;
            }
        }

        if (getCor() == Cor.PRETO){
            pos.setValores(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().existePosicao(pos) && !getTabuleiro().temUmaPeca(pos)){
                mat[pos.getLinha()][pos.getColuna()] = true;
            }
            aux.setValores(pos.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().existePosicao(pos) && !getTabuleiro().temUmaPeca(pos) && !getTabuleiro().temUmaPeca(aux) && getTabuleiro().existePosicao(aux) && getMoveCount() == 0) {
                mat[aux.getLinha()][aux.getColuna()] = true;
            }
            pos.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
            if (getTabuleiro().existePosicao(pos) && temUmaPecaAdversaria(pos)){
                mat[pos.getLinha()][pos.getColuna()] = true;
            }
            pos.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
            if (getTabuleiro().existePosicao(pos) && temUmaPecaAdversaria(pos)){
                mat[pos.getLinha()][pos.getColuna()] = true;
            }
        }
        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
}
