package JogoDeTabuleiro;

public class Tabuleiro {
	private int linhas;
	private int colunas;
	private Peca[][] pecas;

	
	public Tabuleiro(int linhas, int colunas) {
		if(linhas < 1 || colunas <1) {
			throw new TabuleiroException("ERRO CRIANDO TABULEIRO! e necessario que haja pelo menos 1 linha e 1 coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
		
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public Peca peca(int linha, int coluna) {
		if (!posicaoExiste(linha, coluna)) {
			throw new TabuleiroException("POSICAO NAO EXISTE NO TABULEIRO");
		}
		return pecas[linha][coluna];
	}
	
	//sobrecarga de método
	public Peca peca(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new TabuleiroException("POSICAO NAO EXISTE NO TABULEIRO");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void lugarPeca(Peca peca, Posicao posicao) {
		if (thereIsApiece(posicao)) {
			throw new TabuleiroException("JA EXISTE UMA PECA NESTA POSICAO " + posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	public Peca removerPeca(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new TabuleiroException("POSICAO NAO EXISTE NO TABULEIRO");
		}
		if (peca(posicao) == null ) {
			return null;
		}
		Peca aux = peca(posicao);
		aux.posicao = null;
		pecas[posicao.getLinha()][posicao.getColuna()] = null;
		return aux;
	}
	
	private boolean posicaoExiste(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}
	
	public boolean posicaoExiste(Posicao posicao) {
		return posicaoExiste(posicao.getLinha(), posicao.getColuna());	
	}
	
	public  boolean thereIsApiece(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new TabuleiroException("POSICAO NAO EXISTE NO TABULEIRO");
		}
		return peca(posicao) != null;
	}
}
