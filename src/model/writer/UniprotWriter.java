package model.writer;

import model.FastaRecord;

public class UniprotWriter extends Writer {

	private String descLine;
	private String sequence;

	public UniprotWriter() {
		super();
	}

	@Override
	public String getSequenceLine(FastaRecord record) {

		sequence = null;
		if (record != null) {
			sequence = record.getSequence();
		}
		return sequence;
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
