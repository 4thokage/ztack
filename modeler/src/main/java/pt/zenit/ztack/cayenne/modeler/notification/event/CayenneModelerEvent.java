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

package pt.zenit.ztack.cayenne.modeler.notification.event;

import pt.zenit.ztack.cayenne.modeler.notification.listener.CayenneModelerListener;
import pt.zenit.ztack.cayenne.modeler.project.CayenneProject;

public abstract class CayenneModelerEvent
{
    private final CayenneProject cayenneProject;
    private final Object eventSource;

    private final Class<? extends CayenneModelerListener> listenerClass;

    CayenneModelerEvent(CayenneProject cayenneProject, Object eventSource, Class<? extends CayenneModelerListener> listenerClass)
    {
        this.cayenneProject = cayenneProject;
        this.eventSource    = eventSource;
        this.listenerClass  = listenerClass;
    }

    public CayenneProject getCayenneProject()
    {
        return cayenneProject;
    }

    public Object getEventSource()
    {
        return eventSource;
    }

    public Class<? extends CayenneModelerListener> getListenerClass()
    {
        return listenerClass;
    }

    public abstract <T extends CayenneModelerListener> void fire(T target);
}
