package Aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Xadrez.PartidaDeXadrez;
import Xadrez.PecaDeXadrez;
import Xadrez.PosicaoXadrez;
import Xadrez.XadrezException;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner (System.in);
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		List<PecaDeXadrez> capturada = new ArrayList<>();

		while (!partidaDeXadrez.getCheckMate()) {
			try {
				InterfaceUsuario.clearScreen();
				InterfaceUsuario.printPartida(partidaDeXadrez, capturada);
				System.out.println();
				System.out.print("Partindo de: ");
				PosicaoXadrez source = InterfaceUsuario.lerPosicaoXadrez(sc);
				
				boolean[][] movimentosPossiveis = partidaDeXadrez.movimentosPossiveis(source);
				InterfaceUsuario.clearScreen();
				InterfaceUsuario.printTabuleiro(partidaDeXadrez.getPecas(), movimentosPossiveis);

				System.out.println();
				System.out.println("Destino: ");
				PosicaoXadrez target = InterfaceUsuario.lerPosicaoXadrez(sc);

				PecaDeXadrez pecaCapturada = partidaDeXadrez.performMoverXadrez(source, target);
				
				if(pecaCapturada !=null ) {
					capturada.add(pecaCapturada);
				}
				
				if (partidaDeXadrez.getPromovida() != null) {
					System.out.print("Digite a peça que quer ser Promovida (B/C/T/Q): ");
					String type = sc.nextLine().toUpperCase();
					
					while(!type.equals("B") && !type.equals("C") && !type.equals("T") && !type.equals("Q")) {
						System.out.print("Valor Inválido! Digite a peça que quer ser Promovida (B/C/T/Q): ");
						type = sc.nextLine().toUpperCase();						
					}					
					partidaDeXadrez.trocarPecaPromovida(type);
				}				
			}
			catch (XadrezException e ) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			
			

			catch (InputMismatchException e ) {
				System.out.println(e.getMessage());
				sc.nextLine();

			}
		}
		InterfaceUsuario.clearScreen();
		InterfaceUsuario.printPartida(partidaDeXadrez, capturada);

	}
}
