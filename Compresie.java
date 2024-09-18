import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

public class Compresie {
	int n, m;
	int[] A, B;
	int[] dp1, dp2;

	public void solve() {
		readInput();
		writeOutput(compressionNumber());
	}

	private void readInput() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("compresie.in"));

			// pentru problema dp, numerotam de la 1
			// citim n si A
			String line = reader.readLine();
			n = Integer.parseInt(line);

			line = reader.readLine();
			String[] vals = line.trim().split(" ");
			A = new int[n + 1];

			for (int i = 0; i < n; i++) {
				A[i + 1] = Integer.parseInt(vals[i]);
			}

			// citim m si B
			line = reader.readLine();
			m = Integer.parseInt(line);

			line = reader.readLine();
			vals = line.trim().split(" ");
			B = new int[m + 1];

			for (int i = 0; i < m; i++) {
				B[i + 1] = Integer.parseInt(vals[i]);
			}
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void writeOutput(double result) {
		try {
			PrintWriter pw = new PrintWriter(new File("compresie.out"));
			pw.printf(String.format("%.1f", result));
			pw.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private int[] compressionArray(int[] v, int n) {
		int[] a = new int[n + 1];

		// construim un vector unde a[i] = sum(1..i, v)
		a[0] = 0;
		for (int i = 1; i <= n; i++) {
			a[i] = a[i - 1] + v[i];
		}

		return a;
	}


	private int compressionNumber() {
		// construim matricele de "compresie"
		dp1 = compressionArray(A, n);
		dp2 = compressionArray(B, m);

		// verificam daca vectorii sunt valizi
		if (dp1[n] != dp2[m]) {
			return -1;
		}

		// lungime maxima == numarul de elemente comune
		// adaugam elementele lui dp1 intr-un hashtable
		LinkedHashMap<Integer, Boolean> elem = new LinkedHashMap<>();
		for (int i = 1; i <= n; i++) {
			elem.put(dp1[i], true);
		}

		// cautam elementele lui dp2 in hashtable
		int len = 0;
		Boolean ret;
		for (int i = 1; i <= m; i++) {
			ret = elem.get(dp2[i]);

			// ret nu e null => dp2[i] exista in dp1
			if (ret != null) {
				len += 1;
			}

		}

		return len;
	}

	public static void main(String[] args) {
		new Compresie().solve();
	}
}
