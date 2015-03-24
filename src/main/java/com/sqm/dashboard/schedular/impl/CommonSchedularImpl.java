package com.sqm.dashboard.schedular.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.sqm.dashboard.schedular.CommonSchedular;

public class CommonSchedularImpl implements CommonSchedular {

	final Logger log = Logger.getLogger(CommonSchedularImpl.class);

	@Autowired
	private AlmReleasesSchedularImpl almReleasesSchedularImpl;
	
	@Autowired
	private AlmSchedularImpl almSchedularImpl;
	
	/*private JiraSchedulerImpl jiraScheduler=new JiraSchedulerImpl();
	private JiraSchedulerImpl jiraScheduler1=new JiraSchedulerImpl();*/
	
	@Override
	public void runScheduler() throws Exception {
			try{
				//almReleasesSchedularImpl.startAlmReleasesInsert(almReleasesSchedularImpl);
				almSchedularImpl.startAlmInsert(almSchedularImpl);
				//jiraScheduler.startJiraInsert(jiraScheduler1);
			}catch(Exception e){
				log.error("Exception occured:");
				e.printStackTrace();
			}
	}
}
