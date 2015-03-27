package com.sqm.dashboard.schedular.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.sqm.dashboard.schedular.CommonSchedular;
import com.sqm.dashboard.schedular.JiraSchedulerImpl;

public class CommonSchedularImpl implements CommonSchedular {

	final Logger log = Logger.getLogger(CommonSchedularImpl.class);

	@Autowired
	private AlmReleasesSchedularImpl almReleasesSchedularImpl;
	
	@Autowired
	private AlmSchedularImpl almSchedularImpl;
	
	@Autowired
	private JiraSchedulerImpl jiraSchedulerImpl;
	
	@Override
	public void runScheduler() throws Exception {
			try{
				almReleasesSchedularImpl.startAlmReleasesInsert(almReleasesSchedularImpl);
				almSchedularImpl.startAlmInsert(almSchedularImpl);
				jiraSchedulerImpl.startJiraInsert(jiraSchedulerImpl);
			}catch(Exception e){
				log.error("Exception occured:");
				e.printStackTrace();
			}
	}
}
