package com.sqm.dashboard.schedular;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class CommonSchedulerImpl implements CommonScheduler {

	final Logger log = Logger.getLogger(CommonSchedulerImpl.class);

	private JiraSchedulerImpl jiraScheduler=new JiraSchedulerImpl();
	private JiraSchedulerImpl jiraScheduler1=new JiraSchedulerImpl();
	private AlmSchedularImpl almObj=new AlmSchedularImpl();
	private AlmSchedularImpl almObj1=new AlmSchedularImpl();
	@Override
	public void runScheduler() throws Exception {
		
	
			try{
				
				
				almObj.startAlmInsert(almObj1);
				jiraScheduler.startJiraInsert(jiraScheduler1);
			}catch(Exception e){
			log.error("Exception occured:");
				e.printStackTrace();
			}
		

	}

}
