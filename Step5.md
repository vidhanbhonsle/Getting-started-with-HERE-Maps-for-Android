## Step 5 : Edit MainActivity.java, create a new class SearchExample and work on Button 


1. Add new variables to MainActivity,java and create new class (SearchExample)
```java
    private SearchExample searchExample;
    String data = "pizza";
    double latitude = 12.959111;
    double longitude = 77.732022;
```
2. In MainActivity.java, in the if statement of loadMapScene() add following code:
```java
    searchExample = new SearchExample(MainActivity.this,mapView,data,latitude,longitude);
```
You will be prompted to create a constructor in SearchExample.java.

3. Create variables and initialise them inside constructor
```java
   public class SearchExample {

    private final MapView mapView;
    private String data;
    private double latitude;
    private double longitude;
    private MainActivity mainActivity;
    private final Context context;

    public SearchExample(Context context, MapView mapView, String data, double latitude, double longitude) {

        this.mainActivity = mainActivity;
        this.mapView = mapView;
        this.data = data;
        this.latitude = latitude;
        this.longitude = longitude;
        this.context = context;
    }
}
```
4. Define SearchEngine class variable and initialise it inside try-catch block
```java
public class SearchExample {

    private final MapView mapView;
    private String data;
    private double latitude;
    private double longitude;
    private MainActivity mainActivity;
    private final Context context;
    private SearchEngine searchEngine;

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
    }
}
```

[![Foo](/img/next.png)](/Step6.md)

