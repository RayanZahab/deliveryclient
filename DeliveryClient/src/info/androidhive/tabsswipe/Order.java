package info.androidhive.tabsswipe;

import java.util.ArrayList;

public class Order {
	private int id;
	private int customer_id;
	private String status;
	private double total;
	private int count;
	private int address_id;
	private Customer customer;
	private Address address;
	private ArrayList<OrderItem> orderItems;
	private boolean cancel = false,newCustomer;
	private String cancelReason;
	private User Preparer,Delivery;
	private String date, note;
	
	public Order(){}
	public Order(int id, int customer_id, String status, double total,
			int count, int address_id) {
		this.setId(id);
		this.setCustomer_id(customer_id);
		this.setStatus(status);
		this.setTotal(total);
		this.setCount(count);
		this.setAddress_id(address_id);
	}
	public Order(int id, int customer_id, String status, double total,
			int count, int address_id,ArrayList<OrderItem> orderItems) {
		this.setId(id);
		this.setCustomer_id(customer_id);
		this.setStatus(status);
		this.setTotal(total);
		this.setCount(count);
		this.setAddress_id(address_id);
		this.setOrderItems(orderItems);
	}

	public Order(int id, Customer customer, double total, int count) {
		this.id = id;
		this.customer = customer;
		this.total = total;
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getAddress_id() {
		return address_id;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}

	public String toString() {
		return this.customer.getName();
	}
	public ArrayList<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(ArrayList<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public boolean isCancel() {
		return cancel;
	}
	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public User getPreparer() {
		return Preparer;
	}
	public void setPreparer(User preparer) {
		Preparer = preparer;
	}
	public User getDelivery() {
		return Delivery;
	}
	public void setDelivery(User delivery) {
		Delivery = delivery;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isNewCustomer() {
		return newCustomer;
	}
	public void setNewCustomer(boolean newCustomer) {
		this.newCustomer = newCustomer;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public boolean equals(Order newOrder)
	{
		for(int i =0;i<newOrder.getOrderItems().size();i++)
		{
			if(!this.orderItems.get(i).equals(newOrder.getOrderItems().get(i)))
				return false;
		}
		if( this.total == newOrder.getTotal() )
			return true;
		
		return false;
	}
}
