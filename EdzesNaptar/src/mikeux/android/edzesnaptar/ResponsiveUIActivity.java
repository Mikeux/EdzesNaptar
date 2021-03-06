package mikeux.android.edzesnaptar;

import mikeux.android.edzesnaptar.fragments.EdzesFragment;
import mikeux.android.edzesnaptar.fragments.EdzesMenuFragment;
import mikeux.android.edzesnaptar.util.u;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class ResponsiveUIActivity extends SlidingFragmentActivity {
	//http://boroniatechnologies.com/installing-slidingmenu-android-library-and-example/
	private Fragment mContent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setTitle("ResponsiveUIActivity");
		setContentView(R.layout.responsive_content_frame);
		
		//Utils inicializálása
		new u(this);	
		
		// check if the content frame contains the menu frame
		if (findViewById(R.id.menu_frame) == null) {
			setBehindContentView(R.layout.menu_frame);
			getSlidingMenu().setSlidingEnabled(true);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			// show home as up so we can toggle
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().hide();
		} else {
			// add a dummy view
			View v = new View(this);
			setBehindContentView(v);
			getSlidingMenu().setSlidingEnabled(false);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}

		// set the Above View Fragment
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new EdzesFragment();
		
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent)
		.commit();

		// set the Behind View Fragment
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new EdzesMenuFragment())
		.commit();

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindScrollScale(0.25f);
		sm.setFadeDegree(0.25f);

		// show the explanation dialog
		/*if (savedInstanceState == null)
			new AlertDialog.Builder(this)
			.setTitle("Mi ez?")
			.setMessage("Kezdeti üzenet!")
			.show();*/
	}

	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
		}
		return super.onOptionsItemSelected(item);
	}*/

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}
	
	public void switchContent(final Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			public void run() {
				getSlidingMenu().showContent();
			}
		}, 50);
	}

	@Override
	public void onBackPressed() {		
		//Log.e("Mikeux",this.mContent.getClass().getName());
		if(this.mContent.getClass().getName().equals("mikeux.android.edzesnaptar.fragments.EdzesNapFragment")){
			this.switchContent(new EdzesFragment());
		}else{
			this.toggle();
			/*getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.menu_frame, new EdzesMenuFragment())
			.commit();*/
			/*
			new AlertDialog.Builder(this)
	    	.setMessage("Biztosan kilép a programból?").setTitle("Kilépés")
	    	.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	                System.exit(0);
	            }
	        })
	        .setNegativeButton("Nem", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {}
	        }).show();*/
		}
	}

}