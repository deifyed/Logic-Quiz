	package com.cognitiveadventure.logicquiz;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/*
 * TODO:
 * 	* Design
 * 	* Scoreboard?
 */
public class LogikkQuizActivity extends Activity implements OnClickListener {
	static final String PREFS = "logprefs";
	static final String KEY_PREFIX = "pos";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        newGame();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    }
    
    public void onClick(View v)
    {
    	switch(v.getId())
    	{
    		case R.id.btnNewGame:
    			newGame();
    	}
    }
    
    /*
     *		Custom methods/functions
     */
    
    private void newGame()
    {
    	Intent i = new Intent(this, GameActivity.class);
    	
    	startActivity(i);
    }
}