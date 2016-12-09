package com.fourcasters.forec.reconciler.query;

public abstract class Record {

	public abstract boolean shouldIndex(Record prev);

	public abstract Long index();
}
