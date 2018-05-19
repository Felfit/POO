package moradas;

import java.io.Serializable;


public abstract class Localidade implements Serializable{
	private String localidade;

	public String getLocalidade() {
		return localidade;
	}

	public Localidade(String localidade) {
		this.localidade = localidade;
	}
	
	public abstract Localidade clone();
}