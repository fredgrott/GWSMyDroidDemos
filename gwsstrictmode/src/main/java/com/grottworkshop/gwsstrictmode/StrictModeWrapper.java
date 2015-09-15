package com.grottworkshop.gwsstrictmode;

/**
 * Created by fgrott on 9/15/2015.
 */
public class StrictModeWrapper {

    boolean devMode = false;

    /**
     * set the devMode, ie setDevMode(Build.DEBUG)
     * @param ourDevMode the devMode, default is false
     */
    void setDevMode(boolean ourDevMode){
        devMode = ourDevMode;
    }


}
