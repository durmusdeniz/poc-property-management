package property.management.api.utils;

import property.management.api.model.Furnish;
import property.management.api.model.PropertyFor;
import property.management.api.model.PropertyType;

import java.math.BigDecimal;

public class PropertySearchQuery {

	private PropertyType type;

	private PropertyFor goal;

	private Furnish furnish;

	private String location;

	private float size;
	private float maxSize;

	private BigDecimal deposit;

	private BigDecimal fee;

	private boolean negotiable;

	private BigDecimal price;

	private BigDecimal maxPrice;

	private int bedRooms;

	private int bathRooms;

	public PropertyType getType() {
		return type;
	}

	public BigDecimal getMaxPrice() {
		if(maxPrice == null){
			return BigDecimal.ZERO;
		}
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public void setType(PropertyType type) {
		this.type = type;
	}

	public PropertyFor getGoal() {
		return goal;
	}

	public void setGoal(PropertyFor goal) {
		this.goal = goal;
	}

	public Furnish getFurnish() {
		return furnish;
	}

	public void setFurnish(Furnish furnish) {
		this.furnish = furnish;
	}

	public String getLocation() {
		return location;
	}

	public float getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(float maxSize) {
		this.maxSize = maxSize;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public boolean isNegotiable() {
		return negotiable;
	}

	public void setNegotiable(boolean negotiable) {
		this.negotiable = negotiable;
	}

	public BigDecimal getPrice() {
		if(price == null){
			return BigDecimal.ZERO;
		}
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getBedRooms() {
		return bedRooms;
	}

	public void setBedRooms(int bedRooms) {
		this.bedRooms = bedRooms;
	}

	public int getBathRooms() {
		return bathRooms;
	}

	public void setBathRooms(int bathRooms) {
		this.bathRooms = bathRooms;
	}

	public boolean isCompleteQuery(){
		return (size != 0.0 || maxSize != 0.0) && location != null && (price != null || maxPrice != null);
	}
	public boolean isNoSizeQuery(){
		return (size == 0.0 || maxSize == 0.0) && location != null && (price != null || maxPrice != null);
	}

	public boolean isLocationOnlyQuery(){
		return (size == 0.0 || maxSize == 0.0) && location != null && (price == null && maxPrice == null);
	}

	public boolean isPriceOnlyQuery(){
		return (size == 0.0 || maxSize == 0.0) && location == null && (price != null && maxPrice != null);
	}
}
