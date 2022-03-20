package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

    private PartidaXadrez partida;

    public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partida) {
        super(tabuleiro, cor);
        this.partida = partida;
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
            if (posicao.getLinha() == 3){
                Posicao esq = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if (getTabuleiro().existePosicao(esq) &&  temUmaPecaAdversaria(esq) && getTabuleiro().peca(esq) == partida.getVulneravelEnPassant()){
                    mat[esq.getLinha() - 1][esq.getColuna()] = true;
                }
                Posicao dir = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if (getTabuleiro().existePosicao(dir) &&  temUmaPecaAdversaria(dir) && getTabuleiro().peca(dir) == partida.getVulneravelEnPassant()){
                    mat[dir.getLinha() - 1][dir.getColuna()] = true;
                }
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
            if (posicao.getLinha() == 4){
                Posicao esq = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if (getTabuleiro().existePosicao(esq) &&  temUmaPecaAdversaria(esq) && getTabuleiro().peca(esq) == partida.getVulneravelEnPassant()){
                    mat[esq.getLinha() + 1][esq.getColuna()] = true;
                }
                Posicao dir = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if (getTabuleiro().existePosicao(dir) &&  temUmaPecaAdversaria(dir) && getTabuleiro().peca(dir) == partida.getVulneravelEnPassant()){
                    mat[dir.getLinha() + 1][dir.getColuna()] = true;
                }
            }
        }
        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
}
