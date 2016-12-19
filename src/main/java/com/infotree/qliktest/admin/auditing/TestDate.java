package com.infotree.qliktest.admin.auditing;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TestDate {
	public static void main(String[] args) {
		DateTime dt = new DateTime();
		DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
		DateTime dtus = dt.withZone(dtZone);
		Date dateInIndia = dtus.toLocalDateTime().toDate();
		System.out.println("date in india is >>>>"+new java.sql.Date(dateInIndia.getTime()));
	}
	
	public static String convertTimeZones(final String fromTimeZoneString, 
	        final String toTimeZoneString) {
	    final DateTimeZone fromTimeZone = DateTimeZone.forID(fromTimeZoneString);
	    final DateTimeZone toTimeZone = DateTimeZone.forID(toTimeZoneString);
	    final DateTime dateTime = new DateTime(fromTimeZone);

	    final DateTimeFormatter outputFormatter 
	        = DateTimeFormat.forPattern("yyyy-MM-dd H:mm:ss").withZone(toTimeZone);
	    return outputFormatter.print(dateTime);
	}
}
