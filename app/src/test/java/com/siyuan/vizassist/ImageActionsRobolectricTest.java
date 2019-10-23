package com.siyuan.vizassist;

import android.content.Intent;
import android.os.Build;
import android.provider.MediaStore;

import com.siyuan.vizassist.MainActivity;
import com.siyuan.vizassist.imagepipeline.ImageActions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP)
public class ImageActionsRobolectricTest {
    private final static int TEST_CAMERA_REQUEST_CODE = 1;
    private final static int TEST_GALLERY_REQUEST_CODE = 1;

    @Test
    public void cameraActionSendsSystemCameraIntent() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        ImageActions.startCameraActivity(activity, TEST_CAMERA_REQUEST_CODE);

        ShadowActivity.IntentForResult intentForResult =
                shadowOf(activity).peekNextStartedActivityForResult();
        assertEquals(intentForResult.requestCode, TEST_CAMERA_REQUEST_CODE);
        assertEquals(intentForResult.intent.getAction(), MediaStore.ACTION_IMAGE_CAPTURE);
    }

    @Test
    public void galleryActionSendsSystemPickIntent() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        ImageActions.startGalleryActivity(activity, TEST_GALLERY_REQUEST_CODE);

        ShadowActivity.IntentForResult intentForResult =
                shadowOf(activity).peekNextStartedActivityForResult();
        assertEquals(intentForResult.requestCode, TEST_GALLERY_REQUEST_CODE);
        assertEquals(intentForResult.intent.getAction(), Intent.ACTION_PICK);
        assertEquals(intentForResult.intent.getData(),
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);

    }
}
