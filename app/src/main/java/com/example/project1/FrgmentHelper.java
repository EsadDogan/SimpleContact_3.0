package com.example.project1;


import android.app.Activity;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;



public class FrgmentHelper {

    public static void pushFragment(FragmentActivity activity, Fragment fragment, String tag) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.myFrameLay, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    public static void pushFragment(Fragment caller, Fragment fragment, String tag) {
        caller.getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.myFrameLay, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    // =============================================================================================

    public static void setFragment(FragmentActivity activity, Fragment fragment, String tag) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.myFrameLay, fragment, tag)
                .commit();
    }

    public static void setFragment(Fragment caller, Fragment fragment, String tag) {
        caller.getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.myFrameLay, fragment, tag)
                .commit();
    }

    // =============================================================================================

    public static void clearFragmentAndSet(FragmentActivity activity, Fragment fragment, String tag) {
        FragmentManager manager = activity.getSupportFragmentManager();
        manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        manager.beginTransaction()
                .replace(R.id.myFrameLay, fragment, tag)
                .commit();
    }

    public static void clearFragmentAndSet(Fragment caller, Fragment fragment, String tag) {
        FragmentManager manager = caller.getParentFragmentManager();
        manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        manager.beginTransaction()
                .replace(R.id.myFrameLay, fragment, tag)
                .commit();
    }

    // =============================================================================================



}

