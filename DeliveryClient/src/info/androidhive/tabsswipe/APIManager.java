package info.androidhive.tabsswipe;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class APIManager {

	public APIManager() {

	}

	public User getLogedInUser(String cont) {
		JSONObject jsonResponse, jsonRole;

		try {
			jsonResponse = new JSONObject(cont);
			if (!errorCheck(jsonResponse)) {
				int id = 1;// Integer.parseInt(jsonResponse.optString("id")
							// .toString());
				Log.d("rays", cont);
				int branch_id = Integer.parseInt(jsonResponse.optString(
						"branch_id").toString());
				String token = jsonResponse.optString("auth_token").toString();
				String name = jsonResponse.optString("name").toString();
				String role_str = jsonResponse.optString("roles").toString();
				jsonRole = new JSONObject(role_str);
				boolean admin = Boolean.parseBoolean(jsonRole
						.optString("admin").toString());
				boolean preparer = Boolean.parseBoolean(jsonRole.optString(
						"preparer").toString());
				boolean delivery = Boolean.parseBoolean(jsonRole.optString(
						"deliverer").toString());

				return new User(id, name, token, branch_id, admin, preparer,
						delivery);

			}
		} catch (JSONException e) {

			e.printStackTrace();

		}
		return null;

	}

	public ArrayList<Country> getCountries(String cont) {
		Log.d("rays", cont);
		JSONObject jsonResponse;
		ArrayList<Country> gridArray = new ArrayList<Country>();
		try {
			jsonResponse = new JSONObject(cont);
			if (!errorCheck(jsonResponse)) {
				int id;
				String name;
				if (jsonResponse.has("elements")) {
					JSONArray jsonMainNode = jsonResponse
							.optJSONArray("elements");
					int lengthJsonArr = jsonMainNode.length();
					for (int i = 0; i < lengthJsonArr; i++) {
						JSONObject jsonChildNode = jsonMainNode
								.getJSONObject(i);

						id = Integer.parseInt(jsonChildNode.optString("id")
								.toString());

						name = jsonChildNode.optString("name").toString();
						gridArray.add(new Country(id, name));
					}
				} else {
					id = Integer.parseInt(jsonResponse.optString("id")
							.toString());
					name = jsonResponse.optString("name").toString();
					gridArray.add(new Country(id, name));
				}
			} else {
				return gridArray;
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}

		return gridArray;

	}

	public ArrayList<City> getCitiesByCountry(String cont) {
		JSONObject jsonResponse;
		ArrayList<City> gridArray = new ArrayList<City>();
		try {
			jsonResponse = new JSONObject(cont);
			if (!errorCheck(jsonResponse)) {
				int id;
				String name;
				if (jsonResponse.has("elements")) {
					JSONArray jsonMainNode = jsonResponse
							.optJSONArray("elements");
					int lengthJsonArr = jsonMainNode.length();

					for (int i = 0; i < lengthJsonArr; i++) {
						JSONObject jsonChildNode = jsonMainNode
								.getJSONObject(i);

						id = Integer.parseInt(jsonChildNode.optString("id")
								.toString());
						name = jsonChildNode.optString("name").toString();
						gridArray.add(new City(id, name));
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return gridArray;
	}

	public ArrayList<Area> getAreasByCity(String cont) {
		JSONObject jsonResponse;
		ArrayList<Area> gridArray = new ArrayList<Area>();
		try {
			jsonResponse = new JSONObject(cont);
			if (!errorCheck(jsonResponse)) {

				int id, country_id, city_id;
				String name;
				if (jsonResponse.has("elements")) {
					JSONArray jsonMainNode = jsonResponse
							.optJSONArray("elements");
					int lengthJsonArr = jsonMainNode.length();
					for (int i = 0; i < lengthJsonArr; i++) {
						JSONObject jsonChildNode = jsonMainNode
								.getJSONObject(i);

						id = Integer.parseInt(jsonChildNode.optString("id")
								.toString());
						country_id = Integer.parseInt(jsonChildNode.optString(
								"country_id").toString());
						city_id = Integer.parseInt(jsonChildNode.optString(
								"city_id").toString());
						name = jsonChildNode.optString("name").toString();
						gridArray.add(new Area(id, city_id, country_id, name));
					}
				} else {
					id = Integer.parseInt(jsonResponse.optString("id")
							.toString());
					name = jsonResponse.optString("name").toString();
					country_id = Integer.parseInt(jsonResponse.optString(
							"country_id").toString());
					city_id = Integer.parseInt(jsonResponse
							.optString("city_id").toString());
					gridArray.add(new Area(id, city_id, country_id, name));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return gridArray;
	}

	public ArrayList<Business> getBusinesses(String cont) {
		JSONObject jsonResponse;
		ArrayList<Business> gridArray = new ArrayList<Business>();

		try {
			jsonResponse = new JSONObject(cont);
			if (!errorCheck(jsonResponse)) {
				int id;
				String name;
				if (jsonResponse.has("elements")) {
					JSONArray jsonMainNode = jsonResponse
							.optJSONArray("elements");
					int lengthJsonArr = jsonMainNode.length();

					for (int i = 0; i < lengthJsonArr; i++) {
						JSONObject jsonChildNode = jsonMainNode
								.getJSONObject(i);

						id = Integer.parseInt(jsonChildNode.optString("id")
								.toString());
						name = jsonChildNode.optString("name").toString();
						gridArray.add(new Business(id, name));
					}
				} else {
					id = Integer.parseInt(jsonResponse.optString("id")
							.toString());
					name = jsonResponse.optString("name").toString();
					gridArray.add(new Business(id, name));
				}
			} else {
				return gridArray;
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}

		return gridArray;
	}

	public ArrayList<Shop> getShopsByArea(String cont) {
		JSONObject jsonResponse;
		ArrayList<Shop> gridArray = new ArrayList<Shop>();

		try {
			jsonResponse = new JSONObject(cont);
			int id, is_available;
			String name, desc, business_str;
			Business business;
			ArrayList<Business> businesses;
			if (!errorCheck(jsonResponse)) {
				if (jsonResponse.has("elements")) {
					JSONArray jsonMainNode = jsonResponse
							.optJSONArray("elements");
					int lengthJsonArr = jsonMainNode.length();

					for (int i = 0; i < lengthJsonArr; i++) {
						JSONObject jsonChildNode = jsonMainNode
								.getJSONObject(i);

						id = Integer.parseInt(jsonChildNode.optString("id")
								.toString());
						name = jsonChildNode.optString("name").toString();
						is_available = 1;// Integer.parseInt(jsonChildNode.optString("is").toString());
						desc = jsonChildNode.optString("name").toString();

						// Getting business object
						business_str = jsonChildNode.optString("business")
								.toString();
						businesses = getBusinesses(business_str);
						business = businesses.get(0);

						gridArray.add(new Shop(id, name, desc, is_available,
								business));
					}
				} else {
					id = Integer.parseInt(jsonResponse.optString("id")
							.toString());
					name = jsonResponse.optString("name").toString();
					is_available = 1;// Integer.parseInt(jsonResponse.optString("is").toString());
					desc = jsonResponse.optString("name").toString();

					// Getting business object
					business_str = jsonResponse.optString("business")
							.toString();
					businesses = getBusinesses(business_str);
					business = businesses.get(0);

					gridArray.add(new Shop(id, name, desc, is_available,
							business));
				}
			}
		} catch (JSONException e) {

			e.printStackTrace();
			return gridArray;
		}

		return gridArray;
	}

	public int getBranchId(String cont) {
		JSONObject jsonResponse;
		int id = 0;
		try {
			jsonResponse = new JSONObject(cont);
			if (!errorCheck(jsonResponse)) {
				id = Integer.parseInt(jsonResponse.optString("id").toString());
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return id;
	}

	public ArrayList<Branch> getBranchesByShop(String cont) {
		JSONObject jsonResponse;
		ArrayList<Branch> gridArray = new ArrayList<Branch>();
		try {
			jsonResponse = new JSONObject(cont);
			if (!errorCheck(jsonResponse)) {
				int id;
				String name, address, area_str;
				ArrayList<Area> areas;
				if (jsonResponse.has("elements")) {
					JSONArray jsonMainNode = jsonResponse
							.optJSONArray("elements");
					int lengthJsonArr = jsonMainNode.length();
					for (int i = 0; i < lengthJsonArr; i++) {
						JSONObject jsonChildNode = jsonMainNode
								.getJSONObject(i);
						id = Integer.parseInt(jsonChildNode.optString("id")
								.toString());
						name = jsonChildNode.optString("name").toString();
						address = jsonChildNode.optString("address").toString();
						area_str = jsonChildNode.optString("area").toString();
						areas = getAreasByCity(area_str);
						gridArray.add(new Branch(id, name, areas.get(0),
								address));
					}
				}
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return gridArray;
	}

	public Branch getBranch(String cont) {
		try {
			JSONObject jsonResponse = new JSONObject(cont);
			if (!errorCheck(jsonResponse)) {
				int id;
				String name, address, estimation_time, description, area_str;
				JSONObject open_hours;
				Area area;
				ArrayList<Area> areas;
				id = Integer.parseInt(jsonResponse.optString("id").toString());
				name = jsonResponse.optString("name").toString();
				description = jsonResponse.optString("description").toString();
				address = jsonResponse.optString("address").toString();
				estimation_time = jsonResponse.optString("estimation_time")
						.toString();

				area_str = jsonResponse.optString("area");
				areas = getAreasByCity(area_str);
				area = areas.get(0);

				open_hours = new JSONObject(jsonResponse.optString(
						"opening_hours").toString());

				Branch b = new Branch(id, name, description, area, address,
						null, estimation_time);
				b.setOpenHours(getOpenHours(open_hours));
				return b;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private OpenHours getOpenHours(JSONObject jsonMainNode) {
		String from = "", to = "";
		HashMap<Integer, String> days = new HashMap<Integer, String>();
		HashMap<Integer, String> froms = new HashMap<Integer, String>();
		HashMap<Integer, String> tos = new HashMap<Integer, String>();
		HashMap<Integer, Boolean> openDays = new HashMap<Integer, Boolean>();

		days.put(0, "mon");
		days.put(1, "tue");
		days.put(2, "wed");
		days.put(3, "thu");
		days.put(4, "fri");
		days.put(5, "sat");
		days.put(6, "sun");

		try {
			for (int i = 0; i < days.size(); i++) {
				String day = jsonMainNode.optString(days.get(i)).toString();
				if (day != null) {
					JSONObject dayResponse = new JSONObject(day);
					from = dayResponse.optString("from_hour").toString();
					to = dayResponse.optString("to_hour").toString();
					if (from.equals("0") && to.equals("0")) {

						openDays.put(i, false);
						froms.put(i, null);
						tos.put(i, null);
					} else {
						froms.put(i, from);
						tos.put(i, to);
						openDays.put(i, true);
					}
				} else {
					openDays.put(i, false);
					froms.put(i, null);
					tos.put(i, null);
				}
				Log.d("froms", openDays.get(i) + "->" + froms.get(i) + "->"
						+ tos.get(i));
			}
			return new OpenHours(froms, tos, openDays);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<Category> getCategoriesByBranch(String cont) {
		JSONObject jsonResponse;
		ArrayList<Category> gridArray = new ArrayList<Category>();

		try {
			jsonResponse = new JSONObject(cont);
			if (!errorCheck(jsonResponse)) {
				int id;
				String name;
				boolean is_active;
				if (jsonResponse.has("elements")) {
					JSONArray jsonMainNode = jsonResponse
							.optJSONArray("elements");
					int lengthJsonArr = jsonMainNode.length();
					for (int i = 0; i < lengthJsonArr; i++) {
						JSONObject jsonChildNode = jsonMainNode
								.getJSONObject(i);

						id = Integer.parseInt(jsonChildNode.optString("id")
								.toString());
						name = jsonChildNode.optString("name").toString();
						is_active = Boolean.valueOf(jsonChildNode.optString(
								"is_active").toString());
						gridArray.add(new Category(id, name, is_active, 0));
					}
				} else {
					id = Integer.parseInt(jsonResponse.optString("id")
							.toString());
					name = jsonResponse.optString("name").toString();
					is_active = Boolean.valueOf(jsonResponse.optString(
							"is_active").toString());
					gridArray.add(new Category(id, name, is_active, 0));
				}
			} else {
				return gridArray;
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}

		return gridArray;
	}

	public Photo getPhoto(String cont) {
		JSONObject jsonResponse;
		String url = "", thumb = "";
		Photo photo = new Photo(0, url, thumb);
		try {
			jsonResponse = new JSONObject(cont);

			url = jsonResponse.optString("url").toString();
			thumb = jsonResponse.optString("thumb").toString();
			photo.setUrl(url);
			photo.setThumb(thumb);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return photo;
	}

	public ArrayList<Product> getItemsByCategoryAndBranch(String cont) {
		JSONObject jsonResponse, jsonUnit;
		ArrayList<Product> gridArray = new ArrayList<Product>();

		try {
			jsonResponse = new JSONObject(cont);
			if (!errorCheck(jsonResponse)) {
				int id;
				boolean is_available;
				String name, description, photo_str, unit_str;
				int price;
				Unit unit;
				if (jsonResponse.has("elements")) {
					JSONArray jsonMainNode = jsonResponse
							.optJSONArray("elements");
					int lengthJsonArr = jsonMainNode.length();
					for (int i = 0; i < lengthJsonArr; i++) {
						JSONObject jsonChildNode = jsonMainNode
								.getJSONObject(i);

						id = Integer.parseInt(jsonChildNode.optString("id")
								.toString());
						try {
							price = Integer.parseInt(jsonChildNode.optString(
									"price").toString());
						} catch (Exception e) {
							price = 0;
						}

						name = jsonChildNode.optString("name").toString();
						description = jsonChildNode.optString("description")
								.toString();
						photo_str = jsonChildNode.optString("photo").toString();
						Photo p = getPhoto(photo_str);
						unit_str = jsonChildNode.optString("unit").toString();
						jsonUnit = new JSONObject(unit_str);
						unit = new Unit(Integer.parseInt(jsonUnit.optString(
								"id").toString()), jsonUnit.optString("name")
								.toString());

						is_available = Boolean.valueOf(jsonChildNode.optString(
								"is_available").toString());
						Product pro = new Product(id, price, name, description,
								p, new Category(0), unit, is_available, 0);
						gridArray.add(pro);
					}
				} else {
					id = Integer.parseInt(jsonResponse.optString("id")
							.toString());
					try {
						price = Integer.parseInt(jsonResponse
								.optString("price").toString());
					} catch (Exception e) {
						price = 0;
					}

					name = jsonResponse.optString("name").toString();
					description = jsonResponse.optString("description")
							.toString();
					photo_str = jsonResponse.optString("photo").toString();
					Photo p = getPhoto(photo_str);
					is_available = Boolean.valueOf(jsonResponse.optString(
							"is_available").toString());
					unit_str = jsonResponse.optString("unit").toString();
					jsonUnit = new JSONObject(unit_str);
					unit = new Unit(Integer.parseInt(jsonUnit.optString("id")
							.toString()), jsonUnit.optString("name").toString());

					Product pro = new Product(id, price, name, description, p,
							new Category(0), unit, is_available, 0);
					gridArray.add(pro);
				}
			} else {
				return gridArray;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return gridArray;
	}

	public ArrayList<Unit> getUnits(String cont) {

		JSONObject jsonResponse;
		ArrayList<Unit> gridArray = new ArrayList<Unit>();

		try {
			jsonResponse = new JSONObject(cont);
			if (!errorCheck(jsonResponse)) {
				int id;
				String name;
				if (jsonResponse.has("elements")) {
					JSONArray jsonMainNode = jsonResponse
							.optJSONArray("elements");
					int lengthJsonArr = jsonMainNode.length();
					for (int i = 0; i < lengthJsonArr; i++) {
						JSONObject jsonChildNode = jsonMainNode
								.getJSONObject(i);

						id = Integer.parseInt(jsonChildNode.optString("id")
								.toString());
						name = jsonChildNode.optString("name").toString();
						gridArray.add(new Unit(id, name));
					}
				} else {
					id = Integer.parseInt(jsonResponse.optString("id")
							.toString());
					name = jsonResponse.optString("name").toString();
					gridArray.add(new Unit(id, name));
				}
			} else {
				return gridArray;
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}

		return gridArray;

	}

	public void getItem(Integer item_id) {
	}

	public void getOrdersByUser(Integer user_id) {
	}

	public void getPreparers() {
	}

	public void getDeliverers() {
	}

	public void getOrder(Integer order_id) {
	}

	public void getOrdersByCustomer(Integer customer_id) {
	}

	public void getUser(Integer user_id) {
	}

	public ArrayList<Address> getAddress(String cont) {
		JSONObject jsonResponse;
		ArrayList<Address> gridArray = new ArrayList<Address>();
		try {
			jsonResponse = new JSONObject(cont);
			if (!errorCheck(jsonResponse)) {
				int id, customer_id;
				String country, city, area, street, building, floor, details, longitude, latitude, created_at, updated_at;
				if (jsonResponse.has("elements")) {
					JSONArray jsonMainNode = jsonResponse
							.optJSONArray("elements");
					int lengthJsonArr = jsonMainNode.length();
					for (int i = 0; i < lengthJsonArr; i++) {
						JSONObject jsonChildNode = jsonMainNode
								.getJSONObject(i);

						id = Integer.parseInt(jsonChildNode.optString("id")
								.toString());
						country = jsonChildNode.optString("country").toString();
						city = jsonChildNode.optString("city").toString();
						area = jsonChildNode.optString("area").toString();
						street = jsonChildNode.optString("street").toString();
						building = jsonChildNode.optString("building")
								.toString();
						floor = jsonChildNode.optString("floor").toString();
						details = jsonChildNode.optString("details").toString();
						longitude = jsonChildNode.optString("longitude")
								.toString();
						latitude = jsonChildNode.optString("latitude")
								.toString();
						created_at = jsonChildNode.optString("create_at")
								.toString();
						updated_at = jsonChildNode.optString("update_at")
								.toString();
						customer_id = Integer.parseInt(jsonChildNode.optString(
								"customer_id").toString());
						gridArray.add(new Address(id, country, city, area,
								building, floor, street, details, customer_id,
								longitude, latitude, created_at, updated_at));

					}
				} else {
					id = Integer.parseInt(jsonResponse.optString("id")
							.toString());
					country = jsonResponse.optString("country").toString();
					city = jsonResponse.optString("city").toString();
					area = jsonResponse.optString("area").toString();
					street = jsonResponse.optString("street").toString();
					building = jsonResponse.optString("building").toString();
					floor = jsonResponse.optString("floor").toString();
					details = jsonResponse.optString("details").toString();
					longitude = jsonResponse.optString("longitude").toString();
					latitude = jsonResponse.optString("latitude").toString();
					created_at = jsonResponse.optString("create_at").toString();
					updated_at = jsonResponse.optString("update_at").toString();
					customer_id = Integer.parseInt(jsonResponse.optString(
							"customer_id").toString());
					gridArray.add(new Address(id, country, city, area,
							building, floor, street, details, customer_id,
							longitude, latitude, created_at, updated_at));
				}
			} else {
				return gridArray;
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return gridArray;
	}

	public ArrayList<User> getUsers(String cont) {
		JSONObject jsonResponse, jsonRole;
		ArrayList<User> gridArray = new ArrayList<User>();

		try {
			jsonResponse = new JSONObject(cont);
			if (!errorCheck(jsonResponse)) {
				int id, branch_id;
				boolean is_fired, admin, preparer, delivery;
				String name, phone, roles_str;
				if (jsonResponse.has("elements")) {
					JSONArray jsonMainNode = jsonResponse
							.optJSONArray("elements");
					int lengthJsonArr = jsonMainNode.length();
					for (int i = 0; i < lengthJsonArr; i++) {
						JSONObject jsonChildNode = jsonMainNode
								.getJSONObject(i);

						id = Integer.parseInt(jsonChildNode.optString("id")
								.toString());
						name = jsonChildNode.optString("name").toString();
						roles_str = jsonChildNode.optString("roles").toString();
						jsonRole = new JSONObject(roles_str);
						admin = Boolean.parseBoolean(jsonRole
								.optString("admin").toString());
						preparer = Boolean.parseBoolean(jsonRole.optString(
								"preparer").toString());
						delivery = Boolean.parseBoolean(jsonRole.optString(
								"deliverer").toString());

						branch_id = Integer.parseInt(jsonChildNode.optString(
								"branch_id").toString());
						phone = jsonChildNode.optString("phone").toString();
						is_fired = Boolean.parseBoolean(jsonChildNode
								.optString("is_fired").toString());

						User u = new User(id, name, "", phone, (is_fired) ? 1
								: 0, null, branch_id, admin, preparer, delivery);
						gridArray.add(u);

					}
				} else {
					id = Integer.parseInt(jsonResponse.optString("id")
							.toString());
					name = jsonResponse.optString("name").toString();
					branch_id = Integer.parseInt(jsonResponse.optString(
							"branch_id").toString());
					phone = jsonResponse.optString("phone").toString();
					is_fired = Boolean.parseBoolean(jsonResponse.optString(
							"is_fired").toString());
					roles_str = jsonResponse.optString("roles").toString();
					jsonRole = new JSONObject(roles_str);
					admin = Boolean.parseBoolean(jsonRole.optString("admin")
							.toString());
					preparer = Boolean.parseBoolean(jsonRole.optString(
							"preparer").toString());
					delivery = Boolean.parseBoolean(jsonRole.optString(
							"deliverer").toString());

					gridArray.add(new User(id, name, "", phone, (is_fired) ? 1
							: 0, null, branch_id, admin, preparer, delivery));

				}

			} else {
				return gridArray;
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}

		return gridArray;
	}

	public int getUserId(String cont) {
		JSONObject jsonResponse;

		try {
			jsonResponse = new JSONObject(cont);
			if (!errorCheck(jsonResponse)) {
				{
					return Integer.parseInt(jsonResponse.optString("id")
							.toString());

				}
			} else {
				return 0;
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return 0;
	}

	public ArrayList<Customer> getCustomer(String cont) {
		JSONObject jsonResponse, jsonRole;
		ArrayList<Customer> gridArray = new ArrayList<Customer>();

		try {
			jsonResponse = new JSONObject(cont);
			if (!errorCheck(jsonResponse)) {
				int id;
				boolean is_fired, admin, preparer, delivery;
				String name, phone, password, username, mobile, roles_str;
				if (jsonResponse.has("elements")) {
					JSONArray jsonMainNode = jsonResponse
							.optJSONArray("elements");
					int lengthJsonArr = jsonMainNode.length();
					for (int i = 0; i < lengthJsonArr; i++) {
						JSONObject jsonChildNode = jsonMainNode
								.getJSONObject(i);

						id = Integer.parseInt(jsonChildNode.optString("id")
								.toString());
						name = jsonChildNode.optString("name").toString();
						phone = jsonChildNode.optString("phone").toString();
						mobile = jsonChildNode.optString("mobile").toString();

						Customer c = new Customer(id, name, phone, mobile, null);
						gridArray.add(c);

					}
				} else {
					id = Integer.parseInt(jsonResponse.optString("id")
							.toString());
					name = jsonResponse.optString("name").toString();
					phone = jsonResponse.optString("phone").toString();
					mobile = jsonResponse.optString("mobile").toString();

					Customer c = new Customer(id, name, phone, mobile, null);
					gridArray.add(c);

				}

			} else {
				return gridArray;
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}

		return gridArray;
	}

	public void getCustomers() {
	}

	public void getCustomerAddresses(Integer customer_id) {
	}

	public void getCustomerDefaultAddress(String cont) {
	}

	public ArrayList<Order> getOrders(String cont) {
		JSONObject jsonResponse, jsonCustomer;
		ArrayList<Order> gridArray = new ArrayList<Order>();
		Log.d("ray", "get order" + cont);
		try {
			jsonResponse = new JSONObject(cont);
			if (!errorCheck(jsonResponse)) {
				int id, count;
				boolean is_fired, admin, preparer, delivery, is_new;
				String date, password, username, mobile, customer_str, status, customer_name_str;
				double total;
				Customer customer = new Customer(0, null, null);
				if (jsonResponse.has("elements")) {
					JSONArray jsonMainNode = jsonResponse
							.optJSONArray("elements");
					int lengthJsonArr = jsonMainNode.length();
					for (int i = 0; i < lengthJsonArr; i++) {
						JSONObject jsonChildNode = jsonMainNode
								.getJSONObject(i);

						id = Integer.parseInt(jsonChildNode.optString("id")
								.toString());
						status = jsonChildNode.optString("status").toString();
						date = jsonChildNode.optString("created_at").toString();
						is_new = Boolean.parseBoolean(jsonChildNode.optString(
								"is_new").toString());

						customer_name_str = jsonChildNode.optString(
								"customer_name").toString();
						Log.d("rays", "cust: " + customer_name_str);
						if (customer_name_str != null
								&& !customer_name_str.isEmpty()
								&& !jsonChildNode.isNull("customer_name")) {
							customer = new Customer(0, customer_name_str, null);
						}

						total = Double.parseDouble(jsonChildNode.optString(
								"total").toString());
						count = Integer.parseInt(jsonChildNode.optString(
								"count").toString());
						Order c = new Order(id, customer, total, count);
						c.setStatus(status);
						c.setNewCustomer(is_new);
						c.setDate(date);
						gridArray.add(c);

					}
				} else {
					id = Integer.parseInt(jsonResponse.optString("id")
							.toString());
					customer_str = jsonResponse.optString("customer")
							.toString();
					jsonCustomer = new JSONObject(customer_str);
					customer = new Customer(Integer.parseInt(jsonCustomer
							.optString("id").toString()), jsonResponse
							.optString("name").toString(), jsonResponse
							.optString("mobile").toString());
					status = jsonResponse.optString("status").toString();
					date = jsonResponse.optString("created_at").toString();
					is_new = Boolean.parseBoolean(jsonResponse.optString(
							"is_new").toString());
					total = Double.parseDouble(jsonResponse.optString("total")
							.toString());
					count = Integer.parseInt(jsonResponse.optString("count")
							.toString());

					Order c = new Order(id, customer, total, count);
					c.setStatus(status);
					c.setNewCustomer(is_new);
					c.setDate(date);
					gridArray.add(c);
				}

			} else {
				return gridArray;
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}

		return gridArray;
	}

	public Order getOrder(String cont) {
		JSONObject jsonResponse, jsonCustomer, jsonAdd, jsonItem;
		Order order = new Order();

		try {
			jsonResponse = new JSONObject(cont);
			if (!errorCheck(jsonResponse)) {
				int id, count, quantity, preparer_id, deliverer_id;
				String customer_str, add_str, item_str, status, date, customer_name_str, note_str;

				double total;
				Customer customer = new Customer(0, null, null);
				Address address;
				ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
				OrderItem orderItem;
				Product product;
				if (jsonResponse.has("preparer_id")
						&& !jsonResponse.optString("preparer_id").toString()
								.equals("null")
						&& jsonResponse.optString("preparer_id").toString() != null) {
					preparer_id = Integer.parseInt(jsonResponse.optString(
							"preparer_id").toString());
					order.setPreparer(new User(preparer_id));
				}
				if (jsonResponse.has("deliverer_id")
						&& !jsonResponse.optString("deliverer_id").toString()
								.equals("null")
						&& jsonResponse.optString("deliverer_id").toString() != "null") {
					deliverer_id = Integer.parseInt(jsonResponse.optString(
							"deliverer_id").toString());
					order.setDelivery(new User(deliverer_id));
				}
				id = Integer.parseInt(jsonResponse.optString("id").toString());
				order.setId(id);
				total = Double.parseDouble(jsonResponse.optString("total")
						.toString());
				order.setTotal(total);
				count = Integer.parseInt(jsonResponse.optString("count")
						.toString());
				order.setCount(count);

				status = jsonResponse.optString("status").toString();
				date = jsonResponse.optString("created_at").toString();
				order.setStatus(status);
				add_str = jsonResponse.optString("address").toString();
				if (add_str != null && !add_str.isEmpty()
						&& !jsonResponse.isNull("customer")) {

					jsonAdd = new JSONObject(add_str);
					address = new Address(Integer.parseInt(jsonAdd.optString(
							"id").toString()), jsonAdd.optString("country")
							.toString(), jsonAdd.optString("city").toString(),
							jsonAdd.optString("area").toString(), jsonAdd
									.optString("street").toString(), jsonAdd
									.optString("building").toString(), jsonAdd
									.optString("floor").toString(), jsonAdd
									.optString("details").toString());
					order.setAddress(address);
				} else {
					order.setAddress(new Address(0));
				}
				note_str = jsonResponse.optString("note").toString();
				if (note_str != null && !note_str.isEmpty()
						&& !jsonResponse.isNull("note")) {
					order.setNote(note_str);
				}
				customer_str = jsonResponse.optString("customer").toString();
				if (customer_str != null && !customer_str.isEmpty()
						&& !jsonResponse.isNull("customer")) {
					jsonCustomer = new JSONObject(customer_str);
					customer = new Customer(Integer.parseInt(jsonCustomer
							.optString("id").toString()), jsonCustomer
							.optString("name").toString(), jsonCustomer
							.optString("mobile").toString());
				} else {
					customer_name_str = jsonResponse.optString("customer_name")
							.toString();
					if (customer_name_str != null
							&& !customer_name_str.isEmpty()
							&& !jsonResponse.isNull("customer_name")) {
						customer = new Customer(0, customer_name_str, null);
					}
				}
				order.setCustomer(customer);
				order.setDate(date);

				JSONArray jsonItemsNode = jsonResponse.optJSONArray("items");
				int lengthJsonArr = jsonItemsNode.length();

				for (int i = 0; i < lengthJsonArr; i++) {
					JSONObject jsonItemChildNode = jsonItemsNode
							.getJSONObject(i);

					quantity = Integer.parseInt(jsonItemChildNode.optString(
							"qty").toString());
					item_str = jsonItemChildNode.optString("item").toString();
					if (item_str != null && !item_str.isEmpty()
							&& !jsonItemChildNode.isNull("item")) {
						jsonItem = new JSONObject(item_str);
						product = getItemsByCategoryAndBranch(item_str).get(0);
						orderItem = new OrderItem(product, quantity);

						orderItems.add(orderItem);
					}
				}
				order.setOrderItems(orderItems);

			} else {
				return order;
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}

		return order;
	}

	public void getOrderStatusSequence(Integer order_id) {
	}

	public Country createCountry() {
		return new Country(0, "");
	}

	public boolean errorCheck(JSONObject jsonResponse) {
		if (jsonResponse.has("error")) {
			JSONArray jsonMainNode = jsonResponse.optJSONArray("error");
			if (jsonMainNode.length() > 0) {
				return true;
			}
		}
		return false;
	}

	public JSONObject objToCreate(Object o) {
		JSONObject jsonObjSend = new JSONObject();
		if (o instanceof Order) {

			Order c = (Order) o;
			JSONObject body = new JSONObject();
			try {
				if (c.isCancel()) {
					jsonObjSend.put("cancel_reason", c.getCancelReason());
				} else {
					if (c.getStatus() != null) {
						jsonObjSend.put("status", c.getStatus());
						if (c.getPreparer() != null) {
							jsonObjSend.put("p_id", c.getPreparer().getId());
							jsonObjSend.put("d_id", c.getDelivery().getId());
							jsonObjSend.put("note", c.getNote());
						}
					} else {
						JSONArray jsonArray = new JSONArray();
						try {

							ArrayList<OrderItem> oItems = c.getOrderItems();
							for (int i = 0; i < oItems.size(); i++) {
								JSONObject itemObj = new JSONObject();
								itemObj.put("id", oItems.get(i).getId());
								itemObj.put("qty", oItems.get(i).getQuantity());
								jsonArray.put(itemObj);
							}
							body.put("items", jsonArray);
							body.put("count", c.getCount());
							body.put("total", c.getTotal());
							body.put("address_id", c.getAddress_id());
							body.put("customer_id", c.getCustomer_id());
							body.put("note", c.getNote());
							jsonObjSend.put("order", body);
							Log.d("ray", "order:" + body);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		} else if (o instanceof Country) {
			Country c = (Country) o;

			JSONObject body = new JSONObject();
			try {
				body.put("name", c.getName());
				jsonObjSend.put("country", body);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		} else if (o instanceof City) {
			City c = (City) o;

			JSONObject body = new JSONObject();
			try {
				body.put("name", c.getName());
				body.put("country_id", c.getCountry_id());
				jsonObjSend.put("city", body);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		} else if (o instanceof Area) {
			Area c = (Area) o;

			JSONObject body = new JSONObject();
			try {
				body.put("name", c.getName());
				body.put("city_id", c.getCity_id());
				jsonObjSend.put("area", body);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		} else if (o instanceof Business) {
			Business c = (Business) o;

			JSONObject body = new JSONObject();
			try {
				body.put("name", c.getName());
				jsonObjSend.put("business", body);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		} else if (o instanceof Category) {
			Category c = (Category) o;

			JSONObject body = new JSONObject();
			try {
				body.put("name", c.getName());
				body.put("shop_id", c.getShopId());
				jsonObjSend.put("category", body);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		} else if (o instanceof User) {
			User c = (User) o;

			JSONObject body = new JSONObject();
			try {
				body.put("phone", c.getPhone());
				if (c.isLogin()) {
					body.put("pass", c.getPassword());
				} else {
					if (c.getPassword() != null)
						if (c.getId() == 0) {
							c.setEncPassword(c.getPhone());
							body.put("pass", c.getPassword());
						}
					if (c.getName() != null)
						body.put("name", c.getName());
					if (c.getBranch_id() != 0)
						body.put("branch_id", c.getBranch_id());
					body.put("is_fired", 0);

				}

				jsonObjSend.put("user", body);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		} else if (o instanceof Product) {
			Product c = (Product) o;

			JSONObject body = new JSONObject();
			try {
				body.put("name", c.getName());
				body.put("price", c.getPrice());
				body.put("description", c.getDescription());
				body.put("shop_id", c.getShop_id());
				body.put("category_id", c.getCategory().getId());
				body.put("unit_id", c.getUnit().getId());
				body.put("photo", c.getPhoto().getUrl());

				jsonObjSend.put("item", body);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		} else if (o instanceof Branch) {
			Branch c = (Branch) o;

			JSONObject body = new JSONObject();
			try {
				body.put("name", c.getName());
				body.put("address", c.getAddress());
				body.put("description", c.getDescription());
				body.put("shop_id", c.getShop().getId());
				body.put("area_id", c.getArea().getId());
				body.put("long", c.getLongitude());
				body.put("lat", c.getLatitude());
				body.put("estimation_time", c.getEstimation_time());

				jsonObjSend.put("branch", body);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}/*
		 * else if (o instanceof Activate) { Activate c = (Activate) o;
		 * JSONArray jsonArray = new JSONArray(); try { for (int i = 0; i <
		 * c.getToUpdate().size(); i++) { jsonArray.put(c.getToUpdate().get(i));
		 * } jsonObjSend.put(c.getType(), jsonArray); } catch (JSONException e)
		 * { e.printStackTrace(); }
		 * 
		 * }
		 */else if (o instanceof OpenHours) {
			OpenHours oh = (OpenHours) o;
			try {
				jsonObjSend.put("opening_hours", oh.getOpenHours());
			} catch (JSONException e) {
				e.printStackTrace();
			}

		} /*
		 * else if (o instanceof Role) { Role c = (Role) o; JSONObject body =
		 * new JSONObject(); try { body.put("is_admin", c.getAdmin() ? 1 : 0);
		 * body.put("is_preparer", c.getPreparer() ? 1 : 0);
		 * body.put("is_deliverer", c.getDelivery() ? 1 : 0);
		 * 
		 * jsonObjSend.put("roles", body); } catch (JSONException e) {
		 * e.printStackTrace(); } }
		 */

		return jsonObjSend;
	}
}
