import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class p11283 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();

		int cases = Integer.parseInt(bf.readLine());
		for (int i = 0; i < cases; i++) {
			bf.readLine(); // blank-line

			char[][] boogle = new char[4][4];
			for (int j = 0; j < 4; j++) {
				boogle[j] = bf.readLine().trim().toUpperCase().toCharArray();
			}

			String n = "";
			while (n.equals("")) {
				n = bf.readLine().trim();
			}
			int noWords = Integer.parseInt(n);
			Trie t2 = new Trie();
			for (int j = 0; j < noWords; j++) {
			    t2.addWord(bf.readLine().trim().toUpperCase());
			}

			output.append("Score for Boggle game #" + (i + 1) + ": " + calculateScore(boogle, t2) + "\n");
		}
		System.out.print(output);
	}

	private static int calculateScore(char[][] boogle, Trie t) {
		Set<String> foundWords = new HashSet<>();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				boolean[][] used = new boolean[4][4];
				searchWords(boogle, used, new char[16], 0, foundWords, t, i, j);
			}
		}
		int total = 0;
		for (String word : foundWords) {
			total += getScore(word);
		}
		return total;
	}

	public static int getScore(String word) {
		int len = word.length();
		if (len == 3 || len == 4) {
			return 1;
		} else if (len == 5) {
			return 2;
		} else if (len == 6) {
			return 3;
		} else if (len == 7) {
			return 5;
		} else if (len >= 8) {
			return 11;
		}
		return 0;
	}

	private static void searchWords(char[][] boogle, boolean[][] used, char[] currentWord, int pos,
			Set<String> foundWords, Trie t, int i, int j) {
		if (i < 0 || j < 0 || i > 3 || j > 3 || used[i][j]) { // out of bounds
			return;
		}
		char c = boogle[i][j];
		used[i][j] = true;
		currentWord[pos++] = c;

		SearchResult searchResult = t.searchWord(currentWord, pos);
		if (searchResult.isDictionaryWord) {
			String s = getWord(currentWord, pos);
			foundWords.add(s);
		} else if (!searchResult.wordBeginning) {
			used[i][j] = false;
			return;
		}

		searchWords(boogle, used, currentWord, pos, foundWords, t, i - 1, j - 1);
		searchWords(boogle, used, currentWord, pos, foundWords, t, i - 1, j);
		searchWords(boogle, used, currentWord, pos, foundWords, t, i - 1, j + 1);
		searchWords(boogle, used, currentWord, pos, foundWords, t, i, j - 1);
		searchWords(boogle, used, currentWord, pos, foundWords, t, i, j + 1);
		searchWords(boogle, used, currentWord, pos, foundWords, t, i + 1, j + 1);
		searchWords(boogle, used, currentWord, pos, foundWords, t, i + 1, j);
		searchWords(boogle, used, currentWord, pos, foundWords, t, i + 1, j - 1);
		used[i][j] = false;
	}

	private static String getWord(char[] currentWord, int pos) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < pos; i++) {
			s.append(currentWord[i]);
		}
		return s.toString();
	}

}

class SearchResult {
	boolean wordBeginning;
	boolean isDictionaryWord;

	public SearchResult(boolean wB, boolean dW) {
		this.wordBeginning = wB;
		this.isDictionaryWord = dW;
	}
}

class Trie {
	public TrieNode[] roots;

	public Trie() {
		this.roots = new TrieNode['Z' - 'A' + 1];
	}

	public void addWord(String word) {
		TrieNode[] roots = this.roots;
		TrieNode nextLevel = null;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			nextLevel = roots[c - 'A'];
			if (nextLevel == null) {
				nextLevel = new TrieNode(c);
				roots[c - 'A'] = nextLevel;
			}
			roots = nextLevel.children;
		}
		nextLevel.isWord = true;
	}

	public SearchResult searchWord(char[] s, int pos) {
		TrieNode[] roots = this.roots;
		TrieNode next = null;
		for (int i = 0; i < pos; i++) {
			char c = s[i];
			next = roots[c - 'A'];
			if (next == null) {
				return new SearchResult(false, false);
			}
			roots = next.children;
		}

		if (next.isWord) {
			return new SearchResult(true, true);
		}
		return new SearchResult(true, false);
	}

}

class TrieNode {
	boolean isWord;
	public TrieNode[] children;
	char c;

	public TrieNode(char c) {
		this.c = c;
		isWord = false;
		children = new TrieNode['Z' - 'A' + 1];
	}

}
