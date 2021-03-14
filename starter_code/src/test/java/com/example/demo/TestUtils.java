package com.example.demo;

import java.lang.reflect.Field;

public class TestUtils {

    public static void injectObjects(Object target,String fieldName,Object toInject){

        Boolean wasPrivate= false;

        try {
            Field field=target.getClass().getDeclaredField(fieldName);
             if(!field.isAccessible()){

                field.setAccessible(true);
                wasPrivate=true;

             }

            try {
                field.set(target,toInject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(wasPrivate){
                 field.setAccessible(false);
             }


        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


    }







}
