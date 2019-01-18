package model.writer;

import model.FastaRecord;

public class UniprotBuilder extends Writer {

	private String descLine;

	public UniprotBuilder() {
		super("UniProt");
	}

	@Override
	public String getDescLine(FastaRecord record) {
		// TODO Auto-generated method stub
		descLine = null;
		if (record != null) {
			descLine = ">sp|" + record.getIdentyfier() + "|" + record.getEnteryName() + " " + record.getProteinName()
					+ "OS=" + record.getOrganismName() + "GN=" + record.getGenemName() + "PE="
					+ record.getProteinExistence() + "SV=" + record.getSequenceVersion();
		}
		return descLine;
	}

}
