package com.room17.mygdxgame.generate;

public class Generator {

	static int[][] finalMap;

	public static void init(int[][] startMap, int sizeX, int sizeY,
			double fillProb) {
		for (int i = 1; i < startMap.length - 1; i++) {
			for (int j = 1; j < startMap.length - 1; j++) {
				if (Math.random() >= fillProb)
					startMap[i][j] = 1;
			}
		}
	}

	public static int randomWithRange(int min, int max) {
		int range = Math.abs(max - min) + 1;
		return (int) (Math.random() * range) + (min <= max ? min : max);
	}

	public static void generate(int[][] startMap, int generations,
			int wallCount, int floorCount) {
		while (generations > 0) {
			for (int i = 0; i < startMap.length; i++) {
				for (int j = 0; j < startMap.length; j++) {
					if (i > 0 && i < startMap.length - 1 && j > 0
							&& j < startMap.length - 1) {
						for (int k = -1; k <= 1; k++) {
							for (int l = -1; l <= 1; l++) {
								if (startMap[k + i][l + j] == 0)
									wallCount++;
								if (startMap[k + i][l + j] == 1)
									floorCount++;
							}
						}
					}
					if (wallCount > 5 || floorCount == 8)
						startMap[i][j] = 0;
					floorCount = 0;
					wallCount = 0;
				}
			}
			--generations;
		}
	}

	public static void cleanUp(int[][] startMap, int cleanUp, int wallCount,
			int floorCount) {
		while (cleanUp > 0) {
			for (int i = 1; i < startMap.length - 1; i++) {
				for (int j = 1; j < startMap.length - 1; j++) {
					if (i > 0 && i < startMap.length - 1 && j > 0
							&& j < startMap.length - 1) {
						for (int k = -1; k <= 1; k++) {
							for (int l = -1; l <= 1; l++) {
								if (startMap[k + i][l + j] == 0)
									wallCount++;
								if (startMap[k + i][l + j] == 1)
									floorCount++;

							}
						}
					}
					if (wallCount > 4)
						startMap[i][j] = 0;
					if (floorCount > 5)
						startMap[i][j] = 1;

					floorCount = 0;
					wallCount = 0;
				}
			}
			--cleanUp;
		}
	}

	public static void print(int[][] finalMap) {
		for (int i = 0; i < finalMap.length; i++) {
			for (int j = 0; j < finalMap.length; j++) {
				if (finalMap[i][j] == 0)
					System.out.print('#');
				if (finalMap[i][j] == 1)
					System.out.print('_');
				if (j == finalMap.length - 1)
					System.out.println();
			}
		}
	}

	public static int[][] start() {
		int sizeX = 100, sizeY = 100;
		int[][] startMap = new int[sizeX][sizeY];
		finalMap = new int[sizeX][sizeY];
		double fillProb = 0.3;
		int wallCount = 0;
		int floorCount = 0;
		int generations = 5;
		int cleanUp = 4;

		init(startMap, sizeX, sizeY, fillProb);
		generate(startMap, generations, wallCount, floorCount);
		cleanUp(startMap, cleanUp, wallCount, floorCount);

		finalMap = startMap;
		// print(finalMap);
		return finalMap;
	}
}
