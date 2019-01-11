package model.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class DatabaseDownloader {

	public final static String UNIPROT_SWISSPROT_DOWNLOAD = "ftp://ftp.uniprot.org/pub/databases/uniprot/current_release/knowledgebase/complete/uniprot_sprot.fasta.gz";
	public final static String UNIPROT_TREMBL_DOWNLOAD = "ftp://ftp.uniprot.org/pub/databases/uniprot/current_release/knowledgebase/complete/uniprot_trembl.fasta.gz";
	public final static String NCBI_DOWNLOAD = "ftp://ftp.ncbi.nlm.nih.gov/blast/db/FASTA/nr.gz";
	public final static String SGD_DOWNLOAD = "https://downloads.yeastgenome.org/sequence/S288C_reference/orf_protein/orf_trans.fasta.gz";
	public final static String TAIR_DOWNLOAD = "ftp://ftp.arabidopsis.org/home/tair/Sequences/blast_datasets/TAIR10_blastsets/TAIR10_pep_20101214_updated";

	/**
	 * Funkcja œci¹ga okreœlony plik FASTA na dysk
	 * 
	 * @param url
	 * @param filename
	 * @throws IOException
	 */
	public static void downloadDatabase(String dataBaseType, String path, String filename) throws IOException {

		URL website = new URL(getUrlFromDBType(dataBaseType));

		ReadableByteChannel inChannel = Channels.newChannel(website.openStream());

		String dstPath = path + "\\" + filename + ".gz";
		System.out.println(dstPath);
		FileOutputStream outStream = new FileOutputStream(dstPath);
		FileChannel outChannel = outStream.getChannel();

		outChannel.transferFrom(inChannel, 0, Long.MAX_VALUE);

		outStream.close();
		outChannel.close();
		inChannel.close();
	}

	public static String getUrlFromDBType(String dataBaseType) {

		if (dataBaseType.equals("UniProt-SwissProt"))
			return UNIPROT_SWISSPROT_DOWNLOAD;
		else if (dataBaseType.equals("UniProt-TREMBL"))
			return UNIPROT_TREMBL_DOWNLOAD;
		else if (dataBaseType.equals("NCBI"))
			return NCBI_DOWNLOAD;
		else if (dataBaseType.equals("SGD"))
			return SGD_DOWNLOAD;
		else if (dataBaseType.equals("TAIR"))
			return TAIR_DOWNLOAD;
		else
			return null;
	}
}
