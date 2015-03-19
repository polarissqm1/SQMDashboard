package com.sqm.dashboard.schedular.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mongodb.DBCollection;
import com.sqm.dashboard.VO.AlmDomProjRelCycleDetails;
import com.sqm.dashboard.VO.AlmDomainProject;
import com.sqm.dashboard.VO.AlmDomainProjectRelease;
import com.sqm.dashboard.VO.AlmDomainProjectReleaseId;
import com.sqm.dashboard.VO.AlmDomainProjectReleaseName;
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
	
	/*@Autowired*/
	AlmSchedDomainServiceImpl almSchedDomainServiceImpl = new AlmSchedDomainServiceImpl();
	
	/*@Autowired*/
	AlmSchedProjectServiceImpl almSchedProjectServiceImpl = new AlmSchedProjectServiceImpl();

	/*@Autowired*/
	AlmSchedReleaseServiceImpl almSchedReleaseServiceImpl = new AlmSchedReleaseServiceImpl();

	/*@Autowired*/
	AlmDomainProject almDomainProject = new AlmDomainProject();
	
	/*@Autowired*/
	AlmDomainProjectReleaseId almDomainProjectReleaseId = new AlmDomainProjectReleaseId();
	
	/*@Autowired*/
	AlmDomainProjectReleaseName almDomainProjectReleaseName = new AlmDomainProjectReleaseName();

	public void saveReleasesDetails(RestConnectorUtility connection, Map<String, String> requestHeaders, 
									String username, String password, DBCollection collection) throws Exception {
		
		log.info("##AlmReleasesSchedularServiceImpl saveReleasesDetails##");
		HashMap<String,String> jsonDomainsMap = new HashMap<String,String>();
		HashMap<String,String> jsonProjectsMap = null;
		
		ArrayList<AlmDomainProject> almDomainProj = new ArrayList<AlmDomainProject>();
		ArrayList<AlmDomainProjectRelease> almDomainProjRelease = new ArrayList<AlmDomainProjectRelease>();
		ArrayList<AlmDomProjRelCycleDetails> almDomProjRelCycleDetails = new ArrayList<AlmDomProjRelCycleDetails>();
		
		AlmReleaseSchedularDAOImpl almReleaseSchedularDaoImpl = new AlmReleaseSchedularDAOImpl();
		AlmReleaseVO almReleaseVO = new AlmReleaseVO();
		SchedularReleaseCyclesVO schedReleaseCyclesVO = new SchedularReleaseCyclesVO();
		SchedularReleaseDefectsVO schedReleaseDefectsVO = new SchedularReleaseDefectsVO(); 
		
		try {
			jsonDomainsMap = almSchedDomainServiceImpl.getAlmDomains(connection, requestHeaders);
			log.info("jsonDomainsMap : "  + jsonDomainsMap);
			
			Set<String> domains = jsonDomainsMap.keySet();
			log.info("Domains : " + domains);
			
			String domainName = null;
			String projectName = null;
			
			for(String domain : domains){ 
				log.info("Value of "+ domain +" is: "+ jsonDomainsMap.get(domain));
				domainName = jsonDomainsMap.get(domain);
				
				jsonProjectsMap = almSchedProjectServiceImpl.getAlmProjects(connection, requestHeaders, domainName);
				log.info("###jsonProjectsMap### : "  + jsonProjectsMap);
				
				Set<String> projects = jsonProjectsMap.keySet();
				log.info("Projects : " + projects);
				
				for(String project : projects){ 
					log.info("domainName : " + domainName);
					projectName = jsonProjectsMap.get(project);
					log.info("Value of "+ project +" is: "+ jsonProjectsMap.get(project));
					log.info("domainName : " + domainName + "##" + "projectName : " + projectName);
					almDomainProj.add(new AlmDomainProject(domainName, projectName));
				}
			}
			
			log.info("Size of almDomainProj : " + almDomainProj.size());
			
			for(int i=0; i<almDomainProj.size(); i++) {
					
					log.info("Domain #" + i + "##" + almDomainProj.get(i).getDomain());
					log.info("Project #" + i + "##" + almDomainProj.get(i).getProject());
				
					String domain = almDomainProj.get(i).getDomain();
					String project = almDomainProj.get(i).getProject();
				
					log.info("domain : " + domain);
					log.info("project : " + project);
				
				    String releasesUrl = connection.buildEntityCollectionUrl("release", almDomainProj.get(i).getDomain(), almDomainProj.get(i).getProject());
				    log.info("releasesUrl : " + releasesUrl);
					
				    ArrayList<AlmReleaseDetails> releaseDetails = almSchedReleaseServiceImpl.getAlmReleasesDetails(connection, releasesUrl, requestHeaders);
				    log.info("ALM Release Details : " + releaseDetails.toString());
				    
				    for (int j=0; j<releaseDetails.size(); j++) {
				    	almDomainProjRelease.add(new AlmDomainProjectRelease(domain, project, releaseDetails.get(j)));
					}
				    
				    for(int k=0; k<almDomainProjRelease.size(); k++) {
						
				    	log.info("Domain#" + k + "##" + almDomainProjRelease.get(k).getDomain());
						log.info("Project#" + k + "##" + almDomainProjRelease.get(k).getProject());
						log.info("Release Id#" + k + "##" + almDomainProjRelease.get(k).getReleaseDetails().getReleaseId());
						log.info("Release Name#" + k + "##" + almDomainProjRelease.get(k).getReleaseDetails().getReleaseName());
						log.info("Release Start Date#" + k + "##" + almDomainProjRelease.get(k).getReleaseDetails().getRelStartDate());
						log.info("Release End Date#" + k + "##" + almDomainProjRelease.get(k).getReleaseDetails().getRelEndDate());
						
						almReleaseVO.setDomain(almDomainProjRelease.get(k).getDomain());
						almReleaseVO.setProject(almDomainProjRelease.get(k).getProject());
						almReleaseVO.setReleaseId(almDomainProjRelease.get(k).getReleaseDetails().getReleaseId());
						almReleaseVO.setReleaseName(almDomainProjRelease.get(k).getReleaseDetails().getReleaseName());
						almReleaseVO.setReleaseStartDate(almDomainProjRelease.get(k).getReleaseDetails().getRelStartDate());
						almReleaseVO.setReleaseEndDate(almDomainProjRelease.get(k).getReleaseDetails().getRelEndDate());
						almReleaseVO.setReleaseFolder("");
						
						String releaseCyclesUrl = connection.buildEntityCollectionUrl("release-cycle", almDomainProjRelease.get(k).getDomain(), almDomainProjRelease.get(k).getProject());
						log.info("releaseCyclesUrl : " + releaseCyclesUrl);
						    
						ArrayList<AlmReleaseCycleDetails> relCycleDetails = almSchedReleaseServiceImpl.getAlmReleaseCyclesDetails(connection, requestHeaders, releaseCyclesUrl, almDomainProjRelease.get(k).getReleaseDetails().getReleaseId());
					    log.info("ALM Release Cycle Details : " + relCycleDetails.toString());
					    
					    for (int l=0; l<relCycleDetails.size(); l++) {
					    	almDomProjRelCycleDetails.add(new AlmDomProjRelCycleDetails(almDomainProjRelease.get(k).getDomain(), 
					    															almDomainProjRelease.get(k).getProject(),
					    															almDomainProjRelease.get(k).getReleaseDetails(),
					    															relCycleDetails.get(l)));
					    	
					    	log.info("Release Cycle Details : " + l + almDomProjRelCycleDetails.get(l));
						}
					    
						String releaseDefectsUrl = connection.buildEntityCollectionUrl("defect", almDomainProjRelease.get(k).getDomain(), almDomainProjRelease.get(k).getProject());
						log.info("releaseDefectsUrl : " + releaseDefectsUrl);
						
						schedReleaseCyclesVO = almSchedReleaseServiceImpl.getAlmReleaseCyclesData(connection, requestHeaders, releaseCyclesUrl, almDomainProjRelease.get(k).getReleaseDetails().getReleaseId());
						almReleaseVO.setSchedReleaseCyclesVO(schedReleaseCyclesVO);
						
						schedReleaseDefectsVO = almSchedReleaseServiceImpl.getAlmReleaseDefectsData(connection, requestHeaders, releaseDefectsUrl, almDomainProjRelease.get(k).getReleaseDetails().getReleaseId());
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
