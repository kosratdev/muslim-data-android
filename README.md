muslim-data-android
===================

MuslimData is an Islamic Database that provides Prayer Times (fixed and calculated), Offline Geocoding, Location Search, Azkars (Hisnul Muslim) and 99 Names of Allah.

**Fixed and Calculated Prayer Times**:
Most cities around the world find their prayer times by using some calculations which is based on location (longitude and latitude) but some other cities have fixed time table for their prayer times. This library contains most fixed and calculated prayer times. Now you can contribute it to improve it and also you can use it in Muslim communities or Muslim apps.

## Installation
```
implementation 'dev.kosrat:muslimdata:1.4.1'
```

## Usage

### Location

There are some location helper methods in the MuslimRepository that provides **offline** Location Search, Geocoding, and Reverse Geocoding and also each of one will return `Location` object or list that contains (countryCode, countryName, cityName, latitude, longitude, and hasFixedPrayerTime).


#### Search for a location

You can search for any cities or places around the world and this is useful when a user doesn't have internet connection or user's location is turned off so that you can search here:
```kotlin
lifecycleScope.launch {
    val repository = MuslimRepository(this@MainActivity)
    val locationList = repository.searchLocation("erb")
    Log.i("searchLocation", "$locationList")
}
```

#### Geocoder

Use geocoder to find a location by city name.

```kotlin
lifecycleScope.launch {
    val repository = MuslimRepository(this@MainActivity)
    val location = repository.geocoder("iq", "erbil")
    Log.i("geocoder", "$location")
}
```

#### Reverse Geocoder

Use reverseGeocoder to find a location by latitude and longitude.

```kotlin
lifecycleScope.launch {
    val repository = MuslimRepository(this@MainActivity)
    val location = repository.reverseGeocoder(36.0901, 43.0930)
    Log.i("reverseGeocoder", "$location")
}
```

### Prayer Times

You can easily get prayer times for a location just by passing (`Location`, `PrayerAttribute`, and `Date`) object to `getPrayerTimes` method.

```kotlin
lifecycleScope.launch {
    val repository = MuslimRepository(this@MainActivity)
    
    val attribute = PrayerAttribute(
        CalculationMethod.MAKKAH,
        AsrMethod.SHAFII,
        HigherLatitudeMethod.ANGLE_BASED,
        intArrayOf(0, 0, 0, 0, 0, 0)
    )
    val prayerTime = repository.getPrayerTimes(
        location,
        Date(),
        attribute
    )
    Log.i("Prayer times ", "$prayerTime")
    Log.i(
        "formatPrayerTime ",
        prayerTime.formatPrayerTime(TimeFormat.TIME_12).contentToString()
    )
    Log.i("nextPrayerTimeIndex", "${prayerTime.nextPrayerTimeIndex()}")
    Log.i("nextPrayerTimeInterval", "${prayerTime.nextPrayerTimeInterval()}")
    Log.i("nextPrayerTimeRemaining", prayerTime.nextPrayerTimeRemaining())
}
```

### Azkars (Hisnul Muslim)

Get all azkars from (**Hisnul Muslim** book) that is categorized by (`Category`, `Chapter`, and `Item`) and also the azkars are available for these languages (`en`, `ar`, `ckb`, `fa`, and `ru`)

#### Azkar Category

Get all azkar categories and it is localized for the given language.

```kotlin
lifecycleScope.launch {
    val repository = MuslimRepository(this@MainActivity)
    val azkarCategories = repository.getAzkarCategories(Language.EN)
    Log.i("azkarCategories", "$azkarCategories")
}
```

#### Azkar Chapters

Get azkar chapters and it is localized for the given language.

```kotlin
lifecycleScope.launch {
    val repository = MuslimRepository(this@MainActivity)
    val azkarChapters = repository.getAzkarChapters(Language.EN)
    Log.i("azkarChapters", "$azkarChapters")
}
```

Get azkar chapters for a specific category and it is localized for the given language.

```kotlin
lifecycleScope.launch {
    val repository = MuslimRepository(this@MainActivity)
    val azkarChapters = repository.getAzkarChapters(Language.EN, 1)
    Log.i("azkarChapters", "$azkarChapters")
}
```

#### Azkar Items

Get azkar items for a specific chapter and it is localized for the given language.
```kotlin
lifecycleScope.launch {
    val repository = MuslimRepository(this@MainActivity)
    val azkarItems = repository.getAzkarItems(1, Language.EN)
    Log.i("azkarItems", "$azkarItems")
}
```

### Names of Allah

Get 99 Names of Allah with it's translation and now it is available for these languages (`en`, `ar`, `ckb`, `fa`, and `ru`)

```kotlin
lifecycleScope.launch {
    val repository = MuslimRepository(this@MainActivity)
    val names = repository.getNamesOfAllah(Language.EN)
    Log.i("Names", "$names")
} 
```
## Author

Kosrat D. Ahmed, kosrat.d.ahmad@gmail.com

## License

**muslim-data-android** is available under the MIT license. See the [LICENSE](LICENSE) file for more info.
