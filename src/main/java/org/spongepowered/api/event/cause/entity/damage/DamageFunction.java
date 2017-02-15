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
package org.spongepowered.api.event.cause.entity.damage;

import com.google.common.base.Objects;
import org.spongepowered.api.util.Tuple;

import java.util.function.Function;

public class DamageFunction extends Tuple<DamageModifier, Function<? super Double, Double>> {

    public static final Function<? super Double, Double> ZERO_DAMAGE = value -> 0.0D;

    public static DamageFunction of(DamageModifier first, Function<? super Double, Double> second) {
        return new DamageFunction(first, second);
    }

    /**
     * Creates a new {@link DamageFunction} with the provided
     * {@link DamageModifier}. The caveat is that the provided {@link Function}
     * is by default going to provide {@code 0} damage modifications.
     *
     * @param modifier The damage modifier
     */
    public DamageFunction(DamageModifier modifier) {
        this(modifier, ZERO_DAMAGE);
    }

    /**
     * Creates a new {@link DamageFunction} with the provided
     * {@link DamageModifier} and {@link Function}.
     *
     * @param modifier The modifier
     * @param function The fucntion
     */
    public DamageFunction(DamageModifier modifier, Function<? super Double, Double> function) {
        super(modifier, function);
    }

    /**
     * Gets the {@link DamageModifier} for this function.
     *
     * @return The damage modifier
     * @see #getFirst()
     */
    public DamageModifier getModifier() {
        return getFirst();
    }

    /**
     * Gets the {@link Function} for this function.
     *
     * @return The damage function
     * @see #getSecond()
     */
    public Function<? super Double, Double> getFunction() {
        return getSecond();
    }

    @Override
    public DamageModifier getFirst() {
        return super.getFirst();
    }

    @Override
    public Function<? super Double, Double> getSecond() {
        return super.getSecond();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("modifier", getModifier())
            .add("function", getFunction())
            .toString();
    }
}
