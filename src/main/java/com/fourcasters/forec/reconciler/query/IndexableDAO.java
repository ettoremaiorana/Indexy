package com.fourcasters.forec.reconciler.query;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;

public abstract class IndexableDAO {

	protected static final BiFunction <Long, Integer, Long> projectFirst = new BiFunction<Long, Integer, Long>() {
		@Override
		public Long apply(Long t, Integer u) {
			return t;
		}
	};
	protected static final BiFunction<Long, Integer, Long> sum = new BiFunction<Long, Integer, Long>() {
		@Override
		public Long apply(Long t, Integer u) {
			return t + u.longValue();
		}
	};
	private final Map<String, TreeMap<Long, Long>> indexes = new HashMap<>(16);

	public abstract Path getRootPath();
	public abstract Path getFilePath(String cross);
	public abstract RecordBuilder getRecordBuilder(String recordFormat);
	public abstract RecordBuilder getRecordBuilder();
	public abstract void onIndexingEnd(String cross);

	protected TreeMap<Long, Long> createIndex0(String cross) throws IOException {
		return createdIndex0(cross);
	}

	protected TreeMap<Long, Long> createdIndex0(String cross) throws IOException {
		RecordBuilder recordBuilder = getRecordBuilder();
		return createIndex1(cross, recordBuilder);
	}
	protected TreeMap<Long, Long> createdIndex0(String cross, String recordFormat) throws IOException {
		RecordBuilder recordBuilder = getRecordBuilder(recordFormat);
		return createIndex1(cross, recordBuilder);
	}
	private TreeMap<Long, Long> createIndex1(String cross, RecordBuilder recordBuilder)
			throws IOException, FileNotFoundException {

		TreeMap<Long, Long> hash = new TreeMap<>();
		final Path path = getFilePath(cross);
		new BufferedFileParser() {
			@Override
			protected boolean onNewRecord(Record curr, Record prev, long bytesReadSoFar, int recordLength) {
				if (curr.shouldIndex(prev)) {
					hash.put(curr.index(), bytesReadSoFar);
				}
				return true;
			}
		}.walkThroughFile(path, 0, recordBuilder);
		return hash;
	}

	public boolean createIndex(String table, String recordFormat) throws IOException {
		boolean result = indexes.put(table, createdIndex0(table, recordFormat)) == null;
		onIndexingEnd(table);
		return result;
	}
	public boolean createIndex(String table) throws IOException {
		return indexes.put(table, createIndex0(table)) == null;
	}
	public int indexSize(String table) {
		TreeMap<Long, Long> index = indexes.get(table);
		if (index == null) {
			return -1;
		}
		return index.size();
	}

	protected final long offset(String cross, long toSearch) throws IOException {
		return offset(cross, toSearch, projectFirst);
	}
	protected final long offset(String cross, long toSearch, BiFunction<Long, Integer, Long> f) throws IOException {
		long timestamp = toSearch;

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
					offset.set(f.apply(bytesReadSoFar, recordLength));
					return false;
				}
				return true;
			}
		}.walkThroughFile(path, checkpoint.getValue(), hrb);
		return offset.get();
	}
	protected final long offsetExact(String cross, long toSearch) throws IOException {
		return offsetExact(cross, toSearch, projectFirst);
	}
	protected final long offsetExact(String cross, long toSearch, BiFunction<Long, Integer, Long> f) throws IOException {
		long timestamp = toSearch;

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
				if (curr.index() == timestamp) {
					offset.set(f.apply(bytesReadSoFar, recordLength));
					return false;
				}
				if (curr.index() > timestamp) {
					return true;
				}
				return true;
			}
		}.walkThroughFile(path, checkpoint.getValue(), hrb);
		return offset.get();
	}

	public void createIndexAll(String format) throws IOException {
		final File root = validateRootFolder();
		for(File f : root.listFiles()) {
			String table = validateAndGetTableName(f);
			createIndex(table, format);
		}
	}

	public void createIndexAll() throws IOException {
		final File root = validateRootFolder();
		for(File f : root.listFiles()) {
			String table = validateAndGetTableName(f);
			createIndex(table);
		}
	}
	private String validateAndGetTableName(File f) {
		String table = f.getName();
		int pos = table.lastIndexOf(".");
		if (pos > 0) {
			table = table.substring(0, pos);
		}
		return table;
	}
	private File validateRootFolder() {
		final File root = getRootPath().toFile();
		if (!root.isDirectory()) {
			throw new IllegalArgumentException("Unable to read table files from non directory " + root);
		}
		return root;
	}
}
