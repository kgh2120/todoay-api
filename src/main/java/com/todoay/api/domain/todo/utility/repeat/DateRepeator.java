package com.todoay.api.domain.todo.utility.repeat;

import java.time.LocalDate;

public interface DateRepeator {
    LocalDate plus(LocalDate date);
}
