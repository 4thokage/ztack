/*****************************************************************
 *   Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 ****************************************************************/
package pt.zenit.ztack.cayenne.modeler.preferences;

import java.util.prefs.Preferences;

public interface Preference {

    /**
     * Key for preferences.
     */
    public static final String CAYENNE_PREFERENCES_PATH = "org/apache/cayenne";

    /**
     * Preferences node name for the editor
     */
    public static final String EDITOR = "editor";

    /**
     * Preferences node name for list of the last 12 opened project files.
     */
    public static final String LAST_PROJ_FILES = "lastSeveralProjectFiles";

    Preferences getRootPreference();

    Preferences getCayennePreference();

    Preferences getCurrentPreference();
}
