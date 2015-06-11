/*
 * 
 */
package com.etouch.amazon.pages;

 import java.io.File;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.ParseConversionEvent;

import org.apache.commons.logging.Log;
import org.apache.poi.ss.usermodel.Textbox;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.internal.TouchAction;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.server.handler.FindElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.datamanager.excel.TafExcelDataProvider;
import com.etouch.taf.core.datamanager.excel.TestParameters;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
import com.etouch.taf.core.datamanager.excel.annotations.ITafExcelDataProviderInputs;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.core.resources.ObjectType;
import com.etouch.taf.core.resources.ObjectValType;
import com.etouch.taf.core.resources.WaitCondition;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.amazon.common.BaseTest;
import com.etouch.amazon.common.CommonPage;
import com.etouch.taf.webui.selenium.webelement.*;
import com.etouch.taf.webui.selenium.WebPage;

// TODO: Auto-generated Javadoc
/**
 * The Class AmazonMainPage.
 */
public class AmazonMainPage extends CommonPage {
	
	//private WebDriver webDriver;
	
	
	/**
	 * Instantiates a new amazon main page.
	 *
	 * @param sbPageUrl the sb page url
	 * @param webPage the web page
	 */
	public AmazonMainPage(String sbPageUrl, WebPage webPage) {
		super(sbPageUrl, webPage);
  
		
		//webDriver = webPage.getDriver();
		CommonUtil.sop("webDriver in AmazonMainPage "+ webPage.getDriver());
				
			//startRecording();
	//	if(TestBedManager.INSTANCE.getCurrentTestBed().getDevice().getName() != null){
			
			loadPage();		
			
		//}
	}

	/**
	 * Gets the page url.
	 *
	 * @return the page url
	 */
	public String getPageUrl() {
		return webPage.getCurrentUrl();
	}

	
	/**
	 * Pre sign in.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void PreSignIn() throws InterruptedException, PageException{
			final int MAX_WAIT = 20;
			CommonUtil.sop("this is presignIn");
			
			((Button) webPage.findObject(ObjectType.Button, "nav-link-yourAccount",
					ObjectValType.ID, MAX_WAIT, WaitCondition.CLICKABLE)).hover();
			
			((Button) webPage.findObject(ObjectType.Button, "nav-link-yourAccount",
					ObjectValType.ID, MAX_WAIT, WaitCondition.CLICKABLE)).click();
			
			Thread.sleep(2000);
			
		}	
		
	
	
	/**
	 * Sign in.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void SignIn() throws InterruptedException, PageException
	{
		final int MAX_WAIT = 20;
		
		((TextBox) webPage.findObject(ObjectType.TextBox,
			"ap_email", ObjectValType.ID, MAX_WAIT, WaitCondition.VISIBLE))
				.enterText("");

		((TextBox) webPage.findObject(ObjectType.TextBox,
				"ap_password", ObjectValType.ID, MAX_WAIT,
				WaitCondition.VISIBLE)).enterText("");
		
	
		((Button) webPage.findObject(ObjectType.Button,
			"signInSubmit-input", ObjectValType.ID, MAX_WAIT,
				WaitCondition.CLICKABLE)).click();	
			
	}
	
	//The below methods are written using latest ITafElement code
	
	public void PreSignInWithITafElement() throws InterruptedException, PageException{
			CommonUtil.sop("this is PreSignInWithITafElement");
			Thread.sleep(2000);
			( webPage.findObjectById("nav-link-yourAccount")).hover();
			( webPage.findObjectById("nav-link-yourAccount")).click();			
			
		}	
		
	
	/**
	 * Sign in.
	 *
	 * @throws InterruptedException the interrupted exception
	 * @throws PageException the page exception
	 */
	public void SignInWithITafElement() throws InterruptedException, PageException
	{
		CommonUtil.sop("this is signInWIthITafElement");
		Thread.sleep(2000);
		(webPage.findObjectById("ap_email")).sendKeys("lavanya");
		(webPage.findObjectById("ap_password")).sendKeys("lavanya");
		(webPage.findObjectById("signInSubmit-input")).click();		
		Thread.sleep(2000);
	}


}




