package com.development.kernel.draft;

import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

class ViewInspector {

    private LayoutParams viewParams = null;
    private LinearLayout linearLayout = null;

    private int buttonsId = 0;
    private int CountButtonsTags = 0;

    private int CountTextViewTags = 0;
    private int textViewsId = 0;

    private int CountImageViewTags =0;
    private int imageViewsId = 0;

    private int CountCardViewTags = 0;
    private int cardViewsId = 0;

    private int WIS;
    private MainActivity mainActivity;

    private int countOfLinearLayouts = 0;
    private LinearLayout[] linearLayouts;

    ViewInspector(LayoutParams layoutParams, LinearLayout linearLayout, LinearLayout[] linearLayouts, MainActivity mainActivity) { //инициализация обязательных параметров
        this.viewParams = layoutParams;
        this.linearLayout = linearLayout;
        this.mainActivity = mainActivity;
        this.linearLayouts = linearLayouts;
    }

    Button setDefaultViewOptions(Button button) {  //назначает начальные настройки кнопке и тексту перегружен (2)
        button.setText("New button");
        button.setLayoutParams(viewParams);
        button.setId(buttonsId); //теги и id для массива кнопок
        String buttonTags = "Button:";
        button.setTag(buttonTags + String.valueOf(CountButtonsTags++));
        linearLayout.addView(button);
        buttonsId++;
        return button;
    }

    TextView setDefaultViewOptions(TextView textView) {
        textView.setText("New text");
        textView.setTextSize(30);
        textView.setLayoutParams(viewParams);
        textView.setId(textViewsId); //теги и id для массива кнопок
        String textViewsTags = "TextView:";
        textView.setTag(textViewsTags + String.valueOf(CountTextViewTags++)); //Была создана переменная, чтобы упростить читаемость кода, можно все сделать и с 2 переменными
        linearLayout.addView(textView);
        textViewsId++;
        return textView;
    }

    ImageView setDefaultViewOptions(ImageView imageView) {
        imageView.setLayoutParams(viewParams);
        linearLayout.addView(imageView);
        imageView.setMinimumWidth(300);
        imageView.setMinimumHeight(300);
        Picasso.with(mainActivity).load(R.drawable.rectangle).resize(500,500).into(imageView);
        String imageViewsTags = "ImageView:";
        imageView.setId(imageViewsId);
        imageView.setTag(imageViewsTags + String.valueOf(CountImageViewTags++));
        imageViewsId++;
        return imageView;
    }

    CardView setDefaultViewOptions(CardView cardView)
    {
        cardView.setLayoutParams(viewParams);
        linearLayout.addView(cardView);
        cardView.setMinimumWidth(1080);
        cardView.setMinimumHeight(500);

        LayoutParams layoutParams1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(mainActivity);
        linearLayouts[countOfLinearLayouts] = new LinearLayout(mainActivity);
        linearLayouts[countOfLinearLayouts].setOrientation(LinearLayout.HORIZONTAL);
        linearLayouts[countOfLinearLayouts].setLayoutParams(layoutParams1);
        linearLayouts[countOfLinearLayouts].setId(CountCardViewTags);
        horizontalScrollView.addView(linearLayouts[countOfLinearLayouts]);
        cardView.addView(horizontalScrollView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cardView.setElevation(10f);
        }
        countOfLinearLayouts++;
        cardView.setId(cardViewsId);
        cardView.setTag(CountCardViewTags++);
        cardViewsId++;

        return cardView;
    }

    void setContextOptions(MenuItem item)//запись выбранного элемента в контекстном меню в переменную
    {
        WIS = item.getItemId();
    }

    Button setPropertiesForView(Button button, EditText editText) //настройки для кнопки
    {
        switch (WIS)
        {
            case 1: //изменить ширину
                try {
                    button.setWidth(Integer.valueOf(editText.getText().toString()));
                }
                catch (Exception e) {
                    Toast.makeText(mainActivity,"Неверный формат ввода",Toast.LENGTH_SHORT).show();
                }
                break;
            case 2: //изменить высоту
                try {
                    button.setHeight(Integer.valueOf(editText.getText().toString()));
                }
                catch (Exception e) {
                    Toast.makeText(mainActivity,"Неверный формат ввода",Toast.LENGTH_SHORT).show();
                }
                break;
            case 3: //изменить название
                button.setText(editText.getText().toString());
                break;
            case 4: //удалить view элемент с экрана
                CountButtonsTags--; //понизить тег, чтобы предотратить ошибку
                break;
        }
        return button; //возвратить view элемент со всеми настройками
    }

    TextView setPropertiesForView(TextView textView, EditText editText, Button buttonEdit)//настройки для строки
    {
        switch (WIS)
        {
            case 1:

                textView.setText(editText.getText().toString());

                break;
            case 2:
                try {
                    editText.setInputType(InputType.TYPE_CLASS_PHONE);
                    textView.setTextSize(Integer.valueOf(editText.getText().toString()));
                }
                catch (Exception e) {
                    Toast.makeText(mainActivity, "Неверный формат ввода", Toast.LENGTH_SHORT).show();
                }
                break;
            case 4:
                CountTextViewTags--;//понижение тега
                break;
        }
        editText.setVisibility(View.GONE);
        buttonEdit.setVisibility(View.GONE);
        return textView; //возвратить view элемент со всеми настройками
    }

    ImageView setPropertiesForView(ImageView imageView, Uri selectImage, EditText editText, EditText editText1) //настройки для строки
    {
        switch (WIS)
        {
            case 7:
                try {
                    Picasso.with(mainActivity).load(selectImage).resize(Integer.valueOf(editText1.getText().toString()), Integer.valueOf(editText.getText().toString())).into(imageView);
                }
                catch (Exception e) {
                    Toast.makeText(mainActivity,"Неверный формат ввода", Toast.LENGTH_SHORT).show();
                }
                break;
            case 4:
                CountImageViewTags--;//понижение тега
                break;
        }
        return imageView; //возвратить view элемент со всеми настройками
    }

    CardView setPropertiesForView(CardView cardView, ImageView[] imageViews, int ID) //настройки для строки
    {
        switch (WIS)
        {
            case 10:
                linearLayouts[ID].addView(imageViews[ID] = new ImageView(mainActivity));
                Picasso.with(mainActivity).load(R.drawable.rectangle).resize(360,360).into(imageViews[ID]);
                break;
            case 4:
                CountImageViewTags--;//понижение тега
                break;
        }
        return cardView; //возвратить view элемент со всеми настройками
    }

}