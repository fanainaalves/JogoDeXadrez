package Xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import JogoDeTabuleiro.Peca;
import JogoDeTabuleiro.Posicao;
import JogoDeTabuleiro.Tabuleiro;
import Xadrez.pecas.Bispo;
import Xadrez.pecas.Cavalo;
import Xadrez.pecas.Peao;
import Xadrez.pecas.Rainha;
import Xadrez.pecas.Rei;
import Xadrez.pecas.Torre;

public class PartidaDeXadrez {
	
	private int virar;
	private Cor currentJogador;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PecaDeXadrez enPassantVulneravel;
	private PecaDeXadrez promovida;
	
	
	
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		virar = 1;
		currentJogador = Cor.BRANCO;
		initialSetup();
	}
	
	public int getVirar() {
		return virar;
	}
	
	public Cor getCurrentJogador() {
		return currentJogador;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public PecaDeXadrez getEnPassantVulnerabilidade() {
		return enPassantVulneravel;
	}
	
	public PecaDeXadrez getPromovida() {
		return promovida;
	}
	
	
	public PecaDeXadrez[][] getPecas(){
		PecaDeXadrez[][] matriz = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) { // linhas
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j); //Downcast
			}			
		}
		return matriz;
	}
	
	public boolean[][] movimentosPossiveis(PosicaoXadrez sourcePosicao){
		Posicao posicao = sourcePosicao.toPosicao();
		validarSourcePosicao(posicao);
		return tabuleiro.peca(posicao).possiveisMovimentos();		
	}
	
	public PecaDeXadrez performMoverXadrez(PosicaoXadrez sourcePosicao, PosicaoXadrez targetPosicao) {
		Posicao source = sourcePosicao.toPosicao();
		Posicao target = targetPosicao.toPosicao();
		validarSourcePosicao(source);
		validarTargetPosicao(source, target);
		Peca pecaCapturada = pegueEmova(source, target);
		
		if(testeCheck(currentJogador)) {
			desfazerMovimento(source, target, pecaCapturada);
			throw new XadrezException("Voce nao pode estar em Check");
		}
		PecaDeXadrez movendoPecas = (PecaDeXadrez)tabuleiro.peca(target);
		
		//movimento especial promocao
		promovida = null;
		if(movendoPecas instanceof Peao) {
			if((movendoPecas.getCor() == Cor.BRANCO && target.getLinha() == 0) || (movendoPecas.getCor() == Cor.BRANCO && target.getLinha() == 7)){
				promovida = (PecaDeXadrez)tabuleiro.peca(target);
				promovida = trocarPecaPromovida("Q");
			}
		}
		
		
		check = (testeCheck(oponente(currentJogador))) ? true : false;
		
		if(testCheckMate(oponente(currentJogador))) {
			checkMate = true;
		}
		else {
			nextVirar();
		}
		
		// movimento especial En Passant
		if (movendoPecas instanceof Peao && (target.getLinha() == source.getLinha() - 2|| target.getLinha() == source.getLinha() + 2)){
			enPassantVulneravel = movendoPecas;
		}
		else {
			enPassantVulneravel = null;
		}
		
		return (PecaDeXadrez) pecaCapturada;
	}

	public PecaDeXadrez trocarPecaPromovida(String type) {
		if(promovida == null) {
			throw new IllegalStateException("Esta peca nao pode ser promovida");
		}
		if(!type.equals("B") && !type.equals("C") && !type.equals("T") && !type.equals("Q")) {
			return promovida;
		}
		
		Posicao pos = promovida.getPosicaoXadrez().toPosicao();
		Peca p = tabuleiro.removerPeca(pos);
		pecasNoTabuleiro.remove(p);
		
		PecaDeXadrez novaPeca = novaPeca(type, promovida.getCor());
		tabuleiro.lugarPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
		
	}
	
	private PecaDeXadrez novaPeca(String type, Cor cor) {
		if(type.equals("B")) return new Bispo(tabuleiro, cor);
		if(type.equals("C")) return new Cavalo(tabuleiro, cor);
		if(type.equals("Q")) return new Rainha(tabuleiro, cor);		
		if(type.equals("B")) return new Bispo(tabuleiro, cor);
		return new Torre(tabuleiro, cor);
	}
	
	
	private Peca pegueEmova(Posicao source, Posicao target) {
		PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removerPeca(source);
		p.incrementarMoveCount();
		
		Peca pecaCapturada = tabuleiro.removerPeca(target);
		tabuleiro.lugarPeca(p, target);
		
		if(pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		//tratamento do roque pequeno
		if (p instanceof Rei && target.getColuna() == source.getColuna() + 2){
			Posicao sourceT = new Posicao(source.getLinha(), source.getColuna() + 3);
			Posicao targetT = new Posicao(source.getLinha(), source.getColuna() + 1);
			PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removerPeca(sourceT);
			tabuleiro.lugarPeca(torre, targetT);
			torre.incrementarMoveCount();			
		}
		//tratamento do roque grande
		if (p instanceof Rei && target.getColuna() == source.getColuna() - 2){
			Posicao sourceT = new Posicao(source.getLinha(), source.getColuna() - 4);
			Posicao targetT = new Posicao(source.getLinha(), source.getColuna() - 1);
			PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removerPeca(sourceT);
			tabuleiro.lugarPeca(torre, targetT);
			torre.incrementarMoveCount();			
		}
	
		//movimento especial En Passant
		if (p instanceof Peao) {
			if (source.getColuna() != target.getColuna() && pecaCapturada == null) {
				Posicao peaoPosicao;
				if (p.getCor() == Cor.BRANCO) {
					peaoPosicao = new Posicao(target.getLinha() + 1, target.getColuna());
				}
				else {
					peaoPosicao = new Posicao(target.getLinha() - 1, target.getColuna());
				}
				pecaCapturada = tabuleiro.removerPeca(peaoPosicao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}
					
		return pecaCapturada;		
	}
	
	private void desfazerMovimento(Posicao source, Posicao target, Peca pecaCapturada) {
		PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removerPeca(target);
		p.decrementarMoveCount();
		tabuleiro.lugarPeca(p, source);
		
		if(pecaCapturada != null) {
			tabuleiro.lugarPeca(pecaCapturada, target);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
		//tratamento pro roque pequeno
		if (p instanceof Rei && target.getColuna() == source.getColuna() + 2){
			Posicao sourceT = new Posicao(source.getLinha(), source.getColuna() + 3);
			Posicao targetT = new Posicao(source.getLinha(), source.getColuna() + 1);
			PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removerPeca(targetT);
			tabuleiro.lugarPeca(torre, sourceT);
			torre.decrementarMoveCount();			
		}
		//tratamento pro roque grande
		if (p instanceof Rei && target.getColuna() == source.getColuna() - 2){
			Posicao sourceT = new Posicao(source.getLinha(), source.getColuna() - 4);
			Posicao targetT = new Posicao(source.getLinha(), source.getColuna() - 1);
			PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removerPeca(targetT);
			tabuleiro.lugarPeca(torre, sourceT);
			torre.decrementarMoveCount();	
			
		}
		
		//movimento especial En Passant
		if (p instanceof Peao) {
			if (source.getColuna() != target.getColuna() && pecaCapturada == enPassantVulneravel) {
				
				PecaDeXadrez peao = (PecaDeXadrez)tabuleiro.removerPeca(target);
				
				Posicao peaoPosicao;
				if (p.getCor() == Cor.BRANCO) {
					peaoPosicao = new Posicao(3, target.getColuna());
				}
				else {
					peaoPosicao = new Posicao(4, target.getColuna());
				}
				tabuleiro.lugarPeca(peao, peaoPosicao);
			}
		}
		
	}

	private void validarSourcePosicao(Posicao posicao) {
		if(!tabuleiro.thereIsApiece(posicao)) {
			throw new XadrezException("Esta peça nao esta na posicao");
		}
		if(currentJogador != ((PecaDeXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A peça escolhida não é sua!");
			
		}
		if(!tabuleiro.peca(posicao).isThereAnyPossibleMove()) {
			throw new XadrezException("Não é possivel mover esta peça");
		}
	}
	
	private void validarTargetPosicao(Posicao source, Posicao target) {
		if (!tabuleiro.peca(source).possivelMovimento(target)) {
			throw new XadrezException("A peça escolhida nao pode ser movida para a posição de destino");
		}
	}
	
	private void nextVirar() {
		virar++;
		currentJogador = (currentJogador == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private PecaDeXadrez rei(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : lista) {
			if(p instanceof Rei) {
				return (PecaDeXadrez)p;
			}
		}
		throw new IllegalStateException("Nao existe o Rei na cor " + cor + " no Tabuleiro.");
	}
	
	private boolean testeCheck(Cor cor) {
		Posicao reiPosicao = rei(cor).getPosicaoXadrez().toPosicao();
		List<Peca> oponentePeca = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peca p : oponentePeca) {
			boolean[][] mat = p.possiveisMovimentos();
			if(mat [reiPosicao.getLinha()][reiPosicao.getColuna()]) {
				return true;
			}
		}
		return false;		
	}
	
	private boolean testCheckMate(Cor cor) {
		if(!testeCheck(cor)) {
			return false;
		}
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == cor).collect(Collectors.toList());
		for(Peca p : lista) {
			boolean[][] mat = p.possiveisMovimentos();
			for(int i = 0; i < tabuleiro.getLinhas(); i++) {
				for(int j = 0; j < tabuleiro.getColunas(); j++) {
					if(mat[i][j]) {
						Posicao source = ((PecaDeXadrez)p).getPosicaoXadrez().toPosicao();
						Posicao target = new Posicao(i, j);
						Peca pecaCapturada = pegueEmova(source, target);
						boolean testeCheck = testeCheck(cor);
						desfazerMovimento(source, target, pecaCapturada);
						if (!testeCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}	
	
	private void lugarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.lugarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
	}
	
	private void initialSetup() {		
		lugarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        lugarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        lugarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        lugarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        lugarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        lugarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        lugarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        lugarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

        lugarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
        lugarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
        lugarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
        lugarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));
        lugarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
        lugarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
        lugarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
        lugarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
        lugarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
        lugarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
        lugarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
        lugarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
        lugarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
        lugarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
        lugarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
        lugarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO, this));
       }

}
