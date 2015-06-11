package com.etouch.taf.webui;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating ITafElement objects.
 */
public interface ITafElementFactory {
	
	/**
	 * Creates a new ITafElement object.
	 *
	 * @param webElement the web element
	 * @return the i taf element
	 */
	public ITafElement createElement(org.openqa.selenium.WebElement webElement);

}
