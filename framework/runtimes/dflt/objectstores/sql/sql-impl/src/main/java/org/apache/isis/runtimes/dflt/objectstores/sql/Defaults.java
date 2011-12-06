/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.isis.runtimes.dflt.objectstores.sql;

import java.util.Calendar;

import org.joda.time.DateTimeZone;

import org.apache.isis.core.commons.config.IsisConfiguration;
import org.apache.isis.runtimes.dflt.runtime.system.context.IsisContext;

/**
 * Provides objectstore defaults. Most significantly, maintains the object store default TimeZone, and maintains
 * Calendar.
 * 
 * 
 * @version $Rev$ $Date$
 */
public class Defaults {
    private static String propertiesBase;

    public static void initialise(String propertiesBase) {
        Defaults.propertiesBase = propertiesBase;
        setTimeZone(DateTimeZone.UTC);

        final IsisConfiguration configParameters = IsisContext.getConfiguration();

        setPkIdLabel(getStringProperty(propertiesBase, configParameters, "pk_id"));
        setIdColumn(getStringProperty(propertiesBase, configParameters, "id"));
        setMaxInstances(getIntProperty(propertiesBase, configParameters, "maxinstances", 100));
        final String useVersioningProperty = getStringProperty(propertiesBase, configParameters, "versioning", "true");
        final int isTrue = useVersioningProperty.compareToIgnoreCase("true");
        useVersioning(isTrue == 0);

        final String BASE_DATATYPE = propertiesBase + ".datatypes.";
        final IsisConfiguration dataTypes = IsisContext.getConfiguration().getProperties(BASE_DATATYPE);
        populateSqlDataTypes(dataTypes, BASE_DATATYPE);

    }

    protected static String getStringProperty(String propertiesBase, final IsisConfiguration configParameters,
        String property) {
        return configParameters.getString(propertiesBase + ".default." + property, property);
    }

    protected static String getStringProperty(String propertiesBase, final IsisConfiguration configParameters,
        String property, String defaultValue) {
        return configParameters.getString(propertiesBase + ".default." + property, defaultValue);
    }

    protected static int getIntProperty(String propertiesBase, final IsisConfiguration configParameters,
        String property, int defaultValue) {
        return configParameters.getInteger(propertiesBase + ".default." + property, defaultValue);
    }

    // {{ Calendar
    private static Calendar calendar;

    public static Calendar getCalendar() {
        return calendar;
    }

    // }}

    // {{ DateTimeZone
    private static DateTimeZone dateTimeZone;

    public static DateTimeZone getTimeZone() {
        return dateTimeZone;
    }

    public static void setTimeZone(final DateTimeZone timezone) {
        dateTimeZone = timezone;
        calendar = Calendar.getInstance(timezone.toTimeZone());
    }

    // }}

    // {{ Primary Key label, defaults to "pk_id"
    private static String pkIdLabel;

    public static void setPkIdLabel(String pkIdLabel) {
        Defaults.pkIdLabel = pkIdLabel;
    }

    public static String getPkIdLabel() {
        return pkIdLabel;
    }

    // }}

    // {{ Id Column, defaults to "id"
    private static String idColumn;

    public static void setIdColumn(String idColumn) {
        Defaults.idColumn = idColumn;
    }

    public static String getIdColumn() {
        return idColumn;
    }

    // }}

    // {{ MaxInstances
    private static int maxInstances;

    public static int getMaxInstances() {
        return maxInstances;
    }

    public static void setMaxInstances(final int maxInstances) {
        Defaults.maxInstances = maxInstances;
    }

    // }}

    // {{ Default data types
    static String TYPE_BOOLEAN;
    static String TYPE_TIMESTAMP;
    static String TYPE_DATETIME;
    static String TYPE_DATE;
    static String TYPE_TIME;
    static String TYPE_SHORT;
    static String TYPE_DOUBLE;
    static String TYPE_FLOAT;
    static String TYPE_LONG;
    static String TYPE_INT;
    static String TYPE_PK;
    static String TYPE_STRING;
    static String TYPE_LONG_STRING;
    static String TYPE_PASSWORD;
    static String TYPE_DEFAULT;

    /**
     * Default SQL data types used to define the fields in the database. By providing this method, we allow the user an
     * opportunity to override these types by specifying alternatives in sql.properties (or which ever). For example,
     * Postgresql does not know about DATETIME, but can use TIMESTAMP instead.
     * 
     * @param dataTypes
     * @param baseName
     */
    private static void populateSqlDataTypes(final IsisConfiguration dataTypes, final String baseName) {
        TYPE_TIMESTAMP = dataTypes.getString(baseName + "timestamp", "DATETIME");
        TYPE_DATETIME = dataTypes.getString(baseName + "datetime", "DATETIME");
        TYPE_DATE = dataTypes.getString(baseName + "date", "DATE");
        TYPE_TIME = dataTypes.getString(baseName + "time", "TIME");
        TYPE_DOUBLE = dataTypes.getString(baseName + "double", "DOUBLE");
        TYPE_FLOAT = dataTypes.getString(baseName + "float", "FLOAT");
        TYPE_SHORT = dataTypes.getString(baseName + "short", "INT");
        TYPE_LONG = dataTypes.getString(baseName + "long", "BIGINT");
        TYPE_INT = dataTypes.getString(baseName + "int", "INT");
        TYPE_BOOLEAN = dataTypes.getString(baseName + "boolean", "BOOLEAN"); // CHAR(1)
        TYPE_PK = dataTypes.getString(baseName + "primarykey", "INTEGER");
        TYPE_STRING = dataTypes.getString(baseName + "string", "VARCHAR(65)");
        TYPE_LONG_STRING = dataTypes.getString(baseName + "longstring", "VARCHAR(128)");
        TYPE_PASSWORD = dataTypes.getString(baseName + "password", "VARCHAR(12)");
        TYPE_DEFAULT = dataTypes.getString(baseName + "default", "VARCHAR(65)");
    }

    public static String TYPE_TIMESTAMP() {
        return TYPE_TIMESTAMP;
    }

    public static String TYPE_SHORT() {
        return TYPE_SHORT;
    }

    public static String TYPE_INT() {
        return TYPE_INT;
    }

    public static String TYPE_LONG() {
        return TYPE_LONG;
    }

    public static String TYPE_FLOAT() {
        return TYPE_FLOAT;
    }

    public static String TYPE_DOUBLE() {
        return TYPE_DOUBLE;
    }

    public static String TYPE_BOOLEAN() {
        return TYPE_BOOLEAN;
    }

    public static String TYPE_PK() {
        return TYPE_PK;
    }

    public static String TYPE_STRING() {
        return TYPE_STRING;
    }

    public static String TYPE_LONG_STRING() {
        return TYPE_LONG_STRING;
    }

    public static String TYPE_PASSWORD() {
        return TYPE_PASSWORD;
    }

    public static String TYPE_DEFAULT() {
        return TYPE_DEFAULT;
    }

    public static String TYPE_DATE() {
        return TYPE_DATE;
    }

    public static String TYPE_DATETIME() {
        return TYPE_DATETIME;
    }

    public static String TYPE_TIME() {
        return TYPE_TIME;
    }

    // }}

    // {{ Versioning
    private static boolean useVersioning;

    public static void useVersioning(boolean useVersioning) {
        Defaults.useVersioning = useVersioning;
    }

    public static boolean useVersioning() {
        return useVersioning;
    }

    public static boolean useVersioning(String shortIdentifier) {
        if (useVersioning() == false) {
            return false;
        }
        final IsisConfiguration configParameters = IsisContext.getConfiguration();
        final String useVersioningProperty =
            getStringProperty(propertiesBase, configParameters, "versioning." + shortIdentifier, "true");
        return (useVersioningProperty.compareToIgnoreCase("true") == 0);
    }
    // }}

}