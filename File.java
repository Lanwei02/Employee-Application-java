package hw5;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;
public class File extends JFrame implements ActionListener
{
	private JTextField eid;
	private JTextField en;
	private JTextField es;
	private JPanel panel4;
	private JButton undo;
	private JButton save;
	private JLabel label;
	private ArrayList<Employee> list=new ArrayList<Employee>();
	private Employee employee;
	private int number=0;
	private int currentnumber=0;
public File()
{
	setSize(1000,300);
	setTitle("Employee Data");
	
	Container content = getContentPane();//
	content.setBackground(Color.LIGHT_GRAY);
	content.setLayout(new BorderLayout());
	
	label=new JLabel();
	label.setForeground(Color.WHITE);
	content.add(label, BorderLayout.CENTER);
	
	JPanel panel1=new JPanel();
	panel1.setBackground(Color.YELLOW);
	content.add(panel1,BorderLayout.NORTH);
	panel1.setLayout(new GridLayout(2,2));
	
	JPanel panel11=new JPanel();
	panel11.setBackground(Color.yellow);
	panel11.setLayout(new FlowLayout());
	panel1.add(panel11);
	JLabel label1=new JLabel("Employee ID:");
	panel11.add(label1);
	eid=new JTextField(10);
	panel11.add(eid);
	
	JPanel panel12=new JPanel();
	panel12.setBackground(Color.yellow);
	panel12.setLayout(new FlowLayout());
	panel1.add(panel12);
	JLabel label2=new JLabel("Employee Name:");
	panel12.add(label2);
	en=new JTextField(30);
	panel12.add(en);
	
	JPanel panel13=new JPanel();
	panel13.setBackground(Color.yellow);
	panel13.setLayout(new FlowLayout());
	panel1.add(panel13);
	JLabel label3=new JLabel("Employee Salary:");
	panel13.add(label3);
	es=new JTextField(6);
	panel13.add(es);
	
	JPanel panel2=new JPanel();
	content.add(panel2,BorderLayout.SOUTH);
	panel2.setLayout(new BorderLayout());
	
	JPanel panel3=new JPanel();
	panel3.setBackground(Color.cyan);
	panel2.add(panel3,BorderLayout.WEST);
	
	JButton next=new JButton("Next");
	next.addActionListener(this);
	panel3.add(next);
	JButton previous=new JButton("Previous");
	previous.addActionListener(this);
	panel3.add(previous);
	
	panel4=new JPanel();
	panel4.setBackground(Color.cyan);
	panel2.add(panel4,BorderLayout.EAST);
	
	undo=new JButton("Undo");
	undo.addActionListener(this);
	panel4.add(undo);
	save=new JButton("Save");
	save.addActionListener(this);
	panel4.add(save);
	
	JMenu file=new JMenu("File");
	JMenuBar menubar=new JMenuBar();
	menubar.add(file);
	setJMenuBar(menubar);
	
	JMenu modify=new JMenu("Modify");
	file.add(modify);
	JMenuItem edit=new JMenuItem("Edit");
	edit.addActionListener(this);
	modify.add(edit);
	JMenu misc=new JMenu("Misc");
	file.add(misc);
	JMenuItem exit=new JMenuItem ("Exit");
	exit.addActionListener(this);
	misc.add(exit);
	
	addWindowListener(new WindowDestroyer());
}

public void actionPerformed(ActionEvent e)
{
	Container content = getContentPane();
	String action=e.getActionCommand();
	if (action.equals("Next"))
    {
        next();
    }
    else if (action.equals("Previous"))
    {
        previous();
    }
    else if (action.equals("Undo"))
    {
        editundo();
        browse();
    }
    else if (action.equals("Save"))
    {
        editsave();
        browse();
    }
    else if (action.equals("Edit"))
    {
        edit();
    }
    else if (action.equals("Exit"))
    {
      savedata();
      System.exit(0);
    }
    else
    {
      System.out.println("error");
    }
	
}
public void next()
{
	if(list.isEmpty())
	{
		System.out.println("Empty");	
	}
	else
	{
		if(currentnumber==(number-1))
		{
			label.setText("Already at last record");
		}
		else
		{
			currentnumber++;
			label.setText(" ");
			//1//write
			//this.write();
			//2//next
			this.setText(employee);
		}		
	}
}
public void previous()
{
	if(currentnumber==0)
	{
		label.setText("Already at first record");
	}
	else
	{
		currentnumber--;
		label.setText(" ");
		this.setText(employee);
	}
}
public void write()
{
	edit();
	int id=Integer.parseInt(eid.getText());
	String name=en.getText();
	double salary=Double.parseDouble(es.getText());
	Employee saveemployee=new Employee(id,name,salary);
	list.add(currentnumber,saveemployee);
	System.out.println(list.get(currentnumber));
	System.out.println(currentnumber);
}
public void edit()
{
	eid.setEditable(true);
	en.setEditable(true);
	es.setEditable(true);
	panel4.setVisible(true);
	undo.setVisible(true);
	save.setVisible(true);
	//System.out.println("edit");
}
public void editsave()
{
	if(list.isEmpty())
	{
		int id=Integer.parseInt(eid.getText());
		String name=en.getText();
		double salary=Double.parseDouble(es.getText());
		Employee saveemployee=new Employee(id,name,salary);
		list.add(currentnumber,saveemployee);
	}
	else
	{
		employee = ((Employee)list.get(currentnumber));
	    employee.setData(Integer.parseInt(eid.getText()), en.getText(), Double.parseDouble(es.getText()));
	    browse();
	}
	//System.out.println(list.get(currentnumber));	
}
public void editundo()
{
	setText(employee);
}
public void firststep()
{
	readdata(list);
	setText(employee);
	browse();
}
public void readdata(ArrayList<Employee> read)
{
	ObjectInputStream readData;
	try
	{
		readData=new ObjectInputStream(new FileInputStream("employeedata.dat"));
		try
		{
			while(true)
			{
				read.add((Employee)readData.readObject());
				number++;
			}
			}
			catch(EOFException e1){}
			catch(Exception e2)
			{
				System.out.println(e2.getMessage()+"2");
			}
			 System.out.println("Read " + number + " records");
			 readData.close();
	}
		catch(FileNotFoundException e3)
		{
			System.out.println(e3.getMessage()+"3");
		}
		catch(IOException e4)
		{
			System.out.println(e4.getMessage()+"4");
		}
	}
public void savedata()
{
	ObjectOutputStream saveData=null;
	try{
		saveData=new ObjectOutputStream(new FileOutputStream("employeedata.dat", false));
		for(Employee saveemployee:list)
		{
			saveData.writeObject(saveemployee);
		}
		//System.out.println("Wrote " + currentnumber + " records");
		System.out.println("Wrote " + number + " records");
		saveData.close();
	}
	catch(Exception e)
	{
		System.out.println(e.getMessage()+"save");
	}
}
public void setText(Employee employeeText)
{
	if (list.isEmpty())
	{
		 System.out.println("No records in the Employee List.");
		 System.out.println("Please click\"Edit\"");
		 System.out.println("and then write information.");
	}
	else
	{
		employeeText=((Employee)list.get(currentnumber));
		eid.setText(Integer.toString(employeeText.getEID()));
		en.setText(employeeText.getEName());
		es.setText(Double.toString(employeeText.getESalary()));
	}
}

public void browse()
{
	eid.setEditable(false);
	en.setEditable(false);
	es.setEditable(false);
	panel4.setVisible(false);
	undo.setVisible(false);
	save.setVisible(false);
}

public static void main(String[] args)
{
	File file=new File();
	file.firststep();
	file.setVisible(true);
}

}

