/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.listviewdragginganimation;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StableArrayAdapter extends ArrayAdapter<String> implements DraggableAdapter {

    final int INVALID_ID = -1;

    private final List<String> mObjects;
    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

    public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        mObjects = objects;
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        String item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void reorderElements(int position, int newPosition) {
        final List<String> objects = mObjects;

        String previous = objects.get(position);
        int iterator = newPosition < position ? 1 : -1;
        final int afterPosition = position + iterator;
        for (int cellPosition = newPosition; cellPosition != afterPosition; cellPosition += iterator) {
            String tmp = objects.get(cellPosition);
            objects.set(cellPosition, previous);
            previous = tmp;
        }
        notifyDataSetChanged();
    }

    @Override
    public void swapElements(int position, int newPosition) {
        final List<String> objects = mObjects;
        String temp = objects.get(position);
        objects.set(position, objects.get(newPosition));
        objects.set(newPosition, temp);
        notifyDataSetChanged();
    }
}
