import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Cliente {
	
	
	static Scanner teclado = new Scanner(System.in);
	
	public static void muestraErrorSQL(SQLException e) {
	    System.err.println("SQL ERROR mensaje: " + e.getMessage());
	    System.err.println("SQL Estado: " + e.getSQLState());
	    System.err.println("SQL código específico: " + e.getErrorCode());
	  }
	
	  
	  public static void main(String[] args) {
		  
		  String basedatos = "midb";
		  String host = "localhost";
		  String port = "3306";
		  String parAdic = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		  String urlConnection = "jdbc:mysql://" + host + ":" + port + "/" + basedatos + parAdic;
		  String user = "root";
		  String pwd = "";
		  Connection conexion = null;
		  ResultSet r = null;
		  Statement stmt = null;
		  Boolean salir = false;
		 
			
			int posicion = 1;
		  
		  try  {
			  conexion = DriverManager.getConnection(urlConnection, user, pwd);
		      System.out.println("Conexión realizada.");
		      
		    } catch (SQLException e) {
		      muestraErrorSQL(e);
		    } catch (Exception e) {
		      e.printStackTrace(System.err);
		    }
		  
		 try {
			stmt = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 try {
			r = stmt.executeQuery("SELECT * FROM clientes");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
			try {
				if(r.first()) {
					String DNI = r.getString("DNI");
					String APELLIDOS = r.getString("APELLIDOS");
					String CP = r.getString("CP");
					System.out.println("ROW " + posicion + " = " + DNI + " " + APELLIDOS + " " + CP);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	    
	    
	    
	    
	    while(!salir) {
	    	
	    	String opcion = menu();
		    switch(opcion) {
		    case ".":
		    	try {
		    		r.close();
					conexion.close();
					salir = true;
					System.out.println("Vuelve pronto...");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	break;
		    case "k":
		    	try {    		
		    		if(!r.isLast()) {
		    			posicion++;
		    			if(r.next()) {
		    				String DNI = r.getString("DNI");
		    				String APELLIDOS = r.getString("APELLIDOS");
		    				String CP = r.getString("CP");
		    				System.out.println("ROW " + posicion + " = " + DNI + " " + APELLIDOS + " " + CP);
		    			}
		    		}
		    		else {
		    			System.out.println("Estas en ultima fila");
		    		}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	break;
		    case "d":
		    	try {
		    		if(!r.isFirst()) {
		    			posicion--;
		    			if(r.previous()) {
		    				String DNI = r.getString("DNI");
		    				String APELLIDOS = r.getString("APELLIDOS");
		    				String CP = r.getString("CP");
		    				System.out.println("ROW " + posicion + " = " + DNI + " " + APELLIDOS + " " + CP);
		    			}
		    		}
		    		else {
		    			System.out.println("Estas en primera fila");
		    		}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	break;
		    default:
		    	try {
		    		
					if(r.absolute(Integer.parseInt(opcion))) {
						String DNI = r.getString("DNI");
						String APELLIDOS = r.getString("APELLIDOS");
						String CP = r.getString("CP");
						System.out.println("ROW " + r.getRow() + " = " + DNI + " " + APELLIDOS + " " + CP);
					}
					else {
						System.out.println("Opcion no válida");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	catch(NumberFormatException e1) {
		    		System.out.println("Opcion no válida " + e1.getMessage());
		    	}
		    }
	    	
	    }
	    

	  }
	  
	  public static String menu() {
		  System.out.println("Elige una opcion: \n . -para finalizar \n k -para avanzar de fila \n d -para retroceder \n s para salir \n Tambien puedes ir a la fila con su numero");
		  String opcion = teclado.nextLine();
		return opcion;
	  }
	

}

