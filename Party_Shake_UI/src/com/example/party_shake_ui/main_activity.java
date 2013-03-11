package com.example.party_shake_ui;

import java.util.ArrayList;  
import java.util.List;  

import android.R.color;
import android.app.Activity;  
import android.graphics.BitmapFactory;  
import android.graphics.Color;
import android.graphics.Matrix;  
import android.os.Bundle;  
import android.support.v4.view.PagerAdapter;  
import android.support.v4.view.ViewPager;  
import android.support.v4.view.ViewPager.LayoutParams;
import android.support.v4.view.ViewPager.OnPageChangeListener;  
import android.util.DisplayMetrics;  
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.Window;  
import android.view.View.OnClickListener;  
import android.view.animation.Animation;  
import android.view.animation.TranslateAnimation;  
import android.view.ViewGroup;  
import android.widget.TextView; 
  
public class main_activity extends Activity {  
  
    private ViewPager viewPager;  
    private View imageView;
    private TextView textView1, textView2, textView3; 
    private View view1, view2, view3;  
    private List<View> views;  
    private List<TextView> Textviews; 
    private float offSet = 0;// ¶¯»­Í¼Æ¬Æ«ÒÆÁ¿  
    private int currIndex = 0;// µ±Ç°Ò³¿¨±àºÅ  
    private float cursorW=0;
    private View cursor;
    private float screenW=0;
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.main_activity_layout);  
        initView();  
        initListener();  
    }  
  
    private void initView() {  
        viewPager = (ViewPager) findViewById(R.id.vPager);  
  
        textView1 = (TextView) findViewById(R.id.speaktext);  
        textView2 = (TextView) findViewById(R.id.shaketext);  
        textView3 = (TextView) findViewById(R.id.settingtext);   
        Textviews = new ArrayList<TextView>(); 
        Textviews.add(textView1);  
        Textviews.add(textView2);  
        Textviews.add(textView3);
        
        cursor = findViewById(R.id.cursor);
  
        LayoutInflater layoutInflater = getLayoutInflater();  
        view1 = layoutInflater.inflate(R.layout.speaktab, null);  
        view2 = layoutInflater.inflate(R.layout.shaketab, null);  
        view3 = layoutInflater.inflate(R.layout.settingtab, null);   
        
        views = new ArrayList<View>();  
        views.add(view1);  
        views.add(view2);  
        views.add(view3);
  
         
        DisplayMetrics dm = new DisplayMetrics(); 
        Display currDisplay = getWindowManager().getDefaultDisplay(); 
        currDisplay.getMetrics(dm);
        screenW=currDisplay.getWidth();
        cursorW=Float.parseFloat(getString(R.string.curso_size))*dm.density+0.5f;
        Log.i("density", String.valueOf(dm.density));
        Log.i("cursorW", String.valueOf(cursorW));
        offSet=screenW/6 - cursorW/2;
        //Animation animation = new TranslateAnimation(0, offSet, 0, 0);
        //cursor.startAnimation(animation);
    }  
  
    private void initListener() {  
        textView1.setOnClickListener(new MyOnClickListener(0));  
        textView2.setOnClickListener(new MyOnClickListener(1));  
        textView3.setOnClickListener(new MyOnClickListener(2));  
  
        viewPager.setAdapter(new MyPagerAdapter(views));  
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());  
        viewPager.setCurrentItem(0);  
        textView1.setTextColor(Color.WHITE);
    }  
  
    private class MyPagerAdapter extends PagerAdapter {  
        private List<View> mListViews;  
  
        public MyPagerAdapter(List<View> mListViews) {  
            this.mListViews = mListViews;  
        }  
  
        @Override  
        public void destroyItem(ViewGroup container, int position, Object object) {  
            container.removeView(mListViews.get(position));  
        }  
  
        @Override  
        public Object instantiateItem(ViewGroup container, int position) {  
            container.addView(mListViews.get(position));  
            return mListViews.get(position);  
        }  
  
        @Override  
        public int getCount() {  
            return mListViews.size();  
        }  
  
        @Override  
        public boolean isViewFromObject(View arg0, Object arg1) {  
            return arg0 == arg1;  
        }  
  
    }  
  
    private class MyOnPageChangeListener implements OnPageChangeListener {  
        //int move = offSet * 2 + cursorW;// Ò³¿¨1 -> Ò³¿¨2 Æ«ÒÆÁ¿  
          
        public void onPageScrollStateChanged(int arg0) {  
              
  
        }  
  
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
              
  
        }  
  
        public void onPageSelected(int arg0) {
        	float move1=currIndex*screenW/3+offSet;
        	float move2=arg0*screenW/3+offSet;
            Animation animation = new TranslateAnimation(move1, move2, 0, 0);  
            currIndex = arg0;
            animation.setFillAfter(true);// True:Í¼Æ¬Í£ÔÚ¶¯»­½áÊøÎ»ÖÃ  
            animation.setDuration(300);  
            cursor.startAnimation(animation);  
            ((TextView)Textviews.get(currIndex)).setTextColor(0xffc0c0c0);
            ((TextView)Textviews.get(arg0)).setTextColor(Color.WHITE);
            viewPager.setCurrentItem(arg0);
        }  
  
    }  
  
    private class MyOnClickListener implements OnClickListener {  
        private int index = 0;  
  
        public MyOnClickListener(int i) {  
            index = i;  
        }  
  
        public void onClick(View v) {  
            ((TextView)Textviews.get(currIndex)).setTextColor(0xffc0c0c0);
            ((TextView)Textviews.get(index)).setTextColor(Color.WHITE);
            viewPager.setCurrentItem(index);  
              
        }  
  
    }  
}  
