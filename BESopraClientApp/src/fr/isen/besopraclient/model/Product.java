package fr.isen.besopraclient.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable{

	private int id;
	private String title;
	private String description;
	private float price;
	private int categoryId;
	private int subCategoryId;
	private String barcode;
	private byte[] image;

	public Product(int id, String title, String description, float price, int categoryId, int subCategoryId, String barcode, byte[] image) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.price = price;
		this.categoryId = categoryId;
		this.subCategoryId = subCategoryId;
		this.barcode = barcode;
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(int subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	// write your object's data to the passed-in Parcel
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(title);
        out.writeString(description);
        out.writeFloat(price); 
        out.writeInt(categoryId);
        out.writeInt(subCategoryId);
        out.writeString(barcode);
        out.writeInt(image.length);
        out.writeByteArray(image);
    }

    // this is used to regenerate your object. All Category must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Product(Parcel in) {
    	id = in.readInt();
		title = in.readString();
		description = in.readString();
		price = in.readFloat();
		categoryId = in.readInt();
		subCategoryId = in.readInt();
		barcode = in.readString();
		image = new byte[in.readInt()];
	    in.readByteArray(image);
    }
}
