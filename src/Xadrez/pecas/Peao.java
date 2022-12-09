package Xadrez.pecas;

import JogoDeTabuleiro.Posicao;
import JogoDeTabuleiro.Tabuleiro;
import Xadrez.PartidaDeXadrez;
import Xadrez.PecaDeXadrez;
import Xadrez.Cor;

public class Peao extends PecaDeXadrez{
	
	private PartidaDeXadrez partidaDeXadrez;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
		super(tabuleiro, cor);
		this.partidaDeXadrez = partidaDeXadrez;
		
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean [][] mat = new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0, 0);
		if(getCor() == Cor.BRANCO) {
			
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().thereIsApiece(p)) {
				mat[p.getLinha()][p.getColuna()] = true;	
			}
			
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().thereIsApiece(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().thereIsApiece(p2) && getMoveCount() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() -1);
			if(getTabuleiro().posicaoExiste(p) && isThereOpponentPiece(p)) {
				mat[p.getLinha()][p.getColuna()] = true;			
			}
			
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() +1);
			if(getTabuleiro().posicaoExiste(p) && isThereOpponentPiece(p)) {
				mat[p.getLinha()][p.getColuna()] = true;				
			}
			
			// movimento especial en passant pecas brancas
			if (posicao.getLinha() == 3) {				
				Posicao left = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if(getTabuleiro().posicaoExiste(left) && isThereOpponentPiece(left) && getTabuleiro().peca(left) == partidaDeXadrez.getEnPassantVulnerabilidade()){
					mat[left.getLinha() - 1][left.getColuna()] = true;
				}
				Posicao right = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if(getTabuleiro().posicaoExiste(right) && isThereOpponentPiece(right) && getTabuleiro().peca(right) == partidaDeXadrez.getEnPassantVulnerabilidade()){
					mat[right.getLinha() - 1][right.getColuna()] = true;
				}

			}
			
		} 
		else {
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().thereIsApiece(p)) {
				mat[p.getLinha()][p.getColuna()] = true;	
			}
			
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().thereIsApiece(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().thereIsApiece(p2) && getMoveCount() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() -1);
			if(getTabuleiro().posicaoExiste(p) && isThereOpponentPiece(p)) {
				mat[p.getLinha()][p.getColuna()] = true;			
			}
			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() +1);
			if(getTabuleiro().posicaoExiste(p) && isThereOpponentPiece(p)) {
				mat[p.getLinha()][p.getColuna()] = true;				
			}
			
			// movimento especial en passant pecas pretas
			if (posicao.getLinha() == 4) {
				Posicao left = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if(getTabuleiro().posicaoExiste(left) && isThereOpponentPiece(left) && getTabuleiro().peca(left) == partidaDeXadrez.getEnPassantVulnerabilidade()){
					mat[left.getLinha() + 1][left.getColuna()] = true;
				}
				Posicao right = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if(getTabuleiro().posicaoExiste(right) && isThereOpponentPiece(right) && getTabuleiro().peca(right) == partidaDeXadrez.getEnPassantVulnerabilidade()){
					mat[right.getLinha() + 1][right.getColuna()] = true;
				}

			}
			
		}
		
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}

}
