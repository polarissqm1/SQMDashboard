package com.sqm.dashboard.schedular.impl;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.DBCollection;
import com.sqm.dashboard.VO.AlmDomainProject;
import com.sqm.dashboard.VO.AlmDomainProjectRelease;
import com.sqm.dashboard.VO.AlmReleaseDetails;
import com.sqm.dashboard.VO.AlmTestcaseVO;
import com.sqm.dashboard.VO.AlmVO;
import com.sqm.dashboard.VO.SchedularDefectsVO;
import com.sqm.dashboard.dao.impl.AlmSchedularDAOImpl;
import com.sqm.dashboard.schedular.AlmSchedularService;
import com.sqm.dashboard.util.RestConnectorUtility;

@Service("almSchedSchedularServiceImpl")
public class AlmSchedularServiceImpl implements AlmSchedularService {
	
	final Logger log = Logger.getLogger(AlmSchedularServiceImpl.class);
	
	@Autowired
	private AlmSchedDomainServiceImpl almSchedDomainServiceImpl;
	
	@Autowired
	private AlmSchedProjectServiceImpl almSchedProjectServiceImpl;
	
	@Autowired
	private AlmSchedReleaseServiceImpl almSchedReleaseServiceImpl;
	
	@Autowired
	private AlmSchedDefectServiceImpl almSchedDefectServiceImpl;
	
	@Autowired
	private AlmSchedTestcaseServiceImpl almSchedTestcaseServiceImpl;
	
	@Autowired
	private AlmSchedularDAOImpl almSchedularDaoImpl;
	
	@Autowired
	private AlmTestcaseVO almTCVO;
	
	@Autowired
	private SchedularDefectsVO schedDefectsVO;
	
	@Autowired
	private AlmVO almVO;
	
	@Value("${almEntityRelease}")
	private String almEntityRelease;
	
	@Value("${almEntityDefect}")
	private String almEntityDefect;
	
	@Value("${almEntityRun}")
	private String almEntityRun;
	
	public void saveAlmDetails(RestConnectorUtility conn, Map<String, String> requestHeaders, 
									String username, String password, DBCollection collection) throws Exception {
		
		ArrayList<String> domainsList = new ArrayList<String>();
		ArrayList<String> projectsList = new ArrayList<String>();
		
		ArrayList<AlmDomainProject> almDomainProjArrayList = new ArrayList<AlmDomainProject>();
		ArrayList<AlmDomainProjectRelease> almDomainProjReleaseArrayList = new ArrayList<AlmDomainProjectRelease>();
		
		try {
		
			domainsList = almSchedDomainServiceImpl.getAlmDomains(conn, requestHeaders);
			log.info("domainsList : "  + domainsList);
			
			String domainName = null;
			String projectName = null;
			
			for(int p=0; p<domainsList.size(); p++){
				log.info("Domain is: "+ domainsList.get(p));
				domainName = domainsList.get(p);
				
				projectsList = almSchedProjectServiceImpl.getAlmProjects(conn, requestHeaders, domainName);
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
					
				    String releasesUrl = conn.buildEntityCollectionUrl(almEntityRelease, domain, project);
				    log.info("releasesUrl : " + releasesUrl);
					
				    ArrayList<AlmReleaseDetails> releaseDetailsArrayList = almSchedReleaseServiceImpl.getAlmReleasesDetails(conn, releasesUrl, requestHeaders);
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
			    
						String defectsUrl = conn.buildEntityCollectionUrl(almEntityDefect, almDomainProjReleaseArrayList.get(k).getDomain(), almDomainProjReleaseArrayList.get(k).getProject());
						log.info("defectsUrl : " + defectsUrl);
					
						schedDefectsVO = almSchedDefectServiceImpl.getAlmDefects(conn, requestHeaders, defectsUrl, almDomainProjReleaseArrayList.get(k).getReleaseDetails().getReleaseId());
						
						String testcasesUrl = conn.buildEntityCollectionUrl(almEntityRun, almDomainProjReleaseArrayList.get(k).getDomain(), almDomainProjReleaseArrayList.get(k).getProject());
						log.info("testcasesUrl : " + testcasesUrl);
						
						almTCVO = almSchedTestcaseServiceImpl.getAlmTestcases(conn, requestHeaders, testcasesUrl, almDomainProjReleaseArrayList.get(k).getReleaseDetails().getReleaseId());
						
						String releaseDefectsUrl = conn.buildEntityCollectionUrl(almEntityDefect, almDomainProjReleaseArrayList.get(k).getDomain(), almDomainProjReleaseArrayList.get(k).getProject());
						log.info("releaseDefectsUrl : " + releaseDefectsUrl);
						
						ArrayList<String> defectIds = almSchedReleaseServiceImpl.getAlmReleaseDefectIds(conn, requestHeaders, releaseDefectsUrl, almDomainProjReleaseArrayList.get(k).getReleaseDetails().getReleaseId());
						
						almVO.setDomain(almDomainProjReleaseArrayList.get(k).getDomain());
						almVO.setProject(almDomainProjReleaseArrayList.get(k).getProject());
						almVO.setRelease(almDomainProjReleaseArrayList.get(k).getReleaseDetails().getReleaseName());
						almVO.setRelStartDate(almDomainProjReleaseArrayList.get(k).getReleaseDetails().getRelStartDate());
						almVO.setRelEndDate(almDomainProjReleaseArrayList.get(k).getReleaseDetails().getRelEndDate());
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
						
						almSchedularDaoImpl.validatorInsertion(almVO, collection);
				    }
			}
		} catch (Exception e) {
			log.error("Error in Alm Schedular saveAlmDetails method : " + e.getMessage());
			throw e;
		}
	}
}
