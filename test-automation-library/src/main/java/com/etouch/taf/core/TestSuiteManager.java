/*
 * 
 */
package com.etouch.taf.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.testng.ISuiteListener;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlMethodSelector;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.xml.sax.SAXException;

import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.config.TestngConfig;
import com.etouch.taf.core.config.TestngClass;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ConfigUtil;
import com.etouch.taf.util.LogUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
// TODO: Auto-generated Javadoc
/**
 * The Enum TestSuiteManager.
 */
public enum TestSuiteManager {
	
	/** The instance. */
	INSTANCE;

	/** The log. */
	static Log log = LogUtil.getLog(TestSuiteManager.class);
	
	/** The test bed manager config. */
	TestBedManagerConfiguration testBedManagerConfig=TestBedManagerConfiguration.getInstance();
	
	/** The testng config. */
	TestngConfig testngConfig = testBedManagerConfig.getTestngConfig();
	

	/**
	 * This method will read testNG information from TestBedManagerConfiguration
	 * and Will create a first testSuite and will createRest of the testSuites, 
	 * based on the given testbed details .
	 *
	 * @return the test ng
	 */
	public TestNG buildTestSuites() {
		TestNG testng = new TestNG();
		
		/** Creates first testBed */
		List<XmlSuite> existingSuiteList = createTestNGSuite();
	
		testng.setXmlSuites(existingSuiteList);
		CommonUtil.sop(existingSuiteList.get(0).toXml());
		XmlSuite suite=existingSuiteList.get(0);
		suite.addListener(testngConfig.getListener());
		CommonUtil.sop(suite.toXml());
		//testng.setListenerClasses(testngConfig.getListener());
		
		// testng.setPreserveOrder(true);
		return testng;
	}
	
	
	/**
	 * Creates the first TestSuite based on the information given in 
	 * devConfig.yml under testngConfig
	 *
	 * @return the list
	 */
	private List<XmlSuite> createTestNGSuite() {

		// log.info("Start - reading TestNGFile");
		
		List<XmlSuite> testngSuiteList = new ArrayList<XmlSuite>();

		if(ConfigUtil.isWebTestTypeEnabled()){
			
			testngSuiteList.addAll(createWebSuites(testngSuiteList));
		}
		
		if(ConfigUtil.isMobileTestTypeEnabled()){
			
			testngSuiteList.addAll(createMobileSuite(testngSuiteList));
		}

		
		log.info("Start - building TestSuits according to the configuration");
		
		return testngSuiteList;

	}

/**
 * Creates the mobile suite.
 *
 * @param testngSuiteList the testng suite list
 * @return the list
 * @return7i 
 */
	private List<XmlSuite> createMobileSuite(List<XmlSuite> testngSuiteList) {
		
			List<XmlSuite> mobileSuiteList = new ArrayList<XmlSuite>();
			
			for (String testBedName : testBedManagerConfig.getMobileConfig()
					.getCurrentTestBeds()) {
	
				mobileSuiteList.add(createTestSuite(testBedName));
				List<XmlClass> xmlClasses=createXmlClass(TestBedManagerConfiguration.getInstance().getMobileConfig().getTestngClass() );
				addTestClasses(mobileSuiteList,xmlClasses);
				
			}
		
		return mobileSuiteList;
	}

/**
 * Creates the web suites.
 *
 * @param testngSuiteList the testng suite list
 * @return the list
 */
	private List<XmlSuite> createWebSuites(List<XmlSuite> testngSuiteList) {
		List<XmlSuite> webSuiteList = new ArrayList<XmlSuite>();


			for (String testBedName : testBedManagerConfig.getWebConfig()
					.getCurrentTestBeds()) {
				
				webSuiteList.add(createTestSuite(testBedName));		
				List<XmlClass> xmlClasses=createXmlClass(TestBedManagerConfiguration.getInstance().getWebConfig().getTestngClass());
				addTestClasses(webSuiteList,xmlClasses);
				}
		
		return webSuiteList;
	}
	
	
	
	/**
	 * Adds the test classes.
	 *
	 * @param suiteList the suite list
	 * @param xmlClasses the xml classes
	 * @return the list
	 */
	private List<XmlSuite> addTestClasses(List<XmlSuite> suiteList,List<XmlClass> xmlClasses){
		
		if(suiteList!=null){
			
			if((xmlClasses!=null)&&(xmlClasses.size()>0)){
					for(XmlSuite suite:suiteList){
						List<XmlTest> tests=suite.getTests();
						if(tests.size()>0){
							List<XmlClass> classes=tests.get(0).getClasses();
							classes.addAll(xmlClasses);
							
						}else{
							createXMLTest(suite,xmlClasses);
						}
					}
					
				}
				
			}
			
		
		return suiteList;
		
	}
	
	
	
	/**
	 * Helps to create the structure of Test Suite information
	 * Helper method.
	 *
	 * @param testBedName the test bed name
	 * @return the xml suite
	 */
	private  XmlSuite createTestSuite(
			String testBedName) {
		XmlSuite testSuite = new XmlSuite();
		Map<String, String> paramsMap = new HashMap<String, String>();

		testSuite.setName(testBedName + " Suite");
		testSuite.setPreserveOrder("true");

		paramsMap.put("testBedName", testBedName);
		testSuite.setParameters(paramsMap);
		testSuite.setParallel("false");
		//assignParallelMode(testSuite);

		//addListenerToSuite(testSuite);
		createXMLTest( testSuite,testBedName);
		addXmlTestToSuite(testSuite, testBedName);
		

		return testSuite;
	}

	
	
	private XmlSuite assignParallelMode(XmlSuite testSuite) {
		boolean isParallel=false;
		String parallelMode = testBedManagerConfig.getTestngConfig().getParallelMode();
		try{
		 
			isParallel = Boolean.parseBoolean(parallelMode);
		}catch(Exception e){
			isParallel=false;
			// if is not a valid boolean value then set the parallel mode to false
			parallelMode="false";
		}
		 
		 testSuite.setParallel(parallelMode);
		 
		 return testSuite;
		
	}


	/**
	 * Adds the xml test to suite.
	 *
	 * @param xmlSuite the xml suite
	 * @param testBedName the test bed name
	 */
	private void addXmlTestToSuite(XmlSuite xmlSuite, String testBedName){
		xmlSuite.setTests(createXMLTest( xmlSuite,testBedName));
	}

	/**
	 * Creates the xml test.
	 *
	 * @param xmlSuite the xml suite
	 * @param testBedName the test bed name
	 * @return the list
	 */
	private List<XmlTest> createXMLTest(XmlSuite xmlSuite,String testBedName) {
		
		List<XmlTest> xmlTestList=new ArrayList<XmlTest>();
		try {
			
			XmlTest test = new XmlTest();
			addXmlClassToTest(test);
					
			test.setName(testBedName + " Test");
			test.setVerbose(1);
			test.setPreserveOrder("true");
			test.setSuite(xmlSuite);

			if (!test.equals(null))
				xmlTestList.add(test);
			else
				System.out.println("Test not created");

		
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Error occured while creating first testng Suite");
		}
		return xmlTestList;
	}
	
	
	
/**
 * Creates the xml test.
 *
 * @param xmlSuite the xml suite
 * @param classes the classes
 * @return the list
 */
private List<XmlTest> createXMLTest(XmlSuite xmlSuite,List<XmlClass> classes) {
		
		List<XmlTest> xmlTestList=new ArrayList<XmlTest>();
		try {
			
			XmlTest test = new XmlTest();
			test.setClasses(classes);
					
			//test.setName(testBedName + " Test");
			test.setVerbose(1);
			test.setPreserveOrder("true");
			test.setSuite(xmlSuite);

			if (!test.equals(null))
				xmlTestList.add(test);
			else
				System.out.println("Test not created");

		
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Error occured while creating first testng Suite");
		}
		return xmlTestList;
	}
	
	

	
	/**
	 * Adds the xml class to test.
	 *
	 * @param test the test
	 */
	private void addXmlClassToTest(XmlTest test) {
		if(test!=null){
			test.setClasses(createXmlClass());
		}
	}


	/**
	 * Creates the xml class.
	 *
	 * @return the list
	 */
	/*private List<XmlClass> createXmlClass(){
		
	List<XmlClass> classList=new ArrayList<XmlClass>();
			String[] className = testngConfig.getClassName();

			if(className!=null){
			// Populate the class Names that need to be tested from TestNG
			// config.
			for (String clazzName : className) {
				
				XmlClass xmlClass = new XmlClass();
				Class<?> clazz=null;
				try {
					clazz = Class.forName(clazzName);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				xmlClass.setClass(clazz);
				xmlClass.setName(clazzName);
				classList.add(xmlClass);
				}
			}
			return classList;
	}*/
	
	
	private List<XmlClass> createXmlClass(){
		List<XmlClass> xmlClassList=new ArrayList<XmlClass>();
		
		List<TestngClass> classList=testngConfig.getTestngClass();
		if(classList!=null){
		
			for(TestngClass testngClass:classList){
				XmlClass xmlClass=new XmlClass();
				Class<?> clazz=null;
				try{
					clazz=Class.forName(testngClass.getClassName());
				}catch(ClassNotFoundException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
				xmlClass.setClass(clazz);
				xmlClass.setName(testngClass.getClassName());
				xmlClass=addMethodIncludes(xmlClass, testngClass.getMethodList());

				xmlClassList.add(xmlClass);
			}
		}//end-if
		return xmlClassList;
	}
	
	private XmlClass addMethodIncludes(XmlClass xmlClass,List<String> methodList){
		List<XmlInclude> xmlIncludes=new ArrayList<XmlInclude>();
		XmlInclude include=null;
		
		if(methodList==null){
			// if the methodList is null for a class. 
			methodList= getTestMethodList(xmlClass.getClass().getName());
		}
		
		if(methodList!=null){
			for(String methodName:methodList){
				include=new XmlInclude(methodName);
				include.setXmlClass(xmlClass);
				xmlIncludes.add(include);
			}
			xmlClass.setIncludedMethods(xmlIncludes);
		}
		
		return xmlClass;
	}
	
	
	private ArrayList<String> getTestMethodList(String className) {
		 ArrayList<String> methods = new ArrayList<String>();
//	    	Reflections reflections = new Reflections(packageName);
//	    	reflections.get
	    	Class cls;
			try {
				cls = Class.forName(className);
		    	List<Method> methodList = getMethodsAnnotatedWith(cls,org.testng.annotations.Test.class);
		    	for (Method m:methodList) {
		    		methods.add(className+"#"+m.getName());
		    	}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	return methods;
	    }
	
	 public static List<Method> getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation) {
	        final List<Method> methods = new ArrayList<Method>();
	        Class<?> klass = type;
	        while (klass != Object.class) { // need to iterated thought hierarchy in order to retrieve methods from above the current instance
	            // iterate though the list of methods declared in the class represented by klass variable, and add those annotated with the specified annotation
	            final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(klass.getDeclaredMethods()));       
	            for (final Method method : allMethods) {
	                if (annotation == null || method.isAnnotationPresent(annotation)) {
	                    // TODO process annotInstance
	                    methods.add(method);
	                }
	            }
	            // move to the upper class in the hierarchy in search for more methods
	            klass = klass.getSuperclass();
	        }
	        return methods;
	    }


	
	
	/**
	 * Creates the xml class.
	 *
	 * @param className the class name
	 * @return the list
	 */
	private List<XmlClass> createXmlClass(List<TestngClass> classList){
		
		List<XmlClass> xmlClassList=null;
		if(classList!=null && classList.size()>0){
			xmlClassList=new ArrayList<XmlClass>();
		
		for(TestngClass testngClass:classList){
			XmlClass xmlClass=new XmlClass();
			Class<?> clazz=null;
			try{
				clazz=Class.forName(testngClass.getClassName());
			}catch(ClassNotFoundException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			xmlClass.setClass(clazz);
			xmlClass.setName(testngClass.getClassName());
			xmlClass=addMethodIncludes(xmlClass, testngClass.getMethodList());
			
			xmlClassList.add(xmlClass);
			
		}
		}//end-if
		return xmlClassList;
		}
	
	/**
	 * Helps to add the listener details to testSuite.
	 *
	 * @param xmlSuite the xml suite
	 */
	private  void addListenerToSuite( XmlSuite xmlSuite) {
		if(xmlSuite!=null){
			if(xmlSuite.getListeners()!=null){
				System.out.println("Listeners " +  xmlSuite.getListeners().size());
			}
			
			List<String> listeners=new ArrayList<String>();
			listeners.add(testngConfig.getListener());
			xmlSuite.setListeners(listeners);
			//xmlSuite.addListener(TestBedManagerConfiguration
			//		.getInstance().getTestngConfig().getReporter());
		}
	}
	
	/**
	 * Helper method to get TestBedName
	 * @return
	 */
	/*private static String getTestBedName() {
		String firstTestBedName = null;
		TestBedManagerConfiguration testBedConfig = TestBedManagerConfiguration
				.getInstance();

		String[] testBeds = testBedConfig.getWebConfig().getCurrentTestBeds();
		if (testBeds.length > 0) {
			firstTestBedName = testBeds[0];
			updateTestBeds(testBeds);

		} else if (testBedConfig.getMobileConfig().getCurrentTestBeds().length > 0) {
			testBeds = testBedConfig.getMobileConfig().getCurrentTestBeds();
			firstTestBedName = testBeds[0];
			updateTestBeds(testBeds);
		}

		return firstTestBedName;
	}*/

	

	
	

	

	/*
	
	/**
	 * Helper method
	 * @param testBeds
	 */
	/*private static void updateTestBeds(String[] testBeds) {
		if (testBeds.length > 0) {
			for (int i = 0; i < testBeds.length - 1; i++) {
				testBeds[i] = testBeds[i + 1];
			}

		}

	}
	
	private List<XmlTest> readXmlTestInfoFromSuite(List<XmlSuite> suiteList) {
		List<XmlTest> xmlTestList = new ArrayList<XmlTest>();

		if (suiteList != null) {
			// Get all the test information from all the suites given in the
			// testng.xml file
			for (XmlSuite testSuite : suiteList) {
				List<XmlTest> suiteTestList = testSuite.getTests();
				for (XmlTest xmlTest : suiteTestList) {
					// Add all the test information in a xmlTest list
					xmlTestList.add(xmlTest);
				}
			}
		}
		return xmlTestList;
	}*/

	

	// private static List<XmlSuite> handleForExistingSuites(String
	// suiteFileName) {
	// List<XmlSuite> existingSuite = null;
	//
	// try {
	// existingSuite = (List<XmlSuite>) new Parser(suiteFileName).parse();
	// } catch (ParserConfigurationException e) {
	// // TODO Auto-generated catch block
	// System.out.println("ParserConfigurationException occured "
	// + e.getMessage());
	// e.printStackTrace();
	//
	// } catch (SAXException e) {
	// // TODO Auto-generated catch block
	// System.out.println("SAXException occured " + e.getMessage());
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// System.out.println("IOException occured " + e.getMessage());
	// e.printStackTrace();
	// }
	//
	// return existingSuite;
	// }

}


