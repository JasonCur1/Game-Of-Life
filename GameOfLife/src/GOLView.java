import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import javafx.scene.control.Button;

public class GOLView extends JPanel{
	
	private GOLModel model;
	private GOLController controller;

    private JButton startBtn;  
	private JButton stopBtn;
	private JButton nextBtn;
	private JButton clearBtn;
	private JButton resetBtn;
	
	private JSlider speedSlider;
	
	// these constants can't be the actual delay number because the constructor for the slider needs the left to be less than the right
	// intuitively, left is slow and right is fast. But the timer is measured in delay, so 500 is faster than 1000.
	private static final int SLOW = 0;
	private static final int MEDIUM = 1;
	private static final int FAST = 2;

	public GOLView(GOLModel m) {
		this.model = m;
		this.controller = new GOLController(model, this);
		
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(750,788));
		
		startBtn = new JButton("Start");
		startBtn.addActionListener(controller);
		
		stopBtn = new JButton("Stop");
		stopBtn.addActionListener(controller);
		
		nextBtn = new JButton("Next");
		nextBtn.addActionListener(controller);
		
		clearBtn = new JButton("Clear");
		clearBtn.addActionListener(controller);
		
		resetBtn = new JButton("Reset");
		resetBtn.addActionListener(controller);
		
		JPanel buttons = new JPanel(new FlowLayout());		
		
		buttons.add(startBtn);
		buttons.add(stopBtn);
		buttons.add(nextBtn);
		buttons.add(clearBtn);
		buttons.add(resetBtn);
		
		stopBtn.setVisible(false);
		resetBtn.setVisible(false);
		
		CellBoard board = new CellBoard();
		board.addMouseListener(controller);
		board.setBounds(0,0,750,750);
		
		speedSlider = new JSlider(JSlider.HORIZONTAL, SLOW, FAST, MEDIUM);
		speedSlider.addChangeListener(controller);
		speedSlider.setMajorTickSpacing(5);
		
		buttons.add(speedSlider);
		
		
		this.add(buttons, BorderLayout.NORTH);
		this.add(board, BorderLayout.CENTER);
	}
	
	@SuppressWarnings("serial")
	private class CellBoard extends JPanel {
		
		CellBoard() {
			setBackground(Color.WHITE);
		}
		
		public void paintComponent(Graphics g) {	
			g.setColor(Color.black);
            
            for (int i = 0; i < 30; i++) {
            	for (int j = 0; j < 30; j++) {
            		if (model.getAliveState(i, j)) {
            			g.setColor(Color.YELLOW);
            			g.fillRect(i * 25, j * 25, 25, 25);
            			g.setColor(Color.BLACK);
            			g.drawRect(i * 25, j * 25, 25, 25);
            		} else {
            			g.drawRect(i * 25, j * 25, 25, 25);
            		}
            		
            		
            	}
            }

		}		
	}
	
    public String getStartButtonText() {
 		return startBtn.getText();
 	}
    
    public JButton getStartButton() {
    	return startBtn;
    }
    
    public String getStopButtonText() {
 		return stopBtn.getText();
 	}
    
    public JButton getStopButton() {
    	return stopBtn;
    }
    
    public String getNextButtonText() {
 		return nextBtn.getText();
 	}
    
    public JButton getNextButton() {
    	return nextBtn;
    }
    
    public String getClearButtonText() {
 		return clearBtn.getText();
 	}
    
    public JButton getClearButton() {
    	return clearBtn;
    }
    
    public String getResetButtonText() {
 		return resetBtn.getText();
 	}
    
    public JButton getResetButton() {
    	return resetBtn;
    }
}