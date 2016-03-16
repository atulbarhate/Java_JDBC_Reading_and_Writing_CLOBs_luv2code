import java.io.*;
import java.sql.*;

public class ReadClobDemo {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet myRs = null;

        Reader input = null;
        FileWriter output = null;
        try{
            // 1. Get a connection to database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?useSSL=false","root","123456");

            // 2. Execute Statement
            statement = connection.createStatement();
            String sql = "SELECT resume FROM employees WHERE email='john.doe@foo.com'";
            myRs = statement.executeQuery(sql);

            // 3. Set up a handle to the file
            File theFile = new File("resume_from_db.txt");
            output = new FileWriter(theFile);

            if (myRs.next()) {
                input = myRs.getCharacterStream("resume");
                System.out.println("Reading resume from database...");

                int theChar;
                while ((theChar = input.read()) > 0){
                    output.write(theChar);
                }
            }

            System.out.println("\nSaved to file: " + theFile.getAbsolutePath());

            System.out.println("\nCompleted successfully!");

             } catch (SQLException sqle) {
            System.out.println("Sql error: " + sqle.getMessage() + " , error code: " + sqle.getErrorCode());
            sqle.printStackTrace();
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found: " + fnfe.getMessage());
            fnfe.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (myRs != null) {
                    myRs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
