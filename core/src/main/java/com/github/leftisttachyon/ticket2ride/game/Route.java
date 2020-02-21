package com.github.leftisttachyon.ticket2ride.game;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * A class that represents a route card.
 *
 * @author Jed Wang
 * @since 1.0.0
 */
@EqualsAndHashCode
public class Route {
    /**
     * Destinations for this route
     */
    private final String dest1, dest2;
    /**
     * The point value for completing this {@link Route}
     */
    @Getter
    private final int value;

    /**
     * Creates a new {@link Route}.
     *
     * @param dest1 one endpoint of this {@link Route}
     * @param dest2 another endpoint of this {@link Route}
     * @param value the point value for this {@link Route}
     */
    public Route(String dest1, String dest2, int value) {
        this.dest1 = dest1;
        this.dest2 = dest2;
        this.value = value;
    }

    /**
     * Determines whether the given {@link Collection} contains an endpoint of this {@link Route}.
     *
     * @param collection the {@link Collection} to check
     * @return whether the given {@link Collection} contains an endpoint
     */
    public boolean containsEndpoint(Collection<String> collection) {
        return collection.contains(dest1) || collection.contains(dest2);
    }

    /**
     * Determines whether this {@link Route} was completed, given a {@link List} of {@link Set}s that represents
     * adjacency.
     *
     * @param network the object that represents adjacency
     * @return whether this {@link Route} is completed with the given information
     */
    public boolean completed(List<Set<String>> network) {
        for (Set<String> list : network) {
            if (list.contains(dest1) && list.contains(dest2))
                return true;
        }
        return false;
    }
}
