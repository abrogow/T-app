package model.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

import model.FastaRecord;
import view.additionalWindows.AddEditFileWindow;

public abstract class Reader {

	protected ArrayList<Long> positionsList;
	protected String path;
	protected File positionsFile;
	protected long processedBytes;
	protected long size;
	protected RandomAccessFile raf;
	protected AddEditFileWindow addEditFileWindow;

	public abstract FastaRecord parseRecord(int recordNumber);

	public abstract String getRecordFromFile(long startPosition, long endPosition);

	public abstract void savePositionsToFile(ArrayList<Long> positionsList) throws FileNotFoundException;

	public abstract String getPositionsFilePath();

	public abstract File getPositionsFile();

	public Reader(AddEditFileWindow addEditFileWindow) {
		this.addEditFileWindow = addEditFileWindow;
	}

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
		raf = new RandomAccessFile(path, "rw");

		size = raf.length();
		processedBytes = 0;

		raf.seek(0);
		// size = getNumbOfLines(path);
		positionsList = new ArrayList<>();

		String line = null;
		long pos = raf.getFilePointer();
		System.out.println("File pos: " + pos);

		while ((line = raf.readLine()) != null) {
			// do ladowania paska postepu
			// prepareProgressBar();
			processedBytes += line.length() + 2;
			if (line.substring(0, 1).equals(">")) {

				pos = raf.getFilePointer();
				int b = line.getBytes().length;
				int l = line.length();
				pos = pos - line.length() - 2;

				positionsList.add(pos);

				prepareProgressBar();

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

	public long getSize() {
		return size;
	}

	public void prepareProgressBar() {
		float done = (float) processedBytes / (float) size;
		float percent = Math.round(done * 100);
		System.out.println("Done " + percent + "%");
		addEditFileWindow.setProgressBar(done);
		addEditFileWindow.getProgressTextField().setText(percent + "%");
	}
}
