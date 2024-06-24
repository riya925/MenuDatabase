import java.io.File;
import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;

class DataOperation
{
	Scanner scan=new Scanner(System.in);
	void show()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("\n Driver Registered Successfully.... ");
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost/gtukb","root","");
			System.out.println("\n Database Found Successfully");
			
			Statement st=con.createStatement();
			
			ResultSet rs=st.executeQuery("\n select * from mytab");
			
			ResultSetMetaData rsm=rs.getMetaData();
			
			System.out.println("\n TABLE : "+rsm.getTableName(1));
			System.out.println("\n COLUMN : "+rsm.getColumnCount());
			
			for(int i=1;i<=rsm.getColumnCount();i++)
			{
				System.out.println(" "+rsm.getColumnName(i)+" : "+rsm.getColumnTypeName(i));
			}
			
			while(rs.next()) //rs will point to no records so we have to do rs.next() even to print one row.
			{
				int a=rs.getInt("sid");
				String b=rs.getString("sna");
				int c=rs.getInt("sag");
				System.out.println(""+a+" , "+b+" , "+c);
			}
			rs.close();
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println("\n SHOW ERROR : "+e.getMessage());
		}
	}
	void insert()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("\n Driver Registered Successfully.... ");
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost/gtukb","root","");
			System.out.println("\n Database Found Successfully");
			
			Statement st=con.createStatement();
			
			int a,c;
			String b;
			
			System.out.println("\n ENTER THE ID : ");
			a=scan.nextInt();
			System.out.println("\n ENTER THE NAME : ");
			b=scan.next();
			System.out.println("\n ENTER THE AGE : ");
			c=scan.nextInt();
			
			st.executeUpdate("insert into mytab values("+a+",'"+b+"',"+c+")");
			
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println("\n SHOW ERROR : "+e.getMessage());
		}
	}
	void delete()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("\n Driver Registered Successfully.... ");
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost/gtukb","root","");
			System.out.println("\n Database Found Successfully");
			
			Statement st=con.createStatement();
			
			System.out.println("\n Enter the value to be deleted : ");
			int a=scan.nextInt();
			
			st.executeUpdate("delete from mytab where sid="+a);
			
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println("\n SHOW ERROR : "+e.getMessage());
		}
	}
	void update()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("\n Driver Registered Successfully.... ");
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost/gtukb","root","");
			System.out.println("\n Database Found Successfully");
			
			Statement st=con.createStatement();
			
			System.out.println("\n Enter the updated value : ");
			int a=scan.nextInt();
			System.out.println("\n Enter the value you want to update : ");
			int b=scan.nextInt();
			st.executeUpdate("update mytab set sag ="+a+" where sag ="+b);
			
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println("\n SHOW ERROR : "+e.getMessage());
		}
	}
	void updatable() 
	//updatable is used to show as well as update facility provided by jdbc as both cannot be performed together in simple method
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("\n Driver Registered Successfully.... ");
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost/gtukb","root","");
			System.out.println("\n Database Found Successfully");
			
			Statement st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			//type scroll sensitive is used to move forward and backward in the table while insensitive is used to move forward only
			//and concur updatable is used to update
			
			ResultSet rs=st.executeQuery("select * from emp");
			
			rs.absolute(1); //jumps to record having 2 id
			rs.updateString("ena","Liya");
			rs.updateInt("eag",45);
			rs.updateRow();
			
			rs.absolute(5);//write only row no not field value otherwise it will give error
			rs.updateString("ena","Riya");
			rs.updateRow();
			
			rs.absolute(3);
			rs.updateString("ena","tina");
			rs.updateRow();
			
			rs.first();//first points to first value in table
			rs.previous();//previous point to the previous value pointed by first
			while(rs.next())
			{
				int a=rs.getInt("eid");
				String b=rs.getString("ena");
				int c=rs.getInt("eag");
				System.out.println(""+a+" , "+b+" , "+c);
			}
			
			rs.close();
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println("\n SHOW ERROR : "+e.getMessage());
		}
	}
	void preparedInsert()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("\n Driver Registered Successfully.... ");
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost/gtukb","root","");
			System.out.println("\n Database Found Successfully");
			
			//Statement st=con.createStatement();
			PreparedStatement pst=con.prepareStatement("insert into mytab value(?,?,?)");
			
			int id=1;
			
			while(id!=0)
			{
				System.out.println("\n ENTER THE ID : ");
				id=scan.nextInt();
				System.out.println("\n ENTER THE NAME : ");
				String name=scan.next();
				System.out.println("\n ENTER THE AGE : ");
				int age=scan.nextInt();
				
				//st.executeUpdate("insert into mytab values("+a+",'"+b+"',"+c+")");
				if(id!=0)
				{
					pst.setInt(1,id);
					pst.setString(2,name);
					pst.setInt(3,age);
					pst.execute();
				}
			}
			
			//st.close();
			pst.close();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println("\n SHOW ERROR : "+e.getMessage());
		}
	}
	void preparedDelete()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("\n Driver Registered Successfully.... ");
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost/gtukb","root","");
			System.out.println("\n Database Found Successfully");
			
			//Statement st=con.createStatement();
			PreparedStatement pst=con.prepareStatement("delete from mytab where sid=(?)");
				
			System.out.println("\n Enter the value to be deleted : ");
			 int id=scan.nextInt();
			//st.executeUpdate("delete from mytab where sid="+a);
					
			pst.setInt(1,id);
			pst.execute();
			
			//st.close();
			pst.close();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println("\n SHOW ERROR : "+e.getMessage());
		}
	}
	void preparedUpdate()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("\n Driver Registered Successfully.... ");
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost/gtukb","root","");
			System.out.println("\n Database Found Successfully");
			
			//Statement st=con.createStatement();
			PreparedStatement pst=con.prepareStatement("update mytab set sag =(?) where sag =(?)");
			
			int id=1;
			
			while(id!=0)
			{
				System.out.println("\n Enter the updated value : ");
				id=scan.nextInt();
				System.out.println("\n Enter the value you want to update : ");
				String name=scan.nextLine();
				
				//st.executeUpdate("update mytab set sag ="+a+" where sag ="+b);
				
				if(id!=0)
				{
					pst.setInt(1,id);
					//pst.setString(2,name);
					//pst.setInt(3,age);
					pst.execute();
				}
			}
			
			//st.close();
			pst.close();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println("\n SHOW ERROR : "+e.getMessage());
		}
	}
	void callableInsert()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("\n Driver Registered Successfully.... ");
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost/gtukb","root","");
			System.out.println("\n Database Found Successfully");
			
			//Statement st=con.createStatement();
			CallableStatement cst=con.prepareCall(" { call insertQ(?,?,?) }");
			
			System.out.println("\n ENTER THE ID : ");
			int id=scan.nextInt();
			System.out.println("\n ENTER THE NAME : ");
			String name=scan.next();
			System.out.println("\n ENTER THE AGE : ");
			int age=scan.nextInt();
			
			//st.executeUpdate("insert into mytab values("+a+",'"+b+"',"+c+")");
			cst.setInt(1, id);
			cst.setString(2, name);
			cst.setInt(3, age);
			cst.execute();
			
			//st.close();
			cst.close();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println("\n SHOW ERROR : "+e.getMessage());
		}
	}
	void callableOut()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("\n Driver Registered Successfully.... ");
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost/gtukb","root","");
			System.out.println("\n Database Found Successfully");
			
			CallableStatement cst=con.prepareCall(" { call with_out(?,?) }");
			
			System.out.println("\n Enter id: ");
			int a=scan.nextInt();
			
			cst.setInt(1, a);
			cst.registerOutParameter(2, Types.VARCHAR);
			cst.execute();
			
			System.out.println("\n Name : "+cst.getString(2));
			
			cst.close();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println("\n SHOW ERROR : "+e.getMessage());
		}
	}
	void myBatch()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("\n Driver Registered Successfully.... ");
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost/gtukb","root","");
			//System.out.println("\n Database Found Successfully");
			
			Statement st=con.createStatement();
			
			st.addBatch("insert into mytab values(16,'BG',160)");
			st.addBatch("insert into mytab values(17,'JUNIORS',170)");
			st.addBatch("delete from mytab where sid=100");
			
			st.executeBatch();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println("\n SHOW ERROR : "+e.getMessage());
		}
	}
	void upload()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost/gtukb","root","");
			PreparedStatement pst=con.prepareStatement("insert into imgtab values(?,?)");
			
			pst.setInt(1, 1);
			
			File f1=new File("src//images//images.jpg");
			
			FileInputStream fis=new FileInputStream(f1);
			
			pst.setBinaryStream(2, fis);
			pst.execute();
			
			pst.close();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println("\n FILE ERROR : "+e.getMessage());
		}
	}
}

public class MyMenuData
{
	public static void main(String[] args)
	{
		Scanner scan=new Scanner(System.in);
		int ch=0;
		DataOperation d1=new DataOperation();
		
		while(ch!=14)
		{
			System.out.println("\n DATABASE MENU.... ");
			System.out.println("\n 1.SHOW ");
			System.out.println("\n 2.INSERT ");
			System.out.println("\n 3.DELETE");
			System.out.println("\n 4.UPDATE");
			System.out.println("\n 5.UPDATABLE");
			System.out.println("\n 6.PREPARED INSERT");
			System.out.println("\n 7.PREPARED DELETE");
			System.out.println("\n 8.PREPARED UPDATE");
			System.out.println("\n 9.CALLABLE INSERT ");
			System.out.println("\n 10.CALLABLE OUT ");
			System.out.println("\n 12.BATCH METHOD ");
			System.out.println("\n 13.UPLOAD FILE ");
			System.out.println("\n 14.EXIT ");
			System.out.println("\n SELECT YOUR OPTION : ");
			ch=scan.nextInt();
			
			switch(ch)
			{
				case 1: d1.show();
						break;
				case 2 : d1.insert();
						break;
				case 3 : d1.delete();
						break;
				case 4 : d1.update();
						break;
				case 5 : d1.updatable();
						break;
				case 6 : d1.preparedInsert();
						break;
				case 7 : d1.preparedDelete();
						break;
				case 8 : d1.preparedUpdate();
						break;
				case 9 : d1.callableInsert();
						break;
				case 10 : d1.callableOut();
						break;
				case 12 : d1.myBatch();
						break;
				case 13 : d1.upload();
							break;
			}
		}
	}
}