package tabuleiro;

public abstract class Peca {
    protected Posicao posicao;
    private Tabuleiro tabuleiro;

    public Peca(Tabuleiro tabuleiro) {
        this.posicao = null;
        this.tabuleiro = tabuleiro;
    }

    protected Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public Posicao getPosicao() {
        return posicao;
    }

    public void setPosicao(Posicao posicao) {
        this.posicao = posicao;
    }

    public abstract boolean[][] movimentosPossiveis();

    public boolean movimentoPossivel(Posicao pos){
        return movimentosPossiveis()[pos.getLinha()][pos.getColuna()];
    }

    public boolean temAlgumMovimentoPossivel(){
        boolean[][] movimentos = movimentosPossiveis();
        for (int i=0;i<movimentos.length;i++){
            for(int j=0;j<movimentos.length;j++){
                if(movimentos[i][j]) return true;
            }
        }
        return false;
    }
}
