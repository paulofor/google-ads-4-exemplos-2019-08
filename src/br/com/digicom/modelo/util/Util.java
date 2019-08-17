package br.com.digicom.modelo.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Util {
	
	public static String getDataAtualLoopback() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(Calendar.getInstance().getTime());
	}
}
