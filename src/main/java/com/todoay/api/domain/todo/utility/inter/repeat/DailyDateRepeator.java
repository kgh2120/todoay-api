package com.todoay.api.domain.todo.utility.inter.repeat;



import com.todoay.api.domain.todo.utility.inter.DateRepeator;

import java.time.LocalDate;

public class DailyDateRepeator implements DateRepeator {
    @Override
    public LocalDate plus(LocalDate date) {
        return date.plusDays(1);
    }

}
