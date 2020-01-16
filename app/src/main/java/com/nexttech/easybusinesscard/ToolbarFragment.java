package com.nexttech.easybusinesscard;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ToolbarFragment extends Fragment {

    private Context context;

    public ToolbarFragment(Context context){
        this.context=context;
    }
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    private TextView text,icon,image,qrcode,preview,backside,text2;


    int i=0;
    int ii=0;

    public static HashMap<String,TextView> textArrayFront;
    public static HashMap<String,TextView> textArrayBack;




    public void addTextView(){
        final TextView mTextView = new TextView(context);
        mTextView.setText("new Text");
        if(Create_card.isLayoutVisible()){
            mTextView.setTag(String.valueOf(i));
        }else {
            mTextView.setTag(String.valueOf(ii));
        }

        mTextView.setTextSize(25);
        mTextView.setTextColor(Color.BLACK);
        Typeface typeface =  Typeface.createFromAsset(context.getAssets(),new FrontTag().getFrontName("tag1"));
        mTextView.setTypeface(typeface);
        mTextView.setOnLongClickListener(new LongPresslistener(context));
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Create_card.setCurrentFragmentwithData(1,mTextView.getTag().toString());
                Create_card.mAdapter.notifyDataSetChanged();

            }
        });



        if (Create_card.isLayoutVisible()){
            textArrayFront.put(String.valueOf(i),mTextView);
            Create_card.setCurrentFragmentwithData(1,mTextView.getTag().toString());
            Create_card.mAdapter.notifyDataSetChanged();

            i++;
        } else {
            textArrayBack.put(String.valueOf(ii),mTextView);
            Create_card.setCurrentFragmentwithData(1,mTextView.getTag().toString());
            Create_card.mAdapter.notifyDataSetChanged();
            ii++;
        }

    }
    public void addView(View vi){
        if(Create_card.isLayoutVisible()){
            if(vi.getParent() != null) {
                ((ViewGroup)vi.getParent()).removeView(vi); // <- fix
            }
            Create_card.absoluteLayoutFront.addView(vi);
        }else {
            if(vi.getParent() != null) {
                ((ViewGroup)vi.getParent()).removeView(vi); // <- fix
            }
            Create_card.absoluteLayoutBack.addView(vi);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi= getLayoutInflater().inflate(R.layout.fragment_toolbar,null);
        text=vi.findViewById(R.id.textFieldTextview);

        icon=vi.findViewById(R.id.icon);
        image=vi.findViewById(R.id.image);
        qrcode=vi.findViewById(R.id.qrcode);
        preview=vi.findViewById(R.id.preview);
        backside=vi.findViewById(R.id.backside);
        text2=vi.findViewById(R.id.text2);
        builder = new AlertDialog.Builder(context);
        textArrayFront =new HashMap<>();
        textArrayBack =new HashMap<>();


        text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                addTextView();
                if (Create_card.isLayoutVisible()){
                    for(int j=0;j<textArrayFront.size();j++){
                        if(textArrayFront.get(String.valueOf(j))!=null){
                            addView(textArrayFront.get(String.valueOf(j)));
                        }
                    }
                } else {
                    for(int j=0;j<textArrayBack.size();j++){
                        if(textArrayBack.get(String.valueOf(j))!=null){
                            addView(textArrayBack.get(String.valueOf(j)));
                        }
                    }
                }


            }
        });



        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Create_card.viewPager.setCurrentItem(2);
                Create_card.mAdapter.notifyDataSetChanged();


            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Create_card.viewPager.setCurrentItem(3);
                Create_card.mAdapter.notifyDataSetChanged();
            }
        });

        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Create_card.viewPager.setCurrentItem(4);
                Create_card.mAdapter.notifyDataSetChanged();
            }
        });

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Create_card.absoluteLayoutFront.getVisibility()==View.VISIBLE){
                    backside.setText("Front Side");
                    Create_card.absoluteLayoutFront.setVisibility(View.GONE);
                    Create_card.absoluteLayoutBack.setVisibility(View.VISIBLE);
                } else{
                    backside.setText("Back Side");
                    Create_card.absoluteLayoutFront.setVisibility(View.VISIBLE);
                    Create_card.absoluteLayoutBack.setVisibility(View.GONE);
                }
                ShowDialogebox();

            }
        });


        backside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (Create_card.absoluteLayoutFront.getVisibility()==View.VISIBLE){
                    backside.setText("Front Side");
                    Create_card.absoluteLayoutFront.setVisibility(View.GONE);
                    Create_card.absoluteLayoutBack.setVisibility(View.VISIBLE);
                } else{
                    backside.setText("Back Side");
                    Create_card.absoluteLayoutFront.setVisibility(View.VISIBLE);
                    Create_card.absoluteLayoutBack.setVisibility(View.GONE);
                }


            }
        });

        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Create_card.viewPager.setCurrentItem(5);
                Create_card.mAdapter.notifyDataSetChanged();
            }
        });
        return vi;
    }


    void ShowDialogebox()
    {

        final TextView front,back;

        final ImageView imageView;


       View dialogueView = getLayoutInflater().inflate(R.layout.dialougebox, null);

       front=dialogueView.findViewById(R.id.front);
       back=dialogueView.findViewById(R.id.back);
       imageView=dialogueView.findViewById(R.id.imageview);


        imageView.setImageBitmap(Create_card.loadBitmapFromView(Create_card.absoluteLayoutFront));
        front.setTypeface(null, Typeface.BOLD);




       front.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               imageView.setImageBitmap(Create_card.loadBitmapFromView(Create_card.absoluteLayoutFront));
               front.setTypeface(null, Typeface.BOLD);
               back.setTypeface(null, Typeface.NORMAL);
           }
       });

       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Create_card.absoluteLayoutFront.setVisibility(View.GONE);
               Create_card.absoluteLayoutBack.setVisibility(View.VISIBLE);

               imageView.setImageBitmap(Create_card.loadBitmapFromView(Create_card.absoluteLayoutBack));
               back.setTypeface(null, Typeface.BOLD);
               front.setTypeface(null, Typeface.NORMAL);
           }
       });


        builder.setView(null);
        builder.setView(dialogueView);
        alertDialog=builder.create();
        alertDialog.setCanceledOnTouchOutside(true);

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Create_card.absoluteLayoutFront.setVisibility(View.VISIBLE);
                Create_card.absoluteLayoutBack.setVisibility(View.GONE);
            }
        });
        alertDialogDismiss();
        alertDialog.show();



    }
    private void alertDialogDismiss(){
        if (alertDialog.isShowing()){
            alertDialog.dismiss();


        }
    }
}
