package com.univerisity.Common.utils;

import android.content.Context;

import com.univerisity.rentify.MainActivity;

public class Activities {
    private static MainActivity mainActivity = null;

    public static MainActivity getMainActivity(){
        if (mainActivity == null)
            mainActivity = new MainActivity();

        return mainActivity;
    }
}
