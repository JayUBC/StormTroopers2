package com.tutorial.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.events.click.ClickMapEvent;
import com.google.gwt.maps.client.events.click.ClickMapHandler;
import com.google.gwt.maps.client.events.tiles.TilesLoadedMapEvent;
import com.google.gwt.maps.client.events.tiles.TilesLoadedMapHandler;
import com.google.gwt.maps.client.overlays.InfoWindow;
import com.google.gwt.maps.client.overlays.InfoWindowOptions;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.overlays.MarkerOptions;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.ArrayList;
import java.util.List;

public class MapWidgetInstance extends Composite {

    private VerticalPanel pWidget;
    private MapWidget mapWidget;
    private String currURL = Window.Location.getHref();

    public MapWidgetInstance() {
        pWidget = new VerticalPanel();
        initWidget(pWidget);
    }

    public void draw(List<SchoolClient> schoolList) {
        pWidget.clear();
        pWidget.add(new HTML("<br>Demo School map with markers"));

        drawMap();
        drawMarkers(schoolList);
    }

    private void drawMap() {
        LatLng center = LatLng.newInstance(49.236675, -123.12625);
        MapOptions opts = MapOptions.newInstance();
        opts.setZoom(11);
        opts.setCenter(center);
        opts.setMapTypeId(MapTypeId.ROADMAP);

        mapWidget = new MapWidget(opts);
        pWidget.add(mapWidget);
        mapWidget.setSize("900px", "625px");

        mapWidget.addClickHandler(new ClickMapHandler() {
            public void onEvent(ClickMapEvent event) {
                GWT.log("clicked on latlng=" + event.getMouseEvent().getLatLng());
            }
        });

        mapWidget.addTilesLoadedHandler(new TilesLoadedMapHandler() {
            public void onEvent(TilesLoadedMapEvent event) {
                // Load something after the tiles load
            }
        });
    }

    private void drawMarkers(List<SchoolClient> schoolList) {
        for(SchoolClient school : schoolList){
            drawMarker(school);
        }

    }

    private void drawMarker(SchoolClient school) {
        LatLng center = LatLng.newInstance(school.getLocation().getLAT(), school.getLocation().getLONG());
        MarkerOptions options = MarkerOptions.newInstance();
        options.setPosition(center);
        options.setTitle(school.getName());

        final Marker marker = Marker.newInstance(options);
        marker.setMap(mapWidget);

        HTML html = new HTML(school.getName() + "<br> Address: " + school.getAddress() + "<br> Web link: <a  target=\"_blank\"  href='" + school.getUrl() + "'>" + school.getUrl() + "</a> <br>Go to <a  target=\"_blank\" href=" + getSchoolPageLink(school) + ">School page</a>");
        VerticalPanel vp = new VerticalPanel();
        vp.add(html);

        InfoWindowOptions infoWindowOptions = InfoWindowOptions.newInstance();
        infoWindowOptions.setContent(vp);

        final InfoWindow iw = InfoWindow.newInstance(infoWindowOptions);
        marker.addClickHandler(new ClickMapHandler() {
            public void onEvent(ClickMapEvent event) {
                iw.open(mapWidget, marker);
            }
        });
    }

    private String getSchoolPageLink(SchoolClient school) {
        String schoolCode =  String.valueOf(school.getNumber());
        return currURL.substring(0, currURL.indexOf("#") + 1) + "schools/" + schoolCode;
    }


    public static native void console(String text)
/*-{
    console.log(text);
}-*/;

}

