package xadrez;

import tabuleiro.Tabuleiro;

public class PartidaXadrez {

    private Tabuleiro tabuleiro;
    private int rodada = 0;

    public PartidaXadrez() {
        this.tabuleiro = new Tabuleiro(8,8);
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
}
