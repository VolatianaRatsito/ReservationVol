package models;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private static final String CONFIG_PATH = "D:\\Students\\S5\\Mr Naina\\Ticketing\\Vols\\applications.proporties";
    private static Properties properties = new Properties();

    static {
        try (FileInputStream input = new FileInputStream(CONFIG_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier de configuration : " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Optionnel : méthode dédiée
    public static String getUploadDirectory() {
        return getProperty("upload.directory");
    }
}

