package com.graction.developer.zoocaster.Model.Weather;

public class Snow {
	private long _3h; // Snow volume for the last 3 hours

	public long get_3h() {
		return _3h;
	}

	public void set_3h(long _3h) {
		this._3h = _3h;
	}

	@Override
	public String toString() {
		return "Rain [_3h=" + _3h + "]";
	}
}
