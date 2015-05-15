package com.young.tools.common.util.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateUtils {

	public static String getDateString(Date date,String format){
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
}
