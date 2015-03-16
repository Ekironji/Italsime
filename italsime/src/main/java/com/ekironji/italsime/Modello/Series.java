package com.ekironji.italsime.Modello;

import java.util.Arrays;
import java.util.List;

public class Series {
	
	public static final int ALL_SERIES		= 0;
	
	/**    CLOSED BLADE SERIES   **/
	
	public static final int SERIE_TRM		= 1;
	public static final int SERIE_TRU		= 2;
	public static final int SERIE_VPMR      = 3;
	public static final int SERIE_TGR		= 4;
	public static final int SERIE_RS		= 5;
	public static final int SERIE_LBT		= 6;
	public static final int SERIE_TVC		= 7;
	public static final int SERIE_TVP		= 8;
	public static final int SERIE_TVG		= 9;
	
	public static final String PREFIX_TRM	= "TRM";
	public static final String PREFIX_TRU	= "TRU";
	public static final String PREFIX_VPMR  = "VPMR";
	public static final String PREFIX_TGR	= "TGR";
	public static final String PREFIX_RS	= "RS";
	public static final String PREFIX_LBT	= "LBT";
	public static final String PREFIX_TVC	= "TVC";
	public static final String PREFIX_TVP	= "TVP";
	public static final String PREFIX_TVG	= "TVG";
	
	static List<String> seriesClosedBlade = Arrays.asList(PREFIX_TRM, 
													PREFIX_TRU,
                                                    PREFIX_VPMR,
													PREFIX_TGR,
													PREFIX_RS,
													PREFIX_LBT,
													PREFIX_TVC,
													PREFIX_TVP,
													PREFIX_TVG);
	
	/**    OPENED BLADE SERIES   **/
	
	public static final int SERIE_TZM		= 10;
	public static final int SERIE_TM		= 11;
	public static final int SERIE_XM 		= 12;
	public static final int SERIE_TZA		= 13;
	public static final int SERIE_TZB		= 14;
	public static final int SERIE_ALA		= 15;
	public static final int SERIE_RO 		= 16;
	
	public static final String PREFIX_TZM	= "TZM";
	public static final String PREFIX_TM	= "TM";
	public static final String PREFIX_XM	= "XM";
	public static final String PREFIX_TZA	= "TZA";
	public static final String PREFIX_TZB	= "TZB";
	public static final String PREFIX_ALA	= "ALA";
	public static final String PREFIX_RO	= "RO";
	
	static List<String> seriesOpenedBlade = Arrays.asList(PREFIX_TZM, 
													PREFIX_TM,
													PREFIX_XM,
													PREFIX_TZA,
													PREFIX_TZB,
													PREFIX_ALA,
													PREFIX_RO);
	
	public static int getIntFromName(String name) {
		
		if (name.startsWith(PREFIX_TRM)) return SERIE_TRM;
		else if (name.startsWith(PREFIX_TRU)) return SERIE_TRU;
		else if (name.startsWith(PREFIX_VPMR)) return SERIE_VPMR;
		else if (name.startsWith(PREFIX_TGR)) return SERIE_TGR;
		else if (name.startsWith(PREFIX_RS)) return SERIE_RS;
		else if (name.startsWith(PREFIX_LBT)) return SERIE_LBT;
		else if (name.startsWith(PREFIX_TVC)) return SERIE_TVC;
		else if (name.startsWith(PREFIX_TVP)) return SERIE_TVP;
		else if (name.startsWith(PREFIX_TVG)) return SERIE_TVG;
		else if (name.startsWith(PREFIX_TZM)) return SERIE_TZM;
		else if (name.startsWith(PREFIX_TM)) return SERIE_TM;
		else if (name.startsWith(PREFIX_XM)) return SERIE_XM;
		else if (name.startsWith(PREFIX_TZA)) return SERIE_TZA;
		else if (name.startsWith(PREFIX_TZB)) return SERIE_TZB;
		else if (name.startsWith(PREFIX_ALA)) return SERIE_ALA;
		else if (name.startsWith(PREFIX_RO)) return SERIE_RO;
		
		return 0;
	}
	
	public static String getNameFromInt(int idserie) {
		switch(idserie) {
		case ALL_SERIES:
			return "All Series";
		case SERIE_TRM:
			return PREFIX_TRM;
		case SERIE_TRU:
			return PREFIX_TRU;
		case SERIE_VPMR:
			return PREFIX_VPMR;
		case SERIE_TGR:
			return PREFIX_TGR;
		case SERIE_RS:
			return PREFIX_RS;
		case SERIE_LBT:
			return PREFIX_LBT;
		case SERIE_TVC:
			return PREFIX_TVC;
		case SERIE_TVP:
			return PREFIX_TVP;
		case SERIE_TVG:
			return PREFIX_TVG;
		case SERIE_TZM:
			return PREFIX_TZM;
		case SERIE_TM:
			return PREFIX_TM;
		case SERIE_XM:
			return PREFIX_XM;
		case SERIE_TZA:
			return PREFIX_TZA;
		case SERIE_TZB:
			return PREFIX_TZB;
		case SERIE_ALA:
			return PREFIX_ALA;
		case SERIE_RO:
			return PREFIX_RO;
		default:
			return "serie_not_found";
		}
	}
	
	public static List<String> getSeriesListByAriaType(int ariaType) {
		if(ariaType == Modello.ARIA_PULITA) return seriesClosedBlade;
		else if (ariaType == Modello.ARIA_SPORCA) return seriesOpenedBlade;
		return null;
	}
	
}
