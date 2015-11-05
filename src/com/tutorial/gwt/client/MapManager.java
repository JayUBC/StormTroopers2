package com.tutorial.gwt.client;

import com.google.gwt.maps.client.LoadApi;
import com.google.gwt.user.client.ui.HTMLPanel;

import java.util.ArrayList;
import java.util.List;

public class MapManager {
    private MapWidgetInstance mapWidgetInstance;

    public MapManager(){
    }

    public void startMapOnPanel(HTMLPanel targetPanel, List<SchoolClient> schoolList){
        loadMapApi(targetPanel, schoolList);
    }

    private void loadMapApi(final HTMLPanel targetPanel, final List<SchoolClient> schoolList) {
        boolean sensor = true;
        // load all the libs for use in the maps
        ArrayList<LoadApi.LoadLibrary> loadLibraries = new ArrayList<LoadApi.LoadLibrary>();
        loadLibraries.add(LoadApi.LoadLibrary.ADSENSE);
        loadLibraries.add(LoadApi.LoadLibrary.DRAWING);
        loadLibraries.add(LoadApi.LoadLibrary.GEOMETRY);
        loadLibraries.add(LoadApi.LoadLibrary.PANORAMIO);
        loadLibraries.add(LoadApi.LoadLibrary.PLACES);
        loadLibraries.add(LoadApi.LoadLibrary.WEATHER);
        loadLibraries.add(LoadApi.LoadLibrary.VISUALIZATION);

        Runnable onLoad = new Runnable() {
            @Override
            public void run() {
                drawMapWidget(targetPanel, schoolList);
            }
        };

        LoadApi.go(onLoad, loadLibraries, sensor);
    }

    private void drawMapWidget(HTMLPanel targetPanel, List<SchoolClient> schoolList) {
        mapWidgetInstance = new MapWidgetInstance();
        mapWidgetInstance.draw(schoolList);
        targetPanel.add(mapWidgetInstance);
    }

    public MapWidgetInstance getMapWidget() {
        return mapWidgetInstance;
    }



}
