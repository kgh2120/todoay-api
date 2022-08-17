package com.todoay.api.domain.todo.utility.inter;

import java.time.LocalDate;
import java.util.List;

public interface Selector {

    List<LocalDate> select(DateRepeator dateRepeator, int count, LocalDate repeatStandardDate);



}
