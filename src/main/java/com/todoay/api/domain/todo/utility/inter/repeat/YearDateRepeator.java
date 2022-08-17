package com.todoay.api.domain.todo.utility.inter.repeat;



import com.todoay.api.domain.todo.utility.inter.DateRepeator;

import java.time.LocalDate;

public class YearDateRepeator implements DateRepeator {
    @Override
    public LocalDate plus(LocalDate date) {
        return date.plusYears(1);
    }
}
