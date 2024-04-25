package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.*;
import br.com.imagesoft.dateTimeNow.DateTimeGeneretor;

public class LogUtil {
    // Cria um formatador simples para formatar a saída para o arquivo de log
    private static final SimpleFormatter formatter = new SimpleFormatter();

    // Cria um logger global
    private static final Logger logger = Logger.getLogger("GlobalLogger");

    // Classe interna para lidar com diferentes níveis de log


    static {
        try {
            // Define o nível de log para todos os níveis
            logger.setLevel(Level.ALL);
            logger.setUseParentHandlers(false);
            String path = Paths.get("").toAbsolutePath().toString();
            String date = new DateTimeGeneretor().getDate();

            // Adiciona manipuladores de arquivo para cada nível de log
            addHandlerForLevel(Level.INFO, path+"/log/info", "logInfo-"+date+".log");
            addHandlerForLevel(Level.WARNING, path+"/log/warning", "logWarning-"+date+".log");
            addHandlerForLevel(Level.SEVERE, path+"/log/severe", "logSevere-"+date+".log");
        } catch (SecurityException | IOException e) {
            logger.severe("Erro ao settar o logger");
        }
    }

    // Método para adicionar um manipulador de arquivo para um nível de log específico
    private static void addHandlerForLevel(Level level, String directoryPath, String fileName) throws IOException {
        ensureDirectoryExists(directoryPath);
        logger.addHandler(new LevelFileHandler(level, directoryPath + "/" + fileName));
    }

    // Método para garantir que o diretório exista
    private static void ensureDirectoryExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists() && !directory.mkdirs()) {
            logger.severe("Não foi possivel criar o diretorio: " + directoryPath);
        }
    }

    // Método para obter o logger
    public static Logger getLogger() {
        return logger;
    }
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
}
