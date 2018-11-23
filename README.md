# QRUtils-Android

<img src="img/ic_logo.png" width="25%"/>

QRUtils is a set of pre-built, customizable UI components for creating and reading QR codes. It provides different libraries for creating, scanning and reading QR codes. Installation is easy and implementation is easier.

You can expect a fully functional QR scanning activity to launch in **a single line of code** whereas a customized QR code on the device's screen in **just 3 lines of code**. This README will guide you through. Good luck!

> API 14 (Android ICS 4.0)

##  :mag: Table of contents

1. [Modules](#modules)
    1. [QRView](#qrview)
    1. [QRActivity](#qractivity)
1. [Installation](#installation)
1. [Usage](#usage)
1. [Sample App](#sample-app)
1. [Contributing](#contributing)

## Modules

QRUtils provides separate modules for different functionalities. All the modules listed here are concerned with creating or reading QR codes. See individual instructions for each module to know what they do:

#### QRView

QRView is a custom Android View that facilitates generation of customized QR codes in as less as **3 lines of code**. It can also generate QR codes directly from XML attributes. Forget 300 lines of code and multiple classes to generate QR bitmaps. This library is all you need. Read more about implementation and usage of QRView [here](qrview/README.md).

#### QRActivity

QRActivity is a customizable, pre-built QR scanner activity which you can built with required parameters and launched in order to get the scanned result. Other features include flashlight toggle and file picker to read QR codes from image files. Read more about implementation and usage of QRActivity [here](aractivity/README.md).

## Installation

In your `app/build.gradle` file add a dependency on one of the QRUtils libraries.

```groovy

dependencies {

    // QRView for generating QR codes
    implementation 'chtgupta.qrutils:qrview:0.1.0'

    // QRActivity for reading QR codes
    implementation 'chtgupta.qrutils:qractivity:0.1.0'
}

```

## Usage
To get started, see the individual instructions for each module:

* [QRView - to create QR codes](qrview/README.md)
* [QRActivity - to read / scan QR codes](qractivity/README.md)

## Sample app

There is a sample app in the [`app/`](app) directory that demonstrates most
of the features of QRUtils. Load the project in Android Studio and
run it on your Android device to see a demonstration.

If you encounter a version incompatibility error between Android Studio
and Gradle while trying to run the sample app, try disabling the Instant
Run feature of Android Studio. Alternatively, update Android Studio and
Gradle to their latest versions.

A note on importing the project using Android Studio: Using 'Project from 
Version Control' will not automatically link the project with Gradle 
(issue [#1349](https://github.com/firebase/FirebaseUI-Android/issues/1349)). 
When doing so and opening any `build.gradle.kts` file, an error shows up: 
`Project 'QRUtils-Android' isn't linked with Gradle`. To resolve this 
issue, please `git checkout` the project manually and import with `Import 
from external model`.

## Contributing

1. **Fork** it
2. **Clone** the project to your own machine
3. **Commit** changes to your own branch
4. **Push** your work back up to your fork
5. Submit a **Pull request** so that I can review changes


## Release History

* 0.1.0
	* Initial release

## Support

* Liked my work? **Star** it to show support!
* Used it in your app? Let me know and I'll include your app name, logo and link in this repository.
* Want to take this to another level? Read ``CONTRIBUTING`` again!


## Meta

Chahat Gupta – [@chtgupta](https://chtgupta.github.io) – admin@speakapp.me

Distributed under the APACHE 2.0 license. See ``LICENSE`` for more information.

https://github.com/chtgupta/QRUtils-Android
