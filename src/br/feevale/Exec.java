package br.feevale;
public class Exec {

	public static void main(String[] args) {
        Mapa mapa = new Mapa("./mapa.json");
        mapa.tracarRota("Arad","Bucharest");
    }
    
}
