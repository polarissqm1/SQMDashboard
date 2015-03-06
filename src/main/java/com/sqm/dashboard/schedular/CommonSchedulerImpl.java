package com.sqm.dashboard.schedular;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class CommonSchedulerImpl implements CommonScheduler {

	final Logger log = Logger.getLogger(CommonSchedulerImpl.class);
/*	@Autowired
	private JiraSchedulerImpl jiraScheduler;*/
	private JiraSchedulerImpl jiraScheduler=new JiraSchedulerImpl();
	private JiraSchedulerImpl jiraScheduler1=new JiraSchedulerImpl();
	/*public JiraSchedulerImpl getJiraScheduler() {
		return jiraScheduler;
	}
	public void setJiraScheduler(JiraSchedulerImpl jiraScheduler) {
		this.jiraScheduler = jiraScheduler;
	}*/
	@Override
	public void runScheduler() throws Exception {
		
		/*DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");*/
			try{
				
			/*jiraScheduler1.startJiraInsert();*/
				jiraScheduler.startJiraInsert(jiraScheduler1);
			}catch(Exception e){
			log.error("Exception occured:");
				e.printStackTrace();
			}
		/*System.out.println("Invoked on " + dateFormat.format(System.currentTimeMillis()));*/

	}

}
