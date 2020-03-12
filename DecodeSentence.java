package com.example.earfull1;

public class DecodeSentence {
    public static String getWave(String sentence){
        String waveName;
        int spaceIndex;
        spaceIndex = sentence.indexOf(" ");
        waveName = sentence.substring(0, spaceIndex  );
        return waveName;
    }
    public static String getSentence(String sentence){
        int spaces, firstSpace,endSpaces;
        String speechSentence,waveName,finalSentence;
        firstSpace = sentence.indexOf(" ");
        speechSentence = sentence.substring(firstSpace + 1);
        //   System.out.println("speech part =" + speechSentence);

        finalSentence =  speechSentence.replaceAll("#","\n");

        return finalSentence;
    }}
