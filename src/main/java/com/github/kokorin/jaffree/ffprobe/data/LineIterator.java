/*
 *    Copyright  2018 Denis Kokorin
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.github.kokorin.jaffree.ffprobe.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LineIterator implements Iterator<String> {
    private final BufferedReader reader;
    private String nextLine = null;
    private boolean depleted = false;

    public LineIterator(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public boolean hasNext() {
        if (nextLine != null) {
            return true;
        }

        if (depleted) {
            return false;
        }

        try {
            nextLine = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Read failed", e);
        }

        if (nextLine == null) {
            depleted = true;
            return false;
        }

        return true;
    }

    @Override
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more lines");
        }

        String result = nextLine;
        nextLine = null;
        return result;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Ramove not supported");
    }
}
