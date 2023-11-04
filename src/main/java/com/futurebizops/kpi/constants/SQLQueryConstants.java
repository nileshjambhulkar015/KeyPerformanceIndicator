package com.futurebizops.kpi.constants;

public final class SQLQueryConstants {

    SQLQueryConstants() {
    }

    //for department
    public static final String DEPARTMENT_QUERY = "select role.role_id, role.role_name, dept.dept_id, dept.dept_name, dept.remark, dept.status_cd from department dept, roles role where role.role_id = dept.role_id and dept.status_cd = 'A' and dept.role_id = coalesce(:roleId, dept.role_id) and dept.dept_id = coalesce(:deptId, dept.dept_id) and dept.dept_name = coalesce(:deptName, dept.dept_name) and dept.status_cd =coalesce (:statusCd, dept.status_cd) order by  :sortName asc limit :pageSize offset :pageOffset";
    public static final String DEPARTMENT_COUNT_UERY = "select count(*) from department dept, roles role where role.role_id = dept.role_id and dept.status_cd = 'A' and dept.role_id = coalesce(:roleId, dept.role_id) and dept.dept_id = coalesce(:deptId, dept.dept_id) and dept.dept_name = coalesce(:deptName, dept.dept_name) and dept.status_cd =coalesce (:statusCd, dept.status_cd)";

    public static final String DEPARTMENT_BY_ID_QUERY = "select role.role_id, role.role_name, dept.dept_id, dept.dept_name, dept.remark, dept.status_cd from department dept, roles role where role.role_id = dept.role_id and dept.status_cd = 'A' and dept.dept_id = coalesce(:deptId, dept.dept_id)";

    public static final String DEPT_IN_DESIGNATION_QUERY = "select distinct(dept.dept_id) ,dept.dept_name, dept.remark, dept.status_cd from designation desig, department dept where dept.dept_id =desig.dept_id  and dept.status_cd ='A'and desig.dept_id = coalesce(:deptId, desig.dept_id) and desig.status_cd ='A'";

    //get departnment by role id for designation
    public static final String  DEPT_BY_ROLE_ID ="select role.role_id, role.role_name, dept.dept_id, dept.dept_name, dept.remark, dept.status_cd from department dept, roles role where role.role_id = dept.role_id and dept.status_cd = 'A' and dept.role_id = coalesce(:roleId, dept.role_id) order by dept.dept_name";
    //for designation

    public static final String DESIGNATION_QUERY = "select desig.desig_id , role.role_id , role.role_name, dept.dept_id ,dept.dept_name,  desig.desig_name, desig.remark,desig.status_cd  from designation desig, department dept, roles role where role.role_id=desig.role_id and  dept.dept_id =desig.dept_id  and dept.status_cd ='A'and desig.role_id = coalesce(:roleId, desig.role_id) and desig.dept_id = coalesce(:deptId, desig.dept_id) and desig.desig_name =coalesce (:desigName, desig.desig_name) and desig.status_cd =coalesce (:statusCd, desig.status_cd) order by :sortName asc limit :pageSize offset :pageOffset";

    public static final String ROLE_IN_DEPARTMENT_QUERY = "select	distinct(dept.role_id) ,role.role_name,	role.remark,role.status_cd from	roles role,	department dept where	dept.role_id = role.role_id	and dept.status_cd = 'A' and role.status_cd = 'A'";
    public static final String DESIGNATION_COUNT_QUERY = "select count(*)  from designation desig, department dept, roles role where role.role_id=desig.role_id and  dept.dept_id =desig.dept_id  and dept.status_cd ='A'and desig.role_id = coalesce(:roleId, desig.role_id) and desig.dept_id = coalesce(:deptId, desig.dept_id) and desig.desig_name =coalesce (:desigName, desig.desig_name) and desig.status_cd =coalesce (:statusCd, desig.status_cd)";

    public static final String DESIGNATION_BY_DEPT_ID_QUERY ="select dept.dept_id ,dept.dept_name, desig.desig_id , desig.desig_name, desig.remark,desig.status_cd  from designation desig, department dept where dept.dept_id =desig.dept_id  and dept.status_cd ='A'and desig.dept_id = coalesce(:deptId, desig.dept_id) and desig.status_cd ='A'";
    public static final String DESIGNATION_BY_DESIG_ID_QUERY = "select desig.desig_id , role.role_id , role.role_name, dept.dept_id ,dept.dept_name,  desig.desig_name, desig.remark,desig.status_cd  from designation desig, department dept, roles role where role.role_id=desig.role_id and  dept.dept_id =desig.dept_id  and dept.status_cd ='A'and desig.desig_id = coalesce(:desigId, desig.desig_id)";

    //for KPP
    public static final String KPP_QUERY = "select kpp.kpp_id as kppId,dept.dept_id,dept.dept_name as deptName,kpp.desig_id,desig.desig_name as desigName,kpp.kpp_objective as kppObjective,kpp.kpp_performance_indi,kpp.kpp_overall_tar,kpp.kpp_target_period, kpp.kpp_unit_of_measu,kpp.kpp_overall_weightage,kpp.kpp_rating1,kpp.kpp_rating2,kpp.kpp_rating3,kpp.kpp_rating4,kpp.kpp_rating5,kpp.remark,kpp.status_cd from key_perf_parameter  kpp, department dept, designation desig where desig.dept_id =dept.dept_id and desig.dept_id=kpp.dept_id and desig.desig_id=kpp.desig_id and kpp.status_cd='A'and kpp.kpp_id = coalesce(:kppId, kpp.kpp_id) and desig.dept_id = coalesce(:deptId, desig.dept_id) and desig.desig_id = coalesce(:desigId, desig.desig_id) and kpp.kpp_objective= coalesce(:kppObjective,kpp.kpp_objective) and kpp.status_cd =coalesce (:statusCd, kpp.status_cd) order by :sortName asc limit :pageSize offset :pageOffset";

    public static final String KPP_COUNT_QUERY = "select count(*) from key_perf_parameter  kpp, department dept, designation desig where desig.dept_id =dept.dept_id and desig.dept_id=kpp.dept_id and desig.desig_id=kpp.desig_id and kpp.status_cd='A'and kpp.kpp_id = coalesce(:kppId, kpp.kpp_id) and desig.dept_id = coalesce(:deptId, desig.dept_id) and desig.desig_id = coalesce(:desigId, desig.desig_id) and kpp.kpp_objective= coalesce(:kppObjective,kpp.kpp_objective) and kpp.status_cd =coalesce (:statusCd, kpp.status_cd)";
    public static final String KPP_BY_ID_QUERY = "select kpp.kpp_id as kppId,dept.dept_id,dept.dept_name as deptName,kpp.desig_id,desig.desig_name as desigName,kpp.kpp_objective as kppObjective,kpp.kpp_performance_indi,kpp.kpp_overall_tar,kpp.kpp_target_period, kpp.kpp_unit_of_measu,kpp.kpp_overall_weightage,kpp.kpp_rating1,kpp.kpp_rating2,kpp.kpp_rating3,kpp.kpp_rating4,kpp.kpp_rating5,kpp.remark,kpp.status_cd from key_perf_parameter  kpp, department dept, designation desig where desig.dept_id =dept.dept_id and desig.dept_id=kpp.dept_id and desig.desig_id=kpp.desig_id and kpp.status_cd='A'and kpp.kpp_id = coalesce(:kppId, kpp.kpp_id)";

    //for region
    public static final String REGION_QUERY = "select region_id ,region_name,remark,status_cd from region where status_cd = 'A' and region_id= coalesce(:regionId, region_id) and region_name = coalesce(:regionName, region_name) and status_cd = coalesce (:statusCd,status_cd) order by :sortName asc limit :pageSize offset :pageOffset";
    public static final String REGION_COUNT_QUERY = "select count(*) from region where status_cd = 'A' and region_id= coalesce(:regionId, region_id) and region_name = coalesce(:regionName, region_name) and status_cd = coalesce (:statusCd,status_cd)";
//for employee
public static final String EMPLOYEE_QUERY = "select emp.emp_id,emp.dept_id, dept.dept_name,emp.desig_id,desig.desig_name,emp.role_id,emp.region_id, reg.region_name, emp.site_id,sit.site_name, emp.emp_fname,emp.emp_mname,emp.emp_lname,	emp.emp_dob,emp.emp_mbno, emp.emp_emer_mbno,emp.emp_photo,emp.emp_email_id,emp.emp_temp_address,emp.emp_perm_address,emp.emp_gender,emp.emp_blood_group,	emp.REMARK,emp.status_cd from employee emp, department dept, designation desig, region reg,site sit where emp.dept_id =dept.dept_id and desig.desig_id =emp.desig_id and 	emp.region_id=reg.region_id and emp.site_id = sit.site_id and emp.emp_id = coalesce (:empId,emp.emp_id ) and desig.dept_id = coalesce(:deptId, desig.dept_id) and desig.desig_id = coalesce(:desigId, desig.desig_id) and	emp.emp_fname = coalesce(:firstName,emp.emp_fname) and emp.emp_mname = coalesce(:middleName,emp.emp_mname) and emp.emp_lname =coalesce(:lastName, emp.emp_lname) and emp.emp_mbno = coalesce(:empMobNo, emp.emp_mbno) and 	emp.emp_email_id = coalesce(:emailId, emp.emp_email_id) and emp.status_cd=coalesce(:statusCd, emp.status_cd) order by :sortName asc limit :pageSize offset :pageOffset"	;

    public static final String EMPLOYEE_COUNT_QUERY = "select count(*) from employee emp, department dept, designation desig, region reg,site sit where emp.dept_id =dept.dept_id and desig.desig_id =emp.desig_id and 	emp.region_id=reg.region_id and emp.site_id = sit.site_id and emp.emp_id = coalesce (:empId,emp.emp_id )  and desig.dept_id = coalesce(:deptId, desig.dept_id) and desig.desig_id = coalesce(:desigId, desig.desig_id) and	emp.emp_fname = coalesce(:firstName,emp.emp_fname) and emp.emp_mname = coalesce(:middleName,emp.emp_mname) and emp.emp_lname =coalesce(:lastName, emp.emp_lname) and emp.emp_mbno = coalesce(:empMobNo, emp.emp_mbno) and 	emp.emp_email_id = coalesce(:emailId, emp.emp_email_id) and emp.status_cd=coalesce(:statusCd, emp.status_cd)";
    public static final String EMPLOYEE_BY_ID_QUERY = 	"select emp.emp_id,emp.dept_id, dept.dept_name,emp.desig_id,desig.desig_name,emp.role_id,emp.region_id, reg.region_name, emp.site_id,sit.site_name, emp.emp_fname,emp.emp_mname,emp.emp_lname,	emp.emp_dob,emp.emp_mbno, emp.emp_emer_mbno,emp.emp_photo,emp.emp_email_id,emp.emp_temp_address,emp.emp_perm_address,emp.emp_gender,emp.emp_blood_group,	emp.REMARK,emp.status_cd from employee emp, department dept, designation desig, region reg,site sit where emp.dept_id =dept.dept_id and desig.desig_id =emp.desig_id and 	emp.region_id=reg.region_id and emp.site_id = sit.site_id and emp.emp_id = coalesce (:empId,emp.emp_id )";






}
