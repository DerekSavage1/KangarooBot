package me.ChewyN.Data.ErrorHandling;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;

public class ApacheTest implements org.apache.logging.log4j.core.Filter {


    @Override
    public Result getOnMismatch() {
        return null;
    }

    @Override
    public Result getOnMatch() {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
        return null;
    }

    @Override
    public Result filter(LogEvent event) {

        if(event.getThrown() != null) {
//            DiscordMessageHandler.sendToDebugChannel();
            new PlayWright().getImage(event.getThrown());
        }
        return null;
    }

    public static void sendDebugMessage(Throwable exception) {

//        String stacktrace = ExceptionUtils.getStackTrace(exception);
//        String message = ExceptionUtils.getMessage(exception);
//
//
//        String where = ExceptionUtils.getRootCauseMessage(exception);
//
//        EmbedBuilder errorMessage = new EmbedBuilder();
////        errorMessage.setImage(url);
//
//        errorMessage.setTitle("Unhandled Exception Detected");
//        errorMessage.setColor(0xFF3333);
//        errorMessage.setFooter(where);
//
//        DiscordMessageHandler.sendToDebugChannel("Hello");
//        errorMessage.clear();

    }


    @Override
    public State getState() {
        return State.STARTED;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isStarted() {
        return true;
    }

    @Override
    public boolean isStopped() {
        return false;
    }
}
