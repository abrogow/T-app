package model.reader;

import java.util.regex.Pattern;

import model.FastaRecord;

public abstract class FastaRecordParser
{
	protected Pattern pattern=null;
	
	public FastaRecordParser()
	{
		this.pattern = Pattern.compile(this.getRegExp(), Pattern.DOTALL);
	}
	
	//
	//Abstract API
	public abstract FastaRecord parse(String recordStr);
	public abstract String getRegExp();
}
