package graph;

import java.util.ArrayList;
import java.util.List;

// include FileSystem class into this class
import file.FileSystem;

public class GenerateVertex {

	private String fileName;

	// generate constructor with filename of topology
	public GenerateVertex(String file) {
		this.fileName = file; // set the fileName of the topology
		
	}

	// create all vertices in a topology
	public List<Vertex> getVertices() {
		
		// Define the topology file
		FileSystem file = new FileSystem(fileName);
		int[][] adjMatrix = file.getAdjMatrix(); // get weighted adjacency matrix
		int[][] adjBWMatrix = file.getBandwidthMatrix(); // get bandwidth matrix
		int[][] resourceMatrix = file.getResourceMatrix(); // get resource matrix

		List<Vertex> vertices = new ArrayList<Vertex>();
		
		for (int i = 0; i < adjMatrix.length; i++) {
			
			Vertex currentVertex = new Vertex(i); // create temporary vertex
			
			// set resource capacity/demand of a vertex
			currentVertex.setCPU(resourceMatrix[i][0]);
			currentVertex.setRAM(resourceMatrix[i][1]);
			vertices.add(currentVertex);
		} // end-for vertex generation
		
		for (int i = 0; i < adjMatrix.length; i++) {
			// define connected edges with the currentVertex
			ArrayList<Edge> connectedEdgeList = new ArrayList<Edge>();
			
			for (int j = 0; j < adjMatrix[i].length; j++) {

				// check the neighbors of any node[i]
				if (adjMatrix[i][j] != 0) {

					// create an edge between currentVertex and currentNeighbor with link weight
					Edge tempEdge = new Edge(vertices.get(i), vertices.get(j), adjMatrix[i][j]);
					tempEdge.setBandwidth(adjBWMatrix[i][j]); // set link bandwidth capacity/demand
					connectedEdgeList.add(tempEdge); // add the edge into the adjacency list
				}
			} // end-for adjMatrix[i]

			// add connected edges into the adjacent vertex list of the currentVertex
			vertices.get(i).adjacencies = connectedEdgeList;
			
		} // end-for connected edge generation

		return vertices; // return the list
	}

}