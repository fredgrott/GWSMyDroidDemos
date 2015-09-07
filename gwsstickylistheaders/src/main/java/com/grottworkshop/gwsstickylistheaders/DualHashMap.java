/*
 * Copyright 2014 Emil SjÃ¶lander
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
package com.grottworkshop.gwsstickylistheaders;

import java.util.HashMap;

/**
 * DualHashMap class
 * Created by fgrott on 9/3/2015.
 */
class DualHashMap<TKey, TValue> {
    HashMap<TKey, TValue> mKeyToValue = new HashMap<>();
    HashMap<TValue, TKey> mValueToKey = new HashMap<>();

    public void put(TKey t1, TValue t2){
        remove(t1);
        removeByValue(t2);
        mKeyToValue.put(t1, t2);
        mValueToKey.put(t2, t1);
    }

    public TKey getKey(TValue value){
        return mValueToKey.get(value);
    }
    public TValue get(TKey key){
        return mKeyToValue.get(key);
    }

    public void remove(TKey key){
        if(get(key)!=null){
            mValueToKey.remove(get(key));
        }
        mKeyToValue.remove(key);
    }
    public void removeByValue(TValue value){
        if(getKey(value)!=null){
            mKeyToValue.remove(getKey(value));
        }
        mValueToKey.remove(value);
    }
}
