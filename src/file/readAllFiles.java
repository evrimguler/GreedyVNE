package file;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class readAllFiles {

	// get all substrate files
	public static List<String> substrateFileName() {
		String target_dir = "./";
		File dir = new File(target_dir);
		File[] files = dir.listFiles();

		List<String> fileNames = new ArrayList<String>();

		for (File f : files) { // file checking

			if (f.isFile() && f.getName().contains("substrate")) {
				fileNames.add(f.getName()); // add a file into the list
			}
		} // end-for all files

		return fileNames;
	}
	
	// get all virtual files
	public static List<String> virtualFileName() {
		String target_dir = "./";
		File dir = new File(target_dir);
		File[] files = dir.listFiles();

		List<String> fileNames = new ArrayList<String>();

		for (File f : files) { // file checking

			if (f.isFile() && f.getName().contains("virtual")) {
				fileNames.add(f.getName()); // add a file into the list
			}
		} // end-for all files

		return fileNames;
	}
}