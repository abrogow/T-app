package model.reader;

import model.FastaRecord;

public class FastaIPIRecordParser extends FastaRecordParser {

	private String line = "IPI:IPI00000023.4|SWISS-PROT:P18507|TREMBL:Q9UDB3|ENSEMBL:ENSP00000354651|REFSEQ:NP_000807|H-INV:HIT000321503|VEGA:OTTHUMP00000160874 Tax_Id=9606 Gene_Symbol=GABRG2 Gamma-aminobutyric-acid receptor subunit gamma-2 precursor";

	public FastaIPIRecordParser() {
		super("IPI");
	}

	@Override
	public FastaRecord parse(String recordStr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRegExp() {
		// TODO Auto-generated method stub
		return null;
	}

}
