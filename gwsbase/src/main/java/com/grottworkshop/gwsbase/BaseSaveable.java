package com.grottworkshop.gwsbase;

import android.os.Bundle;

/**
 * BaseSaveable interface
 * Created by fgrott on 9/21/2015.
 */
@SuppressWarnings("unused")
@Deprecated
public interface BaseSaveable {

    void saveInstanceState(Bundle bundle);

    void restoreInstanceState(Bundle bundle);
}
