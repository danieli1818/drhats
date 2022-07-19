package drhats.utils.parsers;

import drhats.exceptions.parse.ParseException;

public interface Parser<T, S> {

	public T parse(S object) throws ParseException;
	
}
