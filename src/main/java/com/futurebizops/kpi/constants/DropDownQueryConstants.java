package com.futurebizops.kpi.constants;

public final class DropDownQueryConstants {
    DropDownQueryConstants(){
    }

    // from comapny master to save region id in   employee
    public static final String DD_REGION_FROM_COMPANY_QUERY = "select distinct cm.region_id , reg.region_name, reg.status_cd from company_master cm , region reg where reg.status_cd = 'A' and reg.region_id =cm.region_id order by reg.region_name";

    //from company master to save site id in employee
    public static final String DD_SITE_FROM_COMPANY_QUERY = "select cm.site_id,si.site_name,si.status_cd from company_master cm, site si where si.site_id  = cm.site_id  and si.status_cd = 'A' and cm.region_id  = coalesce(:regionId,cm.region_id)";

    //from company master to save company id in employee
    public static final String DD_COMPANY_FROM_COMPANY_QUERY = "select cm.comp_id,cm.comp_name,cm.status_cd from company_master cm where cm.status_cd = 'A' and cm.region_id  = coalesce(:regionId,cm.region_id) and cm.site_id  = coalesce(:siteId,cm.site_id)";

//to assign employee for kpp
// from comapny master to save region id in   employee
public static final String DD_REGION_FROM_EMPLOYEE_QUERY = "select distinct emp.region_id , reg.region_name, reg.status_cd from employee emp , region reg where reg.status_cd = 'A' and reg.region_id =emp.region_id order by reg.region_name";

    //from company master to save site id in employee
    public static final String DD_SITE_FROM_EMPLOYEE_QUERY = "select distinct emp.site_id,si.site_name,si.status_cd from employee emp, site si where si.site_id  = emp.site_id  and emp.status_cd = 'A' and emp.region_id  = coalesce(:regionId,emp.region_id)";

    //from company master to save company id in employee
    public static final String DD_COMPANY_FROM_EMPLOYEE_QUERY = "select distinct emp.comp_id,cm.comp_name,cm.status_cd from employee emp, company_master cm where emp.comp_id = cm.comp_id and emp.status_cd = 'A' and emp.region_id  = coalesce(:regionId,emp.region_id) and emp.site_id  = coalesce(:siteId,emp.site_id)";


}
