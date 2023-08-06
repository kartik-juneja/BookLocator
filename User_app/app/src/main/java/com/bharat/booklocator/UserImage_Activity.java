package com.bharat.booklocator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserImage_Activity extends AppCompatActivity {
    private static final int REQ = 1000;
    ProgressDialog pd;
    private Bitmap bitmap;
    ImageView BackArrow1;
    CircleImageView userImage3;
    Button saveBtn1;
    private StorageReference storageReference;
    private DatabaseReference reference,deRef;
    private String downloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd= new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        setContentView(R.layout.activity_user_image);
        BackArrow1 = findViewById(R.id.BackArrow1);
        userImage3 = findViewById(R.id.userImage3);
        saveBtn1 = findViewById(R.id.saveBtn1);
        BackArrow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserImage_Activity.this,profileEdit_Activity.class);
                startActivity(intent);
            }
        });
        saveBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (bitmap==null) {
                        Toast.makeText(UserImage_Activity.this, "please Add Your Image", Toast.LENGTH_SHORT).show();
                    }else{
                        pd.setMessage("Uploading...");
                        pd.dismiss();
                        UpoadImage();
                    }
            }
        });

        userImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    private void UpoadImage() {
        ByteArrayOutputStream bharat = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,bharat);
        byte[]finalimg=bharat.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child(finalimg+"jpg");
        final UploadTask uploadTask= filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(UserImage_Activity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                pd.setMessage("Uploading..");
                pd.show();

                if (task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl= String.valueOf(uri);
                                    uploadData();
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(UserImage_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void uploadData() {
        reference=reference.child("UserImages");
        final String uniqueKey = reference.push().getKey();
        reference.child(uniqueKey).setValue(downloadUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(UserImage_Activity.this, "Image successfully Added", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UserImage_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQ);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ && resultCode==RESULT_OK){
            Uri uri= data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            userImage3.setImageBitmap(bitmap);
        }
    }
}