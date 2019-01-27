package model.reader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.FastaRecord;

public class FastaIPIRecordParser extends FastaRecordParser {

	private String line = "IPI:IPI00000023.4|SWISS-PROT:P18507|TREMBL:Q9UDB3|ENSEMBL:ENSP00000354651|REFSEQ:NP_000807|H-INV:HIT000321503|VEGA:OTTHUMP00000160874 Tax_Id=9606 Gene_Symbol=GABRG2 Gamma-aminobutyric-acid receptor subunit gamma-2 precursor";
	private static final String REGEXP = ">IPI:([^\\| .]*)([^\n\r]*)(.*)";

	public FastaIPIRecordParser() {
		super("IPI");
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
