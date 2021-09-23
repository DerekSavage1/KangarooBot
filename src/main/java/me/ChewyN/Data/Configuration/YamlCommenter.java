package me.ChewyN.Data.Configuration;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class YamlCommenter
{
    public static void addComments(File file,Map<Integer, String> comments)
    {
        //load all data from file
        FileInputStream stream;
        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException e)
        {
            return;
        }
        Reader reader=new InputStreamReader(stream);
        BufferedReader input = new BufferedReader(reader);
        List<String> toSave= new ArrayList<>();
        try
        {
            String temp;
            try
            {
                while ((temp = input.readLine()) != null)
                {
                    toSave.add(temp);
                }
            }
            catch (IOException e)
            {
                return;
            }
        }
        finally
        {
            try
            {
                input.close();
            }
            catch (IOException ignored)
            {
            }
        }
        //add comments to the data
        int maxIndex=Integer.MIN_VALUE;
        for(int i:comments.keySet())
            if(i>maxIndex)
                maxIndex=i;
        while(maxIndex>toSave.size())
            toSave.add("");
        for(int i=0;i<=maxIndex;i++)
        {
            if(comments.containsKey(i))
            {
                String comment=comments.get(i);
                if(!comment.startsWith("#"))
                    comment="#"+comment;
                toSave.add(i, comment);
            }
        }
        //make string
        StringBuilder builder = new StringBuilder();
        for(String s:toSave)
        {
            builder.append(s);
            builder.append("\n");
        }
        //save it
        try
        {
            Files.write(file.toPath(), builder.toString().getBytes(), StandardOpenOption.WRITE);
        }
        catch (IOException ignored)
        {
        }
    }

    public static void saveCommented(FileConfiguration fileConfiguration,File file)
    {
        //load all comments
        FileInputStream stream;
        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException e)
        {
            return;
        }
        Reader reader=new InputStreamReader(stream);
        BufferedReader input = new BufferedReader(reader);
        Map<Integer, String> comments= new HashMap<>();
        try
        {
            String line;
            int index=0;
            try
            {
                while ((line = input.readLine()) != null)
                {
                    if(line.contains("#"))
                        comments.put(index, line.substring(line.indexOf("#")));
                    index++;
                }
            }
            catch (IOException e)
            {
                return;
            }
        }
        finally
        {
            try
            {
                input.close();
            }
            catch (IOException ignored)
            {
            }
        }
        //load all data
        List<String> toSave= new ArrayList<>();
        String dataStream=fileConfiguration.saveToString();
        Collections.addAll(toSave, dataStream.split("\n"));
        //add comments to the data
        int maxIndex=Integer.MIN_VALUE;
        for(int i:comments.keySet())
            if(i>maxIndex)
                maxIndex=i;
        while(maxIndex>toSave.size())
            toSave.add("");
        for(int i=0;i<=maxIndex;i++)
        {
            if(comments.containsKey(i))
                toSave.add(i, comments.get(i));
        }
        //make string
        StringBuilder builder = new StringBuilder();
        for(String s:toSave)
        {
            builder.append(s);
            builder.append("\n");
        }
        //save it
        try
        {
            Files.write(file.toPath(), builder.toString().getBytes(), StandardOpenOption.WRITE);
        }
        catch (IOException ignored)
        {
        }
    }
}
