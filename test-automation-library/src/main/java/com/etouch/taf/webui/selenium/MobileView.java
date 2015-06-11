/*
 * 
 */
package com.etouch.taf.webui.selenium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
//import com.etouch.taf.core.config.ScreenOrientation;
import com.etouch.taf.core.driver.IDriver;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.core.resources.ObjectValType;
import com.etouch.taf.core.resources.WaitCondition;
import com.etouch.taf.util.*;
import com.etouch.taf.webui.ITafElement;

// TODO: Auto-generated Javadoc
/**
 * This class finds and renders page objects , drivers.
 * 
 * @author eTouch Systems Corporation
 * @version 1.0
 * 
 */
public class MobileView extends WebPage{

/** The log. */
private static Log log = LogUtil.getLog(MobileView.class);

/** The driver. */
private AppiumDriver driver = null;

public final String UP= "up";
public final String DOWN= "down";
public final String RIGHT= "right";
public final String LEFT= "left";


/**
 * Instantiates a new mobile View.
 */
public MobileView()
{
	driver = (AppiumDriver)TestBedManager.INSTANCE.getTestBed().getDriver();
}

public AppiumDriver getDriver()
{
	return (AppiumDriver) driver;
}

/**
 * Tap.
 *
 * @param driver the driver
 * @param element the element
 * @param duration the duration
 */
public void tap(IDriver driver,WebElement element, Double duration)
{
JavascriptExecutor js = (JavascriptExecutor) driver;
HashMap<String, Double> tapObject = new HashMap<String, Double>();
tapObject.put("x", (double) element.getLocation().getX()); 
tapObject.put("y", (double) element.getLocation().getY()); 
tapObject.put("duration", duration);
js.executeScript("mobile: tap", tapObject);
}

/**
 * Tap element by id.
 *
 * @param driver the driver
 * @param element the element
 */
public void tapElementById(IDriver driver, RemoteWebElement element){
JavascriptExecutor js = (JavascriptExecutor) driver;
HashMap<String, Object> tapObject = new HashMap<String, Object>();
tapObject.put("x", 150); // in pixels from left
tapObject.put("y", 30); // in pixels from top
tapObject.put("element", ((RemoteWebElement) element).getId()); // the id of the element we want to tap
js.executeScript("mobile: tap", tapObject);
}

public ITafElement findElementByAccessibilityId(String id) throws PageException
{
	WebElement element = null;
	try{
			element = driver.findElementByAccessibilityId(id);
		}catch (Exception e)
		{
			log.error("Failed to find object using given selector" + " = "+ id + ", message : " + e.toString());
			throw new PageException("Failed to find object using given id , message : " + e.toString());
		}
	
	return new MobileElement(element);
}


public ITafElement findElementByAndroidUIAutomator(String using) throws PageException
{
	WebElement element = null;
	try{
			element = ((AndroidDriver)driver).findElementByAndroidUIAutomator(using);
		}catch (Exception e)
		{
			log.error("Failed to find object using given selector" + " = "+ using + ", message : " + e.toString());
			throw new PageException("Failed to find object using given id , message : " + e.toString());
		}
	
	return new MobileElement(element);
}

public ITafElement findElementByIosUIAutomation(String using) throws PageException
{
	WebElement element = null;
	try{
		 element = ((IOSDriver)driver).findElementByIosUIAutomation(using);	
		}catch (Exception e)
		{
			log.error("Failed to find object using given selector" + " = "+ using + ", message : " + e.toString());
			throw new PageException("Failed to find object using given id , message : " + e.toString());
		}
	
		return new MobileElement(element);
}

/**
 * Swipe.
 *
 * @param startX the start x
 * @param startY the start y
 * @param endX the end x
 * @param endY the end y
 * @param duration the duration
 */
public void swipe(Double startX,Double startY, Double endX, Double endY,Double duration)
{
JavascriptExecutor js = (JavascriptExecutor) driver;
HashMap<String, Double> swipeObject = new HashMap<String, Double>();
swipeObject.put("startX", startX);
swipeObject.put("startY", startY);
swipeObject.put("endX", endX);
swipeObject.put("endY", endY);
swipeObject.put("duration", duration);
js.executeScript("mobile: swipe", swipeObject);
}

/**
 * Flick.
 */
public void flick(){
JavascriptExecutor js = (JavascriptExecutor) driver;
HashMap<String, Object> flickObject = new HashMap<String, Object>();
flickObject.put("endX", 0);
flickObject.put("endY", 0);
flickObject.put("touchCount", 2);
js.executeScript("mobile: flick", flickObject);
}

/**
 * Slider.
 *
 * @param element the element
 * @param slideValue the slide value
 */
public void slider(WebElement element,float slideValue){
if((slideValue>=0.0) && (slideValue<=1)){
element.sendKeys(String.valueOf(slideValue));
}
}

/**
 * Sets the orientation.
 *
 * @param driver the driver
 * @param orientation the orientation
 *//*
public void setOrientation(RemoteWebDriver driver,ScreenOrientation orientation){

//((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
// Find a way to figure out x.y of elements got changed or not
}

//    tap (on screen or on element) with options:
//        how many fingers
//        how long to tap
//        how many taps
//        where precisely to tap on the screen or element
//    flick (on screen or on element) with options:
//        how many fingers
//        where to start the flick on screen or element
//        where to end the flick on screen or element
//    swipe/drag (on screen or on element) with options:
//        how many fingers
//        how long the swipe/drag takes in seconds
//        where to start the swipe on screen or element
//        where to end the swipe on screen or element
//    scroll to (element)
//    slider
//    shake
//    longTap (element)
//    set the orientation with option:
//        new orientation (landscape or portrait)
*/
/**
 * Tap.
 *
 * @param fingers the fingers
 * @param x the x
 * @param y the y
 * @param duration the duration
 */
public  void tap(int fingers, int x, int y, int duration) 
{
	driver.tap(fingers, x, y, duration);	
}

/**
 * Swipe.
 *
 * @param startx the startx
 * @param starty the starty
 * @param endx the endx
 * @param endy the endy
 * @param duration the duration
 */
public void swipe(int startx, int starty, int endx, int endy, int duration)
{
	driver.swipe(startx, starty, endx, endy, duration);		
}


//This method is not working for IOS

public void swipeRight()
{
  	Dimension size = getDimension();
    int startX = (int)(size.width * 0.10);
    int endX = (int)(size.width * 0.80);
    int startY = size.height/2;
    
    swipe(startX, startY, endX, startY, 1000);   
  
}

//This method is not working for IOS

public void swipeLeft()
{
  	Dimension size = getDimension();
    int startX = (int)(size.width * 0.80);
    int endX = (int)(size.width * 0.10);
    int startY = size.height/2;
    swipe(startX, startY, endX, startY, 1000);  
   
}


public void swipeUp()
{
	 Dimension size = getDimension();
	 int startX = size.width/2;
	 int startY = (int)(size.height * 0.80);
	 int endY = (int)(size.height * 0.10);
	 
	 swipe(startX, startY, startX, endY, 1000);
	 
	
}


public void swipeDown()
{
	 Dimension size = getDimension();
	 int startX = size.width/2;
	 int startY = (int)(size.height * 0.10);
	 int endY = (int)(size.height * 0.80);
	 
	 swipe(startX, startY, startX, endY, 1000);
}


/**
 * Pinch.
 *
 * @param x the x
 * @param y the y
 */
public void pinch(int x, int y)
{
	driver.pinch(x, y);
}

/**
 * Zoom.
 *
 * @param x the x
 * @param y the y
 */
public void zoom(int x, int y)
{
	driver.zoom(x, y);
}

/**
 * Rotate.
 *
 * @param orientation the orientation
 */
public void rotate(ScreenOrientation orientation) 
{
	driver.rotate(orientation);
}
	
/**
 * Lock screen.
 *
 * @param seconds the seconds
 */
public void lockScreen(int seconds) 
{
	driver.lockScreen(seconds);
}

/**
 * Gets the orientation.
 *
 * @return the orientation
 */
public ScreenOrientation getOrientation() 
{
	return driver.getOrientation();
}

/**
 * Scroll to.
 *
 * @param text the text
 * @return the mobile element
 */
public MobileElement scrollTo(String text) {
	io.appium.java_client.MobileElement me = driver.scrollTo(text);
	return new com.etouch.taf.webui.selenium.MobileElement(me);
}

/**
 * Scroll to exact.
 *
 * @param text the text
 * @return the mobile element
 */
public MobileElement scrollToExact(String text) {
	io.appium.java_client.MobileElement me = driver.scrollToExact(text);
	return new com.etouch.taf.webui.selenium.MobileElement(me);
  }

public void navigateBack()
{
	driver.navigate().back();
}

public Dimension getDimension()
{
	return driver.manage().window().getSize();
}

}
