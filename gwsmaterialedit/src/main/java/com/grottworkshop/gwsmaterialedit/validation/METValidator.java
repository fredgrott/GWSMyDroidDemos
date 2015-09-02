/*
 * Copyright 2014 rengwuxian
 * Modifications Copyright (C) 2015 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.grottworkshop.gwsmaterialedit.validation;

import android.support.annotation.NonNull;

/**
 * Base Validator class to either implement or inherit from for custom validation
 * Created by fgrott on 9/2/2015.
 */
public abstract class METValidator {

    /**
     * Error message that the view will display if validation fails.
     * <p/>
     * This is protected, so you can change this dynamically in your {@link #isValid(CharSequence, boolean)}
     * implementation. If necessary, you can also interact with this via its getter and setter.
     */
    protected String errorMessage;

    public METValidator(@NonNull String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setErrorMessage(@NonNull String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @NonNull
    public String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * Abstract method to implement your own validation checking.
     *
     * @param text    The CharSequence representation of the text in the EditText field. Cannot be null,
     *                but may be empty.
     * @param isEmpty Boolean indicating whether or not the text param is empty
     * @return True if valid, false if not
     */
    public abstract boolean isValid(@NonNull CharSequence text, boolean isEmpty);

}
