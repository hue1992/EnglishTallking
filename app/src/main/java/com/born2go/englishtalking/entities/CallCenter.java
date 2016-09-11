package com.born2go.englishtalking.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Hue on 8/23/2016.
 */
public class CallCenter implements Parcelable {
    String ccName;
    String avatar;
    String partnerId;
    String msisdn;
    long totalTime;
    long totalPoint;
    int active;
    int status;
    String timeLimit;
    String videoId;
    String cc_id;
    String grade;

    public CallCenter() {
    }


    public CallCenter(String ccName, String avatar, String partnerId, String msisdn, long totalTime, long totalPoint, int active, int status, String timeLimit, String videoId, String cc_id, String grade) {
        this.ccName = ccName;
        this.avatar = avatar;
        this.partnerId = partnerId;
        this.msisdn = msisdn;
        this.totalTime = totalTime;
        this.totalPoint = totalPoint;
        this.active = active;
        this.status = status;
        this.timeLimit = timeLimit;
        this.videoId = videoId;
        this.cc_id = cc_id;
        this.grade = grade;
    }

    public String getCcName() {
        return ccName;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public long getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(long totalPoint) {
        this.totalPoint = totalPoint;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getCc_id() {
        return cc_id;
    }

    public void setCc_id(String cc_id) {
        this.cc_id = cc_id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    protected CallCenter(Parcel in) {
        ccName = in.readString();
        avatar = in.readString();
        partnerId = in.readString();
        msisdn = in.readString();
        totalTime = in.readLong();
        totalPoint = in.readLong();
        active = in.readInt();
        status = in.readInt();
        timeLimit = in.readString();
        videoId=in.readString();
        cc_id=in.readString();
        grade=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ccName);
        dest.writeString(avatar);
        dest.writeString(partnerId);
        dest.writeString(msisdn);
        dest.writeLong(totalTime);
        dest.writeLong(totalPoint);
        dest.writeInt(active);
        dest.writeInt(status);
        dest.writeString(timeLimit);
        dest.writeString(videoId);
        dest.writeString(cc_id);
        dest.writeString(grade);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CallCenter> CREATOR = new Parcelable.Creator<CallCenter>() {
        @Override
        public CallCenter createFromParcel(Parcel in) {
            return new CallCenter(in);
        }

        @Override
        public CallCenter[] newArray(int size) {
            return new CallCenter[size];
        }
    };
}