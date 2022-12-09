package Xadrez;

import JogoDeTabuleiro.TabuleiroException;

public class XadrezException extends TabuleiroException{

	private static final long serialVersionUID = 1L;
	
	public XadrezException(String mensagem) {
		super(mensagem);
	}
}
