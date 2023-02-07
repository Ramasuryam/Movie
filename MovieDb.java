package movie1;
import java.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;
public class MovieDb 
{
	    Scanner sc=new Scanner(System.in);
		public static void main(String[] args) 
		{
			Scanner sc=new Scanner(System.in);
			MovieDb i=new MovieDb();
			System.out.println("1.List Movies");
			System.out.println("2.Add Movie");
			System.out.println("3.Add Actor");
			System.out.println("4.Add Producer");
			System.out.println("5.Delete Movie");
			System.out.println("6.Exit");
			int r=0;
			while(r>=0)
			{
				System.out.println("What do you want");
				int a=sc.nextInt();
				switch(a)
				{
				case 1:
					i.listMovie();
					r=r+1;
					break;
				case 2:
					i.addMovie();
					r=r+1;
					break;
				case 3:
					i.addActor();
					r=r+1;
					break;
				case 4:
					i.addProducer();
					r=r+1;
					break;
				case 5:
					i.deleteMovie();
					r=r+1;
					break;
				case 6:
					System.out.println("Thankyou");
					r=-1;
					break;
					default:
						System.err.println("invalid Re-enter");
						break;
					}
	      } 
  }
	   public Connection getConnect()
		   {
			  try {
				  DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
				  Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
				   return con;
			    } 
			  catch (Exception e) 
			  {
				System.out.println(e);
			  } 
			  return null;
		   }
	   public void addActor() 
		{
			try {
				Connection con=getConnect();
				String query="insert into actor(actor_id,actor_name,dob) values(seq_actor.nextval,?,?)";
				PreparedStatement pst=con.prepareStatement(query);
				System.out.println("Actor Name:");
				String name=sc.nextLine();
				System.out.println("Actor Dob(DD-MM-YYYY) :");
				String dob=sc.next();
				sc.nextLine();
				SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date dob1=sdf.parse(dob);
				long dob2=dob1.getTime();
				java.sql.Date dob3=new java.sql.Date(dob2);
				pst.setString(1,name);
				pst.setDate(2, dob3);
				pst.executeUpdate();
				System.out.println("update sucessfull");
				con.commit();
				con.close();
			  }
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
	   public void addProducer()
		{
			try {
				Connection con=getConnect();
				String query="insert into producer(producer_id,producer_name,dob) values(seq_producer.nextval,?,?)";
				PreparedStatement pst=con.prepareStatement(query);
				System.out.println("Producer Name :");
				String name=sc.nextLine();
				System.out.println("Dob(DD-MM-YYYY) :");
				String dob=sc.next();
				sc.nextLine();
				SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date dob1=sdf.parse(dob);
				long dob2=dob1.getTime();
				java.sql.Date dob3=new java.sql.Date(dob2);
				pst.setString(1,name);
				pst.setDate(2, dob3);
				pst.executeUpdate();
				System.out.println("updated sucessfull");
				con.commit();
				con.close();
			  }
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
	   public void addMovie()
		{
			try {
				Connection con=getConnect();
				String query="insert into Movie(movie_id,movie_name,movie_year,plot,producer_id)values(seq_movie.nextval,?,?,?,?)";
				PreparedStatement pst=con.prepareStatement(query);
				System.out.println("Movie Name:");
				String movie_name=sc.nextLine();
			    System.out.println("Year");
			    int year=sc.nextInt();
			    System.out.println("Plot");
			    sc.nextLine();
			    String plot=sc.nextLine();
			    System.out.println("Choose producer :");
			    producer();
			    int producer=sc.nextInt();
			    pst.setString(1,movie_name);
			    pst.setInt(2,year);
			    pst.setString(3,plot);
			    pst.setInt(4,producer);
			    pst.executeUpdate();
			    con.commit();
			    System.out.println("Choose the actors");
			    actor();
			    int no=sc.nextInt();
			    int num=no;
				int size=0;
				while(num!=0)
				{
				   int ld=num%10;
				   size++;
				   num/=10;
				}
				int arr[]=new int[size];
				int i=0;
				do 
				{
			     arr[i]=no%10;
			      no/=10;
				  i++;
				 }while(no!=0);
				for(int j=arr.length-1;j>=0;j--)
				{
				   String selectmovie_id="SELECT movie_id FROM movie WHERE movie_name = ?";
				   pst =con.prepareStatement(selectmovie_id);
				   pst.setString(1,movie_name);
				   ResultSet rs=pst.executeQuery();
				   rs.next();
				   int movie_id=rs.getInt("movie_id");
				   
				String insertMovieActor = "INSERT INTO movie_actor (movie_id, actor_id) VALUES (?, ?)";
				pst=con.prepareStatement(insertMovieActor);
				int actor_id=arr[j];
				pst.setInt(1, movie_id);
				pst.setInt(2, actor_id);
				pst.executeUpdate();
				
				con.commit();
		}
				System.out.println("update Succesful");
				con.close();
			   } 
			catch (Exception e)
			{
			   System.out.println(e);
			}
	}
	   public void producer()
	    {
	    	try {
				Connection con=getConnect();
				PreparedStatement pst=con.prepareStatement("SELECT * FROM PRODUCER");
				ResultSet rs=pst.executeQuery();
				while(rs.next())
				{
					 System.out.print(rs.getString(1)+"."+rs.getString(2)+"  ");
				}
				rs.close();
				pst.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
	    }
	   
	   public void actor()
		{
			try {
				Connection con=getConnect();
				PreparedStatement pst=con.prepareStatement("SELECT * FROM Actor");
				ResultSet rs=pst.executeQuery();
				while(rs.next())
				{
					 System.out.print(rs.getString(1)+"."+rs.getString(2)+"  ");
				}
				rs.close();
				pst.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	   public void getMovie()
   	{
   		try {
   			Connection con=getConnect();
   			PreparedStatement pst=con.prepareStatement("SELECT * FROM Movie");
   			ResultSet rs=pst.executeQuery();
   			while(rs.next())
   			{
   				 System.out.print(rs.getString(1)+"."+rs.getString(2)+"  ");
   			}
   			rs.close();
   			pst.close();
   			con.close();
   		} catch (Exception e) {
   			System.out.println(e);
   		}
       }
	   public void deleteMovie()
		{
			try {
				System.out.println("which movie want to delete");
				getMovie();
				System.out.println();
				System.out.println("Enter the Movie Id");
				int movie_id=Integer.parseInt(sc.nextLine());
				Connection con=getConnect();
				PreparedStatement pst=con.prepareStatement("delete from movie where movie_id=?");
				pst.setInt(1,movie_id);
				int x=pst.executeUpdate();
				pst.close();
				con.commit();
				con.close();
				if(x==1)
				{
					System.out.println("Record deleted");
				}
				else
				{
					System.out.println("Record not found");
				}
				} 
			   catch (Exception e) 
			   {
				System.out.println(e);
			   }
		}
	   public void listMovie()
		{
			try {
				Connection con=getConnect();
				PreparedStatement pst=con.prepareStatement("SELECT * FROM MOVIE");
				ResultSet rs=pst.executeQuery();
				while(rs.next())
				{ 
					 String movie_id=rs.getString(1);
					 System.out.println(rs.getString(2)+"("+rs.getString(3)+")");
					 System.out.println("Plot- "+rs.getString(4));
					 System.out.print("Actors- : ");
					 PreparedStatement pst1=con.prepareStatement("SELECT ACTOR_NAME FROM ACTOR "
						+ "WHERE ACTOR_ID IN (SELECT ACTOR_ID FROM MOVIE_ACTOR WHERE MOVIE_ID=?)");
					 pst1.setString(1, movie_id);
					ResultSet rs1=pst1.executeQuery();
					while(rs1.next())
					{
						System.out.print(rs1.getString(1));System.out.print(",");
					}
					System.out.println();
					rs1.close();
					pst1.close();
		      PreparedStatement pst2=con.prepareStatement("SELECT PRODUCER_NAME FROM PRODUCER WHERE PRODUCER_ID IN"
				+ "(SELECT PRODUCER_ID FROM MOVIE WHERE MOVIE_ID=?)");
		      pst2.setString(1, movie_id);
			  ResultSet rs2=pst2.executeQuery();
			  System.out.print("Producer :-");
			  while(rs2.next())
			  {
			   System.out.println(rs2.getString(1));
			  }
			   System.out.println("------------------------------------");
			   rs2.close();
			   pst2.close();
			  }
				rs.close();
				pst.close();
				con.close();
			   }  
			catch (Exception e) 
			{
				System.out.println(e);
			}
	}
	
	   
}
