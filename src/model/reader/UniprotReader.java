package model.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.FastaRecord;
import view.additionalWindows.AddEditFileWindow;

public class UniprotReader extends Reader {

	public UniprotReader(AddEditFileWindow addEditFileWindow) {
		super(addEditFileWindow);
		// TODO Auto-generated constructor stub
	}

	private RandomAccessFile raf;

	private static final String PATTERN = "\\>([a-zA-Z]*)\\|(.*?)\\|(.*?) (.*?)OS=(.*?)GN=(.*?)PE=(.*?)SV=([1-9]*)(.*)";

	protected String positionsFilePath = "C:\\Users\\BROGO\\Desktop\\INZYNIERKA\\uniprotPositionsList.txt";

	@Override
	public FastaRecord parseRecord(int recordNumber) {
		// TODO Auto-generated method stub

		long startPos = positionsList.get(recordNumber - 1);
		long endPos = positionsList.get(recordNumber);

		// String proteinName;

		String record = getRecordFromFile(startPos, endPos);
		FastaRecord fastaRecord = new FastaRecord();

		getMatcher(PATTERN, 0, record);

		return fastaRecord;
	}

	public String getMatcher(String pattern, int group, String record) {

		Pattern p = Pattern.compile(pattern, Pattern.DOTALL);
		Matcher m = p.matcher(record);
		String result = null;
		if (m.matches()) {

			result = m.group(group);
			System.out.println(result);

			for (int i = 0; i <= m.groupCount(); i++) {
				System.out.println("Grupa " + Integer.toString(i));
				System.out.println(m.group(i));
			}
		}
		return result;
	}

	@Override
	public String getRecordFromFile(long startPosition, long endPosition) {
		// TODO Auto-generated method stub
		String recordString = "";
		try {
			raf = new RandomAccessFile(path, "r");

			String line = null;
			String endLine = null;
			raf.seek(startPosition);

			// convert long to byte []
			// ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
			// buffer.putLong(endPosition - startPosition);
			// byte[] b = buffer.array();

			while (startPosition < endPosition) {
				raf.seek(startPosition++);
				recordString += (char) raf.readByte();

			}
			System.out.println("Record : " + recordString);
			return recordString;

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return recordString;
	}

	@Override
	public void savePositionsToFile(ArrayList<Long> positionsList) throws FileNotFoundException {
		// TODO Auto-generated method stub
		if (!ifPositionsFileExist()) {

			positionsFile = new File(positionsFilePath);
			PrintWriter pw = new PrintWriter(positionsFile);

			// PrintWriter positionsFile = new PrintWriter(positionsFilePath);
			// RandomAccessFile raf = new RandomAccessFile(positionsFilePath, "r");
			// raf.seek(0);
			// long pos = raf.getFilePointer();

			for (Long position : positionsList) {

				pw.println(position);
			}
			System.out.println("Save positions to file");
			pw.close();
		} else {
			return;
		}
	}

	public String getPositionsFilePath() {
		return positionsFilePath;
	}

	public void setPositionsFilePath(String positionsFilePath) {
		this.positionsFilePath = positionsFilePath;
	}

	public File getPositionsFile() {
		return positionsFile;
	}

}
