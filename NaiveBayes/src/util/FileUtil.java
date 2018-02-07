package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename = "sample.txt";
		int linkIndex = -1;
		int linkEnd = -1;
		FileUtil fileUtil = new FileUtil();
		ArrayList<String> strings = fileUtil.readFileAfterPTag(filename);
		String htmlString = fileUtil.concatArrayList(strings).trim().replaceAll(" +", " ");
		linkIndex = htmlString.indexOf("href=");
		while(linkIndex > -1) {
			linkEnd = htmlString.indexOf("\"", linkIndex + 6);
			String link = htmlString.substring(linkIndex + 6, linkEnd);
			if(link.startsWith("/wiki/") && !link.contains("#") && !link.contains(":")) System.out.println(htmlString.substring(linkIndex + 6, linkEnd));
			linkIndex = htmlString.indexOf("href=", linkEnd);
		}
	}

	public ArrayList<String> readFileAfterPTag(String fileLocation) {

		ArrayList<String> fileLines = new ArrayList<String>();
		Boolean pTagCheck = false;
		try (BufferedReader br = Files.newBufferedReader(Paths.get(fileLocation))) {
			String line;
			while ((line = br.readLine()) != null) {
				//System.out.println(line + " " + line.contains("<p>"));
				if(line.contains("<p>") || line.contains("<P>")) {
					pTagCheck = true; 
					fileLines.add(line); 
				}
				if(pTagCheck) {
					while((line = br.readLine()) != null) { 
						fileLines.add(line);
					}
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return fileLines;
	}




	public ArrayList<String> readFile(String fileLocation) {

		ArrayList<String> fileLines = new ArrayList<String>();
		try (BufferedReader br = Files.newBufferedReader(Paths.get(fileLocation))) {
			String line;
			while((line = br.readLine()) != null) { 
				fileLines.add(line.trim());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return fileLines;
	}


	public String concatArrayList(ArrayList<String> htmlLines) {
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < htmlLines.size(); i++) {
			strBuilder.append(htmlLines.get(i));
		}
		return strBuilder.toString();
	}

	public void writeToFile(ArrayList<String> lines, String fileName) throws IOException {
		FileWriter writer = new FileWriter(fileName); 
		for(String str: lines) {
			writer.write(str + System.lineSeparator());
		}
		writer.flush();
		writer.close();
	}

	public void writeToFile(ArrayList<String> lines, String fileName, boolean append) throws IOException {
		FileWriter writer = new FileWriter(fileName, append); 
		for(String str: lines) {
			writer.write(str + System.lineSeparator());
		}
		writer.flush();
		writer.close();
	}

	public void writeToFile(String line, String fileName, boolean append) throws IOException {
		FileWriter writer = new FileWriter(fileName, append); 	
		writer.write(line + System.lineSeparator());
		writer.flush();
		writer.close();
	}

}
