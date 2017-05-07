package algorithm;

import java.util.ArrayList;
import java.util.List;

// include graph classes
import graph.Edge;
import graph.Vertex;

public class CandidateSet {
	
	// get substrate candidate vertices for virtualNode
	public List<Vertex> getCandidateList(List<Vertex> substrateVertices, Vertex virtualNode) {

		List<Vertex> candidateList = new ArrayList<Vertex>();

		// check the computing capacity of each substrate node with virtualNode demand
		candidateList = checkComputingResource(substrateVertices, virtualNode);

		// check the substrate node connections with bandwidth demand
		candidateList = checkVertexConnection(candidateList, virtualNode);

		return candidateList;
	}

	// check the computing resource of each substrate node with computing demand of virtualNode
	private List<Vertex> checkComputingResource(List<Vertex> substrateVertices, Vertex virtualNode){
		
		List<Vertex> candidateList = new ArrayList<Vertex>();
		
		// search through all substrate nodes
		for (Vertex vertex : substrateVertices) {
			
			// computing capacity needs to be more than computing demand
			if (vertex.getCPU() >= virtualNode.getCPU()) {
				candidateList.add(vertex);
			}
		}
		
		return candidateList;
	}
	
	// check the connected edges of each substrate candidate nodes with bandwidth demand of virtualNode
	private List<Vertex> checkVertexConnection(List<Vertex> substrateCandidates, Vertex virtualNode) {

		List<Vertex> candidateList = new ArrayList<Vertex>();

		// check connected edges for each substrate candidates
		for (int i = 0; i < substrateCandidates.size(); i++) {

			Vertex currentCandidate = substrateCandidates.get(i);

			// control availability of currentCandidate connections for bandwidth demand of virtualNode  
			boolean isSatisfied = checkBandwidthCapacity(currentCandidate, virtualNode);

			// add satisfied currentNode into the substrate candidate list
			if (isSatisfied) {
				candidateList.add(currentCandidate);
			}
		}

		return candidateList;
	}

	// check bandwidth capacity of connected edges
	private boolean checkBandwidthCapacity(Vertex currentCandidate, Vertex virtualNode) {

		int virtualLinkBandwidthDemand = virtualNode.adjacencies.get(0).getBandwidth();

		int totalNumberofSatisfiedLink = 0;

		// check all connected edge(s) with currentCandidate
		for (Edge connectedSubstrateEdge : currentCandidate.adjacencies) {

			// define how much connected edge satisfies the demand
			if (connectedSubstrateEdge.getBandwidth() >= virtualLinkBandwidthDemand) {
				totalNumberofSatisfiedLink += connectedSubstrateEdge.getBandwidth() / virtualLinkBandwidthDemand;
			}

		} // end-for connected edges with currentCandidate

		// return the result
		if (totalNumberofSatisfiedLink >= virtualNode.adjacencies.size()) {
			return true;
		}

		return false;
	}

}
