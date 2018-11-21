package chtgupta.qrutils.qractivity;

import android.content.Context;
import android.content.Intent;

public class QRScanner {

    private Context context;
    private long interval = 500;
    private boolean focusOnTouch = true, imagePickerEnabled = true, fullScreen = true;

    public QRScanner(Context context) {
        this.context = context;
    }

    public QRScanner setAutoFocusInterval(long interval) {
        this.interval = interval;
        return this;
    }

    public QRScanner setFocusOnTouchEnabled(boolean focusOnTouch) {
        this.focusOnTouch = focusOnTouch;
        return this;
    }

    public QRScanner setImagePickerEnabled(boolean imagePickerEnabled) {
        this.imagePickerEnabled = imagePickerEnabled;
        return this;
    }

    public QRScanner setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
        return this;
    }

    public Intent build() {
                return new Intent(context, QRActivity.class)
                        .putExtra("fullScreen", fullScreen)
                        .putExtra("interval", interval)
                        .putExtra("focusOnTouch", focusOnTouch)
                        .putExtra("imagePickerEnabled", imagePickerEnabled);
    }

}
