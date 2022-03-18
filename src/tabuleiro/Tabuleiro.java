package tabuleiro;

public class Tabuleiro {
    private int linhas, colunas;
    private Peca[][] pecas;

    public Tabuleiro(int linhas, int colunas) {
        if (linhas < 1 || colunas < 1){
            throw new BoardException("Erro criando tabuleiro, precisa 1 linha e coluna no mínimo");
        }
        this.linhas = linhas;
        this.colunas = colunas;
        pecas = new Peca[linhas][colunas];
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public Peca peca(int linha, int coluna){
        if (!existePosicao(linha, coluna)){
            throw new BoardException("Posição fora do tabuleiro");
        }
        return pecas[linha][coluna];
    }

    public Peca peca(Posicao posicao){
        if (!existePosicao(posicao)){
            throw new BoardException("Posição fora do tabuleiro");
        }
        return pecas[posicao.getLinha()][posicao.getColuna()];
    }

    public void colocarPeca(Peca p, Posicao pos){
        if (temUmaPeca(pos)){
            throw new BoardException("Já existe uma peça na posição "+ pos);
        }
        pecas[pos.getLinha()][pos.getColuna()] = p;
        p.posicao = pos;
    }

    public Peca removerPeca(Posicao pos){
        if (!existePosicao(pos)){
            throw new BoardException("Posição inválida");
        }

        if (peca(pos) == null){
            return null;
        }
        Peca aux = peca(pos);
        aux.posicao = null;

        pecas[pos.getLinha()][pos.getColuna()] = null;
        return aux;
    }

    private boolean existePosicao(int linha, int coluna){
         return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;

    }

    public boolean existePosicao(Posicao pos){
        return existePosicao(pos.getLinha(), pos.getColuna());
    }

    public boolean temUmaPeca(Posicao pos){
        if (!existePosicao(pos)){
            throw new BoardException("Posição fora do tabuleiro");
        }
        return peca(pos) != null;
    }


}
