package model.reader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.FastaRecord;

public class FastaSGDRecordParser extends FastaRecordParser {

	private String line = "YLR057W YLR057W SGDID:S000004047, Chr XII from 255306-257855, Genome "
			+ "Release 64-1-1, Uncharacterized ORF, Putative protein of unknown function; YLR050W is not an essential gene";
	private static final String REGEXP = "SGDID:([^,]*), ([^\n\r]*)(.*)";

	public FastaSGDRecordParser() {
		super("SGD");
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
