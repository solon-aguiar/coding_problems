import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class p325 {
	private static final String MATCHING_REGEX = "^[\\+\\-]?\\d+((\\.\\d+)|((\\.\\d+)?[eE]([\\+\\-])?\\d+))$";
	public static void main(String[] args) throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();
		
		while(true) {
			String candidate = bf.readLine();
			if (candidate.equals("*")) {
				break;
			}
			candidate = candidate.trim();
			if (candidate.matches(MATCHING_REGEX)) {
				output.append(candidate + " is legal.\n");
			} else {
				output.append(candidate + " is illegal.\n");
			}
		}
		System.out.print(output);
	}
}
