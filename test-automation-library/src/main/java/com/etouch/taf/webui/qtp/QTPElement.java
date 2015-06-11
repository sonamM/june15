package com.etouch.taf.webui.qtp;

import io.appium.java_client.SwipeElementDirection;

import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.BrowserInfoUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.webui.ITafElement;
//import com.etouch.taf.webui.selenium.WebElement;



import org.apache.commons.logging.Log;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

// TODO: Auto-generated Javadoc
/**
 * The Class QTPElement.
 */
public class QTPElement implements ITafElement {
	
	/** The log. */
	private static Log log = LogUtil.getLog(WebElement.class);
	
	/** The element. */
	protected WebElement element = null;
	
	/** The driver. */
	protected WebDriver driver = null;
	
	/**
	 * Instantiates a new QTP element.
	 *
	 * @param element the element
	 */
	public QTPElement(WebElement element)
	{
		this.element = element;
		if (this.driver == null) {
			this.driver=(WebDriver)TestBedManager.INSTANCE.getTestBed().getDriver();
		} 
	}
	
	
	/**
	 * Click on web element.
	 */
	public void click() {
		try {
			this.element.click();
			Thread.sleep(1000);
		} catch (StaleElementReferenceException e) {
			log.error("Exception in StaleElementReference message, " + e.toString());
		}catch (InterruptedException e) {
		log.error("Exception in thread sleep, message, " + e.toString());
	    }
	}

	/**
	 * Clear the web element.
	 */
	public void clear() {
		try {
			this.element.clear();
			Thread.sleep(1000);
		} catch (StaleElementReferenceException e) {
			log.error("Exception in StaleElementReference message, " + e.toString());
		}catch (InterruptedException e) {
		log.error("Exception in thread sleep, message, " + e.toString());
	    }
	}


	/**
	 * Enter the text in web element.
	 *
	 * @param txt the txt
	 */
	public void sendKeys(String txt) {
		try {
			this.element.sendKeys(txt);
			Thread.sleep(1000);
		} catch (StaleElementReferenceException e) {
			log.error("Exception in StaleElementReference message, " + e.toString());
		}catch (InterruptedException e) {
		log.error("Exception in thread sleep, message, " + e.toString());
	    }
	}
	
	
	/**
	 * hovers on the element.
	 */
	public void hover() {
		if (BrowserInfoUtil.INSTANCE.isSafari()) {
			String javaScript = "var evObj = document.createEvent('MouseEvents');" + "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
			        + "arguments[0].dispatchEvent(evObj);";
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(javaScript, this.element);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				log.error("Exception in thread sleep, message, " + e.toString());
			}
			return;
		}
		/*
		 * else if(BrowserInfoUtil.INSTANCE.isIE9()){ Actions builder=new
		 * Actions(driver); builder.moveToElement(webElement); builder.build();
		 * //builder.click(webElement); builder.perform(); }
		 */
		else {
			Actions builder = new Actions(driver);
			builder.moveToElement(this.element).build().perform();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.etouch.taf.webui.ITafElement#zoom()
	 */
	public void zoom()
	{
		
	}


	public void doubleClick() {
		// TODO Auto-generated method stub
		
	}


	public boolean isElementVisible() {
		// TODO Auto-generated method stub
		return false;
	}


	public Point getCoordinates() {
		// TODO Auto-generated method stub
		return null;
	}


	public Dimension getSize() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getAttribute(String attrName) {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}


	public String getCssValue(String property_name) {
		// TODO Auto-generated method stub
		return null;
	}


	public void clickEvent() {
		// TODO Auto-generated method stub
		
	}


	public void submit() {
		// TODO Auto-generated method stub
		
	}


	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean isSelected() {
		// TODO Auto-generated method stub
		return false;
	}


	public void check() {
		// TODO Auto-generated method stub
		
	}


	public void uncheck() {
		// TODO Auto-generated method stub
		
	}


	public void selectDropDownList(String selection) throws PageException {
		// TODO Auto-generated method stub
		
	}


	public WebElement getWebElement() {
		// TODO Auto-generated method stub
		return null;
	}


	public void tap(int fingers, int duration) {
		// TODO Auto-generated method stub
		
	}


	public void pinch() {
		// TODO Auto-generated method stub
		
	}


	public void dragAndDrop(ITafElement target) {
		// TODO Auto-generated method stub
		
	}


	public boolean isChecked() {
		// TODO Auto-generated method stub
		return false;
	}


	public void swipe(SwipeElementDirection direction, int duration) {
		// TODO Auto-generated method stub
		
	}


	public void scroll(String direction) {
		// TODO Auto-generated method stub
		
	}


	public void swipe(String direction) {
		// TODO Auto-generated method stub
		
	}
	

}
