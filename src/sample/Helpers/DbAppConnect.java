package sample.Helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DbAppConnect {

    public static Connection getConnection(){
        Connection connection = null;

        try{
            //Class.forName("com.mysql.jdbc.Driver");
            //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jawa_eng? useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
            connection = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net/sql12352964","sql12352964","frht8R9pDU");
        }catch (Exception e){
            e.printStackTrace();
        }

        return connection;
    }

}
