package model.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.Paths;
import java.util.List;

import model.FastaRecord;
import model.reader.FastaReader;
import model.reader.FastaUniprotRecordParser;

public abstract class Writer {

	private String positionsFilePath;
	private RandomAccessFile raf;
	private File resultFile;
	private PrintWriter pw;

	public Writer() {

	}

	public abstract String getDescLine(FastaRecord record);

	public abstract String getSequenceLine(FastaRecord record);

	public void saveRecordIntoFile(FastaRecord record) throws IOException {

		if (record != null) {

			String descLine = getDescLine(record);
			String sequence = getSequenceLine(record);

			saveStringRecordIntoFile(descLine, sequence);

		}
	}

	public void saveStringRecordIntoFile(String descLine, String sequence) {

		pw.println(descLine);
		pw.println(sequence);
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

	// TODO: param record; tylko zapis
	public void saveRecordsToFile(List<FastaRecord> resultPositions, String fileName, String srcFile)
			throws IOException {

		// open file
		createAndOpenFile(fileName, srcFile);

		// // TODO:rozpoznawanie readera
		FastaUniprotRecordParser parser = new FastaUniprotRecordParser();
		FastaReader reader = new FastaReader(srcFile, parser);
		// // TODO:ustawianie

		for (FastaRecord position : resultPositions)
			try {
				{

					saveRecordIntoFile(position);

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		closeFile();
	}

	/**
	 * Metoda zamienia rekord w pliku na nowy
	 * 
	 * @throws IOException
	 */
	public void replaceAndSaveRecord(String newRecord, Long startPos, Long endPos, String path) throws IOException {

		raf = new RandomAccessFile(path, "rw");

		int len = (int) (endPos - startPos);
		byte buffer[] = new byte[len];

		raf.seek(startPos);
		raf.read(buffer, 0, len);

		String recordStr = new String(buffer);

		recordStr = recordStr.replace(recordStr, newRecord);

		byte[] b = recordStr.getBytes();

		raf.seek(startPos);
		raf.write(b);

		raf.close();

	}
}
