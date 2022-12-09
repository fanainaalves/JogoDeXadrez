package Xadrez.pecas;

import JogoDeTabuleiro.Posicao;
import JogoDeTabuleiro.Tabuleiro;
import Xadrez.Cor;
import Xadrez.PartidaDeXadrez;
import Xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez{
	
	private PartidaDeXadrez partidaDeXadrez;

	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
		super(tabuleiro, cor);
		this.partidaDeXadrez = partidaDeXadrez;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "R";
	}

	private boolean podeMover(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	
	private boolean testeTorreRoque(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getMoveCount() == 0;
	}
	
	
	//direção das peças
	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		//acima
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//abaixo
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//direita
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//noroeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// nordeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//sudoeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// sudeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Movimento especial de Roque
		if(getMoveCount() == 0 && !partidaDeXadrez.getCheck()) {
			//Roque pequeno
			Posicao posicaoTorre1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
			if(testeTorreRoque(posicaoTorre1)) {
				Posicao posTorre1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				Posicao posTorre2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
				if(getTabuleiro().peca(posTorre1) == null && getTabuleiro().peca(posTorre2) == null) {
					mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}
			}	
			//Roque grande
			Posicao posicaoTorre2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
			if(testeTorreRoque(posicaoTorre2)) {
				Posicao posTorre1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				Posicao posTorre2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
				Posicao posTorre3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
				if(getTabuleiro().peca(posTorre1) == null && getTabuleiro().peca(posTorre2) == null && getTabuleiro().peca(posTorre3) == null) {
					mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
				}
			}	
			
			
		}		
		
		
		return mat;
	}

}
