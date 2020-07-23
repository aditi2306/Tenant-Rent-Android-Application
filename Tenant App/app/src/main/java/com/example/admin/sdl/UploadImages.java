package com.example.admin.sdl;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class UploadImages extends AppCompatActivity {

    private StorageReference mstorage;
    FirebaseAuth mAuth;
    private DatabaseReference dbref,dbref3;
    private ImageView image;
    private Uri imagUri;
    FirebaseStorage storage;
    public  static final int PICK_IMAGE_REQUEST=1234;

    public static final String Storagepath="image/";
    public static final String Databasepath="image";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_images);

        mstorage= FirebaseStorage.getInstance().getReference();

        dbref= FirebaseDatabase.getInstance().getReference(Databasepath);
       dbref3=dbref.child("Upload");
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }

        FirebaseUser user=mAuth.getCurrentUser();



       // dbref3=FirebaseDatabase.getInstance().getReference(Databasepath);

        image=(ImageView)findViewById(R.id.image_upload);


    }

    public void browse_image(View v)
    {
        Intent  i=new Intent();
        i.setType("image/*");
        i.setAction(i.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            imagUri=data.getData();
            try
            {
                Bitmap im= MediaStore.Images.Media.getBitmap(getContentResolver(),imagUri);
                image.setImageBitmap(im);
            }
            catch(FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public  String getImage(Uri uri)
    {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @SuppressWarnings("VisibleForTests")

    public void upload_image(View v) {
        if (imagUri != null) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Uploading image");
            dialog.show();


            StorageReference ref = mstorage.child(Storagepath + System.currentTimeMillis() + "." + getImage(imagUri));

            ref.putFile(imagUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    dialog.dismiss();


                    Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                    ImageUpload imgup=new ImageUpload(taskSnapshot.getDownloadUrl().toString());
                    String uploadid=dbref3.push().getKey();
                    dbref3.child(uploadid).setValue(imgup);



                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            dialog.dismiss();
                            ;

                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Uploaded " + (int) progress + "0");

                        }
                    });
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please select image", Toast.LENGTH_SHORT).show();
        }
    }
}
