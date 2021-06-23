package Ejercicio4;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Graph <T> {
	private List<Edge<T>> edges;
	private List<Vertex<T>> vertexs;

	public Graph(){
		this.edges = new ArrayList<Edge<T>>();
		this.vertexs = new ArrayList<Vertex<T>>();
	}

	public Graph<T> addNodo(T nodo){
		 this.addNodo(new Vertex<T>(nodo));
		 return this;
	}

	private void addNodo(Vertex<T> nodo){
		this.vertexs.add(nodo);
	}
	
	public Graph<T> connect(T n1,T n2){
		this.connect(getVertex(n1), getVertex(n2), 1.0);
		return this;
	}

	public Graph<T> conectar(T n1,T n2,Number valor){
		this.connect(getVertex(n1), getVertex(n2), valor);
		return this;
	}

	private void connect(Vertex<T> nodo1,Vertex<T> nodo2,Number valor){
		this.edges.add(new Edge<T>(nodo1,nodo2,valor));
	}
	
	public Vertex<T> getVertex(T valor){
		return this.vertexs.get(this.vertexs.indexOf(new Vertex<T>(valor)));
	}

	public List<T> getNeighbourhood(T valor){ 
		Vertex<T> aNode = this.getVertex(valor);
		List<T> output = new ArrayList<T>();
		for(Edge<T> theEdges : this.edges){
			if( theEdges.getOrigin().equals(aNode)){
				output.add(theEdges.getEnd().getValue());
			}
		}
		return output;
	}
	

	private List<Vertex<T>> getNeighbourhood(Vertex<T> unNodo){ 
		List<Vertex<T>> salida = new ArrayList<Vertex<T>>();
		for(Edge<T> theEdges : this.edges){
			if( theEdges.getOrigin().equals(unNodo)){
				salida.add(theEdges.getEnd());
			}
		}
		return salida;
	}
	
	public void printEdges(){
		System.out.println(this.edges.toString());
	}

	public Integer inputDegree(Vertex<T> vertice){
		Integer res =0;
		for(Edge<T> arista : this.edges){
			if(arista.getEnd().equals(vertice)) ++res;
		}
		return res;
	}

	public Integer outpuDegree(Vertex<T> vertice){
		Integer res =0;
		for(Edge<T> arista : this.edges){
			if(arista.getOrigin().equals(vertice)) ++res;
		}
		return res;
	}
	
    protected Edge<T> findEdge(T v1, T v2){
    	return this.findEdge(new Vertex<T>(v1), new Vertex<T>(v2));
    }

    private boolean isAdjacent(Vertex<T> v1,Vertex<T> v2){
    	List<Vertex<T>> ady = this.getNeighbourhood(v1);
        for(Vertex<T> unAdy : ady){
        	if(unAdy.equals(v2)) return true;
        }
        return false;
    }
   
    protected Edge<T> findEdge(Vertex<T> v1, Vertex<T> v2){
    	for(Edge<T> unaArista : this.edges) {
    		
    		if(unaArista.getOrigin().equals(v1) && unaArista.getEnd().equals(v2)) return unaArista;
    	}
    	return null;
    }

    
    public List<Vertex<T>> alcanzarNodos(Vertex<T> nodo, int transiciones){
		List<Vertex<T>> nodos= new ArrayList<Vertex<T>>();
		List<Vertex<T>> nodosAlcanzados=new ArrayList<Vertex<T>>();
		List<Vertex<T>> caminosPosibles=new ArrayList<Vertex<T>>();
		
		nodos=this.vertexs;
		
		for (int i = 0; i < nodos.size(); i++) {
			caminosPosibles=paths(nodo, nodos.get(i));
			
				if (caminosPosibles.size()!=0) {
					
					for(int j=0; j<caminosPosibles.size();j++) {
						
						if(caminosPosibles.get(j).size()<transiciones) {
							nodosAlcanzados.add(caminosPosibles.get(j));
						}
					}
				}
		}
		return nodosAlcanzados;
		
	}
	
	public List<List<Vertex<T>>> paths(Vertex<T> v1,Vertex<T> v2){
    	List<List<Vertex<T>>>salida = new ArrayList<List<Vertex<T>>>();
     	List<Vertex<T>> visitados= new ArrayList<Vertex<T>>();
    	visitados.add(v1);
    	findPathAux(v1, v2, visitados, salida);
    	return salida;
    }

    private void findPathAux(Vertex<T> v1,Vertex<T> v2, List<Vertex<T>> visitados, List<List<Vertex<T>>> todosLosCaminos) {
    	List<Vertex<T>> adyacentes = this.getNeighbourhood(v1);
    	List<Vertex<T>> copiaVisitados = null;
    	
    	for(Vertex<T> ad: adyacentes) {
    		copiaVisitados=visitados.stream().collect(Collectors.toList());
			copiaVisitados.add(ad);
    		if (ad.equals(v2)) {
    			todosLosCaminos.add(new ArrayList<Vertex<T>>(copiaVisitados));
    		}else {
    			if(!visitados.contains(ad)) {
    				this.findPathAux(ad, v2, copiaVisitados, todosLosCaminos);
    			}
    		}
    	}
    }
    

}