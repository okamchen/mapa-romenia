package br.feevale;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class RotaTracada {
	
	private Cidade de;
	private Cidade para;
	private HashSet<Cidade> cidadesVisitadas;
	private Stack<Estrada> tragetoPercorrido;
	
	public RotaTracada(Cidade de, Cidade para){
		this.de = de;
		this.para = para;
		this.tragetoPercorrido = montarRota();
	}

	private Stack<Estrada> montarRota() {
		this.cidadesVisitadas = new HashSet<>();
		Stack<Estrada> pilhaDeEstradas = new Stack<>();

		Estrada estradaAtual = new Estrada();
		estradaAtual.setCidade(de);
		pilhaDeEstradas.push(estradaAtual);

		while (!pilhaDeEstradas.isEmpty()) {

			estradaAtual = pilhaDeEstradas.pop();

			if (para.equals(estradaAtual.getCidade())) {
				return obterRotaDeEstradasPercorridas(estradaAtual);
			}

			pilhaDeEstradas.addAll(obterEstradasPelaMenorDistancia(estradaAtual));
		}
		
		return null;
	}

	private List<Estrada> obterEstradasPelaMenorDistancia(Estrada estradaAtual) {
		
		List<Estrada> estradasAnteriores = new ArrayList<>();
		this.cidadesVisitadas.add(estradaAtual.getCidade());
		
		for(Vizinho vizinho : estradaAtual.getCidade().getVizinhos()){
			if(!this.cidadesVisitadas.contains(vizinho.getCidade())){
				Estrada novoRumo = new Estrada();
				novoRumo.setCidade(vizinho.getCidade());
				novoRumo.setEstradaQueVolta(estradaAtual);
				
				double distanciaEmLinhaReta = calcularDistanciaEmLinhaReta(vizinho.getCidade(), para);
				
				novoRumo.setComparador(distanciaEmLinhaReta);
				novoRumo.setCusto(vizinho.getDistancia());
				
				estradasAnteriores.add(novoRumo);
				
			}
		}
		
		Collections.sort(estradasAnteriores, Collections.reverseOrder());
		
		return estradasAnteriores;
	}

	/**
	 * O cálculo para medir a distancia em linha reta entre cidade atual, com cidade destino,
	 * utilizando informaçoes de latitude e longetude das cidades, é uma forma de heurística,
	 * presumindo que a menor distantica em linha reta, seja a melhor rota a ser seguida.
	 * 
	 * @param cidadeAtual cidade {@link Cidade} do vizinho em questão 
	 * @param cidadeDestino cidade {@link Cidade} destino, objetivo de encontro
	 * @return distância em linha reta entre cidades.
	 */
	private double calcularDistanciaEmLinhaReta(Cidade cidadeAtual, Cidade cidadeDestino) {
		return Math.sqrt(
				Math.pow((cidadeDestino.getLatitude() - cidadeAtual.getLatitude()), 2) 
				+ Math.pow((cidadeDestino.getLongetude() - cidadeAtual.getLongetude()), 2)) 
			* 100;
	}

	private Stack<Estrada> obterRotaDeEstradasPercorridas(Estrada estrada) {
		Stack<Estrada> rota = new Stack<>();
		rota.add(estrada);

		Estrada estradaQueVolta = estrada.getEstradaQueVolta();
		while (estradaQueVolta != null) {
			rota.push(estradaQueVolta);
			estradaQueVolta = estradaQueVolta.getEstradaQueVolta();
		}

		return rota;
	}
	
	public void imprimeCaminho() {
        if (tragetoPercorrido == null) {
            return;
        }

        Double custoTotal = new Double(0);
        Estrada estrada;
        do {
        	estrada = tragetoPercorrido.pop();
            custoTotal += estrada.getCusto();
            
            if(de.equals(estrada.getCidade())){
            	System.err.println("Começamos a viajem em: " + de.getNome());
            } else if (!estrada.getCidade().getNome().equals(para.getNome())){
            	System.err.println("passamos por " + estrada.getCidade().getNome() + ", percorrendo uma distância de " + estrada.getCusto());
        	}else{
        		System.err.println("por fim, chegamos em " + estrada.getCidade().getNome() + ", percorrendo uma distância de " + estrada.getCusto());
            }

        } while (!tragetoPercorrido.isEmpty());
        
        System.out.println("No fim da viajem, vimos que a distância total percorrida foi de " + custoTotal.toString());
    }
	
}
