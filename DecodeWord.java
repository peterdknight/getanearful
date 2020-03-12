package com.example.earfull1;

public class DecodeWord {


    public static String getWave(String readLine){
        String waveName;
        int spaceIndex;
        spaceIndex = readLine.indexOf(" ");
        waveName = readLine.substring(0, spaceIndex  );
        return waveName;
    }
    public static String getWord(String readLine) {
        int spaces, firstSpace, endSpaces;
        String speechWord,temp;
        firstSpace = readLine.indexOf(" ");
        speechWord = readLine.substring(firstSpace+1);

        return speechWord;
    }
}

