package com.example.getcontacts.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * 序列化map供Bundle传递map使用
 * @author miaowei
 *
 */
public class SerializableMap implements Serializable{

	/**
	 * 此Map用于接收选中联系人的ID和状态
	 */
	private Map<String , Boolean> map;

	public Map<String, Boolean> getMap() {
		return map;
	}

	public void setMap(Map<String, Boolean> map) {
		this.map = map;
	}
}
