package AblaufBalken;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.lang.Thread.State;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;

import AblaufBalken.ProzessFunktionList;



public class ProzessBalken extends JDialog
{
	
	//TODO HI TEST
	private JPanel jContentPane;
	
	private Container jContentPaneLabel;
	
	private JPanel panelProgressBar;
	
	private JProgressBar progressBar = null;
	
	private JButton buttonCancel;
	
	private JLabel progressProzent;
	
	private JLabel progressLabel;
	
	
	
	public ProzessBalken()
	{
		//TODO In dem Prozessbalken eine Prozentangeba einbauen.
		
		//Die Moeglichketen unten hat der Dialog nicht mehr
		/*
		this.setAlwaysOnTop(true);
		this.setTitle("Prozess Status");
		this.setDefaultCloseOperation (JDialog.DO_NOTHING_ON_CLOSE);
		//this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		*/
		//this.setBackground(Color.LIGHT_GRAY);
		//this.getContentPane().setBackground(Color.BLACK);
		//setIconImage();
		
		this.setUndecorated(true);
		this.setSize(350, 110);
		this.setLocationRelativeTo(null);
		
		//dialogProzess.setVisible(true);
		this.add(getJContentPane());
		
		//this = null; 
		//funktionListDurchlaufen(/*progressBar*/);
	}

	
	private JPanel getJContentPane() 
	{
		if(jContentPane == null)
		{
			GridBagLayout labelUndProzessBalken = new GridBagLayout();
			jContentPane = new JPanel(labelUndProzessBalken);
			jContentPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			
			GridBagConstraints constraintsPanelProgressBarLabel = new GridBagConstraints();
			constraintsPanelProgressBarLabel.fill = GridBagConstraints.NONE;
			constraintsPanelProgressBarLabel.weightx = 0.0;
			constraintsPanelProgressBarLabel.weighty = 0.0;
			constraintsPanelProgressBarLabel.gridx = 0;
			constraintsPanelProgressBarLabel.gridy = 0;
			constraintsPanelProgressBarLabel.ipadx = 100;
			jContentPane.add(getPanelProgressBarLabelAndText(), constraintsPanelProgressBarLabel);
			
			GridBagConstraints constraintsPanelProgressBar = new GridBagConstraints();
			constraintsPanelProgressBar.fill = GridBagConstraints.NONE;
			constraintsPanelProgressBar.weightx = 0.0;
			constraintsPanelProgressBar.weighty = 0.0;
			constraintsPanelProgressBar.gridx = 0;
			constraintsPanelProgressBar.gridy = 1;
			constraintsPanelProgressBar.ipadx = 100;
			jContentPane.add(getPanelProgressBar(), constraintsPanelProgressBar);
			
			GridBagConstraints contentConstraints = new GridBagConstraints();
			//JButton prozessCancel = new JButton("Abbrechen");
			contentConstraints.fill = GridBagConstraints.HORIZONTAL;
			contentConstraints.gridx = 0;
			contentConstraints.gridy = 2;
			jContentPane.add(getCancelButton(), contentConstraints);
			
		}
		return jContentPane;
	}
	
	
	private JButton getCancelButton()
	{
		if (buttonCancel == null)
		{
			buttonCancel = new JButton();
			buttonCancel.setText("Abbrechen");
			buttonCancel.setEnabled(true);
			buttonCancel.setBackground(Color.ORANGE);
			buttonCancel.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					//System.out.println("Klick");
					//progressAbbruchMethode();
					AblaufBalken.ProzessStartDialog.prozessBeenden();
				}
			});
		}
		return buttonCancel;
	}


	private Component getPanelProgressBar() 
	{
		if (panelProgressBar == null)
		{
			GridBagConstraints constraintsProgressBar = new GridBagConstraints();
			constraintsProgressBar.gridx = 0;
			constraintsProgressBar.fill = GridBagConstraints.HORIZONTAL;
			constraintsProgressBar.weightx = 1.0;
			constraintsProgressBar.weighty = 1.0;
			constraintsProgressBar.gridy = 0;
			//constraintsProgressBar.insets = new Insets(0, 50, 0, 50);

			panelProgressBar = new JPanel(new GridBagLayout());
			
			panelProgressBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			panelProgressBar.add(getProgressBar(), constraintsProgressBar);
		}
		return panelProgressBar;
	}
	
	
	private Component getPanelProgressBarLabelAndText() //TODO dass die % über dem ProgressBar dargestellt werden.
	{
		if (jContentPaneLabel == null)
		{
			GridBagLayout labelUndProzessBalken = new GridBagLayout();
			jContentPaneLabel = new JPanel(labelUndProzessBalken);
			GridBagConstraints contentConstraints = new GridBagConstraints();
			
			progressLabel = new JLabel("Prozessablauf:");
			contentConstraints.gridx = 0;
			contentConstraints.fill = GridBagConstraints.HORIZONTAL;
			contentConstraints.weightx = 1.0;
			contentConstraints.weighty = 1.0;
			contentConstraints.gridy = 0;
			//constraintsProgressBar.insets = new Insets(0, 50, 0, 50):
			jContentPaneLabel.add(progressLabel, contentConstraints);
			
			progressProzent = new JLabel("0%");
			contentConstraints.gridx = 1;
			contentConstraints.fill = GridBagConstraints.HORIZONTAL;
			contentConstraints.weightx = 1.0;
			contentConstraints.weighty = 1.0;
			contentConstraints.gridy = 0;
			//constraintsProgressBar.insets = new Insets(0, 50, 0, 50):
			jContentPaneLabel.add(progressProzent, contentConstraints);
			
		}
		return jContentPaneLabel;
	}

	
	private JProgressBar getProgressBar() 
	{
		if (progressBar == null)
		{
			progressBar = new JProgressBar();
			progressBar.setMaximum(100);
			progressBar.setMinimum(0);
			progressBar.setToolTipText("0 %");
		}
		return progressBar;
	}
	
	
	public void setProgressBarValueInt(int value)
	{
		progressBar.setValue(value);
		
		String stringValue = String.valueOf(value);
		progressBar.setToolTipText(stringValue + "%");
		progressProzent.setText(stringValue + "%");
	}
	
	
	public void setProgressBarValueDouble(double value)
	{
		int valueInt = (int) Math.round(value);
		setProgressBarValueInt(valueInt);
	}
	
	
	public int getMaxValueBalken()
	{
		//int maxValue = progressBar.getMaximum();
		return  progressBar.getMaximum();
	}
	
	
	public void setMaxValueBalken(int maxValue)
	{
		progressBar.setMaximum(maxValue);
	}
	
	
	public void setProzessBarClose(Boolean prozessBeendet)
	{
		if (prozessBeendet == true)
		{
			this.dispose(); 
		}
	}
}
