import javax.swing.JFrame;


@SuppressWarnings("serial")
public class Main extends JFrame{

	public static void main(String[] args) {
		
		
		
		// TODO Auto-generated method stub
		
		JFrame f=new JFrame();
		f.setResizable(false);
		Snake p=new Snake();
		f.setContentPane(p);
		f.setVisible(true);
		f.setTitle("METAL GEAR");
		f.pack();
		p.requestFocusInWindow();
		f.setDefaultCloseOperation(EXIT_ON_CLOSE) ;
		
		p.start();
		
		
	}

}
