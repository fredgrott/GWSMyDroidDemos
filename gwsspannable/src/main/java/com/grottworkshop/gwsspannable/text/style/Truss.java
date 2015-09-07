/*
 * Copyright 2014 Jake Wharton
 * Modifications copyright 2015 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.grottworkshop.gwsspannable.text.style;

import android.text.SpannableStringBuilder;

import java.util.ArrayDeque;
import java.util.Deque;

import static android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE;


/**
 * Truss class, wrapper around SpannableBuilder suggested by jake Wharton
 *
 * An example to get the sentence
 *
 * Thanks Jake,<bold>Truss saves time</bold> and you are great
 *
 * use
 *
 * <code>
 *     Truss t = new Truss()
 *      .append("Thanks Jake, ")
 *      .pushSpan(new StyleSpan(android.graphics.Typeface.BOLD))
 *      .append("Truss saves time")
 *      .popSpan()
 *      .append(" and you are great.");
 *      TextView name = (TextView)findViewById(R.id.tvname);
 *      name.setText(t.build());
 * </code>
 *
 * @author Jake Wharton
 * Created by fgrott on 9/7/2015.
 */
@SuppressWarnings("unused")
public class Truss {

    private final SpannableStringBuilder builder;
    private final Deque<Span> stack;

    public Truss() {
        builder = new SpannableStringBuilder();
        stack = new ArrayDeque<>();
    }

    /**
     *
     * @param string the string to append
     * @return the truss
     */
    public Truss append(String string) {
        builder.append(string);
        return this;
    }

    /**
     *
     * @param charSequence the char sequence to append
     * @return the truss
     */
    public Truss append(CharSequence charSequence) {
        builder.append(charSequence);
        return this;
    }

    /**
     *
     * @param c the char
     * @return the truss
     */
    public Truss append(char c) {
        builder.append(c);
        return this;
    }

    /**
     *
     * @param number the number
     * @return the truss
     */
    public Truss append(int number) {
        builder.append(String.valueOf(number));
        return this;
    }

    /** Starts {@code span} at the current position in the builder. */
    public Truss pushSpan(Object span) {
        stack.addLast(new Span(builder.length(), span));
        return this;
    }

    /** End the most recently pushed span at the current position in the builder. */
    public Truss popSpan() {
        Span span = stack.removeLast();
        builder.setSpan(span.span, span.start, builder.length(), SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }

    /** Create the final {@link CharSequence}, popping any remaining spans. */
    public CharSequence build() {
        while (!stack.isEmpty()) {
            popSpan();
        }
        return builder; // TODO make immutable copy?
    }

    /***
     * the Span class
     */
    private static final class Span {
        final int start;
        final Object span;

        /**
         *
         * @param start the start of the span
         * @param span the object
         */
        public Span(int start, Object span) {
            this.start = start;
            this.span = span;
        }
    }
}
