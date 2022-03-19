package xadrez;

import tabuleiro.Peca;
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

    private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca){
        tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).convertePosicao());
    }

    public PecaXadrez fazerMovimentoDePeca(PosicaoXadrez origem, PosicaoXadrez destino){
        Posicao inicio = origem.convertePosicao();
        Posicao fim = destino.convertePosicao();
        validarPosicaoOrigem(inicio);
        validarPosicaoDestino(inicio, fim);
        Peca pecaCapturada = fazerMovimento(inicio, fim);
        return (PecaXadrez) pecaCapturada;
    }

    private void validarPosicaoDestino(Posicao origem, Posicao destino){
        if (!tabuleiro.peca(origem).movimentoPossivel(destino)){
            throw new ChessException("Peça escolhida não pode mover para essa posição");
        }
    }

    private void validarPosicaoOrigem(Posicao pos){
        if (!tabuleiro.temUmaPeca(pos)){
            throw new ChessException("Não existe peça na posição inicial");
        }
        if (!tabuleiro.peca(pos).temAlgumMovimentoPossivel()){
            throw new ChessException("Não existem movimentos possíveis para a peça escolhida");
        }
    }

    private Peca fazerMovimento(Posicao origem, Posicao destino){
        Peca p = tabuleiro.removerPeca(origem);
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.colocarPeca(p, destino);
        return  pecaCapturada;
    }

    private void inicializaPartida(){
        colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO));
        colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
    }
}
