package com.earnwise.api.utils;

import java.util.ArrayList;
import java.util.List;

public class ObjectChecker {
    public static boolean isListOfStrings(Object object) {
        // Check if object is not null
        if(object == null) {
            return false;
        }

        // Checks if object is a list
        if(object instanceof List) {
            List<?> list = (List<?>) object;
            // Iterate over all the list elements
            for(Object element : list) {
                // If any element is not a string, return false
                if(!(element instanceof String)) {
                    return false;
                }
            }
            // All elements are strings, return true
            return true;
        }
        // Object is not a list, return false
        return false;
    }

    public static List<String> convert(Object object) {
        List<String> stringList = new ArrayList<>();
        // Check if the object is not null
        if (object != null) {
            stringList.add(object.toString());
        }
        return stringList;
    }
}
