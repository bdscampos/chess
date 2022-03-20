package aplicacao;

import xadrez.ChessException;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

import java.util.*;

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
                if (partida.getPromovido() != null){
                    System.out.print("Digite a nova peça: (B/Q/C/T) ");
                    String tipo = sc.nextLine().toUpperCase();
                    while (!tipo.equals("B") && !tipo.equals("T") && !tipo.equals("C") && !tipo.equals("Q")){
                        System.out.print("Digite um valor válido.");
                        tipo = sc.nextLine().toUpperCase();
                    }
                    partida.trocarPecaPromovida(tipo);
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
