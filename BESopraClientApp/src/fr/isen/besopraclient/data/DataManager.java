package fr.isen.besopraclient.data;

import java.util.ArrayList;
import java.util.List;

import fr.isen.besopraclient.model.*;

public class DataManager {
	
	private static List<Category> categoryList = new ArrayList<Category>();
	private static List<Product> productList = new ArrayList<Product>();
	private static List<Meeting> meetingList = new ArrayList<Meeting>();
	
	public static boolean add(Category object) {
		return categoryList.add(object);
	}
	
	public static boolean add(Product object) {
		return productList.add(object);
	}

	public static boolean add(Meeting object) {
		return meetingList.add(object);
	}

	public static boolean removeCategory(Object object) {
		return categoryList.remove(object);
	}
	
	public static boolean removeProduct(Object object) {
		return productList.remove(object);
	}
	
	public static boolean removeMeeting(Object object) {
		return meetingList.remove(object);
	}

	public static List<Category> getCategoryList() {
		return categoryList;
	}

	public static void setCategoryList(List<Category> categoryList) {
		DataManager.categoryList = categoryList;
	}

	public static List<Product> getProductList() {
		return productList;
	}

	public static void setProductList(List<Product> productList) {
		DataManager.productList = productList;
	}

	public static List<Meeting> getMeetingList() {
		return meetingList;
	}

	public static void setMeetingList(List<Meeting> meetingList) {
		DataManager.meetingList = meetingList;
	}
	
	public static List<Category> getCategoryOnly(){
		List<Category> list = new ArrayList<Category>();
		for(Category c : categoryList){
			if(c.getSubCategory() == -1){
				list.add(c);
			}
		}
		return list;
	}
	
	public static List<Category> getSubCategoryOf(int id){
		List<Category> list = new ArrayList<Category>();
		if(id > 0){
			for(Category c : categoryList){
				if(c.getSubCategory() == id){
					list.add(c);
				}
			}
		}
		return list;
	}

	public static List<Product> getProductOfCategory(int id) {
		List<Product> list = new ArrayList<Product>();
		if(id > 0){
			for(Product p : productList){
				if(p.getCategoryId() == id){
					list.add(p);
				}
			}
		}
		return list;
	}
	
	public static List<Product> getProductOfSubCategory(int id) {
		List<Product> list = new ArrayList<Product>();
		if(id > 0){
			for(Product p : productList){
				if(p.getSubCategoryId() == id){
					list.add(p);
				}
			}
		}
		return list;
	}
}
