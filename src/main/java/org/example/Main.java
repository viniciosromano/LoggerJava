package org.example;

import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = LogUtil.getLogger();
        logger.info("Test1");
        logger.warning("Test2");
        logger.severe("Test3");
        new Test();
        logger.log(Level.WARNING, "Não foi possivel criar o diretorio: " );
        logger.warning("Não foi possivel criar o diretorio1: ");


    }
}
