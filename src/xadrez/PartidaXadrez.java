package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.*;
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
    private Boolean chequeMate = false;
    private PecaXadrez vulneravelEnPassant;
    private PecaXadrez promovido;

    public Cor getJogadorAtual() {
        return jogadorAtual;
    }

    public int getRodada() {
        return rodada;
    }

    public boolean getCheque(){ return cheque; }

    public boolean getChequeMate(){ return chequeMate; }

    public PecaXadrez getVulneravelEnPassant() {
        return vulneravelEnPassant;
    }

    public PecaXadrez getPromovido() {
        return promovido;
    }

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
        PecaXadrez pecaMovida = (PecaXadrez) tabuleiro.peca(fim);
        promovido = null;
        if (pecaMovida instanceof Peao){
            if (pecaMovida.getCor() == Cor.BRANCO && fim.getColuna() == 0 || pecaMovida.getCor() == Cor.PRETO && fim.getColuna() == 7){
                promovido = (PecaXadrez) tabuleiro.peca(fim);
                promovido = trocarPecaPromovida("Q");
            }
        }
        if (testeCheque(jogadorAtual)){
            desfazerMovimento(inicio, fim, pecaCapturada);
        }
        cheque = (testeCheque(oponente(jogadorAtual))) ? true : false;
        if (testeChequeMate(oponente(jogadorAtual))){
            chequeMate = true;
        }
        if (pecaMovida instanceof Peao && (fim.getLinha() == inicio.getLinha() - 2 || (fim.getLinha() == inicio.getLinha() + 2))){
            vulneravelEnPassant = pecaMovida;
        }
        else {
            vulneravelEnPassant = null;
        }
        trocaRodada();
        return (PecaXadrez) pecaCapturada;
    }

    private void desfazerMovimento(Posicao origem, Posicao destino, Peca capturada){
        PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(destino);
        p.diminuiMoveCount();
        tabuleiro.colocarPeca(p, origem);
        if (capturada != null){
            tabuleiro.colocarPeca(capturada, destino);
            pecasCapturadas.remove(capturada);
            pecasNoTabuleiro.add(capturada);
        }
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2){
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(destinoT);
            tabuleiro.colocarPeca(torre, origemT);
            torre.diminuiMoveCount();
        }
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2){
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(destinoT);
            tabuleiro.colocarPeca(torre, origemT);
            torre.diminuiMoveCount();
        }
        if (p instanceof Peao){
            if (origem.getColuna() != destino.getColuna() && capturada == vulneravelEnPassant) {
                PecaXadrez peao = (PecaXadrez)tabuleiro.removerPeca(destino);
                Posicao posP;
                if (p.getCor() == Cor.BRANCO){
                    posP = new Posicao(3, destino.getColuna());
                }else{
                    posP = new Posicao(4, destino.getColuna());
                }
                tabuleiro.colocarPeca(peao, posP);
            }
        }
    }

    private Peca fazerMovimento(Posicao origem, Posicao destino){
        PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(origem);
        p.aumentaMoveCount();
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.colocarPeca(p, destino);
        if (pecaCapturada != null){
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2){
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(origemT);
            tabuleiro.colocarPeca(torre, destinoT);
            torre.aumentaMoveCount();
        }
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2){
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(origemT);
            tabuleiro.colocarPeca(torre, destinoT);
            torre.aumentaMoveCount();
        }
        if (p instanceof Peao){
            if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
                Posicao posP;
                if (p.getCor() == Cor.BRANCO){
                    posP = new Posicao(destino.getLinha() + 1, destino.getColuna());
                }else{
                    posP = new Posicao(destino.getLinha() - 1, destino.getColuna());
                }
                pecaCapturada = tabuleiro.removerPeca(posP);
                pecasCapturadas.add(pecaCapturada);
                pecasNoTabuleiro.remove(pecaCapturada);
            }
        }
        return pecaCapturada;
    }

    public PecaXadrez trocarPecaPromovida(String tipo){
        if (promovido == null){
            throw new IllegalStateException("Não há peça para ser promovida");
        }
        if (!tipo.equals("B") && !tipo.equals("T") && !tipo.equals("C") && !tipo.equals("Q")){
            return promovido;
        }
        Posicao pos = promovido.getPosicaoXadrez().convertePosicao();
        Peca p = tabuleiro.removerPeca(pos);
        pecasNoTabuleiro.remove(p);

        PecaXadrez novaPeca = novaPeca(tipo, promovido.getCor());
        tabuleiro.colocarPeca(novaPeca,pos);
        pecasNoTabuleiro.add(novaPeca);
        return novaPeca;
    }

    private PecaXadrez novaPeca(String tipo, Cor cor){
        if (tipo.equals("B")) return new Bispo(tabuleiro, cor);
        if (tipo.equals("C")) return new Cavalo(tabuleiro, cor);
        if (tipo.equals("T")) return new Torre(tabuleiro, cor);
        return new Rainha(tabuleiro, cor);
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

    private boolean testeChequeMate(Cor cor){
        if (!testeCheque(cor)){
            return false;
        }
        List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
        for (Peca p : list){
            boolean[][] mat = p.movimentosPossiveis();
            for (int i = 0; i<tabuleiro.getLinhas(); i++){
                for (int j = 0; j<tabuleiro.getColunas();j++){
                    if(mat[i][j]){
                        Posicao origem = ((PecaXadrez)p).getPosicaoXadrez().convertePosicao();
                        Posicao destino = new Posicao(i,j);
                        Peca capturada = fazerMovimento(origem,destino);
                        boolean testeCheque = testeCheque(cor);
                        desfazerMovimento(origem,destino,capturada);
                        if (!testeCheque){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void inicializaPartida(){
        colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

        colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
        colocarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));
        colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
        colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
        colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
        colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
    }
}
