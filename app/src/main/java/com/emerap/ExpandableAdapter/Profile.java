package com.emerap.ExpandableAdapter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by karbunkul on 04.03.17.
 */

public class Profile {

    @SerializedName("_id")
    public String id;

    @SerializedName("balance")
    public String balance;

    @SerializedName("name")
    public String name;

    @SerializedName("gender")
    public String gender;

    @SerializedName("company")
    public String company;

    @SerializedName("email")
    public String email;
}
