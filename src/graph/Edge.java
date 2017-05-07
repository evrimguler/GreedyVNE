package graph;

public class Edge {
	private final Vertex start;
	private final Vertex target;
	private final int weight;
	private int bandWidth;

	// Generate the edge with start and end points
	public Edge(Vertex startVertex, Vertex endVertex, int edgeWeight) {
		this.start = startVertex;	// assign start vertex
		this.target = endVertex;	// assign end vertex
		this.weight = edgeWeight;	// assign edge weight
		this.bandWidth = 0;			// initialize bandwidth capacity/demand
	}
	
	// get the name of target vertex
	public String toString(){
		return "-->"+target.toString();
	}

	// set the bandwidth capacity/demand of the edge
	public void setBandwidth(int bw) {
		bandWidth = bw;
	}
	
	// get the bandwidth capacity/demand of the edge
	public int getBandwidth(){
		return this.bandWidth;
	}
	
	// get start vertex of the edge
	public Vertex getStartVertex(){
		return this.start;
	}
	
	// get target vertex of the edge
	public Vertex getTargetVertex(){
		return this.target;
	}
	
	// get the edge weight
	public int getEdgeWeight(){
		return this.weight;
	}

}