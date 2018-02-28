package com.graction.developer.zoocaster.Model.Response;

import com.graction.developer.zoocaster.Model.Response.IntegratedAirQualityModel.IntegratedAirQualityModelItem;

public class IntegratedAirQualitySingleModel {
	private IntegratedAirQualityModelItem item;
	private IntegratedAirQualityModelItem parm, ArpltnInforInqireSvcVo;

	public IntegratedAirQualitySingleModel(IntegratedAirQualityModelItem item, IntegratedAirQualityModelItem parm, IntegratedAirQualityModelItem arpltnInforInqireSvcVo) {
		this.item = item;
		this.parm = parm;
		ArpltnInforInqireSvcVo = arpltnInforInqireSvcVo;
	}
	
	public IntegratedAirQualitySingleModel(IntegratedAirQualityModel model, IntegratedAirQualityModelItem item) {
		this.item = item;
		this.ArpltnInforInqireSvcVo = model.getArpltnInforInqireSvcVo();
		this.parm = model.getParm();
	}

	public IntegratedAirQualityModelItem getItem() {
		return item;
	}

	public void setItem(IntegratedAirQualityModelItem item) {
		this.item = item;
	}

	public IntegratedAirQualityModelItem getParm() {
		return parm;
	}

	public void setParm(IntegratedAirQualityModelItem parm) {
		this.parm = parm;
	}

	public IntegratedAirQualityModelItem getArpltnInforInqireSvcVo() {
		return ArpltnInforInqireSvcVo;
	}

	public void setArpltnInforInqireSvcVo(IntegratedAirQualityModelItem arpltnInforInqireSvcVo) {
		ArpltnInforInqireSvcVo = arpltnInforInqireSvcVo;
	}

	@Override
	public String toString() {
		return "IntegratedAirQualitySingleModel [item=" + item + ", parm=" + parm + ", ArpltnInforInqireSvcVo="
				+ ArpltnInforInqireSvcVo + "]";
	}

}
