TechCentrix SDK is an Android framework for connecting mobile app and TechCentrix Bluetooth devices, like clips or wristbands. The SDK is provided as an Android Library that can be included in your application with Gradle. 

- [TechCentrix SDK](#techcentrix-sdk)
  * [Step 1 - Integrate your backend with TechCentrix backend](#step-1---integrate-your-backend-with-techcentrix-backend)
  * [Step 2 - Integrate your mobile application with TechCentrix SDK](#step-2---integrate-your-mobile-application-with-techcentrix-sdk)
  * [Step 3 - SDK Initialization](#step-3---sdk-initialization)
  * [Step 4 - UI Configuration](#step-4---ui-configuration)
  * [Step 5 - SDK Usage](#step-5---sdk-usage)
  * [Step 6 - Passing TechCentrix's push notifications](#step-6---passing-techcentrixs-push-notifications)
- [API Documentation](#api-documentation)
- [Credits](#credits)
- [Licence](#licence)

## TechCentrix SDK

[Read more about our LED technology.](http://techcentrix.github.io/)

### Step 1 - Integrate your backend with TechCentrix backend

[Read more about Step 1](http://techcentrix.github.io/quick-start-guide#backend-integration)

### Step 2 - Integrate your mobile application with TechCentrix SDK

#### Requirements

* Kotlin (or Java)
* Android 5.0 (API 21) or newer
* project based on [AndroidX](https://developer.android.com/jetpack/androidx) libraries (old Android Support libraries are not supported)

#### Installation with Gradle
Add TechCentrix repository to your project-level `build.gradle` file. To access the repository, you need to obtain a username and password from [TechCentrix](https://techcentrix.com):
```
allprojects {
    repositories {
        google()
        mavenCentral()

        maven {
            url "https://maven.techcentrix.com/artifactory/android-sdk-release"

            credentials {
                username YOUR_USERNAME
                password YOUR_PASSWORD
            }
        }
    }
}
```

Add this snippet to your `build.gradle` file to use this SDK:
```
implementation "com.techcentrix:android-sdk:1.0.0"
```

#### Project configuration
If your application has [Auto Backup](https://developer.android.com/guide/topics/data/autobackup) enabled, please [exlude](https://developer.android.com/guide/topics/data/autobackup#IncludingFiles) the following files from backing up:

```
<full-backup-content>
    <exclude domain="sharedpref" path="com.techcentrix.instance_id.xml"/>
    <exclude domain="sharedpref" path="com.techcentrix.auth_tokens.xml"/>
    <exclude domain="sharedpref" path="com.techcentrix.bt_mac_addresses.xml"/>
    <exclude domain="database" path="com.techcentrix.main.db"/>
</full-backup-content>
```

### Step 3 - SDK Initialization
To initialize SDK, include the following code in your `Application` class :

```kotlin
class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        TechCentrixSDK.init(SDKConfig.Builder(this, YOUR_MOBILE_API_KEY).build())
    }
}
```

[TechCentrixSDK.init(…)](https://techcentrix.github.io/resources/android-sdk/com.techcentrix.sdk/-tech-centrix-s-d-k/init.html) method is designed to be very lightweight and fast so you can safely call in on the main thread.

[SDKConfig](https://techcentrix.github.io/resources/android-sdk/com.techcentrix.sdk/-s-d-k-config/) is a class used to specify parameters needed in the process of SDK initialization. `SDKConfig` uses a builder pattern with fluent API for creating `SDKConfig` instances.

The constructor of [SDKConfig.Builder](https://techcentrix.github.io/resources/android-sdk/com.techcentrix.sdk/-s-d-k-config/-builder/) requires two parameters:
- Android `context` object
- `mobileApiKey` which uniquely identifies your application within our backend (you need to obtain it from TechCentrix)

Optional features can be set/enabled using additional methods of `SDKConfig.Builder` like this:
```kotlin
SDKConfig.Builder(this, YOUR_MOBILE_API_KEY)
	.setFeature1(…)
	.setFeature2(…)
	.setFeature3(…)
	.build()
```
Check out SDK documentation for all parameters/features which can be changed via `SDKConfig.Builder` class.

**Note:** `TechCentrixSDK.init(…)` method can be called only once and must be called before any other actions.

### Step 4 - UI Configuration
You can configure UI by providing the following resources in your application:

#### Color resources
Name | Description
--- | ---
`tcx_primary` | Primary color that is used across the app
`tcx_secondary` | Secondary color that is used across the app

#### Drawable resources
Item | Description | Image  | Screenshot
--- | --- | --- | ---
`tcx_img_product` | Name of an image that is used on a "List of Clips" screen | <img src="website/assets/img_product.png?raw=true" width="250"> | <img src="website/img_product.png?raw=true" width="250">
`tcx_img_product_add_new` | Name of an image that is used on a "Add New Clip" screen | <img src="website/assets/img_product_add_new.png?raw=true" width="250"> | <img src="website/img_product_add_new.png?raw=true" width="250">
`tcx_img_product_pairing` | Name of an image that is used on a Pairing screen | <img src="website/assets/img_product_pairing.png?raw=true" width="250"> | <img src="website/img_product_pairing.png?raw=true" width="250">
`tcx_img_product_pairing_on_off` | Name of an image that is used on a Pairing screen with zoomed On/Off button | <img src="website/assets/img_product_pairing_on_off.png?raw=true" width="250"> | <img src="website/img_product_pairing_on_off.png?raw=true" width="250">


Your resources will overwrite SDK resources during building final APK as described in [Resource merging](https://developer.android.com/studio/write/add-resources.html#resource_merging).

**Important: You must provide images in MDPI, HDPI, XHDPI, XXHDPI, XXXHDPI densities.** If you don't provide images for all listed densities, your application will show SDK images on devices with densities for which you didn't provide resources.

### Step 5 - SDK Usage
To use our SDK, you need a signed in user. You can create a new user or sign in an existing one using the same method [TechCentrixSDK.signIn(…)](https://techcentrix.github.io/resources/android-sdk/com.techcentrix.sdk/-tech-centrix-s-d-k/sign-in.html) (or [TechCentrixSDK.signInAsync(…)](https://techcentrix.github.io/resources/android-sdk/com.techcentrix.sdk/-tech-centrix-s-d-k/sign-in-async.html) for Java developers). Both methods require a special authentication "one time token" which your backend should get from our backend for that particular user (see [How to integrate with a TechCentrix system](https://techcentrix.github.io/quick-start-guide)).

Before starting SDK UI, you must check if a user [is signed in](https://techcentrix.github.io/resources/android-sdk/com.techcentrix.sdk/-tech-centrix-s-d-k/is-signed-in.html). If the user is signed in you can start UI via [TechCentrixActivity.start(…)](https://techcentrix.github.io/resources/android-sdk/com.techcentrix.sdk.ui/-tech-centrix-activity/start.html) method. If the user is not signed in, `TechCentrixActivity` will finish itself immediately.

```kotlin
if (TechCentrixSDK.isSignedIn()) {
    TechCentrixActivity.start(activity)
} else {
    val signInResult = TechCentrixSDK.signIn(oneTimeToken)
    if (signInResult) {
        TechCentrixActivity.start(activity)
    } else {
        // Show error message to user
    }
}
```

If your application has a "sign out" option, please call our [TechCentrixSDK.signOut()](https://techcentrix.github.io/resources/android-sdk/com.techcentrix.sdk/-tech-centrix-s-d-k/sign-out.html) method (or [TechCentrixSDK.signOutAsync()](https://techcentrix.github.io/resources/android-sdk/com.techcentrix.sdk/-tech-centrix-s-d-k/sign-out-async.html) for Java developers).

### Step 6 - Passing TechCentrix's push notifications
Your app will need to [pass push messages to our SDK](https://techcentrix.github.io/resources/android-sdk/com.techcentrix.sdk/-tech-centrix-s-d-k/handle-push-message.html).

Before passing push message you should check [if it is intended for our SDK](https://techcentrix.github.io/resources/android-sdk/com.techcentrix.sdk/-tech-centrix-s-d-k/is-tech-centrix-push-message.html), for example:

```kotlin
class DemoFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        if (TechCentrixSDK.isTechCentrixPushMessage(message)) {
            TechCentrixSDK.handlePushMessage(message)
        } else {
            // Your existing code for handling push messages
        }
    }
}
```

## API Documentation

We have more in-depth [API documentation](https://techcentrix.github.io/resources/android-sdk/) for TechCentrix SDK.

## Credits

TechCentrix SDK is owned and maintained by TechCentrix Inc.

## Licence
