package AblaufBalken;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import AblaufBalken.ProzessBalken;

public class ProzessStartDialog extends JDialog implements ActionListener
{
	
	private static Container jContentPane;
	
	private static JButton prozessStartenButton;
	
	private static ProzessBalken prozessBalkenDialog;
	
	private static Thread thread1;
	
	private static ProzessFunktionList prozessFunktionlist;
	
	private static ArrayList<Integer> funktionList;
	
	private static double progressValue = 0;
	
	
	
	public static void main(String[] args) 
	{
		//Dialog, in dem ein Prozess durch betätigung des Buttons gestartet wird. Der Balken aber in einer anderen Klasse.
		dialogSettings();
	}

	
	private static void dialogSettings() 
	{
		JFrame window = new JFrame ("Homepage");
		window.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
		//window.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    window.setSize (1000, 800);
	    window.setLocationRelativeTo(null);
	    
	    prozessFunktionlist = new ProzessFunktionList();
	    prozessFunktionlist.getFunktionList();
	    funktionList = new ArrayList<Integer>(prozessFunktionlist.funktionList);
	    
	    window.add(getJContentPane());
	    window.setVisible (true);
	}
	
	
	private static Container getJContentPane() 
	{
		if(jContentPane==null)
		{
			GridBagLayout gbl_jContentPane = new GridBagLayout();
			ProzessStartDialog prozessStartDialog = new ProzessStartDialog();
			jContentPane = new JPanel(gbl_jContentPane);
			
			GridBagConstraints dialogConstraints = new GridBagConstraints();
			
			dialogConstraints.gridx = 0;
			dialogConstraints.gridy = 0;
			dialogConstraints.weightx = 1.0;
			dialogConstraints.weighty = 1.0;
			dialogConstraints.anchor = GridBagConstraints.CENTER;
			//dialogConstraints.fill = GridBagConstraints.HORIZONTAL;
			
			jContentPane.setBackground(Color.LIGHT_GRAY);
			
			prozessStartDialog.getButtonProzessStart();
			jContentPane.add(prozessStartenButton, dialogConstraints);
			
			return jContentPane;
		}
		return jContentPane;
	}

	
	private Component getButtonProzessStart() 
	{
		if ( prozessStartenButton == null)
		{
			prozessStartenButton = new JButton("Start");
			prozessStartenButton.setText("Start Prozess");
			prozessStartenButton.setBackground(Color.GREEN);
			prozessStartenButton.setPreferredSize(new Dimension(200, 70));
			prozessStartenButton.setActionCommand("buttonStart");
			prozessStartenButton.addActionListener(this);
			
			return prozessStartenButton;
		}
		return prozessStartenButton;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("buttonStart"))
		{
			prozessControler();
		}
	}
	
	
	private void prozessControler() 
	{
		prozessStartenButton.setEnabled(false); //Schaltet den Button aus
		prozessStartenButton.setBackground(Color.BLACK);
		prozessBalkenDialog = new ProzessBalken();
		prozessBalkenDialog.setTitle("ListenPrüfung");
		prozessBalkenDialog.setVisible(true);
		
		if (funktionList != null)
		{
			thread1 = new Thread(() -> 
			{
				progressBarStartVerarbeitung();
				
				//Wenn der Prozess beendet ist,
				//prozessBalkenDialog.setProgressBarValue(100, "100"); //Ist nur bei int notwendig gewesen, da der Bar nicht bis zum Ende gekommen ist.
				prozessStartenButton.setEnabled(true);
				prozessStartenButton.setBackground(Color.CYAN);
				prozessBalkenDialog.setProzessBarClose(true);
				prozessBalkenDialog = null;
				progressValue = 0;
			});
			thread1.start();
		}
		
	}


	private void progressBarStartVerarbeitung() 
	{
		
		//Werte in double, damitt die % zum progress richtig berechnet werden (z.B. Elemente in der Liste = 55 --> int progress 1%, double progress 1,818...%).
		//Mit int kahm der Bar bei 55 Objekten nur bis zur Mitte da der Progress = 1 war. 1 * 55 = 55% von 100.
		
//		int anzahlFunktionen = funktionList.size();
//		int progressBarMaximum = prozessBalkenDialog.getMaxValueBalken();
//		int progressGroesseInt = progressBarMaximum / anzahlFunktionen;
//		System.out.println("Die Progressgeöße (%) bei jedem einzelnen Progress: " + progressGroesse); 
		
		
		double anzahlFunktionenDouble = funktionList.size();
		double progressBarMaximumDouble = prozessBalkenDialog.getMaxValueBalken();
		double progressGroesseDouble = progressBarMaximumDouble / anzahlFunktionenDouble;
		System.out.println("Die Progressgeöße (%) bei jedem einzelnen Progress: " + progressGroesseDouble); 
		
		//Der nachste Teil wird nur gebraucht, wenn der Wachstums-% gerundet und in int umgerechnet werden soll. Wir machen es jetzt aber anders.
		//Jetzt wird das fertige Ergebniss gerundet und in int umgerechnet.
		//int progressGroesseInt = (int) Math.round(progressGroesseDouble); //Die Rundung des double Progress-Wertes z.B. von 1.8 zu 2. 
		//int maxValueProgressBar = (int) (progressGroesseInt * anzahlFunktionenDouble);//Einen sehr genauen Maxwert ausrechnen und runden.
		
		//int maxValueProgressBar = 100;
		//prozessBalkenDialog.setMaxValueBalken(maxValueProgressBar); //In diesem Fall sind es 100 die eh schon standartmäßig gesetzt sind.
		
		for(int i = 0; i < funktionList.size(); i++)
		{
			try 
			{
				Thread.sleep(1000); //1 Sekunden Pause = 1000; 2 = 2000 ...
			}
			catch (InterruptedException e)
			{
				System.out.println("In der Klasse ProzessStartDialog im Catch!");
			}
			
			progressValue = progressValue + progressGroesseDouble;//Beim Wechseln zwischen int und double, hier aktualisieren! 
			
			//System.out.println("Ausgabe für ProgressValue: " + progressValue);
			int intProgressValue = (int) Math.round(progressValue);
			System.out.println(i +1  + " / " + funktionList.size() + "     " + intProgressValue + "% / " + /*maxValueProgressBar*/ 100 + "%" + "     Geasamt %: " + progressValue);
			
//			prozessBalkenDialog.setProgressBarValueDouble(progressValue);//Wenn der 1. Uebergabeparameter ein Double ist
			prozessBalkenDialog.setProgressBarValueInt(intProgressValue);//Wenn der 1. Uebergabeparameter ein Int ist
		}
	}
	
	
	public static void prozessBeenden()
	{
		thread1.stop();
		prozessStartenButton.setEnabled(true);
		prozessStartenButton.setBackground(Color.CYAN);
		prozessBalkenDialog.setProzessBarClose(true); // oder prozessBalken.dispose();
		prozessBalkenDialog = null;
		progressValue = 0;
		System.err.println("Prozess abgebrochen.");
	}
}
