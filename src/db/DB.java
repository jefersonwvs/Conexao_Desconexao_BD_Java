package db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {
    
    private static Connection conn = null; 			/*Objeto da conexão com o BD*/
    
    private static Properties loadProperties() {    				/*Método que carrega as propriedades do banco de dados*/
	try (FileInputStream fs = new FileInputStream("db.properties")) {	// leitura do arquivo "db.properties" para o stream fs
	    Properties props = new Properties();				// instanciando um objeto do tipo Properties
	    props.load(fs);							// carregando as informações no fs
	    return props;							// retorno do objeto props devidamente carregado
	} catch (IOException e) {
	    throw new DbException(e.getMessage());
	}
    }    

    public static Connection getConnection() {			/* Método que retorna a conexão com o banco de dados */
	if (conn == null) { // Se ainda não estiver conectado
	    try {
		Properties props = loadProperties(); 		// carregamento das propriedades (guardadas no arquivo "db.properties") do BD
		String url = props.getProperty("dburl"); 	// carregamento da url do BD
		conn = DriverManager.getConnection(url, props); // criando a conexão com o BD (instanciando um objeto do tipo Connection)
	    } catch (SQLException e) {
		throw new DbException(e.getMessage());
	    }
	}
	return conn;
    }
    
    public static void closeConnection() { 			/* Método que fecha a conexão com o banco de dados */
	if (conn != null) {					// caso a conexão já não esteja encerrada
	    try {
		conn.close();					// encerra a conexão
	    } catch (SQLException e) {
		throw new DbException(e.getMessage());
	    }
	}
    }

}
