package com.digipodium.www.objectdetector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.digipodium.www.objectdetector.databinding.ActivityGalleryBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.mlkit.common.model.LocalModel;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.ObjectDetection;
import com.google.mlkit.vision.objects.ObjectDetector;
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;


import java.io.IOException;

public class GalleryActivity extends AppCompatActivity {

    private ActivityGalleryBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityGalleryBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        bind.button.setOnClickListener(view -> {
            selectImage();
        });
    }

    static final int REQUEST_IMAGE_OPEN = 1;

    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_IMAGE_OPEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_OPEN && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            Glide.with(this).load(fullPhotoUri).into(bind.img2);
            try {
                detectObject(fullPhotoUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void detectObject2(Uri uri) throws IOException {
        LocalModel localModel = new LocalModel.Builder()
                .setAssetFilePath("ssd.tflite")
                .build();
        CustomObjectDetectorOptions customObjectDetectorOptions = new CustomObjectDetectorOptions.Builder(localModel)
                .setDetectorMode(CustomObjectDetectorOptions.SINGLE_IMAGE_MODE)
                .enableMultipleObjects()
                .enableClassification()
                .setClassificationConfidenceThreshold(0.5f)
                .setMaxPerObjectLabelCount(3)
                .build();

        ObjectDetector objectDetector = ObjectDetection.getClient(customObjectDetectorOptions);
        InputImage image;
        image = InputImage.fromFilePath(this, uri);
        objectDetector.process(image)
                .addOnSuccessListener(
                        results -> {
                            for (DetectedObject detectedObject : results) {
                                Rect boundingBox = detectedObject.getBoundingBox();
                                Integer trackingId = detectedObject.getTrackingId();
                                for (DetectedObject.Label label : detectedObject.getLabels()) {
                                    String text = label.getText();
                                    int index = label.getIndex();
                                    float confidence = label.getConfidence();
                                    bind.textDetect.setText(text);
                                    bind.textConf.setText(String.valueOf(confidence));
                                }
                            }
                        })
                .addOnFailureListener(
                        e -> Snackbar.make(bind.getRoot(), "Process failed", BaseTransientBottomBar.LENGTH_LONG).show());


    }


    private void detectObject(Uri fullPhotoUri) throws IOException {
        ObjectDetectorOptions options = new ObjectDetectorOptions.Builder()
                .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
                .enableMultipleObjects()
                .enableClassification()  // Optional
                .build();
        ObjectDetector objectDetector = ObjectDetection.getClient(options);
        InputImage image;
        image = InputImage.fromFilePath(this, fullPhotoUri);
        objectDetector.process(image).addOnSuccessListener(
                results -> {
                    for (DetectedObject detectedObject : results) {
                        Rect boundingBox = detectedObject.getBoundingBox();
                        Integer trackingId = detectedObject.getTrackingId();
                        for (DetectedObject.Label label : detectedObject.getLabels()) {
                            String text = label.getText();
                            int index = label.getIndex();
                            float confidence = label.getConfidence();
                            bind.textDetect.setText(text);
                            bind.textConf.setText(String.valueOf(confidence));
                        }
                    }
                }).addOnFailureListener(e -> Snackbar.make(bind.getRoot(), "Process failed", BaseTransientBottomBar.LENGTH_LONG).show());

    }


}