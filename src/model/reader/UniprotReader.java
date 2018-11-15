package model.reader;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

	protected String positionsFilePath;

	private LinkedHashMap<Long, String> idHashMap;
	private LinkedHashMap<Long, String> nameHashMap;
	private LinkedHashMap<Long, String> organismNameHashMap;

	// zwraca sparsowany rekord
	@Override
	public FastaRecord parseRecord(int recordNumber) throws IOException {
		// TODO Auto-generated method stub

		long startPos = positionsList.get(recordNumber);
		long endPos = getEndRecordPosition(recordNumber);

		String record = getRecordFromFile(startPos, endPos);
		FastaRecord fastaRecord = null;
		List<String> parameters = new ArrayList<String>();
		Map<String, String> paramsMap = new LinkedHashMap<String, String>();

		Pattern p = Pattern.compile(PATTERN, Pattern.DOTALL);
		Matcher m = p.matcher(record);

		String[] keys = { "identifier", "enteryName", "proteinName", "organismName", "geneName", "proteinExistence",
				"sequenceVersion", "sequence" };

		if (m.matches()) {

			for (int i = 0; i < m.groupCount() - 1; i++) {

				paramsMap.put(keys[i], m.group(i + 2));
			}
			paramsMap.keySet();
			fastaRecord = new FastaRecord(paramsMap.get("identifier"), paramsMap.get("enteryName"),
					paramsMap.get("proteinName"), paramsMap.get("organismName"), paramsMap.get("geneName"),
					paramsMap.get("proteinExistence"), paramsMap.get("sequenceVersion"), paramsMap.get("sequence"));
			System.out.println(fastaRecord);
		}

		return fastaRecord;
	}

	// sprawdza dzialanie wyrazenia regularnego
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

			while (startPosition < endPosition) {
				raf.seek(startPosition++);
				recordString += (char) raf.readByte();

			}

			return recordString;

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return recordString;
	}

	public LinkedHashMap<Long, String> getIdHashMap() {
		return idHashMap;
	}

	public LinkedHashMap<Long, String> getNameHashMap() {
		return nameHashMap;
	}

	public LinkedHashMap<Long, String> getOrganismNameHashMap() {
		return organismNameHashMap;
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
