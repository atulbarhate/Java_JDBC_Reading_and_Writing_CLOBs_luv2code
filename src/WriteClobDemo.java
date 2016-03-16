import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WriteClobDemo {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement myPreparedStatement = null;

        FileReader inputRead = null;

        try{
            // 1. Get a connection to database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?useSSL=false","root","123456");

            // 2. Prepare Statement
            String sql = "UPDATE employees SET resume=? WHERE email='john.doe@foo.com'";
            myPreparedStatement = connection.prepareStatement(sql);

            // 3. Set parameter for resume file name
            File theFile = new File("sample_resume.txt");
            inputRead = new FileReader(theFile);
            myPreparedStatement.setCharacterStream(1, inputRead);

            System.out.println("Reading input file: " + theFile.getAbsolutePath());

            // 4. Execute statement
            System.out.println("\nStoring resume in database: " + theFile);
            System.out.println(sql);

            myPreparedStatement.executeUpdate();

            System.out.println("\nCompleted successfully!");
        } catch (SQLException sqle) {
            System.out.println("Sql error: " + sqle.getMessage() + " , error code: " + sqle.getErrorCode());
            sqle.printStackTrace();
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found: " + fnfe.getMessage());
            fnfe.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (myPreparedStatement != null) {
                    myPreparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (inputRead != null) {
                    inputRead.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
