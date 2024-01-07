package org.sopt.sweet.domain.room.constant;

public enum TournamentDuration {
    SIX_HOURS(6),
    TWELVE_HOURS(12),
    EIGHTEEN_HOURS(18),
    TWENTY_FOUR_HOURS(24);

    private final int hours;

    TournamentDuration(int hours) {
        this.hours = hours;
    }

    public int getHours() {
        return hours;
    }
}
