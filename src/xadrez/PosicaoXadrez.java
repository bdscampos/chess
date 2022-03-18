package xadrez;

import tabuleiro.Posicao;

public class PosicaoXadrez {
    private char coluna;
    private int linha;

    public PosicaoXadrez(char coluna, int linha) {
        if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8){
            throw new ChessException("Erro na posição de xadrez. Valores válidos (a-h) para coluna, (1-8) para linha");
        }
        this.coluna = coluna;
        this.linha = linha;
    }

    public char getColuna() {
        return coluna;
    }

    public int getLinha() {
        return linha;
    }

    protected Posicao convertePosicao(){
        return new Posicao(8 - linha, coluna - 'a');
    }

    protected static PosicaoXadrez convertePosicaoXadrez(Posicao pos){
        return new PosicaoXadrez((char)('a' -  pos.getColuna()), 8 - pos.getLinha());
    }

    @Override
    public String toString() {
        return "" + coluna + linha;
    }
}
