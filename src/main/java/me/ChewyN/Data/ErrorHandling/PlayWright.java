package me.ChewyN.Data.ErrorHandling;

import org.apache.commons.lang.exception.ExceptionUtils;

import java.io.IOException;

public class PlayWright {

    public void getImage(Throwable thrown) {

        String messageBody = ExceptionUtils.getStackTrace(thrown);
        String messageHeader = ExceptionUtils.getMessage(thrown).replaceAll("\"", "'");

        try {
            Process proc = Runtime.getRuntime().exec("java -jar KangarooBot/PlaywrightTest.jar" + messageBody + messageHeader);
//            return null;
        } catch (IOException e) {
            e.printStackTrace();
//            return null;
        }

    }

}
