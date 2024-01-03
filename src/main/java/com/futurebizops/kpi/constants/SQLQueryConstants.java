package com.futurebizops.kpi.constants;

public final class SQLQueryConstants {

    SQLQueryConstants() {
    }

    //for department
    public static final String DEPARTMENT_QUERY = "select dept.dept_id, dept.dept_name, role.role_id, role.role_name,  dept.remark, dept.status_cd from department dept, roles role where role.role_id = dept.role_id and dept.status_cd = 'A' and dept.role_id = coalesce(:roleId, dept.role_id) and dept.dept_id = coalesce(:deptId, dept.dept_id) and dept.dept_name LIKE CONCAT('%',:deptName,'%') and dept.status_cd =coalesce (:statusCd, dept.status_cd) order by  :sortName asc limit :pageSize offset :pageOffset";
    public static final String DEPARTMENT_COUNT_UERY = "select count(*) from department dept, roles role where role.role_id = dept.role_id and dept.status_cd = 'A' and dept.role_id = coalesce(:roleId, dept.role_id) and dept.dept_id = coalesce(:deptId, dept.dept_id) and dept.dept_name = coalesce(:deptName, dept.dept_name) and dept.status_cd =coalesce (:statusCd, dept.status_cd)";

    public static final String DEPARTMENT_BY_ID_QUERY = "select role.role_id, role.role_name, dept.dept_id, dept.dept_name, dept.remark, dept.status_cd from department dept, roles role where role.role_id = dept.role_id and dept.status_cd = 'A' and dept.dept_id = coalesce(:deptId, dept.dept_id)";

    public static final String DEPT_IN_DESIGNATION_QUERY = "select distinct(dept.dept_id) ,dept.dept_name, dept.remark, dept.status_cd from designation desig, department dept where dept.dept_id =desig.dept_id  and dept.status_cd ='A'and desig.dept_id = coalesce(:deptId, desig.dept_id) and desig.status_cd ='A'";

    //get departnment by role id for designation
    public static final String DEPT_BY_ROLE_ID = "select dept.dept_id, dept.dept_name,role.role_id, role.role_name,  dept.remark, dept.status_cd from department dept, roles role where role.role_id = dept.role_id and dept.status_cd = 'A' and dept.role_id = coalesce(:roleId, dept.role_id) order by dept.dept_name";

    public static final String DEPT_SUGGEST = "select role.role_id, role.role_name, dept.dept_id, dept.dept_name, dept.remark, dept.status_cd from department dept, roles role where role.role_id = dept.role_id and dept.status_cd = 'A' and dept.dept_name like :deptName % order by dept.dept_name";

    // get department by role id from designation for kpp
    public static final String DEPT_BY_ROLE_ID_FROM_DESIG = "select	distinct(dept.dept_id),	dept.dept_name,role.role_id,	role.role_name,		dept.remark,	dept.status_cd from	department dept,	roles role,	designation desig where	role.role_id = desig.role_id	and desig.dept_id =dept.dept_id 	and dept.status_cd = 'A'	and desig.role_id = coalesce(:roleId,	desig.role_id) order by	dept.dept_name";

    //for designation

    public static final String DESIGNATION_QUERY = "select desig.desig_id , role.role_id , role.role_name, dept.dept_id ,dept.dept_name,  desig.desig_name, desig.remark,desig.status_cd  from designation desig, department dept, roles role where role.role_id=desig.role_id and  dept.dept_id =desig.dept_id  and dept.status_cd ='A'and desig.role_id = coalesce(:roleId, desig.role_id) and desig.dept_id = coalesce(:deptId, desig.dept_id) and desig.desig_name LIKE CONCAT('%',:desigName,'%') and desig.status_cd =coalesce (:statusCd, desig.status_cd) order by :sortName asc limit :pageSize offset :pageOffset";

    public static final String ROLE_IN_DEPARTMENT_QUERY = "select distinct(dept.role_id),role.role_name,role.remark,role.status_cd from	roles role,	department dept where dept.role_id = role.role_id and dept.status_cd = 'A' and role.status_cd = 'A'";
    public static final String ROLE_IN_DESIGNATION_QUERY = "select	distinct(desig.role_id) ,role.role_name,	role.remark,role.status_cd from	roles role,	designation desig where	desig.role_id = role.role_id	and desig.status_cd = 'A' and role.status_cd = 'A'";
    public static final String DESIGNATION_COUNT_QUERY = "select count(*)  from designation desig, department dept, roles role where role.role_id=desig.role_id and  dept.dept_id =desig.dept_id  and dept.status_cd ='A'and desig.role_id = coalesce(:roleId, desig.role_id) and desig.dept_id = coalesce(:deptId, desig.dept_id) and desig.desig_name =coalesce (:desigName, desig.desig_name) and desig.status_cd =coalesce (:statusCd, desig.status_cd)";

    public static final String DESIGNATION_BY_DEPT_ID_QUERY = "select    distinct(desig.desig_id) , role.role_id,    role.role_name,	dept.dept_id ,	dept.dept_name,		desig.desig_name,	desig.remark,	desig.status_cd from	designation desig,	department dept,	roles role where	dept.dept_id = desig.dept_id	and role.role_id = desig.role_id 	and dept.status_cd = 'A'	and desig.dept_id = coalesce(:deptId,	desig.dept_id)	and desig.status_cd = 'A'";
    public static final String DESIGNATION_BY_DESIG_ID_QUERY = "select desig.desig_id , role.role_id , role.role_name, dept.dept_id ,dept.dept_name,  desig.desig_name, desig.remark,desig.status_cd  from designation desig, department dept, roles role where role.role_id=desig.role_id and  dept.dept_id =desig.dept_id  and dept.status_cd ='A'and desig.desig_id = coalesce(:desigId, desig.desig_id)";

    //for KPP
    public static final String KPP_QUERY = "select kpp.kpp_id as kppId,kpp.role_id, role.role_name, dept.dept_id,dept.dept_name as deptName,kpp.desig_id,desig.desig_name as desigName,kpp.kpp_objective as kppObjective,kpp.kpp_performance_indi,kpp.kpp_overall_tar,kpp.kpp_target_period, kpp.kpp_unit_of_measu,kpp.kpp_overall_weightage,kpp.kpp_rating1,kpp.kpp_rating2,kpp.kpp_rating3,kpp.kpp_rating4,kpp.kpp_rating5,kpp.remark,kpp.status_cd from key_perf_parameter  kpp, roles role,department dept, designation desig where kpp.role_id=role.role_id   and kpp.dept_id = dept.dept_id   and kpp.desig_id = desig.desig_id and kpp.status_cd='A'and kpp.kpp_id = coalesce(:kppId, kpp.kpp_id) and kpp.role_id = coalesce(:roleId, kpp.role_id) and desig.dept_id = coalesce(:deptId, desig.dept_id) and desig.desig_id = coalesce(:desigId, desig.desig_id) and kpp.kpp_objective LIKE CONCAT('%',:kppObjective,'%') and kpp.status_cd =coalesce (:statusCd, kpp.status_cd) order by :sortName asc limit :pageSize offset :pageOffset";


    public static final String KPP_COUNT_QUERY = "select	count(*) from	key_perf_parameter kpp,	roles role,	department dept,	designation desig where   kpp.role_id=role.role_id   and kpp.dept_id = dept.dept_id   and kpp.desig_id = desig.desig_id 	and kpp.status_cd = 'A'	and kpp.kpp_id = coalesce(:kppId,	kpp.kpp_id)	and kpp.role_id = coalesce(:roleId,	kpp.role_id)	and desig.dept_id = coalesce(:deptId,	desig.dept_id)	and desig.desig_id = coalesce(:desigId,	desig.desig_id)	and kpp.kpp_objective = coalesce(:kppObjective,	kpp.kpp_objective)	and kpp.status_cd = coalesce (:statusCd,	kpp.status_cd)";
    public static final String KPP_BY_ID_QUERY = "select kpp.kpp_id as kppId,kpp.role_id, role.role_name, dept.dept_id,dept.dept_name as deptName,kpp.desig_id,desig.desig_name as desigName,kpp.kpp_objective as kppObjective,kpp.kpp_performance_indi,kpp.kpp_overall_tar,kpp.kpp_target_period, kpp.kpp_unit_of_measu,kpp.kpp_overall_weightage,kpp.kpp_rating1,kpp.kpp_rating2,kpp.kpp_rating3,kpp.kpp_rating4,kpp.kpp_rating5,kpp.remark,kpp.status_cd from key_perf_parameter  kpp, roles role,department dept, designation desig where kpp.role_id=role.role_id   and kpp.dept_id = dept.dept_id   and kpp.desig_id = desig.desig_id and kpp.status_cd='A'and kpp.kpp_id = coalesce(:kppId, kpp.kpp_id)";

    //for hod want to give kpp rating to employee
    public static final String EMPLOYEE_KPP_QUERY = "select ekpp.ekpp_did,ekpp.ekpp_month,ekpp.emp_id,ekpp.emp_eid ,ekpp.role_id ,role.role_name,ekpp.dept_id ,dept.dept_name,ekpp.desig_id ,desig.desig_name,ekpp.kpp_id,kpp.kpp_objective,kpp.kpp_performance_indi,kpp.kpp_overall_tar,kpp.kpp_target_period,kpp.kpp_unit_of_measu,kpp.kpp_overall_weightage,ekpp.ekpp_emp_achived_weight,ekpp.ekpp_emp_overall_achieve,ekpp.ekpp_emp_overall_task_comp,ekpp.hod_emp_id,ekpp.ekpp_hod_achived_weight,ekpp.ekpp_hod_overall_achieve,ekpp.ekpp_hod_overall_task_comp,ekpp.gm_emp_id,ekpp.ekpp_gm_achived_weight,ekpp.ekpp_gm_overall_achieve,ekpp.ekpp_gm_overall_task_comp,kpp.kpp_rating1,kpp.kpp_rating2,kpp.kpp_rating3,kpp.kpp_rating4,kpp.kpp_rating5,emp.emp_fname,emp.emp_mname,emp.emp_lname from employee emp,employee_kpp_details ekpp,roles role, department dept,designation desig,key_perf_parameter kpp where ekpp.role_id = role.role_id and ekpp.dept_id = dept.dept_id and ekpp.desig_id = desig.desig_id and ekpp.kpp_id = kpp.kpp_id and ekpp.emp_eid=emp.emp_eid and (ekpp.emp_id =:empId or ekpp.emp_eid=:empEId) and ekpp.status_cd = coalesce (:statusCd,kpp.status_cd)";
    //for region
    public static final String REGION_QUERY = "select region_id ,region_name,remark,status_cd from region where status_cd = 'A' and region_id= coalesce(:regionId, region_id) and region_name = coalesce(:regionName, region_name) and status_cd = coalesce (:statusCd,status_cd) order by :sortName asc limit :pageSize offset :pageOffset";
    public static final String REGION_COUNT_QUERY = "select count(*) from region where status_cd = 'A' and region_id= coalesce(:regionId, region_id) and region_name = coalesce(:regionName, region_name) and status_cd = coalesce (:statusCd,status_cd)";
    //for employee
    public static final String EMPLOYEE_QUERY = "select	emp.emp_id,emp.emp_eid,	emp.role_id,	role.role_name,	emp.dept_id,	dept.dept_name,	emp.desig_id,	desig.desig_name,	emp.region_id,	reg.region_name,	emp.site_id,	sit.site_name,	emp.emp_fname,	emp.emp_mname,	emp.emp_lname,	emp.emp_dob,	emp.emp_mbno,	emp.emp_emer_mbno,	emp.emp_photo,	emp.emp_email_id,	emp.emp_temp_address,	emp.emp_perm_address,	emp.emp_gender,	emp.emp_blood_group,	emp.REMARK,	emp.status_cd from	employee emp,	roles role,	department dept,	designation desig,	region reg,	site sit where    emp.role_id = role.role_id	and emp.dept_id = dept.dept_id	and desig.desig_id = emp.desig_id	and emp.region_id = reg.region_id	and emp.site_id = sit.site_id	and emp.emp_id = coalesce (:empId,	emp.emp_id )	and emp.role_id = coalesce (:roleId,	emp.role_id)	and desig.dept_id = coalesce(:deptId,	desig.dept_id)	and desig.desig_id = coalesce(:desigId,	desig.desig_id)	and emp.emp_fname LIKE CONCAT('%',:firstName,'%') and emp.emp_mname LIKE CONCAT('%',:middleName,'%') and emp.emp_lname LIKE CONCAT('%',:lastName,'%') and emp.emp_mbno = coalesce(:empMobNo,	emp.emp_mbno)	and emp.emp_email_id = coalesce(:emailId,	emp.emp_email_id)	and emp.status_cd = coalesce(:statusCd,	emp.status_cd)order by	:sortName asc limit :pageSize offset :pageOffset";

    public static final String EMPLOYEE_COUNT_QUERY = "select	count(*) from	employee emp,	roles role,	department dept,	designation desig,	region reg,	site sit where    emp.role_id = role.role_id	and emp.dept_id = dept.dept_id	and desig.desig_id = emp.desig_id	and emp.region_id = reg.region_id	and emp.site_id = sit.site_id	and emp.emp_id = coalesce (:empId,	emp.emp_id )	and emp.role_id = coalesce (:roleId,	emp.role_id)	and desig.dept_id = coalesce(:deptId,	desig.dept_id)	and desig.desig_id = coalesce(:desigId,	desig.desig_id)	and emp.emp_fname = coalesce(:firstName,	emp.emp_fname)	and emp.emp_mname = coalesce(:middleName,	emp.emp_mname)	and emp.emp_lname = coalesce(:lastName,	emp.emp_lname)	and emp.emp_mbno = coalesce(:empMobNo,	emp.emp_mbno)	and emp.emp_email_id = coalesce(:emailId,	emp.emp_email_id)	and emp.status_cd = coalesce(:statusCd,	emp.status_cd)";
    public static final String EMPLOYEE_BY_ID_QUERY = "select emp.emp_id,emp.emp_eid, emp.role_id, role.role_name, emp.dept_id,	dept.dept_name,	emp.desig_id,	desig.desig_name,	emp.region_id,	reg.region_name,	emp.site_id,	sit.site_name,	emp.emp_fname,	emp.emp_mname,	emp.emp_lname,	emp.emp_dob,	emp.emp_mbno,	emp.emp_emer_mbno,	emp.emp_photo,	emp.emp_email_id,	emp.emp_temp_address,	emp.emp_perm_address,	emp.emp_gender,	emp.emp_blood_group,	emp.REMARK,	emp.status_cd from	employee emp,	roles role,	department dept,	designation desig,	region reg,	site sit where    emp.role_id = role.role_id	and emp.dept_id = dept.dept_id	and desig.desig_id = emp.desig_id	and emp.region_id = reg.region_id	and emp.site_id = sit.site_id	and emp.emp_id = coalesce (:empId,	emp.emp_id )";

    public static final String EMPLOYEE_SEARCH_BY_ID_QUERY ="select emp.emp_id,emp.emp_eid,	emp.role_id,	role.role_name,	emp.dept_id,	dept.dept_name,	emp.desig_id,	desig.desig_name,	emp.emp_fname,	emp.emp_mname,	emp.emp_lname from	employee emp,	roles role,	department dept,	designation desig where    emp.role_id = role.role_id	and emp.dept_id = dept.dept_id	and desig.desig_id = emp.desig_id	and emp.emp_id = coalesce (:empId,	emp.emp_id ) and 	emp.status_cd='A'";

    //For HOD's Employee details
    //for employee
    public static final String EMPLOYEE_KPP_STATUS_QUERY = "select emp.emp_id,emp.emp_eid,emp.emp_fname,emp.emp_mname,emp.emp_lname,emp.dept_id,dept.dept_name,emp.desig_id,desig.desig_name,emp.emp_mbno,emp.emp_email_id,ekpp.total_emp_overall_achieve as ekpp_overall_achieve,ekpp.emp_ekpp_status,ekpp.total_hod_overall_achieve,ekpp.hod_ekpp_status,ekpp.total_gm_overall_achieve,ekpp.gm_ekpp_status from employee emp,roles role,department dept,designation desig,employee_kpp_master ekpp where ekpp.emp_eid = emp.emp_eid and ekpp.role_id = role.role_id and ekpp.dept_id = dept.dept_id and ekpp.desig_id = desig.desig_id and emp.reporting_emp_id = coalesce(:reportingEmployee,emp.reporting_emp_id) and ekpp.gm_emp_id = coalesce(:gmEmpId,ekpp.gm_emp_id) and emp.emp_id = coalesce(:empId,emp.emp_id ) and emp.emp_eid = coalesce(:empEId,emp.emp_eid ) and emp.role_id = coalesce(:roleId,emp.role_id ) and emp.dept_id = coalesce(:deptId,emp.dept_id ) and emp.desig_id = coalesce(:desigId,emp.desig_id ) and emp.emp_fname = coalesce(:firstName,emp.emp_fname)	and emp.emp_mname = coalesce(:middleName,emp.emp_mname)	and emp.emp_lname = coalesce(:lastName,	emp.emp_lname) and emp.emp_mbno = coalesce(:empMobNo,emp.emp_mbno) and emp.emp_email_id = coalesce(:emailId,emp.emp_email_id) and emp.status_cd = coalesce(:statusCd,emp.status_cd) and ekpp.emp_ekpp_status= coalesce(:empKppStatus,ekpp.emp_ekpp_status) and ekpp.hod_ekpp_status= coalesce(:hodKppStatus,ekpp.hod_ekpp_status) and ekpp.gm_ekpp_status= coalesce(:gmKppStatus,ekpp.gm_ekpp_status) order by	:sortName asc limit :pageSize offset :pageOffset";

    public static final String EMPLOYEE_KPP_STATUS_COUNT_QUERY = "select count(*) from employee emp,roles role,department dept,designation desig,employee_kpp_master ekpp where ekpp.emp_eid = emp.emp_eid and ekpp.role_id = role.role_id and ekpp.dept_id = dept.dept_id and ekpp.desig_id = desig.desig_id and emp.reporting_emp_id = coalesce(:reportingEmployee,emp.reporting_emp_id) and ekpp.gm_emp_id = coalesce(:gmEmpId,ekpp.gm_emp_id) and emp.emp_id = coalesce(:empId,emp.emp_id ) and emp.emp_eid = coalesce(:empEId,emp.emp_eid ) and emp.role_id = coalesce(:roleId,emp.role_id ) and emp.dept_id = coalesce(:deptId,emp.dept_id ) and emp.desig_id = coalesce(:desigId,emp.desig_id ) and emp.emp_fname = coalesce(:firstName,emp.emp_fname)	and emp.emp_mname = coalesce(:middleName,emp.emp_mname)	and emp.emp_lname = coalesce(:lastName,	emp.emp_lname) and emp.emp_mbno = coalesce(:empMobNo,emp.emp_mbno) and emp.emp_email_id = coalesce(:emailId,emp.emp_email_id) and emp.status_cd = coalesce(:statusCd,emp.status_cd) and ekpp.emp_ekpp_status= coalesce(:empKppStatus,ekpp.emp_ekpp_status) and ekpp.hod_ekpp_status= coalesce(:hodKppStatus,ekpp.hod_ekpp_status) and ekpp.gm_ekpp_status= coalesce(:gmKppStatus,ekpp.gm_ekpp_status)";
    //public static final String EMPLOYEE_BY_ID_QUERY = "select	emp.emp_id,emp.emp_eid,	emp.role_id,	role.role_name,	emp.dept_id,	dept.dept_name,	emp.desig_id,	desig.desig_name,	emp.region_id,	reg.region_name,	emp.site_id,	sit.site_name,	emp.emp_fname,	emp.emp_mname,	emp.emp_lname,	emp.emp_dob,	emp.emp_mbno,	emp.emp_emer_mbno,	emp.emp_photo,	emp.emp_email_id,	emp.emp_temp_address,	emp.emp_perm_address,	emp.emp_gender,	emp.emp_blood_group,	emp.REMARK,	emp.status_cd from	employee emp,	roles role,	department dept,	designation desig,	region reg,	site sit where    emp.role_id = role.role_id	and emp.dept_id = dept.dept_id	and desig.desig_id = emp.desig_id	and emp.region_id = reg.region_id	and emp.site_id = sit.site_id	and emp.emp_id = coalesce (:empId,	emp.emp_id )";


}
