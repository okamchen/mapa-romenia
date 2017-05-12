package br.feevale;

public class Estrada implements Comparable<Estrada>{
	
	private Cidade cidade;
	private Estrada estradaQueVolta = null;
	private double custo = 0.0;
	private double comparador = 0.0;
	
	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Estrada getEstradaQueVolta() {
		return estradaQueVolta;
	}

	public void setEstradaQueVolta(Estrada estradaQueVolta) {
		this.estradaQueVolta = estradaQueVolta;
	}

	public double getCusto() {
		return custo;
	}

	public void setCusto(double custo) {
		this.custo = custo;
	}

	public double getComparador() {
		return comparador;
	}

	public void setComparador(double comparador) {
		this.comparador = comparador;
	}

	@Override
	public int compareTo(Estrada o) {
		if (this.comparador < o.getComparador()){
			return -1;
		}else{
			return 1;
		}		
	}

}
