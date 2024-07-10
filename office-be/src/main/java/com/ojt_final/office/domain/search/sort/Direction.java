package com.ojt_final.office.domain.search.sort;

public abstract class Direction {

    protected static final String ASC = "ASC";
    protected static final String DESC = "DESC";
    protected static final String NONE = "";

    /**
     * 이미 방향이 지정되어 있으면 유지하고, 방향이 지정되어 있지 않으면 새로 지정하도록 방향을 전환한다.
     *
     * @param current the current sorting direction
     * @param direction the new sorting direction to toggle to
     * @return the toggled sorting direction
     */
    protected static String toggle(String current, String direction) {
        return current.equals(NONE) ? direction : current;
    }

}
