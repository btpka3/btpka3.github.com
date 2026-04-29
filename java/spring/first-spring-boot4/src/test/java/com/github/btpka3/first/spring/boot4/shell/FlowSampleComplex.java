package com.github.btpka3.first.spring.boot4.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.jline.tui.component.flow.ComponentFlow;
import org.springframework.shell.jline.tui.component.flow.SelectItem;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author dangqian.zll
 * @date 2026/4/29
 */
public class FlowSampleComplex {

	@Autowired
	private ComponentFlow.Builder componentFlowBuilder;

	public void runFlow() {
		List<SelectItem> single1SelectItems = Arrays.asList(SelectItem.of("key1", "value1"),
				SelectItem.of("key2", "value2"));
		List<SelectItem> multi1SelectItems = Arrays.asList(SelectItem.of("key1", "value1"),
				SelectItem.of("key2", "value2"), SelectItem.of("key3", "value3"));
		ComponentFlow flow = componentFlowBuilder.clone()
			.reset()
			.withStringInput("field1")
			.name("Field1")
			.defaultValue("defaultField1Value")
			.and()
			.withStringInput("field2")
			.name("Field2")
			.and()
			.withNumberInput("number1")
			.name("Number1")
			.and()
			.withNumberInput("number2")
			.name("Number2")
			.defaultValue(20.5)
			.numberClass(Double.class)
			.and()
			.withConfirmationInput("confirmation1")
			.name("Confirmation1")
			.and()
			.withPathInput("path1")
			.name("Path1")
			.and()
			.withSingleItemSelector("single1")
			.name("Single1")
			.selectItems(single1SelectItems)
			.and()
			.withMultiItemSelector("multi1")
			.name("Multi1")
			.selectItems(multi1SelectItems)
			.and()
			.build();
		flow.run();
	}
}
