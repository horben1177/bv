package com.betvictor.entity;

public enum LengthEnum {

    SHORT("short"),
    MEDIUM("medium"),
    LONG("long"),
    VERYLONG("verylong");

    private String length;

    LengthEnum(String length) {
        this.length = length;
    }

    public String getLength() {
        return length;
    }

}
