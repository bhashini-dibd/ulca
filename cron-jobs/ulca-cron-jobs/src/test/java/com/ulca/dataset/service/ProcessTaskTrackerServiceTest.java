package com.ulca.dataset.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import com.ulca.dataset.dao.ProcessTrackerDao;
import com.ulca.dataset.dao.TaskTrackerDao;
import com.ulca.dataset.model.Error;
import com.ulca.dataset.model.ProcessTracker;
import com.ulca.dataset.model.TaskTracker;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest
class ProcessTaskTrackerServiceTest {



    @InjectMocks
    ProcessTaskTrackerService processTaskTrackerService;
    @Mock
    ProcessTrackerDao processTrackerDao;

    @Mock
    TaskTrackerDao taskTrackerDao;

   
    private static Stream<Arguments> updateProcessTrackerParam(){
        return Stream.of(Arguments.of(ProcessTracker.StatusEnum.inprogress),
                Arguments.of(ProcessTracker.StatusEnum.completed));
    }
    
    
	/*
	 * @ParameterizedTest
	 * 
	 * @MethodSource("updateProcessTrackerParam") void
	 * updateProcessTracker(ProcessTracker.StatusEnum status) { ProcessTracker
	 * processTracker = new ProcessTracker();
	 * processTracker.setStatus(status.toString());
	 * when(processTrackerDao.findByServiceRequestNumber("1")).thenReturn(
	 * processTracker);
	 * when(processTrackerDao.save(ArgumentMatchers.any(ProcessTracker.class))).
	 * thenReturn(new ProcessTracker()); //
	 * assertInstanceOf(ProcessTracker.class,processTaskTrackerService.
	 * updateProcessTracker("1",status));
	 * 
	 * }
	 */

   
 
    private static Stream<Arguments> updateTaskTrackerWithDetailsParam(){
        TaskTracker taskTracker = new TaskTracker();
        taskTracker.setStatus(TaskTracker.StatusEnum.completed.toString());
        return Stream.of(Arguments.of("1",TaskTracker.ToolEnum.publish,TaskTracker.StatusEnum.completed,"test",Collections.singletonList(taskTracker)),
                Arguments.of("1",TaskTracker.ToolEnum.publish,TaskTracker.StatusEnum.inprogress,"test",Collections.EMPTY_LIST));

    }

	/*
	 * @ParameterizedTest
	 * 
	 * @MethodSource("updateTaskTrackerWithDetailsParam") void
	 * updateTaskTrackerWithDetails(String serviceRequestNumber,
	 * TaskTracker.ToolEnum tool, TaskTracker.StatusEnum status, String details,
	 * List<TaskTracker> list) { TaskTracker taskTracker = new TaskTracker();
	 * taskTracker.setStatus(status.toString());
	 * when(taskTrackerDao.findAllByServiceRequestNumberAndTool(
	 * serviceRequestNumber, tool.toString())).thenReturn(list);
	 * when(taskTrackerDao.save(ArgumentMatchers.any(TaskTracker.class))).thenReturn
	 * (taskTracker); //
	 * assertInstanceOf(TaskTracker.class,processTaskTrackerService.
	 * updateTaskTrackerWithDetails(serviceRequestNumber,tool,status,details)); }
	 */
    
    
    
    
    private static Stream<Arguments> updateTaskTrackerWithDetailsAndEndTimeParam(){
        TaskTracker taskTracker = new TaskTracker();
        taskTracker.setStatus(TaskTracker.StatusEnum.completed.toString());
        return Stream.of(Arguments.of("1",TaskTracker.ToolEnum.publish,TaskTracker.StatusEnum.completed,"test",Collections.singletonList(taskTracker)),
                Arguments.of("1",TaskTracker.ToolEnum.publish,TaskTracker.StatusEnum.inprogress,"test",Collections.EMPTY_LIST));

    }
    
    
    
    
	/*
	 * @ParameterizedTest
	 * 
	 * @MethodSource("updateTaskTrackerWithDetailsAndEndTimeParam") void
	 * updateTaskTrackerWithDetailsAndEndTime(String serviceRequestNumber,
	 * TaskTracker.ToolEnum tool, TaskTracker.StatusEnum status, String
	 * details,List<TaskTracker> list) {
	 * 
	 * TaskTracker taskTracker = new TaskTracker();
	 * taskTracker.setStatus(status.toString());
	 * when(taskTrackerDao.findAllByServiceRequestNumberAndTool(
	 * serviceRequestNumber, tool.toString())).thenReturn(list);
	 * when(taskTrackerDao.save(ArgumentMatchers.any(TaskTracker.class))).thenReturn
	 * (taskTracker); //
	 * assertInstanceOf(TaskTracker.class,processTaskTrackerService.
	 * updateTaskTrackerWithDetailsAndEndTime(serviceRequestNumber,tool,status,
	 * details)); }
	 */





}
