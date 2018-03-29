package hw5;

import java.io.Serializable;
public class Employee implements Serializable
{
	private int eid;
	private String ename;
	private double esalary;
	public static final long serialVersionUID = 1L;

	public Employee(int i, String n, double s)
	{
		eid = i;
		ename = n;
		esalary = s;
	}

    public void setData(int i, String n, double s)
    {
		eid = i;
	    ename = n;
		esalary = s;
	}
    public int getEID()
    {
		return eid;
	}

	public String getEName()
	{
		return ename;
	}

	public double getESalary()
	{
			return esalary;
	}
	public String toString()
	{
		String s = "";
		s += "ID = " + eid + " Name = " + ename;
		s += " Salary = " + esalary;
		return s;
	}
}

