import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class p10058 {
	public static final String ACTOR_REGEX = "((a|the)\\s)?(tom|jerry|goofy|mickey|jimmy|dog|cat|mouse)";
	public static final String VERB_REGEX = "(hate|love|know|like)(s)?";
	public static final String ACTIVE_LIST_REGEX = "((" + ACTOR_REGEX +" and)\\s)*"+ACTOR_REGEX;
	public static final String ACTION_REGEX = ACTIVE_LIST_REGEX + "\\s" + VERB_REGEX + "\\s" + ACTIVE_LIST_REGEX;
	public static final String STATEMENT_REGEX = "^(" +ACTION_REGEX +"\\s,\\s)*" + ACTION_REGEX + "$";
	
	public static void main(String[] args) throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();
		
		while (true) {
			String line = bf.readLine();
			if (line == null) {
				break;
			}
			
			if (line.matches(STATEMENT_REGEX)) {
				output.append("YES I WILL\n");
			} else {
				output.append("NO I WON'T\n");
			}
		}
		System.out.print(output);
	}
}
