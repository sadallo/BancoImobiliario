import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class BancoImobiliario {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		int [] camposJogada;
		int identificadorJogada, identificadorJogador, quantidadeCasasAndar, quantidadeRodadas=0;
		String linhaJogada;
		
		BufferedReader buffTabuleiro = new BufferedReader(new FileReader("bin/tabuleiro.txt"));
		ArrayList<CasaTabuleiro> tabuleiro = CasaTabuleiro.construirTabuleiro(buffTabuleiro);
		
		// caco - Imprime tabuleiro
		for(CasaTabuleiro c: tabuleiro)
		{
			System.out.println(c.getPosicao());
		}
		//
		
		BufferedReader buffJogadas = new BufferedReader(new FileReader("bin/jogadas.txt"));
		int [] primeiraLinhaJogadas = Auxiliar.intArrayC(buffJogadas.readLine().split("%"));
	    
	    int quantidadeInstrucoesDeJogadas = primeiraLinhaJogadas[0];
	    int quantidadeJogadores = primeiraLinhaJogadas[1];
	    int saldoInicialJogador = primeiraLinhaJogadas[2];
	    
		ArrayList <Jogador> jogadores = Jogador.criarJogadores(quantidadeJogadores, saldoInicialJogador);
		
		
		// Jogar
		for(int i=0; i < quantidadeInstrucoesDeJogadas; i++)
		{
			linhaJogada = buffJogadas.readLine();
			
			// Para o jogo quando encontrar DUMP
			if(linhaJogada.compareTo("DUMP") == 0)
				break;
		
			camposJogada = Auxiliar.intArrayC(linhaJogada.split(";"));
			//identificadorJogada = camposJogada[0];
			identificadorJogador = camposJogada[1];
			quantidadeCasasAndar = camposJogada[2];
		    
		    // caco
		    System.out.println("Dinheiros:");
		    for(Jogador j: jogadores)
				System.out.print(j.getIdentificador() + "-" + j.getSaldo() + ";");
		    
			System.out.println("\nTurno "+ i);
			System.out.println("Jogador "+ identificadorJogador + " anda " + quantidadeCasasAndar);
			// caco
			
		    Jogador jogadorAtual = jogadores.get(identificadorJogador-1); // verificar isso
		    
		    // pular jogada caso Jogador tenha sido eliminado
		    if(jogadorAtual.isEliminado())
		    	continue;
		    
		    jogadorAtual.jogar(tabuleiro, quantidadeCasasAndar);

		    /// [ENCAPSULADO] pensar em encapsular esse pedaco abaixo num metodo jogar()??
		    
//		    jogadorAtual.andar(quantidadeCasasAndar, tabuleiro.size());
//		    CasaTabuleiro casa = tabuleiro.get(jogadorAtual.getPosicaoCasaTabuleiro()-1); // verificar isso
//		    if (casa instanceof Imovel)
//		    {
//		    	Imovel imovel = (Imovel) casa;
//		    	if(imovel.getDono() == Constants.BANCO)
//		    	{
//		    		// rotina tentar comprar
//		    		
//		    		if(jogadorAtual.getSaldo() >= imovel.getValorCompra())
//	    			{
//		    			jogadorAtual.comprarImovel(imovel);		
//		    			
//		    		    // caco
//		    			System.out.println("Jogador " + jogadorAtual.getIdentificador() + " comprando imovel de " + imovel.getValorCompra());
//	    			}
//		    	}
//		    	else if(imovel.getDono() != jogadorAtual)
//		    	{
//		    		// rotina pagar aluguel   		
//		    		if(jogadorAtual.getSaldo() >= imovel.calcularValorAluguel())
//		    		{
//		    		    // caco
//		    			System.out.println("Jogador " + jogadorAtual.getIdentificador() + " pagando aluguel de " + imovel.calcularValorAluguel() + " no imovel " + imovel.getPosicao() + " do jogador " + imovel.getDono().getIdentificador());
//		    			
//		    			jogadorAtual.pagarAluguel(imovel);
//		    		}
//		    		else
//		    		{
//		    		    // caco
//		    			System.out.print("\n!!!!FALENCIA!!!!\n");
//		    			
//		    			// falencia, retornar imoveis ao banco
//		    			CasaTabuleiro.retornarImoveisBanco(tabuleiro, jogadorAtual);
//		    			
//		    			// Eliminar jogador
//		    			jogadorAtual.setEliminado(true);
//		    		}
//		    	}	    		
//		    }
//		    else if(casa instanceof PassaVez)
//		    {
//			    // caco
//		    	System.out.println("Jogador " + jogadorAtual.getIdentificador() + " passa a vez");
//		    	
//		    	jogadorAtual.passarAVez();
//		    	
//		    }
//		    else // Start
//		    {
//			    // caco
//		    	System.out.println("Jogador " + jogadorAtual.getIdentificador() + " parou no start");
//		    	
//		    	// faz nada
//		    }
		    
		    /*Como calcular a quantidade de rodadas? 
		     Numero de vezes que o jogador 1 jogou? E se ele for eliminado? */
		    if(jogadorAtual.getIdentificador() == 1)
		    {
		    	quantidadeRodadas++;
		    }
		}
		
		buffTabuleiro.close();
		buffJogadas.close();
		
		
		// Impressao das Estatisticas
		System.out.print("\n1:"+quantidadeRodadas);
		Jogador.ImprimirEstatisticas(jogadores);
	}

}
