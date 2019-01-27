package model.reader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.FastaRecord;

public class FastaNCBIRecordParser extends FastaRecordParser {

	private String line = "gi|385862198|ref|NP_001245340.1| sodium/myo-inositol cotransporter 2 isoform 2 [Homo sapiens]";
	private static final String REGEXP = ">gi\\|([0-9]*)\\|([^\n\r]*)(.*)";

	public FastaNCBIRecordParser() {
		super("NCBI");
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
		// TODO Auto-generated method stub
		return REGEXP;
	}

}
