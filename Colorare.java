import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Colorare {
	int n;
	int[] num;
	char[] or;
	private final long Mod = 1000000007;

	public void solve() {
		readInput();
		writeOutput(numberOfColourations());
	}

	private void readInput() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("colorare.in"));

			// citim n
			String line = reader.readLine();
			n = Integer.parseInt(line);

			// citim num si or
			line = reader.readLine();
			String[] vals = line.trim().split(" ");
			num = new int[n];
			or = new char[n];

			for (int i = 0; i < n; i++) {
				num[i] = Integer.parseInt(vals[2 * i]);
				or[i] = vals[2 * i + 1].charAt(0);
			}
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void writeOutput(double result) {
		try {
			PrintWriter pw = new PrintWriter(new File("colorare.out"));
			pw.printf(String.format("%.1f", result));
			pw.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// implementam o ridicare la putere eficienta
	private long pow(long base, int exp) {
		long res = 1L;

		while (exp > 0) {
			if (exp % 2 == 1) {
				res = (res * base) % Mod;
			}

			base = (base * base) % Mod;
			exp = exp / 2;
		}

		return res;
	}

	private long numberOfColourations() {
		long nr;

		// procesam primul element
		if (or[0] == 'H') {
			// avem A(2,3) posibilitati pentru a colora primele 4 patratele
			nr = 6;
			// restul se pot aseza in 3 moduri fiecare
			nr = ((nr % Mod) * (pow(3, num[0] - 1) % Mod)) % Mod;
		} else {
			// avem 3 posibilitati de a colora primele 2 patratele
			nr = 3;
			// restul au 2 posibilitati
			nr = ((nr % Mod) * (pow(2, num[0] - 1) % Mod)) % Mod;
		}

		// procesam restul elementelor
		for (int i = 1; i < n; i++) {
			// resetam nr la 1 atunci cand devine 0
			if (nr == 0) {
				nr = 1L;
			}

			// alegem in functie de ce a fost elementul din spate
			if (or[i - 1] == 'H') {
				if (or[i] == 'H') {
					// pentru fiecare zona orizontala exista 3 posibilitati
					nr = ((nr % Mod) * (pow(3, num[i]) % Mod)) % Mod;
				} else {
					// pentru prima exista o posibilitate, pentru restul 2
					nr = ((nr % Mod) * (pow(2, num[i] - 1) % Mod)) % Mod;
				}
			} else {
				if (or[i] == 'H') {
					// pentru prima exista 2 posibilitati,, pt restul 3
					nr = (((nr * 2) % Mod) * (pow(3, num[i] - 1) % Mod)) % Mod;
				} else {
					// exista 2 posibilitati
					nr = ((nr % Mod) * (pow(2, num[i]) % Mod)) % Mod;
				}
			}
		}

		return nr;
	}

	public static void main(String[] args) {
		new Colorare().solve();
	}
}
