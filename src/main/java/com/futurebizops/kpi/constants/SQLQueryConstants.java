package com.futurebizops.kpi.constants;

public final class SQLQueryConstants {

    SQLQueryConstants() {
    }

    public static final String DD_ROLES_QUERY = "select ro.role_id,ro.role_name,ro.status_cd from roles ro where ro.status_cd = 'A' and ro.role_id != 1";

    //for department
    public static final String DEPARTMENT_QUERY = "select dept.dept_id, dept.dept_name,  dept.remark, dept.status_cd from department dept where dept.status_cd = 'A'  and dept.dept_id = coalesce(:deptId, dept.dept_id) and dept.dept_name LIKE CONCAT('%',:deptName,'%') and dept.status_cd =coalesce (:statusCd, dept.status_cd) order by  :sortName asc limit :pageSize offset :pageOffset";
    public static final String DEPARTMENT_COUNT_UERY = "select count(*) from department dept where  dept.status_cd = 'A' and dept.dept_id = coalesce(:deptId, dept.dept_id) and dept.dept_name = coalesce(:deptName, dept.dept_name) and dept.status_cd =coalesce (:statusCd, dept.status_cd)";

    public static final String EMPLOYEE_TYPE_QUERY = "select emp_type.emp_type_id, emp_type.emp_type_name,  emp_type.remark, emp_type.status_cd from employee_type emp_type where emp_type.status_cd = 'A'  and emp_type.emp_type_id = coalesce(:empTypeId, emp_type.emp_type_id) and emp_type.emp_type_name LIKE CONCAT('%',:empTypeName,'%') and emp_type.status_cd =coalesce (:statusCd, emp_type.status_cd) order by  emp_type.emp_type_name";
    public static final String DEPARTMENT_BY_ID_QUERY = "select dept.dept_id, dept.dept_name, dept.remark, dept.status_cd from department dept where dept.status_cd = 'A' and dept.dept_id = coalesce(:deptId, dept.dept_id)";

    public static final String DEPT_IN_DESIGNATION_QUERY = "select distinct(dept.dept_id) ,dept.dept_name, dept.remark, dept.status_cd from designation desig, department dept where dept.dept_id =desig.dept_id  and dept.status_cd ='A'and desig.dept_id = coalesce(:deptId, desig.dept_id) and desig.status_cd ='A'";

    //get departnment by role id for designation
    public static final String DEPT_BY_ROLE_ID = "select dept.dept_id, dept.dept_name,dept.remark, dept.status_cd from department dept, roles role where dept.status_cd = 'A' order by dept.dept_name";

    public static final String DEPT_SUGGEST = "select dept.dept_id, dept.dept_name, dept.remark, dept.status_cd from department dept, roles role where role.role_id = dept.role_id and dept.status_cd = 'A' and dept.dept_name like :deptName % order by dept.dept_name";

    // get department by role id from designation for kpp
    public static final String DEPT_BY_ROLE_ID_FROM_DESIG = "select	distinct(dept.dept_id),	dept.dept_name,role.role_id,	role.role_name,		dept.remark,	dept.status_cd from	department dept,	roles role,	designation desig where	role.role_id = desig.role_id	and desig.dept_id =dept.dept_id 	and dept.status_cd = 'A'	and desig.role_id = coalesce(:roleId,	desig.role_id) order by	dept.dept_name";

    //for designation

    public static final String DESIGNATION_QUERY = "select desig.desig_id ,  dept.dept_id ,dept.dept_name,  desig.desig_name, desig.remark,desig.status_cd  from designation desig, department dept where  dept.dept_id =desig.dept_id  and dept.status_cd ='A' and desig.dept_id = coalesce(:deptId, desig.dept_id) and desig.desig_name LIKE CONCAT('%',:desigName,'%') and desig.status_cd =coalesce (:statusCd, desig.status_cd) order by :sortName asc limit :pageSize offset :pageOffset";

    public static final String ROLE_IN_DEPARTMENT_QUERY = "select distinct(dept.role_id),role.role_name,role.remark,role.status_cd from	roles role,	department dept where dept.role_id = role.role_id and dept.status_cd = 'A' and role.status_cd = 'A'";
    public static final String ROLE_IN_DESIGNATION_QUERY = "select	distinct(desig.role_id) ,role.role_name,	role.remark,role.status_cd from	roles role,	designation desig where	desig.role_id = role.role_id	and desig.status_cd = 'A' and role.status_cd = 'A'";
    public static final String DESIGNATION_COUNT_QUERY = "select count(*)  from designation desig, department dept where  dept.dept_id =desig.dept_id  and dept.status_cd ='A'and  desig.dept_id = coalesce(:deptId, desig.dept_id) and desig.desig_name =coalesce (:desigName, desig.desig_name) and desig.status_cd =coalesce (:statusCd, desig.status_cd)";

    public static final String DESIGNATION_BY_DEPT_ID_QUERY = "select distinct(desig.desig_id),dept.dept_id,dept.dept_name,desig.desig_name,desig.remark,desig.status_cd from designation desig, department dept where dept.dept_id = desig.dept_id and dept.status_cd = 'A' and desig.dept_id = coalesce(:deptId,desig.dept_id) and desig.status_cd = 'A'";
    public static final String DESIGNATION_BY_DESIG_ID_QUERY = "select desig.desig_id , dept.dept_id ,dept.dept_name,  desig.desig_name, desig.remark,desig.status_cd  from designation desig, department dept where  dept.dept_id =desig.dept_id  and dept.status_cd ='A'and desig.desig_id = coalesce(:desigId, desig.desig_id)";

    //for company master
    public static final String COMPANY_MASTER_QUERY = "select comp.comp_id, comp.region_id, reg.region_name, comp.site_id,si.site_name, comp.comp_name, comp.comp_address, comp.comp_mbno, comp.comp_finance_year, comp.remark, comp.status_cd from company_master comp, region reg, site si where reg.region_id =comp.region_id and si.site_id = comp.site_id and comp.region_id = coalesce(:regionId, comp.region_id) and comp.site_id = coalesce(:siteId, comp.site_id) and comp.comp_name LIKE CONCAT('%',:companyName,'%') and comp.status_cd =coalesce (:statusCd, comp.status_cd) order by :sortName asc limit :pageSize offset :pageOffset";
    public static final String COMPANY_MASTER_COUNT_QUERY = "select count(*) from company_master comp, region reg, site si where reg.region_id =comp.region_id and si.site_id = comp.site_id and comp.region_id = coalesce(:regionId, comp.region_id) and comp.site_id = coalesce(:siteId, comp.site_id) and comp.comp_name LIKE CONCAT('%',:companyName,'%') and comp.status_cd =coalesce (:statusCd, comp.status_cd)";
    public static final String COMPANY_BY_COMP_ID_QUERY = "select comp.comp_id, comp.region_id, reg.region_name, comp.site_id,si.site_name, comp.comp_name, comp.comp_address, comp.comp_mbno, comp.comp_finance_year, comp.remark, comp.status_cd from company_master comp, region reg, site si where reg.region_id =comp.region_id and si.site_id = comp.site_id and comp.comp_id = coalesce(:companyId, comp.comp_id)";
    public static final String DD_COMPANY_BY_COMP_ID_QUERY = "select comp.comp_id, comp.region_id, reg.region_name, comp.site_id,si.site_name, comp.comp_name, comp.comp_address, comp.comp_mbno, comp.comp_finance_year, comp.remark, comp.status_cd from company_master comp, region reg, site si where reg.region_id =comp.region_id and si.site_id = comp.site_id and comp.region_id = coalesce(:regionId, comp.region_id) and comp.site_id = coalesce(:siteId, comp.site_id)";


    //for KPP
    public static final String KPP_QUERY = "select kpp.kpp_id as kppId,kpp.role_id, role.role_name, dept.dept_id,dept.dept_name as deptName,kpp.desig_id,desig.desig_name as desigName,kpp.kpp_objective_no,kpp.kpp_objective as kppObjective,kpp.kpp_performance_indi,kpp.kpp_overall_tar,kpp.kpp_target_period, kpp.uom_id, um.uom_name,kpp.kpp_overall_weightage,kpp.kpp_rating1,kpp.kpp_rating2,kpp.kpp_rating3,kpp.kpp_rating4,kpp.kpp_rating5,kpp.remark,kpp.status_cd from key_perf_parameter  kpp, roles role,department dept, designation desig, uom um where  kpp.role_id=role.role_id   and kpp.dept_id = dept.dept_id   and kpp.desig_id = desig.desig_id and um.uom_id=kpp.uom_id and kpp.status_cd='A'and kpp.kpp_id = coalesce(:kppId, kpp.kpp_id) and kpp.role_id = coalesce(:roleId, kpp.role_id) and desig.dept_id = coalesce(:deptId, desig.dept_id) and desig.desig_id = coalesce(:desigId, desig.desig_id) and kpp.kpp_objective_no LIKE CONCAT('%',:kppObjectiveNo,'%') and kpp.kpp_objective LIKE CONCAT('%',:kppObjective,'%') and kpp.status_cd =coalesce (:statusCd, kpp.status_cd) order by :sortName asc limit :pageSize offset :pageOffset";
    public static final String KPP_COUNT_QUERY = "select	count(*) from	key_perf_parameter kpp,	roles role,	department dept,	designation desig, uom um where   kpp.role_id=role.role_id   and kpp.dept_id = dept.dept_id   and kpp.desig_id = desig.desig_id and um.uom_id=kpp.uom_id	and kpp.status_cd = 'A'	and kpp.kpp_id = coalesce(:kppId,	kpp.kpp_id)	and kpp.role_id = coalesce(:roleId,	kpp.role_id)	and desig.dept_id = coalesce(:deptId,	desig.dept_id)	and desig.desig_id = coalesce(:desigId,	desig.desig_id) and kpp.kpp_objective_no LIKE CONCAT('%',:kppObjectiveNo,'%')	and kpp.kpp_objective = coalesce(:kppObjective,	kpp.kpp_objective)	and kpp.status_cd = coalesce (:statusCd,	kpp.status_cd)";

    public static final String ASSIGN_EMPLOYEE_KPP_COUNT_QUERY = "select	count(*) from	key_perf_parameter";
    //public static final String ASSIGN_EMPLOYEE_KPP = "select kpp.kpp_id as kppId,kpp.role_id, role.role_name, kpp.dept_id,dept.dept_name as deptName,kpp.desig_id,desig.desig_name as desigName,kpp.kpp_objective as kppObjective,kpp.kpp_performance_indi,kpp.kpp_overall_tar,kpp.kpp_target_period, kpp.kpp_unit_of_measu,kpp.kpp_overall_weightage,kpp.kpp_rating1,kpp.kpp_rating2,kpp.kpp_rating3,kpp.kpp_rating4,kpp.kpp_rating5,kpp.remark,kpp.status_cd from key_perf_parameter  kpp, roles role,department dept, designation desig where kpp.role_id=role.role_id   and kpp.dept_id = dept.dept_id   and kpp.desig_id = desig.desig_id and kpp.status_cd='A'and kpp.kpp_id = coalesce(:kppId, kpp.kpp_id) and kpp.role_id = coalesce(:roleId, kpp.role_id) and kpp.dept_id = coalesce(:deptId, kpp.dept_id) and kpp.desig_id = coalesce(:desigId, kpp.desig_id) and kpp.kpp_objective LIKE CONCAT('%',:kppObjective,'%') and kpp.status_cd =coalesce (:statusCd, kpp.status_cd) order by :sortName asc limit :pageSize offset :pageOffset";
    public static final String ASSIGN_EMPLOYEE_KPP = "select kpp.kpp_id as kppId,kpp.role_id,kpp.dept_id,kpp.desig_id,kpp.kpp_objective_no,kpp.kpp_objective as kppObjective,kpp.kpp_performance_indi,kpp.kpp_overall_tar,kpp.kpp_target_period, kpp.uom_id,um.uom_name,kpp.kpp_overall_weightage,kpp.kpp_rating1,kpp.kpp_rating2,kpp.kpp_rating3,kpp.kpp_rating4, kpp.kpp_rating5,kpp.remark,kpp.status_cd from key_perf_parameter  kpp,uom um where  kpp.status_cd='A' and um.uom_id=kpp.uom_id and kpp.kpp_id NOT IN (select ek.kpp_id from employee_kpp_details ek where ek.emp_id=:empId) and kpp.role_id =:roleId and kpp.dept_id =:deptId and kpp.desig_id =:desigId";
    public static final String KPP_BY_ID_QUERY = "select kpp.kpp_id as kppId,kpp.role_id, role.role_name, dept.dept_id,dept.dept_name as deptName,kpp.desig_id,desig.desig_name as desigName,kpp.kpp_objective_no,kpp.kpp_objective as kppObjective,kpp.kpp_performance_indi,kpp.kpp_overall_tar,kpp.kpp_target_period, kpp.uom_id,um.uom_name, kpp.kpp_overall_weightage,kpp.kpp_rating1,kpp.kpp_rating2,kpp.kpp_rating3,kpp.kpp_rating4,kpp.kpp_rating5,kpp.remark,kpp.status_cd from key_perf_parameter  kpp, roles role,department dept, designation desig, uom um where kpp.role_id=role.role_id   and kpp.dept_id = dept.dept_id   and kpp.desig_id = desig.desig_id and um.uom_id=kpp.uom_id and kpp.status_cd='A'and kpp.kpp_id = coalesce(:kppId, kpp.kpp_id)";

    public static final String VIEW_EMPLOYEE_KPP_COUNT = "select count(*) from key_perf_parameter kpp, uom um, employee_kpp_details ek, roles ro, department dept, designation desig, employee emp where kpp.status_cd = 'A' and emp.emp_id =ek.emp_id and um.uom_id = kpp.uom_id and kpp.kpp_id = ek.kpp_id and ek.role_id =ro.role_id and dept.dept_id =ek.dept_id and desig.desig_id =ek.desig_id and ek.emp_id =coalesce (:empId,ek.emp_id) and ek.role_id =coalesce (:roleId,ek.role_id) and ek.dept_id =coalesce (:deptId,ek.dept_id) and ek.desig_id =coalesce(:desigId, ek.desig_id)";
    public static final String VIEW_EMPLOYEE_KPP = "select kpp.kpp_id, ek.role_id, ek.dept_id, ek.desig_id, kpp.kpp_objective_no,kpp.kpp_objective as kppObjective, kpp.kpp_performance_indi, kpp.kpp_overall_tar, kpp.kpp_target_period, kpp.uom_id, um.uom_name, kpp.kpp_overall_weightage, kpp.kpp_rating1, kpp.kpp_rating2, kpp.kpp_rating3, kpp.kpp_rating4, kpp.kpp_rating5, kpp.remark, kpp.status_cd from key_perf_parameter kpp, uom um, employee_kpp_details ek, roles ro, department dept, designation desig, employee emp where kpp.status_cd = 'A' and emp.emp_id =ek.emp_id and um.uom_id = kpp.uom_id and kpp.kpp_id = ek.kpp_id and ek.role_id =ro.role_id and dept.dept_id =ek.dept_id and desig.desig_id =ek.desig_id and ek.emp_id =coalesce (:empId,ek.emp_id) and ek.role_id =coalesce (:roleId,ek.role_id) and ek.dept_id =coalesce (:deptId,ek.dept_id) and ek.desig_id =coalesce(:desigId, ek.desig_id)";

    public static final String VIEW_EMPLOYEE_KPP_DETAILS = "select emp.emp_id, emp.emp_fname , emp.emp_mname, emp.emp_lname, emp.emp_eid, emp.emp_mbno, ek.role_id, ro.role_name, ek.dept_id, dept.dept_name , ek.desig_id, desig.desig_name, kpp.kpp_id, kpp.kpp_objective as kppObjective, kpp.kpp_performance_indi, kpp.kpp_overall_tar, kpp.kpp_target_period, kpp.uom_id, um.uom_name, kpp.kpp_overall_weightage, kpp.kpp_rating1, kpp.kpp_rating2, kpp.kpp_rating3, kpp.kpp_rating4, kpp.kpp_rating5, kpp.remark, kpp.status_cd from key_perf_parameter kpp, uom um, employee_kpp_details ek, roles ro, department dept, designation desig, employee emp where kpp.status_cd = 'A' and emp.emp_id = ek.emp_id and um.uom_id = kpp.uom_id and kpp.kpp_id = ek.kpp_id and ek.role_id = ro.role_id and dept.dept_id = ek.dept_id and desig.desig_id = ek.desig_id and ek.emp_id = coalesce (:empId, ek.emp_id) and ek.role_id = coalesce (:roleId, ek.role_id) and ek.dept_id = coalesce (:deptId, ek.dept_id) and ek.desig_id = coalesce(:desigId, ek.desig_id)";
    //for hod want to give kpp rating to employee
    public static final String EMPLOYEE_KPP_QUERY = "select ekpp.ekpp_did,ekpp.ekpp_month,ekpp.emp_id,ekpp.emp_eid ,ekpp.role_id ,role.role_name,ekpp.dept_id ,dept.dept_name,ekpp.desig_id ,desig.desig_name,ekpp.kpp_id,kpp.kpp_objective,kpp.kpp_performance_indi,kpp.kpp_overall_tar,kpp.kpp_target_period,kpp.kpp_unit_of_measu,kpp.kpp_overall_weightage,ekpp.ekpp_emp_achived_weight,ekpp.ekpp_emp_overall_achieve,ekpp.ekpp_emp_overall_task_comp,ekpp.hod_emp_id,ekpp.ekpp_hod_achived_weight,ekpp.ekpp_hod_overall_achieve,ekpp.ekpp_hod_overall_task_comp,ekpp.gm_emp_id,ekpp.ekpp_gm_achived_weight,ekpp.ekpp_gm_overall_achieve,ekpp.ekpp_gm_overall_task_comp,kpp.kpp_rating1,kpp.kpp_rating2,kpp.kpp_rating3,kpp.kpp_rating4,kpp.kpp_rating5,emp.emp_fname,emp.emp_mname,emp.emp_lname from employee emp,employee_kpp_details ekpp,roles role, department dept,designation desig,key_perf_parameter kpp where ekpp.role_id = role.role_id and ekpp.dept_id = dept.dept_id and ekpp.desig_id = desig.desig_id and ekpp.kpp_id = kpp.kpp_id and ekpp.emp_eid=emp.emp_eid and (ekpp.emp_id =:empId or ekpp.emp_eid=:empEId) and ekpp.status_cd = coalesce (:statusCd,kpp.status_cd)";

    public static final String IN_PROGRESS_EMPLOYEE_KPP_STATUS_INFO_QUERY = "select ekm.ekpp_mid,ekm.ekpp_month,ekm.emp_id,emp.emp_fname,emp.emp_mname,emp.emp_lname,ekm.emp_eid,ekm.role_id,ro.role_name,ekm.dept_id,dept.dept_name,ekm.desig_id,desig.desig_name,ekm.total_emp_achived_weight,ekm.total_emp_overall_achieve,ekm.total_emp_overall_task_comp,ekm.emp_ekpp_applied_date,ekm.emp_ekpp_status,ekm.emp_remark,ekm.hod_emp_id,ekm.total_hod_achived_weight,ekm.total_hod_overall_achieve,ekm.total_hod_overall_task_comp,ekm.hod_ekpp_status,ekm.hod_approved_date,ekm.hod_remark,ekm.gm_emp_id,ekm.total_gm_achived_weight,ekm.total_gm_overall_achieve,ekm.total_gm_overall_task_comp,ekm.gm_ekpp_status,ekm.gm_approved_date,ekm.gm_remark,ekm.remark,ekd.ekpp_did,ekd.kpp_id,ekd.ekpp_emp_achived_weight,ekd.ekpp_emp_overall_achieve,ekd.ekpp_emp_overall_task_comp,ekd.hod_emp_id as hodId,ekd.ekpp_hod_achived_weight,ekd.ekpp_hod_overall_achieve,ekd.ekpp_hod_overall_task_comp,ekd.gm_emp_id as gmId,ekd.ekpp_gm_achived_weight,ekd.ekpp_gm_overall_achieve,ekd.ekpp_gm_overall_task_comp,kpp.kpp_objective,kpp.kpp_performance_indi,kpp.kpp_overall_tar,kpp.kpp_target_period,kpp.uom_id,um.uom_name,kpp.kpp_overall_weightage,kpp.kpp_rating1,kpp.kpp_rating2,kpp.kpp_rating3,kpp.kpp_rating4,kpp.kpp_rating5,emp.comp_id,comp.comp_name,comp.comp_address,comp.comp_mbno,comp.comp_finance_year from employee_kpp_master ekm, employee emp,roles ro,department dept,designation desig,uom um,employee_kpp_details ekd,key_perf_parameter kpp,company_master comp  where ekm.emp_id =ekd.emp_id  and ekm.emp_id=emp.emp_id and ekm.role_id =ro.role_id and ekm.dept_id =dept.dept_id and ekm.desig_id =desig.desig_id and um.uom_id=kpp.uom_id and ekd.kpp_id =kpp.kpp_id and  emp.comp_id = comp.comp_id and ekm.emp_id =:empId";

    public static final String COMPLETED_EMPLOYEE_KPP_STATUS_INFO_QUERY = "select ekm.report_ekpp_id, ekm.ekpp_month, ekm.emp_id, emp.emp_fname, emp.emp_mname, emp.emp_lname, ekm.emp_eid, ekm.role_id, ro.role_name, ekm.dept_id, dept.dept_name, ekm.desig_id, desig.desig_name, ekm.total_emp_achived_weight, ekm.total_emp_overall_achieve, ekm.total_emp_overall_task_comp, ekm.emp_ekpp_applied_date, ekm.emp_ekpp_status, ekm.emp_remark, ekm.hod_emp_id, ekm.total_hod_achived_weight, ekm.total_hod_overall_achieve, ekm.total_hod_overall_task_comp, ekm.hod_ekpp_status, ekm.hod_approved_date, ekm.hod_remark, ekm.gm_emp_id, ekm.total_gm_achived_weight, ekm.total_gm_overall_achieve, ekm.total_gm_overall_task_comp, ekm.gm_ekpp_status, ekm.gm_approved_date, ekm.gm_remark, ekm.remark , ekd.report_ekpp_details_id, ekd.kpp_id, ekd.ekpp_emp_achived_weight, ekd.ekpp_emp_overall_achieve, ekd.ekpp_emp_overall_task_comp, ekd.hod_emp_id as hodId, ekd.ekpp_hod_achived_weight, ekd.ekpp_hod_overall_achieve, ekd.ekpp_hod_overall_task_comp, ekd.gm_emp_id as gmId, ekd.ekpp_gm_achived_weight, ekd.ekpp_gm_overall_achieve, ekd.ekpp_gm_overall_task_comp, kpp.kpp_objective, kpp.kpp_performance_indi, kpp.kpp_overall_tar, kpp.kpp_target_period, kpp.uom_id, um.uom_name, kpp.kpp_overall_weightage, kpp.kpp_rating1, kpp.kpp_rating2, kpp.kpp_rating3, kpp.kpp_rating4, kpp.kpp_rating5, emp.comp_id, comp.comp_name, comp.comp_address, comp.comp_mbno, comp.comp_finance_year from report_employee_kpp_master ekm, employee emp , roles ro, department dept, designation desig, uom um, report_employee_kpp_details ekd, key_perf_parameter kpp, company_master comp where ekm.emp_id = emp.emp_id and ekm.ekpp_month = ekd.ekpp_month and ekm.emp_id =:empId and emp.role_id = ro.role_id and emp.dept_id = dept.dept_id and emp.desig_id = desig.desig_id and date(ekm.ekpp_month) = coalesce(date(:ekkStatusMonth),date(ekm.ekpp_month)) and ekd.kpp_id = kpp.kpp_id and um.uom_id = kpp.uom_id and emp.comp_id = comp.comp_id";
    //old query public static final String COMPLETED_EMPLOYEE_KPP_STATUS_INFO_QUERY = "select ekm.report_ekpp_id,ekm.ekpp_month,ekm.emp_id,emp.emp_fname,emp.emp_mname,emp.emp_lname,ekm.emp_eid,ekm.role_id,ro.role_name,ekm.dept_id,dept.dept_name,ekm.desig_id,desig.desig_name,ekm.total_emp_achived_weight,ekm.total_emp_overall_achieve,ekm.total_emp_overall_task_comp,ekm.emp_ekpp_applied_date,ekm.emp_ekpp_status,ekm.emp_remark,ekm.hod_emp_id,ekm.total_hod_achived_weight,ekm.total_hod_overall_achieve,ekm.total_hod_overall_task_comp,ekm.hod_ekpp_status,ekm.hod_approved_date,ekm.hod_remark,ekm.gm_emp_id,ekm.total_gm_achived_weight,ekm.total_gm_overall_achieve,ekm.total_gm_overall_task_comp,ekm.gm_ekpp_status,ekm.gm_approved_date,ekm.gm_remark,ekm.remark,ekd.report_ekpp_details_id,ekd.kpp_id,ekd.ekpp_emp_achived_weight,ekd.ekpp_emp_overall_achieve,ekd.ekpp_emp_overall_task_comp,ekd.hod_emp_id as hodId,ekd.ekpp_hod_achived_weight,ekd.ekpp_hod_overall_achieve,ekd.ekpp_hod_overall_task_comp,ekd.gm_emp_id as gmId,ekd.ekpp_gm_achived_weight,ekd.ekpp_gm_overall_achieve,ekd.ekpp_gm_overall_task_comp,kpp.kpp_objective,kpp.kpp_performance_indi,kpp.kpp_overall_tar,kpp.kpp_target_period,kpp.uom_id,um.uom_name,kpp.kpp_overall_weightage,kpp.kpp_rating1,kpp.kpp_rating2,kpp.kpp_rating3,kpp.kpp_rating4,kpp.kpp_rating5 from report_employee_kpp_master ekm, employee emp,roles ro,department dept,designation desig,uom um,report_employee_kpp_details ekd,key_perf_parameter kpp  where ekm.emp_id =ekd.emp_id  and ekm.emp_id=emp.emp_id and ekm.role_id =ro.role_id and ekm.dept_id =dept.dept_id and ekm.desig_id =desig.desig_id and um.uom_id=kpp.uom_id and ekd.kpp_id =kpp.kpp_id and ekm.emp_id =:empId and  date(ekm.ekpp_month) = coalesce(date(:ekkStatusMonth),date(ekm.ekpp_month))";

    //for region
    public static final String REGION_QUERY = "select region_id ,region_name,remark,status_cd from region where status_cd = 'A' and region_id= coalesce(:regionId, region_id) and region_name = coalesce(:regionName, region_name) and status_cd = coalesce (:statusCd,status_cd) order by :sortName asc limit :pageSize offset :pageOffset";
    public static final String REGION_COUNT_QUERY = "select count(*) from region where status_cd = 'A' and region_id= coalesce(:regionId, region_id) and region_name = coalesce(:regionName, region_name) and status_cd = coalesce (:statusCd,status_cd)";
    public static final String DD_REGION_QUERY = "select region_id ,region_name,status_cd from region where status_cd = 'A' and region_id= coalesce(:regionId, region_id)";
    //for department
    public static final String SITE_QUERY = "select si.site_id,si.site_name, reg.region_id, reg.region_name, si.remark, si.status_cd from site si, region reg where reg.region_id  = si.region_id  and si.status_cd = 'A' and si.site_id  = coalesce(:siteId,si.site_id) and si.region_id  = coalesce(:regionId,si.region_id) and si.site_name  = coalesce(:siteName,si.site_name) and  si.status_cd  = coalesce(:statusCd,si.status_cd) order by :sortName  limit :pageSize offset :pageOffset";
    public static final String SITE_COUNT_UERY = "select count(*) from site si, region reg where reg.region_id  = si.region_id  and si.status_cd = 'A' and si.site_id  = coalesce(:siteId,si.site_id) and si.region_id  = coalesce(:regionId,si.region_id) and si.site_name  = coalesce(:siteName,si.site_name) and  si.status_cd  = coalesce(:statusCd,si.status_cd)";
    public static final String DD_SITE_QUERY = "select si.site_id,si.site_name,si.status_cd from site si, region reg where reg.region_id  = si.region_id  and si.status_cd = 'A' and si.region_id  = coalesce(:regionId,si.region_id) and si.site_id  = coalesce(:siteId,si.site_id)";
    public static final String SITE_BY_ID_QUERY = "select si.site_id,si.site_name, reg.region_id, reg.region_name, si.remark, si.status_cd from site si, region reg where reg.region_id  = si.region_id  and si.status_cd = 'A' and  si.site_id  = coalesce(:siteId,si.site_id)";
    //for UoM
    public static final String UOM_QUERY = "select uom_id ,uom_name,remark,status_cd from uom where status_cd = 'A' and uom_id= coalesce(:uomId, uom_id) and uom_name = coalesce(:uomName, uom_name) and status_cd = coalesce (:statusCd,status_cd) order by :sortName asc limit :pageSize offset :pageOffset";
    public static final String UOM_COUNT_QUERY = "select count(*) from uom where status_cd = 'A' and uom_id= coalesce(:uomId, uom_id) and uom_name = coalesce(:uomName, uom_name) and status_cd = coalesce (:statusCd,status_cd)";

    // for site
    public static final String DD_REGION_FROM_SITE_QUERY = "select distinct si.region_id , reg.region_name, reg.status_cd from site si , region reg where reg.status_cd = 'A' and reg.region_id =si.region_id order by reg.region_name";



    //for employee
        public static final String EMPLOYEE_QUERY = "select	emp.emp_id,emp.emp_eid,	emp.role_id,	role.role_name,	emp.dept_id,	dept.dept_name,	emp.desig_id,	desig.desig_name,	emp.region_id,	reg.region_name,	emp.site_id,	sit.site_name,	emp.emp_fname,	emp.emp_mname,	emp.emp_lname,	emp.emp_dob,	emp.emp_mbno,	emp.emp_emer_mbno,	emp.emp_photo,	emp.emp_email_id,	emp.emp_temp_address,	emp.emp_perm_address,	emp.emp_gender,	emp.emp_blood_group,	emp.REMARK,	emp.status_cd,emp.reporting_emp_id,emp.emp_type_id,emp_type.emp_type_name, emp.comp_id, comp.comp_name from	employee emp,	roles role,	department dept,	designation desig,	region reg,	site sit, employee_type emp_type, company_master comp where    emp.role_id = role.role_id	and emp.dept_id = dept.dept_id	and desig.desig_id = emp.desig_id and emp_type.emp_type_id = emp.emp_type_id and emp.region_id = reg.region_id	and emp.site_id = sit.site_id and emp.comp_id=comp.comp_id	and emp.emp_id = coalesce (:empId,	emp.emp_id ) and emp.emp_eid LIKE CONCAT('%',:empEId,'%') and emp.role_id = coalesce (:roleId,	emp.role_id)	and desig.dept_id = coalesce(:deptId,	desig.dept_id)	and desig.desig_id = coalesce(:desigId,	desig.desig_id)	and emp.emp_fname LIKE CONCAT('%',:firstName,'%') and emp.emp_mname LIKE CONCAT('%',:middleName,'%') and emp.emp_lname LIKE CONCAT('%',:lastName,'%') and emp.emp_mbno = coalesce(:empMobNo,	emp.emp_mbno)	and emp.emp_email_id = coalesce(:emailId,	emp.emp_email_id)	and emp.status_cd = coalesce(:statusCd,	emp.status_cd) and emp.emp_type_id = coalesce(:empTypeId,emp.emp_type_id) and emp.comp_id = coalesce(:companyId,emp.comp_id) order by	:sortName asc limit :pageSize offset :pageOffset";

    public static final String EMPLOYEE_COUNT_QUERY = "select	count(*) from	employee emp,	roles role,	department dept,	designation desig,	region reg,	site sit,employee_type emp_type, company_master comp where    emp.role_id = role.role_id	and emp.dept_id = dept.dept_id	and desig.desig_id = emp.desig_id and emp_type.emp_type_id = emp.emp_type_id 	and emp.region_id = reg.region_id	and emp.site_id = sit.site_id and emp.comp_id = comp.comp_id	and emp.emp_id = coalesce (:empId,	emp.emp_id ) and  emp.emp_eid LIKE CONCAT('%',:empEId,'%')	and emp.role_id = coalesce (:roleId,	emp.role_id)	and desig.dept_id = coalesce(:deptId,	desig.dept_id)	and desig.desig_id = coalesce(:desigId,	desig.desig_id)	and emp.emp_fname = coalesce(:firstName,	emp.emp_fname)	and emp.emp_mname = coalesce(:middleName,	emp.emp_mname)	and emp.emp_lname = coalesce(:lastName,	emp.emp_lname)	and emp.emp_mbno = coalesce(:empMobNo,	emp.emp_mbno)	and emp.emp_email_id = coalesce(:emailId,	emp.emp_email_id)	and emp.status_cd = coalesce(:statusCd,	emp.status_cd) and  emp.emp_type_id = coalesce(:empTypeId,emp.emp_type_id) and  emp.comp_id = coalesce(:companyId,emp.comp_id)";
  //  public static final String EMPLOYEE_BY_ID_QUERY = "select emp.emp_id,emp.emp_eid, emp.role_id, role.role_name, emp.dept_id,	dept.dept_name,	emp.desig_id,	desig.desig_name,	emp.region_id,	reg.region_name,	emp.site_id,	sit.site_name,	emp.emp_fname,	emp.emp_mname,	emp.emp_lname,	emp.emp_dob,	emp.emp_mbno,	emp.emp_emer_mbno,	emp.emp_photo,	emp.emp_email_id,	emp.emp_temp_address,	emp.emp_perm_address,	emp.emp_gender,	emp.emp_blood_group,	emp.REMARK,	emp.status_cd,emp.reporting_emp_id from	employee emp,	roles role,	department dept,	designation desig,	region reg,	site sit where    emp.role_id = role.role_id	and emp.dept_id = dept.dept_id	and desig.desig_id = emp.desig_id	and emp.region_id = reg.region_id	and emp.site_id = sit.site_id	and emp.emp_id = coalesce (:empId,	emp.emp_id )";
  public static final String EMPLOYEE_BY_ID_QUERY = "select	emp.emp_id,emp.emp_eid,	emp.role_id,	role.role_name,	emp.dept_id,	dept.dept_name,	emp.desig_id,	desig.desig_name,	emp.region_id,	reg.region_name,	emp.site_id,	sit.site_name,	emp.emp_fname,	emp.emp_mname,	emp.emp_lname,	emp.emp_dob,	emp.emp_mbno,	emp.emp_emer_mbno,	emp.emp_photo,	emp.emp_email_id,	emp.emp_temp_address,	emp.emp_perm_address,	emp.emp_gender,	emp.emp_blood_group,	emp.REMARK,	emp.status_cd,emp.reporting_emp_id,emp.emp_type_id,emp_type.emp_type_name, emp.comp_id, comp.comp_name from	employee emp,	roles role,	department dept,	designation desig,	region reg,	site sit, employee_type emp_type, company_master comp where    emp.role_id = role.role_id	and emp.dept_id = dept.dept_id	and desig.desig_id = emp.desig_id and emp_type.emp_type_id = emp.emp_type_id and emp.region_id = reg.region_id	and emp.site_id = sit.site_id and emp.comp_id=comp.comp_id	and emp.emp_id = coalesce (:empId,	emp.emp_id )";
    public static final String EMPLOYEE_SEARCH_BY_ID_QUERY = "select emp.emp_id,emp.emp_eid,	emp.role_id,	role.role_name,	emp.dept_id,	dept.dept_name,	emp.desig_id,	desig.desig_name,	emp.emp_fname,	emp.emp_mname,	emp.emp_lname from	employee emp,	roles role,	department dept,	designation desig where    emp.role_id = role.role_id	and emp.dept_id = dept.dept_id	and desig.desig_id = emp.desig_id	and emp.emp_id = coalesce (:empId,	emp.emp_id ) and 	emp.status_cd='A'";

    //For HOD's Employee details
    //for employee
    public static final String EMPLOYEE_KPP_STATUS_QUERY = "select emp.emp_id,emp.emp_eid,emp.emp_fname,emp.emp_mname,emp.emp_lname,emp.dept_id,dept.dept_name,emp.desig_id,desig.desig_name,emp.emp_mbno,emp.emp_email_id,ekpp.total_emp_overall_achieve,ekpp.emp_ekpp_status,ekpp.total_hod_overall_achieve,ekpp.hod_ekpp_status,ekpp.total_gm_overall_achieve,ekpp.gm_ekpp_status,ekpp.ekpp_month from employee emp,roles role,department dept,designation desig,employee_kpp_master ekpp where ekpp.emp_eid = emp.emp_eid and ekpp.role_id = role.role_id and ekpp.dept_id = dept.dept_id and ekpp.desig_id = desig.desig_id and emp.reporting_emp_id = coalesce(:reportingEmployee,emp.reporting_emp_id) and ekpp.gm_emp_id = coalesce(:gmEmpId,ekpp.gm_emp_id) and emp.emp_id = coalesce(:empId,emp.emp_id ) and emp.emp_eid = coalesce(:empEId,emp.emp_eid ) and emp.role_id = coalesce(:roleId,emp.role_id ) and emp.dept_id = coalesce(:deptId,emp.dept_id ) and emp.desig_id = coalesce(:desigId,emp.desig_id ) and emp.emp_fname = coalesce(:firstName,emp.emp_fname)	and emp.emp_mname = coalesce(:middleName,emp.emp_mname)	and emp.emp_lname = coalesce(:lastName,	emp.emp_lname) and emp.emp_mbno = coalesce(:empMobNo,emp.emp_mbno) and emp.emp_email_id = coalesce(:emailId,emp.emp_email_id) and emp.status_cd = coalesce(:statusCd,emp.status_cd) and ekpp.emp_ekpp_status= coalesce(:empKppStatus,ekpp.emp_ekpp_status) and ekpp.hod_ekpp_status= coalesce(:hodKppStatus,ekpp.hod_ekpp_status) and ekpp.gm_ekpp_status= coalesce(:gmKppStatus,ekpp.gm_ekpp_status) order by	:sortName asc limit :pageSize offset :pageOffset";
    public static final String EMPLOYEE_KPP_STATUS_COUNT_QUERY = "select count(*) from employee emp,roles role,department dept,designation desig,employee_kpp_master ekpp where ekpp.emp_eid = emp.emp_eid and ekpp.role_id = role.role_id and ekpp.dept_id = dept.dept_id and ekpp.desig_id = desig.desig_id and emp.reporting_emp_id = coalesce(:reportingEmployee,emp.reporting_emp_id) and ekpp.gm_emp_id = coalesce(:gmEmpId,ekpp.gm_emp_id) and emp.emp_id = coalesce(:empId,emp.emp_id ) and emp.emp_eid = coalesce(:empEId,emp.emp_eid ) and emp.role_id = coalesce(:roleId,emp.role_id ) and emp.dept_id = coalesce(:deptId,emp.dept_id ) and emp.desig_id = coalesce(:desigId,emp.desig_id ) and emp.emp_fname = coalesce(:firstName,emp.emp_fname)	and emp.emp_mname = coalesce(:middleName,emp.emp_mname)	and emp.emp_lname = coalesce(:lastName,	emp.emp_lname) and emp.emp_mbno = coalesce(:empMobNo,emp.emp_mbno) and emp.emp_email_id = coalesce(:emailId,emp.emp_email_id) and emp.status_cd = coalesce(:statusCd,emp.status_cd) and ekpp.emp_ekpp_status= coalesce(:empKppStatus,ekpp.emp_ekpp_status) and ekpp.hod_ekpp_status= coalesce(:hodKppStatus,ekpp.hod_ekpp_status) and ekpp.gm_ekpp_status= coalesce(:gmKppStatus,ekpp.gm_ekpp_status)";

    public static final String EMPLOYEE_CUMULATIVE_KPP_STATUS_REPORT_QUERY = "select emp.emp_id, emp.emp_eid, emp.emp_fname, emp.emp_mname, emp.emp_lname, emp.dept_id, dept.dept_name, emp.desig_id, desig.desig_name, emp.emp_mbno, emp.emp_email_id, ekpp.total_emp_overall_achieve, ekpp.emp_ekpp_status, ekpp.total_hod_overall_achieve, ekpp.hod_ekpp_status, ekpp.total_gm_overall_achieve, ekpp.gm_ekpp_status, ekpp.ekpp_month from employee emp, roles role, department dept, designation desig, report_employee_kpp_master ekpp where ekpp.emp_eid = emp.emp_eid and emp.role_id = role.role_id and emp.dept_id = dept.dept_id and emp.desig_id = desig.desig_id and date(ekpp.ekpp_month) >= coalesce(date(:startDate), date(ekpp.ekpp_month)) and date(ekpp.ekpp_month) <= coalesce(date(:endDate), date(ekpp.ekpp_month)) and emp.emp_id = coalesce(:empId, emp.emp_id ) and emp.role_id = coalesce(:roleId, emp.role_id ) and emp.status_cd = coalesce(:statusCd, emp.status_cd) order by :sortName asc limit :pageSize offset :pageOffset";
            //"select emp.emp_id,emp.emp_eid,emp.emp_fname,emp.emp_mname,emp.emp_lname,emp.dept_id,dept.dept_name,emp.desig_id,desig.desig_name,emp.emp_mbno,emp.emp_email_id,ekpp.total_emp_overall_achieve,ekpp.emp_ekpp_status,ekpp.total_hod_overall_achieve,ekpp.hod_ekpp_status,ekpp.total_gm_overall_achieve,ekpp.gm_ekpp_status,ekpp.ekpp_month from employee emp,roles role,department dept,designation desig,report_employee_kpp_master ekpp where ekpp.emp_eid = emp.emp_eid and date(ekpp.ekpp_month) >= coalesce(date(:startDate),date(ekpp.ekpp_month)) and date(ekpp.ekpp_month) <= coalesce(date(:endDate),date(ekpp.ekpp_month)) and ekpp.role_id = role.role_id and ekpp.dept_id = dept.dept_id and ekpp.desig_id = desig.desig_id and emp.reporting_emp_id = coalesce(:reportingEmployee,emp.reporting_emp_id) and ekpp.gm_emp_id = coalesce(:gmEmpId,ekpp.gm_emp_id) and emp.emp_id = coalesce(:empId,emp.emp_id ) and emp.emp_eid = coalesce(:empEId,emp.emp_eid ) and emp.role_id = coalesce(:roleId,emp.role_id ) and emp.dept_id = coalesce(:deptId,emp.dept_id ) and emp.desig_id = coalesce(:desigId,emp.desig_id ) and emp.emp_fname = coalesce(:firstName,emp.emp_fname)	and emp.emp_mname = coalesce(:middleName,emp.emp_mname)	and emp.emp_lname = coalesce(:lastName,	emp.emp_lname) and emp.emp_mbno = coalesce(:empMobNo,emp.emp_mbno) and emp.emp_email_id = coalesce(:emailId,emp.emp_email_id) and emp.status_cd = coalesce(:statusCd,emp.status_cd) and ekpp.emp_ekpp_status= coalesce(:empKppStatus,ekpp.emp_ekpp_status) and ekpp.hod_ekpp_status= coalesce(:hodKppStatus,ekpp.hod_ekpp_status) and ekpp.gm_ekpp_status= coalesce(:gmKppStatus,ekpp.gm_ekpp_status) order by	:sortName asc limit :pageSize offset :pageOffset";

    public static final String EMPLOYEE_CUMULATIVE_KPP_STATUS_REPORT_COUNT_QUERY = "select count(*) from employee emp, roles role, department dept, designation desig, report_employee_kpp_master ekpp where ekpp.emp_eid = emp.emp_eid and emp.role_id = role.role_id and emp.dept_id = dept.dept_id and emp.desig_id = desig.desig_id and date(ekpp.ekpp_month) >= coalesce(date(:startDate), date(ekpp.ekpp_month)) and date(ekpp.ekpp_month) <= coalesce(date(:endDate), date(ekpp.ekpp_month)) and emp.emp_id = coalesce(:empId, emp.emp_id ) and emp.role_id = coalesce(:roleId, emp.role_id ) and emp.status_cd = coalesce(:statusCd, emp.status_cd)";
    //public static final String EMPLOYEE_BY_ID_QUERY = "select	emp.emp_id,emp.emp_eid,	emp.role_id,	role.role_name,	emp.dept_id,	dept.dept_name,	emp.desig_id,	desig.desig_name,	emp.region_id,	reg.region_name,	emp.site_id,	sit.site_name,	emp.emp_fname,	emp.emp_mname,	emp.emp_lname,	emp.emp_dob,	emp.emp_mbno,	emp.emp_emer_mbno,	emp.emp_photo,	emp.emp_email_id,	emp.emp_temp_address,	emp.emp_perm_address,	emp.emp_gender,	emp.emp_blood_group,	emp.REMARK,	emp.status_cd from	employee emp,	roles role,	department dept,	designation desig,	region reg,	site sit where    emp.role_id = role.role_id	and emp.dept_id = dept.dept_id	and desig.desig_id = emp.desig_id	and emp.region_id = reg.region_id	and emp.site_id = sit.site_id	and emp.emp_id = coalesce (:empId,	emp.emp_id )";

    //Employee advance search
    public static final String EMPLOYEE_ADVANCE_SEARCH_QUERY = "select emp.emp_id, emp.emp_eid, emp.role_id, role.role_name, emp.dept_id, dept.dept_name, emp.desig_id, desig.desig_name, emp.region_id, reg.region_name, emp.site_id, sit.site_name, emp.emp_fname, emp.emp_mname, emp.emp_lname, emp.emp_dob, emp.emp_mbno, emp.emp_emer_mbno, emp.emp_photo, emp.emp_email_id, emp.emp_temp_address, emp.emp_perm_address, emp.emp_gender, emp.emp_blood_group, emp.REMARK, emp.status_cd, emp.reporting_emp_id, emp.emp_type_id, emp_type.emp_type_name, emp.comp_id, comp.comp_name from employee emp, roles role, department dept, designation desig, region reg, site sit, employee_type emp_type,company_master comp where emp.role_id = role.role_id and emp.dept_id = dept.dept_id and desig.desig_id = emp.desig_id and emp.region_id = reg.region_id and emp.site_id = sit.site_id and emp_type.emp_type_id = emp.emp_type_id and emp.comp_id = comp.comp_id and emp.role_id = coalesce (:roleId, emp.role_id) and emp.dept_id = coalesce(:deptId, emp.dept_id) and emp.desig_id = coalesce(:desigId, emp.desig_id) and emp.region_id = coalesce (:regionId, emp.region_id) and emp.site_id = coalesce(:siteId, emp.site_id) and emp.comp_id = coalesce(:companyId, emp.comp_id) and emp.emp_type_id = coalesce(:empTypeId, emp.emp_type_id)  order by :sortName asc limit :pageSize offset :pageOffset";
    public static final String EMPLOYEE_ADVANCE_SEARCH_COUNT_QUERY = "select count(*) from employee emp, roles role, department dept, designation desig, region reg, site sit, employee_type emp_type where emp.role_id = role.role_id and emp.dept_id = dept.dept_id and desig.desig_id = emp.desig_id and emp.region_id = reg.region_id and emp.site_id = sit.site_id and emp_type.emp_type_id = emp.emp_type_id and emp.role_id = coalesce (:roleId, emp.role_id) and emp.dept_id = coalesce(:deptId, emp.dept_id) and emp.desig_id = coalesce(:desigId, emp.desig_id) and emp.region_id = coalesce (:regionId, emp.region_id) and emp.site_id = coalesce(:siteId, emp.site_id) and emp.comp_id = coalesce(:companyId, emp.comp_id) and emp.emp_type_id = coalesce(:empTypeId, emp.emp_type_id)";
}
