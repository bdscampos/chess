package aplicacao;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

import java.util.Scanner;

public class Programa {
    public static void main(String[] args) {
        PartidaXadrez partida = new PartidaXadrez();
        Scanner sc = new Scanner(System.in);

        while (true) {
            UI.imprimeTabuleiro(partida.retornaPecas());
            System.out.println();
            System.out.print("Posicao Origem: ");
            PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
            System.out.println();
            System.out.print("Posicao Destino: ");
            PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
            PecaXadrez pecaCapturada = partida.fazerMovimentoDePeca(origem, destino);
        }
    }
}
