package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import algorithm.MapGreedy;
import file.readAllFiles;
import graph.Edge;
import graph.GenerateVertex;
import graph.Vertex;

public class Test {

	public static void main(String[] args) throws IOException {

		// read all file names of substrate and virtual topologies
		List<String> substrateFile = readAllFiles.substrateFileName();
		List<String> virtualFile = readAllFiles.virtualFileName();

		// wrap FileWriter in BufferedWriter to get the results
		FileWriter fileWriter = new FileWriter("SimulationResults.txt");
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

		System.out.println("Greedy VMTE with duplication:");
		for (int j = 0; j < virtualFile.size(); j++) {

			// initialize result variables
			double greedyAvgHops = 0;
			double greedyAvgCost = 0;
			double greedyDuplication = 0;
			int counterGreedy = 0;
			int rejectGreedy = 0;

			// get number of node,link and bandwidth
			String temp[] = virtualFile.get(j).split("_");

			for (int i = 0; i < substrateFile.size(); i++) { // run substrate

				// get all substrate vertices
				List<Vertex> substrate = new ArrayList<Vertex>();
				GenerateVertex gVertex = new GenerateVertex(substrateFile.get(i));
				substrate = gVertex.getVertices();

				showVertices(substrate); // show all vertices

				// show unsorted substrate vertices
				System.out.println(Arrays.toString(substrate.toArray()));

				Collections.sort(substrate); // sort substrate nodes

				// show sorted substrate vertices
				System.out.println(Arrays.toString(substrate.toArray()));

				// get all virtual vertices
				List<Vertex> virtual = new ArrayList<Vertex>();
				GenerateVertex gVertexV = new GenerateVertex(virtualFile.get(j));
				virtual = gVertexV.getVertices();

				showVertices(virtual); // show all vertices

				// show unsorted virtual vertices
				System.out.println(Arrays.toString(virtual.toArray()));

				Collections.sort(virtual); // sort virtual nodes

				// show sorted virtual vertices
				System.out.println(Arrays.toString(virtual.toArray()));

				// create Greedy Mapping object
				MapGreedy mapGreedy = new MapGreedy(substrateFile.get(i));

				// check the mapping process
				if (mapGreedy.mapping(substrate, virtual)) {

					// get the bandwidth demand
					int bandwidth = virtual.get(0).adjacencies.get(0).getBandwidth();

					// assign all cost values
					greedyAvgCost += mapGreedy.getTotalDistance() * bandwidth;
					greedyAvgHops += mapGreedy.getTotalDistance();
					greedyDuplication += mapGreedy.getTotalDuplication();
					counterGreedy++; // accepted number of virtual request
				} // end-if mapping greedy
				else {

					System.out.println("There is no possible option to map virtual topology.");
					rejectGreedy++; // rejected number of virtual request
				} // end-else mapping greedy

				System.out.println("=============================================");

			} // end-for substrate files

			greedyAvgCost = (int) (greedyAvgCost / counterGreedy);
			greedyAvgHops = (int) (greedyAvgHops / counterGreedy);
			greedyDuplication = (int) (greedyDuplication / counterGreedy);

			System.out.println(greedyAvgHops + "\t" + greedyAvgCost + "\t" + greedyDuplication + "\t" + counterGreedy
					+ "\t" + rejectGreedy + "\t" + temp[1] + "\t" + temp[2] + "\t" + temp[3]);

			System.out.println();

			// Note that write() does not automatically append a newline
			// character.
			String row = greedyAvgHops + "\t" + greedyAvgCost + "\t" + greedyDuplication + "\t" + counterGreedy + "\t"
					+ rejectGreedy + "\t" + temp[1] + "\t" + temp[2] + "\t" + temp[3] + "\n";
			bufferedWriter.write(row);

		} // end virtual files for-loop

		// Always close files.
		bufferedWriter.close();
	}

	// show all vertices in a topology with their connections
	public static void showVertices(List<Vertex> vertexList) {

		for (Vertex currentVertex : vertexList) {
			System.out.println("Node[" + currentVertex + "]: Cpu[" + currentVertex.getCPU() + "]");
			for (Edge connectedEdge : currentVertex.adjacencies) {
				System.out.println("Edge:[" + connectedEdge.getStartVertex() + "]--[" + connectedEdge.getTargetVertex()
						+ "]: BW[" + connectedEdge.getBandwidth() + "], Cost[" + connectedEdge.getEdgeWeight() + "]");
			} // end-for connectedEdge
			System.out.println("---------------------------------------------");
		} // end-for vertexList
		System.out.println("");

	}

}
