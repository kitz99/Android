package com.cosmo.bezier;

public class Calcule {

	private static double[][][] bernie;
	private static final int F = 1000, S = 20;

	public static int getF() {
		return F;
	}

	public static int getS() {
		return S;
	}

	public static void preCalc() {
		double[][] pows = new double[F + 1][S + 1];
		for (int i = 0; i <= F; i++) {
			for (int j = 0; j <= S; j++) {
				pows[i][j] = Math.pow(i / (F * 1.0), j);
			}
		}
		int[][] coef = new int[S + 1][S + 1];
		for (int i = 0; i <= S; i++) {
			coef[i][0] = 1;
		}
		for (int n = 1; n <= S; n++) {
			for (int k = 1; k <= S; k++) {
				coef[n][k] = coef[n - 1][k - 1] + coef[n - 1][k];
			}
		}
		bernie = new double[F + 1][S + 1][];
		for (int i = 0; i <= F; i++) {
			for (int j = 0; j <= S; j++) {
				bernie[i][j] = new double[j + 1];
			}
		}
		for (int i = 0; i <= F; i++) {
			for (int j = 0; j <= S; j++) {
				for (int k = 0; k <= j; k++) {
					bernie[i][j][k] = coef[j][k] * pows[i][k]
							* pows[F - i][j - k];
				}
			}
		}
	}

	public static double getBernie(int a, int b, int c) {
		return bernie[a][b][c];
	}
}
