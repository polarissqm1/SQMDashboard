package com.sqm.dashboard.schedular.impl;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.DBCollection;
import com.sqm.dashboard.VO.AlmDomProjRelCycleDetails;
import com.sqm.dashboard.VO.AlmDomainProject;
import com.sqm.dashboard.VO.AlmDomainProjectRelease;
import com.sqm.dashboard.VO.AlmReleaseCycleDetails;
import com.sqm.dashboard.VO.AlmReleaseDetails;
import com.sqm.dashboard.VO.AlmReleaseVO;
import com.sqm.dashboard.VO.SchedularReleaseCyclesVO;
import com.sqm.dashboard.VO.SchedularReleaseDefectsVO;
import com.sqm.dashboard.dao.impl.AlmReleaseSchedularDAOImpl;
import com.sqm.dashboard.schedular.AlmReleasesSchedularService;
import com.sqm.dashboard.util.RestConnectorUtility;

@Service("almReleasesSchedularServiceImpl")
public class AlmReleasesSchedularServiceImpl implements AlmReleasesSchedularService {

	final Logger log = Logger.getLogger(AlmReleasesSchedularServiceImpl.class);
	
	@Autowired
	private AlmSchedDomainServiceImpl almSchedDomainServiceImpl;
	
	@Autowired
	private AlmSchedProjectServiceImpl almSchedProjectServiceImpl;

	@Autowired
	private AlmSchedReleaseServiceImpl almSchedReleaseServiceImpl;

	@Autowired
	private AlmDomainProject almDomainProject;
	
	@Autowired
	private AlmReleaseSchedularDAOImpl almReleaseSchedularDaoImpl;
	
	@Autowired
	private AlmReleaseVO almReleaseVO;
	
	@Autowired
	private SchedularReleaseCyclesVO schedReleaseCyclesVO;
	
	@Autowired
	private SchedularReleaseDefectsVO schedReleaseDefectsVO; 
	
	@Value("${almEntityRelease}")
	private String almEntityRelease;
	
	@Value("${almEntityReleaseCycle}")
	private String almEntityReleaseCycle;
	
	@Value("${almEntityDefect}")
	private String almEntityDefect;
	
	public void saveReleasesDetails(RestConnectorUtility connection, Map<String, String> requestHeaders, 
									String username, String password, DBCollection collection) throws Exception {
		
		log.info("##AlmReleasesSchedularServiceImpl saveReleasesDetails##");
		
		ArrayList<String> domainsList = new ArrayList<String>();
		ArrayList<String> projectsList = new ArrayList<String>();
		
		ArrayList<AlmDomainProject> almDomainProjArrayList = new ArrayList<AlmDomainProject>();
		ArrayList<AlmDomainProjectRelease> almDomainProjReleaseArrayList = new ArrayList<AlmDomainProjectRelease>();
		ArrayList<AlmDomProjRelCycleDetails> almDomProjRelCycleDetailsArrayList = new ArrayList<AlmDomProjRelCycleDetails>();
		
		try {
			domainsList = almSchedDomainServiceImpl.getAlmDomains(connection, requestHeaders);
			log.info("domainsList : "  + domainsList);
			
			String domainName = null;
			String projectName = null;
			
			for(int p=0; p<domainsList.size(); p++){
				log.info("Domain is: "+ domainsList.get(p));
				domainName = domainsList.get(p);
				
				projectsList = almSchedProjectServiceImpl.getAlmProjects(connection, requestHeaders, domainName);
				log.info("projectsList : "  + projectsList);
				
				for(int q=0; q<projectsList.size(); q++){
					log.info("domainName : " + domainName);
					projectName = projectsList.get(q);
					log.info("Project is: "+ projectsList.get(q));
					log.info("domainName : " + domainName + "#" + "projectName : " + projectName);
					almDomainProjArrayList.add(new AlmDomainProject(domainName, projectName));
				}
			}
			
			log.info("Size of almDomainProj : " + almDomainProjArrayList.size());
			
			for(int i=0; i<almDomainProjArrayList.size(); i++) {
					
					log.info("Domain #" + i + "##" + almDomainProjArrayList.get(i).getDomain());
					log.info("Project #" + i + "##" + almDomainProjArrayList.get(i).getProject());
				
					String domain = almDomainProjArrayList.get(i).getDomain();
					String project = almDomainProjArrayList.get(i).getProject();
				
					log.info("domain : " + domain);
					log.info("project : " + project);
				
				    String releasesUrl = connection.buildEntityCollectionUrl(almEntityRelease, almDomainProjArrayList.get(i).getDomain(), almDomainProjArrayList.get(i).getProject());
				    log.info("releasesUrl : " + releasesUrl);
					
				    ArrayList<AlmReleaseDetails> releaseDetailsArrayList = almSchedReleaseServiceImpl.getAlmReleasesDetails(connection, releasesUrl, requestHeaders);
				    log.info("ALM Release Details : " + releaseDetailsArrayList.toString());
				    
				    for (int j=0; j<releaseDetailsArrayList.size(); j++) {
				    	almDomainProjReleaseArrayList.add(new AlmDomainProjectRelease(domain, project, releaseDetailsArrayList.get(j)));
					}
				    
				    for(int k=0; k<almDomainProjReleaseArrayList.size(); k++) {
						
				    	log.info("Domain#" + k + "##" + almDomainProjReleaseArrayList.get(k).getDomain());
						log.info("Project#" + k + "##" + almDomainProjReleaseArrayList.get(k).getProject());
						log.info("Release Id#" + k + "##" + almDomainProjReleaseArrayList.get(k).getReleaseDetails().getReleaseId());
						log.info("Release Name#" + k + "##" + almDomainProjReleaseArrayList.get(k).getReleaseDetails().getReleaseName());
						log.info("Release Start Date#" + k + "##" + almDomainProjReleaseArrayList.get(k).getReleaseDetails().getRelStartDate());
						log.info("Release End Date#" + k + "##" + almDomainProjReleaseArrayList.get(k).getReleaseDetails().getRelEndDate());
						
						almReleaseVO.setDomain(almDomainProjReleaseArrayList.get(k).getDomain());
						almReleaseVO.setProject(almDomainProjReleaseArrayList.get(k).getProject());
						almReleaseVO.setReleaseId(almDomainProjReleaseArrayList.get(k).getReleaseDetails().getReleaseId());
						almReleaseVO.setReleaseName(almDomainProjReleaseArrayList.get(k).getReleaseDetails().getReleaseName());
						almReleaseVO.setReleaseStartDate(almDomainProjReleaseArrayList.get(k).getReleaseDetails().getRelStartDate());
						almReleaseVO.setReleaseEndDate(almDomainProjReleaseArrayList.get(k).getReleaseDetails().getRelEndDate());
						almReleaseVO.setReleaseFolder("");
						
						String releaseCyclesUrl = connection.buildEntityCollectionUrl(almEntityReleaseCycle, almDomainProjReleaseArrayList.get(k).getDomain(), almDomainProjReleaseArrayList.get(k).getProject());
						log.info("releaseCyclesUrl : " + releaseCyclesUrl);
						    
						ArrayList<AlmReleaseCycleDetails> relCycleDetailsArrayList = almSchedReleaseServiceImpl.getAlmReleaseCyclesDetails(connection, requestHeaders, releaseCyclesUrl, almDomainProjReleaseArrayList.get(k).getReleaseDetails().getReleaseId());
					    log.info("ALM Release Cycle Details : " + relCycleDetailsArrayList.toString());
					    
					    for (int l=0; l<relCycleDetailsArrayList.size(); l++) {
					    	almDomProjRelCycleDetailsArrayList.add(new AlmDomProjRelCycleDetails(almDomainProjReleaseArrayList.get(k).getDomain(), 
					    															almDomainProjReleaseArrayList.get(k).getProject(),
					    															almDomainProjReleaseArrayList.get(k).getReleaseDetails(),
					    															relCycleDetailsArrayList.get(l)));
					    	
					    	log.info("Release Cycle Details : " + l + almDomProjRelCycleDetailsArrayList.get(l));
						}
					    
						String releaseDefectsUrl = connection.buildEntityCollectionUrl(almEntityDefect, almDomainProjReleaseArrayList.get(k).getDomain(), almDomainProjReleaseArrayList.get(k).getProject());
						log.info("releaseDefectsUrl : " + releaseDefectsUrl);
						
						schedReleaseCyclesVO = almSchedReleaseServiceImpl.getAlmReleaseCyclesData(connection, requestHeaders, releaseCyclesUrl, almDomainProjReleaseArrayList.get(k).getReleaseDetails().getReleaseId());
						almReleaseVO.setSchedReleaseCyclesVO(schedReleaseCyclesVO);
						
						schedReleaseDefectsVO = almSchedReleaseServiceImpl.getAlmReleaseDefectsData(connection, requestHeaders, releaseDefectsUrl, almDomainProjReleaseArrayList.get(k).getReleaseDetails().getReleaseId());
						almReleaseVO.setSchedReleaseDefectsVO(schedReleaseDefectsVO);

						//almReleaseVO.setStatus(status);
						
						log.info(almReleaseVO.getDomain() + "|" + 
									almReleaseVO.getProject() + "|" + 
									almReleaseVO.getReleaseName() + "|" + 
									almReleaseVO.getReleaseStartDate() + "|" + 
									almReleaseVO.getReleaseEndDate() + "|" + 
									almReleaseVO.getReleaseFolder() + "|" + 
									almReleaseVO.getSchedReleaseCyclesVO() + "|" + 
									almReleaseVO.getSchedReleaseDefectsVO());
						
						almReleaseSchedularDaoImpl.storeAlmReleasesToDb(almReleaseVO, collection);
				    }
			}
		} catch (Exception e) {
			log.error("Error in Alm Release Schedular : " + e.getMessage());
			throw e;
		}
	}
}
