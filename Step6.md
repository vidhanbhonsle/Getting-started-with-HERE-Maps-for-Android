## Step 6 : Add marker to the locations


1. In SearchExample.java, add 'addPoiMapMarker' method after Log.d("DATA",address); and before '}'
```java
    addPoiMapMarker(searchResult.getCoordinates(), metadata);
```
2. Creating addPoiMapMarker method and assigning markers
    - Inside drawable folder, put the [Pizza Icon](/img/pizzaicon.png) image
    - Add following line at the top 
        - private final List<MapMarker> mapMarkerList = new ArrayList<>();
```java
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
```
3. Run the code and press "SEARCH" button

<img src="/img/pizzas.png" width="348" height="750"/>

[![Foo](/img/next.png)](/step7.md)

