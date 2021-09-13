package me.ChewyN.Data;

import me.ChewyN.Discord.Listeners.DiscordMessageHandler;

public class ConsoleScanner implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        DiscordMessageHandler.sendToAdminChannel("Uncaught Exception");
        DiscordMessageHandler.sendToAdminChannel("```java\n" + e.getStackTrace() + "```");
        System.out.println("AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
    }
}
