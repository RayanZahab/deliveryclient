package info.androidhive.tabsswipe;

public class Shop {
	private Integer id;
	private String name;
	private String description;
	private int is_available;
	private Business business;

	public Shop(Integer id) {
		this.setId(id);
	}

	public Shop(Integer id, String name, String description, int is_available,
			Business business) {
		this.setId(id);
		this.setName(name);
		this.setDescription(description);
		this.setIs_available(is_available);
		this.setBusiness(business);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIs_available() {
		return is_available;
	}

	public void setIs_available(int is_available) {
		this.is_available = is_available;
	}

	public Business getBusiness_id() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

}
