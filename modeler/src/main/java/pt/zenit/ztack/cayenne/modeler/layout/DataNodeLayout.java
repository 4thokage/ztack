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

package pt.zenit.ztack.cayenne.modeler.layout;

import java.io.IOException;

import pt.zenit.ztack.cayenne.modeler.adapters.DataNodeAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

public class DataNodeLayout
    extends AbstractViewLayout
    implements DetailEditorSupport<DataNodeAdapter>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DataNodeLayout.class);

    @FXML
    private AnchorPane configurationTabAnchorPane, databaseAdapterTabAnchorPane, passwordEncoderTabAnchorPane;

    @FXML
    private Tab passwordEncoderTab;

//    private MainWindowLayout mainWindow;

//    @FXML
//    private TextField dataMapName;

    private DataNodeConfigurationTabLayout dataNodeConfigurationTabLayout;
    private DataNodeDatabaseAdapterTabLayout dataNodeDatabaseAdapterTabLayout;
    private DataNodePasswordEncoderTabLayout dataNodePasswordEncoderTabLayout;

    private DataNodeAdapter dataNodeAdapter;

    public DataNodeLayout(final MainWindowSupport parentComponent) throws IOException
    {
        super(parentComponent, "/layouts/DataNodeLayout.fxml");
    }

    @Override
    public void loadChildLayouts()
    {
        try
        {
            dataNodeConfigurationTabLayout   = new DataNodeConfigurationTabLayout(this);
            dataNodeDatabaseAdapterTabLayout = new DataNodeDatabaseAdapterTabLayout(this);
            dataNodePasswordEncoderTabLayout = new DataNodePasswordEncoderTabLayout(this);

            loadTab(dataNodeConfigurationTabLayout, configurationTabAnchorPane);
            loadTab(dataNodeDatabaseAdapterTabLayout, databaseAdapterTabAnchorPane);
            loadTab(dataNodePasswordEncoderTabLayout, passwordEncoderTabAnchorPane);
        }
        catch (final Exception exception)
        {
            // TODO Auto-generated catch block
            LOGGER.error("Could not load subviews", exception);
        }
    }

    @Override
    public void setPropertyAdapter(final DataNodeAdapter dataNodeAdapter)
    {
        this.dataNodeAdapter = dataNodeAdapter;

//        dataNodeConfigurationTabLayout.setPropertyAdapter(dataNodeAdapter);
//        dataNodeDatabaseAdapterTabLayout.setPropertyAdapter(dataNodeAdapter);
//        dataNodePasswordEncoderTabLayout.setPropertyAdapter(dataNodeAdapter);
    }

    @Override
    public void beginEditing()
    {
        DetailEditorSupport.super.beginEditing();

        dataNodeConfigurationTabLayout.showEditor(dataNodeAdapter);
        dataNodeDatabaseAdapterTabLayout.showEditor(dataNodeAdapter);
        dataNodePasswordEncoderTabLayout.showEditor(dataNodeAdapter);

//        dataNodeConfigurationTabLayout.beginEditing();
//        dataNodeDatabaseAdapterTabLayout.beginEditing();
//        dataNodePasswordEncoderTabLayout.beginEditing();
    }

    @Override
    public void endEditing()
    {
        DetailEditorSupport.super.endEditing();

        dataNodeConfigurationTabLayout.endEditing();
        dataNodeDatabaseAdapterTabLayout.endEditing();
        dataNodePasswordEncoderTabLayout.endEditing();
    }

//    public void tabChanged(final Event event)
//    {
//        LOGGER.debug("event: " + event);
//        getMainWindow().getCayenneProject().getDataMaps();
//    }

    public void disablePasswordEncoderTab()
    {
        passwordEncoderTab.setDisable(true);
    }

    public void enablePasswordEncoderTab()
    {
        passwordEncoderTab.setDisable(false);
    }
}
