public class Auxiliar {
	// CLASSE AUXILIAR, MODULARIZACAO E ENCAPSULAMENTO PRECISAM SER MELHORADOS

  	//Convert String Array to int Array
	public static int [] intArrayC(String [] toConvert){
		int i = 0;
		int [] newArray = new int [toConvert.length];
		for(String palavra: toConvert){
			newArray[i] = Integer.parseInt(palavra);
			i = i + 1;
		}
		return newArray;
	}
}
