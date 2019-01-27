package model.writer;

import model.FastaRecord;

public class SGDWriter extends FastaWriter {

	private String descLine;

	public SGDWriter() {
		super("SGD");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getDescLine(FastaRecord record) {
		descLine = null;
		if (record != null) {
			descLine = ">SGDID:" + record.getIdentyfier() + ", " + record.getEnteryName();
		}
		return descLine;
	}

}
