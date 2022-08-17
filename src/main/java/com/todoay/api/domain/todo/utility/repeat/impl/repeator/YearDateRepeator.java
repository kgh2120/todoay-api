package com.todoay.api.domain.todo.utility.repeat.impl.repeator;



import com.todoay.api.domain.todo.utility.repeat.DateRepeator;

import java.time.LocalDate;

public class YearDateRepeator implements DateRepeator {
    @Override
    public LocalDate plus(LocalDate date) {
        return date.plusYears(1);
    }
}
