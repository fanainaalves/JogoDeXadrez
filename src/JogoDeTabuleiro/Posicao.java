package JogoDeTabuleiro;

public class Posicao {
	private int linha;
	private int coluna;
	
	
	//Construtor
	public Posicao(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}

	//Encapsulamento
	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

	public void setValores(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	// To String / Classe Object e Método Override
	@Override
	public String toString() {
		return linha + "," + coluna;
	}
	
	
	

}
