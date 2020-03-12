package com.example.earfull1;

import java.util.Random;

public class RandomArray {

        // with only a range of 12 will have to remove duplicates
        int tableSize;

        boolean chkDup(int newValue, int Array[]) {
            int i;
            tableSize = Array.length;
            //   System.out.println("tablesize="+tableSize);
            for (i = 0; i < tableSize; i++) {
                if (Array[i] == newValue ) {

                    return false;
                }
            }
            return true;
        }

        //routine to setup new multiplier
        public int[] setMultiplier(int tableSize) {
            int i;
            int min = 0;
            int max = tableSize -1;
            int j = 0;
            int fillCount = 0;

            Random rndNumber = new Random();
            //  System.out.println("tablesize insetmult="+tableSize);
            int myArray[] = new int[tableSize];
            boolean fillArray;
            // newRndTable newRnd = new newRndTable();


            for (i = 0; fillCount < tableSize; i++) {
                j = rndNumber.nextInt((max - min) + 1) +1;

                fillArray = this.chkDup(j, myArray);
                if (fillArray == true) {
                    myArray[fillCount] = j;
                    fillCount = fillCount + 1;
                }

            }

            return myArray;
        }

    }
