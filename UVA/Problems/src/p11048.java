import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class p11048 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();

		Trie t = new Trie();
		int dicSize = Integer.parseInt(bf.readLine());
		for (int i = 0; i < dicSize; i++) {
			String dicWord = bf.readLine();
			t.add(dicWord, i);
		}
		int tests = Integer.parseInt(bf.readLine());
		for (int i = 0; i < tests; i++) {
			String testWord = bf.readLine();
			verify(testWord, t, output);
		}
		System.out.print(output);
	}

	private static void verify(String word, Trie t, StringBuilder output) {
		if (t.indexOfExact(word) != null) {
			output.append(word + " is correct\n");
			return;
		}
		Set<DictionaryWord> words = new HashSet<>();
		words.add(new DictionaryWord("", false));
		
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			Set<DictionaryWord> newWords = new HashSet<>();

			for (DictionaryWord w : words) {
				DictionaryWord add = w.add(c);
				if (t.hasPath(add.s)) {
					newWords.add(add);
				}
				addExtras(w, newWords, t, c);
				addReplacements(w, newWords, c, t);

				DictionaryWord delete = w.delete(c);
				if (delete != null && t.hasPath(delete.s)) {
					newWords.add(delete);
				}
				
				if (i < word.length() - 1) {
					char next = word.charAt(i+1);
					DictionaryWord switchConsecutives = w.switchConsecutives(c, next);
					if (switchConsecutives != null && t.hasPath(switchConsecutives.s)) {
						newWords.add(switchConsecutives);
					}
				}
			}
			words = newWords;
		}


		int minIndex = Integer.MAX_VALUE;
		String w = "";
		for (DictionaryWord dw : words) {
			if (dw.s.equals("")) {
				continue;
			}
			TrieNode indexOfExact = t.indexOfExact(dw.s);
			if (indexOfExact != null && indexOfExact.index < minIndex) {
				minIndex = indexOfExact.index;
				w = dw.s;
			}
		}
		if (w.isEmpty()) {
			output.append(word + " is unknown\n");
			return;
		}

		output.append(word + " is a misspelling of " + w + "\n");
	}

	private static void addReplacements(DictionaryWord w, Set<DictionaryWord> newWords, char c, Trie t) {
		for (int i = (int) 'a'; i <= (int) 'z'; i++) {
			if (i != (int) c) {
				DictionaryWord replace = w.replace((char) i);
				if (replace != null && t.hasPath(replace.s)) {
					newWords.add(replace);
				}
			}
		}
	}

	private static void addExtras(DictionaryWord w, Set<DictionaryWord> newWords, Trie t, char c) {
		for (int i = (int) 'a'; i <= (int) 'z'; i++) {
			DictionaryWord addExtra = w.add(c).addExtra((char) i);
			if (addExtra != null && t.hasPath(addExtra.s)) {
				newWords.add(addExtra);
			}
			addExtra = w.add((char) i).addExtra(c);
			if (addExtra != null && t.hasPath(addExtra.s)) {
				newWords.add(addExtra);
			}
		}
	}
}

class DictionaryWord {
	boolean changed;
	String s;

	public DictionaryWord(String s, boolean changed) {
		this.s = s;
		this.changed = changed;
	}

	public DictionaryWord replace(char c) {
		return op("" + c);
	}

	public DictionaryWord delete(char c) {
		return op("");
	}
	
	public DictionaryWord switchConsecutives(char c, char n) {
		if (this.changed) {
			return null;
		}
		return new SwitchedDictionaryWord(this.s + n + "" + c);
	}

	public DictionaryWord add(char c) {
		return new DictionaryWord(s + c, changed);
	}

	public DictionaryWord addExtra(char c) {
		return op("" + c);
	}

	private DictionaryWord op(String c) {
		if (this.changed) {
			return null;
		} else {
			return new DictionaryWord(s + c, true);
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (changed ? 1231 : 1237);
		result = prime * result + ((s == null) ? 0 : s.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DictionaryWord other = (DictionaryWord) obj;
		if (changed != other.changed)
			return false;
		if (s == null) {
			if (other.s != null)
				return false;
		} else if (!s.equals(other.s))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return s;
	}
}

class SwitchedDictionaryWord extends DictionaryWord {

	public SwitchedDictionaryWord(String s) {
		super(s, false);
	}
	
	public DictionaryWord replace(char c) {
		return null;
	}

	public DictionaryWord delete(char c) {
		return new DictionaryWord(s, true);
	}
	
	public DictionaryWord switchConsecutives(char c, char n) {
		return null;
	}

	public DictionaryWord add(char c) {
		return new DictionaryWord(s, true);
	}

	public DictionaryWord addExtra(char c) {
		return null;
	}
}

class Trie {
	TrieNode[] roots;

	public Trie() {
		roots = new TrieNode[26];
	}

	public boolean hasPath(String word) {
		TrieNode[] level = roots;
		TrieNode node = null;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			node = level[c - 'a'];
			if (node == null) {
				return false;
			}
			level = node.children;
		}
		return true;
	}

	public TrieNode indexOfExact(String word) {
		TrieNode[] level = roots;
		TrieNode node = null;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			node = level[c - 'a'];
			if (node == null) {
				return null;
			}
			level = node.children;
		}
		if (node.endOfWord) {
			return node;
		}
		return null;
	}

	public void add(String word, int index) {
		TrieNode[] level = roots;
		TrieNode node = null;

		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			node = level[c - 'a'];
			if (node == null) {
				node = new TrieNode();
				level[c - 'a'] = node;
			}
			level = node.children;
		}
		node.markEnd(word, index);
	}
}

class TrieNode {
	TrieNode[] children;
	boolean endOfWord;
	int index;
	String word;

	public TrieNode() {
		children = new TrieNode[26];
		endOfWord = false;
		index = Integer.MAX_VALUE;
	}

	public void markEnd(String word, int index) {
		this.endOfWord = true;
		this.index = index;
		this.word = word;
	}
}