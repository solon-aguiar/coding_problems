import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class p488 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();

		int cases = Integer.parseInt(bf.readLine());
		String separator = "";

		for (int i = 0; i < cases; i++) {
			output.append(separator);
			bf.readLine(); // blank line

			int amplitude = Integer.parseInt(bf.readLine());
			int frequency = Integer.parseInt(bf.readLine());

			for (int j = 0; j < frequency; j++) {
				if (j != 0) {
					output.append("\n");
				}
				output.append(generateWave(amplitude));
			}
			separator = "\n";
		}
		System.out.print(output.toString());
	}

	private static String generateWave(int amplitude) {
		StringBuilder wave = new StringBuilder();
		for (int currentAmplitude = 1; currentAmplitude <= amplitude; currentAmplitude++) {
			for (int i = 0; i < currentAmplitude; i++) {
				wave.append(currentAmplitude);
			}
			
			wave.append("\n");
		}
		for (int currentAmplitude = amplitude - 1; currentAmplitude > 0; currentAmplitude--) {
			for (int i = 0; i < currentAmplitude; i++) {
				wave.append(currentAmplitude);
			}
			wave.append("\n");
		}
		return wave.toString();
	}
}
