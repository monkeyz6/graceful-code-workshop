package com.xk857.main.core.workflow;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * “积木托盘”：按顺序装载步骤
 */
@Getter
@Component
public final class Workflow {
    private final List<Step> steps = new ArrayList<>();

    public Workflow add(Step s) {
        steps.add(s);
        return this;
    }

    public WFIterator iterator() {
        return new WFIterator(0);
    }

    public ReverseIterator reverseIterator(int startIndex) {
        return new ReverseIterator(startIndex);
    }

    // 正向迭代器
    public final class WFIterator implements Iterator<Step> {
        private int cursor;

        public WFIterator(int start) {
            this.cursor = start;
        }

        @Override
        public boolean hasNext() {
            return cursor < steps.size();
        }

        @Override
        public Step next() {
            return steps.get(cursor++);
        }

        public int cursor() {
            return this.cursor;
        }
    }

    // 反向迭代器
    public final class ReverseIterator implements Iterator<Step> {
        private int cursor;

        public ReverseIterator(int start) {
            this.cursor = start;
        }

        @Override
        public boolean hasNext() {
            return cursor >= 0;
        }

        @Override
        public Step next() {
            return steps.get(cursor--);
        }
    }
}
