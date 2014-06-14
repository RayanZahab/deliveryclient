package info.androidhive.tabsswipe;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class OrdersFragment extends Fragment {

    private List<String> countries = new ArrayList<String>();
    private List<String> categories = new ArrayList<String>();
    private List<FragmentTransaction> mBackStackList = new ArrayList<FragmentTransaction>();
    int depth =0;
    private List<String> sequence =new ArrayList<String>();
   
   
    @Override 
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
		sequence.add("country");
		sequence.add("city");
		sequence.add("area");
        return inflater.inflate(R.layout.fragment_orders, container, false);  
       
    }  
       
       
    @SuppressWarnings("unchecked")
	@Override 
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState);  
           
        List<String> mylist = getList(sequence.get(0));
        final ListView listView = (ListView) getActivity().findViewById(R.id.list);  
        listView.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, mylist));  
           
        listView.setOnItemClickListener(new OnItemClickListener() {  
   
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  
        			depth++;
                 List<String> mylist = getList(sequence.get(depth));
                 listView.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, mylist)); 
            }  
        }); 
           
    }  
    
    public List<String> getList(String type)
    {
    	List<String> mylist = new ArrayList<String>();
    	if(type.equals("country"))
    	{
    		for(int i=0, count=20; i<count; i++){  
    			mylist.add("Country " + i);  
            }  
               
    	}
    	else if (type.equals("city"))
    	{
    		for(int i=0, count=20; i<count; i++){  
    			mylist.add("City " + i);  
            } 
    	}
    	return mylist;
    }
       
    /** 
     *  
     * @param msg 
     */ 
    private void showTost(String msg){  
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();  
    }  
   
} 