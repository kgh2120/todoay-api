package com.todoay.api.domain.todo.utility.inter.repeat;



import com.todoay.api.domain.todo.utility.inter.DateRepeator;

import java.time.LocalDate;

public class MonthDateRepeator implements DateRepeator {
    @Override
    public LocalDate plus(LocalDate date) {
        return date.plusMonths(1);
    }

}
