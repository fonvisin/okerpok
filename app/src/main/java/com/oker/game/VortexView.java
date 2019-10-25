package com.oker.game;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class VortexView extends GLSurfaceView 
{
	public GraffiasMaker _renderer;
	public StartActivity mStartActivity = null;
	public VortexView(Context context) { 
		super(context);
		mStartActivity = (StartActivity)context;
	}
	public VortexView(Context context, AttributeSet attrs) { 
		super(context, attrs);
		mStartActivity = (StartActivity)context;
	}
	public void showRenderer(GraffiasMaker renderer){
		this._renderer=renderer;        
	}
	public boolean onTouchEvent(final MotionEvent event) 
	{ 
		_renderer.root.TouchEvent(event);

		return true;
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return super.onKeyDown( keyCode, event ); 
	}  
	public boolean onKeyUp(int keyCode, KeyEvent event) 
	{  	
	 	return super.onKeyUp( keyCode, event ); 
	}
}
