package algorithm;

// Define Greedy Node Mapping with the explanations 
// in Ayoubi 2015 Paper(MINTED) and Viet ICCSA 2013
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import graph.Edge;
import graph.Vertex;

public class MapGreedy {

	private String substrate;

	private static int totalPathDistance = 0;
	private static int totalDuplication = 0;

	public MapGreedy(String sFile) {
		this.substrate = sFile;
	}

	// mapping process of virtual nodes and links
	public boolean mapping(List<Vertex> substrateVertices, List<Vertex> virtualVertices) {
		// initialize mapped node list
		Vertex[] mappedNodes = new Vertex[substrateVertices.size()];

		// run node mapping
		mappedNodes = nodeMapping(substrateVertices, virtualVertices, mappedNodes);

		if (mappedNodes == null) { // check the process of node mapping
			return false;
		}

		// show the process of node mapping
		System.out.println("Mapped Nodes:" + Arrays.toString(mappedNodes));

		// run link mapping
		List<Vertex> totalPath = linkMapping(substrateVertices, virtualVertices, mappedNodes);

		if (totalPath.isEmpty()) { // check the process of link mapping
			return false;
		}

		// show the process of link mapping
		System.out.println("Total path:" + Arrays.toString(totalPath.toArray()));

		// get the number of link duplication
		totalDuplication = findTotalDuplication(totalPath);

		// show totalDuplication
		System.out.println("Total number of link duplication:\t" + totalDuplication);

		return true;

	}

	private Vertex[] nodeMapping(List<Vertex> substrateVertices, List<Vertex> virtualVertices, Vertex[] mappedNodes) {

		// create new substrate list
		List<Vertex> substrate = new ArrayList<Vertex>();
		for (int i = 0; i < substrateVertices.size(); i++) {
			substrate.add(substrateVertices.get(i));
		} // end-for substrateVertices

		// create new virtual list
		List<Vertex> virtual = new ArrayList<Vertex>();
		for (int i = 0; i < virtualVertices.size(); i++) {
			virtual.add(virtualVertices.get(i));
		} // end-for virtualVertices

		Collections.sort(substrate); // sort all substrate nodes
		Collections.sort(virtual); // sort all virtual nodes

		while (virtual.size() != 0) {

			Vertex currentVirtualNode = virtual.get(0); // get virtual node
			CandidateSet cSetObject = new CandidateSet(); // create the object

			// get all substrate candidate nodes for currentVirtualNode
			List<Vertex> candidateNodes = cSetObject.getCandidateList(substrate, currentVirtualNode);

			// assign the best substrate
			Vertex substrateCandidate = findBestNode(candidateNodes, mappedNodes);

			if (currentVirtualNode != null) {
				// map virtual node onto substrate node
				mappedNodes[substrateCandidate.name] = currentVirtualNode;
			} else {
				System.out.println("There is no available node to map virtual node[Greedy] " + currentVirtualNode);
				return null;
			}

			virtual.remove(0);
		}

		return mappedNodes;
	}

	private List<Vertex> linkMapping(List<Vertex> substrate, List<Vertex> virtual, Vertex[] mapped) {

		// define path matrix to hold all mapped links
		boolean[][] pathMatrix = new boolean[virtual.size()][virtual.size()];
		List<Vertex> totalPath = new ArrayList<Vertex>();

		// set index numbers of source and destination nodes
		int sourceNode;
		int destinationNode;

		for (int i = 0; i < virtual.size(); i++) { // check all virtual nodes

			// hold vertices in a path
			List<Vertex> path = new ArrayList<Vertex>();

			Vertex virtualNode = virtual.get(i); // get the virtual node

			// find index number of the mapped substrate node
			sourceNode = findSubtrateIndex(virtualNode, mapped);

			// check all connected edges to be mapped
			for (Edge connectedEdge : virtualNode.adjacencies) {

				// create shortest path object
				ShortestPath sPath = new ShortestPath(this.substrate);

				// find index number of the mapped substrate node
				destinationNode = findSubtrateIndex(connectedEdge.getTargetVertex(), mapped);

				// check mapped virtual links
				if (pathMatrix[virtualNode.name][connectedEdge.getTargetVertex().name] == false) {

					// get the shortest path from source to destination
					path = sPath.defineShortestPath(sourceNode, destinationNode, connectedEdge.getBandwidth());

					if (path.isEmpty()) { // no path is found
						return path;
					}

					// update mapped virtual links
					pathMatrix[virtualNode.name][connectedEdge.getTargetVertex().name] = true;
					pathMatrix[connectedEdge.getTargetVertex().name][virtualNode.name] = true;

					// get the total weight of the path and add vertices in the
					// path
					totalPathDistance += sPath.getTotalDistance();
					totalPath.addAll(path);
					totalPath.add(null);
				} // end-if

			} // end-for connectedEdges

		} // end-for virtual nodes

		return totalPath;
	}

	private int findTotalDuplication(List<Vertex> totalPath) {

		int counter = 0; // initialize the duplication counter

		List<String> link = new ArrayList<String>();

		for (int i = 0; i < totalPath.size() - 1; i++) {
			if (totalPath.get(i) == null) {
				continue;
			}

			// create all edges in the total path of link mapping
			if (totalPath.get(i + 1) != null) {
				String temp = totalPath.get(i) + "-" + totalPath.get(i + 1);
				link.add(temp);
			}

		} // end-for totalPath

		// check all edges to find the duplications
		for (int i = 0; i < link.size() - 1; i++) {

			// get the link
			String first[] = link.get(i).split("-");

			for (int j = i + 1; j < link.size(); j++) {

				// find all other links
				String second[] = link.get(j).split("-");

				if (first[0].equalsIgnoreCase(second[0]) && first[1].equalsIgnoreCase(second[1])) {
					counter++;
					link.remove(j);
					j--;
				} // end-if for same link

				if (first[0].equalsIgnoreCase(second[1]) && first[1].equalsIgnoreCase(second[0])) {
					counter++;
					link.remove(j);
					j--;
				} // end-if for reverse link

			} // end-for link j

		} // end-for link i

		return counter;
	}

	// find the index number of substrate node
	private int findSubtrateIndex(Vertex virtual, Vertex[] mapped) {

		int savedIndex = 0; // initialize the value

		for (int i = 0; i < mapped.length; i++) {
			if (mapped[i] != null) {

				// check the substrate node mapped by virtual node
				if (mapped[i].name == virtual.name) {
					savedIndex = i; // save index value
				}

			}
		}

		return savedIndex;
	}

	// find the best node in candidate set
	private Vertex findBestNode(List<Vertex> candidateNodes, Vertex[] mapped) {

		// get the unmapped substrate candidate
		for (int i = 0; i < candidateNodes.size(); i++) {

			// check the candidate node is not mapped
			if (mapped[candidateNodes.get(i).name] == null) {
				return candidateNodes.get(i);
			}

		}

		return null;
	}

	// return total weight of link mapping
	public int getTotalDistance() {
		return totalPathDistance;

	}

	// return total number of link duplication
	public int getTotalDuplication() {
		return totalDuplication;
	}

}
