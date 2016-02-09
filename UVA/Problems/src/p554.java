import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class p554 {
	public static void main(String[] args) throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();
		Set<String> dictionary = new HashSet<String>();
		
		while(true) {
			String word = bf.readLine();
			if(word.contentEquals("#")) {
				break;
			}
			dictionary.add(word);
		}
		while(true) {
			String cypher = bf.readLine();
			if (cypher == null) {
				break;
			}
			String bestMatch = "";
			int totalMatches = Integer.MIN_VALUE;
			for (int i = 1; i <= 26; i++) {
				StringBuilder newSentence = new StringBuilder();
				for (int j = 0; j < cypher.length(); j++) {
					newSentence.append(getChar(cypher.charAt(j),i));
				}
				String sentence = newSentence.toString();
				String[] words = sentence.split("\\s+");
				int foundWords = 0;
				for(String word : words) {
					if (dictionary.contains(word)) {
						foundWords++;
					}
				}
				if (foundWords == words.length) {
					bestMatch = sentence;
					break;
				} else if (foundWords > totalMatches) {
					totalMatches = foundWords;
					bestMatch = sentence;
				} 
			}
			output.append(getFormatted(bestMatch) + "\n");
			//System.out.println(getFormatted(bestMatch));
		}
		System.out.print(output.toString());
	}
	
	private static String getFormatted(String bestMatch) {
		String[] words = bestMatch.split("\\s+");
		StringBuilder lines = new StringBuilder();
		StringBuilder currentLine = new StringBuilder();
		String separator = "";
		for (String word : words) {
			if(currentLine.length() + word.length() <= 60) {
				currentLine.append(separator + word);
				separator = " ";
			} else {
				lines.append(currentLine.toString() + "\n");
				currentLine = new StringBuilder();
				currentLine.append(word);
			}
		}
		lines.append(currentLine.toString());
		return lines.toString();
	}

	private static int index(char c) {
		if (c == ' ') {
			return 0;
		}
		return c - 'A' + 1 % 27;
	}
	
	private static char charFor(int index) {
		if (index == 0) {
			return ' ';
		}
		return (char) (index + 'A' - 1); 
	}
	
	private static char getChar(char original, int k) {
		int index = index(original);
		if (index - k < 0) {
			index = 27 - (k - index);
			return charFor(index);
		}
		return charFor(index - k);
	}
}
