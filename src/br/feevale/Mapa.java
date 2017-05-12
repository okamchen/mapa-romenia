package br.feevale;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Mapa {
	
    private List<Cidade> cidades = new ArrayList<>();
    private List<RotaTracada> rotasTracadas;
    
    public Mapa(){}
    
    public Mapa(String diretorioMapa){
    	carregarMapa(diretorioMapa);
    }


    private void carregarMapa(String diretorioMapa) {
        try {
            JSONParser parser = new JSONParser(); 
            Object obj = parser.parse(new FileReader(diretorioMapa));

            JSONObject jsonObject =  (JSONObject) obj;
            JSONArray cidadesJson = (JSONArray) jsonObject.get("cidades");
            
            carregarCidades(cidadesJson);
            carregarVizinhos(cidadesJson);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	private void carregarVizinhos(JSONArray cidadesJson) {
		Iterator<JSONObject> iteratorCidades = cidadesJson.iterator();
		while (iteratorCidades.hasNext()) {
			
			JSONObject cidadeObj = iteratorCidades.next();
			
			JSONArray vizinhosJson = (JSONArray) cidadeObj.get("vizinhos");
			Iterator<JSONObject> iteratorVizinhos = vizinhosJson.iterator();
			
			while (iteratorVizinhos.hasNext()) {
				
				JSONObject vizinhoObj = iteratorVizinhos.next();
				
				Vizinho vizinho = new Vizinho();
				vizinho.setDistancia(Integer.valueOf(vizinhoObj.get("distancia").toString()));
				vizinho.setCidade(getCidadePorNome(vizinhoObj.get("nome").toString()));
				
				Cidade cidade = getCidadePorNome(cidadeObj.get("cidade").toString());
				cidade.addVizinho(vizinho);
			}
			
		}
	}

	private void carregarCidades(JSONArray cidadesJson) {
		Iterator<JSONObject> iteratorCidades = cidadesJson.iterator();
		while (iteratorCidades.hasNext()) {
			JSONObject cidadeObj = iteratorCidades.next();
			
			Cidade novaCidade = new Cidade();
			novaCidade.setLatitude(Double.valueOf(cidadeObj.get("latitude").toString()));
			novaCidade.setLongetude(Double.valueOf(cidadeObj.get("longitude").toString()));
			novaCidade.setNome(cidadeObj.get("cidade").toString());
			
			this.cidades.add(novaCidade);
			
		}
	}

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Cidade cidade : cidades) {
            out.append("=>Cidade: ");
            out.append(cidade.getNome());
            out.append("\n");
            for(Vizinho vizinho:cidade.getVizinhos()){
                out.append("Vizinho: ");
                out.append(vizinho.getCidade().getNome());
                out.append(" Distancia: ");
                out.append(vizinho.getDistancia());
                out.append("\n");
            }
        }
        return out.toString();
    }

    private Cidade getCidadePorNome(String nome) {
        for (Cidade c : cidades) {
            if (c.getNome().equals(nome)) {
                return c;
            }
        }
        return null;
    }

	public void tracarRota(String de, String para) {
		
		Cidade cidadeOrigem = getCidadePorNome(de);
		Cidade cidadeDestino = getCidadePorNome(para);
		
		RotaTracada rota = new RotaTracada(cidadeOrigem, cidadeDestino);
		rota.imprimeCaminho();
	}

	public List<RotaTracada> getRotasTracadas() {
		if(rotasTracadas == null){
			rotasTracadas = new ArrayList<>();
		}
		return rotasTracadas;
	}
    
}