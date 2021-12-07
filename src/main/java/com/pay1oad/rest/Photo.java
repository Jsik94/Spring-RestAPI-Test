package com.pay1oad.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Photo {

	public int albumId;
	public int id;
	public String title;
	public String url;
	public String thumbnailUrl;
	@Override
	public String toString() {
		return "Photo [albumId=" + albumId + ", id=" + id + ", title=" + title + ", url=" + url + ", thumbnailUrl="
				+ thumbnailUrl + "]";
	}
	
	
}
