
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class skiSimWorker extends JFrame implements ActionListener{
	JButton startButton, stopButton, checkButton;
	private MySwingWorker worker;
	JTextArea textArea = new JTextArea();

    public skiSimWorker() {
        setTitle("Ski Lift Simulator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	 
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(1000, 500));

        Container panel = getContentPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		panel.add(setUpToolsPanel(), BorderLayout.NORTH);
		panel.add(setUpButtons(), BorderLayout.SOUTH);
		
		setSize(1000, 700);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		setLocation((d.width - frameSize.width) / 2, (d.height - frameSize.height) / 2);
    }
    
    
	public JPanel setUpToolsPanel() {
		
		JTextField seatsNumberTxt = new JTextField("10");
		seatsNumberTxt.setToolTipText("Set Lift Seat Number");
		seatsNumberTxt.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField)e.getSource();
				try {
					int newValue = Integer.parseInt(tf.getText());
					if (newValue > 0)
					 	skiSimulation.setSeatsNumber(newValue);
					else
						tf.setText(skiSimulation.getSeatsNumber()+"");
				} catch (Exception ex) {
					tf.setText(skiSimulation.getSeatsNumber()+"");
				}
			}
		});
		
		JTextField liftSpeedTxt = new JTextField("1000");
		liftSpeedTxt.setToolTipText("Set Lift Speed");
		liftSpeedTxt.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField)e.getSource();
				try {
					int newValue = Integer.parseInt(tf.getText());
					if (newValue > 0)
						skiSimulation.setLiftSpeed(newValue);
					else
						tf.setText(skiSimulation.getLiftSpeed()+"");
				} catch (Exception ex) {
					tf.setText(skiSimulation.getLiftSpeed()+"");
				}
			}
		});
		
		JTextField skiersNumberTxt = new JTextField("30");
		skiersNumberTxt.setToolTipText("Set Skiers Number");
		skiersNumberTxt.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField)e.getSource();
				try {
					int newValue = Integer.parseInt(tf.getText());
					if (newValue > 0)
						skiSimulation.setSkiersNumber(newValue);
					else
						tf.setText(skiSimulation.getSkiersNumber()+"");
				} catch (Exception ex) {
					tf.setText(skiSimulation.getSkiersNumber()+"");
				}
			}
		});
		
		JTextField slopeTimeTxt = new JTextField("12000");
		slopeTimeTxt.setToolTipText("Set Maximum Slope Time");
		slopeTimeTxt.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField)e.getSource();
				try {
					int newValue = Integer.parseInt(tf.getText());
					if (newValue > 0)
						skiSimulation.setSlopeTime(newValue);
					else
						tf.setText(skiSimulation.getSlopeTime()+"");
				} catch (Exception ex) {
					tf.setText(skiSimulation.getSlopeTime()+"");
				}
			}
		});
		
		JTextField probabilityTxt = new JTextField("0.05");
		probabilityTxt.setToolTipText("Set Probability Lift Stops (up to 8000ms)");
		probabilityTxt.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField)e.getSource();
				try {
					double newValue = Double.parseDouble(tf.getText());
					if (newValue > 0.0)
						skiSimulation.setProbability(newValue);
					else
						tf.setText(skiSimulation.getProbability()+"");
				} catch (Exception ex) {
					tf.setText(skiSimulation.getProbability()+"");
				}
			}
		});
		
		JPanel toolsPanel = new JPanel();
		toolsPanel.add( new JLabel(" Probability Lift Stops: ", JLabel.RIGHT));
		toolsPanel.add(probabilityTxt);
		toolsPanel.setLayout(new BoxLayout(toolsPanel, BoxLayout.X_AXIS));
		toolsPanel.add( new JLabel(" Number of Seats: ", JLabel.RIGHT));
		toolsPanel.add(seatsNumberTxt);
		toolsPanel.add( new JLabel(" Speed of Lift: ", JLabel.RIGHT));
		toolsPanel.add(liftSpeedTxt);
		toolsPanel.add( new JLabel(" Number of Skiers: ", JLabel.RIGHT));
		toolsPanel.add(skiersNumberTxt);
		toolsPanel.add( new JLabel(" Time Down Slope: ", JLabel.RIGHT));
		toolsPanel.add(slopeTimeTxt);

		return toolsPanel;

    }
	
	public JPanel setUpButtons() {
		JPanel buttonPanel= new JPanel(new FlowLayout());
    	startButton = makeButton("Start");
    	stopButton = makeButton("Stop");
    	checkButton = makeButton("Check");
		stopButton.setEnabled(false);
		
		buttonPanel.add(startButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(checkButton);

		return buttonPanel;
		
	}
    
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("Start" == e.getActionCommand()){
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
			(worker = new MySwingWorker(textArea)).execute();
		} 
		
		else if ("Stop" == e.getActionCommand()) {
				startButton.setEnabled(true);
				stopButton.setEnabled(false);
				worker.cancel(true);
				worker = null;
			}
		
		if ("Check" == e.getActionCommand()){
            textArea.append("GUI responsive!\n");
            }
	}
	
	private JButton makeButton(String caption) {
		JButton b = new JButton(caption);
		b.setActionCommand(caption);
		b.addActionListener(this);
		return b;
	}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new skiSimWorker().setVisible(true);
            }
        });
    }
	
}
