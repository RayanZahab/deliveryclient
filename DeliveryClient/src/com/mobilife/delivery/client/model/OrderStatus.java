package com.mobilife.delivery.client.model;

import com.mobilife.delivery.client.R;


public enum OrderStatus {
	Opened("open_status"), Prepared("prepared_status"), Closed("closed_status"), Cancelled("cancelled_status"), Assigned("assigned_orders");
	
	private String i18Key;
	private OrderStatus(String i18Key){
		this.i18Key= i18Key;
	}
	public String getI18Key() {
		return i18Key;
	}
	
	public int getId() {
		switch (this) {
		case Opened:
			return R.string.open_status;
		case Prepared:
			return R.string.prepared_status;
		case Closed:
			return R.string.delivered_status;
		/*case Cancelled:
			return R.string.canceled_orders;
		case Assigned:
			return R.string.assigned_orders;*/
		default:
			return 0;
		}

	}
}
