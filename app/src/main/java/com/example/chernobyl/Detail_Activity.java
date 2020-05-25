package com.example.chernobyl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chernobyl.classes.SubCategory;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.w3c.dom.Text;

import java.util.Calendar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Detail_Activity extends AppCompatActivity {


    private void sendEmail(String subject, String message) {
        //Getting content for email


        //Creating SendMail object
        SendMail sm = new SendMail("jayagupta3969@gmail.com", subject, message);

        //Executing sendmail to send email
        sm.execute();
    }

    TextView buyNowButton;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        setContentView(R.layout.activity_detail);

        PaymentStatusListener paymentListener = new PaymentStatusListener() {
            @Override
            public void onTransactionCompleted(TransactionDetails transactionDetails) {
                if (buyNowButton != null) {
                    buyNowButton.setEnabled(true);
                    buyNowButton.setClickable(true);
                }
                sendEmail("Transaction Details", transactionDetails.toString());
            }

            @Override
            public void onTransactionSuccess() {
                Log.d("GOYALBANNNA", "sucess");
                if (buyNowButton != null) {
                    buyNowButton.setEnabled(true);
                    buyNowButton.setClickable(true);
                }
            }

            @Override
            public void onTransactionSubmitted() {

                Log.d("GOYALBANNNA", "submitted");
                if (buyNowButton != null) {
                    buyNowButton.setEnabled(true);
                    buyNowButton.setClickable(true);
                }
            }

            @Override
            public void onTransactionFailed() {

                Log.d("GOYALBANNNA", "failed");
                if (buyNowButton != null) {
                    buyNowButton.setEnabled(true);
                    buyNowButton.setClickable(true);
                }
            }

            @Override
            public void onTransactionCancelled() {

                Log.d("GOYALBANNNA", "cancelled");
                if (buyNowButton != null) {
                    buyNowButton.setEnabled(true);
                    buyNowButton.setClickable(true);
                }
            }

            @Override
            public void onAppNotFound() {

                Log.d("GOYALBANNNA", "app not found");
                if (buyNowButton != null) {
                    buyNowButton.setEnabled(true);
                    buyNowButton.setClickable(true);
                }
            }
        };

        ImageView imageView = findViewById(R.id.detail_image);
        buyNowButton = findViewById(R.id.buy_now);
        Activity context = this;
        String TransactionId = Long.toString(Calendar.getInstance().getTimeInMillis());

        SubCategory subCategory = (SubCategory) getIntent().getSerializableExtra("data");

        buyNowButton.setOnClickListener(v -> {
            EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
                    .with(context)
                    .setPayeeVpa("jayagupta3969@oksbi")
                    .setPayeeName("Everything About Clothes : Jaya Gupta")
                    .setTransactionId(TransactionId.substring(TransactionId.length() - 12))
                    .setTransactionRefId(TransactionId + "UPI")
                    .setDescription(subCategory.summary)
                    .setAmount(subCategory.title+".00")
                    .build();
            easyUpiPayment.setPaymentStatusListener(paymentListener);
            easyUpiPayment.startPayment();
            if (buyNowButton != null) {
                buyNowButton.setEnabled(false);
                buyNowButton.setClickable(false);
            }

        });

        Transformation transformation = new Transformation() {

            @Override
            public Bitmap transform(Bitmap source) {
                int targetWidth = imageView.getWidth();
                Log.d("GOYSLL", "" + targetWidth);
                if (targetWidth == 0) return source;
                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                Log.d("GOYSLL", "" + aspectRatio);
                int targetHeight = (int) (targetWidth * aspectRatio);
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }

            @Override
            public String key() {
                return "transformation" + " desiredWidth";
            }
        };


        TextView titleView = findViewById(R.id.detail_title);
        titleView.setText(subCategory.title);
        TextView descView = findViewById(R.id.detail_desc);
        descView.setText(subCategory.summary);
        Picasso.with(this).load("http://13.233.83.239" + subCategory.image).transform(transformation).into(imageView);

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }
}
