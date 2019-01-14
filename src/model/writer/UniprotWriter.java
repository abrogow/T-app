package model.writer;

import model.FastaRecord;

public class UniprotWriter extends Writer {

	private String descLine;

	public UniprotWriter() {
		super("UniProt");
	}

	@Override
	public String getDescLine(FastaRecord record) {
		// TODO Auto-generated method stub
		descLine = null;
		if (record != null) {
			descLine = ">gi|" + record.getIdentyfier() + "|" + record.getEnteryName() + " [" + record.getOrganismName()
					+ "]";
		}
		return descLine;
	}

}
