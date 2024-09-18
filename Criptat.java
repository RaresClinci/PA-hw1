import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

// clasa definitorie pentru cuvinte
class Word {
	String s;
	int[] numLetters;
	int len;

	public Word(String w) {
		s = w;
		len = s.length();

		// vector de frecventa pentru litere
		numLetters = new int['z' - 'a' + 1];
		for (int i = 0; i < len; i++) {
			numLetters[s.charAt(i) - 'a']++;
		}
	}
}

// clasa pentru compararea cuvintelor
class ComparatorLetters implements Comparator<Word> {
	char let;

	public ComparatorLetters(char c) {
		let = c;
	}

	@Override
	public int compare(Word o1, Word o2) {
		double rat1 = 1.0 * o1.numLetters[let - 'a'] / o1.len;
		double rat2 = 1.0 * o2.numLetters[let - 'a'] / o2.len;

		if (rat1 > rat2) {
			return -1;
		} else if (rat1 < rat2) {
			return 1;
		} else {
			return o2.len - o1.len;
		}
	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}
}

public class Criptat {
	int n;
	Word[] word;

	public void solve() {
		readInput();
		writeOutput(bestPassword());
	}

	private void readInput() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("criptat.in"));

			// citim n
			String line = reader.readLine();
			n = Integer.parseInt(line);

			// citim cuvintele
			word = new Word[n];

			for (int i = 0; i < n; i++) {
				line = reader.readLine();
				word[i] = new Word(line);
			}
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void writeOutput(int result) {
		try {
			PrintWriter pw = new PrintWriter(new File("criptat.out"));
			pw.print(result);
			pw.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private int bestPassword() {
		int maxLen = 0;

		for (char l = 'a'; l <= 'z'; l++) {
			// exista un cuvant care este mai mult de jumatate aceasta litera?
			boolean exists = false;
			for (Word w : word) {
				if (w.numLetters[l - 'a'] >= w.len / 2) {
					exists = true;
					break;
				}
			}

			// cauatam parola cea mai buna pentru literele posibile
			if (exists) {
				// sortam dupa raportul litera/lungime pt fiecare litera
				Arrays.sort(word, new ComparatorLetters(l));

				// vedem cat timp numarul de litere l e mai mare de jumate
				int numL = 0, len = 0;
				for (int i = 0; i < n; i++) {
					numL += word[i].numLetters[l - 'a'];
					len += word[i].len;

					if (numL <= len / 2) {
						numL -= word[i].numLetters[l - 'a'];
						len -= word[i].len;
					}
				}

				// scoatem lungimea maxima
				if (maxLen < len) {
					maxLen = len;
				}
			}
		}

		return maxLen;
	}

	public static void main(String[] args) {
		new Criptat().solve();
	}
}
