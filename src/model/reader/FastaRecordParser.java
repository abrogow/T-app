package model.reader;

import java.util.regex.Pattern;

import model.FastaRecord;

public abstract class FastaRecordParser {

	Pattern pattern = null;

	public FastaRecordParser() {

		pattern = Pattern.compile(this.getRegExp(), Pattern.DOTALL);
	}

	//
	// Abstract API
	public abstract FastaRecord parse(String recordStr);

	public abstract String getRegExp();
}
