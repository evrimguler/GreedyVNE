
import java.util.List;

public class ClosenessCentrality {

	private String substrateGraphFile;

	public ClosenessCentrality(String fileName) {
		this.substrateGraphFile = fileName;
	}

	public Vertex centrality(List<Vertex> substrateVertices, List<Vertex> candidatesOfCurrentNode,
			List<Vertex> candidatesOfNeighbors, int BW) {

		double totalDistance;
		if (!candidatesOfCurrentNode.isEmpty() && !candidatesOfNeighbors.isEmpty()) {

			Vertex savedVertex = candidatesOfCurrentNode.get(0);
			double minCentrality = Double.POSITIVE_INFINITY;
			int counter = 0;

			for (int i = 0; i < candidatesOfCurrentNode.size(); i++) {
				totalDistance = 0;
				counter = 0;
				ShortestPath path = new ShortestPath(this.substrateGraphFile);
				for (int j = 0; j < candidatesOfNeighbors.size(); j++) {
					double distance = 0;
					if (candidatesOfCurrentNode.get(i).name != candidatesOfNeighbors.get(j).name) {
						List<Vertex> temp = path.defineShortestPath(substrateVertices,
								candidatesOfCurrentNode.get(i).name, candidatesOfNeighbors.get(j).name, BW);
						distance += path.totalDistance(temp);
						if (distance != 0) {
							totalDistance += distance;
							counter++;
						}

					}
				} // end-for candidatesOfNeighbors

				if (counter != 0) {

					double centrality = (double) totalDistance / counter;

					if (centrality < minCentrality) {
						savedVertex = candidatesOfCurrentNode.get(i);
						minCentrality = centrality;
					} else if (centrality == minCentrality && candidatesOfCurrentNode.get(i).CPU > savedVertex.CPU) {
						savedVertex = candidatesOfCurrentNode.get(i);
						minCentrality = centrality;
					} else if (centrality == minCentrality && candidatesOfCurrentNode.get(i).CPU == savedVertex.CPU
							&& findMaxBandwidth(candidatesOfCurrentNode.get(i)) > findMaxBandwidth(savedVertex)) {
						savedVertex = candidatesOfCurrentNode.get(i);
						minCentrality = centrality;
					}
				}
			} // end-for candidatesOfCurrentNode

			return savedVertex;

		} else {
			return null;
		}
	}

	private int findMaxBandwidth(Vertex vertex) {

		int max = Integer.MIN_VALUE;
		
		for (Edge connectedEdge : vertex.adjacencies) {

			if (connectedEdge.bandWidth >= max) {

				max = connectedEdge.bandWidth;
			}

		} //end-for edges

		return max;
	}
}
