package com.sqm.dashboard.schedular.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mongodb.DBCollection;
import com.sqm.dashboard.VO.AlmDomainProject;
import com.sqm.dashboard.VO.AlmDomainProjectRelease;
import com.sqm.dashboard.VO.AlmDomainProjectReleaseId;
import com.sqm.dashboard.VO.AlmDomainProjectReleaseName;
import com.sqm.dashboard.VO.AlmReleaseDetails;
import com.sqm.dashboard.VO.AlmTestcaseVO;
import com.sqm.dashboard.VO.AlmVO;
import com.sqm.dashboard.VO.SchedularDefectsVO;
import com.sqm.dashboard.VO.SchedularReleaseDefectsVO;
import com.sqm.dashboard.dao.impl.AlmSchedularDAOImpl;
import com.sqm.dashboard.dao.impl.JiraSchedulerDAOImpl;
import com.sqm.dashboard.schedular.AlmSchedularService;
import com.sqm.dashboard.util.RestConnectorUtility;

@Service("almSchedSchedularServiceImpl")
public class AlmSchedularServiceImpl implements AlmSchedularService {
	
	final Logger log = Logger.getLogger(AlmSchedularServiceImpl.class);
	
	/*@Autowired*/
	AlmSchedDomainServiceImpl almSchedDomainServiceImpl = new AlmSchedDomainServiceImpl();
	
	/*@Autowired*/
	AlmSchedProjectServiceImpl almSchedProjectServiceImpl = new AlmSchedProjectServiceImpl();
	
	/*@Autowired*/
	AlmSchedReleaseServiceImpl almSchedReleaseServiceImpl = new AlmSchedReleaseServiceImpl();
	
	/*@Autowired*/
	AlmSchedDefectServiceImpl almSchedDefectServiceImpl = new AlmSchedDefectServiceImpl();
	
	/*@Autowired*/
	AlmSchedTestcaseServiceImpl almSchedTestcaseServiceImpl = new AlmSchedTestcaseServiceImpl();
	
	/*@Autowired*/
	AlmDomainProject almDomainProject = new AlmDomainProject();
	
	/*@Autowired*/
	AlmDomainProjectReleaseId almDomainProjectReleaseId = new AlmDomainProjectReleaseId();
	
	/*@Autowired*/
	AlmDomainProjectReleaseName almDomainProjectReleaseName = new AlmDomainProjectReleaseName();
	
	public void saveAlmDetails(RestConnectorUtility conn, Map<String, String> requestHeaders, 
									String username, String password, DBCollection collection) throws Exception {
		
		HashMap<String,String> jsonDomainsMap = new HashMap<String,String>();
		HashMap<String,String> jsonProjectsMap = null;
		ArrayList<AlmDomainProject> almDomainProj = new ArrayList<AlmDomainProject>();
		
		ArrayList<AlmDomainProjectRelease> almDomainProjRelease = new ArrayList<AlmDomainProjectRelease>();
		
		AlmTestcaseVO almTCVO = new AlmTestcaseVO();
		SchedularDefectsVO schedDefectsVO = new SchedularDefectsVO();
		AlmVO almVO = new AlmVO();
		AlmSchedularDAOImpl almSchedularDAOImpl = new AlmSchedularDAOImpl();
		
		try {
		
			jsonDomainsMap = almSchedDomainServiceImpl.getAlmDomains(conn, requestHeaders);
			log.info("###jsonDomainsMap### : "  + jsonDomainsMap);
			
			Set<String> domains = jsonDomainsMap.keySet();
			log.info("Domains : " + domains);
			
			String domainName = null;
			String projectName = null;
			
			for(String domain : domains){ 
				log.info("Value of "+ domain +" is: "+ jsonDomainsMap.get(domain));
				domainName = jsonDomainsMap.get(domain);
				
				jsonProjectsMap = almSchedProjectServiceImpl.getAlmProjects(conn, requestHeaders, domainName);
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
					
				    String releasesUrl = conn.buildEntityCollectionUrl("release", domain, project);
				    log.info("releasesUrl : " + releasesUrl);
					
				    ArrayList<AlmReleaseDetails> releaseDetails = almSchedReleaseServiceImpl.getAlmReleasesDetails(conn, releasesUrl, requestHeaders);
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
			    
						String defectsUrl = conn.buildEntityCollectionUrl("defect", almDomainProjRelease.get(k).getDomain(), almDomainProjRelease.get(k).getProject());
						log.info("defectsUrl : " + defectsUrl);
					
						schedDefectsVO = almSchedDefectServiceImpl.getAlmDefects(conn, requestHeaders, defectsUrl, almDomainProjRelease.get(k).getReleaseDetails().getReleaseId());
						
						String testcasesUrl = conn.buildEntityCollectionUrl("run", almDomainProjRelease.get(k).getDomain(), almDomainProjRelease.get(k).getProject());
						log.info("testcasesUrl : " + testcasesUrl);
						
						almTCVO = almSchedTestcaseServiceImpl.getAlmTestcases(conn, requestHeaders, testcasesUrl, almDomainProjRelease.get(k).getReleaseDetails().getReleaseId());
						
						String releaseDefectsUrl = conn.buildEntityCollectionUrl("defect", almDomainProjRelease.get(k).getDomain(), almDomainProjRelease.get(k).getProject());
						log.info("releaseDefectsUrl : " + releaseDefectsUrl);
						
						ArrayList<String> defectIds = almSchedReleaseServiceImpl.getAlmReleaseDefectIds(conn, requestHeaders, releaseDefectsUrl, almDomainProjRelease.get(k).getReleaseDetails().getReleaseId());
						
						almVO.setDomain(almDomainProjRelease.get(k).getDomain());
						almVO.setProject(almDomainProjRelease.get(k).getProject());
						almVO.setRelease(almDomainProjRelease.get(k).getReleaseDetails().getReleaseName());
						almVO.setRelStartDate(almDomainProjRelease.get(k).getReleaseDetails().getRelStartDate());
						almVO.setRelEndDate(almDomainProjRelease.get(k).getReleaseDetails().getRelEndDate());
						almVO.setDefectIds(defectIds);
						almVO.setDefectsVO(schedDefectsVO);
						almVO.setAlmTCVO(almTCVO);
						
						log.info(almVO.getDomain() + "|" + 
											almVO.getProject() + "|" + 
											almVO.getRelease() + "|" + 
											almVO.getRelStartDate() + "|" + 
											almVO.getRelEndDate() + "|" + 
											almVO.getDefectIds() + "|" +
											almVO.getDefectsVO() + "|" + 
											almVO.getAlmTCVO());
						
						almSchedularDAOImpl.validatorInsertion(almVO, collection);
				    }
			}
		} catch (Exception e) {
			log.error("Error in Alm Schedular saveAlmDetails method : " + e.getMessage());
			throw e;
		}
	}
}
