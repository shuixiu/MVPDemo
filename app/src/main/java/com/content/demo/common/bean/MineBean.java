package com.content.demo.common.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.content.basehttp.BaseMsg;


public class MineBean extends BaseMsg implements Parcelable {


    private String availableIntegral;
    private String currentBalance;
    private String openid;
    private String otherStoreBalance;    // 其他门店余额
    private String id;
    private int flag;
    private String flagValue;
    private int gender;// 2女/1 男
    private String genderValue;
    private int growthValue;
    private String headPortrait;
    private int integral;
    private String level;
    private String address;
    private String userName;
    private String idCard;
    private String birthday;
    private String memberTypeName;
    private String memberType;
    private String couponAccount;//优惠券数量

    public String getCouponAccount() {
        return couponAccount;
    }

    public void setCouponAccount(String couponAccount) {
        this.couponAccount = couponAccount;
    }

    public String getMemberTypeName() {
        return memberTypeName;
    }

    public void setMemberTypeName(String memberTypeName) {
        this.memberTypeName = memberTypeName;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }


    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOtherStoreBalance() {
        return otherStoreBalance;
    }

    public void setOtherStoreBalance(String otherStoreBalance) {
        this.otherStoreBalance = otherStoreBalance;
    }

    private String nickName;

    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String phone;

    public String getAvailableIntegral() {
        return availableIntegral;
    }

    public void setAvailableIntegral(String availableIntegral) {
        this.availableIntegral = availableIntegral;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getFlagValue() {
        return flagValue;
    }

    public void setFlagValue(String flagValue) {
        this.flagValue = flagValue;
    }

    public String getGenderValue() {
        return genderValue;
    }

    public void setGenderValue(String genderValue) {
        this.genderValue = genderValue;
    }

    public int getGrowthValue() {
        return growthValue;
    }

    public void setGrowthValue(int growthValue) {
        this.growthValue = growthValue;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public MineBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.availableIntegral);
        dest.writeString(this.currentBalance);
        dest.writeString(this.openid);
        dest.writeString(this.otherStoreBalance);
        dest.writeString(this.id);
        dest.writeInt(this.flag);
        dest.writeString(this.flagValue);
        dest.writeInt(this.gender);
        dest.writeString(this.genderValue);
        dest.writeInt(this.growthValue);
        dest.writeString(this.headPortrait);
        dest.writeInt(this.integral);
        dest.writeString(this.level);
        dest.writeString(this.address);
        dest.writeString(this.userName);
        dest.writeString(this.idCard);
        dest.writeString(this.birthday);
        dest.writeString(this.memberTypeName);
        dest.writeString(this.memberType);
        dest.writeString(this.nickName);
        dest.writeString(this.phone);
    }

    protected MineBean(Parcel in) {
        this.availableIntegral = in.readString();
        this.currentBalance = in.readString();
        this.openid = in.readString();
        this.otherStoreBalance = in.readString();
        this.id = in.readString();
        this.flag = in.readInt();
        this.flagValue = in.readString();
        this.gender = in.readInt();
        this.genderValue = in.readString();
        this.growthValue = in.readInt();
        this.headPortrait = in.readString();
        this.integral = in.readInt();
        this.level = in.readString();
        this.address = in.readString();
        this.userName = in.readString();
        this.idCard = in.readString();
        this.birthday = in.readString();
        this.memberTypeName = in.readString();
        this.memberType = in.readString();
        this.nickName = in.readString();
        this.phone = in.readString();
    }

    public static final Creator<MineBean> CREATOR = new Creator<MineBean>() {
        @Override
        public MineBean createFromParcel(Parcel source) {
            return new MineBean(source);
        }

        @Override
        public MineBean[] newArray(int size) {
            return new MineBean[size];
        }
    };

    public String getGenderStr() {
        if (this.gender == 2) {
            return "女";
        } else if (this.gender == 1) {
            return "男";
        } else {
            return "";
        }
    }
}
