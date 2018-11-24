package model.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.Paths;
import java.util.ArrayList;

import model.FastaRecord;
import model.reader.Reader;
import model.reader.UniprotReader;

public abstract class Writer {

	private String positionsFilePath;
	private RandomAccessFile raf;
	private File resultFile;
	private PrintWriter pw;

	public abstract String getDescLine(FastaRecord record);

	public abstract String getSequenceLine(FastaRecord record);

	public void saveRecordIntoFile(FastaRecord record) throws IOException {

		if (record != null) {

			// poprawic//
			String descLine = getDescLine(record);
			String sequence = getSequenceLine(record);

			pw.print(descLine);
			pw.print(sequence);

		}
	}

	public void createAndOpenFile(String fileName, String path) throws FileNotFoundException {

		/////////////////////////// !!!
		String dstPath = getResultFilesPath(fileName, path);
		if (dstPath != null && !dstPath.equals("")) {

			resultFile = new File(dstPath);
			pw = new PrintWriter(resultFile);
		}
	}

	public void closeFile() {

		pw.close();
	}

	public String getResultFilesPath(String fileName, String positionsFilePath) {

		java.nio.file.Path p = Paths.get(positionsFilePath);

		String newPath = positionsFilePath.substring(0, positionsFilePath.lastIndexOf(File.separator));
		String dstPath = newPath + "\\" + fileName + ".txt";
		return dstPath;
	}

	public void saveRecordsToFile(ArrayList<Long> resultPositions, String fileName, String srcFile) throws IOException {

		// open file
		createAndOpenFile(fileName, srcFile);
		FastaRecord record = new FastaRecord();

		// TODO:rozpoznawanie readera
		Reader reader = new UniprotReader();
		// TODO:ustawianie
		reader.setPath(srcFile);
		reader.setRaf(srcFile);

		for (Long position : resultPositions)
			try {
				{

					record = reader.parseRecord(position, resultPositions);
					saveRecordIntoFile(record);

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		closeFile();
	}
}
