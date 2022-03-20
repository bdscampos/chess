package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {
    private PartidaXadrez partida;

    public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partida) {
        super(tabuleiro, cor);
        this.partida = partida;
    }

    @Override
    public String toString() {
        return "R";
    }

    private boolean podeMover(Posicao pos){
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(pos);
        return p == null || p.getCor() != getCor();
    }

    private boolean testeRoque(Posicao pos){
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(pos);
        return p != null && p instanceof Torre && p.getCor() == getCor() && getMoveCount() == 0;

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
        if (getMoveCount() == 0 && !partida.getCheque()){
            Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
            if (testeRoque(posT1)){
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
                if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null){
                    mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
                }
            }
        }
        if (getMoveCount() == 0 && !partida.getCheque()){
            Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() -4);
            if (testeRoque(posT1)){
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
                Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
                if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null){
                    mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
                }
            }
        }
        return mat;
    }
}
