package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	GenesDao dao;
	Graph<Genes,DefaultWeightedEdge> grafo;
	Map<String,Genes> idMap;
	
	public Model(){
		this.dao=new GenesDao();
		this.idMap=new HashMap<String,Genes>();
		this.dao.getAllGenes(idMap);
	}
	
	public void creaGrafo() {
		
		grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.dao.getVertici(idMap));
		
		for(Adiacenza a : this.dao.getArchi(idMap)) {
			if(this.grafo.getEdge(a.getG1(), a.getG2())==null) {
				if(a.getG1().getChromosome()==a.getG2().getChromosome()) {
					Graphs.addEdgeWithVertices(this.grafo, a.getG1(), a.getG2(), a.getPeso()*2);
				} else {
					Graphs.addEdgeWithVertices(this.grafo, a.getG1(), a.getG2(), a.getPeso());
				}
			}
		}
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Genes> getVertici(){
		return this.dao.getVertici(idMap);
	}
	
	public List<Adiacenza> getGeniAdiacent(Genes g) {
		List<Genes> vicini = Graphs.neighborListOf(this.grafo, g);
		
		List<Adiacenza> result = new ArrayList<>();
		
		for (Genes v : vicini) {
			result.add(new Adiacenza(g,v, this.grafo.getEdgeWeight(this.grafo.getEdge(g, v))));
		}
		Collections.sort(result);
		return result;

	}
}
