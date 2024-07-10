package com.ojt_final.office.domain.search.sort;

public abstract class Direction {

    protected static final String ASC = "ASC";
    protected static final String DESC = "DESC";
    protected static final String NONE = "";

    /**
     * 이미 방향이 있으면 유지하고, 없는 경우 방향을 지정하도록 전환한다.
     *
     * @param current   the current sorting direction
     * @param direction the new sorting direction to toggle to
     * @return the toggled sorting direction
     */
    protected static String toggle(String current, String direction) {
        return current.equals(NONE) ? direction : current;
    }

}
