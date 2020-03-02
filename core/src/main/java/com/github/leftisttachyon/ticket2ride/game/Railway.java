package com.github.leftisttachyon.ticket2ride.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * A class that represents a path between two cities.
 *
 * @author Jed Wang
 * @since 1.0.0
 */
@Getter
public class Railway {
    /**
     * The length of this railway
     */
    private final int length;
    /**
     * The {@link Color} of this railway
     */
    private final Color color;
    /**
     * A destination/endpoint of this railway
     */
    private final String dest1, dest2;
    /**
     * A boolean that stores whether this {@link Railway} has been claimed or not
     */
    @Setter
    private boolean isClaimed = false;

    /**
     * Creates a new {@link Railway}
     *
     * @param length the length of the railway
     * @param color  the color of this railway
     * @param dest1  one destination of this railway
     * @param dest2  another destination of this railway
     */
    @NonNull
    public Railway(int length, Color color, String dest1, String dest2) {
        this.length = length;
        if (color == Color.RAINBOW) {
            throw new IllegalArgumentException("You can't make a rainbow road");
        }
        this.color = color;

        if (dest1.equals(dest2)) {
            throw new IllegalArgumentException("dest1 and dest2 is the same");
        }
        this.dest1 = dest1;
        this.dest2 = dest2;
    }

    /**
     * Determines whether the given {@link String} is an endpoint of this {@link Railway}
     *
     * @param name the {@link String} to check
     * @return whether the given {@link String} is an endpoint
     */
    public boolean isEndpoint(String name) {
        return dest1.equals(name) || dest2.equals(name);
    }

    /**
     * Determines whether the given {@link Collection} contains one of this {@link Railway}'s endpoints
     *
     * @param collection the {@link Collection} to check
     * @return whether the given {@link Collection} contains an endpoint
     */
    public boolean containsEndpoint(Collection<String> collection) {
        return collection.contains(dest1) || collection.contains(dest2);
    }

    /**
     * Returns both destinations as a {@link Set}.
     *
     * @return both destinations as a {@link Set}.
     */
    public Set<String> getDestinations() {
        return Set.of(dest1, dest2);
    }

    /**
     * Given one of the endpoints, returns the other one. If an endpoint is not supplied, then {@code null} is returned.
     *
     * @param dest the first destination
     * @return the other destination
     */
    public String getOtherDestination(String dest) {
        if (dest1.equals(dest)) {
            return dest2;
        } else if (dest2.equals(dest)) {
            return dest1;
        } else return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Railway)) return false;

        Railway railway = (Railway) o;

        if (length != railway.length || color != railway.color)
            return false;

        return Objects.equals(dest1, railway.dest1) && Objects.equals(dest2, railway.dest2) ||
                Objects.equals(dest1, railway.dest2) && Objects.equals(dest2, railway.dest1);
    }

    @Override
    public int hashCode() {
        int result = length;
        result = 31 * result + color.hashCode();
        result = 31 * result + 10 * (dest2.hashCode() + dest1.hashCode());
        result = 31 * result + (isClaimed ? 1 : 0);
        return result;
    }
}
