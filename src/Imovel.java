
public class Imovel extends CasaTabuleiro{
	private int tipoImovel;
	private double valorCompra;
	private double taxaAluguel;
	private Jogador dono;
	
	public Imovel(int posicao, int tipoImovel, int valorCompra, int taxaAluguel, Jogador dono)
	{
		super(posicao);
		this.valorCompra = valorCompra;
		this.taxaAluguel = taxaAluguel;
		this.dono = dono;
	}

	public Jogador getDono() {
		return dono;
	}

	protected void setDono(Jogador dono) {
		this.dono = dono;
	}

	public double getValorCompra() {
		return valorCompra;
	}

	private double getTaxaAluguel() {
		return taxaAluguel;
	}

	public double calcularValorAluguel()
	{
		return (this.getValorCompra() / 100.0) * this.getTaxaAluguel();
	}
}
