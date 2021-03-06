package contribuintes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.List;
import java.util.stream.Collectors;

import atividadesEconomicas.AtividadeEconomica;
import moradas.Morada;

public class ContribuinteIndividual extends Contribuinte implements Serializable,BeneficioFiscal {
    private int numDependentesAgregado;
    private ArrayList<Integer> nifsAgregado;
    private float coefFiscal;
    //AtividadesEconomicas 
    private HashMap<String,AtividadeEconomica> actDeduziveis;
    
    /**
     * Devolve o numero de dependentes do agregado
     */
    public int getNumDependentesAgregado() {
        return numDependentesAgregado;
    }
    
    
    /**
     * Atualiza o numero de dependentes do agregado
     */
    public void setNumDependentesAgregado(int numDependentesAgregado) {
        this.numDependentesAgregado = numDependentesAgregado;
    }

    /**
     * Devolve o coeficiente fiscal
     */
    public float getCoefFiscal() {
        return coefFiscal;
    }
    
    /**
     * Atualiza o coeficiente fiscal
     */
    public void setCoefFiscal(float coefFiscal) {
        this.coefFiscal = coefFiscal;
    }
    
    /**
     * Atualiza os nifs do agregado familiar
     */
    public void setNifsAgregado(List<Integer> nifs) {
        for(Integer nif : nifs){
            this.nifsAgregado.add(nif);
        }
    }
    
    /**
     * Devolve a lista dos nifs do agreagado familiar
     */
    public List<Integer> getNifsAgregado() {
    	if (this.nifsAgregado == null || this.nifsAgregado.size() == 0)
    		return new ArrayList<>();
        return this.nifsAgregado.stream().collect(Collectors.toList());
    }
    
    /**
     * Devolve as atividades economicas que sao deduziveis
     */
    public Map<String, AtividadeEconomica> getActDeduziveis() {
        Map<String, AtividadeEconomica> res = new HashMap<String,AtividadeEconomica>();
        for(AtividadeEconomica v : this.actDeduziveis.values())
            res.put(v.getNomeAtividade(), v.clone());
        return res;
    }
    
    /**
     * Atualiza as aitividades economicas que sao deduziveis
     */
    private void setActDeduziveis(Map<String, AtividadeEconomica> actDeduziveis2) {
        this.actDeduziveis = new HashMap<String, AtividadeEconomica>();
        for(Entry<String, AtividadeEconomica> e : actDeduziveis.entrySet())
            this.actDeduziveis.put(e.getKey(), e.getValue().clone());
    }

    public void addAgregado(int nif) {
    	if(!this.nifsAgregado.contains(nif))
    		this.nifsAgregado.add(nif);
    }

    
    /**
     * Construtor vazio
     */
    public ContribuinteIndividual() {
        super();
        this.actDeduziveis = new HashMap<>();
        this.nifsAgregado = new ArrayList<Integer>();
        this.coefFiscal = 0.2f;
        this.numDependentesAgregado = 0;
    }
    
    /**
     * Construtor parametrizado
     */
    public ContribuinteIndividual(String nome, int nif, String email, Morada morada, String password,float coefFiscal) {
        super(nome,nif,email,morada,password);
        this.coefFiscal = coefFiscal; 
        this.numDependentesAgregado = 0;
        this.nifsAgregado = new ArrayList<Integer>();
        this.nifsAgregado.add(nif);
        this.actDeduziveis = new HashMap<>();
    }
    
    /**
     * Construtor copia
     */
    public ContribuinteIndividual(ContribuinteIndividual a){
        super(a);
        this.actDeduziveis = new HashMap<>();
        this.nifsAgregado = new ArrayList<Integer>();
        this.setNifsAgregado(a.getNifsAgregado());
        this.numDependentesAgregado = a.getNumDependentesAgregado();
        this.coefFiscal = a.getCoefFiscal();
        this.setActDeduziveis(a.getActDeduziveis());
    }
    

    
    /**
     * Clone
     */
    public Contribuinte clone() {
        return new ContribuinteIndividual(this);
    }
    
    /**
     * Devolve a taxa de reducao de imposto a aplicar
     */
    public double reducaoImposto() {
        if (this.numDependentesAgregado>=5)
            return 0.05*this.numDependentesAgregado;
        return 0;
    }

    public boolean isActDeduzivel(AtividadeEconomica naturezaDespesa) {
        return this.actDeduziveis.containsKey(naturezaDespesa.getNomeAtividade());
    }


    

}
