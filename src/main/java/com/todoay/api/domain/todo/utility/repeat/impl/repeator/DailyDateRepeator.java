package com.todoay.api.domain.todo.utility.repeat.impl.repeator;



import com.todoay.api.domain.todo.utility.repeat.DateRepeator;

import java.time.LocalDate;

public class DailyDateRepeator implements DateRepeator {
    @Override
    public LocalDate plus(LocalDate date) {
        return date.plusDays(1);
    }

}
