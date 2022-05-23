package com.review.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	private int pageNum;
	private int amount;
	private String keyword;
	private String type;
	private String[] typeArr;
	
	public Criteria() {
		this(1,10);
	}
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public void setType(String type) {
		this.type = type;
		this.typeArr = type.split("");
	}
	
	public String[] getTypeArr() {
		return type == null ? new String[] {} : type.split("");
	}
	
	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("pageNum", this.pageNum).queryParam("amount", this.getAmount())
				.queryParam("type", this.getType()).queryParam("keyword", this.getKeyword());
		return builder.toUriString();
	}
}
