package chtgupta.qrutilsdemo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import chtgupta.qrutils.qractivity.QRScanner;
import chtgupta.qrutils.qrview.ErrorCorrection;
import chtgupta.qrutils.qrview.QRParams;
import chtgupta.qrutils.qrview.QRView;
import chtgupta.qrutils.qrview.QRViewListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    QRView qrView;
    EditText data, size;
    Spinner sizeSpinner, errorCorrectionSpinner;
    View foreground, background;
    int foregroundColor = -16777216, backgroundColor = 0;
    Button button;
    ProgressBar progressBar;
    LinearLayout bottomSheet;
    TextView qr_data_textView;
    BottomSheetBehavior bottomSheetBehavior;
    View overlay;

    private final int QR_SCAN_REQUEST_CODE = 123;
    public static final int VIEW_ELEVATION = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = findViewById(R.id.data);
        size = findViewById(R.id.size);
        sizeSpinner = findViewById(R.id.sizeSpinner);
        errorCorrectionSpinner = findViewById(R.id.errorCorrectionSpinner);
        foreground = findViewById(R.id.foreground);
        background = findViewById(R.id.background);
        button = findViewById(R.id.button);
        qrView = findViewById(R.id.qrView);
        progressBar = findViewById(R.id.progressBar);
        bottomSheet = findViewById(R.id.bottomSheet);
        qr_data_textView = findViewById(R.id.qr_data_textView);
        overlay = findViewById(R.id.overlay);

        foreground.setOnClickListener(this);
        background.setOnClickListener(this);
        button.setOnClickListener(this);
        findViewById(R.id.scan).setOnClickListener(this);
        findViewById(R.id.about).setOnClickListener(this);

        sizeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, QRParams.values()));
        errorCorrectionSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ErrorCorrection.values()));

        ViewCompat.setElevation(findViewById(R.id.toolbar), VIEW_ELEVATION);
        ViewCompat.setElevation(foreground, VIEW_ELEVATION);
        ViewCompat.setElevation(background, VIEW_ELEVATION);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        overlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                return true;
            }
        });

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        overlay.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        overlay.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        findViewById(R.id.clear).setOnClickListener(this);

    }

    private void updateQR(String data) {
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
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.foreground:
                ColorPickerDialogBuilder
                        .with(MainActivity.this)
                        .setTitle("Choose color")
                        .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {

                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                if (selectedColor != -1) {
                                    foreground.setBackgroundColor(selectedColor);
                                    foregroundColor = selectedColor;
                                }
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
                break;

            case R.id.background:
                ColorPickerDialogBuilder
                        .with(MainActivity.this)
                        .setTitle("Choose color")
                        .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {

                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                if (selectedColor != -1) {
                                    backgroundColor = selectedColor;
                                    background.setBackgroundColor(selectedColor);
                                }
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
                break;

            case R.id.button:
                if (data.getText().toString().isEmpty() || size.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    updateQR(data.getText().toString());
                }
                break;

            case R.id.scan:

                startActivityForResult(
                        new QRScanner(getBaseContext())
                                .setFullScreen(false)
                                .setAutoFocusInterval(2000)
                                .setFocusOnTouchEnabled(true)
                                .setImagePickerEnabled(true)
                                .build(), QR_SCAN_REQUEST_CODE
                );

                break;

            case R.id.about:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;

            case R.id.clear:
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == QR_SCAN_REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {

                String qrData = data.getStringExtra("qrData");
                qr_data_textView.setText(qrData);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            } else if (resultCode == RESULT_CANCELED) {

                String error = data.getStringExtra("error");
                Toast.makeText(getBaseContext(), error, Toast.LENGTH_LONG).show();
            }
        }
    }
}