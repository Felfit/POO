package Contribuintes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import AtividadesEconomicas.AtividadeEconomica;

public class ContribuinteIndividual extends Contribuinte {
	private int numDependentesAgregado;
	private ArrayList<Integer> nifsAgregado; //Fazer Getters e Setters
	private float coefFiscal;
	//AtividadesEconomicas 
	private HashMap<Integer,AtividadeEconomica> actDeduziveis; //Fazer Getters e Setters
	
	public int getNumDependentesAgregado() {
		return numDependentesAgregado;
	}
	public void setNumDependentesAgregado(int numDependentesAgregado) {
		this.numDependentesAgregado = numDependentesAgregado;
	}

	public float getCoefFiscal() {
		return coefFiscal;
	}
	public void setCoefFiscal(float coefFiscal) {
		this.coefFiscal = coefFiscal;
	}
	public void setNifsAgregado(Set<Integer> nifs) {
		for(Integer nif : nifs){
			this.nifsAgregado.add(nif);
		}
	}
	public Set<Integer> getNifsAgregado() {
		return this.nifsAgregado.stream().collect(Collectors.toSet());
	}
	public Map<Integer,AtividadeEconomica> getActDeduziveis() {
		HashMap<Integer,AtividadeEconomica> res = new HashMap<Integer,AtividadeEconomica>();
		for(AtividadeEconomica v : this.actDeduziveis.values())
			res.put(v.getKey(), v.clone());
		return res;
	}
	private void setActDeduziveis(Map<Integer, AtividadeEconomica> actDeduziveis2) {
		// TODO Auto-generated method stub
		
	}
	public ContribuinteIndividual() {
		// TODO Auto-generated constructor stub
		super();
		this.nifsAgregado = new ArrayList<Integer>();
		this.coefFiscal = 0;
	}
	
	public ContribuinteIndividual(String nome, int nif, String email, String morada, String password,Map<Integer,AtividadeEconomica> aDedusiveis,float coefFiscal) {
		super(nome,nif,email,morada,password);
		this.coefFiscal = coefFiscal; 
	}
	
	public ContribuinteIndividual(ContribuinteIndividual a){
		super(a);
		this.setNifsAgregado(a.getNifsAgregado());
		this.numDependentesAgregado = a.getNumDependentesAgregado();
		this.coefFiscal = a.getCoefFiscal();
		this.setActDeduziveis(a.getActDeduziveis());
	}
	
	@Override
	public Contribuinte clone() {
		// TODO Auto-generated method stub
		return new ContribuinteIndividual(this);
	}
}
