/*
 * This file is part of SpongeAPI, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.api.event.cause.entity.health;

import com.google.common.base.Objects;
import org.spongepowered.api.util.Tuple;

import java.util.function.Function;

public class HealthFunction extends Tuple<HealthModifier, Function<? super Double, Double>> {

    public static final Function<? super Double, Double> NO_HEALTH = value -> 0.0d;

    public static HealthFunction of(HealthModifier first, Function<? super Double, Double> second) {
        return new HealthFunction(first, second);
    }

    /**
     * Creates a new {@link HealthFunction} with the provided
     * {@link HealthModifier}. The caveat is that the provided {@link Function}
     * is by default going to provide {@code 0} healing modifications.
     *
     * @param modifier The damage modifier
     */
    public HealthFunction(HealthModifier modifier) {
        this(modifier, NO_HEALTH);
    }

    /**
     *          o
     * @param first
     * @param second
     */
    public HealthFunction(HealthModifier first, Function<? super Double, Double> second) {
        super(first, second);
    }

    /**
     * Gets the {@link HealthModifier} for this function.
     *
     * @return The health modifier
     * @see #getFirst()
     */
    public HealthModifier getModifier() {
        return getFirst();
    }

    /**
     * Gets the {@link Function} for this function.
     *
     * @return The healing function
     * @see #getSecond()
     */
    public Function<? super Double, Double> getFunction() {
        return getSecond();
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("HealthModifer", getFirst())
            .add("Function", getSecond())
            .toString();
    }
}
