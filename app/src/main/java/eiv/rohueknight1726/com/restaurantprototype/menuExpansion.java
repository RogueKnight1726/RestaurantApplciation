package eiv.rohueknight1726.com.restaurantprototype;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

public class menuExpansion extends AppCompatActivity {
    EditText itemName,itemPrice,itemDescription,itemPreparationTime;
    ImageView pickerImage;
    int RESULT_LOAD_IMG = 1;
    private int STORAGE_PERMISSION_CODE = 23;
    Spinner spinner;
    String selectedCategory;
    String uniqueID;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_expansion);

        initElements();
    }

    private void initElements(){
        itemName = (EditText)findViewById(R.id.itemName);
        itemPrice = (EditText)findViewById(R.id.itemPrice);
        pickerImage = (ImageView)findViewById(R.id.pickerImage);
        itemDescription = (EditText)findViewById(R.id.itemDescription);
        itemPreparationTime = (EditText)findViewById(R.id.itemPreparationTime);
        spinner = (Spinner) findViewById(R.id.categorySpinner);
        final String[] category = getResources().getStringArray(R.array.categories);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        selectedCategory = category[0];
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = category[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        storage.getReference().child("Spagetti").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Picasso.with(menuExpansion.this).load(uri).fit().centerCrop().into(pickerImage);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//            }
//        });
    }

    public void pickImage(View v){
        requestStoragePermission();
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)){

        }
        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }


    public void addItemToDatabase(View v){

        String Name = itemName.getText().toString();
        String Price = itemPrice.getText().toString();
        if(Name.matches("")||Price.matches("")){
            return;
        }




        uniqueID = UUID.randomUUID().toString();
        StorageReference storageReference = storage.getReference().child(""+uniqueID);
        pickerImage.setDrawingCacheEnabled(true);
        pickerImage.buildDrawingCache();
        Bitmap bitmap = pickerImage.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageReference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference(""+selectedCategory);
                String itemDescriptionText = itemDescription.getText().toString();
                String itemPreparationTimeText = itemPreparationTime.getText().toString();
                String Name = itemName.getText().toString();
                String Price = itemPrice.getText().toString();
                DatabaseReference itemRef = ref.child(""+Name);
                itemRef.setValue(""+Name);
                DatabaseReference nameRef = itemRef.child("Name");
                nameRef.setValue(""+Name);
                DatabaseReference priceRef = itemRef.child("Price");
                priceRef.setValue(""+Price);
                DatabaseReference descriptionRef = itemRef.child("Description");
                descriptionRef.setValue(""+itemDescriptionText);
                DatabaseReference prepRef = itemRef.child("PreparationTime");
                prepRef.setValue(""+itemPreparationTimeText);
                DatabaseReference idRef = itemRef.child("Id");
                idRef.setValue(""+uniqueID);

                itemDescription.setText("");
                itemPreparationTime.setText("");
                itemName.setText("");
                itemPrice.setText("");
                pickerImage.setImageBitmap(null);
                spinner.setSelection(0);

                new AlertDialog.Builder(menuExpansion.this)
                        .setTitle("Item Added")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();

            }
        });

    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                pickerImage.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(menuExpansion.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(menuExpansion.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}
