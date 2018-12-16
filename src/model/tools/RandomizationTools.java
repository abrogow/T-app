package model.tools;

import java.io.IOException;
import java.util.Random;

import model.FastaRecord;
import model.builder.FastaRecordBuilder;
import model.writer.Writer;

final public class RandomizationTools {
	public final static char AMINOACIDS[] = { 'L', 'A', 'G', 'S', 'V', 'E', 'K', 'I', 'T', 'R', 'D', 'P', 'N', 'F', 'Q',
			'Y', 'M', 'H', 'C', 'W', 'U', 'X', 'B', 'Z', 'J', 'O' };

	private final static String RANDOM_SEQUENCE = "Losowe sekwencje";
	private final static String REVERSED_SEQUENCE = "Odwrócone sekwencje";

	private Writer writer;
	private FastaRecordBuilder builder;

	public String getRandomRecord(FastaRecord record, String srcPath, Writer writer, String seqType)
			throws IOException {

		StringBuilder rec = new StringBuilder();
		String lineSeparator = FileTools.getLineSeparator(srcPath);
		String desc = writer.getDescLine(record);
		String seq = writer.getSequenceLine(record);
		StringBuilder newSeq = new StringBuilder();
		;

		if (RANDOM_SEQUENCE.equals(seqType))
			newSeq = new StringBuilder(getRandomSequence(seq));
		if (REVERSED_SEQUENCE.equals(seqType))
			newSeq = new StringBuilder(getReversedSequence(seq));
		rec.append(desc);
		rec.append(lineSeparator);
		rec.append(newSeq.toString());

		return rec.toString();
	}

	static public double[] getAminoacidProbs(String sequence) {
		double probs[] = null;
		int sequenceLength = -1;

		probs = new double[26];
		sequenceLength = sequence.length();

		for (int i = 0; i < sequenceLength; i++)
			probs[RandomizationTools.aminoacid2index(sequence.charAt(i))] += 1;

		for (int i = 0; i < 26; i++)
			probs[i] /= sequenceLength;

		return (probs);
	}

	static public double[] getAminoacidDistrib(String sequence) {
		double distrib[] = null;

		distrib = RandomizationTools.getAminoacidProbs(sequence);

		for (int i = 1; i < 26; i++)
			distrib[i] += distrib[i - 1];

		return (distrib);
	}

	// losowe sekwencje
	static public String getRandomSequence(String sequence) {
		return (RandomizationTools.getRandomSequence(sequence.length(),
				RandomizationTools.getAminoacidDistrib(sequence)));
	}

	static public String getRandomSequence(int sequenceLength, double distrib[]) {
		char randSequence[] = null;
		Random randGen = null;
		double randNum = -1;

		randGen = new Random(System.nanoTime());
		randSequence = new char[sequenceLength];

		for (int i = 0; i < sequenceLength; i++) {
			randNum = randGen.nextDouble();
			for (int j = 0; j < 26; j++)
				if (randNum < distrib[j]) {
					randSequence[i] = RandomizationTools.AMINOACIDS[j];
					break;
				}
		}

		return (new String(randSequence));
	}

	// odwrocone sekwencje
	static public String getReversedSequence(String sequence) {
		String revSeq = null;

		if (sequence != null) {
			char revSeqChar[] = new char[sequence.length()];

			for (int j = 0; j < sequence.length(); j++)
				revSeqChar[j] = sequence.charAt(sequence.length() - j - 1);

			revSeq = new String(revSeqChar);
		}
		return (revSeq);
	}

	static private int aminoacid2index(char c) {
		if (c == 'L')
			return (0);
		else if (c == 'A')
			return (1);
		else if (c == 'G')
			return (2);
		else if (c == 'S')
			return (3);
		else if (c == 'V')
			return (4);
		else if (c == 'E')
			return (5);
		else if (c == 'K')
			return (6);
		else if (c == 'I')
			return (7);
		else if (c == 'T')
			return (8);
		else if (c == 'R')
			return (9);
		else if (c == 'D')
			return (10);
		else if (c == 'P')
			return (11);
		else if (c == 'N')
			return (12);
		else if (c == 'F')
			return (13);
		else if (c == 'Q')
			return (14);
		else if (c == 'Y')
			return (15);
		else if (c == 'M')
			return (16);
		else if (c == 'H')
			return (17);
		else if (c == 'C')
			return (18);
		else if (c == 'W')
			return (19);
		else if (c == 'U')
			return (20);
		else if (c == 'X')
			return (21);
		else if (c == 'B')
			return (22);
		else if (c == 'Z')
			return (23);
		else if (c == 'J')
			return (24);
		else if (c == 'O')
			return (25);
		return (0);
	}
};