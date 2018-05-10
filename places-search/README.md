# Places Search
An Android app integrating Google Places REST API and ArcGIS Runtime API. 

## Dependencies
- [ArcGIS Android](https://developers.arcgis.com/android/)
- [Google Places Web](https://developers.google.com/places/web-service/intro)
- [Retrofit](http://square.github.io/retrofit/)

## ArcGIS Runtime SDK for Android
This app is explicitly intended for development and testing, you can become a member of the [ArcGIS Developer Program](https://developers.arcgis.com/pricing/) for free, more details about licensing your ArcGIS Runtime app can be found [here](https://developers.arcgis.com/arcgis-runtime/licensing/).

## Google Places API
This app supports autocomplete service in the [Google Place API for Web](https://developers.google.com/places/web-service/autocomplete), you must register this app project in your Google API Console and get a Google API key to add to the app. Refer to [Google](https://developers.google.com/places/android-api/signup) about signing up and getting your API keys.  Once you have your key, add it to the **gradle.properties** file created in the section above and append the key as follows:

```groovy
...
API_KEY = "YOUR-API-KEY"
```

## Licensing
A copy of the license is available in the repository's [LICENSE](LICENSE) file.
