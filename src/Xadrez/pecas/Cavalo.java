package Xadrez.pecas;


import JogoDeTabuleiro.Posicao;
import JogoDeTabuleiro.Tabuleiro;
import Xadrez.PecaDeXadrez;
import Xadrez.Cor;

public class Cavalo extends PecaDeXadrez{
	
	public Cavalo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "C";
	}

	private boolean podeMover(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	
	//direção das peças
	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		//1
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 2);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//2
		p.setValores(posicao.getLinha() - 2, posicao.getColuna() - 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//3
		p.setValores(posicao.getLinha() - 2, posicao.getColuna() + 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//4
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 2);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//5
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 2);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// 6
		p.setValores(posicao.getLinha() + 2, posicao.getColuna() + 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//7
		p.setValores(posicao.getLinha() + 2, posicao.getColuna() - 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//8
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 2);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		
		
		return mat;
	}

}
