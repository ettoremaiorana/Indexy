package com.fourcasters.forec.reconciler.query;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class IndexableDAO {

	private final Map<String, TreeMap<Long, Long>> indexes = new HashMap<>(16);

	public abstract Path getFilePath(String cross);
	public abstract RecordBuilder getRecordBuilder(String recordFormat);
	public abstract RecordBuilder getRecordBuilder();
	public abstract void onIndexingEnd(String cross);
	public abstract String getDefaultFormat();

	protected TreeMap<Long, Long> javaDbHash(String cross) throws IOException {
		String format = getDefaultFormat();
		return javaDbHash(cross, format);
	}

	protected TreeMap<Long, Long> javaDbHash(String cross, String recordFormat) throws IOException {
		cross = cross.toLowerCase();
		TreeMap<Long, Long> hash = new TreeMap<>();
		final Path path = getFilePath(cross);
		RecordBuilder recordBuilder = getRecordBuilder(recordFormat);
		new BufferedFileParser() {
			@Override
			protected boolean onNewRecord(Record curr, Record prev, long bytesReadSoFar, int recordLength) {
				if (!curr.shouldIndex(prev)) {
					hash.put(curr.index(), bytesReadSoFar);
				}
				return true;
			}
		}.walkThroughFile(path, 0, recordBuilder);
		return hash;
	}

	public boolean dbhash(String cross, String format) throws IOException {
		boolean result = indexes.put(cross, javaDbHash(cross, format)) == null;
		onIndexingEnd(cross);
		return result;
	}
	public boolean dbhash(String cross) throws IOException {
		return indexes.put(cross, javaDbHash(cross)) == null;
	}
	public int hashCount(String cross) {
		TreeMap<Long, Long> index = indexes.get(cross);
		if (index == null) {
			return -1;
		}
		return index.size();
	}

	public long offset(String cross, Date date, boolean exact) throws IOException {
		long timestamp = date.getTime();

		TreeMap<Long, Long> index = indexes.get(cross);
		if (index == null) {
			return -1;
		}
		Entry<Long, Long> checkpoint = index.floorEntry(timestamp);
		if (checkpoint == null) {
			return -1;
		}
		final Path path = getFilePath(cross);
		RecordBuilder hrb = getRecordBuilder();
		final AtomicLong offset = new AtomicLong(-1);
		new BufferedFileParser() {
			@Override
			protected boolean onNewRecord(Record curr, Record prev, long bytesReadSoFar, int recordLength) {
				if (curr.index() >= timestamp) {
					offset.set(exact ? bytesReadSoFar + recordLength
									 : bytesReadSoFar);
					return false;
				}
				return true;
			}
		}.walkThroughFile(path, checkpoint.getValue(), hrb);
		return offset.get();
	}

	public abstract Path getRootPath();

	public void dbhashAll(String format) throws IOException {
		final File root = getRootPath().toFile();
		if (!root.isDirectory()) {
			throw new IllegalArgumentException("Unable to read table files from non directory " + root);
		}
		for(File f : root.listFiles()) {
			String cross = f.getName();
			int pos = cross.lastIndexOf(".");
			if (pos > 0) {
				cross = cross.substring(0, pos);
			}
			dbhash(cross, format);
		}
	}

	public void dbhashAll() throws IOException {
		String format = getDefaultFormat();
		dbhashAll(format);
	}
}
