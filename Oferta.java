import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Oferta {
	int n;
	int k;
	int[] price;
	double[] two;
	int[] three;
	HashMap<Double, Boolean> found;
	PriorityQueue<Double> heap;
	double[] dp;

	public void solve() {
		readInput();
		writeOutput(bestOffer());
	}

	private void readInput() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("oferta.in"));

			// citim n si k
			String line = reader.readLine();
			String[] vals = line.trim().split(" ");
			n = Integer.parseInt(vals[0]);
			k = Integer.parseInt(vals[1]);

			// citim preturile
			line = reader.readLine();
			vals = line.trim().split(" ");
			price = new int[n + 1];

			for (int i = 1; i <= n; i++) {
				price[i] = Integer.parseInt(vals[i - 1]);
			}
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void writeOutput(double result) {
		try {
			PrintWriter pw = new PrintWriter(new File("oferta.out"));
			pw.printf(String.format("%.1f", result));
			pw.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	private void calculteSums(int num, double sum) {
		if (num == n + 1) {
			// am calculat suma, o adaugam daca nu a fost gasita
			if (!found.containsKey(sum)) {
				found.put(sum, true);
				heap.add(sum);
			}
			return;
		}

		// adaugam doar un produs
		calculteSums(num + 1, sum + price[num]);

		// adaugam 2 produse
		if (num + 2 <= n + 1) {
			calculteSums(num + 2, sum + two[num]);
		}

		// adaugam 3 produse
		if (num + 3 <= n + 1) {
			calculteSums(num + 3, sum + three[num]);
		}
	}

	private double bestOffer() {
		int i;

		if (n > 1) {
			// obtinem vectorul cu preturile grupate cate 2
			two = new double[n];
			for (i = 1; i < n; i++) {
				if (price[i] < price[i + 1]) {
					two[i] = price[i] * 0.5 + price[i + 1];
				} else {
					two[i] = price[i] + 0.5 * price[i + 1];
				}
			}
		}

		// obtinem vectorul cu preturile grupate cate 3
		if (n > 2) {
			three = new int[n - 1];
			for (i = 1; i < n - 1; i++) {
				if (price[i] < price[i + 1] && price[i] < price[i + 2]) {
					three[i] = price[i + 1] + price[i + 2];
				} else if (price[i + 1] < price[i] && price[i + 1] < price[i + 2]) {
					three[i] = price[i] + price[i + 2];
				} else {
					three[i] = price[i] + price[i + 1];
				}
			}
		}
		if (k == 1) {
			dp = new double[n + 1];
			dp[0] = 0;

			for (i = 1; i <= n; i++) {
				// vedem care ar fi cea mai buna combinatie de produse
				// lasam produsul singur
				dp[i] = dp[i - 1] + price[i];

				// grupam produsul cu cel din spate?
				if (i > 1 && dp[i] > dp[i - 2] + two[i - 1]) {
					dp[i] = dp[i - 2] + two[i - 1];
				}

				// grupam produsul cu ultimele 2 din spate?
				if (i > 2 && dp[i] > dp[i - 3] + three[i - 2]) {
					dp[i] = dp[i - 3] + three[i - 2];
				}
			}

			// pretul final este ultimul element
			return dp[n];
		} else {
			// folosim un hashtable pentru a elimina duplicatele
			found = new HashMap<>();

			// folosim un min heap pentru a extrage usor suma maxima
			heap = new PriorityQueue<>();

			// calculam sumele posibile
			calculteSums(1, 0);

			// obtinem a k suma
			double sum = 0;
			for (i = 0; i < k; i++) {
				// nu exista al k-lea element
				if (heap.isEmpty()) {
					return -1;
				}

				// extragem cel mai mic element
				sum = heap.remove();
			}

			return sum;
		}
	}

	public static void main(String[] args) {
		new Oferta().solve();
	}
}