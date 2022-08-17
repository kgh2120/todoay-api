package com.todoay.api.domain.todo.utility.repeat.impl.repeator;



import com.todoay.api.domain.todo.utility.repeat.DateRepeator;

import java.time.LocalDate;

public class MonthDateRepeator implements DateRepeator {
    @Override
    public LocalDate plus(LocalDate date) {
        return date.plusMonths(1);
    }

}
