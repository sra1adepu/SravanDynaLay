package com.example.admin.jsonarray;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpHeaders;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;

import static com.example.admin.jsonarray.OffernderDocument.MEDIA_TYPE_IMAGE;

/**
 * Created by admin on 15-05-2017.
 */

public class Photo extends Activity {
    Button photochoosefilebutton;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private Uri fileUri=null;
    String fileUri_path="";
    private static final String IMAGE_DIRECTORY_NAME = "Hydcops/capture";
    private File file_attach;
    TextView phototext;
    String filepath=null;
    String file_str=null;
    AlertDialog levelDialog;
    private static final int SELECT_PHOTO = 10;
    String[] options = {"Camera", "Galery"};
    ImageView Viewimage;
    Button documentsaddnew;
    DatabaseHandler db;
    RequestQueue requestQueue;
    String j_String="";
    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo);
        cd=new ConnectionDetector(this);
        requestQueue = Volley.newRequestQueue(this);
        photochoosefilebutton=(Button)findViewById(R.id.photochoosefilebutton);
        Viewimage=(ImageView)findViewById(R.id.uploadimage);
        phototext=(TextView)findViewById(R.id.phototext);
        documentsaddnew=(Button)findViewById(R.id.documentsaddnew);

        db = new DatabaseHandler(this);
        documentsaddnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor c = db.getAllpersonalinformation();
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        if(cd.isConnectingToInternet()) {
                            final ProgressDialog progressDialog = new ProgressDialog(Photo.this);
                            progressDialog.setMessage("Please wait data is Processing");
                            progressDialog.show();

                            try {

                                JSONObject jsoninner = new JSONObject();
                                jsoninner.put("OFFENDERNAME", c.getString(0));
                                jsoninner.put("ALIASES", c.getString(1));
                                jsoninner.put("DOB", c.getString(2));
                                jsoninner.put("AGE", c.getString(3));
                                jsoninner.put("MOBILENO", c.getString(4));
                                jsoninner.put("EMAIL", c.getString(5));
                                jsoninner.put("PSID",c.getString(6));
                                jsoninner.put("VEHCILEDETAILS", c.getString(7));
                                jsoninner.put("CURRENTACTIVITY",c.getString(8));
                                jsoninner.put("PRESENT_ADDRESS",c.getString(9));
                                jsoninner.put("PERMANENT_ADDRESS", c.getString(10));
                                jsoninner.put("OFFENDER_HOUSE_OWNER", c.getString(11));
                                jsoninner.put("DATEOFLASTARREST",c.getString(12));
                                jsoninner.put("PSARRESTED", c.getString(13));
                                jsoninner.put("DATEOFRELEASE", c.getString(14));
                                jsoninner.put("MODUSOPERENDI", c.getString(15));
                                jsoninner.put("HISTORYSHEET", c.getString(16));
                                jsoninner.put("LATESTPHOTOGRAPHCOLLECTED", c.getString(17));
                                jsoninner.put("CreatedBy","9700600500");


                                JSONObject jsonparent = new JSONObject();
                                jsonparent.put("request", jsoninner);
//                            Log.d("submit",jsonparent.toString());
                                //(Method.POST ,url, new JSONObject("request",jsoninner),
                                Log.d("ravi", jsonparent.toString());
                                String url = "http://crimeproneareaservice.logicshore.co.in//LogicShore.svc/AddOffendersDetails";
                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonparent, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d("data123", String.valueOf(response));
                                        try {
                                            String transId = response.getString("TransId");
                                            Log.d("transid", transId);
//                                        SharedPreferences shared = getSharedPreferences("offender", 0);
//                                        SharedPreferences.Editor editor = shared.edit();
//                                        editor.clear();
//                                        editor.putInt("name", Integer.parseInt(transId));
//
//                                        editor.commit();

                                            progressDialog.dismiss();

                                            finallysubmit(transId);
//                                        Intent i = new Intent(mooffernder_add_personal.this, Data.class);
//                                        startActivity(i);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            progressDialog.dismiss();
                                        }


                                    }
                                },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                            }
                                        });
                                //add request to queue
                                requestQueue.add(jsonObjectRequest);
                            } catch (Exception e) {
                                    e.printStackTrace();
                            }

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        photochoosefilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Photo.this);
                builder.setTitle("Please Select");
                builder.setSingleChoiceItems(options, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                                fileUri_path= fileUri.getPath();
                                phototext.setText(fileUri_path);
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
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                previewCapturedImage();
                file_str=fileUri.getPath();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {

                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        else if(requestCode==SELECT_PHOTO)
        {
            if(resultCode == RESULT_OK&&data!=null)
            {
                Uri uriFromPath = data.getData();
                Log.d("select iamge", uriFromPath.toString());

                Log.d("UTLPAT attach image", uriFromPath.toString());
                file_attach = new File(getRealPathFromURI(uriFromPath));
                phototext.setText(file_attach.toString());
                Log.d("file",file_attach.toString());
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriFromPath));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Viewimage.setImageBitmap(bitmap);
            }
        }


    }
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
    private void previewCapturedImage() {
        try {
            if(fileUri!=null)
            {
                Log.d("file attached" , fileUri.getPath());
                // bimatp factory
//			BitmapFactory.Options options = new BitmapFactory.Options();
//
//
//			options.inSampleSize = 8;
//
//			final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
//					options);


                final Bitmap bitmap=lessResolution(fileUri.getPath());

                Viewimage.setImageBitmap(bitmap);

                Viewimage.buildDrawingCache();

            }
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap lessResolution(String filePath) {
        int reqHeight = 512;
        int reqWidth = 512;
        BitmapFactory.Options options = new BitmapFactory.Options();

        // First decode with inJustDecodeBounds=true to check dimensions
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

// Calculate ratios of height and width to requested height and
// width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

// Choose the smallest ratio as inSampleSize value, this will
// guarantee
// a final image with both dimensions larger than or equal to the
// requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File mediaStorageDir = new File(filepath, IMAGE_DIRECTORY_NAME);

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
            pDialog = new ProgressDialog(Photo.this);
            pDialog.setMessage("Sending ...");

            pDialog.setCancelable(true);
            pDialog.setMax(20);
            pDialog.show();



//            Log("photoimage",c1.getString(0));

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










                //  httppost.setEntity(new UrlEncodedFormEntity(pair));


                //httppost = new HttpPost("http://multipart.logicshore.co.in/FileService.svc/UploadFiles/"+file.getName());
                // httppost.setEntity(new UrlEncodedFormEntity(pair));
                // httppost.setEntity(new UrlEncodedFormEntity(pair));

                //httppost.setHeader("Content-type", "multipart/form-data");

                // Making server call
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
                j_String="";
                JSONObject js = new JSONObject(responseString);
                 j_String=js.getString("FileName");
                Log.d("insertionvalues",j_String);
                db.photoinsert(j_String);
                Cursor c1 = db.getphotoinsert();
                while (c1.moveToNext()) {
                    Log.d("photovalues", c1.getString(0));
                }
//                if(cd.isConnectingToInternet())
//                {
                    //  storeinPreferences();
                    new CommunSubmit().execute();
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext(), "No Internet Connection",Toast.LENGTH_LONG).show();
//                }
//                Cursor c1 = db.getAllDocuments();
//                while (c1.moveToNext()) {
//                    Log.d("dude", c1.getString(2));
//                }

//
//                OffenderDocumentAdaptor todoAdapter = new OffenderDocumentAdaptor(OffernderDocument.this, c1);
//
//                listprinting.setAdapter(todoAdapter);
//                clearText();
                Log.d("photo url is", j_String);
                fileUri_path="";
                //Toast.makeText(getApplicationContext(), j_String, 20).show();
                //Toast.makeText(getApplicationContext(), j_String, 10).show();

            } catch (Exception e) {
                //Toast.makeText(getApplicationContext(), "Network Glitch Please try again later", Toast.LENGTH_LONG).show();

                // TODO Auto-generated catch block
                e.printStackTrace();
            }



            //
        }




    }



    public class CommunSubmit extends AsyncTask<String,String,String>{

        ProgressDialog progressDialog;
        String st;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            progressDialog=new ProgressDialog(Photo.this);
            progressDialog.setMessage("Please Wait..");
            progressDialog.show();

            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            //getSharedPreferences("sending", 0).getString("ps", "");
            //getSharedPreferences("sending", 0).getString("username", "");
            // getSharedPreferences("sending",0).getString("mobile","");

            JSONObject js=new JSONObject();
            try {

                       js.put("OffenderId",getSharedPreferences("offender",0).getInt("name",0));

                js.put("Photo", j_String);

                js.put("PhotoBase64", "");
                js.put("CreatedBy", getSharedPreferences("sendingravi", 0).getString("MobileNumber",""));
                JSONObject js_main=new JSONObject();
                js_main.put("request",js);
                Log.d("photo request",js_main.toString());
                HttpClient client=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost("http://crimeproneareaservice.logicshore.co.in//LogicShore.svc/AddMOPhotoDetails");
                try {
                    httpPost.setEntity(new StringEntity(js_main.toString(), "UTF8"));
                    httpPost.setHeader(HttpHeaders.ACCEPT, "application/json");
                    httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
                    HttpResponse response = client.execute(httpPost);
                    Log.d("http response", response.toString());
                    this.st = EntityUtils.toString(response.getEntity());
                    Log.d("HTTP status", this.st);


                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch(UnknownHostException e)
                {

                }
                catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            // TODO Auto-generated method stub
            return st;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            progressDialog.dismiss();
            try {
                JSONObject jsr=new JSONObject(result);
                if(jsr.getBoolean("status")){
//                    changetv.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),jsr.getString("message"),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),jsr.getString("message"),Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            super.onPostExecute(result);
        }

    }

    private  void finallysubmit(String transid)
    {
        //                //family
                Cursor c = db.getAllContacts();
                JSONArray jsonfamilyarray = new JSONArray();
                JSONObject jsoninnerobj = new JSONObject();
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        if(c.getInt(0)==Integer.parseInt(transid)) {
                            try {
                                JSONObject familyobj = new JSONObject();
                                familyobj.put("OFFENDER_ID",transid);
                                familyobj.put("FM_RELATION_TYPE", c.getString(1));
                                familyobj.put("FM_NAME", c.getString(2));
                                familyobj.put("FM_MOBILE_NO", c.getString(3));
                                familyobj.put("FM_ADDRESS", c.getString(4));
                                familyobj.put("FM_OCCUPATION", c.getString(5));
                                familyobj.put("FM_ANYPROOFS", c.getString(6));
                                familyobj.put("CreatedBy", getSharedPreferences("sendingravi", 0).getString("MobileNumber",""));
                                jsonfamilyarray.put(familyobj);
                                jsoninnerobj.put("EFamilyDetails", jsonfamilyarray);
                                Log.d("data", jsoninnerobj.toString());
                            } catch (Exception e) {

                            }
                        }
                    }
                    try {

                        JSONObject jsonfamilyparent = new JSONObject();
                        jsonfamilyparent.put("erequest", jsoninnerobj);

                        Log.d("request for family", jsonfamilyparent.toString());


                        String urlfamily = "http://crimeproneareaservice.logicshore.co.in//LogicShore.svc/ADDFamilyDetails";

//                    if(cd.isConnectingToInternet()){

                        try {

                            Log.d("jsonparent", jsonfamilyparent.toString());
                            final ProgressDialog pDialog = new ProgressDialog(Photo.this);
                            pDialog.setMessage("Loading...");
                            pDialog.show();

                            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                                    urlfamily, jsonfamilyparent,
                                    new Response.Listener<JSONObject>() {

                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Log.d("family data", response.toString());
                                            pDialog.hide();
                                            try {
                                                if (response.getBoolean("status")) {
                                                    db.deleteallfamily();
                                                }
                                            } catch (Exception e) {

                                            }
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    VolleyLog.d("error message", "Error: " + error.getMessage());
                                    // hide the progress dialog
                                    pDialog.hide();
                                }
                            });

// Adding request to request queue
                            requestQueue.add(jsonObjReq);
                        } catch (Exception e) {

                        }
//                    }
//                    else
//                    {
//                        Toast.makeText(getApplicationContext(),"No internet Connection",Toast.LENGTH_SHORT).show();
//                    }
                    } catch (Exception e) {

                    }

                }

                //associates details

                Cursor c1 = db.getAllAsscoiaates();
                JSONArray jsonassociatesarray = new JSONArray();
                JSONObject jsonassociatesinnerobj = new JSONObject();
                if (c1.getCount() > 0) {
                    while (c1.moveToNext()) {
                        if(c1.getInt(0)==Integer.parseInt(transid)) {
                            try {
                                JSONObject familyobj = new JSONObject();
                                familyobj.put("OFFENDER_ID", transid);
                                familyobj.put("ASSO_NAME", c1.getString(1));
                                familyobj.put("ASSO_TYPE", c1.getString(2));
                                familyobj.put("ASSO_DESCRIPTION", c1.getString(3));
                                familyobj.put("ASSO_MOBILENO", c1.getString(4));
                                familyobj.put("ASSO_OCCUPATION", c1.getString(5));
                                familyobj.put("ASSO_ADDRESS", c1.getString(6));
                                familyobj.put("CreatedBy", getSharedPreferences("sendingravi", 0).getString("MobileNumber",""));
                                jsonassociatesarray.put(familyobj);
                                jsonassociatesinnerobj.put("EAssoDetails", jsonassociatesarray);
                                Log.d("data", jsoninnerobj.toString());
                            } catch (Exception e) {

                            }
                        }

                    }
                    try {

                        JSONObject jsonfamilyparent = new JSONObject();
                        jsonfamilyparent.put("erequest", jsonassociatesinnerobj);

                        Log.d("request for associates", jsonfamilyparent.toString());


                        String urlfamily = "http://crimeproneareaservice.logicshore.co.in//LogicShore.svc/ADDAssociatesDetails";

//                    if(cd.isConnectingToInternet()){

                        try {

                            Log.d("jsonparent", jsonassociatesinnerobj.toString());
                            final ProgressDialog pDialog = new ProgressDialog(Photo.this);
                            pDialog.setMessage("Loading...");
                            pDialog.show();

                            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                                    urlfamily, jsonfamilyparent,
                                    new Response.Listener<JSONObject>() {

                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Log.d("family data", response.toString());
                                            pDialog.hide();
                                            try {
                                                if (response.getBoolean("status")) {
                                                    //db.deleteallassocitates();
                                                }
                                            } catch (Exception e) {

                                            }
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    VolleyLog.d("error message", "Error: " + error.getMessage());
                                    // hide the progress dialog
                                    pDialog.hide();
                                }
                            });

// Adding request to request queue
                            requestQueue.add(jsonObjReq);
                        } catch (Exception e) {

                        }
//                    }
//                    else
//                    {
//                        Toast.makeText(getApplicationContext(),"No internet Connection",Toast.LENGTH_SHORT).show();
//                    }
                    } catch (Exception e) {

                    }
                }
                //cases


                Cursor c2 = db.getAllInvolved();
                JSONArray jsoncasesarray = new JSONArray();
                JSONObject jsoncasesinnerobj = new JSONObject();
if(c2.getCount()>0)
{
                while (c2.moveToNext()) {
                    if(c2.getInt(0)==Integer.parseInt(transid)) {
                        try {
                            JSONObject familyobj = new JSONObject();
                            familyobj.put("OFFENDER_ID", transid);
                            familyobj.put("CRIMENO", c2.getString(1));
                            familyobj.put("POLICESTATION", c2.getString(2));
                            familyobj.put("DATEOFARREST", c2.getString(3));
                            familyobj.put("STATUSOFCRIME", c2.getString(4));
                            familyobj.put("CreatedBy", getSharedPreferences("sendingravi", 0).getString("MobileNumber",""));

                            jsoncasesarray.put(familyobj);
                            jsoncasesinnerobj.put("ECrimeDetails", jsoncasesarray);
                            Log.d("data", jsoninnerobj.toString());
                        } catch (Exception e) {

                        }
                    }
                }
                try {

                    JSONObject jsonfamilyparent = new JSONObject();
                    jsonfamilyparent.put("erequest", jsoncasesinnerobj);

                    Log.d("request for cases", jsonfamilyparent.toString());


                    String urlfamily = "http://crimeproneareaservice.logicshore.co.in//LogicShore.svc/ADDCrimeDetails";

//                    if(cd.isConnectingToInternet()){

                    try {

                        Log.d("jsonparent", jsonassociatesinnerobj.toString());
                        final ProgressDialog pDialog = new ProgressDialog(Photo.this);
                        pDialog.setMessage("Loading...");
                        pDialog.show();

                        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                                urlfamily, jsonfamilyparent,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d("family data", response.toString());
                                        pDialog.hide();
                                        try {
                                            if (response.getBoolean("status")) {
                                                db.deleteallcases();
                                            }
                                        } catch (Exception e) {

                                        }
                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.d("error message", "Error: " + error.getMessage());
                                // hide the progress dialog
                                pDialog.hide();
                            }
                        });

// Adding request to request queue
                        requestQueue.add(jsonObjReq);
                    } catch (Exception e) {

                    }
//                    }
//                    else
//                    {
//                        Toast.makeText(getApplicationContext(),"No internet Connection",Toast.LENGTH_SHORT).show();
//                    }
                } catch (Exception e) {

                }
            }
                //document

                Cursor c3= db.getAllDocuments();
                JSONArray jsondocumentarray=new JSONArray();
                JSONObject jsondocumentinnerobj=new JSONObject();
if(c3.getCount()>0) {
    while (c3.moveToNext()) {
        if(c3.getInt(0)==Integer.parseInt(transid)) {
            try {
                JSONObject familyobj = new JSONObject();
                familyobj.put("OFFENDER_ID", transid);
                familyobj.put("PROOFTYPE", c3.getString(1));
                familyobj.put("PROOFVALUE", c3.getString(2));
                familyobj.put("PROOF_ID_ATTACHMENT", c3.getString(3));

                familyobj.put("CreatedBy", getSharedPreferences("sendingravi", 0).getString("MobileNumber",""));
                jsondocumentarray.put(familyobj);
                jsondocumentinnerobj.put("EProofDetails", jsondocumentarray);
                Log.d("data", jsoninnerobj.toString());
            } catch (Exception e) {

            }
        }
    }
    try {

        JSONObject jsonfamilyparent = new JSONObject();
        jsonfamilyparent.put("erequest", jsondocumentinnerobj);

        Log.d("request for proof", jsonfamilyparent.toString());


        String urlfamily = "http://crimeproneareaservice.logicshore.co.in//LogicShore.svc/ADDProofDetails";

//                    if(cd.isConnectingToInternet()){

        try {

            Log.d("jsonparent", jsonassociatesinnerobj.toString());
            final ProgressDialog pDialog = new ProgressDialog(Photo.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    urlfamily, jsonfamilyparent,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("family data", response.toString());
                            pDialog.hide();
                            try {
                                if (response.getBoolean("status")) {
                                    db.deletealldocuments();
                                }
                            } catch (Exception e) {

                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("error message", "Error: " + error.getMessage());
                    // hide the progress dialog
                    pDialog.hide();
                }
            });

// Adding request to request queue
            requestQueue.add(jsonObjReq);
        } catch (Exception e) {

        }
//                    }
//                    else
//                    {
//                        Toast.makeText(getApplicationContext(),"No internet Connection",Toast.LENGTH_SHORT).show();
//                    }
    } catch (Exception e) {

    }
}


                new Upload().execute();
    }
}

