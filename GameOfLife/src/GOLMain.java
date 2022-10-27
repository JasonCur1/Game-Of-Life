import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GOLMain {

	public static void main(String[] args) {
		JFrame window = new JFrame("Game Of Life");
        
        // Model, following Observer pattern, is oblivious.  It doesn't need a reference to anyone
        GOLModel model = new GOLModel();
        GOLView view = new GOLView(model);
        
        //  CheckersController controller = new CheckersController(model); // view is created and hooked up inside controller constructor 
        // **Changed to have view take in the controller, not controller take in view.
        // Because we need a reference to the view here, so set it as content pane.  
        // Or I suppose you could have a getView() method in the controller
        
        window.setContentPane(view);
        window.pack(); 
        
        //****this centers the window on the users screen
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation( (screensize.width - window.getWidth())/2,
                (screensize.height - window.getHeight())/2 );
        
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.setResizable(false);  
        window.setVisible(true);

	}

}
