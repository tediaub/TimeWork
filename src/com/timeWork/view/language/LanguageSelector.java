package com.timeWork.view.language;

import java.util.ResourceBundle;

/**
 * Class managing internationalization
 * @author AUBERT_T
 *
 */

public class LanguageSelector  {

	private final static String DICTIONNAIRES ="UI_Label/TimeWork";


	// La locale utilisée (la langue actuellement utilisée)
	private static ELanguage currentLanguage = ELanguage.ANGLAIS;

	// Ressource locale
	private static ResourceBundle messages = ResourceBundle.getBundle(
			DICTIONNAIRES,currentLanguage.getLocale());

	/**
	 * Returns the corresponding text in the selected language
	 * @param text Text you want
	 * @return string
	 **/
	public static String getLocalizedText(String text)
	{
		return messages.getString(text);
	}

	/**
	 * This function is used to change the language
	 * @param langue la nouvelle langue
	 **/
	public static void setLangue(ELanguage langue)
	{
		currentLanguage = langue;

		// Ressource locale
		messages = ResourceBundle.getBundle(DICTIONNAIRES,currentLanguage.getLocale());
	}

	/**
	 *Return current language
	 *@return The current language
	 **/
	public static ELanguage getCurrentLanguage()
	{
		return currentLanguage;
	}

	public static ResourceBundle getRessourceBundle(){
		return messages;
	}
}
