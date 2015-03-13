package com.sqm.dashboard.schedular;

import org.apache.log4j.Logger;

import com.sqm.dashboard.schedular.impl.AlmReleasesSchedularImpl;

public class CommonSchedulerImpl implements CommonScheduler {

	final Logger log = Logger.getLogger(CommonSchedulerImpl.class);

	private JiraSchedulerImpl jiraScheduler=new JiraSchedulerImpl();
	private JiraSchedulerImpl jiraScheduler1=new JiraSchedulerImpl();
	private AlmSchedularImpl almObj=new AlmSchedularImpl();
	private AlmSchedularImpl almObj1=new AlmSchedularImpl();
	AlmReleasesSchedularImpl almRelease = new AlmReleasesSchedularImpl();
	AlmReleasesSchedularImpl almRelease1 = new AlmReleasesSchedularImpl();
	
	@Override
	public void runScheduler() throws Exception {
			try{
				almRelease.startAlmReleasesInsert(almRelease1);
				almObj.startAlmInsert(almObj1);
				jiraScheduler.startJiraInsert(jiraScheduler1);
			}catch(Exception e){
				log.error("Exception occured:");
				e.printStackTrace();
			}
	}
}
