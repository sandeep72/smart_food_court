package com.be.b.beproject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;







import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CustomerFoodOrderPlacementFragment extends Fragment implements OnItemClickListener{
	int sanvai=CustomerFoodOrderAdapterClass.saawan;
	List<CustomerFoodOrderPlacementItemFieldClass> list = new ArrayList<CustomerFoodOrderPlacementItemFieldClass>();
	EditText number;
	ImageView imageView;
	Context context=getActivity();
//	static List<String> SelectedItemName,SelectedItemQuantity,SelectedItemPrice;
	CustomerFoodOrderPlacementItemFieldClass clickToast;
	TextView t1,t2,t3;
	Dialog dialog;
	ImageLoader imgLoader ;
	@Override  
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
	
//		HashMap<String,List<String>> listData =CustomerFoodOrderCategoryProcessClass.listDataItemName;
		List<String> ItemName=new ArrayList<String>();
		List<String> ItemPrice=new ArrayList<String>();
		List<String> ItemDescription=new ArrayList<String>();
		List<String> ItemImageURL=new ArrayList<String>();
//		SelectedItemName=new ArrayList<String>();
//		SelectedItemPrice=new ArrayList<String>();
//		SelectedItemQuantity=new ArrayList<String>();
		 imgLoader = new ImageLoader(getActivity());
		list.clear();

		ItemName=CustomerFoodOrderCategoryProcessClass.listDataItemName.get(""+sanvai);
		String itemName[]=ItemName.toArray(new String[ItemName.size()]);
		

		ItemPrice=CustomerFoodOrderCategoryProcessClass.listDataPrice.get(""+sanvai);
		String itemPrice[]=ItemPrice.toArray(new String[ItemPrice.size()]);
		

		ItemDescription=CustomerFoodOrderCategoryProcessClass.listDataDescription.get(""+sanvai);
		String itemDescription[]=ItemDescription.toArray(new String[ItemDescription.size()]);
		
		ItemImageURL=CustomerFoodOrderCategoryProcessClass.listImage.get(""+sanvai);
		String itemImageURL[]=ItemImageURL.toArray(new String[ItemImageURL.size()]);
		
		
		for(int i=0;i<itemName.length;i++){
			list.add(new CustomerFoodOrderPlacementItemFieldClass(itemName[i], itemPrice[i], itemDescription[i],"00",itemImageURL[i]));
			
		}
		
		View view = inflater.inflate(R.layout.customerfoodorderplacementfragment, container, false);
		final ListView listView = (ListView)view.findViewById(R.id.listView1);
		
		
		
		
		ArrayAdapter<CustomerFoodOrderPlacementItemFieldClass> adapter = new CustomListViewAdapter();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				dialog =new Dialog(getActivity());
				dialog.setContentView(R.layout.quantity);
				
				dialog.setTitle("Select Quantity...");
				
				number=(EditText) dialog.findViewById(R.id.editText1);
				number.setText("1");
				
				Button add=(Button) dialog.findViewById(R.id.button1);
				Button minus=(Button) dialog.findViewById(R.id.button2);
				Button ok=(Button) dialog.findViewById(R.id.button3);
				
				clickToast=list.get(position);
				
				dialog.show();
				ok.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
					    Log.i("value",number.getText().toString());
					    int val=Integer.parseInt(number.getText().toString());
					    if(val>0 && !(number.getText().toString().trim().equals(""))){
					    CustomerFoodOrderTabPage.SelectedItemName.add(clickToast.getItemName());
//					    Log.i("add",clickToast.getItemName());
					    CustomerFoodOrderTabPage.SelectedItemPrice.add(clickToast.getItemPrice());
					    CustomerFoodOrderTabPage.SelectedItemQuantity.add(number.getText().toString());
						dialog.dismiss();
					}
					}
				});
				
				
				
				
				
				add.setOnClickListener( new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String qtyStr=number.getText().toString();
						int cnt=Integer.parseInt(qtyStr.trim());
						int sum=cnt+1;
						if(sum<=9)
						{
							number.setText("0"+sum);
						}
						else
						{
							number.setText(""+sum);
						}
					}
				});
				
				
				minus.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						
						String qtyStr=number.getText().toString();
						int cnt=Integer.parseInt(qtyStr.trim());
						if(cnt>01)
						{
							int sum=cnt-1;
							if(sum<=9)
							{
								number.setText("0"+sum);
							}
							else
							{
								number.setText(""+sum);
							}
						}
					}
				});
	
	}


});
		

		return view;

}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}
	
	
	class CustomListViewAdapter extends ArrayAdapter<CustomerFoodOrderPlacementItemFieldClass> {
		public CustomListViewAdapter() {
			super(getActivity(), R.layout.row_viewmenu,list);
		}
		  
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView=convertView;
			if(itemView==null)
			{
				itemView=getActivity().getLayoutInflater().inflate(R.layout.row_viewmenu, null);
				
			}
			CustomerFoodOrderPlacementItemFieldClass obj=list.get(position);
			
			t1=(TextView) itemView.findViewById(R.id.customerMenuItemNameTextView);
			t1.setText(obj.getItemName());
			t2=(TextView) itemView.findViewById(R.id.customerMenuItemPriceTextView);
			t2.setText(obj.getItemPrice());
			t3=(TextView) itemView.findViewById(R.id.customerMenuItemDescriptionTextView);
			t3.setText(obj.getItemDescription());
			
			imageView=(ImageView) itemView.findViewById(R.id.imageView1);
			//int loader = R.drawable.ic_launcher;
			
			String image_url = RestaurantConfig.URL_ITEMS+"/"+obj.getImageURL();
			Log.i("image path",image_url);
			
			imgLoader.DisplayImage(image_url,imageView);
			
			//imageView.setEnabled(false);
			//Bitmap bitmap = DownloadImage(image_url);
		       // ImageView img = (ImageView) findViewById(R.id.img);
			//imageView.setImageBitmap(bitmap);
			
			
			
			
			return itemView;
			
		}
		
	    
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	
	
	
	
	
}//end of parent class
