public class Cell {
	private boolean alive;
	
	public Cell() {
		alive = false;
	}
	
	public void setAliveState(boolean bool) {
		alive = bool;
	} 
	
	public boolean getAliveState() {
		return alive;
	}

}
