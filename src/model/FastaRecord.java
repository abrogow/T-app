package model;

public class FastaRecord {

	private String identifier;
	private String enteryName;
	private String proteinName;
	private String organismName;
	private String geneName;
	private String proteinExistence;
	private String sequenceVersion;
	private String sequence;

	public FastaRecord() {

	}

	public FastaRecord(String identifier, String enteryName, String proteinName, String organismName, String geneName,
			String proteinExistence, String sequenceVersion, String sequence) {

		this.identifier = identifier;
		this.enteryName = enteryName;
		this.proteinName = proteinName;
		if (organismName == null)
			System.out.println("NULL");
		this.organismName = organismName;
		this.geneName = geneName;
		this.proteinExistence = proteinExistence;
		this.sequenceVersion = sequenceVersion;
		this.sequence = sequence;

	}

	public FastaRecord(String identifier, String enteryName, String organismName, String sequence) {

		this.identifier = identifier;
		this.enteryName = enteryName;
		this.sequence = sequence;
		this.organismName = organismName;
	}

	public String getIdentyfier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getEnteryName() {
		return enteryName;
	}

	public void setEnteryName(String enteryName) {
		this.enteryName = enteryName;
	}

	public String getProteinName() {
		return proteinName;
	}

	public void setProteinName(String proteinName) {
		this.proteinName = proteinName;
	}

	public String getOrganismName() {
		return organismName;
	}

	public void setOrganismName(String organismName) {
		this.organismName = organismName;
	}

	public String getGenemName() {
		return geneName;
	}

	public void setGeneName(String geneName) {
		this.geneName = geneName;
	}

	public String getProteinExistence() {
		return proteinExistence;
	}

	public void setProteinExistence(String proteinExistence) {
		this.proteinExistence = proteinExistence;
	}

	public String getSequenceVersion() {
		return sequenceVersion;
	}

	public void setSequenceVersion(String sequenceVersion) {
		this.sequenceVersion = sequenceVersion;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

}
