package Aplicacao;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Xadrez.PartidaDeXadrez;
import Xadrez.PecaDeXadrez;
import Xadrez.PosicaoXadrez;
import Xadrez.Cor;

public class InterfaceUsuario {


	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	
	public static PosicaoXadrez lerPosicaoXadrez(Scanner sc) {
		try {
			String s = sc.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new PosicaoXadrez(coluna, linha);
		}
		catch (RuntimeException e) {
			throw new InputMismatchException("ERRO AO LER A POSICAO DO XADREZ. POSICOES VALIDAS DE A1 ATE H8.");
		}
	}

	public static void printPartida(PartidaDeXadrez partidaDeXadrez, List<PecaDeXadrez> capturada) {
		printTabuleiro(partidaDeXadrez.getPecas());
		System.out.println();
		printPecasCapturadas(capturada);
		System.out.println();
		System.out.println("Turno: " + partidaDeXadrez.getVirar());
		if (!partidaDeXadrez.getCheckMate()) {
			System.out.println("Esperando Jogador: " + partidaDeXadrez.getCurrentJogador());			
			if(partidaDeXadrez.getCheck()) {
				System.out.println("CHECK!");
			}
		}
		else {
			System.out.println("CHECKMATE!");
			System.out.println("Vencedor: " + partidaDeXadrez.getCurrentJogador());
		}				
	}
	
	public static void printTabuleiro(PecaDeXadrez[][] pecas) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				printPeca(pecas[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	public static void printTabuleiro(PecaDeXadrez[][] pecas, boolean[][] movimentosPossiveis) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				printPeca(pecas[i][j], movimentosPossiveis[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");

	}

	private static void printPeca(PecaDeXadrez peca, boolean background) {
		if(background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (peca == null) {
			System.out.print("-" + ANSI_RESET);
		}
		else {
			if(peca.getCor() == Cor.BRANCO) {
				System.out.print(ANSI_WHITE + peca + ANSI_RESET);
			}
			else {	
				System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}
	
	private static void printPecasCapturadas(List<PecaDeXadrez> capturada) {
		List<PecaDeXadrez> branco = capturada.stream().filter(x -> x.getCor() == Cor.BRANCO).collect(Collectors.toList());
		List<PecaDeXadrez> preto = capturada.stream().filter(x -> x.getCor() == Cor.PRETO).collect(Collectors.toList());
		System.out.println("Peças Capturadas: ");
		System.out.print("Branco: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(branco.toArray()));
		System.out.print(ANSI_RESET);
		System.out.print("Preto: ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(preto.toArray()));
		System.out.print(ANSI_RESET);	
	}
}
