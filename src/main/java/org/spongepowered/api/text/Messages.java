/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered.org <http://www.spongepowered.org>
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
package org.spongepowered.api.text;

import org.spongepowered.api.text.translation.Translation;

public final class Messages {

    private static final MessageFactory factory = new NullMessageFactory();

    private Messages() {
    }

    public static <T> MessageBuilder<T> builder(T content) {
        return factory.createBuilder(content);
    }

    public static MessageBuilder<Translation> builder(Translation t, Object... args) {
        return factory.createTranslationBuilder(t, args);
    }

    // TODO: Score API
    public static MessageBuilder<Object> builder(Object score, String override) {
        return factory.createScoreBuilder(score, override);
    }

    public static <T> Message<T> of(T message) {
        return builder(message).build();
    }

    @Deprecated
    public static char getLegacyChar() {
        return factory.getColorChar();
    }

    @Deprecated
    public static Message<?> fromLegacy(String message) {
        return fromLegacy(message, getLegacyChar());
    }

    @Deprecated
    public static Message<?> fromLegacy(String message, char color) {
        return factory.parseCodes(message, color);
    }

    @Deprecated
    public static String stripCodes(String message) {
        return stripCodes(message, getLegacyChar());
    }

    @Deprecated
    public static String stripCodes(String message, char color) {
        return factory.stripCodes(message, color);
    }

    @Deprecated
    public static String translateCodes(String message, char from) {
        return translateCodes(message, from, getLegacyChar());
    }

    @Deprecated
    public static String translateCodes(String message, char from, char to) {
        return factory.translateCodes(message, from, to);
    }
}
