package com.pug.car_rent_app.model;

public class SubscriptionType implements Comparable<SubscriptionType> {

    private Integer id;
    private String name;
    private Integer MonthMin;
    private Integer MonthMax;

    public SubscriptionType() {

    }

    public SubscriptionType(Integer id, String name, Integer monthMin, Integer monthMax) {
        this.id = id;
        this.name = name;
        MonthMin = monthMin;
        MonthMax = monthMax;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getMonthMin() {
        return MonthMin;
    }

    public Integer getMonthMax() {
        return MonthMax;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMonthMin(Integer monthMin) {
        MonthMin = monthMin;
    }

    public void setMonthMax(Integer monthMax) {
        MonthMax = monthMax;
    }

    @Override
    public int compareTo(SubscriptionType that) {
        return Integer.compare(that.getMonthMax(), this.MonthMax);
    }

}
