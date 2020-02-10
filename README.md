![element](https://github.com/Element1/element-android-examples/raw/master/element-cordova-face-matching-example/images/element.png "element")
# Element Face Matching Cordova Plugin

## OS Support
:white_check_mark: Android

##### Version Support
- The Element Face Matching SDK supports Android 5.0+ / API 21+ (Lollipop and up)
- Android Studio 3.1.3 with Gradle 4.4
- Last Target SDK Version: 27
- OutSystem with MAPS v4.2

:ballot_box_with_check: IOS



## Prerequisites
- Replace with your BASE-URL (@see [ElementApiTask.java](src/android/ElementApiTask.java))
- Replace with your API Key (@see [ElementApiTask.java](src/android/ElementApiTask.java))
- Replace with your EAK file (@see [element.eak](src/android/element.eak))

## Integrate with existing cordova app
### Add this module
```
cordova plugin add <this module path>
```
### Import the AAR
1. Open your project in Android Studio. It's in the **/platforms/android** of your Cordova project.
1. On the top menu bar, click `File -> New -> New Module`. In the `Create New Module` window, click `Import .JAR/.AAR Package`, then click `Next`.
1. In the next window, click the `...` next to `File name` field and select the AAR file that has been copied in by our Plugin into the Android **src/libs** folder. Then type in `element-face-matching-sdk` in the `Subproject Name` field.
1. Click the `Finish` button and wait for Android Studio to finish building the project.


### Add element-face-matching-sdk and dependencies
The following steps require you to use Android Studio to open the Android project inside of the "platforms/android" directory of your Cordova App.
1. On the top menu bar, click `File -> Project Structure`.
1. Select your app module under `Modules` on the left pane, click on the `Dependencies` tab, and click on the `+` button at the bottom of the window. In the popup, click `Module Dependency` and select `:element-face-matching-sdk`. Click `Ok`.
1. Initiate a "Sync Project with Gradle Files"


## Usage
```
// Matching existing user
element.match("elementId", function successCallback(message){
    const { displayMessage, confidenceScore } = message;
    // TODO: What you want to do with the confidence score
    alert("Success -> " + message);
}, function errorCallback(message){
     alert("Failure -> " + message);
  }
);

// Enrolling new user
element.enroll("elementId", "name", function successCallback(message){
    // TODO: What you want to do with the message
    alert("Success -> " + message);
}, function errorCallback(message){
     alert("Failure -> " + message);
  }
));
```


### Liveness detection only (Skip network call)
If you want to skip network call and return only image that pass liveness detection, then set `CALLBACK_TO_JS` in `src/android/ElementFaceMatchingSDK.java` to true.

you can use either `element.match` or `element.enroll` with random *non empty* parameters and expect json array to be returned in `message`

### Customization
New SDK support some customization, although still need to be done in android way. You can customize your `plugin.xml` file to add `string` or resources that available in this docs
https://github.com/Element1/element-face-sdk-android/blob/master/docs/element-face-ui.md
