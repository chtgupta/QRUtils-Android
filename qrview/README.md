# QRView

[ ![Download](https://api.bintray.com/packages/chtgupta/QRUtils/chtgupta.qrutils.qrview/images/download.svg) ](https://bintray.com/chtgupta/QRUtils/chtgupta.qrutils.qrview/_latestVersion)

<img src="../img/ic_logo.png" width="25%" height="25%" />

Say hi to QRView! A custom View that draws QR codes on the screen easily and efficiently.It  is the first ever library that deals with drawing QR codes on the layout.

* **Efficient:** Creates a QR code in just 3 lines of code
* **Flexible:** Can generate QR codes directly from XML attributes
* **Customizable:** Can play around with colours, error correction levels and more

## Installation

### Gradle

In your `app/build.gradle` file add a dependency on one of the QRUtils libraries.

```groovy

  dependencies {

      implementation 'chtgupta.qrutils:qrview:0.1.0'
  }

```

### Maven

Step 1. Add the JitPack repository to your build file

Add the dependency

```sh
  <dependency>
     <groupId>chtgupta.qrutils</groupId>
     <artifactId>qrview</artifactId>
     <version>0.1.0</version>
     <type>pom</type>
  </dependency>
```

## Usage

#### Case I: Creating QR directly from XML

```xml
<chtgupta.qrutils.qrview.QRView
    android:id="@+id/qrView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="center"

    app:QR_size="96dp"                            
    app:QR_data="chtgupta.github.io"
    app:QR_errorCorrectionLevel="M"
    app:QR_foregroundColor="@android:color/black"
    app:QR_backgroundColor="@android:color/white"/>
```

### Case II: Creating QR in Java file

```java

  QRView qrView = findViewById(R.id.qrView);
  
  qrView.setData(data)
                .setSize(Integer.parseInt(size.getText().toString()), (QRParams) sizeSpinner.getSelectedItem())
                .setErrorCorrectionLevel((ErrorCorrection) errorCorrectionSpinner.getSelectedItem())
                .setQRForegroundColor(foregroundColor)
                .setQRBackgroundColor(backgroundColor)
                .addListener(new QRViewListener() {
                    @Override
                    public void onQRInitiated() {
                        qrView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onQRGenerating() {

                    }

                    @Override
                    public void onQRGenerated(Bitmap qrBitmap) {
                        progressBar.setVisibility(View.GONE);
                        qrView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {

                    }
                })
                .build();
  
```

## Release History

* 0.1.0
	* Initial release

## Meta

Chahat Gupta – [@chtgupta](https://chtgupta.github.io) – admin@speakapp.me

Distributed under the MIT license. See ``LICENSE`` for more information.

[https://github.com/chtgupta/FadeInTextView-Android/](https://github.com/chtgupta/FadeInTextView-Android/)

## Contributing

1. **Fork** it
2. **Clone** the project to your own machine
3. **Commit** changes to your own branch
4. **Push** your work back up to your fork
5. Submit a **Pull request** so that I can review changes

## Support

* Liked my work? **Star** it to show support!
* Used it in your app? Let me know and I'll include your app name, logo and link in this repository.
* Want to take this to another level? Read ``CONTRIBUTING`` again!
