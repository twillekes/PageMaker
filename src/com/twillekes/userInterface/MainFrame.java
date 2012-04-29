package com.twillekes.userInterface;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.twillekes.jsonImporter.Importer;
import com.twillekes.portfolio.Portfolio;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	// Object fields
    private JButton copyButton;
    private JButton cutButton;
    private JButton pasteButton;
    private JButton exitButton;


    /**
     * Public no-arg constructor
     */
    public MainFrame() {

        super("Simple AWT Example");
        setSize(450, 250);

        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );

        ActionListener buttonListener = new ActionListener() {
        
            public void actionPerformed(ActionEvent ae) {

                String action = ae.getActionCommand();
                
                if (action.equals("Exit")) {
                    dispose();
                    System.out.println("Exiting.");
                    System.exit(0);
                } else {
                    System.out.println(action);
                }
            }
            
        };


        // Toolbar Panel
        JPanel toolbarPanel = new JPanel();
        toolbarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        copyButton = new JButton("Copy");
        copyButton.addActionListener(buttonListener);
        toolbarPanel.add(copyButton);

        cutButton = new JButton("Cut");
        cutButton.addActionListener(buttonListener);
        toolbarPanel.add(cutButton);

        pasteButton = new JButton("Paste");
        pasteButton.addActionListener(buttonListener);
        toolbarPanel.add(pasteButton);

        add(toolbarPanel, BorderLayout.NORTH);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();

        exitButton = new JButton("Exit");
        exitButton.addActionListener(buttonListener);
        bottomPanel.add(exitButton);
        
        add(bottomPanel, BorderLayout.SOUTH);
        
        // TBD...

        Portfolio portfolio = new Portfolio();
		Importer importer = new Importer();
		importer.populate(portfolio);
		
        PortfolioUserInterface portfolioUserInterface = new PortfolioUserInterface(portfolio);
        
		this.add(portfolioUserInterface, BorderLayout.CENTER);
		this.pack();
    }




    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {

        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);

    }

}
