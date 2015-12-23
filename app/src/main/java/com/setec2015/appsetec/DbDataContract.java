package com.setec2015.appsetec;

/**
 * Created by Krets on 11/12/2015.
 */

public final class DbDataContract {

    // Default constructor to avoid accidental object creation
        DbDataContract() {}

    // Define TABLE 1
        public static abstract class DataEntry_1
        {
            public static final String TABLE_NAME = "pandlet1_table";

            public static final String ID = "_id";
            public static final String TEMP = "temp";
            public static final String LUM = "lum";
            public static final String HUM_SOLO = "hum_solo";
            public static final String HUM_AR = "hum_ar";
            public static final String PLUV = "pluv";
            public static final String DATA = "data";
        }

    // Define TABLE 2
    public static abstract class DataEntry_2
    {
        public static final String TABLE_NAME = "pandlet2_table";

        public static final String ID = "_id";
        public static final String TEMP = "temp";
        public static final String LUM = "lum";
        public static final String HUM_SOLO = "hum_solo";
        public static final String HUM_AR = "hum_ar";
        public static final String PLUV = "pluv";
        public static final String DATA = "data";
    }

    // Define TABLE 3
    public static abstract class DataEntry_3
    {
        public static final String TABLE_NAME = "pandlet3_table";

        public static final String ID = "_id";
        public static final String TEMP = "temp";
        public static final String LUM = "lum";
        public static final String HUM_SOLO = "hum_solo";
        public static final String HUM_AR = "hum_ar";
        public static final String PLUV = "pluv";
        public static final String DATA = "data";
    }

    // Define TABLE 4
    public static abstract class DataEntry_4
    {
        public static final String TABLE_NAME = "alertas1_table";

        public static final String ID = "_id";
        public static final String TYPE = "type";
        public static final String ALERT = "alert";
        public static final String VALUE = "value";
        public static final String DATA = "data";
    }

    // Define TABLE 5
    public static abstract class DataEntry_5
    {
        public static final String TABLE_NAME = "alertas2_table";

        public static final String ID = "_id";
        public static final String TYPE = "type";
        public static final String ALERT = "alert";
        public static final String VALUE = "value";
        public static final String DATA = "data";
    }

    // Define TABLE 6
    public static abstract class DataEntry_6
    {
        public static final String TABLE_NAME = "alertas3_table";

        public static final String ID = "_id";
        public static final String TYPE = "type";
        public static final String ALERT = "alert";
        public static final String VALUE = "value";
        public static final String DATA = "data";
    }

}
