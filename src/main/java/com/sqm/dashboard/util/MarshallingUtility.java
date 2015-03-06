package com.sqm.dashboard.util;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class MarshallingUtility {
	
	/**
	 * DefaultConstructor for the class MarshallingUtils
	 */
	private MarshallingUtility() {
	}
	
	/**
	 * 
	 * @param c of type Class
	 * @param xml of type String
	 * @return unmarshall XML
	 * @throws JAXBException
	 */

	@SuppressWarnings("unchecked")
	public static <T> T marshal(Class<T> c, String xml) throws JAXBException {
		T res;
		if (c == xml.getClass()) {
			res = (T) xml;
		} else {
			JAXBContext ctx = JAXBContext.newInstance(c);
			Unmarshaller marshaller = ctx.createUnmarshaller();
			res = (T) marshaller.unmarshal(new StringReader(xml));
		}
		return res;
	}
	
	/**
	 * 
	 * @param c of type Class
	 * @param o of type Object
	 * @return entityString of type String
	 * @throws Exception
	 */

	public static <T> String unmarshal(Class<T> c, Object o) throws Exception {
		JAXBContext ctx = JAXBContext.newInstance(c);
		Marshaller marshaller = ctx.createMarshaller();
		StringWriter entityXml = new StringWriter();
		marshaller.marshal(o, entityXml);
		String entityString = entityXml.toString();
		return entityString;
	}
}
