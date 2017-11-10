import java.sql.*;
import java.util.*;


public class MySQLDatabase {

   String uri_,driver_,user_,password_;

   static Connection conn;
   static String[][] sqlArr;

   public MySQLDatabase(){
       //default constructor
   }

   public MySQLDatabase(String uri_, String driver_, String user_, String password_){
   
      this.uri_ = uri_;
      this.driver_ = driver_;
      this.user_ = user_;
      this.password_ = password_;      
   
   }
   
       public String[][] getAllData(String tableName){


        try{
            //*******************get row count***************
            String sql = "SELECT * FROM "+ tableName + ";" ;
            //String sql = "SELECT * FROM " +

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int rowNum = 0;
            //get row count
            if(rs.last()){
               rowNum = rs.getRow();
               rs.beforeFirst(); 
            }

            
            //************************************************


            ResultSetMetaData rsmd = rs.getMetaData();
            //boolean yesNo = colnames;                           ///NEW
            int columnCount = rsmd.getColumnCount();

            String headers[] = new String[columnCount];

            //new

            for (int i = 1; i <= columnCount; i++){          //loops through and collects headings and their lengths

                headers[i-1] = rsmd.getColumnName(i);     //creates the column heading for chart


            }




            while(rs.next()){

                sqlArr = new String[rowNum][columnCount];
                for (int i = 0; i < rowNum; i++){
                    for(int j = 1; j <= columnCount; j++){
                        if(i == 0){
                            sqlArr[i][j-1] = headers[j-1]; //loop through column data from list(?) and populate this row with column headers
                        }else{
                            sqlArr[i][j-1] = rs.getString(j);

                        }
                    }

                }
            }

         System.out.println("Number of Rows retrieved: " + rowNum);

        }catch(SQLException sqle){
            //System.out.println("Error in getData(): SQL Statement not valid (?) ");

        }catch(NullPointerException npe){

        }

        return sqlArr;

    } // end getData();

   
   
   
   //*************************************** CONNECT *********************************
   
   public boolean makeConnection() {
   
      
      
      //load the drivers
      try{
         Class.forName( driver_ );
         //System.out.println("Loaded Driver");
      }
      catch(ClassNotFoundException cnfe){
         System.out.println("Cannot load driver: " + driver_);
         return false;
      }    
      
      //connect to db
      try{
         this.conn = DriverManager.getConnection( uri_, user_, password_ );
         //System.out.println("Connected to database");
         return true;
      }
      catch(SQLException sqle){
         System.out.println("Could not connect to db " + uri_);
         return false;
      }catch(Exception e) {
         e.printStackTrace();
         return false;
      }

   
   }//end make connection
   
   
   //****************************************** CLOSE ***************************************

   public boolean closeConnection(){
   
      try{
            conn.close();
            //System.out.println("DB closed");
            return true;
      }
      catch(SQLException sqle){
         System.out.println("Could not close db " + conn);
         return false;
      }catch(NullPointerException npe){
         System.out.print("Null pointer exception");
      }catch(Exception e) {
            e.printStackTrace();
      }

      
      return false;
   
   }// end  closeConnection()

//****************************************** descTable() ***************************************
  
   public void descTable(String statement){
      try{
            
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT " + statement);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            int rowCount = 0;
            String colType = "";
            String colName = ""; 
            
            //get row count
            if(rs.last()){
               rowCount = rs.getRow();
               rs.beforeFirst(); 
            }
            
            
            sqlArr = new String [rowCount][columnCount];
            
            int row = 0;
            
            //System.out.println("COLUM COUNT: " + columnCount);
            while(rs.next()){
               for (int i=1; i<=columnCount; i++){
                  sqlArr[row][i-1] = rs.getString(i);

               }
               row++;
            }
            
            
            
//*******************************************META DATA***********************************************
           System.out.println("+---------------------+--------------------+");
           System.out.println("| Field               | Type               |");
           System.out.println("+------------------------------------------+");
           for (int i = 1; i <= columnCount; i++)
           {
              System.out.printf ("| %-20s| %19s|\n",rsmd.getColumnName(i), rsmd.getColumnTypeName(i));
           }
           System.out.println("+---------------------+--------------------+\n\n");
           
           


//*******************************************SELECT STATEMENT***********************************************
           //figure out which row has the longest column, scale each row accordingly, cut off at 50(?)--47 and append...
           
           //get longest column
           String longestColVal[] = new String[columnCount];
           String colHeading[] = new String[columnCount]; //moved to global
           int    colLongest[] = new int[columnCount];
           String colHeadingIndex = "";
           String longest = "";
           //String numofStr = "";
           String headings = "";
           
           
           
 //                   **********************Acquire meta data for table******************************

            
           for (int i = 1; i <= columnCount; i++){          //loops through and collects headings and their lengths
               
               colHeading[i-1] = rsmd.getColumnName(i);     //creates the column heading for chart
               longestColVal[i-1] = rsmd.getColumnName(i);  //adds column headings to array as default length for columns
         
               
           }
        
//                   ********************Keeps all column lenth to size:15****************************
          
           //System.out.println(Arrays.deepToString(sqlArr));
           for (int i = 0; i <= columnCount-1; i++){
               
               //run through array of values from select statement
               //compare lengths from each column && length of header text
               //save value of longest column
               for(int j = 0; j <= rowCount-1; j++){
                  
                  if( sqlArr[j][i].length() > 15){
                     sqlArr[j][i] = sqlArr[j][i].substring(0,13) + "..";
                  }
                  
                  if( (sqlArr[j][i].length() > longest.length()) 
                  &&  (sqlArr[j][i].length()) > colHeading[i].length() ){ //0,0
                     
                     longest = sqlArr[j][i]; 
                     longestColVal[i] = longest;
                     
                  }
                  //trim the length of each column to not exceed over a length of 15
                  if(longestColVal[i].length() > 15){
                     longestColVal[i] = longestColVal[i].substring(0,13) + "..";
                  }
                  
                  if(colHeading[i].length() > 15){
                     colHeading[i] = colHeading[i].substring(0,13) + "..";
                  }
               }
               
                             
               longest = "";
               colLongest[i] = longestColVal[i].length();

           }
           
//                   ***************************Creates header and label String***********************

           String headerString   = "+";
           String headerLabelStr = "";

            for(int i = 0; i <= columnCount-1; i++){
               
               int numDash = colLongest[i]+2;
               int numSpace = (colLongest[i] - colHeading[i].length() ); //compares longest string to column header; finds difference

               headerString  += "" + String.join("", Collections.nCopies(numDash, "-")) + "+"; //uses numDash to determine the number of dashes needed for table border
               if(i == 0){
                  headerLabelStr += "| ";
               }
               headerLabelStr += colHeading[i] + String.join("", Collections.nCopies(numSpace, " ")) + " | "; //creates a string to be used as the header label 
               
            }

//                   ***************************Prints each row data*********************************
           String rowString = "";
           for( int i = 0; i <= rowCount-1; i++){
           
               if(i == 0){
                  System.out.println(headerString);            //Header fields added
                  System.out.println(headerLabelStr);
                  System.out.println(headerString);
               }
           
               for(int j = 0; j <= columnCount-1; j++){
                  if(j == 0){
                     rowString += "| ";
                  }
                  
                  int numSpace = (colLongest[j] - sqlArr[i][j].length() );                                     //compares longest column to length of row in data
                  rowString += sqlArr[i][j] + String.join("", Collections.nCopies(numSpace, " ")) + " | ";     //uses difference of longest column and current row data to add spaces
               }   
               
               System.out.println(rowString);
               rowString = "";
               
               if(i == rowCount-1){
                  System.out.println(headerString);
               }
           
           }
           
           
           
           
            
            
            System.out.println("# rows retrieved: " + rowCount);
            
         
         }catch(SQLException sqle){
            System.out.println("SQL exception");
         }catch(NullPointerException npe){
            System.out.println("Null pointer exception");
         }catch(Exception e) {
            e.printStackTrace();
         }
      
         
         //return sqlArr;
    
   } // end descTable();


    public static PreparedStatement prepare(String sql, String[] strvals){
        PreparedStatement ps = null;

        String preparedStr = sql; //sql string must contain ?
        //strvals list must contain values in order of ?


        try{
            //makeConnection();
            ps = conn.prepareStatement(preparedStr);

            //int j = 1;
            for(int i = 1; i <= strvals.length; i++){

                for(int j = 0; j <= strvals.length-1; j++){
                    ps.setString(i, strvals[j]);
                    //System.out.println("i: " + i + " j: " + j);

                }


            }

            //System.out.println(ps);

        }catch(SQLException sqle){
            //System.out.println("Could not connect to db " + uri_);

        }

        return ps;
    }


//****************************************** getData() ***************************************

   public static String[][] getData(String sql){
   
      try{
         
         
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         ResultSetMetaData rsmd = rs.getMetaData();
         int columnCount = rsmd.getColumnCount();
         
         sqlArr = new String [1][columnCount];
         
         int row = 0;
         while(rs.next()){
            for (int i=1; i<=columnCount; i++){
               sqlArr[row][i-1] = rs.getString(i);
            }
            row++;
         }
      
      }catch(SQLException sqle){
         System.out.println("Error in getData(): SQL Statement not valid (?) ");
        
      }catch(NullPointerException npe){
         System.out.println("Error in getData() : Null Pointer Exception");
      }catch(Exception e) {
            e.printStackTrace();
      }

   
      return sqlArr;
    
   } // end getData();


    public String[][] getData(String sql, String[] strvals){


        try{


            PreparedStatement stmt = prepare(sql, strvals);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            //boolean yesNo = colnames;                           ///NEW
            int columnCount = rsmd.getColumnCount();

            String headers[] = new String[columnCount];

            //new

            for (int i = 1; i <= columnCount; i++){          //loops through and collects headings and their lengths

                headers[i-1] = rsmd.getColumnName(i);     //creates the column heading for chart


            }




            while(rs.next()){

                sqlArr = new String[2][columnCount];
                for (int i = 0; i < 2; i++){
                    for(int j = 1; j <= columnCount; j++){
                        if(i == 0){
                            sqlArr[i][j-1] = headers[j-1]; //loop through column data from list(?) and populate this row with column headers
                        }else{
                            sqlArr[i][j-1] = rs.getString(j);

                        }
                    }

                }
            }


        }catch(SQLException sqle){
            //System.out.println("Error in getData(): SQL Statement not valid (?) ");

        }catch(NullPointerException npe){

        }

        return sqlArr;

    } // end getData();
//****************************************** setData() ***************************************
   public static boolean setData(String sql){
      boolean flag;
   
      try{
      
         Statement stmt = conn.createStatement();
         int rc = stmt.executeUpdate(sql);
         flag = true;
         
      
      
      }catch(SQLException se){
      //Handle errors for JDBC
      flag = false;
      se.printStackTrace();

      }catch(NullPointerException npe){
         flag = false;
         //Handle errors for Class.forName
         System.out.println("Null pointer exceptoin");

      }catch(Exception e) {
         e.printStackTrace();
         return false;
      }     
      return flag;
   
   }//end setData()

    public boolean setData(String sql, String[] strvals){
        boolean flag = true;

        if(executeStmt(sql, strvals) == -1){
            flag = false;
        }
        else{
            flag = true;
        }
        return flag;
    }

    public int executeStmt(String sql, String[] strvals){

        int rc = 0;

        try{

            PreparedStatement stmt = prepare(sql, strvals);
            //ResultSet rs = stmt.executeUpdate();
            rc = stmt.executeUpdate();


        }catch(SQLException sqle){

        }catch(NullPointerException npe){

        }

        return rc;
    }//end executeStmt


} // end program