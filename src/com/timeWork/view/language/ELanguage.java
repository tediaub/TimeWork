package com.timeWork.view.language;

import java.util.Locale;

/**
 * Enumeration of program languages
 *
 * @author AUBERT_T
 * @version 1.0
 */

public enum ELanguage {
	ANGLAIS("ANGLAIS",new Locale("en"));

	protected String label;
	protected Locale locale;

	/** Constructeur */
	ELanguage(String pLabel,Locale locale) {
		this.label = pLabel;
		this.locale=locale;
	}

	public String getLabel() {
		return this.label;
	}

	public Locale getLocale() {
		return this.locale;
	}
}