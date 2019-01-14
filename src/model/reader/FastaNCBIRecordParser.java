package model.reader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.FastaRecord;

public class FastaNCBIRecordParser extends FastaRecordParser {

	private String line = "gi|385862198|ref|NP_001245340.1| sodium/myo-inositol cotransporter 2 isoform 2 [Homo sapiens]";
	private static final String REGEXP = ">gi\\|([0-9]*)\\|(.*) \\[(.*)\\](?:[\n\r](.*))?";
	private String poprzedniaWersjaRegExp = ">gi\\|([0-9]*)\\|(.*) \\[(.*)\\](?:[\n\r](.*))?";

	public FastaNCBIRecordParser() {
		super("NCBI");
	}

	@Override
	public FastaRecord parse(String recordStr) {
		String id = null, name = null, sequence = null, organismName = null;
		FastaRecord fastaRecord = null;

		Matcher matcher = Pattern.compile(REGEXP).matcher(recordStr);
		if (matcher.find() && matcher.groupCount() > 0) {

			id = matcher.group(1);
			name = matcher.group(2);
			organismName = matcher.group(3);
			sequence = matcher.group(4);
		} else
			return null;

		fastaRecord = new FastaRecord(id, name, organismName, sequence);
		return (fastaRecord);
	}

	@Override
	public String getRegExp() {
		// TODO Auto-generated method stub
		return REGEXP;
	}

}
