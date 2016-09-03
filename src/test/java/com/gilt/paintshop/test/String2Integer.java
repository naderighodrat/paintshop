package com.gilt.paintshop.test;

import java.util.Arrays;

/**
 * Copyright (c) 2016  Ghodratollah Naderi Darehkat
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Project:paintshop
 * Author: Ghodratollah Naderi
 * E-Mail: Naderi.ghodrat@gmail.com
 * Date  : 7/26/16
 * Time  : 11:41 PM
 */
public class String2Integer {

    public static void main(String args[]) {

        System.out.println(str2int1("-123", -1));
        System.out.println(str2int2("-123", -1));
        System.out.println(str2int2("-12sdfs3", -1));
        System.out.println(str2int2("+123", -1));
        System.out.println(str2int2("123", -1));
        int array[] = {1, 94, 93, 1000, 2, 92, 1001};
        System.out.println("consecutive numbers:" + maximumNumberConsecutiveInArray(array));

        int i = (new String2Integer()).BalancePoint(new int[]{-7, 1, 5, 2, -4, 3, 0});
        System.out.println("Hello world!="+ i);

    }

    public static int BalancePoint(int[] input) {
        int leftS=input[0];
        int rightS=0;
        for (int i=0;i<input.length;i++){
            rightS+=input[i];
        }
        for(int j=0;j<input.length-1;j++){
            if(leftS==rightS){
                return j;
            }
            leftS+=input[j+1];
            rightS-=input[j];
        }
        return -1;//not found
    }


    private static Integer maximumNumberConsecutiveInArray(int[] array) {

        Arrays.sort(array);
        int max = 0;
        int count = 1;
        for (int i = 1; i < array.length; i++) {
            if (count > max) {
                max = count;
            }
            if (array[i] == (array[i - 1] + 1)) {
                count++;
            } else {
                count = 1;
            }

        }
        return max;
    }

    private static Integer str2int1(String str, int alt) {

        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            return alt;
        }
    }

    private static Integer str2int2(String str, int alt) {

        char flag = ' ';
        int i = 0;
        if (str.charAt(i) == '-') {
            flag = '-';
            i++;

        } else if (str.charAt(i) == '+') {
            flag = '+';
            i++;
        }
        Integer val = 0;
        for (int j = i; j < str.length(); j++) {
            if (str.charAt(i) >= '0' && str.charAt(j) <= '9') {
                val = val * 10 + (str.charAt(j) - 48);
            } else {
                return alt;
            }
        }
        if (flag == '-') {
            val = -val;
        }
        return val;
    }

}
