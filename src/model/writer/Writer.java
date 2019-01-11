package model.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.Files;
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
	public void replaceRecordAndUpdateFile(String newRecord, String path, int startPos, int endPos) throws IOException {

		String tmpPath = "tmp.fasta";
		File file = new File(tmpPath);

		raf = new RandomAccessFile(path, "r");
		byte[] buffer = new byte[startPos]; // dlugosc od poacztku do nowego pliku
		raf.seek(0);
		raf.read(buffer, 0, startPos);
		String preRecord = new String(buffer);

		byte[] buffer2 = new byte[(int) (raf.length() - endPos)];
		raf.seek(endPos);
		raf.read(buffer2, 0, (int) (raf.length() - endPos));
		String aftRecord = new String(buffer2);

		// zapis do nowego pliku

		RandomAccessFile raf2 = new RandomAccessFile(tmpPath, "rw");
		raf2.writeBytes("");
		raf2.seek(0);
		raf2.writeBytes(preRecord);
		raf2.writeBytes(newRecord);
		raf2.writeBytes(aftRecord);

		File newFile = new File(path);
		OutputStream os = new FileOutputStream(path);
		PrintWriter writer = new PrintWriter(newFile);
		writer.print("");
		Files.copy(Paths.get(tmpPath), os);

		// file.delete();
		writer.close();
		raf.close();
		raf2.close();
		os.close();
	}
}
