package com.futurebizops.kpi.constants;

public final class SQLQueryConstants {

    SQLQueryConstants() {
    }

    //for all

    public static final String DESIGNATION_QUERY = "select dept.dept_id ,dept.dept_name, desig.desig_id , desig.desig_name, desig.status_cd  from designation desig, department dept where dept.dept_id =desig.dept_id  and dept.status_cd ='A'and desig.dept_id = coalesce(:deptId, desig.dept_id) and desig.desig_name =coalesce (:desigName, desig.desig_name) and desig.status_cd =coalesce (:statusCd, desig.status_cd)order by :sortName asc limit :pageSize offset :pageOffset";
    public static final String DESIGNATION_COUNT_QUERY = "select count(desig.desig_id) from designation desig, department dept where dept.dept_id =desig.dept_id  and dept.status_cd ='A'and desig.dept_id = coalesce(:deptId, desig.dept_id) and desig.desig_name =coalesce (:desigName, desig.desig_name) and desig.status_cd =coalesce (:statusCd, desig.status_cd)";

    public static final String REGION_QUERY = "select region_id ,region_name,remark,status_cd from region where status_cd = 'A' and region_id= coalesce(:regionId, region_id) and region_name = coalesce(:regionName, region_name) and status_cd = coalesce (:statusCd,status_cd) order by :sortName asc limit :pageSize offset :pageOffset";
    public static final String REGION_COUNT_QUERY = "select count(*) from region where status_cd = 'A' and region_id= coalesce(:regionId, region_id) and region_name = coalesce(:regionName, region_name) and status_cd = coalesce (:statusCd,status_cd)";

    public static final String UPDATE_ASSIGNED_JOB_PART = "update part set PART_JOB_ASSIGNED = :totalPartJobAssigned where PART_ID = :partId";


}
