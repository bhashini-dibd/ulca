package org.ulca.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UMSResponse {

    @JsonProperty("count")
    public Integer count;

    @JsonProperty("data")
    public User data;

    @JsonProperty("message")
    public String message;

    public UMSResponse(Integer count, User data, String message) {
        this.count = count;
        this.data = data;
        this.message = message;
    }

    public UMSResponse() {
    }

    @Override
    public String toString() {
        return "UMSResponse{" +
                "count=" + count +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
