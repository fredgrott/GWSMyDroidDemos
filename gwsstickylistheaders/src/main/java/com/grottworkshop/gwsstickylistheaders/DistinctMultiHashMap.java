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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * a hash map can maintain an one-to-many relationship which the value only belongs to one â€œoneâ€ part
 * and the map also support getKey by value quickly
 * Created by fgrott on 9/3/2015.
 */
class DistinctMultiHashMap<TKey,TItemValue> {
    private IDMapper<TKey, TItemValue> mIDMapper;

    interface IDMapper<TKey,TItemValue>{
        public Object keyToKeyId(TKey key);
        public TKey keyIdToKey(TKey keyId);
        public Object valueToValueId(TItemValue value);
        public TItemValue valueIdToValue(Object valueId);
    }

    LinkedHashMap<Object,List<TItemValue>> mKeyToValuesMap = new LinkedHashMap<Object, List<TItemValue>>();
    LinkedHashMap<Object,TKey> mValueToKeyIndexer = new LinkedHashMap<Object, TKey>();

    @SuppressWarnings("unchecked")
    DistinctMultiHashMap(){
        this(new IDMapper<TKey, TItemValue>() {
            @Override
            public Object keyToKeyId(TKey key) {
                return key;
            }

            @Override
            public TKey keyIdToKey(TKey keyId) {
                return keyId;
            }

            @Override
            public Object valueToValueId(TItemValue value) {
                return value;
            }

            @Override
            public TItemValue valueIdToValue(Object valueId) {
                return (TItemValue) valueId;
            }
        });
    }
    DistinctMultiHashMap(IDMapper<TKey, TItemValue> idMapper){
        mIDMapper = idMapper;
    }

    public List<TItemValue> get(TKey key){
        //todo immutable
        return mKeyToValuesMap.get(mIDMapper.keyToKeyId(key));
    }
    public TKey getKey(TItemValue value){
        return mValueToKeyIndexer.get(mIDMapper.valueToValueId(value));
    }

    public void add(TKey key,TItemValue value){
        Object keyId = mIDMapper.keyToKeyId(key);
        if(mKeyToValuesMap.get(keyId)==null){
            mKeyToValuesMap.put(keyId,new ArrayList<TItemValue>());
        }
        //remove old relationship
        TKey keyForValue = getKey(value);
        if(keyForValue !=null){
            mKeyToValuesMap.get(mIDMapper.keyToKeyId(keyForValue)).remove(value);
        }
        mValueToKeyIndexer.put(mIDMapper.valueToValueId(value), key);
        if(!containsValue(mKeyToValuesMap.get(mIDMapper.keyToKeyId(key)),value)) {
            mKeyToValuesMap.get(mIDMapper.keyToKeyId(key)).add(value);
        }
    }

    public void removeKey(TKey key){
        if(mKeyToValuesMap.get(mIDMapper.keyToKeyId(key))!=null){
            for (TItemValue value : mKeyToValuesMap.get(mIDMapper.keyToKeyId(key))){
                mValueToKeyIndexer.remove(mIDMapper.valueToValueId(value));
            }
            mKeyToValuesMap.remove(mIDMapper.keyToKeyId(key));
        }
    }
    public void removeValue(TItemValue value){
        if(getKey(value)!=null){
            List<TItemValue> itemValues = mKeyToValuesMap.get(mIDMapper.keyToKeyId(getKey(value)));
            if(itemValues!=null){
                itemValues.remove(value);
            }
        }
        mValueToKeyIndexer.remove(mIDMapper.valueToValueId(value));
    }

    public void clear(){
        mValueToKeyIndexer.clear();
        mKeyToValuesMap.clear();
    }

    public void clearValues(){
        for (Map.Entry<Object,List<TItemValue>> entry:entrySet()){
            if(entry.getValue()!=null){
                entry.getValue().clear();
            }
        }
        mValueToKeyIndexer.clear();
    }

    public Set<Map.Entry<Object,List<TItemValue>>> entrySet(){
        return mKeyToValuesMap.entrySet();
    }

    public Set<Map.Entry<Object,TKey>> reverseEntrySet(){
        return mValueToKeyIndexer.entrySet();
    }

    public int size(){
        return mKeyToValuesMap.size();
    }
    public int valuesSize(){
        return mValueToKeyIndexer.size();
    }

    protected boolean containsValue(List<TItemValue> list,TItemValue  value){
        for (TItemValue itemValue :list){
            if(mIDMapper.valueToValueId(itemValue).equals(mIDMapper.valueToValueId(value))){
                return true;
            }
        }
        return false;
    }

    /**
     * @param position
     * @return
     */
    public TItemValue getValueByPosition(int position){
        Object[] vauleIdArray = mValueToKeyIndexer.keySet().toArray();
        if(position>vauleIdArray.length){
            throw new IndexOutOfBoundsException();
        }
        Object valueId = vauleIdArray[position];
        return mIDMapper.valueIdToValue(valueId);
    }
}
