package com.floorat.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.floorat.Activity.Home;
import com.floorat.ImageUtils.ImageLoader;
import com.floorat.R;
import com.floorat.RequestHandler.CustomRequest;
import com.floorat.SharedPrefrences.UserLocalStore;
import com.floorat.Utils.Util;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommentsAdapter extends ArrayAdapter<String> {

    ArrayList<String> ques_list = new ArrayList<>();
    ArrayList<String> ans_list = new ArrayList<>();
    ArrayList<String> name_list = new ArrayList<>();
    ArrayList<String> pic_list = new ArrayList<>();
    ArrayList<String> head_pic_list = new ArrayList<>();
    ArrayList<String> head_name_list = new ArrayList<>();
    ArrayList<String> comment_id_list = new ArrayList<>();
    ImageLoader imageLoader;
    Context ctx;
    String ids;
    UserLocalStore userLocalStore;

    public CommentsAdapter(Context context, String id, ArrayList<String> comment_id, ArrayList<String> ques, ArrayList<String> ans, ArrayList<String> name, ArrayList<String> pic, ArrayList<String> head_pic, ArrayList<String> head_name) {
        super(context, R.layout.classifieds_rowview, ques);
        ques_list = ques;
        ans_list = ans;
        name_list = name;
        pic_list = pic;
        head_pic_list = head_pic;
        head_name_list = head_name;
        comment_id_list = comment_id;
        imageLoader = new ImageLoader(context);
        ctx = context;
        ids=id;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater myinflater = LayoutInflater.from(getContext());
        View customView = myinflater.inflate(R.layout.comment_rowview, parent, false);


        TextView ques_name = (TextView) customView.findViewById(R.id.ques_name);
        TextView ques_text = (TextView) customView.findViewById(R.id.ques_text);
        TextView ans_name = (TextView) customView.findViewById(R.id.ans_name);
        TextView ans_text = (TextView) customView.findViewById(R.id.ans_text);
        ImageView ques_img = (ImageView) customView.findViewById(R.id.ques_image);
        ImageView ans_img = (ImageView) customView.findViewById(R.id.ans_image);


        if(ques_list.get(position).equals(ans_list.get(position)))
        {
            ans_text.setText("Write your answer here .. ");
            ans_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater layoutInflater = LayoutInflater.from(ctx);
                    View promptView = layoutInflater.inflate(R.layout.classified_comment_input_dialog, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
                    alertDialogBuilder.setView(promptView);

                    final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
                    alertDialogBuilder.setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String str = editText.getText().toString();
                                    insertans(str, head_name_list.get(position), comment_id_list.get(position));
                                }
                            })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = alertDialogBuilder.create();
                    alert.show();

                }
            });
        }
        else
        {
            ans_text.setText(ans_list.get(position));
        }

        ques_name.setText(name_list.get(position));
        ques_text.setText(ques_list.get(position));
        ans_name.setText(head_name_list.get(position));


        imageLoader.DisplayImage(pic_list.get(position), ques_img);
        imageLoader.DisplayImage(head_pic_list.get(position), ans_img);
        return customView;
    }
//bs_id 37 user_name Sanchit Mittal comment yes I have check  parent Nupur
    void insertans(String comment, String parent, String id)
    {
        final ProgressDialog pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Saving Answer...");
        pDialog.show();

        userLocalStore = new UserLocalStore(ctx);

        String url = "http://mogwliisjunglee.96.lt/classifiedapi.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", "insert_buy_sell_ans");
        params.put("bs_id", ids);
        params.put("user_name", userLocalStore.getname());
        params.put("comment", comment);
        params.put("parent", id);


        $id, $user_name, $comment, $parent


        System.out.println("Inputs are bs_id " + ids + " user_name " + userLocalStore.getname() + " comment " + comment + " parent " + id);

        System.out.println("Response" + params.toString());

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("Response: ", response.toString());
                pDialog.hide();
                confirmInsert(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    pDialog.hide();
                    Toast.makeText(ctx, "Time Out Error.....Try Later!!!", Toast.LENGTH_SHORT).show();
                    ctx.startActivity(new Intent(ctx, Home.class));
                } else if (error instanceof AuthFailureError) {
                    pDialog.hide();
                    Toast.makeText(ctx, "Authentication Error.....Try Later!!!", Toast.LENGTH_SHORT).show();
                    ctx.startActivity(new Intent(ctx, Home.class));
                } else if (error instanceof ServerError) {
                    pDialog.hide();
                    Toast.makeText(ctx, "Server Error.....Try Later!!!", Toast.LENGTH_SHORT).show();
                    ctx.startActivity(new Intent(ctx, Home.class));
                } else if (error instanceof NetworkError) {
                    pDialog.hide();
                    Toast.makeText(ctx, "Network Error.....Try Later!!!", Toast.LENGTH_SHORT).show();
                    ctx.startActivity(new Intent(ctx, Home.class));
                } else if (error instanceof ParseError) {
                    pDialog.hide();
                    Log.d("Response: ", error.toString());
                    Toast.makeText(ctx, "Unknown Error.....Try Later!!!", Toast.LENGTH_SHORT).show();
                    ctx.startActivity(new Intent(ctx, Home.class));
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(jsObjRequest);
    }

    void confirmInsert(JSONArray response) {
        String res = null;
        try {
            res = response.getString(0);
            if(res.equals("Success")){
                Intent in = new Intent(ctx, Home.class);
                ctx.startActivity(in);
            }
            else
                new Util().showerrormessage(ctx,"Something Went Wrong...." +
                        "Please Try Later");
        }
        catch (JSONException e) {
        }
    }
}