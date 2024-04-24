package org.example;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

public class LogUtil {
    // Cria um formatador simples para formatar a saída para o arquivo de log
    private static SimpleFormatter formatter = new SimpleFormatter();

    // Cria um logger global
    private static Logger logger = Logger.getLogger("GlobalLogger");

    // Classe interna para lidar com diferentes níveis de log
    private static class LevelFileHandler extends FileHandler {
        private final Level level;

        public LevelFileHandler(Level level, String filename) throws IOException, SecurityException {
            // Passa 'true' para anexar ao arquivo existente
            super(filename, true);
            this.level = level;
            setFormatter(formatter);
            setLevel(level);
        }

        @Override
        public synchronized void publish(LogRecord record) {
            // Publica apenas registros de log que correspondem ao nível especificado
            if (record.getLevel() == level) {
                super.publish(record);
            }
        }
    }

    static {
        try {
            String directoryPathInfo = "log/info";
            String directoryPathWarning = "log/warning";
            String directoryPathSevere = "log/severe";
            ensureDirectoryExists(directoryPathInfo);
            ensureDirectoryExists(directoryPathWarning);
            ensureDirectoryExists(directoryPathSevere);

            // Define o nível de log para todos os níveis
            logger.setLevel(Level.ALL);

            // Adiciona manipuladores de arquivo para cada nível de log
            logger.addHandler(new LevelFileHandler(Level.INFO, directoryPathInfo+"/logInfo.log"));
            logger.addHandler(new LevelFileHandler(Level.WARNING, directoryPathWarning+"/logWarning.log"));
            logger.addHandler(new LevelFileHandler(Level.SEVERE, directoryPathSevere+"/logSevere.log"));
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    // Método para garantir que o diretório exista
    private static String ensureDirectoryExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directoryPath;
    }

    // Método para obter o logger
    public static Logger getLogger() {
        return logger;
    }
}
