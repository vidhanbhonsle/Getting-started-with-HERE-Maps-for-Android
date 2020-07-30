## Step 4 : Add an icon to the Map and edit layout

1. Inside drawable folder, put the [home](/img/home.png) image

<img src="/img/drawable.png" width="286" height="700"/>

2. In MainActivity.java file, between 'handleAndroidPermissions();' and '}' add following code:

```java
    MapImage mapImage = MapImageFactory.fromResource(getApplicationContext().getResources(), R.drawable.home);
    MapMarker mapMarker = new MapMarker((new GeoCoordinates(52.530932, 13.384915)), mapImage);

    mapView.getMapScene().addMapMarker(mapMarker);
```
3. Run app to see the change

<img src="/img/marker.png" width="348" height="750"/>

4. Open app/res/layout/activity_main.xml and before "</android.support.constraint.ConstraintLayout>", add following code -

```xml
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">
        <Button
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Search"
            android:onClick="searchExampleButtonClicked" />
    </LinearLayout>
```
5. Run app to see the changes

<img src="/img/layout.png" width="348" height="750"/>

[![Foo](/img/next.png)](/Step5.md)

