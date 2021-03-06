package com.addresses.manager.api.spec;

public class SearchCriteria {
	
	private String key;
    private String value;
    
    public SearchCriteria() {

    }

    public SearchCriteria(final String key, final String value) {
        super();
        this.key = key;
        this.value = value;
    }
    
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

}
