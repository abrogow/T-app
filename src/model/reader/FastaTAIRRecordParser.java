package model.reader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.FastaRecord;

public class FastaTAIRRecordParser extends FastaRecordParser {

	private String line = "AT1G44090.1 | Symbols: ATGA20OX5, GA20OX5 | gibberellin 20-oxidase 5 | chr1:16760677-16762486 REVERSE LENGTH=385";
	private static final String REGEXP = ">([^\\| ]*) \\| Symbols\\: ([^\n\r]*)(.*)";

	public FastaTAIRRecordParser() {
		super("TAIR");
	}

	@Override
	public FastaRecord parse(String recordStr) {
		String id = null, name = null, sequence = null;
		FastaRecord fastaRecord = null;

		Matcher matcher = Pattern.compile(REGEXP, Pattern.DOTALL).matcher(recordStr);
		if (matcher.find() && matcher.groupCount() > 0) {

			id = matcher.group(1);
			name = matcher.group(2);
			sequence = matcher.group(3);

			int j;
			for (j = 0; j < sequence.length(); j++) {
				if (sequence.charAt(j) != '\n' && sequence.charAt(j) != '\r')
					break;
			}
			sequence = sequence.substring(j);
		} else
			return null;

		fastaRecord = new FastaRecord(id, name, sequence);
		return (fastaRecord);
	}

	@Override
	public String getRegExp() {
		return REGEXP;
	}
}
