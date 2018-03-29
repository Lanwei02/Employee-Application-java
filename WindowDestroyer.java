package hw5;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class WindowDestroyer extends WindowAdapter
{
	public void windowClosing(WindowEvent e)
	{
		File en = (File)e.getSource();
		en.savedata();
		System.exit(0);
	}
}
