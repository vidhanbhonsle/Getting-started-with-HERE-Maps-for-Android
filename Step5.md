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
5. Define Button click method in MainActivity.java, create it outside loadMapScene()
```java
public void searchExampleButtonClicked(View view) {
    searchExample.onSearchButtonClicked();
    }
```
6. Create a method onSearchButtonClicked() in SearchExample.java
```java
    public void onSearchButtonClicked() {

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
                //Log.d("Data Size", String.valueOf(list.size()));

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
                }
            }
        });
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
```
6. Uncomment the Logs and run the application

[![Foo](/img/next.png)](/Step6.md)

