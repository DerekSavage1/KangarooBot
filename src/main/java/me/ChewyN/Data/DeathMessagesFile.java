package me.ChewyN.Data;

import me.ChewyN.Main;

import java.util.List;

public class DeathMessagesFile extends AbstractFile{

    public DeathMessagesFile(Main instance) {
        super(instance, "death_messages");


        //FIXME this breaks bc static assignment in AbstractFile
//        if(!config.contains("Messages")){
//            config.set("messages", "{is no longer with us, passed away}");
//        }

    }

    public List<String> getDeathMessages(){
        return config.getStringList("Messages");
    }

}
