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
package org.spongepowered.api.event.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.HealEntityEvent;
import org.spongepowered.api.eventgencore.annotation.UseField;
import org.spongepowered.api.util.Tuple;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * An abstract base class for implementations of {@link DamageEntityEvent} and {@link HealEntityEvent}
 * @param <T> The modifier type to use
 */
public abstract class AbstractModifierEvent<T extends Tuple<M, Function<? super Double, Double>>, M> extends AbstractEvent {

    protected double originalFinalAmount;
    protected List<Tuple<M, Double>> originalModifiers;
    protected Map<M, Double> originalModifierMap;
    @UseField protected final LinkedHashMap<M, Double> modifiers = Maps.newLinkedHashMap();
    protected final List<T> modifierFunctions = new ArrayList<>();

    protected ImmutableList<T> init(double originalValue, List<T> originalFunctions) {
        final ImmutableList.Builder<Tuple<M, Double>> modifierMapBuilder = ImmutableList.builder();
        final ImmutableList.Builder<T> functionListBuilder = ImmutableList.builder();
        final ImmutableMap.Builder<M, Double> mapBuilder = ImmutableMap.builder();
        double finalDamage = originalValue;
        for (T tuple : originalFunctions) {
            this.modifierFunctions.add(convertTuple(tuple.getFirst(), tuple.getSecond()));
            double tempDamage = checkNotNull(tuple.getSecond().apply(finalDamage));
            finalDamage += tempDamage;
            modifierMapBuilder.add(new Tuple<>(tuple.getFirst(), tempDamage));
            mapBuilder.put(tuple.getFirst(), tempDamage);
            this.modifiers.put(tuple.getFirst(), tempDamage);
            functionListBuilder.add(convertTuple(tuple.getFirst(), tuple.getSecond()));
        }
        this.originalFinalAmount = finalDamage;
        this.originalModifiers = modifierMapBuilder.build();
        this.originalModifierMap = mapBuilder.build();
        return functionListBuilder.build();
    }

    protected abstract T convertTuple(M obj, Function<? super Double, Double> function);

    protected void recalculateDamages(double baseAmount) {
        double tempAmount = baseAmount;
        this.modifiers.clear();
        for (T entry : this.modifierFunctions) {
            double modifierAmount = checkNotNull(entry.getSecond().apply(tempAmount));
            if (this.modifiers.containsKey(entry.getFirst())) {
                double oldAmount = this.modifiers.get(entry.getFirst());
                double difference = oldAmount - modifierAmount;
                if (oldAmount > 0) {
                    this.modifiers.put(entry.getFirst(), Math.max(0, oldAmount - difference));
                } else {
                    this.modifiers.put(entry.getFirst(), Math.min(0, oldAmount - difference));
                }
            } else {
                this.modifiers.put(entry.getFirst(), modifierAmount);
            }
            tempAmount += modifierAmount;
        }
    }

    protected double getFinalAmount(double baseAmount) {
        double damage = baseAmount;
        for (T entry : this.modifierFunctions) {
            damage += checkNotNull(entry.getSecond().apply(damage));
        }
        return damage;
    }

    public List<T> getModifiers() {
        ImmutableList.Builder<T> builder = ImmutableList.builder();
        for (T entry : this.modifierFunctions) {
            builder.add(entry);
        }
        return builder.build();
    }
}
