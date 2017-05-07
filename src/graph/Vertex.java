package graph;

import java.util.ArrayList;

public class Vertex implements Comparable<Vertex> {

	// define vertex variables
	public final int name;
	public ArrayList<Edge> adjacencies;
	private int minDistance = Integer.MAX_VALUE;
	private Vertex previous;
	private int CPU;
	private int RAM;

	// create Vertex constructor
	public Vertex(int vertexName) {
		this.name = vertexName;
		this.CPU = 0;
		this.RAM = 0;
	}

	// get the name of current vertex
	public String toString() {
		return String.valueOf(this.name);
	}

	// set previous connected vertex of the current vertex
	public void setPreviousVertex(Vertex previousVertex) {
		this.previous = previousVertex;
	}

	// get previous connected vertex of the current vertex
	public Vertex getPreviousVertex() {
		return this.previous;
	}

	// set the value of minimum distance
	public void setMinDistance(int distance) {
		this.minDistance = distance;
	}

	// get the value of minimum distance
	public int getMinDistance() {
		return this.minDistance;
	}

	// set CPU capacity/demand
	public void setCPU(int cpu) {
		this.CPU = cpu;
	}

	// get the value of CPU capacity/demand
	public int getCPU() {
		return this.CPU;
	}

	// set RAM capacity/demand
	public void setRAM(int ram) {
		this.RAM = ram;
	}

	// get the value of RAM capacity/demand
	public int getRAM() {
		return this.RAM;
	}

	// Compare vertices with their computing and bandwidth capacity/demand and
	// degree
	@Override
	public int compareTo(Vertex nextNode) {

		int CPU = Integer.compare(nextNode.getCPU(), getCPU()); // compare CPU
		if (CPU != 0) {
			return CPU;
		}
		
		// compare degree of both vertices
		int degree = Integer.compare(nextNode.adjacencies.size(), this.adjacencies.size());

		return degree;
	}
}