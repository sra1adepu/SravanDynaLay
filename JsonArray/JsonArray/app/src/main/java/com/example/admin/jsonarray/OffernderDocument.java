package com.example.admin.jsonarray;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by admin on 12-05-2017.
 */

public class OffernderDocument extends Activity {
   TableRow voterid,pancard,gasno,adharcard,drivinglicense,passport,electricitybill,rationcard;
    TextView voteridtext;
    String filepath=null;
    EditText  proofvalueedit,voteridedit;
    Spinner prooftypespinner;
    private File file_attach;
    String file_str=null;
    private static final int SELECT_PHOTO = 10;
    String fileUri_path="";
    public static final int MEDIA_TYPE_IMAGE = 1;
    Button choosefilebutton, documentsaddnew,documentssavebutton;
    Dialog dialog;
    private Uri fileUri=null;
    private static final String IMAGE_DIRECTORY_NAME = "Hydcops/capture";
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    DatabaseHandler db;
    ListView listprinting;
    static final int CAM_REQUEST = 1;
    private static int  CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE=3;
    String[] options = {"Camera", "Galery"};
    String[] prooftype={"VOTERID","PANCARD","GASNUMBER","ADHAR CARD","DRIVING LICENCE","PASSPORT","ELETRICITY BILL","RATION CARD"};
    private static int SELECT_FILE = 2;
    AlertDialog levelDialog;
    TextView pathview;
    int transid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offenderdocuments);
        db = new DatabaseHandler(this);
        voteridtext=(TextView)findViewById(R.id.voteridtext);
        voteridedit=(EditText)findViewById(R.id.voteridedit);
        pathview=(TextView)findViewById(R.id.text);
        listprinting=(ListView)findViewById(R.id.listprinting);
        documentssavebutton=(Button)findViewById(R.id.documentssavebutton) ;

        prooftypespinner = (Spinner) findViewById(R.id.prooftypespinner);
        proofvalueedit = (EditText) findViewById(R.id.proofvalueedit);

        choosefilebutton = (Button) findViewById(R.id.choosefilebutton);
        documentsaddnew = (Button) findViewById(R.id.documentsaddnew);
//        dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.custom_dialog);
//        dialog.show();
        SharedPreferences sharedPreferences= getSharedPreferences("offender",0);
        transid=sharedPreferences.getInt("name",0);
        Log.d("transid3", String.valueOf(transid));

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,prooftype);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        prooftypespinner.setAdapter(spinnerArrayAdapter);
        prooftypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                voteridtext.setText(parent.getItemAtPosition(position)+ " No.");
//
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        choosefilebutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AlertDialog.Builder builder = new AlertDialog.Builder(OffernderDocument.this);
                builder.setTitle("Please Select");
                builder.setSingleChoiceItems(options, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {


                        switch (item) {
                            case 0:
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                                // start the image capture Intent
                                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);


                                fileUri_path= fileUri.getPath();

                                Log.d("url for image",fileUri_path);
                                break;
                            case 1:

                                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                photoPickerIntent.setType("image/*");
                                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                                break;

                        }
                        levelDialog.dismiss();
                    }
                });
                levelDialog = builder.create();
                levelDialog.show();

            }


        });
        documentssavebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OffernderDocument.this, Photo.class);
                startActivity(i);

            }
        });
        documentsaddnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pathis",pathview.getText().toString());
                if(pathview.getText().toString().equals("No file Choose"))
                {
                    db.offenderdocumentinsert(transid, String.valueOf(prooftypespinner.getSelectedItem()), voteridedit.getText().toString(), "", "documents");
                    Cursor c1 = db.getAllDocuments();
                    while (c1.moveToNext()) {
                        Log.d("documents", c1.getString(0));
                    }


                    OffenderDocumentAdaptor todoAdapter = new OffenderDocumentAdaptor(OffernderDocument.this, c1);

                    listprinting.setAdapter(todoAdapter);
                    clearText();
                }
                else {
                    new Upload().execute();

                }



            }


        });
    }

    private void clearText() {

        voteridedit.setText("");
        pathview.setText("No file Choose");

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
              //  previewCapturedImage();


                file_str=fileUri.getPath();
                fileUri_path= fileUri.getPath();

                Log.d("url for image",fileUri_path);
                pathview.setText(fileUri_path);


            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }



        }	else if(requestCode==SELECT_PHOTO) {
            if (resultCode == RESULT_OK && data != null) {


                Uri uriFromPath = data.getData();
                Log.d("select iamge", uriFromPath.toString());
                //   fileUri=uriFromPath;
                // Log.d("preview image",realPath);
//		     imgPreview.setImageURI(uriFromPath);


                Log.d("UTLPAT attach image", uriFromPath.toString());
                file_attach = new File(getRealPathFromURI(uriFromPath));


                Log.d("file", file_attach.toString());
                // filepath= file.getAbsolutePath();
                pathview.setText(file_attach.toString());


            }
        }



    }


    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File mediaStorageDir = new File(
                filepath,
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date(0));
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");

        }  else {
            return null;
        }


        return mediaFile;
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }



    private class Upload extends AsyncTask<Void, Void, String>
    {
        HttpPost httppost;
        ProgressDialog pDialog;
        String responseString = null;
        private long totalSize;
        private int serverResponseCode;
        private String res;


        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            pDialog = new ProgressDialog(OffernderDocument.this);
            pDialog.setMessage("Sending ...");

            pDialog.setCancelable(true);
            pDialog.setMax(20);
            pDialog.show();
            super.onPreExecute();
        }
        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub

            HttpClient httpclient = new DefaultHttpClient();

            try
            {

                MultipartEntity entity = new MultipartEntity();


                if(fileUri_path.length()>0)
                {
                    filepath = fileUri_path;
                    Log.d("filepath ravi",filepath);
                    if(filepath.contains(".png")||(filepath.contains(".jpg")||(filepath.contains("images"))))
                    {
                        File file = new File(filepath);
                        Log.d(filepath,filepath);;
                        FileInputStream fis = new FileInputStream(file);
                        httppost = new HttpPost("http://220.225.38.123:8083/FileService.svc/UploadFiles/"+file.getName());
                        Bitmap b = BitmapFactory.decodeStream(fis);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        b.compress(Bitmap.CompressFormat.JPEG, 75, bos);
                        byte[] data = bos.toByteArray();
                        ByteArrayBody bab = new ByteArrayBody(data, file.getName());
                        httppost.setEntity(new ByteArrayEntity(data));

                    }


                }
                else
                {

                    Log.d("FILE_ATTACH", file_attach.toString());
                    if(file_attach!=null)
                    {
                        Log.d("file","file_attach");
                        FileInputStream fis = new FileInputStream(file_attach);
                        httppost = new HttpPost("http://220.225.38.123:8083/FileService.svc/UploadFiles/"+file_attach.getName());
                        Bitmap b = BitmapFactory.decodeStream(fis);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        b.compress(Bitmap.CompressFormat.JPEG, 75, bos);
                        byte[] data = bos.toByteArray();
                        ByteArrayBody bab = new ByteArrayBody(data, file_attach.getName());
                        httppost.setEntity(new ByteArrayEntity(data));
                    }
                }

                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                Log.d("Response", String.valueOf(statusCode));
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                    Log.d("Respose", responseString);
                    ///
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            }
            catch(UnknownHostException e)
            {

            }
            catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;
        }
        @Override
        protected void onPostExecute(String responseString) {
            // TODO Auto-generated method stub
            pDialog.dismiss();
            //Log.d("Response", responseString);



            //Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_LONG).show();
            Log.d("Resonse",responseString);

            try {

                JSONObject js = new JSONObject(responseString);
               String j_String=js.getString("FileName");
                db.offenderdocumentinsert(transid, String.valueOf(prooftypespinner.getSelectedItem()), voteridedit.getText().toString(), j_String, "documents");
                Cursor c1 = db.getAllDocuments();
                while (c1.moveToNext()) {
                    Log.d("documents", c1.getString(0));
                }


                OffenderDocumentAdaptor todoAdapter = new OffenderDocumentAdaptor(OffernderDocument.this, c1);

                listprinting.setAdapter(todoAdapter);
                clearText();
                Log.d("j_String", j_String);
                fileUri_path="";
                //Toast.makeText(getApplicationContext(), j_String, 20).show();
                //Toast.makeText(getApplicationContext(), j_String, 10).show();

            } catch (Exception e) {
                //Toast.makeText(getApplicationContext(), "Network Glitch Please try again later", Toast.LENGTH_LONG).show();

                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }




    }
    @Override
    protected void onResume() {
        super.onResume();
        Cursor c1 = db.getAllDocuments();


        OffenderDocumentAdaptor todoAdapter = new OffenderDocumentAdaptor(OffernderDocument.this, c1);

        listprinting.setAdapter(todoAdapter);

    }
}
