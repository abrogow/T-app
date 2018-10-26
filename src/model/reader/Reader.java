package model.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

import model.FastaRecord;

public abstract class Reader {

	protected ArrayList<Long> positionsList;
	protected String path;
	protected File positionsFile;

	public abstract FastaRecord parseRecord(int recordNumber);

	public abstract String getRecordFromFile(long startPosition, long endPosition);

	public abstract void savePositionsToFile(ArrayList<Long> positionsList) throws FileNotFoundException;

	public abstract String getPositionsFilePath();

	public abstract File getPositionsFile();

	public void start() throws IOException {

		// if (!ifPositionsFileExist()) {
		Scanner input = new Scanner(System.in);
		System.out.println("Sciezka do pliku FASTA :");
		String path = "C:\\Users\\BROGO\\Desktop\\INZYNIERKA\\test.txt";
		if (!("").equals(path)) {

			readPositions(path);
		}
		// }

	}

	public ArrayList<Long> readPositions(String path) throws IOException {

		this.path = path;
		RandomAccessFile raf = new RandomAccessFile(path, "rw");

		positionsList = new ArrayList<>();

		String line = null;
		long pos = raf.getFilePointer();
		System.out.println("File pos: " + pos);

		while ((line = raf.readLine()) != null) {

			if (line.substring(0, 1).equals(">")) {

				pos = raf.getFilePointer();
				int b = line.getBytes().length;
				int l = line.length();
				pos = pos - line.length() - 2;

				positionsList.add(pos);

			}

		}
		return positionsList;
	}

	public boolean ifPositionsFileExist() {

		positionsFile = getPositionsFile();
		if (positionsFile != null && positionsFile.exists())
			return true;
		return false;
	}
}
