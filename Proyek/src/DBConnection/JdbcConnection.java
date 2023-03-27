package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcConnection {
    private static final Logger LOGGER =
            Logger.getLogger(JdbcConnection.class.getName());

    private static Optional<Connection> connection = Optional.empty();

    public static Optional<Connection>getConnection(){
        if (connection.isEmpty()) {
            // Jenis database yang digunakan
            String dbType = "jdbc:postgresql:";
            // URL database yang dituju. localhost berarti sama dengan 127.0.0.1
            String dbUrl  = "//localhost:";
            // Port yang digunakan untuk koneksi dengan database. DEFAULT-nya 5432
            // TERGANTUNG proses instalasi
            String dbPort = "5432/";
            // Nama Database yang digunakan
            String dbName = "Proyek";
            // Nama User Database
            String dbUser = "postgres";
            // Password User
            String dbPass = "admin";

            try {
                connection = Optional.ofNullable(
                        // Menggunakan java.sql.DriverManager
                        // untuk mendapatkan koneksi
                        DriverManager.getConnection(
                                dbType+dbUrl+dbPort+dbName, dbUser, dbPass
                        )
                );
                if (connection != null) {
                    // Jika koneksi berhasil dibuat
                    System.out.println("Connection OK!");
                } else {
                    System.out.println("Connection Failed!");
                }
            } catch (SQLException ex) {
                // Jika di dalam try ada terjadi error
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        return connection;
    }
}
