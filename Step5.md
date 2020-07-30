## Step 5 : Edit MainActivity.java, create a new class SearchEngine and a constructor 

1. Inside drawable folder, put the [home](/img/home.png) image

<img src="/img/drawable.png" width="286" height="700"/>

2. In MainActivity.java file, between 'handleAndroidPermissions();' and '}' add following code:

```java
    MapImage mapImage = MapImageFactory.fromResource(getApplicationContext().getResources(), R.drawable.home);
    MapMarker mapMarker = new MapMarker((new GeoCoordinates(52.530932, 13.384915)), mapImage);

    mapView.getMapScene().addMapMarker(mapMarker);
```
3. Run app to see the change

![alt text](/img/marker.png)


[![Foo](/img/next.png)](/Step6.md)
