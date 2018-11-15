package model.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FilenameUtils;

import javafx.scene.control.Alert;
import model.FastaRecord;
import view.additionalWindows.AddEditFileWindow;

public abstract class Reader {

	private static Reader instance = null;

	protected ArrayList<Long> positionsList;
	protected String path;
	protected File positionsFile;
	protected long processedBytes;
	protected long size;
	protected RandomAccessFile raf;
	protected AddEditFileWindow addEditFileWindow;
	protected String FASTA_EXTENSION = "fasta";
	protected String ZIP_EXTENSION = "zip";
	protected String RAR_EXTENSION = "rar";
	protected String TXT_EXTENSION = "txt";
	protected ZipInputStream zipinputstream;

	private LinkedHashMap<String, ArrayList<Long>> idHashMap;
	private LinkedHashMap<String, ArrayList<Long>> nameHashMap;
	private LinkedHashMap<String, ArrayList<Long>> organismNameHashMap;

	private String positionsFilePath;

	public abstract FastaRecord parseRecord(int recordNumber) throws IOException;

	public abstract String getRecordFromFile(long startPosition, long endPosition);

	public abstract String getPositionsFilePath();

	public abstract File getPositionsFile();

	public abstract LinkedHashMap<Long, String> getOrganismNameHashMap();

	public abstract LinkedHashMap<Long, String> getIdHashMap();

	public abstract LinkedHashMap<Long, String> getNameHashMap();

	public Reader(AddEditFileWindow addEditFileWindow) {
		this.addEditFileWindow = addEditFileWindow;
	}

	public ArrayList<Long> readPositions(String path) throws IOException {

		this.path = path;

		openInputFile();

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

			}

		}
		closeInputFile();
		return positionsList;
	}

	public void savePositionsToFile(ArrayList<Long> positionsList) throws FileNotFoundException {
		// TODO Auto-generated method stub
		if (!ifPositionsFileExist()) {

			positionsFilePath = getPositionsListFilePath(path);
			positionsFile = new File(positionsFilePath);
			PrintWriter pw = new PrintWriter(positionsFile);

			for (Long position : positionsList) {

				pw.println(position);

			}
			System.out.println("Save positions to file");
			pw.close();
		} else {
			return;
		}
	}

	// zwraca hash mape z pozycjami i id rekordow
	public void savePositionsAndIdToMap(ArrayList<Long> positionsList) throws IOException {

		idHashMap = new LinkedHashMap<String, ArrayList<Long>>();
		nameHashMap = new LinkedHashMap<String, ArrayList<Long>>();
		organismNameHashMap = new LinkedHashMap<String, ArrayList<Long>>();

		ArrayList<Long> positions;
		Long pos = null;
		for (int i = 0; i < positionsList.size(); i++) {

			pos = positionsList.get(i);
			FastaRecord fastaRecord;
			fastaRecord = parseRecord(i);

			if (fastaRecord != null) {
				String id = fastaRecord.getIdentyfier();
				String name = fastaRecord.getEnteryName();
				String organismName = fastaRecord.getOrganismName();

				/////////////// !!!!!!!!!!!!!!!nowa metoda
				// jezeli 1 pozycja - tworze nowa liste i dodaje do hashmapy
				if (idHashMap.get(id) == null) {
					positions = new ArrayList<Long>();
					positions.add(pos);
					idHashMap.put(id, positions);
					// jezeli kolejna pozycja dodaje do listy, referaencja jest juz trzymana w
					// hashmapie wiec nie musze dodawac jeszcze raz
				} else {
					positions = idHashMap.get(id);
					positions.add(pos);
				}

				if (nameHashMap.get(name) == null) {
					positions = new ArrayList<Long>();
					positions.add(pos);
					nameHashMap.put(name, positions);
				} else {
					positions = nameHashMap.get(name);
					positions.add(pos);
				}

				if (organismNameHashMap.get(organismName) == null) {
					positions = new ArrayList<Long>();
					positions.add(pos);
					organismNameHashMap.put(organismName, positions);
				} else {
					positions = organismNameHashMap.get(organismName);
					positions.add(pos);
				}

			} else {
				System.out.println("Reader.savePositionsAndIdToMap: Failed to parse fasta record");
			}
		}
		idHashMap.keySet();
		nameHashMap.keySet();
		organismNameHashMap.keySet();
	}

	// zamienic na spisywanie z listy w petli
	public void saveMapsToFile() throws FileNotFoundException, IOException {

		List<LinkedHashMap<String, ArrayList<Long>>> mapList = new ArrayList<LinkedHashMap<String, ArrayList<Long>>>();
		mapList.add(idHashMap);
		mapList.add(nameHashMap);
		mapList.add(organismNameHashMap);

		String dstFile;

		for (LinkedHashMap<String, ArrayList<Long>> map : mapList) {

			dstFile = getHashMapFilePath(path, map);
			FileOutputStream fos = new FileOutputStream(new File(dstFile));
			PrintWriter pw = new PrintWriter(fos);
			String rec = "";

			for (Map.Entry<String, ArrayList<Long>> m : map.entrySet()) {
				rec = m.getKey() + "=";

				for (int i = 0; i < m.getValue().size(); i++) {
					if (i != 0)
						rec += ",";
					rec += m.getValue().get(i);
				}
				pw.println(rec);
			}
			pw.flush();
			pw.close();
			fos.close();
		}
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

	public String getExtension() {
		String extension = "";
		int i = path.lastIndexOf(".");
		if (i > 0) {
			extension = path.substring(i + 1);
		}
		System.out.println("Extension:" + extension);
		return extension;
	}

	public long getEndRecordPosition(int recordNumber) throws IOException {

		long endPos;
		if (recordNumber + 1 < positionsList.size())
			endPos = positionsList.get(recordNumber + 1);
		else
			endPos = size;

		return endPos;
	}

	// funkcja rozpoznaje czy plik jest spakowany
	public void openInputFile() throws IOException {
		String extension = getExtension();
		String filePath;
		if (ZIP_EXTENSION.equals(extension) | RAR_EXTENSION.equals(extension)) {
			zipinputstream = new ZipInputStream(new FileInputStream(path));
			ZipEntry zipentry = zipinputstream.getNextEntry();
			filePath = zipentry.getName();
			raf = new RandomAccessFile(filePath, "rw");
		} else if (FASTA_EXTENSION.equals(extension) | TXT_EXTENSION.equals(extension)) {
			raf = new RandomAccessFile(path, "rw");
		} else {
			showAlertInfo();
			return;
		}
	}

	// zamyka raf i zip imput zaleznie od rozszerzenia
	public void closeInputFile() throws IOException {

		String extension = getExtension();
		if (ZIP_EXTENSION.equals(extension) | RAR_EXTENSION.equals(extension)) {
			zipinputstream.closeEntry();
			raf.close();
			zipinputstream.close();
		} else if (FASTA_EXTENSION.equals(extension) | TXT_EXTENSION.equals(extension)) {
			raf.close();
		}
	}

	// funkcja generuje sciezke do pliku z pozycjami dokonczyc
	public String getPositionsListFilePath(String path) {

		String fileName = "positionsList";
		String dstPath = getResultFilesPath(path, fileName);
		return dstPath;
	}

	public String getHashMapFilePath(String path, LinkedHashMap<String, ArrayList<Long>> mapa) {

		String hashMapName = "";
		if (mapa == idHashMap)
			hashMapName = "idHashMap";
		if (mapa == nameHashMap)
			hashMapName = "nameHashMap";
		if (mapa == organismNameHashMap)
			hashMapName = "organismNameHashMap";

		String dstPath = getResultFilesPath(path, hashMapName);

		return dstPath;
	}

	public String getResultFilesPath(String path, String fileName) {

		java.nio.file.Path p = Paths.get(path);
		String fileNameWithOutExt = FilenameUtils.removeExtension(p.getFileName().toString());

		// TODO moze zrobic osobny folder na wsyztskie dodawane pliki??
		File dir = new File("resultFiles");
		dir.mkdir();

		String newPath = path.substring(0, path.lastIndexOf(File.separator));
		String dstPath = newPath + "\\" + fileNameWithOutExt + "-" + fileName + ".txt";
		return dstPath;

	}

	private void showAlertInfo() {

		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("B³¹d!");
		alert.setHeaderText(null);
		alert.setContentText("Wybrano niew³aœciwy plik!");
		alert.showAndWait();
	}

	public void setHashMaps() {

		if (idHashMap != null) {

		} else {

		}
		if (nameHashMap != null) {

		} else {

		}
		if (organismNameHashMap != null) {

		} else {

		}
	}

}
