package exceptions;

import java.util.Arrays;

public class IllegalInputHeaderException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6785550377469155124L;
	String[] inputHedaer;


	public IllegalInputHeaderException(String[] inputHedaer) {
		super();
		this.inputHedaer = inputHedaer;
	}
	
	 @Override
		public String toString() {
			return Arrays.toString(inputHedaer);
		}





}
