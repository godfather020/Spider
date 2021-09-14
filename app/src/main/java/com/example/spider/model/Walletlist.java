package com.example.spider.model;

import com.google.gson.annotations.SerializedName;

public class Walletlist {

    @SerializedName("deposit")
    private Integer deposit;

    @SerializedName("withdraw")
    private Integer withdraw;

    @SerializedName("total")
    private Integer total;


    @SerializedName("id")
    
    private String id;
    @SerializedName("mindeposit")
    
    private String mindeposit;
    @SerializedName("maxdeposit")
    
    private String maxdeposit;
    @SerializedName("minwithdraw")
    
    private String minwithdraw;
    @SerializedName("maxwithdraw")
    
    private String maxwithdraw;

    @SerializedName("reward")
    private String reward;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMindeposit() {
        return mindeposit;
    }

    public void setMindeposit(String mindeposit) {
        this.mindeposit = mindeposit;
    }

    public String getMaxdeposit() {
        return maxdeposit;
    }

    public void setMaxdeposit(String maxdeposit) {
        this.maxdeposit = maxdeposit;
    }

    public String getMinwithdraw() {
        return minwithdraw;
    }

    public void setMinwithdraw(String minwithdraw) {
        this.minwithdraw = minwithdraw;
    }

    public String getMaxwithdraw() {
        return maxwithdraw;
    }

    public void setMaxwithdraw(String maxwithdraw) {
        this.maxwithdraw = maxwithdraw;
    }


    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public Integer getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(Integer withdraw) {
        this.withdraw = withdraw;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }
}
