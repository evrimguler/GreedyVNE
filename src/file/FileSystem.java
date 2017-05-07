package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileSystem {

	private static String fileName;
	private static ArrayList<String> fileRows;

	public FileSystem(String file) {// constructor with filename
		fileName = file;
		fileRows = new ArrayList<String>();
		readFile(fileName);// get all rows from the file
	}

	public void readFile(String fileName) { // read file

		// This will reference one line at a time
		String line = null;

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				fileRows.add(line); // add all lines into the list
			}

			// Always close files
			bufferedReader.close();
		} catch (FileNotFoundException ex) { // error handling
			System.err.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.err.println("Error reading file '" + fileName + "'");
		}

	}

	public void writeFile(String fileName, String temp) {// write into the file

		try {
			// create writer object
			FileWriter fileWriter = new FileWriter(fileName);

			// always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			// append the string into the file
			bufferedWriter.write(temp);

			// Always close files
			bufferedWriter.close();
		} catch (IOException ex) { // error handling
			System.err.println("Error writing to file '" + fileName + "'");
		}
	}

	public int[][] getAdjMatrix() { // get weighted adjacency matrix

		int[][] adjMatrix = findMatrix(fileRows, 0); // run matrix finder

		return adjMatrix;
	}

	public int[][] getBandwidthMatrix() { // get bandwidth matrix

		int[][] linkMatrix = findMatrix(fileRows, 1); // run matrix finder

		return linkMatrix;
	}

	public int[][] getResourceMatrix() { // get resource matrix

		int[][] resourceMatrix = findMatrix(fileRows, 2); // run matrix finder

		return resourceMatrix;
	}

	// find matrices from the list
	public int[][] findMatrix(ArrayList<String> fileRows, int type) {

		int[][] tempMatrix;
		String row[];
		int start;

		switch (type) {
			case 0: { // get weighted adjacency matrix
				tempMatrix = new int[fileRows.size() / 3][fileRows.size() / 3];
				row = new String[fileRows.size() / 3];
				start = type * fileRows.size() / 3;
				break;
			}
			case 1: { // get bandwidth matrix
				tempMatrix = new int[fileRows.size() / 3][fileRows.size() / 3];
				row = new String[fileRows.size() / 3];
				start = type * fileRows.size() / 3 + 1;
				break;
			}
			case 2: { // get resource matrix
				tempMatrix = new int[fileRows.size() / 3][2];
				row = new String[2];
				start = type * fileRows.size() / 3 + 1;
				break;
			}
			default: {
				tempMatrix = null;
				row = null;
				start = 0;
				break;
			}
		}// end-switch

		// travel through rows
		for (int i = start; i < start + fileRows.size() / 3; i++) {
			if (fileRows.get(i).length() != 0) {

				row = fileRows.get(i).split(":"); // find all elements in a row

				for (int j = 0; j < row.length; j++) { // visit row elements
					tempMatrix[i - start][j] = Integer.valueOf(row[j]);

				} // end-for row elements

			}
		} // end-for getting matrix elements

		return tempMatrix; // return the matrix
	}

}