package model.writer;

import model.FastaRecord;

public class TAIRWriter extends FastaWriter {

	private String descLine;

	public TAIRWriter() {
		super("TAIR");

	}

	@Override
	public String getDescLine(FastaRecord record) {

		descLine = null;
		if (record != null) {
			descLine = ">sp|" + record.getIdentyfier() + " | Symbols: " + record.getEnteryName();
		}
		return descLine;
	}

}
