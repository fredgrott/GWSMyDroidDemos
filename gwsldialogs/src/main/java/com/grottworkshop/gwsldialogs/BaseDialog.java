package com.grottworkshop.gwsldialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;

/**
 * BaseDialog class
 * Created by fgrott on 9/2/2015.
 */
public abstract class BaseDialog extends AlertDialog {

    enum LightColours {
        TITLE("#474747"), CONTENT("#999999"), ITEM("#999999"), BUTTON("#212121");

        final String mColour;

        LightColours(String _colour) {
            this.mColour = _colour;
        }
    }

    enum DarkColours {
        TITLE("#CCCCCC"), CONTENT("#999999"), ITEM("#999999"), BUTTON("#CCCCCC");

        final String mColour;

        DarkColours(String _colour) {
            this.mColour = _colour;
        }
    }

    public enum Theme {
        LIGHT, DARK
    }

    public enum Alignment {
        LEFT, CENTER, RIGHT
    }

    BaseDialog(Context _context) {
        super(_context);
    }

    static int getGravityFromAlignment(Alignment _alignment) {
        // Return corresponding gravity from our Alignment value.
        switch (_alignment) {
            case LEFT:
                return Gravity.LEFT;
            case CENTER:
                return Gravity.CENTER;
            case RIGHT:
                return Gravity.RIGHT;
            default:
                return Gravity.LEFT;
        }
    }
}
