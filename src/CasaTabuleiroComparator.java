import java.util.Comparator;

public class CasaTabuleiroComparator implements Comparator<CasaTabuleiro>{

	@Override
	public int compare(CasaTabuleiro o1, CasaTabuleiro o2) {
		// TODO Auto-generated method stub
		if(o1.getPosicao() < o2.getPosicao())
		{
			return -1;
		}
		else if(o1.getPosicao() > o2.getPosicao())
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
}
