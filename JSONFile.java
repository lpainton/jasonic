package jasonic;
import jasonic.JSONParser.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
	
public class JSONFile
{
	public static String FILENAME = "testfile";
	
	public static void main(String[] args)
	{
		try 
		{
			JSONFile jf = new JSONFile(FILENAME);
		} catch (JSONParseException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	String filePath;
	List<JSONObject> content;
	
	public JSONFile(String path) throws FileNotFoundException, JSONParseException
	{
		content = new ArrayList<JSONObject>();
		filePath = path;
		loadFromFile();
	}
	
	private void loadFromFile() throws JSONParseException
	{
		Scanner br = null;
		JSONParser parser = new JSONParser();
		try 
		{
			br = new Scanner(new File(filePath));
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
		
		StringBuilder sb = new StringBuilder();
		while(br.hasNextLine())
		{
			String line = br.nextLine();
			parser.debugPrintLn(line);
			sb.append(line);
		}
		parser.debugPrintLn("File loaded.  Attempting parse.");
		parser.setDebugMode(true);
		parser.parseJSON(sb.toString());
		br.close();
		
	}
	
	public void writeToFile(List<String> list)
	{
		FileWriter fw;
		try {
			fw = new FileWriter(new File(filePath));
			for (String l : list)
			{
				fw.write(l);
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
