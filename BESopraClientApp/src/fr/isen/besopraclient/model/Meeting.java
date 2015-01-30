package fr.isen.besopraclient.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Meeting implements Parcelable{
	private int id;
	private int categoryId;
	private String client;
	private String adviser;
	private String reason;
    private long startDate;
    public long endDate;
    
    /**
     * @param client
     */
    public Meeting(String client){
		this.client = client;
	}
    
    /**
     * @param client
     * @param reason
     */
    public Meeting(String client, String reason){
		this(client);
		this.reason = reason;
	}
    
    /**
     * @param client
     * @param reason
     * @param startDate
     */
    public Meeting(String client, String reason, long startDate){
		this(client, reason);
		this.startDate = startDate;
	}
    
    /**
     * @param id
     * @param client
     * @param reason
     * @param startDate
     */
    public Meeting(int id, String client, String reason, long startDate){
		this(client, reason, startDate);
		this.id = id;
	}
    
    /**
     * @param id
     * @param categoryId
     * @param client
     * @param adviser
     * @param reason
     * @param startDate
     */
    public Meeting(int id, int categoryId, String client, String adviser, String reason, long startDate) {
    	this(id, client, reason, startDate);
		this.categoryId = categoryId;
		this.adviser = adviser;
	}
	
    /**
     * @param id
     * @param categoryId
     * @param client
     * @param adviser
     * @param reason
     * @param startDate
     * @param endDate
     */
	public Meeting(int id, int categoryId, String client, String adviser, String reason, long startDate, long endDate) {
		this(id, categoryId, client, adviser, reason, startDate);
		this.endDate = endDate;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getAdviser() {
		return adviser;
	}
	public void setAdviser(String adviser) {
		this.adviser = adviser;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public long getEndDate() {
		return endDate;
	}
	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}
	  
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public long getStartDate() {
		return startDate;
	}
	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	// write your object's data to the passed-in Parcel
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeInt(categoryId);
        out.writeString(client);
        out.writeString(adviser);
        out.writeString(reason);
        out.writeLong(startDate);
        out.writeLong(endDate);
    }

    // this is used to regenerate your object. All Category must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Meeting> CREATOR = new Parcelable.Creator<Meeting>() {
        public Meeting createFromParcel(Parcel in) {
            return new Meeting(in);
        }

        public Meeting[] newArray(int size) {
            return new Meeting[size];
        }
    };

    private Meeting(Parcel in) {
    	id = in.readInt();
    	categoryId = in.readInt();
    	client = in.readString();
    	adviser = in.readString();
    	reason = in.readString();
        startDate = in.readLong();
        endDate = in.readLong();
    }
}
