package com.github.btpka3.bpm.first.flowable;

import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World~");


        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration();
        ProcessEngine processEngine = cfg.buildProcessEngine();
        processEngine.getFormService();

        // 1. 定义 flow
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("holiday-request.bpmn20.xml")
                .deploy();

        // 1. 发布 flow
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();

        System.out.println("Found process definition : " + processDefinition.getName());


        // 1. 创建 flow 实例
        String employee = null;
        Integer nrOfHolidays = 10;
        String description = null;

        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employee", employee);
        variables.put("nrOfHolidays", nrOfHolidays);
        variables.put("description", description);
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey("holidayRequest", variables);

        // 1. FIXME 指定审批人员

        // 1. 获取审批任务
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        System.out.println("You have " + tasks.size() + " tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ") " + tasks.get(i).getName());
        }


        System.out.println("Which task would you like to complete?");
        int taskIndex = 1;
        Task task = tasks.get(taskIndex - 1);
        Map<String, Object> processVariables = taskService.getVariables(task.getId());
        System.out.println(processVariables.get("employee") + " wants " +
                processVariables.get("nrOfHolidays") + " of holidays. Do you approve this?");

        // 1. 通过审批
        boolean approved = true;
        variables = new HashMap<String, Object>();
        variables.put("approved", approved);
        taskService.complete(task.getId(), variables);

        // 1. FIXME 审批结束后自动调用
    }

}



