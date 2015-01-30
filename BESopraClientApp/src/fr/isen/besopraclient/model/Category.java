package fr.isen.besopraclient.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable{
	
	private int id;
	private String name;
	private String adviser;
	private String description;
	private int subCategory;

	/**
	 * @param id
	 * @param name
	 */
	public Category(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	/**
	 * @param id
	 * @param name
	 * @param adviser
	 * @param description
	 * @param subCategory
	 */
	public Category(int id, String name, String adviser, String description, int subCategory) {
		this(id, name);
		this.adviser = adviser;
		this.description = description;
		this.subCategory = subCategory;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAdviser() {
		return adviser;
	}

	public void setAdviser(String adviser) {
		this.adviser = adviser;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(int subCategory) {
		this.subCategory = subCategory;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	// write your object's data to the passed-in Parcel
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeString(adviser);
        out.writeString(description);
        out.writeInt(subCategory);
    }

    // this is used to regenerate your object. All Category must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Category(Parcel in) {
        id = in.readInt();
        name = in.readString();
        adviser = in.readString();
        description = in.readString();
        subCategory = in.readInt();
    }
}

