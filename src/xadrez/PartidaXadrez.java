package xadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

    private Tabuleiro tabuleiro;
    private int rodada = 0;

    public PartidaXadrez() {
        this.tabuleiro = new Tabuleiro(8,8);
        inicializaPartida();
    }

    public PecaXadrez[][] retornaPecas(){
        PecaXadrez[][] matriz = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for (int i=0; i<tabuleiro.getLinhas(); i++){
            for (int j=0; j< tabuleiro.getColunas(); j++){
                matriz[i][j] = (PecaXadrez) tabuleiro.peca(i,j);
            }
        }
        return matriz;
    }

    private void inicializaPartida(){
        tabuleiro.colocarPeca(new Rei(tabuleiro, Cor.BRANCO), new Posicao(7,4));
        tabuleiro.colocarPeca(new Torre(tabuleiro, Cor.PRETO), new Posicao(0,0));
        tabuleiro.colocarPeca(new Rei(tabuleiro, Cor.PRETO), new Posicao(0,4));
        tabuleiro.colocarPeca(new Torre(tabuleiro, Cor.PRETO), new Posicao(0,7));
    }
}
