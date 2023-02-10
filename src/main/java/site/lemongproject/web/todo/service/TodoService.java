package site.lemongproject.web.todo.service;

import site.lemongproject.web.todo.model.dto.DailyFindVo;
import site.lemongproject.web.todo.model.dto.DailyTodoVo;
import site.lemongproject.web.todo.model.dto.MonthFindVo;
import site.lemongproject.web.todo.model.dto.MonthMarkVo;
import site.lemongproject.web.todo.model.vo.Todo;

import java.util.List;
import java.util.Map;

public interface TodoService {

    List<Todo> selectTodo(Todo t);

    int insertTodo(Todo t);

    int deleteTodo(Todo t);

    int clearTodo(Todo t);

    int updateTodo(Todo t);

    int delayTodo(Todo t);

    DailyTodoVo getDaily(DailyFindVo dailyFind);


    List<Todo> calTodo(Todo t);

    int dndTodo2(Todo t);

    int dndTodo3(List<Todo> dndTodoList);

    int dndTodo(Map<String, Object> dndTodo);

    MonthMarkVo getMonthMark(MonthFindVo findVo);
}