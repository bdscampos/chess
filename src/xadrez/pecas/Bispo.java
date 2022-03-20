package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Bispo extends PecaXadrez {

    public Bispo(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        Posicao p = new Posicao(0,0);
        //acima
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
        while(getTabuleiro().existePosicao(p) && !getTabuleiro().temUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() - 1, p.getColuna() - 1);
        }
        if (getTabuleiro().existePosicao(p) && temUmaPecaAdversaria(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }
        //abaixo
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
        while(getTabuleiro().existePosicao(p) && !getTabuleiro().temUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() + 1, p.getColuna() - 1);
        }
        if (getTabuleiro().existePosicao(p) && temUmaPecaAdversaria(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }
        //esquerda
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
        while(getTabuleiro().existePosicao(p) && !getTabuleiro().temUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() - 1, p.getColuna() + 1);
        }
        if (getTabuleiro().existePosicao(p) && temUmaPecaAdversaria(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }
        //direita
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
        while(getTabuleiro().existePosicao(p) && !getTabuleiro().temUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() + 1, p.getColuna() - 1);
        }
        if (getTabuleiro().existePosicao(p) && temUmaPecaAdversaria(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }
        return mat;
    }

    @Override
    public String toString() {
        return "B";
    }
}
