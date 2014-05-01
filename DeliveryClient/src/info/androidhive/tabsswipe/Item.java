package info.androidhive.tabsswipe;

import android.graphics.Bitmap;

public class Item {
	//text, text+image, product(text+input number+price), order(text+number+number), address(radiogroupbutton), 
	//cart(text+inputnumb+int total+intprice)
	
	private String type;
	//txt, txtImg, product,order,cart,address 

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	
}
