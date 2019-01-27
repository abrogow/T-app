package model.reader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import model.FastaRecord;
import model.tools.FileTools;

public class FastaIndexBuilder {
	public final static String FASTA_EXTENSION = "fasta";
	public final static String GZIP_EXTENSION = "gz";

	private String path;

	private BufferedReader reader = null;
	private FastaRecordParser parser = null;

	private LinkedHashMap<String, Long> idHashMap;
	private LinkedHashMap<String, Long> nameHashMap;
	private LinkedHashMap<String, ArrayList<Long>> organismNameHashMap;

	/**
	 * Konstruktor
	 * 
	 * @param filepath
	 *            sciezka do pliku
	 * @param recordParser
	 *            parser rekordow FASTA
	 */
	public FastaIndexBuilder(String filepath, FastaRecordParser recordParser) {
		this.path = filepath;
		this.parser = recordParser;
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void buildIndex() throws IOException {
		this.createMaps();
		this.saveMaps();
	}

	/**
	 * Metoda odczytujaca plik FASTA i tworzaca mapy
	 * 
	 * @return
	 * @throws IOException
	 */
	public void createMaps() throws IOException {

		String line = null, lineSeparator = null;
		long processedChars = 0;
		lineSeparator = FileTools.getLineSeparator(this.path);

		this.openInputFile();
		this.idHashMap = new LinkedHashMap<String, Long>();
		this.nameHashMap = new LinkedHashMap<String, Long>();
		this.organismNameHashMap = new LinkedHashMap<String, ArrayList<Long>>();

		while ((line = this.reader.readLine()) != null) {
			if (line.length() > 0 && line.charAt(0) == '>') {
				long position = processedChars;
				FastaRecord record = this.parser.parse(line);
				if (record != null) {
					this.idHashMap.put(record.getIdentyfier(), position);
					this.nameHashMap.put(record.getEnteryName(), position);
					ArrayList<Long> list = null;
					if (record.getOrganismName() != null) {
						if ((list = this.organismNameHashMap.get(record.getOrganismName())) == null)
							this.organismNameHashMap.put(record.getOrganismName(), (list = new ArrayList<>()));
						list.add(position);
					}
				}
			}
			processedChars += line.length() + lineSeparator.length();
		}
		this.closeInputFile();

	}

	/**
	 * Metoda zapisujaca mapy do plikow
	 * 
	 * @throws IOException
	 */
	public void saveMaps() throws IOException {
		List<LinkedHashMap<String, ?>> mapList = new ArrayList<LinkedHashMap<String, ?>>();
		mapList.add(idHashMap);
		mapList.add(nameHashMap);
		mapList.add(organismNameHashMap);

		String dstFile;

		for (LinkedHashMap<String, ?> map : mapList) {
			dstFile = this.getHashMapFilePath(this.path, map);
			BufferedWriter writer = new BufferedWriter(new FileWriter(dstFile));

			for (Map.Entry<String, ?> entry : map.entrySet()) {
				StringBuilder line = new StringBuilder(entry.getKey());
				line.append("=");

				if (entry.getValue() instanceof Long)
					line.append(entry.getValue());
				else {
					List<Long> list = (List<Long>) entry.getValue();
					for (int i = 0; i < list.size(); i++) {
						if (i != 0)
							line.append(",");
						line.append(list.get(i));
					}
				}
				writer.write(line.toString());
				writer.newLine();
			}
			writer.close();
		}
	}

	public String getHashMapFilePath(String path, Map<?, ?> mapa) {

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
		String fileNameWithOutExt = FileTools.removeExtension(p.getFileName().toString());

		// TODO moze zrobic osobny folder na wsyztskie dodawane pliki??
		File dir = new File("resultFiles");
		dir.mkdir();

		String newPath = path.substring(0, path.lastIndexOf(File.separator));
		String dstPath = newPath + "\\" + fileNameWithOutExt + "-" + fileName + ".txt";
		return dstPath;

	}

	/**
	 * 
	 * @throws IOException
	 */
	private void openInputFile() throws IOException {
		String extension = FileTools.getExtension(this.path);

		System.out.println("openInputFile: path: " + this.path);

		if (extension.equals(GZIP_EXTENSION)) {
			InputStream gzipStream = new GZIPInputStream(new FileInputStream(this.path));
			reader = new BufferedReader(new InputStreamReader(gzipStream));
		} else if (extension.equals(FASTA_EXTENSION)) {
			this.reader = new BufferedReader(new FileReader(path));
		} else {
			throw new IOException("Unknown format");
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	private void closeInputFile() throws IOException {
		this.reader.close();
	}

}
