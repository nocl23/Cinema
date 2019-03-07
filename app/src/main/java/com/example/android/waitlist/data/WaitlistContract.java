package com.example.android.waitlist.data;

import android.provider.BaseColumns;

public class WaitlistContract {

    public static final class WaitlistEntry implements BaseColumns {
        public static final String TABLE_NAME = "cinema";
        public static final String COLUMN_TITLE = "titre";
        public static final String COLUMN_SCENARIO = "scenario";
        public static final String COLUMN_REALISATION = "realisation";
        public static final String COLUMN_MUSIQUE = "musique";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_HORAIRE = "horaire";

    }

}
