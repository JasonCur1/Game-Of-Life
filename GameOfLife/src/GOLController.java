import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GOLController implements ActionListener, MouseListener, ChangeListener {

	private GOLModel model;
	private GOLView view;

	private Timer timer;

	private int timerSpeed = 500;

	public GOLController(GOLModel m, GOLView v) {
		this.model = m;
		this.view = v;
	}

	// button actions
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals(view.getStartButtonText())) {
			view.getStartButton().setVisible(false);
			view.getStopButton().setVisible(true);
			view.getClearButton().setVisible(false);
			view.getResetButton().setVisible(true);

			timer();


		} else if (command.equals(view.getStopButtonText())) {
			view.getStartButton().setVisible(true);
			view.getStopButton().setVisible(false);

			timer.cancel();

		} else if (command.equals(view.getNextButtonText())) {
			view.getClearButton().setVisible(false);
			view.getResetButton().setVisible(true);

			model.runIterationOfCellLogic();
			view.repaint();

		} else if (command.equals(view.getClearButtonText())) {
			model.clearCellArray();
			view.repaint();

		} else if (command.equals(view.getResetButtonText())) {
			view.getResetButton().setVisible(false);
			view.getClearButton().setVisible(true);

			model.restoreArchivedState();
			view.repaint();
		}
	}

	public void mousePressed(MouseEvent evt) {
		int col = (evt.getX()) / 25;
		int row = (evt.getY()) / 25;

		//DEBUGGING
		//System.out.println("Col: " + col + ", Row: " + row);

		// if the cell is alive, kill it
		if (model.getAliveState(col, row)) {
			model.makeCellDead(col, row);
		} else {
			model.makeCellAlive(col, row);
		}
		view.repaint();


		if (view.getResetButton().isVisible()) {
			view.getResetButton().setVisible(false);
			view.getClearButton().setVisible(true);
		}

		model.archiveCurrentCellArray();
	}

	public void mouseReleased(MouseEvent evt) { }
	public void mouseClicked(MouseEvent evt) { }
	public void mouseEntered(MouseEvent evt) { }
	public void mouseExited(MouseEvent evt) { }

	// Slider changes
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		timerSpeed = source.getValue();
		if (!source.getValueIsAdjusting()) {
			if (source.getValue() == 0) {
				timerSpeed = 1000;
			} else if (source.getValue() == 2) {
				timerSpeed = 100;
			} else {
				timerSpeed = 500;
			}
			
			// this stops an update to the slider from starting the simulation
			if (view.getStopButton().isVisible()) {
				timer.cancel();
				timer();
			}
			
			
		}
		
	}

	private void timer() {
		this.timer = new Timer();
		this.timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {

				view.getResetButton().setVisible(true);
				view.getClearButton().setVisible(false);
				model.runIterationOfCellLogic();
				view.repaint();
			}
		}, 0, timerSpeed);
	}
}
