package com.ekironji.italsime.Modello;

public class Modello {
	
	private int id;
	private String Name;
	private String ariaType;
	public static String ARIA_PULITA = "pulita";
	public static String ARIA_SPORCA = "sporca";
	private double Kw;
	private int Rpm;
	private int misura1, misura2, misura3, misura4, misura5, misura6, misura7, misura8, misura9, 
				misura10, misura11, misura12, misura13, misura14, misura15, misura16, misura17, misura18;
	private int Kg;
	private int m3h1, m3h2, m3h3, m3h4, m3h5;
	
	
	public Modello(){}

	public Modello(String name, String ariaType, double kw, int rpm, int misura1,
			int misura2, int misura3, int misura4, int misura5, int misura6,
			int misura7, int misura8, int misura9, int misura10, int misura11,
			int misura12, int misura13, int misura14, int misura15,
			int misura16, int misura17, int misura18, int kg, int m3h1,
			int m3h2, int m3h3, int m3h4, int m3h5) {
		super();
		this.Name = name;
		this.ariaType = ariaType;
		this.Kw = kw;
		this.Rpm = rpm;
		this.misura1 = misura1;
		this.misura2 = misura2;
		this.misura3 = misura3;
		this.misura4 = misura4;
		this.misura5 = misura5;
		this.misura6 = misura6;
		this.misura7 = misura7;
		this.misura8 = misura8;
		this.misura9 = misura9;
		this.misura10 = misura10;
		this.misura11 = misura11;
		this.misura12 = misura12;
		this.misura13 = misura13;
		this.misura14 = misura14;
		this.misura15 = misura15;
		this.misura16 = misura16;
		this.misura17 = misura17;
		this.misura18 = misura18;
		this.Kg = kg;
		this.m3h1 = m3h1;
		this.m3h2 = m3h2;
		this.m3h3 = m3h3;
		this.m3h4 = m3h4;
		this.m3h5 = m3h5;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * @return the ariaType
	 */
	public String getAriaType() {
		return ariaType;
	}

	/**
	 * @param ariaType the ariaType to set
	 */
	public void setAriaType(String ariaType) {
		this.ariaType = ariaType;
	}

	/**
	 * @return the kw
	 */
	public double getKw() {
		return Kw;
	}

	/**
	 * @param kw the kw to set
	 */
	public void setKw(double kw) {
		Kw = kw;
	}

	/**
	 * @return the rpm
	 */
	public int getRpm() {
		return Rpm;
	}

	/**
	 * @param rpm the rpm to set
	 */
	public void setRpm(int rpm) {
		Rpm = rpm;
	}

	/**
	 * @return the misura1
	 */
	public int getMisura1() {
		return misura1;
	}

	/**
	 * @param misura1 the misura1 to set
	 */
	public void setMisura1(int misura1) {
		this.misura1 = misura1;
	}

	/**
	 * @return the misura2
	 */
	public int getMisura2() {
		return misura2;
	}

	/**
	 * @param misura2 the misura2 to set
	 */
	public void setMisura2(int misura2) {
		this.misura2 = misura2;
	}

	/**
	 * @return the misura3
	 */
	public int getMisura3() {
		return misura3;
	}

	/**
	 * @param misura3 the misura3 to set
	 */
	public void setMisura3(int misura3) {
		this.misura3 = misura3;
	}

	/**
	 * @return the misura4
	 */
	public int getMisura4() {
		return misura4;
	}

	/**
	 * @param misura4 the misura4 to set
	 */
	public void setMisura4(int misura4) {
		this.misura4 = misura4;
	}

	/**
	 * @return the misura5
	 */
	public int getMisura5() {
		return misura5;
	}

	/**
	 * @param misura5 the misura5 to set
	 */
	public void setMisura5(int misura5) {
		this.misura5 = misura5;
	}

	/**
	 * @return the misura6
	 */
	public int getMisura6() {
		return misura6;
	}

	/**
	 * @param misura6 the misura6 to set
	 */
	public void setMisura6(int misura6) {
		this.misura6 = misura6;
	}

	/**
	 * @return the misura7
	 */
	public int getMisura7() {
		return misura7;
	}

	/**
	 * @param misura7 the misura7 to set
	 */
	public void setMisura7(int misura7) {
		this.misura7 = misura7;
	}

	/**
	 * @return the misura8
	 */
	public int getMisura8() {
		return misura8;
	}

	/**
	 * @param misura8 the misura8 to set
	 */
	public void setMisura8(int misura8) {
		this.misura8 = misura8;
	}

	/**
	 * @return the misura9
	 */
	public int getMisura9() {
		return misura9;
	}

	/**
	 * @param misura9 the misura9 to set
	 */
	public void setMisura9(int misura9) {
		this.misura9 = misura9;
	}

	/**
	 * @return the misura10
	 */
	public int getMisura10() {
		return misura10;
	}

	/**
	 * @param misura10 the misura10 to set
	 */
	public void setMisura10(int misura10) {
		this.misura10 = misura10;
	}

	/**
	 * @return the misura11
	 */
	public int getMisura11() {
		return misura11;
	}

	/**
	 * @param misura11 the misura11 to set
	 */
	public void setMisura11(int misura11) {
		this.misura11 = misura11;
	}

	/**
	 * @return the misura12
	 */
	public int getMisura12() {
		return misura12;
	}

	/**
	 * @param misura12 the misura12 to set
	 */
	public void setMisura12(int misura12) {
		this.misura12 = misura12;
	}

	/**
	 * @return the misura13
	 */
	public int getMisura13() {
		return misura13;
	}

	/**
	 * @param misura13 the misura13 to set
	 */
	public void setMisura13(int misura13) {
		this.misura13 = misura13;
	}

	/**
	 * @return the misura14
	 */
	public int getMisura14() {
		return misura14;
	}

	/**
	 * @param misura14 the misura14 to set
	 */
	public void setMisura14(int misura14) {
		this.misura14 = misura14;
	}

	/**
	 * @return the misura15
	 */
	public int getMisura15() {
		return misura15;
	}

	/**
	 * @param misura15 the misura15 to set
	 */
	public void setMisura15(int misura15) {
		this.misura15 = misura15;
	}

	/**
	 * @return the misura16
	 */
	public int getMisura16() {
		return misura16;
	}

	/**
	 * @param misura16 the misura16 to set
	 */
	public void setMisura16(int misura16) {
		this.misura16 = misura16;
	}

	/**
	 * @return the misura17
	 */
	public int getMisura17() {
		return misura17;
	}

	/**
	 * @param misura17 the misura17 to set
	 */
	public void setMisura17(int misura17) {
		this.misura17 = misura17;
	}

	/**
	 * @return the misura18
	 */
	public int getMisura18() {
		return misura18;
	}

	/**
	 * @param misura18 the misura18 to set
	 */
	public void setMisura18(int misura18) {
		this.misura18 = misura18;
	}

	/**
	 * @return the kg
	 */
	public int getKg() {
		return Kg;
	}

	/**
	 * @param kg the kg to set
	 */
	public void setKg(int kg) {
		Kg = kg;
	}

	/**
	 * @return the m3h1
	 */
	public int getM3h1() {
		return m3h1;
	}

	/**
	 * @param m3h1 the m3h1 to set
	 */
	public void setM3h1(int m3h1) {
		this.m3h1 = m3h1;
	}

	/**
	 * @return the m3h2
	 */
	public int getM3h2() {
		return m3h2;
	}

	/**
	 * @param m3h2 the m3h2 to set
	 */
	public void setM3h2(int m3h2) {
		this.m3h2 = m3h2;
	}

	/**
	 * @return the m3h3
	 */
	public int getM3h3() {
		return m3h3;
	}

	/**
	 * @param m3h3 the m3h3 to set
	 */
	public void setM3h3(int m3h3) {
		this.m3h3 = m3h3;
	}

	/**
	 * @return the m3h4
	 */
	public int getM3h4() {
		return m3h4;
	}

	/**
	 * @param m3h4 the m3h4 to set
	 */
	public void setM3h4(int m3h4) {
		this.m3h4 = m3h4;
	}

	/**
	 * @return the m3h5
	 */
	public int getM3h5() {
		return m3h5;
	}

	/**
	 * @param m3h5 the m3h5 to set
	 */
	public void setM3h5(int m3h5) {
		this.m3h5 = m3h5;
	}
	

}
