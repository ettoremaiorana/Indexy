package com.fourcasters.forec.reconciler.query;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.file.Path;

public abstract class BufferedFileParser {

	final String parseLine(ByteBuffer bb) throws IOException {
		StringBuilder input = new StringBuilder();
		int c = -1;
		boolean eol = false;
		while (!eol) {
			if (!bb.hasRemaining()) {
				break;
			}
			switch ((c = bb.get())) {
			case -1:
			case '\n':
				eol = true;
				break;
			case '\r':
				if (bb.hasRemaining()) {
					eol = true;
					if ((bb.get()) != '\n') {
						throw new RuntimeException();
					}
					break;
				}
				input.append((char)c);
				break;
			default:
				input.append((char)c);
				break;
			}
		}
		if (!eol) {
			bb.position(bb.limit() - input.length());
			return null;
		}
		if ((c == -1) && (input.length() == 0)) {
			return null;
		}
		return input.toString();
	}

	void walkThroughFile(Path path, long start, RecordBuilder recordBuilder)
			throws IOException, FileNotFoundException {
		try(RandomAccessFile sc = new RandomAccessFile(path.toFile(), "r");){
			String recordAsString;
			byte[] buff = new byte[4096];
			boolean finished = false;
			boolean shouldContinue = true;
			int rem = 0;
			Record prev = null;
			if (start > 0) {
				sc.seek(start);
			}
			long offset = start;
			while (!finished && shouldContinue) {
				int bytesRead = sc.read(buff, rem, 4096);
				ByteBuffer bb = ByteBuffer.wrap(buff);
				int initPosition = bb.position();
				while (shouldContinue && (recordAsString = parseLine(bb)) != null) {
					int endPosition = bb.position();
					int bytesReadLastRecord = endPosition - initPosition;
					Record curr = recordBuilder.newRecord(recordAsString);
					shouldContinue = onNewRecord(curr, prev, offset, bytesReadLastRecord);
					prev = curr;
					initPosition = endPosition;
					offset += bytesReadLastRecord; // /r/n
				}
				rem = 0;
				if (bb.remaining() > 0 ) {
					rem = bb.remaining();
				}
				buff = new byte[4096+rem];
				bb.get(buff, 0 , rem);
				if (bytesRead < 4096) {
					finished = true;
				}
			}
		}
	}

	protected abstract boolean onNewRecord(Record curr, Record prev, long bytesReadSoFar, int recordLengt);
}
