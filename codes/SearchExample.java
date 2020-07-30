package com.here.hellomap;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.here.sdk.core.Anchor2D;
import com.here.sdk.core.CustomMetadataValue;
import com.here.sdk.core.GeoCircle;
import com.here.sdk.core.GeoCoordinates;
import com.here.sdk.core.LanguageCode;
import com.here.sdk.core.Metadata;
import com.here.sdk.core.Point2D;
import com.here.sdk.core.errors.InstantiationErrorException;
import com.here.sdk.mapview.MapImage;
import com.here.sdk.mapview.MapImageFactory;
import com.here.sdk.mapview.MapMarker;
import com.here.sdk.mapview.MapView;
import com.here.sdk.mapview.MapViewBase;
import com.here.sdk.mapview.PickMapItemsResult;
import com.here.sdk.search.Place;
import com.here.sdk.search.SearchCallback;
import com.here.sdk.search.SearchEngine;
import com.here.sdk.search.SearchError;
import com.here.sdk.search.SearchOptions;
import com.here.sdk.search.TextQuery;

import java.util.ArrayList;
import java.util.List;


public class SearchExample {

    private final MapView mapView;
    private String data;
    private double latitude;
    private double longitude;
    private MainActivity mainActivity;
    private final Context context;
    private SearchEngine searchEngine;
    private final List<MapMarker> mapMarkerList = new ArrayList<>();

    public SearchExample(Context context, MapView mapView, String data, double latitude, double longitude) {

        this.mainActivity = mainActivity;
        this.mapView = mapView;
        this.data = data;
        this.latitude = latitude;
        this.longitude = longitude;
        this.context = context;

        try {
            searchEngine = new SearchEngine();
        } catch (InstantiationErrorException e) {
            throw new RuntimeException("Initialization of SearchEngine failed: " + e.error.name());
        }

        setTapGestureHandler();
    }

    private void setTapGestureHandler() {
        mapView.getGestures().setTapListener(touchPoint -> pickMapMarker(touchPoint));
    }

    private void pickMapMarker(Point2D point2D) {
        float radiusInPixel = 2;
        mapView.pickMapItems(point2D, radiusInPixel, new MapViewBase.PickMapItemsCallback() {
            @Override
            public void onPickMapItems(@Nullable PickMapItemsResult pickMapItemsResult) {
                if (pickMapItemsResult == null) {
                    return;
                }

                List<MapMarker> mapMarkerList = pickMapItemsResult.getMarkers();
                if (mapMarkerList.size() == 0) {
                    return;
                }
                MapMarker topmostMapMarker = mapMarkerList.get(0);

                Metadata metadata = topmostMapMarker.getMetadata();
                if (metadata != null) {
                    CustomMetadataValue customMetadataValue = metadata.getCustomValue("key_search_result");
                    if (customMetadataValue != null) {
                        SearchResultMetadata searchResultMetadata = (SearchResultMetadata) customMetadataValue;
                        String title = searchResultMetadata.searchResult.getTitle();
                        String address = searchResultMetadata.searchResult.getAddress().addressText;
                        showDialog("Details",title + "\n" + address);
                        return;
                    }
                }

                showDialog("Picked Map Marker",
                        "Geographic coordinates: " +
                                topmostMapMarker.getCoordinates().latitude + ", " +
                                topmostMapMarker.getCoordinates().longitude);
            }
        });
    }

    private void showDialog(String title, String message) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void onSearchButtonClicked() {

        //searchExample(data,latitude,longitude);
        GeoCircle viewportGeoCircle = new GeoCircle(new GeoCoordinates(latitude,longitude),1000);
        TextQuery query = new TextQuery(data, viewportGeoCircle);

        int maxItems = 10;
        SearchOptions searchOptions = new SearchOptions(LanguageCode.EN_US, maxItems);

        searchEngine.search(query, searchOptions, new SearchCallback() {
            @Override
            public void onSearchCompleted(SearchError searchError, List<Place> list) {
                if (searchError != null) {
                    //Error
                    //Log.d("Data Error", "Not working");
                    return;
                }
                // If error is null, list is guaranteed to be not empty.
                Log.d("Data Size", String.valueOf(list.size()));

                // Add new marker for each search result on map.
                for (Place searchResult : list) {
                    Metadata metadata = new Metadata();
                    metadata.setCustomValue("key_search_result", new SearchResultMetadata(searchResult));

                    CustomMetadataValue customMetadataValue = metadata.getCustomValue("key_search_result");
                    SearchResultMetadata searchResultMetadata = (SearchResultMetadata) customMetadataValue;
                    String title = searchResultMetadata.searchResult.getTitle();
                    String address = searchResultMetadata.searchResult.getAddress().addressText;
                    //Log.d("DATA",title);
                    //Log.d("DATA",address);

                    addPoiMapMarker(searchResult.getCoordinates(), metadata);
                }
            }
        });
    }

    private void addPoiMapMarker(GeoCoordinates coordinates, Metadata metadata) {
        MapMarker mapMarker = createPoiMapMarker(coordinates);
        mapMarker.setMetadata(metadata);
        mapView.getMapScene().addMapMarker(mapMarker);
        mapMarkerList.add(mapMarker);
    }

    private MapMarker createPoiMapMarker(GeoCoordinates coordinates) {
        MapImage mapImage = MapImageFactory.fromResource(context.getResources(), R.drawable.pizzaicon);
        return new MapMarker(coordinates, mapImage, new Anchor2D(0.5F, 1));
    }
    private static class SearchResultMetadata implements CustomMetadataValue {

        public final Place searchResult;

        public SearchResultMetadata(Place searchResult) {
            this.searchResult = searchResult;
        }
        @NonNull
        @Override
        public String getTag() {
            return "SearchResult Metadata";
        }
    }
}
