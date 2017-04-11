import java.util.ArrayList;

public class Jogador {
	private int identificador;
	private double saldo;
	private double quantidadeAluguelRecebido;
	private double quantidadeAluguelPago;
	private double quantidadeCompraImovel;
	private int contadorPassaVez;
	private int contadorVoltaTabuleiro;
	private int posicaoCasaTabuleiro;
	private boolean eliminado;
	
	public Jogador(int identificador, double saldo)
	{
		this.identificador = identificador;
		this.saldo = saldo;
		this.quantidadeAluguelRecebido = 0;
	    this.quantidadeAluguelPago = 0;
	    this.quantidadeCompraImovel = 0;
	    this.contadorPassaVez = 0;
	    this.contadorVoltaTabuleiro = 0;
	    this.posicaoCasaTabuleiro = Constants.POSICAO_INICIO;
	    this.eliminado = false;
	}
	
	public int getIdentificador() {
		return identificador;
	}
	
	public double getSaldo() {
		return saldo;
	}
	
	private double getQuantidadeAluguelRecebido() {
		return quantidadeAluguelRecebido;
	}

	private double getQuantidadeAluguelPago() {
		return quantidadeAluguelPago;
	}

	private double getQuantidadeCompraImovel() {
		return quantidadeCompraImovel;
	}

	private int getContadorPassaVez() {
		return contadorPassaVez;
	}

	private int getContadorVoltaTabuleiro() {
		return contadorVoltaTabuleiro;
	}
	
	private int getPosicaoCasaTabuleiro() {
		return posicaoCasaTabuleiro;
	}
	
	public boolean isEliminado() {
		return eliminado;
	}

	private void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}
		
	private void comprarImovel(Imovel imovel)
	{
		double valor = imovel.getValorCompra();
		this.pagar(valor);
		imovel.setDono(this);

		// Estatística
		this.quantidadeCompraImovel += valor;
	}
	
	private void pagarAluguel(Imovel imovel)
	{
		double valor = imovel.calcularValorAluguel();
		this.pagar(valor);
		imovel.getDono().receberAluguel(valor);
		
		// Estatística
		this.quantidadeAluguelPago += valor;
	}
	
	private void receberAluguel(double valor)
	{
		this.receber(valor);

		// Estatística
		this.quantidadeAluguelRecebido += valor;
	}
	
	private void receber(double valor)
	{
		this.saldo = this.saldo + valor;
		System.out.println("recebi "+ valor);

	}
	
	private void pagar(double valor)
	{
		this.saldo = this.saldo - valor;
		System.out.println("paguei "+ valor);
	}
	
	private void passarAVez()
	{
		// Estatística
		this.contadorPassaVez++;
	}
	
	private void andar(int quantidadeCasasAndar, int tamanhoTabuleiro)
	{
		int posicaoAnterior = this.posicaoCasaTabuleiro;
		int posicaoNova = (posicaoAnterior+quantidadeCasasAndar) % tamanhoTabuleiro;
		this.posicaoCasaTabuleiro = posicaoNova;
		
		System.out.println("eu tava na " + posicaoAnterior + " e fui pra " + posicaoNova);
		
		if(posicaoNova <= posicaoAnterior)
		{
			this.receber(Constants.VALOR_AO_PASSAR_INICIO); // Passou pelo inicio
			
			// Estatística
			this.contadorVoltaTabuleiro++;
		}
	}

	public void jogar(ArrayList<CasaTabuleiro> tabuleiro, int quantidadeCasasAndar)
	{
		this.andar(quantidadeCasasAndar, tabuleiro.size());
	    CasaTabuleiro casa = tabuleiro.get(this.getPosicaoCasaTabuleiro()-1); // verificar isso
	    if (casa instanceof Imovel)
	    {
	    	Imovel imovel = (Imovel) casa;
	    	if(imovel.getDono() == Constants.BANCO)
	    	{
	    		// rotina tentar comprar
	    		
	    		if(this.getSaldo() >= imovel.getValorCompra())
    			{
	    			this.comprarImovel(imovel);		
	    			
	    		    // caco
	    			System.out.println("Jogador " + this.getIdentificador() + " comprando imovel de " + imovel.getValorCompra());
    			}
	    	}
	    	else if(imovel.getDono() != this)
	    	{
	    		// rotina pagar aluguel   		
	    		if(this.getSaldo() >= imovel.calcularValorAluguel())
	    		{
	    		    // caco
	    			System.out.println("Jogador " + this.getIdentificador() + " pagando aluguel de " + imovel.calcularValorAluguel() + " no imovel " + imovel.getPosicao() + " do jogador " + imovel.getDono().getIdentificador());
	    			
	    			this.pagarAluguel(imovel);
	    		}
	    		else
	    		{
	    		    // caco
	    			System.out.print("\n!!!!FALENCIA!!!!\n");
	    			
	    			// falencia, retornar imoveis ao banco
	    			CasaTabuleiro.retornarImoveisBanco(tabuleiro, this);
	    			
	    			// Eliminar jogador
	    			this.setEliminado(true);
	    		}
	    	}	    		
	    }
	    else if(casa instanceof PassaVez)
	    {
		    // caco
	    	System.out.println("Jogador " + this.getIdentificador() + " passa a vez");
	    	
	    	this.passarAVez();
	    	
	    }
	    else // Start
	    {
		    // caco
	    	System.out.println("Jogador " + this.getIdentificador() + " parou no start");
	    	
	    	// faz nada
	    } 
	}

	
	public static ArrayList<Jogador> criarJogadores(int quantidadeJogadores, int saldoInicial) throws Exception{
		ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
	     
		for (int identificador = 1; identificador <= quantidadeJogadores; identificador++)
		{
			jogadores.add(new Jogador(identificador, saldoInicial));
		}
	
		return jogadores;
	}
	
	public static void ImprimirEstatisticas(ArrayList<Jogador> jogadores)
	{
		System.out.print("\n2:");
		for(Jogador j: jogadores)
			System.out.print(j.getIdentificador() + "-" + j.getContadorVoltaTabuleiro() + ";");
		System.out.print("\n3:");
		for(Jogador j: jogadores)
			System.out.print(j.getIdentificador() + "-" + j.getSaldo() + ";");
		System.out.print("\n4:");
		for(Jogador j: jogadores)
			System.out.print(j.getIdentificador() + "-" + j.getQuantidadeAluguelRecebido() + ";");
		System.out.print("\n5:");
		for(Jogador j: jogadores)
			System.out.print(j.getIdentificador() + "-" + j.getQuantidadeAluguelPago() + ";");
		System.out.print("\n6:");
		for(Jogador j: jogadores)
			System.out.print(j.getIdentificador() + "-" + j.getQuantidadeCompraImovel() + ";");
		System.out.print("\n7:");
		for(Jogador j: jogadores)
			System.out.print(j.getIdentificador() + "-" + j.getContadorPassaVez() + ";");	
	}
	
}
