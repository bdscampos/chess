package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PartidaXadrez {

    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    private int rodada;
    private List<Peca> pecasNoTabuleiro = new ArrayList<>();
    private List<Peca> pecasCapturadas = new ArrayList<>();
    private Boolean cheque = false;

    public Cor getJogadorAtual() {
        return jogadorAtual;
    }

    public int getRodada() {
        return rodada;
    }

    public boolean getCheque(){ return cheque; }

    public PartidaXadrez() {
        this.rodada = 1;
        this.jogadorAtual = Cor.BRANCO;
        this.tabuleiro = new Tabuleiro(8,8);
        inicializaPartida();
    }

    private void trocaRodada(){
        if (jogadorAtual == Cor.PRETO) {
            rodada++;
        }
        jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
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
        pecasNoTabuleiro.add(peca);
        tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).convertePosicao());
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
        if (jogadorAtual != ((PecaXadrez) tabuleiro.peca(pos)).getCor()) {
            throw new ChessException("Não é possível mover peça do outro jogador");
        }
    }

    public PecaXadrez fazerMovimentoDePeca(PosicaoXadrez origem, PosicaoXadrez destino){
        Posicao inicio = origem.convertePosicao();
        Posicao fim = destino.convertePosicao();
        validarPosicaoOrigem(inicio);
        validarPosicaoDestino(inicio, fim);
        Peca pecaCapturada = fazerMovimento(inicio, fim);
        if (testeCheque(jogadorAtual)){
            desfazerMovimento(inicio, fim, pecaCapturada);
        }
        cheque = (testeCheque(oponente(jogadorAtual))) ? true : false;
        trocaRodada();
        return (PecaXadrez) pecaCapturada;
    }

    private void desfazerMovimento(Posicao origem, Posicao destino, Peca capturada){
        Peca p = tabuleiro.removerPeca(destino);
        tabuleiro.colocarPeca(p, origem);
        if (capturada != null){
            tabuleiro.colocarPeca(capturada, destino);
            pecasCapturadas.remove(capturada);
            pecasNoTabuleiro.add(capturada);
        }
    }

    private Peca fazerMovimento(Posicao origem, Posicao destino){
        Peca p = tabuleiro.removerPeca(origem);
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.colocarPeca(p, destino);
        if (pecaCapturada != null){
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }
        return pecaCapturada;
    }

    public boolean[][] movimentosPossiveis(PosicaoXadrez origem){
        Posicao posicao = origem.convertePosicao();
        validarPosicaoOrigem(posicao);
        return tabuleiro.peca(posicao).movimentosPossiveis();
    }

    private Cor oponente(Cor cor){
        return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }

    private PecaXadrez rei(Cor cor){
        List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
        for (Peca p : list){
            if(p instanceof Rei){
                return (PecaXadrez) p;
            }
        }
        throw new IllegalStateException("Não existe rei " + cor + "no tabuleiro");
    }

    private boolean testeCheque(Cor cor){
        Posicao posicaoRei = rei(cor).getPosicaoXadrez().convertePosicao();
        List<Peca> pecasOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
        for (Peca p : pecasOponente){
            boolean[][] mat = p.movimentosPossiveis();
            if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) return true;
        }
        return false;
    }

    private void inicializaPartida(){
        colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO));
        colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
    }
}
