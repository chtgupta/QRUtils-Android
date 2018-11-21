package chtgupta.qrutils.qrview;

import android.graphics.Bitmap;

public interface QRViewListener {

    void onQRInitiated();

    void onQRGenerating();

    void onQRGenerated(Bitmap qrBitmap);

    void onError();
}
