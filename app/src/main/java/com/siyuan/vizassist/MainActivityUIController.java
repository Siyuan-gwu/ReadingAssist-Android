package com.siyuan.vizassist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.siyuan.vizassist.R;

/**
 * Controller of main activity.
 */
public class MainActivityUIController {
    private final Activity activity;
    private final Handler mainThreadHandler;

    private TextView resultView;
    private ImageView imageView;
    //record the last result text
    private String lastResult;

    public MainActivityUIController(Activity activity) {
        this.activity = activity;
//       get the main thread handler.
        this.mainThreadHandler = new Handler(Looper.getMainLooper());
        lastResult = activity.getString(R.string.result_placeholder);
    }

    /**
     * when users press the image, then show the full results.
     */
    public void resume() {
        resultView = activity.findViewById(R.id.resultView);
        imageView = activity.findViewById(R.id.capturedImage);
        resultView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                announceRecognitionResult(lastResult);
            }
        });
    }
//
//    public void updateResultView(final String text) {
//        mainThreadHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                resultView.setText(text);
//            }
//        });
//    }

    public void updateImageViewWithBitmap(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
        imageView.setContentDescription(activity.getString(R.string.image_sent));
    }

    /**
     *
     * if the result is longer than 1
     * line you will see only partial result on the screen, this is also not a best practice.
     * The easiest way is to show the results in a dialog
     */
    public void announceRecognitionResult(final String text) {
        lastResult = text;
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(R.string.recognition_dialog_title);
                builder.setMessage(text);
                builder.setPositiveButton(R.string.error_dialog_dismiss_button,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.show();
            }
        });
    }

    public void showErrorDialogWithMessage(int messageStringID) {
//      Allert dialog can have many buttons.
//      allert users that what happened, about what error the app meets.
//      allert is a kind of remind for users.
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.error_dialog_title);
        builder.setMessage(messageStringID);
        builder.setPositiveButton(R.string.error_dialog_dismiss_button,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
    }

    public void showInternetError() {
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
//               if the information is not important, then we use toast, and the toast will
//                disappear automatically.
                Toast.makeText(activity, R.string.internet_error_message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[] {permission}, requestCode);
        } else {
            ActivityCompat.requestPermissions(activity, new String[] {permission}, requestCode);
        }
    }
}
