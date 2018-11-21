package chtgupta.qrutils.qrview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.EnumMap;
import java.util.Map;

public class QRView extends AppCompatImageView {

    private int QR_SIZE = dpToPx(20);
    private String data = null;
    private ErrorCorrectionLevel baseErrorCorrectionLevel = ErrorCorrectionLevel.M;
    private int foregroundColor = android.R.color.black, backgroundColor = android.R.color.white;
    private QRViewListener listener = null;

    public QRView(Context context) {
        super(context);
    }

    public QRView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyAttrs(attrs);
    }

    public QRView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyAttrs(attrs);
    }

    private void applyAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.QRView);

        setData(typedArray.getString(R.styleable.QRView_QR_data));
        setQRForegroundColor(typedArray.getColor(R.styleable.QRView_QR_foregroundColor, Color.BLACK));
        setQRBackgroundColor(typedArray.getColor(R.styleable.QRView_QR_backgroundColor, Color.WHITE));
        setErrorCorrectionLevel(ErrorCorrection.values()[typedArray.getInt(R.styleable.QRView_QR_errorCorrectionLevel, 1)]);
        setSize(typedArray.getDimensionPixelSize(R.styleable.QRView_QR_size, 100));
        build();

        typedArray.recycle();
    }

    public QRView setData(String data) {
        this.data = data;
        return this;
    }

    public QRView setSize(int sizeInPX) {
        QR_SIZE = sizeInPX;
        return this;
    }

    public QRView setSize(int size, QRParams param) {
        if (param == QRParams.DP) {
            QR_SIZE = dpToPx(size);
        } else if (param == QRParams.PX) {
            QR_SIZE = size;
        }
        return this;
    }

    public QRView setErrorCorrectionLevel(ErrorCorrection level) {
        switch (level) {
            case L:
                baseErrorCorrectionLevel = ErrorCorrectionLevel.L;
                break;
            case M:
                baseErrorCorrectionLevel = ErrorCorrectionLevel.M;
                break;
            case Q:
                baseErrorCorrectionLevel = ErrorCorrectionLevel.Q;
                break;
            case H:
                baseErrorCorrectionLevel = ErrorCorrectionLevel.H;
                break;

        }
        return this;
    }

    public QRView setQRForegroundColor(int color) {
        this.foregroundColor = color;
        return this;
    }

    public QRView setQRBackgroundColor(int color) {
        this.backgroundColor = color;
        return this;
    }

    public QRView addListener(QRViewListener listener) {
        this.listener = listener;
        return this;
    }

    public void build() {
        new QRGenerator(data).execute();
    }

    private class QRGenerator extends AsyncTask<String, Void, Bitmap> {

        Bitmap qrBitmap = null;
        String data;

        public QRGenerator(String data) {
            super();
            this.data = data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (listener != null) {
                listener.onQRInitiated();
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if (bitmap != null) {

                if (listener != null) {
                    listener.onQRGenerated(bitmap);
                }

                QRView.this.setImageBitmap(qrBitmap);
            } else {

                if (listener != null) {
                    listener.onError();
                }

            }
        }

        @Override
        protected Bitmap doInBackground(String... strings) {

            if (listener != null) {
                listener.onQRGenerating();
            }

            try {
                qrBitmap = TextToImageEncode(data);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            return qrBitmap;
        }
    }

    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.ERROR_CORRECTION, baseErrorCorrectionLevel);
            hints.put(EncodeHintType.MARGIN, 0);
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QR_SIZE, QR_SIZE, hints
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ? foregroundColor : backgroundColor;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);


        bitmap.setPixels(pixels, 0, QR_SIZE, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

}