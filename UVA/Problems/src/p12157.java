import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class p12157 {

	private static final int MILE_SLOT_COST = 10;
	private static final int JUICE_SLOT_COST = 15;

	public static void main(String[] args) throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();
		
		String cases = bf.readLine();
		int n = Integer.parseInt(cases);
		for (int i = 0; i < n; i++) {
			output.append("Case " + (i + 1) + ": ");
			bf.readLine();
			String[] callDurations = bf.readLine().split("\\s+");
			
			long mileCost = 0;
			long juiceCost = 0;
			
			for (String callDuration : callDurations) {
				int duration = Integer.parseInt(callDuration);
				mileCost += mileCost(duration);
				juiceCost += juiceCost(duration);
			}
			
			if (mileCost < juiceCost) {
				output.append("Mile " + mileCost + "\n");
			} else if (juiceCost < mileCost) {
				output.append("Juice " + juiceCost + "\n");
			} else {
				output.append("Mile Juice " + mileCost + "\n");
			}
		}
		System.out.print(output.toString());
	}

	private static long mileCost(int minutes) {
		int completeMinutes = minutes / 30 + 1;
		return (completeMinutes) * MILE_SLOT_COST;
	}

	private static long juiceCost(int minutes) {
		int completeMinutes = minutes / 60 + 1;
		return (completeMinutes) * JUICE_SLOT_COST;
	}

}
