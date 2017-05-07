package algorithm;

// include graph classes
import graph.Edge;
import graph.Vertex;
import graph.GenerateVertex;

import java.util.List;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;

public class ShortestPath {

	public Vertex source;
	public Vertex destination;
	private List<Vertex> vertices;
	private String fileName;
	private int totalDistance;

	// constructor without any file
	public ShortestPath() { // initialize all variables
		source = null;
		destination = null;
		totalDistance = 0;

	}

	// constructor with initialized vertices
	public ShortestPath(String file) { // initialize all variables
		source = null;
		destination = null;
		fileName = file;
		totalDistance = 0;
	}

	// compute all paths from source to all other nodes
	public void computePaths(Vertex sourceNode, int bandwidth) {

		sourceNode.setMinDistance(0); // initialize minimum distance
		
		// create a queue for measuring distance
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(sourceNode);

		while (!vertexQueue.isEmpty()) {// check all connections	
			
			// get the first element from the queue
			Vertex currentNode = vertexQueue.poll();
			
			// check all connected edges with currentNode
			for (int i = 0; i < currentNode.adjacencies.size(); i++) {
				
				Edge connectedEdge = currentNode.adjacencies.get(i);

				if (connectedEdge.getBandwidth() >= bandwidth) { // check bandwidth capacity

					Vertex targetNode = connectedEdge.getTargetVertex(); // get adjacent node of currentNode

					int edgeWeight = connectedEdge.getEdgeWeight(); // get the weight of edge

					int weightToCurrentNode = currentNode.getMinDistance() + edgeWeight; // total weight through the neighbor

					// check the minimum distance from source to the neighbor of the currentNode
					if (weightToCurrentNode < targetNode.getMinDistance()) {
						vertexQueue.remove(targetNode); // remove current version
						
						// update minimum distance and previous node of the target node
						targetNode.setMinDistance(weightToCurrentNode);
						targetNode.setPreviousVertex(currentNode);

						vertexQueue.add(targetNode); // add updated node into the list
					}
				}

			}

		}

		vertexQueue.clear();
	}

	// define the path from source to destination
	public List<Vertex> getShortestPathTo(Vertex destinationNode) {

		List<Vertex> path = new ArrayList<Vertex>();
		for (Vertex vertex = destinationNode; vertex != null; vertex = vertex.getPreviousVertex()) {
			path.add(vertex);
		} // end-for path list

		Collections.reverse(path); // reverse the nodes in the list
		return path;
	}

	// find the shortest path from source to destination
	public List<Vertex> defineShortestPath(int sourceID, int destionationID, int bandWidth) {

		// get all substrate vertices from the file
		GenerateVertex vertexObject = new GenerateVertex(this.fileName);
		vertices = vertexObject.getVertices();
		Vertex source = getVertex(vertices, sourceID); // get source vertex
		Vertex destination = getVertex(vertices, destionationID); // get destination vertex

		// Computation for the shortest path from source to destination
		computePaths(source, bandWidth); // run Dijkstra Shortest path

		List<Vertex> path = getShortestPathTo(destination); // get all nodes in the path

		this.totalDistance = destination.getMinDistance(); // get total weight of the path

		if (path.size() > 1) {
			return path;
		} else {
			path.clear();
			return path;
		}
	}

	// find the vertex with its ID
	private Vertex getVertex(List<Vertex> vertices, int vertexName) {

		// find the vertex with vertexName
		for (Vertex vertex : vertices) {
			if (vertex.name==vertexName) {
				return vertex; // return vertex
			}
		}
		return null;
	}

	public int getTotalDistance() {
		// return total weight of the path
		return this.totalDistance;
	}

}