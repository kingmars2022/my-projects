import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class DebugRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PrintStream oldOut = System.out;

		Double score = Double.valueOf(0);
		
		URL u = DebugRunner.class.getResource("DebugRunner.class");
		System.out.println(u.toString());
		
		File file = new File("bin");
		URL url = null;
		try {
			url = file.toURI().toURL();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		File file2 = new File(".");
		URL url2 = null;
		try {
			url2 = file2.toURI().toURL();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		URL[] urls = new URL[]{u, url, url2}; 

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse( new File("config.xml"));
			
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression expr = xpath.compile("/config/test");
			NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			
			for(int i = 0; i < nl.getLength(); i++) {
				score = classloadWrapTest(oldOut, score, urls, nl.item(i));				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(score);
	}

	
	private static Double classloadWrapTest(PrintStream oldOut, Double score, URL[] urls, Node test) {

		String driver = "";
		
		try {
		
			driver = getString(test, "./@driver");
			String methodName = getString(test, "./@method");

			URLClassLoader clsLoader = URLClassLoader.newInstance(urls,null);
			Class cls = clsLoader.loadClass("DebugRunner");
			Method method = cls.getMethod("tester", 
					PrintStream.class, 
					Double.class, 
					Method.class,
					Node.class);

			Class mainClass = clsLoader.loadClass(driver);

			Method mainMethod = mainClass.getMethod(methodName, String[].class);

			Thread.currentThread().setContextClassLoader(clsLoader);
			score = (Double)(method.invoke(null, oldOut, score, mainMethod, test));
			
		} catch (ClassNotFoundException e) {
			System.err.println("We could not find " + driver + ".class, are you sure you're running this from the correct spot?");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return score;
	}

	public static Double tester(PrintStream oldOut, Double score,
			Method mainMethod, Node test) throws XPathExpressionException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(baos));


		String label = getString(test, "./@label");
		String args = getString(test, "./args/text()");
		
		
		try {
			try {
				//This exists so that the vararg unfolding doesn't eat the string array
				Object[] param ={args.split(" ")};
				mainMethod.invoke(null, param);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.setOut(oldOut);
			
			String response = baos.toString("UTF-8").trim().replaceAll("\\r?\\n|\\r", "_NL_");
			System.err.println(label);
			
			for(Node n: getNodes(test, "mark")) {
				Double grade = Double.parseDouble(getString(n, "./@grade"));
				System.err.println("Testing for grade weight: " + grade);
				Node[] checks = getNodes(n, "check");
				for(Node check: checks) {
					System.err.println("\t"+getString(check, "./@label"));
					String regex = ".*" + getString(check, "regex/text()").trim().replaceAll("\\r?\\n|\\r", "_NL_");;
					Pattern pattern = Pattern.compile("(?s)(?i)" + regex);
					Matcher matcher = pattern.matcher(response);
					if(matcher.find()) {
						double gradePart = grade/checks.length;
						Node[] evals = getNodes(check, "eval");
						if(evals.length == 0) {
							System.err.println("\t\tExpecting: \"" + regex + "\" and found it. (+" + gradePart +")");
							score += gradePart;
						} else {
							System.err.println("\t\tcomplex testing.");
							//<eval step="2" stepval="0.2" maxstep="3">16</eval>
							double gradeSubPart = gradePart/evals.length;

							int group = 0;
							for(Node eval: evals) {
								group++;
								int step = Integer.parseInt(getString(eval, "./@step"));
								double stepval = Double.parseDouble(getString(eval, "./@stepval"));
								double maxstep = Double.parseDouble(getString(eval, "./@maxstep"));
								double target = Double.parseDouble(getString(eval, "text()"));
								double found = Double.parseDouble(matcher.group(group));
								Double stepsAwayFromTarget = Math.abs(target-found)/step;
								if(stepsAwayFromTarget > maxstep) {
									System.err.println("\t\tfound result '" + found + "' but to far away from expected value '" + target + "'");
								} else {
									double subgrade = (1-stepsAwayFromTarget*stepval)*gradeSubPart;
									System.err.println("\t\tfound result '" + found + "' within range of '" + target + "' worth " + subgrade + " of " + gradeSubPart + " (+"+subgrade+")");
									score+=subgrade;
								}
								
							}
							
						}
					} else {
						System.err.println("\t\tExpecting: \"" + regex + "\" but could not find it.");
					}
					
					
				}
			}
			
			/*
			for(String expectedRuslts: expected) {
				if(response.matches("(?s)(?i).*" + expectedRuslts + ".*")) {
					System.err.println("\tLooking for and found: \"" + expectedRuslts + "\"");
					score+=0.5/expected.length;
				} else {
					System.err.println("\tExpecting: \"" + expectedRuslts + "\" but could not find it");
				}
			}
			*/
			System.err.println();
			

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.err.println("This system does not support debug.");
		}
		
		return score;
	}	
	
	

	private static String getString(Node test, String xpath_query) throws XPathExpressionException {
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		return (String)xpath.compile(xpath_query).evaluate(test, XPathConstants.STRING);
	}
	
	private static Node[] getNodes(Node test, String xpath_query) throws XPathExpressionException {
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		NodeList nl = (NodeList)xpath.compile(xpath_query).evaluate(test, XPathConstants.NODESET);
		Node[] nodes = new Node[nl.getLength()];
		for(int i = 0; i < nl.getLength(); i++) {
			nodes[i] = nl.item(i);
		}
		
		return nodes;
		
	}


}
