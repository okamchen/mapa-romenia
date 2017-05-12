package br.feevale;

import java.util.ArrayList;
import java.util.List;

public class Cidade {

	private String nome;
	private double latitude;
	private double longetude;
    private List<Vizinho> vizinhos = new ArrayList<>();

    public void addVizinho(Vizinho vizinho){
        vizinhos.add(vizinho);
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Vizinho> getVizinhos() {
        return vizinhos;
    }

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongetude() {
		return longetude;
	}

	public void setLongetude(double longetude) {
		this.longetude = longetude;
	}
    
}