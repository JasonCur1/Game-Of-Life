
public class GOLModel {
	
	// archived cells keep track of original population in case reset button is pressed
	private Cell[][] cells;
	private boolean[][] archivedCells;
	
	public GOLModel() {
		cells = new Cell[30][30];
		archivedCells = new boolean[30][30];
		populateArray();
	}
	
	private void populateArray() {
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 30; j++) {
				cells[i][j] = new Cell();
			}
		}
	}
	
	public void runIterationOfCellLogic() {
		// make copy of cells array
		// copy is useful so that the order in which cells are updated doesn't affect the next cell
		// i.e. if cells are updated top to bottom, left to right, then some cells would live and die differently than if all cells are updated at once like a blanket
		boolean[][] cellsCopy = new boolean[cells.length][cells[0].length];
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 30; j++) {
				cellsCopy[i][j] = cells[i][j].getAliveState();
			}
		}
		
		
		
		for (int x = 0; x < 30; x++) {
			for (int y = 0; y < 30; y++) {
				// if cell is alive then check conditions to be killed
				if (cellsCopy[x][y]) {
					if (getNumAdjacentCells(cellsCopy, x, y) <= 1) {
						cells[x][y].setAliveState(false);
						// DEBUGGING
						//System.out.println("Cell " + x + ", " + y + " died of isolation");
					} else if (getNumAdjacentCells(cellsCopy, x, y) >= 4) {
						cells[x][y].setAliveState(false);
						// DEBUGGING
						//System.out.println("Cell " + x + ", " + y + " died of overpopulation");
					}	
				// else cell is dead, then check conditions to be brought to life 
				} else {
					if (getNumAdjacentCells(cellsCopy, x, y) == 3) {
						cells[x][y].setAliveState(true);
						// DEBUGGING
						//System.out.println("Cell " + x + ", " + y + " brought to life");
					}
				}
			}
		}
	}
	
	public int getNumAdjacentCells(boolean[][] c, int x, int y) {
		int count = 0;
		
		for (int p = x - 1; p <= x + 1; p++) {
			for (int q = y - 1; q <= y + 1; q++) {
				
				if (p == x && q == y) {
					continue;
				}
				
				try {
					if (c[p][q]) {
						count++;
					}
				} catch(IndexOutOfBoundsException e) {
					continue;
				}
			}
		}
		return count;
	}
	
	public void makeCellAlive(int x, int y) {
		cells[x][y].setAliveState(true);
	}
	
	public void makeCellDead(int x, int y) {
		cells[x][y].setAliveState(false);
	}
	
	public boolean getAliveState(int x, int y) {
		return cells[x][y].getAliveState();
	}
	
	public void clearCellArray() {
		cells = new Cell[30][30];
		archivedCells = new boolean[30][30];
		populateArray();
	}
	
	public void archiveCurrentCellArray() {
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 30; j++) {
				archivedCells[i][j] = cells[i][j].getAliveState();
			}
		}
	}
	
	public void restoreArchivedState() {
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 30; j++) {
				cells[i][j].setAliveState(archivedCells[i][j]);
			}
		}
	}
	
}
