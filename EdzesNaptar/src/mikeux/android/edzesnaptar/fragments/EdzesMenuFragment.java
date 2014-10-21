package mikeux.android.edzesnaptar.fragments;

import java.util.ArrayList;

import mikeux.android.edzesnaptar.R;
import mikeux.android.edzesnaptar.ResponsiveUIActivity;
import mikeux.android.edzesnaptar.util.EdzesMenuList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//http://www.tutorialsbuzz.com/2014/05/android-listfragment-using-baseadapter.html

public class EdzesMenuFragment extends ListFragment {
    String[] menutitles;
    TypedArray menuIcons;
    public EdzesMenuList adapter;
    
	private ArrayList<String> nevek = new ArrayList<String>();
	private ArrayList<Integer> kepek = new ArrayList<Integer>();
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_menu, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		/*TypedArray typedarr = getResources().obtainTypedArray(R.array.menu_fragment);
		
		for(int i=0;i<typedarr.length();i++) {
			nevek.add(typedarr.getResourceId(i, -1).);
		}
		
        menutitles = getResources().getStringArray(R.array.menu_fragment);
        menuIcons = getResources().obtainTypedArray(R.array.menu_icons);*/

        nevek.add("Edzések");
        nevek.add("Edzés fajták");
        nevek.add("Élelmiszerek");
        nevek.add("Statisztikák");
        nevek.add("Beállítások");      
        nevek.add("Kilépés");
        
        kepek.add(R.drawable.stopper);
        kepek.add(R.drawable.sziv);
        kepek.add(R.drawable.alma);
        kepek.add(R.drawable.kulacs);
        kepek.add(R.drawable.beallitas);
        kepek.add(R.drawable.kilepes);
        //kepek.add(-1);
        
        adapter = new EdzesMenuList(this.getActivity(), nevek, kepek);
        setListAdapter(adapter);
        
		/*String[] birds = getResources().getStringArray(R.array.menu_fragment);
		ArrayAdapter<String> menuAdapter = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1, android.R.id.text1, birds);
		setListAdapter(menuAdapter);*/
	}
	
	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = null;
		
		if(position==0) newContent = new EdzesFragment();
		else if(position==1) newContent = new EdzesFajtaFragment();
		else if(lv.getItemAtPosition(position).toString().equals("Statisztikák")) newContent = new StatisztikaFragment(); //newContent = new EdzesStatisztikaFragment();
		else if(lv.getItemAtPosition(position).toString().equals("Beállítások")) newContent = new EdzesBeallitasFragment();
		else if(lv.getItemAtPosition(position).toString().equals("Élelmiszerek")) newContent = new ElelmiszerFragment();
		else if(lv.getItemAtPosition(position).toString().equals("Kilépés")) System.exit(0);
		
		if (newContent != null)
			switchFragment(newContent);
	}
	
	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof ResponsiveUIActivity) {
			ResponsiveUIActivity ra = (ResponsiveUIActivity) getActivity();
			ra.switchContent(fragment);
		}
	}
}