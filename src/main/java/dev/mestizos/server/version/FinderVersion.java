package dev.mestizos.server.version;

import com.unicenta.data.loader.Session;
import java.sql.SQLException;

import org.apache.commons.lang.SystemUtils;

public class FinderVersion {
    
    public static VersionDto getVersion(Session session) {
        System.out.println("Get Version");
        var versionDataBase = "";
        try {
            final var connection = session.getConnection();

            final var statement = connection.createStatement();
            final var resultSet = statement.executeQuery("select version() as version");
            
            while (resultSet.next()) {
                versionDataBase = resultSet.getString("version");
            }

        } catch (SQLException e) {
            versionDataBase = "Error: " + e.getMessage();
        }
        
        return new VersionDto(
            "1.0.1", 
            "Command POS", 
            "Jorge Luis", 
            SystemUtils.OS_NAME + " " + SystemUtils.OS_VERSION + " " + SystemUtils.OS_ARCH, 
            SystemUtils.JAVA_VERSION, 
            versionDataBase
            );
    }
}
