package main;

import java.awt.EventQueue;

import user_interface.GUI;

public class Main {
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


}
