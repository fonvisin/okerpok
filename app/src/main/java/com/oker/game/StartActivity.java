package com.oker.game;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class StartActivity extends Activity
{
	int _keyCode = 0;
	GraffiasMaker graffiasMaker = null;
	private static Context CONTEXT;

	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) 
	{
		CONTEXT	=	this;
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);//OnlyOneChange
		setContentView(R.layout.game);
		WindowManager mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		CardRules.ScreenWidth =mWinMgr.getDefaultDisplay().getWidth();
		CardRules.ScreenHieght=mWinMgr.getDefaultDisplay().getHeight();
		graffiasMaker =new GraffiasMaker(this);
	    VortexView glSurface=(VortexView) findViewById(R.id.vortexview); // use the xml to set the view
	    glSurface.setRenderer(graffiasMaker);
	    glSurface.showRenderer(graffiasMaker);
	}
	public static Context getContext() {
	        return CONTEXT;
	}
	@Override 
	public void onPause () {
		CardRules.stop(CONTEXT);
		pause();
		super.onPause();
	}
	@Override 
	public void onResume() {
		resume();
		super.onResume();
		//view.onResume();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		Log.d("----------------=>  "+keyCode,"   -----------    ");
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			switch (CardRules.GameScreen) {
			case CardRules.GAMELOGO:case CardRules.GAMEMENU:
					get();
				break;
				case CardRules.GAMERAISE:
					CardRules.GameScreen = CardRules.GAMEPLAY;
					break;
				case CardRules.GAMEPLAY:
					CardRules.playStop();
					CardRules.GameScreen = CardRules.GAMEPAUSE;
					break;
				case CardRules.GAMEOVER:
				case CardRules.GAMECONG:
				case CardRules.GAMEWIN:
					graffiasMaker.IsGamePause = false;
				default:
					CardRules.GameScreen = CardRules.GAMEMENU;
					break;
				
			}
			return false;
		}
		_keyCode = keyCode;
		return super.onKeyDown(keyCode,event); 
	}  
	public boolean onKeyUp(int keyCode, KeyEvent event) 
	{
		if(keyCode==KeyEvent.KEYCODE_BACK)
			return false;
		_keyCode = 0;
		return super.onKeyUp( keyCode, event ); 
	}
	public void onDestroy()
	{
		super.onDestroy();
	}
	void pause()
	{
		int i=0;
//		if(CardRules.GameScreen == CardRules.GAMEPLAY)
//			CardRules.GameScreen = CardRules.GAMEPAUSE;
		
		graffiasMaker.resumeCounter = 0;
		CardRules.stop(graffiasMaker.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", CardRules.GameScreen);
	    editor.putBoolean("setValue", CardRules.setValue);
	    
	    editor.putBoolean("IsGamePause", graffiasMaker.IsGamePause);
	    for(i=0; i< graffiasMaker.cardShuffle.length; i++)
	    	editor.putInt("cs"+i, graffiasMaker.cardShuffle[i]);
	    
	    editor.putInt("AniCounter", graffiasMaker.AniCounter);
	    editor.putInt("ShowCurrent", graffiasMaker.ShowCurrent);
	    editor.putInt("cardNo", graffiasMaker.cardNo);
	    editor.putInt("mHighScore", graffiasMaker.mHighScore);
	    editor.putInt("NewCong", graffiasMaker.NewCong);
	    editor.putFloat("mSliderX", graffiasMaker.mSliderX);
		
	    
	    for(i=0; i< graffiasMaker.mCard.length; i++)
	    {
	    	editor.putInt("CdmState"+i, graffiasMaker.mCard[i].mState);
	    	editor.putInt("CdmBig"+i, graffiasMaker.mCard[i].mBig);
	    	editor.putInt("CdmBat"+i, graffiasMaker.mCard[i].mBat);
	    	editor.putInt("mTotalCoin"+i, graffiasMaker.mCard[i].mTotalCoin);
	    	
	    	editor.putFloat("Cdx"+i, graffiasMaker.mCard[i].x);
	    	editor.putFloat("Cdy"+i, graffiasMaker.mCard[i].y);
	    	editor.putFloat("Cdvx"+i, graffiasMaker.mCard[i].vx);
	    	editor.putFloat("Cdvy"+i, graffiasMaker.mCard[i].vy);
	    	
	    	editor.putFloat("Cdx1"+i, graffiasMaker.mCard[i].x1);
	    	editor.putFloat("Cdy1"+i, graffiasMaker.mCard[i].y1);
	    	editor.putFloat("Cdvx1"+i, graffiasMaker.mCard[i].vx1);
	    	editor.putFloat("Cdvy1"+i, graffiasMaker.mCard[i].vy1);
	    	
	    	
	    }
	    {
	    	for(i=0; i< graffiasMaker.mPoker.cardvals.length; i++)
	    		editor.putInt("pkcs"+i, graffiasMaker.mPoker.cardvals[i]);
	    	for(i=0; i< graffiasMaker.mPoker.correct.length; i++)
	    		editor.putInt("pkct"+i, graffiasMaker.mPoker.correct[i]);
	    	editor.putInt("pkst", graffiasMaker.mPoker.mState);
	    	
	    }
	    {
	    	editor.putInt("dlbc", graffiasMaker.mDealer.mBatChance);
	    	editor.putInt("dlgc", graffiasMaker.mDealer.GameCounter);
	    	editor.putInt("dlsc", graffiasMaker.mDealer.mShowCard);
	    	editor.putInt("dltb", graffiasMaker.mDealer.mTableBat);
	    	editor.putInt("dlcb", graffiasMaker.mDealer.mCurrentBat);
	    	editor.putInt("dlrc", graffiasMaker.mDealer.mRaiseCount);
	    	editor.putInt("dlbb", graffiasMaker.mDealer.mBigBat);
	    	editor.putInt("dlst", graffiasMaker.mDealer.mStart);
	    	editor.putInt("dlns", graffiasMaker.mDealer.mNewStart);
	    	
	   
	    }
	    
	    
	    
	    
	    editor.commit();
	}
	void resume()
	{
		int i=0;
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		CardRules.GameScreen = prefs.getInt("screen", CardRules.GAMELOGO);
		CardRules.setValue = prefs.getBoolean("setValue", CardRules.setValue);
		
		graffiasMaker.IsGamePause = prefs.getBoolean("IsGamePause", false);
	    for(i=0; i< graffiasMaker.cardShuffle.length; i++)
	    	graffiasMaker.cardShuffle[i] = (byte)prefs.getInt("cs"+i, graffiasMaker.cardShuffle[i]);
	    
	    graffiasMaker.AniCounter = prefs.getInt("AniCounter", graffiasMaker.AniCounter);
	    graffiasMaker.ShowCurrent = prefs.getInt("ShowCurrent", graffiasMaker.ShowCurrent);
	    graffiasMaker.cardNo = prefs.getInt("cardNo", graffiasMaker.cardNo);
	    graffiasMaker.mHighScore = prefs.getInt("mHighScore", graffiasMaker.mHighScore);
	    graffiasMaker.NewCong = prefs.getInt("NewCong", graffiasMaker.NewCong);
	    graffiasMaker.mSliderX = prefs.getFloat("mSliderX", graffiasMaker.mSliderX);
		
	    
	    for(i=0; i< graffiasMaker.mCard.length; i++)
	    {
	    	graffiasMaker.mCard[i].mState = (byte)prefs.getInt("CdmState"+i, graffiasMaker.mCard[i].mState);
	    	graffiasMaker.mCard[i].mBig = (byte)prefs.getInt("CdmBig"+i, graffiasMaker.mCard[i].mBig);
	    	graffiasMaker.mCard[i].mBat = prefs.getInt("CdmBat"+i, graffiasMaker.mCard[i].mBat);
	    	graffiasMaker.mCard[i].mTotalCoin = prefs.getInt("mTotalCoin"+i, graffiasMaker.mCard[i].mTotalCoin);
	    	
	    	graffiasMaker.mCard[i].x = prefs.getFloat("Cdx"+i, graffiasMaker.mCard[i].x);
	    	graffiasMaker.mCard[i].y = prefs.getFloat("Cdy"+i, graffiasMaker.mCard[i].y);
	    	graffiasMaker.mCard[i].vx= prefs.getFloat("Cdvx"+i, graffiasMaker.mCard[i].vx);
	    	graffiasMaker.mCard[i].vy= prefs.getFloat("Cdvy"+i, graffiasMaker.mCard[i].vy);
	    	
	    	graffiasMaker.mCard[i].x1 = prefs.getFloat("Cdx1"+i, graffiasMaker.mCard[i].x1);
	    	graffiasMaker.mCard[i].y1 = prefs.getFloat("Cdy1"+i, graffiasMaker.mCard[i].y1);
	    	graffiasMaker.mCard[i].vx1= prefs.getFloat("Cdvx1"+i, graffiasMaker.mCard[i].vx1);
	    	graffiasMaker.mCard[i].vy1= prefs.getFloat("Cdvy1"+i, graffiasMaker.mCard[i].vy1);
	    	
	    	
	    }
	    {
	    	for(i=0; i< graffiasMaker.mPoker.cardvals.length; i++)
	    		graffiasMaker.mPoker.cardvals[i] = (byte)prefs.getInt("pkcs"+i, graffiasMaker.mPoker.cardvals[i]);
	    	for(i=0; i< graffiasMaker.mPoker.correct.length; i++)
	    		graffiasMaker.mPoker.correct[i] = (byte)prefs.getInt("pkct"+i, graffiasMaker.mPoker.correct[i]);
	    	graffiasMaker.mPoker.mState = (byte)prefs.getInt("pkst", graffiasMaker.mPoker.mState);
	    	
	    }
	    {
	    	graffiasMaker.mDealer.mBatChance = prefs.getInt("dlbc", graffiasMaker.mDealer.mBatChance);
	    	graffiasMaker.mDealer.GameCounter = prefs.getInt("dlgc", graffiasMaker.mDealer.GameCounter);
	    	graffiasMaker.mDealer.mShowCard = prefs.getInt("dlsc", graffiasMaker.mDealer.mShowCard);
	    	graffiasMaker.mDealer.mTableBat = prefs.getInt("dltb", graffiasMaker.mDealer.mTableBat);
	    	graffiasMaker.mDealer.mCurrentBat = prefs.getInt("dlcb", graffiasMaker.mDealer.mCurrentBat);
	    	graffiasMaker.mDealer.mRaiseCount = prefs.getInt("dlrc", graffiasMaker.mDealer.mRaiseCount);
	    	graffiasMaker.mDealer.mBigBat = prefs.getInt("dlbb", graffiasMaker.mDealer.mBigBat);
	    	graffiasMaker.mDealer.mStart = prefs.getInt("dlst", graffiasMaker.mDealer.mStart);
	    	graffiasMaker.mDealer.mNewStart = prefs.getInt("dlns", graffiasMaker.mDealer.mNewStart);
	    	
	   
	    }
		
	    graffiasMaker.resumeCounter = 0;
	}
	void get()
	{
		   new AlertDialog.Builder(this).setIcon(R.drawable.empty).setTitle("Do you want to Exit?")
		    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
		    	finish();
                CardRules.GameScreen= CardRules.GAMELOGO;
				graffiasMaker.root.Counter =0;
	      }}).setNegativeButton("No",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which) {
    		   
	      }}).show();
	  }




}