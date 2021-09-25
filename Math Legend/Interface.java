import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

 

public class Interface extends JFrame implements ActionListener, KeyListener{
	JFrame frame=new JFrame("MATH LEGEND");
    JLabel label=new JLabel("Welcome to MATH LEGEND!");
    JPanel panel=new JPanel();
    JButton studentLog = new JButton("STUDENT");   //add buttonm
    JButton teacherLog = new JButton("TEACHER");
    JButton exit = new JButton("EXIT");
    
    public Interface() {
    	 studentLog.setBounds(300, 200, 100, 30);
         teacherLog.setBounds(300, 260, 100, 30);
         exit.setBounds(300, 320, 100, 30);
         exit.addActionListener(this);
         //frame.setLayout(null);
         panel.setLayout(null);
       //  label.setHorizontalAlignment(SwingConstants.CENTER);
       // label.setVerticalAlignment(SwingConstants.CENTER);
         label.setBounds(280,100,200,30);
         panel.add(teacherLog);
         panel.add(studentLog);
         panel.add(label);
         panel.add(exit);
         //panel.setLayout(null);
         frame.getContentPane().add(panel);
         frame.pack();
         frame.setSize(800, 600);
         frame.setVisible(true);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();

	if (source == exit) {
			System.exit(0);
	}
	}
    public static void main(String args[]){
        
        //JFrame.setDefaultLookAndFeelDecorated(true);
        
    	Interface simple = new Interface();
        
    }
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}