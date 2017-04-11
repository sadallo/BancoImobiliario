import java.io.BufferedReader;
import java.util.ArrayList;

public abstract class CasaTabuleiro {
	private int tipoPosicao; // START, PASSE, IMOVEL
	private int posicao;
	
	public CasaTabuleiro (int tipoPosicao, int posicao)
	{
		this.tipoPosicao = tipoPosicao;
		this.posicao = posicao;
	}
	
	public int getTipo() {
		return tipoPosicao;
	}
	
	public int getPosicao() {
		return posicao;
	}
	
	public static ArrayList<CasaTabuleiro> construirTabuleiro(BufferedReader buffTabuleiro) throws Exception
	{
		int tamanhoTabuleiro = Integer.parseInt(buffTabuleiro.readLine());
		ArrayList<CasaTabuleiro> tabuleiro = new ArrayList<CasaTabuleiro>(tamanhoTabuleiro);
		int []valores;
			
		for(int i = 0; i< tamanhoTabuleiro; i++){
			valores = Auxiliar.intArrayC(buffTabuleiro.readLine().split(";"));
			
			CasaTabuleiro casa;
			if(valores[Constants.TIPO_POS] == Constants.IMOVEL)
			{
				casa = new Imovel(valores[Constants.TIPO_POS], 
								valores[Constants.POSICAO], 
						        valores[Constants.TIPO_IMOVEL], 
						        valores[Constants.VALOR], 
				                valores[Constants.ALUGUEL],
				                Constants.BANCO);
			} 
			else if(valores[Constants.TIPO_POS] == Constants.START)
			{
				casa = new Start(valores[Constants.TIPO_POS], valores[Constants.POSICAO]);
			}
			else
			{
				casa = new PassaVez(valores[Constants.TIPO_POS], valores[Constants.POSICAO]);
			}
			
			tabuleiro.add(casa);	
		}
		
		// Ordena as posicoes do tabuleiro caso elas nao tenham sido descritas em ordem crescente
		tabuleiro.sort(new CasaTabuleiroComparator());
		return tabuleiro;
	}
	
	public static void retornarImoveisBanco(ArrayList<CasaTabuleiro> tabuleiro, Jogador jogadorFalido)
	{
		for(CasaTabuleiro c: tabuleiro)
		{
			if(c instanceof Imovel)
			{
				Imovel imovelAux = (Imovel) c;
				if(imovelAux.getDono() == jogadorFalido)
				{
					imovelAux.setDono(Constants.BANCO);
				}
			}
		}
	}
	
}