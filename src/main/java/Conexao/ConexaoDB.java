package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

    private static final String url = "jdbc:mysql://localhost:3306/gestao";
    private static final String user = "root";
    private static final String password = "1234";

    private static Connection conn;
    public  static Connection getConexaoDB(){
        try {
            if (conn == null){
                conn = DriverManager.getConnection(url,user,password);
                return conn;
            }else {
                return conn;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro, ao conectar"+e);
            return null;
        }
    }
}
