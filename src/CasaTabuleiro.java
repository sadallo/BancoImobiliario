import java.util.ArrayList;



public abstract class CasaTabuleiro {
	private int posicao;
	
	public CasaTabuleiro (int posicao)
	{
		this.posicao = posicao;
	}

	public int getPosicao() { 
		return this.posicao;
	}
	
	public static ArrayList<CasaTabuleiro> construirTabuleiro(int [][]camposTabuleiro, int tamanhoTabuleiro)
	{
		ArrayList<CasaTabuleiro> tabuleiro = new ArrayList<CasaTabuleiro>(tamanhoTabuleiro);
			
		for(int i = 0; i< tamanhoTabuleiro; i++){
			
			CasaTabuleiro casa;
			if(camposTabuleiro[i][Constants.TIPO_POS] == Constants.IMOVEL)
			{
				casa = new Imovel(camposTabuleiro[i][Constants.POSICAO], 
						camposTabuleiro[i][Constants.TIPO_IMOVEL], 
						camposTabuleiro[i][Constants.VALOR], 
						camposTabuleiro[i][Constants.ALUGUEL],
				                Constants.BANCO);
			} 
			else if(camposTabuleiro[i][Constants.TIPO_POS] == Constants.START)
			{
				casa = new Start(camposTabuleiro[i][Constants.POSICAO]);
			}
			else
			{
				casa = new PassaVez(camposTabuleiro[i][Constants.POSICAO]);
			}
			
			tabuleiro.add(casa);	
		}
		
		// Ordena as posicoes do tabuleiro caso elas nao tenham sido descritas em ordem crescente
		tabuleiro.sort(new CasaTabuleiroComparator());
		return tabuleiro;
	}
	
	// !!! explicar na doc trade-off memoria-eficiencia
	public static void retornarImoveisBanco(ArrayList<CasaTabuleiro> tabuleiro, Jogador jogadorFalido)
	{
		for(CasaTabuleiro c: tabuleiro)
		{
			if(c instanceof Imovel)
			{
				Imovel imovel = (Imovel) c;
				if(imovel.getDono() == jogadorFalido)
				{
					imovel.setDono(Constants.BANCO);
				}
			}
		}
	}
	
}