package me.Skyla.Data;

import me.ChewyN.Data.AbstractFile;
import me.ChewyN.Main;

//TODO make me

/**
 * File to store last death location of players so players can still tp back if they log off or the server shuts down.
 * @author Skyla
 */
public class LastDeathFile extends AbstractFile {
    public LastDeathFile(Main _main, String fileName) {
        super(_main, fileName);
    }


}
