package br.com.digicom.adsservice;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.digicom.modelo.CampanhaAds;

public class UtilAds {
	
	
	private static CampanhaAds campanha;
	
	
	public static void setCampanha(CampanhaAds campanhaEnt) {
		campanha = campanhaEnt;
	}
	
	public static String getDataInicial() {
		return converteData(getDataInicialCal());
	}
	public static String getDataFinal() {
		return converteData(getDataFinalCal());
	}
	
	private String getDataInicialDB() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dia = sdf.format(getDataInicialCal().getTime());
		return dia;
	}
	private String getDataFinalDB() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dia = sdf.format(getDataFinalCal().getTime());
		return dia;
	}
	
	
	private static Calendar getDataInicialCal() {
		Calendar date1 = Calendar.getInstance();
		date1.add(Calendar.DATE, 1);
		while (date1.get(Calendar.DAY_OF_WEEK) != getPosicaoDia(campanha.getSetupCampanha().getDiaSemanaInicio())) {
			date1.add(Calendar.DATE, 1);
		}
		return date1;
	}

	private static int getPosicaoDia(String dia) {
		if ("SUNDAY".equals(dia))
			return 1;
		if ("MONDAY".equals(dia))
			return 2;
		if ("TUESDAY".equals(dia))
			return 3;
		if ("WEDNESDAY".equals(dia))
			return 4;
		if ("THURSDAY".equals(dia))
			return 5;
		if ("FRIDAY".equals(dia))
			return 6;
		if ("SATURDAY".equals(dia))
			return 7;
		return 0;
	}

	private static String converteData(Calendar data) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(data.getTime());
	}

	private static String converteDataInicioDia(Calendar data) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dia = sdf.format(data.getTime());
		return dia;
	}

	private static String converteDataFinalDia(Calendar data) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dia = sdf.format(data.getTime());
		return dia;
	}

	private static Calendar getDataFinalCal() {
		Calendar date1 = getDataInicialCal();
		date1.add(Calendar.DATE, 1);

		while (date1.get(Calendar.DAY_OF_WEEK) != getPosicaoDia(campanha.getSetupCampanha().getDiaSemanaFinal())) {
			date1.add(Calendar.DATE, 1);
		}
		return date1;

	}

}
