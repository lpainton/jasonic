package jasonic;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class JSONParser {
	
	boolean debugMode;
	PrintStream debugStream;
	public void setDebugMode(boolean val)
	{
		debugMode = val;
	}
	public void setDebugStream(PrintStream ps)
	{
		debugStream = ps;
	}
	public void debugPrint(String s)
	{
		if (debugMode)
			debugStream.print(s);
	}
	public void debugPrintLn(String s)
	{
		if (debugMode)
			debugStream.println(s);
	}
	
	public class JSONParseException extends Exception
	{
		public JSONParseException(String msg)
		{
			super(msg);
		}
	}
	
	public class JSONObject extends JSONValue
	{		
		public JSONObject()
		{
			super(new ArrayList<JSONTuple>());
		}		
		public void add(JSONTuple t)
		{
			((ArrayList<JSONTuple>)value).add(t);
		}
		public JSONTuple[] getTuples()
		{
			return (JSONTuple[])((ArrayList<JSONTuple>)value).toArray();
		}
	}
	public class JSONTuple
	{
		String name;
		JSONValue value;
	}
	public class JSONValue
	{
		Object value;
		Class type;
		public JSONValue(Object val)
		{
			value = val;
			type = val.getClass();
		}
	}
	
	public JSONParser()
	{
		debugMode = false;
		debugStream = System.out;
	}
	
	public String parseString(String raw) throws JSONParseException
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i<raw.length(); i++)
		{
			Character c = raw.charAt(i);
			switch (c)
			{
				case '\\':
					switch (raw.charAt(i+1))
					{
						case '"':
							sb.append('"');
							break;
						case '\\':
							sb.append('\\');
							break;
						case '/':
							sb.append('/');
							break;
						case 'b':
							sb.append('\b');
							break;
						case 'f':
							sb.append('f');
							break;
						case 'n':
							sb.append('\n');
							break;
						case 'r':
							sb.append('\r');
							break;
						case 't':
							sb.append('\t');
							break;
						case 'u':
							//Ignoring unicode...for now
							i = i+4;
							break;
					}
					break;
					
				default:
					if (Character.isAlphabetic(c) || Character.isDigit(c))
					{
						sb.append(c);
					}
					else
					{
						throw new JSONParseException(raw);
					}
			}
		}
		return sb.toString();
	}

	public JSONObject parseObject(String raw)
	{
		JSONObject myObj = new JSONObject();
		debugPrintLn("Object parser received string: " + raw);
		StringBuilder line = new StringBuilder();
		int braces = 0;
		int brackets = 0;
		boolean isVal = false; 
		for (int i=0; i<raw.length(); i++)
		{
			Character c = raw.charAt(i);
			switch(c)
			{
			}
		}
		return myObj;
	}
	
	public List<JSONObject> parseJSON(String raw) throws JSONParseException
	{
		debugPrintLn("Parser received string: " + raw);
		List<JSONObject> allObjs = new ArrayList<JSONObject>();
		StringBuilder buffer = new StringBuilder();
		int braces = 0;
		boolean isObj = false;
		for (int i=0; i<raw.length(); i++)
		{
			Character c = raw.charAt(i);
			//System.out.println(braces);
			switch(c)
			{
				case '{':
					braces++;
					isObj = true;
					break;
					
				case '}':
					braces--;
					if (!isObj || braces < 0)
						throw new JSONParseException(Character.toString(c));
					if (braces == 0)
					{
						debugPrintLn(buffer.toString());
						allObjs.add(parseObject(buffer.toString()));
						buffer = new StringBuilder();
						isObj = false;
					}
					break;
					
				default:
					if (Character.isWhitespace(c))
					{
						break;
					}
					else if (isObj)
					{
						buffer.append(c);
						break;
					}
					else if (!isObj)			
					{
						throw new JSONParseException(Character.toString(c));
					}
			}
		}
		return null;
	}
}
