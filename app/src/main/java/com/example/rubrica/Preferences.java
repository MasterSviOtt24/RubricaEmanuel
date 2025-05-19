package com.example.rubrica;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public static void salvaNumero(String num, Context context){
        SharedPreferences pref = context.getSharedPreferences("numero", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("numero", num);
        editor.apply();
    }

    public static String leggiNumero(Context context){
        SharedPreferences pref = context.getSharedPreferences("numero", Context.MODE_PRIVATE);
        return pref.getString("numero", null);
    }

}
