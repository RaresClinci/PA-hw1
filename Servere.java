import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Servere {
	int n;
	int[] p;
	double[] c;
	private final double tol = 0.001;

	public void solve() {
		readInput();
		writeOutput(getResult());
	}

	private void readInput() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("servere.in"));

			// citim n
			String line = reader.readLine();
			n = Integer.parseInt(line);

			// citim p
			line = reader.readLine();
			String[] vals = line.trim().split(" ");
			p = new int[n];

			for (int i = 0; i < n; i++) {
				p[i] = Integer.parseInt(vals[i]);
			}

			// citim c
			line = reader.readLine();
			vals = line.trim().split(" ");
			c = new double[n];

			for (int i = 0; i < n; i++) {
				c[i] = Double.parseDouble(vals[i]);
			}
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void writeOutput(double result) {
		try {
			PrintWriter pw = new PrintWriter(new File("servere.out"));
			// trunchem la prima zecimala
			pw.printf(String.format("%.1f", result));
			pw.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// calculam puterea minima pentru pragul dat
	private double minPower(double level) {
		double min = p[0] - Math.abs(level - c[0]), sol;
		for (int i = 1; i < n; i++) {
			sol = p[i] - Math.abs(level - c[i]);
			if (sol < min) {
				min = sol;
			}
		}

		return min;
	}

	// functie pentru calcularea curentului minim
	private double minCurrent() {
		double min = c[0];
		for (int i = 1; i < n; i++) {
			if (c[i] < min) {
				min = c[i];
			}
		}

		return min;
	}

	// functie pentru calcularea curentului maxim
	private double maxCurrent() {
		double max = c[0];
		for (int i = 1; i < n; i++) {
			if (c[i] > max) {
				max = c[i];
			}
		}

		return max;
	}

	private double getResult() {
		// obtinem curentul minim si maxim(capetele)
		double start = minCurrent();
		double end = maxCurrent();
		double mid;
		double l1, l2, l3;

		// calculam puterea la capete
		l1 = minPower(start);
		l3 = minPower(end);

		// cautam pragul optim
		while (end - start > tol) {
			// impartim intervalul in 2 segmente egale
			mid = start + (end - start) / 2;

			// calculam nivelul centrului
			l2 = minPower(mid);

			// decidem in ce interval coboram
			if (l1 > l3) {
				// coboram in prima jumatate
				end = mid;
				l3 = l2;
			} else {
				// coboram in a doua jumatate
				start = mid;
				l1 = l2;
			}
		}

		// intoarcem pragul
		return minPower(start);
	}


	public static void main(String[] args) {
		new Servere().solve();
	}
}