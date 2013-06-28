package com.haier.openplatform.console.jmx.webapp.action;
 
import com.haier.openplatform.console.issue.webapp.action.BaseIssueAction;
import com.haier.openplatform.console.jmx.domain.HornetqResourcesAuditTrail;
import com.haier.openplatform.console.jmx.model.JmxSearchModel;
import com.haier.openplatform.console.jmx.service.HornetqResourcesOperationService;
import com.haier.openplatform.util.Pager;
/**
 * 查询hornetQueue
 * @author WangJian 
 */
public class QueueListAction extends BaseIssueAction{ 
	private static final long serialVersionUID = 445041250274029349L; 
	private Long rows = 0L;
	private Long page = 0L;
	private Long total = 0L;
	private Long records = 0L;
	private String sord;
	private String sidx; 
	private HornetqResourcesOperationService hornetqResourcesOperationService;
	private Pager<HornetqResourcesAuditTrail> pager = new Pager<HornetqResourcesAuditTrail>();
	private HornetqResourcesAuditTrail hornetqResourcesAuditTrail = new HornetqResourcesAuditTrail();
	public void setHornetqResourcesOperationService(
			HornetqResourcesOperationService hornetqResourcesOperationService) {
		this.hornetqResourcesOperationService = hornetqResourcesOperationService;
	}

	public Long getRows() {
		return rows;
	} 
	public void setRows(Long rows) {
		this.rows = rows;
	} 
	public Long getPage() {
		return page;
	} 
	public void setPage(Long page) {
		this.page = page;
	} 
	public Long getTotal() {
		return total;
	} 
	public void setTotal(Long total) {
		this.total = total;
	} 
	public Long getRecords() {
		return records;
	} 
	public void setRecords(Long records) {
		this.records = records;
	} 
	public String getSord() {
		return sord;
	} 
	public void setSord(String sord) {
		this.sord = sord;
	} 
	public String getSidx() {
		return sidx;
	} 
	public void setSidx(String sidx) {
		this.sidx = sidx;
	} 
	public Pager<HornetqResourcesAuditTrail> getPager() {
		return pager;
	} 
	public void setPager(Pager<HornetqResourcesAuditTrail> pager) {
		this.pager = pager;
	} 
	public HornetqResourcesAuditTrail getHornetqResourcesAuditTrail() {
		return hornetqResourcesAuditTrail;
	} 
	public void setHornetqResourcesAuditTrail(
			HornetqResourcesAuditTrail hornetqResourcesAuditTrail) {
		this.hornetqResourcesAuditTrail = hornetqResourcesAuditTrail;
	}


	@Override
	public String execute() throws Exception { 
		JmxSearchModel jmxSearchModel = new JmxSearchModel();
		jmxSearchModel.setHornetqResourcesAuditTrail(hornetqResourcesAuditTrail); 
		pager.setCurrentPage(page);
		pager.setPageSize(rows);
		jmxSearchModel.setPager(pager);
		this.pager = hornetqResourcesOperationService.findAllByPage(jmxSearchModel);
		this.setTotal(pager.getTotalPages());
		this.setRecords(pager.getTotalRecords());
		return SUCCESS;
	}
}
