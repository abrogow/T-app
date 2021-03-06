package model.reader;

import java.util.regex.Pattern;

import model.FastaRecord;

public abstract class FastaRecordParser {

	Pattern pattern = null;
	private static final String UNIPROT_PARSER = "UniProt";
	private static final String NCBI_PARSER = "NCBI";
	private static final String IPI_PARSER = "IPI";
	private static final String TAIR_PARSER = "TAIR";
	private static final String SGD_PARSER = "SGD";

	private static FastaRecordParser instance = null;

	public FastaRecordParser(String parserType) {

		pattern = Pattern.compile(this.getRegExp(), Pattern.DOTALL);
	}

	public static FastaRecordParser getInstance(String parserType) {
		if (instance == null) {
			if (UNIPROT_PARSER.equals(parserType))
				return new FastaUniprotRecordParser();
			if (NCBI_PARSER.equals(parserType))
				return new FastaNCBIRecordParser();
			if (IPI_PARSER.equals(parserType))
				return new FastaIPIRecordParser();
			if (TAIR_PARSER.equals(parserType))
				return new FastaTAIRRecordParser();
			if (SGD_PARSER.equals(parserType))
				return new FastaSGDRecordParser();
			else
				return null;
		}
		return instance;
	}

	//
	// Abstract API
	public abstract FastaRecord parse(String recordStr);

	public abstract String getRegExp();
}
