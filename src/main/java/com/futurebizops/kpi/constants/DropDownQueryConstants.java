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
}
