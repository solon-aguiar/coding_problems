import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class p902 {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		StringBuilder output = new StringBuilder();
		
		while(true) {
			if (!sc.hasNextInt()) {
				break;
			}
			int passwordLength = sc.nextInt();
			String encryptedMessage = sc.next();
			
			Map<String, Integer> stringOccurrences = new HashMap<>();
			String password = "";
			int passwordCount = Integer.MIN_VALUE;
			
			for(int i = 0 ; i <= encryptedMessage.length() - passwordLength; i++) {
			   String candidate = encryptedMessage.substring(i, i + passwordLength);
			   Integer previousOccurences = stringOccurrences.get(candidate);
			   
			   if (previousOccurences == null) {
				   stringOccurrences.put(candidate, 0);
				   previousOccurences = 0;
			   }
			   
			   int newOccurences = previousOccurences + 1;
			   stringOccurrences.put(candidate, newOccurences);
			   if (newOccurences > passwordCount) {
				   passwordCount = newOccurences;
				   password = candidate;
			   }
			}
			output.append(password + "\n");
		}
		sc.close();
		System.out.print(output);
	}
}
