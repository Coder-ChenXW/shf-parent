package com.atguigu.vo;


import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HouseVo implements java.io.Serializable {

	private Long id;
	private String communityName;
	private String name;
	private String buildArea;
	private BigDecimal totalPrice;
	private BigDecimal unitPrice;
	private Long houseTypeId;
	private Long floorId;
	private Long directionId;
	private String defaultImageUrl;
	private Date createTime;

	private String houseTypeName;
	private String floorName;
	private String directionName;

	public HouseVo() {
	}

	public HouseVo(Long id, String communityName, String name, String buildArea, BigDecimal totalPrice, BigDecimal unitPrice, Long houseTypeId, Long floorId, Long directionId, String defaultImageUrl, Date createTime, String houseTypeName, String floorName, String directionName) {
		this.id = id;
		this.communityName = communityName;
		this.name = name;
		this.buildArea = buildArea;
		this.totalPrice = totalPrice;
		this.unitPrice = unitPrice;
		this.houseTypeId = houseTypeId;
		this.floorId = floorId;
		this.directionId = directionId;
		this.defaultImageUrl = defaultImageUrl;
		this.createTime = createTime;
		this.houseTypeName = houseTypeName;
		this.floorName = floorName;
		this.directionName = directionName;
	}


	//日期转换为字符串
	public String getCreateTimeString() {
		Date date = this.getCreateTime();
		if(null == date) return "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = df.format(date);
		return dateString;
	}

	/**
	 * 获取
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取
	 * @return communityName
	 */
	public String getCommunityName() {
		return communityName;
	}

	/**
	 * 设置
	 * @param communityName
	 */
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	/**
	 * 获取
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取
	 * @return buildArea
	 */
	public String getBuildArea() {
		return buildArea;
	}

	/**
	 * 设置
	 * @param buildArea
	 */
	public void setBuildArea(String buildArea) {
		this.buildArea = buildArea;
	}

	/**
	 * 获取
	 * @return totalPrice
	 */
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	/**
	 * 设置
	 * @param totalPrice
	 */
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * 获取
	 * @return unitPrice
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	/**
	 * 设置
	 * @param unitPrice
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * 获取
	 * @return houseTypeId
	 */
	public Long getHouseTypeId() {
		return houseTypeId;
	}

	/**
	 * 设置
	 * @param houseTypeId
	 */
	public void setHouseTypeId(Long houseTypeId) {
		this.houseTypeId = houseTypeId;
	}

	/**
	 * 获取
	 * @return floorId
	 */
	public Long getFloorId() {
		return floorId;
	}

	/**
	 * 设置
	 * @param floorId
	 */
	public void setFloorId(Long floorId) {
		this.floorId = floorId;
	}

	/**
	 * 获取
	 * @return directionId
	 */
	public Long getDirectionId() {
		return directionId;
	}

	/**
	 * 设置
	 * @param directionId
	 */
	public void setDirectionId(Long directionId) {
		this.directionId = directionId;
	}

	/**
	 * 获取
	 * @return defaultImageUrl
	 */
	public String getDefaultImageUrl() {
		return defaultImageUrl;
	}

	/**
	 * 设置
	 * @param defaultImageUrl
	 */
	public void setDefaultImageUrl(String defaultImageUrl) {
		this.defaultImageUrl = defaultImageUrl;
	}

	/**
	 * 获取
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取
	 * @return houseTypeName
	 */
	public String getHouseTypeName() {
		return houseTypeName;
	}

	/**
	 * 设置
	 * @param houseTypeName
	 */
	public void setHouseTypeName(String houseTypeName) {
		this.houseTypeName = houseTypeName;
	}

	/**
	 * 获取
	 * @return floorName
	 */
	public String getFloorName() {
		return floorName;
	}

	/**
	 * 设置
	 * @param floorName
	 */
	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	/**
	 * 获取
	 * @return directionName
	 */
	public String getDirectionName() {
		return directionName;
	}

	/**
	 * 设置
	 * @param directionName
	 */
	public void setDirectionName(String directionName) {
		this.directionName = directionName;
	}

	public String toString() {
		return "HouseVo{id = " + id + ", communityName = " + communityName + ", name = " + name + ", buildArea = " + buildArea + ", totalPrice = " + totalPrice + ", unitPrice = " + unitPrice + ", houseTypeId = " + houseTypeId + ", floorId = " + floorId + ", directionId = " + directionId + ", defaultImageUrl = " + defaultImageUrl + ", createTime = " + createTime + ", houseTypeName = " + houseTypeName + ", floorName = " + floorName + ", directionName = " + directionName + "}";
	}
}

