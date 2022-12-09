package Xadrez;

import JogoDeTabuleiro.Peca;
import JogoDeTabuleiro.Posicao;
import JogoDeTabuleiro.Tabuleiro;

public abstract class PecaDeXadrez extends Peca {
	
	private Cor cor;
	private int moveCount;

	public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	
	public void incrementarMoveCount() {
		moveCount++;
	}
	
	public void decrementarMoveCount() {
		moveCount--;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.fromPosicao(posicao); 
	}
	
	protected boolean isThereOpponentPiece(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}

}
