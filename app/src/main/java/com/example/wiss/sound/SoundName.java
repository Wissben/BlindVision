package com.example.wiss.sound;

import android.content.res.Resources;
import com.example.wiss.myapplication.GameActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;



public class SoundName {

    private static HashMap<Integer, Integer> soundName = null;


    //a method you call before you use the class
    public static void initMap() throws IOException
    {
        //we put the ressources in here
        int nameId;
        int soundId;
        String name ;
        String sound;
        Resources res = GameActivity.getCurrentActivity().getResources();

        soundName= new HashMap<>();
        LinkedList<String> names = getSoundNames("soundNames.txt");
        int size = names.size();

        for(int i=0; i<size; i++)
        {
            //the name of the sound
            name = names.get(i);
            nameId=res.getIdentifier(name,"raw",GameActivity.getCurrentActivity().getPackageName());

            //the sound itself
            sound = name.concat("Sound");
            soundId=res.getIdentifier(sound,"raw",GameActivity.getCurrentActivity().getPackageName());


            soundName.put(nameId, soundId);
        }
    }

    public static Integer getSoundOfName(int name) throws IOException {
        if(soundName == null)
            initMap();

        return soundName.get(name);

    }


    //method to read the names of sounds available in a file so we don't need to touch the code
    private static LinkedList<String> getSoundNames(String fileName) throws IOException {


        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        LinkedList<String> names = new LinkedList<>();
        String name;
        while((name = reader.readLine())!=null)
        {
            names.add(name);
        }
        reader.close();
        return names;
    }



}
