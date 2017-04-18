import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class BancoImobiliario {
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		int [] camposJogada;
		int tamanhoTabuleiro, identificadorJogada=0, identificadorJogador;
		int quantidadeCasasAndar, quantidadeRodadas=1, identificadorJogadorAnterior = 1;
		int [][]camposTabuleiro;
		String linhaJogada;

		BufferedReader buffTabuleiro = new BufferedReader(new FileReader("bin/tabuleiro.txt"));
		tamanhoTabuleiro = Integer.parseInt(buffTabuleiro.readLine());
		camposTabuleiro = new int[tamanhoTabuleiro][];
		for(int i = 0; i< tamanhoTabuleiro; i++){
			camposTabuleiro[i] = stringArrayToIntArray(buffTabuleiro.readLine().split(";"));
		}
		buffTabuleiro.close();		

		ArrayList<CasaTabuleiro> tabuleiro = CasaTabuleiro.construirTabuleiro(camposTabuleiro, tamanhoTabuleiro);

		BufferedReader buffJogadas = new BufferedReader(new FileReader("bin/jogadas.txt"));
		int [] primeiraLinhaJogadas = stringArrayToIntArray(buffJogadas.readLine().split("%"));
	    
	    int quantidadeInstrucoesDeJogadas = primeiraLinhaJogadas[0];
	    int quantidadeJogadores = primeiraLinhaJogadas[1];
	    int saldoInicialJogador = primeiraLinhaJogadas[2];
	    
		ArrayList <Jogador> jogadores = Jogador.criarJogadores(quantidadeJogadores, saldoInicialJogador);
		
		// Ler instrucao de jogada e realiza-la caso o jogador esteja ainda ativo
		for(int i=1; i <= quantidadeInstrucoesDeJogadas; i++)
		{
			linhaJogada = buffJogadas.readLine();
			
			// Para o jogo quando so haver um jogador ativo ou encontrar DUMP
			if(linhaJogada.compareTo("DUMP") == 0 || Jogador.getQuantidadeJogadoresAtivos() == 1)
				break;
			
			// Le as informacoes de cada jogada
			camposJogada = stringArrayToIntArray(linhaJogada.split(";")); 
			identificadorJogada = camposJogada[0];
			identificadorJogador = camposJogada[1];
			quantidadeCasasAndar = camposJogada[2];

		    // Recupera o jogador atual
		    Jogador jogadorAtual = jogadores.get(identificadorJogador-1);
		    
		    // pular jogada caso Jogador tenha sido eliminado
		    if(jogadorAtual.isEliminado())
		    	continue;
		    
		    // Verifica se eh uma nova rodada
		    if(identificadorJogador < identificadorJogadorAnterior)
			{
				quantidadeRodadas++;
			}
			
		    jogadorAtual.jogar(tabuleiro, quantidadeCasasAndar);    
		    identificadorJogadorAnterior = jogadorAtual.getIdentificador();
		}
		
		buffJogadas.close();
	
		// Impressao das Estatisticas
		System.out.print("\n1:"+quantidadeRodadas);
		Jogador.imprimirEstatisticas(jogadores);
	}
	
  	//Converte vetor de Strings para Array de int
	public static int [] stringArrayToIntArray(String [] toConvert){
		int i = 0;
		int [] newArray = new int [toConvert.length];
		for(String palavra: toConvert){
			newArray[i] = Integer.parseInt(palavra);
			i = i + 1;
		}
		return newArray;
	}
}
