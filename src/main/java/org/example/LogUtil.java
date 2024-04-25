package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
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
            // Define o nível de log para todos os níveis
            logger.setLevel(Level.ALL);
            String path = Paths.get("").toAbsolutePath().toString();
            // Adiciona manipuladores de arquivo para cada nível de log
            addHandlerForLevel(Level.INFO, path+"/log/info", "logInfo.log");
            addHandlerForLevel(Level.WARNING, path+"/log/warning", "logWarning.log");
            addHandlerForLevel(Level.SEVERE, path+"/log/severe", "logSevere.log");
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    // Método para adicionar um manipulador de arquivo para um nível de log específico
    private static void addHandlerForLevel(Level level, String directoryPath, String fileName) throws IOException {
        System.out.println(directoryPath);
        ensureDirectoryExists(directoryPath);
        logger.addHandler(new LevelFileHandler(level, directoryPath + "/" + fileName));
    }

    // Método para garantir que o diretório exista
    private static void ensureDirectoryExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    // Método para obter o logger
    public static Logger getLogger() {
        return logger;
    }
}
