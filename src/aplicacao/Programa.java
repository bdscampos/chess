package aplicacao;

import xadrez.ChessException;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Programa {
    public static void main(String[] args) {
        PartidaXadrez partida = new PartidaXadrez();
        Scanner sc = new Scanner(System.in);
        List<PecaXadrez> list = new ArrayList<>();

        while (!partida.getChequeMate()) {
            try {
                UI.limpaTela();
                UI.imprimePartida(partida, list);
                System.out.println();
                System.out.print("Posicao Origem: ");
                PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
                boolean[][] movimentosPossiveis = partida.movimentosPossiveis(origem);
                UI.limpaTela();
                UI.imprimeTabuleiro(partida.retornaPecas(), movimentosPossiveis);
                System.out.println();
                System.out.print("Posicao Destino: ");
                PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
                PecaXadrez pecaCapturada = partida.fazerMovimentoDePeca(origem, destino);
                if (pecaCapturada != null){
                    list.add(pecaCapturada);
                }
            }catch (ChessException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }catch (InputMismatchException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        UI.limpaTela();
        UI.imprimePartida(partida,list);
    }
}
