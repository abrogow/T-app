package model.writer;

import model.FastaRecord;

public class NCBIWriter extends FastaWriter {

	private String descLine;

	public NCBIWriter() {
		super("NCBI");
	}

	@Override
	public String getDescLine(FastaRecord record) {
		// TODO Auto-generated method stub

		descLine = null;
		if (record != null) {
			descLine = ">gi|" + record.getIdentyfier() + "|" + record.getEnteryName();
			;
		}
		return descLine;

	}

}
