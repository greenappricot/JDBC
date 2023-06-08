SELECT * FROM BS.EMPLOYEE;
UPDATE BS.EMPLOYEE SET SALARY=1000000;
ROLLBACK;

CREATE TABLE TEST(
    TEST_NO NUMBER,
    TESTCONTENT VARCHAR2(200)
);

-- TCL : 트렌잭션을 컨트롤하는 명령어
-- COMMIT : 지금까지 실행한 수정구문(DML)명령어를 모두 DB에 저장
-- ROLLBACK : 지금까지 실행한 수정구문(DML)명령어를 모두 취소
-- 트랜잭션 : 하나의 작업단위 한개 서비스 -> COMMIT해야 한다
-- 트랜잭션의 대상이 되는 명령어는 : DML(INSERT,UPDATE,DELETE)

INSERT INTO JOB VALUES('J0','강사');
SELECT * FROM JOB;
COMMIT; 

-- ORACLE에서 제공하는 OBJECT활용하기
-- USER, TABLE, VIEW, SEQUENCE, INDEX, SYNONYM, FUNTION, PROCEDURE, PACKAGE 등
