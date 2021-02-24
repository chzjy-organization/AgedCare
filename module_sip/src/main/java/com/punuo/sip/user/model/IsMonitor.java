package com.punuo.sip.user.model;

import com.google.gson.annotations.SerializedName;
import com.punuo.sys.sdk.model.PNBaseModel;

/**
 * Created by han.chen.
 * Date on 2021/1/29.
 **/
public class IsMonitor extends PNBaseModel {

    @SerializedName("ismonitor")
    public boolean isMonitor;

    @SerializedName("devid")
    public String targetDevId;

    @SerializedName("userid")
    public String targetUserId;
}
