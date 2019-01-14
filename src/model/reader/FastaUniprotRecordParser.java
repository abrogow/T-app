package model.reader;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import model.FastaRecord;

public class FastaUniprotRecordParser extends FastaRecordParser {
	private static final String REGEXP = "\\>([a-zA-Z]*)\\|(.*?)\\|(.*?)(?: (.*?))?(?:OS=(.*?))?(?:GN=(.*?))?(?:PE=(.*?))?(?:SV=([1-9]*))?(?:[\n\r](.*))?";
	private static final String[] KEYS = { "identifier", "enteryName", "proteinName", "organismName", "geneName",
			"proteinExistence", "sequenceVersion", "sequence" };

	private Map<String, String> paramsMap = null;
	private FastaRecord fastaRecord;

	public FastaUniprotRecordParser() {
		super("UniProt");
		this.paramsMap = new HashMap<String, String>();
	}

	@Override
	public FastaRecord parse(String recordStr) {
		FastaRecord fastaRecord = null;

		paramsMap.clear();
		Matcher m = this.pattern.matcher(recordStr);
		paramsMap.clear();

		if (m.matches()) {
			for (int i = 0; i < m.groupCount() - 1; i++) {
				paramsMap.put(KEYS[i], m.group(i + 2));
				if (paramsMap.get(KEYS[i]) == null)
					paramsMap.put(KEYS[i], "");
			}
		} else
			return null;

		fastaRecord = new FastaRecord(paramsMap.get("identifier"), paramsMap.get("enteryName"),
				paramsMap.get("proteinName"), paramsMap.get("organismName"), paramsMap.get("geneName"),
				paramsMap.get("proteinExistence"), paramsMap.get("sequenceVersion"),
				paramsMap.get("sequence").replaceAll("\\s", ""));

		return (fastaRecord);
	}

	@Override
	public String getRegExp() {
		return (REGEXP);
	}

}
