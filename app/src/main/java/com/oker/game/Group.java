package com.oker.game;
import javax.microedition.khronos.opengles.GL10;
import android.view.MotionEvent;
public class Group extends Mesh 
{
	GraffiasMaker mGR = null;
	int Counter =0;
	int Counter2 =0;
	public Group(GraffiasMaker _GraffiasMaker)
	{
		mGR = _GraffiasMaker;
	}
	
	@Override
	public void draw(GL10 gl) 
	{
		
		Counter++;
		if(Counter%10==0)
			Counter2++;
//		CardRules.GameScreen = CardRules.GAMEOVER;
		switch (CardRules.GameScreen) {
		case CardRules.GAMELOGO:
			/*for(int i =0;i<15;i++){
				DrawTexture(gl, graffiasMaker.mTex_Card[graffiasMaker.cardShuffle[i]], -.9f+i*.1f, 0);
			}*/
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter >60)
			{
				CardRules.GameScreen = CardRules.GAMEMENU;
				CardRules.GameScreen = CardRules.GAMEADD;//AdHouse
				Counter=0;//AdHouse
			}
			break;
			/*AdHouse*/
		case CardRules.GAMEADD:
			if(Counter>100)
				DrawTexture(gl,mGR.mTex_Skip, .9f,-.9f);
			else{
				DrawTexture(gl, mGR.mTex_Hightbar,0.2f,-.9f);
				DrawTexture(gl, mGR.mTex_Pointer,.2f+Counter*(mGR.mTex_Hightbar.width()/100f)-.32f,-.9f);
			}
			break;
			/*AdHouse*/
		case CardRules.GAMELOAD:
			DrawTexture(gl, mGR.mTex_Ling,0.0f,-.9f);
			mGR.mTex_LingBar.drawSS(gl,-.63f,-.9f	, Counter*.31f, 1.3f);//10
			DrawTexture(gl, mGR.mTex_Loading,0.0f,-.8f);
			if(Counter>200){
				CardRules.GameScreen = CardRules.GAMEPLAY;
			}
			break;
		case CardRules.GAMESPLA:
		{
			DrawSplash(gl);
		}
			break;
		case CardRules.GAMEMENU:
			DrawMenu(gl);
			break;
		case CardRules.GAMEOVER:
		case CardRules.GAMEPAUSE:
		case CardRules.GAMECONG:
			DrawGameOver(gl);
			break;
		case CardRules.GAMEHELP:
		case CardRules.GAMEINFO:
		case CardRules.GAMEHIGH:
			Draw_HIH(gl);
			break;
		case CardRules.GAMERAISE:
		case CardRules.GAMEPLAY:
		case CardRules.GAMEWIN:
			Draw_GamePlay(gl);
			break;
		}
	}
	public boolean TouchEvent(MotionEvent event) 
	{	
		switch (CardRules.GameScreen) {
		/*AdHouse*/
		case CardRules.GAMEADD:
			if((MotionEvent.ACTION_UP == event.getAction())&&(Counter>100)&&
					CirCir(0.9f,-.9f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				CardRules.GameScreen = CardRules.GAMESPLA;
			}
			break;
			/*AdHouse*/
		case CardRules.GAMESPLA:
			Handle_Splash(event);
			break;
		case CardRules.GAMEMENU:
			Handle_Menu(event);
			break;
		case CardRules.GAMEOVER:
		case CardRules.GAMEPAUSE:
		case CardRules.GAMECONG:
			Handle_GameOver(event);
			break;
		case CardRules.GAMERAISE:
			Handle_Raise(event);
			break;
		case CardRules.GAMEPLAY:
		case CardRules.GAMEWIN:
			Handle_Game(event);
			break;
		case CardRules.GAMEHELP:
		case CardRules.GAMEINFO:
		case CardRules.GAMEHIGH:
			Handle_HIH(event);
			break;
		}
		return true;
	}
	
	/************Help Info HighScore StartActivity*************/
	void Draw_HIH(GL10 gl)
	{
		if(CardRules.GameScreen == CardRules.GAMEHELP)
		{
			DrawTexture(gl, mGR.mTex_Help, 0, 0);
		}
		if(CardRules.GameScreen == CardRules.GAMEINFO)
		{
			DrawTexture(gl, mGR.mTex_AllBack, 0, 0);
			DrawTexture(gl, mGR.mTex_BigPopup, 0, 0);
			DrawTexture(gl, mGR.mTex_Poker, 0, .4f);
			DrawTexture(gl, mGR.mTex_About, 0, -.1f);
		}
		if(CardRules.GameScreen == CardRules.GAMEHIGH)
		{
			DrawTexture(gl, mGR.mTex_AllBack, 0, 0);
			DrawTexture(gl, mGR.mTex_BigPopup, 0, 0);
			DrawTexture(gl, mGR.mTex_Poker, 0, .4f);
			DrawTextureTS(gl, mGR.mTex_MD[4],0, .2f,.4f,1);
			drawNumber(gl, mGR.mHighScore, -.1f, -.1f, 1);
		}
		DrawTexture(gl, mGR.mTex_Back,0.88f, -.8f);
		if(mGR.mSel == 1)
			DrawTexture(gl, mGR.mTex_buttonSe, 0.88f, -.8f);
	}
	boolean Handle_HIH(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(0.88f, -.8f, mGR.mTex_Back.width()*.4f, mGR.mTex_Back.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;//Cancle
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				CardRules.GameScreen = CardRules.GAMEMENU;
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	
	/************Help Info HighScore End***************/
	
	/*************************Splash StartActivity*************/
	void DrawSplash(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_AllBack, 0, 0);
		DrawTexture(gl, mGR.mTex_SplCount[mGR.mSel==1?1:0], 0, -.7f);
		DrawTexture(gl, mGR.mTex_Star	, -.87f,-.88f);
		DrawTexture(gl, mGR.mTex_Cancel	, +.90f,-.88f);
	}
	boolean Handle_Splash(MotionEvent event)
	{
		mGR.mSel =0;
		if(CircRectsOverlap(-.0f, -.7f, mGR.mTex_SplCount[0].width()*.4f, mGR.mTex_SplCount[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;//Continue
		}
//		if(CircRectsOverlap(-.87f,-.88f, graffiasMaker.mTex_Star.width()*.4f, graffiasMaker.mTex_Star.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
//		{
//			graffiasMaker.mSel = 2;//Star
//		}
		if(CircRectsOverlap(+.90f,-.88f, mGR.mTex_Star.width()*.4f, mGR.mTex_Star.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;//Cancle
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
//				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				CardRules.GameScreen = CardRules.GAMEMENU;
				break;
//			case 2:
//				RateUs();
//				break;
			case 3:
				GraffiasMaker.mStartActivity.get();
				break;

			}
			mGR.mSel =0;	
		}
		return true;
	}
	/*************************Splash End*************/
	/******************Menu  StartActivity*******************/
	void DrawMenu(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_AllBack, 0, 0);
		DrawTexture(gl, mGR.mTex_BigPopup, 0, -.1f);
		
		for(int i=0;i<mGR.mTex_MS.length;i++)
		{
			if((mGR.IsGamePause && i==0)||i!=0)
			{
				DrawTexture(gl, mGR.mTex_MD[i],0, .3f-i*.2f);
				if(i==mGR.mSel-1)
					DrawTexture(gl, mGR.mTex_MS[i],0, .3f-i*.2f);
			}
			else
				DrawTextureTS(gl, mGR.mTex_MD[i],0, .3f-i*.2f,.3f,1);
		}
		
		DrawTexture(gl, mGR.mTex_MSound[0], -.88f, -.8f);
		if(!CardRules.setValue)
			DrawTexture(gl, mGR.mTex_MSound[1], -.88f, -.8f);
		if(mGR.mSel==6)
			DrawTexture(gl, mGR.mTex_buttonSe, -.88f, -.8f);
		DrawTexture(gl, mGR.mTex_MExit,0.88f, -.8f);
		if(mGR.mSel==7)
			DrawTexture(gl, mGR.mTex_buttonSe, 0.88f, -.8f);
		
	}
	boolean Handle_Menu(MotionEvent event)
	{
		mGR.mSel =0;
		for(int i=0;i<mGR.mTex_MS.length;i++){
			if((mGR.IsGamePause && i==0)||i!=0)
			if(CircRectsOverlap(0, .3f-i*.2f, mGR.mTex_SoundOn[0].width()*.4f, mGR.mTex_SoundOn[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = i+1;//Sound
			}
		}
		
		if(CircRectsOverlap(-.88f, -.8f, mGR.mTex_Menu.width()*.4f, mGR.mTex_Menu.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 6;//Sound
		}
		if(CircRectsOverlap(0.88f, -.8f, mGR.mTex_Share.width()*.4f, mGR.mTex_Share.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 7;//Exit
		}
		
		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			
			switch (mGR.mSel){
			case 1:
				CardRules.GameScreen = CardRules.GAMEPLAY;
				break;
			case 2:
				mGR.Reset();
				mGR.NewCong = 0;
				Counter = 0;
				CardRules.GameScreen = CardRules.GAMELOAD;
				CardRules.GameScreen = CardRules.GAMEPLAY;
				break;
			case 3:
				CardRules.GameScreen = CardRules.GAMEHELP;
				break;
			case 4:
				CardRules.GameScreen = CardRules.GAMEINFO;
				break;
			case 5:
				CardRules.GameScreen = CardRules.GAMEHIGH;
				break;
			case 6:
				CardRules.setValue = !CardRules.setValue;//Sound
				break;
			case 7:
				GraffiasMaker.mStartActivity.get();
				break;
				}
			mGR.mSel =0;
		}
		return true;
	}
	
	/******************Menu End*******************/
	void DrawPopUp(GL10 gl,int cno)
	{
		switch (cno) {
		case 0:
			DrawTexture(gl, mGR.mTex_Popup,-.5f, .6f);
			drawNumber(gl, mGR.mCard[cno].mTotalCoin-mGR.mCard[cno].mBat, -.48f, .69f,0);
			drawNumber(gl, mGR.mCard[cno].mBat, -.48f, .60f,0);
			break;
		case 1:
			DrawTexture(gl, mGR.mTex_Popup,0.5f, .6f);
			drawNumber(gl, mGR.mCard[cno].mTotalCoin-mGR.mCard[cno].mBat, 0.52f, .69f,0);
			drawNumber(gl, mGR.mCard[cno].mBat, 0.52f, .60f,0);
			break;
		case 2:
			DrawTexture(gl, mGR.mTex_Popup,0.5f,-.6f);
			drawNumber(gl, mGR.mCard[cno].mTotalCoin-mGR.mCard[cno].mBat, 0.52f,-.51f,0);
			drawNumber(gl, mGR.mCard[cno].mBat, 0.52f,-.60f,0);
			break;
		case 3:
			DrawTexture(gl, mGR.mTex_Popup,0.0f,-.6f);
			drawNumber(gl, mGR.mCard[cno].mTotalCoin-mGR.mCard[cno].mBat, 0.02f,-.51f,0);
			drawNumber(gl, mGR.mCard[cno].mBat, 0.02f,-.60f,0);
			break;
		case 4:
			DrawTexture(gl, mGR.mTex_Popup,-.5f,-.6f);
			drawNumber(gl, mGR.mCard[cno].mTotalCoin-mGR.mCard[cno].mBat, -.48f,-.51f,0);
			drawNumber(gl, mGR.mCard[cno].mBat, -.48f,-.60f,0);
			break;
		}
	}
	
	

	void DrawGameOver(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_AllBack, 0, 0);
		DrawTexture(gl, mGR.mTex_BigPopup, 0, 0);
		DrawTexture(gl, mGR.mTex_NScore, -.0f, 0.1f);
		DrawTexture(gl, mGR.mTex_BScore, -.0f, -.1f);
		if(CardRules.GameScreen == CardRules.GAMEOVER)
		{
			DrawTexture(gl, mGR.mTex_Gameover, 0, 0.3f);
			DrawTexture(gl, mGR.mTex_Share,0.88f, -.8f);
			if(mGR.mSel== 3)
				DrawTexture(gl, mGR.mTex_Retry[1],.0f,-.6f);
			else
				DrawTexture(gl, mGR.mTex_Retry[0],.0f,-.6f);
			
		}
		if(CardRules.GameScreen == CardRules.GAMEPAUSE)
		{
			DrawTexture(gl, mGR.mTex_Pausescr, 0, 0.3f);
//			DrawTexture(gl, graffiasMaker.mTex_RateUs,0.88f, -.8f);
			if(mGR.mSel== 3)
				DrawTexture(gl, mGR.mTex_Countinue[1],.0f,-.6f);
			else
				DrawTexture(gl, mGR.mTex_Countinue[0],.0f,-.6f);
		}
		if(CardRules.GameScreen == CardRules.GAMECONG)
		{
			DrawTexture(gl, mGR.mTex_Win, 0, 0.3f);
			DrawTexture(gl, mGR.mTex_Share,0.88f, -.8f);
			if(mGR.mSel== 3)
				DrawTexture(gl, mGR.mTex_Next[1],.0f,-.6f);
			else
				DrawTexture(gl, mGR.mTex_Next[0],.0f,-.6f);
		}
		
		drawNumber(gl, mGR.mCard[CardRules.ID].mTotalCoin, .05f, .1f, 1);
		drawNumber(gl, mGR.mHighScore, .05f,-.1f, 1);
		
		if(CardRules.setValue)
		{
			if(mGR.mSel== 1)
				DrawTexture(gl, mGR.mTex_SoundOn[1]	, -.23f, -.32f);
			else
				DrawTexture(gl, mGR.mTex_SoundOn[0]	, -.23f, -.32f);
		}
		else
		{
			if(mGR.mSel== 1)
				DrawTexture(gl, mGR.mTex_SoundOff[1], -.23f, -.32f);
			else
				DrawTexture(gl, mGR.mTex_SoundOff[0], -.23f, -.32f);
		}
		if(mGR.mSel== 2)
			DrawTexture(gl, mGR.mTex_HSin[1]	, 0.17f, -.32f);
		else
			DrawTexture(gl, mGR.mTex_HSin[0]	, 0.17f, -.32f);
		
		DrawTexture(gl, mGR.mTex_Menu, -.88f, -.8f);
		if(mGR.mSel == 4)
			DrawTexture(gl, mGR.mTex_buttonSe, -.88f, -.8f);
		if(mGR.mSel == 5)
			DrawTexture(gl, mGR.mTex_buttonSe, 0.88f, -.8f);
	}
	boolean Handle_GameOver(MotionEvent event)
	{
		mGR.mSel =0;
		if(CircRectsOverlap(-.23f, -.32f, mGR.mTex_SoundOn[0].width()*.4f, mGR.mTex_SoundOn[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;//Sound
		}
		if(CircRectsOverlap(0.17f, -.32f, mGR.mTex_HSin[0].width()*.4f, mGR.mTex_HSin[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;//HighScore
		}
		if(CircRectsOverlap(.0f,-.6f, mGR.mTex_Retry[0].width()*.4f, mGR.mTex_Retry[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;//Retry
		}
		if(CircRectsOverlap(-.88f, -.8f, mGR.mTex_Menu.width()*.4f, mGR.mTex_Menu.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4;//Menu
		}
//		if(CircRectsOverlap(0.88f, -.8f, graffiasMaker.mTex_Share.width()*.4f, graffiasMaker.mTex_Share.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
//		{
//			graffiasMaker.mSel = 5;//Share
//		}
		
		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			
			switch (mGR.mSel){
			case 1:
				CardRules.setValue = !CardRules.setValue;//Sound
				break;
			case 2:
				CardRules.GameScreen = CardRules.GAMEHIGH;
				break;
			case 3:
				if(CardRules.GAMEPAUSE == CardRules.GameScreen)
					CardRules.GameScreen = CardRules.GAMEPLAY;
				else if(CardRules.GAMECONG == CardRules.GameScreen)
					mGR.Reset();
				else{
					mGR.Reset();
					mGR.NewCong = 0;
				}
				break;
			case 4:
				CardRules.GameScreen = CardRules.GAMEMENU;
				break;
			case 5:
//				if(CardRules.GAMEPAUSE != CardRules.GameScreen)
//					share2friend();
//				else
//					RateUs();
				break;
				}
			mGR.mSel =0;
		}
		return true;
	}
	
	
	void performAction()
    {
		int tableTotal = 0;
//    	int tableTotal = graffiasMaker.mPlayer[graffiasMaker.mDealer.CurrentPos].TMoney+graffiasMaker.mDealer.CurrentBat;
    	int playerCardTotal = (mGR.cardShuffle[mGR.mDealer.mBatChance*2]%13)+(mGR.cardShuffle[mGR.mDealer.mBatChance*2+1]%13);
    	switch (mGR.mCard[mGR.mDealer.mBatChance].mState) {
		case CardRules.NOTHING:
			if(playerCardTotal<10)
			{
				if(mGR.mDealer.mBigBat <= mGR.mCard[mGR.mDealer.mBatChance].mBat)
				{
					mGR.mCard[mGR.mDealer.mBatChance].mState = CardRules.NOTHING;
					checkShowCard();
				}
				else
				{
					if(mGR.mCard[mGR.mDealer.mBatChance].mState > CardRules.FOLD)
					{
						mGR.mCard[mGR.mDealer.mBatChance].mState = CardRules.FOLD;
					}
				}
					
			}
			else if(mGR.mDealer.mTableBat*3 >  mGR.mDealer.mCurrentBat)
			{
				Raise(0);
			}
			else
			{
				if(mGR.mCard[mGR.mDealer.mBatChance].mState > CardRules.FOLD){
					mGR.mCard[mGR.mDealer.mBatChance].mState = CardRules.FOLD;}
			}
			break;
			
			case CardRules.ROYALIST:
				if(mGR.mDealer.mRaiseCount<2)
					Raise(15+ CardRules.mRand.nextInt(30));
				else
					Raise(0);
		    	break;
		    	
		    case CardRules.STRAIGHTENS:
		    	if(mGR.mDealer.mRaiseCount<2)
		    		Raise(15+ CardRules.mRand.nextInt(25));
				else
					Raise(0);
		    	break;
		    case CardRules.FORESKIN:
		    	if(mGR.mDealer.mRaiseCount<2)
		    		Raise(15+ CardRules.mRand.nextInt(20));
				else
					Raise(0);
		    	break;
		    	
		    case CardRules.FALLOUT:
		    	if(mGR.mDealer.mRaiseCount<2)
		    		Raise(15+ CardRules.mRand.nextInt(15));
				else
					Raise(0);
		    	break;
		    	
		    case CardRules.FLUSH:
		    	if(mGR.mDealer.mRaiseCount<2)
		    		Raise(15+ CardRules.mRand.nextInt(15));
				else
					Raise(0);
		    	break;
		    case CardRules.STRAIGHT:
		    	if(mGR.mDealer.mRaiseCount<2)
		    		Raise(15+ CardRules.mRand.nextInt(22));
				else
					Raise(0);
		    	
		    	break;
		    case CardRules.THREEFOLD:
		    	if(mGR.mDealer.mRaiseCount<2)
		    		Raise(CardRules.mRand.nextInt(35));
				else
					Raise(0);
		    	
		    	break;
		    case CardRules.TAPIR:
		    	if(mGR.mDealer.mRaiseCount<2)
		    		Raise(CardRules.mRand.nextInt(25));
				else
					Raise(0);
		    	
		    	break;
		    case CardRules.PAIR:
		    	if(mGR.mDealer.mRaiseCount<2)
		    	{
		    		if(tableTotal>50)
		    		{
			    		Raise(0);
		    		}
			    	else if(playerCardTotal<6)
			    	{
			    		if(CardRules.mRand.nextBoolean())
							Raise(CardRules.mRand.nextInt(15));
						else
							Raise(0);
			    	}
					else if(playerCardTotal<16 && tableTotal<30)
					{	if(CardRules.mRand.nextBoolean())
							Raise(CardRules.mRand.nextInt(15));
						else
							Raise(0);
					}else
					{	if(CardRules.mRand.nextBoolean())
							Raise(CardRules.mRand.nextInt(15));
						else
							Raise(0);
					}
		    	}
				else
					Raise(0);
		    	
	    	break;
		    case CardRules.FOLD:
			break;
		}
    }
	void Raise(int raise)
	{
		int total = mGR.mCard[mGR.mDealer.mBatChance].mBat + mGR.mDealer.mCurrentBat+raise;
		if(mGR.mDealer.mBigBat > mGR.mCard[mGR.mDealer.mBatChance].mBat || raise !=0)
		{
			if(raise == 0)
			{
				total = mGR.mDealer.mBigBat - mGR.mCard[mGR.mDealer.mBatChance].mBat;
				if(total > 0)
					mGR.mDealer.mCurrentBat = total;
			}
			total = mGR.mCard[mGR.mDealer.mBatChance].mBat + mGR.mDealer.mCurrentBat+raise;
			if(mGR.mCard[mGR.mDealer.mBatChance].mTotalCoin > total)
			{
				mGR.mCard[mGR.mDealer.mBatChance].mBat += mGR.mDealer.mCurrentBat+raise;
				mGR.mDealer.mCurrentBat += raise;
			}
			else
			{
				total = mGR.mCard[mGR.mDealer.mBatChance].mBat + mGR.mDealer.mCurrentBat;
				if(mGR.mCard[mGR.mDealer.mBatChance].mTotalCoin > total)
				{
					total = mGR.mCard[mGR.mDealer.mBatChance].mTotalCoin - total;
					if(total > 0)
						raise = total;
					else
						raise =0;
					mGR.mCard[mGR.mDealer.mBatChance].mBat += mGR.mDealer.mCurrentBat+raise;
					mGR.mDealer.mCurrentBat += raise;
				}
				else
				{
					if(mGR.mCard[mGR.mDealer.mBatChance].mBat != mGR.mCard[mGR.mDealer.mBatChance].mTotalCoin)
					{
						mGR.mCard[mGR.mDealer.mBatChance].mBat = mGR.mCard[mGR.mDealer.mBatChance].mTotalCoin;
					}
					else
					{
						checkShowCard();
					}
				}
			}
			if(raise!=0)
    		{
    			mGR.mDealer.mRaiseCount++;
    		}
		}
		else
			checkShowCard();
		if(mGR.mDealer.mBigBat < mGR.mCard[mGR.mDealer.mBatChance].mBat)	
			mGR.mDealer.mBigBat = mGR.mCard[mGR.mDealer.mBatChance].mBat;
	}
	void checkShowCard()
	{
		for(int i=0;i<mGR.mCard.length;i++)
		{
			if(mGR.mCard[mGR.mDealer.mStart].mState <= CardRules.FOLD)
			{
				mGR.mDealer.mStart++;
				mGR.mDealer.mStart%=mGR.mCard.length;
			}
			else
				break;
		}
		if(mGR.mDealer.mStart == mGR.mDealer.mBatChance)
		{
			if(mGR.mDealer.mShowCard < 3)
				mGR.mDealer.mShowCard = 3;
			else if(mGR.mDealer.mShowCard < 5)
				mGR.mDealer.mShowCard ++;
			else
			{
				mGR.mPoker.CheckGameWin();
			}
		}
	}
	void gameLogic()
	{
		mGR.mDealer.GameCounter++;
		if(mGR.mDealer.GameCounter >= 40 && mGR.mDealer.mBatChance!= CardRules.ID)
		{
			if(mGR.mCard[mGR.mDealer.mBatChance].mState > CardRules.FOLD)
			{
				mGR.mCard[mGR.mDealer.mBatChance].mState = mGR.mPoker.CheckState((byte)mGR.mDealer.mBatChance);
				performAction();
			}
			for(int i = 0;i<5;i++)
			{
				mGR.mDealer.mBatChance++;
				mGR.mDealer.mBatChance = mGR.mDealer.mBatChance%5;
				mGR.mDealer.GameCounter = 0;
				if(mGR.mCard[mGR.mDealer.mBatChance].mState > CardRules.FOLD || mGR.mDealer.mBatChance == CardRules.ID)
				{
					break;
				}
			}
			int k =0;
			for(int i = 0;i<mGR.mCard.length;i++)
			{
				if(mGR.mCard[i].mState > CardRules.FOLD)
				{
					k++;
				}
			}
			if(k==1)
			{
				mGR.mPoker.CheckGameWin();
			}
			
		}
	}
	
	void Draw_GamePlay(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_GameBG, 0, 0);
		DrawTexture(gl, mGR.mTex_Pot, 	-.88f, -.5f);
		int total = 0;
		for(int i=0;i<mGR.mCard.length;i++)
			total+=mGR.mCard[i].mBat;
		drawNumber(gl, total, -.94f, -.55f, 1);
		if(CardRules.GameScreen == CardRules.GAMEPLAY)
			 DrawTexture(gl, mGR.mTex_Pause, 0.88f, -.5f);
		
		
		//for(int i =0;i<graffiasMaker.mCard.length;i++)
		if(mGR.AniCounter<10)
		{
			mGR.mCard[(mGR.AniCounter)%mGR.mCard.length].update(mGR.AniCounter<mGR.mCard.length);
			if ((mGR.AniCounter<mGR.mCard.length && (Math.abs(mGR.mCard[(mGR.AniCounter)%mGR.mCard.length].y)>.28f))||
					mGR.mCard[(mGR.AniCounter)%mGR.mCard.length].mState == CardRules.GAMEST){
				mGR.AniCounter ++;
			}
			else if(Math.abs(mGR.mCard[(mGR.AniCounter)%mGR.mCard.length].y1)>.28f)
			{
				mGR.mCard[(mGR.AniCounter)%mGR.mCard.length].vy = mGR.mCard[(mGR.AniCounter)%mGR.mCard.length].vx =0;
				mGR.AniCounter ++;
			}
			if(mGR.AniCounter == 9)
			{
				CardRules.playStop();
			}
		}
		else if(CardRules.GameScreen != CardRules.GAMEWIN)
			gameLogic();
		if(mGR.AniCounter>=10 && CardRules.GameScreen != CardRules.GAMEWIN){
			if(mGR.mDealer.mNewStart<2)
				DrawTexture(gl, mGR.mTex_BB[0], mGR.mCard[mGR.mDealer.mNewStart].x+.08f, mGR.mCard[mGR.mDealer.mNewStart].y-.15f);
			else
				DrawTexture(gl, mGR.mTex_BB[0], mGR.mCard[mGR.mDealer.mNewStart].x+.08f, mGR.mCard[mGR.mDealer.mNewStart].y+.15f);
			DrawPopUp(gl, mGR.mDealer.mBatChance);
		}
		for(int i =0;i<mGR.mCard.length;i++)
		{
			if(CardRules.GameScreen == CardRules.GAMEWIN && mGR.mCard[i].mState == CardRules.SHOWCASE)
			{
				DrawTexture(gl, mGR.mTex_Card[mGR.cardShuffle[i*2+0]], mGR.mCard[i].x, mGR.mCard[i].y);
				DrawTexture(gl, mGR.mTex_Card[mGR.cardShuffle[i*2+1]], mGR.mCard[i].x1, mGR.mCard[i].y1);
			}
			if(mGR.mCard[i].mState > CardRules.FOLD)
			{
				if(CardRules.ID == i || CardRules.GameScreen == CardRules.GAMEWIN)
				{
					DrawTexture(gl, mGR.mTex_Card[mGR.cardShuffle[i*2+0]], mGR.mCard[i].x, mGR.mCard[i].y);
					DrawTexture(gl, mGR.mTex_Card[mGR.cardShuffle[i*2+1]], mGR.mCard[i].x1, mGR.mCard[i].y1);
				}
				else
				{
					DrawTexture(gl, mGR.mTex_BackCard, mGR.mCard[i].x, mGR.mCard[i].y);
					DrawTexture(gl, mGR.mTex_BackCard, mGR.mCard[i].x1, mGR.mCard[i].y1);
				}
				if(CardRules.GameScreen == CardRules.GAMEWIN)
				{
					if(mGR.mCard[i].mState > CardRules.FOLD)
					{
						for(int m=0;m<mGR.mPoker.correct.length;m++)
						{
							if(mGR.mPoker.correct[m] == mGR.cardShuffle[i*2+0])
							{
								DrawTexture(gl, mGR.mTex_Card[mGR.cardShuffle[i*2+0]], mGR.mCard[i].x, mGR.mCard[i].y);
								DrawTexture(gl, mGR.mTex_WinCrd[Counter2%2], mGR.mCard[i].x, mGR.mCard[i].y);
							}
							if(mGR.mPoker.correct[m] == mGR.cardShuffle[i*2+1])
							{
								DrawTexture(gl, mGR.mTex_Card[mGR.cardShuffle[i*2+1]], mGR.mCard[i].x1, mGR.mCard[i].y1);
								DrawTexture(gl, mGR.mTex_WinCrd[Counter2%2], mGR.mCard[i].x1, mGR.mCard[i].y1);
							}
						}
					}
				}
			}
			if((mGR.mCard[i].mState >= CardRules.FOLD && mGR.AniCounter>=10 && CardRules.GameScreen != CardRules.GAMEWIN)
					|| (CardRules.GameScreen == CardRules.GAMEWIN && mGR.mCard[i].mState > CardRules.FOLD)){
				if(i == mGR.mDealer.mBatChance)
				{
					if(i<2)
						DrawTexture(gl, mGR.mTex_ChaalBack[0], mGR.mCard[i].x, mGR.mCard[i].y-.15f);
					else
						DrawTexture(gl, mGR.mTex_ChaalBack[0], mGR.mCard[i].x, mGR.mCard[i].y+.15f);
				}
				else
				{
					if(i<2)
						DrawTexture(gl, mGR.mTex_ChaalBack[1], mGR.mCard[i].x, mGR.mCard[i].y-.15f);
					else
						DrawTexture(gl, mGR.mTex_ChaalBack[1], mGR.mCard[i].x, mGR.mCard[i].y+.15f);
				}
				if(i<2)
					drawNumber(gl, mGR.mCard[i].mBat, mGR.mCard[i].x-.03f, mGR.mCard[i].y-.15f,0);
				else
					drawNumber(gl, mGR.mCard[i].mBat, mGR.mCard[i].x-.03f, mGR.mCard[i].y+.15f,0);
			}
			if(mGR.mCard[i].mState != CardRules.GAMEST)
			{
				switch (i) {
				case 0:
					DrawTexture(gl, mGR.mTex_Plyar[0], -.37f, .6f);
					break;
				case 1:
					DrawTexture(gl, mGR.mTex_Plyar[1], 0.36f, .6f);
					break;
				case 2:
					DrawTexture(gl, mGR.mTex_Plyar[2], 0.37f,-.6f);
					break;
				case 3:
					DrawTexture(gl, mGR.mTex_Plyar[3], -.0f,-.6f);
					break;
				case 4:
					DrawTexture(gl, mGR.mTex_Plyar[4], -.38f,-.6f);
					break;
					}
			}
		}
		if(mGR.AniCounter>=10 && CardRules.GameScreen != CardRules.GAMEWIN){
			DrawPopUp(gl, mGR.mDealer.mBatChance);
		}
		if(mGR.AniCounter>=10){
			if(mGR.mCard[0].mTotalCoin > 0){
				if(mGR.mCard[0].mBat>0)
					DrawTexture(gl, mGR.mTex_Chip[0], -.26f, .30f);
				if(mGR.mCard[0].mBat>10)
					DrawTexture(gl, mGR.mTex_Chip[1], -.29f, .29f);
				if(mGR.mCard[0].mBat>20)
					DrawTexture(gl, mGR.mTex_Chip[2], -.29f, .32f);
				if(mGR.mCard[0].mBat>30)
					DrawTexture(gl, mGR.mTex_Chip[3], -.26f, .33f);
			}
			if(mGR.mCard[1].mTotalCoin > 0){
				if(mGR.mCard[1].mBat>0 )
					DrawTexture(gl, mGR.mTex_Chip[1], +.26f, .31f);
				if(mGR.mCard[1].mBat>10)
					DrawTexture(gl, mGR.mTex_Chip[2], +.24f, .34f);
				if(mGR.mCard[1].mBat>20)
					DrawTexture(gl, mGR.mTex_Chip[3], +.29f, .33f);
				if(mGR.mCard[1].mBat>30)
					DrawTexture(gl, mGR.mTex_Chip[0], +.27f, .34f);
				
				
			}
			if(mGR.mCard[2].mTotalCoin > 0){
				if(mGR.mCard[2].mBat>0)
					DrawTexture(gl, mGR.mTex_Chip[3], 0.28f,-.28f);
				if(mGR.mCard[2].mBat>10)
					DrawTexture(gl, mGR.mTex_Chip[2], 0.29f,-.29f);
				if(mGR.mCard[2].mBat>20)
					DrawTexture(gl, mGR.mTex_Chip[1], 0.23f,-.30f);
				if(mGR.mCard[2].mBat>30)
					DrawTexture(gl, mGR.mTex_Chip[0], 0.26f,-.31f);
				
			}
			if(mGR.mCard[3].mTotalCoin > 0){
				if(mGR.mCard[3].mBat>0)
					DrawTexture(gl, mGR.mTex_Chip[2], -.14f,-.25f);
				if(mGR.mCard[3].mBat>10)
					DrawTexture(gl, mGR.mTex_Chip[3], -.15f,-.29f);
				if(mGR.mCard[3].mBat>20)
					DrawTexture(gl, mGR.mTex_Chip[1], -.16f,-.27f);
				if(mGR.mCard[3].mBat>40)
					DrawTexture(gl, mGR.mTex_Chip[0], -.12f,-.28f);
				
			}
			if(mGR.mCard[4].mTotalCoin > 0){
				if(mGR.mCard[4].mBat>0)
					DrawTexture(gl, mGR.mTex_Chip[1], -.27f,-.34f);
				if(mGR.mCard[4].mBat>10)
					DrawTexture(gl, mGR.mTex_Chip[2], -.28f,-.36f);
				if(mGR.mCard[4].mBat>20)
					DrawTexture(gl, mGR.mTex_Chip[3], -.29f,-.32f);
				if(mGR.mCard[4].mBat>30)
					DrawTexture(gl, mGR.mTex_Chip[0], -.30f,-.34f);
				
			}
		}
		for(int i =0;i<mGR.mDealer.mShowCard;i++)
		{
			DrawTexture(gl, mGR.mTex_Card[mGR.cardShuffle[i+10]], -.2f+i*.1f, 0);
			for(int m = 0; m<mGR.mPoker.correct.length && (CardRules.GameScreen == CardRules.GAMEWIN); m++)
			{
				if(mGR.mPoker.correct[m] == mGR.cardShuffle[i+10])
					DrawTexture(gl, mGR.mTex_WinCrd[Counter2%2],-.2f+i*.1f, 0);
			}
		}
			

		if(CardRules.GameScreen == CardRules.GAMEWIN)
		{
			DrawTextureTS(gl, mGR.mTex_SplCount[Counter2%2],-.0f,-.8f,.7f,1);
		}
		else if(mGR.mDealer.mBatChance == CardRules.ID && CardRules.GameScreen != CardRules.GAMERAISE){
			DrawTexture(gl, mGR.mTex_Button[2][0],-.86f,-.85f);
			if((mGR.mCard[CardRules.ID].mTotalCoin-mGR.mCard[CardRules.ID].mBat) > mGR.mDealer.mCurrentBat)
				DrawTexture(gl, mGR.mTex_Button[3][0],-.56f,-.85f);
			else
				DrawTextureTS(gl, mGR.mTex_Button[3][1],-.56f,-.85f,.3f,1);
			
			if(mGR.mDealer.mBigBat == mGR.mCard[CardRules.ID].mBat)
			{
				DrawTexture(gl, mGR.mTex_Button[1][0],0.56f,-.85f);
				DrawTextureTS(gl, mGR.mTex_Button[0][1],0.86f,-.85f,.4f,1);
			}
			else
			{
				DrawTextureTS(gl, mGR.mTex_Button[1][1],0.56f,-.85f,.4f,1);
				DrawTexture(gl, mGR.mTex_Button[0][0],0.86f,-.85f);
			}
			switch (mGR.mSel) {
			case 1:
				DrawTexture(gl, mGR.mTex_Button[0][1],0.86f,-.85f);
				break;
			case 2:
				DrawTexture(gl, mGR.mTex_Button[1][1],0.56f,-.85f);
				break;
			case 3:
				DrawTexture(gl, mGR.mTex_Button[2][1],-.86f,-.85f);
				break;
			case 4:
				DrawTexture(gl, mGR.mTex_Button[3][1],-.56f,-.85f);
				break;
				}
		}
		else
		{
			DrawTextureTS(gl, mGR.mTex_Button[2][1],-.86f,-.85f,.3f,1);
			DrawTextureTS(gl, mGR.mTex_Button[3][1],-.56f,-.85f,.3f,1);
			DrawTextureTS(gl, mGR.mTex_Button[1][1],0.56f,-.85f,.3f,1);
			DrawTextureTS(gl, mGR.mTex_Button[0][1],0.86f,-.85f,.3f,1);
		}
		mGR.ShowCurrent --;
		if(mGR.ShowCurrent > 0)
		{
			DrawPopUp(gl, mGR.cardNo);
		}
		if(CardRules.GameScreen == CardRules.GAMERAISE)
		{
			DrawTexture(gl, mGR.mTex_RaiseBox,0.0f,-.78f);
			DrawTexture(gl, mGR.mTex_Cancel  ,0.4f,-.60f);
			DrawTexture(gl, mGR.mTex_Slider  ,mGR.mSliderX,-.88f);
			drawNumber(gl, (int)(((mGR.mCard[CardRules.ID].mTotalCoin-mGR.mCard[CardRules.ID].mBat)/.6f)*(mGR.mSliderX+.3f)), -.05f, -.69f,1);
//			DrawTexture(gl, graffiasMaker.mTex_Cancel  ,-.27f,-.69f);
		}
		if (CardRules.GAMEWIN == CardRules.GameScreen && mGR.mPoker.st[0] >= 0 && mGR.mPoker.st[0] < 10) {
			DrawTextureS(gl, mGR.mTex_Condition[mGR.mPoker.st[0]], 0.0f, 0.2f,ex);
			if (ex > 1.1f)
				evx = -0.01f;
			if (ex < 0.9f)
				evx = 0.01f;
			ex += evx;
		}
	}

	float ex = 1;
	float evx = .01f;
	
	boolean Handle_Game(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CardRules.GameScreen == CardRules.GAMEWIN)
		{
			if(event.getAction() == MotionEvent.ACTION_UP)
			{
				if(CircRectsOverlap(0.0f,-.8f, mGR.mTex_Countinue[0].width()*.6f,mGR.mTex_Countinue[0].Height()*.6f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
					mGR.gameReset();
			}
				return true;
		}
		if(event.getAction() == MotionEvent.ACTION_UP){
			if(CircRectsOverlap(-.45f, .6f, mGR.mTex_Button[0][0].width()*.4f,mGR.mTex_Button[0][0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.ShowCurrent =100;
				mGR.cardNo = 0;
			}
			if(CircRectsOverlap(+.45f, .6f, mGR.mTex_Button[0][0].width()*.4f,mGR.mTex_Button[0][0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.ShowCurrent =100;
				mGR.cardNo = 1;
			}
			if(CircRectsOverlap(0.45f,-.6f, mGR.mTex_Button[0][0].width()*.4f,mGR.mTex_Button[0][0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.ShowCurrent =100;
				mGR.cardNo = 2;
			}
			if(CircRectsOverlap(0.00f,-.6f, mGR.mTex_Button[0][0].width()*.4f,mGR.mTex_Button[0][0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.ShowCurrent =100;
				mGR.cardNo = 3;
			}
			if(CircRectsOverlap(-.45f,-.6f, mGR.mTex_Button[0][0].width()*.4f,mGR.mTex_Button[0][0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.ShowCurrent =100;
				mGR.cardNo = 4;
			}
			if(CircRectsOverlap(0.88f, -.5f, mGR.mTex_Pause.width()*.4f,mGR.mTex_Pause.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				CardRules.GameScreen = CardRules.GAMEPAUSE;
				CardRules.playStop();
			}
			mGR.mSel = 0;
		}
		if(mGR.mDealer.mBatChance != CardRules.ID)
			return true;
		if(CircRectsOverlap(0.86f,-.85f, mGR.mTex_Button[0][0].width()*.4f,mGR.mTex_Button[0][0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(mGR.mDealer.mBigBat != mGR.mCard[CardRules.ID].mBat)
				mGR.mSel = 1;
		}
		if(CircRectsOverlap(0.56f,-.85f, mGR.mTex_Button[0][0].width()*.4f,mGR.mTex_Button[0][0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(mGR.mDealer.mBigBat == mGR.mCard[CardRules.ID].mBat)
				mGR.mSel = 2;
		}
		if(CircRectsOverlap(-.86f,-.85f, mGR.mTex_Button[0][0].width()*.4f,mGR.mTex_Button[0][0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;
		}
		if(CircRectsOverlap(-.56f,-.85f, mGR.mTex_Button[0][0].width()*.4f,mGR.mTex_Button[0][0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4;
		}
		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch (mGR.mSel){
			case 1:case 2:
				Raise(0);//Check
				for(int i = mGR.mDealer.mBatChance;i<5;i++){
					mGR.mDealer.mBatChance++;
					mGR.mDealer.mBatChance = mGR.mDealer.mBatChance%5;
					mGR.mDealer.GameCounter = 0;
					if(mGR.mCard[mGR.mDealer.mBatChance].mState > CardRules.FOLD)
						break;
					if(mGR.mDealer.mBatChance == CardRules.ID){
						mGR.mPoker.CheckGameWin();
						break;
					}
				}break;
			case 3:
				mGR.mCard[CardRules.ID].mState = CardRules.FOLD;//Fold
				mGR.mDealer.mShowCard =5;
				mGR.mPoker.CheckGameWin();
				break;
			case 4:
				if((mGR.mCard[CardRules.ID].mTotalCoin-mGR.mCard[CardRules.ID].mBat) > mGR.mDealer.mCurrentBat){
					CardRules.GameScreen = CardRules.GAMERAISE;
					mGR.mSliderX = ((mGR.mDealer.mCurrentBat*.6f)/(mGR.mCard[CardRules.ID].mTotalCoin-mGR.mCard[CardRules.ID].mBat))-.3f;
				}break;
			}
			mGR.mSel =0;
		}
		return true;
	}
	
	
	
	boolean Handle_Raise(MotionEvent event)
	{
		mGR.mSel =0;
		if(CircRectsOverlap(0.4f,-.60f, mGR.mTex_Cancel.width()*.45f,mGR.mTex_Cancel.Height()*.45f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;//Cencel
		}
		if(CircRectsOverlap(mGR.mSliderX,-.88f, mGR.mTex_Slider.width()*.4f,mGR.mTex_Slider.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;//RaiseBar
			float min = ((mGR.mDealer.mCurrentBat*.6f)/(mGR.mCard[CardRules.ID].mTotalCoin-mGR.mCard[CardRules.ID].mBat))-.3f;
			if(screen2worldX(event.getX())>min && screen2worldX(event.getX())<.3)
			{
				mGR.mSliderX = screen2worldX(event.getX()); 
			}
			if(screen2worldX(event.getX())>.3)
			{
				mGR.mSliderX = .3f;
			}
		}
		if(CircRectsOverlap(-.27f,-.65f, mGR.mTex_Cancel.width()*.4f,mGR.mTex_Cancel.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;//Ok
		}
		
		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			
			switch (mGR.mSel){
			case 1:
				CardRules.GameScreen = CardRules.GAMEPLAY;
				//Cancle
				break;
			case 2:
				//
				break;
			case 3:
				CardRules.GameScreen = CardRules.GAMEPLAY;
				int raise = (int)(((mGR.mCard[CardRules.ID].mTotalCoin-mGR.mCard[CardRules.ID].mBat)/.6f)*(mGR.mSliderX+.3f));
				//Raise(raise);
				if(raise>0){
					mGR.mCard[CardRules.ID].mBat +=raise;
					boolean temp = CardRules.setValue;
					CardRules.setValue = false;
					Raise(0);
					CardRules.setValue = temp;
				}
				for(int i = mGR.mDealer.mBatChance;i<5;i++)
				{
					mGR.mDealer.mBatChance++;
					mGR.mDealer.mBatChance = mGR.mDealer.mBatChance%5;
					mGR.mDealer.GameCounter = 0;
					if(mGR.mCard[mGR.mDealer.mBatChance].mState > CardRules.FOLD)
					{
						break;
					}
					if(mGR.mDealer.mBatChance == CardRules.ID)
					{
						mGR.mPoker.CheckGameWin();
						break;
					}
				}
				//Ok
				break;
				}
			mGR.mSel =0;
		}
		return true;
	}
	void DrawTextureTS(GL10 gl,SimplePlane Tex,float x,float y,float t,float s)
	{
		Tex.drawTransprentScal(gl, x, y,s,t);
	}
	
	
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	
	void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle)
	{
		Tex.drawRotet(gl, 0,0,angle, x, y);
	}
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float scal)
	{
		Tex.drawScal(gl, x, y,scal,scal);
	}
	void DrawFlip(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawFilp(gl, x, y);
	}
	boolean CircRectsOverlap(double CRX,  double CRY,double CRDX,double CRDY ,double centerX,double centerY, double radius)
    {
        if ((Math.abs(centerX - CRX) <= (CRDX + radius)) && (Math.abs(centerY - CRY) <= (CRDY + radius)))
           return true;
        return false ;

    }
	float screen2worldX(float a)
	{
		float c = ((a / CardRules.ScreenWidth)- 0.5f)*2;
		return c;
	}
	float screen2worldY(float a)
	{
		float c = ((a / CardRules.ScreenHieght)- 0.5f)*(-2);
		return c;
	}
	boolean Rect2RectIntersection(float ax,float ay,float adx,float ady,float bx,float by,float bdx,float bdy)
	{
		if( ax+adx > bx  && ay+ady > by && bx+bdx > ax && by+bdy> ay)
		{
			return true;
		}
		return false;
	}
	boolean CirCir(double cx1,double cy1, double r1,double cx2,double cy2, double r2)
    {
		float bVectMag = (float) Math.sqrt(((cx1-cx2)*(cx1-cx2)) + ((cy1-cy2)*(cy1-cy2)));
		if (bVectMag<(r1+r2))
           return true;
        return false ;

    }
	double GetAngle(double d,double e)
	{
	  if(d==0)
		  return e>=0 ? Math.PI/2 : -Math.PI/2;
	  else if (d > 0)
		  return Math.atan(e/d);
	  else
		  return Math.atan(e/d) + Math.PI;
	}
	void drawNumber(GL10 gl,int no,float x, float y,int type)
	{
		float dx = mGR.mTex_Font[type][0].width()*.6f;
		 String strs = ""+no;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font[type] .length)
				mGR.mTex_Font[type][k].drawPos(gl,x+i*dx,y);
		}
	}
	
	
//	void RateUs()
//	{
//		Intent mIntent = new Intent(Intent.ACTION_VIEW);
////		mIntent.setData(Uri.parse(CardRules.LINK+getClass().getPackage().getName()));
//		mIntent.setData(Uri.parse(CardRules.Market));
//	    graffiasMaker.mContext.startActivity(mIntent);
//	}
//	void MoreGame()
//	{
//		Intent mIntent = new Intent(Intent.ACTION_VIEW);
//		mIntent.setData(Uri.parse(CardRules.MARKET));
//		graffiasMaker.mContext.startActivity(mIntent);
//	}
//	void share2friend()
//	{
//		Intent i = new Intent(Intent.ACTION_SEND);
//		i.setType("text/plain");
//		i.putExtra(Intent.EXTRA_SUBJECT,"Roking new Game '"+graffiasMaker.mContext.getResources().getString(R.string.app_name)+"'");
//		//i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+CardRules.PUBLICLINK+getClass().getPackage().getName());
//		i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+CardRules.SHARELINK);
//		try {
//		    graffiasMaker.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
//		} catch (android.content.ActivityNotFoundException ex) {
//		    //Toast.makeText(GraffiasMaker.mStartActivity, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//		}
//	}
	
}
