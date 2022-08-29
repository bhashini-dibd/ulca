package com.ulca.dataset.service;

import com.ulca.dataset.dao.ProcessTrackerDao;
import com.ulca.dataset.dao.TaskTrackerDao;
import com.ulca.dataset.model.Error;
import com.ulca.dataset.model.ProcessTracker;
import com.ulca.dataset.model.TaskTracker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProcessTaskTrackerServiceTest {

    @InjectMocks
    ProcessTaskTrackerService processTaskTrackerService;
    @Mock
    ProcessTrackerDao processTrackerDao;

    @Mock
    TaskTrackerDao taskTrackerDao;

    @Test
    void createProcessTracker() {
        assertInstanceOf(ProcessTracker.class,processTaskTrackerService.createProcessTracker());


    }
    private static Stream<Arguments> updateProcessTrackerParam(){
        return Stream.of(Arguments.of(ProcessTracker.StatusEnum.inprogress),
                         Arguments.of(ProcessTracker.StatusEnum.completed));
    }
    @ParameterizedTest
    @MethodSource("updateProcessTrackerParam")
    void updateProcessTracker(ProcessTracker.StatusEnum status) {
        ProcessTracker processTracker = new ProcessTracker();
        processTracker.setStatus(status.toString());
        when(processTrackerDao.findByServiceRequestNumber("1")).thenReturn(processTracker);
        when(processTrackerDao.save(ArgumentMatchers.any())).thenReturn(new ProcessTracker());
        assertInstanceOf(ProcessTracker.class,processTaskTrackerService.updateProcessTracker("1",status));

    }

    @Test
    void createTaskTracker() {
        when(taskTrackerDao.save(ArgumentMatchers.any(TaskTracker.class))).thenReturn(new TaskTracker());

        assertInstanceOf(TaskTracker.class,processTaskTrackerService.createTaskTracker("test",TaskTracker.ToolEnum.download,TaskTracker.StatusEnum.completed));

    }
    private static Stream<Arguments> updateTaskTrackerParam(){
        return Stream.of(Arguments.of(TaskTracker.StatusEnum.inprogress),
                Arguments.of(TaskTracker.StatusEnum.completed));
    }
    @ParameterizedTest
    @MethodSource("updateTaskTrackerParam")
    void updateTaskTracker(TaskTracker.StatusEnum status) {
        TaskTracker taskTracker = new TaskTracker();
        taskTracker.setStatus(status.toString());
        when(taskTrackerDao.findAllByServiceRequestNumberAndTool("1",TaskTracker.ToolEnum.download.toString())).thenReturn(Collections.singletonList(taskTracker));
        when(taskTrackerDao.save(ArgumentMatchers.any())).thenReturn(new TaskTracker());
        assertInstanceOf(TaskTracker.class,processTaskTrackerService.updateTaskTracker("1",TaskTracker.ToolEnum.download,status));

    }
    private static Stream<Arguments> updateTaskTrackerWithDetailsParam(){
        TaskTracker taskTracker = new TaskTracker();
        taskTracker.setStatus(TaskTracker.StatusEnum.completed.toString());
        return Stream.of(Arguments.of("1",TaskTracker.ToolEnum.publish,TaskTracker.StatusEnum.completed,"test",Collections.singletonList(taskTracker)),
                         Arguments.of("1",TaskTracker.ToolEnum.publish,TaskTracker.StatusEnum.inprogress,"test",Collections.EMPTY_LIST));

    }

    @ParameterizedTest
    @MethodSource("updateTaskTrackerWithDetailsParam")
    void updateTaskTrackerWithDetails(String serviceRequestNumber, TaskTracker.ToolEnum tool, TaskTracker.StatusEnum status, String details, List<TaskTracker> list) {
        TaskTracker taskTracker = new TaskTracker();
        taskTracker.setStatus(status.toString());
    when(taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool.toString())).thenReturn(list);
    when(taskTrackerDao.save(ArgumentMatchers.any(TaskTracker.class))).thenReturn(taskTracker);
    assertInstanceOf(TaskTracker.class,processTaskTrackerService.updateTaskTrackerWithDetails(serviceRequestNumber,tool,status,details));
    }
    private static Stream<Arguments> updateTaskTrackerWithDetailsAndEndTimeParam(){
        TaskTracker taskTracker = new TaskTracker();
        taskTracker.setStatus(TaskTracker.StatusEnum.completed.toString());
        return Stream.of(Arguments.of("1",TaskTracker.ToolEnum.publish,TaskTracker.StatusEnum.completed,"test",Collections.singletonList(taskTracker)),
                Arguments.of("1",TaskTracker.ToolEnum.publish,TaskTracker.StatusEnum.inprogress,"test",Collections.EMPTY_LIST));

    }
    @ParameterizedTest
    @MethodSource("updateTaskTrackerWithDetailsAndEndTimeParam")

    void updateTaskTrackerWithDetailsAndEndTime(String serviceRequestNumber, TaskTracker.ToolEnum tool, TaskTracker.StatusEnum status, String details,List<TaskTracker> list) {

        TaskTracker taskTracker = new TaskTracker();
        taskTracker.setStatus(status.toString());
        when(taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool.toString())).thenReturn(list);
        when(taskTrackerDao.save(ArgumentMatchers.any(TaskTracker.class))).thenReturn(taskTracker);
        assertInstanceOf(TaskTracker.class,processTaskTrackerService.updateTaskTrackerWithDetailsAndEndTime(serviceRequestNumber,tool,status,details));
    }
    private static Stream<Arguments> updateTaskTrackerWithErrorParam(){
        TaskTracker taskTracker = new TaskTracker();
        taskTracker.setStatus(TaskTracker.StatusEnum.completed.toString());
        return Stream.of(Arguments.of("1",TaskTracker.ToolEnum.publish,TaskTracker.StatusEnum.completed,new Error(),Collections.singletonList(taskTracker)),
                Arguments.of("1",TaskTracker.ToolEnum.publish,TaskTracker.StatusEnum.inprogress,new Error(),Collections.EMPTY_LIST));

    }
    @ParameterizedTest
    @MethodSource("updateTaskTrackerWithErrorParam")

    void updateTaskTrackerWithError(String serviceRequestNumber, TaskTracker.ToolEnum tool, TaskTracker.StatusEnum status, Error error, List<TaskTracker> list) {

        TaskTracker taskTracker = new TaskTracker();
        taskTracker.setStatus(status.toString());
        when(taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool.toString())).thenReturn(list);
        when(taskTrackerDao.save(ArgumentMatchers.any(TaskTracker.class))).thenReturn(taskTracker);
        assertInstanceOf(TaskTracker.class,processTaskTrackerService.updateTaskTrackerWithError(serviceRequestNumber,tool,status,error));
    }

    private static Stream<Arguments> updateTaskTrackerWithErrorAndEndTimeParam(){
        TaskTracker taskTracker = new TaskTracker();
        taskTracker.setStatus(TaskTracker.StatusEnum.completed.toString());
        return Stream.of(Arguments.of("1",TaskTracker.ToolEnum.publish,TaskTracker.StatusEnum.completed,new Error(),Collections.singletonList(taskTracker)),
                Arguments.of("1",TaskTracker.ToolEnum.publish,TaskTracker.StatusEnum.inprogress,new Error(),Collections.EMPTY_LIST));

    }
    @ParameterizedTest
    @MethodSource("updateTaskTrackerWithErrorAndEndTimeParam")

    void updateTaskTrackerWithErrorAndEndTime(String serviceRequestNumber, TaskTracker.ToolEnum tool, TaskTracker.StatusEnum status, Error error, List<TaskTracker> list) {

        TaskTracker taskTracker = new TaskTracker();
        taskTracker.setStatus(status.toString());
        when(taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool.toString())).thenReturn(list);
        when(taskTrackerDao.save(ArgumentMatchers.any(TaskTracker.class))).thenReturn(taskTracker);
        assertInstanceOf(TaskTracker.class,processTaskTrackerService.updateTaskTrackerWithErrorAndEndTime(serviceRequestNumber,tool,status,error));
    }

}