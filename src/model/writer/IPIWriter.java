package model.writer;

import model.FastaRecord;

public class IPIWriter extends FastaWriter {

	private String descLine;

	public IPIWriter() {
		super("IPI");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getDescLine(FastaRecord record) {
		descLine = null;
		if (record != null) {
			descLine = ">IPI:" + record.getIdentyfier() + "|" + record.getEnteryName();
		}
		return descLine;
	}
}
