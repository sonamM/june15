package com.etouch.taf.webui.selenium;

import java.util.HashMap;

import io.appium.java_client.AppiumDriver;
//import io.appium.java_client.

import io.appium.java_client.SwipeElementDirection;

import org.apache.commons.logging.Log;

import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.webui.ITafElement;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


// TODO: Auto-generated Javadoc
/**
 * The Class MobileElement.
 */
public class MobileElement extends com.etouch.taf.webui.selenium.WebElement{
	
	//** The log. *//*
	/** The log. */
	private static Log log = LogUtil.getLog(MobileElement.class);
	
	/** The driver. */
	private static WebDriver driver = null;
	
	/**
	 * Instantiates a new mobile element.
	 *
	 * @param element the element
	 */
	public MobileElement(WebElement element)
	{
		super(element);
		driver=(WebDriver)TestBedManager.INSTANCE.getTestBed().getDriver();
	}
	
	/* (non-Javadoc)
	 * @see com.etouch.taf.webui.ITafElement#tap(int, int)
	 */
	@Override
	public void tap(int fingers, int duration)
	{
		((AppiumDriver)driver).tap(fingers, this.webElement, duration);
	}
	
	/* (non-Javadoc)
	 * @see com.etouch.taf.webui.ITafElement#pinch()
	 */
	@Override
	public void pinch()
	{
		((AppiumDriver)driver).pinch(this.webElement);
	}
	
	/* (non-Javadoc)
	 * @see com.etouch.taf.webui.selenium.Element#zoom()
	 */
	@Override
	public void zoom()
	{
		((AppiumDriver)driver).zoom(this.webElement);
	}
	
	/* (non-Javadoc)
	 * @see com.etouch.taf.webui.selenium.Element#zoom()
	 */
	
	////This method is not working for IOS
	public void swipe(SwipeElementDirection direction, int duration)
	{
		io.appium.java_client.MobileElement mobElement = (io.appium.java_client.MobileElement) this.webElement;
		mobElement.swipe(direction, duration);
	}
	
	@Override
	public void scroll(String direction)
	{
		
        JavascriptExecutor js = (JavascriptExecutor) driver;

        HashMap<String, String> scrollObject = new HashMap<String, String>();

        scrollObject.put("direction", direction);


        scrollObject.put("element", ((org.openqa.selenium.remote.RemoteWebElement) this.webElement).getId());

        js.executeScript("mobile: scroll", scrollObject);
	   
		
	}
	
	/*@Override
	public void swipe(String direction)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;

        HashMap<String, String> swipeObject = new HashMap<String, String>();

        swipeObject.put("direction", direction);

        swipeObject.put("element", ((org.openqa.selenium.remote.RemoteWebElement) this.webElement).getId());

        js.executeScript("mobile: swipe", swipeObject);
	}
*/
}
