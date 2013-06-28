package com.haier.openplatform.console.issue.webapp.action;

import java.util.List;

class HopValueData {
	private String value;

	public HopValueData(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

class HopPointData {
	private String x;
	private String y;

	public HopPointData(String x, String y) {
		this.x = x;
		this.y = y;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String formatPoint() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(this.x);
		sb.append(",");
		sb.append(this.y);
		sb.append("]");
		return sb.toString();
	}
}

/**
 * 异常返回json数据结构并包含相关方法
 * 
 * @author WuWeiguang
 * 
 */
public class HopChartUtil {

	private String datas;
	private List<String> ticks;
	private List<String> labels;

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public String getDatas() {
		return datas;
	}

	public void setDatas(String datas) {
		this.datas = datas;
	}

	public List<String> getTicks() {
		return ticks;
	}

	public void setTicks(List<String> ticks) {
		this.ticks = ticks;
	}

	public static String formatPointData(List<HopPointData> pointDatas) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		if (pointDatas != null) {
			int size = pointDatas.size();
			for (int i = 0; i < size; i++) {
				sb.append(pointDatas.get(i).formatPoint());
				if (i != size - 1) {
					sb.append(",");
				}
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public static String formatValueData(List<HopValueData> valueDatas) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		if (valueDatas != null) {
			int size = valueDatas.size();
			for (int i = 0; i < size; i++) {
				sb.append(valueDatas.get(i).getValue());
				if (i != size - 1) {
					sb.append(",");
				}
			}
		}
		sb.append("]");
		return sb.toString();
	}

}
