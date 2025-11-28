package appDomain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.EmptyQueueException;
import implementations.MyQueue;
import implementations.MyStack;

public class XMLParser
{
	private MyStack<XMLTag> stack;
	private MyQueue<XMLTag> errorQ;
	private MyQueue<XMLTag> extrasQ;
	/**
	 * Main method to run the parser from command line.
	 * 
	 * @param args Command line arguments. First argument should be XML File path.
	 * @throws EmptyStackException 
	 */
	
	public static void main(String[] args) throws EmptyStackException {
		if(args.length == 0) {
			System.out.println("Usage: java -jar Parser.jar <xml-file-path>");
			return;
		}
		
		XMLParser parser = new XMLParser();
		parser.parseFile(args[0]);
	}
	
	public XMLParser() {
		stack = new MyStack<>();
		errorQ = new MyQueue<>();
		extrasQ = new MyQueue<>();
	}
	
	/**
	 * parses the specified XML file and reports errors
	 * 
	 * @param filePath
	 * @throws EmptyStackException 
	 */
	
	public void parseFile(String filePath) throws EmptyStackException {
		File file = new File(filePath);
		
		if (!file.exists()) {
			System.out.println("Error: File not found - " + filePath);
			return;
		}
		
		System.out.println("Parsing XML file:" + filePath);
		System.out.println("==============================================");
		
		try (BufferedReader reader = new BufferedReader (new FileReader(file))) {
			String line;
			int lineNumber = 0;
			boolean hasRoot = false;
			int rootCount = 0;
			
			while ((line = reader.readLine()) != null) {
				lineNumber++;
				processLine(line, lineNumber, hasRoot);
				
				if (!hasRoot && !stack.isEmpty() && rootCount == 0) {
					hasRoot = true;
					rootCount++;
				}
			}
			
			processRemainingErrors();
			
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
	}
	
	/**
	 * Process a single line of XML.
	 * 
	 * @param line
	 * @param lineNumber
	 * @param hasRoot
	 * @throws EmptyStackException 
	 */
	
	private void processLine(String line, int lineNumber, boolean hasRoot) throws EmptyStackException {
		Pattern pattern = Pattern.compile("</?[^>]+>");
		Matcher matcher = pattern.matcher(line);
		
		while (matcher.find()) {
			String tagText = matcher.group();
			
			if (tagText.startsWith("<?") && tagText.endsWith("?>")) {	
				continue;
			}
			
			XMLTag tag = new XMLTag(tagText, lineNumber, line.trim());
			
			if (tag.isSelfClosing()) {
				continue;
			} else if (tag.isStartTag()) {
				stack.push(tag);
			} else if (tag.isEndTag()) {
				handleEndTag(tag);
			}
		}
	}
	
	/**
	 * 
	 * @param endTag
	 * @throws EmptyStackException 
	 */
	
	private void handleEndTag(XMLTag endTag) throws EmptyStackException {
		try  {
			if (!stack.isEmpty()) {
				XMLTag top = stack.peek();
				
				if (endTag.matches(top)) {
					stack.pop();
				} else if (!errorQ.isEmpty() && endTag.matches(errorQ.peek())) {
					errorQ.dequeue();
				} else {
					if (searchStackForMatch(endTag)) {
						popUntilMatch(endTag);
					} else {
						extrasQ.enqueue(endTag);
						}
					}
				} else if (!errorQ.isEmpty() && endTag.matches(errorQ.peek())) {
					errorQ.dequeue();
			} else {
				errorQ.enqueue(endTag);
			}
		} catch (EmptyQueueException e) {
			errorQ.enqueue(endTag);
		}
	}
	
	/**
	 * Searches the stack for  matching start tag.
	 * 
	 * @param endTag
	 * @return
	 */
	private boolean searchStackForMatch(XMLTag endTag) {
		utilities.Iterator<XMLTag> it = stack.iterator();
		while (it.hasNext()) {
			XMLTag tag = it.next();
			if (endTag.matches(tag)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Pops element from stack until matching tag is found,
	 * adding them to errorQ and reporting each as an error.
	 * 
	 * @param endTag
	 * @throws EmptyStackException 
	 */
	
	private void popUntilMatch(XMLTag endTag) throws EmptyStackException {
		while (!stack.isEmpty()) {
			XMLTag top = stack.pop();
			if (endTag.matches(top)) {
				printErrorLine(top);
				return;
			}
			
			errorQ.enqueue(top);
			printErrorLine(top);
			}
		}
	private void processRemainingErrors() throws EmptyStackException {
		while (!stack.isEmpty()) {
			XMLTag tag = stack.pop();
			errorQ.enqueue(tag);
		}
		
		try {
			while (!errorQ.isEmpty() || !extrasQ.isEmpty()) {
				if (errorQ.isEmpty() && !extrasQ.isEmpty()) {
					XMLTag extra = extrasQ.dequeue();
					printErrorLine(extra);
				} else if (!errorQ.isEmpty() && extrasQ.isEmpty()) {
					XMLTag error = errorQ.dequeue();
					printErrorLine(error);
				} else {
					XMLTag error = errorQ.peek();
					XMLTag extra = extrasQ.peek();
					
					if (error.matches(extra)) {
						errorQ.dequeue();
						extrasQ.dequeue();
					} else {
						errorQ.dequeue();
						printErrorLine(error);
					}
				}
			}
		} catch (EmptyQueueException e) {
			System.err.println("Unexpected error in queue processing:" + e.getMessage());
		}
	}
	
	/**
	 * Prints the line content for a tag with an error.
	 * 
	 * @param tag
	 */
	
	private void printErrorLine(XMLTag tag) {
		System.out.println(tag.getLineContent());
	}
	
	private static class XMLTag {
		private String fullTag;
		private String tagName;
		private String lineContent;
		private boolean isStartTag;
		private boolean isEndTag;
		private boolean isSelfClosing;
		
		/**
		 * Constructs an XMLTag from raw tag text.
		 * 
		 * @param fullTag
		 * @param lineNumber
		 * @param lineContent
		 */
		
		public XMLTag(String fullTag, int lineNumber, String lineContent) {
			this.fullTag = fullTag.trim();
			this.lineContent = lineContent;
			parseTag();
		}
		
		private void parseTag() {
			if (fullTag.endsWith("/>")) {
				isSelfClosing = true;
				tagName = extractTagName(fullTag.substring(1, fullTag.length() -2));
				return;
			}
			
			if (fullTag.startsWith("</")) {
				isEndTag = true;
				tagName = extractTagName(fullTag.substring(2,fullTag.length() -1));
			} else {
				isStartTag = true;
				tagName = extractTagName(fullTag.substring(1, fullTag.length() -1));
			}
		}
		
		/**
		 * 
		 * @param content
		 * @return
		 */
		
		private String extractTagName(String content) {
			int spaceIndex = content.indexOf(' ');
			if (spaceIndex != -1) {
				return content.substring(0, spaceIndex).trim();
			}
			return content.trim();
		}
		
		/**
		 * 
		 * @param other
		 * @return
		 */
		
		public boolean matches(XMLTag other) {
			if (other == null) return false;
			return this.tagName.equals(other.tagName);
		}
		/**
		 * 
		 * @return
		 */
		
		public String getLineContent() {
			return lineContent;
		}
		
		/**
		 * 
		 * @return
		 */
		
		public boolean isStartTag() {
			return isStartTag;
		}
		
		/**
		 * 
		 * @return
		 */
		
		public boolean isEndTag() {
			return isEndTag;
		}
		
		/**
		 * 
		 * @return
		 */
		
		public boolean isSelfClosing() {
			return isSelfClosing;
		}
	}
	}

	
	
	