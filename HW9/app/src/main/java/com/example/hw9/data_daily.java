package com.example.hw9;

import java.util.*;

public class data_daily {

    public List<data_list_structure> list;
    public String summary;
    public String icon;

    public void setData(List<data_list_structure> list){
        this.list = list;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }

    public void setSummary(String summary){
        this.summary = summary;
    }

    public List<data_list_structure> getData(){
        return list;
    }

    public String getIcon(){
        return icon;
    }

    public String getSummary(){
        return  summary;
    }



}
