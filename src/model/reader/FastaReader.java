package model.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import model.FastaRecord;
import model.tools.FileTools;

public class FastaReader {

	private String path = null;
	private FastaRecordParser parser = null;

	private RandomAccessFile raf = null;

	private Map<String, Long> idHashMap = null;
	private Map<String, Long> nameHashMap = null;
	private Map<String, ArrayList<Long>> organismHashMap = null;

	private List<Long> positionsList = null; // lista pozycji poczatkow rekordow w pliku
	private HashMap<Long, Long> positionsMap = null; // mapa {pozycja poczatku -> pozycja konca}

	private int currentRecord = -1; // indeks ostatnio przeczytanego rekordu (w ramach calego pliku lub listy bialek
									// organizmu)
	private Long fileSize;

	private ArrayList<Long> organismPositionsList = null;

	// parametry do filtrowania
	private String idFilter;
	private String nameFilter;

	/**
	 * 
	 * @param filePath
	 */
	public FastaReader(String filePath, FastaRecordParser recordParser) {
		this.path = filePath;
		this.parser = recordParser;
	}

	public void setIdFilter(String idFilter) {
		this.idFilter = idFilter;
	}

	public void setNameFilter(String nameFilter) {
		this.nameFilter = nameFilter;
	}

	public void setRaf() throws FileNotFoundException {

		this.raf = new RandomAccessFile(path, "r");
	}

	/**
	 * Otwieranie pliku do odczytu (lacznie z odczytaniem indeksow i utworzeniem
	 * listy pozycji w pliku)
	 * 
	 * @throws IOException
	 */
	public void open() throws IOException {
		this.readIndex();

		this.raf = new RandomAccessFile(path, "r");

		this.positionsList = new ArrayList<>(this.idHashMap.values());

		this.positionsMap = new HashMap<>();

		for (int i = 0; i < this.positionsList.size() - 1; i++)
			this.positionsMap.put(this.positionsList.get(i), this.positionsList.get(i + 1));
		this.positionsMap.put(this.positionsList.get(this.positionsList.size() - 1), this.raf.length());
	}

	public void openFile() throws FileNotFoundException {

		this.raf = new RandomAccessFile(path, "r");
	}

	/**
	 * Zamykanie pliku
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		this.raf.close();
	}

	public void setPositionsList(List<Long> positionsList) {

		this.positionsList = positionsList;
	}

	public Map<String, Long> getIdHashMap() {
		return idHashMap;
	}

	/**
	 * Ustawienie organizmu, dla ktorego czytane beda rekordy
	 * 
	 * @param organism
	 *            nazwa organizmu (jezeli null, to czytane sa wszystkie rekordy z
	 *            pliku)
	 * @return
	 */
	public boolean setOrganism(String organism) {
		boolean result = false;

		if (organism == null) {
			this.organismPositionsList = null;
			this.currentRecord = -1;
			result = false;
		} else if (this.organismHashMap.get(organism) != null) {
			this.organismPositionsList = this.organismHashMap.get(organism);

			// System.out.println(this.organismPositionsList);
			this.currentRecord = -1;
			result = true;
		}
		return (result);
	}

	/**
	 * Pobranie liczby rekordow (w calym pliku lub w ramach listy bialek organizmu)
	 */
	public int getRecordsCount() {
		if (this.organismPositionsList != null)
			return (this.organismPositionsList.size());
		else
			return (positionsList.size());
	}

	/**
	 * Sprawdzenie czy mozna pobrac kolejny rekord
	 * 
	 * @return
	 */
	public boolean hasNextRecord() {
		return (this.currentRecord < this.getRecordsCount() - 1);
	}

	/**
	 * Pobranie kolejnego rekordu (z calego pliku lub z listy bialek organizmu)
	 */
	public FastaRecord getNextRecord() {
		FastaRecord record = null;

		while (true) {
			if (!this.hasNextRecord())
				return null;

			record = this.getRecord(this.currentRecord + 1);

			if (idFilter != null && !ifRecordContainsId(record))
				continue;

			if (nameFilter != null && !ifRecordContainsName(record))
				continue;

			return record;
		}
	}

	public boolean ifRecordContainsId(FastaRecord rec) {

		if (rec.getIdentyfier().contains(idFilter))
			return true;
		else
			return false;
	}

	public boolean ifRecordContainsName(FastaRecord rec) {

		if (rec.getEnteryName().contains(nameFilter))
			return true;
		else
			return false;
	}

	/**
	 * Pobranie rekordu o konkretnym numerze w ramach calego pliku lub z listy
	 * bialek organizmu. Numeracja zaczyna sie od 0
	 * 
	 * @param n
	 * @return
	 */
	public FastaRecord getRecord(int n) {
		FastaRecord record = null;

		if (n >= 0 && n < this.getRecordsCount()) {
			this.currentRecord = n;
			try {
				record = this.readRecord(this.getStartPos(n));
			} catch (IOException ioe) {
				record = null;
			}
		}

		return (record);
	}

	/**
	 * Pobranie rekordu o zadanym ID
	 * 
	 * @param id
	 * @return
	 */
	public FastaRecord getRecord(String id) {
		FastaRecord record = null;
		Long startPos = null;

		if ((startPos = this.idHashMap.get(id)) != null) {
			try {
				record = this.readRecord(startPos);
			} catch (IOException ioe) {
				record = null;
			}
		}

		return (record);
	}

	public FastaRecord getRecordByName(String name) {
		FastaRecord record = null;
		Long startPos = null;

		if ((startPos = this.nameHashMap.get(name)) != null) {
			try {
				record = this.readRecord(startPos);
			} catch (IOException ioe) {
				record = null;
			}
		}

		return (record);
	}

	/**
	 * Pobranie rekordu który zawiera String id
	 * 
	 * @param id,
	 *            startPos
	 * @return
	 */

	public FastaRecord getRecordContainsId(String id, Long startPos) {

		FastaRecord record = null;
		// get key from map value
		String key = "";

		for (Entry<String, Long> entry : idHashMap.entrySet()) {
			if (entry.getValue().equals(startPos)) {
				key = entry.getKey();
			}
		}
		// if key contains id get record
		if (key.contains(id)) {

			record = getRecord(key);
		}

		return (record);
	}

	public FastaRecord getRecordContainsName(String name, Long startPos) {

		FastaRecord record = null;
		String key = "";

		for (Entry<String, Long> entry : nameHashMap.entrySet()) {
			if (entry.getValue().equals(startPos)) {
				key = entry.getKey();
			}
		}

		if (key.contains(name)) {

			record = getRecordByName(key);
		}

		return record;
	}

	/**
	 * Pobranie pozycji poczatku rekordu o zadanym indeksie (w ramach calego pliku
	 * lub w liscie bialek organizmu)
	 * 
	 * @param n
	 * @return
	 */
	public long getStartPos(int n) {
		long startPos = -1;

		if (this.organismPositionsList != null)
			startPos = this.organismPositionsList.get(n);
		else
			startPos = this.positionsList.get(n);

		return (startPos);
	}

	public Long getStartPos(FastaRecord record) {
		Long startPos = null;

		if (idHashMap != null) {
			startPos = idHashMap.get(record.getIdentyfier());
			return startPos;
		} else if (nameHashMap != null) {
			startPos = nameHashMap.get(record.getEnteryName());
			return startPos;
		}
		return startPos;
	}

	public Long getEndPos(Long startPos) {
		Long endPos = null;

		if (positionsMap != null)
			endPos = positionsMap.get(startPos);
		return endPos;
	}

	/**
	 * Czytanie rekordu o zadanej pozycji w pliku
	 * 
	 * @param startPos
	 * @param endPos
	 * @return
	 * @throws IOException
	 */
	private FastaRecord readRecord(long startPos) throws IOException {
		FastaRecord record = null;

		if (positionsMap.size() > 0) {
			long endPos = this.positionsMap.get(startPos);
			int len = (int) (endPos - startPos);
			byte buffer[] = new byte[len];

			raf.seek(startPos);
			raf.read(buffer, 0, len);

			String recordStr = new String(buffer);

			record = this.parser.parse(recordStr);
		}
		return (record);
	}

	/**
	 * Metoda czytajaca pliki indeksow
	 */
	public void readIndex() throws IOException {
		this.idHashMap = this.readIdIndex(FileTools.removeExtension(this.path) + "-idHashMap.txt");
		this.nameHashMap = this.readIdIndex(FileTools.removeExtension(this.path) + "-nameHashMap.txt");
		this.organismHashMap = this
				.readOrganismIndex(FileTools.removeExtension(this.path) + "-organismNameHashMap.txt");
	}

	/**
	 * 
	 * @throws IOException
	 */
	public Map<String, Long> readIdIndex(String filepath) throws IOException {
		LinkedHashMap<String, Long> map = new LinkedHashMap<>();
		String line = null;
		int index = -1;

		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		while ((line = reader.readLine()) != null) {
			if ((index = line.indexOf("=")) != -1)
				map.put(line.substring(0, index), Long.parseLong(line.substring(index + 1)));
		}
		reader.close();

		return (map);
	}

	/**
	 * 
	 * @throws IOException
	 */
	public Map<String, ArrayList<Long>> readOrganismIndex(String filepath) throws IOException {
		HashMap<String, ArrayList<Long>> map = new HashMap<>();

		String line = null;
		int index = -1;

		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		while ((line = reader.readLine()) != null) {
			if ((index = line.indexOf("=")) != -1) {
				String key = line.substring(0, index);
				ArrayList<Long> value = new ArrayList<>();

				StringTokenizer tokenizer = new StringTokenizer(line.substring(index + 1), ",");
				while (tokenizer.hasMoreTokens())
					value.add(Long.parseLong(tokenizer.nextToken()));

				map.put(key, value);
			}
		}
		reader.close();
		return (map);
	}

	public void setMaps(Map<String, ArrayList<Long>> organismHashMap, Map<String, Long> idHashMap,
			Map<String, Long> nameHashMap) {
		this.organismHashMap = organismHashMap;
		this.nameHashMap = nameHashMap;
		this.idHashMap = idHashMap;
	}

	public void setPositionsListFromIdHashMap(Map<String, Long> idHashMap) {

		if (idHashMap != null) {
			positionsList = new ArrayList<Long>();

			for (Map.Entry<String, Long> entry : idHashMap.entrySet()) {
				positionsList.add(entry.getValue());

			}
		}

	}

	public void setPositionsMapFromPositionsList() {

		if (fileSize == null)
			setFileSize();
		int j = 1;
		this.positionsMap = new HashMap<>();
		for (int i = 0; i < positionsList.size(); i++) {

			positionsMap.put(positionsList.get(i), positionsList.get(j) - 1);
			j++;
			// dla ostatniego rekordu endPos = fileSize
			if (j == positionsList.size()) {
				positionsMap.put(positionsList.get(j - 1), fileSize);
				return;
			}
		}
	}

	public void setFileSize() {

		fileSize = new File(path).length();

	}

}
