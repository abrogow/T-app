package model.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import model.FastaRecord;
import model.reader.FastaReader;
import model.reader.FastaRecordParser;

public abstract class Writer {

	private String positionsFilePath;
	private RandomAccessFile raf;
	private File resultFile;
	private PrintWriter pw;
	private String sequence;

	private static final String UNIPROT_WRITER = "UniProt";
	private static final String NCBI_WRITER = "NCBI";
	private static final String IPI_WRITER = "IPI";
	private static final String TAIR_WRITER = "TAIR";
	private static final String OTHER_WRITER = "Other";

	private static Writer instance = null;

	public Writer(String writerType) {

	}

	public static Writer getInstance(String parserType) {
		if (instance == null) {
			if (UNIPROT_WRITER.equals(parserType))
				return new UniprotBuilder();
			if (NCBI_WRITER.equals(parserType))
				return new NCBIBuilder();
			else
				return null;
		}
		return instance;
	}

	public abstract String getDescLine(FastaRecord record);

	public String getSequenceLine(FastaRecord record) {

		sequence = null;
		if (record != null) {
			sequence = record.getSequence();
		}
		return sequence;
	}

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
		String dstPath = newPath + "\\" + fileName + ".fasta";
		return dstPath;
	}

	// TODO: param record; tylko zapis
	public void saveRecordsToFile(List<FastaRecord> resultPositions, String fileName, String srcFile, String dbType)
			throws IOException {

		// open file
		createAndOpenFile(fileName, srcFile);

		// // TODO:rozpoznawanie readera
		FastaRecordParser parser = FastaRecordParser.getInstance(dbType);
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

		System.out.println("path: " + path);

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

		raf.close();

		// zapis do nowego pliku

		FileWriter fw = new FileWriter(tmpPath, false);
		fw.write(preRecord);
		fw.write(newRecord);
		fw.write(aftRecord);
		fw.close();

		File newFile = new File(path);
		OutputStream os = new FileOutputStream(path);
		// PrintWriter writer = new PrintWriter(newFile);
		// writer.print("");
		Files.copy(Paths.get(tmpPath), os);

		// file.delete();
		// writer.close();

		os.close();
	}
}
