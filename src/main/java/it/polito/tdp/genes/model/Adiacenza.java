package it.polito.tdp.genes.model;

public class Adiacenza implements Comparable<Adiacenza>{
	
	Genes g1;
	Genes g2;
	Double peso;
	
	public Adiacenza(Genes g1, Genes g2, Double peso) {
		this.g1 = g1;
		this.g2 = g2;
		this.peso = peso;
	}

	public Genes getG1() {
		return g1;
	}

	public void setG1(Genes g1) {
		this.g1 = g1;
	}

	public Genes getG2() {
		return g2;
	}

	public void setG2(Genes g2) {
		this.g2 = g2;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(Adiacenza o) {
		// TODO Auto-generated method stub
		return -this.getPeso().compareTo(o.getPeso());
	}
	
	

}
