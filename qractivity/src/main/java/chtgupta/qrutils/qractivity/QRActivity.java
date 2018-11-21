package chtgupta.qrutils.qractivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.FileNotFoundException;
import java.io.IOException;

public class QRActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener, View.OnClickListener {

    private QRCodeReaderView qrCodeReaderView;
    private ImageButton flash, imagePicker;

    private boolean isFlashOn = false, focusOnTouch, imagePickerEnabled, fullScreen;
    long interval;

    private final int IMAGE_PICKER_REQUEST_CODE = 101;
    private final int REQUEST_CODE_CAMERA = 010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isCameraGranted()) {
            requestCameraPermission();
            return;
        }

        interval = getIntent().getLongExtra("interval", 500);
        focusOnTouch = getIntent().getBooleanExtra("focusOnTouch", true);
        imagePickerEnabled = getIntent().getBooleanExtra("imagePickerEnabled", true);
        fullScreen = getIntent().getBooleanExtra("fullScreen", true);

        if (fullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_qr);

        qrCodeReaderView = findViewById(R.id.qrdecoderview);
        flash = findViewById(R.id.flash);
        imagePicker = findViewById(R.id.imagePicker);

        imagePicker.setVisibility(imagePickerEnabled ? View.VISIBLE : View.GONE);

        qrCodeReaderView.setAutofocusInterval(interval);
        qrCodeReaderView.setOnQRCodeReadListener(this);
        flash.setOnClickListener(this);
        imagePicker.setOnClickListener(this);

    }

    @Override
    public void onQRCodeRead(String qrData, PointF[] points) {
        finishWithResult(qrData);
    }

    private void toggleFlash() {
        if (isFlashOn) {
            flash.setBackgroundResource(R.drawable.circle_translucent_unselected);
            qrCodeReaderView.setTorchEnabled(false);
        } else {
            flash.setBackgroundResource(R.drawable.circle_translucent_selected);
            qrCodeReaderView.setTorchEnabled(true);
        }
        isFlashOn = !isFlashOn;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.flash) {
            try {
                toggleFlash();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), R.string.unsupported_error, Toast.LENGTH_LONG).show();
            }

        } else if (id == R.id.imagePicker){

            startActivityForResult(
                    new Intent()
                            .setType("image/*")
                            .setAction(Intent.ACTION_GET_CONTENT), IMAGE_PICKER_REQUEST_CODE
            );

        } else {

            if (focusOnTouch) {
                qrCodeReaderView.forceAutoFocus();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == IMAGE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {

            try {
                Uri imageUri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                String qrData = decodeQRBitmap(bitmap);
                finishWithResult(qrData);
            } catch (FileNotFoundException f) {
                f.printStackTrace();
                finishWIthError("FileNotFoundException");
            } catch (IOException i) {
                i.printStackTrace();
                finishWIthError("IOException");
            }
        }

    }

    private String decodeQRBitmap(Bitmap bitmap) {
        String decoded = null;

        int[] intArray = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(intArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(),
                bitmap.getHeight());
        LuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(),
                bitmap.getHeight(), intArray);

        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

        com.google.zxing.Reader reader = new QRCodeReader();
        try {
            Result result = reader.decode(binaryBitmap);
            decoded = result.getText();
        } catch (NotFoundException e) {
            e.printStackTrace();
            finishWIthError("NotFoundException");
        } catch (ChecksumException e) {
            e.printStackTrace();
            finishWIthError("ChecksumException");
        } catch (FormatException e) {
            e.printStackTrace();
            finishWIthError("FormatException");
        }
        return decoded;
    }

    private void finishWithResult(String qrData) {
        setResult(
                Activity.RESULT_OK, new Intent()
                        .putExtra("qrData", qrData)
        );
        finish();
    }

    private void finishWIthError(String error) {
        setResult(
                Activity.RESULT_CANCELED, new Intent()
                        .putExtra("error", error)
        );
        finish();
    }

    @Override
    public void onBackPressed() {
        finishWIthError("BackPressed");
    }

    // ______ handling camera permissions ______

    private boolean isCameraGranted() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(QRActivity.this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(QRActivity.this,
                new String[]{Manifest.permission.CAMERA},
                REQUEST_CODE_CAMERA);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    recreate();
                } else {
                    finishWIthError("CameraPermissionDenied");
                }
                break;
            }
        }
    }

}