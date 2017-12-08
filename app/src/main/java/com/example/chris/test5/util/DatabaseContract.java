package com.example.chris.test5.util;

import android.provider.BaseColumns;

/**
 * Created by Admin on 11/14/2017.
 */

public class DatabaseContract
{
    public static class Entry implements BaseColumns
    {
        public static final String TABLE_NAME = "News";
        public static final String COLUMN_TITLE = "Title";
        public static final String COLUMN_SOURCE = "Source";
        public static final String COLUMN_AUTHOR = "Author";
        public static final String COLUMN_DESC = "Description";
        public static final String COLUMN_URL = "URL";
        public static final String COLUMN_PIC = "Pic";
        public static final String COLUMN_PUB = "Published";
    }
}
