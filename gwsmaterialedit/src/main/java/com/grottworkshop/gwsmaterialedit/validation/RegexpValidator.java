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

import java.util.regex.Pattern;

/**
 * Custom validator for Regexes
 * Created by fgrott on 9/2/2015.
 */
public class RegexpValidator extends METValidator {

    private Pattern pattern;

    public RegexpValidator(@NonNull String errorMessage, @NonNull String regex) {
        super(errorMessage);
        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
        return pattern.matcher(text).matches();
    }
}
