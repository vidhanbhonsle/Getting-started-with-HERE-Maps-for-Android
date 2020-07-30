## Step 4 : Add an icon to the Map

1. Inside drawable folder, put the [home](https://github.com/vidhanbhonsle/Android-workshop-with-HERE-SDK/blob/master/img/home.png) image

![alt text](/img/drawable.png)

2. In MainActivity.java file, between 'handleAndroidPermissions();' and '}' add following code:

```java
    MapImage mapImage = MapImageFactory.fromResource(getApplicationContext().getResources(), R.drawable.home);
    MapMarker mapMarker = new MapMarker((new GeoCoordinates(52.530932, 13.384915)), mapImage);

    mapView.getMapScene().addMapMarker(mapMarker);
```
3. Run app to see the change

![alt text](/img/marker.png)


[![Foo](/img/next.png)](/Step6.md)

