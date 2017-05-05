
import java.util.ArrayList;
import java.util.List;

public class CandidateSet {

	public List<Vertex> getPossibleNodes(List<Vertex> possibleCandidateVertices, Vertex virtualNode) {

		List<Vertex> candidateList = new ArrayList<Vertex>();

		int maxBandwidth = Integer.MIN_VALUE;
		boolean satisfiedLink;

		for (Edge e : virtualNode.adjacencies) {
			if (e.bandWidth >= maxBandwidth) {
				maxBandwidth = e.bandWidth;
			}
		}

		for (int i = 0; i < possibleCandidateVertices.size(); i++) {

			Vertex currentSubstrateNode = possibleCandidateVertices.get(i);
			satisfiedLink = false;

			for (Edge edge : currentSubstrateNode.adjacencies) {
				if (edge.bandWidth >= maxBandwidth) {
					satisfiedLink = true;
				}
			} // end-for currentSubstrateNode Connections

			if (satisfiedLink) {
				candidateList.add(currentSubstrateNode);
			}
		} // end-for possibleCandidateVertices

		return candidateList;

	}

	public List<Vertex> getCandidateList(List<Vertex> substrateVertices, Vertex virtualNode) {
		List<Vertex> candidateList = new ArrayList<Vertex>();

		for (Vertex vertex : substrateVertices) {

			if (vertex.CPU >= virtualNode.CPU) {
				candidateList.add(vertex);
			}

		} // end-for substrateVertices

		candidateList = getPossibleNodes(candidateList, virtualNode);

		return candidateList;

	}

}
